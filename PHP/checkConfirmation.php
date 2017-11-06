<?php
ini_set('registerUser', '1');
error_reporting(E_ALL);
$email = $_GET[my_email];
$code = $_GET[my_code];

    $dsn = 'mysql:host=cssgate.insttech.washington.edu;dbname=if30';
    $username = 'if30';
    $password = 'crarsh*';
    try{
        $db = new PDO($dsn, $username, $password);
        $check_code = "select ConfirmCode from LoginData where Email = '$email'";
        //echo json_encode($check_code);
        $check_query = $db->query($check_code);
        //echo json_encode($check_query);
        $users = $check_code->fetchAll(PDO::FETCH_ASSOC);
        if($users){
            
        }
    } catch (PDOException $e){
        $error_message = $e->getMessage();
        $result = array("code"=>300, "message"=>"There was an error connecting to
        the database: $error_message");
        echo json_encode($result);
        exit();
    }
?>