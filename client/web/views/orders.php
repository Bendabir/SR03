<?php 
	require_once("./utils/config.php");
	require_once("./libraries/Requests.php");

	Requests::register_autoloader();

	$headers = array(
		"Cookie" => "JSESSIONID=".$_SESSION["JSESSIONID"] // Setting cookie 
	);
	$ordersRequest = Requests::get($apiEndPoint."/orders", $headers);
	$orders = $ordersRequest->body;

	// Building interface from orders
	$orders = json_decode($orders, true);
?>

<div class="mdl-grid">
	<?php
		// If detecting connection error, then logging out ?
		if(isset($orders["message"])){
			echo "<div class=\"mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop\">";
	?>

				<div class="wide-card error-card mdl-card mdl-shadow--2dp">
					<div class="mdl-card__title">
						<h2 class="mdl-card__title-text">Ooops</h2>
					</div>
					<div class="mdl-card__supporting-text">
						Une erreur s'est produite : <b><?php echo $orders["message"]; ?></b><br />
						Tentez de vous déconnecter et de vous reconnecter pour résoudre le problème.
						<br />
						<br />
					</div>
					<div class="mdl-card__actions mdl-card--border">
						<a class="mdl-button mdl-js-button mdl-js-ripple-effect">
							Se déconnecter
						</a>
					</div>
					<div class="mdl-card__menu">
						<button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect">
							<i class="material-icons">loop</i>
						</button>
					</div>
				</div>
	<?php
			echo "</div>";
			return;
		}

		// If no orders yet
		if(count($orders) == 0){
			echo "Aucune commande passée pour le moment.";
			return;
		}

		// Processing each order
		foreach($orders as $k1 => $o){
			// Processing lines
			$totalAmount = 0;
			$linesInterface = "";

			// Building lines list
			foreach($o["lines"] as $k2 => $l){
				$totalAmount += $l["unitPrice"] * $l["quantity"];

				// Checking some parameters
				if(!isset($l["game"]["description"])){
					$l["game"]["description"] = "Pas de description.";
				}

				if(!isset($l["game"]["publisher"])){
					$l["game"]["publisher"] = "Inconnu";
				}

				$linesInterface .= "<li class=\"mdl-list__item mdl-list__item--three-line\">";
				$linesInterface .= 	"<span class=\"mdl-list__item-primary-content\">";
				$linesInterface .= 		"<img src=\"./img/just_cause_3_pc.jpg\" class=\"mdl-list__item-avatar\" />";
				$linesInterface .= 		"<span>".$l["game"]["title"]." (".$l["game"]["console"].")</span>";
				$linesInterface .= 		"<span class=\"mdl-list__item-text-body\">";
				// $linesInterface .= 			"<b>Editeur:</b> ".$l["game"]["publisher"]."<br />";
				// $linesInterface .= 			"<b>Date de sortie:</b> ".$l["game"]["releaseDate"]."<br />";
				$linesInterface .= 			$l["game"]["description"];
				$linesInterface .= 		"</span>";
				$linesInterface .= 	"</span>";
				$linesInterface .= 	"<span class=\"mdl-list__item-secondary-content\">";
				$linesInterface .= 		"<p>";
				$linesInterface .= 			"<b>".number_format($l["unitPrice"], 2, ",", " ")."€</b>";
				$linesInterface .= 			"<br />";
				$linesInterface .= 			"x<b>".$l["quantity"]."</b>";
				$linesInterface .= 		"</p>";
				$linesInterface .= 	"</span>";
				$linesInterface .= "</li>";

			}

			$totalAmount = number_format($totalAmount, 2, ",", " ");

?>
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop">
		<div class="wide-card order-card mdl-card mdl-shadow--2dp">
			<div class="mdl-card__title">
				<h2 class="mdl-card__title-text">Commande n°<?php echo $o["num"]; ?> <br />Effectuée le <?php echo date_format(date_create($o["date"]), "d/m/Y"); ?></h2>
			</div>
			<div class="mdl-card__menu">
				<p>Montant total: <?php echo $totalAmount; ?>€</p>
			</div>
			<div class="mdl-card__supporting-text">
				<ul class="demo-list-three mdl-list">
					<?php echo $linesInterface; ?>
					<hr />
					<li class="mdl-list__item mdl-list__item--three-line">
						<span class="mdl-list__item-primary-content"></span>
						<span class="mdl-list__item-secondary-content">
							<p>
								<b><?php echo $totalAmount; ?>€</b>
							</p>
						</span>
					</li>											
				</ul>
			</div>
<!-- 			<div class="mdl-card__actions mdl-card--border">
				<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
					Facture
				</a>										
			</div> -->
		</div>
	</div>
	
	<?php
		}
	?>
</div>