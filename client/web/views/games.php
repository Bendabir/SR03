<?php 
	require_once("./config.php");

	$games = file_get_contents($apiEndPoint."/games");

	// Building interface from games
	$games = json_decode($games, true);
?>

<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop">
		<div class="mdl-grid">
			<?php 
				foreach($games as $key => $g){
					// Preparing genres
					$genres = (count($g["genres"]) ? "" : "Inconnu");

					foreach($g["genres"] as $k => $ge){
						if($k > 0)
							$genres .= " | ";

						$genres .= $ge;
					}

					echo "<div class=\"mdl-cell mdl-cell--3-col mdl-cell--12-col-phone\">";
					echo	"<div class=\"game-card mdl-card mdl-shadow--2dp\">";
					echo		"<div class=\"mdl-card__title mdl-card--expand\">";
					echo			"<h2 class=\"mdl-card__title-text\">".$g["title"]."<br />(".$g["console"].")</h2>";
					echo		"</div>";
					echo		"<div class=\"mdl-card__supporting-text\">";
					echo			"<p>Pas de description.</p>";
					echo			"<p>";
					echo 				"<b>Editeur:</b> Inconnu<br />";
					echo				"<b>Date de sortie:</b> ".$g["releaseDate"]."<br />";
					echo				"<b>Genre:</b> ".$genres."<br />";
					echo				"<b>Prix:</b> ".number_format($g["price"], 2, ",", " ")."€";
					echo 			"</p>";
					echo		"</div>";
					echo	"<div class=\"mdl-card__actions mdl-card--border\">";
					echo			"<button class=\"mdl-button mdl-js-button mdl-js-ripple-effect\" game-id=\"".$g["id"]."\" ".($g["stock"] ? "" : "disabled").">";
					echo				($g["stock"] ? "Ajouter au panier" : "En rupture de stock");
					echo			"</button>";
					echo		"</div>";
					echo	"</div>";
					echo "</div>";
				}
			?>
		</div>
	</div>
</div>