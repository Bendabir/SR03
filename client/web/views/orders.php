<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop">
		<div class="mdl-grid">
	
		</div>
	</div>
</div>

<!-- Card template for orders -->
<template id="order-card-template">
	<div class="mdl-cell mdl-cell--12-col">
		<div class="wide-card dark-card mdl-card mdl-shadow--2dp">
			<div class="mdl-card__title">
				<h2 class="mdl-card__title-text order-card-title">
					Commande n°NUM_COMMANDE
					Effectuée le DATE
				</h2>
			</div>
			<div class="mdl-card__menu">
				<p class="order-card-amount">
					Montant total: <span class="order-card-order-amount">MONTANT</span>€
				</p>
			</div>
			<div class="mdl-card__actions mdl-card--border">
				<ul class="demo-list-three mdl-list order-card-order-lines">
					<!-- LINES -->
					<hr class="order-lines-list-seperator" />
					<li class="mdl-list__item mdl-list__item--three-line">
						<span class="mdl-list__item-primary-content"></span>
						<span class="mdl-list__item-secondary-content">
							<p>
								<b><span class="order-card-order-amount">MONTANT</span>€</b>
							</p>
						</span>
					</li>											
				</ul>
			</div>
		</div>
	</div>
</template>

<template id="order-line-template">
	 <li class="mdl-list__item mdl-list__item--three-line">
	 	<span class="mdl-list__item-primary-content">
	 		<img src="./img/just_cause_3_pc.jpg" class="mdl-list__item-avatar" />
	 		<span class="order-line-title">
	 			TITLE (CONSOLE)
	 		</span>
	 		<span class="mdl-list__item-text-body order-line-game-description">
	 			DESCRIPTION
	 		</span>
	 	</span>
	 	<span class="mdl-list__item-secondary-content">
	 		<p>
	 			<b><span class="order-line-game-price">PRICE</span>€</b>
	 			<br />
	 			x<b><span class="order-line-game-quantity">QUANTITY</span></b>
	 		</p>
	 	</span>
	 </li>
</template>