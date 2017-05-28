<?php 
	session_start();

	require_once(dirname(__FILE__)."/../utils/config.php");
	require_once(dirname(__FILE__)."/../libraries/Requests.php");

	Requests::register_autoloader();	

	// If no session id, go to home ?
	$alreadyLoggedOut = !isset($_SESSION["JSESSIONID"]);

	$logoutError = null;
	$statusCode = -1;

	if($alreadyLoggedOut)
		$logoutError = "Vous êtes déjà déconnecté. <br />";
	else {
		// Need to add the logout from the server as well
		$headers = Array(
			"Cookie" => "JSESSIONID=".$_SESSION["JSESSIONID"] // Setting cookie 
		);
		$logoutRequest = Requests::get($logoutEndPoint, $headers);
		$information = $logoutRequest->body;
		$information = json_decode($information, true);	
		$statusCode = $logoutRequest->status_code;
		
		if($statusCode != 200)
			$logoutError = $information["message"];
	}

	// Cleaning PHP session
	$_SESSION = Array();

	if (ini_get("session.use_cookies")) {
	    $params = session_get_cookie_params();
	    setcookie(session_name(), '', time() - 42000, $params["path"], $params["domain"], $params["secure"], $params["httponly"]);
	}

	session_destroy();
?>

<!DOCTYPE html>
<html>
	<head>
		<title>Video Games Store - Déconnexion</title>
		<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.blue-pink.min.css" />
		<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
		<link rel="stylesheet" href="../css/gamestore.css">
		<link rel="icon" type="image/png" href="../img/favicon.png" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta charset="utf-8">
	</head>
	<body>
		<!-- Always shows a header, even in smaller screens. -->
		<div class="mdl-layout mdl-js-layout">
			<main class="mdl-layout__content">
				<div class="page-content">
					<div class="mdl-grid">
						<div class="mdl-cell mdl-cell--8-col mdl-cell--2-offset-desktop mdl-cell--12-col-phone">
							<?php  
								if(($statusCode == 200) && !$alreadyLoggedOut){
							?>
								<div class="wide-card dark-card mdl-card mdl-shadow--2dp">
									<div class="mdl-card__title">
										<h2 class="mdl-card__title-text info-title">Déconnexion réussie</h2>
									</div>
									<div class="mdl-card__supporting-text info-text">
										Vous êtes à présent déconnecté.
										<br />
										<br />
										<br />
									</div>
									<div class="mdl-card__actions mdl-card--border">
										<a class="mdl-button mdl-js-button mdl-js-ripple-effect info-action" href="../">
											Retourner à l'accueil
										</a>
									</div>
								</div>								
							<?php
								}
								else {
							?>
								<div class="wide-card error-card mdl-card mdl-shadow--2dp">
									<div class="mdl-card__title">
										<h2 class="mdl-card__title-text">Ooops</h2>
									</div>
									<div class="mdl-card__supporting-text">
										Une erreur s'est produite lors de la déconnexion : <b><?php echo $logoutError; ?></b><br />
										Veuillez réessayer.
										<br />
										<br />
									</div>
									<div class="mdl-card__actions mdl-card--border">
										<a class="mdl-button mdl-js-button mdl-js-ripple-effect info-action" href="../">
											Retourner à l'accueil
										</a>										
										<a class="mdl-button mdl-js-button mdl-js-ripple-effect error-action" href="./logout.php">
											Se déconnecter
										</a>
									</div>
								</div>
							<?php		
								}
							?>	
						</div>
					</div>
				</div>
			</main>
		</div>

		<script src="../js/material.min.js"></script>		
	</body>
</html>