<?php
ini_set('registerUser', '1');
error_reporting(E_ALL);

    //Connect to an ssh
    $dsn = 'mysql:host=cssgate.insttech.washington.edu;dbname=if30';
    $username = 'if30';
    $password = 'crarsh*';
    $uName = $_GET[my_username];
    $uPass = $_GET[my_password];
?>