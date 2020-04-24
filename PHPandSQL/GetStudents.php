<?php
   $mysqli = new mysqli('localhost', 'root', '', 'DB2'); //The Blank string is the password

   $query = "SELECT * FROM users WHERE id IN(SELECT student_id FROM students) ORDER BY name ASC";
   $result = $mysqli->query($query);

   $response = array();
   $i = 0;

   while($row = mysqli_fetch_array($result)) {
     $sid = $row['id'];
     $query2 = "SELECT * FROM students WHERE student_id=$sid";
     $result2 = $mysqli->query($query2);
     $row2 = mysqli_fetch_array($result2);
     $response[strval($i) . "grade"] = $row2["grade"];
     $response[strval($i) . "sid"] = $sid;
     $response[strval($i) . "name"] = $row['name'];

     $i = $i + 1;
   }

   echo json_encode($response);
?>
