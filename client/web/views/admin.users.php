<!-- Main frame -->
<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop">

	</div>
</div>

<!-- Our templates for dynamic filling -->
<template id="admin-users-line">
	<tr>
		<td class="mdl-data-table__cell--non-numeric admin-users-line-user-login">LOGIN</td>
		<td class="mdl-data-table__cell--non-numeric admin-users-line-user-firstname">FIRSTNAME</td>
		<td class="mdl-data-table__cell--non-numeric admin-users-line-user-lastname">LASTNAME</td>
		<td class="mdl-data-table__cell--non-numeric admin-users-line-user-status">STATUS</td>
	</tr>
</template>

<template id="admin-users-table">
	<table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp" style="width: 100%;">
		<thead>
			<tr>
				<th class="mdl-data-table__cell--non-numeric">Login</th>
				<th class="mdl-data-table__cell--non-numeric">Prénom</th>
				<th class="mdl-data-table__cell--non-numeric">Nom</th>
				<th class="mdl-data-table__cell--non-numeric">Status</th>
			</tr>
		</thead>
		<tbody id="admin-users-list">

		</tbody>
	</table>		
</template>