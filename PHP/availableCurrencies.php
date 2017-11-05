<?php
ini_set('availableCurrencies', '1');
error_reporting(E_ALL);

    //Connect to an ssh
    $dsn = 'mysql:host=cssgate.insttech.washington.edu;dbname=if30';
    $username = 'if30';
    $password = 'crarsh*';

    try{
        $db = new PDO($dsn, $username, $password);
        $select_sql = "select * from Currencies";
        $query = $db->query($select_sql);
        $users = $query->fetchAll(PDO::FETCH_ASSOC);
        if( $users){
            $result = array("code"=>100, "size"=>count($users));
            $curr_array = array();
            #iterate through the results
            for($i = 0; $i < count($users); $i++){
                $currID = $users[$i]['CurrencyID'];
                $currDesc = $users[$i]['Description'];
                $curr_array[$i] = array($currID=>$currDesc);
            }
            $result["Currencies"] = $curr_array;
        } else {
            $result = array("code"=>200, "message"=>"ERROR: Database not working properly");
        }
        echo json_encode($result);
    } catch (PDOException $e){
        $error_message = $e->getMessage();
        $result = array("code"=>300, "message"=>"There was an error connecting to
        the database: $error_message");
        echo json_encode($result);
        exit();
    }
?>