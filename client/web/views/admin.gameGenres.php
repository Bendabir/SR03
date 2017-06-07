<!-- Main frame -->
<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop">

	</div>
</div>

<!-- Button for the add dialog -->
<button class="mdl-button mdl-js-button mdl-button--fab mdl-button--colored bottom-right-button" id="add-game-genre">
	<i class="material-icons">add</i>
</button>

<!-- The dialog to add a game genre -->
<dialog class="mdl-dialog add-dialog" id="add-game-genre-dialog">
	<h4 class="mdl-dialog__title">Ajouter un genre</h4>
	<div class="mdl-dialog__content">
		<div class="mdl-grid">
			<div class="mdl-cell mdl-cell--12-col">
				Seul le nom du genre est obligatoire.<br />
				<b>ATTENTION : La suppression d'un genre n'est pas autorisée par cette interface.</b>
			</div>
			<div class="mdl-cell mdl-cell--12-col mdl-cell--12-col-phone mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<input class="mdl-textfield__input" type="text" id="dialog-game-genre-name" maxlength="64">
				<label class="mdl-textfield__label" for="dialog-game-genre-name">Nom du genre <span class="mdl-color-text--pink-A200">*</span></label>
			</div>
			<div class="mdl-cell mdl-cell--12-col mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<textarea class="mdl-textfield__input" type="text" rows="5" id="dialog-game-genre-description" maxlength="4096"></textarea>
				<label class="mdl-textfield__label" for="dialog-game-genre-description">Description</label>
			</div>
		</div>		
	</div>
	<div class="mdl-dialog__actions">
		<button type="button" class="mdl-button add">Ajouter</button>
		<button type="button" class="mdl-button close">Fermer</button>
	</div>
</dialog>

<!-- Our templates for dynamic filling -->
<template id="admin-game-genres-line">
	<tr>
		<td input-type="text" class="mdl-data-table__cell--non-numeric admin-game-genres-line-genre-name">NAME</td>
		<td input-type="textarea" class="mdl-data-table__cell--non-numeric admin-game-genres-line-genre-description text-truncation" style="text-align: justify;">DESCRIPTION</td>
	</tr>
</template>

<template id="admin-game-genres-table">
	<table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp" style="width: 100%;">
		<thead>
			<tr>
				<th class="mdl-data-table__cell--non-numeric">Genre</th>
				<th class="mdl-data-table__cell--non-numeric">Description</th>
			</tr>
		</thead>
		<tbody id="game-genres-list">

		</tbody>
	</table>		
</template>