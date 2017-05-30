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

		// Reinitializing the game cover
		template.content.querySelector('.mdl-card__title').style.backgroundColor = '#2E2E2E';
		template.content.querySelector('.mdl-card__title').style.backgroundImage = '';

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

		// If we have an cover, then displaying it
		if(typeof game.cover != 'undefined'){
			template.content.querySelector('.mdl-card__title').style.backgroundImage = 'url(\'' + game.cover + '\')';
			template.content.querySelector('.mdl-card__title').style.backgroundRepeat = 'no-repeat';
			template.content.querySelector('.mdl-card__title').style.backgroundPosition = 'center center';
			template.content.querySelector('.mdl-card__title').style.backgroundSize = '100%';
		}

		return document.importNode(template.content, true);
	}

	function __search(gameName){

	}


	Modules.Games = function(){
		this.__moduleName = 'games';
		this.__defaultContainer = null;
		this.__dependencies = [Modules.Interface, Modules.Cart]; // List of classes for dependencies
	}

	Modules.Games.prototype.init = function(){
		this.setDefaultContainer('#games .page-content .mdl-grid .mdl-cell .mdl-grid');

		var module = this;

		// Link the sort function
		document.querySelectorAll('ul[for = "sort-menu"] li').forEach(function(choiceElement){
			choiceElement.addEventListener('click', function(event){
				module.__sort(this.getAttribute("sort-type"));
			});
		});

		// Linking the tab to the reload function
		document.querySelector('a[href="#games"]').addEventListener('click', (e) => {
			this.reload();

			var module = this;

			// Link the sort function
			document.querySelectorAll('ul[for = "sort-menu"] li').forEach(function(choiceElement){
				choiceElement.addEventListener('click', function(event){
					module.__sort(this.getAttribute("sort-type"));
				});
			});
		});

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
			url: this.parent.apiPath('games/' + id)
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

	// Sort the interface
	Modules.Games.prototype.__sort = function(choice){
		// Game cards
		var cards = [].slice.call(this.__defaultContainer.children);

		switch(choice){
			case 'by-name': {
				// Sorting the array
				cards.sort(function(a, b){
					var titleA = a.querySelector('.mdl-card__title-text').innerHTML.split('<br>')[0],
						titleB = b.querySelector('.mdl-card__title-text').innerHTML.split('<br>')[0];

					return titleA.localeCompare(titleB);
				});
			} break;

			case 'by-genre': {
				// Sorting the array
				cards.sort(function(a, b){
					var genreA = a.querySelector('.game-card-genre').innerHTML,
						genreB = b.querySelector('.game-card-genre').innerHTML;

					return genreA.localeCompare(genreB);
				});
			} break;

			case 'by-date': {
				// Sorting the array
				cards.sort(function(a, b){
					var dateA = a.querySelector('.game-card-release-date').innerHTML,
						dateB = b.querySelector('.game-card-release-date').innerHTML;

					dateA = dateA.split('/');
					dateB = dateB.split('/');

					dateA = dateA[1] + '/' + dateA[0] + '/' + dateA[2];
					dateB = dateB[1] + '/' + dateB[0] + '/' + dateB[2];

					return new Date(dateA) - new Date(dateB);
				});
			} break;

			case 'by-price': {
				// Sorting the array
				cards.sort(function(a, b){
					var priceA = parseFloat(a.querySelector('.game-card-price').innerHTML.replace(',', '.')),
						priceB = parseFloat(b.querySelector('.game-card-price').innerHTML.replace(',', '.'));

					return priceA - priceB;
				});
			} break;

			default: {
				cards.sort(function(a, b){
					var idA = parseInt(a.querySelector('.game-card-add-to-cart-button').getAttribute('game-id')),
						idB = parseInt(b.querySelector('.game-card-add-to-cart-button').getAttribute('game-id'));

					return idA - idB;
				});
			} break;
		}

		// Cleaning and reappending sorted games
		this.clean();

		cards.forEach((e) => {
			this.__defaultContainer.append(e);
		});		
	}	

	// Reload the interface with all games
	Modules.Games.prototype.reload = function(){
		this.clean();

		this.__defaultContainer.append(this.parent.getModule(Modules.Interface).__loader());

		var currentModule = this;

		this.parent.ajax({
			method: 'GET',
			url: this.parent.apiPath('games')
		}, function(obj){
			var games = obj.response;
			
			currentModule.clean();

			// If we have orders, then buiding the interface
			if(games.length > 0){
				// Building the interface
				games.forEach(function(g){
					currentModule.__defaultContainer.append(__card(g));
				});

				// When clicking on a card, the product is added to cart
				document.querySelectorAll('.game-card-add-to-cart-button').forEach(function(el){
					el.addEventListener('click', function(e){
						currentModule.parent.getModule(Modules.Cart).post(this.getAttribute('game-id'), 1);
					});
				});
			}
			else {
				// Otherwise, just displaying a card saying there is no order yet
				var info = {
					title: 'Aucun jeu disponible',
					text: 'Aucun jeu n\'est disponible à la vente pour le moment. Revenez plus tard.<br />',
					action: 'Accéder à mes commandes',
					actionLink: '#orders',
					actionSimulateTabClick: true,
					icon: ''
				};

				currentModule.__defaultContainer.append(currentModule.parent.getModule(Modules.Interface).__infoCard(info));
				
				// Simulating click
				document.querySelector('#cart .info-action').addEventListener('click', function(e){
					document.querySelector('a[href = "' + info.actionLink + '"]').click();
				});
			}			

			// Updating the game number in the tabs
			document.querySelector('#games-number').textContent = games.length;

			// Displaying the sort button
			document.querySelector('#sort-menu').style.display = '';
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

			currentModule.__defaultContainer.append(currentModule.parent.getModule(Modules.Interface).__errorCard(e));

			console.error(err.error);
		});
	}
})();