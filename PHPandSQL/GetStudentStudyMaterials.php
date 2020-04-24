<?php
   $mysqli = new mysqli('localhost', 'root', '', 'DB2'); //The Blank string is the password

   $sid = (int) $_POST['sid'];
   //$sid = 29;
   $today = date("Y-m-d");

   $meetings_query =
     "SELECT * FROM meetings WHERE group_id IN
       (SELECT group_id FROM groups WHERE
         description =(SELECT grade FROM students WHERE student_id=$sid))
       ORDER BY date ASC";
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
     $sm_query = "SELECT * FROM material WHERE material_id IN(SELECT material_id FROM assign WHERE meet_id=$mid)";
     $sm_result = $mysqli->query($sm_query);
     while($sm_row = mysqli_fetch_array($sm_result)) {
       $response[strval($i) . "x" . strval($j) . "title"] = $sm_row['title'];
       $response[strval($i) . "x" . strval($j) . "author"] = $sm_row['author'];
       $response[strval($i) . "x" . strval($j) . "type"] = $sm_row['type'];
       $response[strval($i) . "x" . strval($j) . "url"] = $sm_row['url'];
       $response[strval($i) . "x" . strval($j) . "assigned_date"] = $sm_row['assigned_date'];
       $response[strval($i) . "x" . strval($j) . "notes"] = $sm_row['notes'];
       $j = $j + 1;
     }
     $i = $i + 1;
   }

   echo json_encode($response);
?>
