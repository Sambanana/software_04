<?php

$serverName = "localhost:8889";
$userName = "root";
$password = "root";
$dbName = "mr_dankwah";


$conn = new mysqli($serverName, $userName, $password, $dbName);


if ($conn->connect_error) {

    $response = array("status" => "error", "message" => "Connection failed");
    echo json_encode($response);

    die("connection faild " .$conn->connect_error);
    
} 