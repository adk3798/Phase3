<?php
   $mysqli = new mysqli('localhost', 'root', '', 'DB2'); //The Blank string is the password

   $sid = (int) $_POST['sid'];
   //$sid = 21;
   $today = date("Y-m-d");

   $mentor_query =
     "SELECT * FROM meetings WHERE group_id IN
       (SELECT group_id FROM groups WHERE
         mentor_grade_req <=(SELECT grade FROM students WHERE student_id=$sid))
       AND meet_id IN(SELECT meet_id FROM enroll2 WHERE mentor_id=$sid)
       AND date>='$today' ORDER BY date ASC";
   $result = $mysqli->query($mentor_query);

   $response = array();
   $i = 0;

   while($row = mysqli_fetch_array($result)) {
     $response[strval($i) . "mid"] = $row['meet_id'];
     $response[strval($i) . "mname"] = $row['meet_name'];
     $response[strval($i) . "mdate"] = $row['date'];
     $tid = $row['time_slot_id'];
     $time_query = "SELECT * FROM time_slot WHERE time_slot_id ='$tid'";
     $tresult = $mysqli->query($time_query);
     $trow = mysqli_fetch_array($tresult);
     $response[strval($i) . "mday"] = $trow['day_of_the_week'];
     $response[strval($i) . "mstart"] = $trow['start_time'];
     $response[strval($i) . "mend"] = $trow['end_time'];
     $i = $i + 1;
   }

   echo json_encode($response);
?>
