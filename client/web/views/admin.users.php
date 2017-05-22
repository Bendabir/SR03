<?php 
	require_once(dirname(__FILE__)."/../utils/config.php");
	require_once(dirname(__FILE__)."/../libraries/Requests.php");

	Requests::register_autoloader();

	$headers = array(
		"Cookie" => "JSESSIONID=".$_SESSION["JSESSIONID"] // Setting cookie 
	);
	$usersRequest = Requests::get($apiEndPoint."/users", $headers);
	$users = $usersRequest->body;

	// Building interface from genres
	$users = json_decode($users, true);
?>

<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop">
		<table class="mdl-data-table mdl-js-data-table mdl-data-table--selectable mdl-shadow--2dp" style="width: 100%;">
			<thead>
				<tr>
					<th class="mdl-data-table__cell--non-numeric">Login</th>
					<th class="mdl-data-table__cell--non-numeric">Prénom</th>
					<th class="mdl-data-table__cell--non-numeric">Nom</th>
					<th class="mdl-data-table__cell--non-numeric">Status</th>
				</tr>
			</thead>
			<tbody>
				<?php 
					foreach($users as $key => $u){
						// Building line
						echo "<tr user-id=\"".$u["username"]."\">";
						echo	"<td class=\"mdl-data-table__cell--non-numeric\">".$u["username"]."</td>";
						echo	"<td class=\"mdl-data-table__cell--non-numeric\">".$u["firstName"]."</td>";
						echo	"<td class=\"mdl-data-table__cell--non-numeric\">".$u["lastName"]."</td>";
						echo	"<td class=\"mdl-data-table__cell--non-numeric\">".$u["status"]."</td>";
						echo "</tr>";
					}
				?>
			</tbody>
		</table>		
	</div>
</div>