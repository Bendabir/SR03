<!-- Main frame -->
<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop">
			
	</div>
</div>

<!-- Button for the add dialog -->
<button class="mdl-button mdl-js-button mdl-button--fab mdl-button--colored bottom-right-button" id="add-console">
	<i class="material-icons">add</i>
</button>

<!-- The dialog to add a console -->
<dialog class="mdl-dialog add-dialog" id="add-console-dialog">
	<h4 class="mdl-dialog__title">Ajouter une console</h4>
	<div class="mdl-dialog__content">
		<div class="mdl-grid">
			<div class="mdl-cell mdl-cell--12-col">
				Tous les champs sont obligatoires.<br />
				La date de lancement est au format <b>JJ/MM/AAAA</b>.<br />
				<b>ATTENTION : La suppression d'une console n'est pas autorisée par cette interface.</b>
			</div>
			<div class="mdl-cell mdl-cell--6-col mdl-cell--12-col-phone mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<input class="mdl-textfield__input" type="text" id="dialog-console-name" maxlength="32">
				<label class="mdl-textfield__label" for="dialog-console-name">Nom <span class="mdl-color-text--pink-A200">*</span></label>
			</div>
			<div class="mdl-cell mdl-cell--6-col mdl-cell--12-col-phone mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<input class="mdl-textfield__input" type="text" pattern="(0[1-9]|[1-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/[12][0-9]{3}" id="dialog-console-launch-date">
				<label class="mdl-textfield__label" for="dialog-console-launch-date">Date de lancement <span class="mdl-color-text--pink-A200">*</span></label>
			</div>
		</div>		
	</div>
	<div class="mdl-dialog__actions">
		<button type="button" class="mdl-button add">Ajouter</button>
		<button type="button" class="mdl-button close">Fermer</button>
	</div>
</dialog>

<!-- Our templates for dynamic filling -->
<template id="admin-consoles-line">
	<tr>
		<td input-type="text" class="mdl-data-table__cell--non-numeric admin-consoles-line-console-name">NAME</td>
		<td input-type="date" class="mdl-data-table__cell--non-numeric admin-consoles-line-console-launch-date">LAUNCH_DATE</td>
	</tr>
</template>

<template id="admin-consoles-table">
	<table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp" style="width: 100%;">
		<thead>
			<tr>
				<th class="mdl-data-table__cell--non-numeric">Console</th>
				<th class="mdl-data-table__cell--non-numeric">Date de lancement</th>
			</tr>
		</thead>
		<tbody id="consoles-list">

		</tbody>
	</table>		
</template>