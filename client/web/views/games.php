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

					// Checking on fields
					if(!isset($g["publisher"])){
						$g["publisher"] = "Inconnu";
					}

					if(!isset($g["description"])){
						$g["description"] = "Pas de description.";
					}

					// Building card
					echo "<div class=\"mdl-cell mdl-cell-4--col mdl-cell--12-col-phone\">";
					echo	"<div class=\"game-card mdl-card mdl-shadow--2dp\">";
					echo		"<div class=\"mdl-card__title mdl-card--expand\">";
					echo			"<h2 class=\"mdl-card__title-text\">".$g["title"]."<br />(".$g["console"].")</h2>";
					echo		"</div>";
					echo		"<div class=\"mdl-card__supporting-text\">";
					echo			"<p class=\"game-card-description\">".$g["description"]."</p>";
					echo			"<p>";
					echo 				"<b>Editeur:</b> ".$g["publisher"]."<br />";
					echo				"<b>Date de sortie:</b> ".date_format(date_create($g["releaseDate"]), "d/m/Y")."<br />";
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