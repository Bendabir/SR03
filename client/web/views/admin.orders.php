<!-- Main frame -->
<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop">
		<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" style="width: 100%;">
			<input class="mdl-textfield__input" type="text" id="search-user-orders" maxlength="8" pattern="[a-z]*">
			<label class="mdl-textfield__label" for="search-user-orders">Rechercher les commandes d'un utilisateur (via son login)</label>
			<span class="mdl-textfield__error">Un login est composé de 8 lettre miniatures maximum</span>
		</div>
	</div>
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop">
		<table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp" style="width: 100%;">
			<thead>
				<tr>
					<th class="mdl-data-table__cell--non-numeric">Login</th>
					<th class="mdl-data-table__cell--non-numeric">Numéro</th>
					<th class="mdl-data-table__cell--non-numeric">Date de commande</th>
					<th class="mdl-data-table__cell--non-numeric">Montant total</th>
				</tr>
			</thead>
			<tbody id="game-users-list">

			</tbody>
		</table>	
	</div>
</div>

<!-- Our templates for dynamic filling -->
<template id="admin-orders-line">
	<tr>
		<td class="mdl-data-table__cell--non-numeric admin-users-line-user-login">LOGIN</td>
		<td class="mdl-data-table__cell--non-numeric admin-users-line-user-firstname">FIRSTNAME</td>
		<td class="mdl-data-table__cell--non-numeric admin-users-line-user-lastname">LASTNAME</td>
		<td class="mdl-data-table__cell--non-numeric admin-users-line-user-status">STATUS</td>
	</tr>
</template>

<template id="admin-orders-table">
	<table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp" style="width: 100%;">
		<thead>
			<tr>
				<th class="mdl-data-table__cell--non-numeric">Login</th>
				<th class="mdl-data-table__cell--non-numeric">Numéro</th>
				<th class="mdl-data-table__cell--non-numeric">Date de commande</th>
				<th class="mdl-data-table__cell--non-numeric">Montant total</th>
			</tr>
		</thead>
		<tbody id="admin-orders-list">

		</tbody>
	</table>		
</template>