<?php
   $mysqli = new mysqli('localhost', 'root', '', 'DB2'); //The Blank string is the password

   $sid = (int) $_POST['sid'];
   $meet_id = (int) $_POST['mid'];
   $type = $_POST['type'];

   if($type === "mentor") {
     $query = "DELETE FROM enroll2 WHERE mentor_id='$sid' AND meet_id='$meet_id'";
     $result = $mysqli->query($query);

     $response = array();
     echo json_encode($response);
   }
   else if($type === "mentee") {
     $query = "DELETE FROM enroll WHERE mentee_id='$sid' AND meet_id='$meet_id'";
     $result = $mysqli->query($query);

     $response = array();
     echo json_encode($response);
   }
   else {
     $response = array();
     $response["error"] = "Got meeting type not mentee or mentor";
     echo json_encode($response);
   }

?>
