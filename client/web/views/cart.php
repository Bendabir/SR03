<?php 
	require_once(dirname(__FILE__)."/../utils/config.php");
	require_once(dirname(__FILE__)."/../libraries/Requests.php");

	Requests::register_autoloader();

	// $gamesRequest = Requests::get($apiEndPoint."/games");
	// $games = $gamesRequest->body;

	// Building interface from games
	// $games = json_decode($games, true);
?>

<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--2-offset-desktop mdl-cell--6-col mdl-cell--12-col-phone">
		<ul class="demo-list-three mdl-list">
			<li class="mdl-list__item mdl-list__item--three-line">
				<span class="mdl-list__item-primary-content">
					<img src="./img/just_cause_3_pc.jpg" class="mdl-list__item-avatar" />
					<span>Just Cause 3 (PC)</span>
					<span class="mdl-list__item-text-body">
						Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla suscipit ornare turpis, sit amet tempor nunc convallis pharetra. Vestibulum a mollis odio. Interdum et malesuada fames ac ante ipsum primis in faucibus. Phasellus vitae nisi tortor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.
					</span>
				</span>
				<span class="mdl-list__item-secondary-content">
					<p>
						<b>3,50€</b>
						<br />
						x<b>2</b>
					</p>
				</span>
			</li>
			<li class="mdl-list__item mdl-list__item--three-line">
				<span class="mdl-list__item-primary-content">
					<img src="./img/minecraft_pc.jpg" class="mdl-list__item-avatar" />
					<span>Minecraft (PC)</span>
					<span class="mdl-list__item-text-body">
						Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla suscipit ornare turpis, sit amet tempor nunc convallis pharetra. Vestibulum a mollis odio. Interdum et malesuada fames ac ante ipsum primis in faucibus.
					</span>
				</span>
				<span class="mdl-list__item-secondary-content">
					<p>
						<b>3,00€</b>
						<br />
						x<b>1</b>
					</p>
				</span>
			</li>
			<li class="mdl-list__item mdl-list__item--three-line">
				<span class="mdl-list__item-primary-content">
					<i class="material-icons  mdl-list__item-avatar">help</i>
					<span>Bob Odenkirk</span>
					<span class="mdl-list__item-text-body">
						Bob Odinkrik played the role of Saul in Breaking Bad. Due to public fondness for the
						character, Bob stars in his own show now, called "Better Call Saul".
					</span>
				</span>
				<span class="mdl-list__item-secondary-content">
					<p>3,00€</p>
				</span>
			</li>
			<hr />
			<li class="mdl-list__item mdl-list__item--three-line">
				<span class="mdl-list__item-primary-content"></span>
				<span class="mdl-list__item-secondary-content">
					<p>
						<b>13,00€</b>
					</p>
				</span>
			</li>									
		</ul>
	</div>
	<div class="mdl-cell mdl-cell-order-2-desktop mdl-cell--12-col-phone">
		<button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent">Valider la commande</button>
	</div>
</div>