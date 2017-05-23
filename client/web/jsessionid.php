<?php 
	session_start();

	$json = Array(
		"id" => $_SESSION["JSESSIONID"]
	);

	echo json_encode($json);
?>