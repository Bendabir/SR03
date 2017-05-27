<?php 
	// require_once(dirname(__FILE__)."/../utils/config.php");
	// require_once(dirname(__FILE__)."/../libraries/Requests.php");

	// Requests::register_autoloader();

	// $gamesRequest = Requests::get($apiEndPoint."/games");
	// $games = $gamesRequest->body;

	// Building interface from games
	// $games = json_decode($games, true);
?>

<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--2-offset-desktop mdl-cell--8-col mdl-cell--12-col-phone">
		<div class="mdl-grid">
			
		</div>
	</div>
</div>

<template id="cart-card-template">
	<div class="mdl-cell mdl-cell--12-col">
		<div class="wide-card dark-card mdl-card mdl-shadow--2dp">
			<div class="mdl-card__title">
				<h2 class="mdl-card__title-text">Ma commande</h2>
			</div>
			<div class="mdl-card__supporting-text">
				<ul class="mdl-list cart-card-cart-lines">
					<!-- LINES -->
					<hr />
					<li class="mdl-list__item mdl-list__item--three-line">
						<span class="mdl-list__item-primary-content"></span>
						<span class="mdl-list__item-secondary-content">
							<p>
							<b class="cart-card-order-amount">AMOUNT</b>
							</p>
						</span>
					</li>									
				</ul>
			</div>
			<div class="mdl-card__actions mdl-card--border">
				<a class="mdl-button mdl-js-button mdl-js-ripple-effect cart-card-validate-cart">
					Valider la commande
				</a>
				<a class="mdl-button mdl-js-button mdl-js-ripple-effect cart-card-clear-cart">
					Vider le panier
				</a>
			</div>
		<!-- 
			<div class="mdl-card__menu">
				<button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect">
					<i class="material-icons">close</i>
				</button>
			</div>
		-->
		</div>
	</div>
</template>

<template id="cart-line-template">
	<li class="mdl-list__item mdl-list__item--three-line game-line" game-id="-1">
		<span class="mdl-list__item-primary-content">
			<img src="./img/no_cover.png" class="mdl-list__item-avatar cart-line-game-cover" />
			<span class="cart-line-title">NAME (CONSOLE)</span>
			<span class="mdl-list__item-text-body cart-line-game-description">
				<b class="cart-line-game-price">PRICE</b>
				<!-- <br /> -->
				x<b class="cart-line-game-quantity">QUANTITY</b>
				<!-- DESCRIPTION -->
			</span>
		</span>
		<span class="mdl-list__item-secondary-content">
			<a class="mdl-list__item-secondary-action cart-line-game-modify-quantity" href="#" modifier-type="decrease"><i class="material-icons">remove</i></a>
			<a class="mdl-list__item-secondary-action cart-line-game-modify-quantity" href="#" modifier-type="increase"><i class="material-icons">add</i></a>
		</span>
	</li>	
</template>

