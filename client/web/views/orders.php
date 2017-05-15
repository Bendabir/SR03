<?php 
	require_once("./utils/config.php");
	require_once("./libraries/Requests.php");

	Requests::register_autoloader();

	// $gamesRequest = Requests::get($apiEndPoint."/games");
	// $games = $gamesRequest->body;

	// Building interface from games
	// $games = json_decode($games, true);
?>

<div class="mdl-grid">
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop">
		<div class="demo-card-wide mdl-card mdl-shadow--2dp">
			<div class="mdl-card__title">
				<h2 class="mdl-card__title-text">Commande n°125 <br />Effectuée le 01/01/2017</h2>
			</div>
			<div class="mdl-card__menu">
				<p>Montant total: 10,00€</p>
			</div>
			<div class="mdl-card__supporting-text">
				<ul class="demo-list-three mdl-list">
					<li class="mdl-list__item mdl-list__item--three-line">
						<span class="mdl-list__item-primary-content">
							<img src="./img/just_cause_3_pc.jpg" class="mdl-list__item-avatar" />
							<!-- <i class="material-icons mdl-list__item-avatar">person</i> -->
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
					<hr />
					<li class="mdl-list__item mdl-list__item--three-line">
						<span class="mdl-list__item-primary-content"></span>
						<span class="mdl-list__item-secondary-content">
							<p>
								<b>10,00€</b>
							</p>
						</span>
					</li>
				</ul>
			</div>
			<div class="mdl-card__actions mdl-card--border">
				<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
					Facture
				</a>										
			</div>
		</div>
	</div>
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop">
		<div class="demo-card-wide mdl-card mdl-shadow--2dp">
			<div class="mdl-card__title">
				<h2 class="mdl-card__title-text">Commande n°12 <br />Effectuée le 05/12/2016</h2>
			</div>
			<div class="mdl-card__menu">
				<p>Montant total: 20,00€</p>
			</div>
			<div class="mdl-card__supporting-text">
				<ul class="demo-list-three mdl-list">
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
								<b>20,00€</b>
								<br />
								x<b>1</b>
							</p>
						</span>
					</li>
					<hr />
					<li class="mdl-list__item mdl-list__item--three-line">
						<span class="mdl-list__item-primary-content"></span>
						<span class="mdl-list__item-secondary-content">
							<p>
								<b>20,00€</b>
							</p>
						</span>
					</li>											
				</ul>
			</div>
			<div class="mdl-card__actions mdl-card--border">
				<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
					Facture
				</a>										
			</div>
		</div>
	</div>
	<div class="mdl-cell mdl-cell--8-col mdl-cell--12-col-phone mdl-cell--2-offset-desktop">
		<div class="demo-card-wide mdl-card mdl-shadow--2dp">
			<div class="mdl-card__title">
				<h2 class="mdl-card__title-text">Commande n°10 <br />Effectuée le 24/10/2016</h2>
			</div>
			<div class="mdl-card__menu">
				<p>Montant total: 35,00€</p>
			</div>
			<div class="mdl-card__supporting-text">
				<ul class="demo-list-three mdl-list">
					<li class="mdl-list__item mdl-list__item--three-line">
						<span class="mdl-list__item-primary-content">
							<img src="./img/just_cause_3_pc.jpg" class="mdl-list__item-avatar" />
							<!-- <i class="material-icons mdl-list__item-avatar">person</i> -->
							<span>Just Cause 3 (PC)</span>
							<span class="mdl-list__item-text-body">
								Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla suscipit ornare turpis, sit amet tempor nunc convallis pharetra. Vestibulum a mollis odio. Interdum et malesuada fames ac ante ipsum primis in faucibus. Phasellus vitae nisi tortor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.
							</span>
						</span>
						<span class="mdl-list__item-secondary-content">
							<p>
								<b>35,00€</b>
								<br />
								x<b>1</b>
							</p>
						</span>
					</li>
					<hr />
					<li class="mdl-list__item mdl-list__item--three-line">
						<span class="mdl-list__item-primary-content"></span>
						<span class="mdl-list__item-secondary-content">
							<p>
								<b>35,00€</b>
							</p>
						</span>
					</li>											
				</ul>
			</div>
			<div class="mdl-card__actions mdl-card--border">
				<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
					Facture
				</a>										
			</div>
		</div>
	</div>
</div>