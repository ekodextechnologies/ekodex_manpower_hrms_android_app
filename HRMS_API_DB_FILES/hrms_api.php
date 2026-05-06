<?php
require __DIR__ . '/vendor/autoload.php';

use Google\Cloud\DocumentAI\V1\Client\DocumentProcessorServiceClient;
use Google\Cloud\DocumentAI\V1\RawDocument;
use Google\Cloud\DocumentAI\V1\ProcessRequest;
use Google\Client;
use GuzzleHttp\Client as GuzzleClient;


// Full path to your service account JSON
//$serviceAccountPath = __DIR__ . '/saiventure-workorderai-d13458269172.json';

// Now you can use $client safely without errors

putenv('GOOGLE_APPLICATION_CREDENTIALS=saiventure-workorderai-d13458269172.json');

ini_set('max_execution_time', 300);



 
 // error_reporting(0);


require_once 'connection.php';
$response = array();
if(isset($_GET['apicall'])){

    switch($_GET['apicall']){  
   case 'login':

    if (isTheseParametersAvailable(array('email','password'))) {

        $email = $_POST['email'];
        $password = $_POST['password'];
        $new_password = md5($password);

        $stmt_check_email = $conn->prepare("SELECT id,role FROM users WHERE email ='$email'");
        $stmt_check_email->execute();
        $stmt_check_email->store_result();

        if ($stmt_check_email->num_rows > 0) {

            $stmt_check_email->bind_result($id, $role);
            $stmt_check_email->fetch();

            // Restrict only Supervisor login
           $roleLower = strtolower($role); // convert to lowercase
               if ($roleLower !== "employee") {

                $stmt_login = $conn->prepare("SELECT id,fullname,role,email,gender,phone,emp_code,address,city,pincode,onboarding,client_id,site_id,client_code,bank_name,account_no,bank_ifsc,ac_holder_name,bank_address,bank_state,bank_city,bank_micr,card_no FROM users WHERE email=? AND password=? AND active = '0'");
                $stmt_login->bind_param("ss", $email, $new_password);
                $stmt_login->execute();
                $stmt_login->store_result();

                if ($stmt_login->num_rows > 0) {

                    $stmt_login->bind_result($id, $fullname, $rank, $email, $gender, $phone, $emp_code, $address, $city, $pincode, $onboarding, $client_id, $site_id, $client_code, $bank_name, $account_no, $bank_ifsc, $ac_holder_name, $bank_address, $bank_state, $bank_city, $bank_micr, $card_no);
                    $stmt_login->fetch();

                    $user = array(
                        'id' => $id,
                        'emp_code' => $emp_code,
                        'fname' => $fullname,
                        'lname' => "",
                        'rank' => $rank,
                        'gender' => $gender,
                        'email' => $email,
                        'phone' => $phone,
                        'address' => $address,
                        'state' => "",
                        'city' => $city,
                        'pincode' => $pincode,
                        'onboarding' => $onboarding,
                        'role' => $rank,
                        'client_id' => $client_id,
                        'site_id' => $site_id,
                        'client_code' => $client_code,
                        'bank_name' => $bank_name,
                        'account_no' => $account_no,
                        'bank_ifsc' => $bank_ifsc,
                        'ac_holder_name' => $ac_holder_name,
                        'bank_address' => $bank_address,
                        'bank_state' => $bank_state,
                        'bank_city' => $bank_city,
                        'bank_micr' => $bank_micr,
                        'card_no' => $card_no
                    );

                    $response['error'] = false;
                    $response['message'] = 'Login successful !!';
                    $response['user'] = $user;

                } else {
                    $response['error'] = true;
                    $response['message'] = 'Wrong password!!';
                }

            } else {
                // This else is now correctly inside the email check
                $response['error'] = true;
                $response['message'] = 'Not allowed to login!!';
            }

        } else {
            $response['error'] = true;
            $response['message'] = 'Email is not registered!!';
        }

    } else {
        $response['error'] = true;
        $response['message'] = 'Required parameters are not available';
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

    case 'checkIn':

    if (isTheseParametersAvailable(array('employee_id'))) {  
        
        date_default_timezone_set('Asia/Kolkata');

        $employee_id   = $_POST['employee_id']; 
        $emp_name      = $_POST['emp_name'];
        $emp_code      = $_POST['emp_code'];
        $rank          = $_POST['rank'];
        $client_code   = $_POST['client_code'];
        $site_id       = $_POST['site_id'];
        $client_id       = $_POST['client_id'];

        $check_in_time = date('h:i a');      
        $check_in_date = date('Y-m-d');
        $created_on    = date('Y-m-d H:i:s');
        $status        = 'P';
        $att_type      = "Both";

        $check_in_latitude  = $_POST['latitude'];
$check_in_longitude = $_POST['longitude'];
$check_in_address   = $_POST['address'];
$check_in_city      = $_POST['city'];
$check_in_state     = $_POST['state'];
$check_in_pincode   = $_POST['pincode'];


        $dateArray = date_parse_from_format('Y-m-d', $check_in_date);

        // Insert Query
        $stmt_check_in = $conn->prepare("
    INSERT INTO emp_attendance 
    (
        employee_id,
        site_id,
        client_id,
        check_in_date,
        check_in_time,
        checkin_latitude,
        checkin_longitude,
        checkin_address,
        checkin_city,
        checkin_state,
        checkin_pincode,
        created_by,
        created_on
    )
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
");


$stmt_check_in->bind_param(
    "sssssssssssss",
    $employee_id,
    $site_id,
    $client_id,
    $check_in_date,
    $check_in_time,
    $check_in_latitude,
    $check_in_longitude,
    $check_in_address,
    $check_in_city,
    $check_in_state,
    $check_in_pincode,
    $employee_id,
    $created_on
);

        if ($stmt_check_in->execute()) {
            $response['error']   = false;   
            $response['message'] = 'Attendance added successfully!';
        } else {
            $response['error']   = true;
            $response['message'] = 'Database error! Unable to insert attendance.';
        }

    } else {

        $response['error']   = true;   
        $response['message'] = 'Required parameters are not available';

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


case 'checkOut':

    if(isTheseParametersAvailable(array('employee_id'))){  
        date_default_timezone_set('Asia/Kolkata');

        $employee_id = $_POST['employee_id']; 
        $client_id   = $_POST['client_id']; 
        $site_id     = $_POST['site_id'];     // used as rate_id
        $client_code = $_POST['client_code'];
        $client_id = $_POST['client_id'];
        $emp_name    = $_POST['emp_name'];
        $emp_code    = $_POST['emp_code'];
        $created_on  = date('Y-m-d H:i:s');
        $attendance_date = date('Y-m-d');
        $att_type    = "Both";
        $dateArray   = date_parse_from_format('Y-m-d', $attendance_date);

         $check_out_time = date('h:i a');
        //$check_out_time = '06:14 pm';    
        $check_in_date  = date('Y-m-d');

        $check_out_latitude  = $_POST['latitude'];
$check_out_longitude = $_POST['longitude'];
$check_out_address   = $_POST['address'];
$check_out_city      = $_POST['city'];
$check_out_state     = $_POST['state'];
$check_out_pincode   = $_POST['pincode'];


        // CHECK IF CHECK-IN EXISTS
        $stmt_check = $conn->prepare("SELECT id FROM emp_attendance WHERE employee_id = ? AND site_id = ? AND client_id = ? AND check_in_date = ?");
        $stmt_check->bind_param("ssss",$employee_id,$site_id,$client_id,$check_in_date);
        $stmt_check->execute();
        $stmt_check->store_result();

        if($stmt_check->num_rows > 0)
        {
            // UPDATE CHECK-OUT TIME
             $stmt_check_out = $conn->prepare("
    UPDATE emp_attendance 
    SET 
        check_out_time = ?,
        checkout_latitude = ?,
        checkout_longitude = ?,
        checkout_address = ?,
        checkout_city = ?,
        checkout_state = ?,
        checkout_pincode = ?
    WHERE employee_id = ? 
    AND site_id = ? 
    AND client_id = ? 
      AND check_in_date = ? 
      AND active = '0'
");

$stmt_check_out->bind_param(
    "sssssssssss",
    $check_out_time,
    $check_out_latitude,
    $check_out_longitude,
    $check_out_address,
    $check_out_city,
    $check_out_state,
    $check_out_pincode,
    $employee_id,
 $site_id,
  $client_id,
    $check_in_date
);


            if($stmt_check_out->execute())
            {
               

               //emp hours
                $stmt_hours = $conn->prepare("
                    SELECT emp_hours 
                    FROM client_other_info 
                    WHERE client_id = ? 
                      AND rate_id = ? 
                      AND LOWER(emp_type) = LOWER(?) 
                      AND active = '0'
                ");
                $stmt_hours->bind_param("sss", $client_id, $site_id, $rank);
                $stmt_hours->execute();
                $stmt_hours->bind_result($emp_hours);
                $stmt_hours->fetch();
                $stmt_hours->close();

                if(empty($emp_hours)){
                    $emp_hours = 8;  // default
                }

      
                // times checkin checkout
                $stmt_time = $conn->prepare("
                    SELECT check_in_time, check_out_time
                    FROM emp_attendance
                    WHERE employee_id=? AND site_id = ? AND client_id = ? AND check_in_date=?
                ");
                $stmt_time->bind_param("ssss", $employee_id, $site_id,$client_id,$check_in_date);
                $stmt_time->execute();
                $stmt_time->bind_result($cin, $cout);
                $stmt_time->fetch();
                $stmt_time->close();

                // TIME CALCULATION
                $in  = strtotime(trim($cin));
                $out = strtotime(trim($cout));

                if($out < $in){
                    $out = strtotime("+1 day", $out);
                }

                $worked_hours = round(($out - $in) / 3600, 2);

               //status part
                if($worked_hours >= $emp_hours){
                    $status = "P";
                }
                else if($worked_hours >= ($emp_hours / 2)){
                    $status = "HF";
                }
                else{
                    $status = "A";
                }

                $stmt_last_id = $conn->prepare("SELECT MAX(uniqueid) FROM attendance");  
                $stmt_last_id->execute();
                $stmt_last_id->bind_result($lastid);
                $stmt_last_id->fetch();
                $stmt_last_id->close();

                $new_id = ($lastid !== null) ? $lastid + 1 : 1;

                //Check if record exists
                $stmt_exist = $conn->prepare("
                    SELECT id 
                    FROM attendance 
                    WHERE site_id=? AND client_code=? AND emp_code=? 
                      AND at_day=? AND at_month=? AND at_year=? 
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

                if ($stmt_exist->num_rows > 0)
                {
                    $stmt_exist->close();

                    // --- UPDATE ---
                    $stmt_add = $conn->prepare("
                        UPDATE attendance 
                        SET status=?, modified_by=?, modified_on=?
                        WHERE site_id=? AND client_code=? AND emp_code=? 
                          AND at_day=? AND at_month=? AND at_year=? AND active='0'
                    ");

                    $stmt_add->bind_param(
                        "sssssssss",
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
                }
                else
                {
                    $stmt_exist->close();

                    // --- INSERT ---
                    $new_id++;

$stmt_get_rank = $conn->prepare("SELECT rank FROM employee WHERE employee_code = ? AND site_id = ? AND client_id = ? AND active = '0'");
$stmt_get_rank->bind_param("sss", $emp_code,$site_id,$client_id);
$stmt_get_rank->execute();
$stmt_get_rank->bind_result($rank);
$stmt_get_rank->fetch();
$stmt_get_rank->close();


                    $stmt_add = $conn->prepare("
                        INSERT INTO attendance 
                        (site_id, client_code, emp_name, rank, emp_code, at_day, at_month, at_year, status, att_type, uniqueid, created_by, created_on) 
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    ");

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
                        $employee_id,
                        $created_on
                    );
                }

                $stmt_add->execute();
                $stmt_add->close();

             
                // SUCCESS
                $response['error'] = false;   
                $response['message'] = 'Checked out successfully!!'; 

            }  
            else{
                $response['error'] = true;   
                $response['message'] = 'Some problem occurred!!';   
            }     
        }   
        else
        {
            $response['error'] = true;   
            $response['message'] = 'Please first check in!!';   
        }                        
    }
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
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
case 'canWeCheckOut':

    if(isTheseParametersAvailable(array('employee_id'))){  
        date_default_timezone_set('Asia/Kolkata');

        $employee_id = $_POST['employee_id']; 
        $check_out_time = date('h:i a');    
        $check_in_date = date('Y-m-d');
    

     $stmt_check= $conn->prepare("SELECT id from emp_attendance where employee_id = ? AND site_id = ? AND client_id = ? and check_in_date = ?");
                     $stmt_check->bind_param("ssss",$employee_id,$site_id,$client_id,$check_in_date);
                      $stmt_check->execute();
                      $stmt_check->store_result();
                      if($stmt_check->num_rows>0)
                      {
                       $response['error'] = false;   
                       $response['message'] = 'yes';      
                      }   
                      else
                      {
                       $response['error'] = false;   
                       $response['message'] = 'no';   
                      }                         
}
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 
//----------------------------------------------------------------------------     
case 'getAttendanceHistory':  

    $employee_id = $_POST['employee_id'];  
    $from_date   = $_POST['from_date']; // format: 'YYYY-MM-DD'
    $to_date     = $_POST['to_date'];   // format: 'YYYY-MM-DD'

    // Prepare the SQL to fetch records between the two dates
    $stmt = $conn->prepare(
        "SELECT id, check_in_date, check_in_time, check_out_time,checkin_city,checkin_state,checkin_pincode,checkin_address,checkout_address,checkout_state,checkout_city,checkout_pincode 
         FROM emp_attendance 
         WHERE employee_id = ? AND active='0' AND check_in_date BETWEEN ? AND ?"
    );  
    $stmt->bind_param("sss", $employee_id, $from_date, $to_date); 
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
// case 'getLeaves':  

// $user_id = $_POST['user_id'];
// $status = $_POST['status'];

// $stmt = $conn->prepare("SELECT id,from_date,to_date,total_days,description,status FROM leaves where user_id=? and status = ? and active='0'"); 
// $stmt->bind_param("ss",$user_id,$status); 
// $stmt->execute();  
// $stmt->store_result(); 

// if($stmt->num_rows > 0){  
//     $stmt->bind_result($id,$from_date,$to_date,$total_days,$description,$status);  

//     $banner_data = array(); 

//     while($stmt->fetch()){

//         $date1 = new DateTime($from_date);
//         $date2 = new DateTime($to_date);
//         $interval = $date1->diff($date2);

//      $temp = array(); 
//      $temp['id'] = $id; 
//      $temp['from_date']=date("d", strtotime(date($from_date)))." ".date("F", strtotime(date($from_date)))." ".date("Y", strtotime(date($from_date)));
//      $temp['to_date'] =date("d", strtotime(date($to_date)))." ".date("F", strtotime(date($to_date)))." ".date("Y", strtotime(date($to_date)));
//      $temp['total_days'] = $interval->d;
//       $temp['description'] = $description; 
//       $temp['status'] = $status;

//      array_push($banner_data, $temp);
//  }

//  $response['error'] = false;   
//  $response['message'] = 'Leaves got successful!!';   
//  $response['user'] = $banner_data;   
// }  
// else{  
//     $response['error'] = true;   
//     $response['message'] = 'No leaves available!!'; 
//     $response['user'] = array();
// }  
// //}  
// //}  
// break;  

// case 'getLeaves':  

//     $status    = $_POST['status'];
//     $role      = $_POST['role'];
//     $created_by = $_POST['created_by'] ?? null;

//     $site_id   = $_POST['site_id'] ?? "";
//     $client_id = $_POST['client_id'] ?? "";

//     $filter_from = $_POST['from_date'] ?? null;
//     $filter_to   = $_POST['to_date'] ?? null;

//     $limit  = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
//     $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0;


//     /* ---------------- Base Query ---------------- */

//     $query = "SELECT id, from_date, to_date,
//               DATEDIFF(
//                   STR_TO_DATE(to_date, '%Y-%m-%d'),
//                   STR_TO_DATE(from_date, '%Y-%m-%d')
//               ) + 1 AS total_days,
//               reason as description, status, created_by, created_on,approved_by,approved_on,rejected_by,rejected_on,site_id,client_id,site_name,client_name,type,from_time
//               FROM emp_leaves
//               WHERE active='0' AND status=?";

//     $params = [$status];
//     $types  = "s";


//     /* ---------------- Role Based Conditions ---------------- */

//     if (strcasecmp($role, "employee") === 0) {

//         $query .= " AND created_by = ?";
//         $params[] = $created_by;
//         $types .= "s";

//     }
//     elseif (strcasecmp($role, "admin") === 0 && $client_id == "" && $site_id == "") {

//         // No extra condition → admin gets all
//     }
//     else {

//         $query .= " AND client_id=? AND site_id=?";
//         $params[] = $client_id;
//         $params[] = $site_id;
//         $types .= "ss";
//     }


//     /* ---------------- Date Filter (Optional Overlap) ---------------- */

//     if ($filter_from && $filter_to) {

//         $query .= " AND STR_TO_DATE(from_date, '%Y-%m-%d') <= ?
//                     AND STR_TO_DATE(to_date, '%Y-%m-%d') >= ?";

//         $params[] = $filter_to;
//         $params[] = $filter_from;
//         $types .= "ss";
//     }


//     /* ---------------- Order + Pagination ---------------- */
//     $countQuery = "
//     SELECT COUNT(id)
//     FROM emp_leaves
//     WHERE active='0' AND status=?";
    
// $countParams = [$status];
// $countTypes = "s";

//     $query .= " ORDER BY STR_TO_DATE(from_date, '%Y-%m-%d') DESC LIMIT ? OFFSET ?";

//     $params[] = $limit;
//     $params[] = $offset;
//     $types .= "ii";


//     $stmt = $conn->prepare($query);
//     $stmt->bind_param($types, ...$params);
//     $stmt->execute();
//     $stmt->store_result();


//     if ($stmt->num_rows > 0) {

//         $stmt->bind_result($id, $from_date, $to_date, $total_days, $description, $leave_status, $created_by, $created_on,$approved_by,$approved_on,$rejected_by,$rejected_on,$site_id,$client_id,$site_name,$client_name,$type,$from_time);

//         $banner_data = array();

//         while ($stmt->fetch()) {

//                     $stmt_approved_by = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
//         $stmt_approved_by->bind_param("s", $approved_by);
//         $stmt_approved_by->execute();
//         $stmt_approved_by->store_result();
//         $stmt_approved_by->bind_result($appr_by); 
//         $stmt_approved_by->fetch();

//         $stmt_rejected_by = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
//         $stmt_rejected_by->bind_param("s", $rejected_by);
//         $stmt_rejected_by->execute();
//         $stmt_rejected_by->store_result();
//         $stmt_rejected_by->bind_result($rejec_by); 
//         $stmt_rejected_by->fetch();

//         $stmt_created_by = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
//         $stmt_created_by->bind_param("s", $created_by);
//         $stmt_created_by->execute();
//         $stmt_created_by->store_result();
//         $stmt_created_by->bind_result($creat_by); 
//         $stmt_created_by->fetch();

//             $temp = array();
//             $temp['id'] = $id;
//             $temp['from_date'] = date("d F Y", strtotime($from_date));
//             $temp['to_date']   = date("d F Y", strtotime($to_date));
//             $temp['total_days'] = $total_days;
//             $temp['description'] = $description;
//             $temp['status'] = $leave_status;
//             $temp['type'] = $type;
//             $temp['approved_by'] = $appr_by;
//             $temp['rejected_by'] = $rejec_by;
//             $temp['created_by'] = $creat_by;
//             $temp['approved_on'] = $approved_on;
//             $temp['rejected_on'] = $rejected_on;
//             $temp['created_on'] = $created_on;
//             $temp['client'] = $client_name;
//             $temp['site'] = $site_name;
//             $temp['client_id'] = $client_id;
//             $temp['site_id'] = $site_id;
//             $temp['from_time'] = $from_time;
//             $banner_data[] = $temp;
//         }

//         $response['error'] = false;
//         $response['message'] = 'Leaves fetched successfully!';
//         $response['user'] = $banner_data;

//     } else {

//         $response['error'] = true;
//         $response['message'] = 'No leaves available!';
//         $response['user'] = array();
//     }

// break;

case 'getLeaves':

    $status     = $_POST['status'];
    $role       = $_POST['role'];
    $created_by = $_POST['created_by'] ?? null;

    $site_id    = $_POST['site_id'] ?? "";
    $client_id  = $_POST['client_id'] ?? "";

    $filter_from = $_POST['from_date'] ?? null;
    $filter_to   = $_POST['to_date'] ?? null;

    $limit  = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
    $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0;


    /* ================= MAIN QUERY BASE ================= */

    $query = "SELECT id, from_date, to_date,
              DATEDIFF(
                  STR_TO_DATE(to_date, '%Y-%m-%d'),
                  STR_TO_DATE(from_date, '%Y-%m-%d')
              ) + 1 AS total_days,
              reason as description, status, created_by, created_on,
              approved_by, approved_on, rejected_by, rejected_on,
              site_id, client_id, site_name, client_name, type, from_time
              FROM emp_leaves
              WHERE active='0' AND status=?";

    $params = [$status];
    $types  = "s";


    /* ================= COUNT QUERY BASE ================= */

    $countQuery = "SELECT COUNT(id)
                   FROM emp_leaves
                   WHERE active='0' AND status=?";

    $countParams = [$status];
    $countTypes  = "s";


    /* ================= ROLE CONDITIONS ================= */

    if (strcasecmp($role, "employee") === 0) {

        $query .= " AND created_by = ?";
        $params[] = $created_by;
        $types .= "s";

        $countQuery .= " AND created_by = ?";
        $countParams[] = $created_by;
        $countTypes .= "s";
    }
    elseif (strcasecmp($role, "admin") === 0 && $client_id == "" && $site_id == "") {
        // admin → no extra condition
    }
    else {

        $query .= " AND client_id=? AND site_id=?";
        $params[] = $client_id;
        $params[] = $site_id;
        $types .= "ss";

        $countQuery .= " AND client_id=? AND site_id=?";
        $countParams[] = $client_id;
        $countParams[] = $site_id;
        $countTypes .= "ss";
    }


    /* ================= DATE FILTER ================= */

    if ($filter_from && $filter_to) {

        $query .= " AND STR_TO_DATE(from_date, '%Y-%m-%d') <= ?
                    AND STR_TO_DATE(to_date, '%Y-%m-%d') >= ?";

        $params[] = $filter_to;
        $params[] = $filter_from;
        $types .= "ss";

        $countQuery .= " AND STR_TO_DATE(from_date, '%Y-%m-%d') <= ?
                         AND STR_TO_DATE(to_date, '%Y-%m-%d') >= ?";

        $countParams[] = $filter_to;
        $countParams[] = $filter_from;
        $countTypes .= "ss";
    }


    /* ================= EXECUTE COUNT ================= */

    $stmt_count = $conn->prepare($countQuery);
    $stmt_count->bind_param($countTypes, ...$countParams);
    $stmt_count->execute();
    $stmt_count->bind_result($total_rows);
    $stmt_count->fetch();
    $stmt_count->close();


    /* ================= ADD PAGINATION ================= */

    $query .= " ORDER BY STR_TO_DATE(from_date, '%Y-%m-%d') DESC LIMIT ? OFFSET ?";

    $params[] = $limit;
    $params[] = $offset;
    $types .= "ii";


    /* ================= EXECUTE MAIN QUERY ================= */

    $stmt = $conn->prepare($query);
    $stmt->bind_param($types, ...$params);
    $stmt->execute();
    $stmt->store_result();


    if ($stmt->num_rows > 0) {

        $stmt->bind_result(
            $id, $from_date, $to_date, $total_days,
            $description, $leave_status,
            $created_by, $created_on,
            $approved_by, $approved_on,
            $rejected_by, $rejected_on,
            $site_id, $client_id,
            $site_name, $client_name,
            $type, $from_time
        );

        $banner_data = array();

        while ($stmt->fetch()) {

            /* ===== Fetch User Names ===== */

            $appr_by = $rejec_by = $creat_by = "";

            if (!empty($approved_by)) {
                $u = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
                $u->bind_param("s", $approved_by);
                $u->execute();
                $u->bind_result($appr_by);
                $u->fetch();
                $u->close();
            }

            if (!empty($rejected_by)) {
                $u = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
                $u->bind_param("s", $rejected_by);
                $u->execute();
                $u->bind_result($rejec_by);
                $u->fetch();
                $u->close();
            }

            if (!empty($created_by)) {
                $u = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
                $u->bind_param("s", $created_by);
                $u->execute();
                $u->bind_result($creat_by);
                $u->fetch();
                $u->close();
            }

            $temp = array();
            $temp['id'] = $id;
            $temp['from_date'] = date("d F Y", strtotime($from_date));
            $temp['to_date'] = date("d F Y", strtotime($to_date));
            $temp['total_days'] = $total_days;
            $temp['description'] = $description;
            $temp['status'] = $leave_status;
            $temp['type'] = $type;
            $temp['approved_by'] = $appr_by;
            $temp['rejected_by'] = $rejec_by;
            $temp['created_by'] = $creat_by;
            $temp['approved_on'] = $approved_on;
            $temp['rejected_on'] = $rejected_on;
            $temp['created_on'] = $created_on;
            $temp['client'] = $client_name;
            $temp['site'] = $site_name;
            $temp['client_id'] = $client_id;
            $temp['site_id'] = $site_id;
            $temp['from_time'] = $from_time;

            $banner_data[] = $temp;
        }

        $response['error'] = false;
        $response['message'] = 'Leaves fetched successfully!';
        $response['user'] = $banner_data;
        $response['total_rows'] = $total_rows;

    } else {

        $response['error'] = false;
        $response['message'] = 'No leaves available!';
        $response['user'] = array();
        $response['total_rows'] = 0;
    }

break;
//---------------------------------------------------------------------------------------------------------------------  
case 'getParticularLeave':

    $leave_id = $_POST['leave_id'];

    $stmt = $conn->prepare("SELECT id, from_date, to_date, DATEDIFF(STR_TO_DATE(to_date, '%Y-%m-%d'), STR_TO_DATE(from_date, '%Y-%m-%d')) + 1 AS total_days, reason AS description, status, created_by, created_on, approved_by, approved_on, rejected_by, rejected_on, site_id, client_id, site_name, client_name, type, from_time, emp_id FROM emp_leaves WHERE id = ? AND active = '0'");

    $stmt->bind_param("s", $leave_id);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows > 0) {

        $stmt->bind_result(
            $id,
            $from_date,
            $to_date,
            $total_days,
            $description,
            $status,
            $created_by,
            $created_on,
            $approved_by,
            $approved_on,
            $rejected_by,
            $rejected_on,
            $site_id,
            $client_id,
            $site_name,
            $client_name,
            $type,
            $from_time,
            $emp_id
        );

        $banner_data = array();

        while ($stmt->fetch()) {

            $appr_by = "";
            $rejec_by = "";
            $creat_by = "";
            $emp_name = "";

            if (!empty($approved_by)) {
                $stmt_approved_by = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
                $stmt_approved_by->bind_param("s", $approved_by);
                $stmt_approved_by->execute();
                $stmt_approved_by->bind_result($appr_by);
                $stmt_approved_by->fetch();
                $stmt_approved_by->close();
            }

            if (!empty($rejected_by)) {
                $stmt_rejected_by = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
                $stmt_rejected_by->bind_param("s", $rejected_by);
                $stmt_rejected_by->execute();
                $stmt_rejected_by->bind_result($rejec_by);
                $stmt_rejected_by->fetch();
                $stmt_rejected_by->close();
            }

            if (!empty($created_by)) {
                $stmt_created_by = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
                $stmt_created_by->bind_param("s", $created_by);
                $stmt_created_by->execute();
                $stmt_created_by->bind_result($creat_by);
                $stmt_created_by->fetch();
                $stmt_created_by->close();
            }

             if (!empty($emp_id)) {
                $stmt_emp_name = $conn->prepare("SELECT emp_full_name FROM employee WHERE id = ?");
                $stmt_emp_name->bind_param("s", $emp_id);
                $stmt_emp_name->execute();
                $stmt_emp_name->bind_result($emp_name);
                $stmt_emp_name->fetch();
                $stmt_emp_name->close();
            }

            $temp = array();
            $temp['id'] = $id;
            $temp['from_date'] = $from_date;
            $temp['to_date'] = $to_date;
            $temp['total_days'] = $total_days;
            $temp['description'] = $description;
            $temp['status'] = $status;
            $temp['type'] = $type;
            $temp['created_by'] = $creat_by;
            $temp['created_on'] = $created_on;
            $temp['approved_by'] = $appr_by;
            $temp['approved_on'] = $approved_on;
            $temp['rejected_by'] = $rejec_by;
            $temp['rejected_on'] = $rejected_on;
            $temp['client'] = $client_name;
            $temp['site'] = $site_name;
            $temp['client_id'] = $client_id;
            $temp['site_id'] = $site_id;
            $temp['from_time'] = $from_time;
            $temp['emp_name'] = $emp_name;

            array_push($banner_data, $temp);
        }

        $response['error'] = false;
        $response['message'] = 'Leave fetched successfully!';
        $response['user'] = $banner_data;

    } else {

        $response['error'] = true;
        $response['message'] = 'Leave not available!';
        $response['user'] = array();
    }

break;
//--------------------------------------------------------------------------------------------------------
case 'addLeave':

    if(isTheseParametersAvailable(array('user_id'))){  
        date_default_timezone_set('Asia/Kolkata');
        $user_id = $_POST['user_id'];
        $emp_name = $_POST['emp_name'];
        $emp_code = $_POST['emp_code'];
        $from_date = $_POST['from_date'];
        $to_date = $_POST['to_date'];
        $description = $_POST['description'];
        $created_on = date('Y-m-d H:i:s');

        $date1 = new DateTime($from_date);
        $date2 = new DateTime($to_date);
        $interval = $date1->diff($date2);

    

                     $stmt_add = $conn->prepare("INSERT INTO leaves (user_id,emp_name,emp_code,from_date,to_date,total_days,description,created_on) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"); 

                     $stmt_add->bind_param("ssssssss",$user_id,$emp_name,$emp_code,$from_date,$to_date,$interval->d,$description,$created_on);
                     if($stmt_add->execute())
                     {

                      $response['error'] = false;   
                      $response['message'] = 'Leave added successfully!!'; 
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

    case 'rejectEmployeeLeave':

    if(isTheseParametersAvailable(array('leave_id'))){  
        date_default_timezone_set('Asia/Kolkata');
       
        $leave_id = $_POST['leave_id'];
        $rejected_by = $_POST['rejected_by'];
        $rejected_on = date('Y-m-d H:i:s');
        
                     $stmt_update = $conn->prepare("UPDATE emp_leaves SET status='Rejected',rejected_on=?,rejected_by=?,modified_by=?,modified_on=? where id =?"); 

                     $stmt_update->bind_param("sssss",$rejected_on,$rejected_by,$rejected_by,$rejected_on,$leave_id);

                      $stmt_update->execute();

                    
     
                      $response['error'] = false;   
                      $response['message'] = 'Leave rejected successfully!!';    
                  }
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 
//-------------------------------------------------------------------------------------
    // case 'approveEmployeeLeave':

    // if(isTheseParametersAvailable(array('leave_id'))){  
    //     date_default_timezone_set('Asia/Kolkata');
       
    //     $leave_id = $_POST['leave_id'];
    //     $approved_by = $_POST['approved_by'];
    //     $approved_on = date('Y-m-d H:i:s');
        
    //                  $stmt_update = $conn->prepare("UPDATE emp_leaves SET status='Approved',approved_on=?,approved_by=?,modified_by=?,modified_on=? where id =?"); 

    //                  $stmt_update->bind_param("sssss",$approved_on,$approved_by,$approved_by,$approved_on,$leave_id);

    //                   $stmt_update->execute();

                    
     
    //                   $response['error'] = false;   
    //                   $response['message'] = 'Leave approved successfully!!';    
    //               }
    // else
    // {
    //     $response['error'] = true;   
    //     $response['message'] = 'required parameters are not available';  
    // }
    // break; 

//     case 'approveEmployeeLeave':

// if(isTheseParametersAvailable(array('leave_id'))){  

//     date_default_timezone_set('Asia/Kolkata');

//     $leave_id = $_POST['leave_id'];
//     $approved_by = $_POST['approved_by'];
//     $approved_on = date('Y-m-d H:i:s');

//     /* ---------------- GET LEAVE DETAILS ---------------- */

//     $stmtLeave = $conn->prepare("
//         SELECT emp_id,type,from_date,to_date,from_time,to_time 
//         FROM emp_leaves 
//         WHERE id=? AND active='0'
//     ");
//     $stmtLeave->bind_param("s",$leave_id);
//     $stmtLeave->execute();
//     $resultLeave = $stmtLeave->get_result();

//     if($rowLeave = $resultLeave->fetch_assoc()){

//         $emp_id    = $rowLeave['emp_id'];
//         $type      = $rowLeave['type'];
//         $from_date = $rowLeave['from_date'];
//         $to_date   = $rowLeave['to_date'];
//         $from_time = $rowLeave['from_time'];
//         $to_time   = $rowLeave['to_time'];

//         /* ---------------- GET EMPLOYEE CODE ---------------- */

// $employee_code = "";

// $stmtEmp = $conn->prepare("
//     SELECT employee_code 
//     FROM employee 
//     WHERE id=? AND active='0'
// ");

// $stmtEmp->bind_param("s",$emp_id);
// $stmtEmp->execute();
// $resultEmp = $stmtEmp->get_result();

// if($rowEmp = $resultEmp->fetch_assoc()){
//     $employee_code = $rowEmp['employee_code'];
// }

// $stmtEmp->close();

//         /* ---------------- CHECK HALF DAY ---------------- */

//         $isHalfDay = false;

//         if(!empty($from_time) && !empty($to_time)){
//             $isHalfDay = true;
//         }

//         /* ---------------- CALCULATE LEAVE DAYS ---------------- */

//         $leave_days = 0;

//         if(!$isHalfDay){

//             $start = new DateTime($from_date);
//             $end   = new DateTime($to_date);
//             $diff  = $start->diff($end);

//             $leave_days = $diff->days + 1;
//         }

//         /* ---------------- FIND LEAVE COLUMN ---------------- */

//         $column = "";

//         if ($type == "CL") {
//             $column = "cl_used";
//         } 
//         else if ($type == "SL") {
//             $column = "sl_used";
//         } 
//         else if ($type == "PL") {
//             $column = "pl_used";
//         } 
//         else if ($type == "ML") {
//             $column = "ml_used";
//         }

//         /* ---------------- FINANCIAL YEAR ---------------- */

//         $currentMonth = date('n');
//         $currentYear  = date('Y');

//         if ($currentMonth >= 4) {
//             $fy_from = $currentYear;
//             $fy_to   = $currentYear + 1;
//         } else {
//             $fy_from = $currentYear - 1;
//             $fy_to   = $currentYear;
//         }

//         /* ---------------- START TRANSACTION ---------------- */

//         $conn->begin_transaction();

//         /* ---------------- CHECK BALANCE ---------------- */

//         if(!$isHalfDay && $column != ""){

//             $balanceQuery = $conn->prepare("
//                 SELECT $column, 
//                        REPLACE($column,'used','total')
//                 FROM employee_leaves_balance
//                 WHERE emp_code=? AND from_year=? AND to_year=? AND active='0'
//             ");

//             $balanceQuery->bind_param("sii",$employee_code,$fy_from,$fy_to);
//             $balanceQuery->execute();
//             $balanceResult = $balanceQuery->get_result();

//             if($rowBalance = $balanceResult->fetch_assoc()){

//                 $used  = $rowBalance[$column];
//                 $total = $rowBalance[str_replace("used","total",$column)];

//                 $remaining = $total - $used;

//                 if($leave_days > $remaining){

//                     $conn->rollback();

//                     $response['error'] = true;
//                     $response['message'] = 'Not enough leave balance';
//                     break;
//                 }

//             }

//             $balanceQuery->close();
//         }

//         /* ---------------- APPROVE LEAVE ---------------- */

//         $stmt_update = $conn->prepare("
//             UPDATE emp_leaves 
//             SET status='Approved',
//                 approved_on=?,
//                 approved_by=?,
//                 modified_by=?,
//                 modified_on=? 
//             WHERE id =?
//         "); 

//         $stmt_update->bind_param("sssss",
//             $approved_on,
//             $approved_by,
//             $approved_by,
//             $approved_on,
//             $leave_id
//         );

//         $stmt_update->execute();

//         /* ---------------- UPDATE BALANCE ---------------- */

//         if(!$isHalfDay && $column != ""){

//             $update = $conn->prepare("
//                 UPDATE employee_leaves_balance
//                 SET $column = $column + ?
//                 WHERE emp_code=? 
//                 AND from_year=? 
//                 AND to_year=? 
//                 AND active='0'
//             ");

//             $update->bind_param("isii",
//                 $leave_days,
//                 $employee_code,
//                 $fy_from,
//                 $fy_to
//             );

//             $update->execute();
//             $update->close();
//         }

//         $conn->commit();

//         $response['error'] = false;   
//         $response['message'] = 'Leave approved successfully!!';

//     }
//     else{

//         $response['error'] = true;
//         $response['message'] = 'Leave not found';
//     }

// }
// else{

//     $response['error'] = true;   
//     $response['message'] = 'required parameters are not available';  

// }

// break;

    case 'approveEmployeeLeave':

if(isTheseParametersAvailable(array('leave_id'))){  

    date_default_timezone_set('Asia/Kolkata');

    $leave_id = $_POST['leave_id'];
    $approved_by = $_POST['approved_by'];
    $approved_on = date('Y-m-d H:i:s');

    /* ---------------- GET LEAVE DETAILS ---------------- */

    $stmtLeave = $conn->prepare("
        SELECT emp_id,type,from_date,to_date,from_time,to_time 
        FROM emp_leaves 
        WHERE id=? AND active='0'
    ");
    $stmtLeave->bind_param("s",$leave_id);
    $stmtLeave->execute();
    $resultLeave = $stmtLeave->get_result();

    if($rowLeave = $resultLeave->fetch_assoc()){

        $emp_id    = $rowLeave['emp_id'];
        $type      = $rowLeave['type'];
        $from_date = $rowLeave['from_date'];
        $to_date   = $rowLeave['to_date'];
        $from_time = $rowLeave['from_time'];
        $to_time   = $rowLeave['to_time'];

        /* ---------------- GET EMPLOYEE CODE ---------------- */

        $employee_code = "";

        $stmtEmp = $conn->prepare("
            SELECT employee_code 
            FROM employee 
            WHERE id=? AND active='0'
        ");

        $stmtEmp->bind_param("s",$emp_id);
        $stmtEmp->execute();
        $resultEmp = $stmtEmp->get_result();

        if($rowEmp = $resultEmp->fetch_assoc()){
            $employee_code = $rowEmp['employee_code'];
        }

        $stmtEmp->close();

        if($employee_code == ""){
            $response['error'] = true;
            $response['message'] = 'Employee not found';
            break;
        }

        /* ---------------- CHECK HALF DAY ---------------- */

        $isHalfDay = false;

        if(!empty($from_time) && !empty($to_time)){
            $isHalfDay = true;
        }

        /* ---------------- CALCULATE LEAVE DAYS ---------------- */

        $leave_days = 0;

        if(!$isHalfDay){

            $start = new DateTime($from_date);
            $end   = new DateTime($to_date);
            $diff  = $start->diff($end);

            $leave_days = $diff->days + 1;
        }

        /* ---------------- FIND LEAVE COLUMN ---------------- */

        $column = "";

        if ($type == "CL") {
            $column = "cl_used";
        } 
        else if ($type == "SL") {
            $column = "sl_used";
        } 
        else if ($type == "PL") {
            $column = "pl_used";
        } 
        else if ($type == "ML") {
            $column = "ml_used";
        }

        /* ---------------- FIND TOTAL COLUMN ---------------- */

        $total_column = str_replace("used","total",$column);

        /* ---------------- FINANCIAL YEAR ---------------- */

        $currentMonth = date('n');
        $currentYear  = date('Y');

        if ($currentMonth >= 4) {
            $fy_from = $currentYear;
            $fy_to   = $currentYear + 1;
        } else {
            $fy_from = $currentYear - 1;
            $fy_to   = $currentYear;
        }

        /* ---------------- START TRANSACTION ---------------- */

        $conn->begin_transaction();

        /* ---------------- CHECK BALANCE ---------------- */

        if(!$isHalfDay && $column != ""){

            $balanceQuery = $conn->prepare("
                SELECT $column, $total_column
                FROM employee_leaves_balance
                WHERE emp_code=? AND from_year=? AND to_year=? AND active='0'
            ");

            $balanceQuery->bind_param("sii",$employee_code,$fy_from,$fy_to);
            $balanceQuery->execute();
            $balanceResult = $balanceQuery->get_result();

            if($rowBalance = $balanceResult->fetch_assoc()){

                $used  = $rowBalance[$column];
                $total = $rowBalance[$total_column];

                $remaining = $total - $used;

                if($leave_days > $remaining){

                    $conn->rollback();

                    $response['error'] = true;
                    $response['message'] = 'Not enough leave balance';
                    break;
                }

            }

            $balanceQuery->close();
        }

        /* ---------------- APPROVE LEAVE ---------------- */

        $stmt_update = $conn->prepare("
            UPDATE emp_leaves 
            SET status='Approved',
                approved_on=?,
                approved_by=?,
                modified_by=?,
                modified_on=? 
            WHERE id =?
        "); 

        $stmt_update->bind_param("sssss",
            $approved_on,
            $approved_by,
            $approved_by,
            $approved_on,
            $leave_id
        );

        $stmt_update->execute();

        /* ---------------- UPDATE BALANCE ---------------- */

        if(!$isHalfDay && $column != ""){

            $update = $conn->prepare("
                UPDATE employee_leaves_balance
                SET $column = $column + ?
                WHERE emp_code=? 
                AND from_year=? 
                AND to_year=? 
                AND active='0'
            ");

            $update->bind_param("isii",
                $leave_days,
                $employee_code,
                $fy_from,
                $fy_to
            );

            $update->execute();
            $update->close();
        }

        $conn->commit();

        $response['error'] = false;   
        $response['message'] = 'Leave approved successfully!!';

    }
    else{

        $response['error'] = true;
        $response['message'] = 'Leave not found';
    }

}
else{

    $response['error'] = true;   
    $response['message'] = 'required parameters are not available';  

}

break;
//----------------------------------------------------------------------------  
    case 'getBirthdays':  

$stmt = $conn->prepare("SELECT id,first_name,last_name,dob FROM employee where DATE_ADD(dob, INTERVAL YEAR(CURDATE())-YEAR(dob) + IF(DAYOFYEAR(CURDATE()) > DAYOFYEAR(dob),1,0) YEAR) BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)");  
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$first_name,$last_name,$dob);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['id'] = $id; 
     $temp['first_name']=$first_name ;
     $temp['last_name'] = $last_name;
     $temp['dob'] = date("d", strtotime(date($dob)))." ".date("F", strtotime(date($dob)));

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
//}  
//}  
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

    if(isTheseParametersAvailable(array('sender_id'))){  

        date_default_timezone_set('Asia/Kolkata');

        $sender_id   = $_POST['sender_id'];
        $receiver_id = $_POST['receiver_id'];
        $message     = $_POST['message'];
        $birthday_date   = $_POST['birthday_date']; // yyyy-mm-dd
        $created_on  = date('Y-m-d H:i:s');
      

        $stmt_add = $conn->prepare(
            "INSERT INTO birthday_wishes 
            (sender_id, receiver_id, message, birthday_date, created_by, created_on) 
            VALUES (?, ?, ?, ?, ?, ?)"
        ); 

        $stmt_add->bind_param(
            "ssssss",
            $sender_id,
            $receiver_id,
            $message,
            $birthday_date,
            $sender_id,
            $created_on
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
    else
    {
        $response['error'] = true;   
        $response['message'] = 'Required parameters are not available';  
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
//     case 'forgotPassword':  

// $email = $_POST['email'];

// $stmt = $conn->prepare("SELECT password FROM users where email=?"); 
// $stmt->bind_param('s',$email); 
// $stmt->execute();  
// $stmt->store_result(); 

// if($stmt->num_rows > 0){  
//     $stmt->bind_result($password);  
//     $stmt->fetch();

//  $response['error'] = false;   
//  $response['message'] = 'Password sent successfully to this email id!!';   
//  $response['password'] = $password;   
// }  
// else{  
//     $response['error'] = true;   
//     $response['message'] = 'User not registered with this email id!!'; 
//     $response['user'] = array();
// }  
// //}  
// //}  
// break;  

//     case 'forgotPassword':  

//     $email = $_POST['email'];

//     $stmt = $conn->prepare("SELECT password FROM users WHERE email=?"); 
//     $stmt->bind_param('s', $email); 
//     $stmt->execute();  
//     $stmt->store_result(); 

//     if($stmt->num_rows > 0){  
//         $stmt->bind_result($password);  
//         $stmt->fetch();

    
//         //mailgun email
//         $mailgunApiKey = "47c14e38547698993d2490b296991c7b-235e4bb2-9397c908"; 
//         $mailgunDomain = "scholarbook.in";          
//         $url = "https://api.mailgun.net/v3/{$mailgunDomain}/messages";

//         $subject = "Password Recovery - Your Account";
//         $htmlMessage = "
//             <div style='font-family:Arial,sans-serif; max-width:600px; margin:auto; padding:20px; border:1px solid #ddd;'>
//                 <h2>Password Recovery</h2>
//                 <p>Hello,</p>
//                 <p>You requested to recover your password. Your password is:</p>
//                 <p style='font-size:18px; font-weight:bold;'>{$password}</p>
//                 <p>If you did not request this, please ignore this email.</p>
//                 <p>Regards,<br>Support Team</p>
//             </div>
//         ";

//         $ch = curl_init();
//         curl_setopt_array($ch, [
//             CURLOPT_URL => $url,
//             CURLOPT_RETURNTRANSFER => true,
//             CURLOPT_POST => true,
//             CURLOPT_POSTFIELDS => [
//                 'from' => "Support <support@{$mailgunDomain}>",
//                 'to' => $email,
//                 'subject' => $subject,
//                 'html' => $htmlMessage
//             ],
//             CURLOPT_USERPWD => "api:{$mailgunApiKey}",
//             CURLOPT_HTTPAUTH => CURLAUTH_BASIC,
//             CURLOPT_TIMEOUT => 30,
//             CURLOPT_SSL_VERIFYPEER => false
//         ]);

//         $mailResult = curl_exec($ch);
//         curl_close($ch);

//         // Response after sending email
//         $response['error'] = false;   
//         $response['message'] = 'Password has been sent successfully to your email address!';   

//     } else {  
//         $response['error'] = true;   
//         $response['message'] = 'User not registered with this email id!'; 
//     }  

// break;

    case 'sendPasswordRecoveryCode':

  date_default_timezone_set('Asia/Kolkata');
    $email = $_POST['email'];

    // Check if user exists
    $stmt = $conn->prepare("SELECT id FROM users WHERE email=?");
    $stmt->bind_param('s', $email);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows > 0) {
        $stmt->bind_result($userId);
        $stmt->fetch();

        // Generate a random recovery key (6-digit OTP or token)
        $recoveryKey = bin2hex(random_bytes(4)); // 8-character hex key
        $expiryTime = date('Y-m-d H:i:s', strtotime('+15 minutes')); // optional expiry

        // Store recovery key in user table (or password_reset table)
        $updateStmt = $conn->prepare("UPDATE users SET recovery_key=?, recovery_key_expiry=? WHERE id=?");
        $updateStmt->bind_param('ssi', $recoveryKey, $expiryTime, $userId);
        $updateStmt->execute();

        // Prepare email content
      $mailgunApiKey = "47c14e38547698993d2490b296991c7b-235e4bb2-9397c908";
        //$mailgunApiKey = "e4b0503d614e64446dad43b5f8ae1d79-77c6c375-4db1b3d0";
        
        $mailgunDomain = "scholarbook.in";
         //$mailgunDomain = "ekodextechnologies.com";
        $url = "https://api.mailgun.net/v3/{$mailgunDomain}/messages";

        $subject = "Password Recovery - Your Account";
        $htmlMessage = "
            <div style='font-family:Arial,sans-serif; max-width:600px; margin:auto; padding:20px; border:1px solid #ddd;'>
                <h2>Password Recovery</h2>
                <p>Hello,</p>
                <p>You requested to recover your password. Please use the following key to reset your password:</p>
                <p style='font-size:18px; font-weight:bold;'>{$recoveryKey}</p>
                <p>This key will expire in 15 minutes.</p>
                <p>If you did not request this, please ignore this email.</p>
                <p>Regards,<br>Support Team</p>
            </div>
        ";

        // Send email using Mailgun
        $ch = curl_init();
        curl_setopt_array($ch, [
            CURLOPT_URL => $url,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_POST => true,
            CURLOPT_POSTFIELDS => [
                'from' => "Support <support@{$mailgunDomain}>",
                'to' => $email,
                'subject' => $subject,
                'html' => $htmlMessage
            ],
            CURLOPT_USERPWD => "api:{$mailgunApiKey}",
            CURLOPT_HTTPAUTH => CURLAUTH_BASIC,
            CURLOPT_TIMEOUT => 30,
            CURLOPT_SSL_VERIFYPEER => false
        ]);

        $mailResult = curl_exec($ch);
        curl_close($ch);

        $response['error'] = false;
        $response['message'] = 'A recovery key has been sent to your email address!';

    } else {
        $response['error'] = true;
        $response['message'] = 'User not registered with this email id!';
    }

break;


case 'resetPassword':

    $email = trim($_POST['email'] ?? '');
    $recoveryKey = trim($_POST['recovery_key'] ?? '');
    $newPassword = $_POST['new_password'] ?? '';

    if ($email === '' || $recoveryKey === '' || $newPassword === '') {
        $response['error'] = true;
        $response['message'] = 'All fields are required.';
        break;
    }

    // Hash password (keeping MD5 as per your current system)
    $md5Password = md5($newPassword);

    // UPDATE with expiry check INSIDE SQL
    $updateStmt = $conn->prepare("
        UPDATE users
        SET password = ?, recovery_key = NULL, recovery_key_expiry = NULL
        WHERE email = ?
          AND recovery_key = ?
          AND STR_TO_DATE(recovery_key_expiry, '%Y-%m-%d %H:%i:%s') >= NOW()
        LIMIT 1
    ");

    $updateStmt->bind_param(
        'sss',
        $md5Password,
        $email,
        $recoveryKey
    );

    $updateStmt->execute();

    if ($updateStmt->affected_rows === 1) {
        $response['error'] = false;
        $response['message'] = 'Password has been updated successfully!';
    } else {
        $response['error'] = true;
        $response['message'] = 'Recovery key is invalid or has expired.';
    }

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

// case 'addSupervisorAttendance':

//     if (isTheseParametersAvailable(array('site_id'))) {  
//         date_default_timezone_set('Asia/Kolkata');

//         $employees = json_decode($_POST['employees'], true);
//         $status = $_POST['status'];
//         $client_code = $_POST['client_code'];
//         $site_id = $_POST['site_id'];
//         $created_by = $_POST['created_by'];
//         $created_on = date('Y-m-d H:i:s');
//         $attendance_date = $_POST['attendance_date'];
//         $att_type = "Both";
//         $dateArray = date_parse_from_format('Y-m-d', $attendance_date);

//         $check_in_date  = $attendance_date;

//         // Decide check-in/out based on status
//         if($status == 'A'){
//             $check_in_time  = "00:00";
//             $check_out_time = "00:00";
//         } else {
//             $check_in_time  = "09:00 AM";
//             $check_out_time = "00:00";
//         }

//         // Start transaction
//         $conn->begin_transaction();

//         try {

//             // Get max uniqueid
//             $stmt_last_id = $conn->prepare("SELECT MAX(uniqueid) FROM attendance");  
//             $stmt_last_id->execute();
//             $stmt_last_id->bind_result($lastid);
//             $stmt_last_id->fetch();
//             $stmt_last_id->close();

//             $new_id = ($lastid !== null) ? $lastid : 0;

//             foreach ($employees as $emp) {

//                 $emp_name = $emp['emp_name'];
//                 $emp_code = $emp['emp_code'];
//                 $emp_id   = $emp['emp_id'];
//                 $rank     = $emp['rank'];

//                 // Check if attendance already exists
//                 $stmt = $conn->prepare("
//                     SELECT id FROM attendance 
//                     WHERE site_id=? AND client_code=? AND emp_code=? 
//                     AND at_day=? AND at_month=? AND at_year=? AND active='0'
//                 ");

//                 $stmt->bind_param(
//                     "ssssss",
//                     $site_id,
//                     $client_code,
//                     $emp_code,
//                     $dateArray['day'],
//                     $dateArray['month'],
//                     $dateArray['year']
//                 );

//                 $stmt->execute();
//                 $stmt->store_result();

//                 if ($stmt->num_rows > 0) {

//                     $stmt->close();

//                     $stmt_add = $conn->prepare("
//                         UPDATE attendance 
//                         SET status=?, modified_by=?, modified_on=? 
//                         WHERE site_id=? AND client_code=? AND emp_code=? 
//                         AND at_day=? AND at_month=? AND at_year=? AND active='0'
//                     ");

//                     $stmt_add->bind_param(
//                         "sssssssss",
//                         $status,
//                         $created_by,
//                         $created_on,
//                         $site_id,
//                         $client_code,
//                         $emp_code,
//                         $dateArray['day'],
//                         $dateArray['month'],
//                         $dateArray['year']
//                     );

//                 } else {

//                     $stmt->close();

//                     $new_id++;

//                     $stmt_add = $conn->prepare("
//                         INSERT INTO attendance 
//                         (site_id, client_code, emp_name, rank, emp_code,
//                         at_day, at_month, at_year, status, att_type,
//                         uniqueid, created_by, created_on)
//                         VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
//                     ");

//                     $stmt_add->bind_param(
//                         "sssssssssssss",
//                         $site_id,
//                         $client_code,
//                         $emp_name,
//                         $rank,
//                         $emp_code,
//                         $dateArray['day'],
//                         $dateArray['month'],
//                         $dateArray['year'],
//                         $status,
//                         $att_type,
//                         $new_id,
//                         $created_by,
//                         $created_on
//                     );
//                 }

//                 if (!$stmt_add->execute()) {
//                     throw new Exception("Failed attendance insert/update for: $emp_name");
//                 }

//                 $stmt_add->close();


//                 // =========================
//                 // EMP_ATTENDANCE INSERT / UPDATE
//                 // =========================

//                 $stmt_check_emp = $conn->prepare("
//                     SELECT id FROM emp_attendance
//                     WHERE emp_code=? AND check_in_date=? AND site_id=? AND client_id=?
//                 ");

//                 $stmt_check_emp->bind_param(
//                     "ssss",
//                     $emp_code,
//                     $check_in_date,
//                     $site_id,
//                     $client_code
//                 );

//                 $stmt_check_emp->execute();
//                 $stmt_check_emp->store_result();

//                 if ($stmt_check_emp->num_rows > 0) {

//                     $stmt_check_emp->close();

//                     $stmt_emp = $conn->prepare("
//                         UPDATE emp_attendance
//                         SET check_in_time=?, check_out_time=?,
//                             modified_by=?, modified_on=?
//                         WHERE emp_code=? AND check_in_date=? AND site_id=? AND client_id=?
//                     ");

//                     $stmt_emp->bind_param(
//                         "ssssssss",
//                         $check_in_time,
//                         $check_out_time,
//                         $created_by,
//                         $created_on,
//                         $emp_code,
//                         $check_in_date,
//                         $site_id,
//                         $client_code
//                     );

//                 } else {

//                     $stmt_check_emp->close();

//                     $stmt_emp = $conn->prepare("
//                         INSERT INTO emp_attendance
//                         (employee_id, emp_code, check_in_date, check_in_time, check_out_time,
//                          created_by, created_on, site_id, client_id)
//                         VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
//                     ");

//                     $stmt_emp->bind_param(
//                         "sssssssss",
//                         $emp_id,
//                         $emp_code,
//                         $check_in_date,
//                         $check_in_time,
//                         $check_out_time,
//                         $created_by,
//                         $created_on,
//                         $site_id,
//                         $client_code
//                     );
//                 }

//                 if (!$stmt_emp->execute()) {
//                     throw new Exception("Failed emp_attendance for: $emp_name");
//                 }

//                 $stmt_emp->close();
//             }

//             // Commit transaction
//             $conn->commit();

//             $response['error'] = false;   
//             $response['message'] = 'Attendance added successfully!!';

//         } catch (Exception $e) {

//             $conn->rollback();

//             $response['error'] = true;
//             $response['message'] = $e->getMessage();
//         }

//     } else {

//         $response['error'] = true;   
//         $response['message'] = 'Required parameters are not available';
//     }

// break;


// case 'addSupervisorAttendance':

// if (isTheseParametersAvailable(array('site_id','employees','client_code','created_by','attendance_date'))) {

//     date_default_timezone_set('Asia/Kolkata');

//     $employees = json_decode($_POST['employees'], true);
//     $status = $_POST['status'];
//     $client_code = $_POST['client_code'];
//     $site_id = $_POST['site_id'];
//     $created_by = $_POST['created_by'];
//     $created_on = date('Y-m-d H:i:s');
//     $attendance_date = $_POST['attendance_date'];

//     $att_type = "Both";
//     $check_in_date = $attendance_date;

//     $dateArray = date_parse_from_format('Y-m-d', $attendance_date);

//     // time logic
//     if($status == 'A'){
//         $check_in_time  = "00:00";
//         $check_out_time = "00:00";
//     } else {
//         $check_in_time  = "09:00 AM";
//         $check_out_time = "00:00";
//     }

//     $conn->begin_transaction();

//     try {

//         // get last uniqueid
//         $res = $conn->query("SELECT MAX(uniqueid) as uid FROM attendance");
//         $row_uid = $res->fetch_assoc();
//         $new_id = ($row_uid['uid'] ?? 0);

//         foreach ($employees as $emp) {

//             $emp_name = $emp['emp_name'];
//             $emp_code = $emp['emp_code'];
//             $emp_id   = $emp['emp_id'];

//             // ================== GET RANK (ERP SAFE) ==================
//             $stmt_rank = $conn->prepare("
//                 SELECT rank FROM employee 
//                 WHERE employee_code=? AND active='0'
//             ");
//             $stmt_rank->bind_param("s",$emp_code);
//             $stmt_rank->execute();
//             $stmt_rank->bind_result($rank);
//             $stmt_rank->fetch();
//             $stmt_rank->close();

//             if(empty($rank)) $rank = "employee";

//             // ================== ERP ATTENDANCE ==================
//             $stmt_exist = $conn->prepare("
//                 SELECT id FROM attendance 
//                 WHERE site_id=? AND client_code=? AND emp_code=? 
//                 AND at_day=? AND at_month=? AND at_year=? AND active='0'
//             ");

//             $stmt_exist->bind_param(
//                 "ssssss",
//                 $site_id,
//                 $client_code,
//                 $emp_code,
//                 $dateArray['day'],
//                 $dateArray['month'],
//                 $dateArray['year']
//             );

//             $stmt_exist->execute();
//             $stmt_exist->store_result();

//             if ($stmt_exist->num_rows > 0) {

//                 $stmt_exist->close();

//                 // UPDATE
//                 $stmt_add = $conn->prepare("
//                     UPDATE attendance 
//                     SET status=?, modified_by=?, modified_on=? 
//                     WHERE site_id=? AND client_code=? AND emp_code=? 
//                     AND at_day=? AND at_month=? AND at_year=? AND active='0'
//                 ");

//                 $stmt_add->bind_param(
//                     "sssssssss",
//                     $status,
//                     $created_by,
//                     $created_on,
//                     $site_id,
//                     $client_code,
//                     $emp_code,
//                     $dateArray['day'],
//                     $dateArray['month'],
//                     $dateArray['year']
//                 );

//             } else {

//                 $stmt_exist->close();

//                 $new_id++;

//                 // INSERT
//                 $stmt_add = $conn->prepare("
//                     INSERT INTO attendance 
//                     (site_id, client_code, emp_name, rank, emp_code,
//                      at_day, at_month, at_year, status, att_type,
//                      uniqueid, created_by, created_on)
//                     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
//                 ");

//                 $stmt_add->bind_param(
//                     "sssssssssssss",
//                     $site_id,
//                     $client_code,
//                     $emp_name,
//                     $rank,
//                     $emp_code,
//                     $dateArray['day'],
//                     $dateArray['month'],
//                     $dateArray['year'],
//                     $status,
//                     $att_type,
//                     $new_id,
//                     $created_by,
//                     $created_on
//                 );
//             }

//             if (!$stmt_add->execute()) {
//                 throw new Exception("Attendance failed for $emp_name");
//             }

//             $stmt_add->close();

//             // ================== EMP_ATTENDANCE SAFE ==================

//             // check if already checked out → DO NOT TOUCH
//             $stmt_done = $conn->prepare("
//                 SELECT id FROM emp_attendance
//                 WHERE emp_code=? 
//                   AND check_in_date=? 
//                   AND site_id=? 
//                   AND client_id=?
//                   AND check_out_time != '00:00'
//             ");
//             $stmt_done->bind_param("ssss",$emp_code,$check_in_date,$site_id,$client_code);
//             $stmt_done->execute();
//             $stmt_done->store_result();

//             if($stmt_done->num_rows > 0){
//                 $stmt_done->close();
//                 continue; // skip employee
//             }
//             $stmt_done->close();

//             // check existing
//             $stmt_check_emp = $conn->prepare("
//                 SELECT id, check_in_time FROM emp_attendance
//                 WHERE emp_code=? AND check_in_date=? AND site_id=? AND client_id=?
//             ");

//             $stmt_check_emp->bind_param(
//                 "ssss",
//                 $emp_code,
//                 $check_in_date,
//                 $site_id,
//                 $client_code
//             );

//             $stmt_check_emp->execute();
//             $result_emp = $stmt_check_emp->get_result();

//             if ($result_emp->num_rows > 0) {

//                 $row_emp = $result_emp->fetch_assoc();

//                 // ONLY update if not checked-in
//                 if($row_emp['check_in_time'] == '00:00' || empty($row_emp['check_in_time'])){

//                     $stmt_emp = $conn->prepare("
//                         UPDATE emp_attendance
//                         SET check_in_time=?, check_out_time=?,
//                             modified_by=?, modified_on=?
//                         WHERE id=?
//                     ");

//                     $stmt_emp->bind_param(
//                         "sssss",
//                         $check_in_time,
//                         $check_out_time,
//                         $created_by,
//                         $created_on,
//                         $row_emp['id']
//                     );

//                     if (!$stmt_emp->execute()) {
//                         throw new Exception("emp_attendance update failed for $emp_name");
//                     }

//                     $stmt_emp->close();
//                 }

//             } else {

//                 // INSERT only if not exists
//                 $stmt_emp = $conn->prepare("
//                     INSERT INTO emp_attendance
//                     (employee_id, emp_code, check_in_date, check_in_time, check_out_time,
//                      created_by, created_on, site_id, client_id)
//                     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
//                 ");

//                 $stmt_emp->bind_param(
//                     "sssssssss",
//                     $emp_id,
//                     $emp_code,
//                     $check_in_date,
//                     $check_in_time,
//                     $check_out_time,
//                     $created_by,
//                     $created_on,
//                     $site_id,
//                     $client_code
//                 );

//                 if (!$stmt_emp->execute()) {
//                     throw new Exception("emp_attendance insert failed for $emp_name");
//                 }

//                 $stmt_emp->close();
//             }

//             $stmt_check_emp->close();
//         }

//         $conn->commit();

//         $response['error'] = false;
//         $response['message'] = 'Attendance added successfully';

//     } catch (Exception $e) {

//         $conn->rollback();

//         $response['error'] = true;
//         $response['message'] = $e->getMessage();
//     }

// } else {
//     $response['error'] = true;
//     $response['message'] = 'Required parameters missing';
// }

// break;

case 'addSupervisorAttendance':

if (isTheseParametersAvailable(array('site_id','employees','client_code','created_by','attendance_date'))) {

    date_default_timezone_set('Asia/Kolkata');

    $employees = json_decode($_POST['employees'], true);

    // Debug: log incoming POST

  // Print raw POST for debugging
    // $response['debug_raw_post'] = $_POST;

    // // Try to decode employees JSON
    // $employees = json_decode($_POST['employees'], true);
    // if ($employees === null) {
    //     $response['error'] = true;
    //     $response['message'] = "JSON decode failed!";
    //     $response['debug_employees'] = $_POST['employees'];
    //     echo json_encode($response);
    //     exit;
    // } else {
    //     $response['debug_employees_decoded'] = $employees;
    // }



    $att_type = 'Both';
    $status = $_POST['status'] ?? 'A';
    $client_code = $_POST['client_code'];
    $site_id = $_POST['site_id'];
    $created_by = $_POST['created_by'];
    $created_on = date('Y-m-d H:i:s');
    $attendance_date = $_POST['attendance_date'];
    $check_in_date = $attendance_date;
    $dateArray = date_parse_from_format('Y-m-d', $attendance_date);

    // Set proper defaults for check-in/out
    if($status == 'A'){
        $check_in_time  = "00:00";
        $check_out_time = "00:00";
    } else {
        $check_in_time  = $_POST['check_in_time'] ?? "09:00 AM";
        $check_out_time = $_POST['check_out_time'] ?? "00:00";
    }

    $conn->begin_transaction();

    try {
        $res = $conn->query("SELECT MAX(uniqueid) as uid FROM attendance");
        $row_uid = $res->fetch_assoc();
        $new_id = ($row_uid['uid'] ?? 0);

        foreach ($employees as $emp) {

            $emp_name = $emp['emp_name'];
            $emp_code = $emp['emp_code'];
            $emp_id   = $emp['emp_id'];
            $rank     = $emp['rank'] ?? 'employee';

            // ----- Attendance table -----
            $stmt_exist = $conn->prepare("
                SELECT id FROM attendance 
                WHERE site_id=? AND client_code=? AND emp_code=? 
                AND at_day=? AND at_month=? AND at_year=? AND active='0'
            ");
            $stmt_exist->bind_param(
                "ssssss",
                $site_id,
                $client_code,
                $emp_code,
                $dateArray['day'],
                $dateArray['month'],
                $dateArray['year']
            );
            $stmt_exist->execute();
            $stmt_exist->store_result();

            if ($stmt_exist->num_rows > 0) {
                $stmt_exist->close();
                $stmt_add = $conn->prepare("
                    UPDATE attendance 
                    SET status=?, modified_by=?, modified_on=? 
                    WHERE site_id=? AND client_code=? AND emp_code=? 
                    AND at_day=? AND at_month=? AND at_year=? AND active='0'
                ");
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
                $stmt_exist->close();
                $new_id++;
                $stmt_add = $conn->prepare("
                    INSERT INTO attendance 
                    (site_id, client_code, emp_name, rank, emp_code,
                     at_day, at_month, at_year, status, att_type,
                     uniqueid, created_by, created_on)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ");
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

            if (!$stmt_add->execute()) throw new Exception("Attendance failed for $emp_name");
            $stmt_add->close();

            // ----- emp_attendance table -----
            $stmt_check_emp = $conn->prepare("
                SELECT id, check_in_time FROM emp_attendance
                WHERE emp_code=? AND check_in_date=? AND site_id=? AND client_id=?
            ");
            $stmt_check_emp->bind_param("ssss", $emp_code, $check_in_date, $site_id, $client_code);
            $stmt_check_emp->execute();
            $stmt_check_emp->store_result();
            $stmt_check_emp->bind_result($emp_att_id, $existing_check_in);

            if ($stmt_check_emp->fetch()) {
                $stmt_check_emp->close();
                // Only update if check_in_time is empty
                if(empty($existing_check_in) || $existing_check_in == '00:00'){
                    $stmt_emp = $conn->prepare("
                        UPDATE emp_attendance
                        SET check_in_time=?, check_out_time=?,
                            modified_by=?, modified_on=?
                        WHERE id=?
                    ");
                    $stmt_emp->bind_param(
                        "sssss",
                        $check_in_time,
                        $check_out_time,
                        $created_by,
                        $created_on,
                        $emp_att_id
                    );
                    if (!$stmt_emp->execute()) throw new Exception("emp_attendance update failed for $emp_name");
                    $stmt_emp->close();
                }
            } else {
                $stmt_check_emp->close();
                $stmt_emp = $conn->prepare("
                    INSERT INTO emp_attendance
                    (employee_id, emp_code, check_in_date, check_in_time, check_out_time,
                     created_by, created_on, site_id, client_id)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                ");
                $stmt_emp->bind_param(
                    "sssssssss",
                    $emp_id,
                    $emp_code,
                    $check_in_date,
                    $check_in_time,
                    $check_out_time,
                    $created_by,
                    $created_on,
                    $site_id,
                    $client_code
                );
                if (!$stmt_emp->execute()) throw new Exception("emp_attendance insert failed for $emp_name");
                $stmt_emp->close();
            }
        }

        $conn->commit();
        $response['error'] = false;
        $response['message'] = 'Attendance added successfully';

    } catch (Exception $e) {
        $conn->rollback();
        $response['error'] = true;
        $response['message'] = $e->getMessage();
    }

} else {
    $response['error'] = true;
    $response['message'] = 'Required parameters missing';
}

break;


// case 'addSupervisorAttendance':

// if (isTheseParametersAvailable(array('site_id','employees','client_code','created_by','attendance_date'))) {

//     date_default_timezone_set('Asia/Kolkata');

//     $employees = json_decode($_POST['employees'], true);
//     $status = $_POST['status'] ?? 'A';
//     $client_code = $_POST['client_code'];
//     $site_id = $_POST['site_id'];
//     $created_by = $_POST['created_by'];
//     $created_on = date('Y-m-d H:i:s');
//     $attendance_date = $_POST['attendance_date'];
//     $check_in_date = $attendance_date;
//     $dateArray = date_parse_from_format('Y-m-d', $attendance_date);

//     $check_in_time  = $_POST['check_in_time'] ?? '';
//     $check_out_time = $_POST['check_out_time'] ?? '';

//     $conn->begin_transaction();

//     try {
//         $res = $conn->query("SELECT MAX(uniqueid) as uid FROM attendance");
//         $row_uid = $res->fetch_assoc();
//         $new_id = ($row_uid['uid'] ?? 0);

//         foreach ($employees as $emp) {

//             $emp_name = $emp['emp_name'];
//             $emp_code = $emp['emp_code'];
//             $emp_id   = $emp['emp_id'];
//             $rank     = $emp['rank'] ?? 'employee';

//             // ----- Attendance table -----
//             $stmt_exist = $conn->prepare("
//                 SELECT id FROM attendance 
//                 WHERE site_id=? AND client_code=? AND emp_code=? 
//                 AND at_day=? AND at_month=? AND at_year=? AND active='0'
//             ");
//             $stmt_exist->bind_param(
//                 "ssssss",
//                 $site_id,
//                 $client_code,
//                 $emp_code,
//                 $dateArray['day'],
//                 $dateArray['month'],
//                 $dateArray['year']
//             );
//             $stmt_exist->execute();
//             $stmt_exist->store_result();

//             if ($stmt_exist->num_rows > 0) {
//                 $stmt_exist->close();
//                 $stmt_add = $conn->prepare("
//                     UPDATE attendance 
//                     SET status=?, modified_by=?, modified_on=? 
//                     WHERE site_id=? AND client_code=? AND emp_code=? 
//                     AND at_day=? AND at_month=? AND at_year=? AND active='0'
//                 ");
//                 $stmt_add->bind_param(
//                     "sssssssss",
//                     $status,
//                     $created_by,
//                     $created_on,
//                     $site_id,
//                     $client_code,
//                     $emp_code,
//                     $dateArray['day'],
//                     $dateArray['month'],
//                     $dateArray['year']
//                 );
//             } else {
//                 $stmt_exist->close();
//                 $new_id++;
//                 $stmt_add = $conn->prepare("
//                     INSERT INTO attendance 
//                     (site_id, client_code, emp_name, rank, emp_code,
//                      at_day, at_month, at_year, status, att_type,
//                      uniqueid, created_by, created_on)
//                     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
//                 ");
//                 $stmt_add->bind_param(
//                     "sssssssssssss",
//                     $site_id,
//                     $client_code,
//                     $emp_name,
//                     $rank,
//                     $emp_code,
//                     $dateArray['day'],
//                     $dateArray['month'],
//                     $dateArray['year'],
//                     $status,
//                     'Both',
//                     $new_id,
//                     $created_by,
//                     $created_on
//                 );
//             }

//             if (!$stmt_add->execute()) throw new Exception("Attendance failed for $emp_name");
//             $stmt_add->close();

//             // ----- emp_attendance table -----
//             $stmt_check_emp = $conn->prepare("
//                 SELECT id FROM emp_attendance
//                 WHERE emp_code=? AND check_in_date=? AND site_id=? AND client_id=?
//             ");
//             $stmt_check_emp->bind_param("ssss",$emp_code,$check_in_date,$site_id,$client_code);
//             $stmt_check_emp->execute();
//             $result_emp = $stmt_check_emp->get_result();

//             if ($result_emp->num_rows > 0) {
//                 $row_emp = $result_emp->fetch_assoc();
//                 $stmt_emp = $conn->prepare("
//                     UPDATE emp_attendance
//                     SET check_in_time=?, check_out_time=?,
//                         modified_by=?, modified_on=?
//                     WHERE id=?
//                 ");
//                 $stmt_emp->bind_param(
//                     "sssss",
//                     $check_in_time,
//                     $check_out_time,
//                     $created_by,
//                     $created_on,
//                     $row_emp['id']
//                 );
//                 if (!$stmt_emp->execute()) throw new Exception("emp_attendance update failed for $emp_name");
//                 $stmt_emp->close();
//             } else {
//                 $stmt_emp = $conn->prepare("
//                     INSERT INTO emp_attendance
//                     (employee_id, emp_code, check_in_date, check_in_time, check_out_time,
//                      created_by, created_on, site_id, client_id)
//                     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
//                 ");
//                 $stmt_emp->bind_param(
//                     "sssssssss",
//                     $emp_id,
//                     $emp_code,
//                     $check_in_date,
//                     $check_in_time,
//                     $check_out_time,
//                     $created_by,
//                     $created_on,
//                     $site_id,
//                     $client_code
//                 );
//                 if (!$stmt_emp->execute()) throw new Exception("emp_attendance insert failed for $emp_name");
//                 $stmt_emp->close();
//             }

//             $stmt_check_emp->close();
//         }

//         $conn->commit();
//         $response['error'] = false;
//         $response['message'] = 'Attendance added successfully';

//     } catch (Exception $e) {
//         $conn->rollback();
//         $response['error'] = true;
//         $response['message'] = $e->getMessage();
//     }

// } else {
//     $response['error'] = true;
//     $response['message'] = 'Required parameters missing';
// }

// break;



//-------------------------------------------------------------------------------------

    case 'getAllSalarySlips':  

    $client_id = $_POST['client_id'];  
    $site_id = $_POST['site_id'];   
    $keyword = trim($_POST['keyword']);
    $month = $_POST['month'];
    $year = $_POST['year'];
    $limit = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
    $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0; 

        if(!empty($keyword)) {
            $stmt = $conn->prepare("SELECT DISTINCT e.emp_code,e.emp_name,e.rank FROM emp_wages e JOIN attendance a ON e.emp_code = a.emp_code WHERE e.client_id = ? AND e.site_id = ? AND e.emp_name LIKE ? AND a.status = 'P' AND e.at_month = ? AND e.at_year= ? AND a.at_month = ? AND a.at_year= ? LIMIT ?, ?");

            $keyword_param = "%$keyword%";
            $stmt->bind_param("sssssssss", $client_id, $site_id, $keyword_param, $month, $year, $month, $year, $offset, $limit);

            $stmt_count = $conn->prepare("SELECT COUNT(DISTINCT e.emp_code) FROM emp_wages e JOIN attendance a ON e.emp_code = a.emp_code WHERE e.client_id = ? AND e.site_id = ? AND e.emp_name LIKE ? AND a.status = 'P' AND e.at_month = ? AND e.at_year= ? AND a.at_month = ? AND a.at_year= ?");
            $stmt_count->bind_param("sssssss",$client_id, $site_id, $keyword_param, $month, $year, $month, $year);
        } else {
            $stmt = $conn->prepare("SELECT DISTINCT e.emp_code,e.emp_name,e.rank FROM emp_wages e JOIN attendance a ON e.emp_code = a.emp_code WHERE e.client_id = ? AND e.site_id = ? AND a.status = 'P' AND e.at_month = ? AND e.at_year= ? AND a.at_month = ? AND a.at_year= ? LIMIT ?, ?");
            $stmt->bind_param("ssssssss", $client_id, $site_id, $month, $year, $month, $year, $offset, $limit);

            $stmt_count = $conn->prepare("SELECT COUNT(DISTINCT e.emp_code) FROM emp_wages e JOIN attendance a ON e.emp_code = a.emp_code WHERE e.client_id = ? AND e.site_id = ? AND a.status = 'P' AND e.at_month = ? AND e.at_year= ? AND a.at_month = ? AND a.at_year= ?");
            $stmt_count->bind_param("ssssss", $client_id, $site_id, $month, $year, $month, $year);
        }
    
    // Execute
    $stmt->execute();  
    $stmt->store_result();

    $stmt_count->execute();
    $stmt_count->bind_result($total_rows);
    $stmt_count->fetch();
    $stmt_count->close(); 

    if($stmt->num_rows > 0){  
        $stmt->bind_result($code,$name,$rank);  
        $banner_data = array();

        while($stmt->fetch()){
            $temp = array(); 
            $temp['code'] = $code; 
            $temp['name'] = $name;
            $temp['rank'] = $rank;
            array_push($banner_data, $temp);
        }

        $response['error'] = false;   
        $response['message'] = 'Salary slips got successfully!!';   
        $response['user'] = $banner_data; 
        $response['total_rows'] = $total_rows; 
    } else {  
        $response['error'] = true;   
        $response['message'] = 'No salary slips not available!!'; 
        $response['user'] = array();
    }

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
    case 'getAllCompanies':   

$id = $_POST['id'];

$stmt = $conn->prepare("SELECT DISTINCT id,company_name FROM new_client WHERE id=? and active = '0'");  
$stmt->bind_param("s",$id); 
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$title);  

    $banner_data = array();

    while($stmt->fetch()){
     $temp = array(); 
     $temp['id'] = $id; 
     $temp['title']=$title ;
     array_push($banner_data, $temp);
 }

 $response['error'] = false;   
 $response['message'] = 'Companies got successful!!';   
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
 case 'getAllBranches':   

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

    if(isTheseParametersAvailable(array('id'))){  
      
    $id = $_POST['id'];  

    $stmt_get_data = $conn->prepare("SELECT initial,first_name,middle_name,last_name,gender,rank,address,state,city,pincode,phone1,email_id,em_status,bank_name,bank_ifsc,account_no,ac_holder_name,employee_code,esis_no,pf_no,passport_no,uan_no,pancard_no,aadhar_no,uan_date,esis_date,passport_valid_date,dob,date_of_joining,father_name FROM employee WHERE id ='$id'");
                     $stmt_get_data->execute();
                     $stmt_get_data->store_result();

  if ($stmt_get_data->num_rows > 0) {

  $stmt_get_data->bind_result($initial,$fname,$mname,$lname,$gender,$rank,$address,$state,$city,$pincode,$phone,$email,$jobLeftStatus,$bank,$ifsc,$account_no,$holder,$empcode,$esis,$pf,$passport,$uan,$pan,$aadhar,$uan_date,$esis_date,$passport_date,$dob,$doj,$father_name);
  
   $stmt_get_data->fetch();

                 $user = array(  
                'initial'=>$initial, 
                'fname'=>$fname,
                'mname'=>$mname,
                'lname'=>$lname,
                'gender'=>$gender,
                'rank'=>$rank,
                'address'=>$address,
                'state'=>$state,
                'city'=>$city,
                'pincode'=>$pincode,
                'phone'=>$phone,
                'email'=>$email,
                'jobLeftStatus'=>$jobLeftStatus,
                'bank'=>$bank,
                'ifsc'=>$ifsc,
                'account_no'=>$account_no,
                'holder'=>$holder,
                'empcode'=>$empcode,
                'esis'=>$esis,
                'pf'=>$pf,
                'passport'=>$passport,
                'uan'=>$uan,
                'pan'=>$pan,
                'aadhar'=>$aadhar,
                'uan_date'=>$uan_date,
                'esis_date'=>$esis_date,
                'passport_date'=>$passport_date,
                'dob'=>$dob,
                'doj'=>$doj,
                'father_name'=>$father_name
            );  

            $response['error'] = false;   
            $response['message'] = 'Employee data fetched successful!!';   
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


    case 'updateEmployeePersonalDetails': 
    if(isTheseParametersAvailable(array('id'))){   

        $id = $_POST['id']; 
        $fname = $_POST['fname'];
        $mname = $_POST['mname']; 
        $lname = $_POST['lname'];
        $father_name = $_POST['father_name'];
        $full_name = $_POST['full_name'];
        $dob = $_POST['dob'];
        $doj = $_POST['doj'];
        $address = $_POST['address']; 
        $state = $_POST['state']; 
        $city = $_POST['city'];
        $pincode = $_POST['pincode'];
        $phone = $_POST['phone']; 
        $email = $_POST['email'];
        $gender = $_POST['gender']; 
        $rank = $_POST['rank'];

        $aadharCount = $_POST['aadharCount'];
        $panCount = $_POST['panCount'];
        $aadhar_no = $_POST['aadhar_no'];
        $pan_no = $_POST['pan_no'];

        $created_by = $_POST['created_by'];
        $created_on = date('Y-m-d H:i:s');

        // --- Update Employee Personal Details ---
        $stmt = $conn->prepare("UPDATE employee 
                                SET first_name=?, middle_name=?, last_name=?, emp_full_name=?,address=?, state=?, city=?, pincode=?, 
                                    phone1=?, email_id=?, rank=?, gender=?, father_name=?, dob=?, date_of_joining=? 
                                WHERE id=?");  
        $stmt->bind_param("ssssssssssssssss",
            $fname,$mname,$lname,$full_name,$address,$state,$city,$pincode,$phone,$email,$rank,
            $gender,$father_name,$dob,$doj,$id
        );  
        $stmt->execute();
        $stmt->close();


        // --- Soft delete existing documents ---
        $stmt_soft_delete = $conn->prepare("UPDATE emp_docs SET active = 1 WHERE emp_id = ? AND (
        doc_name LIKE '%aadhar%' OR doc_name LIKE '%pan%')");
        $stmt_soft_delete->bind_param("s", $id);
        $stmt_soft_delete->execute();


        // --- Update employee ID numbers ---
        $stmt_update_numbers = $conn->prepare("UPDATE employee SET aadhar_no=?, pancard_no=? WHERE id=?");
        $stmt_update_numbers->bind_param("sss", $aadhar_no, $pan_no, $id);
        $stmt_update_numbers->execute();

         $stmt_last = $conn->prepare("SELECT max(id) lastid from emp_docs");  
                         $stmt_last->execute();
                         $stmt_last->store_result(); 

                         if($stmt_last->num_rows > 0){  
                         $stmt_last->bind_result($lastid);  
                         $stmt_last->fetch();
                         $new_id = $lastid+1; 
                         }
                          


        // --- Get active doc count ---
        function getCurrentDocCount($conn, $id, $doc_type){
            $stmt = $conn->prepare("SELECT COUNT(*) FROM emp_docs WHERE emp_id=? AND doc_type=? AND active=0");
            $stmt->bind_param("ss", $id, $doc_type);
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
                        "INSERT INTO emp_docs (doc_type, doc_name, emp_id, doc_remark, created_by, created_on) 
                         VALUES (?,?,?,?,?,?)"
                    );
                    $stmt_insert->bind_param("ssssss", 
                        $doc_name, $file_name, $id, $remark, $created_by, $created_on
                    );
                    $stmt_insert->execute();
                    $stmt_insert->close();
                }
            }
        }

        // --- FINAL SUCCESS RESPONSE (fixed position) ---
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

$stmt = $conn->prepare("SELECT doc_type,doc_name FROM emp_docs where emp_id=? and active ='0'");
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

     case 'getEmployeeBankDetails':

    if(isTheseParametersAvailable(array('id'))){  
      
    $id = $_POST['id'];  

    $stmt_get_data = $conn->prepare("SELECT  emp_full_name,bank_name,account_no,bank_ifsc,ac_holder_name,bank_address,bank_state,bank_city,bank_micr,card_no FROM employee WHERE id ='$id'");
                     $stmt_get_data->execute();
                     $stmt_get_data->store_result();

  if ($stmt_get_data->num_rows > 0) {

  $stmt_get_data->bind_result($full_name,$bank,$account_no,$ifsc,$holder,$address,$state,$city,$micr,$cardno);
  
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
                    'holder'=>$full_name
            );      

            $response['error'] = false;   
            $response['message'] = 'Bank details fetched successful!!';   
            $response['user'] = $user;   
        } 
        else
        {
            $response['error'] = true;   
            $response['message'] = 'Bank details not found!!';    
        }
       
}
    else
    {
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
    case 'updateEmployeeBankDetails': 
      if(isTheseParametersAvailable(array('id'))){   
      
  $id = $_POST['id']; 
  $bank = $_POST['bank'];
  $acno = $_POST['acno']; 
  $ifsc = $_POST['ifsc'];
  $holder = $_POST['holder']; 
  $address = $_POST['address'];
  $state = $_POST['state']; 
  $city = $_POST['city'];
  $micr = $_POST['micr']; 
  $card = $_POST['card'];
 
            $stmt = $conn->prepare("UPDATE employee SET bank_name =?,account_no=?,bank_ifsc=?,ac_holder_name=?,bank_address=?,bank_state=?,bank_city=?,bank_micr=?,card_no=? where id = ?");  
            $stmt->bind_param("ssssssssss",$bank,$acno,$ifsc,$holder,$address,$state,$city,$micr,$card,$id);  
            $stmt->execute();
            $stmt->close();
        
                $response['error'] = false;   
                $response['message'] = 'Bank details updated successfully!!';   
    }  
    else{  
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';   
    }  
    break; 
//--------------------------------------------------------------------------------------------------

     case 'updateParticularBankDetails': 
      if(isTheseParametersAvailable(array('id'))){
           date_default_timezone_set('Asia/Kolkata');    
      
  $id = $_POST['id']; 
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
  $modified_on = date('Y-m-d H:i:s');
 
            $stmt = $conn->prepare("UPDATE user_bank_details SET bank_name =?,account_no=?,bank_ifsc=?,ac_holder_name=?,bank_address=?,bank_state=?,bank_city=?,bank_micr=?,card_no=?,modified_by=?,modified_on=? where id = ?");  
            $stmt->bind_param("ssssssssssss",$bank,$acno,$ifsc,$holder,$address,$state,$city,$micr,$card,$user_id,$modified_on,$id);  
            $stmt->execute();
            $stmt->close();
        
                $response['error'] = false;   
                $response['message'] = 'Bank details updated successfully!!';   
    }  
    else{  
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';   
    }  
    break; 

    //---------------------------------------------------------------------------------

 case 'addNewBankDetails':

    if (isTheseParametersAvailable(array('user_id'))) {

        date_default_timezone_set('Asia/Kolkata');

        $user_id   = $_POST['user_id'];
        $bank      = $_POST['bank'];
        $acno      = $_POST['acno'];
        $ifsc      = $_POST['ifsc'];
        $holder    = $_POST['holder'];
        $address   = $_POST['address'];
        $state     = $_POST['state'];
        $city      = $_POST['city'];
        $micr      = $_POST['micr'];
        $card      = $_POST['card'];
        $chequeCount = (int) $_POST['chequeCount'];
        $created_on = date('Y-m-d H:i:s');

        /* ================= INSERT BANK DETAILS ================= */

        $stmt = $conn->prepare(
            "INSERT INTO user_bank_details
            (bank_name, account_no, bank_ifsc, ac_holder_name, bank_address,
             bank_state, bank_city, bank_micr, card_no, user_id, created_by, created_on)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );

        $stmt->bind_param(
            "ssssssssssss",
            $bank, $acno, $ifsc, $holder, $address,
            $state, $city, $micr, $card,
            $user_id, $user_id, $created_on
        );

        $stmt->execute();
        $stmt->close();

        /* ================= HANDLE CHEQUE IMAGES ================= */

        $chequeImages = [];

        if ($chequeCount > 0) {

            for ($i = 0; $i < $chequeCount; $i++) {

                $counter = $i + 1;

                if (!empty($_POST['cheque' . $counter])) {

                    $file_name = "emp_docs/" . "cheque_" . $acno . "_" . $counter . ".jpg";
                    $file_path =  $file_name;

                    file_put_contents(
                        $file_path,
                        base64_decode($_POST['cheque' . $counter])
                    );

                    $chequeImages[] = $file_name;
                }
            }

            /* ===== UPDATE ONLY IF IMAGES EXIST ===== */

            if (!empty($chequeImages)) {

                $chequeImageString = implode(',', $chequeImages);

                $stmt_update = $conn->prepare(
                    "UPDATE user_bank_details
                     SET cheque_image = ?
                     WHERE user_id = ? AND account_no = ?"
                );

                $stmt_update->bind_param(
                    "sss",
                    $chequeImageString,
                    $user_id,
                    $acno
                );

                $stmt_update->execute();
                $stmt_update->close();
            }
        }

        $response['error'] = false;
        $response['message'] = 'Bank added successfully!!';

    } else {
        $response['error'] = true;
        $response['message'] = 'Required parameters are not available';
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
//     case 'getSupervisorAttendance':  

// $from_date = $_POST['from_date'];  
// $to_date = $_POST['to_date'];  
// $site_id = $_POST['site_id'];  
// $client_code = $_POST['client_code'];
// $status = $_POST['status'];
// $role = strtolower($_POST['role']);
// $emp_code = $_POST['emp_code'];
// $from_year = date('Y', strtotime($from_date));
// $from_month = date('n', strtotime($from_date));
// $from_day = date('j', strtotime($from_date));
// $to_year = date('Y', strtotime($to_date));
// $to_month = date('n', strtotime($to_date));
// $to_day = date('j', strtotime($to_date)); 
// $limit = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
// $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0; 


// if($status == 'All')
// {
//     if($role == "employee")
//     {
//           $stmt = $conn->prepare("SELECT id,emp_name,emp_code,rank,status,concat(at_year,'-',at_month,'-',at_day),created_by FROM attendance WHERE emp_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and active = '0' LIMIT ?,?");  
//           $stmt->bind_param("sss",$emp_code,$offset,$limit); 

//            $stmt_count = $conn->prepare("SELECT Count(id) FROM attendance WHERE emp_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and active = '0' ");  
//           $stmt_count->bind_param("s",$emp_code); 
//     }
//     else
//     {
//         $stmt = $conn->prepare("SELECT id,emp_name,emp_code,rank,status,concat(at_year,'-',at_month,'-',at_day),created_by FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year)  and active = '0' LIMIT ?,?");  
//           $stmt->bind_param("ssss",$site_id,$client_code,$offset,$limit); 

//           $stmt_count = $conn->prepare("SELECT COUNT(id) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and active = '0' ");  
//           $stmt_count->bind_param("ss",$site_id,$client_code); 

//     }
  
// }
// else if($status == 'Absent')
// {
// $stmt = $conn->prepare("SELECT id,emp_name,emp_code,rank,status,concat(at_year,'-',at_month,'-',at_day),created_by FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status='A' and active = '0' LIMIT ?,?");  
// $stmt->bind_param("ssss",$site_id,$client_code,$offset,$limit); 


// $stmt_count = $conn->prepare("SELECT COUNT(id) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status='A' and active = '0'");  
// $stmt_count->bind_param("ss",$site_id,$client_code); 
// }
// else if($status == 'Present')
// {
//  $stmt = $conn->prepare("SELECT id,emp_name,emp_code,rank,status,concat(at_year,'-',at_month,'-',at_day),created_by FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status!='A' and active = '0'  LIMIT ?,?");  
// $stmt->bind_param("ssss",$site_id,$client_code,$offset,$limit); 

//  $stmt_count = $conn->prepare("SELECT COUNT(id) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status!='A' and active = '0' ");  
// $stmt_count->bind_param("ss",$site_id,$client_code); 

// }
// else if($status == 'Sl')
// {
//  $stmt = $conn->prepare("SELECT id,emp_name,emp_code,rank,status,concat(at_year,'-',at_month,'-',at_day),created_by FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status ='SL' and active = '0' LIMIT ?,?");  
// $stmt->bind_param("ssss",$site_id,$client_code,$offset,$limit); 

//  $stmt_count = $conn->prepare("SELECT COUNT(id) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status ='SL' and active = '0' ");  
// $stmt_count->bind_param("ss",$site_id,$client_code); 

// }
// else if($status == 'Cl')
// {
//  $stmt = $conn->prepare("SELECT id,emp_name,emp_code,rank,status,concat(at_year,'-',at_month,'-',at_day),created_by FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status ='CL' and active = '0'  LIMIT ?,?");  
// $stmt->bind_param("ssss",$site_id,$client_code,$offset,$limit); 

//  $stmt_count = $conn->prepare("SELECT COUNT(id) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status ='CL' and active = '0' ");  
// $stmt_count->bind_param("ss",$site_id,$client_code); 

// }
// // else if($status == 'Sl')
// // {
// //  $stmt = $conn->prepare("SELECT id,emp_name,emp_code,rank,status,concat(at_year,'-',at_month,'-',at_day),created_by FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status ='SL' and active = '0'  LIMIT ?,?");  
// // $stmt->bind_param("ssss",$site_id,$client_code,$offset,$limit); 

// //  $stmt_count = $conn->prepare("SELECT COUNT(id) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status ='SL' and active = '0' ");  
// // $stmt_count->bind_param("ss",$site_id,$client_code); 

// // }
// else if($status == 'H')
// {
//  $stmt = $conn->prepare("SELECT id,emp_name,emp_code,rank,status,concat(at_year,'-',at_month,'-',at_day),created_by FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status ='H' and active = '0'  LIMIT ?,?");  
// $stmt->bind_param("ssss",$site_id,$client_code,$offset,$limit); 

//  $stmt_count = $conn->prepare("SELECT COUNT(id) FROM attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status ='H' and active = '0' ");  
// $stmt_count->bind_param("ss",$site_id,$client_code); 

// }
// $stmt->execute();  
// $stmt->store_result(); 

//  $stmt_count->execute();
// $stmt_count->bind_result($total_rows);
// $stmt_count->fetch();
// $stmt_count->close();


// if($stmt->num_rows > 0){  
//     $stmt->bind_result($id,$emp_name,$emp_code,$rank,$status,$date,$created_by);  

//     $banner_data = array();

//     while($stmt->fetch()){

//     $fullname = '';

// if (!empty($created_by)) {
//     $stmt_get_username = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
//     $stmt_get_username->bind_param("s", $created_by);
//     $stmt_get_username->execute();
//     $stmt_get_username->bind_result($fullname);
//     $stmt_get_username->fetch();
//     $stmt_get_username->close();
// }

//      $temp = array(); 
//      $temp['id'] = $id; 
//      $temp['emp_name']=$emp_name ;
//      $temp['emp_code'] = $emp_code;
//      $temp['rank'] = $rank;
//      $temp['status'] = $status;
//      $temp['created_by'] = $fullname;
//      $temp['date'] = date('Y-m-d',strtotime($date));
//      array_push($banner_data, $temp);
//  }

//  $response['error'] = false;   
//  $response['message'] = 'Attendance got successful!!';   
//  $response['user'] = $banner_data;
//   $response['total_rows'] = $total_rows;   
// }  
// else{  
//     $response['error'] = true;   
//     $response['message'] = 'No attendance for this dates!!'; 
//     $response['user'] = array();
// }  
// //}  
// //}  
// break;  

//     case 'getSupervisorAttendance':  

// $from_date = $_POST['from_date'];  
// $to_date = $_POST['to_date'];  
// $site_id = $_POST['site_id'];  
// $client_code = $_POST['client_code'];
// $status = $_POST['status'];
// $emp_code = isset($_POST['emp_code']) ? trim($_POST['emp_code']) : '';

// $from_year = date('Y', strtotime($from_date));
// $from_month = date('n', strtotime($from_date));
// $from_day = date('j', strtotime($from_date));
// $to_year = date('Y', strtotime($to_date));
// $to_month = date('n', strtotime($to_date));
// $to_day = date('j', strtotime($to_date)); 

// $limit = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
// $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0; 

// // Base condition
// $base_condition = " 
// (at_day >= $from_day AND at_month >= $from_month AND at_year >= $from_year)
// AND
// (at_day <= $to_day AND at_month <= $to_month AND at_year <= $to_year)
// AND active = '0'
// ";

// // If emp_code is present add filter
// if(!empty($emp_code)){
//     $base_condition .= " AND emp_code = ? ";
//     $use_emp = true;
// }else{
//     $base_condition .= " AND site_id = ? AND client_code = ? ";
//     $use_emp = false;
// }

// // Status condition
// $status_condition = "";
// if($status == 'Absent'){
//     $status_condition = " AND status = 'A' ";
// }
// else if($status == 'Present'){
//     $status_condition = " AND status != 'A' ";
// }
// else if($status == 'Sl'){
//     $status_condition = " AND status = 'SL' ";
// }
// else if($status == 'Cl'){
//     $status_condition = " AND status = 'CL' ";
// }
// else if($status == 'H'){
//     $status_condition = " AND status = 'H' ";
// }

// // Final query
// $query = "SELECT id,emp_name,emp_code,rank,status,
// concat(at_year,'-',at_month,'-',at_day),created_by 
// FROM attendance 
// WHERE $base_condition $status_condition
// LIMIT ?,?";

// $stmt = $conn->prepare($query);

// // Bind parameters
// if($use_emp){
//     $stmt->bind_param("sii", $emp_code, $offset, $limit);
// }else{
//     $stmt->bind_param("ssii", $site_id, $client_code, $offset, $limit);
// }

// // Count query
// $count_query = "SELECT COUNT(id) FROM attendance WHERE $base_condition $status_condition";
// $stmt_count = $conn->prepare($count_query);

// if($use_emp){
//     $stmt_count->bind_param("s", $emp_code);
// }else{
//     $stmt_count->bind_param("ss", $site_id, $client_code);
// }

// $stmt->execute();  
// $stmt->store_result(); 

// $stmt_count->execute();
// $stmt_count->bind_result($total_rows);
// $stmt_count->fetch();
// $stmt_count->close();

// if($stmt->num_rows > 0){  

//     $stmt->bind_result($id,$emp_name,$emp_code,$rank,$status,$date,$created_by);  
//     $banner_data = array();

//     while($stmt->fetch()){

//         $fullname = '';

//         if (!empty($created_by)) {
//             $stmt_get_username = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
//             $stmt_get_username->bind_param("s", $created_by);
//             $stmt_get_username->execute();
//             $stmt_get_username->bind_result($fullname);
//             $stmt_get_username->fetch();
//             $stmt_get_username->close();
//         }

//         $temp = array(); 
//         $temp['id'] = $id; 
//         $temp['emp_name'] = $emp_name;
//         $temp['emp_code'] = $emp_code;
//         $temp['rank'] = $rank;
//         $temp['status'] = $status;
//         $temp['created_by'] = $fullname;
//         $temp['date'] = date('Y-m-d',strtotime($date));

//         array_push($banner_data, $temp);
//     }

//     $response['error'] = false;   
//     $response['message'] = 'Attendance got successful!!';   
//     $response['user'] = $banner_data;
//     $response['total_rows'] = $total_rows;   
// }  
// else{  
//     $response['error'] = true;   
//     $response['message'] = 'No attendance for this dates!!'; 
//     $response['user'] = array();
// }  

// break;

//     case 'getSupervisorAttendance':

// $from_date   = $_POST['from_date'];
// $to_date     = $_POST['to_date'];
// $site_id     = $_POST['site_id'];
// $client_code = $_POST['client_code'];
// $status      = isset($_POST['status']) ? trim($_POST['status']) : '';
// $emp_code    = isset($_POST['emp_code']) ? trim($_POST['emp_code']) : '';

// $limit  = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
// $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0;

// /*
// --------------------------------------------------
// CORRECT DATE FILTER
// --------------------------------------------------
// Instead of comparing day/month/year separately
// we convert to real DATE and compare properly
// --------------------------------------------------
// */

// $base_condition = "
// STR_TO_DATE(CONCAT(at_year,'-',at_month,'-',at_day),'%Y-%m-%d')
// BETWEEN ? AND ?
// AND active = '0'
// ";

// $params = [];
// $types  = "";

// /* Date params */
// $params[] = $from_date;
// $params[] = $to_date;
// $types   .= "ss";

// /*
// --------------------------------------------------
// EMPLOYEE OR SITE FILTER
// --------------------------------------------------
// */

// if(!empty($emp_code)){
//     $base_condition .= " AND emp_code = ? ";
//     $params[] = $emp_code;
//     $types   .= "s";
// } else {
//     $base_condition .= " AND site_id = ? AND client_code = ? ";
//     $params[] = $site_id;
//     $params[] = $client_code;
//     $types   .= "ss";
// }

// /*
// --------------------------------------------------
// STATUS FILTER
// --------------------------------------------------
// */

// if(!empty($status)){

//     if($status == 'P'){
//         // Present should include W
//         $base_condition .= " AND (status = 'P' OR status = 'W') ";
//     } else {
//         $base_condition .= " AND status = ? ";
//         $params[] = $status;
//         $types   .= "s";
//     }
// }

// /*
// --------------------------------------------------
// FINAL QUERY
// --------------------------------------------------
// */

// $query = "
// SELECT id, emp_name, emp_code, rank, status,
// CONCAT(at_year,'-',LPAD(at_month,2,'0'),'-',LPAD(at_day,2,'0')) as att_date,
// created_by
// FROM attendance
// WHERE $base_condition
// ORDER BY at_year DESC, at_month DESC, at_day DESC
// LIMIT ?, ?
// ";

// $params[] = $offset;
// $params[] = $limit;
// $types   .= "ii";

// $stmt = $conn->prepare($query);
// $stmt->bind_param($types, ...$params);

// $stmt->execute();
// $result = $stmt->get_result();

// /*
// --------------------------------------------------
// COUNT QUERY
// --------------------------------------------------
// */

// $count_query = "SELECT COUNT(id) as total FROM attendance WHERE $base_condition";
// $stmt_count = $conn->prepare($count_query);
// $stmt_count->bind_param(substr($types, 0, strlen($types)-2), ...array_slice($params, 0, -2));
// $stmt_count->execute();
// $count_result = $stmt_count->get_result();
// $count_row = $count_result->fetch_assoc();
// $total_rows = $count_row['total'];

// $stmt_count->close();

// /*
// --------------------------------------------------
// FETCH DATA
// --------------------------------------------------
// */

// if($result->num_rows > 0){

//     $banner_data = [];

//     while($row = $result->fetch_assoc()){

//         $fullname = '';

//         if (!empty($row['created_by'])) {
//             $stmt_user = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
//             $stmt_user->bind_param("s", $row['created_by']);
//             $stmt_user->execute();
//             $res_user = $stmt_user->get_result();
//             if($u = $res_user->fetch_assoc()){
//                 $fullname = $u['fullname'];
//             }
//             $stmt_user->close();
//         }

//         $banner_data[] = [
//             'id'         => $row['id'],
//             'emp_name'   => $row['emp_name'],
//             'emp_code'   => $row['emp_code'],
//             'rank'       => $row['rank'],
//             'status'     => $row['status'],
//             'created_by' => $fullname,
//             'date'       => $row['att_date']
//         ];
//     }

//     $response['error'] = false;
//     $response['message'] = 'Attendance got successful!!';
//     $response['user'] = $banner_data;
//     $response['total_rows'] = $total_rows;

// }else{

//     $response['error'] = true;
//     $response['message'] = 'No attendance for this dates!!';
//     $response['user'] = [];
//     $response['total_rows'] = 0;
// }

// $stmt->close();

// break;

 
 case 'getSupervisorAttendance':

$from_date   = $_POST['from_date'];
$to_date     = $_POST['to_date'];
$site_id     = $_POST['site_id'];
$client_code = $_POST['client_code'];
$status      = isset($_POST['status']) ? trim($_POST['status']) : '';
$emp_code    = isset($_POST['emp_code']) ? trim($_POST['emp_code']) : '';

$limit  = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
$offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0;

date_default_timezone_set('Asia/Kolkata');

/*
--------------------------------------------------
DATE FILTER
--------------------------------------------------
*/

$base_condition = "
STR_TO_DATE(CONCAT(a.at_year,'-',a.at_month,'-',a.at_day),'%Y-%m-%d')
BETWEEN ? AND ?
AND a.active = '0'
";

$params = [];
$types  = "";

/* Date params */
$params[] = $from_date;
$params[] = $to_date;
$types   .= "ss";

/*
--------------------------------------------------
EMPLOYEE OR SITE FILTER
--------------------------------------------------
*/

if(!empty($emp_code)){
    $base_condition .= " AND a.emp_code = ? ";
    $params[] = $emp_code;
    $types   .= "s";
} else {
    $base_condition .= " AND a.site_id = ? AND a.client_code = ? ";
    $params[] = $site_id;
    $params[] = $client_code;
    $types   .= "ss";
}

/*
--------------------------------------------------
STATUS FILTER
--------------------------------------------------
*/

if(!empty($status)){

    if($status == 'P'){
        $base_condition .= " AND (a.status = 'P' OR a.status = 'W') ";
    } else {
        $base_condition .= " AND a.status = ? ";
        $params[] = $status;
        $types   .= "s";
    }
}

/*
--------------------------------------------------
MAIN QUERY (WITH PUNCH DATA)
--------------------------------------------------
*/

$query = "
SELECT
a.id,
a.emp_name,
a.emp_code,
a.rank,
a.status,

CONCAT(a.at_year,'-',LPAD(a.at_month,2,'0'),'-',LPAD(a.at_day,2,'0')) as att_date,

IFNULL(e.check_in_time,'00:00')  as check_in,
IFNULL(e.check_out_time,'00:00') as check_out,

u.fullname as created_by

FROM attendance a

LEFT JOIN emp_attendance e
ON a.emp_code = e.emp_code
AND e.check_in_date = CONCAT(a.at_year,'-',LPAD(a.at_month,2,'0'),'-',LPAD(a.at_day,2,'0'))

LEFT JOIN users u
ON a.created_by = u.id

WHERE $base_condition

ORDER BY a.at_year DESC, a.at_month DESC, a.at_day DESC

LIMIT ?, ?
";

$params[] = $offset;
$params[] = $limit;
$types   .= "ii";

$stmt = $conn->prepare($query);
$stmt->bind_param($types, ...$params);

$stmt->execute();
$result = $stmt->get_result();

/*
--------------------------------------------------
COUNT QUERY
--------------------------------------------------
*/

$count_query = "SELECT COUNT(a.id) as total FROM attendance a WHERE $base_condition";

$stmt_count = $conn->prepare($count_query);

$stmt_count->bind_param(substr($types,0,strlen($types)-2), ...array_slice($params,0,-2));

$stmt_count->execute();

$count_result = $stmt_count->get_result();
$count_row = $count_result->fetch_assoc();
$total_rows = $count_row['total'];

$stmt_count->close();

/*
--------------------------------------------------
FETCH DATA
--------------------------------------------------
*/

if($result->num_rows > 0){

    $banner_data = [];

    while($row = $result->fetch_assoc()){

        $banner_data[] = [
            'id'         => $row['id'],
            'emp_name'   => $row['emp_name'],
            'emp_code'   => $row['emp_code'],
            'rank'       => $row['rank'],
            'status'     => $row['status'],
            'date'       => $row['att_date'],
            'check_in'   => $row['check_in'],
            'check_out'  => $row['check_out'],
            'created_by' => $row['created_by'] ?? ''
        ];
    }

    $response['error'] = false;
    $response['message'] = 'Attendance fetched successfully';
    $response['user'] = $banner_data;
    $response['total_rows'] = $total_rows;

}else{

    $response['error'] = true;
    $response['message'] = 'No attendance for this dates';
    $response['user'] = [];
    $response['total_rows'] = 0;
}

$stmt->close();

break;

//---------------------------------------------------------------------------------------------------------------------


case 'attendanceErrorReport':

$from_date   = $_POST['from_date'];
$to_date     = $_POST['to_date'];
$site_id     = $_POST['site_id'];
$client_code = $_POST['client_code'];
$emp_code    = isset($_POST['emp_code']) ? trim($_POST['emp_code']) : '';

$limit  = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
$offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0;

date_default_timezone_set('Asia/Kolkata');

$data = [];

// -------------------------------
// Get employee list
// -------------------------------
$emp_list = [];
if(!empty($emp_code)){
    $emp_list[] = $emp_code;
} else {
    $emp_query = "SELECT employee_code, emp_full_name as emp_name, COALESCE(rank,'') as rank 
                  FROM employee 
                  WHERE active='0' AND LOWER(em_status) = 'working' 
                  AND site_id=? AND client_id=?";
    $stmt_emp = $conn->prepare($emp_query);
    $stmt_emp->bind_param("ss", $site_id, $client_code);
    $stmt_emp->execute();
    $res_emp = $stmt_emp->get_result();
    while($row = $res_emp->fetch_assoc()){
        $emp_list[] = [
            'code' => $row['employee_code'],
            'name' => $row['emp_name'],
            'rank' => $row['rank']
        ];
    }
    $stmt_emp->close();
}

// -------------------------------
// Loop through employees
// -------------------------------
foreach($emp_list as $emp){
    if(is_array($emp)){
        $code = $emp['code'];
        $emp_name = $emp['name'];
        $emp_rank = $emp['rank'];
    } else {
        $code = $emp;
        $emp_name = '';
        $emp_rank = '';
    }

    // Attendance query
    $query = "
    SELECT
        a.id,
        COALESCE(a.emp_name,?) as emp_name,
        e.emp_code,
        COALESCE(a.rank,?) as rank,
        COALESCE(a.status,'NA') as status,
        e.check_in_date as att_date,
        IFNULL(e.check_in_time,'00:00') as check_in,
        IFNULL(e.check_out_time,'00:00') as check_out,
        u.fullname as created_by
    FROM emp_attendance e
    LEFT JOIN attendance a
        ON a.emp_code = e.emp_code
        AND CONCAT(a.at_year,'-',LPAD(a.at_month,2,'0'),'-',LPAD(a.at_day,2,'0')) = e.check_in_date
    LEFT JOIN users u
        ON a.created_by = u.id
    WHERE e.emp_code = ?
    AND e.check_in_date BETWEEN ? AND ?
    ORDER BY e.check_in_date DESC
    LIMIT ?, ?";

    $stmt = $conn->prepare($query);
    $stmt->bind_param("sssssii", $emp_name, $emp_rank, $code, $from_date, $to_date, $offset, $limit);
    $stmt->execute();
    $result = $stmt->get_result();

    $added_dates = []; // track already added dates

    if($result->num_rows > 0){
        while($row = $result->fetch_assoc()){
            $date_key = $row['emp_code'].'_'.$row['att_date'];
            if(isset($added_dates[$date_key])) continue; // skip duplicates
            $added_dates[$date_key] = true;

            $row['status'] = strtoupper(trim($row['status']));
            if($row['status']=='' || $row['status']=='M' || $row['status']==null){
                $row['status'] = 'NA';
            }

            // Only push a record if check-in exists or status is not NA
            if($row['check_in']!='00:00' || $row['status']!='NA'){
                $data[] = [
                    'id'         => $row['id'] ?? '',
                    'emp_name'   => $row['emp_name'],
                    'emp_code'   => $row['emp_code'],
                    'rank'       => $row['rank'],
                    'status'     => $row['status'],
                    'date'       => $row['att_date'],
                    'check_in'   => $row['check_in'],
                    'check_out'  => $row['check_out'],
                    'created_by' => $row['created_by'] ?? ''
                ];
            }
        }
    } else {
        // No attendance, push default NA record
        $data[] = [
            'id' => '',
            'emp_name' => $emp_name,
            'emp_code' => $code,
            'rank' => $emp_rank,
            'status' => 'NA',
            'date' => $from_date,
            'check_in' => '00:00',
            'check_out' => '00:00',
            'created_by' => ''
        ];
    }

    $stmt->close();
}

// -------------------------------
// Response
// -------------------------------
$response['error'] = false;
$response['message'] = 'Attendance error report fetched';
$response['user'] = $data;
$response['total_rows'] = count($data);

break;

// case 'attendanceErrorReport':

// $from_date   = $_POST['from_date'];
// $to_date     = $_POST['to_date'];
// $site_id     = $_POST['site_id'];
// $client_code = $_POST['client_code'];
// $emp_code    = isset($_POST['emp_code']) ? trim($_POST['emp_code']) : '';

// $limit  = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
// $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0;

// date_default_timezone_set('Asia/Kolkata');

// $params = [];
// $types  = "";

// /*
// --------------------------------------------------
// DATE FILTER
// --------------------------------------------------
// */

// $base_condition = "
// e.check_in_date BETWEEN ? AND ?
// ";

// $params[] = $from_date;
// $params[] = $to_date;
// $types   .= "ss";

// /*
// --------------------------------------------------
// EMPLOYEE / SITE FILTER
// --------------------------------------------------
// */

// if(!empty($emp_code)){
//     $base_condition .= " AND e.emp_code = ? ";
//     $params[] = $emp_code;
//     $types   .= "s";
// }
// else{
//     $base_condition .= " AND e.site_id = ? AND e.client_id = ? ";
//     $params[] = $site_id;
//     $params[] = $client_code;
//     $types   .= "ss";
// }

// /*
// --------------------------------------------------
// ERROR CONDITIONS
// --------------------------------------------------
// */

// $error_condition = "

// (
//     a.id IS NULL

//     OR

//     (IFNULL(e.check_in_time,'00:00')='00:00' AND IFNULL(e.check_out_time,'00:00')='00:00')

//     OR

//     (IFNULL(e.check_in_time,'00:00')!='00:00' AND IFNULL(e.check_out_time,'00:00')='00:00')

//     OR

//     (a.status='A' AND IFNULL(e.check_in_time,'00:00')!='00:00')
// )

// ";

// /*
// --------------------------------------------------
// MAIN QUERY
// --------------------------------------------------
// */

// $query = "

// SELECT

// a.id,

// COALESCE(a.emp_name,'') as emp_name,
// e.emp_code,
// COALESCE(a.rank,'') as rank,

// COALESCE(a.status,'M') as status,

// e.check_in_date as att_date,

// IFNULL(e.check_in_time,'00:00') as check_in,
// IFNULL(e.check_out_time,'00:00') as check_out,

// u.fullname as created_by

// FROM emp_attendance e

// LEFT JOIN attendance a
// ON a.emp_code = e.emp_code
// AND CONCAT(a.at_year,'-',LPAD(a.at_month,2,'0'),'-',LPAD(a.at_day,2,'0')) = e.check_in_date

// LEFT JOIN users u
// ON a.created_by = u.id

// WHERE $base_condition
// AND $error_condition

// ORDER BY e.check_in_date DESC

// LIMIT ?, ?
// ";

// $params[] = $offset;
// $params[] = $limit;
// $types   .= "ii";

// $stmt = $conn->prepare($query);
// $stmt->bind_param($types, ...$params);

// $stmt->execute();
// $result = $stmt->get_result();

// /*
// --------------------------------------------------
// COUNT QUERY
// --------------------------------------------------
// */

// $count_query = "

// SELECT COUNT(*) as total

// FROM emp_attendance e

// LEFT JOIN attendance a
// ON a.emp_code = e.emp_code
// AND CONCAT(a.at_year,'-',LPAD(a.at_month,2,'0'),'-',LPAD(a.at_day,2,'0')) = e.check_in_date

// WHERE $base_condition
// AND $error_condition
// ";

// $stmt_count = $conn->prepare($count_query);

// $stmt_count->bind_param(substr($types,0,strlen($types)-2), ...array_slice($params,0,-2));

// $stmt_count->execute();

// $count_result = $stmt_count->get_result();
// $count_row = $count_result->fetch_assoc();
// $total_rows = $count_row['total'];

// $stmt_count->close();

// /*
// --------------------------------------------------
// FETCH DATA
// --------------------------------------------------
// */

// if($result->num_rows > 0){

//     $data = [];

//     while($row = $result->fetch_assoc()){

//         $data[] = [
//             'id'         => $row['id'] ?? '',
//             'emp_name'   => $row['emp_name'],
//             'emp_code'   => $row['emp_code'],
//             'rank'       => $row['rank'],
//             'status'     => $row['status'],
//             'date'       => $row['att_date'],
//             'check_in'   => $row['check_in'],
//             'check_out'  => $row['check_out'],
//             'created_by' => $row['created_by'] ?? ''
//         ];
//     }

//     $response['error'] = false;
//     $response['message'] = 'Attendance error report fetched';
//     $response['user'] = $data;
//     $response['total_rows'] = $total_rows;

// }
// else{

//     $response['error'] = true;
//     $response['message'] = 'No error records found';
//     $response['user'] = [];
//     $response['total_rows'] = 0;
// }

// $stmt->close();

// break;

//----------------------------------------------------------------------------------------------

// case 'attendanceErrorReport':

// $from_date   = $_POST['from_date'];
// $to_date     = $_POST['to_date'];
// $site_id     = $_POST['site_id'];
// $client_code = $_POST['client_code'];
// $emp_code    = isset($_POST['emp_code']) ? trim($_POST['emp_code']) : '';

// $limit  = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
// $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0;

// date_default_timezone_set('Asia/Kolkata');

// $params = [];
// $types  = "";

// /*
// --------------------------------------------------
// DATE FILTER
// --------------------------------------------------
// */

// $base_condition = "
// e.check_in_date BETWEEN ? AND ?
// ";

// $params[] = $from_date;
// $params[] = $to_date;
// $types   .= "ss";

// /*
// --------------------------------------------------
// EMPLOYEE / SITE FILTER
// --------------------------------------------------
// */

// if(!empty($emp_code)){
//     $base_condition .= " AND e.emp_code = ? ";
//     $params[] = $emp_code;
//     $types   .= "s";
// }
// else{
//     $base_condition .= " AND e.site_id = ? AND e.client_id = ? ";
//     $params[] = $site_id;
//     $params[] = $client_code;
//     $types   .= "ss";
// }

// /*
// --------------------------------------------------
// ERROR CONDITIONS
// --------------------------------------------------
// */

// $error_condition = "

// (
//     a.id IS NULL

//     OR

//     (IFNULL(e.check_in_time,'00:00')='00:00' AND IFNULL(e.check_out_time,'00:00')='00:00')

//     OR

//     (IFNULL(e.check_in_time,'00:00')!='00:00' AND IFNULL(e.check_out_time,'00:00')='00:00')

//     OR

//     (a.status='A' AND IFNULL(e.check_in_time,'00:00')!='00:00')
// )

// ";

// /*
// --------------------------------------------------
// MAIN QUERY
// --------------------------------------------------
// */

// $query = "

// SELECT

// a.id,

// COALESCE(a.emp_name,'') as emp_name,
// e.emp_code,
// COALESCE(a.rank,'') as rank,

// COALESCE(a.status,'M') as status,

// e.check_in_date as att_date,

// IFNULL(e.check_in_time,'00:00') as check_in,
// IFNULL(e.check_out_time,'00:00') as check_out,

// u.fullname as created_by

// FROM emp_attendance e

// LEFT JOIN attendance a
// ON a.emp_code = e.emp_code
// AND CONCAT(a.at_year,'-',LPAD(a.at_month,2,'0'),'-',LPAD(a.at_day,2,'0')) = e.check_in_date

// LEFT JOIN users u
// ON a.created_by = u.id

// WHERE $base_condition
// AND $error_condition

// ORDER BY e.check_in_date DESC

// LIMIT ?, ?
// ";

// $params[] = $offset;
// $params[] = $limit;
// $types   .= "ii";

// $stmt = $conn->prepare($query);
// $stmt->bind_param($types, ...$params);

// $stmt->execute();
// $result = $stmt->get_result();

// /*
// --------------------------------------------------
// COUNT QUERY
// --------------------------------------------------
// */

// $count_query = "

// SELECT COUNT(*) as total

// FROM emp_attendance e

// LEFT JOIN attendance a
// ON a.emp_code = e.emp_code
// AND CONCAT(a.at_year,'-',LPAD(a.at_month,2,'0'),'-',LPAD(a.at_day,2,'0')) = e.check_in_date

// WHERE $base_condition
// AND $error_condition
// ";

// $stmt_count = $conn->prepare($count_query);

// $stmt_count->bind_param(substr($types,0,strlen($types)-2), ...array_slice($params,0,-2));

// $stmt_count->execute();

// $count_result = $stmt_count->get_result();
// $count_row = $count_result->fetch_assoc();
// $total_rows = $count_row['total'];

// $stmt_count->close();

// /*
// --------------------------------------------------
// FETCH DATA
// --------------------------------------------------
// */

// if($result->num_rows > 0){

//     $data = [];

//     while($row = $result->fetch_assoc()){

//         $data[] = [
//             'id'         => $row['id'] ?? '',
//             'emp_name'   => $row['emp_name'],
//             'emp_code'   => $row['emp_code'],
//             'rank'       => $row['rank'],
//             'status'     => $row['status'],
//             'date'       => $row['att_date'],
//             'check_in'   => $row['check_in'],
//             'check_out'  => $row['check_out'],
//             'created_by' => $row['created_by'] ?? ''
//         ];
//     }

//     $response['error'] = false;
//     $response['message'] = 'Attendance error report fetched';
//     $response['user'] = $data;
//     $response['total_rows'] = $total_rows;

// }
// else{

//     $response['error'] = true;
//     $response['message'] = 'No error records found';
//     $response['user'] = [];
//     $response['total_rows'] = 0;
// }

// $stmt->close();

// break;


// case 'attendanceErrorReport':

// $from_date   = $_POST['from_date'];
// $to_date     = $_POST['to_date'];
// $site_id     = $_POST['site_id'];
// $client_code = $_POST['client_code'];
// $emp_code    = isset($_POST['emp_code']) ? trim($_POST['emp_code']) : '';

// $limit  = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
// $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0;

// date_default_timezone_set('Asia/Kolkata');

// $params = [];
// $types  = "";

// /*
// --------------------------------------------------
// BASE CONDITION (EMPLOYEE FILTER)
// --------------------------------------------------
// */

// $base_condition = "
// LOWER(emp.em_status) = 'working'
// AND emp.active = '0'
// ";

// if(!empty($emp_code)){
//     $base_condition .= " AND emp.employee_code = ? ";
//     $params[] = $emp_code;
//     $types   .= "s";
// }
// else{
//     $base_condition .= " AND emp.site_id = ? AND emp.client_id = ? ";
//     $params[] = $site_id;
//     $params[] = $client_code;
//     $types   .= "ss";
// }

// /*
// --------------------------------------------------
// ERROR CONDITIONS
// --------------------------------------------------
// */

// $error_condition = "

// (
//     e.emp_code IS NULL

//     OR

//     (
//         (e.check_in_time IS NULL OR TRIM(e.check_in_time)='' OR LOWER(e.check_in_time)='null')
//         AND
//         (e.check_out_time IS NULL OR TRIM(e.check_out_time)='' OR LOWER(e.check_out_time)='null')
//     )

//     OR

//     (
//         (e.check_in_time IS NOT NULL AND TRIM(e.check_in_time)!='' AND LOWER(e.check_in_time)!='null')
//         AND
//         (e.check_out_time IS NULL OR TRIM(e.check_out_time)='' OR LOWER(e.check_out_time)='null')
//     )

//     OR

//     (
//         (e.check_in_time IS NULL OR TRIM(e.check_in_time)='' OR LOWER(e.check_in_time)='null')
//         AND
//         (e.check_out_time IS NOT NULL AND TRIM(e.check_out_time)!='' AND LOWER(e.check_out_time)!='null')
//     )

//     OR

//     (
//         a.status='A'
//         AND
//         (e.check_in_time IS NOT NULL AND TRIM(e.check_in_time)!='' AND LOWER(e.check_in_time)!='null')
//     )
// )

// ";

// /*
// --------------------------------------------------
// MAIN QUERY
// --------------------------------------------------
// */

// $query = "

// SELECT

// a.id,

// COALESCE(a.emp_name, emp.emp_full_name) as emp_name,
// emp.employee_code AS emp_code,
// COALESCE(a.rank, emp.rank) as rank,

// COALESCE(a.status,'M') as status,

// COALESCE(e.check_in_date, ?) as att_date,

// CASE 
//     WHEN e.check_in_time IS NULL OR TRIM(e.check_in_time)='' OR LOWER(e.check_in_time)='null'
//     THEN '00:00'
//     ELSE e.check_in_time
// END as check_in,

// CASE 
//     WHEN e.check_out_time IS NULL OR TRIM(e.check_out_time)='' OR LOWER(e.check_out_time)='null'
//     THEN '00:00'
//     ELSE e.check_out_time
// END as check_out,

// u.fullname as created_by

// FROM employee emp

// LEFT JOIN emp_attendance e 
// ON emp.employee_code = e.emp_code 
// AND e.check_in_date BETWEEN ? AND ?

// LEFT JOIN attendance a
// ON a.emp_code = emp.employee_code
// AND CONCAT(a.at_year,'-',LPAD(a.at_month,2,'0'),'-',LPAD(a.at_day,2,'0')) 
// = COALESCE(e.check_in_date, ?)

// LEFT JOIN users u
// ON a.created_by = u.id

// WHERE $base_condition
// AND $error_condition

// ORDER BY emp.employee_code ASC

// LIMIT ?, ?
// ";

// /*
// --------------------------------------------------
// PARAMS (IMPORTANT ORDER)
// --------------------------------------------------
// */

// $params_final = [];

// /* SELECT COALESCE date */
// $params_final[] = $from_date;

// /* emp/site filter params */
// $params_final = array_merge($params_final, $params);

// /* JOIN date range */
// $params_final[] = $from_date;
// $params_final[] = $to_date;

// /* JOIN attendance fallback date */
// $params_final[] = $from_date;

// /* pagination */
// $params_final[] = $offset;
// $params_final[] = $limit;

// /*
// types:
// s = select date
// + base condition types
// + ss (date range)
// + s (join fallback)
// + ii (limit)
// */

// $types = "s" . $types . "ss" . "s" . "ii";

// /*
// --------------------------------------------------
// EXECUTE
// --------------------------------------------------
// */

// $stmt = $conn->prepare($query);
// $stmt->bind_param($types, ...$params_final);

// $stmt->execute();
// $result = $stmt->get_result();

// /*
// --------------------------------------------------
// COUNT QUERY
// --------------------------------------------------
// */

// $count_query = "

// SELECT COUNT(*) as total

// FROM employee emp

// LEFT JOIN emp_attendance e 
// ON emp.employee_code = e.emp_code 
// AND e.check_in_date BETWEEN ? AND ?

// LEFT JOIN attendance a
// ON a.emp_code = emp.employee_code
// AND CONCAT(a.at_year,'-',LPAD(a.at_month,2,'0'),'-',LPAD(a.at_day,2,'0')) 
// = COALESCE(e.check_in_date, ?)

// WHERE $base_condition
// AND $error_condition
// ";

// $count_params = [];

// /* emp/site filter params */
// $count_params = array_merge($count_params, $params);

// /* date range */
// $count_params[] = $from_date;
// $count_params[] = $to_date;

// /* fallback */
// $count_params[] = $from_date;

// $count_types = substr($types, 1, strlen($types) - 3);

// $stmt_count = $conn->prepare($count_query);
// $stmt_count->bind_param($count_types, ...$count_params);

// $stmt_count->execute();

// $count_result = $stmt_count->get_result();
// $count_row = $count_result->fetch_assoc();
// $total_rows = $count_row['total'];

// $stmt_count->close();

// /*
// --------------------------------------------------
// FETCH DATA
// --------------------------------------------------
// */

// if($result->num_rows > 0){

//     $data = [];

//     while($row = $result->fetch_assoc()){

//         $data[] = [
//             'id'         => $row['id'] ?? '',
//             'emp_name'   => $row['emp_name'],
//             'emp_code'   => $row['emp_code'],
//             'rank'       => $row['rank'],
//             'status'     => $row['status'],
//             'date'       => $row['att_date'],
//             'check_in'   => $row['check_in'],
//             'check_out'  => $row['check_out'],
//             'created_by' => $row['created_by'] ?? ''
//         ];
//     }

//     $response['error'] = false;
//     $response['message'] = 'Attendance error report fetched';
//     $response['user'] = $data;
//     $response['total_rows'] = $total_rows;

// }
// else{

//     $response['error'] = true;
//     $response['message'] = 'No error records found';
//     $response['user'] = [];
//     $response['total_rows'] = 0;
// }

// $stmt->close();

// break;

// case 'attendanceErrorReport':

// $from_date   = $_POST['from_date'];
// $to_date     = $_POST['to_date'];
// $site_id     = $_POST['site_id'];
// $client_code = $_POST['client_code'];
// $emp_code    = isset($_POST['emp_code']) ? trim($_POST['emp_code']) : '';

// $limit  = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
// $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0;

// date_default_timezone_set('Asia/Kolkata');

// $params = [];
// $types  = "";

// /*
// --------------------------------------------------
// BASE CONDITION
// --------------------------------------------------
// */

// $base_condition = "
// LOWER(emp.em_status) = 'working'
// AND emp.active = '0'
// ";

// if(!empty($emp_code)){
//     $base_condition .= " AND emp.employee_code = ? ";
//     $params[] = $emp_code;
//     $types   .= "s";
// }
// else{
//     $base_condition .= " AND emp.site_id = ? AND emp.client_id = ? ";
//     $params[] = $site_id;
//     $params[] = $client_code;
//     $types   .= "ss";
// }

// /*
// --------------------------------------------------
// ERROR CONDITIONS (same as yours)
// --------------------------------------------------
// */

// $error_condition = "
// (
//     e.emp_code IS NULL
//     OR
//     (IFNULL(e.check_in_time,'00:00')='00:00' AND IFNULL(e.check_out_time,'00:00')='00:00')
//     OR
//     (IFNULL(e.check_in_time,'00:00')!='00:00' AND IFNULL(e.check_out_time,'00:00')='00:00')
//     OR
//     (IFNULL(e.check_out_time,'00:00')!='00:00' AND IFNULL(e.check_in_time,'00:00')='00:00')
//     OR
//     (a.status='A' AND IFNULL(e.check_in_time,'00:00')!='00:00')
// )
// ";

// /*
// --------------------------------------------------
// MAIN QUERY
// --------------------------------------------------
// */

// $query = "

// SELECT
// a.id,
// COALESCE(a.emp_name, emp.emp_full_name) as emp_name,
// emp.employee_code AS emp_code,
// COALESCE(a.rank, emp.rank) as rank,
// COALESCE(a.status,'M') as status,

// COALESCE(e.check_in_date, ?) as att_date,

// IFNULL(e.check_in_time,'00:00') as check_in,
// IFNULL(e.check_out_time,'00:00') as check_out,

// u.fullname as created_by

// FROM employee emp

// LEFT JOIN emp_attendance e 
// ON emp.employee_code = e.emp_code 
// AND e.check_in_date BETWEEN ? AND ?

// LEFT JOIN attendance a
// ON a.emp_code = emp.employee_code
// AND CONCAT(a.at_year,'-',LPAD(a.at_month,2,'0'),'-',LPAD(a.at_day,2,'0')) 
// = COALESCE(e.check_in_date, ?)

// LEFT JOIN users u
// ON a.created_by = u.id

// WHERE $base_condition
// AND $error_condition

// ORDER BY emp.employee_code ASC

// LIMIT ?, ?
// ";

// /*
// --------------------------------------------------
// PARAMS (SAFE STYLE)
// --------------------------------------------------
// */

// /* coalesce date */
// $params[] = $from_date;
// $types = "s" . $types;

// /* join date range */
// $params[] = $from_date;
// $params[] = $to_date;
// $types .= "ss";

// /* fallback date */
// $params[] = $from_date;
// $types .= "s";

// /* pagination */
// $params[] = $offset;
// $params[] = $limit;
// $types .= "ii";

// /*
// --------------------------------------------------
// EXECUTE
// --------------------------------------------------
// */

// $stmt = $conn->prepare($query);
// $stmt->bind_param($types, ...$params);

// $stmt->execute();
// $result = $stmt->get_result();

// /*
// --------------------------------------------------
// COUNT QUERY
// --------------------------------------------------
// */

// $count_query = "

// SELECT COUNT(*) as total

// FROM employee emp

// LEFT JOIN emp_attendance e 
// ON emp.employee_code = e.emp_code 
// AND e.check_in_date BETWEEN ? AND ?

// LEFT JOIN attendance a
// ON a.emp_code = emp.employee_code
// AND CONCAT(a.at_year,'-',LPAD(a.at_month,2,'0'),'-',LPAD(a.at_day,2,'0')) 
// = COALESCE(e.check_in_date, ?)

// WHERE $base_condition
// AND $error_condition
// ";

// $stmt_count = $conn->prepare($count_query);

// $count_params = [];

// /* base condition */
// $count_params = array_merge($count_params, $params);

// /* remove pagination */
// array_pop($count_params);
// array_pop($count_params);

// /* remove first coalesce param */
// array_shift($count_params);

// /* rebuild correctly */
// $count_params[] = $from_date;
// $count_params[] = $to_date;
// $count_params[] = $from_date;

// $count_types = ( !empty($emp_code) ? "s" : "ss" ) . "sss";

// $stmt_count->bind_param($count_types, ...$count_params);



// $stmt_count->execute();

// $count_result = $stmt_count->get_result();
// $total_rows = $count_result->fetch_assoc()['total'];

// $stmt_count->close();

// /*
// --------------------------------------------------
// FETCH DATA
// --------------------------------------------------
// */

// if($result->num_rows > 0){

//     $data = [];

//     while($row = $result->fetch_assoc()){
//         $data[] = [
//             'id'         => $row['id'] ?? '',
//             'emp_name'   => $row['emp_name'],
//             'emp_code'   => $row['emp_code'],
//             'rank'       => $row['rank'],
//             'status'     => $row['status'],
//             'date'       => $row['att_date'],
//             'check_in'   => $row['check_in'],
//             'check_out'  => $row['check_out'],
//             'created_by' => $row['created_by'] ?? ''
//         ];
//     }

//     $response['error'] = false;
//     $response['message'] = 'Attendance error report fetched';
//     $response['user'] = $data;
//     $response['total_rows'] = $total_rows;

// }
// else{
//     $response['error'] = true;
//     $response['message'] = 'No error records found';
//     $response['user'] = [];
//     $response['total_rows'] = 0;
// }

// $stmt->close();

// break;


//----------------------------------------------------------------------------------------------


case 'getSupervisorAttendanceDownload':

$from_date   = $_POST['from_date'];
$to_date     = $_POST['to_date'];
$site_id     = $_POST['site_id'];
$client_code = $_POST['client_code'];
$status      = isset($_POST['status']) ? trim($_POST['status']) : '';
$emp_code    = isset($_POST['emp_code']) ? trim($_POST['emp_code']) : '';

date_default_timezone_set('Asia/Kolkata');

/*
--------------------------------------------------
DATE FILTER
--------------------------------------------------
*/

$base_condition = "
STR_TO_DATE(CONCAT(a.at_year,'-',a.at_month,'-',a.at_day),'%Y-%m-%d')
BETWEEN ? AND ?
AND a.active = '0'
";

$params = [];
$types  = "";

/* Date params */
$params[] = $from_date;
$params[] = $to_date;
$types   .= "ss";

/*
--------------------------------------------------
EMPLOYEE OR SITE FILTER
--------------------------------------------------
*/

if(!empty($emp_code)){
    $base_condition .= " AND a.emp_code = ? ";
    $params[] = $emp_code;
    $types   .= "s";
} else {
    $base_condition .= " AND a.site_id = ? AND a.client_code = ? ";
    $params[] = $site_id;
    $params[] = $client_code;
    $types   .= "ss";
}

/*
--------------------------------------------------
STATUS FILTER
--------------------------------------------------
*/

if(!empty($status)){
    if($status == 'P'){
        $base_condition .= " AND (a.status = 'P' OR a.status = 'W') ";
    } else {
        $base_condition .= " AND a.status = ? ";
        $params[] = $status;
        $types   .= "s";
    }
}

/*
--------------------------------------------------
FINAL QUERY (WITH PUNCH DATA)
--------------------------------------------------
*/

$query = "
SELECT
a.id,
a.emp_name,
a.emp_code,
a.rank,
a.status,

CONCAT(a.at_year,'-',LPAD(a.at_month,2,'0'),'-',LPAD(a.at_day,2,'0')) as att_date,

IFNULL(e.check_in_time,'00:00')  as check_in,
IFNULL(e.check_out_time,'00:00') as check_out,

u.fullname as created_by

FROM attendance a

LEFT JOIN emp_attendance e
ON a.emp_code = e.emp_code
AND e.check_in_date = CONCAT(a.at_year,'-',LPAD(a.at_month,2,'0'),'-',LPAD(a.at_day,2,'0'))

LEFT JOIN users u
ON a.created_by = u.id

WHERE $base_condition

ORDER BY a.at_year DESC, a.at_month DESC, a.at_day DESC
";

$stmt = $conn->prepare($query);

if(!empty($params)){
    $stmt->bind_param($types, ...$params);
}

$stmt->execute();
$result = $stmt->get_result();

/*
--------------------------------------------------
COUNT QUERY
--------------------------------------------------
*/

$count_query = "SELECT COUNT(a.id) as total FROM attendance a WHERE $base_condition";

$stmt_count = $conn->prepare($count_query);

if(!empty($params)){
    $stmt_count->bind_param($types, ...$params);
}

$stmt_count->execute();

$count_result = $stmt_count->get_result();
$count_row = $count_result->fetch_assoc();
$total_rows = $count_row['total'];

$stmt_count->close();

/*
--------------------------------------------------
FETCH DATA
--------------------------------------------------
*/

if($result->num_rows > 0){

    $banner_data = [];

    while($row = $result->fetch_assoc()){

        $banner_data[] = [
            'id'         => $row['id'],
            'emp_name'   => $row['emp_name'],
            'emp_code'   => $row['emp_code'],
            'rank'       => $row['rank'],
            'status'     => $row['status'],
            'date'       => $row['att_date'],
            'check_in'   => $row['check_in'],
            'check_out'  => $row['check_out'],
            'created_by' => $row['created_by'] ?? ''
        ];
    }

    $response['error'] = false;
    $response['message'] = 'Attendance download fetched successfully';
    $response['user'] = $banner_data;
    $response['total_rows'] = $total_rows;

}else{

    $response['error'] = true;
    $response['message'] = 'No attendance for this dates';
    $response['user'] = [];
    $response['total_rows'] = 0;
}

$stmt->close();

break;




//  case 'getSupervisorAttendanceDownload':

// $from_date   = $_POST['from_date'];
// $to_date     = $_POST['to_date'];
// $site_id     = $_POST['site_id'];
// $client_code = $_POST['client_code'];
// $status      = isset($_POST['status']) ? trim($_POST['status']) : '';
// $emp_code    = isset($_POST['emp_code']) ? trim($_POST['emp_code']) : '';

// $base_condition = "
// STR_TO_DATE(CONCAT(at_year,'-',at_month,'-',at_day),'%Y-%m-%d')
// BETWEEN ? AND ?
// AND active = '0'
// ";

// $params = [];
// $types  = "";

// /* Date params */
// $params[] = $from_date;
// $params[] = $to_date;
// $types   .= "ss";

// /*
// --------------------------------------------------
// EMPLOYEE OR SITE FILTER
// --------------------------------------------------
// */

// if(!empty($emp_code)){
//     $base_condition .= " AND emp_code = ? ";
//     $params[] = $emp_code;
//     $types   .= "s";
// } else {
//     $base_condition .= " AND site_id = ? AND client_code = ? ";
//     $params[] = $site_id;
//     $params[] = $client_code;
//     $types   .= "ss";
// }

// /*
// --------------------------------------------------
// STATUS FILTER
// --------------------------------------------------
// */

// if(!empty($status)){

//     if($status == 'P'){
//         // Present includes W
//         $base_condition .= " AND (status = 'P' OR status = 'W') ";
//     } else {
//         $base_condition .= " AND status = ? ";
//         $params[] = $status;
//         $types   .= "s";
//     }
// }

// /*
// --------------------------------------------------
// FINAL QUERY
// --------------------------------------------------
// */

// $query = "
// SELECT id, emp_name, emp_code, rank, status,
// CONCAT(at_year,'-',LPAD(at_month,2,'0'),'-',LPAD(at_day,2,'0')) as att_date,
// created_by
// FROM attendance
// WHERE $base_condition
// ORDER BY at_year DESC, at_month DESC, at_day DESC";

// $stmt = $conn->prepare($query);

// if(!empty($params)){
//     $stmt->bind_param($types, ...$params);
// }

// $stmt->execute();
// $result = $stmt->get_result();

// /*
// --------------------------------------------------
// COUNT QUERY
// --------------------------------------------------
// */

// $count_query = "SELECT COUNT(id) as total FROM attendance WHERE $base_condition";

// $stmt_count = $conn->prepare($count_query);

// if(!empty($params)){
//     $stmt_count->bind_param($types, ...$params);
// }

// $stmt_count->execute();
// $count_result = $stmt_count->get_result();
// $count_row = $count_result->fetch_assoc();
// $total_rows = $count_row['total'];

// $stmt_count->close();

// /*
// --------------------------------------------------
// FETCH DATA
// --------------------------------------------------
// */

// if($result->num_rows > 0){

//     $banner_data = [];

//     while($row = $result->fetch_assoc()){

//         $fullname = '';

//         if (!empty($row['created_by'])) {

//             $stmt_user = $conn->prepare("SELECT fullname FROM users WHERE id = ?");
//             $stmt_user->bind_param("s", $row['created_by']);
//             $stmt_user->execute();
//             $res_user = $stmt_user->get_result();

//             if($u = $res_user->fetch_assoc()){
//                 $fullname = $u['fullname'];
//             }

//             $stmt_user->close();
//         }

//         $banner_data[] = [
//             'id'         => $row['id'],
//             'emp_name'   => $row['emp_name'],
//             'emp_code'   => $row['emp_code'],
//             'rank'       => $row['rank'],
//             'status'     => $row['status'],
//             'created_by' => $fullname,
//             'date'       => $row['att_date']
//         ];
//     }

//     $response['error'] = false;
//     $response['message'] = 'Attendance got successful!!';
//     $response['user'] = $banner_data;
//     $response['total_rows'] = $total_rows;

// }else{

//     $response['error'] = true;
//     $response['message'] = 'No attendance for this dates!!';
//     $response['user'] = [];
//     $response['total_rows'] = 0;
// }

// $stmt->close();

// break;
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

$stmt_dates_count = $conn->prepare("SELECT COUNT(DISTINCT emp_code) from attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) group by at_day,at_month,at_year");  
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
//-----------------------------------------------------------------------------------------

// case 'getEmployeeAttendanceReport':

//     if (isTheseParametersAvailable(array('site_id','client_id','from_date','to_date'))) {

//         date_default_timezone_set('Asia/Kolkata');

//         $site_id   = $_POST['site_id'];
//         $client_id = $_POST['client_id'];
//         $from_date = $_POST['from_date'];
//         $to_date   = $_POST['to_date'];

//         $today = date('Y-m-d');
//         if ($to_date > $today) {
//             $to_date = $today;
//         }

//         $limit  = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
//         $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0;

//         // Fetch employees
//         $stmt_emp = $conn->prepare("
//             SELECT employee_code, first_name,last_name 
//             FROM employee 
//             WHERE site_id=? AND client_id=? 
//             LIMIT ?,?
//         ");

//         //AND em_status = 'Working'

//         $stmt_emp->bind_param("ssii", $site_id, $client_id, $offset, $limit);
//         $stmt_emp->execute();
//         $result_emp = $stmt_emp->get_result();

//         if ($result_emp->num_rows > 0) {

//             $employee_data = array();

//             while ($emp = $result_emp->fetch_assoc()) {

//                 $emp_code = $emp['employee_code'];
//                 $emp_name = $emp['first_name'].' '.$emp['last_name'];

//                 // Proper Date Filtering Using STR_TO_DATE
//                 $stmt = $conn->prepare("
//                     SELECT status, COUNT(*) as total
//                     FROM attendance
//                     WHERE emp_code = ?
//                     AND active = '0'
//                     AND STR_TO_DATE(
//                         CONCAT(at_year, '-', LPAD(at_month,2,'0'), '-', LPAD(at_day,2,'0')),
//                         '%Y-%m-%d'
//                     ) BETWEEN ? AND ?
//                     GROUP BY status
//                 ");

//                 $stmt->bind_param("sss", $emp_code, $from_date, $to_date);
//                 $stmt->execute();
//                 $res = $stmt->get_result();

//                 $present = 0;
//                 $absent = 0;
//                 $halfday = 0;

//                 $sl = 0;
//                 $pl = 0;
//                 $cl = 0;
//                 $ml = 0;

//                 while ($row = $res->fetch_assoc()) {

//                    while ($row = $res->fetch_assoc()) {

//     switch (strtoupper(trim($row['status']))) {

//         case 'P':
//         case 'W': // Weekly off treated as Present
//         case 'H': // Holiday treated as Present
//             $present += $row['total'];
//             break;

//         case 'SL':
//             $sl += $row['total'];
//             $present += $row['total'];  
//             break;

//         case 'PL':
//             $pl += $row['total'];
//             $present += $row['total'];   
//             break;

//         case 'CL':
//             $cl += $row['total'];
//             $present += $row['total'];   
//             break;

//         case 'ML':
//             $ml += $row['total'];
//             $present += $row['total'];   
//             break;

//         case 'HF':
//         case 'HD':
//             $halfday += $row['total'];
//             break;

//         case 'A':
//         default:
//             $absent += $row['total'];
//             break;
//     }
// }
//                 }

//                $total_leave = $sl + $pl + $cl + $ml;
//                $total = $present + $absent + $halfday;

//                 $employee_data[] = array(
//                     'emp_code' => $emp_code,
//     'emp_name' => $emp_name,
//     'present'  => $present,
//     'absent'   => $absent,
//     'halfday'  => $halfday,
//     'sl'       => $sl,
//     'pl'       => $pl,
//     'cl'       => $cl,
//     'ml'       => $ml,
//     'total_leave' => $total_leave,
//     'total'    => $total
//                 );

//                 $stmt->close();
//             }

//             $response['error'] = false;
//             $response['message'] = 'Employee attendance report fetched successfully!';
//             $response['data'] = $employee_data;
//             $response['total_rows'] = $result_emp->num_rows;

//         } else {
//             $response['error'] = false;
//             $response['message'] = 'No employees found for this site!';
//         }

//     } else {
//         $response['error'] = true;
//         $response['message'] = 'Required parameters are missing';
//     }

// break;

// case 'getEmployeeAttendanceReport':

//     if (isTheseParametersAvailable(array('site_id','client_id','from_date','to_date'))) {

//         date_default_timezone_set('Asia/Kolkata');

//         $site_id   = $_POST['site_id'];
//         $client_id = $_POST['client_id'];
//         $from_date = $_POST['from_date'];
//         $to_date   = $_POST['to_date'];

//         $today = date('Y-m-d');
//         if ($to_date > $today) {
//             $to_date = $today;
//         }

//         $limit  = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
//         $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0;

//         // Fetch employees
//         $stmt_emp = $conn->prepare("
//             SELECT employee_code, first_name,last_name 
//             FROM employee 
//             WHERE site_id=? AND client_id=? AND LOWER(em_status) = 'working'
// AND active = '0'  LIMIT ?,?
//         ");

//         $stmt_emp->bind_param("ssii", $site_id, $client_id, $offset, $limit);
//         $stmt_emp->execute();
//         $result_emp = $stmt_emp->get_result();

//         if ($result_emp->num_rows > 0) {

//             $employee_data = array();

//             while ($emp = $result_emp->fetch_assoc()) {

//                 $emp_code = $emp['employee_code'];
//                 $emp_name = $emp['first_name'].' '.$emp['last_name'];

//                 $stmt = $conn->prepare("
//                     SELECT status, COUNT(*) as total
//                     FROM attendance
//                     WHERE emp_code = ?
//                     AND active = '0'
//                     AND STR_TO_DATE(
//                         CONCAT(at_year, '-', LPAD(at_month,2,'0'), '-', LPAD(at_day,2,'0')),
//                         '%Y-%m-%d'
//                     ) BETWEEN ? AND ?
//                     GROUP BY status
//                 ");

//                 $stmt->bind_param("sss", $emp_code, $from_date, $to_date);
//                 $stmt->execute();
//                 $res = $stmt->get_result();

//                 $present = 0;
//                 $absent = 0;
//                 $halfday = 0;

//                 $sl = 0;
//                 $pl = 0;
//                 $cl = 0;
//                 $ml = 0;

//                 // ✅ FIXED: Only ONE while loop
//                 while ($row = $res->fetch_assoc()) {

//                     switch (strtoupper(trim($row['status']))) {

//                         case 'P':
//                         case 'W':
//                         case 'H':
//                             $present += $row['total'];
//                             break;

//                         case 'SL':
//                             $sl += $row['total'];
//                             $present += $row['total'];
//                             break;

//                         case 'PL':
//                             $pl += $row['total'];
//                             $present += $row['total'];
//                             break;

//                         case 'CL':
//                             $cl += $row['total'];
//                             $present += $row['total'];
//                             break;

//                         case 'ML':
//                             $ml += $row['total'];
//                             $present += $row['total'];
//                             break;

//                         case 'HF':
//                         case 'HD':
//                             $halfday += $row['total'];
//                             break;

//                         case 'A':
//                         default:
//                             $absent += $row['total'];
//                             break;
//                     }
//                 }

//                 $total_leave = $sl + $pl + $cl + $ml;
//                 $total = $present + $absent + $halfday;

//                 $employee_data[] = array(
//                     'emp_code' => $emp_code,
//                     'emp_name' => $emp_name,
//                     'present'  => $present,
//                     'absent'   => $absent,
//                     'halfday'  => $halfday,
//                     'sl'       => $sl,
//                     'pl'       => $pl,
//                     'cl'       => $cl,
//                     'ml'       => $ml,
//                     'total_leave' => $total_leave,
//                     'total'    => $total
//                 );

//                 $stmt->close();
//             }

//             $response['error'] = false;
//             $response['message'] = 'Employee attendance report fetched successfully!';
//             $response['data'] = $employee_data;
//             $response['total_rows'] = $result_emp->num_rows;

//         } else {
//             $response['error'] = false;
//             $response['message'] = 'No employees found for this site!';
//         }

//     } else {
//         $response['error'] = true;
//         $response['message'] = 'Required parameters are missing';
//     }

// break;


case 'getEmployeeAttendanceReport':

if (isTheseParametersAvailable(array('site_id','client_id','from_date','to_date'))) {

    date_default_timezone_set('Asia/Kolkata');

    $site_id   = $_POST['site_id'];
    $client_id = $_POST['client_id'];
    $from_date = $_POST['from_date'];
    $to_date   = $_POST['to_date'];

    $today = date('Y-m-d');
    if ($to_date > $today) {
        $to_date = $today;
    }

    $limit  = isset($_POST['limit']) ? intval($_POST['limit']) : 50;
    $offset = isset($_POST['offset']) ? intval($_POST['offset']) : 0;

    $query = "
    SELECT 
        emp.employee_code AS emp_code,
        CONCAT(emp.first_name,' ',emp.last_name) AS emp_name,

        SUM(CASE WHEN a.status IN ('P','W','H') THEN 1 ELSE 0 END) AS present,

        SUM(CASE WHEN a.status IN ('SL','PL','CL','ML') THEN 1 ELSE 0 END) AS total_leave,

        SUM(CASE WHEN a.status IN ('HF','HD') THEN 1 ELSE 0 END) AS halfday,

        SUM(CASE WHEN a.status = 'A' OR a.status IS NULL THEN 1 ELSE 0 END) AS absent,

        SUM(CASE WHEN a.status='SL' THEN 1 ELSE 0 END) AS sl,
        SUM(CASE WHEN a.status='PL' THEN 1 ELSE 0 END) AS pl,
        SUM(CASE WHEN a.status='CL' THEN 1 ELSE 0 END) AS cl,
        SUM(CASE WHEN a.status='ML' THEN 1 ELSE 0 END) AS ml

    FROM employee emp

    LEFT JOIN attendance a 
    ON a.emp_code = emp.employee_code
    AND a.active = '0'
    AND STR_TO_DATE(
        CONCAT(a.at_year,'-',LPAD(a.at_month,2,'0'),'-',LPAD(a.at_day,2,'0')),
        '%Y-%m-%d'
    ) BETWEEN ? AND ?

    WHERE emp.site_id = ?
    AND emp.client_id = ?
    AND LOWER(emp.em_status) = 'working'
    AND emp.active = '0'

    GROUP BY emp.employee_code

    ORDER BY emp.employee_code ASC

    LIMIT ?, ?
    ";

    $stmt = $conn->prepare($query);
    $stmt->bind_param("ssssii", $from_date, $to_date, $site_id, $client_id, $offset, $limit);

    $stmt->execute();
    $result = $stmt->get_result();

    $data = [];

    while ($row = $result->fetch_assoc()) {

        $present = intval($row['present']) + intval($row['total_leave']); // same logic as yours
        $absent  = intval($row['absent']);
        $halfday = intval($row['halfday']);

        $total = $present + $absent + $halfday;

        $data[] = [
            'emp_code' => $row['emp_code'],
            'emp_name' => $row['emp_name'],
            'present'  => $present,
            'absent'   => $absent,
            'halfday'  => $halfday,
            'sl'       => intval($row['sl']),
            'pl'       => intval($row['pl']),
            'cl'       => intval($row['cl']),
            'ml'       => intval($row['ml']),
            'total_leave' => intval($row['total_leave']),
            'total'    => $total
        ];
    }

    // ✅ TOTAL COUNT (correct pagination)
    $count_query = "
    SELECT COUNT(*) as total 
    FROM employee 
    WHERE site_id=? 
    AND client_id=? 
    AND LOWER(em_status)='working'
    AND active='0'
    ";

    $stmt_count = $conn->prepare($count_query);
    $stmt_count->bind_param("ss", $site_id, $client_id);
    $stmt_count->execute();
    $count_result = $stmt_count->get_result();
    $total_rows = $count_result->fetch_assoc()['total'];

    $response['error'] = false;
    $response['message'] = 'Employee attendance report fetched successfully!';
    $response['data'] = $data;
    $response['total_rows'] = $total_rows;

}
else {
    $response['error'] = true;
    $response['message'] = 'Required parameters are missing';
}

break;



case 'getEmployeesLeaveBalance':

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

    $status     = 'Approved';
   

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
         created_by, created_on,approved_on,approved_by)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
    );

    $stmt->bind_param(
        "ssssssssssssssss",
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
        $created_on,
        $created_on,
        $created_by
    );

$conn->begin_transaction();

if ($stmt->execute()) {

    /* -------- Calculate Leave Days -------- */

    $start = new DateTime($from_date);
    $end   = new DateTime($to_date);

    $diff  = $start->diff($end);

    $leave_days = $diff->days + 1;


    /* -------- Decide Column to Update -------- */

    $column = "";

    if ($type == "CL") {
        $column = "cl_used";
    } 
    else if ($type == "SL") {
        $column = "sl_used";
    } 
    else if ($type == "PL") {
        $column = "pl_used";
    } 
    else if ($type == "ML") {
        $column = "ml_used";
    }


    /* -------- Update Leave Balance -------- */

    if ($column != "") {

    $currentMonth = date('n');
$currentYear  = date('Y');

if ($currentMonth >= 4) {
    $fy_from = $currentYear;
    $fy_to   = $currentYear + 1;
} else {
    $fy_from = $currentYear - 1;
    $fy_to   = $currentYear;
}

$update = $conn->prepare("
    UPDATE employee_leaves_balance
    SET $column = $column + ?
    WHERE emp_code = ?
    AND from_year = ?
    AND to_year = ?
    AND active = '0'
");

$update->bind_param("isii", $leave_days, $emp_id, $fy_from, $fy_to);

        $update->execute();
        $update->close();
    }

    $conn->commit();

    $response['error'] = false;
    $response['message'] = 'Leave applied successfully';

} else {

    $conn->rollback();

    $response['error'] = true;
    $response['message'] = 'Failed to apply leave';
}

$stmt->close();

} else {
    $response['error'] = true;
    $response['message'] = 'Required parameters are missing';
}
break;

//-------------------------------------------------------------------------------------------
// case 'getSupervisorAttendanceCountForDashboard':  
// date_default_timezone_set('Asia/Kolkata');
// $site_id = $_POST['site_id'];  
// $client_code = $_POST['client_code'];  
// //$current_date = date('Y-m-d');
// $from_date = $_POST['from_date'];
// $to_date = $_POST['to_date'];
// $from_year = date('Y', strtotime($from_date));
// $from_month = date('n', strtotime($from_date));
// $from_day = date('j', strtotime($from_date));
// $to_year = date('Y', strtotime($to_date));
// $to_month = date('n', strtotime($to_date));
// $to_day = date('j', strtotime($to_date)); 

// $stmt = $conn->prepare("SELECT SUM(emp_nos) FROM `client_other_info` WHERE rate_id=?");  
// $stmt->bind_param("s",$site_id);
// $stmt->execute();  
// $stmt->store_result(); 

// if($stmt->num_rows > 0){  
//     $stmt->bind_result($expected);  
//     $stmt->fetch();
//     $banner_data = array();     
//     $stmt_absent = $conn->prepare("SELECT COUNT(DISTINCT emp_code) from attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status='A' and active='0'");  
// $stmt_absent->bind_param("ss",$site_id,$client_code); 
// $stmt_absent->execute();  
// $stmt_absent->store_result();
// $stmt_absent->bind_result($absent); 
// $stmt_absent->fetch();

//     $stmt_present = $conn->prepare("SELECT COUNT(DISTINCT emp_code) from attendance WHERE site_id = ? and client_code = ? and (at_day>=$from_day and at_month >= $from_month and at_year>=$from_year) and (at_day<=$to_day and at_month <= $to_month and at_year<=$to_year) and status!='A' and active='0'");  
// $stmt_present->bind_param("ss",$site_id,$client_code); 
// $stmt_present->execute();  
// $stmt_present->store_result();
// $stmt_present->bind_result($count);  
// $stmt_present->fetch();

// $stmt_sl = $conn->prepare("SELECT COUNT(DISTINCT emp_code) 
//     FROM attendance 
//     WHERE site_id = ? 
//       AND client_code = ? 
//       AND (at_day >= $from_day AND at_month >= $from_month AND at_year >= $from_year) 
//       AND (at_day <= $to_day   AND at_month <= $to_month   AND at_year <= $to_year)
//       AND status='SL' AND active='0'");

// $stmt_sl->bind_param("ss", $site_id, $client_code);
// $stmt_sl->execute();
// $stmt_sl->store_result();
// $stmt_sl->bind_result($sl);
// $stmt_sl->fetch();

// $stmt_pl = $conn->prepare("SELECT COUNT(DISTINCT emp_code) 
//     FROM attendance 
//     WHERE site_id = ? 
//       AND client_code = ? 
//       AND (at_day >= $from_day AND at_month >= $from_month AND at_year >= $from_year) 
//       AND (at_day <= $to_day   AND at_month <= $to_month   AND at_year <= $to_year)
//       AND status='PL' AND active='0'");

// $stmt_pl->bind_param("ss", $site_id, $client_code);
// $stmt_pl->execute();
// $stmt_pl->store_result();
// $stmt_pl->bind_result($pl);
// $stmt_pl->fetch();



// $stmt_cl = $conn->prepare("SELECT COUNT(DISTINCT emp_code) 
//     FROM attendance 
//     WHERE site_id = ? 
//       AND client_code = ? 
//       AND (at_day >= $from_day AND at_month >= $from_month AND at_year >= $from_year) 
//       AND (at_day <= $to_day   AND at_month <= $to_month   AND at_year <= $to_year)
//       AND status='CL' AND active='0'");

// $stmt_cl->bind_param("ss", $site_id, $client_code);
// $stmt_cl->execute();
// $stmt_cl->store_result();
// $stmt_cl->bind_result($cl);
// $stmt_cl->fetch();

// $stmt_h = $conn->prepare("SELECT COUNT(DISTINCT emp_code) 
//     FROM attendance 
//     WHERE site_id = ? 
//       AND client_code = ? 
//       AND (at_day >= $from_day AND at_month >= $from_month AND at_year >= $from_year) 
//       AND (at_day <= $to_day   AND at_month <= $to_month   AND at_year <= $to_year)
//       AND status='H' AND active='0'");

// $stmt_h->bind_param("ss", $site_id, $client_code);
// $stmt_h->execute();
// $stmt_h->store_result();
// $stmt_h->bind_result($h);
// $stmt_h->fetch();

//      $temp = array(); 
//      $temp['count'] = $count; 
//      //$temp['status']=$status ;
//     // $temp['date'] = sprintf("%04d-%02d-%02d", $year, $month, $day);
//      $temp['expected'] = $expected; 
//      $temp['absent'] = $absent;
//      $temp['sl'] = $sl;
//      $temp['cl'] = $cl;
//      $temp['pl'] = $pl;
//      $temp['h'] = $h;
//      array_push($banner_data, $temp);  

//  $response['error'] = false;   
//  $response['message'] = 'Todays attendance report got successful!!';   
//  $response['user'] = $banner_data; 
// }
// else
// {  
//     $response['error'] = false;   
//     $response['message'] = 'Employees not present!!';  
// } 
// break;  

case 'getSupervisorAttendanceCountForDashboard':  

date_default_timezone_set('Asia/Kolkata');

$site_id     = $_POST['site_id'];  
$client_code = $_POST['client_code'];  
$from_date   = $_POST['from_date'];
$to_date     = $_POST['to_date'];

/*
--------------------------------------------------
EXPECTED EMPLOYEE COUNT
--------------------------------------------------
*/

$stmt = $conn->prepare("SELECT SUM(emp_nos) FROM client_other_info WHERE rate_id=?");  
$stmt->bind_param("s",$site_id);
$stmt->execute();  
$stmt->store_result(); 

if($stmt->num_rows > 0){  

    $stmt->bind_result($expected);  
    $stmt->fetch();

    $banner_data = array();     

    /*
    --------------------------------------------------
    COMMON DATE CONDITION (CORRECT)
    --------------------------------------------------
    */

    $date_condition = "
    STR_TO_DATE(CONCAT(at_year,'-',at_month,'-',at_day),'%Y-%m-%d')
    BETWEEN ? AND ?
    AND site_id = ?
    AND client_code = ?
    AND active = '0'
    ";

    /*
    --------------------------------------------------
    ABSENT COUNT
    --------------------------------------------------
    */

    $stmt_absent = $conn->prepare("
        SELECT COUNT(id)
        FROM attendance
        WHERE $date_condition
        AND status = 'A'
    ");

    $stmt_absent->bind_param("ssss",$from_date,$to_date,$site_id,$client_code);
    $stmt_absent->execute();
    $stmt_absent->bind_result($absent);
    $stmt_absent->fetch();
    $stmt_absent->close();

    /*
    --------------------------------------------------
    PRESENT COUNT (Everything except A)
    --------------------------------------------------
    */

    $stmt_present = $conn->prepare("
        SELECT COUNT(id)
        FROM attendance
        WHERE $date_condition
        AND status != 'A'
    ");

    $stmt_present->bind_param("ssss",$from_date,$to_date,$site_id,$client_code);
    $stmt_present->execute();
    $stmt_present->bind_result($count);
    $stmt_present->fetch();
    $stmt_present->close();

    /*
    --------------------------------------------------
    SL COUNT
    --------------------------------------------------
    */

    $stmt_sl = $conn->prepare("
        SELECT COUNT(id)
        FROM attendance
        WHERE $date_condition
        AND status = 'SL'
    ");

    $stmt_sl->bind_param("ssss",$from_date,$to_date,$site_id,$client_code);
    $stmt_sl->execute();
    $stmt_sl->bind_result($sl);
    $stmt_sl->fetch();
    $stmt_sl->close();

    /*
    --------------------------------------------------
    PL COUNT
    --------------------------------------------------
    */

    $stmt_pl = $conn->prepare("
        SELECT COUNT(id)
        FROM attendance
        WHERE $date_condition
        AND status = 'PL'
    ");

    $stmt_pl->bind_param("ssss",$from_date,$to_date,$site_id,$client_code);
    $stmt_pl->execute();
    $stmt_pl->bind_result($pl);
    $stmt_pl->fetch();
    $stmt_pl->close();

    /*
    --------------------------------------------------
    CL COUNT
    --------------------------------------------------
    */

    $stmt_cl = $conn->prepare("
        SELECT COUNT(id)
        FROM attendance
        WHERE $date_condition
        AND status = 'CL'
    ");

    $stmt_cl->bind_param("ssss",$from_date,$to_date,$site_id,$client_code);
    $stmt_cl->execute();
    $stmt_cl->bind_result($cl);
    $stmt_cl->fetch();
    $stmt_cl->close();

    /*
    --------------------------------------------------
    H COUNT
    --------------------------------------------------
    */

    $stmt_h = $conn->prepare("
        SELECT COUNT(id)
        FROM attendance
        WHERE $date_condition
        AND status = 'H'
    ");

    $stmt_h->bind_param("ssss",$from_date,$to_date,$site_id,$client_code);
    $stmt_h->execute();
    $stmt_h->bind_result($h);
    $stmt_h->fetch();
    $stmt_h->close();

    /*
    --------------------------------------------------
    RESPONSE
    --------------------------------------------------
    */

    $temp = array(); 
    $temp['count']    = $count; 
    $temp['expected'] = $expected; 
    $temp['absent']   = $absent;
    $temp['sl']       = $sl;
    $temp['cl']       = $cl;
    $temp['pl']       = $pl;
    $temp['h']        = $h;

    array_push($banner_data, $temp);  

    $response['error'] = false;   
    $response['message'] = 'Attendance report got successful!!';   
    $response['user'] = $banner_data; 

}
else
{  
    $response['error'] = false;   
    $response['message'] = 'Employees not present!!';  
} 

$stmt->close();

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
  //   case 'updateSupervisorAttendanceStatus': 
  //     if(isTheseParametersAvailable(array('status'))){   
      
  // $id = $_POST['id']; 
  // $status = $_POST['status'];

  //           $stmt = $conn->prepare("UPDATE attendance SET status = ? where id = ?");  
  //           $stmt->bind_param("ss",$status,$id);  
  //           $stmt->execute();
  //           $stmt->close();

  //               $response['error'] = false;   
  //               $response['message'] = 'Attendance status updated successfully!!';   
  //   }  
  //   else{  
  //       $response['error'] = true;   
  //       $response['message'] = 'required parameters are not available';   
  //   }  
  //   break; 


// case 'updateSupervisorAttendanceStatus':

// if(isTheseParametersAvailable(array('status','id'))){

//     date_default_timezone_set('Asia/Kolkata');

//     $id = $_POST['id'];
//     $user_id = $_POST['user_id'];
//     $status = $_POST['status'];
//     $modified_on = date('Y-m-d H:i:s');

//     // Update attendance status
//     $stmt = $conn->prepare("UPDATE attendance SET status=?, modified_by=?, modified_on=? WHERE id=?");
//     $stmt->bind_param("ssss",$status,$user_id,$modified_on,$id);
//     $stmt->execute();
//     $stmt->close();

//     // Get employee details
//     $stmt_get = $conn->prepare("
//         SELECT emp_code, client_code, site_id, at_day, at_month, at_year
//         FROM attendance
//         WHERE id=?
//     ");

//     $stmt_get->bind_param("s",$id);
//     $stmt_get->execute();
//     $result = $stmt_get->get_result();
//     $row = $result->fetch_assoc();
//     $stmt_get->close();

//     if($row){

//         $emp_code = $row['emp_code'];
//         $client_code = $row['client_code'];
//         $site_id = $row['site_id'];

//         $check_in_date = $row['at_year']."-".$row['at_month']."-".$row['at_day'];

//         // Decide time
//         if($status == 'A'){
//             $check_in_time = "00:00";
//             $check_out_time = "00:00";
//         }else{
//             $check_in_time = "09:00 AM";
//             $check_out_time = "00:00";
//         }

//         // Update emp_attendance
//         $stmt_emp = $conn->prepare("
//             UPDATE emp_attendance
//             SET check_in_time=?,
//                 check_out_time=?,
//                 modified_on=?,
//                 modified_by=?
//             WHERE emp_code=? AND check_in_date=? AND site_id=? AND client_id=?
//         ");

//         $stmt_emp->bind_param(
//             "ssssssss",
//             $check_in_time,
//             $check_out_time,
//             $modified_on,
//             $user_id,
//             $emp_code,
//             $check_in_date,
//             $site_id,
//             $client_code
//         );

//         $stmt_emp->execute();
//         $stmt_emp->close();
//     }

//     $response['error'] = false;
//     $response['message'] = 'Attendance status updated successfully!!';

// }
// else{
//     $response['error'] = true;
//     $response['message'] = 'required parameters are not available';
// }

// break;


case 'updateSupervisorAttendanceStatus':

if(isTheseParametersAvailable(array('status','id','check_in','check_out'))){

    date_default_timezone_set('Asia/Kolkata');

    $id = $_POST['id'];
    $user_id = $_POST['user_id'];
    $status = $_POST['status'];
    $check_in_time = $_POST['check_in'];    // NEW
    $check_out_time = $_POST['check_out'];  // NEW
    $modified_on = date('Y-m-d H:i:s');

    // Update attendance status
    $stmt = $conn->prepare("UPDATE attendance SET status=?, modified_by=?, modified_on=? WHERE id=?");
    $stmt->bind_param("ssss",$status,$user_id,$modified_on,$id);
    $stmt->execute();
    $stmt->close();

    // Get employee details
    $stmt_get = $conn->prepare("
        SELECT emp_code, client_code, site_id, at_day, at_month, at_year
        FROM attendance
        WHERE id=?
    ");
    $stmt_get->bind_param("s",$id);
    $stmt_get->execute();
    $result = $stmt_get->get_result();
    $row = $result->fetch_assoc();
    $stmt_get->close();

    if($row){
        $emp_code = $row['emp_code'];
        $client_code = $row['client_code'];
        $site_id = $row['site_id'];
        
        $month = str_pad($row['at_month'], 2, '0', STR_PAD_LEFT);
$day   = str_pad($row['at_day'], 2, '0', STR_PAD_LEFT);

$check_in_date = $row['at_year']."-".$month."-".$day;

        // Update emp_attendance with values from POST
        $stmt_emp = $conn->prepare("
            UPDATE emp_attendance
            SET check_in_time=?,
                check_out_time=?,
                modified_on=?,
                modified_by=?
            WHERE emp_code=? AND check_in_date=?
        ");

        $stmt_emp->bind_param(
            "ssssss",
            $check_in_time,
            $check_out_time,
            $modified_on,
            $user_id,
            $emp_code,
            $check_in_date
        );

        $stmt_emp->execute();
        $stmt_emp->close();
    }

    $response['error'] = false;
    $response['message'] = 'Attendance status updated successfully!!';

} else {
    $response['error'] = true;
    $response['message'] = 'Required parameters are not available';
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
    $client_name = $_POST['client_name']; 
    $site_name = $_POST['site_name']; 
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


    // FCM notifications
$stmt_approvers = $conn->prepare("
    SELECT u.device_token, u.id, u.first_name 
    FROM voucher_approvers va
    JOIN users u ON va.user_id = u.id
    WHERE va.active = '0' AND u.active = '0'
");
$stmt_approvers->execute();
$result = $stmt_approvers->get_result();

if ($result->num_rows > 0) {

//echo ' number of rows - '.$result->num_rows;
    // Initialize Firebase client
    $client = new Client();
    $client->setAuthConfig(__DIR__ . '/hrms-notifications-a063b-2b8b0eb9b3b0.json');
    $client->addScope('https://www.googleapis.com/auth/firebase.messaging');
    $accessToken = $client->fetchAccessTokenWithAssertion()['access_token'];

    $projectId = "hrms-notifications-a063b";
    $url = "https://fcm.googleapis.com/v1/projects/{$projectId}/messages:send";
    $http = new GuzzleClient();

    $title = "Voucher Added!!";
    $body  = "Added by " . $beneficiary_name . " with amount of ₹" . $original_amount;

    while ($row = $result->fetch_assoc()) {

        $token = $row['device_token'];

        if (empty($token)) continue; // skip users without token

        // Optional: print token for debugging
         //echo "Sending FCM to token: " . $token . "<br>";

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

        try {
            $response_new = $http->post($url, [
                "headers" => [
                    "Authorization" => "Bearer " . $accessToken,
                    "Content-Type"  => "application/json"
                ],
                "json" => $message
            ]);

            // Optional: check response
             //echo "FCM sent to {$row['name']} (" . $token . ")<br>";
        } catch (\GuzzleHttp\Exception\ClientException $e) {
            // Catch FCM errors for this token and continue
           // echo "Error sending to token {$token}: " . $e->getMessage() . "<br>";
            continue;
        }
    }

} else {
    echo "No active approvers found.";
}


  /*  //fcm notifications
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
      //  $client->setAuthConfig(__DIR__ . '/hrms-notifications-a063b-2b8b0eb9b3b0.json');
        // $client->setAuthConfig('hrms-notifications-a063b-2b8b0eb9b3b0.json');
        // //$client->setAuthConfig(__DIR__ . '/hrmsnotify-569ac-5424f3d07fc5.json');
        // $client->addScope('https://www.googleapis.com/auth/firebase.messaging');
        // $accessToken = $client->fetchAccessTokenWithAssertion()["access_token"];

       $client = new Client();
$client->setAuthConfig(__DIR__ . '/hrms-notifications-a063b-2b8b0eb9b3b0.json');
$client->addScope('https://www.googleapis.com/auth/firebase.messaging');
$accessToken = $client->fetchAccessTokenWithAssertion()['access_token'];


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


    }*/
  

    $response['error'] = false;
    $response['message'] = 'Voucher added successfully!!';
}
else {
    $response['error'] = true;
    $response['message'] = 'Required parameters are not available';
}

break;



//---------------------------------------------------------------------------- 
case 'updateVoucher':

    if(isTheseParametersAvailable(array('client_id'))){  
        date_default_timezone_set('Asia/Kolkata');
        $client_id = $_POST['client_id']; 
        $site_id = $_POST['site_id'];
        $client_name = $_POST['client_name']; 
        $site_name = $_POST['site_name'];
        $onlineImgCount = $_POST['onlineImgCount']; 
        // $emp_code = $_POST['emp_code']; 
        // $emp_id = $_POST['emp_id'];
        // $emp_name = $_POST['emp_name']; 
        $gang = $_POST['gang']; 
       // $type = $_POST['type']; 
        $adv_date = $_POST['adv_date']; 
        $mode = $_POST['mode']; 
        $modified_by = $_POST['modified_by'];
        $modified_on = date('Y-m-d H:i:s');
        //$count = $_POST['count'];
        $voucher_no = $_POST['voucher_no'];
        $part1 = $_POST['part1'];
        $amt1 = $_POST['amt1'];

       //proof images
       $proofCount = $_POST['proofCount'];

       //  for($i=0;$i<$count;$i++)
       //  {
       // if(($_POST['part'.$i+1] !='') && ($_POST['amt'.$i+1]!='')){

         // $stmt_check_in = $conn->prepare("UPDATE emp_advance SET client_id=?,site_id=?,client_name=?,site_name=?,emp_code=?,emp_id=?,emp_name=?,type=?,mode=?,adv_date=?,gang_name=?,particular=?,amount=?,modified_on=?,modified_by=? where voucher_no =? and id = ?"); 

         //             $stmt_check_in->bind_param("sssssssssssssssss",$client_id,$site_id,$client_name,$site_name,$emp_code,$emp_id,$emp_name,$type,$mode,$adv_date,$gang,$_POST['part'.$i+1],$_POST['amt'.$i+1],$modified_on,$modified_by,$voucher_no,$_POST['voucher'.$i]);


                     $stmt_check_in = $conn->prepare("UPDATE emp_advance SET client_id=?,site_id=?,client_name=?,site_name=?,mode=?,adv_date=?,gang_name=?,particular=?,amount=?,modified_on=?,modified_by=? where voucher_no =?"); 

                     $stmt_check_in->bind_param("ssssssssssss",$client_id,$site_id,$client_name,$site_name,$mode,$adv_date,$gang,$part1,$amt1,$modified_on,$modified_by,$voucher_no);

                     if($stmt_check_in->execute())
                     {

                                //proof images
                         if($proofCount>0)
                         {

                        if($onlineImgCount==0)
                        {
                            $counter = 1;
                        }   
                        else
                        {
                            $counter = $onlineImgCount + 1;
                        }    


                          for ($i=0; $i < $proofCount; $i++)
                            {

                              $newcount = $i + 1;   
                              $file_path = "emp_docs/".$client_name."_voucher_".$voucher_no."_".$counter.".jpg";
                              file_put_contents($file_path, base64_decode($_POST['proof'.$newcount])); 
                              $img_name = $client_name."_voucher_".$voucher_no."_".$counter.".jpg";
                              $counter = $counter + 1;
                              //echo $new_id;
                              if($i==0)
                              {

                                 $nothing = "";

                                  if($onlineImgCount<=0)
                                   {
                                        //clear all image names only for first time
                                 $result1 = $conn->prepare("UPDATE emp_advance SET proof_images = ''  WHERE voucher_no = '$voucher_no'");
                                 //$result1->bind_param('s',$);
                                 $result1->execute();

                                  $result2 = $conn->prepare("UPDATE emp_advance SET proof_images= '$img_name' WHERE voucher_no = '$voucher_no'");
                                $result2->execute();

                                   } 
                                   else
                                   {
                                        $result2 = $conn->prepare("UPDATE emp_advance SET proof_images = CONCAT(proof_images,',$img_name') WHERE voucher_no = '$voucher_no'");
                                       $result2->execute();
                                   } 
                              }
                              else
                              {
                                $result2 = $conn->prepare("UPDATE emp_advance SET proof_images = CONCAT(proof_images,',$img_name') WHERE voucher_no = '$voucher_no'");
                              $result2->execute();
                              }  
                            } 
                        }
                        //case for edit - if no new images uploaded , then just reinsert names of online images already present in voucher.
                        //it can be optional else loop , as nothing is present , then nothing is needed to handle it.
                        else if($proofCount<=0 && $onlineImgCount!=0)
                        {

                            $counter = $onlineImgCount + 1;
                     
                            for ($i=0; $i < $onlineImgCount; $i++){

                                //$counter = $onlineImgCount + 1; 
                                $img_name = $client_name."_voucher_".$voucher_no."_".$counter.".jpg";
                                $counter = $counter + 1;

                            if($i==0)
                              {

                                 $nothing = "";
                                //clear all image names only for first time
                                 $result1 = $conn->prepare("UPDATE emp_advance SET proof_images = ''  WHERE voucher_no = '$voucher_no'");
                                 //$result1->bind_param('s',$);
                                 $result1->execute();
                                
                                $result2 = $conn->prepare("UPDATE emp_advance SET proof_images= '$img_name' WHERE voucher_no = '$voucher_no'");
                                $result2->execute();
                              }
                              else
                              {
                                $result2 = $conn->prepare("UPDATE emp_advance SET proof_images = CONCAT(proof_images,',$img_name') WHERE voucher_no = '$voucher_no'");
                              $result2->execute();
                              }  
                            }
                        }    
                        //case for edit - if no online images already present in voucher , as well as no new images uploaded.
                        //it can be optional else loop , as nothing is present , then nothing is needed to handle it.
                        else
                        {
                             $result3 = $conn->prepare("UPDATE emp_advance SET proof_images = '' WHERE voucher_no = '$voucher_no'");
                                // $result3->bind_param('s',"");
                                 $result3->execute();
                        }
                            

                      $response['error'] = false;   
                      $response['message'] = 'Voucher updated successfully!!'; 
                     }  
                      else{
                       $response['error'] = true;   
                       $response['message'] = 'Some problem occured!!';   
                      } 
                 // }

        //}    
                
}
    else
    {
        $response['error'] = true;   
        $response['message'] = 'required parameters are not available';  
    }
    break; 
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


if ($status == "Approved") {
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
        emp_id,emp_code,particular,SUM(amount),created_by,created_on,approved_on,rejected_on,utr_updated_by,utr_updated_on
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
        emp_id,emp_code,particular,SUM(amount),created_by,created_on,approved_on,rejected_on,utr_updated_by,utr_updated_on
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
        emp_id,emp_code,particular,SUM(amount),created_by,created_on,approved_on,rejected_on,utr_updated_by,utr_updated_on
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
        $emp_id,$emp_code,$particular,$amount,$created_by,$created_on,$approved_on,$rejected_on,$utr_updated_by,$utr_updated_on
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


        $temp = array(); 
        $temp['id'] = $id; 
        $temp['date'] = $date;
        $temp['type'] = $type;
        $temp['mode'] = $mode;
        $temp['status'] = $status;
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

 $stmt = $conn->prepare("SELECT id,adv_date,type,mode,status,vendor_comm,approved_by,approved_on,rejected_by,emp_name,voucher_no,client_name,site_name,gang_name,client_id,site_id,emp_id,emp_code,particular,SUM(amount),bank_name, bank_ac_no, ifsc_code, bank_branch, beneficiary_name, rejected_on from emp_advance WHERE voucher_no = ? group by voucher_no");
 
            $stmt->bind_param("s",$voucher_no);    
            $stmt->execute();  
            $stmt->store_result(); 

if($stmt->num_rows > 0){  
    $stmt->bind_result($id,$date,$type,$mode,$status,$vendor,$approved_by,$approved_on,$rejected_by,$emp_name,$voucher_no,$client,$site,$gang,$client_id,$site_id,$emp_id,$emp_code,$particular,$amount,$bank,$acc_no,$ifsc,$bank_branch,$beneficiary_name,$rejected_on);  

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
     $temp['rejected_on'] = $rejected_on;



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

//-----------------------------------------------------------------------------------------------------------------
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