<?php
   $mysqli = new mysqli('localhost', 'root', '', 'DB2'); //The Blank string is the password

   $sid = (int) $_POST['sid'];
   //$sid = 29;
   $today = date("Y-m-d");

   $meetings_query =
     "SELECT * FROM meetings WHERE group_id IN
       (SELECT group_id FROM groups WHERE
         mentor_grade_req <=(SELECT grade FROM students WHERE student_id=$sid))
       AND meet_id IN(SELECT meet_id FROM enroll2 WHERE mentor_id=$sid)
       AND date>='$today' ORDER BY date ASC";
   $result = $mysqli->query($meetings_query);

   $response = array();
   $i = 0;

   while($row = mysqli_fetch_array($result)) {
     $mid = $row['meet_id'];
     $response[strval($i) . "mid"] = $mid;
     $response[strval($i) . "mname"] = $row['meet_name'];
     $response[strval($i) . "mdate"] = $row['date'];
     $tid = $row['time_slot_id'];
     $time_query = "SELECT * FROM time_slot WHERE time_slot_id ='$tid'";
     $tresult = $mysqli->query($time_query);
     $trow = mysqli_fetch_array($tresult);
     $response[strval($i) . "mday"] = $trow['day_of_the_week'];
     $response[strval($i) . "mstart"] = $trow['start_time'];
     $response[strval($i) . "mend"] = $trow['end_time'];
     $j = 0;
     $mentors_query = "SELECT * FROM users WHERE id IN(SELECT mentor_id FROM enroll2 WHERE meet_id=$mid)";
     $mentors_result = $mysqli->query($mentors_query);
     while($mentors_row = mysqli_fetch_array($mentors_result)) {
       $response[strval($i) . "x" . strval($j) . "email"] = $mentors_row['email'];
       $response[strval($i) . "x" . strval($j) . "name"] = $mentors_row['name'];
       $j = $j + 1;
     }
     $j = 0;
     $mentees_query = "SELECT * FROM users WHERE id IN(SELECT mentee_id FROM enroll WHERE meet_id=$mid)";
     $mentees_result = $mysqli->query($mentees_query);
     while($mentees_row = mysqli_fetch_array($mentees_result)) {
       $response[strval($i) . "z" . strval($j) . "email"] = $mentees_row['email'];
       $response[strval($i) . "z" . strval($j) . "name"] = $mentees_row['name'];
       $j = $j + 1;
     }
     $i = $i + 1;
   }

   echo json_encode($response);
?>
