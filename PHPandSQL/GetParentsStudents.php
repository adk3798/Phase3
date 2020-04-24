<?php
   $mysqli = new mysqli('localhost', 'root', '', 'DB2'); //The Blank string is the password

   $pid = (int) $_POST['pid'];
   $query = "SELECT * FROM users WHERE id IN(SELECT student_id FROM students WHERE parent_id=$pid)";
   $result = $mysqli->query($query);

   $response = array();
   $i = 0;

   while($row = mysqli_fetch_array($result)) {
     $response[strval($i) . "sid"] = $row['id'];
     $response[strval($i) . "name"] = $row['name'];
     $i = $i + 1;
   }

   echo json_encode($response);
?>
