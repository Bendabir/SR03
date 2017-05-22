<?php 
	require_once(dirname(__FILE__)."/../utils/config.php");
	require_once(dirname(__FILE__)."/../libraries/Requests.php");

	Requests::register_autoloader();

	$consolesRequest = Requests::get($apiEndPoint."/consoles");
	$consoles = $consolesRequest->body;

	// Building interface from consoles
	$consoles = json_decode($consoles, true);
?>

<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop">
		<table class="mdl-data-table mdl-js-data-table mdl-data-table--selectable mdl-shadow--2dp" style="width: 100%;">
			<thead>
				<tr>
					<th class="mdl-data-table__cell--non-numeric">Console</th>
					<th class="mdl-data-table__cell--non-numeric">Date de lancement</th>
				</tr>
			</thead>
			<tbody>
				<?php 
					foreach($consoles as $key => $c){
						// Building line
						echo "<tr console-id=\"".$c["name"]."\">";
						echo	"<td class=\"mdl-data-table__cell--non-numeric\">".$c["name"]."</td>";
						echo	"<td class=\"mdl-data-table__cell--non-numeric\">".date_format(date_create($c["launchedDate"]), "d/m/Y")."</td>";
						echo "</tr>";
					}
				?>
			</tbody>
		</table>		
	</div>
</div>