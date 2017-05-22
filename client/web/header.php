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
			<a href="#fixed-tab-1" class="mdl-layout__tab is-active">Jeux</a>
			<a href="#fixed-tab-2" class="mdl-layout__tab">Mon panier (3)</a>
			<a href="#fixed-tab-3" class="mdl-layout__tab">Mes commandes</a>
		</div>
	<?php 
		}
		else {
	?>
		<div class="mdl-layout__tab-bar mdl-js-ripple-effect">
			<a href="#fixed-tab-1" class="mdl-layout__tab is-active">Jeux</a>
			<a href="#fixed-tab-2" class="mdl-layout__tab">Consoles</a>
			<a href="#fixed-tab-3" class="mdl-layout__tab">Genres</a>
			<a href="#fixed-tab-4" class="mdl-layout__tab">Commandes</a>
			<a href="#fixed-tab-5" class="mdl-layout__tab">Utilisateurs</a>
		</div>
	<?php
		}
	?>
</header>

<div class="mdl-layout__drawer">
	<span class="mdl-layout-title">Game Store</span>
	<nav class="mdl-navigation">
		<a class="mdl-navigation__link" href="#">Mon compte</a>
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
	</nav>
</div>

<ul class="mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect" for="filter-menu">
	<li class="mdl-menu__item">
		<label class="mdl-checkbox mdl-js-checkbox mdl-js-ripple-effect" for="checkbox-1">
			<input type="checkbox" id="checkbox-1" class="mdl-checkbox__input" checked>
			<span class="mdl-checkbox__label">Consoles</span>
		</label>
	</li>
</ul>