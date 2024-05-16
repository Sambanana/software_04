<?PHP


require 'connection.php';


if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    
    if (!isset($_POST['task_name']) || !isset($_POST['task_status']) || !isset($_POST['user_id'])) {
        
        // echo("all fields are required!!");
        $respose = array("status" => "error", "message" => "All fields are required!");
        echo json_encode($respose);
        exit;
    } 

    // getting various fields into variables
    $taskName = $_POST['task_name'];
    $taskStatus = $_POST['task_status'];
    $userId = $_POST['user_id'];

    $insert_stmt = $conn->prepare("INSERT INTO task (task_name, task_date, task_status, user_id) VALUES (?, NOW(), ?, ?)");
    $insert_stmt->bind_param("sss", $taskName, $taskStatus, $userId);


    if($insert_stmt->execute()){
        $respose = array("status" => "success", "message" => "Task Added successful");
        echo json_encode($respose);
    }else{
        $respose = array("status" => "error", "message" => "Failed to add task");
        echo json_encode($respose);
    }

    $insert_stmt->close();

}