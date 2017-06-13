<!-- Main frame -->
<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop">
		<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" style="width: 95%;">
			<input class="mdl-textfield__input" type="text" id="search-user-orders" maxlength="8" pattern="[a-z]*">
			<label class="mdl-textfield__label" for="search-user-orders">Rechercher les commandes d'un utilisateur (via son login)</label>
			<span class="mdl-textfield__error">Un login est composé de 8 lettre miniatures maximum</span>
		</div>
		<button class="clean-button" id="clean-search-button">
			<i class="material-icons">close</i>
		</button>
	</div>
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop" id="orders-container">

	</div>
</div>

<!-- Our templates for dynamic filling -->
<template id="admin-orders-line">
	<tr>
		<td class="mdl-data-table__cell--non-numeric admin-orders-line-order-login">LOGIN</td>
		<td class="admin-orders-line-order-num">NUM</td>
		<td class="mdl-data-table__cell--non-numeric admin-orders-line-order-date">DATE</td>
		<td class="mdl-data-table__cell--non-numeric admin-orders-line-order-lines text-full" style="text-align: justify;">
			<ul class="mdl-list table-order-lines">
					<!-- LINES -->											
			</ul>
		</td>
		<td class="admin-orders-line-order-amount">AMOUNT</td>
	</tr>
</template>

<template id="admin-orders-table">
	<table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp" style="width: 100%;">
		<thead>
			<tr>
				<th class="mdl-data-table__cell--non-numeric">Login</th>
				<th>Numéro</th>
				<th class="mdl-data-table__cell--non-numeric">Date de commande</th>
				<th class="mdl-data-table__cell--non-numeric">Jeux</th>
				<th>Montant total</th>
			</tr>
		</thead>
		<tbody id="admin-orders-list">

		</tbody>
	</table>		
</template>

<template id="order-line-template">
	 <li class="mdl-list__item mdl-list__item--three-line">
	 	<span class="mdl-list__item-primary-content">
	 		<img src="./img/no_cover.png" class="mdl-list__item-avatar order-line-game-cover" />
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