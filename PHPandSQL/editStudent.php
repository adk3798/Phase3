<?php
   $mysqli = new mysqli('localhost', 'root', '', 'DB2'); //The Blank string is the password

   $email = $_POST['email'];
   $password = $_POST['password'];
   $name = $_POST['name'];
   $phone = $_POST['phone'];
   $grade = (int) $_POST['grade'];
   $sid = (int) $_POST['id'];
   $cemail = $_POST['cemail'];
   $cpassword = $_POST['cpassword'];
   $cname = $_POST['cname'];
   $cphone = $_POST['cphone'];
   $cgrade = $_POST['cgrade'];

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
   else if(($grade < 6 || $grade > 12) && !$failure  && $cgrade === "true") {
     // phone is blank, return error message and don't insert
     $response = array();
     $response['success'] = "false";
     $response['error'] = "Received invalid grade level";
     $failure = true;
     echo json_encode($response);
   }
   else if($result->num_rows === 0  && !$failure) {
     // student is not in database
     $response = array();
     $response['success'] = "false";
     $response['error'] = "This student does not exist";
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

     if($cgrade === "true") {
       $update_query = "UPDATE students SET grade = $grade WHERE student_id=$sid";
       $result = $mysqli->query($update_query);
       // remove student from all meetings when changing grades
       $query = "DELETE FROM mentors WHERE mentor_id=$sid";
       $result = $mysqli->query($query);
       $query = "DELETE FROM mentees WHERE mentee_id=$sid";
       $result = $mysqli->query($query);
     }

     $response = array();
     $response['success'] = "true";
     $response['error'] = "";
     echo json_encode($response);
   }

   $mysqli->close();

?>
