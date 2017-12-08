<?php
ini_set('profile', '1');
error_reporting(E_ALL);

    //Connect to an ssh
    $dsn = 'mysql:host=localhost;dbname=if30';
    $username = 'if30';
    $password = 'crarsh*';
    $email = $_GET[my_email];

    try{
        $db = new PDO($dsn, $username, $password);
        $getID = "select UserID from LoginData where Email = '$email'";
        $id_query = $db->query($getID);
        $users = $id_query->fetchAll(PDO::FETCH_ASSOC);
        //echo json_encode($users);
        $id = $users[0]['UserID'];
        //echo json_encode($users[0]['UserID']);

        $getName = "select FName, LName from UserInfo where UserID ='$id'";
        //echo json_encode($getName);
        $user_query = $db->query($getName);
        $users = $user_query->fetchAll(PDO::FETCH_ASSOC);
        //echo json_encode($users);
        if($users){
            echo json_encode(array('first' => $users[0]['FName'], 'last' => $users[0]['LName']));
        }
    } catch (PDOException $e){
        $error_message = $e->getMessage();
        $result = array("code"=>300, "message"=>"There was an error connecting to
        the database: $error_message");
        echo json_encode($result);
        exit();
    }

?>