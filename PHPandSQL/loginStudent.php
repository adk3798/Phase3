<?php
   $mysqli = new mysqli('localhost', 'root', '', 'DB2'); //The Blank string is the password

   $email = $_POST['email'];
   $password = $_POST['password'];

   $failure = false;

   $query = "SELECT * FROM users WHERE email='$email' AND password='$password'"; //You don't need a ; like you do in SQL
   $result = $mysqli->query($query);

   function checkEmail($test_email) {
     $atSignPos = strpos($test_email, '@');
     $periodPos = strpos($test_email, '.');
     return ($atSignPos !== false && $periodPos !== false && $periodPos > $atSignPos);
   }

   if(!checkEmail($email)) {
     // email does not fit format of an email address, return error message and don't insert
     $response = array();
     $response['success'] = "false";
     $response['error'] = "Given email was invalid";
     $failure = true;
     echo json_encode($response);
   }
   else if($password === "" && !$failure) {
     // password is blank, return error message and don't insert
     $response = array();
     $response['success'] = "false";
     $response['error'] = "No password given";
     $failure = true;
     echo json_encode($response);
   }
   else if($result->num_rows === 0  && !$failure) {
     // email is already taken, return error message and don't insert
     $response = array();
     $response['success'] = "false";
     $response['error'] = "No accounts exist with that email and password";
     $failure = true;
     echo json_encode($response);
   }
   else if(!$failure){
     // If all the checks were successful, actually register the parent

     $row = mysqli_fetch_array($result);
     $sid = $row['id'];

     $query2 = "SELECT * FROM students WHERE student_id=$sid";
     $result2 = $mysqli->query($query2);

     if($result2->num_rows !== 0  && !$failure) {
       $response = array();
       $response['success'] = "true";
       $response['error'] = "";
       $response['sid'] = $sid;
       $response['name'] = $row['name'];
       echo json_encode($response);
     }
     else {
       $response = array();
       $response['success'] = "false";
       $response['error'] = "Email and password given is not associated with a student account!";
       $failure = true;
       echo json_encode($response);
     }
   }

   $mysqli->close();

?>
