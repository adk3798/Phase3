<?php
   $mysqli = new mysqli('localhost', 'root', '', 'DB2'); //The Blank string is the password

   $sid = (int) $_POST['sid'];
   $meet_id = (int) $_POST['mid'];
   $type = $_POST['type'];
   if($type === "mentor") {
     $query = "SELECT * FROM mentors WHERE mentor_id=$sid";
     $result = $mysqli->query($query);

     if($result->num_rows === 0) {
       $query = "INSERT INTO mentors (mentor_id) VALUES ({$sid})";
       $result = $mysqli->query($query);
     }

     $query = "INSERT INTO enroll2 (meet_id, mentor_id) VALUES ({$meet_id}, {$sid})";
     $result = $mysqli->query($query);

     $response = array();
     echo json_encode($response);
   }
   else if($type === "mentee") {
     $query = "SELECT * FROM mentees WHERE mentee_id=$sid";
     $result = $mysqli->query($query);

     if($result->num_rows === 0) {
       $query = "INSERT INTO mentees (mentee_id) VALUES ({$sid})";
       $result = $mysqli->query($query);
     }

     $query = "INSERT INTO enroll (meet_id, mentee_id) VALUES ({$meet_id}, {$sid})";
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
