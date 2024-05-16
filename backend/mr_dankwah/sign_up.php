<?php

require 'connection.php';


if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    
    if (!isset($_POST['username']) || !isset($_POST['email']) || !isset($_POST['password']) ) {
        
        // echo("all fields are required!!");
        $respose = array("status" => "error", "message" => "Username, Password and Email are required to signUp!");
        echo json_encode($respose);
        exit;
    } 

    // getting various fields into variables
    $username = $_POST['username'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    //checking to see if account with email or password is already available
    $check_stmt = $conn->prepare("SELECT id FROM users where username = ? OR email = ?");
    $check_stmt->bind_param("ss", $username, $email);
    $check_stmt->execute(); 
    $check_results = $check_stmt->get_result();

    if ($check_results->num_rows > 0) {
        $respose = array("status" => "error", "message" => "Provided username or email is already taken");
        echo json_encode($respose);
    } else {
       //hashing password
        $hash_password = password_hash($password, PASSWORD_DEFAULT);

        $insert_stmt = $conn->prepare("INSERT INTO users (username, password, email, created_at) VALUES (?, ?, ?, NOW())");
        $insert_stmt->bind_param("sss", $username, $hash_password, $email);

        if($insert_stmt->execute()){
            $respose = array("status" => "success", "message" => "Registeration successful");
            echo json_encode($respose);
        }else{
            $respose = array("status" => "error", "message" => "Registeration failed");
            echo json_encode($respose);
        }

        $insert_stmt->close();
    }
    
    //close the sql statement
    $check_stmt->close();
} else {
    $respose = array("status" => "error", "message" => "Invalid request");
    echo json_encode($respose);
}
