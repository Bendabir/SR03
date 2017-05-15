<?php 
	session_start();

	require_once('./utils/config.php');

	// Checking if user is logged in
	if(!isset($_SESSION["JSESSIONID"]) || empty($_SESSION["JSESSIONID"])){
		// If not, asking for connection to the server
		// header("Location: ".$loginEndPoint);

		echo 

		// Getting the result of the connection (user information)
	}

	// Else, just displaying the webpage
?>