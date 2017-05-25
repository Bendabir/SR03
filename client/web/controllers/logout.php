<?php 
	session_start();

	$_SESSION = Array();

	if (ini_get("session.use_cookies")) {
	    $params = session_get_cookie_params();
	    setcookie(session_name(), '', time() - 42000, $params["path"], $params["domain"], $params["secure"], $params["httponly"]);
	}

	// Need to add the logout from the server as well

	session_destroy();
?>