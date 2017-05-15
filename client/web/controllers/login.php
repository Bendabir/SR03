<?php 
	session_start();

	require_once('./utils/config.php');
	require_once("./libraries/Requests.php");

	Requests::register_autoloader();


	// If we got a token, then we are back from a connection request
	if(isset($_GET["token"]) && !empty($_GET["token"])){
		// Storing user information

		$_SESSION["JSESSIONID"] = $_GET["token"];

		// Retrieving user information
		$headers = array(
			"Cookie" => "JSESSIONID=".$_SESSION["JSESSIONID"] // Setting cookie 
		);
		$userRequest = Requests::get($apiEndPoint."/users/me", $headers);
		$user = $userRequest->body;
		$user = json_decode($user, true);

		// Parsing and storing in session
		$_SESSION["username"] = $user["username"];
		$_SESSION["firstname"] = $user["firstName"];
		$_SESSION["lastname"] = $user["lastName"];
		$_SESSION["status"] = $user["status"];

		// Going to root
		header("Location: ./");
	}
	else {
		// Else check if connected
		// Checking if user is logged in
		if(!isset($_SESSION["JSESSIONID"]) || empty($_SESSION["JSESSIONID"])){
			// If not, asking for connection to the server (with a callback to get back here right after)
			header("Location: ".$loginEndPoint."?callback="."http://".$_SERVER["SERVER_NAME"].$_SERVER["REQUEST_URI"]);

			// Getting the result of the connection (user information)
		}
	}

	// Else, just displaying the webpage
?>