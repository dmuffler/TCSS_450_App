<?php
ini_set('edit_profile', '1');
error_reporting(E_ALL);

    //Connect to an ssh
    $dsn = 'mysql:host=localhost;dbname=if30';
    $username = 'if30';
    $password = 'crarsh*';
    $email = $_GET[my_email];
    $fName = $_GET[firstname];
    $lName = $_GET[lastname];

    try{
        $db = new PDO($dsn, $username, $password);
        $getID = "select UserID from LoginData where Email = '$email'";
        $id_query = $db->query($getID);
        $users = $id_query->fetchAll(PDO::FETCH_ASSOC);
        //echo json_encode($users);
        $id = $users[0]['UserID'];
        //echo json_encode($users[0]['UserID']);

        $updateName = "update UserInfo set FName = '$fName', LName = '$lName' where UserID = '$id'";
        echo json_encode($updateName);
        $user_query = $db->query($updateName);
    } catch (PDOException $e){
        $error_message = $e->getMessage();
        $result = array("code"=>300, "message"=>"There was an error connecting to
        the database: $error_message");
        echo json_encode($result);
        exit();
    }

?>