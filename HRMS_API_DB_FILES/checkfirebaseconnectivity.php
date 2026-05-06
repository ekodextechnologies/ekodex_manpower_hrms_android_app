<?php
require 'vendor/autoload.php'; // Make sure Guzzle & Google Client are installed

use GuzzleHttp\Client as GuzzleClient;
use Google\Client as GoogleClient;

$projectId = "hrms-notifications-a063b"; // Your project ID
$serviceAccountFile = __DIR__ . '/hrms-notifications-a063b-2b8b0eb9b3b0.json'; // Path to JSON

$client = new GoogleClient();
$client->setAuthConfig($serviceAccountFile);
$client->addScope('https://www.googleapis.com/auth/firebase.messaging');

$accessToken = $client->fetchAccessTokenWithAssertion()['access_token'];
$url = "https://fcm.googleapis.com/v1/projects/{$projectId}/messages:send";

$http = new GuzzleClient();

$message = [
    "message" => [
        "token" => "dummytoken1234567890", // Invalid token is fine for this test
        "notification" => [
            "title" => "Test",
            "body"  => "Checking FCM billing"
        ]
    ]
];

try {
    $response = $http->post($url, [
        "headers" => [
            "Authorization" => "Bearer " . $accessToken,
            "Content-Type"  => "application/json"
        ],
        "json" => $message
    ]);

    echo "FCM request succeeded: " . $response->getBody();
} catch (\GuzzleHttp\Exception\ClientException $e) {
    echo "Error: " . $e->getResponse()->getBody();
}