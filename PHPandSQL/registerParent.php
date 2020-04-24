<?php
   $mysqli = new mysqli('localhost', 'root', '', 'DB2'); //The Blank string is the password

   $email = $_POST['email'];
   $password = $_POST['password'];
   $name = $_POST['name'];
   $phone = $_POST['phone'];

   $failure = false;

   $query = "SELECT * FROM users WHERE email='$email'"; //You don't need a ; like you do in SQL
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
     $response['error'] = "You must enter a password to register";
     $failure = true;
     echo json_encode($response);
   }
   else if($name === ""  && !$failure) {
     // name is blank, return error message and don't insert
     $response = array();
     $response['success'] = "false";
     $response['error'] = "You must enter a name to register";
     $failure = true;
     echo json_encode($response);
   }
   else if($phone === ""  && !$failure) {
     // phone is blank, return error message and don't insert
     $response = array();
     $response['success'] = "false";
     $response['error'] = "You must enter a phone number to register";
     $failure = true;
     echo json_encode($response);
   }
   else if($result->num_rows !== 0  && !$failure) {
     // email is already taken, return error message and don't insert
     $response = array();
     $response['success'] = "false";
     $response['error'] = "That email is already taken";
     $failure = true;
     echo json_encode($response);
   }
   else if(!$failure){
     // If all the checks were successful, actually register the parent

     $register = "INSERT INTO users (email, password, name, phone) VALUES ('{$email}', '{$password}', '{$name}', '{$phone}')";
     $insert_result = $mysqli->query($register);

     $query = "SELECT * FROM users WHERE email='$email'"; //You don't need a ; like you do in SQL
     $result = $mysqli->query($query);

     $row = mysqli_fetch_array($result);
     $pid = $row['id'];
     $pname = $row['name'];

     $registerp = "INSERT INTO parents (parent_id) VALUES ('{$pid}')";
     $insert_result2 = $mysqli->query($registerp);

     $response = array();
     $response['success'] = "true";
     $response['error'] = "";
     $response['pid'] = $pid;
     $response['name'] = $pname;
     echo json_encode($response);
   }

   $mysqli->close();

?>
