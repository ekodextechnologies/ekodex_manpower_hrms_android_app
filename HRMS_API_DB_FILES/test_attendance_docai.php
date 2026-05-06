<?php

require __DIR__ . '/vendor/autoload.php';

use Google\Cloud\DocumentAI\V1\Client\DocumentProcessorServiceClient;
use Google\Cloud\DocumentAI\V1\RawDocument;
use Google\Cloud\DocumentAI\V1\ProcessRequest;

// ----------------------------
// CONFIGURATION
// ----------------------------
putenv('GOOGLE_APPLICATION_CREDENTIALS=' . __DIR__ . '/saiventure-workorderai-d13458269172.json');

$projectId = "77305643982";
$location = "us";  // Replace with your processor location if different
$processorId = "c4e82c795e893fe3";

$processorName = "projects/$projectId/locations/$location/processors/$processorId";

// ----------------------------
// INPUT FILE (PDF or JPG)
// ----------------------------
$filePath = __DIR__ . "/attendance.pdf"; // or "/attendance.jpeg"

if (!file_exists($filePath)) {
    die(json_encode(["error" => "File not found at: $filePath"]));
}

$fileContent = file_get_contents($filePath);
$mimeType = str_ends_with($filePath, ".pdf") ? "application/pdf" : "image/jpeg";

// ----------------------------
// CREATE RAW DOCUMENT
// ----------------------------
$rawDocument = new RawDocument();
$rawDocument->setContent($fileContent);
$rawDocument->setMimeType($mimeType);

// ----------------------------
// CREATE CLIENT
// ----------------------------
$client = new DocumentProcessorServiceClient([
    'apiEndpoint' => "$location-documentai.googleapis.com"
]);

// ----------------------------
// PROCESS DOCUMENT
// ----------------------------
$request = new ProcessRequest([
    'name' => $processorName,
    'raw_document' => $rawDocument
]);

try {
    $response = $client->processDocument($request);
    $document = $response->getDocument();

    $entities = $document->getEntities();

    if (count($entities) === 0) {
        echo json_encode(["error" => "No entities found. Make sure you trained your processor and the file has correct format."]);
    } else {
        $jsonOutput = [];
        foreach ($entities as $entity) {
            $jsonOutput[] = entityToArray($document, $entity);
        }
        header('Content-Type: application/json');
        echo json_encode($jsonOutput, JSON_PRETTY_PRINT);
    }

} catch (Exception $e) {
    echo json_encode(["error" => "Error processing document: " . $e->getMessage()]);
}

$client->close();

// ----------------------------
// FUNCTION: Extract Text from TextAnchor
// ----------------------------
function extractText($fullText, $textAnchor) {
    if (!$textAnchor) return "";
    $segments = $textAnchor->getTextSegments();
    if (!$segments) return "";

    $text = "";
    foreach ($segments as $segment) {
        $start = $segment->getStartIndex();
        $end = $segment->getEndIndex();
        $text .= substr($fullText, $start, $end - $start);
    }
    return $text;
}

// ----------------------------
// FUNCTION: Convert Entity to Array Recursively
// ----------------------------
function entityToArray($document, $entity) {
    $fullText = $document->getText();
    $value = extractText($fullText, $entity->getTextAnchor());
    $children = $entity->getProperties();

    $childArray = [];
    foreach ($children as $child) {
        $childArray[] = entityToArray($document, $child);
    }

    return [
        "type" => $entity->getType(),
        "value" => $value,
        "children" => $childArray
    ];
}
?>
