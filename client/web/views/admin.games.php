<!-- The main "frame" -->
<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--10-col mdl-cell--12-col-phone mdl-cell--1-offset-desktop">
	
	</div>
</div>

<!-- Button that will show the dialog -->
<button class="mdl-button mdl-js-button mdl-button--fab mdl-button--colored add-button" id="add-game">
	<i class="material-icons">add</i>
</button>

<!-- The dialog to add a game -->
<dialog class="mdl-dialog" id="add-dialog">
	<h4 class="mdl-dialog__title">Ajouter un jeu</h4>
	<div class="mdl-dialog__content">
		Formulaire pour ajouter un jeu
	</div>
	<div class="mdl-dialog__actions">
		<button type="button" class="mdl-button">Ajouter</button>
		<button type="button" class="mdl-button close">Fermer</button>
	</div>
</dialog>

<!-- Our templates for dynamic filling -->
<template id="admin-games-line">
	<tr game-id="-1">
		<td input-type="text" class="mdl-data-table__cell--non-numeric admin-games-line-game-title">TITLE</td>
		<td input-type="text" class="mdl-data-table__cell--non-numeric admin-games-line-game-console">CONSOLE</td>
		<td input-type="date" class="mdl-data-table__cell--non-numeric admin-games-line-game-release-date">RELEASE_DATE</td>
		<td input-type="price" class="admin-games-line-game-price">PRICE</td>
		<td input-type="text" class="mdl-data-table__cell--non-numeric admin-games-line-game-publisher">PUBLISHER</td>
		<td input-type="textarea" class="mdl-data-table__cell--non-numeric admin-games-line-game-description text-truncation" style="text-align: justify;">DESCRIPTION</td>
		<td input-type="link" class="mdl-data-table__cell--non-numeric admin-games-line-game-cover text-truncation">COVER</td>
		<td input-type="text" class="mdl-data-table__cell--non-numeric admin-games-line-game-genres">GENRES</td>
		<td input-type="number" class="admin-games-line-game-stock">STOCK</td>
	</tr>
</template>

<template id="admin-games-table">
	<table class="mdl-data-table mdl-js-data-table mdl-data-table--selectable mdl-shadow--2dp" style="width: 100%;">
		<thead>
			<tr>
				<th class="mdl-data-table__cell--non-numeric">Jeu</th>
				<th class="mdl-data-table__cell--non-numeric">Console</th>
				<th class="mdl-data-table__cell--non-numeric">Date de sortie</th>
				<th>Prix</th>
				<th class="mdl-data-table__cell--non-numeric">Editeur</th>
				<th class="mdl-data-table__cell--non-numeric">Description</th>
				<th class="mdl-data-table__cell--non-numeric">Jaquette</th>
				<th class="mdl-data-table__cell--non-numeric">Genres</th>
				<th>Stock</th>
			</tr>
		</thead>
		<tbody>

		</tbody>
	</table>		
</template>