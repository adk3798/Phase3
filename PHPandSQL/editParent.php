<?php
   $mysqli = new mysqli('localhost', 'root', '', 'DB2'); //The Blank string is the password

   $email = $_POST['email'];
   $password = $_POST['password'];
   $name = $_POST['name'];
   $phone = $_POST['phone'];
   $sid = (int) $_POST['id'];
   $cemail = $_POST['cemail'];
   $cpassword = $_POST['cpassword'];
   $cname = $_POST['cname'];
   $cphone = $_POST['cphone'];

   $failure = false;

   $query = "SELECT * FROM users WHERE id=$sid"; //You don't need a ; like you do in SQL
   $result = $mysqli->query($query);

   function checkEmail($test_email) {
     $atSignPos = strpos($test_email, '@');
     $periodPos = strpos($test_email, '.');
     return ($atSignPos !== false && $periodPos !== false && $periodPos > $atSignPos);
   }

   if(!checkEmail($email) && $cemail === "true") {
     // email does not fit format of an email address, return error message and don't insert
     $response = array();
     $response['success'] = "false";
     $response['error'] = "Cannot change to invalid email";
     $failure = true;
     echo json_encode($response);
   }
   else if($password === "" && !$failure && $cpassword === "true") {
     // password is blank, return error message and don't insert
     $response = array();
     $response['success'] = "false";
     $response['error'] = "Cannot change password to empty";
     $failure = true;
     echo json_encode($response);
   }
   else if($name === ""  && !$failure && $cname === "true") {
     // name is blank, return error message and don't insert
     $response = array();
     $response['success'] = "false";
     $response['error'] = "Cannot change name to empty";
     $failure = true;
     echo json_encode($response);
   }
   else if($phone === ""  && !$failure && $cphone === "true") {
     // phone is blank, return error message and don't insert
     $response = array();
     $response['success'] = "false";
     $response['error'] = "Cannot change phone number to empty";
     $failure = true;
     echo json_encode($response);
   }
   else if($result->num_rows === 0  && !$failure) {
     // student is not in database
     $response = array();
     $response['success'] = "false";
     $response['error'] = "This parent does not exist";
     $failure = true;
     echo json_encode($response);
   }
   else if(!$failure){
     $update_query = "UPDATE users SET";
     if($cemail === "true") {
       $update_query = $update_query . " email = '$email',";
     }
     if($cpassword === "true") {
       $update_query = $update_query . " password = '$password',";
     }
     if($cname === "true") {
       $update_query = $update_query . " name = '$name',";
     }
     if($cphone === "true") {
       $update_query = $update_query . " phone = '$phone',";
     }
     $update_query = substr($update_query, 0, -1) . " WHERE id=$sid";
     $result = $mysqli->query($update_query);

     $response = array();
     $response['success'] = "true";
     $response['error'] = "";
     echo json_encode($response);
   }

   $mysqli->close();

?>
