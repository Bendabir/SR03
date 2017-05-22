<?php 
	require_once(dirname(__FILE__)."/../utils/config.php");
	require_once(dirname(__FILE__)."/../libraries/Requests.php");

	Requests::register_autoloader();

	$gamesRequest = Requests::get($apiEndPoint."/games");
	$games = $gamesRequest->body;

	// Building interface from games
	$games = json_decode($games, true);
?>

<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop">
		<table class="mdl-data-table mdl-js-data-table mdl-data-table--selectable mdl-shadow--2dp" style="width: 100%;">
			<thead>
				<tr>
					<th class="mdl-data-table__cell--non-numeric">Jeu</th>
					<th class="mdl-data-table__cell--non-numeric">Console</th>
					<th class="mdl-data-table__cell--non-numeric">Date de sortie</th>
					<th>Prix</th>
					<th class="mdl-data-table__cell--non-numeric">Editeur</th>
					<th class="mdl-data-table__cell--non-numeric">Description</th>
					<th class="mdl-data-table__cell--non-numeric">Genres</th>
					<th>Stock</th>
				</tr>
			</thead>
			<tbody>
				<?php 
					foreach($games as $key => $g){
						// Preparing genres
						$genres = (count($g["genres"]) ? "" : "Inconnu");

						foreach($g["genres"] as $k => $ge){
							if($k > 0)
								$genres .= " | ";

							$genres .= $ge;
						}

						// Checking on fields
						if(!isset($g["publisher"])){
							$g["publisher"] = "Inconnu";
						}

						if(!isset($g["description"])){
							$g["description"] = "Pas de description.";
						}

						$descriptionMaxLength = 35;

						// Building line
						echo "<tr game-id=\"".$g["id"]."\">";
						echo	"<td class=\"mdl-data-table__cell--non-numeric\">".$g["title"]."</td>";
						echo	"<td class=\"mdl-data-table__cell--non-numeric\">".$g["console"]."</td>";
						echo	"<td class=\"mdl-data-table__cell--non-numeric\">".date_format(date_create($g["releaseDate"]), "d/m/Y")."</td>";
						echo	"<td>".number_format($g["price"], 2, ",", " ")."€</td>";
						echo	"<td class=\"mdl-data-table__cell--non-numeric\">".$g["publisher"]."</td>";
						echo	"<td class=\"mdl-data-table__cell--non-numeric\">".(strlen($g["description"]) < $descriptionMaxLength ? $g["description"] : (substr($g["description"], 0, $descriptionMaxLength - 3)."..."))."</td>";
						echo	"<td class=\"mdl-data-table__cell--non-numeric\">".$genres."</td>";
						echo	"<td>".$g["stock"]."</td>";
						echo "</tr>";
					}
				?>
			</tbody>
		</table>		
	</div>
</div>