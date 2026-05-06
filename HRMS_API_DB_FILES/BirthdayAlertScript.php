<?php
ini_set('max_execution_time', 300);

require __DIR__ . '/vendor/autoload.php';
require_once 'connection.php';

use Google\Client;

$todayMonth = date('m');
$todayDay   = date('d');

$stmt_birthday = $conn->prepare("
    SELECT first_name
    FROM employee
    WHERE active = '0'
      AND MONTH(dob) = ?
      AND DAY(dob) = ?
");
$stmt_birthday->bind_param("ii", $todayMonth, $todayDay);
$stmt_birthday->execute();
$result_birthday = $stmt_birthday->get_result();

if ($result_birthday->num_rows === 0) {
    exit; // No birthdays
}

//all tokens
$stmt_tokens = $conn->prepare("
    SELECT device_token
    FROM users
    WHERE active = '0'
      AND device_token IS NOT NULL
      AND device_token != ''
");
$stmt_tokens->execute();
$stmt_tokens->store_result();
$stmt_tokens->bind_result($token);

if ($stmt_tokens->num_rows === 0) {
    exit; // No devices
}

//firebase code
$client = new Client();
$client->setAuthConfig(__DIR__ . '/hrms-notifications-a063b-2b8b0eb9b3b0.json');
$client->addScope('https://www.googleapis.com/auth/firebase.messaging');

$accessToken = $client->fetchAccessTokenWithAssertion()['access_token'];

$projectId = "hrms-notifications-a063b";
$fcmUrl = "https://fcm.googleapis.com/v1/projects/{$projectId}/messages:send";

/**
 * STEP 5: Notification title
 */
$title = "🎂 Happy Birthday!";

/**
 * STEP 6: LOOP EACH BIRTHDAY EMPLOYEE
 */
while ($emp = $result_birthday->fetch_assoc()) {

    $employeeName = $emp['first_name'];
    $body = "Best wishes to $employeeName 🎉";

    // Reset token pointer for each employee
    $stmt_tokens->data_seek(0);

    /**
     * STEP 7: Send notification to ALL users
     */
    while ($stmt_tokens->fetch()) {

        if (empty($token)) {
            continue;
        }

        $payload = json_encode([
            "message" => [
                "token" => $token,
                "data" => [
                    "type"  => "birthday",
                    "title" => $title,
                    "body"  => $body
                ],
                "notification" => [
                    "title" => $title,
                    "body"  => $body
                ]
            ]
        ]);

        $ch = curl_init($fcmUrl);
        curl_setopt_array($ch, [
            CURLOPT_POST           => true,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_HTTPHEADER     => [
                "Authorization: Bearer $accessToken",
                "Content-Type: application/json"
            ],
            CURLOPT_POSTFIELDS     => $payload
        ]);

        curl_exec($ch);
        curl_close($ch);
    }
}
