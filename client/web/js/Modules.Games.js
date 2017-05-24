; var Modules = Modules || {};

(function(){
	'use stict';

	// Transform an array into a string, 
	function __stringifyGenres(genres){
		if(!Array.isArray(genres))
			throw new Error('The genre attribute must be an array.');

		return (genres.length == 0 ? 'Inconnu' : genres.join(' | '));
	}

	// Build a card and return the DOM object
	function __card(game){
		// Checking the game
		if(typeof game != 'object' || !game)
			throw new Error('The game attribute must be an object.');

		if(typeof game.publisher == 'undefined')
			game.publisher = 'Inconnu';

		if(typeof game.description == 'undefined')
			game.description = 'Pas de description.';

		// Building the card using a HTML5 template
		var template = document.querySelector('#game-card-template');

		// Filling the template
		template.content.querySelector('.mdl-card__title-text').innerHTML = game.title + '<br />(' + game.console + ')';
		template.content.querySelector('.game-card-description').textContent = game.description;
		template.content.querySelector('.game-card-publisher').textContent = ' ' + game.publisher;
		template.content.querySelector('.game-card-release-date').textContent = ' ' + (new Date(game.releaseDate)).toLocaleDateString();
		template.content.querySelector('.game-card-genre').textContent = ' ' + __stringifyGenres(game.genres);
		template.content.querySelector('.game-card-price').textContent = ' ' + game.price.formatNumber(2, ',', ' ');
		template.content.querySelector('.game-card-add-to-cart-button').textContent = (game.stock > 0 ? 'Ajouter au panier' : 'En rupture de stock');
		template.content.querySelector('.game-card-add-to-cart-button').setAttribute('game-id', game.id);
		template.content.querySelector('.game-card-add-to-cart-button').removeAttribute('disabled'); // Cleaning template
		template.content.querySelector('.game-card-add-to-cart-button').removeAttribute('enabled');
		template.content.querySelector('.game-card-add-to-cart-button').setAttribute(game.stock == 0 ? 'disabled' : 'enabled', true);

		return document.importNode(template.content, true);
	}

	Modules.Games = function(){
		this.__moduleName = 'games';
		this.__defaultContainer = null;
	}

	Modules.Games.prototype.init = function(){
		this.setDefaultContainer('#games .page-content .mdl-grid .mdl-cell .mdl-grid');

		// Linking the tab to the reload function
		document.querySelector('a[href="#games"]').addEventListener('click', e => this.reload());

		this.reload();

		console.log('Games module initialized.');
	}

	// Set the default container when modifying content
	Modules.Games.prototype.setDefaultContainer = function(container){
		if(typeof container != 'string' || !container)
			throw new Error('The container attribute must be a string (CSS selector of the container).');

		this.__defaultContainer = document.querySelector(container);
	}

	// Defining methods
	Modules.Games.prototype.get = function(id = '', onSuccess, onError){
		this.parent.ajax({
			method: 'GET',
			url: this.parent.apiPath('games' + (id == '' ? '' : '/') + id)
		}, function(obj){
			console.log(obj.response);
		}, function(err){
			console.error(err.error);
		});
	}

	Modules.Games.prototype.post = function(onSuccess, onError){

	}

	Modules.Games.prototype.put = function(id, onSuccess, onError){

	}

	Modules.Games.prototype.delete = function(id, onSuccess, onError){
		this.parent.ajax({
			method: 'DELETE',
			url: this.parent.__apiPath('games/' + id)
		}, function(obj){
			console.log(obj.response);
		}, function(err){
			console.error(err.error);
		});
	}

	// Clean the interface
	Modules.Games.prototype.clean = function(){
		this.__defaultContainer.innerHTML = '';
	}

	// Reload the interface with all games
	Modules.Games.prototype.reload = function(){
		this.clean();
		
		if(typeof this.parent.interface == 'undefined')
			throw new Error('This module needs the Interface module in order to run properly.');

		this.__defaultContainer.append(this.parent.interface.__loader());

		var currentModule = this;

		this.parent.ajax({
			method: 'GET',
			url: this.parent.apiPath('games'),
			async: this.parent.__async
		}, function(obj){
			var games = obj.response;
			
			currentModule.clean();

			// If we have orders, then buiding the interface
			if(games.length > 0){
				// Building the interface
				games.forEach(function(g){
					currentModule.__defaultContainer.append(__card(g));
				});
			}
			else {
				// Otherwise, just displaying a card saying there is no order yet
				var info = {
					title: 'Aucun jeu disponible',
					text: 'Aucun jeu n\'est disponible à la vente pour le moment. Revenez plus tard.<br />',
					action: 'Accéder à mes commandes',
					actionLink: '#orders',
					icon: ''
				};

				currentModule.__defaultContainer.append(currentModule.parent.interface.__infoCard(info));
			}			

			// Updating the game number in the tabs
			document.querySelector('#games-number').textContent = games.length;
		}, function(err){
			// Building a card depending on the error
			var e = {
				message: err.error.message,
				code: err.error.status,
				moreInformation: null,
				action: 'Recharger',
				icon: 'loop'
			};

			// Depeding on the error code
			switch (e.code) {
				case 500: {
					e.moreInformation = 'Une erreur critique s\'est produite. Veuillez contacter l\'administrateur.';
				} break;
				default: {
					e.moreInformation = 'Une erreur inconnue s\'est produite. Tentez de recharger la page.';
				} break;
			}

			currentModule.clean();

			currentModule.__defaultContainer.append(currentModule.parent.interface.__errorCard(e));

			console.error(err.error);
		});
	}
})();