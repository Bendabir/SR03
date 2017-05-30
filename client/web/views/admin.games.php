<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--10-col mdl-cell--12-col-phone mdl-cell--1-offset-desktop">
	
	</div>
</div>

<template id="admin-games-line">
	<tr game-id="-1">
		<td input-type="text" class="mdl-data-table__cell--non-numeric admin-games-line-game-title">TITLE</td>
		<td input-type="text" class="mdl-data-table__cell--non-numeric admin-games-line-game-console">CONSOLE</td>
		<td input-type="text" class="mdl-data-table__cell--non-numeric admin-games-line-game-release-date">RELEASE_DATE</td>
		<td input-type="text" class="admin-games-line-game-price">PRICE</td>
		<td input-type="text" class="mdl-data-table__cell--non-numeric admin-games-line-game-publisher">PUBLISHER</td>
		<td input-type="textarea" class="mdl-data-table__cell--non-numeric admin-games-line-game-description text-truncation" style="text-align: justify;">DESCRIPTION</td>
		<td input-type="textarea" class="mdl-data-table__cell--non-numeric admin-games-line-game-cover text-truncation">COVER</td>
		<td input-type="text" class="mdl-data-table__cell--non-numeric admin-games-line-game-genres">GENRES</td>
		<td input-type="text" class="admin-games-line-game-stock">STOCK</td>
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