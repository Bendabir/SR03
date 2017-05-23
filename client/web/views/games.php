<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop">
		<div class="mdl-grid">
			<!-- Game cards are going here -->
		</div>
	</div>
</div>

<!-- Card template for games -->
<template id="game-card-template">
	<div class="mdl-cell mdl-cell-4--col mdl-cell--12-col-phone">
		<div class="dark-card wide-card game-card mdl-card mdl-shadow--2dp">
			<div class="mdl-card__title mdl-card--expand">
				<h2 class="mdl-card__title-text">

				</h2>
			</div>
			<div class="mdl-card__supporting-text">
				<p class="game-card-description">

				</p>
				<p>
					<b>Editeur:</b><span class="game-card-publisher"></span><br />
					<b>Date de sortie:</b><span class="game-card-release-date"></span><br />
					<b>Genre:</b><span class="game-card-genre"></span><br />
					<b>Prix:</b><span class="game-card-price"></span>€
				</p>
			</div>
			<div class="mdl-card__actions mdl-card--border">
				<button class="mdl-button mdl-js-button mdl-js-ripple-effect game-card-add-to-cart-button" game-id="-1">

				</button>
			</div>
		</div>
	</div>	
</template>