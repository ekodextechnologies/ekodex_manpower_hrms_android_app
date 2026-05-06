 <?php 
 // error_reporting(0);
require_once 'connection.php';
$response = array();
if(isset($_GET['apicall'])){

    switch($_GET['apicall']){ 

 case 'getSupervisorNetDaysCountForDashboard':

if(isTheseParametersAvailable(array('site_id'))){  
   
$site_id = $_POST['site_id'];
$client_code = $_POST['client_code'];       
$selected_date = $_POST['selected_date'];
$year = date('Y', strtotime($selected_date));
$month = date('n', strtotime($selected_date));
$day = date('j', strtotime($selected_date));


//PRESENT DAYS
 $stmt_present_days = $conn->prepare("SELECT COUNT(id)  from attendance where site_id=? and client_code = ? and at_day = ? and  at_month=? and at_year=?  and (status like '%P%' or status like '%D%' or status like '%N%') and active='0'");
 $stmt_present_days->bind_param("sssss",$site_id,$client_code,$at_day,$at_month,$at_year); 
 $stmt_present_days->execute();
 $stmt_present_days->store_result();

  if ($stmt_present_days->num_rows > 0) {
  $stmt_present_days->bind_result($total_present_days);
  $stmt_present_days->fetch();
}
else
{
    $total_present_days = 0;
}

//HALF DAYS 
$stmt_half_days = $conn->prepare("SELECT COUNT(id)  from attendance where site_id=? and client_code = ? and at_day = ? and  at_month=? and at_year=?  and (status like '%P%' or status like '%D%' or status like '%N%') and active='0'");
 $stmt_half_days->bind_param("sssss",$site_id,$client_code,$at_day,$at_month,$at_year); 
 $stmt_half_days->execute();
 $stmt_half_days->store_result();  

 if ($stmt_half_days->num_rows > 0) {
  $stmt_half_days->bind_result($total_half_days);
  $stmt_half_days->fetch();
}
else
{
    $total_half_days = 0;
}

  // NET DAYS 
  $net_days = $total_present_days + $total_half_days;

//-------------------------------------------------------------------------------------------

  //TOTAL OT DAYS
$stmt_ot_days = $conn->prepare("SELECT COUNT(id) from attendance where site_id=? and client_code = ? and at_day = ? and  at_month=? and at_year=? and (status like '%PP%') and active='0'");
 $stmt_ot_days->bind_param("sssss",$site_id,$client_code,$at_day,$at_month,$at_year); 
 $stmt_ot_days->execute();
 $stmt_ot_days->store_result();  


if ($stmt_ot_days->num_rows > 0) {
  $stmt_ot_days->bind_result($total_ot_days);
  $stmt_ot_days->fetch();
}
else
{
    $total_ot_days = 0;
}

//--------------------------------------------------------------------------------------------

  //TOTAL OT HOURS
$stmt_ot_hours = $conn->prepare("SELECT * from attendance where site_id=? and client_code = ? and at_day = ? and  at_month=? and at_year=? and (status!='P' AND status!='W' and status!='PP') and active='0'");
 $stmt_ot_hours->bind_param("sssss",$site_id,$client_code,$at_day,$at_month,$at_year); 
 $stmt_ot_hours->execute();
 $stmt_ot_hours->store_result();

if ($stmt_ot_hours->num_rows > 0) {
  $stmt_ot_hours->bind_result($total_ot_hours);
  $stmt_ot_hours->fetch();

  $total_ot_hours=0;
    foreach ($query as $key => $value) {
        $total_ot = (float)$total_ot + (float)ltrim($value['status'], 'P');
    }
}
else
{
    $total_ot_hours = 0;
}

//----------------------------------------------------------------------------------------------
    //TOTAL WEEK OFF
$stmt_obj1 = $conn->prepare("SELECT count(id) total_days from attendance where  status='W' and site_id=? client_code = ? and at_day = ?  and at_month=? and at_year=? and active='0'");
 $stmt_obj1->bind_param("sssss",$site_id,$client_code,$at_day,$at_month,$at_year); 
 $stmt_obj1->execute();
 $stmt_obj1->store_result();

if ($stmt_obj1->num_rows > 0) {
  $stmt_obj1->bind_result($obj1_days);
  $stmt_obj1->fetch();
}
else
{
   $obj1_days = 0;
}

//-------------------------------

$stmt_obj2 = $conn->prepare("SELECT count(id) total_days from attendance where  status='WP' and site_id=? client_code = ? and at_day = ?  and at_month=? and at_year=? and active='0'");
 $stmt_obj2->bind_param("sssss",$site_id,$client_code,$at_day,$at_month,$at_year); 
 $stmt_obj2->execute();
 $stmt_obj2->store_result();

if ($stmt_obj2->num_rows > 0) {
  $stmt_obj2->bind_result($obj2_days);
  $stmt_obj2->fetch();
}
else
{
   $obj2_days = 0;
}

$week_off_days = $obj1_days + $obj2_days;

//-------------------------------------------------------------------------------------------------

 // TOTAL DAYS
 $total_days = ($total_present_days + $total_half_days + $obj1_days + $obj2_days)/2 + $total_ot_days;

                 $user = array(  
                'net_days'=>$net_days, 
                'ot_days'=>$total_ot_days,
                'ot_hours'=>$total_ot_hours, 
                'week_off'=>$week_off_days,
                'total'=>$total_days
            );  

            $response['error'] = false;   
            $response['message'] = 'Net days counts successful!!';   
            $response['user'] = $user;   
        }
        else
        {
            $response['error'] = true;   
            $response['message'] = 'No data!!';    
        }
}
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 
//-----------------------------------------------------------------------------------------------------------

    default:   
$response['error'] = true;   
$response['message'] = 'Invalid Operation Called';   
}  
}  
else{ 
//Execute this if no such case exists 
    $response['error'] = true;   
    $response['message'] = 'Invalid API Call';  
}

echo json_encode($response,JSON_PARTIAL_OUTPUT_ON_ERROR);  


function isTheseParametersAvailable($params){  
    foreach($params as $param){  
     if(!isset($_POST[$param])){  
         return false;   
     }  
 }  
 return true;   
} 
?>