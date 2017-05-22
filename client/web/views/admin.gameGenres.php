<?php 
	require_once(dirname(__FILE__)."/../utils/config.php");
	require_once(dirname(__FILE__)."/../libraries/Requests.php");

	Requests::register_autoloader();

	$genresRequest = Requests::get($apiEndPoint."/gameGenres");
	$genres = $genresRequest->body;

	// Building interface from genres
	$genres = json_decode($genres, true);
?>

<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop">
		<table class="mdl-data-table mdl-js-data-table mdl-data-table--selectable mdl-shadow--2dp" style="width: 100%;">
			<thead>
				<tr>
					<th class="mdl-data-table__cell--non-numeric">Genre</th>
					<th class="mdl-data-table__cell--non-numeric">Description</th>
				</tr>
			</thead>
			<tbody>
				<?php 
					foreach($genres as $key => $g){
						// Description
						if(!isset($g["description"])){
							$g["description"] = "Pas de description.";
						}

						$descriptionMaxLength = 165;

						// Building line
						echo "<tr genre-id=\"".$g["name"]."\">";
						echo	"<td class=\"mdl-data-table__cell--non-numeric\">".$g["name"]."</td>";
						echo	"<td class=\"mdl-data-table__cell--non-numeric\" style=\"width: 500px;\">".(strlen($g["description"]) < $descriptionMaxLength ? $g["description"] : (substr($g["description"], 0, $descriptionMaxLength - 3)."..."))."</td>";
						echo "</tr>";
					}
				?>
			</tbody>
		</table>		
	</div>
</div>