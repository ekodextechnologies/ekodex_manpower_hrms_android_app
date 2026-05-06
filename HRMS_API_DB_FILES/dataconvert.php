<?php
require_once 'connection.php';

use PhpOffice\PhpSpreadsheet\{Spreadsheet,IOFactory};
use PhpOffice\PhpSpreadsheet\Writer\Xlsx;

// Create a new Spreadsheet
$spreadsheet = new Spreadsheet();
$sheet = $spreadsheet->getActiveSheet();

// Prepare the drawing object
$drawing = new \PhpOffice\PhpSpreadsheet\Worksheet\Drawing();
$drawing->setName('Sample image');
$drawing->setDescription('Sample image');
$drawing->setPath('C:\Users\ADMIN\Downloads\table.png'); // Set the path to your image
$drawing->setCoordinates('A1'); // Set the cell address where the picture will be inserted

// Add the drawing to the worksheet
$drawing->setWorksheet($sheet);

// Save the Excel file
$writer = new Xlsx($spreadsheet);
$writer->save('output.xlsx');
?>
