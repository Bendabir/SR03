<!-- The main "frame" -->
<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--10-col mdl-cell--12-col-phone mdl-cell--1-offset-desktop">
	
	</div>
</div>

<!-- Button that will show the dialog -->
<button class="mdl-button mdl-js-button mdl-button--fab mdl-button--colored bottom-right-button" id="add-game">
	<i class="material-icons">add</i>
</button>

<!-- Button that will delete (a) game(s) -->
<button class="mdl-button mdl-js-button mdl-button--fab bottom-left-button" id="delete-game">
	<i class="material-icons">delete</i>
</button>

<!-- The dialog to add a game -->
<dialog class="mdl-dialog add-dialog" id="add-dialog">
	<h4 class="mdl-dialog__title">Ajouter un jeu</h4>
	<div class="mdl-dialog__content">
		<div class="mdl-grid">
			<div class="mdl-cell mdl-cell--12-col">
				Tous les champs sont obligatoires à l'exception de l'éditeur, de la description, du lien de la jaquette ainsi que des genres.<br />
				Les genres doivent être séparés par des <b>|</b> (<i>pipe</i>).<br />
				La date de sortie est au format <b>JJ/MM/AAAA</b>.
			</div>
			<div class="mdl-cell mdl-cell--4-col mdl-cell--12-col-phone mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<input class="mdl-textfield__input" type="text" id="dialog-game-title" maxlength="255">
				<label class="mdl-textfield__label" for="dialog-game-title">Titre <span class="mdl-color-text--pink-A200">*</span></label>
			</div>
			<div class="mdl-cell mdl-cell--4-col mdl-cell--12-col-phone mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<input class="mdl-textfield__input" type="text" id="dialog-game-console" maxlength="32">
				<label class="mdl-textfield__label" for="dialog-game-console">Console <span class="mdl-color-text--pink-A200">*</span></label>
			</div>
			<div class="mdl-cell mdl-cell--4-col mdl-cell--12-col-phone mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<input class="mdl-textfield__input" type="text" pattern="(0[1-9]|[1-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/[12][0-9]{3}" id="dialog-game-release-date">
				<label class="mdl-textfield__label" for="dialog-game-release-date">Date de sortie <span class="mdl-color-text--pink-A200">*</span></label>
			</div>
			<div class="mdl-cell mdl-cell--4-col mdl-cell--12-col-phone mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<input class="mdl-textfield__input" type="number" min="0" step="0.01" id="dialog-game-price">
				<label class="mdl-textfield__label" for="dialog-game-price">Prix <span class="mdl-color-text--pink-A200">*</span></label>
			</div>
			<div class="mdl-cell mdl-cell--4-col mdl-cell--12-col-phone mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<input class="mdl-textfield__input" type="text" id="dialog-game-publisher" maxlength="64">
				<label class="mdl-textfield__label" for="dialog-game-publisher">Editeur</label>
			</div>
			<div class="mdl-cell mdl-cell--4-col mdl-cell--12-col-phone mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<input class="mdl-textfield__input" type="number" min="0" id="dialog-game-stock">
				<label class="mdl-textfield__label" for="dialog-game-stock">Stock <span class="mdl-color-text--pink-A200">*</span></label>
			</div>
			<div class="mdl-cell mdl-cell--12-col mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<input class="mdl-textfield__input" type="text" pattern="((\s)*[a-zA-Z0-9\s]+\|?)+" id="dialog-game-genres">
				<label class="mdl-textfield__label" for="dialog-game-genres">Genres</label>
			</div>
			<div class="mdl-cell mdl-cell--12-col mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<input class="mdl-textfield__input" type="url" id="dialog-game-cover" maxlength="4096">
				<label class="mdl-textfield__label" for="dialog-game-cover">URL de la jaquette</label>
			</div>
			<div class="mdl-cell mdl-cell--12-col mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<textarea class="mdl-textfield__input" type="text" rows="5" id="dialog-game-description" maxlength="4096"></textarea>
				<label class="mdl-textfield__label" for="dialog-game-description">Description</label>
			</div>			
		</div>		
	</div>
	<div class="mdl-dialog__actions">
		<button type="button" class="mdl-button add">Ajouter</button>
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
		<tbody id="games-list">

		</tbody>
	</table>		
</template>