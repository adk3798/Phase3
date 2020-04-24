<?php
   $mysqli = new mysqli('localhost', 'root', '', 'DB2'); //The Blank string is the password

   $email = $_POST['email'];
   $parentEmail = $_POST['parentEmail'];
   $password = $_POST['password'];
   $name = $_POST['name'];
   $phone = $_POST['phone'];
   $grade = (int) $_POST['grade'];

   $failure = false;

   $query = "SELECT * FROM users WHERE email='$email'"; //You don't need a ; like you do in SQL
   $result = $mysqli->query($query);

   $parent_query = "SELECT * FROM users WHERE email='$parentEmail' AND id IN(SELECT parent_id FROM parents)"; //You don't need a ; like you do in SQL
   $parent_result = $mysqli->query($parent_query);

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
   else if(!checkEmail($parentEmail) && !$failure) {
     // email does not fit format of an email address, return error message and don't insert
     $response = array();
     $response['success'] = "false";
     $response['error'] = "Given parent email was invalid";
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
   else if(($grade < 6 || $grade > 12) && !$failure) {
     // phone is blank, return error message and don't insert
     $response = array();
     $response['success'] = "false";
     $response['error'] = "Received invalid grade level";
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
   else if($parent_result->num_rows === 0  && !$failure) {
     // given parent email doesn't belong to a parent
     $response = array();
     $response['success'] = "false";
     $response['error'] = "Given parent email is not associated with a parent acount";
     $failure = true;
     echo json_encode($response);
   }
   else if(!$failure){
     $parent_row = mysqli_fetch_array($parent_result);
     $pid = $parent_row['id'];

     $register = "INSERT INTO users (email, password, name, phone) VALUES ('{$email}', '{$password}', '{$name}', '{$phone}')";
     $insert_result = $mysqli->query($register);

     $query = "SELECT * FROM users WHERE email='$email'"; //You don't need a ; like you do in SQL
     $result = $mysqli->query($query);

     $row = mysqli_fetch_array($result);
     $sid = $row['id'];
     $sname = $row['name'];

     $registers = "INSERT INTO students (student_id, grade, parent_id) VALUES ('{$sid}', '{$grade}', '{$pid}')";
     $insert_result2 = $mysqli->query($registers);

     $response = array();
     $response['success'] = "true";
     $response['error'] = "";
     $response['sid'] = $sid;
     $response['name'] = $sname;
     echo json_encode($response);
   }

   $mysqli->close();

?>
