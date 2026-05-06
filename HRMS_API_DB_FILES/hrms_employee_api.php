<?php

use Google\Cloud\DocumentAI\V1\Client\DocumentProcessorServiceClient;
use Google\Cloud\DocumentAI\V1\RawDocument;
use Google\Cloud\DocumentAI\V1\ProcessRequest;

use Google\Client;
use GuzzleHttp\Client as GuzzleClient;

ini_set('max_execution_time', 300);

require __DIR__ . '/vendor/autoload.php';

putenv('GOOGLE_APPLICATION_CREDENTIALS=saiventure-workorderai-d13458269172.json');


// putenv('GOOGLE_APPLICATION_CREDENTIALS=' . __DIR__ . '/saiventure-workorderai-d13458269172.json');


 
 // error_reporting(0);


require_once 'connection.php';
$response = array();
if(isset($_GET['apicall'])){

    switch($_GET['apicall']){  
    case 'login':

    if(isTheseParametersAvailable(array('identifier','password'))){  
      
       date_default_timezone_set('Asia/Kolkata');
        $identifier = $_POST['identifier']; 
        $password = $_POST['password'];
        $new_password = md5($password); 

        if(filter_var($identifier, FILTER_VALIDATE_EMAIL)) {
             $field = "email";
        } else {
            $field = "phone";
        }

        //location details
        $user_address   = $_POST['user_address']   ?? null;
        $user_city      = $_POST['user_city']      ?? null;
        $user_state     = $_POST['user_state']     ?? null;
        $user_pincode   = $_POST['user_pincode']   ?? null;
        $user_latitude  = $_POST['user_latitude']  ?? null;
        $user_longitude = $_POST['user_longitude'] ?? null;

        $emp_id = '';

        $created_on    = date('Y-m-d H:i:s');

        $activity_type = 'login';

$stmt_check_user = $conn->prepare("SELECT id, role FROM users WHERE $field = ? AND active='0'");
$stmt_check_user->bind_param("s", $identifier);
$stmt_check_user->execute();
$stmt_check_user->store_result();

if ($stmt_check_user->num_rows > 0) {
  $stmt_check_user->bind_result($id,$role);
                      $stmt_check_user->fetch();

                      //we can resttrict to only spervisor login.
                    //    if($role == "Supervisor")
                    // {
                   $stmt_login = $conn->prepare("
    SELECT id,fullname,role,email,gender,phone,emp_code,address,city,pincode,onboarding,
           client_id,site_id,client_code,bank_name,account_no,bank_ifsc,ac_holder_name,
           bank_address,bank_state,bank_city,bank_micr,card_no,profile_image
    FROM users 
    WHERE $field=? AND password=?
");
$stmt_login->bind_param("ss", $identifier, $new_password);
$stmt_login->execute();
$stmt_login->store_result();

// $stmt_login = $conn->prepare("SELECT id,fullname,role,email,gender,phone,address,city,pincode,onboarding,client_id,site_id,client_code from users WHERE email ='$email'");
//                      $stmt_login->execute();
//                      $stmt_login->store_result();

                     if ($stmt_login->num_rows > 0) {
    
                    $stmt_login->bind_result($id,$fullname,$role,$email,$gender,$phone,$emp_code,$address,$city,$pincode,$onboarding,$client_id,$site_id,$client_code,$bank_name,$account_no,$bank_ifsc,$ac_holder_name,$bank_address,$bank_state,$bank_city,$bank_micr,$card_no,$profile_image);
                    $stmt_login->fetch();


                        $rank = '';

                        //getting employee id
                    $stmt_emp = $conn->prepare(
    "SELECT id,rank FROM employee WHERE employee_code = ? and active = '0' LIMIT 1"
);
$stmt_emp->bind_param("s", $emp_code);
$stmt_emp->execute();
$stmt_emp->store_result();

if ($stmt_emp->num_rows > 0) {
    $stmt_emp->bind_result($emp_id,$rank);
    $stmt_emp->fetch();
    $stmt_emp->close();
}


                    //login log
                    $stmt_log = $conn->prepare(
    "INSERT INTO emp_activity_log
    (user_id, user_activity_type, user_address, user_city, user_state, user_pincode,
     user_latitude, user_longitude, created_by, created_on)
    VALUES (?,?,?,?,?,?,?,?,?,?)"
);

$stmt_log->bind_param(
    "ssssssssss",
    $id,
    $activity_type,
    $user_address,
    $user_city,
    $user_state,
    $user_pincode,
    $user_latitude,
    $user_longitude,
    $id,
    $created_on
);

$stmt_log->execute();
                   
            $user = array(  
                'id'=>$id, 
                'emp_id'=>$emp_id,
                'emp_code'=>$emp_code,
                'fname'=>$fullname,
                'lname'=>"",
                'rank'=>$rank,
                'gender'=>$gender,
                'email'=>$email,
                 'phone'=>$phone,
                  'address'=>$address,
                   'state'=>"",
                    'city'=>$city,
                     'pincode'=>$pincode,
                      'onboarding'=>$onboarding,
                       'role'=>$role,
                       'client_id'=>$client_id,
                       'site_id'=>$site_id,
                       'client_code'=>$client_code,
                        'bank_name' => $bank_name,
                         'account_no' => $account_no,
                          'bank_ifsc' => $bank_ifsc,
                            'ac_holder_name' => $ac_holder_name,
                             'bank_address' => $bank_address,
                             'bank_state' => $bank_state,
                                 'bank_city' => $bank_city,
                                  'bank_micr' => $bank_micr,
                                     'card_no' => $card_no,
                                     'profile_image' => IMGPATHPROFILE . $profile_image
            );  

            $response['error'] = false;   
            $response['message'] = 'Login successful !!';   
            $response['user'] = $user;   
        }
         else{
         $response['error'] = true;   
         $response['message'] = 'Wrong password!!';  
    }
                    }
    //                 else{
    //      $response['error'] = true;   
    //      $response['message'] = 'Only supervisor can login!!';  
    // }
 else
    {
         $response['error'] = true;   
         $response['message'] = 'Email or Phone entered is not registered!!';  
    }    
      }              
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 
//-----------------------------------------------------------------------------------------------------------

 case 'editProfile':

    if(isTheseParametersAvailable(array('fname','mname','lname','id'))){  

        $id = $_POST['id'];        
        $fname = $_POST['fname'];  
        $mname = $_POST['mname'];  
        $lname = $_POST['lname'];  

        $gender = $_POST['gender'];  
        $dob = $_POST['dob'];  
        $address = $_POST['address'];  
        $state = $_POST['state'];  
        $city = $_POST['city'];  
        $pincode = $_POST['pincode'];  

        $phone1 = $_POST['phone1'];  
        $phone2 = $_POST['phone2'];  
        $email_id = $_POST['email_id'];  
        $contact_person = $_POST['contact_person'];  
        $contact_mobile = $_POST['contact_mobile']; 

        $p_address = $_POST['p_address'];  
        $p_state = $_POST['p_state'];  
        $p_city = $_POST['p_city'];  
        $p_pincode = $_POST['p_pincode'];  
        $p_phone1 = $_POST['p_phone1'];
        $p_phone2 = $_POST['p_phone2'];  

        $contact_relation = $_POST['contact_relation'];  
        $contact_email = $_POST['contact_email'];  
        $marital_status = $_POST['marital_status'];  
        $mrg_date = $_POST['mrg_date'];  
        $cast = $_POST['cast'];
        $category = $_POST['category'];  
        $native_place = $_POST['native_place'];  
        $blood_group = $_POST['blood_group'];  

        $driving_license = $_POST['driving_license'];  
        $pancard_no = $_POST['pancard_no'];  
        $aadhar_no = $_POST['aadhar_no'];
        $passport_no = $_POST['passport_no'];  
        $uan_no = $_POST['uan_no'];  
        $passport_valid_date = $_POST['passport_valid_date'];  


        $lang1 = $_POST['lang1'];  
        $lang2 = $_POST['lang2'];  
        $lang3 = $_POST['lang3'];
        $lang4 = $_POST['lang4'];  
        $lang5 = $_POST['lang5']; 

        $hobby1 = $_POST['hobby1'];  
        $hobby2 = $_POST['hobby2'];
        $hobby3 = $_POST['hobby3'];  
        $hobby4 = $_POST['hobby4'];  

        $bank_name = $_POST['bank_name'];  
        $account_no = $_POST['account_no'];
        $bank_address = $_POST['bank_address'];  
        $bank_city = $_POST['bank_city'];  
        $bank_state = $_POST['bank_state'];  
        $bank_ifsc = $_POST['bank_ifsc'];  
        $ac_holder_name = $_POST['ac_holder_name']; 
        $card_no = $_POST['card_no']; 


                     $stmt_edit = $conn->prepare("UPDATE employee set first_name=?,middle_name=?,last_name=?,gender=?,dob=?,address=?,city=?,state=?,pincode=?,phone1=?,phone2=?,email_id=?,contact_person=?,contact_mobile=?,p_address=?,p_state=?,p_city=?,p_pincode=?,p_phone1=?,p_phone2=?,contact_relation=?,contact_email=?,marital_status=?,mrg_date=?,cast=?,category=?,native_place=?,blood_group=?,driving_license=?,pancard_no=?,aadhar_no=?,passport_no=?,uan_no=?,passport_valid_date=?,lang1=?,lang2=?,lang3=?,lang4=?,lang5=?,hobby1=?,hobby2=?,hobby3=?,hobby4=?,bank_name=?,account_no=?,bank_address=?,bank_city=?,bank_state=?,bank_ifsc=?,ac_holder_name=?,card_no=? WHERE id =?");
                     $stmt_edit->bind_param("ssssssssssssssssssssssssssssssssssssssssssssssssssss",$fname,$mname,$lname,$gender,$dob,$address,$city,$state,$pincode,$phone1,$phone2,$email_id,$contact_person,$contact_mobile,$p_address,$p_state,$p_city,$p_pincode,$p_phone1,$p_phone2,$contact_relation,$contact_email,$marital_status,$mrg_date,$cast,$category,$native_place,$blood_group,$driving_license,$pancard_no,$aadhar_no,$passport_no,$uan_no,$passport_valid_date,$lang1,$lang2,$lang3,$lang4,$lang5,$hobby1,$hobby2,$hobby3,$hobby4,$bank_name,$account_no,$bank_address,$bank_city,$bank_state,$bank_ifsc,$ac_holder_name,$card_no,$id);
                     $stmt_edit->execute();
                
                 $response['error'] = false;   
                 $response['message'] = 'Profile updated successfully!!';  
}
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 
//----------------------------------------------------------------------------
     case 'getMyProfile':

    if(isTheseParametersAvailable(array('id'))){  
      
        $id = $_POST['id'];  

 $stmt_get_data = $conn->prepare("SELECT first_name,middle_name,last_name,gender,dob,address,city,state,pincode,phone1,phone2,email_id,contact_person,contact_mobile,p_address,p_state,p_city,p_pincode,p_phone1,p_phone2,contact_relation,contact_email,marital_status,mrg_date,cast,category,native_place,blood_group,driving_license,pancard_no,aadhar_no,passport_no,uan_no,esis_no,pf_no,passport_valid_date,lang1,lang2,lang3,lang4,lang5,hobby1,hobby2,hobby3,hobby4,bank_name,account_no,bank_address,bank_city,bank_state,bank_ifsc,ac_holder_name,card_no,client_id,site_id FROM employee WHERE id ='$id'");
                     $stmt_get_data->execute();
                     $stmt_get_data->store_result();

  if ($stmt_get_data->num_rows > 0) {

  $stmt_get_data->bind_result($first_name,$middle_name,$last_name,$gender,$dob,$address,$city,$state,$pincode,$phone1,$phone2,$email_id,$contact_person,$contact_mobile,$p_address,$p_state,$p_city,$p_pincode,$p_phone1,$p_phone2,$contact_relation,$contact_email,$marital_status,$mrg_date,$cast,$category,$native_place,$blood_group,$driving_license,$pancard_no,$aadhar_no,$passport_no,$uan_no,$esis_no,$pf_no,$passport_valid_date,$lang1,$lang2,$lang3,$lang4,$lang5,$hobby1,$hobby2,$hobby3,$hobby4,$bank_name,$account_no,$bank_address,$bank_city,$bank_state,$bank_ifsc,$ac_holder_name,$card_no,$client_id,$site_id);
  
   $stmt_get_data->fetch();


   $stmt_get_client = $conn->prepare("SELECT DISTINCT company_name FROM new_client WHERE id=? and active = '0'");  
$stmt_get_client->bind_param("s",$client_id); 
$stmt_get_client->execute();  
$stmt_get_client->store_result(); 
$stmt_get_client->bind_result($client_name); 
$stmt_get_client->fetch(); 

   $stmt_get_site = $conn->prepare("SELECT DISTINCT site_name FROM client_rates WHERE client_id=? and active = '0'");  
$stmt_get_site->bind_param("s",$site_id); 
$stmt_get_site->execute();  
$stmt_get_site->store_result(); 
$stmt_get_site->bind_result($site_name); 
$stmt_get_site->fetch(); 


                 $user = array(  
                'first_name'=>$first_name, 
                'middle_name'=>$middle_name,
                'last_name'=>$last_name,
                'gender'=>$gender,
                'dob'=>$dob,
                'address'=>$address,
                'state'=>$state,
                'city'=>$city,
                'pincode'=>$pincode,
                'phone1'=>$phone1,
                'phone2'=>$phone2, 
                'email_id'=>$email_id,
                'contact_person'=>$contact_person,
                'contact_mobile'=>$contact_mobile,
                'p_address'=>$p_address,
                'p_state'=>$p_state,
                'p_city'=>$p_city,
                'p_pincode'=>$p_pincode,
                'p_phone1'=>$p_phone2,
                'p_phone2'=>$p_phone2,

                'contact_relation'=>$contact_relation, 
                'contact_email'=>$contact_email,
                'marital_status'=>$marital_status,
                'mrg_date'=>$mrg_date,
                'cast'=>$cast,
                'category'=>$category,
                'native_place'=>$native_place,
                'blood_group'=>$blood_group,
                'driving_license'=>$driving_license,
                'passport_no'=>$passport_no,
                'aadhar_no'=>$aadhar_no, 
                'pancard_no'=>$pancard_no,
                'uan_no'=>$uan_no,
                'esis_no'=>$esis_no,
                'pf_no'=>$pf_no,
                'passport_valid_date'=>$passport_valid_date,

                'bank_name'=>$bank_name,
                'account_no'=>$account_no,
                'bank_address'=>$bank_address,
                'bank_state'=>$bank_state,
                'bank_city'=>$bank_city,
                'bank_ifsc'=>$bank_ifsc,
                'acc_holder_name'=>$ac_holder_name, 
                'card_no'=>$card_no,

                'lang1'=>$lang1,
                'lang2'=>$lang2,
                'lang3'=>$lang3,
                'lang4'=>$lang4,
                'lang5'=>$lang5,
                'hobby1'=>$hobby1,
                'hobby2'=>$hobby2,
                'hobby3'=>$hobby3,
                'hobby4'=>$hobby4,

                 'client_name'=>$client_name,
                  'site_name'=>$site_name
            );  

            $response['error'] = false;   
            $response['message'] = 'Profile data fetched successful!!';   
            $response['user'] = $user;   
        }
        else
        {
            $response['error'] = true;   
            $response['message'] = 'Profile data not found!!';    
        }
       
}
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 
//-----------------------------------------------------------------------------------------------------------

// case 'checkIn':

//     if(isTheseParametersAvailable(array('employee_id'))){  
//         date_default_timezone_set('Asia/Kolkata');
//         $employee_id = $_POST['employee_id']; 
//         $check_in_time = date('h:i a');      
//         $created_on = date('Y-m-d H:i:s');
//         $check_in_date = date('Y-m-d');

//         $emp_name = $_POST['emp_name'];
//         $emp_code = $_POST['emp_code'];
//         $rank = $_POST['rank'];
//         $status = 'P';
//         $client_code = $_POST['client_code'];
//         $site_id = $_POST['site_id'];
//         $created_on = date('Y-m-d H:i:s');
//         $att_type = "Both";
//         $dateArray = date_parse_from_format('Y-m-d', $check_in_date);

//                      $stmt_check_in = $conn->prepare("INSERT INTO emp_attendance (employee_id,check_in_date,check_in_time,created_by,created_on) VALUES (?, ?, ?, ?, ?)"); 

//                      $stmt_check_in->bind_param("sssss",$employee_id,$check_in_date,$check_in_time,$employee_id,$created_on);
//                      $stmt_check_in->execute();
                   

//                       $response['error'] = false;   
//                       $response['message'] = 'Attendance added successfully!!'; 
//                      }  
//                       else{
//                        $response['error'] = true;   
//                        $response['message'] = 'Some problem occured!!';   
//                       }      
//     }       
//                      }  
//                       else{
//                        $response['error'] = true;   
//                        $response['message'] = 'Some problem occured!!';   
//                       }             
// }
//     else
//     {
//         $response['error'] = true;   
//         $response['message'] = 'required parameters are not available';  
//     }
//     break; 


    //Check in by matching coordinates from client rates table

//     case 'checkIn':

//     if (isTheseParametersAvailable(array('employee_id'))) {  
        
//         date_default_timezone_set('Asia/Kolkata');

//         $employee_id   = $_POST['employee_id']; 
//         $emp_name      = $_POST['emp_name'];
//         $emp_code      = $_POST['emp_code'];
//         $rank          = $_POST['rank'];
//         $client_code   = $_POST['client_code'];
//         $site_id       = $_POST['site_id'];

//         $check_in_time = date('h:i a');      
//         $check_in_date = date('Y-m-d');
//         $created_by   = $_POST['created_by']; 
//         $created_on    = date('Y-m-d H:i:s');
//         $status        = 'P';
//         $att_type      = "Both";

//         $check_in_latitude  = $_POST['latitude'];
// $check_in_longitude = $_POST['longitude'];
// $check_in_address   = $_POST['address'];
// $check_in_city      = $_POST['city'];
// $check_in_state     = $_POST['state'];
// $check_in_pincode   = $_POST['pincode'];


//         $dateArray = date_parse_from_format('Y-m-d', $check_in_date);


// $radius = 100; // in meters

// // Assuming your table is 'office_locations' with 'lat' and 'lng'
// $sql = "SELECT id, office_latitude, office_longitude,
//         (6371000 * acos(
//             cos(radians(?)) *
//             cos(radians(office_latitude)) *
//             cos(radians(office_longitude) - radians(?)) +
//             sin(radians(?)) *
//             sin(radians(office_latitude))
//         )) AS distance
//         FROM client_rates where client_code = ?
//         HAVING distance <= ?
//         ORDER BY distance ASC
//         LIMIT 1";

// $stmt = $conn->prepare($sql);
// $stmt->bind_param("sssss", $check_in_latitude, $check_in_longitude, $check_in_latitude,$client_code,$radius);
// $stmt->execute();
// $result = $stmt->get_result();

// if ($result->num_rows > 0) {
//         $stmt_check_in = $conn->prepare("
//     INSERT INTO emp_attendance 
//     (
//         employee_id,
//         emp_code,
//         check_in_date,
//         check_in_time,
//         checkin_latitude,
//         checkin_longitude,
//         checkin_address,
//         checkin_city,
//         checkin_state,
//         checkin_pincode,
//         created_by,
//         created_on
//     )
//     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
// ");


// $stmt_check_in->bind_param(
//     "ssssssssssss",
//     $employee_id,
//     $emp_code,
//     $check_in_date,
//     $check_in_time,
//     $check_in_latitude,
//     $check_in_longitude,
//     $check_in_address,
//     $check_in_city,
//     $check_in_state,
//     $check_in_pincode,
//     $created_by,
//     $created_on
// );

//         if ($stmt_check_in->execute()) {
//             $response['error']   = false;   
//             $response['message'] = 'Attendance added successfully!';
//         } else {
//             $response['error']   = true;
//             $response['message'] = 'Database error! Unable to insert attendance.';
//         }
//         } else {
//     $response['error']   = true;
//             $response['message'] = 'You are too far from the office. Please move closer to check in.';
// }
    

//     } else {

//         $response['error']   = true;   
//         $response['message'] = 'Required parameters are not available';

//     }

//     break;

    //Check in by matching coordinates from employee_login_coordinates table
// case 'checkIn':

//     if (isTheseParametersAvailable(array('employee_id'))) {  

//         date_default_timezone_set('Asia/Kolkata');

//         $employee_id   = $_POST['employee_id']; 
//         $emp_name      = $_POST['emp_name'];
//         $emp_code      = $_POST['emp_code'];
//         $rank          = $_POST['rank'];
//         $client_code   = $_POST['client_code'];
//         $site_id       = $_POST['site_id'];

//         $check_in_time = date('H:i:s');      
//         $check_in_date = date('Y-m-d');
//         $created_by    = $_POST['created_by']; 
//         $created_on    = date('Y-m-d H:i:s');
//         $status        = 'P';
//         $att_type      = "Both";

//         $check_in_latitude  = $_POST['latitude'];
//         $check_in_longitude = $_POST['longitude'];
//         $check_in_address   = $_POST['address'];
//         $check_in_city      = $_POST['city'];
//         $check_in_state     = $_POST['state'];
//         $check_in_pincode   = $_POST['pincode'];

//         $radius = 100; // in meters
//         $can_check_in = false;

//         // Fetch all coordinates for this employee
//         $sql = "SELECT office_latitude, office_longitude 
//                 FROM employee_login_coordinates 
//                 WHERE emp_code = ? AND active = '0'";

//         $stmt = $conn->prepare($sql);
//         $stmt->bind_param("s", $emp_code);
//         $stmt->execute();
//         $result = $stmt->get_result();

//         if ($result->num_rows > 0) {
//             while ($row = $result->fetch_assoc()) {
//                 $office_lat = $row['office_latitude'];
//                 $office_lng = $row['office_longitude'];

//                 // Haversine formula in PHP
//                 $earth_radius = 6371000; // meters
//                 $latFrom = deg2rad($check_in_latitude);
//                 $lonFrom = deg2rad($check_in_longitude);
//                 $latTo   = deg2rad($office_lat);
//                 $lonTo   = deg2rad($office_lng);

//                 $latDelta = $latTo - $latFrom;
//                 $lonDelta = $lonTo - $lonFrom;

//                 $angle = 2 * asin(sqrt(pow(sin($latDelta / 2), 2) +
//                           cos($latFrom) * cos($latTo) * pow(sin($lonDelta / 2), 2)));

//                 $distance = $earth_radius * $angle; // distance in meters

//                 if ($distance <= $radius) {
//                     $can_check_in = true;
//                     break; // no need to check further
//                 }
//             }

//             if ($can_check_in) {
//                 // Insert attendance
//                 $stmt_check_in = $conn->prepare("
//                     INSERT INTO emp_attendance 
//                     (
//                         employee_id,
//                         emp_code,
//                         check_in_date,
//                         check_in_time,
//                         checkin_latitude,
//                         checkin_longitude,
//                         checkin_address,
//                         checkin_city,
//                         checkin_state,
//                         checkin_pincode,
//                         created_by,
//                         created_on
//                     )
//                     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
//                 ");

//                 $stmt_check_in->bind_param(
//                     "ssssssssssss",
//                     $employee_id,
//                     $emp_code,
//                     $check_in_date,
//                     $check_in_time,
//                     $check_in_latitude,
//                     $check_in_longitude,
//                     $check_in_address,
//                     $check_in_city,
//                     $check_in_state,
//                     $check_in_pincode,
//                     $created_by,
//                     $created_on
//                 );

//                 if ($stmt_check_in->execute()) {

//                     //new part , push entry for admin table also

//                     // Now also insert into main attendance table

// $dateArray = date_parse_from_format('Y-m-d', $check_in_date);

// // Get last uniqueid
// $stmt_last_id = $conn->prepare("SELECT MAX(uniqueid) FROM attendance");
// $stmt_last_id->execute();
// $stmt_last_id->bind_result($lastid);
// $stmt_last_id->fetch();
// $stmt_last_id->close();

// $new_id = ($lastid !== null) ? $lastid + 1 : 1;

// // Check if already exists for that date
// $stmt_check = $conn->prepare("
//     SELECT id FROM attendance 
//     WHERE site_id=? AND client_code=? AND emp_code=? 
//     AND at_day=? AND at_month=? AND at_year=? AND active='0'
// ");

// $stmt_check->bind_param(
//     "ssssss",
//     $site_id,
//     $client_code,
//     $emp_code,
//     $dateArray['day'],
//     $dateArray['month'],
//     $dateArray['year']
// );

// $stmt_check->execute();
// $stmt_check->store_result();

// if ($stmt_check->num_rows > 0) {

//     // Update status to P
//     $stmt_update = $conn->prepare("
//         UPDATE attendance 
//         SET status='P', modified_by=?, modified_on=? 
//         WHERE site_id=? AND client_code=? AND emp_code=? 
//         AND at_day=? AND at_month=? AND at_year=? AND active='0'
//     ");

//     $stmt_update->bind_param(
//         "ssssssss",
//         $created_by,
//         $created_on,
//         $site_id,
//         $client_code,
//         $emp_code,
//         $dateArray['day'],
//         $dateArray['month'],
//         $dateArray['year']
//     );

//     $stmt_update->execute();
//     $stmt_update->close();

// } else {

//     // Insert new attendance record
//     $stmt_insert = $conn->prepare("
//         INSERT INTO attendance 
//         (site_id, client_code, emp_name, rank, emp_code, 
//         at_day, at_month, at_year, status, att_type, uniqueid, created_by, created_on) 
//         VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
//     ");

//     $stmt_insert->bind_param(
//         "sssssssssssss",
//         $site_id,
//         $client_code,
//         $emp_name,
//         $rank,
//         $emp_code,
//         $dateArray['day'],
//         $dateArray['month'],
//         $dateArray['year'],
//         $status,          // P
//         $att_type,        // Both
//         $new_id,
//         $created_by,
//         $created_on
//     );

//     $stmt_insert->execute();
//     $stmt_insert->close();
// }

// $stmt_check->close();

//                     $response['error']   = false;   
//                     $response['message'] = 'Attendance added successfully!';
//                 } else {
//                     $response['error']   = true;
//                     $response['message'] = 'Database error! Unable to insert attendance.';
//                 }

//             } else {
//                 $response['error']   = true;
//                 $response['message'] = 'You are too far from the office. Please move closer to check in.';
//             }

//         } else {
//             $response['error']   = true;
//             $response['message'] = 'No office coordinates found for this employee.';
//         }

//     } else {
//         $response['error']   = true;   
//         $response['message'] = 'Required parameters are not available';
//     }

//     break;

 
 // case 'checkIn':

 //    date_default_timezone_set('Asia/Kolkata');

 //    try {

 //        $employee_id   = $_POST['employee_id'];
 //        $emp_name      = $_POST['emp_name'];
 //        $emp_code      = $_POST['emp_code'];
 //        $rank          = $_POST['rank'];
 //        $client_code   = $_POST['client_code'];
 //        $site_id       = $_POST['site_id'];
 // $client_id   = $_POST['client_id'];
 //        $check_in_time = date('h:i a');
 //        $check_in_date = date('Y-m-d');
 //        $created_by    = $_POST['created_by'];
 //        $created_on    = date('Y-m-d H:i:s');

 //        $status   = 'P';
 //        $att_type = 'Both';

 //        $check_in_latitude  = $_POST['latitude'];
 //        $check_in_longitude = $_POST['longitude'];
 //        $check_in_address   = $_POST['address'];
 //        $check_in_city      = $_POST['city'];
 //        $check_in_state     = $_POST['state'];
 //        $check_in_pincode   = $_POST['pincode'];

 //        $radius = 100;
 //        $can_check_in = false;

 //        // =========================
 //        // 1️⃣ CHECK LOCATION
 //        // =========================
 //        $stmt = $conn->prepare("SELECT office_latitude, office_longitude 
 //                                FROM employee_login_coordinates 
 //                                WHERE emp_code = ? AND active = '0'");

 //        if (!$stmt) throw new Exception("Prepare failed (coordinates)");

 //        $stmt->bind_param("s", $emp_code);
 //        $stmt->execute();
 //        $result = $stmt->get_result();

 // //no coordinates found
 //        if ($result->num_rows == 0)
 //            throw new Exception("No login details found for this employee.");

 //        while ($row = $result->fetch_assoc()) {

 //            $earth_radius = 6371000;

 //            $latFrom = deg2rad($check_in_latitude);
 //            $lonFrom = deg2rad($check_in_longitude);
 //            $latTo   = deg2rad($row['office_latitude']);
 //            $lonTo   = deg2rad($row['office_longitude']);

 //            $latDelta = $latTo - $latFrom;
 //            $lonDelta = $lonTo - $lonFrom;

 //            $angle = 2 * asin(sqrt(pow(sin($latDelta/2),2) +
 //                     cos($latFrom) * cos($latTo) *
 //                     pow(sin($lonDelta/2),2)));

 //            $distance = $earth_radius * $angle;

 //            if ($distance <= $radius) {
 //                $can_check_in = true;
 //                break;
 //            }
 //        }

 //        $stmt->close();

 //        if (!$can_check_in)
 //            throw new Exception("You are too far from the office.");

 //        // =========================
 //        // 2️⃣ START TRANSACTION
 //        // =========================
 //        $conn->begin_transaction();

 //        // =========================
 //        // 3️⃣ INSERT INTO emp_attendance
 //        // =========================
 //        $stmt_check_in = $conn->prepare("
 //            INSERT INTO emp_attendance 
 //            (employee_id, emp_code, check_in_date, check_in_time,
 //             checkin_latitude, checkin_longitude, checkin_address,
 //             checkin_city, checkin_state, checkin_pincode,
 //             created_by, created_on, site_id,client_id)
 //            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
 //        ");

 //        if (!$stmt_check_in) throw new Exception("Prepare failed (emp_attendance)");

 //        $stmt_check_in->bind_param(
 //            "ssssssssssssss",
 //            $employee_id,
 //            $emp_code,
 //            $check_in_date,
 //            $check_in_time,
 //            $check_in_latitude,
 //            $check_in_longitude,
 //            $check_in_address,
 //            $check_in_city,
 //            $check_in_state,
 //            $check_in_pincode,
 //            $created_by,
 //            $created_on,
 //            $site_id,
 //            $client_id
 //        );

 //        if (!$stmt_check_in->execute())
 //            throw new Exception("Insert failed (emp_attendance)");

 //        $stmt_check_in->close();

 //        // =========================
 //        // 4️⃣ INSERT / UPDATE attendance
 //        // =========================
 //        $dateArray = date_parse_from_format('Y-m-d', $check_in_date);

 //        $stmt_check = $conn->prepare("
 //            SELECT id FROM attendance
 //            WHERE site_id=? AND client_code=? AND emp_code=? 
 //            AND at_day=? AND at_month=? AND at_year=? AND active='0'
 //        ");

 //        if (!$stmt_check) throw new Exception("Prepare failed (attendance check)");

 //        $stmt_check->bind_param(
 //            "ssssss",
 //            $site_id,
 //            $client_code,
 //            $emp_code,
 //            $dateArray['day'],
 //            $dateArray['month'],
 //            $dateArray['year']
 //        );

 //        $stmt_check->execute();
 //        $stmt_check->store_result();

 //        if ($stmt_check->num_rows > 0) {

 //            $stmt_update = $conn->prepare("
 //                UPDATE attendance
 //                SET status='P', modified_by=?, modified_on=?
 //                WHERE site_id=? AND client_code=? AND emp_code=? 
 //                AND at_day=? AND at_month=? AND at_year=? AND active='0'
 //            ");

 //            if (!$stmt_update) throw new Exception("Prepare failed (attendance update)");

 //            $stmt_update->bind_param(
 //                "ssssssss",
 //                $created_by,
 //                $created_on,
 //                $site_id,
 //                $client_code,
 //                $emp_code,
 //                $dateArray['day'],
 //                $dateArray['month'],
 //                $dateArray['year']
 //            );

 //            if (!$stmt_update->execute())
 //                throw new Exception("Update failed (attendance)");

 //            $stmt_update->close();

 //        } else {

 //            $stmt_last_id = $conn->prepare("SELECT MAX(uniqueid) FROM attendance");
 //            $stmt_last_id->execute();
 //            $stmt_last_id->bind_result($lastid);
 //            $stmt_last_id->fetch();
 //            $stmt_last_id->close();

 //            $new_id = ($lastid !== null) ? $lastid + 1 : 1;

 //            $stmt_insert = $conn->prepare("
 //                INSERT INTO attendance
 //                (site_id, client_code, emp_name, rank, emp_code,
 //                 at_day, at_month, at_year, status, att_type,
 //                 uniqueid, created_by, created_on)
 //                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
 //            ");

 //            if (!$stmt_insert) throw new Exception("Prepare failed (attendance insert)");

 //            $stmt_insert->bind_param(
 //                "sssssssssssss",
 //                $site_id,
 //                $client_code,
 //                $emp_name,
 //                $rank,
 //                $emp_code,
 //                $dateArray['day'],
 //                $dateArray['month'],
 //                $dateArray['year'],
 //                $status,
 //                $att_type,
 //                $new_id,
 //                $created_by,
 //                $created_on
 //            );

 //            if (!$stmt_insert->execute())
 //                throw new Exception("Insert failed (attendance)");

 //            $stmt_insert->close();
 //        }

 //        $stmt_check->close();

 //        // =========================
 //        // 5️⃣ COMMIT
 //        // =========================
 //        $conn->commit();

 //        $response['error'] = false;
 //        $response['message'] = "Attendance added successfully!";

 //    } catch (Exception $e) {

 //        $conn->rollback();
 //        $response['error'] = true;
 //        $response['message'] = $e->getMessage();
 //    }

 //    break;



case 'checkIn':

date_default_timezone_set('Asia/Kolkata');

try {

    // =========================
    // INPUTS
    // =========================
    $employee_id   = $_POST['employee_id'];
    $emp_name      = $_POST['emp_name'];
    $emp_code      = $_POST['emp_code'];
    $client_code   = $_POST['client_code'];
    $site_id       = $_POST['site_id'];
    $client_id     = $_POST['client_id'];
    $created_by    = $_POST['created_by'];

    $check_in_time = date('h:i a');
    $check_in_date = date('Y-m-d');
    $created_on    = date('Y-m-d H:i:s');

    $status   = 'P';
    $att_type = 'Both';

    $check_in_latitude  = $_POST['latitude'];
    $check_in_longitude = $_POST['longitude'];
    $check_in_address   = $_POST['address'];
    $check_in_city      = $_POST['city'];
    $check_in_state     = $_POST['state'];
    $check_in_pincode   = $_POST['pincode'];

    $radius = 100;
    $can_check_in = false;

    // =========================
    // 1️⃣ GET RANK FROM DB (CRITICAL)
    // =========================
    $rank = '';

    $stmt_emp = $conn->prepare("
        SELECT rank FROM employee 
        WHERE employee_code=? AND active='0' LIMIT 1
    ");
    if (!$stmt_emp) throw new Exception("Prepare failed (employee)");

    $stmt_emp->bind_param("s", $emp_code);
    $stmt_emp->execute();
    $stmt_emp->bind_result($rank);
    $stmt_emp->fetch();
    $stmt_emp->close();

    if (empty($rank)) {
        throw new Exception("Employee rank not found.");
    }

    // =========================
    // 2️⃣ CHECK LOCATION
    // =========================
    $stmt = $conn->prepare("
        SELECT office_latitude, office_longitude 
        FROM employee_login_coordinates 
        WHERE emp_code = ? AND active = '0'
    ");
    if (!$stmt) throw new Exception("Prepare failed (coordinates)");

    $stmt->bind_param("s", $emp_code);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows == 0)
        throw new Exception("No login location found.");

    while ($row = $result->fetch_assoc()) {

        $earth_radius = 6371000;

        $latFrom = deg2rad($check_in_latitude);
        $lonFrom = deg2rad($check_in_longitude);
        $latTo   = deg2rad($row['office_latitude']);
        $lonTo   = deg2rad($row['office_longitude']);

        $latDelta = $latTo - $latFrom;
        $lonDelta = $lonTo - $lonFrom;

        $angle = 2 * asin(sqrt(pow(sin($latDelta/2),2) +
                 cos($latFrom) * cos($latTo) *
                 pow(sin($lonDelta/2),2)));

        $distance = $earth_radius * $angle;

        if ($distance <= $radius) {
            $can_check_in = true;
            break;
        }
    }

    $stmt->close();

    if (!$can_check_in)
        throw new Exception("You are too far from office.");

    // =========================
    // 3️⃣ START TRANSACTION
    // =========================
    $conn->begin_transaction();

    // =========================
    // 4️⃣ DUPLICATE CHECK (FAST CHECK)
    // =========================
    $stmt_dup = $conn->prepare("
        SELECT id FROM emp_attendance 
        WHERE emp_code=? 
        AND check_in_date=? 
        AND site_id=? 
        AND active='0'
        LIMIT 1
    ");

    if (!$stmt_dup) throw new Exception("Prepare failed (dup check)");

    $stmt_dup->bind_param("sss", $emp_code, $check_in_date, $site_id);
    $stmt_dup->execute();
    $stmt_dup->store_result();

    if ($stmt_dup->num_rows > 0) {
        throw new Exception("Already checked in for today.");
    }
    $stmt_dup->close();

    // =========================
    // 5️⃣ INSERT INTO emp_attendance
    // =========================
    $stmt_check_in = $conn->prepare("
        INSERT INTO emp_attendance 
        (employee_id, emp_code, check_in_date, check_in_time,
         checkin_latitude, checkin_longitude, checkin_address,
         checkin_city, checkin_state, checkin_pincode,
         created_by, created_on, site_id, client_id)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    ");

    if (!$stmt_check_in) throw new Exception("Prepare failed (emp_attendance)");

    $stmt_check_in->bind_param(
        "ssssssssssssss",
        $employee_id,
        $emp_code,
        $check_in_date,
        $check_in_time,
        $check_in_latitude,
        $check_in_longitude,
        $check_in_address,
        $check_in_city,
        $check_in_state,
        $check_in_pincode,
        $created_by,
        $created_on,
        $site_id,
        $client_id
    );

    if (!$stmt_check_in->execute()) {

        if ($conn->errno == 1062) {
            throw new Exception("Already checked in for today.");
        } else {
            throw new Exception("Insert failed (emp_attendance)");
        }
    }

    $stmt_check_in->close();

    // =========================
    // 6️⃣ INSERT / UPDATE attendance (ERP TABLE)
    // =========================
    $dateArray = date_parse_from_format('Y-m-d', $check_in_date);

    $stmt_check = $conn->prepare("
        SELECT id FROM attendance
        WHERE site_id=? AND client_code=? AND emp_code=? 
        AND at_day=? AND at_month=? AND at_year=? AND active='0'
    ");

    if (!$stmt_check) throw new Exception("Prepare failed (attendance check)");

    $stmt_check->bind_param(
        "ssssss",
        $site_id,
        $client_code,
        $emp_code,
        $dateArray['day'],
        $dateArray['month'],
        $dateArray['year']
    );

    $stmt_check->execute();
    $stmt_check->store_result();

    if ($stmt_check->num_rows > 0) {

        // UPDATE ONLY (no rank change)
        $stmt_update = $conn->prepare("
            UPDATE attendance
            SET status='P', modified_by=?, modified_on=?
            WHERE site_id=? AND client_code=? AND emp_code=? 
            AND at_day=? AND at_month=? AND at_year=? AND active='0'
        ");

        if (!$stmt_update) throw new Exception("Prepare failed (attendance update)");

        $stmt_update->bind_param(
            "ssssssss",
            $created_by,
            $created_on,
            $site_id,
            $client_code,
            $emp_code,
            $dateArray['day'],
            $dateArray['month'],
            $dateArray['year']
        );

        if (!$stmt_update->execute())
            throw new Exception("Update failed (attendance)");

        $stmt_update->close();

    } else {

        $stmt_last_id = $conn->prepare("SELECT MAX(uniqueid) FROM attendance");
        $stmt_last_id->execute();
        $stmt_last_id->bind_result($lastid);
        $stmt_last_id->fetch();
        $stmt_last_id->close();

        $new_id = ($lastid !== null) ? $lastid + 1 : 1;

        $stmt_insert = $conn->prepare("
            INSERT INTO attendance
            (site_id, client_code, emp_name, rank, emp_code,
             at_day, at_month, at_year, status, att_type,
             uniqueid, created_by, created_on)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        ");

        if (!$stmt_insert) throw new Exception("Prepare failed (attendance insert)");

        $stmt_insert->bind_param(
            "sssssssssssss",
            $site_id,
            $client_code,
            $emp_name,
            $rank,
            $emp_code,
            $dateArray['day'],
            $dateArray['month'],
            $dateArray['year'],
            $status,
            $att_type,
            $new_id,
            $created_by,
            $created_on
        );

        if (!$stmt_insert->execute())
            throw new Exception("Insert failed (attendance)");

        $stmt_insert->close();
    }

    $stmt_check->close();

    // =========================
    // 7️⃣ COMMIT
    // =========================
    $conn->commit();

    $response['error'] = false;
    $response['message'] = "Check-in successful!";

} catch (Exception $e) {

    $conn->rollback();
    $response['error'] = true;
    $response['message'] = $e->getMessage();
}

break;


//----------------------------------------------------------------------------    
//     case 'checkOut':

//     if(isTheseParametersAvailable(array('employee_id'))){  
//         date_default_timezone_set('Asia/Kolkata');

//         $employee_id = $_POST['employee_id']; 
//         $client_id = $_POST['client_id']; 
//         $site_id = $_POST['site_id']; 
//         $rank = $_POST['rank']; 

//         $check_out_time = date('h:i a');    
//         $check_in_date = date('Y-m-d');
    
//      $stmt_check= $conn->prepare("SELECT id from emp_attendance where employee_id = ? and check_in_date = ?");
//                      $stmt_check->bind_param("ss",$employee_id,$check_in_date);
//                       $stmt_check->execute();
//                       $stmt_check->store_result();
//                       if($stmt_check->num_rows>0)
//                       {
//                         $stmt_check_out = $conn->prepare("UPDATE emp_attendance SET check_out_time=? where employee_id = ? and check_in_date = ?");
 
//                      $stmt_check_out->bind_param("sss",$check_out_time,$employee_id,$check_in_date);
//                      if($stmt_check_out->execute())
//                      {

//                       $response['error'] = false;   
//                       $response['message'] = 'Checked out successfully!!'; 
//                      }  
//                       else{
//                        $response['error'] = true;   
//                        $response['message'] = 'Some problem occured!!';   
//                       }     
//                       }   
//                       else
//                       {
//                        $response['error'] = true;   
//                        $response['message'] = 'Please first check in!!';   
//                       }                         
// }
//     else
//     {
//         $response['error'] = true;   
//         $response['message'] = 'required parameters are not available';  
//     }
//     break; 


// case 'checkOut':

//     if(isTheseParametersAvailable(array('employee_id'))){  
//         date_default_timezone_set('Asia/Kolkata');

//         $employee_id = $_POST['employee_id']; 
//         $client_id   = $_POST['client_id']; 
//         $site_id     = $_POST['site_id'];     // used as rate_id
//         $client_code = $_POST['client_code'];
//         $emp_name    = $_POST['emp_name'];
//         $emp_code    = $_POST['emp_code'];
//         $created_on  = date('Y-m-d H:i:s');
//         $attendance_date = date('Y-m-d');
//         $att_type    = "Both";
//         $dateArray   = date_parse_from_format('Y-m-d', $attendance_date);

//          $check_out_time = date('h:i a');
//         //$check_out_time = '06:14 pm';    
//         $check_in_date  = date('Y-m-d');

//          $rank   = $_POST['rank'];


//         $check_out_latitude  = $_POST['latitude'];
// $check_out_longitude = $_POST['longitude'];
// $check_out_address   = $_POST['address'];
// $check_out_city      = $_POST['city'];
// $check_out_state     = $_POST['state'];
// $check_out_pincode   = $_POST['pincode'];


// $radius = 100; // in meters

// // Assuming your table is 'office_locations' with 'lat' and 'lng'
// $sql = "SELECT id, office_latitude, office_longitude,
//         (6371000 * acos(
//             cos(radians(?)) *
//             cos(radians(office_latitude)) *
//             cos(radians(office_longitude) - radians(?)) +
//             sin(radians(?)) *
//             sin(radians(office_latitude))
//         )) AS distance
//         FROM client_rates where client_code = ?
//         HAVING distance <= ?
//         ORDER BY distance ASC
//         LIMIT 1";

// $stmt = $conn->prepare($sql);
// $stmt->bind_param("sssss", $check_out_latitude, $check_out_longitude, $check_out_latitude,$client_code,$radius);
// $stmt->execute();
// $result = $stmt->get_result();

// if ($result->num_rows > 0) {
//         // CHECK IF CHECK-IN EXISTS
//         $stmt_check = $conn->prepare("SELECT id FROM emp_attendance WHERE employee_id = ? AND check_in_date = ?");
//         $stmt_check->bind_param("ss",$employee_id,$check_in_date);
//         $stmt_check->execute();
//         $stmt_check->store_result();

//         if($stmt_check->num_rows > 0)
//         {
//             // UPDATE CHECK-OUT TIME
//              $stmt_check_out = $conn->prepare("
//     UPDATE emp_attendance 
//     SET 
//         check_out_time = ?,
//         checkout_latitude = ?,
//         checkout_longitude = ?,
//         checkout_address = ?,
//         checkout_city = ?,
//         checkout_state = ?,
//         checkout_pincode = ?
//     WHERE employee_id = ? 
//       AND check_in_date = ? 
//       AND active = '0'
// ");

// $stmt_check_out->bind_param(
//     "sssssssss",
//     $check_out_time,
//     $check_out_latitude,
//     $check_out_longitude,
//     $check_out_address,
//     $check_out_city,
//     $check_out_state,
//     $check_out_pincode,
//     $employee_id,
//     $check_in_date
// );


//             if($stmt_check_out->execute())
//             {
               

//                //emp hours
//                 $stmt_hours = $conn->prepare("
//                     SELECT emp_hours 
//                     FROM client_other_info 
//                     WHERE client_id = ? 
//                       AND rate_id = ? 
//                       AND LOWER(emp_type) = LOWER(?) 
//                       AND active = '0'
//                 ");
//                 $stmt_hours->bind_param("sss", $client_id, $site_id, $rank);
//                 $stmt_hours->execute();
//                 $stmt_hours->bind_result($emp_hours);
//                 $stmt_hours->fetch();
//                 $stmt_hours->close();

//                 if(empty($emp_hours)){
//                     $emp_hours = 8;  // default
//                 }

      
//                 // times checkin checkout
//                 $stmt_time = $conn->prepare("
//                     SELECT check_in_time, check_out_time
//                     FROM emp_attendance
//                     WHERE employee_id=? AND check_in_date=?
//                 ");
//                 $stmt_time->bind_param("ss", $employee_id, $check_in_date);
//                 $stmt_time->execute();
//                 $stmt_time->bind_result($cin, $cout);
//                 $stmt_time->fetch();
//                 $stmt_time->close();

//                 // TIME CALCULATION
//                 $in  = strtotime(trim($cin));
//                 $out = strtotime(trim($cout));

//                 if($out < $in){
//                     $out = strtotime("+1 day", $out);
//                 }

//                 $worked_hours = round(($out - $in) / 3600, 2);

//                //status part
//                 if($worked_hours >= $emp_hours){
//                     $status = "P";
//                 }
//                 else if($worked_hours >= ($emp_hours / 2)){
//                     $status = "HF";
//                 }
//                 else{
//                     $status = "A";
//                 }

//                 $stmt_last_id = $conn->prepare("SELECT MAX(uniqueid) FROM attendance");  
//                 $stmt_last_id->execute();
//                 $stmt_last_id->bind_result($lastid);
//                 $stmt_last_id->fetch();
//                 $stmt_last_id->close();

//                 $new_id = ($lastid !== null) ? $lastid + 1 : 1;

//                 //Check if record exists
//                 $stmt_exist = $conn->prepare("
//                     SELECT id 
//                     FROM attendance 
//                     WHERE site_id=? AND client_code=? AND emp_code=? 
//                       AND at_day=? AND at_month=? AND at_year=? 
//                       AND active='0'
//                 ");
//                 $stmt_exist->bind_param("ssssss",
//                     $site_id,
//                     $client_code,
//                     $emp_code,
//                     $dateArray['day'],
//                     $dateArray['month'],
//                     $dateArray['year']
//                 );
//                 $stmt_exist->execute();
//                 $stmt_exist->store_result();

//                 if ($stmt_exist->num_rows > 0)
//                 {
//                     $stmt_exist->close();

//                     // --- UPDATE ---
//                     $stmt_add = $conn->prepare("
//                         UPDATE attendance 
//                         SET status=?, modified_by=?, modified_on=?
//                         WHERE site_id=? AND client_code=? AND emp_code=? 
//                           AND at_day=? AND at_month=? AND at_year=? AND active='0'
//                     ");

//                     $stmt_add->bind_param(
//                         "sssssssss",
//                         $status,
//                         $employee_id,
//                         $created_on,
//                         $site_id,
//                         $client_code,
//                         $emp_code,
//                         $dateArray['day'],
//                         $dateArray['month'],
//                         $dateArray['year']
//                     );
//                 }
//                 else
//                 {
//                     $stmt_exist->close();

//                     // --- INSERT ---
//                     // $new_id++;

// $stmt_get_rank = $conn->prepare("SELECT rank FROM employee WHERE employee_code = ? AND active = '0'");
// $stmt_get_rank->bind_param("s", $emp_code);
// $stmt_get_rank->execute();
// $stmt_get_rank->bind_result($rank);
// $stmt_get_rank->fetch();
// $stmt_get_rank->close();


//                     $stmt_add = $conn->prepare("
//                         INSERT INTO attendance 
//                         (site_id, client_code, emp_name, rank, emp_code, at_day, at_month, at_year, status, att_type, uniqueid, created_by, created_on) 
//                         VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
//                     ");

//                     $stmt_add->bind_param(
//                         "sssssssssssss",
//                         $site_id,
//                         $client_code,
//                         $emp_name, $rank,
//                         $emp_code,
//                         $dateArray['day'],
//                         $dateArray['month'],
//                         $dateArray['year'],
//                         $status,
//                         $att_type,
//                         $new_id,
//                         $employee_id,
//                         $created_on
//                     );
//                 }

//                 $stmt_add->execute();
//                 $stmt_add->close();

             
//                 // SUCCESS
//                 $response['error'] = false;   
//                 $response['message'] = 'Checked out successfully!!'; 

//             }  
//             else{
//                 $response['error'] = true;   
//                 $response['message'] = 'Some problem occurred!!';   
//             }     
//         }
//         else
//         {
//             $response['error'] = true;   
//             $response['message'] = 'Please first check in!!';   
//         }   
          
//           }
//           else {
//     $response['error']   = true;
//             $response['message'] = 'You are too far from the office. Please move closer to check out.';
// }        

//     }
//     else
//     {
//         $response['error'] = true;   
//         $response['message'] = 'required parameters are not available';  
//     }

// break;



// case 'checkOut':

//     if(isTheseParametersAvailable(array('employee_id'))){  
//         date_default_timezone_set('Asia/Kolkata');

//         $employee_id = $_POST['employee_id']; 
//         $client_id   = $_POST['client_id']; 
//         $site_id     = $_POST['site_id'];     // used as rate_id
//         $client_code = $_POST['client_code'];
//         $emp_name    = $_POST['emp_name'];
//         $emp_code    = $_POST['emp_code'];
//         $created_on  = date('Y-m-d H:i:s');
//         $attendance_date = date('Y-m-d');
//         $att_type    = "Both";
//         $dateArray   = date_parse_from_format('Y-m-d', $attendance_date);

//          $check_out_time = date('h:i a');
//         //$check_out_time = '06:14 pm';    
//         $check_in_date  = date('Y-m-d');

//          $rank   = $_POST['rank'];


//         $check_out_latitude  = $_POST['latitude'];
// $check_out_longitude = $_POST['longitude'];
// $check_out_address   = $_POST['address'];
// $check_out_city      = $_POST['city'];
// $check_out_state     = $_POST['state'];
// $check_out_pincode   = $_POST['pincode'];


// $radius = 100; // in meters

// // Assuming your table is 'office_locations' with 'lat' and 'lng'
// $sql = "SELECT id, office_latitude, office_longitude,
//         (6371000 * acos(
//             cos(radians(?)) *
//             cos(radians(office_latitude)) *
//             cos(radians(office_longitude) - radians(?)) +
//             sin(radians(?)) *
//             sin(radians(office_latitude))
//         )) AS distance
//         FROM users where emp_code = ?
//         HAVING distance <= ?
//         ORDER BY distance ASC
//         LIMIT 1";

// $stmt = $conn->prepare($sql);
// $stmt->bind_param("sssss", $check_out_latitude, $check_out_longitude, $check_out_latitude,$emp_code,$radius);
// $stmt->execute();
// $result = $stmt->get_result();

// if ($result->num_rows > 0) {
//         // CHECK IF CHECK-IN EXISTS
//         $stmt_check = $conn->prepare("SELECT id FROM emp_attendance WHERE employee_id = ? AND check_in_date = ?");
//         $stmt_check->bind_param("ss",$employee_id,$check_in_date);
//         $stmt_check->execute();
//         $stmt_check->store_result();

//         if($stmt_check->num_rows > 0)
//         {
//             // UPDATE CHECK-OUT TIME
//              $stmt_check_out = $conn->prepare("
//     UPDATE emp_attendance 
//     SET 
//         check_out_time = ?,
//         checkout_latitude = ?,
//         checkout_longitude = ?,
//         checkout_address = ?,
//         checkout_city = ?,
//         checkout_state = ?,
//         checkout_pincode = ?
//     WHERE employee_id = ? 
//       AND check_in_date = ? 
//       AND active = '0'
// ");

// $stmt_check_out->bind_param(
//     "sssssssss",
//     $check_out_time,
//     $check_out_latitude,
//     $check_out_longitude,
//     $check_out_address,
//     $check_out_city,
//     $check_out_state,
//     $check_out_pincode,
//     $employee_id,
//     $check_in_date
// );


//             if($stmt_check_out->execute())
//             {
               

//                //emp hours
//                 $stmt_hours = $conn->prepare("
//                     SELECT emp_hours 
//                     FROM client_other_info 
//                     WHERE client_id = ? 
//                       AND rate_id = ? 
//                       AND LOWER(emp_type) = LOWER(?) 
//                       AND active = '0'
//                 ");
//                 $stmt_hours->bind_param("sss", $client_id, $site_id, $rank);
//                 $stmt_hours->execute();
//                 $stmt_hours->bind_result($emp_hours);
//                 $stmt_hours->fetch();
//                 $stmt_hours->close();

//                 if(empty($emp_hours)){
//                     $emp_hours = 8;  // default
//                 }

      
//                 // times checkin checkout
//                 $stmt_time = $conn->prepare("
//                     SELECT check_in_time, check_out_time
//                     FROM emp_attendance
//                     WHERE employee_id=? AND check_in_date=?
//                 ");
//                 $stmt_time->bind_param("ss", $employee_id, $check_in_date);
//                 $stmt_time->execute();
//                 $stmt_time->bind_result($cin, $cout);
//                 $stmt_time->fetch();
//                 $stmt_time->close();

//                 // TIME CALCULATION
//                 $in  = strtotime(trim($cin));
//                 $out = strtotime(trim($cout));

//                 if($out < $in){
//                     $out = strtotime("+1 day", $out);
//                 }

//                 $worked_hours = round(($out - $in) / 3600, 2);

//                //status part
//                 if($worked_hours >= $emp_hours){
//                     $status = "P";
//                 }
//                 else if($worked_hours >= ($emp_hours / 2)){
//                     $status = "HF";
//                 }
//                 else{
//                     $status = "A";
//                 }

//                 $stmt_last_id = $conn->prepare("SELECT MAX(uniqueid) FROM attendance");  
//                 $stmt_last_id->execute();
//                 $stmt_last_id->bind_result($lastid);
//                 $stmt_last_id->fetch();
//                 $stmt_last_id->close();

//                 $new_id = ($lastid !== null) ? $lastid + 1 : 1;

//                 //Check if record exists
//                 $stmt_exist = $conn->prepare("
//                     SELECT id 
//                     FROM attendance 
//                     WHERE site_id=? AND client_code=? AND emp_code=? 
//                       AND at_day=? AND at_month=? AND at_year=? 
//                       AND active='0'
//                 ");
//                 $stmt_exist->bind_param("ssssss",
//                     $site_id,
//                     $client_code,
//                     $emp_code,
//                     $dateArray['day'],
//                     $dateArray['month'],
//                     $dateArray['year']
//                 );
//                 $stmt_exist->execute();
//                 $stmt_exist->store_result();

//                 if ($stmt_exist->num_rows > 0)
//                 {
//                     $stmt_exist->close();

//                     // --- UPDATE ---
//                     $stmt_add = $conn->prepare("
//                         UPDATE attendance 
//                         SET status=?, modified_by=?, modified_on=?
//                         WHERE site_id=? AND client_code=? AND emp_code=? 
//                           AND at_day=? AND at_month=? AND at_year=? AND active='0'
//                     ");

//                     $stmt_add->bind_param(
//                         "sssssssss",
//                         $status,
//                         $employee_id,
//                         $created_on,
//                         $site_id,
//                         $client_code,
//                         $emp_code,
//                         $dateArray['day'],
//                         $dateArray['month'],
//                         $dateArray['year']
//                     );
//                 }
//                 else
//                 {
//                     $stmt_exist->close();

//                     // --- INSERT ---
//                     // $new_id++;

// $stmt_get_rank = $conn->prepare("SELECT rank FROM employee WHERE employee_code = ? AND active = '0'");
// $stmt_get_rank->bind_param("s", $emp_code);
// $stmt_get_rank->execute();
// $stmt_get_rank->bind_result($rank);
// $stmt_get_rank->fetch();
// $stmt_get_rank->close();


//                     $stmt_add = $conn->prepare("
//                         INSERT INTO attendance 
//                         (site_id, client_code, emp_name, rank, emp_code, at_day, at_month, at_year, status, att_type, uniqueid, created_by, created_on) 
//                         VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
//                     ");

//                     $stmt_add->bind_param(
//                         "sssssssssssss",
//                         $site_id,
//                         $client_code,
//                         $emp_name, $rank,
//                         $emp_code,
//                         $dateArray['day'],
//                         $dateArray['month'],
//                         $dateArray['year'],
//                         $status,
//                         $att_type,
//                         $new_id,
//                         $employee_id,
//                         $created_on
//                     );
//                 }

//                 $stmt_add->execute();
//                 $stmt_add->close();

             
//                 // SUCCESS
//                 $response['error'] = false;   
//                 $response['message'] = 'Checked out successfully!!'; 

//             }  
//             else{
//                 $response['error'] = true;   
//                 $response['message'] = 'Some problem occurred!!';   
//             }     
//         }
//         else
//         {
//             $response['error'] = true;   
//             $response['message'] = 'Please first check in!!';   
//         }   
          
//           }
//           else {
//     $response['error']   = true;
//             $response['message'] = 'You are too far from the office. Please move closer to check out.';
// }        

//     }
//     else
//     {
//         $response['error'] = true;   
//         $response['message'] = 'required parameters are not available';  
//     }

// break;


// case 'checkOut':

//     if(isTheseParametersAvailable(array('employee_id'))){  
//         date_default_timezone_set('Asia/Kolkata');

//         $employee_id = $_POST['employee_id']; 
//         $client_id   = $_POST['client_id']; 
//         $site_id     = $_POST['site_id'];     // used as rate_id
//         $client_code = $_POST['client_code'];
//         $emp_name    = $_POST['emp_name'];
//         $emp_code    = $_POST['emp_code'];
//         $created_on  = date('Y-m-d H:i:s');
//         $attendance_date = date('Y-m-d');
//         $att_type    = "Both";
//         $dateArray   = date_parse_from_format('Y-m-d', $attendance_date);

//         $check_out_time = date('h:i a');
//         $check_in_date  = date('Y-m-d');

//         $rank   = $_POST['rank'];

//         $check_out_latitude  = $_POST['latitude'];
//         $check_out_longitude = $_POST['longitude'];
//         $check_out_address   = $_POST['address'];
//         $check_out_city      = $_POST['city'];
//         $check_out_state     = $_POST['state'];
//         $check_out_pincode   = $_POST['pincode'];

//         $radius = 100; // in meters
//         $can_check_out = false;

//         // Updated: Fetch all allowed office coordinates from employee_login_coordinates
//         $sql = "SELECT office_latitude, office_longitude 
//                 FROM employee_login_coordinates 
//                 WHERE emp_code = ? AND active = '0'";
//         $stmt = $conn->prepare($sql);
//         $stmt->bind_param("s", $emp_code);
//         $stmt->execute();
//         $result = $stmt->get_result();

//         if($result->num_rows > 0){
//             while($row = $result->fetch_assoc()){
//                 $office_lat = $row['office_latitude'];
//                 $office_lng = $row['office_longitude'];

//                 // Haversine formula
//                 $earth_radius = 6371000; // meters
//                 $latFrom = deg2rad($check_out_latitude);
//                 $lonFrom = deg2rad($check_out_longitude);
//                 $latTo   = deg2rad($office_lat);
//                 $lonTo   = deg2rad($office_lng);

//                 $latDelta = $latTo - $latFrom;
//                 $lonDelta = $lonTo - $lonFrom;

//                 $angle = 2 * asin(sqrt(pow(sin($latDelta / 2), 2) +
//                           cos($latFrom) * cos($latTo) * pow(sin($lonDelta / 2), 2)));

//                 $distance = $earth_radius * $angle;

//                 if($distance <= $radius){
//                     $can_check_out = true;
//                     break; // employee is within allowed location
//                 }
//             }

//             if ($can_check_out) {
//                 // CHECK IF CHECK-IN EXISTS
//                 $stmt_check = $conn->prepare("SELECT id FROM emp_attendance WHERE employee_id = ? AND site_id = ? AND client_id = ? AND check_in_date = ?");
//                 $stmt_check->bind_param("ssss",$employee_id,$site_id,$client_id,$check_in_date);
//                 $stmt_check->execute();
//                 $stmt_check->store_result();

//                 if($stmt_check->num_rows > 0)
//                 {
//                     // UPDATE CHECK-OUT TIME
//                     $stmt_check_out = $conn->prepare("
//                         UPDATE emp_attendance 
//                         SET 
//                             check_out_time = ?,
//                             checkout_latitude = ?,
//                             checkout_longitude = ?,
//                             checkout_address = ?,
//                             checkout_city = ?,
//                             checkout_state = ?,
//                             checkout_pincode = ?
//                         WHERE employee_id = ? 
//                           AND check_in_date = ? 
//                           AND site_id = ? AND client_id = ?
//                           AND active = '0'
//                     ");

//                     $stmt_check_out->bind_param(
//                         "sssssssssss",
//                         $check_out_time,
//                         $check_out_latitude,
//                         $check_out_longitude,
//                         $check_out_address,
//                         $check_out_city,
//                         $check_out_state,
//                         $check_out_pincode,
//                         $employee_id,
//                         $check_in_date,
//                         $site_id,
//                         $client_id
//                     );

//                     if($stmt_check_out->execute())
//                     {
//                         //emp hours
//                         $stmt_hours = $conn->prepare("
//                             SELECT emp_hours 
//                             FROM client_other_info 
//                             WHERE client_id = ? 
//                               AND rate_id = ? 
//                               AND LOWER(emp_type) = LOWER(?) 
//                               AND active = '0'
//                         ");
//                         $stmt_hours->bind_param("sss", $client_id, $site_id, $rank);
//                         $stmt_hours->execute();
//                         $stmt_hours->bind_result($emp_hours);
//                         $stmt_hours->fetch();
//                         $stmt_hours->close();

//                         if(empty($emp_hours)){
//                             $emp_hours = 8;  // default
//                         }

//                         // times checkin checkout
//                         $stmt_time = $conn->prepare("
//                             SELECT check_in_time, check_out_time
//                             FROM emp_attendance
//                             WHERE employee_id=? AND check_in_date=? AND site_id = ? AND client_id = ?
//                         ");
//                         $stmt_time->bind_param("ssss", $employee_id, $check_in_date,$site_id,$client_id);
//                         $stmt_time->execute();
//                         $stmt_time->bind_result($cin, $cout);
//                         $stmt_time->fetch();
//                         $stmt_time->close();

//                         // TIME CALCULATION
//                         $in  = strtotime(trim($cin));
//                         $out = strtotime(trim($cout));

//                         if($out < $in){
//                             $out = strtotime("+1 day", $out);
//                         }

//                         $worked_hours = round(($out - $in) / 3600, 2);

//                         //status part
//                         if($worked_hours >= $emp_hours){
//                             $status = "P";
//                         }
//                         else if($worked_hours >= ($emp_hours / 2)){
//                             $status = "HF";
//                         }
//                         else{
//                             $status = "A";
//                         }

//                         $stmt_last_id = $conn->prepare("SELECT MAX(uniqueid) FROM attendance");  
//                         $stmt_last_id->execute();
//                         $stmt_last_id->bind_result($lastid);
//                         $stmt_last_id->fetch();
//                         $stmt_last_id->close();

//                         $new_id = ($lastid !== null) ? $lastid + 1 : 1;

//                         //Check if record exists
//                         $stmt_exist = $conn->prepare("
//                             SELECT id 
//                             FROM attendance 
//                             WHERE site_id=? AND client_code=? AND emp_code=? 
//                               AND at_day=? AND at_month=? AND at_year=? 
//                               AND active='0'
//                         ");
//                         $stmt_exist->bind_param("ssssss",
//                             $site_id,
//                             $client_code,
//                             $emp_code,
//                             $dateArray['day'],
//                             $dateArray['month'],
//                             $dateArray['year']
//                         );
//                         $stmt_exist->execute();
//                         $stmt_exist->store_result();

//                         if ($stmt_exist->num_rows > 0)
//                         {
//                             $stmt_exist->close();

//                             // --- UPDATE ---
//                             $stmt_add = $conn->prepare("
//                                 UPDATE attendance 
//                                 SET status=?, modified_by=?, modified_on=?
//                                 WHERE site_id=? AND client_code=? AND emp_code=? 
//                                   AND at_day=? AND at_month=? AND at_year=? AND active='0'
//                             ");

//                             $stmt_add->bind_param(
//                                 "sssssssss",
//                                 $status,
//                                 $employee_id,
//                                 $created_on,
//                                 $site_id,
//                                 $client_code,
//                                 $emp_code,
//                                 $dateArray['day'],
//                                 $dateArray['month'],
//                                 $dateArray['year']
//                             );
//                         }
//                         else
//                         {
//                             $stmt_exist->close();

//                             // --- INSERT ---
//                             $stmt_get_rank = $conn->prepare("SELECT rank FROM employee WHERE employee_code = ? AND active = '0'");
//                             $stmt_get_rank->bind_param("s", $emp_code);
//                             $stmt_get_rank->execute();
//                             $stmt_get_rank->bind_result($rank);
//                             $stmt_get_rank->fetch();
//                             $stmt_get_rank->close();

//                             $stmt_add = $conn->prepare("
//                                 INSERT INTO attendance 
//                                 (site_id, client_code, emp_name, rank, emp_code, at_day, at_month, at_year, status, att_type, uniqueid, created_by, created_on) 
//                                 VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
//                             ");

//                             $stmt_add->bind_param(
//                                 "sssssssssssss",
//                                 $site_id,
//                                 $client_code,
//                                 $emp_name, $rank,
//                                 $emp_code,
//                                 $dateArray['day'],
//                                 $dateArray['month'],
//                                 $dateArray['year'],
//                                 $status,
//                                 $att_type,
//                                 $new_id,
//                                 $employee_id,
//                                 $created_on
//                             );
//                         }

//                         $stmt_add->execute();
//                         $stmt_add->close();

//                         // SUCCESS
//                         $response['error'] = false;   
//                         $response['message'] = 'Checked out successfully!!'; 

//                     }  
//                     else{
//                         $response['error'] = true;   
//                         $response['message'] = 'Some problem occurred!!';   
//                     }     
//                 }
//                 else
//                 {
//                     $response['error'] = true;   
//                     $response['message'] = 'Please first check in!!';   
//                 }   

//             } else {
//                 $response['error']   = true;
//                 $response['message'] = 'You are too far from the office. Please move closer to check out.';
//             }

//         } else {
//             //no coordinates found
//             $response['error']   = true;
//             $response['message'] = 'No login details found for this employee.';
//         }

//     }
//     else
//     {
//         $response['error'] = true;   
//         $response['message'] = 'required parameters are not available';  
//     }

// break;


case 'checkOut':

if(isTheseParametersAvailable(array('employee_id','site_id','client_id','emp_code'))){

    date_default_timezone_set('Asia/Kolkata');

    $employee_id = $_POST['employee_id']; 
    $client_id   = $_POST['client_id']; 
    $site_id     = $_POST['site_id'];
    $client_code = $_POST['client_code'];
    $emp_name    = $_POST['emp_name'];
    $emp_code    = $_POST['emp_code'];

    $created_on  = date('Y-m-d H:i:s');
    $attendance_date = date('Y-m-d');
    $check_in_date  = $attendance_date;
    $check_out_time = date('h:i a');

    $dateArray = date_parse_from_format('Y-m-d', $attendance_date);

    // location
    $lat  = $_POST['latitude'];
    $lng  = $_POST['longitude'];
    $addr = $_POST['address'];
    $city = $_POST['city'];
    $state= $_POST['state'];
    $pin  = $_POST['pincode'];

    $radius = 100;
    $can_check_out = false;

    // ================== LOCATION CHECK ==================
    $stmt = $conn->prepare("
        SELECT office_latitude, office_longitude 
        FROM employee_login_coordinates 
        WHERE emp_code=? AND active='0'
    ");
    $stmt->bind_param("s",$emp_code);
    $stmt->execute();
    $result = $stmt->get_result();

    if($result->num_rows == 0){
        $response['error'] = true;
        $response['message'] = 'No login coordinates found';
        break;
    }

    while($row = $result->fetch_assoc()){

        $earth_radius = 6371000;

        $latFrom = deg2rad($lat);
        $lonFrom = deg2rad($lng);
        $latTo   = deg2rad($row['office_latitude']);
        $lonTo   = deg2rad($row['office_longitude']);

        $latDelta = $latTo - $latFrom;
        $lonDelta = $lonTo - $lonFrom;

        $angle = 2 * asin(sqrt(
            pow(sin($latDelta/2),2) +
            cos($latFrom)*cos($latTo)*pow(sin($lonDelta/2),2)
        ));

        $distance = $earth_radius * $angle;

        if($distance <= $radius){
            $can_check_out = true;
            break;
        }
    }

    if(!$can_check_out){
        $response['error'] = true;
        $response['message'] = 'You are too far from office';
        break;
    }

    // ================== CHECK VALID CHECK-IN ==================
    $stmt_check = $conn->prepare("
        SELECT id, check_in_time, check_out_time 
        FROM emp_attendance 
        WHERE employee_id=? 
          AND site_id=? 
          AND client_id=? 
          AND check_in_date=?
          AND check_in_time != '00:00'
          AND (check_out_time='00:00' OR check_out_time IS NULL)
          AND active='0'
    ");
    $stmt_check->bind_param("ssss",$employee_id,$site_id,$client_id,$check_in_date);
    $stmt_check->execute();
    $result_check = $stmt_check->get_result();

    if($result_check->num_rows == 0){
        $response['error'] = true;
        $response['message'] = 'No valid check-in found or already checked out';
        break;
    }

    $row = $result_check->fetch_assoc();
    $check_in_time = $row['check_in_time'];

    // ================== UPDATE CHECKOUT ==================
    $stmt_update = $conn->prepare("
        UPDATE emp_attendance 
        SET check_out_time=?,
            checkout_latitude=?,
            checkout_longitude=?,
            checkout_address=?,
            checkout_city=?,
            checkout_state=?,
            checkout_pincode=?
        WHERE id=?
    ");

    $stmt_update->bind_param(
        "ssssssss",
        $check_out_time,
        $lat,
        $lng,
        $addr,
        $city,
        $state,
        $pin,
        $row['id']
    );

    if(!$stmt_update->execute()){
        $response['error'] = true;
        $response['message'] = 'Checkout failed';
        break;
    }

    // ================== GET RANK (ERP SAFE) ==================
    $stmt_rank = $conn->prepare("
        SELECT rank 
        FROM employee 
        WHERE employee_code=? AND active='0'
    ");
    $stmt_rank->bind_param("s",$emp_code);
    $stmt_rank->execute();
    $stmt_rank->bind_result($rank);
    $stmt_rank->fetch();
    $stmt_rank->close();

// echo 'rank - '.$rank;

    if(empty($rank)) $rank = "employee";

    // echo 'client id - '.$client_id;
    // echo 'site id - '.$site_id;
    // echo 'rank - '.$rank;

    // ================== GET WORK HOURS ==================
    // $stmt_hours = $conn->prepare("
    //     SELECT emp_hours 
    //     FROM client_other_info 
    //     WHERE client_id=? 
    //       AND rate_id=? 
    //       AND LOWER(emp_type)=LOWER(?) 
    //       AND active='0'
    // ");
    // $stmt_hours->bind_param("sss",$client_id,$site_id,$rank);
    // $stmt_hours->execute();
    // $stmt_hours->bind_result($emp_hours);
    // $stmt_hours->fetch();
    // $stmt_hours->close();

    // // echo 'emp hours - '.$emp_hours;

    // if(empty($emp_hours)) $emp_hours = 8;

    $emp_hours = 8;
    
    // ================== CALCULATE HOURS ==================
    $in  = strtotime($check_in_time);
    $out = strtotime($check_out_time);

// echo ' in time - '.$in;
// echo ' out time - '.$out;

    if($out < $in){
        $out = strtotime("+1 day",$out);
    }

    $worked_hours = round(($out - $in)/3600,2);

    // echo ' worked hours - '.$worked_hours;
    //  echo ' emp hours - '.$emp_hours;

    if($worked_hours >= $emp_hours){
        $status = "P";
    }elseif($worked_hours >= ($emp_hours/2)){
        $status = "HF";
    }else{
        $status = "A";
    }

  // echo ' status - '.$status;
    // ================== ERP ATTENDANCE ==================
    $stmt_exist = $conn->prepare("
        SELECT id 
        FROM attendance 
        WHERE site_id=? 
          AND client_code=? 
          AND emp_code=? 
          AND at_day=? 
          AND at_month=? 
          AND at_year=? 
          AND active='0'
    ");

    $stmt_exist->bind_param("ssssss",
        $site_id,
        $client_code,
        $emp_code,
        $dateArray['day'],
        $dateArray['month'],
        $dateArray['year']
    );

    $stmt_exist->execute();
    $stmt_exist->store_result();

    if($stmt_exist->num_rows > 0){

        // UPDATE
        $stmt_add = $conn->prepare("
            UPDATE attendance 
            SET status=?, modified_by=?, modified_on=? 
            WHERE site_id=? AND client_code=? AND emp_code=? 
              AND at_day=? AND at_month=? AND at_year=? AND active='0'
        ");

        $stmt_add->bind_param("sssssssss",
            $status,
            $employee_id,
            $created_on,
            $site_id,
            $client_code,
            $emp_code,
            $dateArray['day'],
            $dateArray['month'],
            $dateArray['year']
        );

    }else{

        // UNIQUE ID
        $res = $conn->query("SELECT MAX(uniqueid) as uid FROM attendance");
        $row_uid = $res->fetch_assoc();
        $new_id = ($row_uid['uid'] ?? 0) + 1;

        // INSERT
        $stmt_add = $conn->prepare("
            INSERT INTO attendance
            (site_id, client_code, emp_name, rank, emp_code, at_day, at_month, at_year, status, att_type, uniqueid, created_by, created_on)
            VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)
        ");

        $att_type = "Both";

        $stmt_add->bind_param("sssssssssssss",
            $site_id,
            $client_code,
            $emp_name,
            $rank,
            $emp_code,
            $dateArray['day'],
            $dateArray['month'],
            $dateArray['year'],
            $status,
            $att_type,
            $new_id,
            $employee_id,
            $created_on
        );
    }

    $stmt_add->execute();

    // ================== SUCCESS ==================
    $response['error'] = false;
    $response['message'] = 'Checked out successfully';

}
else{
    $response['error'] = true;
    $response['message'] = 'Missing parameters';
}

break;


//----------------------------------------------------------------------------   
 case 'attendanceStatus':

    if(isTheseParametersAvailable(array('employee_id'))){  
        date_default_timezone_set('Asia/Kolkata');

        $employee_id = $_POST['employee_id']; 
            
        $check_in_date = date('Y-m-d');
        $todays_date = date('d')." ".date("F", strtotime(date('Y-m-d H:i:s')));
        //echo $todays_date;
    
                      $stmt_checked_in = $conn->prepare("SELECT check_in_time from emp_attendance where employee_id = ? and DATE(check_in_date)= ?");
                     $stmt_checked_in->bind_param("ss",$employee_id,$check_in_date);
                     $stmt_checked_in->execute();
                     $stmt_checked_in->store_result();
                    
                     if($stmt_checked_in->num_rows > 0)
                     {
                      $stmt_checked_in->bind_result($checked_in_time);
                      $stmt_checked_in->fetch();  

                    $stmt_checked_out = $conn->prepare("SELECT check_out_time from emp_attendance where employee_id = ? and check_in_date = ?");
 
                     $stmt_checked_out->bind_param("ss",$employee_id,$check_in_date);
                     $stmt_checked_out->execute();
                     $stmt_checked_out->store_result();
                     $stmt_checked_out->bind_result($checked_out_time);
                     $stmt_checked_out->fetch(); 

                     $response['error'] = false;   
                     $response['checked_in_time'] = $checked_in_time;
                     $response['checked_out_time'] = $checked_out_time; 
                     $response['todays_date'] = (string)$todays_date;
                     $response['message'] = 'Attendance got successful!!';
                     }  
                     else{
                       $response['error'] = true;  
                       $response['todays_date'] = (string)$todays_date; 
                       $response['message'] = 'No attendance for today yet!!';   
                      }             
}
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 
//---------------------------------------------------------------------------- 
// case 'canWeCheckOut':

//     if(isTheseParametersAvailable(array('employee_id'))){  
//         date_default_timezone_set('Asia/Kolkata');

//         $employee_id = $_POST['employee_id']; 
//         $site_id = $_POST['site_id']; 
//         $client_id = $_POST['client_id']; 
//         $check_out_time = date('h:i a');    
//         $check_in_date = date('Y-m-d');
    
//      $stmt_check= $conn->prepare("SELECT id from emp_attendance where employee_id = ? and check_in_date = ? AND site_id = ? AND client_id = ?");
//                      $stmt_check->bind_param("ssss",$employee_id,$check_in_date,$site_id,$client_id);
//                       $stmt_check->execute();
//                       $stmt_check->store_result();
//                       if($stmt_check->num_rows>0)
//                       {
//                        $response['error'] = false;   
//                        $response['message'] = 'yes';      
//                       }   
//                       else
//                       {
//                        $response['error'] = false;   
//                        $response['message'] = 'no';   
//                       }                         
// }
//     else
//     {
//         $response['error'] = true;   
//         $response['message'] = 'required parameters are not available';  
//     }
//     break; 

    case 'canWeCheckOut':

if(isTheseParametersAvailable(array('employee_id','site_id','client_id'))){

    date_default_timezone_set('Asia/Kolkata');

    $employee_id = $_POST['employee_id']; 
    $site_id     = $_POST['site_id']; 
    $client_id   = $_POST['client_id']; 
    $check_in_date = date('Y-m-d');

   $stmt_check = $conn->prepare("
    SELECT id 
    FROM emp_attendance 
    WHERE employee_id = ? 
      AND check_in_date = ? 
      AND site_id = ? 
      AND client_id = ?
      AND check_in_time != '00:00'
      AND (check_out_time = '00:00' OR check_out_time IS NULL)
");
$stmt_check->bind_param("ssss",$employee_id,$check_in_date,$site_id,$client_id);
    $stmt_check->execute();
    $stmt_check->store_result();

    if($stmt_check->num_rows > 0){
        $response['error'] = false;   
        $response['message'] = 'yes';      
    } else {
        $response['error'] = false;   
        $response['message'] = 'no';   
    }

    $stmt_check->close();

} else {
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';  
}

break;
//----------------------------------------------------------------------------     
case 'getAttendanceHistory':  

    $employee_id = $_POST['employee_id'];  
    $from_date   = $_POST['from_date']; // format: 'YYYY-MM-DD'
    $to_date     = $_POST['to_date'];   // format: 'YYYY-MM-DD'
      $site_id = $_POST['site_id']; 
        $client_id = $_POST['client_id']; 

    // Prepare the SQL to fetch records between the two dates
    $stmt = $conn->prepare(
        "SELECT id, check_in_date, check_in_time, check_out_time,checkin_city,checkin_state,checkin_pincode,checkin_address,checkout_address,checkout_state,checkout_city,checkout_pincode 
         FROM emp_attendance 
         WHERE employee_id = ? AND site_id = ? AND client_id = ? AND active='0' AND check_in_date BETWEEN ? AND ?"
    );  
    $stmt->bind_param("sssss", $employee_id, $site_id, $client_id ,$from_date, $to_date); 
    $stmt->execute();  
    $stmt->store_result(); 

   if($stmt->num_rows > 0){  
    // Bind all columns including location fields
    $stmt->bind_result(
        $id,
        $check_in_date,
        $check_in_time,
        $check_out_time,
        $checkin_city,
        $checkin_state,
        $checkin_pincode,
        $checkin_address,
        $checkout_address,
        $checkout_state,
        $checkout_city,
        $checkout_pincode
    );  

    $attendance_data = array();

    while($stmt->fetch()){
        $temp = array(); 
        $temp['id'] = $id; 
        $temp['check_in_date'] = $check_in_date;
        $temp['check_in_time'] = $check_in_time;
        $temp['check_out_time'] = $check_out_time;

        // 🔹 Add check-in location details
        $temp['check_in_address'] = $checkin_address;
        $temp['check_in_city'] = $checkin_city;
        $temp['check_in_state'] = $checkin_state;
        $temp['check_in_pincode'] = $checkin_pincode;

        // 🔹 Add check-out location details
        $temp['check_out_address'] = $checkout_address;
        $temp['check_out_city'] = $checkout_city;
        $temp['check_out_state'] = $checkout_state;
        $temp['check_out_pincode'] = $checkout_pincode;

        array_push($attendance_data, $temp);
    }

        $response['error'] = false;   
        $response['message'] = 'Attendance history fetched successfully!';   
        $response['user'] = $attendance_data;   
    }  
    else{  
        $response['error'] = false;   
        $response['message'] = 'No attendance records found for this period.'; 
        $response['user'] = array();
    }  

break;
 
//------------------------------------------------------------------------------------------------------ 

case 'getEvents':

    // 📅 From today
    $from_date = date('Y-m-d');

    // 📅 Today + 3 days
    $to_date = date('Y-m-d', strtotime('+3 days'));

    $stmt = $conn->prepare(
        "SELECT 
            id,
            title,
            date,
            start_time,
            end_time,
            location,
            event_type,
            status
         FROM events
         WHERE active = '0'
           AND date BETWEEN ? AND ?
         ORDER BY date ASC, start_time ASC"
    );

    $stmt->bind_param("ss", $from_date, $to_date);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows > 0) {

        $stmt->bind_result(
            $id,
            $title,
            $date,
            $start_time,
            $end_time,
            $location,
            $event_type,
            $status
        );

        $events = array();

        while ($stmt->fetch()) {
            $temp = array();
            $temp['id'] = $id;
            $temp['title'] = $title;
            $temp['date'] = $date;
            $temp['start_time'] = $start_time;
            $temp['end_time'] = $end_time;
            $temp['location'] = $location;
            $temp['event_type'] = $event_type;
            $temp['status'] = $status;

            $events[] = $temp;
        }

        $response['error'] = false;
        $response['message'] = 'Upcoming events fetched successfully';
        $response['events'] = $events;

    } else {

        $response['error'] = false;
        $response['message'] = 'No upcoming events';
        $response['events'] = array();
    }

break;

 
//------------------------------------------------------------------------------------------------------
case 'getCompanyBanks':  

$employee_id = $_POST['employee_id'];  
 
$stmt = $conn->prepare("SELECT id,check_in_date,check_in_time,check_out_time FROM attendance WHERE employee_id = ? and active='0'");  
$stmt->bind_param("s",$employee_id); 
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$check_in_date,$check_in_time,$check_out_time);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['id'] = $id; 
     $temp['check_in_date']=$check_in_date ;
     $temp['check_in_time'] = $check_in_time;
     $temp['check_out_time'] = $check_out_time;

     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Attendance history got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//------------------------------------------------------------------------------------------

case 'getAllEmployees':  

    $client_id = $_POST['client_id'];  
    $site_id = $_POST['site_id'];   
    $status = $_POST['status'];
    $keyword = trim($_POST['keyword']);
    $from_date = $_POST['from_date'];
    $to_date = $_POST['to_date'];

    $from_year = date('Y', strtotime($from_date));
    $from_month = date('n', strtotime($from_date));
    $from_day = date('j', strtotime($from_date));
    $to_year = date('Y', strtotime($to_date));
    $to_month = date('n', strtotime($to_date));
    $to_day = date('j', strtotime($to_date));

    $limit = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
    $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0; 

    if($status == "Total") {
        if(!empty($keyword)) {
            $stmt = $conn->prepare("SELECT id, first_name, last_name, gender, rank, phone1, employee_code,em_status 
                                    FROM employee 
                                    WHERE client_id=? AND site_id=? AND active='0' 
                                    AND (first_name LIKE ? OR last_name LIKE ?)
                                    LIMIT ?, ?");
            $keyword_param = "%$keyword%";
            $stmt->bind_param("ssssii", $client_id, $site_id, $keyword_param, $keyword_param, $offset, $limit);

            $stmt_count = $conn->prepare("SELECT COUNT(id) FROM employee 
                                          WHERE client_id=? AND site_id=? AND active='0' 
                                          AND (first_name LIKE ? OR last_name LIKE ?)");
            $stmt_count->bind_param("ssss", $client_id, $site_id, $keyword_param, $keyword_param);
        } else {
            $stmt = $conn->prepare("SELECT id, first_name, last_name, gender, rank, phone1, employee_code,em_status  
                                    FROM employee 
                                    WHERE client_id=? AND site_id=? AND active='0' 
                                    LIMIT ?, ?");
            $stmt->bind_param("ssii", $client_id, $site_id, $offset, $limit);

            $stmt_count = $conn->prepare("SELECT COUNT(id) FROM employee 
                                          WHERE client_id=? AND site_id=? AND active='0'");
            $stmt_count->bind_param("ss", $client_id, $site_id);
        }
    } 


// else if($status == "Working") {
//         if(!empty($keyword)) {
//             $stmt = $conn->prepare("SELECT e.id, e.first_name, e.last_name, e.gender, e.rank, e.phone1, e.employee_code,e.em_status  
//                                     FROM employee e 
//                                     JOIN attendance a ON e.employee_code = a.emp_code 
//                                     WHERE e.site_id=? AND e.client_id=? AND e.active='0' AND e.em_status='Working' 
//                                     AND a.status='P' 
//                                     AND (a.at_day>=? AND a.at_month>=? AND a.at_year>=?) 
//                                     AND (a.at_day<=? AND a.at_month<=? AND a.at_year<=?) 
//                                     AND (e.first_name LIKE ? OR e.last_name LIKE ?) 
//                                     LIMIT ?, ?");
//             $keyword_param = "%$keyword%";
//             $stmt->bind_param("ssssssssssii", $site_id, $client_id, $from_date, $from_month, $from_year, $to_date, $to_month, $to_year, $keyword_param, $keyword_param, $offset, $limit);

//             $stmt_count = $conn->prepare("SELECT COUNT(e.id) 
//                                           FROM employee e 
//                                           JOIN attendance a ON e.employee_code = a.emp_code 
//                                           WHERE e.site_id=? AND e.client_id=? AND e.active='0' AND e.em_status='Working' 
//                                           AND a.status='P' 
//                                           AND (a.at_day>=? AND a.at_month>=? AND a.at_year>=?) 
//                                           AND (a.at_day<=? AND a.at_month<=? AND a.at_year<=?) 
//                                           AND (e.first_name LIKE ? OR e.last_name LIKE ?)");
//             $stmt_count->bind_param("ssssssssss", $site_id, $client_id, $from_date, $from_month, $from_year, $to_date, $to_month, $to_year, $keyword_param, $keyword_param);
//         } else {
//             $stmt = $conn->prepare("SELECT e.id, e.first_name, e.last_name, e.gender, e.rank, e.phone1, e.employee_code,e.em_status  
//                                     FROM employee e 
//                                     JOIN attendance a ON e.employee_code = a.emp_code 
//                                     WHERE e.site_id=? AND e.client_id=? AND e.active='0' AND e.em_status='Working' 
//                                     AND a.status='P' 
//                                     AND (a.at_day>=? AND a.at_month>=? AND a.at_year>=?) 
//                                     AND (a.at_day<=? AND a.at_month<=? AND a.at_year<=?) 
//                                     LIMIT ?, ?");
//             $stmt->bind_param("ssssssssii", $site_id, $client_id, $from_date, $from_month, $from_year, $to_date, $to_month, $to_year, $offset, $limit);

//             $stmt_count = $conn->prepare("SELECT COUNT(e.id) 
//                                           FROM employee e 
//                                           JOIN attendance a ON e.employee_code = a.emp_code 
//                                           WHERE e.site_id=? AND e.client_id=? AND e.active='0' AND e.em_status='Working' 
//                                           AND a.status='P' 
//                                           AND (a.at_day>=? AND a.at_month>=? AND a.at_year>=?) 
//                                           AND (a.at_day<=? AND a.at_month<=? AND a.at_year<=?)");
//             $stmt_count->bind_param("ssssssss", $site_id, $client_id, $from_date, $from_month, $from_year, $to_date, $to_month, $to_year);
//         }
//     }

else if ($status == "Working") {

    if (!empty($keyword)) {

        $stmt = $conn->prepare(
            "SELECT id, first_name, last_name, gender, rank,
                    phone1, employee_code, em_status
             FROM employee
             WHERE site_id = ?
               AND client_id = ?
               AND active = '0'
               AND em_status = 'Working'
               AND (first_name LIKE ? OR last_name LIKE ?)
             LIMIT ?, ?"
        );

        $keyword_param = "%$keyword%";
        $stmt->bind_param(
            "ssssss",
            $site_id,
            $client_id,
            $keyword_param,
            $keyword_param,
            $offset,
            $limit
        );

        $stmt_count = $conn->prepare(
            "SELECT COUNT(id)
             FROM employee
             WHERE site_id = ?
               AND client_id = ?
               AND active = '0'
               AND em_status = 'Working'
               AND (first_name LIKE ? OR last_name LIKE ?)"
        );

        $stmt_count->bind_param(
            "ssss",
            $site_id,
            $client_id,
            $keyword_param,
            $keyword_param
        );

    } else {

        $stmt = $conn->prepare(
            "SELECT id, first_name, last_name, gender, rank,
                    phone1, employee_code, em_status
             FROM employee
             WHERE site_id = ?
               AND client_id = ?
               AND active = '0'
               AND em_status = 'Working'
             LIMIT ?, ?"
        );

        $stmt->bind_param(
            "ssss",
            $site_id,
            $client_id,
            $offset,
            $limit
        );

        $stmt_count = $conn->prepare(
            "SELECT COUNT(id)
             FROM employee
             WHERE site_id = ?
               AND client_id = ?
               AND active = '0'
               AND em_status = 'Working'"
        );

        $stmt_count->bind_param(
            "ss",
            $site_id,
            $client_id
        );
    }
}


    
    else if($status == "Left") {
        if(!empty($keyword)) {
            $stmt = $conn->prepare("SELECT id, first_name, last_name, gender, rank, phone1, employee_code,em_status  
                                    FROM employee 
                                    WHERE site_id=? AND client_id=? AND em_status='Left' AND active='0' 
                                    AND (first_name LIKE ? OR last_name LIKE ?) 
                                    LIMIT ?, ?");
            $keyword_param = "%$keyword%";
            $stmt->bind_param("ssssii", $site_id, $client_id, $keyword_param, $keyword_param, $offset, $limit);

            $stmt_count = $conn->prepare("SELECT COUNT(id) FROM employee 
                                          WHERE site_id=? AND client_id=? AND em_status='Left' AND active='0' 
                                          AND (first_name LIKE ? OR last_name LIKE ?)");
            $stmt_count->bind_param("ssss", $site_id, $client_id, $keyword_param, $keyword_param);
        } else {
            $stmt = $conn->prepare("SELECT id, first_name, last_name, gender, rank, phone1, employee_code,em_status  
                                    FROM employee 
                                    WHERE site_id=? AND client_id=? AND em_status='Left' AND active='0' 
                                    LIMIT ?, ?");
            $stmt->bind_param("ssii", $site_id, $client_id, $offset, $limit);

            $stmt_count = $conn->prepare("SELECT COUNT(id) FROM employee 
                                          WHERE site_id=? AND client_id=? AND em_status='Left' AND active='0'");
            $stmt_count->bind_param("ss", $site_id, $client_id);
        }
    }

    // Execute
    $stmt->execute();  
    $stmt->store_result();

    $stmt_count->execute();
    $stmt_count->bind_result($total_rows);
    $stmt_count->fetch();
    $stmt_count->close(); 

    if($stmt->num_rows > 0){  
        $stmt->bind_result($id, $first_name, $last_name, $gender, $rank, $phone, $empcode, $status);  
        $banner_data = array();

        while($stmt->fetch()){
            $temp = array(); 
            $temp['id'] = $id; 
            $temp['first_name'] = $first_name;
            $temp['last_name'] = $last_name;
            $temp['gender'] = $gender;
            $temp['rank'] = $rank;
            $temp['phone'] = $phone;
            $temp['empcode'] = $empcode;
            $temp['status'] = $status;
            array_push($banner_data, $temp);
        }

        $response['error'] = false;   
        $response['message'] = 'Employee directory got successfully!!';   
        $response['user'] = $banner_data; 
        $response['total_rows'] = $total_rows; 
    } else {  
        $response['error'] = true;   
        $response['message'] = 'Employees not available!!'; 
        $response['user'] = array();
    }

    break;

// case 'getAllEmployees':  

// $client_id = $_POST['client_id'];  
// $site_id = $_POST['site_id'];   
// $status = $_POST['status'];
// $keyword = $_POST['keyword'];
// $from_date = $_POST['from_date'];
// $to_date = $_POST['to_date'];
// $from_year = date('Y', strtotime($from_date));
// $from_month = date('n', strtotime($from_date));
// $from_day = date('j', strtotime($from_date));
// $to_year = date('Y', strtotime($to_date));
// $to_month = date('n', strtotime($to_date));
// $to_day = date('j', strtotime($to_date));

//  $limit = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
//  $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0; 

//   // if($role == "Supervisor")
//   //          {
//             if($status == "Total")
//             {
//             $stmt = $conn->prepare("SELECT id,first_name,last_name,gender,rank,phone1,employee_code FROM employee where client_id=? and site_id=? and active='0' LIMIT ?, ?");
//             $stmt->bind_param("ssss",$client_id,$site_id,$offset,$limit); 


//              $stmt_count = $conn->prepare("SELECT COUNT(id) FROM employee where client_id=? and site_id=? and active='0'");
//             $stmt_count->bind_param("ss",$client_id,$site_id);    
//             }
//             else if($status == "Working")
//             {
//            $stmt = $conn->prepare("SELECT e.id,e.first_name,e.last_name,e.gender,e.rank,e.phone1,e.employee_code FROM employee e JOIN attendance a ON e.employee_code = a.emp_code WHERE e.site_id = ? AND e.client_id = ? AND e.active = '0' AND e.em_status = 'Working' AND a.status = 'P' and (a.at_day>= ? and a.at_month >= ? and a.at_year>= ?) and (a.at_day<= ? and a.at_month <= ? and a.at_year<= ?) LIMIT ?, ?");
//             $stmt->bind_param("ssssssssss",$site_id,$client_id,$from_date,$from_month,$from_year,$to_date,$to_month,$to_year,$offset,$limit);

//              $stmt_count = $conn->prepare("SELECT COUNT(e.id) FROM employee e JOIN attendance a ON e.employee_code = a.emp_code WHERE e.site_id = ? AND e.client_id = ? AND e.active = '0' AND e.em_status = 'Working' AND a.status = 'P' and (a.at_day>= ? and a.at_month >= ? and a.at_year>= ?) and (a.at_day<= ? and a.at_month <= ? and a.at_year<= ?)");
//             $stmt_count->bind_param("ssssssss",$site_id,$client_id,$from_date,$from_month,$from_year,$to_date,$to_month,$to_year);
//             } 
//             else if($status == "Left")
//             {

//              $stmt = $conn->prepare("SELECT id,first_name,last_name,gender,rank,phone1,employee_code from employee WHERE site_id = ? and client_id = ? and em_status = 'Left' and active = '0' LIMIT ?, ?");
//             $stmt->bind_param("ssss",$site_id,$client_id,$offset,$limit); 

//              $stmt_count = $conn->prepare("SELECT COUNT(id) from employee WHERE site_id = ? and client_id = ? and em_status = 'Left' and active = '0'");
//             $stmt_count->bind_param("ss",$site_id,$client_id); 
//             }     
       
    

// $stmt->execute();  
// $stmt->store_result();

// $stmt_count->execute();
// $stmt_count->bind_result($total_rows);
// $stmt_count->fetch();
// $stmt_count->close(); 

// if($stmt->num_rows > 0){  
//     $stmt->bind_result($id,$first_name,$last_name,$gender,$rank,$phone,$empcode);  

//     $banner_data = array();

//     while($stmt->fetch()){
//      $temp = array(); 
//      $temp['id'] = $id; 
//      $temp['first_name']=$first_name ;
//      $temp['last_name'] = $last_name;
//      $temp['gender'] = $gender;
//      $temp['rank'] = $rank;
//      $temp['phone'] = $phone;
//     $temp['empcode'] = $empcode;
//      array_push($banner_data, $temp);
//  }

//  $response['error'] = false;   
//  $response['message'] = 'Employee directory got successful!!';   
//  $response['user'] = $banner_data; 
//  $response['total_rows'] = $total_rows; 


// }  
// else{  
//     $response['error'] = true;   
//     $response['message'] = 'Employees not available!!'; 
//     $response['user'] = array();
// }  
// //}  
// //}  
// break;  
//------------------------------------------------------------------------------------------------------ 
   case 'getKycSummaryEmployees':  

    $client_id = $_POST['client_id'];  
    $site_id = $_POST['site_id'];   
    $type = $_POST['type'];
    $status = $_POST['status'];
    $keyword = trim($_POST['keyword']);
    $limit = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
    $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0; 

    //status is done/not done and type is name of column of particular document or like doj or dob
    if ($status == "done") {
        // type should NOT be blank/null/empty
        $type_condition = "($type IS NOT NULL AND $type != '')";
    } 
    else if ($status == "not done") {
        // type should be blank/null/empty
        $type_condition = "($type IS NULL OR $type = '')";
    } 
    else {
        // Default fallback if status is unknown
        $type_condition = "1=1";
    }

    // ✅ Prepare query
    if (!empty($keyword)) {
        $stmt = $conn->prepare("SELECT id, first_name, last_name, gender, rank, phone1, employee_code 
                                FROM employee 
                                WHERE client_id=? AND site_id=? AND active='0' AND em_status = 'Working' 
                                AND (first_name LIKE ? OR last_name LIKE ?) 
                                AND $type_condition
                                LIMIT ?, ?");
        $keyword_param = "%$keyword%";
        $stmt->bind_param("ssssii", $client_id, $site_id, $keyword_param, $keyword_param, $offset, $limit);

        $stmt_count = $conn->prepare("SELECT COUNT(id) FROM employee 
                                      WHERE client_id=? AND site_id=? AND active='0' AND em_status = 'Working'  
                                      AND (first_name LIKE ? OR last_name LIKE ?) 
                                      AND $type_condition");
        $stmt_count->bind_param("ssss", $client_id, $site_id, $keyword_param, $keyword_param);
    } 
    else {
        $stmt = $conn->prepare("SELECT id, first_name, last_name, gender, rank, phone1, employee_code 
                                FROM employee 
                                WHERE client_id=? AND site_id=? AND active='0' AND em_status = 'Working'  
                                AND $type_condition
                                LIMIT ?, ?");
        $stmt->bind_param("ssii", $client_id, $site_id, $offset, $limit);

        $stmt_count = $conn->prepare("SELECT COUNT(id) FROM employee 
                                      WHERE client_id=? AND site_id=? AND active='0' AND em_status = 'Working' 
                                      AND $type_condition");
        $stmt_count->bind_param("ss", $client_id, $site_id);
    }


    $stmt->execute();  
    $stmt->store_result();

    $stmt_count->execute();
    $stmt_count->bind_result($total_rows);
    $stmt_count->fetch();
    $stmt_count->close(); 

    if ($stmt->num_rows > 0) {  
        $stmt->bind_result($id, $first_name, $last_name, $gender, $rank, $phone, $empcode);  
        $banner_data = array();

        while ($stmt->fetch()) {
            $temp = array(); 
            $temp['id'] = $id; 
            $temp['first_name'] = $first_name;
            $temp['last_name'] = $last_name;
            $temp['gender'] = $gender;
            $temp['rank'] = $rank;
            $temp['phone'] = $phone;
            $temp['empcode'] = $empcode;
            array_push($banner_data, $temp);
        }

        $response['error'] = false;   
        $response['message'] = 'Employee directory got successfully!!';   
        $response['user'] = $banner_data; 
        $response['total_rows'] = $total_rows; 
    } 
    else {  
        $response['error'] = true;   
        $response['message'] = 'Employees not available!!'; 
        $response['user'] = array();
    }

    break;

    //-----------------------------------------------------------------------------------------------------
case 'getHolidays':  

$state = $_POST['state'];
$status = "";
$today_date = date('Y-m-d');

$stmt = $conn->prepare("SELECT hds.id,hds.holiday_name,hds.holiday_date from holidays hd inner join holiday_detail hds on hds.holiday_id=hd.id where hd.state=? and hds.active = '0'"); 

$stmt->bind_param("s",$state);
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$holiday_name,$holiday_date);  

    $banner_data = array();
    if(date($holiday_date)<$today_date)
    {
     $status = "over";
    }
    else
    {
     $status = "upcoming";
    }    

    while($stmt->fetch()){
     $temp = array(); 
     $temp['id'] = $id; 
     $temp['name']=$holiday_name ;
     $temp['date'] = $holiday_date;
     $temp['status'] = $status;

     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Holidays got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = true;   
    $response['message'] = 'No holidays available'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//------------------------------------------------------------------------------------------------------ 
case 'getLeaves':  

$user_id = $_POST['user_id'];
$status = $_POST['status'];

$stmt = $conn->prepare("SELECT id,from_date,to_date,total_days,description,status FROM leaves where user_id=? and status = ? and active='0'"); 
$stmt->bind_param("ss",$user_id,$status); 
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$from_date,$to_date,$total_days,$description,$status);  

    $banner_data = array(); 

    while($stmt->fetch()){

        $date1 = new DateTime($from_date);
        $date2 = new DateTime($to_date);
        $interval = $date1->diff($date2);

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['from_date']=date("d", strtotime(date($from_date)))." ".date("F", strtotime(date($from_date)))." ".date("Y", strtotime(date($from_date)));
     $temp['to_date'] =date("d", strtotime(date($to_date)))." ".date("F", strtotime(date($to_date)))." ".date("Y", strtotime(date($to_date)));
     $temp['total_days'] = $interval->d;
      $temp['description'] = $description; 
      $temp['status'] = $status;

     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Leaves got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = true;   
    $response['message'] = 'No leaves available!!'; 
    $response['user'] = array();
}  
//}  
//}  
break;  

//--------------------------------------------------------

// case 'getLeaveBalance':

//     if (isset($_POST['emp_code'])) {

//         $emp_code = $_POST['emp_code'];

//         $stmt = $conn->prepare("
//             SELECT 
//                 id,
//                 emp_code,
//                 year,
//                 total_leaves,
//                 used_leaves,
//                 remaining_leaves
//             FROM employee_leaves_balance
//             WHERE emp_code = ?
//               AND active = '0'
//             ORDER BY id DESC
//         ");

//         $stmt->bind_param("s", $emp_code);
//         $stmt->execute();
//         $stmt->store_result();

//         if ($stmt->num_rows > 0) {

//             $stmt->bind_result(
//                 $id,
//                 $emp_code,
//                 $year,
//                 $total_leaves,
//                 $used_leaves,
//                 $remaining_leaves
//             );

//             $leave_data = array();

//             while ($stmt->fetch()) {

//                 $temp = array();
//                 $temp['id'] = $id;
//                 $temp['emp_code'] = $emp_code;
//                 $temp['year'] = $year;
//                 $temp['total_leaves'] = $total_leaves;
//                 $temp['used_leaves'] = $used_leaves;
//                 $temp['remaining_leaves'] = $remaining_leaves;
            

//                 array_push($leave_data, $temp);
//             }

//             $response['error'] = false;
//             $response['message'] = 'Leave balance fetched successfully!';
//             $response['data'] = $leave_data;

//         }else {

//     $leave_data = array();

//     $temp = array();
//     $temp['id'] = "0";
//     $temp['emp_code'] = $emp_code;
//     $temp['year'] = date("Y");
//     $temp['total_leaves'] = "0";
//     $temp['used_leaves'] = "0";
//     $temp['remaining_leaves'] = "0";

//     array_push($leave_data, $temp);

//     $response['error'] = false;
//     $response['message'] = 'No leave balance found. Returning default values.';
//     $response['data'] = $leave_data;
// }

//     } else {

//         $response['error'] = true;
//         $response['message'] = 'emp_code parameter missing!';
//     }

// break;

// case 'getLeaveBalance':

//     if (isset($_POST['emp_code'])) {

//         $emp_code = $_POST['emp_code'];

//         // Select type-wise columns along with old totals
//         $stmt = $conn->prepare("
//             SELECT 
//                 id,
//                 emp_code,
//                 year,
//                 total_leaves,
//                 used_leaves,
//                 remaining_leaves,
//                 cl_total,
//                 cl_used,
//                 sl_total,
//                 sl_used,
//                 pl_total,
//                 pl_used,
//                 ml_total,
//                 ml_used
//             FROM employee_leaves_balance
//             WHERE emp_code = ?
//               AND active = '0'
//             ORDER BY id DESC
//         ");

//         $stmt->bind_param("s", $emp_code);
//         $stmt->execute();
//         $stmt->store_result();

//         $leave_data = array();

//         if ($stmt->num_rows > 0) {

//             $stmt->bind_result(
//                 $id,
//                 $emp_code,
//                 $year,
//                 $total_leaves,
//                 $used_leaves,
//                 $remaining_leaves,
//                 $cl_total,
//                 $cl_used,
//                 $sl_total,
//                 $sl_used,
//                 $pl_total,
//                 $pl_used,
//                 $ml_total,
//                 $ml_used
//             );

//             while ($stmt->fetch()) {

//                 $temp = array(
//                     'id' => $id,
//                     'emp_code' => $emp_code,
//                     'year' => $year,

//                     // Old totals for backward compatibility
//                     'total_leaves' => $total_leaves ?? "0",
//                     'used_leaves' => $used_leaves ?? "0",
//                     'remaining_leaves' => $remaining_leaves ?? "0",

//                     // Type-wise balances (solid-proof)
//                     'cl_total' => $cl_total ?? "0",
//                     'cl_used' => $cl_used ?? "0",
//                     'sl_total' => $sl_total ?? "0",
//                     'sl_used' => $sl_used ?? "0",
//                     'pl_total' => $pl_total ?? "0",
//                     'pl_used' => $pl_used ?? "0",
//                     'ml_total' => $ml_total ?? "0",
//                     'ml_used' => $ml_used ?? "0"
//                 );

//                 array_push($leave_data, $temp);
//             }

//             $response['error'] = false;
//             $response['message'] = 'Leave balance fetched successfully!';
//             $response['data'] = $leave_data;

//         } else {

//             // Default row if no leave balance exists
//             $temp = array(
//                 'id' => "0",
//                 'emp_code' => $emp_code,
//                 'year' => date("Y"),

//                 'total_leaves' => "0",
//                 'used_leaves' => "0",
//                 'remaining_leaves' => "0",

//                 'cl_total' => "0",
//                 'cl_used' => "0",
//                 'sl_total' => "0",
//                 'sl_used' => "0",
//                 'pl_total' => "0",
//                 'pl_used' => "0",
//                 'ml_total' => "0",
//                 'ml_used' => "0"
//             );

//             array_push($leave_data, $temp);

//             $response['error'] = false;
//             $response['message'] = 'No leave balance found. Returning default values.';
//             $response['data'] = $leave_data;
//         }

//         $stmt->close();

//     } else {

//         $response['error'] = true;
//         $response['message'] = 'emp_code parameter missing!';
//     }

// break;

case 'getLeaveBalance':

    if (isset($_POST['emp_code'])) {

        $emp_code = $_POST['emp_code'];

        // Determine current financial year
        $currentMonth = date('n'); // 1-12
        $currentYear = date('Y');

        if ($currentMonth >= 4) { // April to Dec
            $fy_from = $currentYear;
            $fy_to = $currentYear + 1;
        } else { // Jan to Mar
            $fy_from = $currentYear - 1;
            $fy_to = $currentYear;
        }

        // Select type-wise columns along with old totals for current FY
        $stmt = $conn->prepare("
            SELECT 
                id,
                emp_code,
                year,
                total_leaves,
                used_leaves,
                remaining_leaves,
                cl_total,
                cl_used,
                sl_total,
                sl_used,
                pl_total,
                pl_used,
                ml_total,
                ml_used
            FROM employee_leaves_balance
            WHERE emp_code = ?
              AND active = '0'
              AND from_year = ?
              AND to_year = ?
            ORDER BY id DESC
        ");

        $stmt->bind_param("sii", $emp_code, $fy_from, $fy_to);
        $stmt->execute();
        $stmt->store_result();

        $leave_data = array();

        if ($stmt->num_rows > 0) {

            $stmt->bind_result(
                $id,
                $emp_code,
                $year,
                $total_leaves,
                $used_leaves,
                $remaining_leaves,
                $cl_total,
                $cl_used,
                $sl_total,
                $sl_used,
                $pl_total,
                $pl_used,
                $ml_total,
                $ml_used
            );

            while ($stmt->fetch()) {

                $temp = array(
                    'id' => $id,
                    'emp_code' => $emp_code,
                    'year' => $year,

                    // Old totals for backward compatibility
                    'total_leaves' => $total_leaves ?? "0",
                    'used_leaves' => $used_leaves ?? "0",
                    'remaining_leaves' => $remaining_leaves ?? "0",

                    // Type-wise balances
                    'cl_total' => $cl_total ?? "0",
                    'cl_used' => $cl_used ?? "0",
                    'sl_total' => $sl_total ?? "0",
                    'sl_used' => $sl_used ?? "0",
                    'pl_total' => $pl_total ?? "0",
                    'pl_used' => $pl_used ?? "0",
                    'ml_total' => $ml_total ?? "0",
                    'ml_used' => $ml_used ?? "0"
                );

                array_push($leave_data, $temp);
            }

            $response['error'] = false;
            $response['message'] = 'Leave balance fetched successfully!';
            $response['data'] = $leave_data;

        } else {

            // Default row if no leave balance exists
            $temp = array(
                'id' => "0",
                'emp_code' => $emp_code,
                'year' => date("Y"),

                'total_leaves' => "0",
                'used_leaves' => "0",
                'remaining_leaves' => "0",

                'cl_total' => "0",
                'cl_used' => "0",
                'sl_total' => "0",
                'sl_used' => "0",
                'pl_total' => "0",
                'pl_used' => "0",
                'ml_total' => "0",
                'ml_used' => "0"
            );

            array_push($leave_data, $temp);

            $response['error'] = false;
            $response['message'] = 'No leave balance found for current financial year. Returning default values.';
            $response['data'] = $leave_data;
        }

        $stmt->close();

    } else {

        $response['error'] = true;
        $response['message'] = 'emp_code parameter missing!';
    }

break;

//---------------------------------------------------------------------------------------------------------------------  
// case 'addLeave':
//     if(isTheseParametersAvailable(array('user_id'))){  
//         date_default_timezone_set('Asia/Kolkata');
//         $user_id = $_POST['user_id'];
//         $emp_name = $_POST['emp_name'];
//         $emp_code = $_POST['emp_code'];
//         $from_date = $_POST['from_date'];
//         $to_date = $_POST['to_date'];
//         $description = $_POST['description'];
//         $created_on = date('Y-m-d H:i:s');

//         $date1 = new DateTime($from_date);
//         $date2 = new DateTime($to_date);
//         $interval = $date1->diff($date2);

    

//                      $stmt_add = $conn->prepare("INSERT INTO leaves (user_id,emp_name,emp_code,from_date,to_date,total_days,description,created_on) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"); 

//                      $stmt_add->bind_param("ssssssss",$user_id,$emp_name,$emp_code,$from_date,$to_date,$interval->d,$description,$created_on);
//                      if($stmt_add->execute())
//                      {

//                       $response['error'] = false;   
//                       $response['message'] = 'Leave added successfully!!'; 
//                      }  
//                       else{
//                        $response['error'] = true;   
//                        $response['message'] = 'Some problem occured!!';   
//                       }             
// }
//     else
//     {
//         $response['error'] = true;   
//         $response['message'] = 'required parameters are not available';  
//     }
//     break; 
//----------------------------------------------------------------------------  

    //--------------------------------------------------------------------------
    case 'getBirthdays':  

$stmt = $conn->prepare("
SELECT 
u.id,
e.first_name,
e.last_name,
e.dob
FROM employee e
LEFT JOIN users u ON u.emp_code = e.employee_code
WHERE DATE_ADD(
    e.dob,
    INTERVAL YEAR(CURDATE())-YEAR(e.dob) 
    + IF(DAYOFYEAR(CURDATE()) > DAYOFYEAR(e.dob),1,0) YEAR
) BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)
");

$stmt->execute();
$stmt->store_result();

if($stmt->num_rows > 0){

    $stmt->bind_result($id,$first_name,$last_name,$dob);

    $banner_data = array();

    while($stmt->fetch()){

        $temp = array();

        $temp['id'] = $id;
        $temp['first_name'] = $first_name;
        $temp['last_name'] = $last_name;

        $temp['dob'] = date("d", strtotime($dob))." ".date("F", strtotime($dob));

        array_push($banner_data, $temp);
    }

    $response['error'] = false;
    $response['message'] = 'Birthdays got successful!!';
    $response['user'] = $banner_data;

}
else{

    $response['error'] = true;
    $response['message'] = 'No birthdays available in this week!!';
    $response['user'] = array();

}

break; 
//--------------------------------------------------------------------------------------------------
 case 'getEvents':  

$stmt = $conn->prepare("SELECT id,event_name,event_description,event_venue,from_date,to_date FROM events where (DATE(from_date) BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY)) OR (DATE(to_date) BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY))");  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$desc,$venue,$from,$to);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['id'] = $id; 
     $temp['name']=$name ;
     $temp['desc'] = $desc;
     $temp['venue'] = $venue;
     $temp['from'] = date("d", strtotime(date($from)))." ".date("F", strtotime(date($from)))." ".date("Y", strtotime(date($from)));
     $temp['to'] =date("d", strtotime(date($to)))." ".date("F", strtotime(date($to)))." ".date("Y", strtotime(date($to)));
    
     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Events got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = true;   
    $response['message'] = 'No events available in this week!!'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//------------------------------------------------------------------------------------------------
case 'getExpenses':  

$user_id = $_POST['user_id'];

$stmt = $conn->prepare("SELECT amount,particular,adv_date FROM emp_advance where created_by=? and active='0'"); 
$stmt->bind_param("s",$user_id); 
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($amount,$particular,$adv_date);  

    $banner_data = array(); 

    while($stmt->fetch()){

     $temp = array(); 
     $temp['amount'] = $amount; 
     $temp['remark'] = $particular; 
     $temp['date'] = $adv_date; 
     

     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Expenses got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = true;   
    $response['message'] = 'No expenses!!'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//----------------------------------------------------------------------------------------------------- 
case 'addExpense':

    if(isTheseParametersAvailable(array('user_id'))){  
        date_default_timezone_set('Asia/Kolkata');
        $user_id = $_POST['user_id'];
        $amount = $_POST['amount'];
        $emp_code = $_POST['emp_code'];
        $adv_date = $_POST['adv_date'];
        $payment_details = 'Expense';
        $particular = $_POST['particular'];
        $voucher_no = $_POST['voucher_no'];
        $created_on = date('Y-m-d H:i:s');
    

                     $stmt_add = $conn->prepare("INSERT INTO emp_advance (emp_code,emp_id,amount,adv_date,payment_detail,particular,voucher_no,created_by,created_on) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"); 

                     $stmt_add->bind_param("sssssssss",$emp_code,$user_id,$amount,$adv_date,$payment_details,$particular,$voucher_no,$user_id,$created_on);
                     if($stmt_add->execute())
                     {

                      $response['error'] = false;   
                      $response['message'] = 'Expense added successfully!!'; 
                     }  
                      else{
                       $response['error'] = true;   
                       $response['message'] = 'Some problem occured!!';   
                      }             
}
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 
//---------------------------------------------------------------------------- 

 case 'addBirthdayWish':

    if(isTheseParametersAvailable(array('sender_id','receiver_id','message','birthday_date'))){  

        date_default_timezone_set('Asia/Kolkata');

        $sender_id      = $_POST['sender_id'];
        $receiver_id    = $_POST['receiver_id'];
        $message        = $_POST['message'];
        $birthday_date  = $_POST['birthday_date']; 

        // location details
        $user_address   = $_POST['user_address']   ?? null;
        $user_city      = $_POST['user_city']      ?? null;
        $user_state     = $_POST['user_state']     ?? null;
        $user_pincode   = $_POST['user_pincode']   ?? null;
        $user_latitude  = $_POST['user_latitude']  ?? null;
        $user_longitude = $_POST['user_longitude'] ?? null;

        $created_on = date('Y-m-d H:i:s');

        /* ---------- CHECK DUPLICATE WISH ---------- */

        $check = $conn->prepare("SELECT id FROM birthday_wishes 
                                 WHERE sender_id=? AND receiver_id=? AND birthday_date=?");

        $check->bind_param("iis",$sender_id,$receiver_id,$birthday_date);
        $check->execute();
        $check->store_result();

        if($check->num_rows > 0){

            $response['error'] = true;   
            $response['message'] = 'You already wished this user today';  

        } else {

            /* ---------- INSERT WISH ---------- */

            $stmt_add = $conn->prepare(
                "INSERT INTO birthday_wishes 
                (sender_id, receiver_id, message, birthday_date, created_by, created_on,
                sender_address, sender_city, sender_state, sender_pincode, sender_latitude, sender_longitude) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            ); 

            $stmt_add->bind_param(
                "iissssssssss",
                $sender_id,
                $receiver_id,
                $message,
                $birthday_date,
                $sender_id,
                $created_on,
                $user_address,
                $user_city,
                $user_state,
                $user_pincode,
                $user_latitude,
                $user_longitude
            );

            if($stmt_add->execute())
            {
                $response['error'] = false;   
                $response['message'] = 'Birthday wish sent successfully!!'; 
            }  
            else
            {
                $response['error'] = true;   
                $response['message'] = 'Some problem occurred!!';   
            }   
        }         
    }
    else
    {
        $response['error'] = true;   
        $response['message'] = 'Required parameters are not available';  
    }

break;

//--------------------------------------------------------------------------------------------

case 'getBirthdayNotificationCount':

if(isTheseParametersAvailable(array('user_id'))){

    $user_id = $_POST['user_id'];

    $stmt = $conn->prepare("
        SELECT COUNT(*) as total
        FROM birthday_wishes
        WHERE
        (receiver_id = ? AND seen_status = 0)
        OR
        (sender_id = ? AND reply_seen_status = 0 AND reply_message IS NOT NULL)
    ");

    $stmt->bind_param("ii",$user_id,$user_id);
    $stmt->execute();

    $result = $stmt->get_result()->fetch_assoc();

    $response['error'] = false;
    $response['count'] = $result['total'];

}
break;
//------------------------------------------------------------------------------------------
case 'getBirthdayNotifications':

if(isTheseParametersAvailable(array('user_id'))){

$user_id = $_POST['user_id'];

$stmt = $conn->prepare("

SELECT 
bw.id,
bw.message,
bw.reply_message,
bw.created_on,
bw.reply_on,
bw.seen_status,
bw.reply_seen_status,
bw.sender_id,
bw.receiver_id,

CASE
WHEN bw.receiver_id = ? THEN sender.fullname
WHEN bw.sender_id = ? THEN receiver.fullname
END AS fullname,

CASE
WHEN bw.receiver_id = ? THEN 'wish'
WHEN bw.sender_id = ? AND bw.reply_message IS NOT NULL THEN 'reply'
END AS notification_type

FROM birthday_wishes bw

LEFT JOIN users sender ON bw.sender_id = sender.id
LEFT JOIN users receiver ON bw.receiver_id = receiver.id

WHERE
(bw.receiver_id = ?)
OR
(bw.sender_id = ? AND bw.reply_message IS NOT NULL)

ORDER BY bw.created_on DESC

");

$stmt->bind_param("iiiiii",
$user_id,
$user_id,
$user_id,
$user_id,
$user_id,
$user_id
);

$stmt->execute();

$result = $stmt->get_result();
$notifications = [];

while($row = $result->fetch_assoc()){
$notifications[] = $row;
}

$response['error'] = false;
$response['notifications'] = $notifications;

}else{

$response['error'] = true;
$response['message'] = 'Required parameters missing';

}

break;
//--------------------------------------------------------------------------------------------

case 'replyBirthdayWish':

if(isTheseParametersAvailable(array('wish_id','reply_message'))){

    date_default_timezone_set('Asia/Kolkata');

    $wish_id = $_POST['wish_id'];
    $reply_message = $_POST['reply_message'];
    $reply_on = date('Y-m-d H:i:s');

    $stmt = $conn->prepare("
        UPDATE birthday_wishes
        SET reply_message = ?, 
            reply_on = ?, 
            reply_seen_status = 0
        WHERE id = ?
    ");

    $stmt->bind_param("ssi",$reply_message,$reply_on,$wish_id);

    if($stmt->execute()){
        $response['error'] = false;
        $response['message'] = "Reply saved";
    }else{
        $response['error'] = true;
        $response['message'] = "Failed";
    }

}
break;

//----------------------------------------------------------------------------------------------

case 'markBirthdayNotificationSeen':

if(isTheseParametersAvailable(array('user_id'))){

$user_id = $_POST['user_id'];

$stmt = $conn->prepare("
UPDATE birthday_wishes
SET seen_status = 1
WHERE receiver_id = ?
");

$stmt->bind_param("i",$user_id);
$stmt->execute();

$stmt2 = $conn->prepare("
UPDATE birthday_wishes
SET reply_seen_status = 1
WHERE sender_id = ?
");

$stmt2->bind_param("i",$user_id);
$stmt2->execute();

$response['error'] = false;
$response['message'] = "Notifications updated";

}else{

$response['error'] = true;

}

break;

//------------------------------------------------------------------------------- 
    case 'updateOnboardStatus': 
      if(isTheseParametersAvailable(array('user_id'))){   
      
  $user_id = $_POST['user_id']; 

            $stmt = $conn->prepare("UPDATE users SET onboarding = '1' where id = ?");  
            $stmt->bind_param("s",$user_id);  

            $stmt->execute();
            $stmt->close();  

                $response['error'] = false;   
                $response['message'] = 'Onboarding status updated successfully';   
    }  
    else{  
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';   
    }  
    break; 
//--------------------------------------------------------------------------------------------------
    case 'forgotPassword':  

$email = $_POST['email'];

$stmt = $conn->prepare("SELECT password FROM users where email=?"); 
$stmt->bind_param('s',$email); 
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($password);  
    $stmt->fetch();

 $response['error'] = false;   
 $response['message'] = 'Password sent successfully to this email id!!';   
 $response['password'] = $password;   
}  
else{  
    $response['error'] = true;   
    $response['message'] = 'User not registered with this email id!!'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//-----------------------------------------------------------------------------------------------------
case 'getJobs':   

$created_by = $_POST['created_by'];

$stmt = $conn->prepare("SELECT id,title,compensation,location,experience,work_approach,employment_type,company_name,total_post,total_hired FROM jobs WHERE created_by=? and active='0'");  
$stmt->bind_param("s",$created_by); 
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$title,$compensation,$location,$experience,$work_approach,$employment_type,$company_name,$total_post,$total_hired);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['id'] = $id; 
     $temp['title']=$title ;
     $temp['compensation'] = $compensation;
     $temp['location'] = $location;
     $temp['experience']=$experience ;
     $temp['work_approach'] = $work_approach;
     $temp['employment_type'] = $employment_type;
     $temp['company_name']=$company_name ;
     $temp['total_post'] = $total_post;
     $temp['total_hired'] = $total_hired;

     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Jobs got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//-----------------------------------------------------------------------------
case 'getApplicants':   

$created_by = $_POST['created_by'];

$stmt = $conn->prepare("SELECT id,name,post_title,status,applied_date,resume FROM applicants WHERE created_by=? and active='0'");  
$stmt->bind_param("s",$created_by); 
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$name,$title,$status,$applied_date,$resume);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['id'] = $id; 
     $temp['title']=$title ;
     $temp['name'] = $name;
     $temp['status'] = $status;
     $temp['link'] = $resume;
     $temp['applied_date'] = $applied_date;

     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Applicants got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//-----------------------------------------------------------------------------
case 'createPost':

    if(isTheseParametersAvailable(array('refno'))){  
        date_default_timezone_set('Asia/Kolkata');
        $refno = $_POST['refno']; 
        $category = $_POST['category'];
        $title = $_POST['title'];
        $location = $_POST['location'];
        $emp_type = $_POST['emp_type'];
        $work_approach = $_POST['work_approach'];
        $compensation = $_POST['compensation'];
        $experience = $_POST['experience'];
        $total_posts = $_POST['total_posts'];
        $created_by = $_POST['created_by'];
        $created_on = date('Y-m-d H:i:s');
        
    

                     $stmt_check_in = $conn->prepare("INSERT INTO jobs (ref_no,title,category,location,employment_type,work_approach,compensation,experience,total_post,created_by,created_on) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); 

                     $stmt_check_in->bind_param("sssssssssss",$refno,$title,$category,$location,$emp_type,$work_approach,$compensation,$experience,$total_posts,$created_by,$created_on);
                     if($stmt_check_in->execute())
                     {

                       $stmt_new = $conn->prepare("SELECT max(id) lastid from jobs");  
                       $stmt_new->execute();
                       $stmt_new->store_result();
                       $stmt_new->bind_result($last_id); 
                       $stmt_new->fetch();

                      $response['error'] = false;   
                      $response['message'] = 'Basic details added successfully!!'; 
                      $response['lastid'] = $last_id; 
                     }  
                      else{
                       $response['error'] = true;   
                       $response['message'] = 'Some problem occured!!';   
                      }             
}
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 
//---------------------------------------------------------------------------- 
        case 'addPostDetails': 
      if(isTheseParametersAvailable(array('id'))){   
      
  $id = $_POST['id']; 
  $desc = $_POST['desc']; 
  $resp = $_POST['resp']; 
  $skill = $_POST['skill']; 

            $stmt = $conn->prepare("UPDATE jobs SET description =?,responsibilities =?,skills =? where id = ? and active='0'");  
            $stmt->bind_param("ssss",$desc,$resp,$skill,$id);  
            $stmt->execute();
            $stmt->close();  

                $response['error'] = false;   
                $response['message'] = 'Job post created successfully!!';   
    }  
    else{  
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';   
    }  
    break; 
//--------------------------------------------------------------------------------------------------
     case 'getPostDetails':

    if(isTheseParametersAvailable(array('id'))){  
      
        $id = $_POST['id'];  

 $stmt_get_data = $conn->prepare("SELECT ref_no,title,category,location,employment_type,work_approach,experience,compensation,description,responsibilities,skills,total_post FROM jobs WHERE id ='$id'");
                     $stmt_get_data->execute();
                     $stmt_get_data->store_result();

  if ($stmt_get_data->num_rows > 0) {

  $stmt_get_data->bind_result($refno,$title,$category,$location,$emptype,$workapproach,$experience,$compensation,$desc,$resp,$skill,$totalpost);
  
   $stmt_get_data->fetch();

                 $user = array(  
                'ref'=>$refno, 
                'title'=>$title,
                'cat'=>$category,
                'loc'=>$location,
                'emtype'=>$emptype,
                'woapp'=>$workapproach,
                'exp'=>$experience,
                'comp'=>$compensation,
                'desc'=>$desc,
                'resp'=>$resp,
                'skill'=>$skill, 
                'totalpost'=>$totalpost
            );  

            $response['error'] = false;   
            $response['message'] = 'Job data fetched successful!!';   
            $response['user'] = $user;   
        }
        else
        {
            $response['error'] = true;   
            $response['message'] = 'Job post not found!!';    
        }
       
}
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 
//-----------------------------------------------------------------------------------------------------------
        case 'updatePost': 
      if(isTheseParametersAvailable(array('id'))){   
      
  $ref = $_POST['ref'];
  $title = $_POST['title'];
  $cat = $_POST['cat'];
  $loc = $_POST['loc'];
  $emp = $_POST['emp'];
  $work = $_POST['work'];
  $exp = $_POST['exp'];
  $comp = $_POST['comp'];
  $desc = $_POST['desc'];
  $resp = $_POST['resp'];
  $skill = $_POST['skill'];
  $total = $_POST['total'];
  

            $stmt = $conn->prepare("UPDATE jobs SET ref_no=?,title=?,category=?,location=?,employment_type=?,work_approach=?,experience=?,compensation=?,description=?,responsibilities=?,skills=?,total_post=? where id = ?");  
            $stmt->bind_param("sssssssssssss",$ref,$title,$cat,$loc,$emp,$work,$exp,$comp,$desc,$resp,$skill,$total,$id);  

            $stmt->execute();
            $stmt->close();  

                $response['error'] = false;   
                $response['message'] = 'Job post updated successfully';   
    }  
    else{  
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';   
    }  
    break; 
//--------------------------------------------------------------------------------------------------
    case 'getAllCategories':  

$stmt = $conn->prepare("SELECT id,title FROM categories where active='0' order by title DESC"); 
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$title);  

    $banner_data = array(); 

    while($stmt->fetch()){

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['title'] = $title; 

     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Categories got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = true;   
    $response['message'] = 'No categories!!'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//-----------------------------------------------------------------------------------------------------

case 'createTravel': 

    if(isTheseParametersAvailable(array('name'))){  
        date_default_timezone_set('Asia/Kolkata');
        $name = $_POST['name']; 
        $empid = $_POST['empid'];
        $rank = $_POST['rank'];
        $purpose = $_POST['purpose'];
        $billto = $_POST['billto'];
        $travtype = $_POST['travtype'];
        $worktype = $_POST['worktype'];
        $contact_person = $_POST['contact_person'];
        $contact_number = $_POST['contact_number'];
        $description = $_POST['description'];
        $adv_required = $_POST['adv_required'];
        $created_by = $_POST['created_by'];
        $created_on = date('Y-m-d H:i:s');
        

                     $stmt_check_in = $conn->prepare("INSERT INTO travel_requests (name,emp_id,rank,travel_purpose,bill_to,travel_type,work_type,contact_person,contact_number,description,advance_required,created_by,created_on) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); 

                     $stmt_check_in->bind_param("sssssssssssss",$name,$empid,$rank,$purpose,$billto,$travtype,$worktype,$contact_person,$contact_number,$description,$adv_required,$created_by,$created_on);
                     if($stmt_check_in->execute())
                     {

                       $stmt_new = $conn->prepare("SELECT max(id) lastid from travel_requests");  
                       $stmt_new->execute();
                       $stmt_new->store_result();
                       $stmt_new->bind_result($last_id); 
                       $stmt_new->fetch();

                      $response['error'] = false;   
                      $response['message'] = 'Travel details added successfully!!'; 
                      $response['lastid'] = $last_id; 
                     }  
                      else{
                       $response['error'] = true;   
                       $response['message'] = 'Some problem occured!!';   
                      }             
}
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 
//----------------------------------------------------------------------------  
      case 'addTravelSchedule': 
      if(isTheseParametersAvailable(array('id'))){   
      
  $id = $_POST['id']; 
  $group_size = $_POST['group_size']; 
  $travel_from_date = $_POST['travel_from_date']; 
  $travel_to_date = $_POST['travel_to_date']; 
  $traveler1_name = $_POST['traveler1_name']; 
  $destination = $_POST['destination']; 
  $mode_of_transportation = $_POST['mode_of_transportation']; 


            $stmt = $conn->prepare("UPDATE travel_requests SET group_size =?,travel_from_date =?,travel_to_date =?,traveler1_name =?,destination =?,mode_of_transportation =? where id = ? and active='0'");  
            $stmt->bind_param("sssssss",$group_size,$travel_from_date,$travel_to_date,$traveler1_name,$destination,$mode_of_transportation,$id);  
            $stmt->execute();
            $stmt->close();  

                $response['error'] = false;   
                $response['message'] = 'Travel schedule added successfully!!';   
    }  
    else{  
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';   
    }  
    break; 
//--------------------------------------------------------------------------------------------------
case 'getTravelRequests':   

$created_by = $_POST['created_by']; 

$stmt = $conn->prepare("SELECT id,travel_from_date,travel_to_date,destination location FROM travel_requests WHERE created_by=? and active='0'");  
$stmt->bind_param("s",$created_by); 
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$from,$to,$destination);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['id'] = $id; 
     $temp['from']=$from ;
     $temp['to'] = $to;
     $temp['destination'] = $destination;
    
     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Travel requests got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//-----------------------------------------------------------------------------
case 'getAllMessages':  

$stmt = $conn->prepare("SELECT id,contact_no,invoice_no,total_amount FROM bill_details where sms_sent='0' and contact_no IS NOT NULL and CHAR_LENGTH(contact_no) > 9");  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$contact_no,$inv_no,$amount);  

    $banner_data = array();

    while($stmt->fetch()){

         if(strlen($contact_no) == 10)
    {
        $contact_no = "+91".$contact_no;
    }
    else if ((strlen($contact_no) > 10) && strstr($contact_no,"+91"))
   {
     $contact_no = $contact_no;
   }

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['phone']="+91".$contact_no;
     $temp['message'] = "Your order #".$inv_no."is booked in Scholar book distributors. You can track your order at https://scholarbook.in/profile";
     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Messages got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id'; 
    $response['user'] = array();
}  
//}  
//}  
break;
//---------------------------------------------------------------------- 
 case 'updateMessageStatus': 
      if(isTheseParametersAvailable(array('id'))){   
      
  $id = $_POST['id']; 

            $stmt = $conn->prepare("UPDATE bill_details SET sms_sent = '1' where id = ?");  
            $stmt->bind_param("s",$id);  

            $stmt->execute();
            $stmt->close();  

                $response['error'] = false;   
                $response['message'] = 'Message sent  status updated successfully';   
    }  
    else{  
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';   
    }  
    break; 
//--------------------------------------------------------------------------------------------------
    case 'getAllDesignations':  

$stmt = $conn->prepare("SELECT id,emp_type FROM emp_type where active='0' order by emp_type DESC"); 
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$title);  

    $banner_data = array(); 

    while($stmt->fetch()){

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['title'] = $title; 

     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Designations got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = true;   
    $response['message'] = 'No categories!!'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//-----------------------------------------------------------------------------------------------------
 case 'getAllEmployeesForSupervisor':  

$client_id = $_POST['client_id'];
$site_id = $_POST['site_id'];
//$date = $_POST['date'];
// $at_year = date('Y', strtotime($date));
// $at_month = date('n', strtotime($date));
// $at_day = date('j', strtotime($date));

$stmt = $conn->prepare("SELECT id,first_name,last_name,rank,employee_code,phone1,gender FROM employee where client_id =? and site_id = ? and active = '0' and (em_status = 'Working' OR em_status IS NULL OR em_status = '') group by employee_code order by first_name DESC");

$stmt->bind_param("ss",$client_id,$site_id);
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$fname,$lname,$rank,$emp_code,$phone,$gender);  

    $banner_data = array(); 

    while($stmt->fetch()){

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['name'] = $fname." ".$lname; 
     $temp['rank'] = $rank; 
     $temp['phone'] = $phone; 
     $temp['gender'] = $gender; 
     $temp['first_name'] = $fname; 
     $temp['last_name'] = $lname; 
     $temp['emp_code'] = $emp_code; 
     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Employees got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = true;   
    $response['message'] = 'No employees available!!'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//-----------------------------------------------------------------------------------------------------
case 'addSupervisorAttendance':

    if (isTheseParametersAvailable(array('site_id'))) {  
        date_default_timezone_set('Asia/Kolkata');

        $employees = json_decode($_POST['employees'], true);
        $status = $_POST['status'];
        $client_code = $_POST['client_code'];
        $site_id = $_POST['site_id'];
        $created_by = $_POST['created_by'];
        $created_on = date('Y-m-d H:i:s');
        $attendance_date = $_POST['attendance_date'];
        $att_type = "Both";
        $dateArray = date_parse_from_format('Y-m-d', $attendance_date);

        // Start transaction
        $conn->begin_transaction();
        try {
            // Get max uniqueid once
            $stmt_last_id = $conn->prepare("SELECT MAX(uniqueid) FROM attendance");  
            $stmt_last_id->execute();
            $stmt_last_id->bind_result($lastid);
            $stmt_last_id->fetch();
            $stmt_last_id->close();

            $new_id = ($lastid !== null) ? $lastid + 1 : 1;

            foreach ($employees as $emp) {
                $emp_name = $emp['emp_name'];
                $emp_code = $emp['emp_code'];
                $rank = $emp['rank'];

                // Check if attendance exists for this employee on this date
                $stmt = $conn->prepare("SELECT id FROM attendance WHERE site_id=? AND client_code=? AND emp_code=? AND at_day=? AND at_month=? AND at_year=? AND active='0'");
                $stmt->bind_param("ssssss", $site_id, $client_code, $emp_code, $dateArray['day'], $dateArray['month'], $dateArray['year']); 
                $stmt->execute();  
                $stmt->store_result(); 

                if ($stmt->num_rows > 0) {
                    $stmt->close();

                    $stmt_add = $conn->prepare("UPDATE attendance SET status=?, modified_by=?, modified_on=? WHERE site_id=? AND client_code=? AND emp_code=? AND at_day=? AND at_month=? AND at_year=? AND active='0'");
                    $stmt_add->bind_param(
                        "sssssssss",
                        $status,
                        $created_by,
                        $created_on,
                        $site_id,
                        $client_code,
                        $emp_code,
                        $dateArray['day'],
                        $dateArray['month'],
                        $dateArray['year']
                    );
                } else {
                    $stmt->close();

                   $new_id++;
                    
                    $stmt_add = $conn->prepare("INSERT INTO attendance (site_id, client_code, emp_name, rank, emp_code, at_day, at_month, at_year, status, att_type, uniqueid, created_by, created_on) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    $stmt_add->bind_param(
                        "sssssssssssss",
                        $site_id,
                        $client_code,
                        $emp_name,
                        $rank,
                        $emp_code,
                        $dateArray['day'],
                        $dateArray['month'],
                        $dateArray['year'],
                        $status,
                        $att_type,
                        $new_id,
                        $created_by,
                        $created_on
                    );
                }

                if (!$stmt_add->execute()) {
                    throw new Exception("Failed to insert/update attendance for employee: $emp_name");
                }

                $stmt_add->close();
            }

            // Commit transaction
            $conn->commit();

            $response['error'] = false;   
            $response['message'] = 'Attendance added successfully!!'; 

        } catch (Exception $e) {
            $conn->rollback(); // Rollback all inserts/updates on error
            $response['error'] = true;
            $response['message'] = $e->getMessage();
        }

    } else {
        $response['error'] = true;   
        $response['message'] = 'Required parameters are not available';  
    }
    break;

//-------------------------------------------------------------------------------------

    // case 'getAllSalarySlips':  

    // $client_id = $_POST['client_id'];  
    // $site_id = $_POST['site_id'];   
    // $keyword = trim($_POST['keyword']);
    // $month = $_POST['month'];
    // $year = $_POST['year'];
    // $limit = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
    // $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0; 

    //     if(!empty($keyword)) {
    //         $stmt = $conn->prepare("SELECT DISTINCT e.emp_code,e.emp_name,e.rank FROM emp_wages e JOIN attendance a ON e.emp_code = a.emp_code WHERE e.client_id = ? AND e.site_id = ? AND e.emp_name LIKE ? AND a.status = 'P' AND e.at_month = ? AND e.at_year= ? AND a.at_month = ? AND a.at_year= ? LIMIT ?, ?");

    //         $keyword_param = "%$keyword%";
    //         $stmt->bind_param("sssssssss", $client_id, $site_id, $keyword_param, $month, $year, $month, $year, $offset, $limit);

    //         $stmt_count = $conn->prepare("SELECT COUNT(DISTINCT e.emp_code) FROM emp_wages e JOIN attendance a ON e.emp_code = a.emp_code WHERE e.client_id = ? AND e.site_id = ? AND e.emp_name LIKE ? AND a.status = 'P' AND e.at_month = ? AND e.at_year= ? AND a.at_month = ? AND a.at_year= ?");
    //         $stmt_count->bind_param("sssssss",$client_id, $site_id, $keyword_param, $month, $year, $month, $year);
    //     } else {
    //         $stmt = $conn->prepare("SELECT DISTINCT e.emp_code,e.emp_name,e.rank FROM emp_wages e JOIN attendance a ON e.emp_code = a.emp_code WHERE e.client_id = ? AND e.site_id = ? AND a.status = 'P' AND e.at_month = ? AND e.at_year= ? AND a.at_month = ? AND a.at_year= ? LIMIT ?, ?");
    //         $stmt->bind_param("ssssssss", $client_id, $site_id, $month, $year, $month, $year, $offset, $limit);

    //         $stmt_count = $conn->prepare("SELECT COUNT(DISTINCT e.emp_code) FROM emp_wages e JOIN attendance a ON e.emp_code = a.emp_code WHERE e.client_id = ? AND e.site_id = ? AND a.status = 'P' AND e.at_month = ? AND e.at_year= ? AND a.at_month = ? AND a.at_year= ?");
    //         $stmt_count->bind_param("ssssss", $client_id, $site_id, $month, $year, $month, $year);
    //     }
    
    // // Execute
    // $stmt->execute();  
    // $stmt->store_result();

    // $stmt_count->execute();
    // $stmt_count->bind_result($total_rows);
    // $stmt_count->fetch();
    // $stmt_count->close(); 

    // if($stmt->num_rows > 0){  
    //     $stmt->bind_result($code,$name,$rank);  
    //     $banner_data = array();

    //     while($stmt->fetch()){
    //         $temp = array(); 
    //         $temp['code'] = $code; 
    //         $temp['name'] = $name;
    //         $temp['rank'] = $rank;
    //         array_push($banner_data, $temp);
    //     }

    //     $response['error'] = false;   
    //     $response['message'] = 'Salary slips got successfully!!';   
    //     $response['user'] = $banner_data; 
    //     $response['total_rows'] = $total_rows; 
    // } else {  
    //     $response['error'] = true;   
    //     $response['message'] = 'No salary slips not available!!'; 
    //     $response['user'] = array();
    // }

    // break;

    //for employee only 

  // case 'getAllSalarySlips':  

  //   // ----------------------------
  //   // Required POST Params
  //   // ----------------------------
  //   $client_id = $_POST['client_id'] ?? '';  
  //   $site_id   = $_POST['site_id'] ?? '';   
  //   $emp_code  = $_POST['emp_code'] ?? '';

  //   $from_month = $_POST['from_month'] ?? '';
  //   $from_year  = $_POST['from_year'] ?? '';
  //   $to_month   = $_POST['to_month'] ?? '';
  //   $to_year    = $_POST['to_year'] ?? '';

  //   $limit  = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
  //   $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0; 

  //   // ----------------------------
  //   // Validation
  //   // ----------------------------
  //   if(empty($client_id) || empty($site_id) || empty($emp_code) ||
  //      empty($from_month) || empty($from_year) || empty($to_month) || empty($to_year)) {
  //       $response['error'] = true;
  //       $response['message'] = 'Required parameters missing';
  //       $response['user'] = array();
  //       $response['total_rows'] = 0;
  //       echo json_encode($response);
  //       exit;
  //   }

  //   // ----------------------------
  //   // Convert year+month into a single number (YYYYMM)
  //   // ----------------------------
  //   $from = intval($from_year . str_pad($from_month, 2, '0', STR_PAD_LEFT)); // e.g., 202511
  //   $to   = intval($to_year   . str_pad($to_month, 2, '0', STR_PAD_LEFT));   // e.g., 202602

  //   // ----------------------------
  //   // Main Query (Employee Specific, Month Range)
  //   // ----------------------------
  //   $stmt = $conn->prepare("
  //       SELECT DISTINCT e.emp_code, e.emp_name, e.rank
  //       FROM emp_wages e
  //       INNER JOIN attendance a ON e.emp_code = a.emp_code
  //       WHERE e.client_id = ?
  //         AND e.site_id   = ?
  //         AND e.emp_code  = ?
  //         AND a.status = 'P'
  //         AND (e.at_year*100 + e.at_month BETWEEN ? AND ?)
  //         AND (a.at_year*100 + a.at_month BETWEEN ? AND ?)
  //       LIMIT ?, ?
  //   ");

  //   $stmt->bind_param(
  //       "sssssssss",
  //       $client_id,
  //       $site_id,
  //       $emp_code,
  //       $from, $to, // e.at_year*100+at_month range
  //       $from, $to, // a.at_year*100+at_month range
  //       $offset,
  //       $limit
  //   );

  //   // ----------------------------
  //   // Count Query (Month Range)
  //   // ----------------------------
  //   $stmt_count = $conn->prepare("
  //       SELECT COUNT(DISTINCT e.emp_code)
  //       FROM emp_wages e
  //       INNER JOIN attendance a ON e.emp_code = a.emp_code
  //       WHERE e.client_id = ?
  //         AND e.site_id   = ?
  //         AND e.emp_code  = ?
  //         AND a.status = 'P'
  //         AND (e.at_year*100 + e.at_month BETWEEN ? AND ?)
  //         AND (a.at_year*100 + a.at_month BETWEEN ? AND ?)
  //   ");

  //   $stmt_count->bind_param(
  //       "sssssss",
  //       $client_id,
  //       $site_id,
  //       $emp_code,
  //       $from, $to, // e.at_year*100+at_month range
  //       $from, $to  // a.at_year*100+at_month range
  //   );

  //   // ----------------------------
  //   // Execute
  //   // ----------------------------
  //   $stmt->execute();
  //   $stmt->store_result();

  //   $stmt_count->execute();
  //   $stmt_count->bind_result($total_rows);
  //   $stmt_count->fetch();
  //   $stmt_count->close();

  //   // ----------------------------
  //   // Response
  //   // ----------------------------
  //   if ($stmt->num_rows > 0) {

  //       $stmt->bind_result($code, $name, $rank);
  //       $data = array();

  //       while ($stmt->fetch()) {
  //           $data[] = array(
  //               'code' => $code,
  //               'name' => $name,
  //               'rank' => $rank
  //           );
  //       }

  //       $response['error'] = false;
  //       $response['message'] = 'Salary slip fetched successfully';
  //       $response['user'] = $data;
  //       $response['total_rows'] = $total_rows;

  //   } else {

  //       $response['error'] = true;
  //       $response['message'] = 'No salary slip available';
  //       $response['user'] = array();
  //       $response['total_rows'] = 0;
  //   }

  //   $stmt->close();
  //   break;

// case 'getAllSalarySlips':

//     // ----------------------------
//     // Required POST Params
//     // ----------------------------
//     $client_id = $_POST['client_id'] ?? '';
//     $site_id   = $_POST['site_id'] ?? '';
//     $emp_code  = $_POST['emp_code'] ?? '';

//     $from_month = $_POST['from_month'] ?? '';
//     $from_year  = $_POST['from_year'] ?? '';
//     $to_month   = $_POST['to_month'] ?? '';
//     $to_year    = $_POST['to_year'] ?? '';

//     $limit  = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
//     $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0;

//     // ----------------------------
//     // Validation
//     // ----------------------------
//     if(empty($client_id) || empty($site_id) || empty($emp_code) ||
//        empty($from_month) || empty($from_year) || empty($to_month) || empty($to_year)) {
//         $response['error'] = true;
//         $response['message'] = 'Required parameters missing';
//         $response['user'] = array();
//         $response['total_rows'] = 0;
//         echo json_encode($response);
//         exit;
//     }

//     // ----------------------------
//     // Convert month/year to first and last date
//     // ----------------------------
//     $from_date = $from_year . '-' . str_pad($from_month, 2, '0', STR_PAD_LEFT) . '-01';
//     $to_date   = date("Y-m-t", strtotime($to_year . '-' . str_pad($to_month, 2, '0', STR_PAD_LEFT) . '-01')); // last day of month

//     // ----------------------------
//     // Main Query (Employee Specific, Month Range)
//     // ----------------------------
//     $stmt = $conn->prepare("
//         SELECT DISTINCT e.emp_code, e.emp_name, e.rank, e.at_year, e.at_month
//         FROM emp_wages e
//         INNER JOIN emp_attendance a ON e.emp_code = a.emp_code
//         WHERE e.client_id = ?
//           AND e.site_id   = ?
//           AND e.emp_code  = ?
//           AND a.check_in_date BETWEEN ? AND ?
//         LIMIT ?, ?
//     ");

//     $stmt->bind_param(
//         "sssssss",
//         $client_id,
//         $site_id,
//         $emp_code,
//         $from_date,
//         $to_date,
//         $offset,
//         $limit
//     );

//     // ----------------------------
//     // Count Query
//     // ----------------------------
//     $stmt_count = $conn->prepare("
//         SELECT COUNT(DISTINCT e.emp_code)
//         FROM emp_wages e
//         INNER JOIN emp_attendance a ON e.emp_code = a.emp_code
//         WHERE e.client_id = ?
//           AND e.site_id   = ?
//           AND e.emp_code  = ?
//           AND a.check_in_date BETWEEN ? AND ?
//     ");

//     $stmt_count->bind_param(
//         "sssss",
//         $client_id,
//         $site_id,
//         $emp_code,
//         $from_date,
//         $to_date
//     );

//     // ----------------------------
//     // Execute
//     // ----------------------------
//     $stmt->execute();
//     $stmt->store_result();

//     $stmt_count->execute();
//     $stmt_count->bind_result($total_rows);
//     $stmt_count->fetch();
//     $stmt_count->close();

//     // ----------------------------
//     // Response
//     // ----------------------------
//     if ($stmt->num_rows > 0) {

//         $stmt->bind_result($code, $name, $rank, $year, $month);
//         $data = array();

//         while ($stmt->fetch()) {
//             $data[] = array(
//                 'code'  => $code,
//                 'name'  => $name,
//                 'rank'  => $rank,
//                 'year'  => $year,
//                 'month' => $month
//             );
//         }

//         $response['error'] = false;
//         $response['message'] = 'Salary slip fetched successfully';
//         $response['user'] = $data;
//         $response['total_rows'] = $total_rows;

//     } else {

//         $response['error'] = true;
//         $response['message'] = 'No salary slip available';
//         $response['user'] = array();
//         $response['total_rows'] = 0;
//     }

//     $stmt->close();
//     break;

case 'getAllSalarySlips':

    // ----------------------------
    // Required POST Params
    // ----------------------------
    $client_id = $_POST['client_code'] ?? '';
    $site_id   = $_POST['site_id'] ?? '';
    $emp_id  = $_POST['emp_id'] ?? '';

    $from_month = $_POST['from_month'] ?? '';
    $from_year  = $_POST['from_year'] ?? '';
    $to_month   = $_POST['to_month'] ?? '';
    $to_year    = $_POST['to_year'] ?? '';

    $limit  = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
    $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0;

    // ----------------------------
    // Validation
    // ----------------------------
    if(empty($site_id) || empty($emp_id) ||
       empty($from_month) || empty($from_year) || empty($to_month) || empty($to_year)) {
        $response['error'] = true;
        $response['message'] = 'Required parameters missing';
        $response['user'] = array();
        $response['total_rows'] = 0;
        echo json_encode($response);
        exit;
    }

    // ----------------------------
    // Convert month/year to first and last date
    // ----------------------------
    $from_date = $from_year . '-' . str_pad($from_month, 2, '0', STR_PAD_LEFT) . '-01';
    $to_date   = date("Y-m-t", strtotime($to_year . '-' . str_pad($to_month, 2, '0', STR_PAD_LEFT) . '-01')); // last day of month

    // ----------------------------
    // Main Query (Salary Slips + Attendance)
    // ----------------------------
    $stmt = $conn->prepare("
        SELECT DISTINCT 
            s.emp_id AS code,
            s.rank,
            s.at_year AS year,
            s.at_month AS month
        FROM salary_slip s
        INNER JOIN emp_attendance a 
            ON s.emp_id = a.employee_id
            AND YEAR(STR_TO_DATE(a.check_in_date, '%Y-%m-%d')) = s.at_year
            AND MONTH(STR_TO_DATE(a.check_in_date, '%Y-%m-%d')) = s.at_month
        WHERE s.client_code = ?
          AND s.site_id   = ?
          AND s.emp_id    = ?
          AND STR_TO_DATE(CONCAT(s.at_year, '-', s.at_month, '-01'), '%Y-%m-%d') BETWEEN ? AND ?
        ORDER BY s.at_year DESC, s.at_month DESC
        LIMIT ?, ?
    ");

    $stmt->bind_param(
        "sssssss",
        $client_id,
        $site_id,
        $emp_id,
        $from_date,
        $to_date,
        $offset,
        $limit
    );

    // ----------------------------
    // Count Query
    // ----------------------------
    $stmt_count = $conn->prepare("
        SELECT COUNT(DISTINCT s.emp_id)
        FROM salary_slip s
        INNER JOIN emp_attendance a 
            ON s.emp_id = a.employee_id
            AND YEAR(STR_TO_DATE(a.check_in_date, '%Y-%m-%d')) = s.at_year
            AND MONTH(STR_TO_DATE(a.check_in_date, '%Y-%m-%d')) = s.at_month
        WHERE s.client_code = ?
          AND s.site_id   = ?
          AND s.emp_id    = ?
          AND STR_TO_DATE(CONCAT(s.at_year, '-', s.at_month, '-01'), '%Y-%m-%d') BETWEEN ? AND ?
    ");

    $stmt_count->bind_param(
        "sssss",
        $client_id,
        $site_id,
        $emp_id,
        $from_date,
        $to_date
    );

    // ----------------------------
    // Execute Queries
    // ----------------------------
    $stmt->execute();
    $stmt->store_result();

    $stmt_count->execute();
    $stmt_count->bind_result($total_rows);
    $stmt_count->fetch();
    $stmt_count->close();

    // ----------------------------
    // Response
    // ----------------------------
    if ($stmt->num_rows > 0) {

        $stmt->bind_result($code, $rank, $year, $month);
        $data = array();

        while ($stmt->fetch()) {
            $data[] = array(
                'code'  => $code,
                'rank'  => $rank,
                'year'  => $year,
                'month' => $month
            );
        }

        $response['error'] = false;
        $response['message'] = 'Salary slip fetched successfully';
        $response['user'] = $data;
        $response['total_rows'] = $total_rows;

    } else {

        $response['error'] = true;
        $response['message'] = 'No salary slip available';
        $response['user'] = array();
        $response['total_rows'] = 0;
    }

    $stmt->close();
break;




//----------------------------------------------------------------------------  

case 'getBulkAttendance':

    $client_code = $_POST['client_code'] ?? '';
    $site_id     = $_POST['site_id'] ?? '';
    $month       = $_POST['month'];
    $year        = $_POST['year'];

    $stmt = $conn->prepare("SELECT id FROM attendance WHERE at_month = ? AND at_year = ? AND active = '0'  AND site_id = ?");

    $stmt->bind_param("sss", $month, $year, $site_id);

    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows > 0) {
        $response['error'] = false;
        $response['message'] = 'Attendance available!!';
    } else {
        $response['error'] = true;
        $response['message'] = 'Attendance records not available!!';
    }

    $stmt->close();
    break;

    //---------------------------------------------------------------------------------
    case 'addNewEmployee':

    if(isTheseParametersAvailable(array('fname'))){  
        date_default_timezone_set('Asia/Kolkata');
        $fname = $_POST['fname'];
        $mname = $_POST['mname'];
        $lname = $_POST['lname'];
         $full_name = $_POST['full_name'];
        $pincode = $_POST['pincode'];
 $father_name = $_POST['father_name'];
 $address = $_POST['address'];
  $uan = $_POST['uan'];
   $esis = $_POST['esis'];
    $passport = $_POST['passport'];
     $dob = $_POST['dob'];
      $doj = $_POST['doj'];
        $phone = $_POST['phone'];
        $gender = $_POST['gender'];
        $client_id = $_POST['client_id'];
        $client_code = $_POST['client_code'];
        $site_id = $_POST['site_id'];
        $designation = $_POST['designation'];
        //$salary = $_POST['salary'];
       // $doc_name = $_POST['doc_name'];

        $aadharCount = $_POST['aadharCount'];
        $panCount = $_POST['panCount'];
        $passportCount = $_POST['passportCount'];
        $uanCount = $_POST['uanCount'];
        $pfCount = $_POST['pfCount'];
        $esisCount = $_POST['esisCount'];

        $created_by = $_POST['created_by'];
        $created_on = date('Y-m-d H:i:s');

        $aadhar_no = $_POST['aadhar_no'];
        $pan_no = $_POST['pan_no'];
        $uan_no = $_POST['uan_no'];
        $pf_no = $_POST['pf_no'];
        $passport_no = $_POST['passport_no'];
        $esis_no = $_POST['esis_no'];


       $stmt_check_number = $conn->prepare("SELECT id FROM employee WHERE phone1 ='$phone'");
                     $stmt_check_number->execute();
                     $stmt_check_number->store_result();


                       if (!empty(trim($phone))) {
            $stmt_check_number = $conn->prepare("SELECT id FROM employee WHERE phone1 ='$phone'");
            $stmt_check_number->execute();
            $stmt_check_number->store_result();

            if ($stmt_check_number->num_rows > 0) {
                $response['error'] = true;   
                $response['message'] = 'Phone number already used!!';   
                break;
            }
        }


          $stmt_code = $conn->prepare("SELECT max(employee_code) lastid from employee");  
                         $stmt_code->execute();
                         $stmt_code->store_result(); 

                         if($stmt_code->num_rows > 0){  
                         $stmt_code->bind_result($lastid);  
                         $stmt_code->fetch();
                         $new_id = $lastid+1; 
                       }
                       else
                       {
                        $new_id = '1';
                       }
    

                     $stmt_add = $conn->prepare("INSERT INTO employee (first_name,middle_name,last_name,emp_full_name,address,pincode,gender,rank,client_id,site_id,employee_code,phone1,father_name,dob,date_of_joining,uan_date,esis_date,passport_valid_date,created_by,created_on) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?)"); 

                     $stmt_add->bind_param("ssssssssssssssssssss",$fname,$mname,$lname,$full_name,$address,$pincode,$gender,$designation,$client_id,$site_id,$new_id,$phone,$father_name,$dob,$doj,$uan,$esis,$passport,$created_by,$created_on);
                     if($stmt_add->execute())
                     {


                        //emp_id , here we are getting current record , so not +1
                         $stmt_last = $conn->prepare("SELECT max(id) lastid from employee");  
                         $stmt_last->execute();
                         $stmt_last->store_result(); 

                         if($stmt_last->num_rows > 0){  
                         $stmt_last->bind_result($lastid);  
                         $stmt_last->fetch();
                         $new_id = $lastid; 
                         }
                          


                          //execute this only if there are any images  

                         //aadhar card
                         if($aadharCount>0)
                         {
                             $stmt_update_no = $conn->prepare("UPDATE employee SET aadhar_no=? where id=?");
                             $stmt_update_no->bind_param("ss",$aadhar_no,$new_id);   
                             $stmt_update_no->execute();

                          for ($i=0; $i < $aadharCount; $i++)
                            {

                              $counter = $i + 1; 
                              $file_path = "emp_docs/" . $fname . "_" . $lname . "_aadhar" ."_".$new_id."_".$counter.".jpg";
                              $file_name = $fname . "_" . $lname . "_aadhar" ."_".$new_id."_".$counter.".jpg";
                              file_put_contents($file_path, base64_decode($_POST['aadhar'.$counter]));
   
                              $doc_name = "Aadhar Card";  
                              $stmt_aadhar = $conn->prepare("INSERT INTO emp_docs (doc_type,doc_name,emp_id,doc_remark,created_by,created_on) VALUES (?,?,?,?,?,?)");
                              $stmt_aadhar->bind_param("ssssss",$doc_name,$file_name,$new_id,$aadhar_no,$created_by,$created_on);  
                              $stmt_aadhar->execute();  
                            } 

                        }



                          //pan card
                         if($panCount>0)
                         {
                             $stmt_update_no = $conn->prepare("UPDATE employee SET pancard_no=? where id=?");
                             $stmt_update_no->bind_param("ss",$pan_no,$new_id);   
                             $stmt_update_no->execute();
                          for ($i=0; $i < $panCount; $i++)
                            {

                              $counter = $i + 1; 
                              $file_path = "emp_docs/" . $fname . "_" . $lname . "_pan" ."_".$new_id."_".$counter.".jpg";
                              $file_name = $fname . "_" . $lname . "_pan" ."_".$new_id."_".$counter.".jpg";
                              file_put_contents($file_path, base64_decode($_POST['pan'.$counter]));
   
                              $doc_name = "Pan Card"; 
                              $stmt_pan = $conn->prepare("INSERT INTO emp_docs (doc_type,doc_name,emp_id,doc_remark,created_by,created_on) VALUES (?,?,?,?,?,?)");
                              $stmt_pan->bind_param("ssssss",$doc_name,$file_name,$new_id,$pan_no,$created_by,$created_on);  
                              $stmt_pan->execute();  
                            } 

                        }


                          //aadhar card
                         if($uanCount>0)
                         {
                             $stmt_update_no = $conn->prepare("UPDATE employee SET uan_no=? where id=?");
                             $stmt_update_no->bind_param("ss",$uan_no,$new_id);   
                             $stmt_update_no->execute();
                          for ($i=0; $i < $uanCount; $i++)
                            {

                              $counter = $i + 1; 
                              $file_path = "emp_docs/" . $fname . "_" . $lname . "_uan" ."_".$new_id."_".$counter.".jpg";
                              $file_name = $fname . "_" . $lname . "_uan" ."_".$new_id."_".$counter.".jpg";
                              file_put_contents($file_path, base64_decode($_POST['uan'.$counter]));
   
                              $doc_name = "UAN Card"; 
                              $stmt_uan = $conn->prepare("INSERT INTO emp_docs (doc_type,doc_name,emp_id,doc_remark,created_by,created_on) VALUES (?,?,?,?,?,?)");
                              $stmt_uan->bind_param("ssssss",$doc_name,$file_name,$new_id,$uan_no,$created_by,$created_on);  
                              $stmt_uan->execute();  
                            } 

                        }


                          //passport card
                         if($passportCount>0)
                         {
                             $stmt_update_no = $conn->prepare("UPDATE employee SET passport_no=? where id=?");
                             $stmt_update_no->bind_param("ss",$passport_no,$new_id);   
                             $stmt_update_no->execute();
                          for ($i=0; $i < $passportCount; $i++)
                            {

                              $counter = $i + 1; 
                              $file_path = "emp_docs/" . $fname . "_" . $lname . "_passport" ."_".$new_id."_".$counter.".jpg";
                              $file_name = $fname . "_" . $lname . "_passport" ."_".$new_id."_".$counter.".jpg";
                              file_put_contents($file_path, base64_decode($_POST['passport'.$counter]));
                              $doc_name = "Passport"; 
                              $stmt_passport = $conn->prepare("INSERT INTO emp_docs (doc_type,doc_name,emp_id,doc_remark,created_by,created_on) VALUES (?,?,?,?,?,?)");
                              $stmt_passport->bind_param("ssssss",$doc_name,$file_name,$new_id,$passport_no,$created_by,$created_on);  
                              $stmt_passport->execute();  
                            } 

                        }


                          //aadhar card
                         if($pfCount>0)
                         {
                             $stmt_update_no = $conn->prepare("UPDATE employee SET pf_no=? where id=?");
                             $stmt_update_no->bind_param("ss",$pf_no,$new_id);   
                             $stmt_update_no->execute();
                          for ($i=0; $i < $pfCount; $i++)
                            {

                              $counter = $i + 1; 
                              $file_path = "emp_docs/" . $fname . "_" . $lname . "_pf" ."_".$new_id."_".$counter.".jpg";
                              $file_name = $fname . "_" . $lname . "_pf" ."_".$new_id."_".$counter.".jpg";
                              file_put_contents($file_path, base64_decode($_POST['pf'.$counter]));
   
                              $doc_name = "PF"; 
                              $stmt_pf = $conn->prepare("INSERT INTO emp_docs (doc_type,doc_name,emp_id,doc_remark,created_by,created_on) VALUES (?,?,?,?,?,?)");
                              $stmt_pf->bind_param("ssssss",$doc_name,$file_name,$new_id,$pf_no,$created_by,$created_on);  
                              $stmt_pf->execute();  
                            } 

                        }


                          //aadhar card
                         if($esisCount>0)
                         {
                             $stmt_update_no = $conn->prepare("UPDATE employee SET esis_no=? where id=?");
                             $stmt_update_no->bind_param("ss",$esis_no,$new_id);   
                             $stmt_update_no->execute();
                          for ($i=0; $i < $esisCount; $i++)
                            {

                              $counter = $i + 1; 
                              $file_path = "emp_docs/" . $fname . "_" . $lname . "_esis" ."_".$new_id."_".$counter.".jpg";
                              $file_name = $fname . "_" . $lname . "_esis" ."_".$new_id."_".$counter.".jpg";
                              file_put_contents($file_path, base64_decode($_POST['esis'.$counter]));
   
                              $doc_name = "ESIS"; 
                              $stmt_esis = $conn->prepare("INSERT INTO emp_docs (doc_type,doc_name,emp_id,doc_remark,created_by,created_on) VALUES (?,?,?,?,?,?)");
                              $stmt_esis->bind_param("ssssss",$doc_name,$file_name,$new_id,$esis_no,$created_by,$created_on);  
                              $stmt_esis->execute();  
                            } 

                        }
                      $response['error'] = false;   
                      $response['message'] = 'Employee added successfully!!'; 
                     }  
                      else{
                       $response['error'] = true;   
                       $response['message'] = 'Some problem occured!!';   
                      }  
                              
}
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 
//----------------------------------------------------------------------------  
//     case 'getAllCompanies':   

// $id = $_POST['id'];

// $stmt = $conn->prepare("SELECT DISTINCT id,company_name FROM new_client WHERE id=? and active = '0'");  
// $stmt->bind_param("s",$id); 
// $stmt->execute();  
// $stmt->store_result(); 

// if($stmt->num_rows > 0){  
//     $stmt->bind_result($id,$title);  

//     $banner_data = array();

//     while($stmt->fetch()){
//      $temp = array(); 
//      $temp['id'] = $id; 
//      $temp['title']=$title ;
//      array_push($banner_data, $temp);
//  }

//  $response['error'] = false;   
//  $response['message'] = 'Companies got successful!!';   
//  $response['user'] = $banner_data;   
// }  
// else{  
//     $response['error'] = false;   
//     $response['message'] = 'Invalid id'; 
//     $response['user'] = array();
// }  
// //}  
// //}  
// break;  

    case 'getCompanies':
    $ids = $_POST['id']; // e.g., "1,2,3"
    $idArray = explode(',', $ids); // split into array

    // Prepare placeholders for prepared statement
    $placeholders = implode(',', array_fill(0, count($idArray), '?'));
    $types = str_repeat('s', count($idArray)); // all string types

    $stmt = $conn->prepare("SELECT DISTINCT id, company_name FROM new_client WHERE id IN ($placeholders) AND active = '0'");
    
    // bind dynamically
    $stmt->bind_param($types, ...$idArray);
    $stmt->execute();
    $stmt->store_result();

    if($stmt->num_rows > 0){  
        $stmt->bind_result($id, $title);
        $banner_data = array();
        while($stmt->fetch()){
            $banner_data[] = array('id' => $id, 'title' => $title);
        }
        $response['error'] = false;
        $response['message'] = 'Companies fetched successfully';
        $response['user'] = $banner_data;
    } else {
        $response['error'] = false;
        $response['message'] = 'No valid IDs found';
        $response['user'] = array();
    }
break;

//-----------------------------------------------------------------------------
 case 'getBranches':   

$id = $_POST['id'];

$stmt = $conn->prepare("SELECT DISTINCT id,site_name,client_code FROM client_rates WHERE client_id=? and active = '0'");  
$stmt->bind_param("s",$id); 
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$title,$code);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['id'] = $id; 
     $temp['title']=$title."-".$code ;
     $temp['client_code'] = $code;
     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Branches got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//-----------------------------------------------------------------------------

case 'getAllVendors':   

$client_id = $_POST['client_id'];
$site_id = $_POST['site_id'];

$stmt = $conn->prepare("SELECT DISTINCT id,vendor_name FROM vendors WHERE active = '0'");  
//$stmt->bind_param("ss",$client_id,$site_id); 
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$title);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['id'] = $id; 
     $temp['title']=$title;
     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Branches got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//-----------------------------------------------------------------------------
  case 'getEmployeeDetails':

if(isTheseParametersAvailable(array('emp_code'))){

    $emp_code = $_POST['emp_code'];

    $stmt = $conn->prepare("SELECT initial,first_name,middle_name,last_name,father_name,gender,dob,email_id,address,city,state,pincode,country,phone1,phone2,p_address,p_city,p_state,p_pincode,p_country,p_phone1,p_phone2,contact_person,contact_mobile,contact_relation,contact_email,employee_code,aadhar_no,pancard_no,passport_no,uan_no,esis_no,pf_no,uan_date,esis_date,passport_valid_date FROM employee WHERE employee_code = ?");

    $stmt->bind_param("s", $emp_code);
    $stmt->execute();
    $stmt->store_result();

    if($stmt->num_rows > 0){

        $stmt->bind_result($initial,$fname,$mname,$lname,$father_name,$gender,$dob,$email,$address,$city,$state,$pincode,$country,$phone1,$phone2,$p_address,$p_city,$p_state,$p_pincode,$p_country,$p_phone1,$p_phone2,$contact_person,$contact_mobile,$contact_relation,$contact_email,$empcode,$aadhar_no, $pancard_no, $passport_no, $uan_no, $esis_no, $pf_no, $uan_date, $esis_date, $passport_valid_date);

        $stmt->fetch();

        $user = array(
            'initial'=>$initial,
            'first_name'=>$fname,
            'middle_name'=>$mname,
            'last_name'=>$lname,
            'father_name'=>$father_name,
            'gender'=>$gender,
            'dob'=>$dob,
            'email'=>$email,

            'address'=>$address,
            'city'=>$city,
            'state'=>$state,
            'pincode'=>$pincode,
            'country'=>$country,
            'phone1'=>$phone1,
            'phone2'=>$phone2,

            'p_address'=>$p_address,
            'p_city'=>$p_city,
            'p_state'=>$p_state,
            'p_pincode'=>$p_pincode,
            'p_country'=>$p_country,
            'p_phone1'=>$p_phone1,
            'p_phone2'=>$p_phone2,

            'contact_person'=>$contact_person,
            'contact_mobile'=>$contact_mobile,
            'contact_relation'=>$contact_relation,
            'contact_email'=>$contact_email,

            'employee_code'=>$empcode,
             'aadhar' => $aadhar_no,
    'pan' => $pancard_no,
    'passport' => $passport_no,
    'uan' => $uan_no,
    'esis' => $esis_no,
    'pf' => $pf_no,
    'uan_date' => $uan_date,
    'esis_date' => $esis_date,
    'passport_date' => $passport_valid_date
        );

        $response['error'] = false;
        $response['message'] = "Employee data fetched successfully";
        $response['user'] = $user;

    } else {
        $response['error'] = true;
        $response['message'] = "Employee not found";
    }

} else {
    $response['error'] = true;
    $response['message'] = "Required parameters missing";
}

break;
//-------------------------------------------------------------------------------------------

case 'updateEmployeeBasicDetails':

if(isTheseParametersAvailable(array('emp_code','first_name','last_name','email'))){

$emp_code = $_POST['emp_code'];
$initial = $_POST['initial'];
$first = $_POST['first_name'];
$middle = $_POST['middle_name'];
$last = $_POST['last_name'];
$father = $_POST['father_name'];
$gender = $_POST['gender'];
$dob = $_POST['dob'];
$email = $_POST['email'];

$emp_full_name = trim($initial." ".$first." ".$middle." ".$last);
$fullname = trim($first." ".$last);

$conn->begin_transaction();

try {

$stmt1 = $conn->prepare("UPDATE employee SET initial=?,first_name=?,middle_name=?,last_name=?,father_name=?,gender=?,dob=?,email_id=?,emp_full_name=? WHERE employee_code=?");
$stmt1->bind_param("ssssssssss",$initial,$first,$middle,$last,$father,$gender,$dob,$email,$emp_full_name,$emp_code);
$stmt1->execute();

$stmt2 = $conn->prepare("UPDATE users SET first_name=?,last_name=?,fullname=?,email=?,gender=? WHERE emp_code=?");
$stmt2->bind_param("ssssss",$first,$last,$fullname,$email,$gender,$emp_code);
$stmt2->execute();

$conn->commit();

$response['error']=false;
$response['message']="Basic details updated successfully";

} catch (Exception $e) {
$conn->rollback();
$response['error']=true;
$response['message']="Update failed";
}

} else {
$response['error']=true;
$response['message']="Required parameters missing";
}
break;
//-----------------------------------------------------------------------------------------

case 'updateEmployeeContactDetails':

if(isTheseParametersAvailable(array('emp_code','address','city','state','pincode','country','phone1'))){

$emp_code = $_POST['emp_code'];

$address = $_POST['address'];
$city = $_POST['city'];
$state = $_POST['state'];
$pincode = $_POST['pincode'];
$country = $_POST['country'];
$phone1 = $_POST['phone1'];
$phone2 = $_POST['phone2'];

$p_address = $_POST['p_address'];
$p_city = $_POST['p_city'];
$p_state = $_POST['p_state'];
$p_pincode = $_POST['p_pincode'];
$p_country = $_POST['p_country'];
$p_phone1 = $_POST['p_phone1'];
$p_phone2 = $_POST['p_phone2'];

$conn->begin_transaction();

try {

$stmt1 = $conn->prepare("UPDATE employee SET address=?,city=?,state=?,pincode=?,country=?,phone1=?,phone2=?,p_address=?,p_city=?,p_state=?,p_pincode=?,p_country=?,p_phone1=?,p_phone2=? WHERE employee_code=?");

$stmt1->bind_param("sssssssssssssss",$address,$city,$state,$pincode,$country,$phone1,$phone2,$p_address,$p_city,$p_state,$p_pincode,$p_country,$p_phone1,$p_phone2,$emp_code);
$stmt1->execute();

$stmt2 = $conn->prepare("UPDATE users SET address=?,city=?,pincode=?,phone=? WHERE emp_code=?");
$stmt2->bind_param("sssss",$address,$city,$pincode,$phone1,$emp_code);
$stmt2->execute();

$conn->commit();

$response['error']=false;
$response['message']="Contact details updated successfully";

} catch (Exception $e) {
$conn->rollback();
$response['error']=true;
$response['message']="Update failed";
}

} else {
$response['error']=true;
$response['message']="Required parameters missing";
}
break;

//----------------------------------------------------------------------------------------------

case 'updateEmployeeEmergencyDetails':

if(isTheseParametersAvailable(array('emp_code','contact_person','contact_mobile'))){

$emp_code = $_POST['emp_code'];
$person = $_POST['contact_person'];
$mobile = $_POST['contact_mobile'];
$relation = $_POST['contact_relation'];
$email = $_POST['contact_email'];

$stmt = $conn->prepare("UPDATE employee SET contact_person=?,contact_mobile=?,contact_relation=?,contact_email=? WHERE employee_code=?");
$stmt->bind_param("sssss",$person,$mobile,$relation,$email,$emp_code);
$stmt->execute();

$response['error']=false;
$response['message']="Emergency contact updated successfully";

} else {
$response['error']=true;
$response['message']="Required parameters missing";
}
break;

//------------------------------------------------------------------------------------------------------

case 'getEmployeeExperience':

if(isTheseParametersAvailable(array('emp_id'))){

    $emp_id = $_POST['emp_id'];

    $stmt = $conn->prepare("SELECT id,
                                   emp_company_name,
                                   emp_designation,
                                   emp_address,
                                   emp_city,
                                   emp_state,
                                   emp_country,
                                   emp_pincode,
                                   emp_joined_date,
                                   last_working_date,
                                   annual_ctc,
                                   monthly_ctc,
                                   reporting_to,
                                   reporting_to_designation,
                                   reporting_to_email,
                                   reporting_to_contact,
                                   gross_income,
                                   gross_tds,
                                   gross_pt,
                                   total_pt
                            FROM emp_experience
                            WHERE emp_id = ? AND active = 0
                            ORDER BY id ASC");

    $stmt->bind_param("i",$emp_id);
    $stmt->execute();

    $result = $stmt->get_result();

    $data = array();

    while($row = $result->fetch_assoc()){
        $data[] = $row;
    }

    $stmt->close();

    $response['error'] = false;
    $response['data'] = $data;

} else {

    $response['error'] = true;
    $response['message'] = 'Required parameters missing';

}

break;
//-------------------------------------------------------------------------------------------------------

case 'saveEmployeeExperience':

if(isset($_POST['emp_id'])){

    $id = $_POST['id'] ?? '';
    $emp_id = $_POST['emp_id'];

    $company = $_POST['emp_company_name'];
    $designation = $_POST['emp_designation'];
    $address = $_POST['emp_address'];
    $city = $_POST['emp_city'];
    $state = $_POST['emp_state'];
    $country = $_POST['emp_country'];
    $pincode = $_POST['emp_pincode'];
    $joined = $_POST['emp_joined_date'];
    $last_working = $_POST['last_working_date'];
    $annual_ctc = $_POST['annual_ctc'];
    $monthly_ctc = $_POST['monthly_ctc'];
    $reporting_to = $_POST['reporting_to'];
    $reporting_designation = $_POST['reporting_to_designation'];
    $reporting_email = $_POST['reporting_to_email'];
    $reporting_contact = $_POST['reporting_to_contact'];
    $gross_income = $_POST['gross_income'];
    $gross_tds = $_POST['gross_tds'];
    $gross_pt = $_POST['gross_pt'];
    $total_pt = $_POST['total_pt'];

    // ================= UPDATE =================
    if(!empty($id)){

        $stmt = $conn->prepare("UPDATE emp_experience SET
            emp_company_name=?,
            emp_designation=?,
            emp_address=?,
            emp_city=?,
            emp_state=?,
            emp_country=?,
            emp_pincode=?,
            emp_joined_date=?,
            last_working_date=?,
            annual_ctc=?,
            monthly_ctc=?,
            reporting_to=?,
            reporting_to_designation=?,
            reporting_to_email=?,
            reporting_to_contact=?,
            gross_income=?,
            gross_tds=?,
            gross_pt=?,
            total_pt=?,
            active='0'
            WHERE id=? AND emp_id=?");

        $stmt->bind_param(
            "sssssssssssssssssssss",
            $company,
            $designation,
            $address,
            $city,
            $state,
            $country,
            $pincode,
            $joined,
            $last_working,
            $annual_ctc,
            $monthly_ctc,
            $reporting_to,
            $reporting_designation,
            $reporting_email,
            $reporting_contact,
            $gross_income,
            $gross_tds,
            $gross_pt,
            $total_pt,
            $id,
            $emp_id
        );

        $stmt->execute();
        $stmt->close();

        $response['error'] = false;
        $response['message'] = "Experience updated successfully";
    }

    // ================= INSERT =================
    else{

        $stmt = $conn->prepare("INSERT INTO emp_experience (
            emp_id,
            emp_company_name,
            emp_designation,
            emp_address,
            emp_city,
            emp_state,
            emp_country,
            emp_pincode,
            emp_joined_date,
            last_working_date,
            annual_ctc,
            monthly_ctc,
            reporting_to,
            reporting_to_designation,
            reporting_to_email,
            reporting_to_contact,
            gross_income,
            gross_tds,
            gross_pt,
            total_pt
        ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

        $stmt->bind_param(
            "ssssssssssssssssssss",
            $emp_id,
            $company,
            $designation,
            $address,
            $city,
            $state,
            $country,
            $pincode,
            $joined,
            $last_working,
            $annual_ctc,
            $monthly_ctc,
            $reporting_to,
            $reporting_designation,
            $reporting_email,
            $reporting_contact,
            $gross_income,
            $gross_tds,
            $gross_pt,
            $total_pt
        );

        $stmt->execute();
        $stmt->close();

        $response['error'] = false;
        $response['message'] = "Experience added successfully";
    }

} else {
    $response['error'] = true;
    $response['message'] = "emp_id missing";
}

break;
//-----------------------------------------------------------------------------------------------------------
  //   case 'updateEmployeePersonalDetails': 
  //     if(isTheseParametersAvailable(array('id'))){   
      
  // $id = $_POST['id']; 
  // $fname = $_POST['fname'];
  // $mname = $_POST['mname']; 
  // $lname = $_POST['lname'];
  // $father_name = $_POST['father_name'];
  // $dob = $_POST['dob'];
  // $doj = $_POST['doj'];
  // $address = $_POST['address']; 
  // $state = $_POST['state']; 
  // $city = $_POST['city'];
  // $pincode = $_POST['pincode'];
  // $phone = $_POST['phone']; 
  // $email = $_POST['email'];
  // $gender = $_POST['gender']; 
  // $rank = $_POST['rank'];

  // $aadharCount = $_POST['aadharCount'];
  // $panCount = $_POST['panCount'];
  // $aadhar_no = $_POST['aadhar_no'];
  // $pan_no = $_POST['pan_no'];
 
  //           $stmt = $conn->prepare("UPDATE employee SET first_name =?,middle_name=?,last_name=?,address=?,state=?,city=?,pincode=?,phone1=?,email_id=?,rank=?,gender=?,father_name=?,dob=?,date_of_joining=? where id = ?");  
  //           $stmt->bind_param("sssssssssssssss",$fname,$mname,$lname,$address,$state,$city,$pincode,$phone,$email,$rank,$gender,$father_name,$dob,$doj,$id);  
  //           $stmt->execute();
  //           $stmt->close();

  //            // 1️⃣ Soft delete all existing docs for this employee
  //       $stmt_soft_delete = $conn->prepare("UPDATE emp_docs SET active=1 WHERE emp_id=?");
  //       $stmt_soft_delete->bind_param("s", $id);
  //       $stmt_soft_delete->execute();

  //       // Update employee numbers
  //       $stmt_update_numbers = $conn->prepare("UPDATE employee SET aadhar_no=?, pancard_no=? WHERE id=?");
  //       $stmt_update_numbers->bind_param("sss", $aadhar_no, $pan_no, $id);
  //       $stmt_update_numbers->execute();

  //       // 2️⃣ Function to get current active doc count (for numbering)
  //       function getCurrentDocCount($conn, $id, $doc_type){
  //           $stmt = $conn->prepare("SELECT COUNT(*) FROM emp_docs WHERE emp_id=? AND doc_type=? AND active=0");
  //           $stmt->bind_param("ss", $id, $doc_type);
  //           $stmt->execute();
  //           $stmt->bind_result($count);
  //           $stmt->fetch();
  //           $stmt->close();
  //           return $count;
  //       }

  //       // 3️⃣ Loop through all doc types
  //       $docTypes = [
  //           'Aadhar Card' => ['prefix'=>'aadhar', 'count'=>$aadharCount, 'remark'=>$aadhar_no],
  //           'Pan Card'    => ['prefix'=>'pan', 'count'=>$panCount, 'remark'=>$pan_no],
  //       ];

  //       foreach($docTypes as $doc_name => $info){
  //           $prefix = $info['prefix'];
  //           $newCount = intval($info['count']);
  //           $remark = $info['remark'];

  //           $currentCount = getCurrentDocCount($conn, $id, $doc_name);

  //           for($i=1; $i<=$newCount; $i++){
  //               $counter = $currentCount + $i;
  //               $field_name = $prefix.$i;

  //               if(isset($_POST[$field_name]) && !empty($_POST[$field_name])){
  //                   $file_name = $fname . "_" . $lname . "_".$prefix."_".$counter.".jpg";
  //                   $file_path = "emp_docs/".$file_name;
  //                   file_put_contents($file_path, base64_decode($_POST[$field_name]));

  //                   $stmt_insert = $conn->prepare("INSERT INTO emp_docs (doc_type, doc_name, emp_id, doc_remark, created_by, created_on) VALUES (?,?,?,?,?,?)");
  //                   $stmt_insert->bind_param("ssssss", $doc_name, $file_name, $id, $remark, $created_by, $created_on);
  //                   $stmt_insert->execute();
  //               }
        
  //               $response['error'] = false;   
  //               $response['message'] = 'Personal details updated successfully!!';   

  //           }}
  //   }  
  //   else{  
  //       $response['error'] = true;   
  //       $response['message'] = 'required parameters are not available';   
  //   }  
  //   break; 
//


//     case 'updateEmployeePersonalDetails': 
//     if(isTheseParametersAvailable(array('id'))){   

//         $id = $_POST['id']; 
//         $fname = $_POST['fname'];
//         $mname = $_POST['mname']; 
//         $lname = $_POST['lname'];
//         $father_name = $_POST['father_name'];
//         $full_name = $_POST['full_name'];
//         $dob = $_POST['dob'];
//        // $doj = $_POST['doj'];
//         $address = $_POST['address']; 
//         $state = $_POST['state']; 
//         $city = $_POST['city'];
//         $pincode = $_POST['pincode'];
//         $phone = $_POST['phone']; 
//         $email = $_POST['email'];
//         $gender = $_POST['gender']; 
//       //  $rank = $_POST['rank'];

//         $aadharCount = $_POST['aadharCount'];
//         $panCount = $_POST['panCount'];
//         $aadhar_no = $_POST['aadhar_no'];
//         $pan_no = $_POST['pan_no'];

//         $created_by = $_POST['created_by'];
//         $created_on = date('Y-m-d H:i:s');

//         // --- Update Employee Personal Details ---
//         $stmt = $conn->prepare("UPDATE employee 
//                                 SET first_name=?, middle_name=?, last_name=?, emp_full_name=?,address=?, state=?, city=?, pincode=?, 
//                                     phone1=?, email_id=?, gender=?, father_name=?, dob=?
//                                 WHERE id=?");  
//         $stmt->bind_param("ssssssssssssss",
//             $fname,$mname,$lname,$full_name,$address,$state,$city,$pincode,$phone,$email,
//             $gender,$father_name,$dob,$id
//         );  
//         $stmt->execute();
//         $stmt->close();


//         // --- Soft delete existing documents ---
//         $stmt_soft_delete = $conn->prepare("UPDATE emp_docs SET active = 1 WHERE emp_id = ? AND (
//         doc_name LIKE '%aadhar%' OR doc_name LIKE '%pan%')");
//         $stmt_soft_delete->bind_param("s", $id);
//         $stmt_soft_delete->execute();


//         // --- Update employee ID numbers ---
//         $stmt_update_numbers = $conn->prepare("UPDATE employee SET aadhar_no=?, pancard_no=? WHERE id=?");
//         $stmt_update_numbers->bind_param("sss", $aadhar_no, $pan_no, $id);
//         $stmt_update_numbers->execute();

//          $stmt_last = $conn->prepare("SELECT max(id) lastid from emp_docs");  
//                          $stmt_last->execute();
//                          $stmt_last->store_result(); 

//                          if($stmt_last->num_rows > 0){  
//                          $stmt_last->bind_result($lastid);  
//                          $stmt_last->fetch();
//                          $new_id = $lastid+1; 
//                          }
                          


//         // --- Get active doc count ---
//         function getCurrentDocCount($conn, $id, $doc_type){
//             $stmt = $conn->prepare("SELECT COUNT(*) FROM emp_docs WHERE emp_id=? AND doc_type=? AND active=0");
//             $stmt->bind_param("ss", $id, $doc_type);
//             $stmt->execute();
//             $stmt->bind_result($count);
//             $stmt->fetch();
//             $stmt->close();
//             return $count;
//         }


//         // --- Document Types Loop ---
//      // --- Document Types Loop (Updated to include remaining docs) ---
// $docTypes = [
//     'Aadhar Card' => ['prefix'=>'aadhar', 'count'=>$aadharCount, 'remark'=>$aadhar_no],
//     'Pan Card'    => ['prefix'=>'pan', 'count'=>$panCount, 'remark'=>$pan_no],
//     'UAN Card'    => ['prefix'=>'uan', 'count'=>$_POST['uanCount'] ?? 0, 'remark'=>$_POST['uan_no'] ?? ''],
//     'PF'          => ['prefix'=>'pf', 'count'=>$_POST['pfCount'] ?? 0, 'remark'=>$_POST['pf_no'] ?? ''],
//     'ESIS'        => ['prefix'=>'esis', 'count'=>$_POST['esisCount'] ?? 0, 'remark'=>$_POST['esis_no'] ?? ''],
//     'Passport'    => ['prefix'=>'passport', 'count'=>$_POST['passportCount'] ?? 0, 'remark'=>$_POST['passport_no'] ?? ''],
// ];

// foreach($docTypes as $doc_name => $info){
//     $prefix = $info['prefix'];
//     $newCount = intval($info['count']);
//     $remark = $info['remark'];

//     $currentCount = getCurrentDocCount($conn, $id, $doc_name);

//     for($i=1; $i <= $newCount; $i++){
//         $counter = $currentCount + $i;
//         $field_name = $prefix.$i;

//         if(isset($_POST[$field_name]) && !empty($_POST[$field_name])){
//             $file_name = $fname . "_" . $lname . "_" . $prefix ."_".$new_id."_".$counter.".jpg";
//             $file_path = "emp_docs/" . $file_name;

//             file_put_contents($file_path, base64_decode($_POST[$field_name]));

//             $stmt_insert = $conn->prepare(
//                 "INSERT INTO emp_docs (doc_type, doc_name, emp_id, doc_remark, created_by, created_on) 
//                  VALUES (?,?,?,?,?,?)"
//             );
//             $stmt_insert->bind_param("ssssss", 
//                 $doc_name, $file_name, $id, $remark, $created_by, $created_on
//             );
//             $stmt_insert->execute();
//             $stmt_insert->close();
//         }
//     }
// }

// // --- Update employee ID numbers for all docs ---
// $stmt_update_numbers = $conn->prepare(
//     "UPDATE employee SET aadhar_no=?, pancard_no=?, uan_no=?, pf_no=?, passport_no=?, esis_no=?, 
//      uan_date=?, esis_date=?, passport_valid_date=? WHERE id=?"
// );
// $stmt_update_numbers->bind_param(
//     "ssssssssss", 
//     $aadhar_no, $pan_no, $_POST['uan_no'] ?? '', $_POST['pf_no'] ?? '', 
//     $_POST['passport_no'] ?? '', $_POST['esis_no'] ?? '', 
//     $_POST['uan'] ?? '', $_POST['esis'] ?? '', $_POST['passport'] ?? '', 
//     $id
// );
// $stmt_update_numbers->execute();
// $stmt_update_numbers->close();

//         // --- FINAL SUCCESS RESPONSE (fixed position) ---
//         $response['error'] = false;   
//         $response['message'] = 'Personal details updated successfully!!';   

//     } else {  

//         $response['error'] = true;   
//         $response['message'] = 'Required parameters are not available';   

//     }  
//     break;



    case 'updateEmployeePersonalDetails':
    if(isTheseParametersAvailable(array('id'))){

        date_default_timezone_set('Asia/Kolkata');

        //this is is emp code
        $id = $_POST['id']; 

        //this is user id 
        $user_id = $_POST['user_id']; 

        $fname = $_POST['fname'];
        $mname = $_POST['mname']; 
        $lname = $_POST['lname'];
        $father_name = $_POST['father_name'];
        $full_name = trim($fname . ' ' . $mname . ' ' . $lname);
        $dob = $_POST['dob'];

        $address = $_POST['address']; 
        $state = $_POST['state']; 
        $city = $_POST['city'];
        $pincode = $_POST['pincode'];
        $phone = $_POST['phone']; 
        $email = $_POST['email'];
        $gender = $_POST['gender']; 

        $aadharCount = $_POST['aadharCount'];
        $panCount = $_POST['panCount'];
        $aadhar_no = $_POST['aadhar_no'];
        $pan_no = $_POST['pan_no'];

        $uan_no       = $_POST['uan_no'] ?? '';
$pf_no        = $_POST['pf_no'] ?? '';
$passport_no  = $_POST['passport_no'] ?? '';
$esis_no      = $_POST['esis_no'] ?? '';

$uan_date     = $_POST['uan'] ?? '';
$esis_date    = $_POST['esis'] ?? '';
$passport_date= $_POST['passport'] ?? '';

        $created_on = date('Y-m-d H:i:s');

        // --- Update Employee Table ---
        $stmt_emp = $conn->prepare("UPDATE employee 
            SET first_name=?, middle_name=?, last_name=?, emp_full_name=?,
                phone1=?, email_id=?, gender=?, father_name=?, dob=?,passport_valid_date=?,uan_date=?,esis_date=?,aadhar_no=?,pancard_no=?,esis_no=?,pf_no=?,uan_no=?,passport_no=?,modified_by=?,modified_on=?
            WHERE employee_code=?");
        $stmt_emp->bind_param(
            "sssssssssssssssssssss",
            $fname, $mname, $lname, $full_name,
            $phone, $email, $gender, $father_name, $dob,$passport_date,$uan_date,$esis_date,$aadhar_no,$pan_no,$esis_no,$pf_no,$uan_no,$passport_no,$user_id,$created_on,$id
        );
        $stmt_emp->execute();
        $stmt_emp->close();

        // --- Update Users Table ---
        $stmt_users = $conn->prepare(
            "UPDATE users SET first_name=?, last_name=?, fullname=?, email=?, phone=?, gender=?,modified_by=?,modified_on=? WHERE id=?"
        );
        $stmt_users->bind_param(
            "sssssssss",
            $fname, $lname, $full_name, $email, $phone, $gender, $user_id, $created_on ,$user_id
        );
        $stmt_users->execute();
        $stmt_users->close();

        // --- Soft delete existing documents ---
        $stmt_soft_delete = $conn->prepare(
            "UPDATE emp_docs SET active=1 WHERE emp_code=?"
        );
        $stmt_soft_delete->bind_param("s", $id);
        $stmt_soft_delete->execute();

        // --- Get last emp_docs id for new filenames ---
        $stmt_last = $conn->prepare("SELECT MAX(id) AS lastid FROM emp_docs");
        $stmt_last->execute();
        $stmt_last->store_result();
        $new_id = 1;
        if($stmt_last->num_rows > 0){
            $stmt_last->bind_result($lastid);
            $stmt_last->fetch();
            $new_id = $lastid + 1;
        }

        // --- Function to get active doc count ---
        function getCurrentDocCount($conn, $emp_id, $doc_type){
            $stmt = $conn->prepare("SELECT COUNT(*) FROM emp_docs WHERE emp_code=? AND doc_type=? AND active=0");
            $stmt->bind_param("ss", $emp_id, $doc_type);
            $stmt->execute();
            $stmt->bind_result($count);
            $stmt->fetch();
            $stmt->close();
            return $count;
        }

        // --- Document Types Loop ---
        $docTypes = [
            'Aadhar Card' => ['prefix'=>'aadhar', 'count'=>$aadharCount, 'remark'=>$aadhar_no],
            'Pan Card'    => ['prefix'=>'pan', 'count'=>$panCount, 'remark'=>$pan_no],
            'UAN Card'    => ['prefix'=>'uan', 'count'=>$_POST['uanCount'] ?? 0, 'remark'=>$_POST['uan_no'] ?? ''],
            'PF'          => ['prefix'=>'pf', 'count'=>$_POST['pfCount'] ?? 0, 'remark'=>$_POST['pf_no'] ?? ''],
            'ESIS'        => ['prefix'=>'esis', 'count'=>$_POST['esisCount'] ?? 0, 'remark'=>$_POST['esis_no'] ?? ''],
            'Passport'    => ['prefix'=>'passport', 'count'=>$_POST['passportCount'] ?? 0, 'remark'=>$_POST['passport_no'] ?? ''],
        ];

        foreach($docTypes as $doc_name => $info){
            $prefix = $info['prefix'];
            $newCount = intval($info['count']);
            $remark = $info['remark'];

            $currentCount = getCurrentDocCount($conn, $id, $doc_name);

            for($i=1; $i <= $newCount; $i++){
                $counter = $currentCount + $i;
                $field_name = $prefix.$i;

                if(isset($_POST[$field_name]) && !empty($_POST[$field_name])){
                    $file_name = $fname . "_" . $lname . "_" . $prefix ."_".$new_id."_".$counter.".jpg";
                    $file_path = "emp_docs/" . $file_name;

                    file_put_contents($file_path, base64_decode($_POST[$field_name]));

                    $stmt_insert = $conn->prepare(
                        "INSERT INTO emp_docs (doc_type, doc_name, emp_code, doc_remark, created_by, created_on)
                         VALUES (?,?,?,?,?,?)"
                    );
                    $stmt_insert->bind_param(
                        "ssssss",
                        $doc_name, $file_name, $id, $remark, $user_id, $created_on
                    );
                    $stmt_insert->execute();
                    $stmt_insert->close();
                }
            }
        }

        // --- Update employee ID numbers for all docs ---
        $stmt_update_numbers = $conn->prepare(
            "UPDATE employee SET aadhar_no=?, pancard_no=?, uan_no=?, pf_no=?, passport_no=?, esis_no=?,
             uan_date=?, esis_date=?, passport_valid_date=? WHERE employee_code=?"
        );
      $stmt_update_numbers->bind_param(
    "ssssssssss",
    $aadhar_no,
    $pan_no,
    $uan_no,
    $pf_no,
    $passport_no,
    $esis_no,
    $uan_date,
    $esis_date,
    $passport_date,
    $id
);
        $stmt_update_numbers->execute();
        $stmt_update_numbers->close();

        // --- Insert Activity Log ---
        $activity_type = 'Profile Update';
        $user_address = $_POST['address'] ?? '';
        $user_city = $_POST['city'] ?? '';
        $user_state = $_POST['state'] ?? '';
        $user_pincode = $_POST['pincode'] ?? '';
        $user_latitude = $_POST['user_latitude'] ?? '';
        $user_longitude = $_POST['user_longitude'] ?? '';

        $stmt_log = $conn->prepare(
            "INSERT INTO emp_activity_log
            (user_id, user_activity_type, user_address, user_city, user_state, user_pincode,
             user_latitude, user_longitude, created_by, created_on)
            VALUES (?,?,?,?,?,?,?,?,?,?)"
        );
        $stmt_log->bind_param(
            "ssssssssss",
            $user_id,
            $activity_type,
            $user_address,
            $user_city,
            $user_state,
            $user_pincode,
            $user_latitude,
            $user_longitude,
            $user_id,
            $created_on
        );
        $stmt_log->execute();
        $stmt_log->close();

        // --- FINAL RESPONSE ---
        $response['error'] = false;
        $response['message'] = 'Personal details updated successfully!!';

    } else {

        $response['error'] = true;
        $response['message'] = 'Required parameters are not available';

    }
break;

//-------------------------------------------------------------------------------------------------

case 'getAadharDetails':

    if(isTheseParametersAvailable(array('image_base64'))){

        $image_base64 = $_POST['image_base64'];

        // --- Prepare the request for Google Document AI ---
       $projectId   = "77305643982";
       $location    = "us";
       $processorId = "f09db5a1cbacaf5b";
       $apiKey = 'YOUR_API_KEY'; // or use OAuth token

        $endpoint = "https://documentai.googleapis.com/v1/projects/$projectId/locations/$location/processors/$processorId:process?key=$apiKey";

        $payload = [
            "rawDocument" => [
                "content" => $image_base64,
                "mimeType" => "image/jpeg" // or 'application/pdf' if PDF
            ]
        ];

        // --- Make the POST request ---
        $ch = curl_init($endpoint);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, [
            "Content-Type: application/json"
        ]);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($payload));

        $result = curl_exec($ch);
        $httpcode = curl_getinfo($ch, CURLINFO_HTTP_CODE);

        if(curl_errno($ch)){
            $response['error'] = true;
            $response['message'] = 'Request error: ' . curl_error($ch);
        } else if($httpcode != 200){
            $response['error'] = true;
            $response['message'] = 'API error: HTTP ' . $httpcode . ' - ' . $result;
        } else {
            $response['error'] = false;

            $data = json_decode($result, true);

            // --- Extract all fields detected by Document AI ---
            $extractedFields = [];
            if(isset($data['document']['entities'])){
                foreach($data['document']['entities'] as $entity){
                    $fieldName = $entity['type'] ?? '';
                    $fieldValue = $entity['mentionText'] ?? '';
                    $extractedFields[$fieldName] = $fieldValue;
                }
            }

            $response['data'] = $extractedFields;
        }

        curl_close($ch);

    } else {
        $response['error'] = true;
        $response['message'] = 'Required parameter image_base64 is missing';
    }

    break;


    //--------------------------------------------------------------------------------------------------------------------------
    case 'getAllEmployeeDocs':  

 $emp_id = $_POST['emp_id']; 

$stmt = $conn->prepare("SELECT doc_type,doc_name FROM emp_docs where emp_code=? and active ='0'");
$stmt->bind_param("s",$emp_id);  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($type,$image);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
         $temp['image'] =IMGPATH.$image ;
         $temp['doc_type']=$type;
    
     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Employee docs got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//------------------------------------------------------------------------------------------------------ 
case 'updateJobLeftStatus': 
    if (isTheseParametersAvailable(array('employees'))) {   
        date_default_timezone_set('Asia/Kolkata');

        $employees = json_decode($_POST['employees'], true);
        $status = "Left";
        $last_date = date('Y-m-d'); 

        // Begin transaction
        $conn->begin_transaction();
        try {
            foreach ($employees as $emp) {
                $emp_code = $emp['emp_code'];

                if ($status == "Working") {
                    $stmt = $conn->prepare("UPDATE employee SET em_status = ? WHERE employee_code = ?");
                    $stmt->bind_param("ss", $status, $emp_code);
                } else {
                    $stmt = $conn->prepare("UPDATE employee SET em_status = ?, last_working_date_sp = ? WHERE employee_code = ?");
                    $stmt->bind_param("sss", $status, $last_date, $emp_code);
                }

                if (!$stmt->execute()) {
                    throw new Exception("Failed to update job status for employee: $emp_code");
                }

                $stmt->close();
            }

            // Commit all updates
            $conn->commit();

            $response['error'] = false;   
            $response['message'] = 'Job left status updated successfully!!';   

        } catch (Exception $e) {
            // Rollback if any update fails
            $conn->rollback();
            $response['error'] = true;
            $response['message'] = $e->getMessage();
        }

    } else {  
        $response['error'] = true;   
        $response['message'] = 'Required parameters are not available';   
    }  
    break;

//--------------------------------------------------------------------------------------------------
   case 'updateJobLeftStatusIndividual':
      if(isTheseParametersAvailable(array('id'))){   
      
  $id = $_POST['id']; 
  $status = $_POST['status']; 
  $last_date = date('Y-m-d'); 

           if($status == "Working")
           {
            $stmt = $conn->prepare("UPDATE employee SET em_status = ? where id = ?");  
            $stmt->bind_param("ss",$status,$id);  
            $stmt->execute();
            $stmt->close();
           }
           else{
                 $stmt = $conn->prepare("UPDATE employee SET em_status = ?,last_working_date_sp= ? where id = ?");  
            $stmt->bind_param("sss",$status,$last_date,$id);  
            $stmt->execute();
            $stmt->close();
             }  

                $response['error'] = false;   
                $response['message'] = 'Job left status updated successfully!!';   
    }  
    else{  
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';   
    }  
    break; 
//--------------------------------------------------------------------------------------------------

//      case 'getEmployeeBankDetails':

//     if(isTheseParametersAvailable(array('emp_code'))){  
      
//     $emp_code = $_POST['emp_code'];  

//     $stmt_get_data = $conn->prepare("SELECT emp_full_name,bank_name,account_no,bank_ifsc,ac_holder_name,bank_address,bank_state,bank_city,bank_micr,card_no FROM employee WHERE employee_code = ?");
// $stmt_get_data->bind_param("s", $emp_code);
//                      $stmt_get_data->execute();
//                      $stmt_get_data->store_result();

//   if ($stmt_get_data->num_rows > 0) {

//   $stmt_get_data->bind_result($full_name,$bank,$account_no,$ifsc,$holder,$address,$state,$city,$micr,$cardno);
  
//    $stmt_get_data->fetch();

//                  $user = array(  
//                 'address'=>$address,
//                 'state'=>$state,
//                 'city'=>$city,
//                 'micr'=>$micr,
//                 'cardno'=>$cardno,
//                  'bank'=>$bank,
//                   'ifsc'=>$ifsc,
//                    'account_no'=>$account_no,
//                     'holder'=>$full_name
//             );      

//             $response['error'] = false;   
//             $response['message'] = 'Bank details fetched successful!!';   
//             $response['user'] = $user;   
//         } 
//         else
//         {
//             $response['error'] = true;   
//             $response['message'] = 'Bank details not found!!';    
//         }
       
// }
//     else
//     {
//         $response['error'] = true;   
//         $response['message'] = 'required parameters are not available';  
//     }
//     break; 


    case 'getEmployeeBankDetails':

if(isTheseParametersAvailable(array('emp_code'))){  
      
    $emp_code = $_POST['emp_code'];  

    $stmt_get_data = $conn->prepare("SELECT emp_full_name,bank_name,account_no,bank_ifsc,
    ac_holder_name,bank_address,bank_state,bank_city,bank_micr,card_no,cancelled_cheque_image 
    FROM employee WHERE employee_code = ?");

    $stmt_get_data->bind_param("s", $emp_code);
    $stmt_get_data->execute();
    $stmt_get_data->store_result();

    if ($stmt_get_data->num_rows > 0) {

        $stmt_get_data->bind_result(
            $full_name,$bank,$account_no,$ifsc,$holder,
            $address,$state,$city,$micr,$cardno,$cancelled_cheque_image
        );
  
        $stmt_get_data->fetch();

        /* ===== Convert cheque images to full path array ===== */

        $chequeImagesArray = [];

        if (!empty($cancelled_cheque_image)) {

            $images = explode(',', $cancelled_cheque_image);

            foreach ($images as $img) {
                $chequeImagesArray[] = IMGPATH . $img;
            }
        }

        $user = array(  
            'address' => $address,
            'state' => $state,
            'city' => $city,
            'micr' => $micr,
            'cardno' => $cardno,
            'bank' => $bank,
            'ifsc' => $ifsc,
            'account_no' => $account_no,
            'holder' => $full_name,
            'cheque_image' => $chequeImagesArray
        );      

        $response['error'] = false;   
        $response['message'] = 'Bank details fetched successful!!';   
        $response['user'] = $user;   

    } else {

        $response['error'] = true;   
        $response['message'] = 'Bank details not found!!';    

    }
       
} else {

    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';  

}

break;

//-----------------------------------------------------------------------------------------------------------
    case 'getBeneficiaryBankDetails':

    if(isTheseParametersAvailable(array('id'))){  
      
    $id = $_POST['id'];  

    $stmt_get_data = $conn->prepare("SELECT fullname,bank_name,account_no,bank_ifsc,ac_holder_name,bank_address,bank_state,bank_city,bank_micr,card_no FROM users WHERE id ='$id'");
                     $stmt_get_data->execute();
                     $stmt_get_data->store_result();

  if ($stmt_get_data->num_rows > 0) {

  $stmt_get_data->bind_result($name,$bank,$account_no,$ifsc,$holder,$address,$state,$city,$micr,$cardno);
  
   $stmt_get_data->fetch();

                 $user = array(  
                'address'=>$address,
                'state'=>$state,
                'city'=>$city,
                'micr'=>$micr,
                'cardno'=>$cardno,
                 'bank'=>$bank,
                  'ifsc'=>$ifsc,
                   'account_no'=>$account_no,
                    'holder'=>$holder,
                    'name'=>$name
            );      

            $response['error'] = false;   
            $response['message'] = 'Bank details fetched successful!!';   
            $response['user'] = $user;   
        } 
        else
        {
            $response['error'] = true;   
            $response['message'] = 'Job post not found!!';    
        }
       
}
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 
//---------
//    case 'updateEmployeeBankDetails':

// if(isTheseParametersAvailable(array('emp_code'))){

// $emp_code = $_POST['emp_code'];
// $bank = $_POST['bank'];
// $acno = $_POST['acno'];
// $ifsc = $_POST['ifsc'];
// $holder = $_POST['holder'];
// $address = $_POST['address'];
// $state = $_POST['state'];
// $city = $_POST['city'];
// $micr = $_POST['micr'];
// $card = $_POST['card'];

// $conn->begin_transaction();

// try{

// $stmt1 = $conn->prepare("UPDATE employee SET bank_name=?,account_no=?,bank_ifsc=?,ac_holder_name=?,bank_address=?,bank_state=?,bank_city=?,bank_micr=?,card_no=? WHERE employee_code=?");
// $stmt1->bind_param("ssssssssss",$bank,$acno,$ifsc,$holder,$address,$state,$city,$micr,$card,$emp_code);
// $stmt1->execute();
// $stmt1->close();

// $stmt2 = $conn->prepare("UPDATE users SET bank_name=?,account_no=?,bank_ifsc=?,ac_holder_name=?,bank_address=?,bank_state=?,bank_city=?,bank_micr=?,card_no=? WHERE emp_code=?");
// $stmt2->bind_param("ssssssssss",$bank,$acno,$ifsc,$holder,$address,$state,$city,$micr,$card,$emp_code);
// $stmt2->execute();
// $stmt2->close();

// $conn->commit();
// $response['error'] = false;
// $response['message'] = 'Bank details updated successfully!';

// }catch(Exception $e){
// $conn->rollback();
// $response['error'] = true;
// $response['message'] = 'Update failed.';
// }

// }else{
// $response['error'] = true;
// $response['message'] = 'required parameters are not available';
// }
// break;

    case 'updateEmployeeBankDetails':

if(isTheseParametersAvailable(array('emp_code'))){

$emp_code = $_POST['emp_code'];
$bank = $_POST['bank'];
$acno = $_POST['acno'];
$ifsc = $_POST['ifsc'];
$holder = $_POST['holder'];
$address = $_POST['address'];
$state = $_POST['state'];
$city = $_POST['city'];
$micr = $_POST['micr'];
$card = $_POST['card'];
$chequeCount = isset($_POST['chequeCount']) ? (int)$_POST['chequeCount'] : 0;

$conn->begin_transaction();

try{

/* ================= UPDATE employee ================= */

$stmt1 = $conn->prepare("UPDATE employee SET 
bank_name=?,account_no=?,bank_ifsc=?,ac_holder_name=?,
bank_address=?,bank_state=?,bank_city=?,bank_micr=?,card_no=? 
WHERE employee_code=?");

$stmt1->bind_param("ssssssssss",
$bank,$acno,$ifsc,$holder,$address,
$state,$city,$micr,$card,$emp_code);

$stmt1->execute();
$stmt1->close();

/* ================= UPDATE users ================= */

$stmt2 = $conn->prepare("UPDATE users SET 
bank_name=?,account_no=?,bank_ifsc=?,ac_holder_name=?,
bank_address=?,bank_state=?,bank_city=?,bank_micr=?,card_no=? 
WHERE emp_code=?");

$stmt2->bind_param("ssssssssss",
$bank,$acno,$ifsc,$holder,$address,
$state,$city,$micr,$card,$emp_code);

$stmt2->execute();
$stmt2->close();

/* ================= HANDLE CHEQUE IMAGES ================= */

$chequeImages = [];

if ($chequeCount > 0) {

    for ($i = 0; $i < $chequeCount; $i++) {

        $counter = $i + 1;

        if (!empty($_POST['cheque' . $counter])) {

            $file_name = "cheque_" . $acno . "_" . $counter . ".jpg";

            file_put_contents(
                $file_name,
                base64_decode($_POST['cheque' . $counter])
            );

            $chequeImages[] = $file_name;
        }
    }
}

/* ================= UPDATE cheque_image COLUMN ================= */

if (!empty($chequeImages)) {

    $chequeImageString = implode(',', $chequeImages);

    $stmt3 = $conn->prepare(
        "UPDATE employee SET cancelled_cheque_image=? WHERE employee_code=?"
    );

    $stmt3->bind_param("ss", $chequeImageString, $emp_code);
    $stmt3->execute();
    $stmt3->close();

} else {

    // No images → make blank
    $stmt3 = $conn->prepare(
        "UPDATE employee SET cancelled_cheque_image='' WHERE employee_code=?"
    );

    $stmt3->bind_param("s", $emp_code);
    $stmt3->execute();
    $stmt3->close();
}

$conn->commit();
$response['error'] = false;
$response['message'] = 'Bank details updated successfully!';

}catch(Exception $e){

$conn->rollback();
$response['error'] = true;
$response['message'] = $e->getMessage();

}

}else{

$response['error'] = true;
$response['message'] = 'required parameters are not available';

}

break;

// //--------------------------------------------------------------------------------------------------

//      case 'updateParticularBankDetails': 
//       if(isTheseParametersAvailable(array('id'))){
//            date_default_timezone_set('Asia/Kolkata');    
      
//   $id = $_POST['id']; 
//   $user_id = $_POST['user_id']; 
//   $bank = $_POST['bank'];
//   $acno = $_POST['acno']; 
//   $ifsc = $_POST['ifsc'];
//   $holder = $_POST['holder']; 
//   $address = $_POST['address'];
//   $state = $_POST['state']; 
//   $city = $_POST['city'];
//   $micr = $_POST['micr']; 
//   $card = $_POST['card'];
//   $modified_on = date('Y-m-d H:i:s');
 
//             $stmt = $conn->prepare("UPDATE user_bank_details SET bank_name =?,account_no=?,bank_ifsc=?,ac_holder_name=?,bank_address=?,bank_state=?,bank_city=?,bank_micr=?,card_no=?,modified_by=?,modified_on=? where id = ?");  
//             $stmt->bind_param("ssssssssssss",$bank,$acno,$ifsc,$holder,$address,$state,$city,$micr,$card,$user_id,$modified_on,$id);  
//             $stmt->execute();
//             $stmt->close();
        
//                 $response['error'] = false;   
//                 $response['message'] = 'Bank details updated successfully!!';   
//     }  
//     else{  
//         $response['error'] = true;   
//         $response['message'] = 'required parameters are not available';   
//     }  
//     break; 

    //---------------------------------------------------------------------------------

 case 'addNewBankDetails': 
      if(isTheseParametersAvailable(array('user_id'))){ 
      date_default_timezone_set('Asia/Kolkata');  
      
  $user_id = $_POST['user_id']; 
  $bank = $_POST['bank'];
  $acno = $_POST['acno']; 
  $ifsc = $_POST['ifsc'];
  $holder = $_POST['holder']; 
  $address = $_POST['address'];
  $state = $_POST['state']; 
  $city = $_POST['city'];
  $micr = $_POST['micr']; 
  $card = $_POST['card'];
  $created_on = date('Y-m-d H:i:s');
 
           $stmt = $conn->prepare("INSERT INTO user_bank_details 
    (bank_name, account_no, bank_ifsc, ac_holder_name, bank_address, bank_state, bank_city, bank_micr, card_no, user_id, created_by, created_on) 
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    $stmt->bind_param("ssssssssssss", $bank, $acno, $ifsc, $holder, $address, $state, $city, $micr, $card,$user_id,$user_id,$created_on);

$stmt->execute();
$stmt->close();
        
                $response['error'] = false;   
                $response['message'] = 'Bank added successfully!!';   
    }  
    else{  
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';   
    }  
    break; 

    //-------------------------------------------------------------------------------------

case 'deleteParticularBank': 
      if(isTheseParametersAvailable(array('id'))){ 
      date_default_timezone_set('Asia/Kolkata');  
      
  $id = $_POST['id']; 

    $stmt_soft_delete = $conn->prepare("UPDATE user_bank_details SET active = 1 WHERE id = ?");
        $stmt_soft_delete->bind_param("s", $id);
        $stmt_soft_delete->execute();
        $stmt_soft_delete->close();
        
                $response['error'] = false;   
                $response['message'] = 'Bank deleted successfully!!';   
    }  
    else{  
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';   
    }  
    break; 

    //------------------------------------------------------------------------------------------

 case 'getParticularBankDetails':

    if(isTheseParametersAvailable(array('id'))){  
      
    $id = $_POST['id'];  

    $stmt_get_data = $conn->prepare("SELECT bank_name,account_no,bank_ifsc,ac_holder_name,bank_address,bank_state,bank_city,bank_micr,card_no FROM user_bank_details WHERE id ='$id'");
                     $stmt_get_data->execute();
                     $stmt_get_data->store_result();

  if ($stmt_get_data->num_rows > 0) {

  $stmt_get_data->bind_result($bank,$account_no,$ifsc,$holder,$address,$state,$city,$micr,$cardno);
  
   $stmt_get_data->fetch();

                 $user = array(  
                'address'=>$address,
                'state'=>$state,
                'city'=>$city,
                'micr'=>$micr,
                'cardno'=>$cardno,
                 'bank'=>$bank,
                  'ifsc'=>$ifsc,
                   'account_no'=>$account_no,
                    'holder'=>$holder
            );      

            $response['error'] = false;   
            $response['message'] = 'Bank details fetched successful!!';   
            $response['user'] = $user;   
        } 
        else
        {
            $response['error'] = true;   
            $response['message'] = 'Bank detail not found!!';    
        }
       
}
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 
//-------------


    //-----------------------------------------------------------------------------------------
    case 'getSupervisorAttendance':  

$from_date = $_POST['from_date'];  
$to_date = $_POST['to_date'];  
$site_id = $_POST['site_id'];  
$client_code = $_POST['client_code'];
$status = $_POST['status'];
$role = strtolower($_POST['role']);
$emp_code = $_POST['emp_code'];
$from_year = date('Y', strtotime($from_date));
$from_month = date('n', strtotime($from_date));
$from_day = date('j', strtotime($from_date));
$to_year = date('Y', strtotime($to_date));
$to_month = date('n', strtotime($to_date));
$to_day = date('j', strtotime($to_date)); 
$limit = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
$offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0; 


if($status == 'All')
{
    if($role == "employee")
    {
          $stmt = $conn->prepare("SELECT id,emp_name,emp_code,rank,status,concat(at_year,'-',at_month,'-',at_day) FROM attendance WHERE emp_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and active = '0' LIMIT ?,?");  
          $stmt->bind_param("sss",$emp_code,$offset,$limit); 

           $stmt_count = $conn->prepare("SELECT Count(id) FROM attendance WHERE emp_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and active = '0' ");  
          $stmt_count->bind_param("s",$emp_code); 
    }
    else
    {
        $stmt = $conn->prepare("SELECT id,emp_name,emp_code,rank,status,concat(at_year,'-',at_month,'-',at_day) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year)  and active = '0' LIMIT ?,?");  
          $stmt->bind_param("ssss",$site_id,$client_code,$offset,$limit); 

          $stmt_count = $conn->prepare("SELECT COUNT(id) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and active = '0' ");  
          $stmt_count->bind_param("ss",$site_id,$client_code); 

    }
  
}
else if($status == 'Absent')
{
$stmt = $conn->prepare("SELECT id,emp_name,emp_code,rank,status,concat(at_year,'-',at_month,'-',at_day) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status='A' and active = '0' LIMIT ?,?");  
$stmt->bind_param("ssss",$site_id,$client_code,$offset,$limit); 


$stmt_count = $conn->prepare("SELECT COUNT(id) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status='A' and active = '0'");  
$stmt_count->bind_param("ss",$site_id,$client_code); 
}
else if($status == 'Present')
{
 $stmt = $conn->prepare("SELECT id,emp_name,emp_code,rank,status,concat(at_year,'-',at_month,'-',at_day) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status!='A' and active = '0'  LIMIT ?,?");  
$stmt->bind_param("ssss",$site_id,$client_code,$offset,$limit); 

 $stmt_count = $conn->prepare("SELECT COUNT(id) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status!='A' and active = '0' ");  
$stmt_count->bind_param("ss",$site_id,$client_code); 

}
else if($status == 'Sl')
{
 $stmt = $conn->prepare("SELECT id,emp_name,emp_code,rank,status,concat(at_year,'-',at_month,'-',at_day) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status ='SL' and active = '0' LIMIT ?,?");  
$stmt->bind_param("ssss",$site_id,$client_code,$offset,$limit); 

 $stmt_count = $conn->prepare("SELECT COUNT(id) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status ='SL' and active = '0' ");  
$stmt_count->bind_param("ss",$site_id,$client_code); 

}
else if($status == 'Cl')
{
 $stmt = $conn->prepare("SELECT id,emp_name,emp_code,rank,status,concat(at_year,'-',at_month,'-',at_day) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status ='CL' and active = '0'  LIMIT ?,?");  
$stmt->bind_param("ssss",$site_id,$client_code,$offset,$limit); 

 $stmt_count = $conn->prepare("SELECT COUNT(id) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status ='CL' and active = '0' ");  
$stmt_count->bind_param("ss",$site_id,$client_code); 

}
else if($status == 'Sl')
{
 $stmt = $conn->prepare("SELECT id,emp_name,emp_code,rank,status,concat(at_year,'-',at_month,'-',at_day) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status ='SL' and active = '0'  LIMIT ?,?");  
$stmt->bind_param("ssss",$site_id,$client_code,$offset,$limit); 

 $stmt_count = $conn->prepare("SELECT COUNT(id) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status ='SL' and active = '0' ");  
$stmt_count->bind_param("ss",$site_id,$client_code); 

}
else if($status == 'H')
{
 $stmt = $conn->prepare("SELECT id,emp_name,emp_code,rank,status,concat(at_year,'-',at_month,'-',at_day) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status ='H' and active = '0'  LIMIT ?,?");  
$stmt->bind_param("ssss",$site_id,$client_code,$offset,$limit); 

 $stmt_count = $conn->prepare("SELECT COUNT(id) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status ='H' and active = '0' ");  
$stmt_count->bind_param("ss",$site_id,$client_code); 

}
$stmt->execute();  
$stmt->store_result(); 

 $stmt_count->execute();
$stmt_count->bind_result($total_rows);
$stmt_count->fetch();
$stmt_count->close();


if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$emp_name,$emp_code,$rank,$status,$date);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['id'] = $id; 
     $temp['emp_name']=$emp_name ;
     $temp['emp_code'] = $emp_code;
     $temp['rank'] = $rank;
     $temp['status'] = $status;
     $temp['date'] = date('Y-m-d',strtotime($date));
     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Attendance got successful!!';   
 $response['user'] = $banner_data;
  $response['total_rows'] = $total_rows;   
}  
else{  
    $response['error'] = true;   
    $response['message'] = 'No attendance for this dates!!'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//------------------------------------------------------------------------------------------------------
//
 case 'getSupervisorAttendanceReport':  
  
$site_id = $_POST['site_id'];  
$client_code = $_POST['client_code'];  
$from_date = $_POST['from_date'];
$to_date = $_POST['to_date'];
$from_year = date('Y', strtotime($from_date));
$from_month = date('n', strtotime($from_date));
$from_day = date('j', strtotime($from_date));
$to_year = date('Y', strtotime($to_date));
$to_month = date('n', strtotime($to_date));
$to_day = date('j', strtotime($to_date));  
$limit = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
$offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0; 

//echo ($from_day." ".$from_month." ".$from_year." ").'=========';
//echo ($to_day." ".$to_month." ".$to_year." ");die();

$stmt = $conn->prepare("SELECT SUM(emp_nos) FROM `client_other_info` WHERE rate_id=?");  
$stmt->bind_param("s",$site_id);
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($expected);  
    $stmt->fetch();
    $banner_data = array();

$stmt_dates = $conn->prepare("SELECT at_day,at_month,at_year from attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) group by at_day,at_month,at_year LIMIT ?,?");  
$stmt_dates->bind_param("ssss",$site_id,$client_code,$offset,$limit);
$stmt_dates->execute();  
$stmt_dates->store_result(); 

$stmt_dates_count = $conn->prepare("SELECT COUNT(id) from attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) group by at_day,at_month,at_year");  
$stmt_dates_count->bind_param("ss",$site_id,$client_code);
 $stmt_dates_count->execute();
$stmt_dates_count->bind_result($total_rows);
$stmt_dates_count->fetch();
$stmt_dates_count->close();

if($stmt_dates->num_rows > 0){  
    $stmt_dates->bind_result($day,$month,$year);  
    while($stmt_dates->fetch()){
      
    $stmt_absent = $conn->prepare("SELECT COUNT(DISTINCT emp_code) from attendance WHERE site_id = ? and client_code = ? and at_day=? and at_month= ? and at_year=? and status='A'");  
$stmt_absent->bind_param("sssss",$site_id,$client_code,$day,$month,$year); 
$stmt_absent->execute();  
$stmt_absent->store_result();
$stmt_absent->bind_result($absent); 
$stmt_absent->fetch();

    $stmt_present = $conn->prepare("SELECT COUNT(DISTINCT emp_code) from attendance WHERE site_id = ? and client_code = ? and at_day=? and at_month= ? and at_year=? and status!='A'");  
$stmt_present->bind_param("sssss",$site_id,$client_code,$day,$month,$year); 
$stmt_present->execute();  
$stmt_present->store_result();
$stmt_present->bind_result($count);  
$stmt_present->fetch();

     $temp = array(); 
     $temp['count'] = $count; 
     //$temp['status']=$status ;
     $temp['date'] = sprintf("%04d-%02d-%02d", $year, $month, $day);
     $temp['expected'] = $expected; 
     $temp['absent'] = $absent;
     array_push($banner_data, $temp);  

 $response['error'] = false;   
 $response['message'] = 'Attendance report got successful!!';   
 $response['user'] = $banner_data; 
  $response['total_rows'] = $total_rows; 
}
}
else
{  
    $response['error'] = false;   
    $response['message'] = 'No attendance for these dates!!';  
}
}
else
{  
    $response['error'] = false;   
    $response['message'] = 'Employees not present!!';  
}

//}  
//}  
break;  
//------------------------------------------------------------------------------------------------------
 case 'getSupervisorAttendanceReportForDownload':  
  
$site_id = $_POST['site_id'];  
$client_code = $_POST['client_code'];  
$from_date = $_POST['from_date'];
$to_date = $_POST['to_date'];
$from_year = date('Y', strtotime($from_date));
$from_month = date('n', strtotime($from_date));
$from_day = date('j', strtotime($from_date));
$to_year = date('Y', strtotime($to_date));
$to_month = date('n', strtotime($to_date));
$to_day = date('j', strtotime($to_date));  

$stmt = $conn->prepare("SELECT SUM(emp_nos) FROM `client_other_info` WHERE rate_id=?");  
$stmt->bind_param("s",$site_id);
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($expected);  
    $stmt->fetch();
    $banner_data = array();

$stmt_dates = $conn->prepare("SELECT at_day,at_month,at_year from attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) group by at_day,at_month,at_year");  
$stmt_dates->bind_param("ss",$site_id,$client_code);
$stmt_dates->execute();  
$stmt_dates->store_result(); 

if($stmt_dates->num_rows > 0){  
    $stmt_dates->bind_result($day,$month,$year);  
    while($stmt_dates->fetch()){
      
    $stmt_absent = $conn->prepare("SELECT COUNT(DISTINCT emp_code) from attendance WHERE site_id = ? and client_code = ? and at_day=? and at_month= ? and at_year=? and status='A'");  
$stmt_absent->bind_param("sssss",$site_id,$client_code,$day,$month,$year); 
$stmt_absent->execute();  
$stmt_absent->store_result();
$stmt_absent->bind_result($absent); 
$stmt_absent->fetch();

    $stmt_present = $conn->prepare("SELECT COUNT(DISTINCT emp_code) from attendance WHERE site_id = ? and client_code = ? and at_day=? and at_month= ? and at_year=? and status!='A'");  
$stmt_present->bind_param("sssss",$site_id,$client_code,$day,$month,$year); 
$stmt_present->execute();  
$stmt_present->store_result();
$stmt_present->bind_result($count);  
$stmt_present->fetch();

     $temp = array(); 
     $temp['count'] = $count; 
     //$temp['status']=$status ;
     $temp['date'] = sprintf("%04d-%02d-%02d", $year, $month, $day);
     $temp['expected'] = $expected; 
     $temp['absent'] = $absent;
     array_push($banner_data, $temp);  

 $response['error'] = false;   
 $response['message'] = 'Attendance report got successful!!';   
 $response['user'] = $banner_data; 
}
}
else
{  
    $response['error'] = false;   
    $response['message'] = 'No attendance for these dates!!';  
}
}
else
{  
    $response['error'] = false;   
    $response['message'] = 'Employees not present!!';  
}

//}  
//}  
break; 
//-------------------------------------------------------------------------------------------
case 'getSupervisorAttendanceCountForDashboard':  
date_default_timezone_set('Asia/Kolkata');
$site_id = $_POST['site_id'];  
$client_code = $_POST['client_code'];  
//$current_date = date('Y-m-d');
$from_date = $_POST['from_date'];
$to_date = $_POST['to_date'];
$from_year = date('Y', strtotime($from_date));
$from_month = date('n', strtotime($from_date));
$from_day = date('j', strtotime($from_date));
$to_year = date('Y', strtotime($to_date));
$to_month = date('n', strtotime($to_date));
$to_day = date('j', strtotime($to_date)); 

$stmt = $conn->prepare("SELECT SUM(emp_nos) FROM `client_other_info` WHERE rate_id=?");  
$stmt->bind_param("s",$site_id);
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($expected);  
    $stmt->fetch();
    $banner_data = array();     
    $stmt_absent = $conn->prepare("SELECT COUNT(DISTINCT emp_code) from attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status='A' and active='0'");  
$stmt_absent->bind_param("ss",$site_id,$client_code); 
$stmt_absent->execute();  
$stmt_absent->store_result();
$stmt_absent->bind_result($absent); 
$stmt_absent->fetch();

    $stmt_present = $conn->prepare("SELECT COUNT(DISTINCT emp_code) from attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status!='A' and active='0'");  
$stmt_present->bind_param("ss",$site_id,$client_code); 
$stmt_present->execute();  
$stmt_present->store_result();
$stmt_present->bind_result($count);  
$stmt_present->fetch();

$stmt_sl = $conn->prepare("SELECT COUNT(DISTINCT emp_code) 
    FROM attendance 
    WHERE site_id = ? 
      AND client_code = ? 
      AND (at_day >= $from_day AND at_month >= $from_month AND at_year >= $from_year) 
      AND (at_day <= $to_day   AND at_month <= $to_month   AND at_year <= $to_year)
      AND status='SL' AND active='0'");

$stmt_sl->bind_param("ss", $site_id, $client_code);
$stmt_sl->execute();
$stmt_sl->store_result();
$stmt_sl->bind_result($sl);
$stmt_sl->fetch();

$stmt_pl = $conn->prepare("SELECT COUNT(DISTINCT emp_code) 
    FROM attendance 
    WHERE site_id = ? 
      AND client_code = ? 
      AND (at_day >= $from_day AND at_month >= $from_month AND at_year >= $from_year) 
      AND (at_day <= $to_day   AND at_month <= $to_month   AND at_year <= $to_year)
      AND status='PL' AND active='0'");

$stmt_pl->bind_param("ss", $site_id, $client_code);
$stmt_pl->execute();
$stmt_pl->store_result();
$stmt_pl->bind_result($pl);
$stmt_pl->fetch();



$stmt_cl = $conn->prepare("SELECT COUNT(DISTINCT emp_code) 
    FROM attendance 
    WHERE site_id = ? 
      AND client_code = ? 
      AND (at_day >= $from_day AND at_month >= $from_month AND at_year >= $from_year) 
      AND (at_day <= $to_day   AND at_month <= $to_month   AND at_year <= $to_year)
      AND status='CL' AND active='0'");

$stmt_cl->bind_param("ss", $site_id, $client_code);
$stmt_cl->execute();
$stmt_cl->store_result();
$stmt_cl->bind_result($cl);
$stmt_cl->fetch();

$stmt_h = $conn->prepare("SELECT COUNT(DISTINCT emp_code) 
    FROM attendance 
    WHERE site_id = ? 
      AND client_code = ? 
      AND (at_day >= $from_day AND at_month >= $from_month AND at_year >= $from_year) 
      AND (at_day <= $to_day   AND at_month <= $to_month   AND at_year <= $to_year)
      AND status='H' AND active='0'");

$stmt_h->bind_param("ss", $site_id, $client_code);
$stmt_h->execute();
$stmt_h->store_result();
$stmt_h->bind_result($h);
$stmt_h->fetch();

     $temp = array(); 
     $temp['count'] = $count; 
     //$temp['status']=$status ;
    // $temp['date'] = sprintf("%04d-%02d-%02d", $year, $month, $day);
     $temp['expected'] = $expected; 
     $temp['absent'] = $absent;
     $temp['sl'] = $sl;
     $temp['cl'] = $cl;
     $temp['pl'] = $pl;
     $temp['h'] = $h;
     array_push($banner_data, $temp);  

 $response['error'] = false;   
 $response['message'] = 'Todays attendance report got successful!!';   
 $response['user'] = $banner_data; 
}
else
{  
    $response['error'] = false;   
    $response['message'] = 'Employees not present!!';  
} 
break;  
//------------------------------------------------------------------------------------------------------
 case 'getSupervisorEmployeesCount':

    if(isTheseParametersAvailable(array('site_id'))){  
       
$site_id = $_POST['site_id'];  
$client_id = $_POST['client_id'];  
$from_date = $_POST['from_date'];
$to_date = $_POST['to_date'];
$from_year = date('Y', strtotime($from_date));
$from_month = date('n', strtotime($from_date));
$from_day = date('j', strtotime($from_date));
$to_year = date('Y', strtotime($to_date));
$to_month = date('n', strtotime($to_date));
$to_day = date('j', strtotime($to_date)); 

 $stmt_get_data = $conn->prepare("SELECT COUNT(DISTINCT employee_code) from employee WHERE site_id = ? and client_id = ? and active = '0'");
 $stmt_get_data->bind_param("ss",$site_id,$client_id);
 $stmt_get_data->execute();
 $stmt_get_data->store_result();

  if ($stmt_get_data->num_rows > 0) {

  $stmt_get_data->bind_result($total);
   $stmt_get_data->fetch();

 // $stmt_get_working = $conn->prepare("SELECT COUNT(DISTINCT e.employee_code) FROM employee e JOIN attendance a ON e.employee_code = a.emp_code WHERE e.site_id = ? AND e.client_id = ? AND e.active = '0' AND e.em_status = 'Working' AND a.status = 'P' and (a.at_day>= ? and a.at_month >= ? and a.at_year>= ?) and (a.at_day<= ? and a.at_month <= ? and a.at_year<= ?)");
 // $stmt_get_working->bind_param("ssssssss",$site_id,$client_id,$from_date,$from_month,$from_year,$to_date,$to_month,$to_year);

  $stmt_get_working = $conn->prepare("SELECT COUNT(DISTINCT employee_code) from employee WHERE site_id = ? and client_id = ? and em_status = 'Working' and active = '0'");
 $stmt_get_working->bind_param("ss",$site_id,$client_id);  
 $stmt_get_working->execute();
 $stmt_get_working->store_result();
 $stmt_get_working->bind_result($working);
 $stmt_get_working->fetch();

 $stmt_get_left = $conn->prepare("SELECT COUNT(DISTINCT employee_code) from employee WHERE site_id = ? and client_id = ? and em_status = 'Left' and active = '0'");
 $stmt_get_left->bind_param("ss",$site_id,$client_id);
 $stmt_get_left->execute();
 $stmt_get_left->store_result();
 $stmt_get_left->bind_result($left);
 $stmt_get_left->fetch();

                 $user = array(  
                'total'=>$total, 
                'working'=>$working,
                'left'=>$left
            );  

            $response['error'] = false;   
            $response['message'] = 'Employees count fetched successful!!';   
            $response['user'] = $user;   
        }
        else
        {
            $response['error'] = true;   
            $response['message'] = 'Employees not found!!';    
        }
}
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 
//-----------------------------------------------------------------------------------------------------------
  case 'getKycSummaryCountsForDashboard':

    if(isTheseParametersAvailable(array('site_id'))){  
       
        $site_id = $_POST['site_id'];  
        $client_id = $_POST['client_id'];  

        // Aadhaar
        $stmt_aadhar_done = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND aadhar_no IS NOT NULL AND aadhar_no!='' AND active='0' AND em_status='Working'");

        $stmt_aadhar_done->bind_param("ss",$site_id,$client_id);
        $stmt_aadhar_done->execute();
        $stmt_aadhar_done->store_result();
        $stmt_aadhar_done->bind_result($aadhar_done);
        $stmt_aadhar_done->fetch();
       
        $stmt_aadhar_not = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND (aadhar_no IS NULL OR aadhar_no='') AND active='0' AND em_status='Working'");
        $stmt_aadhar_not->bind_param("ss",$site_id,$client_id);
        $stmt_aadhar_not->execute();
        $stmt_aadhar_not->store_result();
        $stmt_aadhar_not->bind_result($aadhar_not);
        $stmt_aadhar_not->fetch();
        

        // Pancard
        $stmt_pancard_done = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND pancard_no IS NOT NULL AND pancard_no!='' AND active='0' AND em_status='Working'");
        $stmt_pancard_done->bind_param("ss",$site_id,$client_id);
        $stmt_pancard_done->execute();
        $stmt_pancard_done->store_result();
        $stmt_pancard_done->bind_result($pancard_done);
        $stmt_pancard_done->fetch();

        $stmt_pancard_not = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND (pancard_no IS NULL OR pancard_no='') AND active='0' AND em_status='Working'");
        $stmt_pancard_not->bind_param("ss",$site_id,$client_id);
        $stmt_pancard_not->execute();
        $stmt_pancard_not->store_result();
        $stmt_pancard_not->bind_result($pancard_not);
        $stmt_pancard_not->fetch();

        // Passport
        $stmt_passport_done = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND passport_no IS NOT NULL AND passport_no!='' AND active='0' AND em_status='Working'");
        $stmt_passport_done->bind_param("ss",$site_id,$client_id);
        $stmt_passport_done->execute();
        $stmt_passport_done->store_result();
        $stmt_passport_done->bind_result($passport_done);
        $stmt_passport_done->fetch();

        $stmt_passport_not = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND (passport_no IS NULL OR passport_no='') AND active='0' AND em_status='Working'");
        $stmt_passport_not->bind_param("ss",$site_id,$client_id);
        $stmt_passport_not->execute();
        $stmt_passport_not->store_result();
        $stmt_passport_not->bind_result($passport_not);
        $stmt_passport_not->fetch();

        // UAN
        $stmt_uan_done = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND uan_no IS NOT NULL AND uan_no!='' AND active='0' AND em_status='Working'");
        $stmt_uan_done->bind_param("ss",$site_id,$client_id);
        $stmt_uan_done->execute();
        $stmt_uan_done->store_result();
        $stmt_uan_done->bind_result($uan_done);
        $stmt_uan_done->fetch();

        $stmt_uan_not = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND (uan_no IS NULL OR uan_no='') AND active='0' AND em_status='Working'");
        $stmt_uan_not->bind_param("ss",$site_id,$client_id);
        $stmt_uan_not->execute();
        $stmt_uan_not->store_result();
        $stmt_uan_not->bind_result($uan_not);
        $stmt_uan_not->fetch();

        // ESIS
        $stmt_esis_done = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND esis_no IS NOT NULL AND esis_no!='' AND active='0' AND em_status='Working'");
        $stmt_esis_done->bind_param("ss",$site_id,$client_id);
        $stmt_esis_done->execute();
        $stmt_esis_done->store_result();
        $stmt_esis_done->bind_result($esis_done);
        $stmt_esis_done->fetch();

        $stmt_esis_not = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND (esis_no IS NULL OR esis_no='') AND active='0' AND em_status='Working'");
        $stmt_esis_not->bind_param("ss",$site_id,$client_id);
        $stmt_esis_not->execute();
        $stmt_esis_not->store_result();
        $stmt_esis_not->bind_result($esis_not);
        $stmt_esis_not->fetch();

        // PF
        $stmt_pf_done = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND pf_no IS NOT NULL AND pf_no!='' AND active='0' AND em_status='Working'");
        $stmt_pf_done->bind_param("ss",$site_id,$client_id);
        $stmt_pf_done->execute();
        $stmt_pf_done->store_result();
        $stmt_pf_done->bind_result($pf_done);
        $stmt_pf_done->fetch();

        $stmt_pf_not = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND (pf_no IS NULL OR pf_no='') AND active='0' AND em_status='Working'");
        $stmt_pf_not->bind_param("ss",$site_id,$client_id);
        $stmt_pf_not->execute();
        $stmt_pf_not->store_result();
        $stmt_pf_not->bind_result($pf_not);
        $stmt_pf_not->fetch();

         // DOB
        $stmt_dob_done = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND dob IS NOT NULL AND dob!='' AND active='0' AND em_status='Working'");
        $stmt_dob_done->bind_param("ss",$site_id,$client_id);
        $stmt_dob_done->execute();
        $stmt_dob_done->store_result();
        $stmt_dob_done->bind_result($dob_done);
        $stmt_dob_done->fetch();

        $stmt_dob_not = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND (dob IS NULL OR dob='') AND active='0' AND em_status='Working'");
        $stmt_dob_not->bind_param("ss",$site_id,$client_id);
        $stmt_dob_not->execute();
        $stmt_dob_not->store_result();
        $stmt_dob_not->bind_result($dob_not);
        $stmt_dob_not->fetch();

           // DOJ
        $stmt_doj_done = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND date_of_joining IS NOT NULL AND date_of_joining!='' AND active='0' AND em_status='Working'");
        $stmt_doj_done->bind_param("ss",$site_id,$client_id);
        $stmt_doj_done->execute();
        $stmt_doj_done->store_result();
        $stmt_doj_done->bind_result($doj_done);
        $stmt_doj_done->fetch();

        $stmt_doj_not = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND (date_of_joining IS NULL OR date_of_joining='') AND active='0' AND em_status='Working'");
        $stmt_doj_not->bind_param("ss",$site_id,$client_id);
        $stmt_doj_not->execute();
        $stmt_doj_not->store_result();
        $stmt_doj_not->bind_result($doj_not);
        $stmt_doj_not->fetch();

          // BANK
        $stmt_bank_done = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND account_no IS NOT NULL AND account_no!='' AND active='0' AND em_status='Working'");
        $stmt_bank_done->bind_param("ss",$site_id,$client_id);
        $stmt_bank_done->execute();
        $stmt_bank_done->store_result();
        $stmt_bank_done->bind_result($bank_done);
        $stmt_bank_done->fetch();

        $stmt_bank_not = $conn->prepare("SELECT COUNT(DISTINCT employee_code) FROM employee WHERE site_id=? AND client_id=? AND (account_no IS NULL OR account_no='') AND active='0' AND em_status='Working'");
        $stmt_bank_not->bind_param("ss",$site_id,$client_id);
        $stmt_bank_not->execute();
        $stmt_bank_not->store_result();
        $stmt_bank_not->bind_result($bank_not);
        $stmt_bank_not->fetch();

        $user = array(  
            'aadhar_done'=>$aadhar_done,
            'aadhar_not'=>$aadhar_not,
            'pancard_done'=>$pancard_done,
            'pancard_not'=>$pancard_not,
            'passport_done'=>$passport_done,
            'passport_not'=>$passport_not,
            'uan_done'=>$uan_done,
            'uan_not'=>$uan_not,
            'esis_done'=>$esis_done,
            'esis_not'=>$esis_not,
            'pf_done'=>$pf_done,
            'pf_not'=>$pf_not,
            'dob_done'=>$dob_done,
            'dob_not'=>$dob_not,
            'doj_done'=>$doj_done,
            'doj_not'=>$doj_not,
            'bank_done'=>$bank_done,
            'bank_not'=>$bank_not
        );  

        $response['error'] = false;   
        $response['message'] = 'KYC summary counts fetched successfully!!';   
        $response['user'] = $user;   

    } else {
        $response['error'] = true;   
        $response['message'] = 'Required parameters are not available';  
    }
    break;


    //---------------------------------------------------------------------------------------
//     case 'getSupervisorVoucherCountForDashboard':

//     if(isTheseParametersAvailable(array('created_by'))){  
       
// $created_by = $_POST['created_by'];
// $from_date = $_POST['from_date'];  
// $to_date = $_POST['to_date'];
// $site_id = $_POST['site_id'];
// $client_id = $_POST['client_id'];
//  $role = strtolower(trim($_POST['role']));

// if($role == 'employee')
// {
//  $stmt_get_data = $conn->prepare("SELECT COUNT(DISTINCT voucher_no),SUM(amount),sum(commission_paid) from emp_advance WHERE created_by = ? and DATE(adv_date) >= ? and DATE(adv_date) <= ? and active = '0'");
//  $stmt_get_data->bind_param("sss",$created_by,$from_date,$to_date); 

//  $stmt_status = $conn->prepare("
//     SELECT 
//         COUNT(DISTINCT CASE WHEN status = 'Pending'   THEN voucher_no END) AS pending,
//         COUNT(DISTINCT CASE WHEN status = 'Approved'  THEN voucher_no END) AS approved,
//         COUNT(DISTINCT CASE WHEN status = 'Cancelled' THEN voucher_no END) AS cancelled,
//         COUNT(DISTINCT CASE WHEN status = 'Rejected'  THEN voucher_no END) AS rejected
//     FROM emp_advance
//     WHERE created_by = ?
//       AND DATE(adv_date) >= ?
//       AND DATE(adv_date) <= ?
//       AND active = '0'
// ");
// $stmt_status->bind_param("sss", $created_by, $from_date, $to_date);

// }
// else
// {
//      $stmt_get_data = $conn->prepare("SELECT COUNT(DISTINCT voucher_no),SUM(amount),sum(commission_paid) from emp_advance WHERE site_id = ? and client_id = ? and DATE(adv_date) >= ? and DATE(adv_date) <= ? and active = '0'");
//  $stmt_get_data->bind_param("ssss",$site_id,$client_id,$from_date,$to_date); 

//  $stmt_status = $conn->prepare("
//     SELECT 
//         COUNT(DISTINCT CASE WHEN status = 'Pending'   THEN voucher_no END) AS pending,
//         COUNT(DISTINCT CASE WHEN status = 'Approved'  THEN voucher_no END) AS approved,
//         COUNT(DISTINCT CASE WHEN status = 'Cancelled' THEN voucher_no END) AS cancelled,
//         COUNT(DISTINCT CASE WHEN status = 'Rejected'  THEN voucher_no END) AS rejected
//     FROM emp_advance
//     WHERE site_id = ?
//       AND client_id = ?
//       AND DATE(adv_date) >= ?
//       AND DATE(adv_date) <= ?
//       AND active = '0'
// ");
// $stmt_status->bind_param("ssss", $site_id, $client_id, $from_date, $to_date);

// }

//  $stmt_get_data->execute();
//  $stmt_get_data->store_result();

//  $stmt_status->execute();
//  $stmt_status->store_result();
 

//   if ($stmt_get_data->num_rows > 0) {

//   $stmt_get_data->bind_result($total,$sum,$paid);
//    $stmt_get_data->fetch();

//                  $user = array(  
//                 'total'=>$total, 
//                 'sum'=>$sum,
//                 'paid'=>$paid
//             );  

//             $response['error'] = false;   
//             $response['message'] = 'Voucher counts successful!!';   
//             $response['user'] = $user;   
//         }
//         else
//         {
//             $response['error'] = true;   
//             $response['message'] = 'Vouchers not found!!';    
//         }
// }
//     else
//     {
//         $response['error'] = true;   
//         $response['message'] = 'required parameters are not available';  
//     }
//     break; 

    case 'getSupervisorVoucherCountForDashboard':

    if (isTheseParametersAvailable(array('created_by'))) {  
       
        $created_by = $_POST['created_by'];
        $from_date  = $_POST['from_date'];  
        $to_date    = $_POST['to_date'];
        $site_id    = $_POST['site_id'];
        $client_id  = $_POST['client_id'];
        $role       = strtolower(trim($_POST['role']));

        // MAIN TOTAL / SUM / PAID
        if (strcasecmp($role, 'employee') == 0) {

            $stmt_get_data = $conn->prepare("SELECT COUNT(DISTINCT voucher_no), SUM(amount), SUM(CASE WHEN utr_no IS NOT NULL AND utr_no != '' AND utr_no != 'null' THEN paid_amt ELSE 0 END) FROM emp_advance WHERE created_by = ? AND DATE(adv_date) >= ? AND DATE(adv_date) <= ? AND active = '0'");

            $stmt_get_data->bind_param("sss", $created_by, $from_date, $to_date);

        } 
     else if (strcasecmp($role, 'admin') == 0) {

            $stmt_get_data = $conn->prepare("
                SELECT 
                    COUNT(DISTINCT voucher_no),
                    SUM(amount),
                    SUM(CASE WHEN utr_no IS NOT NULL AND utr_no != '' AND utr_no != 'null' THEN paid_amt ELSE 0 END) 
                FROM emp_advance 
                WHERE DATE(adv_date) >= ? 
                  AND DATE(adv_date) <= ? 
                  AND active = '0'
            ");
            $stmt_get_data->bind_param("ss",$from_date, $to_date);

        } 
        else {

            $stmt_get_data = $conn->prepare("
                SELECT 
                    COUNT(DISTINCT voucher_no),
                    SUM(amount),
                    SUM(CASE WHEN utr_no IS NOT NULL AND utr_no != '' AND utr_no != 'null' THEN paid_amt ELSE 0 END)
                FROM emp_advance 
                WHERE site_id = ? 
                  AND client_id = ? 
                  AND DATE(adv_date) >= ? 
                  AND DATE(adv_date) <= ? 
                  AND active = '0'
            ");
            $stmt_get_data->bind_param("ssss", $site_id, $client_id, $from_date, $to_date);

        }

        // STATUS COUNTS WITH YOUR NEW UTR LOGIC
      if (strcasecmp($role, 'employee') == 0) {
            $stmt_status = $conn->prepare("
                SELECT 
                    COUNT(DISTINCT CASE 
                        WHEN status = 'Pending' THEN voucher_no 
                    END) AS pending,

                    COUNT(DISTINCT CASE 
                        WHEN status = 'Approved' AND (utr_no IS NULL OR utr_no = '') 
                        THEN voucher_no 
                    END) AS approved,

                    COUNT(DISTINCT CASE 
                        WHEN status = 'Approved' AND (utr_no IS NOT NULL AND utr_no != '') 
                        THEN voucher_no 
                    END) AS paid,

                    COUNT(DISTINCT CASE 
                        WHEN status = 'Cancelled' THEN voucher_no 
                    END) AS cancelled,

                    COUNT(DISTINCT CASE 
                        WHEN status = 'Rejected' THEN voucher_no 
                    END) AS rejected

                FROM emp_advance
                WHERE created_by = ?
                  AND DATE(adv_date) >= ?
                  AND DATE(adv_date) <= ?
                  AND active = '0'
            ");
            $stmt_status->bind_param("sss", $created_by, $from_date, $to_date);

        } 

        else if (strcasecmp($role, 'admin') == 0) {
            $stmt_status = $conn->prepare("
                SELECT 
                    COUNT(DISTINCT CASE 
                        WHEN status = 'Pending' THEN voucher_no 
                    END) AS pending,

                    COUNT(DISTINCT CASE 
                        WHEN status = 'Approved' AND (utr_no IS NULL OR utr_no = '') 
                        THEN voucher_no 
                    END) AS approved,

                    COUNT(DISTINCT CASE 
                        WHEN status = 'Approved' AND (utr_no IS NOT NULL AND utr_no != '') 
                        THEN voucher_no 
                    END) AS paid,

                    COUNT(DISTINCT CASE 
                        WHEN status = 'Cancelled' THEN voucher_no 
                    END) AS cancelled,

                    COUNT(DISTINCT CASE 
                        WHEN status = 'Rejected' THEN voucher_no 
                    END) AS rejected

                FROM emp_advance
                WHERE
                   DATE(adv_date) >= ?
                  AND DATE(adv_date) <= ?
                  AND active = '0'
            ");
            $stmt_status->bind_param("ss", $from_date, $to_date);

        }
        else {

            $stmt_status = $conn->prepare("
                SELECT 
                    COUNT(DISTINCT CASE 
                        WHEN status = 'Pending' THEN voucher_no 
                    END) AS pending,

                    COUNT(DISTINCT CASE 
                        WHEN status = 'Approved' AND (utr_no IS NULL OR utr_no = '') 
                        THEN voucher_no 
                    END) AS approved,

                    COUNT(DISTINCT CASE 
                        WHEN status = 'Approved' AND (utr_no IS NOT NULL AND utr_no != '') 
                        THEN voucher_no 
                    END) AS paid,

                    COUNT(DISTINCT CASE 
                        WHEN status = 'Cancelled' THEN voucher_no 
                    END) AS cancelled,

                    COUNT(DISTINCT CASE 
                        WHEN status = 'Rejected' THEN voucher_no 
                    END) AS rejected

                FROM emp_advance
                WHERE site_id = ?
                  AND client_id = ?
                  AND DATE(adv_date) >= ?
                  AND DATE(adv_date) <= ?
                  AND active = '0'
            ");
            $stmt_status->bind_param("ssss", $site_id, $client_id, $from_date, $to_date);

        }

        // Execute both queries
        $stmt_get_data->execute();
        $stmt_get_data->store_result();

        $stmt_status->execute();
        $stmt_status->store_result();

        if ($stmt_get_data->num_rows > 0) {

            // Fetch main totals
            $stmt_get_data->bind_result($total, $sum, $paid_amount);
            $stmt_get_data->fetch();

            // Fetch status counts
            $stmt_status->bind_result($pending, $approved, $paid, $cancelled, $rejected);
            $stmt_status->fetch();

            // Final Response
            $user = array(
                'total'     => $total,
                'sum'       => $sum,
                'paid'      => $paid_amount,
                'pending'   => (int)$pending,
                'approved'  => (int)$approved,
                'paid_count'=> (int)$paid,
                'cancelled' => (int)$cancelled,
                'rejected'  => (int)$rejected
            );

            $response['error'] = false;   
            $response['message'] = 'Voucher counts successful!!';   
            $response['user'] = $user;   

        } else {

            $response['error'] = true;   
            $response['message'] = 'Vouchers not found!!';    
        }

    } else {

        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break;

//-----------------------------------------------------------------------------------------------------------

case 'addDeviceToken':  

if(isTheseParametersAvailable(array('token','id'))){ 
   
    $id = $_POST['id'];    
    $token = $_POST['token']; 

    $stmt = $conn->prepare("UPDATE users SET device_token=? where id = ? ");
    $stmt->bind_param("ss", $token,$id);  
    $stmt->execute();  

    $stmt->close();   

    $response['error'] = false;   
    $response['message'] = 'Token updated successfully';   

}  

else
{  
    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';   
}  
break; 

    //-------------------------------------------------------------------------------------------------------------------

 case 'getSupervisorNetDaysCountForDashboard':

    if (isTheseParametersAvailable(array('role','from_date', 'to_date'))) {

        $role = strtolower(trim($_POST['role']));
        $emp_code = $_POST['emp_code'];
        $from_date = $_POST['from_date'];
        $to_date   = $_POST['to_date'];
        $att_type  = "Both";

    
        if ($role == 'employee') {
            $filter_column = "emp_code";
            $filter_value  = $_POST['emp_code'];
        } else {
            $filter_column = "site_id";
            $filter_value  = $_POST['site_id'];
        }

        // --- Extract date parts ---
        $from_year  = date('Y', strtotime($from_date));
        $from_month = date('n', strtotime($from_date));
        $from_day   = date('j', strtotime($from_date));
        $to_year    = date('Y', strtotime($to_date));
        $to_month   = date('n', strtotime($to_date));
        $to_day     = date('j', strtotime($to_date));

        // 👇 Define reusable WHERE clause dynamically
        $where_clause = "
            $filter_column = ?
            AND att_type = ?
            AND (at_day >= ? AND at_month >= ? AND at_year >= ?)
            AND (at_day <= ? AND at_month <= ? AND at_year <= ?)
            AND active = '0'
        ";

        // --- Query 1: Total overtime hours ---
        $stmt_overtime_hours = $conn->prepare("
            SELECT SUM(CAST(REPLACE(status, 'P', '') AS DECIMAL(10,2))) AS total_overtime_hours
            FROM attendance
            WHERE $where_clause
              AND status NOT IN ('P', 'W', 'PP')
        ");
        $stmt_overtime_hours->bind_param(
            'ssiiiiii',
            $filter_value, $att_type,
            $from_day, $from_month, $from_year,
            $to_day, $to_month, $to_year
        );
        $stmt_overtime_hours->execute();
        $stmt_overtime_hours->bind_result($total_overtime_hours);
        $stmt_overtime_hours->fetch();
        $stmt_overtime_hours->close();

        if (!$total_overtime_hours) $total_overtime_hours = 0;

        // echo 'total ot hours - '.$total_overtime_hours;

        // --- Query 2: Total overtime days ---
        $stmt_overtime_days = $conn->prepare("
            SELECT status
            FROM attendance
            WHERE $where_clause
              AND status LIKE '%PP%'
        ");
        $stmt_overtime_days->bind_param(
            'ssiiiiii',
            $filter_value, $att_type,
            $from_day, $from_month, $from_year,
            $to_day, $to_month, $to_year
        );
        $stmt_overtime_days->execute();
        $result = $stmt_overtime_days->get_result();

        $total_overtime_days = 0;
        while ($row = $result->fetch_assoc()) {
            $total_overtime_days += (float)ltrim($row['status'], 'P');
        }
        $stmt_overtime_days->close();

        // --- Convert hours to days ---
        $standard_work_hours = 8;
        $overtime_hours_in_days = $total_overtime_hours / $standard_work_hours;
        $total_overtime_days_combined = $total_overtime_days + $overtime_hours_in_days;

        // --- Query 3: Half days ---
        $stmt_half_days = $conn->prepare("
            SELECT COUNT(DISTINCT emp_code) AS total_half_days
            FROM attendance
            WHERE $where_clause
              AND status LIKE '%HF%'
        ");
        $stmt_half_days->bind_param(
            'ssiiiiii',
            $filter_value, $att_type,
            $from_day, $from_month, $from_year,
            $to_day, $to_month, $to_year
        );
        $stmt_half_days->execute();
        $stmt_half_days->bind_result($total_half_days);
        $stmt_half_days->fetch();
        $stmt_half_days->close();
        if (!$total_half_days) $total_half_days = 0;

        // --- Query 4: Week offs ---
        $stmt_week_off = $conn->prepare("
            SELECT COUNT(DISTINCT emp_code) AS total_week_off
            FROM attendance
            WHERE $where_clause
              AND status IN ('W', 'WP')
        ");
        $stmt_week_off->bind_param(
            'ssiiiiii',
            $filter_value, $att_type,
            $from_day, $from_month, $from_year,
            $to_day, $to_month, $to_year
        );
        $stmt_week_off->execute();
        $stmt_week_off->bind_result($total_week_off);
        $stmt_week_off->fetch();
        $stmt_week_off->close();
        if (!$total_week_off) $total_week_off = 0;

        // --- Query 5: Present days ---
        $stmt_present_days = $conn->prepare("
            SELECT COUNT(DISTINCT emp_code) AS total_present_days
            FROM attendance
            WHERE $where_clause
              AND (status LIKE '%P%' OR status LIKE '%D%' OR status LIKE '%N%')
        ");
        $stmt_present_days->bind_param(
            'ssiiiiii',
            $filter_value, $att_type,
            $from_day, $from_month, $from_year,
            $to_day, $to_month, $to_year
        );
        $stmt_present_days->execute();
        $stmt_present_days->bind_result($total_present_days);
        $stmt_present_days->fetch();
        $stmt_present_days->close();
        if (!$total_present_days) $total_present_days = 0;

        // --- Calculate totals ---
        $net_days = $total_present_days + ($total_half_days / 2);
        $total_days = $net_days + $total_week_off + $total_overtime_days_combined;

        $response['error'] = false;
        $response['message'] = 'Days summary fetched successfully!';
        $response['user'] = array(
            'ot_hours' => $total_overtime_hours,
            'ot_days'  => $total_overtime_days_combined,
            'total_half_days' => $total_half_days,
            'week_off' => $total_week_off,
            'total_present_days' => $total_present_days,
            'net_days' => $net_days,
            'total' => $total_days
        );
    } else {
        $response['error'] = true;
        $response['message'] = 'Required parameters are not available';
    }

    break;


//---------------------------------------------------------------------------------------------------------------
    case 'updateSupervisorAttendanceStatus': 
      if(isTheseParametersAvailable(array('status'))){   
      
  $id = $_POST['id']; 
  $status = $_POST['status'];

            $stmt = $conn->prepare("UPDATE attendance SET status = ? where id = ?");  
            $stmt->bind_param("ss",$status,$id);  
            $stmt->execute();
            $stmt->close();

                $response['error'] = false;   
                $response['message'] = 'Attendance status updated successfully!!';   
    }  
    else{  
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';   
    }  
    break; 
//--------------------------------------------------------------------------------------------------
    case 'getAllGangs':  

$stmt = $conn->prepare("SELECT id,gang_master FROM gang_master WHERE active='0'");  
//$stmt->bind_param("s",$id);    
$stmt->execute();    
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$gang);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['id'] = $id; 
     $temp['gang'] = $gang;
     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Gangs Fetch successfull!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'No Gangs!!';  
}  
//}  
//}  
break;  

// //--------------------------------------------------------------------------------------------------  
case 'getAllBanks':  

$stmt = $conn->prepare("SELECT id,ac_no,bank_name FROM company_bank_detail WHERE active='0'");  
//$stmt->bind_param("s",$id);    
$stmt->execute();    
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$acno,$bank);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['id'] = $id; 
     $temp['bank'] = $acno." ".$bank;
     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Banks Fetch successfull!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'No Banks!!';  
}  
//}  
//}  
break;  
// //--------------------------------------------------------------------------------------------------  
// case 'addVoucher':

//     if(isTheseParametersAvailable(array('client_id'))){  
//         date_default_timezone_set('Asia/Kolkata');
//         $client_id = $_POST['client_id']; 
//         $site_id = $_POST['site_id'];
//         $client_name = $_POST['client_name']; 
//         $site_name = $_POST['site_name']; 
//         $gang = $_POST['gang']; 
//         $mode = $_POST['mode']; 
//         $adv_date = $_POST['adv_date'];
//         $payment_detail = "Voucher";
//         $employees = json_decode($_POST['employees'], true);
//         $created_by = $_POST['created_by'];
//         $created_on = date('Y-m-d H:i:s');
//         $bank = $_POST['bank']; 
//         $ifsc = $_POST['ifsc']; 
//         $account_no = $_POST['account_no']; 
//         $branch = $_POST['branch']; 


//         //proof images
//        $proofCount = $_POST['proofCount'];


//         $part1 = $_POST['part1']; 
//         $amt1 = $_POST['amt1']; 

//             $stmt_last_id = $conn->prepare("SELECT max(voucher_no) lastid from emp_advance");  
//                          $stmt_last_id->execute();
//                          $stmt_last_id->store_result(); 

//                          if($stmt_last_id->num_rows > 0){  
//                          $stmt_last_id->bind_result($lastid);  
//                          $stmt_last_id->fetch();
//                          $new_id = $lastid + 1; 
//                          }
//                          else
//                          {
//                           $new_id = "1"; 
//                          } 


//                      $stmt_check_in = $conn->prepare("INSERT INTO emp_advance (client_id,site_id,client_name,site_name,mode,adv_date,gang_name,payment_detail,voucher_no,particular,amount,created_by,created_on) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); 

//                      $stmt_check_in->bind_param("sssssssssssss",$client_id,$site_id,$client_name,$site_name,$mode,$adv_date,$gang,$payment_detail,$new_id,$part1,$amt1,$created_by,$created_on);

//                      if($stmt_check_in->execute())
//                      {

//                           //proof images
//                          if($proofCount>0)
//                          {

//                           for ($i=0; $i < $proofCount; $i++)
//                             {

//                                 //echo $proofCount;

//                               $counter = $i + 1; 
//                               $file_path = "emp_docs/".$client_name."_voucher_".$new_id."_".$counter.".jpg";
//                               file_put_contents($file_path, base64_decode($_POST['proof'.$counter])); 
//                               $img_name = $client_name."_voucher_".$new_id."_".$counter.".jpg";
//                               //echo $new_id;
//                               if($i==0)
//                               {
//                                 $result3 = $conn->prepare("UPDATE emp_advance SET proof_images= '$img_name' WHERE voucher_no = '$new_id'");
//                               $result3->execute();
//                               }
//                               else
//                               {
//                                 $result3 = $conn->prepare("UPDATE emp_advance SET proof_images = CONCAT(proof_images,',$img_name') WHERE voucher_no = '$new_id'");
//                               $result3->execute();
//                               }  
//                             } 

//                         }


//                       $response['error'] = false;   
//                       $response['message'] = 'Voucher added successfully!!'; 
//                      }  
//                       else{
//                        $response['error'] = true;   
//                        $response['message'] = 'Some problem occured!!';   
//                       } 
//                   //}

//         //}    
                
// }
//     else
//     {
//         $response['error'] = true;   
//         $response['message'] = 'required parameters are not available';  
//     }
//     break; 



case 'addVoucher':

if(isTheseParametersAvailable(array('client_id'))){

    date_default_timezone_set('Asia/Kolkata');

    $client_id = $_POST['client_id']; 
    $site_id = $_POST['site_id'];
    $vendor_id = $_POST['vendor_id'];
    $vendor_name = 'NA';
    $original_amount = $_POST['original_amount'];
    // $client_name = $_POST['client_name']; 
    // $site_name = $_POST['site_name']; 
    $gang = $_POST['gang']; 
    $mode = $_POST['mode']; 
     $type = $_POST['type']; 
    $adv_date = $_POST['adv_date'];
    $payment_detail = "Voucher";
    $employees = json_decode($_POST['employees'], true);
    $created_by = $_POST['created_by'];
    $created_on = date('Y-m-d H:i:s');

    $bank = $_POST['bank']; 
    $ifsc = $_POST['ifsc']; 
    $account_no = $_POST['account_no']; 
    $branch = $_POST['branch']; 
    $beneficiary_name = $_POST['beneficiary_name']; 

    // proof images
    $proofCount = $_POST['proofCount'];

    $part1 = $_POST['part1'];
    //$amt1 = $_POST['amt1'];

    $company_id = '';
    $company_name = '';

    // ====== GET CLIENT NAME ======
$client_name = '';
$stmt_client = $conn->prepare("SELECT DISTINCT company_name FROM new_client WHERE id = ? AND active='0'");
$stmt_client->bind_param("s", $client_id);
$stmt_client->execute();
$stmt_client->store_result();
$stmt_client->bind_result($fetched_client_name);
if($stmt_client->num_rows > 0) {
    $stmt_client->fetch();
    $client_name = $fetched_client_name;
}
$stmt_client->close();

// ====== GET SITE NAME ======
$site_name = '';
$stmt_site = $conn->prepare("SELECT DISTINCT site_name FROM client_rates WHERE id=? AND active='0'");
$stmt_site->bind_param("s", $site_id);
$stmt_site->execute();
$stmt_site->store_result();
$stmt_site->bind_result($fetched_site_name);
if($stmt_site->num_rows > 0) {
    $stmt_site->fetch();
    $site_name = $fetched_site_name;
}
$stmt_site->close();

    //get vendor name
 if (!empty($vendor_id) && $vendor_id !== "null") {
        $stmt_vendor = $conn->prepare("SELECT vendor_name FROM vendors WHERE id = ? AND active = '0'");
        $stmt_vendor->bind_param("s", $vendor_id);
        $stmt_vendor->execute();
        $stmt_vendor->store_result();
        $stmt_vendor->bind_result($fetched_vendor_name);

        if ($stmt_vendor->num_rows > 0) {
            $stmt_vendor->fetch();
            $vendor_name = $fetched_vendor_name;
        }

        $stmt_vendor->close();
    }

    // Get new voucher no
    $stmt_last_id = $conn->prepare("SELECT max(voucher_no) lastid FROM emp_advance");  
    $stmt_last_id->execute();
    $stmt_last_id->store_result(); 
    $stmt_last_id->bind_result($lastid);  
    $stmt_last_id->fetch();

    $new_id = ($lastid > 0) ? $lastid + 1 : 1;

    //Getting company id , name
        $stmt_client_id = $conn->prepare("SELECT billing_company_id FROM new_client WHERE id = ?");
        $stmt_client_id->bind_param("s", $client_id);
        $stmt_client_id->execute();
        $stmt_client_id->store_result();
        $stmt_client_id->bind_result($company_id); 
        $stmt_client_id->fetch();

        $stmt_client_details = $conn->prepare("SELECT company_name FROM companies WHERE id = ?");
        $stmt_client_details->bind_param("s", $company_id);
        $stmt_client_details->execute();
        $stmt_client_details->store_result();
        $stmt_client_details->bind_result($company_name); 
        $stmt_client_details->fetch();        


    // ========= INSERT QUERY (same as yours but extended) =========
    $stmt_insert = $conn->prepare("
        INSERT INTO emp_advance
        (client_id,site_id,vendor_id,vendor_name,client_name,site_name,mode,adv_date,gang_name,payment_detail,
         voucher_no,particular,amount,created_by,created_on,
         emp_id, emp_name, emp_code, bank_name, bank_ac_no, ifsc_code, bank_branch,beneficiary_name,type,company_id,company_name)
        VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
    ");

    // ========= RUN THIS INSERT FOR EACH EMPLOYEE =========
if (empty($employees) || $employees === "null" || $employees === null) {

    $emp_id = "";
    $emp_name = "";
    $emp_code = "";
    $emp_amt = "";

    $stmt_insert->bind_param("ssssssssssssssssssssssssss",
        $client_id, $site_id, $vendor_id,$vendor_name,$client_name, $site_name, $mode, $adv_date, $gang,
        $payment_detail, $new_id, $part1, $original_amount, $created_by, $created_on,
        $emp_id, $emp_name, $emp_code, $bank, $account_no, $ifsc, $branch, $beneficiary_name,$type,$company_id,$company_name
    );
    $stmt_insert->execute();

} else {

    foreach ($employees as $emp) {

        $emp_id = $emp['emp_id'] ?? "";
        $emp_name = $emp['emp_name'] ?? "";
        $emp_code = $emp['emp_code'] ?? "";
        $emp_amt = $emp['amt'] ?? "";

        $stmt_insert->bind_param("ssssssssssssssssssssssssss",
            $client_id, $site_id, $vendor_id,$vendor_name,$client_name, $site_name, $mode, $adv_date, $gang,
            $payment_detail, $new_id, $part1, $emp_amt, $created_by, $created_on,
            $emp_id, $emp_name, $emp_code, $bank, $account_no, $ifsc, $branch, $beneficiary_name,$type,$company_id,$company_name
        );
        $stmt_insert->execute();
    }
}


    //voucher log

    $activity = 'Voucher Created';

       $stmt_insert_log = $conn->prepare("
        INSERT INTO emp_advance_log
        (client_id,site_id,vendor_id,vendor_name,client_name,site_name,mode,adv_date,gang_name,payment_detail,
         voucher_no,particular,amount,created_by,created_on,bank_name, bank_ac_no, ifsc_code, bank_branch,beneficiary_name,activity,type,company_id,company_name,emp_code,emp_id,emp_name)
        VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

        $stmt_insert_log->bind_param(
            "sssssssssssssssssssssssssss",
            $client_id, $site_id, $vendor_id,$vendor_name,$client_name, $site_name, $mode, $adv_date, $gang, 
            $payment_detail, $new_id, $part1, $original_amount, $created_by, $created_on,
            $bank, $account_no, $ifsc, $branch, $beneficiary_name,$activity,$type,$company_id,$company_name,$emp_code,$emp_id,$emp_name
        );

        $stmt_insert_log->execute();

    // ========= PROOF IMAGES (unchanged) =========
    if($proofCount > 0){
        for ($i=0; $i < $proofCount; $i++) {
            $counter = $i + 1; 
            $file_path = "emp_docs/".$client_name."_voucher_".$new_id."_".$counter.".jpg";
            file_put_contents($file_path, base64_decode($_POST['proof'.$counter])); 
            $img_name = $client_name."_voucher_".$new_id."_".$counter.".jpg";

            if ($i == 0) {
                $conn->query("UPDATE emp_advance SET proof_images='$img_name' WHERE voucher_no='$new_id'");
            } else {
                $conn->query("UPDATE emp_advance SET proof_images=CONCAT(proof_images,',$img_name') WHERE voucher_no='$new_id'");
            }
        }
    }


try{
       //fcm notifications
       $stmt_approvers = $conn->prepare("
        SELECT u.device_token 
        FROM voucher_approvers va
        JOIN users u ON va.user_id = u.id
        WHERE va.active = '0' AND u.active = '0'
    ");
    $stmt_approvers->execute();
    $stmt_approvers->store_result();
    $stmt_approvers->bind_result($token);

    if ($stmt_approvers->num_rows > 0) {

        $client = new Client();
        $client->setAuthConfig(__DIR__ . '/hrms-notifications-a063b-2b8b0eb9b3b0.json');
        //$client->setAuthConfig(__DIR__ . '/hrmsnotify-569ac-5424f3d07fc5.json');
        $client->addScope('https://www.googleapis.com/auth/firebase.messaging');
        $accessToken = $client->fetchAccessTokenWithAssertion()["access_token"];

        $projectId = "hrms-notifications-a063b";
        //$projectId = "hrmsnotify-569ac";

        $url = "https://fcm.googleapis.com/v1/projects/{$projectId}/messages:send";

        $http = new GuzzleClient();

        $title = "Voucher Added!!";
        $body  = "Added by ".$beneficiary_name." with amount of ₹".$original_amount;

        while ($stmt_approvers->fetch()) {

            if (empty($token)) continue; // skip users without token

            $message = [
                "message" => [
                    "token" => $token,
                    "data" => [
                        "type"  => "voucher",
                        "title" => $title,
                        "body"  => $body
                    ],
                    "notification" => [
                        "title" => $title,
                        "body"  => $body
                    ]
                ]
            ];

            $response_new = $http->post($url, [
                "headers" => [
                    "Authorization" => "Bearer ".$accessToken,
                    "Content-Type"  => "application/json"
                ],
                "json" => $message
            ]);
        }
    }
}
 catch (Exception $e) {
    // Log error but don’t break the script
    error_log("FCM error: ".$e->getMessage());
}
 
  

    $response['error'] = false;
    $response['message'] = 'Voucher added successfully!!';
}
else {
    $response['error'] = true;
    $response['message'] = 'Required parameters are not available';
}

break;
//-------------------------------------------------------------------------------
case 'updateVoucher':

if (isTheseParametersAvailable(array('voucher_no'))) {

    date_default_timezone_set('Asia/Kolkata');

    // ========= BASIC DATA =========
    $voucher_no = $_POST['voucher_no'];
    $client_id  = $_POST['client_id'] ?? '';
    $original_amount = $_POST['original_amount'] ?? '';
    $mode = $_POST['mode'] ?? '';
    $type = $_POST['type'] ?? '';
    $adv_date = $_POST['adv_date'] ?? '';
    $part1 = $_POST['part1'] ?? '';
    $created_by = $_POST['created_by'] ?? '';

    $bank = $_POST['bank'] ?? '';
    $ifsc = $_POST['ifsc'] ?? '';
    $account_no = $_POST['account_no'] ?? '';
    $branch = $_POST['branch'] ?? '';
    $beneficiary_name = $_POST['beneficiary_name'] ?? '';

    $emp_id = $_POST['emp_id'] ?? '';
    $emp_name = $_POST['emp_name'] ?? '';
    $emp_code = $_POST['emp_code'] ?? '';

    // 🔥 TOTAL IMAGES SENT (OLD + NEW, ALL BASE64)
    $imageCount = $_POST['imageCount'] ?? 0;

    $updated_on = date('Y-m-d H:i:s');

    // ========= UPDATE emp_advance =========
    $stmt_update = $conn->prepare("
        UPDATE emp_advance SET
            mode = ?,
            adv_date = ?,
            particular = ?,
            amount = ?,
            bank_name = ?,
            bank_ac_no = ?,
            ifsc_code = ?,
            bank_branch = ?,
            beneficiary_name = ?,
            type = ?,
            modified_by = ?,
            modified_on = ?
        WHERE voucher_no = ?
    ");

    $stmt_update->bind_param(
        "sssssssssssss",
        $mode, $adv_date, $part1, $original_amount,
        $bank, $account_no, $ifsc, $branch,
        $beneficiary_name, $type,
        $created_by, $updated_on, $voucher_no
    );
    $stmt_update->execute();

    // ========= LOG =========
    $activity = 'Voucher Updated';
    $stmt_log = $conn->prepare("
        INSERT INTO emp_advance_log
        (voucher_no, client_id, mode, adv_date, particular, amount, created_by, created_on,
         bank_name, bank_ac_no, ifsc_code, bank_branch, beneficiary_name, activity,
         type, emp_code, emp_id, emp_name)
        VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
    ");

    $stmt_log->bind_param(
        "ssssssssssssssssss",
        $voucher_no, $client_id, $mode, $adv_date, $part1,
        $original_amount, $created_by, $updated_on,
        $bank, $account_no, $ifsc, $branch,
        $beneficiary_name, $activity, $type,
        $emp_code, $emp_id, $emp_name
    );
    $stmt_log->execute();

    // ========= HARD REPLACE ALL IMAGES =========
    if ($imageCount > 0) {

        // 1️⃣ Fetch & delete old images
        $res = $conn->query(
            "SELECT proof_images FROM emp_advance WHERE voucher_no='$voucher_no'"
        );
        if ($row = $res->fetch_assoc()) {
            $oldImages = explode(',', $row['proof_images'] ?? '');
            foreach ($oldImages as $img) {
                $path = "emp_docs/" . trim($img);
                if (!empty($img) && file_exists($path)) {
                    unlink($path);
                }
            }
        }

        // 2️⃣ Save ALL incoming images fresh
        $savedImages = [];
        $counter = 1;

        for ($i = 1; $i <= $imageCount; $i++) {

            if (empty($_POST['image'.$i])) continue;

            $imgName = "voucher_{$voucher_no}_{$counter}.jpg";
            $filePath = "emp_docs/" . $imgName;

            file_put_contents(
                $filePath,
                base64_decode($_POST['image'.$i])
            );

            $savedImages[] = $imgName;
            $counter++;
        }

        // 3️⃣ Update DB with fresh list
        $conn->query(
            "UPDATE emp_advance
             SET proof_images='".implode(',', $savedImages)."'
             WHERE voucher_no='$voucher_no'"
        );
    }

    $response['error'] = false;
    $response['message'] = 'Voucher updated successfully!!';

} else {
    $response['error'] = true;
    $response['message'] = 'Required parameters are not available';
}

break;


//---------------------------------------------------------------------------- 
// case 'updateVoucher':

//     if(isTheseParametersAvailable(array('client_id'))){  
//         date_default_timezone_set('Asia/Kolkata');
//         $client_id = $_POST['client_id']; 
//         $site_id = $_POST['site_id'];
//         $client_name = $_POST['client_name']; 
//         $site_name = $_POST['site_name'];
//         $onlineImgCount = $_POST['onlineImgCount']; 
//         // $emp_code = $_POST['emp_code']; 
//         // $emp_id = $_POST['emp_id'];
//         // $emp_name = $_POST['emp_name']; 
//         $gang = $_POST['gang']; 
//        // $type = $_POST['type']; 
//         $adv_date = $_POST['adv_date']; 
//         $mode = $_POST['mode']; 
//         $modified_by = $_POST['modified_by'];
//         $modified_on = date('Y-m-d H:i:s');
//         //$count = $_POST['count'];
//         $voucher_no = $_POST['voucher_no'];
//         $part1 = $_POST['part1'];
//         $amt1 = $_POST['amt1'];

//        //proof images
//        $proofCount = $_POST['proofCount'];

//        //  for($i=0;$i<$count;$i++)
//        //  {
//        // if(($_POST['part'.$i+1] !='') && ($_POST['amt'.$i+1]!='')){

//          // $stmt_check_in = $conn->prepare("UPDATE emp_advance SET client_id=?,site_id=?,client_name=?,site_name=?,emp_code=?,emp_id=?,emp_name=?,type=?,mode=?,adv_date=?,gang_name=?,particular=?,amount=?,modified_on=?,modified_by=? where voucher_no =? and id = ?"); 

//          //             $stmt_check_in->bind_param("sssssssssssssssss",$client_id,$site_id,$client_name,$site_name,$emp_code,$emp_id,$emp_name,$type,$mode,$adv_date,$gang,$_POST['part'.$i+1],$_POST['amt'.$i+1],$modified_on,$modified_by,$voucher_no,$_POST['voucher'.$i]);


//                      $stmt_check_in = $conn->prepare("UPDATE emp_advance SET client_id=?,site_id=?,client_name=?,site_name=?,mode=?,adv_date=?,gang_name=?,particular=?,amount=?,modified_on=?,modified_by=? where voucher_no =?"); 

//                      $stmt_check_in->bind_param("ssssssssssss",$client_id,$site_id,$client_name,$site_name,$mode,$adv_date,$gang,$part1,$amt1,$modified_on,$modified_by,$voucher_no);

//                      if($stmt_check_in->execute())
//                      {

//                                 //proof images
//                          if($proofCount>0)
//                          {

//                         if($onlineImgCount==0)
//                         {
//                             $counter = 1;
//                         }   
//                         else
//                         {
//                             $counter = $onlineImgCount + 1;
//                         }    


//                           for ($i=0; $i < $proofCount; $i++)
//                             {

//                               $newcount = $i + 1;   
//                               $file_path = "emp_docs/".$client_name."_voucher_".$voucher_no."_".$counter.".jpg";
//                               file_put_contents($file_path, base64_decode($_POST['proof'.$newcount])); 
//                               $img_name = $client_name."_voucher_".$voucher_no."_".$counter.".jpg";
//                               $counter = $counter + 1;
//                               //echo $new_id;
//                               if($i==0)
//                               {

//                                  $nothing = "";

//                                   if($onlineImgCount<=0)
//                                    {
//                                         //clear all image names only for first time
//                                  $result1 = $conn->prepare("UPDATE emp_advance SET proof_images = ''  WHERE voucher_no = '$voucher_no'");
//                                  //$result1->bind_param('s',$);
//                                  $result1->execute();

//                                   $result2 = $conn->prepare("UPDATE emp_advance SET proof_images= '$img_name' WHERE voucher_no = '$voucher_no'");
//                                 $result2->execute();

//                                    } 
//                                    else
//                                    {
//                                         $result2 = $conn->prepare("UPDATE emp_advance SET proof_images = CONCAT(proof_images,',$img_name') WHERE voucher_no = '$voucher_no'");
//                                        $result2->execute();
//                                    } 
//                               }
//                               else
//                               {
//                                 $result2 = $conn->prepare("UPDATE emp_advance SET proof_images = CONCAT(proof_images,',$img_name') WHERE voucher_no = '$voucher_no'");
//                               $result2->execute();
//                               }  
//                             } 
//                         }
//                         //case for edit - if no new images uploaded , then just reinsert names of online images already present in voucher.
//                         //it can be optional else loop , as nothing is present , then nothing is needed to handle it.
//                         else if($proofCount<=0 && $onlineImgCount!=0)
//                         {

//                             $counter = $onlineImgCount + 1;
                     
//                             for ($i=0; $i < $onlineImgCount; $i++){

//                                 //$counter = $onlineImgCount + 1; 
//                                 $img_name = $client_name."_voucher_".$voucher_no."_".$counter.".jpg";
//                                 $counter = $counter + 1;

//                             if($i==0)
//                               {

//                                  $nothing = "";
//                                 //clear all image names only for first time
//                                  $result1 = $conn->prepare("UPDATE emp_advance SET proof_images = ''  WHERE voucher_no = '$voucher_no'");
//                                  //$result1->bind_param('s',$);
//                                  $result1->execute();
                                
//                                 $result2 = $conn->prepare("UPDATE emp_advance SET proof_images= '$img_name' WHERE voucher_no = '$voucher_no'");
//                                 $result2->execute();
//                               }
//                               else
//                               {
//                                 $result2 = $conn->prepare("UPDATE emp_advance SET proof_images = CONCAT(proof_images,',$img_name') WHERE voucher_no = '$voucher_no'");
//                               $result2->execute();
//                               }  
//                             }
//                         }    
//                         //case for edit - if no online images already present in voucher , as well as no new images uploaded.
//                         //it can be optional else loop , as nothing is present , then nothing is needed to handle it.
//                         else
//                         {
//                              $result3 = $conn->prepare("UPDATE emp_advance SET proof_images = '' WHERE voucher_no = '$voucher_no'");
//                                 // $result3->bind_param('s',"");
//                                  $result3->execute();
//                         }
                            

//                       $response['error'] = false;   
//                       $response['message'] = 'Voucher updated successfully!!'; 
//                      }  
//                       else{
//                        $response['error'] = true;   
//                        $response['message'] = 'Some problem occured!!';   
//                       } 
//                  // }

//         //}    
                
// }
//     else
//     {
//         $response['error'] = true;   
//         $response['message'] = 'required parameters are not available';  
//     }
//     break; 
//----------------------------------------------------------------------------   
// case 'getAllVouchers':  
  
// $created_by = $_POST['created_by'];
// $status = $_POST['status'];
// $from_date = $_POST['from_date'];  
// $to_date = $_POST['to_date'];
// $role = $_POST['role'];
// $client_id = $_POST['client_id'];
// $site_id = $_POST['site_id'];
//  $limit = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
//  $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0; 

//  if (strcasecmp($role, "employee") === 0) {
//    $stmt = $conn->prepare("SELECT id,adv_date,type,mode,status,vendor_comm,approved_by,rejected_by,emp_name,voucher_no,client_name,site_name,gang_name,client_id,site_id,emp_id,emp_code,particular,SUM(amount) from emp_advance WHERE created_by = ? and status = ? and DATE(adv_date) >= ? and DATE(adv_date) <= ? and active = '0' GROUP BY voucher_no LIMIT ?,?");
//    $stmt->bind_param("ssssss",$created_by,$status,$from_date,$to_date,$offset,$limit); 

//      $stmt_count = $conn->prepare("SELECT COUNT(DISTINCT voucher_no) from emp_advance WHERE created_by = ? and status = ? and DATE(adv_date) >= ? and DATE(adv_date) <= ? and active = '0'");
//    $stmt_count->bind_param("ssss",$created_by,$status,$from_date,$to_date); 

//     }
// else
// {
//  $stmt = $conn->prepare("SELECT id,adv_date,type,mode,status,vendor_comm,approved_by,rejected_by,emp_name,voucher_no,client_name,site_name,gang_name,client_id,site_id,emp_id,emp_code,particular,SUM(amount) from emp_advance WHERE status = ? and client_id = ? and site_id = ? and  DATE(adv_date) >= ? and DATE(adv_date) <= ? and active = '0' GROUP BY voucher_no LIMIT ?,?");
//                $stmt->bind_param("sssssss",$status,$client_id,$site_id,$from_date,$to_date,$offset,$limit);

//                $stmt_count = $conn->prepare("SELECT COUNT(DISTINCT voucher_no) from emp_advance WHERE status = ? and client_id = ? and site_id = ? and  DATE(adv_date) >= ? and DATE(adv_date) <= ? and active = '0'");
//                $stmt_count->bind_param("sssss",$status,$client_id,$site_id,$from_date,$to_date);  
// }

//             $stmt->execute();  
//             $stmt->store_result(); 

//             $stmt_count->execute();
//             $stmt_count->bind_result($total_rows);
//             $stmt_count->fetch();
//             $stmt_count->close(); 

// if($stmt->num_rows > 0){  
//     $stmt->bind_result($id,$date,$type,$mode,$status,$vendor,$approved_by,$rejected_by,$emp_name,$voucher_no,$client,$site,$gang,$client_id,$site_id,$emp_id,$emp_code,$particular,$amount);  

//     $banner_data = array();

//     while($stmt->fetch()){

//             $stmt_approved_by = $conn->prepare("SELECT fullname from users WHERE id = ?");
//             $stmt_approved_by->bind_param("s",$approved_by);    
//             $stmt_approved_by->execute();  
//             $stmt_approved_by->store_result(); 
//             $stmt_approved_by->bind_result($appr_by); 
//             $stmt_approved_by->fetch();

//             $stmt_rejected_by = $conn->prepare("SELECT fullname from users WHERE id = ?");
//             $stmt_rejected_by->bind_param("s",$rejected_by);    
//             $stmt_rejected_by->execute();  
//             $stmt_rejected_by->store_result(); 
//             $stmt_rejected_by->bind_result($rejec_by); 
//             $stmt_rejected_by->fetch();

//      $temp = array(); 
//      $temp['id'] = $id; 
//      $temp['date']=$date ;
//      $temp['type'] = $type;
//      $temp['mode'] = $mode;
//      $temp['status'] = $status;
//      $temp['vendor'] = $vendor;
//      $temp['approved_by'] = $appr_by;
//      $temp['rejected_by'] = $rejec_by;
//      $temp['emp_name'] = $emp_name;
//      $temp['voucher_no'] = $voucher_no;
//      $temp['client'] = $client;
//      $temp['site'] = $site;
//      $temp['gang'] = $gang;
//      $temp['client_id'] = $client_id;
//      $temp['site_id'] = $site_id;
//      $temp['emp_id'] = $emp_id;
//      $temp['emp_code'] = $emp_code;
//      $temp['particular'] = $particular;
//      $temp['amount'] = $amount;
//      array_push($banner_data, $temp);
//  }

//  $response['error'] = false;   
//  $response['message'] = 'Vouchers got successful!!';   
//  $response['user'] = $banner_data; 
//  $response['total_rows'] = $total_rows;   
// }  
// else{  
//     $response['error'] = true;   
//     $response['message'] = 'Vouchers not available!!'; 
//     $response['user'] = array();
// }  
// //}  
// //}  
// break;  

//     case 'getAllVouchers':  

// $created_by = $_POST['created_by'];
// $status = $_POST['status'];
// $from_date = $_POST['from_date'];  
// $to_date = $_POST['to_date'];
// $role = $_POST['role'];
// $client_id = $_POST['client_id'];
// $site_id = $_POST['site_id'];

// $limit = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
// $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0; 


// if ($status == "Approved") {
//     $status_condition = "status = 'Approved' AND (utr_no IS NULL OR utr_no = '')";
// } 
// elseif ($status == "Paid") {
//     $status_condition = "status = 'Approved' AND (utr_no IS NOT NULL AND utr_no != '')";
// } 
// else {
//     $status_condition = "status = '$status'";
// }


// if (strcasecmp($role, "employee") === 0) {

//     $query = "
//         SELECT id,adv_date,type,mode,status,vendor_comm,approved_by,rejected_by,
//         emp_name,voucher_no,client_name,site_name,gang_name,client_id,site_id,
//         emp_id,emp_code,particular,SUM(amount)
//         FROM emp_advance 
//         WHERE created_by = ? 
//         AND $status_condition
//         AND DATE(adv_date) >= ? 
//         AND DATE(adv_date) <= ? 
//         AND active = '0'
//         GROUP BY voucher_no 
//         LIMIT ?,?
//     ";

//     $stmt = $conn->prepare($query);
//     $stmt->bind_param("sssss", $created_by, $from_date, $to_date, $offset, $limit);

//     $countQuery = "
//         SELECT COUNT(DISTINCT voucher_no)
//         FROM emp_advance 
//         WHERE created_by = ? 
//         AND $status_condition
//         AND DATE(adv_date) >= ? 
//         AND DATE(adv_date) <= ? 
//         AND active = '0'
//     ";

//     $stmt_count = $conn->prepare($countQuery);
//     $stmt_count->bind_param("sss", $created_by, $from_date, $to_date);
// }

// // ---------------------------------------------

// else {

//     $query = "
//         SELECT id,adv_date,type,mode,status,vendor_comm,approved_by,rejected_by,
//         emp_name,voucher_no,client_name,site_name,gang_name,client_id,site_id,
//         emp_id,emp_code,particular,SUM(amount)
//         FROM emp_advance 
//         WHERE $status_condition
//         AND client_id = ? 
//         AND site_id = ?
//         AND DATE(adv_date) >= ? 
//         AND DATE(adv_date) <= ? 
//         AND active = '0'
//         GROUP BY voucher_no 
//         LIMIT ?,?
//     ";

//     $stmt = $conn->prepare($query);
//     $stmt->bind_param("ssssss", $client_id, $site_id, $from_date, $to_date, $offset, $limit);

//     $countQuery = "
//         SELECT COUNT(DISTINCT voucher_no)
//         FROM emp_advance 
//         WHERE $status_condition
//         AND client_id = ? 
//         AND site_id = ?
//         AND DATE(adv_date) >= ? 
//         AND DATE(adv_date) <= ? 
//         AND active = '0'
//     ";

//     $stmt_count = $conn->prepare($countQuery);
//     $stmt_count->bind_param("ssss", $client_id, $site_id, $from_date, $to_date);
// }

// $stmt->execute();  
// $stmt->store_result(); 

// $stmt_count->execute();
// $stmt_count->bind_result($total_rows);
// $stmt_count->fetch();
// $stmt_count->close(); 


// if ($stmt->num_rows > 0) {  

//     $stmt->bind_result(
//         $id,$date,$type,$mode,$status,$vendor,$approved_by,$rejected_by,
//         $emp_name,$voucher_no,$client,$site,$gang,$client_id,$site_id,
//         $emp_id,$emp_code,$particular,$amount
//     );  

//     $banner_data = array();

//     while($stmt->fetch()){

//         // Fetch approved_by name
//         $stmt_approved_by = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
//         $stmt_approved_by->bind_param("s", $approved_by);
//         $stmt_approved_by->execute();
//         $stmt_approved_by->store_result();
//         $stmt_approved_by->bind_result($appr_by); 
//         $stmt_approved_by->fetch();

//         // Fetch rejected_by name
//         $stmt_rejected_by = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
//         $stmt_rejected_by->bind_param("s", $rejected_by);
//         $stmt_rejected_by->execute();
//         $stmt_rejected_by->store_result();
//         $stmt_rejected_by->bind_result($rejec_by); 
//         $stmt_rejected_by->fetch();

//         // Push Data
//         $temp = array(); 
//         $temp['id'] = $id; 
//         $temp['date'] = $date;
//         $temp['type'] = $type;
//         $temp['mode'] = $mode;
//         $temp['status'] = $status;
//         $temp['vendor'] = $vendor;
//         $temp['approved_by'] = $appr_by;
//         $temp['rejected_by'] = $rejec_by;
//         $temp['emp_name'] = $emp_name;
//         $temp['voucher_no'] = $voucher_no;
//         $temp['client'] = $client;
//         $temp['site'] = $site;
//         $temp['gang'] = $gang;
//         $temp['client_id'] = $client_id;
//         $temp['site_id'] = $site_id;
//         $temp['emp_id'] = $emp_id;
//         $temp['emp_code'] = $emp_code;
//         $temp['particular'] = $particular;
//         $temp['amount'] = $amount;

//         array_push($banner_data, $temp);
//     }

//     $response['error'] = false;   
//     $response['message'] = 'Vouchers got successful!!';   
//     $response['user'] = $banner_data; 
//     $response['total_rows'] = $total_rows;   
// }  
// else {  
//     $response['error'] = true;   
//     $response['message'] = 'Vouchers not available!!'; 
//     $response['user'] = array();
// }

// break;

    case 'getAllVouchers':  

$created_by = $_POST['created_by'];
$status = $_POST['status'];
$from_date = $_POST['from_date'];  
$to_date = $_POST['to_date'];
$role = $_POST['role'];
$client_id = $_POST['client_id'];
$site_id = $_POST['site_id'];

$limit = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
$offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0; 


if ($status == "All" || $status == "" || $status == null) {
    $status_condition = "1=1"; // no status filter
}
elseif ($status == "Approved") {
    $status_condition = "status = 'Approved' AND (utr_no IS NULL OR utr_no = '')";
}
elseif ($status == "Paid") {
    $status_condition = "status = 'Approved' AND (utr_no IS NOT NULL AND utr_no != '')";
}
else {
    $status_condition = "status = '$status'";
}


if (strcasecmp($role, "employee") === 0) {

    $query = "
        SELECT id,adv_date,type,mode,vendor_comm,approved_by,rejected_by,
        emp_name,voucher_no,client_name,site_name,gang_name,client_id,site_id,
        emp_id,emp_code,particular,SUM(amount),created_by,created_on,approved_on,rejected_on,utr_updated_by,utr_updated_on,status,proof_images,bank_name, bank_ac_no, ifsc_code, bank_branch,beneficiary_name
        FROM emp_advance 
        WHERE created_by = ? 
        AND $status_condition
        AND DATE(adv_date) >= ? 
        AND DATE(adv_date) <= ? 
        AND active = '0'
        GROUP BY voucher_no 
        LIMIT ?,?
    ";

    $stmt = $conn->prepare($query);
    $stmt->bind_param("sssss", $created_by, $from_date, $to_date, $offset, $limit);

    $countQuery = "
        SELECT COUNT(DISTINCT voucher_no)
        FROM emp_advance 
        WHERE created_by = ? 
        AND $status_condition
        AND DATE(adv_date) >= ? 
        AND DATE(adv_date) <= ? 
        AND active = '0'
    ";

    $stmt_count = $conn->prepare($countQuery);
    $stmt_count->bind_param("sss", $created_by, $from_date, $to_date);
}
else if (strcasecmp($role, "admin") === 0 && $client_id == "" && $site_id == "") {
    $query = "
        SELECT id,adv_date,type,mode,vendor_comm,approved_by,rejected_by,
        emp_name,voucher_no,client_name,site_name,gang_name,client_id,site_id,
        emp_id,emp_code,particular,SUM(amount),created_by,created_on,approved_on,rejected_on,utr_updated_by,utr_updated_on,status,proof_images,bank_name, bank_ac_no, ifsc_code, bank_branch,beneficiary_name
        FROM emp_advance 
        WHERE $status_condition
        AND DATE(adv_date) >= ? 
        AND DATE(adv_date) <= ? 
        AND active = '0'
        GROUP BY voucher_no 
        LIMIT ?,?
    ";

    $stmt = $conn->prepare($query);
    $stmt->bind_param("ssss", $from_date, $to_date, $offset, $limit);

    $countQuery = "
        SELECT COUNT(DISTINCT voucher_no)
        FROM emp_advance 
        WHERE $status_condition
        AND DATE(adv_date) >= ? 
        AND DATE(adv_date) <= ? 
        AND active = '0'
    ";

    $stmt_count = $conn->prepare($countQuery);
    $stmt_count->bind_param("ss", $from_date, $to_date);
}
else {

    $query = "
        SELECT id,adv_date,type,mode,vendor_comm,approved_by,rejected_by,
        emp_name,voucher_no,client_name,site_name,gang_name,client_id,site_id,
        emp_id,emp_code,particular,SUM(amount),created_by,created_on,approved_on,rejected_on,utr_updated_by,utr_updated_on,status,proof_images,bank_name, bank_ac_no, ifsc_code, bank_branch,beneficiary_name
        FROM emp_advance 
        WHERE $status_condition
        AND client_id = ? 
        AND site_id = ?
        AND DATE(adv_date) >= ? 
        AND DATE(adv_date) <= ? 
        AND active = '0'
        GROUP BY voucher_no 
        LIMIT ?,?
    ";

    $stmt = $conn->prepare($query);
    $stmt->bind_param("ssssss", $client_id, $site_id, $from_date, $to_date, $offset, $limit);

    $countQuery = "
        SELECT COUNT(DISTINCT voucher_no)
        FROM emp_advance 
        WHERE $status_condition
        AND client_id = ? 
        AND site_id = ?
        AND DATE(adv_date) >= ? 
        AND DATE(adv_date) <= ? 
        AND active = '0'
    ";

    $stmt_count = $conn->prepare($countQuery);
    $stmt_count->bind_param("ssss", $client_id, $site_id, $from_date, $to_date);
}


$stmt->execute();  
$stmt->store_result(); 

$stmt_count->execute();
$stmt_count->bind_result($total_rows);
$stmt_count->fetch();
$stmt_count->close(); 


if ($stmt->num_rows > 0) {  

    $stmt->bind_result(
        $id,$date,$type,$mode,$vendor,$approved_by,$rejected_by,
        $emp_name,$voucher_no,$client,$site,$gang,$client_id,$site_id,
        $emp_id,$emp_code,$particular,$amount,$created_by,$created_on,$approved_on,$rejected_on,$utr_updated_by,$utr_updated_on,$db_status,$proof_images,$bank_name, $bank_ac_no, $ifsc_code, $bank_branch,$beneficiary_name
    );  

    $banner_data = array();

    while($stmt->fetch()){

        $stmt_approved_by = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
        $stmt_approved_by->bind_param("s", $approved_by);
        $stmt_approved_by->execute();
        $stmt_approved_by->store_result();
        $stmt_approved_by->bind_result($appr_by); 
        $stmt_approved_by->fetch();

        $stmt_rejected_by = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
        $stmt_rejected_by->bind_param("s", $rejected_by);
        $stmt_rejected_by->execute();
        $stmt_rejected_by->store_result();
        $stmt_rejected_by->bind_result($rejec_by); 
        $stmt_rejected_by->fetch();

        $stmt_created_by = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
        $stmt_created_by->bind_param("s", $created_by);
        $stmt_created_by->execute();
        $stmt_created_by->store_result();
        $stmt_created_by->bind_result($creat_by); 
        $stmt_created_by->fetch();

        $stmt_created_by = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
        $stmt_created_by->bind_param("s", $utr_updated_by);
        $stmt_created_by->execute();
        $stmt_created_by->store_result();
        $stmt_created_by->bind_result($paid_by); 
        $stmt_created_by->fetch();


        $images = [];
if (!empty($proof_images)) {
    $imgs = explode(',', $proof_images); // split by comma
    foreach ($imgs as $img) {
        $images[] = IMGPATH . trim($img); // prepend path
    }
}

        $temp = array(); 
        $temp['id'] = $id; 
        $temp['date'] = $date;
        $temp['type'] = $type;
        $temp['mode'] = $mode;
        $temp['status'] = $db_status;
        $temp['vendor'] = $vendor;
        $temp['approved_by'] = $appr_by;
        $temp['rejected_by'] = $rejec_by;
        $temp['created_by'] = $creat_by;
        $temp['approved_on'] = $approved_on;
        $temp['rejected_on'] = $rejected_on;
        $temp['created_on'] = $created_on;
        $temp['paid_on'] = $utr_updated_on;
        $temp['paid_by'] = $paid_by;
        $temp['emp_name'] = $emp_name;
        $temp['voucher_no'] = $voucher_no;
        $temp['client'] = $client;
        $temp['site'] = $site;
        $temp['gang'] = $gang;
        $temp['client_id'] = $client_id;
        $temp['site_id'] = $site_id;
        $temp['emp_id'] = $emp_id;
        $temp['emp_code'] = $emp_code;
        $temp['particular'] = $particular;
        $temp['amount'] = $amount;
        $temp['images'] = $images; 
        $temp['bank_name'] = $bank_name;
$temp['bank_ac_no'] = $bank_ac_no;
$temp['ifsc_code'] = $ifsc_code;
$temp['bank_branch'] = $bank_branch;
$temp['beneficiary_name'] = $beneficiary_name;


        array_push($banner_data, $temp);
    }

    $response['error'] = false;   
    $response['message'] = 'Vouchers got successful!!';   
    $response['user'] = $banner_data; 
    $response['total_rows'] = $total_rows;   
}  
else {  
    $response['error'] = true;   
    $response['message'] = 'Vouchers not available!!'; 
    $response['user'] = array();
}

break;


//------------------------------------------------------------------------------------------------------ 
case 'getParticularVouchers':  
  
$voucher_no = $_POST['voucher_no'];


            // $stmt = $conn->prepare("SELECT id,adv_date,type,mode,status,vendor_comm,approved_by,rejected_by,emp_name,voucher_no,client_name,site_name,gang_name,client_id,site_id,emp_id,emp_code,particular,amount from emp_advance WHERE voucher_no = ?");

 $stmt = $conn->prepare("SELECT id,adv_date,type,mode,status,vendor_comm,approved_by,approved_on,rejected_by,emp_name,voucher_no,client_name,site_name,gang_name,client_id,site_id,emp_id,emp_code,particular,SUM(amount),bank_name, bank_ac_no, ifsc_code, bank_branch, beneficiary_name from emp_advance WHERE voucher_no = ? group by voucher_no");
 
            $stmt->bind_param("s",$voucher_no);    
            $stmt->execute();  
            $stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$date,$type,$mode,$status,$vendor,$approved_by,$approved_on,$rejected_by,$emp_name,$voucher_no,$client,$site,$gang,$client_id,$site_id,$emp_id,$emp_code,$particular,$amount,$bank,$acc_no,$ifsc,$bank_branch,$beneficiary_name);  

    $banner_data = array();

    while($stmt->fetch()){

            $stmt_approved_by = $conn->prepare("SELECT fullname from users WHERE id = ?");
            $stmt_approved_by->bind_param("s",$approved_by);    
            $stmt_approved_by->execute();  
            $stmt_approved_by->store_result(); 
            $stmt_approved_by->bind_result($appr_by); 
            $stmt_approved_by->fetch();

            $stmt_rejected_by = $conn->prepare("SELECT fullname from users WHERE id = ?");
            $stmt_rejected_by->bind_param("s",$rejected_by);    
            $stmt_rejected_by->execute();  
            $stmt_rejected_by->store_result(); 
            $stmt_rejected_by->bind_result($rejec_by); 
            $stmt_rejected_by->fetch();


     $temp = array(); 
     $temp['id'] = $id; 
     $temp['date']=$date ;
     $temp['type'] = $type;
     $temp['mode'] = $mode;
     $temp['status'] = $status;
     $temp['vendor'] = $vendor;
     $temp['approved_by'] = $appr_by;
     $temp['rejected_by'] = $rejec_by;
     $temp['emp_name'] = $emp_name;
     $temp['emp_id'] = $emp_id;
     $temp['emp_code'] = $emp_code;
     $temp['voucher_no'] = $voucher_no;
     $temp['client'] = $client;
     $temp['site'] = $site;
     $temp['gang'] = $gang;
     $temp['particular'] = $particular;
     $temp['amount'] = $amount;
     $temp['client_id'] = $client_id;
     $temp['site_id'] = $site_id;
     $temp['bank'] = $bank;
     $temp['acc_no'] = $acc_no;
     $temp['ifsc'] = $ifsc;
     $temp['bank_branch'] = $bank_branch;
     $temp['beneficiary_name'] = $beneficiary_name;
    $temp['approved_on'] = $approved_on;



     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Vouchers got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = true;   
    $response['message'] = 'Vouchers not available!!'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//------------------------------------------------------------------------------------------------------
//this is to store id of each particular for update voucher part
case 'getParticularVouchersIds':  
  
$voucher_no = $_POST['voucher_no'];


            $stmt = $conn->prepare("SELECT id from emp_advance WHERE voucher_no = ?");
            $stmt->bind_param("s",$voucher_no);    
            $stmt->execute();  
            $stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['id'] = $id;
     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Vouchers ids successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = true;   
    $response['message'] = 'Voucher ids not available!!'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//------------------------------------------------------------------------------------------------------
case 'getAllTypesForVoucher':  

$stmt = $conn->prepare("SELECT id,charges_master FROM charges_master WHERE active='0'");  
//$stmt->bind_param("s",$id);    
$stmt->execute();    
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$type);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['id'] = $id; 
     $temp['type'] = $type;
     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Voucher types successfull!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'No voucher types!!';  
}  
//}  
//}  
break;  
// //--------------------------------------------------------------------------------------------------   
case 'approveVoucher':

    if(isTheseParametersAvailable(array('voucher_no'))){  
        date_default_timezone_set('Asia/Kolkata');
       
        $voucher_no = $_POST['voucher_no'];
        $approved_by = $_POST['approved_by'];
        $approved_on = date('Y-m-d H:i:s');
        $original_amount = $_POST['original_amount'];
        $schedule_date = $_POST['schedule_date'];
        $status = 'Approved';
        

                     $stmt_update = $conn->prepare("UPDATE emp_advance SET status='Approved',approved_on=?,approved_by=?,schedule_date=? where voucher_no =?"); 

                     $stmt_update->bind_param("ssss",$approved_on,$approved_by,$schedule_date,$voucher_no);

                      $stmt_update->execute();

                    
      $stmt = $conn->prepare("SELECT * FROM emp_advance WHERE voucher_no = ?");
$stmt->bind_param("s", $voucher_no);
$stmt->execute();
$result = $stmt->get_result();
$row = $result->fetch_assoc();

// ------------------------------------------
// 2️⃣ EXTRACT VALUES FROM FETCHED ROW
// ------------------------------------------
$client_id       = $row['client_id'];
$site_id         = $row['site_id'];
$client_name     = $row['client_name'];
$site_name       = $row['site_name'];
$mode            = $row['mode'];
$adv_date        = $row['adv_date'];
$gang            = $row['gang_name'];
$payment_detail  = $row['payment_detail'];
$part1           = $row['particular'];
$bank            = $row['bank_name'];
$account_no      = $row['bank_ac_no'];
$ifsc            = $row['ifsc_code'];
$branch          = $row['bank_branch'];
$beneficiary_name = $row['beneficiary_name'];
$utr_no       = $row['utr_no'];
$company_id       = $row['company_id'];
$company_name       = $row['company_name'];
$type       = $row['type'];
$emp_code       = $row['emp_code'];
$emp_id       = $row['emp_id'];
$emp_name       = $row['emp_name'];
$vendor_id       = $row['vendor_id'];
$vendor_name       = $row['vendor_name'];

$activity = "Voucher Approved";

$stmt_insert_log = $conn->prepare("
    INSERT INTO emp_advance_log
    (client_id,site_id,client_name,site_name,mode,adv_date,schedule_date,gang_name,payment_detail,
     voucher_no,particular,amount,created_by,created_on,bank_name, bank_ac_no, ifsc_code, 
     bank_branch,beneficiary_name,utr_no,activity,company_id,company_name,type,emp_code,emp_id,emp_name,status,vendor_id,vendor_name)
    VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
");

$stmt_insert_log->bind_param(
    "ssssssssssssssssssssssssssssss",
    $client_id, $site_id, $client_name, $site_name, $mode, $adv_date, $schedule_date,$gang, 
    $payment_detail, $voucher_no, $part1, $original_amount, $approved_by, $approved_on,
    $bank, $account_no, $ifsc, $branch, $beneficiary_name, $utr_no,$activity,$company_id,$company_name,$type,$emp_code,$emp_id,$emp_name,$status,$vendor_id,$vendor_name
);

$stmt_insert_log->execute();
                      $response['error'] = false;   
                      $response['message'] = 'Voucher approved successfully!!';    
                  }
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 
//---------------------------------------------------------------------------- 

case 'rejectVoucher':

    if(isTheseParametersAvailable(array('voucher_no'))){  
        date_default_timezone_set('Asia/Kolkata');
       
        $voucher_no = $_POST['voucher_no'];
        $rejected_by = $_POST['rejected_by'];
        $rejected_on = date('Y-m-d H:i:s');
        $original_amount = $_POST['original_amount'];
        $status = 'Cancelled';
        
                     $stmt_update = $conn->prepare("UPDATE emp_advance SET status='Cancelled',rejected_on=?,rejected_by=? where voucher_no =?"); 

                     $stmt_update->bind_param("sss",$rejected_on,$rejected_by,$voucher_no);

                      $stmt_update->execute();

                    
      $stmt = $conn->prepare("SELECT * FROM emp_advance WHERE voucher_no = ?");
$stmt->bind_param("s", $voucher_no);
$stmt->execute();
$result = $stmt->get_result();
$row = $result->fetch_assoc();

// ------------------------------------------
// 2️⃣ EXTRACT VALUES FROM FETCHED ROW
// ------------------------------------------
$client_id       = $row['client_id'];
$site_id         = $row['site_id'];
$client_name     = $row['client_name'];
$site_name       = $row['site_name'];
$mode            = $row['mode'];
$adv_date        = $row['adv_date'];
$gang            = $row['gang_name'];
$payment_detail  = $row['payment_detail'];
$part1           = $row['particular'];
$bank            = $row['bank_name'];
$account_no      = $row['bank_ac_no'];
$ifsc            = $row['ifsc_code'];
$branch          = $row['bank_branch'];
$beneficiary_name = $row['beneficiary_name'];
$utr_no       = $row['utr_no'];
$company_id       = $row['company_id'];
$company_name       = $row['company_name'];
$type       = $row['type'];
$emp_code       = $row['emp_code'];
$emp_id       = $row['emp_id'];
$emp_name       = $row['emp_name'];
$vendor_id       = $row['vendor_id'];
$vendor_name       = $row['vendor_name'];

$activity = "Voucher Rejected";

$stmt_insert_log = $conn->prepare("
    INSERT INTO emp_advance_log
    (client_id,site_id,client_name,site_name,mode,adv_date,gang_name,payment_detail,
     voucher_no,particular,amount,created_by,created_on,bank_name, bank_ac_no, ifsc_code, 
     bank_branch,beneficiary_name,utr_no,activity,company_id,company_name,type,emp_code,emp_id,emp_name,status,vendor_id,vendor_name)
    VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
");

$stmt_insert_log->bind_param(
    "sssssssssssssssssssssssssssss",
    $client_id, $site_id, $client_name, $site_name, $mode, $adv_date, $gang, 
    $payment_detail, $voucher_no, $part1, $original_amount, $approved_by, $approved_on,
    $bank, $account_no, $ifsc, $branch, $beneficiary_name, $utr_no,$activity,$company_id,$company_name,$type,$emp_code,$emp_id,$emp_name,$status,$vendor_id,$vendor_name
);

$stmt_insert_log->execute();
                      $response['error'] = false;   
                      $response['message'] = 'Voucher rejected successfully!!';    
                  }
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 

    //---------------------------------------------------------------------------------------

     case 'getVoucherImages':  

 $voucher_no = $_POST['voucher_no']; 

$stmt = $conn->prepare("SELECT proof_images FROM emp_advance where voucher_no=?");
$stmt->bind_param("s",$voucher_no);  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($image);  
 $stmt->fetch();
    $banner_data = array();
    if($image!="")
    {
 $img_names = explode(",", $image);

foreach ($img_names as $value) {
     $temp = array(); 
     $temp['img'] =IMGPATH.$value ;
     array_push($banner_data, $temp);
}

 $response['error'] = false;   
 $response['message'] = 'Voucher images got successful!!';   
 $response['user'] = $banner_data; 
    }  
    else
    {
        $response['error'] = true;   
        $response['message'] = 'No images!!'; 
    }    
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//----------------------------------------------------------------------------------------------------------

//working code without pagination
// case 'getVoucherSummaryForDownload':  
// $from_date = $_POST['from_date'];  
// $to_date = $_POST['to_date']; 
// $company_id = $_POST['company_id'];  
// $gang = $_POST['gang'];   
// $created_by = $_POST['created_by'];
// $role = $_POST['role'];

//  $params = [];
//     $base_query = "SELECT DATE(created_on) AS date, 
//                           COUNT(id) AS total, 
//                           SUM(CASE WHEN status = 'pending' THEN 1 ELSE 0 END) AS pending,
//                           SUM(CASE WHEN status = 'approved' THEN 1 ELSE 0 END) AS approved,
//                           SUM(CASE WHEN status = 'rejected' THEN 1 ELSE 0 END) AS rejected
//                    FROM emp_advance 
//                    WHERE DATE(adv_date) BETWEEN ? AND ? AND active = '0'";
    
//     // Add date parameters
//     $params[] = $from_date;
//     $params[] = $to_date;

//     if (!empty($company_id)) {
//         $base_query .= " AND client_id = ?";
//         $params[] = $company_id;
//     }

//     if (!empty($gang)) {
//         $base_query .= " AND gang_name = ?";
//         $params[] = $gang;
//     }


//  if (strcasecmp($role, "employee") === 0) {
// // Append created_by for other roles
//         $base_query .= " AND created_by = ?";
//         $base_query .= " GROUP BY DATE(adv_date),voucher_no";

//         $params[] = $created_by; // Add created_by

//         $stmt = $conn->prepare($base_query);

//         // Bind parameters
//         $stmt->bind_param(str_repeat('s', count($params)), ...$params);
//         $stmt->execute();  
//         $stmt->store_result(); 

//         $banner_data = [];
//         $stmt->bind_result($date, $total, $pending, $approved, $rejected);

//          if ($stmt->num_rows > 0) {
//         while ($stmt->fetch()) {
//             $temp = [
//                 'date' => date('Y-m-d', strtotime($date)), // Format date to Y-m-d
//                 'total' => $total,
//                 'pending' => $pending,
//                 'approved' => $approved,
//                 'rejected' => $rejected
//             ];
//             $banner_data[] = $temp;
//         }

//         $response['error'] = false;   
//         $response['message'] = 'Voucher summary retrieved successfully!';   
//         $response['user'] = $banner_data;   
//     }
//     else
//     {
//          $response['error'] = true;   
//          $response['message'] = 'Voucher summary not available!'; 
//          $response['user'] = [];  
//     } 
//  }
//     // other than employees
//    else{
//         $base_query .= " GROUP BY DATE(adv_date),voucher_no";
//         $stmt = $conn->prepare($base_query);

//         // Bind parameters dynamically
//         $stmt->bind_param(str_repeat('s', count($params)), ...$params);
//         $stmt->execute();  
//         $stmt->store_result();
        
//         $banner_data = [];
//         $stmt->bind_result($date, $total, $pending, $approved, $rejected);


//             if ($stmt->num_rows > 0) {
//         while ($stmt->fetch()) {
//             $temp = [
//                 'date' => date('Y-m-d', strtotime($date)), // Format date to Y-m-d
//                 'total' => $total,
//                 'pending' => $pending,
//                 'approved' => $approved,
//                 'rejected' => $rejected
//             ];
//             $banner_data[] = $temp;
//         }

//         $response['error'] = false;   
//         $response['message'] = 'Voucher summary retrieved successfully!';   
//         $response['user'] = $banner_data;   
//     }
//     else
//     {
//          $response['error'] = true;   
//          $response['message'] = 'Voucher summary not available!'; 
//          $response['user'] = [];  
//     }    

//   } 
//     break; 


case 'getVoucherSummaryForDownload':  

$from_date   = $_POST['from_date'];  
$to_date     = $_POST['to_date']; 
$company_id  = $_POST['company_id'];  
$site_id  = $_POST['site_id'];  
$gang        = $_POST['gang'];   
$created_by  = $_POST['created_by'];
$role        = $_POST['role'];

$params = [];

// Build WHERE conditions (same as your code)
$where = " WHERE DATE(adv_date) BETWEEN ? AND ? AND active = '0' ";
$params[] = $from_date;
$params[] = $to_date;

if (!empty($company_id)) {
    $where .= " AND client_id = ?";
    $params[] = $company_id;
}

if (!empty($site_id)) {
    $where .= " AND site_id = ?";
    $params[] = $site_id;
}


if (!empty($gang)) {
    $where .= " AND gang_name = ?";
    $params[] = $gang;
}

if (strcasecmp($role, "employee") === 0) {
    $where .= " AND created_by = ?";
    $params[] = $created_by;
}

$base_query = "
SELECT 
    date,
    COUNT(*) AS total,
    SUM(pending) AS pending,
    SUM(approved) AS approved,
    SUM(rejected) AS rejected,
    SUM(paid) AS paid
FROM 
(
    SELECT 
        DATE(adv_date) AS date,
        voucher_no,
        MAX(CASE WHEN status = 'pending' THEN 1 ELSE 0 END) AS pending,
        MAX(CASE WHEN status='approved' AND (utr_no IS NULL OR utr_no = '') THEN 1 ELSE 0 END) AS approved,
        MAX(CASE WHEN status = 'cancelled' THEN 1 ELSE 0 END) AS rejected,
        MAX(CASE WHEN status='approved' AND (utr_no IS NOT NULL AND utr_no != '') THEN 1 ELSE 0 END) AS paid

    FROM emp_advance
    $where
    GROUP BY DATE(adv_date), voucher_no
) AS v
GROUP BY date
ORDER BY date ASC
";

$stmt = $conn->prepare($base_query);
$stmt->bind_param(str_repeat('s', count($params)), ...$params);
$stmt->execute();  
$stmt->store_result();

$banner_data = [];
$stmt->bind_result($date, $total, $pending, $approved, $rejected, $paid);


if ($stmt->num_rows > 0) {
    while ($stmt->fetch()) {
        $banner_data[] = [
            'date'     => date('Y-m-d', strtotime($date)),
            'total'    => $total,
            'pending'  => $pending,
            'approved' => $approved,
            'rejected' => $rejected,
            'paid' => $paid
        ];
    }

    $response['error'] = false;   
    $response['message'] = 'Voucher summary retrieved successfully!';   
    $response['user'] = $banner_data;   

} else {

    $response['error'] = true;   
    $response['message'] = 'Voucher summary not available!'; 
    $response['user'] = [];  
}

break;



//---------------------------------------------------------------------------------------------------

case 'getVoucherSummary':

$from_date  = $_POST['from_date'];  
$to_date    = $_POST['to_date']; 
$company_id = $_POST['company_id']; 
$site_id = $_POST['site_id'];  
$gang       = $_POST['gang'];   
$created_by = $_POST['created_by'];
$role       = $_POST['role'];
$limit      = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
$offset     = isset($_POST['offset']) ? intval($_POST['offset']) : 0;

// --------------------------
// PARAM ARRAYS
// --------------------------
$params = [$from_date, $to_date];
$params_count = [$from_date, $to_date];

// --------------------------
// BUILD SUB QUERY
// --------------------------
$sub_query = "
    SELECT 
        DATE(adv_date) AS date,
        voucher_no,
        MAX(CASE WHEN status='pending' THEN 1 ELSE 0 END) AS pending,
        MAX(CASE WHEN status='approved' AND (utr_no IS NULL OR utr_no = '') THEN 1 ELSE 0 END) AS approved,
        MAX(CASE WHEN status='cancelled' THEN 1 ELSE 0 END) AS rejected,
        MAX(CASE WHEN status='approved' AND (utr_no IS NOT NULL AND utr_no != '') THEN 1 ELSE 0 END) AS paid

    FROM emp_advance
    WHERE DATE(adv_date) BETWEEN ? AND ? AND active='0'
";

if (!empty($company_id)) {
    $sub_query .= " AND client_id = ?";
    $params[] = $company_id;
    $params_count[] = $company_id;
}

if (!empty($site_id)) {
    $sub_query .= " AND site_id = ?";
    $params[] = $site_id;
    $params_count[] = $site_id;
}

if (!empty($gang)) {
    $sub_query .= " AND gang_name = ?";
    $params[] = $gang;
    $params_count[] = $gang;
}

if (strcasecmp($role, "employee") === 0) {
    $sub_query .= " AND created_by = ?";
    $params[] = $created_by;
    $params_count[] = $created_by;
}

$sub_query .= " GROUP BY DATE(adv_date), voucher_no ";


// --------------------------
// MAIN OUTER QUERY (PER DATE)
// --------------------------
$base_query = "
SELECT 
    date,
    COUNT(*) AS total,
    SUM(pending) AS pending,
    SUM(approved) AS approved,
    SUM(rejected) AS rejected,
    SUM(paid) AS paid
FROM ($sub_query) AS v
GROUP BY date
ORDER BY date ASC
LIMIT ?, ?
";

$params[] = $offset;
$params[] = $limit;


// --------------------------
// COUNT QUERY (PER DATE)
// --------------------------
$base_query_for_count = "
SELECT COUNT(*) AS total_rows FROM (
    SELECT date
    FROM ($sub_query) AS x
    GROUP BY date
) AS t
";


// --------------------------
// EXECUTE COUNT QUERY
// --------------------------
$stmt_count = $conn->prepare($base_query_for_count);
$stmt_count->bind_param(str_repeat('s', count($params_count)), ...$params_count);
$stmt_count->execute();
$stmt_count->bind_result($total_rows);
$stmt_count->fetch();
$stmt_count->close();


// --------------------------
// EXECUTE MAIN DATA QUERY
// --------------------------
$stmt = $conn->prepare($base_query);

$bind_types = str_repeat('s', count($params) - 2) . "ii";  // string params + 2 int (offset & limit)
$stmt->bind_param($bind_types, ...$params);

$stmt->execute();
$stmt->store_result();
$stmt->bind_result($date, $total, $pending, $approved, $rejected, $paid);

$banner_data = [];

while ($stmt->fetch()) {
    $banner_data[] = [
        'date' => $date,
        'total' => $total,
        'pending' => $pending,
        'approved' => $approved,
        'rejected' => $rejected,
        'paid' => $paid
    ];
}

$response = [
    'error' => empty($banner_data),
    'message' => empty($banner_data) ? 'Voucher summary not available!' : 'Voucher summary retrieved successfully!',
    'total_rows' => $total_rows,
    'user' => $banner_data
];

break;




// case 'getVoucherSummary':

// $from_date = $_POST['from_date'];  
// $to_date = $_POST['to_date']; 
// $company_id = $_POST['company_id'];  
// $gang = $_POST['gang'];   
// $created_by = $_POST['created_by'];
// $role = $_POST['role'];
// $limit = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
// $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0; 

// // ---- Base Queries ----
// $base_query = "SELECT DATE(adv_date) AS date, 
//                       COUNT(DISTINCT voucher_no) AS total, 
//                       SUM(CASE WHEN status='pending' THEN 1 ELSE 0 END) AS pending,
//                       SUM(CASE WHEN status='approved' THEN 1 ELSE 0 END) AS approved,
//                       SUM(CASE WHEN status='rejected' THEN 1 ELSE 0 END) AS rejected
//                FROM emp_advance 
//                WHERE DATE(adv_date) BETWEEN ? AND ? AND active='0'";

// $base_query_for_count = "SELECT COUNT(DISTINCT DATE(adv_date)) AS total_rows
//                          FROM emp_advance 
//                          WHERE DATE(adv_date) BETWEEN ? AND ? AND active='0'";

// $params = [$from_date, $to_date];
// $params_count = [$from_date, $to_date];

// // ---- Optional Filters ----
// if (!empty($company_id)) {
//     $base_query .= " AND client_id = ?";
//     $base_query_for_count .= " AND client_id = ?";
//     $params[] = $company_id;
//     $params_count[] = $company_id;
// }
// if (!empty($gang)) {
//     $base_query .= " AND gang_name = ?";
//     $base_query_for_count .= " AND gang_name = ?";
//     $params[] = $gang;
//     $params_count[] = $gang;
// }
// if (strcasecmp($role, "employee") === 0) {
//     $base_query .= " AND created_by = ?";
//     $base_query_for_count .= " AND created_by = ?";
//     $params[] = $created_by;
//     $params_count[] = $created_by;
// }

// $base_query .= " GROUP BY DATE(adv_date) LIMIT ?,?";
// $params[] = $offset;
// $params[] = $limit;

// // ---- Get total count ----
// $stmt_count = $conn->prepare($base_query_for_count);
// $stmt_count->bind_param(str_repeat('s', count($params_count)), ...$params_count);
// $stmt_count->execute();
// $stmt_count->bind_result($total_rows);
// $stmt_count->fetch();
// $stmt_count->close();

// // ---- Get paginated data ----
// $stmt = $conn->prepare($base_query);
// $stmt->bind_param(str_repeat('s', count($params)), ...$params);
// $stmt->execute();
// $stmt->store_result();
// $stmt->bind_result($date, $total, $pending, $approved, $rejected);

// $banner_data = [];
// while ($stmt->fetch()) {
//     $banner_data[] = [
//         'date' => $date,
//         'total' => $total,
//         'pending' => $pending,
//         'approved' => $approved,
//         'rejected' => $rejected
//     ];
// }

// if (count($banner_data) > 0) {
//     $response = [
//         'error' => false,
//         'message' => 'Voucher summary retrieved successfully!',
//         'total_rows' => $total_rows,
//         'user' => $banner_data
//     ];
// } else {
//     $response = [
//         'error' => true,
//         'message' => 'Voucher summary not available!',
//         'total_rows' => 0,
//         'user' => []
//     ];
// }

// break;

    //---------------------------------------------------------------
    case 'getEmployeesAllDocs':

    $emp_id = $_POST['emp_id'];  

    // List of document types (suffixes in your doc_name)
    $docTypes = ['_aadhar', '_pan', '_uan', '_passport', '_pf', '_esis'];

    // Initialize response arrays
    $response = [];
    foreach ($docTypes as $type) {
        $response[substr($type, 1)] = []; // remove underscore for key
    }

    foreach ($docTypes as $type) {

        $stmt = $conn->prepare("SELECT doc_name 
                                FROM emp_docs 
                                WHERE emp_id = ? AND doc_name LIKE ? and active ='0'");
        $likeParam = "%$type%";
        $stmt->bind_param("ss", $emp_id, $likeParam);
        $stmt->execute();
        $stmt->store_result();
        $stmt->bind_result($doc_name);

        $docs = [];
        while($stmt->fetch()){
            $docs[] = IMGPATH . $doc_name;
        }

        $response[substr($type, 1)] = $docs; // assign array
    }

    $response['error'] = false;
    $response['message'] = 'Documents fetched successfully!';
    break;
    //----------------------------------------------------------------------------------------------------------------
   case 'updateEmployeesAllDocs':

    if(isTheseParametersAvailable(array('aadharCount'))){  
        date_default_timezone_set('Asia/Kolkata');

        $emp_id = $_POST['emp_id']; // Make sure emp_id is sent
        $fname = $_POST['fname'];
        $lname = $_POST['lname'];

        //update dates
         $uan = $_POST['uan'];
          $esis = $_POST['esis'];
           $passport = $_POST['passport'];

        $aadharCount = $_POST['aadharCount'];
        $panCount = $_POST['panCount'];
        $passportCount = $_POST['passportCount'];
        $uanCount = $_POST['uanCount'];
        $pfCount = $_POST['pfCount'];
        $esisCount = $_POST['esisCount'];

        $created_by = $_POST['created_by'];
        $created_on = date('Y-m-d H:i:s');

        $aadhar_no = $_POST['aadhar_no'];
        $pan_no = $_POST['pan_no'];
        $uan_no = $_POST['uan_no'];
        $pf_no = $_POST['pf_no'];
        $passport_no = $_POST['passport_no'];
        $esis_no = $_POST['esis_no'];

        // 1️⃣ Soft delete all existing docs for this employee
        $stmt_soft_delete = $conn->prepare("UPDATE emp_docs SET active=1 WHERE emp_id=?");
        $stmt_soft_delete->bind_param("s", $emp_id);
        $stmt_soft_delete->execute();

        // Update employee numbers
        $stmt_update_numbers = $conn->prepare("UPDATE employee SET aadhar_no=?, pancard_no=?, uan_no=?, pf_no=?, passport_no=?, esis_no=?,uan_date=?,esis_date=?,passport_valid_date=? WHERE id=?");
        $stmt_update_numbers->bind_param("ssssssssss", $aadhar_no, $pan_no, $uan_no, $pf_no, $passport_no, $esis_no, $uan, $esis, $passport, $emp_id);
        $stmt_update_numbers->execute();

         $stmt_last = $conn->prepare("SELECT max(id) lastid from emp_docs");  
                         $stmt_last->execute();
                         $stmt_last->store_result(); 

                         if($stmt_last->num_rows > 0){  
                         $stmt_last->bind_result($lastid);  
                         $stmt_last->fetch();
                         $new_id = $lastid+1; 
                         }
                          

        // 2️⃣ Function to get current active doc count (for numbering)
        function getCurrentDocCount($conn, $emp_id, $doc_type){
            $stmt = $conn->prepare("SELECT COUNT(*) FROM emp_docs WHERE emp_id=? AND doc_type=? AND active=0");
            $stmt->bind_param("ss", $emp_id, $doc_type);
            $stmt->execute();
            $stmt->bind_result($count);
            $stmt->fetch();
            $stmt->close();
            return $count;
        }

        // 3️⃣ Loop through all doc types
        $docTypes = [
            'Aadhar Card' => ['prefix'=>'aadhar', 'count'=>$aadharCount, 'remark'=>$aadhar_no],
            'Pan Card'    => ['prefix'=>'pan', 'count'=>$panCount, 'remark'=>$pan_no],
            'UAN Card'    => ['prefix'=>'uan', 'count'=>$uanCount, 'remark'=>$uan_no],
            'Passport'    => ['prefix'=>'passport', 'count'=>$passportCount, 'remark'=>$passport_no],
            'PF'          => ['prefix'=>'pf', 'count'=>$pfCount, 'remark'=>$pf_no],
            'ESIS'        => ['prefix'=>'esis', 'count'=>$esisCount, 'remark'=>$esis_no],
        ];

        foreach($docTypes as $doc_name => $info){
            $prefix = $info['prefix'];
            $newCount = intval($info['count']);
            $remark = $info['remark'];

            $currentCount = getCurrentDocCount($conn, $emp_id, $doc_name);

            for($i=1; $i<=$newCount; $i++){
                $counter = $currentCount + $i;
                $field_name = $prefix.$i;

                if(isset($_POST[$field_name]) && !empty($_POST[$field_name])){
                    $file_name = $fname . "_" . $lname . "_".$prefix ."_".$new_id."_".$counter.".jpg";
                    $file_path = "emp_docs/".$file_name;
                    file_put_contents($file_path, base64_decode($_POST[$field_name]));

                    $stmt_insert = $conn->prepare("INSERT INTO emp_docs (doc_type, doc_name, emp_id, doc_remark, created_by, created_on) VALUES (?,?,?,?,?,?)");
                    $stmt_insert->bind_param("ssssss", $doc_name, $file_name, $emp_id, $remark, $created_by, $created_on);
                    $stmt_insert->execute();
                }
            }
        }

        $response['error'] = false;
        $response['message'] = 'Documents updated successfully!!';

    } else {
        $response['error'] = true;
        $response['message'] = 'Required parameters are not available';
    }
break;

    //----------------------------------------------------------------------------------------------------------------------

    //fetching aadhar card details

//     case 'getData':  
//     if(isTheseParametersAvailable(array('created_by'))){ 
//         date_default_timezone_set('Asia/Kolkata'); 
        
//         $contact_and_pin = $_POST['contact_and_pin']; 
//         $address         = $_POST['address']; 
//         $created_by      = $_POST['created_by'];     
//         $created_on      = date('Y-m-d H:i:s');
//         $names           = $_POST['names'];

//         // ---------------- PHONE EXTRACTION ----------------
//         // Remove + and non-digits first
//         $clean_contact = preg_replace('/[^0-9]/', '', $contact_and_pin);

//         if(strlen($clean_contact) >= 10){
//             // take last 10 digits as phone
//             $phone = substr($clean_contact, -10);
//         } else {
//             $phone = "NA";
//         }

//         // ---------------- PINCODE EXTRACTION ----------------
//         if(preg_match('/\b\d{6}\b/', $contact_and_pin, $mypin)){
//             $pincode = $mypin[0];
//         } 
//         else if(preg_match('/\b\d{6}\b/', $address, $mypins)){
//             $pincode = $mypins[0];
//         } 
//         else {
//             $pincode = "NA";
//         }

//         // ---------------- ADDRESS CLEANUP ----------------
//         // remove word "address", colons, trim spaces
//         $address = preg_replace('/\baddress\b/i', '', $address);
//         $address = str_replace(":", "", $address);
//         $address = trim(preg_replace('/\s+/', ' ', $address)); // normalize spaces

//         // ---------------- NAME CLEANUP ----------------
//         // remove word "name" with or without colon/special chars
//         $names = preg_replace('/\bname\b\s*[:\-]*/i', '', $names);
//         $names = trim(preg_replace('/[^a-zA-Z0-9\s,]/', '', $names)); // remove unwanted special chars except spaces/commas
//         $names = trim(preg_replace('/\s+/', ' ', $names)); // normalize spaces

//         // ---------------- RESPONSE ----------------
//         $response = [
//             'error'   => false,
//             'message' => 'Data got successful!!',
//             'name'    => $names,
//             'address' => $address,
//             'pincode' => $pincode,
//             'phone'   => $phone
//         ];
//     }  
//     else {  
//         $response = [ 
//             'error'   => true,   
//             'message' => 'required parameters are not available' 
//         ];   
//     }  
// break; 

// case 'getAadharData':  
//     if (isTheseParametersAvailable(array('aadhar_text'))) { 
//         date_default_timezone_set('Asia/Kolkata'); 
        
// $aadhar_text = $_POST['aadhar_text'] ?? '';

// // Remove all characters except digits and spaces
// $clean_text = preg_replace('/[^\d\s]/', '', $aadhar_text);

// $aadhar_num = "NA";
// $temp = "";

// for ($i = 0; $i < strlen($clean_text); $i++) {
//     $char = $clean_text[$i];

//     if (ctype_digit($char)) {
//         $temp .= $char; // add digit
//     } elseif ($char === ' ') {
//         continue; // ignore spaces
//     } else {
//         $temp = ""; // reset if any other character
//     }

//     if (strlen($temp) === 12) {
//         $aadhar_num = $temp;
//         break; // stop after finding first 12-digit Aadhaar
//     }
// }

// // RESPONSE
// $response = [
//     'error' => false,
//     'message' => 'Aadhaar extracted successfully!',
//     'aadhar_num' => $aadhar_num
// ];

  
//     }  
// break;


//    case 'getAadharData':  
//     if (isTheseParametersAvailable(array('aadhar_text'))) { 
//         date_default_timezone_set('Asia/Kolkata'); 
        
//         $aadhar_text = $_POST['aadhar_text'] ?? '';

//         $aadhar_num = "NA";
//         $temp = "";

//         for ($i = 0; $i < strlen($aadhar_text); $i++) {
//             $char = $aadhar_text[$i];

//             if (ctype_digit($char)) {
//                 $temp .= $char; // add digit
//             } elseif ($char === ' ') {
//                 continue; // ignore spaces
//             } else {
//                 $temp = ""; // reset if any other character
//             }

//             if (strlen($temp) === 12) {
//                 $aadhar_num = $temp;
//                 break; // stop after finding first 12-digit Aadhaar
//             }
//         }

//         // RESPONSE
//         $response = [
//             'error' => false,
//             'message' => 'Aadhaar extracted successfully!',
//             'aadhar_num' => $aadhar_num
//         ];
//     } else {  
//         $response = [ 
//             'error'   => true,   
//             'message' => 'required parameters are not available' 
//         ];   
//     }  
// break;


//working script before adding document ai 
// case 'getAadharData':  
//     if (isTheseParametersAvailable(array('aadhar_text'))) { 
//         date_default_timezone_set('Asia/Kolkata'); 
        
//         $aadhar_text = $_POST['aadhar_text'] ?? '';

//         $aadhar_num = "";
//         $temp = "";

//         // --- Aadhaar Extraction ---
//         for ($i = 0; $i < strlen($aadhar_text); $i++) {
//             $char = $aadhar_text[$i];

//             if (ctype_digit($char)) {
//                 $temp .= $char; // add digit
//             } elseif ($char === ' ') {
//                 continue; // ignore spaces
//             } else {
//                 $temp = ""; // reset if any other character
//             }

//             if (strlen($temp) === 12) {
//                 $aadhar_num = $temp;
//                 break; // stop after finding first 12-digit Aadhaar
//             }
//         }

//         // --- Phone Extraction (10 digits, no digit before or after) ---
//         $phone_num = "";
//         if (preg_match('/(?<!\d)(\d{10})(?!\d)/', $aadhar_text, $matches)) {
//             $phone_num = $matches[1];
//         }

//         // --- Gender Extraction ---
//         $gender = "";
//         if (preg_match('/\bmale\b/i', $aadhar_text)) {
//             $gender = "Male";
//         } elseif (preg_match('/\bfemale\b/i', $aadhar_text)) {
//             $gender = "Female";
//         }

//         // --- DOB Extraction ---
//   // --- DOB Extraction ---
// $dob = "";
// if (preg_match('/DOB[^0-9]*(\d{1,2})[-\/\.]?\s*(\d{1,2})[-\/\.]?\s*(\d{2,4})/i', $aadhar_text, $matches)) {
//     // Normalize to dd-mm-yyyy format
//     $day = str_pad($matches[1], 2, '0', STR_PAD_LEFT);
//     $month = str_pad($matches[2], 2, '0', STR_PAD_LEFT);
//     $year = $matches[3];

//     // If year is 2-digit, prefix 19 or 20
//     if (strlen($year) == 2) {
//         $year = ($year < 25) ? '20' . $year : '19' . $year;
//     }

//     $raw_dob = "$day-$month-$year";
//     $dateObj = date_create_from_format('d-m-Y', $raw_dob);

//     if ($dateObj) {
//         $dob = date_format($dateObj, 'Y-m-d');
//     }
// }


//         // --- NAME Extraction (Line above DOB) ---
//         $first_name = $middle_name = $last_name = "";
//         $lines = preg_split('/\r\n|\r|\n/', $aadhar_text);

//         for ($i = 0; $i < count($lines); $i++) {
//             if (stripos($lines[$i], 'DOB') !== false && $i > 0) {
//                 $possible_name = trim($lines[$i - 1]);
//                 $possible_name = preg_replace('/[^A-Za-z\s]/', '', $possible_name); // remove special chars
//                 $name_parts = preg_split('/\s+/', trim($possible_name));

//                 if (count($name_parts) === 1) {
//                     $first_name = ucfirst(strtolower($name_parts[0]));
//                 } elseif (count($name_parts) === 2) {
//                     $first_name = ucfirst(strtolower($name_parts[0]));
//                     $last_name = ucfirst(strtolower($name_parts[1]));
//                 } elseif (count($name_parts) >= 3) {
//                     $first_name = ucfirst(strtolower($name_parts[0]));
//                     $middle_name = ucfirst(strtolower($name_parts[1]));
//                     $last_name = ucfirst(strtolower(end($name_parts)));
//                 }
//                 break;
//             }
//         }

//         // --- Address Extraction (from D/O, S/O, W/O till 6-digit pincode) ---
//        $address = "";
// if (preg_match('/((?:D\/O|S\/O|W\/O|DIO|SIO|WIO)[:\-]?\s*.*?\b\d{6}\b)/is', $aadhar_text, $matches)) {
//     $address_raw = trim($matches[1]);
//     // Clean up newlines, tabs, and multiple spaces
//     $address_clean = preg_replace('/\s+/', ' ', $address_raw);
//     $address = trim($address_clean);
// }

// //Pincode
// $pincode = "";
// if (preg_match('/(?<![\dA-Za-z])(\d{6})(?![\dA-Za-z])/', $aadhar_text, $match)) {
//     $pincode = $match[1];
// }



//         // --- Response ---
//         $response = [
//             'error' => false,
//             'message' => 'Aadhaar extracted successfully!',
//             'aadhar_num' => $aadhar_num,
//             'phone_num' => $phone_num,
//             'gender' => $gender,
//             'dob' => $dob,
//             'first_name' => $first_name,
//             'middle_name' => $middle_name,
//             'last_name' => $last_name,
//             'address' => $address,
//             'pincode' => $pincode
//         ];
//     } else {  
//         $response = [ 
//             'error'   => true,   
//             'message' => 'required parameters are not available' 
//         ];   
//     }  
// break;


case 'getAadharData':

    if (isTheseParametersAvailable(array('aadhar_text'))) {

        date_default_timezone_set('Asia/Kolkata');

        $img_base64 = $_POST['aadhar_text'];
      //  echo $img_base64;
        $img_base64 = preg_replace('#^data:image/\w+;base64,#i', '', $img_base64);
        $fileContent = base64_decode($img_base64);

        if (!$fileContent) {
            $response = [
                'error' => true,
                'message' => 'Invalid Base64 Image'
            ];
            break;
        }

        // ----------------------------------------------------
        // 2. Document AI Setup
        // ----------------------------------------------------
        $projectId   = "77305643982";
        $location    = "us";
        $processorId = "f09db5a1cbacaf5b";

        $processorName = "projects/$projectId/locations/$location/processors/$processorId";

        // Detect MIME automatically
        $mime = finfo_buffer(finfo_open(), $fileContent, FILEINFO_MIME_TYPE);
        if (!$mime) $mime = "image/jpeg";

        $rawDocument = new RawDocument([
            'content'  => $fileContent,
            'mime_type' => $mime
        ]);

        // Correct API endpoint
        $client = new DocumentProcessorServiceClient([
            'apiEndpoint' => "$location-documentai.googleapis.com"
        ]);

        $request = new ProcessRequest([
            'name' => $processorName,
            'raw_document' => $rawDocument
        ]);

        // ----------------------------------------------------
        // 3. PROCESS DOCUMENT
        // ----------------------------------------------------
        try {
            $result = $client->processDocument($request);
            $doc = $result->getDocument();
            $entities = $doc->getEntities();

            $fields = [];
            foreach ($entities as $e) {
                $fields[$e->getType()] = $e->getMentionText();
            }

            // Extract data
            $full_name   = $fields["Name"] ?? "";
            $aadhar_num  = $fields["aadhar_number"] ?? "";
            $phone_num   = $fields["phone_number"] ?? "";
            $gender      = $fields["gender"] ?? "";
            $dob         = $fields["birth_date"] ?? "";
            $father_name = $fields["father_name"] ?? "";
            $address     = $fields["address"] ?? "";
            $pincode     = $fields["pincode"] ?? "";

            $aadhar_num = str_replace(" ", "", $aadhar_num);
            $phone_num = str_replace(" ", "", $phone_num);
            $pincode = str_replace(" ", "", $pincode);
            $dob = str_replace(" ", "", $dob);


            // ----------------------------------------------------
            // 4. Split Name
            // ----------------------------------------------------
            $first_name = $middle_name = $last_name = "";
            $split = explode(" ", trim($full_name));

            if (count($split) == 1) {
                $first_name = $split[0];
            } elseif (count($split) == 2) {
                $first_name = $split[0];
                $last_name  = $split[1];
            } else {
                $first_name = $split[0];
                $middle_name = $split[1];
                $last_name = implode(" ", array_slice($split, 2));
            }

            // ----------------------------------------------------
            // 5. Final Response
            // ----------------------------------------------------
            $response = [
                'error' => false,
                'message' => 'Aadhaar extracted successfully',
                'aadhar_num' => $aadhar_num,
                'phone_num'  => $phone_num,
                'gender'     => $gender,
                'dob'        => $dob,
                'first_name' => $first_name,
                'middle_name'=> $middle_name,
                'last_name'  => $last_name,
                'father_name'=> $father_name,
                'address'    => $address,
                'pincode'    => $pincode
            ];

        } catch (Exception $ex) {
            $response = [
                'error' => true,
                'message' => "Document AI Error: " . $ex->getMessage()
            ];
        }

        $client->close();

    } else {
        $response = [
            'error' => true,
            'message' => 'Required parameters missing'
        ];
    }

break;


//-----------------------------------------------------------------------

// case 'getPanData':  
//     if (isTheseParametersAvailable(array('pan_text'))) { 
//         date_default_timezone_set('Asia/Kolkata'); 
        
//         $pan_text = $_POST['pan_text'] ?? '';
//         $pan_num = "NA";

//         // Remove all characters except letters, digits, and spaces
//         $clean_text = preg_replace('/[^A-Za-z0-9\s]/', '', $pan_text);

//         // Remove multiple spaces
//         $clean_text = preg_replace('/\s+/', ' ', $clean_text);

//         // Find all continuous sequences of 10 alphanumeric chars (to check potential PAN)
//         preg_match_all('/[A-Za-z0-9]{10,}/', $clean_text, $matches);

//         if (!empty($matches[0])) {
//             foreach ($matches[0] as $possible) {
//                 // Uppercase for uniformity
//                 $possible = strtoupper($possible);

//                 // Check valid PAN pattern: 5 letters + 4 digits + 1 letter
//                 if (preg_match('/^[A-Z]{5}[0-9]{4}[A-Z]{1}$/', $possible)) {
//                     $pan_num = $possible;
//                     break;
//                 }
//             }
//         }

//         //dob
//          // --- DOB Extraction ---
// // --- DOB Extraction ---
// $dob = "";
// if (preg_match('/(\d{1,2})[\/\-](\d{1,2})[\/\-](\d{2,4})/', $pan_text, $matches)) {
//     $day = str_pad($matches[1], 2, '0', STR_PAD_LEFT);
//     $month = str_pad($matches[2], 2, '0', STR_PAD_LEFT);
//     $year = $matches[3];

//     // Handle 2-digit year
//     if (strlen($year) == 2) {
//         $year = ($year < 25) ? '20' . $year : '19' . $year;
//     }

//     $raw_dob = "$day-$month-$year";
//     $dateObj = date_create_from_format('d-m-Y', $raw_dob);

//     if ($dateObj) {
//         $dob = date_format($dateObj, 'Y-m-d');
//     }
// }


//         // RESPONSE
//         $response = [
//             'error' => false,
//             'message' => 'PAN extracted successfully!',
//             'pan_num' => $pan_num,
//             'dob' => $dob
//         ];
//     } else {  
//         $response = [ 
//             'error'   => true,   
//             'message' => 'required parameters are not available' 
//         ];   
//     }  
// break;

case 'getPanData':

    if (isTheseParametersAvailable(array('pan_text'))) {

        date_default_timezone_set('Asia/Kolkata');

        $img_base64 = $_POST['pan_text'];
      //  echo $img_base64;
        $img_base64 = preg_replace('#^data:image/\w+;base64,#i', '', $img_base64);
        $fileContent = base64_decode($img_base64);

        if (!$fileContent) {
            $response = [
                'error' => true,
                'message' => 'Invalid Base64 Image'
            ];
            break;
        }

        // ----------------------------------------------------
        // 2. Document AI Setup
        // ----------------------------------------------------
        $projectId   = "77305643982";
        $location    = "us";
        $processorId = "a821d3e094b88d55";

        $processorName = "projects/$projectId/locations/$location/processors/$processorId";

        // Detect MIME automatically
        $mime = finfo_buffer(finfo_open(), $fileContent, FILEINFO_MIME_TYPE);
        if (!$mime) $mime = "image/jpeg";

        $rawDocument = new RawDocument([
            'content'  => $fileContent,
            'mime_type' => $mime
        ]);

        // Correct API endpoint
        $client = new DocumentProcessorServiceClient([
            'apiEndpoint' => "$location-documentai.googleapis.com"
        ]);

        $request = new ProcessRequest([
            'name' => $processorName,
            'raw_document' => $rawDocument
        ]);

        // ----------------------------------------------------
        // 3. PROCESS DOCUMENT
        // ----------------------------------------------------
        try {
            $result = $client->processDocument($request);
            $doc = $result->getDocument();
            $entities = $doc->getEntities();

            $fields = [];
            foreach ($entities as $e) {
                $fields[$e->getType()] = $e->getMentionText();
            }

            // Extract data
            $pan_num  = $fields["pan_number"] ?? "";
            $dob         = $fields["dob"] ?? "";
           

            $pan_num = str_replace(" ", "", $pan_num);
            $dob = str_replace(" ", "", $dob);

            // ----------------------------------------------------
            // 5. Final Response
            // ----------------------------------------------------
            $response = [
                'error' => false,
                'message' => 'Pan card extracted successfully',
                'pan_num' => $pan_num,
                'dob'  => $dob
            ];

        } catch (Exception $ex) {
            $response = [
                'error' => true,
                'message' => "Document AI Error: " . $ex->getMessage()
            ];
        }

        $client->close();

    } else {
        $response = [
            'error' => true,
            'message' => 'Required parameters missing'
        ];
    }

break;


//------------------------------------------------------------------

case 'getUanData':  
    if (isTheseParametersAvailable(array('uan_text'))) { 
        date_default_timezone_set('Asia/Kolkata'); 
        
        $uan_text = $_POST['uan_text'] ?? '';

        $uan_num = "NA";
        $temp = "";

        for ($i = 0; $i < strlen($uan_text); $i++) {
            $char = $uan_text[$i];

            if (ctype_digit($char)) {
                $temp .= $char; // add digit
            } elseif ($char === ' ') {
                continue; // ignore spaces
            } else {
                $temp = ""; // reset if any other character
            }

            if (strlen($temp) === 12) {
                $uan_num = $temp;
                break; // stop after finding first 12-digit UAN
            }
        }


        //uan date
        $date_found = "";

        // Match dates like: 12/05/1995, 12-05-1995, 12.05.1995, 12 05 1995
        $date_regex = '/\b(\d{1,2})[\/\.\-\s](\d{1,2})[\/\.\-\s](\d{4})\b/';

        if (preg_match($date_regex, $uan_text, $match)) {
            $day = $match[1];
            $month = $match[2];
            $year = $match[3];

            // Validate & convert to Y-m-d
            if (checkdate($month, $day, $year)) {
                $date_found = date('Y-m-d', strtotime("$year-$month-$day"));
            }
        }

        // RESPONSE
        $response = [
            'error' => false,
            'message' => 'UAN extracted successfully!',
            'uan_num' => $uan_num,
            'uan_date' => $date_found
        ];
    } else {  
        $response = [ 
            'error'   => true,   
            'message' => 'required parameters are not available' 
        ];   
    }  
break;
//------------------------------------------------------------------------------------------------------ 
case 'getEsisData':  
    if (isTheseParametersAvailable(array('esis_text'))) { 
        date_default_timezone_set('Asia/Kolkata'); 
        
        $esis_text = $_POST['esis_text'] ?? '';

        $esis_num = "NA";
        $temp = "";

        for ($i = 0; $i < strlen($esis_text); $i++) {
            $char = $esis_text[$i];

            // Keep digits only
            if (ctype_digit($char)) {
                $temp .= $char;
            } elseif ($char === ' ' || $char === '/') {
                continue; // ignore space or slash
            } else {
                $temp = ""; // reset on other characters
            }

            // Check for valid ESIS number (17 digits)
            if (strlen($temp) === 17) {
                $esis_num = $temp;
                break;
            }
        }

           //esis date
        $date_found = "";

        // Match dates like: 12/05/1995, 12-05-1995, 12.05.1995, 12 05 1995
        $date_regex = '/\b(\d{1,2})[\/\.\-\s](\d{1,2})[\/\.\-\s](\d{4})\b/';

        if (preg_match($date_regex, $esis_text, $match)) {
            $day = $match[1];
            $month = $match[2];
            $year = $match[3];

            // Validate & convert to Y-m-d
            if (checkdate($month, $day, $year)) {
                $date_found = date('Y-m-d', strtotime("$year-$month-$day"));
            }
        }

        $response = [
            'error' => false,
            'message' => 'ESIS extracted successfully!',
            'esis_num' => $esis_num,
            'esis_date' => $date_found
        ];
    } else {  
        $response = [ 
            'error'   => true,   
            'message' => 'required parameters are not available' 
        ];   
    }  
break;
//------------------------------------------------------------------------------------------------------ 
case 'getPassportData':
    if (isTheseParametersAvailable(array('pass_text'))) {
        date_default_timezone_set('Asia/Kolkata');

        $passport_text = $_POST['pass_text'] ?? '';
        $passport_num = "NA";

        // Clean text - remove unnecessary characters but keep spaces
        $clean_text = preg_replace('/[^A-Za-z0-9\s]/', ' ', $passport_text);

        // Passport number pattern: One letter + 7 digits (e.g., J8369854 or J 8369 854)
        if (preg_match('/\b([A-Z]\s*\d\s*\d\s*\d\s*\d\s*\d\s*\d\s*\d)\b/i', $clean_text, $matches)) {
            // Remove spaces inside
            $passport_num = strtoupper(str_replace(' ', '', $matches[1]));
        }

           //passport valid date
        $date_found = "";

        // Match dates like: 12/05/1995, 12-05-1995, 12.05.1995, 12 05 1995
        $date_regex = '/\b(\d{1,2})[\/\.\-\s](\d{1,2})[\/\.\-\s](\d{4})\b/';

        if (preg_match($date_regex, $passport_text, $match)) {
            $day = $match[1];
            $month = $match[2];
            $year = $match[3];

            // Validate & convert to Y-m-d
            if (checkdate($month, $day, $year)) {
                $date_found = date('Y-m-d', strtotime("$year-$month-$day"));
            }
        }

        // RESPONSE
        $response = [
            'error' => false,
            'message' => 'Passport number extracted successfully!',
            'passport_num' => $passport_num,
            'passport_date' => $date_found
        ];
    } else {
        $response = [
            'error' => true,
            'message' => 'required parameters are not available'
        ];
    }
break;

//-------------------------------------------------------------------------------------------------------------
case 'getPfData':
    if (isTheseParametersAvailable(array('pf_text'))) {
        date_default_timezone_set('Asia/Kolkata');

        $pf_text = $_POST['pf_text'] ?? '';
        $pf_number = "NA";

        // Remove spaces around slashes for consistency
        $clean_text = preg_replace('/\s*\/\s*/', '', $pf_text);

        // Scan through text character by character
        $temp = "";
        for ($i = 0; $i < strlen($clean_text); $i++) {
            $char = $clean_text[$i];

            if (ctype_alnum($char)) { 
                $temp .= strtoupper($char); // add letters and digits
            } else {
                $temp = ""; // reset on any other character
            }

            // Check if we have 22 characters
            if (strlen($temp) === 22) {
                $pf_number = $temp;
                break; // stop after first valid PF number
            }
        }

        // RESPONSE
        $response = [
            'error' => false,
            'message' => 'PF number extracted successfully!',
            'pf_num' => $pf_number
        ];
    } else {
        $response = [
            'error' => true,
            'message' => 'required parameters are not available'
        ];
    }
break;


//---------------------------------------------------------------------------------------------------------------------------

case 'getParticularUserBanks':  

$user_id = $_POST['user_id'];


$stmt = $conn->prepare("SELECT id,bank_name,account_no,bank_ifsc,ac_holder_name,bank_address,bank_state,bank_city,bank_micr,card_no FROM user_bank_details where user_id =? and active = '0'");

$stmt->bind_param("s",$user_id);
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$bank_name,$account_no,$bank_ifsc,$ac_holder_name,$bank_address,$bank_state,$bank_city,$bank_micr,$card_no);  

    $banner_data = array(); 

    while($stmt->fetch()){

     $temp = array(); 
     $temp['id'] = $id; 
     $temp['bank_name']     = $bank_name; 
     $temp['account_number'] = $account_no; 
     $temp['bank_ifsc']     = $bank_ifsc; 
     $temp['ac_holder_name']= $ac_holder_name;
     $temp['bank_address']  = $bank_address;
     $temp['bank_state']    = $bank_state;
     $temp['bank_city']     = $bank_city;
     $temp['bank_micr']     = $bank_micr;
     $temp['card_no']       = $card_no;
     array_push($banner_data, $temp);
 }


 $response['error'] = false;   
 $response['message'] = 'User bank details got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = true;   
    $response['message'] = 'No user bank available!!'; 
    $response['user'] = array();
}  
//}  
//}  
break;  
//-----
//------------------------------------------------------------------------------------------------------ 
// case 'insertWorkOrder':  
//     if (isTheseParametersAvailable(array('raw_text'))) {  
//         date_default_timezone_set('Asia/Kolkata');  
        
//         $raw_text = $_POST['raw_text'] ?? '';

//         // Normalize text
//         $text = preg_replace('/\s+/', ' ', trim($raw_text));

//         // --- Company Name ---
//         $company_name = "";
//         if (preg_match('/CENTRAL\s+WAREHOUSING\s+CORPORATION/i', $text)) {
//             $company_name = "Central Warehousing Corporation";
//         }

//         // --- Location ---
//         $location = "";
//         if (preg_match('/Sector[\-\s]*\d+.*?Navi\s*Mumbai[\-\s]*\d{3,6}/i', $text, $m)) {
//             $location = trim($m[0]);
//         }

//         // --- Work Order No ---
//         $work_order_no = "";
//         if (preg_match('/Work\s*Order\s*No\s*[:\-]?\s*([A-Za-z0-9\/\-]+)/i', $text, $m)) {
//             $work_order_no = trim($m[1]);
//         }

//         // --- Work Order Date ---
//         $work_order_date = "";
//         if (preg_match('/Work\s*Order\s*Date\s*[:\-]?\s*([0-9]{1,2}[\-\/\.][A-Za-z]{3}[\-\/\.][0-9]{2,4})/i', $text, $m)) {
//             $date_raw = str_replace('.', '-', $m[1]);
//             $dateObj = date_create_from_format('d-M-Y', $date_raw);
//             if (!$dateObj) $dateObj = date_create_from_format('d-M-y', $date_raw);
//             if (!$dateObj) $dateObj = date_create_from_format('d-m-Y', $date_raw);
//             if ($dateObj) {
//                 $work_order_date = date_format($dateObj, 'Y-m-d');
//             } else {
//                 $work_order_date = $m[1];
//             }
//         } else {
//             // fallback for formats like "01-0ct-2025" (OCR "0ct" typo)
//             if (preg_match('/Work\s*Order\s*Date\s*[:\-]?\s*([0-9]{1,2}[\-\/\.][0O][C][Tt][\-\/\.][0-9]{2,4})/i', $text, $m)) {
//                 $work_order_date = str_ireplace(['0C', '0c'], 'OC', $m[1]);
//             }
//         }

//         // --- IGM No ---
//         $igm_no = "";
//         if (preg_match('/IGM\s*NO\s*[:\-]?\s*([A-Za-z0-9]+)/i', $text, $m)) {
//             $igm_no = trim($m[1]);
//         }

//         // --- Item No ---
//         $item_no = "";
//         if (preg_match('/Item\s*No\s*[:\-]?\s*([A-Za-z0-9]+)/i', $text, $m)) {
//             $item_no = trim($m[1]);
//         }

//    // --- CHA Name ---
// $cha_name = "";
// if (preg_match('/CHA\s*Name\s*[:\-]?\s*([A-Za-z&\.\s]+?)(?=\s*Imp[o|a]rt[e|o]r\s*Name|Imp[o|a]terName|$)/i', $text, $m)) {
//     $cha_name = trim($m[1]);
// } elseif (preg_match('/CHA\s*Name\s*[:\-]?\s*([A-Za-z&\.\s]+)/i', $text, $m)) {
//     $cha_name = trim($m[1]);
// }

// // --- Importer Name ---
// $importer_name = "";
// if (preg_match('/Imp[o|a]rt[e|o]r\s*Name\s*[:\-]?\s*([A-Za-z0-9&\.\s]+)/i', $text, $m)) {
//     $importer_name = trim($m[1]);
// }

//         // --- Response ---
//         $response = [
//             'error' => false,
//             'message' => 'Work Order data extracted successfully!',
//             'company_name' => $company_name,
//             'location' => $location,
//             'work_order_no' => $work_order_no,
//             'work_order_date' => $work_order_date,
//             'igm_no' => $igm_no,
//             'item_no' => $item_no,
//             'cha_name' => $cha_name,
//             'importer_name' => $importer_name
//         ];

//     } else {  
//         $response = [ 
//             'error' => true,   
//             'message' => 'Required parameter raw_text not available' 
//         ];   
//     }  
// break;


case 'insertWorkOrder':  
    if (isTheseParametersAvailable(array('raw_text'))) {  
        date_default_timezone_set('Asia/Kolkata'); 
        $created_on = date('Y-m-d H:i:s'); 

        $raw_text = $_POST['raw_text'] ?? '';
        $text = preg_replace('/\s+/', ' ', trim($raw_text)); // normalize whitespace

        // --- COMPANY NAME ---
        $company_name = "";
        if (preg_match('/CENTRAL\s+WAREHOUSING\s+CORPORATION/i', $text)) {
            $company_name = "Central Warehousing Corporation";
        }

        // --- LOCATION ---
        $location = "";
        if (preg_match('/(Sector[\-\s]*\d+.*?Navi\s*Mumbai[\-\s]*\d{3,6})/i', $text, $m)) {
            $location = trim($m[1]);
        }

        // --- WORK ORDER NO ---
        $work_order_no = "";
        if (preg_match('/Work\s*Order\s*No\s*[:\-]?\s*([A-Za-z0-9\/\-]+)/i', $text, $m)) {
            $work_order_no = trim($m[1]);
        } elseif (preg_match('/Wo\s*\/\s*[0-9]{2,4}\-[0-9]{2,4}\/[0-9A-Za-z]+/i', $text, $m)) {
            $work_order_no = trim($m[0]);
        }

        // --- WORK ORDER DATE ---
        $work_order_date = "";
        if (preg_match('/Work\s*Order\s*Date\s*[:\-]?\s*([0-9]{1,2}[\-\/\.][A-Za-z0-9]{3,5}[\-\/\.][0-9]{2,4})/i', $text, $m)) {
            $date_raw = str_replace(['.', '/', '0C', '0c'], ['-', '-', 'Oct', 'Oct'], $m[1]);
            $dateObj = date_create_from_format('d-M-Y', $date_raw);
            if (!$dateObj) $dateObj = date_create_from_format('d-M-y', $date_raw);
            if (!$dateObj) $dateObj = date_create_from_format('d-m-Y', $date_raw);
            if ($dateObj) $work_order_date = date_format($dateObj, 'Y-m-d');
            else $work_order_date = trim($date_raw);
        }

        // --- IGM NO ---
        $igm_no = "";
        if (preg_match('/IGM\s*(?:NO|No)?\s*[:\-]?\s*([A-Za-z0-9]+)/i', $text, $m)) {
            $igm_no = trim($m[1]);
        }

        // --- ITEM NO ---
        $item_no = "";
        if (preg_match('/Item\s*No\s*[:\-]?\s*([A-Za-z0-9]+)/i', $text, $m)) {
            $item_no = trim($m[1]);
        }

        // --- CHA NAME ---
        $cha_name = "";
        if (preg_match('/CHA\s*Name\s*[:\-]?\s*([A-Za-z&\.\,\(\)\s]+?)(?=\s*(Imp[o|a]rt[e|o]r\s*Name|AllowID|Cargo\s*Description|Vendor|On\s*Account|$))/i', $text, $m)) {
            $cha_name = trim($m[1]);
        }

        // --- IMPORTER NAME ---
        $importer_name = "";
        if (preg_match('/Imp[o|a]rt[e|o]r\s*Name\s*[:\-]?\s*([A-Za-z0-9&\.\,\(\)\s]+)/i', $text, $m)) {
            $importer_name = trim($m[1]);
        }

        // --- VENDOR NAME ---
        $vendor_name = "";
        if (preg_match('/Vendor\s*[:\-]?\s*([A-Za-z0-9&\.\,\(\)\s]+)/i', $text, $m)) {
            $vendor_name = trim($m[1]);
        }

        // --- ON ACCOUNT NAME ---
        $on_account_name = "";
        if (preg_match('/On\s*Account\s*Name\s*[:\-]?\s*([A-Za-z0-9&\.\,\(\)\s]+)/i', $text, $m)) {
            $on_account_name = trim($m[1]);
        }

     // --- CARGO DESCRIPTION ---
if (preg_match('/Cargo\s*Description\s*[:\-]?\s*(.+?)(?=\s*(On\s*Account|OnAccountName|Party\s*Name|$))/i', $text, $m)) {
    $cargo_description = trim($m[1]);
}


// --- PARTY NAME ---
if (preg_match('/Party\s*Name\s*[:\-]?\s*([A-Za-z0-9&\s]+?)(?=\s*(Sr\s|Container|$))/i', $text, $m)) {
    $party_name = trim($m[1]);
}



        // --- COMMODITY DESCRIPTION ---
        $commodity_description = "";
        if (preg_match('/Commodity\s*Description\s*[:\-]?\s*([A-Za-z0-9&\.\(\)\/\-\s]+?)(?=\s*(Surveyor|Vendor|Signature|$))/i', $text, $m)) {
            $commodity_description = trim($m[1]);
        }

        // --- SURVEYOR ---
        $surveyor = "";
        if (preg_match('/Surveyor\s*[:\-]?\s*([A-Za-z0-9&\.\,\(\)\s]+)/i', $text, $m)) {
            $surveyor = trim($m[1]);
        }

        // --- ALLOW ID ---
        $allow_id = "";
        if (preg_match('/Allow\s*ID\s*[:\-]?\s*([A-Za-z0-9]+)/i', $text, $m)) {
            $allow_id = trim($m[1]);
        }

       // --- WORK TYPE ---
$work_type = "";
if (preg_match('/(Import|Export)\s*-\s*Work\s*Order\s*For\s*-\s*([A-Za-z\s]+?)(?=\s*(Work\s*Order\s*No|CHA\s*Name|Cargo\s*Description|On\s*Account|Prepared\s*by|$))/i', $text, $m)) {
    $work_type = trim($m[2]);
}


 $stmt_work_order = $conn->prepare("INSERT INTO work_order (client_name,work_order_type,work_order_no,work_order_date,igm_no,importer_name,cha_name,vendor,cargo_description,on_account_name,party_name,allow_id,item_no,created_on) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); 

                     $stmt_work_order->bind_param("ssssssssssssss",$company_name,$work_type,$work_order_no,$work_order_date,$igm_no,$importer_name,$cha_name,$vendor_name,$cargo_description,$on_account_name,$party_name,$allow_id,$item_no,$created_on);
                     $stmt_work_order->execute();


        // --- RESPONSE ---
        $response = [
            'error' => false,
            'message' => 'Work Order data extracted successfully!',
            'company_name' => $company_name,
            'location' => $location,
            'work_order_no' => $work_order_no,
            'work_order_date' => $work_order_date,
            'igm_no' => $igm_no,
            'item_no' => $item_no,
            'cha_name' => $cha_name,
            'importer_name' => $importer_name,
            'vendor_name' => $vendor_name,
            'on_account_name' => $on_account_name,
            'cargo_description' => $cargo_description,
            'commodity_description' => $commodity_description,
            'surveyor' => $surveyor,
            'allow_id' => $allow_id,
            'work_type' => $work_type,
            'part_name' => $party_name
        ];

    } else {                                                                                                                                                                                  
        $response = [ 
            'error' => true,   
            'message' => 'Required parameter work order text not available' 
        ];   
    }  
break;
//----------------------------------------------------------------------------------------------------------
case 'getVoucherSplitDetails':  

$voucher_no = $_POST['voucher_no'];  
 

$stmt = $conn->prepare("SELECT amount,emp_name FROM emp_advance WHERE voucher_no = ? and active='0'");  
$stmt->bind_param("s",$voucher_no); 
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($amt,$name);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['amt'] = $amt; 
     $temp['name']=$name ;
     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Split details of voucher got successful!!';   
 $response['user'] = $banner_data;   
}  
else{  
    $response['error'] = false;   
    $response['message'] = 'Invalid id'; 
    $response['user'] = array();
}  
//}  
//}  
break; 

//-----------------------------------------------------------------------------------------------

case 'insertAttendance':

    if (isTheseParametersAvailable(array('file_base64'))) {

        date_default_timezone_set('Asia/Kolkata');
        $base64 = $_POST['file_base64'];

        if (strpos($base64, "base64,") !== false) {
            $base64 = explode("base64,", $base64)[1];
        }

        $fileContent = base64_decode($base64, true);

        if ($fileContent === false || strlen($fileContent) < 50) {
            $response = ['error' => true, 'message' => 'Invalid or corrupted Base64 file.'];
            break;
        }

        // Save uploaded debug file
        $debug_folder = __DIR__ . "/emp_docs/";
        $debug_file_path = $debug_folder . "upload_" . time() . ".jpg";
        file_put_contents($debug_file_path, $fileContent);

        // MIME detection
        $finfo = new finfo(FILEINFO_MIME_TYPE);
        $mime  = $finfo->buffer($fileContent);
        if (!$mime || $mime == "application/octet-stream") {
            $mime = "application/pdf";
        }

        // DOC AI Setup
        putenv('GOOGLE_APPLICATION_CREDENTIALS=' . __DIR__ . '/saiventure-workorderai-d13458269172.json');

        $projectId   = "77305643982";
        $location    = "us";
        $processorId = "c4e82c795e893fe3";
        $processorName = "projects/$projectId/locations/$location/processors/$processorId";

        $client = new DocumentProcessorServiceClient([
            'apiEndpoint' => "$location-documentai.googleapis.com"
        ]);

        $rawDocument = new RawDocument([
            'content'   => $fileContent,
            'mime_type' => $mime
        ]);

        $request = new ProcessRequest([
            'name'         => $processorName,
            'raw_document' => $rawDocument
        ]);

        try {

            // PROCESS DOCUMENT
            $result = $client->processDocument($request);
            $doc = $result->getDocument();
            $entities = $doc->getEntities();
            $fullText = $doc->getText();

            if (count($entities) === 0) {
                $response = ['error' => true, 'message' => 'No entities extracted. Check training or incorrect file format.'];
                break;
            }

            function extractTextSeg($fullText, $textAnchor) {
                if (!$textAnchor) return "";
                $segments = $textAnchor->getTextSegments();
                if (!$segments) return "";

                $txt = "";
                foreach ($segments as $seg) {
                    $start = $seg->getStartIndex();
                    $end   = $seg->getEndIndex();
                    $txt  .= substr($fullText, $start, $end - $start);
                }
                return trim($txt);
            }

            function entityToArray($doc, $entity) {
                $text = extractTextSeg($doc->getText(), $entity->getTextAnchor());
                $children = $entity->getProperties();

                $childArr = [];
                foreach ($children as $child) {
                    $childArr[] = entityToArray($doc, $child);
                }

                return [
                    "type"     => $entity->getType(),
                    "value"    => $text,
                    "children" => $childArr
                ];
            }

            // Convert Doc-AI to array
            $rows = [];
            foreach ($entities as $e) {
                $rows[] = entityToArray($doc, $e);
            }

            // ---------------------------------------------
            // NOW → INSERT ATTENDANCE INTO DATABASE
            // ---------------------------------------------
            $created_on = date("Y-m-d H:i:s");
            $created_by = 1;

            foreach ($rows as $sheet) {

                $empName = "";
                $empRank = "";

                // First pass — get name & rank
                foreach ($sheet['children'] as $item) {
                    if ($item['type'] === "name")  $empName = $item['value'];
                    if ($item['type'] === "rank")  $empRank = $item['value'];
                }

                // Second pass — insert each day row
                foreach ($sheet['children'] as $item) {

                    $type  = $item['type'];
                    $value = $item['value'];

                    // Detect "dayXX"
                    if (preg_match('/day(\d+)/', $type, $m)) {

                        $dayNumber = $m[1];   // e.g. 17
                        $status    = $value;  // e.g. P, A, PWO

                        // INSERT QUERY
                        $insertSql = "
                            INSERT INTO attendance 
                            (emp_name, rank, at_day, status, created_by, created_on)
                            VALUES 
                            ('$empName', '$empRank', '$dayNumber', '$status', $created_by, '$created_on')
                        ";

                        mysqli_query($conn, $insertSql);
                    }
                }
            }

            $response = [
                'error' => false,
                'message' => 'Attendance inserted successfully'
            ];

        } catch (Exception $ex) {
            $response = ['error' => true, 'message' => 'DOC AI Error: ' . $ex->getMessage()];
        }

        $client->close();

    } else {
        $response = ['error' => true, 'message' => 'Required parameters missing'];
    }

break;


//-----------------------------------------------------------------------------------------------------------------

case 'updateProfilePicture': 
    if(isTheseParametersAvailable(array('user_id'))){   
    
        $user_id = $_POST['user_id'];
        $image   = $_POST['image']; // base64 image content
        date_default_timezone_set('Asia/Kolkata');
        
        //location details
        $user_address   = $_POST['user_address']   ?? null;
        $user_city      = $_POST['user_city']      ?? null;
        $user_state     = $_POST['user_state']     ?? null;
        $user_pincode   = $_POST['user_pincode']   ?? null;
        $user_latitude  = $_POST['user_latitude']  ?? null;
        $user_longitude = $_POST['user_longitude'] ?? null;

        $modified_on    = date('Y-m-d H:i:s');
        $activity_type  = 'Profile Photo Update';

        // ------------------------
        // 2️⃣ Handle profile image
        // ------------------------
        $uploadDir = __DIR__ . "/profilephoto/";
        if (!is_dir($uploadDir)) {
            mkdir($uploadDir, 0777, true);
        }

        $profileFileName = "employee_" . $user_id . ".jpg";
        $profileFilePath = $uploadDir . $profileFileName;

        if(!empty($image)) {
            // decode base64 image
            $fileData = base64_decode($image);
            file_put_contents($profileFilePath, $fileData);

            $profileImageDB = "profilephoto/" . $profileFileName;

            // update users table with profile image
            $stmt = $conn->prepare("UPDATE users SET profile_image = ? WHERE id = ?");
            $stmt->bind_param("si", $profileImageDB, $user_id);
            $stmt->execute();
            $stmt->close();
        }

        // ------------------------
        // 3️⃣ Insert activity log
        // ------------------------
        $stmt_log = $conn->prepare(
            "INSERT INTO emp_activity_log
            (user_id, user_activity_type, user_address, user_city, user_state, user_pincode,
             user_latitude, user_longitude, created_by, created_on)
            VALUES (?,?,?,?,?,?,?,?,?,?)"
        );

        $stmt_log->bind_param(
            "ssssssssss",
            $user_id,
            $activity_type,
            $user_address,
            $user_city,
            $user_state,
            $user_pincode,
            $user_latitude,
            $user_longitude,
            $user_id,
            $modified_on
        );
        $stmt_log->execute();
        $stmt_log->close();

        $response['profile_image'] = IMGPATHPROFILE . $profileImageDB;
        $response['error'] = false;   
        $response['message'] = 'Profile photo updated successfully';   
    }  
    else{  
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';   
    }  
break;

//------------------------------------------------------------------------------------------------------

// case 'getMonthlyAttendance':

//     if (isTheseParametersAvailable(array('employee_code', 'month'))) {

//         date_default_timezone_set('Asia/Kolkata');

//         $employee_id = $_POST['employee_code'];
//         $month       = $_POST['month']; // YYYY-MM

//         // Month start and end dates
//         $start_date = date('Y-m-01', strtotime($month));
//         $end_date   = date('Y-m-t', strtotime($month)); // full month

//         // Optional: restrict till today if month is current
//         $today = date('Y-m-d');
//         if ($end_date > $today) {
//             $end_date = $today;
//         }

//         // Debug logs (can remove later)
//         // echo 'from date: '.$start_date.' to date: '.$end_date.' employee code: '.$employee_id;

//         // Fetch existing attendance records for this employee and month
//         $stmt = $conn->prepare("
//             SELECT
//                 check_in_date,
//                 check_in_time,
//                 check_out_time
//             FROM emp_attendance
//             WHERE emp_code = ?
//               AND check_in_date BETWEEN ? AND ?
//             ORDER BY check_in_date ASC
//         ");
//         $stmt->bind_param("sss", $employee_id, $start_date, $end_date);
//         $stmt->execute();
//         $result = $stmt->get_result();

//         // Store fetched records keyed by date
//         $attendanceRows = [];
//         while ($row = $result->fetch_assoc()) {
//             $attendanceRows[$row['check_in_date']] = $row;
//         }

//         $attendance = [];

//         // Loop through each date of the month
//         $period = new DatePeriod(
//             new DateTime($start_date),
//             new DateInterval('P1D'),
//             (new DateTime($end_date))->modify('+1 day') // include last day
//         );

//         foreach ($period as $dateObj) {
//             $date = $dateObj->format('Y-m-d');

//             // Default values for absent day
//             $status = "A"; 
//             $check_in = "--:--";
//             $check_out = "--:--";
//             $workedHours = 0;

//             // If there is attendance record for this date
//             if (isset($attendanceRows[$date])) {
//                 $row = $attendanceRows[$date];
//                 $check_in  = $row['check_in_time'] ?: "--:--";
//                 $check_out = $row['check_out_time'] ?: "--:--";

//                 if ($check_in != "--:--") {
//                     if ($check_out == "--:--") {
//                         $status = "HD"; // half day if no check-out
//                     } else {
//                         $in  = strtotime($date . ' ' . $check_in);
//                         $out = strtotime($date . ' ' . $check_out);
//                         $workedSeconds = max($out - $in, 0);
//                         $workedHours   = round($workedSeconds / 3600, 2);

//                         if ($workedHours >= 8) {
//                             $status = "P"; // full day
//                         } elseif ($workedHours >= 4) {
//                             $status = "HD"; // half day
//                         } else {
//                             $status = "A"; // less than 4 hours
//                         }
//                     }
//                 }
//             }

//             $attendance[] = [
//                 "date"         => $date,
//                 "check_in"     => $check_in,
//                 "check_out"    => $check_out,
//                 "worked_hours" => $workedHours,
//                 "status"       => $status
//             ];
//         }

//         // Return response
//         $response['error'] = false;
//         $response['month'] = $month;
//         $response['data']  = $attendance;

//     } else {

//         $response['error']   = true;
//         $response['message'] = 'Required parameters are not available';
//     }

// break;


// case 'getMonthlyAttendance':

//     if (isTheseParametersAvailable(array('employee_code', 'month'))) {

//         date_default_timezone_set('Asia/Kolkata');

//         $employee_id = $_POST['employee_code'];
//         $month       = $_POST['month']; // YYYY-MM

//         // Month start and end dates
//         $start_date = date('Y-m-01', strtotime($month));
//         $end_date   = date('Y-m-t', strtotime($month));

//         // Restrict till today if month is current
//         $today = date('Y-m-d');
//         if ($end_date > $today) {
//             $end_date = $today;
//         }

//         // ---------------------------------------------------
//         // 1️⃣ Fetch punch attendance (emp_attendance)
//         // ---------------------------------------------------
//         $stmt = $conn->prepare("
//             SELECT
//                 check_in_date,
//                 check_in_time,
//                 check_out_time
//             FROM emp_attendance
//             WHERE emp_code = ?
//               AND check_in_date BETWEEN ? AND ?
//             ORDER BY check_in_date ASC
//         ");
//         $stmt->bind_param("sss", $employee_id, $start_date, $end_date);
//         $stmt->execute();
//         $result = $stmt->get_result();

//         $attendanceRows = [];
//         while ($row = $result->fetch_assoc()) {
//             $attendanceRows[$row['check_in_date']] = $row;
//         }

//         // ---------------------------------------------------
//         // 2️⃣ Fetch office/manual attendance (attendance table)
//         // ---------------------------------------------------
//         $monthNum = (int)date('m', strtotime($month));
//         $yearNum  = (int)date('Y', strtotime($month));

//         $stmt2 = $conn->prepare("
//             SELECT
//                 at_day,
//                 status
//             FROM attendance
//             WHERE emp_code = ?
//               AND at_month = ?
//               AND at_year = ?
//               AND active = '0'
//         ");
//         $stmt2->bind_param("sii", $employee_id, $monthNum, $yearNum);
//         $stmt2->execute();
//         $res2 = $stmt2->get_result();

//         $officeAttendance = [];
//         while ($row = $res2->fetch_assoc()) {
//             $day = str_pad($row['at_day'], 2, '0', STR_PAD_LEFT);
//             $dateKey = $yearNum . '-' . str_pad($monthNum, 2, '0', STR_PAD_LEFT) . '-' . $day;
//             $officeAttendance[$dateKey] = $row['status'];
//         }

//         // ---------------------------------------------------
//         // 3️⃣ Build monthly attendance day by day
//         // ---------------------------------------------------
//         $attendance = [];

//         $period = new DatePeriod(
//             new DateTime($start_date),
//             new DateInterval('P1D'),
//             (new DateTime($end_date))->modify('+1 day')
//         );

//         foreach ($period as $dateObj) {

//             $date = $dateObj->format('Y-m-d');

//             // Default values
//             $status       = "A";
//             $check_in     = "--:--";
//             $check_out    = "--:--";
//             $workedHours  = 0;

//             //  Priority 1: Punch attendance
//             if (isset($attendanceRows[$date])) {

//                 $row = $attendanceRows[$date];
//                 $check_in  = $row['check_in_time'] ?: "--:--";
//                 $check_out = $row['check_out_time'] ?: "--:--";

//                 if ($check_in != "--:--") {

//                     if ($check_out == "--:--") {

//                         $status = "HD";

//                     } else {

//                         $in  = strtotime($date . ' ' . $check_in);
//                         $out = strtotime($date . ' ' . $check_out);

//                         $workedSeconds = max($out - $in, 0);
//                         $workedHours   = round($workedSeconds / 3600, 2);

//                         if ($workedHours >= 8) {
//                             $status = "P";
//                         } elseif ($workedHours >= 4) {
//                             $status = "HD";
//                         } else {
//                             $status = "A";
//                         }
//                     }
//                 }

//             //  Priority 2: Office/manual attendance
//             } elseif (isset($officeAttendance[$date])) {

//                 $status = $officeAttendance[$date];
//                 $check_in = "--:--";
//                 $check_out = "--:--";
//                 $workedHours = 0;
//             }

//             $attendance[] = [
//                 "date"         => $date,
//                 "check_in"     => $check_in,
//                 "check_out"    => $check_out,
//                 "worked_hours" => $workedHours,
//                 "status"       => $status
//             ];
//         }

//         // ---------------------------------------------------
//         // 4️⃣ Response
//         // ---------------------------------------------------
//         $response['error'] = false;
//         $response['month'] = $month;
//         $response['data']  = $attendance;

//     } else {

//         $response['error']   = true;
//         $response['message'] = 'Required parameters are not available';
//     }

// break;

// case 'getMonthlyAttendance':

//     if (isTheseParametersAvailable(array('employee_code', 'month'))) {

//         date_default_timezone_set('Asia/Kolkata');

//         $employee_id = $_POST['employee_code'];
//         $month       = $_POST['month']; // YYYY-MM

//         // Validate month format
//         if (!preg_match("/^\d{4}-\d{2}$/", $month)) {
//             $response['error'] = true;
//             $response['message'] = 'Invalid month format. Use YYYY-MM';
//             break;
//         }

//         // Month start and end dates
//         $start_date = date('Y-m-01', strtotime($month));
//         $end_date   = date('Y-m-t', strtotime($month));

//         // Restrict till today if current month
//         $today = date('Y-m-d');
//         if ($end_date > $today) {
//             $end_date = $today;
//         }

//         // ---------------------------------------------------
//         // 1️⃣ Fetch punch attendance
//         // ---------------------------------------------------
//         $stmt = $conn->prepare("
//             SELECT check_in_date, check_in_time, check_out_time
//             FROM emp_attendance
//             WHERE emp_code = ?
//               AND check_in_date BETWEEN ? AND ?
//             ORDER BY check_in_date ASC
//         ");
//         $stmt->bind_param("sss", $employee_id, $start_date, $end_date);
//         $stmt->execute();
//         $result = $stmt->get_result();

//         $attendanceRows = [];
//         while ($row = $result->fetch_assoc()) {
//             echo 'punch attendance';
//             print_r($row);
//             $attendanceRows[$row['check_in_date']] = $row;
//         }
//         $stmt->close();

//         // ---------------------------------------------------
//         // 2️⃣ Fetch manual attendance
//         // ---------------------------------------------------
//         $monthNum = (int)date('m', strtotime($month));
//         $yearNum  = (int)date('Y', strtotime($month));


//         print_r("month year");
//         echo $monthNum.' '.$yearNum;
//         $stmt2 = $conn->prepare("
//             SELECT at_day, status
//             FROM attendance
//             WHERE emp_code = ?
//               AND at_month = ?
//               AND at_year = ?
//               AND active = '0'
//         ");
//         $stmt2->bind_param("sii", $employee_id, $monthNum, $yearNum);
//         $stmt2->execute();
//         $res2 = $stmt2->get_result();

//         $officeAttendance = [];
//         while ($row = $res2->fetch_assoc()) {
//             echo 'office_attendance';
//             print_r($row);
//             $day = str_pad($row['at_day'], 2, '0', STR_PAD_LEFT);
//             $dateKey = $yearNum . '-' . str_pad($monthNum, 2, '0', STR_PAD_LEFT) . '-' . $day;
//             $officeAttendance[$dateKey] = strtoupper(trim($row['status']));
//         }
//         $stmt2->close();

//         // ---------------------------------------------------
//         // 3️⃣ Initialize summary
//         // ---------------------------------------------------
//         $attendance = [];
//         $totalPresent = 0;
//         $totalAbsent = 0;
//         $totalHalfDay = 0;
//         $totalWorkedHours = 0;

//         $period = new DatePeriod(
//             new DateTime($start_date),
//             new DateInterval('P1D'),
//             (new DateTime($end_date))->modify('+1 day')
//         );

//         foreach ($period as $dateObj) {

//             $date = $dateObj->format('Y-m-d');

//             $status = "A";
//             $check_in = "--:--";
//             $check_out = "--:--";
//             $workedHours = 0;

//             // ===============================
//             // PRIORITY 1: Punch Attendance
//             // ===============================
//             if (isset($attendanceRows[$date])) {

//                 $row = $attendanceRows[$date];

//                 $check_in  = !empty($row['check_in_time']) ? $row['check_in_time'] : "--:--";
//                 $check_out = !empty($row['check_out_time']) ? $row['check_out_time'] : "--:--";

//                 if ($check_in != "--:--" && $check_out != "--:--") {

//                     $in  = strtotime($date . ' ' . $check_in);
//                     $out = strtotime($date . ' ' . $check_out);

//                     if ($out < $in) {
//                         $out = strtotime('+1 day', $out); // night shift support
//                     }

//                     $workedSeconds = max($out - $in, 0);
//                     $workedHours = round($workedSeconds / 3600, 2);

//                     if ($workedHours >= 8) {
//                         $status = "P";
//                     } elseif ($workedHours >= 4) {
//                         $status = "HD";
//                     } else {
//                         $status = "A";
//                     }

//                 } elseif ($check_in != "--:--") {

//                     // Only check-in exists
//                     $status = "HD";
//                 }
//             }

//             // ===============================
//             // PRIORITY 2: Manual Attendance
//             // Only apply if no punch record
//             // ===============================
//             elseif (isset($officeAttendance[$date])) {

//                 $manualStatus = $officeAttendance[$date];

//                 if (in_array($manualStatus, ["P", "A", "HD", "W"])) {
//                     $status = $manualStatus;
//                 }
//             }

//             // ===============================
//             // Summary Count
//             // ===============================
//             switch ($status) {

//                 case "P":
//                     $totalPresent++;
//                     break;

//                 case "W":   // Weekly off counted as present
//                     $totalPresent++;
//                     break;

//                 case "HD":
//                     $totalHalfDay++;
//                     break;

//                 case "A":
//                 default:
//                     $totalAbsent++;
//                     break;
//             }

//             $totalWorkedHours += $workedHours;

//             // Add to response
//             $attendance[] = [
//                 "date"         => $date,
//                 "check_in"     => $check_in,
//                 "check_out"    => $check_out,
//                 "worked_hours" => $workedHours,
//                 "status"       => $status
//             ];
//         }

//         // ---------------------------------------------------
//         // Final Response
//         // ---------------------------------------------------
//         $response['error'] = false;
//         $response['month'] = $month;
//         $response['data'] = $attendance;
//         $response['summary'] = [
//             "total_present"    => $totalPresent,
//             "total_absent"     => $totalAbsent,
//             "total_half_day"   => $totalHalfDay,
//             "total_work_hours" => round($totalWorkedHours, 2)
//         ];

//     } else {

//         $response['error'] = true;
//         $response['message'] = 'Required parameters are not available';
//     }

// break;


case 'getMonthlyAttendance':

    if (isTheseParametersAvailable(array('employee_code', 'month'))) {

        date_default_timezone_set('Asia/Kolkata');

        $employee_id = $_POST['employee_code'];
        $month       = $_POST['month']; // YYYY-MM

        // Validate month format
        if (!preg_match("/^\d{4}-\d{2}$/", $month)) {
            $response['error'] = true;
            $response['message'] = 'Invalid month format. Use YYYY-MM';
            break;
        }

        // Month start & end
        $start_date = date('Y-m-01', strtotime($month));
        $end_date   = date('Y-m-t', strtotime($month));

        $today = date('Y-m-d');
        if ($end_date > $today) {
            $end_date = $today;
        }

        // ============================================
        // 1️⃣ Fetch Punch Attendance
        // ============================================
        $stmt = $conn->prepare("
            SELECT check_in_date, check_in_time, check_out_time
            FROM emp_attendance
            WHERE emp_code = ?
              AND check_in_date BETWEEN ? AND ?
            ORDER BY check_in_date ASC
        ");
        $stmt->bind_param("sss", $employee_id, $start_date, $end_date);
        $stmt->execute();
        $result = $stmt->get_result();

        $attendanceRows = [];
        while ($row = $result->fetch_assoc()) {
            $attendanceRows[$row['check_in_date']] = $row;
        }
        $stmt->close();

        // ============================================
        // 2️⃣ Fetch Office Attendance (PRIMARY SOURCE)
        // ============================================
        $monthNum = (int)date('m', strtotime($month));
        $yearNum  = (int)date('Y', strtotime($month));

        $stmt2 = $conn->prepare("
            SELECT at_day, status
            FROM attendance
            WHERE emp_code = ?
              AND at_month = ?
              AND at_year = ?
              AND active = '0'
        ");
        $stmt2->bind_param("sii", $employee_id, $monthNum, $yearNum);
        $stmt2->execute();
        $res2 = $stmt2->get_result();

        $officeAttendance = [];
        while ($row = $res2->fetch_assoc()) {
            $day = str_pad($row['at_day'], 2, '0', STR_PAD_LEFT);
            $dateKey = $yearNum . '-' . str_pad($monthNum, 2, '0', STR_PAD_LEFT) . '-' . $day;
          $officeAttendance[$dateKey] = strtoupper(trim((string)($row['status'] ?? '')));
        }
        $stmt2->close();

        // ============================================
        // 3️⃣ Initialize
        // ============================================
        $attendance = [];
        $totalPresent = 0;
        $totalAbsent = 0;
        $totalHalfDay = 0;
        $totalWorkedHours = 0;

        $totalSL = 0;
        $totalPL = 0;
        $totalCL = 0;
        $totalML = 0;
        $totalLeave = 0;

        $period = new DatePeriod(
            new DateTime($start_date),
            new DateInterval('P1D'),
            (new DateTime($end_date))->modify('+1 day')
        );

        foreach ($period as $dateObj) {

            $date = $dateObj->format('Y-m-d');

            $status = "A";
            $check_in = "--:--";
            $check_out = "--:--";
            $workedHours = 0;

            // ==================================================
            // ✅ PRIORITY 1: Office Attendance (PRIMARY)
            // ==================================================
            if (isset($officeAttendance[$date])) {

                $status = $officeAttendance[$date];

                // If punch exists, calculate hours (but DO NOT override status)
                if (isset($attendanceRows[$date])) {

                    $row = $attendanceRows[$date];

                    $check_in  = !empty($row['check_in_time']) ? $row['check_in_time'] : "--:--";
                    $check_out = !empty($row['check_out_time']) ? $row['check_out_time'] : "--:--";

                    if ($check_in != "--:--" && $check_out != "--:--" && $check_out != "00:00") {

                        $in  = strtotime($date . ' ' . $check_in);
                        $out = strtotime($date . ' ' . $check_out);

                        if ($out < $in) {
                            $out = strtotime('+1 day', $out);
                        }

                        $workedSeconds = max($out - $in, 0);
                        $workedHours = round($workedSeconds / 3600, 2);

                    } elseif ($check_in != "--:--") {

                        // ✅ If checkout missing → default 10 hours
                        $workedHours = 10;
                    }
                }
            }

            // ==================================================
            // ✅ PRIORITY 2: Punch Only (if no office entry)
            // ==================================================
            elseif (isset($attendanceRows[$date])) {

                $row = $attendanceRows[$date];

                $check_in  = !empty($row['check_in_time']) ? $row['check_in_time'] : "--:--";
                $check_out = !empty($row['check_out_time']) ? $row['check_out_time'] : "--:--";

                if ($check_in != "--:--" && $check_out != "--:--" && $check_out != "00:00") {

                    $in  = strtotime($date . ' ' . $check_in);
                    $out = strtotime($date . ' ' . $check_out);

                    if ($out < $in) {
                        $out = strtotime('+1 day', $out);
                    }

                    $workedSeconds = max($out - $in, 0);
                    $workedHours = round($workedSeconds / 3600, 2);

                    if ($workedHours >= 8) {
                        $status = "P";
                    } elseif ($workedHours >= 4) {
                        $status = "HD";
                    } else {
                        $status = "A";
                    }

                } elseif ($check_in != "--:--") {

                    // ✅ If only check-in → default 10 hours
                    $workedHours = 10;
                    $status = "P";
                }
            }

            // ==================================================
            // Summary Counting
            // ==================================================
      switch (strtoupper($status)) {

    case "P":
    case "W":
        $totalPresent++;
        break;
    case "H":
        $totalPresent++;
        break;

    case "HD":
        $totalHalfDay++;
        break;

    case "SL":
        $totalSL++;
       // $totalPresent++;
        break;

    case "PL":
        $totalPL++;
       // $totalPresent++;
        break;

    case "CL":
        $totalCL++;
      //  $totalPresent++;
        break;

    case "ML":
        $totalML++;
       // $totalPresent++;
        break;

    case "A":
    default:
        $totalAbsent++;
        break;
}

            $totalWorkedHours += $workedHours;

            $attendance[] = [
                "date"         => $date,
                "check_in"     => $check_in,
                "check_out"    => $check_out,
                "worked_hours" => $workedHours,
                "status"       => $status
            ];
        }

        $totalLeave = $totalSL + $totalPL + $totalCL + $totalML;

        // ============================================
        // Final Response
        // ============================================
        $response['error'] = false;
        $response['month'] = $month;
        $response['data'] = $attendance;
        $response['summary'] = [
            "total_present"    => $totalPresent,
            "total_absent"     => $totalAbsent,
            "total_half_day"   => $totalHalfDay,
            "total_work_hours" => round($totalWorkedHours, 2),
             "sl" => $totalSL,
            "pl" => $totalPL,
            "cl" => $totalCL,
            "ml" => $totalML,
            "total_leave" => $totalLeave
        ];

    } else {

        $response['error'] = true;
        $response['message'] = 'Required parameters are not available';
    }

break;



// case 'getAttendanceByDateRange':

//     if (isTheseParametersAvailable(array('employee_code', 'from_date', 'to_date'))) {

//         date_default_timezone_set('Asia/Kolkata');

//         $employee_id = $_POST['employee_code'];
//         $start_date  = $_POST['from_date']; // Y-m-d
//         $end_date    = $_POST['to_date'];   // Y-m-d

//         // Restrict till today if end_date is future
//         $today = date('Y-m-d');
//         if ($end_date > $today) {
//             $end_date = $today;
//         }

//         // ---------------------------------------------------
//         // 1️⃣ Fetch punch attendance (emp_attendance)
//         // ---------------------------------------------------
//         // $stmt = $conn->prepare("
//         //     SELECT
//         //         check_in_date,
//         //         check_in_time,
//         //         check_out_time
//         //     FROM emp_attendance
//         //     WHERE emp_code = ?
//         //       AND check_in_date BETWEEN ? AND ?
//         //     ORDER BY check_in_date ASC
//         // ");

//         $stmt = $conn->prepare("
//     SELECT
//         DATE(check_in_date) as check_in_date,
//         check_in_time,
//         check_out_time
//     FROM emp_attendance
//     WHERE emp_code = ?
//       AND DATE(check_in_date) BETWEEN ? AND ?
//     ORDER BY check_in_date ASC
// ");

//         $stmt->bind_param("sss", $employee_id, $start_date, $end_date);
//         $stmt->execute();
//         $result = $stmt->get_result();

//         $attendanceRows = [];
//         while ($row = $result->fetch_assoc()) {
//             $attendanceRows[$row['check_in_date']] = $row;
//         }

//         // ---------------------------------------------------
//         // 2️⃣ Fetch office/manual attendance (attendance table)
//         // ---------------------------------------------------
//         $stmt2 = $conn->prepare("
//             SELECT
//                 at_day,
//                 at_month,
//                 at_year,
//                 status
//             FROM attendance
//             WHERE emp_code = ?
//               AND active = '0'
//               AND STR_TO_DATE(
//                     CONCAT(at_year, '-', LPAD(at_month,2,'0'), '-', LPAD(at_day,2,'0')),
//                     '%Y-%m-%d'
//                   ) BETWEEN ? AND ?
//         ");
//         $stmt2->bind_param("sss", $employee_id, $start_date, $end_date);
//         $stmt2->execute();
//         $res2 = $stmt2->get_result();

//         $officeAttendance = [];
//         while ($row = $res2->fetch_assoc()) {
//             $dateKey = $row['at_year'] . '-' .
//                        str_pad($row['at_month'], 2, '0', STR_PAD_LEFT) . '-' .
//                        str_pad($row['at_day'], 2, '0', STR_PAD_LEFT);

//             $officeAttendance[$dateKey] = $row['status'];
//         }

//         // ---------------------------------------------------
//         // 3️⃣ Initialize summary counters
//         // ---------------------------------------------------
//         $attendance = [];
//         $totalPresent   = 0;
//         $totalAbsent    = 0;
//         $totalHalfDay   = 0;
//         $totalWorkedHours = 0;

//         $period = new DatePeriod(
//             new DateTime($start_date),
//             new DateInterval('P1D'),
//             (new DateTime($end_date))->modify('+1 day')
//         );

//         foreach ($period as $dateObj) {

//             $date = $dateObj->format('Y-m-d');

//             // Default values
//             $status       = "A";
//             $check_in     = "--:--";
//             $check_out    = "--:--";
//             $workedHours  = 0;

//             //  Priority 1: Punch attendance
//             if (isset($attendanceRows[$date])) {

//                 $row = $attendanceRows[$date];
//                 $check_in  = $row['check_in_time'] ?: "--:--";
//                 $check_out = $row['check_out_time'] ?: "--:--";

//                 // if ($check_in != "--:--") {

//                 //     if ($check_out == "--:--") {

//                 //         $status = "P";

//                 //     } else {

//                 //         $in  = strtotime($date . ' ' . $check_in);
//                 //         $out = strtotime($date . ' ' . $check_out);

//                 //         $workedSeconds = max($out - $in, 0);
//                 //         $workedHours   = round($workedSeconds / 3600, 2);

//                 //         if ($workedHours >= 8) {
//                 //             $status = "P";
//                 //         } elseif ($workedHours >= 4) {
//                 //             $status = "HD";
//                 //         } else {
//                 //             $status = "P";
//                 //         }
//                 //     }
//                 // }

//                 if ($check_in != "--:--") {

//     // If record exists, mark Present
//     $status = "P";

// }


//             //  Priority 2: Office/manual attendance
//             } elseif (isset($officeAttendance[$date])) {

//                 $status = $officeAttendance[$date];
//                 $check_in = "--:--";
//                 $check_out = "--:--";
//                 $workedHours = 0;
//             }

//             // ---------------------------------------------------
//             // 4️⃣ Update summary counters
//             // ---------------------------------------------------
//             switch ($status) {
//                 case "P":
//                     $totalPresent++;
//                     break;
//                 case "HD":
//                     $totalHalfDay++;
//                     break;
//                 case "A":
//                     $totalAbsent++;
//                     break;
//             }

//             $totalWorkedHours += $workedHours;

//             // ---------------------------------------------------
//             // 5️⃣ Add to daily attendance array
//             // ---------------------------------------------------
//             $attendance[] = [
//                 "date"         => $date,
//                 "check_in"     => $check_in,
//                 "check_out"    => $check_out,
//                 "worked_hours" => $workedHours,
//                 "status"       => $status
//             ];
//         }

//         // ---------------------------------------------------
//         // 6️⃣ Response
//         // ---------------------------------------------------
//         $response['error'] = false;
//         $response['from_date'] = $start_date;
//         $response['to_date']   = $end_date;
//         $response['data']  = $attendance;
//         $response['summary'] = [
//             "total_present"    => $totalPresent,
//             "total_absent"     => $totalAbsent,
//             "total_half_day"   => $totalHalfDay,
//             "total_work_hours" => round($totalWorkedHours, 2)
//         ];

//     } else {

//         $response['error']   = true;
//         $response['message'] = 'Required parameters are not available';
//     }

// break;

// case 'getAttendanceByDateRange':

//     if (isTheseParametersAvailable(array('employee_code', 'from_date', 'to_date'))) {

//         date_default_timezone_set('Asia/Kolkata');

//         $employee_id = $_POST['employee_code'];
//         $start_date  = $_POST['from_date'];
//         $end_date    = $_POST['to_date'];

//         $today = date('Y-m-d');
//         if ($end_date > $today) {
//             $end_date = $today;
//         }

//         /* ---------------- Punch Attendance ---------------- */
//         $stmt = $conn->prepare("
//             SELECT
//                 DATE(check_in_date) as att_date,
//                 check_in_time,
//                 check_out_time
//             FROM emp_attendance
//             WHERE emp_code = ?
//               AND DATE(check_in_date) BETWEEN ? AND ?
//         ");

//         $stmt->bind_param("sss", $employee_id, $start_date, $end_date);
//         $stmt->execute();
//         $result = $stmt->get_result();

//         $punchAttendance = [];

//         while ($row = $result->fetch_assoc()) {

//             $punchAttendance[$row['att_date']] = [
//                 'check_in'  => !empty($row['check_in_time']) ? $row['check_in_time'] : "--:--",
//                 'check_out' => !empty($row['check_out_time']) ? $row['check_out_time'] : "--:--"
//             ];
//         }

//         /* ---------------- Manual Attendance ---------------- */
//         $stmt2 = $conn->prepare("
//             SELECT
//                 at_day,
//                 at_month,
//                 at_year,
//                 status
//             FROM attendance
//             WHERE emp_code = ?
//               AND active = '0'
//               AND STR_TO_DATE(
//                     CONCAT(at_year, '-', LPAD(at_month,2,'0'), '-', LPAD(at_day,2,'0')),
//                     '%Y-%m-%d'
//                   ) BETWEEN ? AND ?
//         ");

//         $stmt2->bind_param("sss", $employee_id, $start_date, $end_date);
//         $stmt2->execute();
//         $res2 = $stmt2->get_result();

//         $manualAttendance = [];

//         while ($row = $res2->fetch_assoc()) {

//             $dateKey = $row['at_year'] . '-' .
//                        str_pad($row['at_month'], 2, '0', STR_PAD_LEFT) . '-' .
//                        str_pad($row['at_day'], 2, '0', STR_PAD_LEFT);

//             $manualAttendance[$dateKey] = $row['status'];
//         }

//         /* ---------------- Build Range ---------------- */
//         $attendance = [];

//         $totalPresent = 0;
//         $totalAbsent = 0;
//         $totalHalfDay = 0;
//         $totalLeaveDay = 0;
//         $totalDays = 0;

//         $period = new DatePeriod(
//             new DateTime($start_date),
//             new DateInterval('P1D'),
//             (new DateTime($end_date))->modify('+1 day')
//         );

//         foreach ($period as $dateObj) {

//             $date = $dateObj->format('Y-m-d');
//             $totalDays++;

//             $status = "A";
//             $check_in = "--:--";
//             $check_out = "--:--";

//             // Manual override
//             if (isset($manualAttendance[$date])) {

//                 $status = $manualAttendance[$date];

//             }
//             // Punch record
//             elseif (isset($punchAttendance[$date])) {

//                 $check_in  = $punchAttendance[$date]['check_in'];
//                 $check_out = $punchAttendance[$date]['check_out'];

//                 if ($check_in != "--:--") {
//                     $status = "P";
//                 }
//             }

//             // Count summary
//             switch ($status) {
//                 case "P":
//                     $totalPresent++;
//                     break;

//                 case "HD":
//                     $totalHalfDay++;
//                     break;

//                 case "W":
//                     $totalLeaveDay++;
//                     break;

//                 case "A":
//                 default:
//                     $totalAbsent++;
//                     break;
//             }

//             $attendance[] = [
//                 "date" => $date,
//                 "check_in" => $check_in,
//                 "check_out" => $check_out,
//                 "worked_hours" => 0,
//                 "status" => $status
//             ];
//         }

//         /* ---------------- Response ---------------- */
//         $response['error'] = false;
//         $response['from_date'] = $start_date;
//         $response['to_date'] = $end_date;
//         $response['data'] = $attendance;
//         $response['summary'] = [
//             "total_days" => $totalDays,
//             "total_present" => $totalPresent,
//             "total_absent" => $totalAbsent,
//             "total_half_day" => $totalHalfDay,
//             "total_leave_day" => $totalLeaveDay,
//             "total_work_hours" => 0
//         ];

//     } else {

//         $response['error'] = true;
//         $response['message'] = 'Required parameters are not available';
//     }

// break;


case 'getAttendanceByDateRange':

    if (isTheseParametersAvailable(array('employee_code', 'from_date', 'to_date'))) {

        date_default_timezone_set('Asia/Kolkata');

        $employee_id = $_POST['employee_code'];
        $start_date  = $_POST['from_date'];
        $end_date    = $_POST['to_date'];

        $today = date('Y-m-d');
        if ($end_date > $today) {
            $end_date = $today;
        }

        /* ---------------- Manual Attendance (status priority) ---------------- */
        $stmt2 = $conn->prepare("
            SELECT
                at_day,
                at_month,
                at_year,
                status
            FROM attendance
            WHERE emp_code = ?
              AND active = '0'
              AND STR_TO_DATE(
                    CONCAT(at_year, '-', LPAD(at_month,2,'0'), '-', LPAD(at_day,2,'0')),
                    '%Y-%m-%d'
                  ) BETWEEN ? AND ?
        ");
        $stmt2->bind_param("sss", $employee_id, $start_date, $end_date);
        $stmt2->execute();
        $res2 = $stmt2->get_result();

        $manualAttendance = [];
        while ($row = $res2->fetch_assoc()) {

           // echo "Manual Raw Status: [" . $row['status'] . "]<br>";

            $dateKey = $row['at_year'] . '-' .
                       str_pad($row['at_month'], 2, '0', STR_PAD_LEFT) . '-' .
                       str_pad($row['at_day'], 2, '0', STR_PAD_LEFT);

            $manualAttendance[$dateKey] = $row['status'];
        }

        /* ---------------- Punch Attendance ---------------- */
        $stmt = $conn->prepare("
            SELECT
                DATE(check_in_date) as att_date,
                check_in_time,
                check_out_time
            FROM emp_attendance
            WHERE emp_code = ?
              AND DATE(check_in_date) BETWEEN ? AND ?
        ");
        $stmt->bind_param("sss", $employee_id, $start_date, $end_date);
        $stmt->execute();
        $result = $stmt->get_result();

        $punchAttendance = [];
        while ($row = $result->fetch_assoc()) {
            $punchAttendance[$row['att_date']] = [
                'check_in'  => !empty($row['check_in_time']) ? $row['check_in_time'] : "--:--",
                'check_out' => !empty($row['check_out_time']) ? $row['check_out_time'] : "--:--"
            ];
        }

        /* ---------------- Build Range ---------------- */
        $attendance = [];
        $totalPresent = 0;
        $totalAbsent = 0;
        $totalHalfDay = 0;
        $totalDays = 0;

        // leaves count 
        $totalSL = 0;
        $totalPL = 0;
        $totalCL = 0;
        $totalML = 0;
        $totalLeave = 0;

        $period = new DatePeriod(
            new DateTime($start_date),
            new DateInterval('P1D'),
            (new DateTime($end_date))->modify('+1 day')
        );
foreach ($period as $dateObj) {



    $date = $dateObj->format('Y-m-d');

   // echo "Date: $date | Status Before Switch: [" . $status . "]<br>";

    $status = null; // ❗ Do NOT default to A
    $check_in = "--:--";
    $check_out = "--:--";

    $isToday = ($date == date('Y-m-d'));

    // Manual attendance overrides punches
    if (isset($manualAttendance[$date])) {
        $status = $manualAttendance[$date];
    }
    // Punch record only if no manual entry
    elseif (isset($punchAttendance[$date])) {
        $check_in  = $punchAttendance[$date]['check_in'];
        $check_out = $punchAttendance[$date]['check_out'];

        if ($check_in != "--:--") {
            $status = "P";
        }
    }
    // If no record exists
    else {
        if ($date < date('Y-m-d')) {
            $status = "A"; // Past dates = absent
        } else {
            $status = ""; // Today or future = blank
        }
    }

    // 🔥 Only count days that have final status
    if (!empty($status)) {
        $totalDays++;

        switch (strtoupper($status)) {
           case "P":
    case "W":
        $totalPresent++;
       // echo "Present Incremented (P/W). Total Now: $totalPresent <br>";
        break;

    case "H":
        $totalPresent++;
      //  echo "Present Incremented (H). Total Now: $totalPresent <br>";
        break;
            case "HF":
                $totalHalfDay++;
                break;

            case "A":
                $totalAbsent++;
                break;

            case "SL":
                $totalSL++;
               // $totalPresent++;
                break;

            case "PL":
                $totalPL++;
                //$totalPresent++;
                break;

            case "CL":
                $totalCL++;
               // $totalPresent++;
                break;

            case "ML":
                $totalML++;
               // $totalPresent++;
                break;
        }
    }

   

    $attendance[] = [
        "date" => $date,
        "check_in" => $check_in,
        "check_out" => $check_out,
        "worked_hours" => 0,
        "status" => $status
    ];
}

 $totalLeave = $totalSL + $totalPL + $totalCL + $totalML;

/* ---------------- Response ---------------- */
$response['error'] = false;
$response['from_date'] = $start_date;
$response['to_date'] = $end_date;
$response['data'] = $attendance;
$response['summary'] = [
    "total_days" => $totalDays,
    "total_present" => $totalPresent,
    "total_absent" => $totalAbsent,
    "total_half_day" => $totalHalfDay,
    "total_work_hours" => 0,
    "sl" => $totalSL,
    "pl" => $totalPL,
    "cl" => $totalCL,
    "ml" => $totalML,
    "total_leave" => $totalLeave
];
}
  else {
        $response['error'] = true;
        $response['message'] = 'Required parameters are not available';
    }

break;


case 'getAttendanceByDateRangeForDashboard':

    if (isTheseParametersAvailable(array('employee_code', 'from_date', 'to_date'))) {

        date_default_timezone_set('Asia/Kolkata');

        $employee_id = $_POST['employee_code'];
        $start_date  = $_POST['from_date'];
        $end_date    = $_POST['to_date'];

        $today = date('Y-m-d');
        if ($end_date > $today) {
            $end_date = $today;
        }

        /* ---------------- Manual Attendance (status priority) ---------------- */
        $stmt2 = $conn->prepare("
            SELECT at_day, at_month, at_year, status
            FROM attendance
            WHERE emp_code = ?
              AND active = '0'
              AND STR_TO_DATE(
                    CONCAT(at_year, '-', LPAD(at_month,2,'0'), '-', LPAD(at_day,2,'0')),
                    '%Y-%m-%d'
                  ) BETWEEN ? AND ?
        ");
        $stmt2->bind_param("sss", $employee_id, $start_date, $end_date);
        $stmt2->execute();
        $res2 = $stmt2->get_result();

        $manualAttendance = [];
        while ($row = $res2->fetch_assoc()) {
            $dateKey = $row['at_year'] . '-' .
                       str_pad($row['at_month'], 2, '0', STR_PAD_LEFT) . '-' .
                       str_pad($row['at_day'], 2, '0', STR_PAD_LEFT);
            $manualAttendance[$dateKey] = strtoupper(trim($row['status']));
        }

        /* ---------------- Punch Attendance ---------------- */
        $stmt = $conn->prepare("
            SELECT DATE(check_in_date) as att_date, check_in_time, check_out_time
            FROM emp_attendance
            WHERE emp_code = ?
              AND DATE(check_in_date) BETWEEN ? AND ?
        ");
        $stmt->bind_param("sss", $employee_id, $start_date, $end_date);
        $stmt->execute();
        $result = $stmt->get_result();

        $punchAttendance = [];
        while ($row = $result->fetch_assoc()) {
            $punchAttendance[$row['att_date']] = [
                'check_in'  => !empty($row['check_in_time']) ? $row['check_in_time'] : "--:--",
                'check_out' => !empty($row['check_out_time']) ? $row['check_out_time'] : "--:--"
            ];
        }

        /* ---------------- Build Range ---------------- */
        $attendance = [];
        $totalPresent = 0;
        $totalAbsent = 0;
        $totalHalfDay = 0;
        $totalDays = 0;

        // leaves count 
        $totalSL = 0;
        $totalPL = 0;
        $totalCL = 0;
        $totalML = 0;
        $totalLeave = 0;

        $period = new DatePeriod(
            new DateTime($start_date),
            new DateInterval('P1D'),
            (new DateTime($end_date))->modify('+1 day')
        );

        foreach ($period as $dateObj) {
            $date = $dateObj->format('Y-m-d');

            // Skip Sundays
            if (date('N', strtotime($date)) == 7) {
                continue;
            }

            $status = null;
            $check_in = "--:--";
            $check_out = "--:--";

            // Manual attendance overrides punches
            if (isset($manualAttendance[$date])) {
                $status = $manualAttendance[$date];
            } 
            elseif (isset($punchAttendance[$date])) {
                $check_in  = $punchAttendance[$date]['check_in'];
                $check_out = $punchAttendance[$date]['check_out'];

                if ($check_in != "--:--") {
                    $status = "P";
                }
            } 
            else {
                if ($date < $today) {
                    $status = "A"; // Past dates = absent
                } else {
                    $status = ""; // Today/future = blank
                }
            }

            $status = strtoupper(trim($status));

            // 🔥 Only count days with a status
            if (!empty($status)) {
                $totalDays++;

                switch ($status) {
                    case "P":
                    case "H":
                        $totalPresent++;
                        break;
                    case "HF":
                        $totalHalfDay++;
                        break;
                    case "A":
                        $totalAbsent++;
                        break;
                    case "SL":
                        $totalSL++;
                        break;
                    case "PL":
                        $totalPL++;
                        break;
                    case "CL":
                        $totalCL++;
                        break;
                    case "ML":
                        $totalML++;
                        break;
                }
            }

            $attendance[] = [
                "date" => $date,
                "check_in" => $check_in,
                "check_out" => $check_out,
                "worked_hours" => 0,
                "status" => $status
            ];
        }

        $totalLeave = $totalSL + $totalPL + $totalCL + $totalML;

        /* ---------------- Response (Dashboard included) ---------------- */
        $response['error'] = false;
        $response['from_date'] = $start_date;
        $response['to_date']   = $end_date;
        $response['data']      = $attendance;
        $response['summary'] = [
            "total_days"      => $totalDays,
            "total_present"   => $totalPresent,
            "total_absent"    => $totalAbsent,
            "total_half_day"  => $totalHalfDay,
            "total_work_hours"=> 0,
            "sl"              => $totalSL,
            "pl"              => $totalPL,
            "cl"              => $totalCL,
            "ml"              => $totalML,
            "total_leave"     => $totalLeave
        ];

    } else {
        $response['error'] = true;
        $response['message'] = 'Required parameters are not available';
    }

break;

case 'getAttendanceMonthWiseSummary':

    if (isTheseParametersAvailable(array('employee_code','from_date','to_date'))) {

        date_default_timezone_set('Asia/Kolkata');

        $employee_id = $_POST['employee_code'];
        $start_date  = $_POST['from_date'];
        $end_date    = $_POST['to_date'];

        $today = date('Y-m-d');
        if ($end_date > $today) {
            $end_date = $today;
        }

        /* ---------------- Manual Attendance ---------------- */
        $stmt2 = $conn->prepare("
            SELECT at_day, at_month, at_year, status
            FROM attendance
            WHERE emp_code = ?
            AND active = '0'
            AND STR_TO_DATE(
                CONCAT(at_year,'-',LPAD(at_month,2,'0'),'-',LPAD(at_day,2,'0')),
                '%Y-%m-%d'
            ) BETWEEN ? AND ?
        ");
        $stmt2->bind_param("sss",$employee_id,$start_date,$end_date);
        $stmt2->execute();
        $res2 = $stmt2->get_result();

        $manualAttendance = [];

        while ($row = $res2->fetch_assoc()) {

            $dateKey =
                $row['at_year']."-".
                str_pad($row['at_month'],2,'0',STR_PAD_LEFT)."-".
                str_pad($row['at_day'],2,'0',STR_PAD_LEFT);

            $manualAttendance[$dateKey] = strtoupper(trim($row['status']));
        }

        /* ---------------- Punch Attendance ---------------- */
        $stmt = $conn->prepare("
            SELECT DATE(check_in_date) as att_date, check_in_time
            FROM emp_attendance
            WHERE emp_code = ?
            AND DATE(check_in_date) BETWEEN ? AND ?
        ");
        $stmt->bind_param("sss",$employee_id,$start_date,$end_date);
        $stmt->execute();
        $result = $stmt->get_result();

        $punchAttendance = [];

        while ($row = $result->fetch_assoc()) {

            $punchAttendance[$row['att_date']] =
                !empty($row['check_in_time']) ? "P" : "";
        }

        /* ---------------- Monthly Storage ---------------- */

        $months = [];

        $period = new DatePeriod(
            new DateTime($start_date),
            new DateInterval('P1D'),
            (new DateTime($end_date))->modify('+1 day')
        );

        foreach ($period as $dateObj) {

            $date = $dateObj->format('Y-m-d');

            // Skip Sundays
            if (date('N',strtotime($date)) == 7) {
                continue;
            }

            $status = "";

            if (isset($manualAttendance[$date])) {

                $status = $manualAttendance[$date];

            } elseif (isset($punchAttendance[$date])) {

                $status = "P";

            } else {

                if ($date < $today) {
                    $status = "A";
                }

            }

            $status = strtoupper(trim($status));

            if ($status == "") {
                continue;
            }

            /* ----------- Month Key ----------- */

            $monthKey  = date('Y-m',strtotime($date));
            $monthName = date('M Y',strtotime($date));

            if (!isset($months[$monthKey])) {

                $months[$monthKey] = [
                    "month" => $monthName,
                    "year" => date('Y',strtotime($date)),
                    "month_number" => date('m',strtotime($date)),
                    "total_days" => 0,
                    "present" => 0,
                    "absent" => 0,
                    "half_day" => 0,
                    "sl" => 0,
                    "pl" => 0,
                    "cl" => 0,
                    "ml" => 0
                ];
            }

            $months[$monthKey]["total_days"]++;

            switch ($status) {

                case "P":
                case "H":
                    $months[$monthKey]["present"]++;
                    break;

                case "HF":
                    $months[$monthKey]["half_day"]++;
                    break;

                case "A":
                    $months[$monthKey]["absent"]++;
                    break;

                case "SL":
                    $months[$monthKey]["sl"]++;
                    break;

                case "PL":
                    $months[$monthKey]["pl"]++;
                    break;

                case "CL":
                    $months[$monthKey]["cl"]++;
                    break;

                case "ML":
                    $months[$monthKey]["ml"]++;
                    break;
            }

        }

        /* ---------------- Response ---------------- */

        $response['error'] = false;
        $response['from_date'] = $start_date;
        $response['to_date'] = $end_date;
        $response['data'] = array_values($months);

    } else {

        $response['error'] = true;
        $response['message'] = 'Required parameters are not available';

    }

break;
// case 'attendanceSummaryTableFormat':

//     if (isTheseParametersAvailable(array('employee_code', 'from_date', 'to_date'))) {

//         date_default_timezone_set('Asia/Kolkata');

//         $employee_id = $_POST['employee_code'];
//         $start_date  = $_POST['from_date'];
//         $end_date    = $_POST['to_date'];

//         $today = date('Y-m-d');
//         if ($end_date > $today) {
//             $end_date = $today;
//         }

//         /* ---------------- Manual Attendance ---------------- */
//         $stmt2 = $conn->prepare("
//             SELECT at_day, at_month, at_year, status
//             FROM attendance
//             WHERE emp_code = ?
//               AND active = '0'
//               AND STR_TO_DATE(
//                     CONCAT(at_year,'-',LPAD(at_month,2,'0'),'-',LPAD(at_day,2,'0')),
//                     '%Y-%m-%d'
//                   ) BETWEEN ? AND ?
//         ");
//         $stmt2->bind_param("sss", $employee_id, $start_date, $end_date);
//         $stmt2->execute();
//         $res2 = $stmt2->get_result();

//         $manualAttendance = [];
//         while ($row = $res2->fetch_assoc()) {
//             $dateKey = $row['at_year'].'-'.str_pad($row['at_month'],2,'0',STR_PAD_LEFT).'-'.str_pad($row['at_day'],2,'0',STR_PAD_LEFT);
//             $manualAttendance[$dateKey] = strtoupper($row['status']);
//         }

//         /* ---------------- Punch Attendance ---------------- */
//         $stmt = $conn->prepare("
//             SELECT DATE(check_in_date) as att_date, check_in_time, check_out_time
//             FROM emp_attendance
//             WHERE emp_code = ?
//               AND DATE(check_in_date) BETWEEN ? AND ?
//         ");
//         $stmt->bind_param("sss", $employee_id, $start_date, $end_date);
//         $stmt->execute();
//         $result = $stmt->get_result();

//         $punchAttendance = [];
//         while ($row = $result->fetch_assoc()) {
//             $punchAttendance[$row['att_date']] = [
//                 'check_in'  => !empty($row['check_in_time']) ? $row['check_in_time'] : "--:--",
//                 'check_out' => !empty($row['check_out_time']) ? $row['check_out_time'] : "--:--"
//             ];
//         }

//         /* ---------------- Build Daily Attendance ---------------- */
//         $attendance = [];
//         $totalPresent = $totalAbsent = $totalHalfDay = 0;
//         $totalSL = $totalPL = $totalCL = $totalML = $totalHFCL = 0;
//         $totalDays = 0;

//         $period = new DatePeriod(
//             new DateTime($start_date),
//             new DateInterval('P1D'),
//             (new DateTime($end_date))->modify('+1 day')
//         );

//         foreach ($period as $dateObj) {

//             $date = $dateObj->format('Y-m-d');
//             $status = "";
//             $check_in = "--:--";
//             $check_out = "--:--";

//             // Manual overrides
//             if (isset($manualAttendance[$date])) {
//                 $status = $manualAttendance[$date];
//             } 
//             elseif (isset($punchAttendance[$date])) {
//                 $check_in = $punchAttendance[$date]['check_in'];
//                 $check_out = $punchAttendance[$date]['check_out'];
//                 if ($check_in != "--:--") $status = "P";
//             } 
//             else {
//                 if ($date < $today) $status = "A"; // past days absent
//             }

//             // Increment counters
//             if (!empty($status)) {
//                 $totalDays++;
//                 switch ($status) {
//                     case "P": case "W":
//                         $totalPresent++; break;
//                     case "H":
//                         $totalPresent++; break;
//                     case "HF":
//                         $totalHalfDay++;
//                         $totalHFCL++; break;
//                     case "A":
//                         $totalAbsent++; break;
//                     case "SL":
//                         $totalSL++; $totalPresent++; break;
//                     case "PL":
//                         $totalPL++; $totalPresent++; break;
//                     case "CL":
//                         $totalCL++; $totalPresent++; break;
//                     case "ML":
//                         $totalML++; $totalPresent++; break;
//                 }
//             }

//             $attendance[] = [
//                 "date" => $date,
//                 "total" => 1,                 // per employee
//                 "present" => in_array($status, ["P","W","H","SL","PL","CL","ML"]) ? 1 : 0,
//                 "absent"  => ($status=="A") ? 1 : 0,
//                 "HFCL"    => ($status=="HF") ? 1 : 0,
//                 "ML"      => ($status=="ML") ? 1 : 0,
//                 "PL"      => ($status=="PL") ? 1 : 0,
//                 "SL"      => ($status=="SL") ? 1 : 0,
//                 "check_in" => $check_in,
//                 "check_out"=> $check_out,
//                 "worked_hours" => 0,
//                 "status" => $status
//             ];
//         }

//         $totalLeave = $totalSL + $totalPL + $totalCL + $totalML;

//         /* ---------------- Response ---------------- */
//         $response['error'] = false;
//         $response['from_date'] = $start_date;
//         $response['to_date']   = $end_date;
//         $response['data']      = $attendance;
//         $response['summary'] = [
//             "total_days"      => $totalDays,
//             "total_present"   => $totalPresent,
//             "total_absent"    => $totalAbsent,
//             "total_half_day"  => $totalHalfDay,
//             "total_work_hours"=> 0,
//             "sl"              => $totalSL,
//             "pl"              => $totalPL,
//             "cl"              => $totalCL,
//             "ml"              => $totalML,
//             "hfcl"            => $totalHFCL,
//             "total_leave"     => $totalLeave
//         ];

//     } else {
//         $response['error'] = true;
//         $response['message'] = 'Required parameters are not available';
//     }

// break;

// case 'attendanceSummaryTableFormat':

//     if (isTheseParametersAvailable(array('employee_code', 'from_date', 'to_date'))) {

//         date_default_timezone_set('Asia/Kolkata');

//         $employee_id = $_POST['employee_code'];
//         $start_date  = $_POST['from_date'];
//         $end_date    = $_POST['to_date'];
//         $filter_status = isset($_POST['status']) ? strtoupper(trim($_POST['status'])) : 'TOTAL';

//         $today = date('Y-m-d');
//         if ($end_date > $today) {
//             $end_date = $today;
//         }

//         /* ---------------- Manual Attendance ---------------- */
//         $stmt2 = $conn->prepare("
//             SELECT at_day, at_month, at_year, status
//             FROM attendance
//             WHERE emp_code = ?
//               AND active = '0'
//               AND STR_TO_DATE(
//                     CONCAT(at_year,'-',LPAD(at_month,2,'0'),'-',LPAD(at_day,2,'0')),
//                     '%Y-%m-%d'
//                   ) BETWEEN ? AND ?
//         ");
//         $stmt2->bind_param("sss", $employee_id, $start_date, $end_date);
//         $stmt2->execute();
//         $res2 = $stmt2->get_result();

//         $manualAttendance = [];
//         while ($row = $res2->fetch_assoc()) {
//             $dateKey = $row['at_year'].'-'.str_pad($row['at_month'],2,'0',STR_PAD_LEFT).'-'.str_pad($row['at_day'],2,'0',STR_PAD_LEFT);
//             $manualAttendance[$dateKey] = strtoupper(trim($row['status']));
//         }

//         /* ---------------- Punch Attendance ---------------- */
//         $stmt = $conn->prepare("
//             SELECT DATE(check_in_date) as att_date, check_in_time, check_out_time
//             FROM emp_attendance
//             WHERE emp_code = ?
//               AND DATE(check_in_date) BETWEEN ? AND ?
//         ");
//         $stmt->bind_param("sss", $employee_id, $start_date, $end_date);
//         $stmt->execute();
//         $result = $stmt->get_result();

//         $punchAttendance = [];
//         while ($row = $result->fetch_assoc()) {
//             $punchAttendance[$row['att_date']] = [
//                 'check_in'  => !empty($row['check_in_time']) ? $row['check_in_time'] : "--:--",
//                 'check_out' => !empty($row['check_out_time']) ? $row['check_out_time'] : "--:--"
//             ];
//         }

//         /* ---------------- Build Daily Attendance ---------------- */
//         $attendance = [];
//         $totalPresent = $totalAbsent = $totalHalfDay = 0;
//         $totalSL = $totalPL = $totalCL = $totalML = $totalHFCL = 0;
//         $totalDays = 0;

//         $period = new DatePeriod(
//             new DateTime($start_date),
//             new DateInterval('P1D'),
//             (new DateTime($end_date))->modify('+1 day')
//         );

//         foreach ($period as $dateObj) {
//             $date = $dateObj->format('Y-m-d');
//             $status = "";
//             $check_in = "--:--";
//             $check_out = "--:--";

//             // Manual overrides
//             if (isset($manualAttendance[$date])) {
//                 $status = $manualAttendance[$date];
//             } 
//             elseif (isset($punchAttendance[$date])) {
//                 $check_in = $punchAttendance[$date]['check_in'];
//                 $check_out = $punchAttendance[$date]['check_out'];
//                 if ($check_in != "--:--") $status = "P";
//             } 
//             else {
//                 if ($date < $today) $status = "A"; // past days absent
//             }

//             $status = strtoupper(trim($status));

//             // Increment counters
//             if (!empty($status)) {
//                 $totalDays++;
//                 switch ($status) {
//                     case "P": case "W":
//                         $totalPresent++; break;
//                     case "H":
//                         $totalPresent++; break;
//                     case "HF":
//                         $totalHalfDay++;
//                         $totalHFCL++; break;
//                     case "A":
//                         $totalAbsent++; break;
//                     case "SL":
//                         $totalSL++; $totalPresent++; break;
//                     case "PL":
//                         $totalPL++; $totalPresent++; break;
//                     case "CL":
//                         $totalCL++; $totalPresent++; break;
//                     case "ML":
//                         $totalML++; $totalPresent++; break;
//                 }
//             }

//             // Add to array only if status matches filter OR filter is TOTAL
//             if ($filter_status == 'TOTAL' || $filter_status == $status) {
//                 $attendance[] = [
//                     "date" => $date,
//                     "total" => 1,
//                     "present" => in_array($status, ["P","W","H","SL","PL","CL","ML"]) ? 1 : 0,
//                     "absent"  => ($status=="A") ? 1 : 0,
//                     "HFCL"    => ($status=="HF") ? 1 : 0,
//                     "ML"      => ($status=="ML") ? 1 : 0,
//                     "PL"      => ($status=="PL") ? 1 : 0,
//                     "SL"      => ($status=="SL") ? 1 : 0,
//                     "check_in" => $check_in,
//                     "check_out"=> $check_out,
//                     "worked_hours" => 0,
//                     "status" => $status
//                 ];
//             }
//         }

//         $totalLeave = $totalSL + $totalPL + $totalCL + $totalML;

//         /* ---------------- Response ---------------- */
//         $response['error'] = false;
//         $response['from_date'] = $start_date;
//         $response['to_date']   = $end_date;
//         $response['data']      = $attendance;
//         $response['summary'] = [
//             "total_days"      => $totalDays,
//             "total_present"   => $totalPresent,
//             "total_absent"    => $totalAbsent,
//             "total_half_day"  => $totalHalfDay,
//             "total_work_hours"=> 0,
//             "sl"              => $totalSL,
//             "pl"              => $totalPL,
//             "cl"              => $totalCL,
//             "ml"              => $totalML,
//             "hfcl"            => $totalHFCL,
//             "total_leave"     => $totalLeave
//         ];

//     } else {
//         $response['error'] = true;
//         $response['message'] = 'Required parameters are not available';
//     }

// break;

case 'attendanceSummaryTableFormat':

if (isTheseParametersAvailable(array('employee_code', 'from_date', 'to_date'))) {

    date_default_timezone_set('Asia/Kolkata');

    $employee_id   = $_POST['employee_code'];
    $start_date    = $_POST['from_date'];
    $end_date      = $_POST['to_date'];
    $filter_status = isset($_POST['status']) ? strtoupper(trim($_POST['status'])) : 'TOTAL';

    $today = date('Y-m-d');
    if ($end_date > $today) {
        $end_date = $today;
    }

    /* ---------------- Manual Attendance ---------------- */

    $stmt2 = $conn->prepare("
        SELECT at_day, at_month, at_year, status
        FROM attendance
        WHERE emp_code = ?
        AND active = '0'
        AND STR_TO_DATE(
            CONCAT(at_year,'-',LPAD(at_month,2,'0'),'-',LPAD(at_day,2,'0')),
            '%Y-%m-%d'
        ) BETWEEN ? AND ?
    ");

    $stmt2->bind_param("sss", $employee_id, $start_date, $end_date);
    $stmt2->execute();
    $res2 = $stmt2->get_result();

    $manualAttendance = [];

    while ($row = $res2->fetch_assoc()) {

        $dateKey = $row['at_year'].'-'.str_pad($row['at_month'],2,'0',STR_PAD_LEFT).'-'.str_pad($row['at_day'],2,'0',STR_PAD_LEFT);

        $manualAttendance[$dateKey] = strtoupper(trim($row['status']));
    }


    /* ---------------- Punch Attendance ---------------- */

    $stmt = $conn->prepare("
        SELECT *, DATE(check_in_date) as att_date
        FROM emp_attendance
        WHERE emp_code = ?
        AND DATE(check_in_date) BETWEEN ? AND ?
    ");

    $stmt->bind_param("sss", $employee_id, $start_date, $end_date);
    $stmt->execute();
    $result = $stmt->get_result();

    $punchAttendance = [];

    while ($row = $result->fetch_assoc()) {

        $checkIn  = $row['check_in_time'];
        $checkOut = $row['check_out_time'];

        $punchAttendance[$row['att_date']] = [

            'check_in'  => ($checkIn !== null && $checkIn !== '') ? $checkIn : "--:--",

            'check_out' => ($checkOut !== null && $checkOut !== '' && $checkOut !== '00:00') ? $checkOut : "--:--",

            'checkin_address' => $row['checkin_address'] ?? "",
            'checkin_city'    => $row['checkin_city'] ?? "",
            'checkin_state'   => $row['checkin_state'] ?? "",
            'checkin_pincode' => $row['checkin_pincode'] ?? "",

            'checkout_address'=> $row['checkout_address'] ?? "",
            'checkout_city'   => $row['checkout_city'] ?? "",
            'checkout_state'  => $row['checkout_state'] ?? "",
            'checkout_pincode'=> $row['checkout_pincode'] ?? "",
        ];
    }


    /* ---------------- Build Daily Attendance ---------------- */

    $attendance = [];

    $totalPresent = 0;
    $totalAbsent = 0;
    $totalHalfDay = 0;

    $totalSL = 0;
    $totalPL = 0;
    $totalCL = 0;
    $totalML = 0;
    $totalHFCL = 0;

    $totalDays = 0;

    $period = new DatePeriod(
        new DateTime($start_date),
        new DateInterval('P1D'),
        (new DateTime($end_date))->modify('+1 day')
    );

    foreach ($period as $dateObj) {

        $date = $dateObj->format('Y-m-d');

        $status = "";

        $check_in  = "--:--";
        $check_out = "--:--";

        $addressData = [
            'checkin_address'=> "",
            'checkin_city'   => "",
            'checkin_state'  => "",
            'checkin_pincode'=> "",
            'checkout_address'=> "",
            'checkout_city'  => "",
            'checkout_state' => "",
            'checkout_pincode'=> "",
        ];


        /* Manual attendance override */

        if (isset($manualAttendance[$date])) {

            $status = $manualAttendance[$date];

            if (isset($punchAttendance[$date])) {

                $check_in  = $punchAttendance[$date]['check_in'];
                $check_out = $punchAttendance[$date]['check_out'];

                $addressData = $punchAttendance[$date];
            }

        }

        elseif (isset($punchAttendance[$date])) {

            $check_in  = $punchAttendance[$date]['check_in'];
            $check_out = $punchAttendance[$date]['check_out'];

            $addressData = $punchAttendance[$date];

            if ($check_in != "--:--") {
                $status = "P";
            }

        }

        else {

            if ($date < $today) {
                $status = "A";
            }

        }


        $status = strtoupper(trim($status));


        /* Counters */

        if (!empty($status) && $status != "W") {
            $totalDays++;
        }

        switch ($status) {

            case "P":
            case "H":
                $totalPresent++;
                break;

            case "HF":
                $totalHalfDay++;
                $totalHFCL++;
                break;

            case "A":
                $totalAbsent++;
                break;

            case "SL":
                $totalSL++;
                break;

            case "PL":
                $totalPL++;
                break;

            case "CL":
                $totalCL++;
                break;

            case "ML":
                $totalML++;
                break;
        }


        /* Filter */

        if (

            ($filter_status == 'TOTAL' && $status != "W") ||
            ($filter_status == "P" && in_array($status, ["P","H"])) ||
            $filter_status == $status

        ) {

            $attendance[] = array_merge([

                "date" => $date,
                "total" => 1,

                "present" => in_array($status, ["P","H"]) ? 1 : 0,
                "absent"  => ($status=="A") ? 1 : 0,

                "HFCL" => ($status=="HF") ? 1 : 0,

                "ML" => ($status=="ML") ? 1 : 0,
                "PL" => ($status=="PL") ? 1 : 0,
                "SL" => ($status=="SL") ? 1 : 0,
                "CL" => ($status=="CL") ? 1 : 0,

                "check_in" => $check_in,
                "check_out"=> $check_out,

                "worked_hours" => 0,

                "status" => $status

            ], $addressData);
        }

    }


    $totalLeave = $totalSL + $totalPL + $totalCL + $totalML;


    /* ---------------- Response ---------------- */

    $response['error'] = false;

    $response['from_date'] = $start_date;
    $response['to_date']   = $end_date;

    $response['data'] = $attendance;

    $response['summary'] = [

        "total_days"      => $totalDays,
        "total_present"   => $totalPresent,
        "total_absent"    => $totalAbsent,
        "total_half_day"  => $totalHalfDay,
        "total_work_hours"=> 0,

        "sl"  => $totalSL,
        "pl"  => $totalPL,
        "cl"  => $totalCL,
        "ml"  => $totalML,
        "hfcl"=> $totalHFCL,

        "total_leave" => $totalLeave
    ];

}

else {

    $response['error'] = true;
    $response['message'] = 'Required parameters are not available';
}

break;

//------------------------------------------------------------------------------------------------------------------

case 'addEmployeeLeave':
if (isTheseParametersAvailable(array('emp_id', 'from_date', 'to_date', 'type', 'reason','site_id','client_id'))) {

    date_default_timezone_set('Asia/Kolkata');

    $emp_id     = $_POST['emp_id'];
    $site_id    = $_POST['site_id'];
    $client_id  = $_POST['client_id'];
    $from_date  = $_POST['from_date'];
    $to_date    = $_POST['to_date'];

    $from_time  = $_POST['from_time'] ?? null;
    $to_time    = $_POST['to_time'] ?? null;

    $type       = $_POST['type'];
    $reason     = $_POST['reason'];

    $status     = 'Pending';
   

    $created_by = $_POST['created_by'];
    $created_on = date('Y-m-d H:i:s');

    /* ---------------- Get Client Name ---------------- */
    $client_name = "";
    $stmtClient = $conn->prepare("
        SELECT company_name 
        FROM new_client 
        WHERE id = ? AND active = '0'
    ");
    $stmtClient->bind_param("s", $client_id);
    $stmtClient->execute();
    $resultClient = $stmtClient->get_result();
    if ($rowClient = $resultClient->fetch_assoc()) {
        $client_name = $rowClient['company_name'];
    }
    $stmtClient->close();

    /* ---------------- Get Site Name ---------------- */
    $site_name = "";
    $stmtSite = $conn->prepare("
        SELECT site_name 
        FROM client_rates 
        WHERE id = ? AND active = '0'
    ");
    $stmtSite->bind_param("s", $site_id);
    $stmtSite->execute();
    $resultSite = $stmtSite->get_result();
    if ($rowSite = $resultSite->fetch_assoc()) {
        $site_name = $rowSite['site_name'];
    }
    $stmtSite->close();

    /* ---------------- Insert Leave ---------------- */
    $stmt = $conn->prepare(
        "INSERT INTO emp_leaves
        (emp_id, from_date, to_date, from_time, to_time, type, reason, status,
         site_id, client_id, site_name, client_name,
         created_by, created_on)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    );

    $stmt->bind_param(
        "ssssssssssssss",
        $emp_id,
        $from_date,
        $to_date,
        $from_time,
        $to_time,
        $type,
        $reason,
        $status,
        $site_id,
        $client_id,
        $site_name,
        $client_name,
        $created_by,
        $created_on
    );

    if ($stmt->execute()) {
        $response['error'] = false;
        $response['message'] = 'Leave applied successfully';
    } else {
        $response['error'] = true;
        $response['message'] = 'Failed to apply leave';
    }

    $stmt->close();

} else {
    $response['error'] = true;
    $response['message'] = 'Required parameters are missing';
}
break;

//---------------------------------------------------------------------------------------------------------------
case 'getEmployeeLeaves':

    if (isTheseParametersAvailable(array('emp_id'))) {

        $emp_id = $_POST['emp_id'];
        $status = $_POST['status'] ?? 'All';
        $limit = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
        $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0;

        if ($status === 'All') {
            $stmt = $conn->prepare("SELECT id,emp_id,from_date,to_date,from_time,to_time,type,reason,status,approved_by,approved_on,created_on FROM emp_leaves WHERE emp_id=? AND active='0' ORDER BY created_on DESC LIMIT ?,?");
            $stmt->bind_param("sss", $emp_id, $offset, $limit);
        } else {
            $stmt = $conn->prepare("SELECT id,emp_id,from_date,to_date,from_time,to_time,type,reason,status,approved_by,approved_on,created_on FROM emp_leaves WHERE emp_id=? AND type=? AND active='0' ORDER BY created_on DESC LIMIT ?,?");
            $stmt->bind_param("ssss", $emp_id, $status, $offset, $limit);
        }

        $stmt->execute();
        $result = $stmt->get_result();

        $leaves = [];
        while ($row = $result->fetch_assoc()) { $leaves[] = $row; }

        $stmt->close();

        $response['error'] = false;
        $response['leaves'] = $leaves;
        $response['limit'] = $limit;
        $response['offset'] = $offset;
        $response['debug'] = [
    'emp_id' => $emp_id,
    'status' => $status,
    'limit' => $limit,
    'offset' => $offset,
    'query_used' => $status === 'All' ? 'without status filter' : 'with status filter'
];

    } else {
        $response['error'] = true;
        $response['message'] = 'Required parameters missing';
    }

    break;
//-----------------------------------------------------------------------------------------------------------

    case 'deleteLeave':
    if (isTheseParametersAvailable(array('leave_id'))) {

        $leave_id = $_POST['leave_id'];

        // Soft delete: set active = 1
        $stmt = $conn->prepare(
            "UPDATE emp_leaves 
             SET active = '1' 
             WHERE id = ?"
        );

        $stmt->bind_param("s", $leave_id);

        if ($stmt->execute()) {
            $response['error'] = false;
            $response['message'] = 'Leave deleted successfully';
        } else {
            $response['error'] = true;
            $response['message'] = 'Failed to delete leave';
        }

        $stmt->close();

    } else {
        $response['error'] = true;
        $response['message'] = 'Required parameters are missing';
    }
    break;

//----------------------------------------------------------------------------------------------------------------
    case 'editEmployeeLeave':

    if (isTheseParametersAvailable(array(
        'leave_id',
        'from_date',
        'to_date',
        'type',
        'reason'
    ))) {

        date_default_timezone_set('Asia/Kolkata');

        $leave_id  = $_POST['leave_id'];

        $from_date = $_POST['from_date'];   // YYYY-MM-DD
        $to_date   = $_POST['to_date'];     // YYYY-MM-DD

        $from_time = $_POST['from_time'] ?? null; // HH:MM
        $to_time   = $_POST['to_time'] ?? null;   // HH:MM

        $type      = $_POST['type'];         // Casual / Sick / Half Day / WFH
        $reason    = $_POST['reason'];

        $updated_by = $_POST['updated_by'] ?? null;
        $updated_on = date('Y-m-d H:i:s');

        $stmt = $conn->prepare(
            "UPDATE emp_leaves SET
                from_date = ?,
                to_date   = ?,
                from_time = ?,
                to_time   = ?,
                type      = ?,
                reason    = ?,
                modified_by = ?,
                modified_on = ?
             WHERE id = ?"
        );

        $stmt->bind_param(
            "ssssssssi",
            $from_date,
            $to_date,
            $from_time,
            $to_time,
            $type,
            $reason,
            $updated_by,
            $updated_on,
            $leave_id
        );

        if ($stmt->execute()) {

            if ($stmt->affected_rows > 0) {
                $response['error'] = false;
                $response['message'] = 'Leave updated successfully';
            } else {
                $response['error'] = true;
                $response['message'] = 'No changes made or invalid leave ID';
            }

        } else {
            $response['error'] = true;
            $response['message'] = 'Failed to update leave';
        }

        $stmt->close();

    } else {
        $response['error'] = true;
        $response['message'] = 'Required parameters are missing';
    }

    break;


//-------------------------------------------------------------------------------------------------------------------------

     case 'deleteVoucher':
    if (isTheseParametersAvailable(array('voucher_id'))) {

        $voucher_id = $_POST['voucher_id'];

        // Soft delete: set active = 1
        $stmt = $conn->prepare(
            "UPDATE emp_advance 
             SET active = '1' 
             WHERE id = ?"
        );

        $stmt->bind_param("s", $voucher_id);

        if ($stmt->execute()) {
            $response['error'] = false;
            $response['message'] = 'Voucher deleted successfully';
        } else {
            $response['error'] = true;
            $response['message'] = 'Failed to delete voucher';
        }

        $stmt->close();

    } else {
        $response['error'] = true;
        $response['message'] = 'Required parameters are missing';
    }
    break;


//-----------------------------------------------------------------------------------------------------------------

case 'addEducationDoc':

    // Required parameters: emp_id, education_type, imageCount
    if (isTheseParametersAvailable(array('emp_id', 'education_type', 'imageCount'))) {

        date_default_timezone_set('Asia/Kolkata');

        $emp_id           = $_POST['emp_id'];
        $education_type   = $_POST['education_type'];         // 10th, 12th, Diploma, Degree
        $percentage       = $_POST['percentage'] ?? null;     // optional
        $university       = $_POST['university'] ?? null;     // optional
        $year_of_passing  = $_POST['year_of_passing'] ?? null;// optional
        $imageCount       = intval($_POST['imageCount']);  
        $created_by       = $_POST['created_by'] ?? 'system';
        $created_on       = date('Y-m-d H:i:s');
      

        $image_names = []; // To store all file names

        // 1️⃣ Save images to server and collect filenames
        for ($i = 0; $i < $imageCount; $i++) {
            $counter = $i + 1;

            if (isset($_POST['image' . $counter])) {
                // Generate file name: edu_<education_type>_<emp_id>_<counter>.jpg
                $file_name = "edu_" . $education_type . "_" . $emp_id . "_" . $counter . ".jpg";
                $file_path = "emp_docs/" . $file_name;

                file_put_contents($file_path, base64_decode($_POST['image' . $counter]));

                $image_names[] = $file_name;
            }
        }

        // Convert array of filenames to comma-separated string
        $images_str = !empty($image_names) ? implode(",", $image_names) : null;

        // 2️⃣ Insert record into emp_education table
        $stmt = $conn->prepare("
            INSERT INTO emp_education
            (emp_id, education_type, percentage, university, year_of_passing, images, created_by, created_on)
            VALUES (?,?,?,?,?,?,?,?)
        ");
        $stmt->bind_param(
            "ssssssss",
            $emp_id,
            $education_type,
            $percentage,
            $university,
            $year_of_passing,
            $images_str,
            $created_by,
            $created_on
        );

        if (!$stmt->execute()) {
            $response['error'] = true;
            $response['message'] = 'Failed to add education record';
            break;
        }

        // 3️⃣ Response
        $response['error'] = false;
        $response['message'] = 'Education document(s) added successfully';

    } else {
        $response['error'] = true;
        $response['message'] = 'Required parameters missing';
    }

break;

    //-----------------------------------------------------------------------------------------------------------------
case 'getEducationDocs':

    // Required parameter: emp_id
    if (isTheseParametersAvailable(array('emp_id'))) {

        $emp_id = $_POST['emp_id'];

        // Prepare response
        $response = array();
        $response['error'] = false;
        $response['education'] = [];

        // 1️⃣ Fetch all active education records for this employee
        $stmt = $conn->prepare("
            SELECT id, education_type, percentage, university, year_of_passing, images, created_on
            FROM emp_education
            WHERE emp_id = ? AND active = '0'
            ORDER BY created_on DESC
        ");
        $stmt->bind_param("s", $emp_id);
        $stmt->execute();
        $result = $stmt->get_result();

        while ($row = $result->fetch_assoc()) {

            // Convert comma-separated images string to array
        

        $images = [];

if (!empty($row['images'])) {
    $imgArray = explode(",", $row['images']);

    foreach ($imgArray as $img) {
        // clean junk characters
        $img = trim($img);
        $img = ltrim($img, "[");
        $img = rtrim($img, "]");

        if ($img !== '') {
            $images[] = IMGPATH . $img;
        }
    }
}



            // Add record to response
            $response['education'][] = array(
                'id'              => $row['id'],
                'education_type'  => $row['education_type'],
                'percentage'      => $row['percentage'],
                'university'      => $row['university'],
                'year_of_passing' => $row['year_of_passing'],
                'images'          => $images,
                'created_on'      => $row['created_on']
            );
        }

        // 2️⃣ If no records found
        if (empty($response['education'])) {
            $response['message'] = 'No education documents found';
        }

    } else {
        $response['error'] = true;
        $response['message'] = 'Required parameter emp_id missing';
    }

break;
//---------------------------------------------------------------------------------------------------------------------

case 'deleteEducationDoc':

    // Required parameter: id
    if (isTheseParametersAvailable(array('id'))) {

        $id = $_POST['id'];

        // Update record to set active = 1 (soft delete)
        $stmt = $conn->prepare("
            UPDATE emp_education
            SET active = '1'
            WHERE id = ?
        ");
        $stmt->bind_param("s", $id);

        if ($stmt->execute()) {
            $response['error'] = false;
            $response['message'] = 'Education document deleted successfully';
        } else {
            $response['error'] = true;
            $response['message'] = 'Failed to delete education document';
        }

    } else {
        $response['error'] = true;
        $response['message'] = 'Required parameter id missing';
    }

break;
//--------------------------------------------------------------------------------------------------------------------------

case 'updateEducationDoc':

    // Required parameters
    if (isTheseParametersAvailable(['id', 'emp_id', 'education_type', 'imageCount'])) {

        $id = $_POST['id'];
        $emp_id = $_POST['emp_id'];
        $education_type = $_POST['education_type'];
        $university = $_POST['university'] ?? '';
        $percentage = $_POST['percentage'] ?? '';
        $year_of_passing = $_POST['year_of_passing'] ?? '';
        $imageCount = (int)$_POST['imageCount'];
        $serverImages = $_POST['serverImages'] ?? '';

        $baseDir = "emp_docs/"; // folder
        $baseUrl = "http://192.168.0.108/HRMS/" . $baseDir; // optional if you need URL later

        // 1️⃣ Collect old server images (filenames only)
        $serverImagesArray = [];
        if (!empty($serverImages)) {
            // extract filename from full URL if needed
            $parts = explode(',', $serverImages);
            foreach ($parts as $img) {
                $serverImagesArray[] = basename($img); // keep only filename
            }
        }

        // 2️⃣ Save new images from base64
        $newImages = [];
        for ($i = 0; $i < $imageCount; $i++) {
            $counter = $i + 1;
            if (isset($_POST['image' . $counter]) && !empty($_POST['image' . $counter])) {
                $file_name = "edu_" . $education_type . "_" . $emp_id . "_" . time() . "_$counter.jpg";
                $file_path = $baseDir . $file_name;

                if (file_put_contents($file_path, base64_decode($_POST['image' . $counter]))) {
                    $newImages[] = $file_name;
                }
            }
        }

        // 3️⃣ Merge old + new images
        $allImages = array_merge($serverImagesArray, $newImages);

        // 4️⃣ Re-rename all images sequentially starting from 1
        $finalImages = [];
        foreach ($allImages as $idx => $imgName) {
            $newName = "edu_" . $education_type . "_" . $emp_id . "_" . ($idx + 1) . ".jpg";
            $oldPath = $baseDir . $imgName;
            $newPath = $baseDir . $newName;

            // Rename file if filename changed
            if ($imgName !== $newName && file_exists($oldPath)) {
                rename($oldPath, $newPath);
            }

            $finalImages[] = $newName;
        }

        // 5️⃣ Convert to comma-separated string
        $imagesStr = !empty($finalImages) ? implode(',', $finalImages) : null;

        // 6️⃣ Update database
        $stmt = $conn->prepare("
            UPDATE emp_education SET
            education_type = ?,
            percentage = ?,
            university = ?,
            year_of_passing = ?,
            images = ?,
            modified_by = ?,
            modified_on = NOW()
            WHERE id = ?
        ");

        $updated_by = 'user'; // replace as needed
        $stmt->bind_param(
            "sssssss",
            $education_type,
            $percentage,
            $university,
            $year_of_passing,
            $imagesStr,
            $updated_by,
            $id
        );

        if ($stmt->execute()) {
            $response['error'] = false;
            $response['message'] = 'Education updated successfully';
        } else {
            $response['error'] = true;
            $response['message'] = 'Failed to update education';
        }

    } else {
        $response['error'] = true;
        $response['message'] = 'Missing required parameters';
    }

break;
//------------------------------------------------------------------------------------------------------------------------

// ------------------ Verify Phone ------------------
// case 'forgotPasswordVerify':

//     if (isTheseParametersAvailable(array('phone'))) {

//         $phone = $_POST['phone'];

//         // Check if phone exists in users table
//         $stmt = $conn->prepare("SELECT id FROM users WHERE phone = ? AND active = '0'");
//         $stmt->bind_param("s", $phone);
//         $stmt->execute();
//         $result = $stmt->get_result();

//         if ($result->num_rows > 0) {
//             $response['error'] = false;
//             $response['message'] = 'Phone number verified';
//         } else {
//             $response['error'] = true;
//             $response['message'] = 'Phone number not registered';
//         }

//     } else {
//         $response['error'] = true;
//         $response['message'] = 'Required parameter phone missing';
//     }

// break;

// // ------------------ Update Password ------------------
// case 'forgotPasswordUpdate':

//     if (isTheseParametersAvailable(array('phone', 'new_password'))) {

//         $phone = $_POST['phone'];
//         $newPassword = md5($_POST['new_password']); // MD5 hash

//         // Check if phone exists
//         $stmt = $conn->prepare("SELECT id FROM users WHERE phone = ? AND active = '0'");
//         $stmt->bind_param("s", $phone);
//         $stmt->execute();
//         $result = $stmt->get_result();

//         if ($result->num_rows > 0) {
//             $user = $result->fetch_assoc();
//             $userId = $user['id'];

//             // Update password
//             $stmtUpdate = $conn->prepare("UPDATE users SET password = ? WHERE id = ?");
//             $stmtUpdate->bind_param("ss", $newPassword, $userId);

//             if ($stmtUpdate->execute()) {
//                 $response['error'] = false;
//                 $response['message'] = 'Password updated successfully';
//             } else {
//                 $response['error'] = true;
//                 $response['message'] = 'Failed to update password';
//             }

//         } else {
//             $response['error'] = true;
//             $response['message'] = 'Phone number not registered';
//         }

//     } else {
//         $response['error'] = true;
//         $response['message'] = 'Required parameters missing';
//     }

// break;



case 'forgotPasswordVerify':

    if (isTheseParametersAvailable(array('identifier'))) {

        $identifier = $_POST['identifier'];

        // Check if identifier is email or phone
        if (filter_var($identifier, FILTER_VALIDATE_EMAIL)) {
            // It's an email
            $stmt = $conn->prepare("SELECT id FROM users WHERE email = ? AND active = '0'");
        } else {
            // It's a phone
            $stmt = $conn->prepare("SELECT id FROM users WHERE phone = ? AND active = '0'");
        }

        $stmt->bind_param("s", $identifier);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows > 0) {
            $response['error'] = false;
            $response['message'] = 'Identifier verified';
        } else {
            $response['error'] = true;
            $response['message'] = 'Identifier not registered';
        }

    } else {
        $response['error'] = true;
        $response['message'] = 'Required parameter missing';
    }

break;


case 'forgotPasswordUpdate':

    if (isTheseParametersAvailable(array('phone', 'new_password'))) {

        $identifier = $_POST['phone'];
        $newPassword = md5($_POST['new_password']); // MD5 hash

        // Determine if email or phone
        if (filter_var($identifier, FILTER_VALIDATE_EMAIL)) {
            $stmt = $conn->prepare("SELECT id FROM users WHERE email = ? AND active = '0'");
        } else {
            $stmt = $conn->prepare("SELECT id FROM users WHERE phone = ? AND active = '0'");
        }

        $stmt->bind_param("s", $identifier);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows > 0) {
            $user = $result->fetch_assoc();
            $userId = $user['id'];

            $stmtUpdate = $conn->prepare("UPDATE users SET password = ? WHERE id = ?");
            $stmtUpdate->bind_param("ss", $newPassword, $userId);

            if ($stmtUpdate->execute()) {
                $response['error'] = false;
                $response['message'] = 'Password updated successfully';
            } else {
                $response['error'] = true;
                $response['message'] = 'Failed to update password';
            }

        } else {
            $response['error'] = true;
            $response['message'] = 'Identifier not registered';
        }

    } else {
        $response['error'] = true;
        $response['message'] = 'Required parameters missing';
    }

break;
//------------------------------------------------------------------------------------------------------------------------------
case 'updatePasswordFromProfile':

    if (isTheseParametersAvailable(array('user_id','new_password'))) {

        $userId = $_POST['user_id'];
        $newPassword = md5($_POST['new_password']); // hash password

        $stmt = $conn->prepare("UPDATE users SET password=? WHERE id=?");
        $stmt->bind_param("ss", $newPassword, $userId);

        if ($stmt->execute()) {

            if ($stmt->affected_rows > 0) {

                $response['error'] = false;
                $response['message'] = 'Password updated successfully';

            } else {

                $response['error'] = true;
                $response['message'] = 'User not found or password already same';

            }

        } else {

            $response['error'] = true;
            $response['message'] = 'Failed to update password';

        }

    } else {

        $response['error'] = true;
        $response['message'] = 'Required parameters missing';

    }

break;

//----------------------------------------------------------------------------

case 'getTodayCheckInOut':

if(isTheseParametersAvailable(array('employee_code'))){

    date_default_timezone_set('Asia/Kolkata');

    $employee_code = $_POST['employee_code'];
    $today = date('Y-m-d');

    $stmt = $conn->prepare("SELECT check_in_time, check_out_time
                            FROM emp_attendance
                            WHERE emp_code=? 
                            AND check_in_date=?");

    $stmt->bind_param("ss", $employee_code, $today);
    $stmt->execute();
    $result = $stmt->get_result();

    if($result->num_rows > 0){

        $row = $result->fetch_assoc();

        $response['error'] = false;
        $response['check_in'] = $row['check_in_time'] ?? "";
        $response['check_out'] = $row['check_out_time'] ?? "";

    }else{

        $response['error'] = false;
        $response['check_in'] = "";
        $response['check_out'] = "";

    }

}else{

    $response['error'] = true;
    $response['message'] = "Required parameters missing";

}

break;


//-------------------------------------------------------------------------
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