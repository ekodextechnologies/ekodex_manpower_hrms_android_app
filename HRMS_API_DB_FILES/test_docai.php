<?php

require __DIR__ . '/vendor/autoload.php';

use Google\Cloud\DocumentAI\V1\Client\DocumentProcessorServiceClient;
use Google\Cloud\DocumentAI\V1\RawDocument;
use Google\Cloud\DocumentAI\V1\ProcessRequest;

putenv('GOOGLE_APPLICATION_CREDENTIALS=' . __DIR__ . '/saiventure-workorderai-d13458269172.json');

// Project / Processor details
$projectId   = "77305643982";
$location    = "us";
$processorId = "f09db5a1cbacaf5b";

$processorName = "projects/$projectId/locations/$location/processors/$processorId";

// Image to process
$imagePath = __DIR__ . "/ad1.jpeg";

if (!file_exists($imagePath)) {
    die("Image not found at: $imagePath");
}

$fileContent = file_get_contents($imagePath);

// Prepare the RawDocument
$rawDocument = new RawDocument([
    'content'  => $fileContent,
    'mime_type' => 'image/jpeg'
]);

// Create client
$client = new DocumentProcessorServiceClient([
    'apiEndpoint' => "$location-documentai.googleapis.com"
]);

// Create the request
$request = new ProcessRequest([
    'name' => $processorName,
    'raw_document' => $rawDocument
]);

try {
    // Process the document
    $response = $client->processDocument($request);

    // Extract fields
    $document = $response->getDocument();
    $entities = $document->getEntities();

    $extractedFields = [];

    foreach ($entities as $entity) {
        $fieldName = $entity->getType() ?? '';
        $fieldValue = $entity->getMentionText() ?? '';
        $extractedFields[$fieldName] = $fieldValue;
    }

    // Print extracted fields
    echo "<pre>";
    print_r($extractedFields);
    echo "</pre>";

} catch (Exception $e) {
    echo "Error processing document: " . $e->getMessage();
}

$client->close();
?>
