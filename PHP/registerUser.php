<?php
ini_set('registerUser', '1');
error_reporting(E_ALL);

    //Connect to an ssh
    $dsn = 'mysql:host=cssgate.insttech.washington.edu;dbname=if30';
    $username = 'if30';
    $password = 'crarsh*';
    $FName = $_GET[my_firstname];
    $LName = $_GET[my_lastname];
    $uEmail = $_GET[my_email];
    $uPass = $_GET[my_password];
    try{
        $db = new PDO($dsn, $username, $password);
    //Getting LastID inserted
        $getNewID = "select count(*) from UserInfo";
        $getID_query = $db->query($getNewID);
        $getIDResult = $getID_query->fetchAll(PDO::FETCH_ASSOC);
        $lastID = ($getIDResult[0]['count(*)']) + 1;
    //End of getting LastID inserted
        $insertUserInfo = "insert into UserInfo values ($lastID, '$FName','$LName')";
        /**
         * 0 = not confirmed
         * 1 = confirmed
         */
        $insertLoginData = "insert into LoginData (Email, Pwd, UserID, Confirmed) values ('$uEmail', '$uPass', $lastID,0)";
    
        //echo json_encode($insertUserInfo);
        //echo json_encode($insertLoginData);
        $userInfo_query = $db->query($insertUserInfo);
        $loginData_query = $db->query($insertLoginData);
        //echo json_encode($userInfo_query);
        //echo json_encode($loginData_query);
        if($userInfo_query && $loginData_query){
            print("Register successful.");
        } else {
            if(!$userInfo_query){
                print("Registration failed. UserInfoFailed");
                $deleteLoginData = "delete from LoginData where UserID = $lastID";
                $deleteLoginData_query = $db->query($deleteLoginData);
            } 
            if(!$loginData_query){
                print("Registration failed. LoginDataFailed");
                $deleteUserInfo = "delete from UserInfo where UserID = $lastID";
                $deleteUserInfo_query = $db->query($deleteUserInfo);
            }
        }
    } catch (PDOException $e){
        $error_message = $e->getMessage();
        $result = array("code"=>300, "message"=>"There was an error connecting to
        the database: $error_message");
        echo json_encode($result);
        exit();
    }
?>