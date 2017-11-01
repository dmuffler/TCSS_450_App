<?php
ini_set('checkUserAccount', '1');
error_reporting(E_ALL);

    //Connect to an ssh
    $dsn = 'mysql:host=cssgate.insttech.washington.edu;dbname=if30';
    $username = 'if30';
    $password = 'crarsh*';
    $uName = $_GET[my_username];
    $uPass = $_GET[my_password];

    try{
        $db = new PDO($dsn, $username, $password);
        $checkLogin = "select * from User where username='$uName' and pwd='$uPass'";
        //echo json_encode($checkLogin);
        $user_query = $db->query($checkLogin);
        //echo json_encode($user_query);
        $users = $user_query->fetchAll(PDO::FETCH_ASSOC);
        //echo json_encode($users);
        if($users){
            print("Login Successful ");
        } else {
            print("Login Failed: User not found or Wrong password");
        }
    } catch (PDOException $e){
        $error_message = $e->getMessage();
        $result = array("code"=>300, "message"=>"There was an error connecting to
        the database: $error_message");
        echo json_encode($result);
        exit();
    }

?>