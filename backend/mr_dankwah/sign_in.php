<?php

include 'connection.php';


if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    

    //validating our fields
    if (!isset($_POST['username']) || !isset($_POST['password']) ) {

        $respose = array("status" => "error", "message" => "Username and Password are required to LogIn!");
        echo json_encode($respose);
        exit;
    } 

    // assigning our fields to a variable
    $username = $_POST['username'];
    $password = $_POST['password'];

    
    $stmt = $conn->prepare("SELECT id, password FROM users WHERE username = ?");
    $stmt->bind_param("s", $username);
    $stmt->execute();


    $results = $stmt->get_result();

    if ($results->num_rows > 0){
        $row = $results->fetch_assoc();
        $hashed_password = $row['password'];
        $userId = $row['id'];


        if(password_verify($password, $hashed_password)) {

            $update = $conn->prepare("UPDATE users SET last_login = NOW() WHERE id =?");
            $update->bind_param("i", $userId);
            $update->execute();
            $update->close();


            $respose = array("status" => "success", "message" => "Welcome back $username");
            echo json_encode($respose);
        }else{
            $respose = array("status" => "error", "message" => "Wrong password");
            echo json_encode($respose);
        } 

    }else{
        $respose = array("status" => "error", "message" => "username does not exist");
        echo json_encode($respose);
    }

    $stmt->close();
}else{
    $respose = array("status" => "error", "message" => "Invalid request!");
    echo json_encode($respose);
}