<?php 
	require_once(dirname(__FILE__)."/../controllers/login.php");

	// If no rights, go back to root
	if($_SESSION["status"] != "admin"){
		header("Location: ../");
	}
?>

<!DOCTYPE html>
<html>
	<head>
		<title>Video Games Store - Administration</title>
		<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.blue-pink.min.css" />
		<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
		<link rel="stylesheet" href="../css/gamestore.css">
		<link rel="icon" type="image/png" href="../img/favicon.png" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta charset="utf-8">
	</head>
	<body>
		<!-- Always shows a header, even in smaller screens. -->
		<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header mdl-layout--fixed-tabs">

			<?php require_once('../header.php'); ?>

			<main class="mdl-layout__content">
				<section class="mdl-layout__tab-panel is-active" id="fixed-tab-1">
					<div class="page-content">
						<?php require_once('../views/admin.games.php'); ?>
					</div>
				</section>
				<section class="mdl-layout__tab-panel" id="fixed-tab-2">
					<div class="page-content">
						<?php require_once('../views/admin.consoles.php'); ?>
					</div>
				</section>
				<section class="mdl-layout__tab-panel" id="fixed-tab-3">
					<div class="page-content">
						<?php require_once('../views/admin.gameGenres.php'); ?>
					</div>
				</section>
				<section class="mdl-layout__tab-panel" id="fixed-tab-4">
					<div class="page-content">
					</div>
				</section>
				<section class="mdl-layout__tab-panel" id="fixed-tab-5">
					<div class="page-content">
						<?php require_once('../views/admin.users.php'); ?>
					</div>
				</section>								
			</main>
		</div>

		<script src="../js/material.min.js"></script>
		<script src="../js/workshop.js"></script>
		<script src="../js/gamestore.js"></script>
	</body>
</html>