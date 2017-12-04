<?php
ini_set('registerUser', '1');
error_reporting(E_ALL);
$to = $_GET[my_email];

    //create the new code
    function uniqueCode( $length = 6 ) {
        $chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        $code = substr( str_shuffle( $chars ), 0, $length );
        return $code;
    }
    //sending the code out
    $code = uniqueCode();
    $subject = "Confirmation Code for Smart Convert";
    $msg = "Thank you for registering with Smart Convert, your code is $code";
    $headers = "From: smartconvert@gmail.com" . "\r\n";

    mail($to,$subject,$msg,$headers);
    print("sent");

    //update database
    $dsn = 'mysql:host=cssgate.insttech.washington.edu;dbname=if30';
    $username = 'if30';
    $password = 'crarsh*';
    try{
        $db = new PDO($dsn, $username, $password);
        $update_user = "update LoginData set ConfirmCode = '$code' where Email = '$to'";
        //echo json_encode($update_user);
        $update_query = $db->query($update_user);
        //echo json_encode($update_query);
    } catch (PDOException $e){
        $error_message = $e->getMessage();
        $result = array("code"=>300, "message"=>"There was an error connecting to
        the database: $error_message");
        echo json_encode($result);
        exit();
    }
?>