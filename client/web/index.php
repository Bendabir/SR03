<!DOCTYPE html>
<html>
	<head>
		<title>Video Games Store</title>
		<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.blue-pink.min.css" />
		<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
		<link rel="stylesheet" href="./css/gamestore.css">
		<link rel="icon" type="image/png" href="./img/favicon.png" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta charset="utf-8">
	</head>
	<body>
		<!-- Always shows a header, even in smaller screens. -->
		<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header mdl-layout--fixed-tabs">

			<?php require_once('header.php'); ?>

			<main class="mdl-layout__content">
				<section class="mdl-layout__tab-panel is-active" id="fixed-tab-1">
					<div class="page-content">
						<?php require_once('./views/games.php'); ?>
					</div>
				</section>
				<section class="mdl-layout__tab-panel" id="fixed-tab-2">
					<div class="page-content">
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
					</div>
				</section>
				<section class="mdl-layout__tab-panel" id="fixed-tab-3">
					<div class="page-content">
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
					</div>
				</section>
			</main>

<!-- 			<footer class="mdl-mini-footer mdl-layout--large-screen-only">
				<p>Made with ♥ by Ben & Jo</p>
			</footer> -->
		</div>

		<script src="./js/material.min.js"></script>
		<script src="./js/workshop.js"></script>
		<script src="./js/gamestore.js"></script>
	</body>
</html>