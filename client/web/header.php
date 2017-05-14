<header class="mdl-layout__header">
	<div class="mdl-layout__header-row">
		<!-- Title -->
		<span class="mdl-layout-title">Video Games Store</span>
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
		<button id="admin-menu" class="mdl-button mdl-js-button mdl-button--icon">
			<i class="material-icons">more_vert</i>
		</button>
	</div>
	
	<!-- Tabs -->
	<div class="mdl-layout__tab-bar mdl-js-ripple-effect">
		<a href="#fixed-tab-1" class="mdl-layout__tab is-active">Jeux</a>
		<a href="#fixed-tab-2" class="mdl-layout__tab">Mon panier (3)</a>
		<a href="#fixed-tab-3" class="mdl-layout__tab">Mes commandes</a>
	</div>
</header>

<!-- Admin menu -->
<ul class="mdl-menu mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect" for="admin-menu">
	<li class="mdl-menu__item mdl-menu__item--full-bleed-divider">Mon compte</li>
	<li class="mdl-menu__item">Gérer les jeux</li>
	<li class="mdl-menu__item">Gérer les consoles</li>
	<li class="mdl-menu__item">Gérer les genres</li>
	<li disabled class="mdl-menu__item">Voir les commandes</li>
	<li class="mdl-menu__item">Gérer les utilisateurs</li>
</ul>

<!-- 			<div class="mdl-layout__drawer">
	<span class="mdl-layout-title">Options</span>
	<nav class="mdl-navigation">
		<a class="mdl-navigation__link" href="">Gérer les jeux</a>
		<a class="mdl-navigation__link" href="">Gérer les genres</a>
		<a class="mdl-navigation__link" href="">Gérer les consoles</a>
		<a class="mdl-navigation__link" href="">Voir les commandes</a>
		<a class="mdl-navigation__link" href="">Gérer les utilisateurs</a>
	</nav>
</div> -->