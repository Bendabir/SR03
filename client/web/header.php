<!-- Always shows a header, even in smaller screens. -->
<header class="mdl-layout__header">
	<div class="mdl-layout__header-row">
		<!-- Title -->
		<span class="mdl-layout-title">Game Store</span>
		<!-- Add spacer, to align navigation to the right -->
		<div class="mdl-layout-spacer"></div>
		<div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable mdl-textfield--floating-label mdl-textfield--align-right">
			<label class="mdl-button mdl-js-button mdl-button--icon" for="fixed-header-drawer-exp">
				<i class="material-icons">search</i>
			</label>

			<div class="mdl-textfield__expandable-holder">
				<input class="mdl-textfield__input" type="text" name="sample" id="fixed-header-drawer-exp">
			</div>	
		</div>

		<!-- Right aligned menu below button -->
		<button id="filter-menu" class="mdl-button mdl-js-button mdl-button--icon">
			<i class="material-icons">filter_list</i>
		</button>	
	</div>

	<!-- Tabs -->
	<?php 
		if(!strpos($_SERVER["REQUEST_URI"], "admin")){
	?>
		<div class="mdl-layout__tab-bar mdl-js-ripple-effect">
			<a href="#games" class="mdl-layout__tab is-active">Jeux (<span id="games-number">-</span>)</a>
			<a href="#cart" class="mdl-layout__tab">Mon panier (<span id="products-in-cart-number">-</span>)</a>
			<a href="#orders" class="mdl-layout__tab">Mes commandes (<span id="orders-number">-</span>)</a>
		</div>
	<?php 
		}
		else {
	?>
		<div class="mdl-layout__tab-bar mdl-js-ripple-effect">
			<a href="#games" class="mdl-layout__tab is-active">Jeux</a>
			<a href="#consoles" class="mdl-layout__tab">Consoles</a>
			<a href="#genres" class="mdl-layout__tab">Genres</a>
			<a href="#orders" class="mdl-layout__tab">Commandes</a>
			<a href="#users" class="mdl-layout__tab">Utilisateurs</a>
		</div>
	<?php
		}
	?>
</header>

<div class="mdl-layout__drawer">
	<span class="mdl-layout-title">Game Store</span>
	<nav class="mdl-navigation">
		<?php 
			if($_SESSION["status"] == "admin"){
				if(strpos($_SERVER["REQUEST_URI"], "admin")){
					echo "<a class=\"mdl-navigation__link\" href=\"../\">Retour au site</a>";
				}
				else {
					echo "<a class=\"mdl-navigation__link\" href=\"./admin/\">Gérer le site</a>";
				}
			}
		?>
		<a class="mdl-navigation__link" href="<?php	echo 'http://'.$_SERVER['SERVER_NAME'].'/sr03/controllers/logout.php'; ?>">Se déconnecter</a>
	</nav>
</div>

<ul class="mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect" for="filter-menu">
	<li class="mdl-menu__item">Par nom</li>
	<li class="mdl-menu__item">Par date de sortie</li>
	<li class="mdl-menu__item">Par prix</li>
	<li class="mdl-menu__item">Par genre</li>
</ul>