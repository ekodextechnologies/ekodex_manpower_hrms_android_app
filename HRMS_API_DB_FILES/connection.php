<?php  
error_reporting(-1);
// error_reporting(E_ALL);
// ini_set('display_errors', 1);
$servername = "localhost";  
$username = "root";  
$password = "";  
#below is our db name on mysql --- comment
$database = "hrms";  
$conn = new mysqli($servername, $username, $password, $database);  
if ($conn->connect_error) {  
    die("Connection failed: " . $conn->connect_error);  
}  

 define("IMGPATH", "http://192.168.0.108/HRMS/emp_docs/");
 define("IMGPATHPROFILE", "http://192.168.0.108/HRMS/");
//define("IMGPATH", "http://192.168.0.108/HRMS/");
define("UPLOADPATH", "http://192.168.0.108/HRMS/emp_docs/");
?> 


       

