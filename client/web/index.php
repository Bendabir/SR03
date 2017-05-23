<?php 
	require_once(dirname(__FILE__).'/controllers/login.php');
?>

<!DOCTYPE html>
<html>
	<head>
		<title>Video Games Store</title>
		<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.blue-pink.min.css" />
		<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
		<link rel="stylesheet" href="./css/gamestore.css">
		<link rel="icon" type="image/png" href="./img/favicon.png" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta charset="utf-8">
	</head>
	<body>
		<!-- Always shows a header, even in smaller screens. -->
		<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header mdl-layout--fixed-tabs">

			<?php require_once('header.php'); ?>

			<main class="mdl-layout__content">
				<section class="mdl-layout__tab-panel is-active" id="games">
					<div class="page-content">
						<?php require_once('./views/games.php'); ?>
					</div>
				</section>
				<section class="mdl-layout__tab-panel" id="cart">
					<div class="page-content">
						<?php require_once('./views/cart.php'); ?>
					</div>
				</section>
				<section class="mdl-layout__tab-panel" id="orders">
					<div class="page-content">
						<?php require_once('./views/orders.php'); ?>
					</div>
				</section>
			</main>
		</div>

		<script src="./js/material.min.js"></script>
		<script src="./js/workshop.js"></script>
		<script src="./js/main.js"></script>
		<script src="./js/main.games.js"></script>
		<script src="./js/main.orders.js"></script>
		<script type="text/javascript">
			document.addEventListener('DOMContentLoaded', function(e){
				main.init();
			}, false);
		</script>

		<!-- HTML template for error cards -->
		<template id="error-card-template">
			<div class="wide-card error-card mdl-card mdl-shadow--2dp">
				<div class="mdl-card__title">
					<h2 class="mdl-card__title-text">Ooops</h2>
				</div>
				<div class="mdl-card__supporting-text">
					Une erreur s'est produite : <b class="error-message">ERROR</b><br />
					<p class="error-more-information">MORE_INFORMATION</p>
					<br />
					<br />
				</div>
				<div class="mdl-card__actions mdl-card--border">
					<a class="mdl-button mdl-js-button mdl-js-ripple-effect error-action">
						ACTION
					</a>
				</div>
				<div class="mdl-card__menu">
					<button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect">
						<i class="material-icons">loop</i>
					</button>
				</div>
			</div>		
		</template>

		<!-- HTML template for info cards -->
		<template id="info-card-template">
			<div class="wide-card dark-card mdl-card mdl-shadow--2dp">
				<div class="mdl-card__title">
					<h2 class="mdl-card__title-text info-title">TITLE</h2>
				</div>
				<div class="mdl-card__supporting-text info-text">
					<!-- INFO -->
					<br />
					<br />
				</div>
				<div class="mdl-card__actions mdl-card--border">
					<a class="mdl-button mdl-js-button mdl-js-ripple-effect info-action" href="#action">
						ACTION
					</a>
				</div>
				<div class="mdl-card__menu">
					<button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect info-icon">
						<i class="material-icons">loop</i>
					</button>
				</div>
			</div>		
		</template>
	</body>
</html>