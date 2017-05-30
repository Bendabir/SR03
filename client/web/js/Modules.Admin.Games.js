; var Modules = Modules || {};

(function(){
	'use stict';

	// Transform an array into a string, 
	function __stringifyGenres(genres){
		if(!Array.isArray(genres))
			throw new Error('The genre attribute must be an array.');

		return (genres.length == 0 ? 'Inconnu' : genres.join(' | '));
	}

	function __line(game){
		// Checking the game
		if(typeof game != 'object' || !game)
			throw new Error('The game attribute must be an object.');

		if(typeof game.publisher == 'undefined')
			game.publisher = 'Inconnu';

		if(typeof game.description == 'undefined')
			game.description = 'Pas de description.';

		if(typeof game.cover == 'undefined')
			game.cover = 'Pas de jaquette.';
		// else
			// game.cover = '<a href="' + game.cover + '" target="_blank">' + game.cover + '</a>';

		var maxLength = 25;

		// Building the card using a HTML5 template
		var template = document.querySelector('#admin-games-line');

		template.content.querySelector('.admin-games-line-game-title').innerHTML = game.title;
		template.content.querySelector('.admin-games-line-game-console').innerHTML = game.console;
		template.content.querySelector('.admin-games-line-game-release-date').innerHTML = (new Date(game.releaseDate)).toLocaleDateString();
		template.content.querySelector('.admin-games-line-game-price').innerHTML = game.price.formatNumber(2, ',', ' ') + '€';
		template.content.querySelector('.admin-games-line-game-publisher').innerHTML = game.publisher;
		template.content.querySelector('.admin-games-line-game-description').innerHTML = game.description;
		template.content.querySelector('.admin-games-line-game-cover').innerHTML = game.cover;
		template.content.querySelector('.admin-games-line-game-genres').innerHTML = __stringifyGenres(game.genres);
		template.content.querySelector('.admin-games-line-game-stock').innerHTML = game.stock;

		return document.importNode(template.content, true);
	}

	function __table(games){
		// Checking the line
		if(!Array.isArray(games) || !games)
			throw new Error('The games attribute must be an array.');

		// Building the card using a HTML5 template
		var template = document.querySelector('#admin-games-table');

		var table = template.content.querySelector('tbody');

		// Cleaning previous lines
		table.innerHTML = '';

		games.forEach(function(g){
			table.append(__line(g));
		});

		return document.importNode(template.content, true);	
	}


	Modules.Admin = Modules.Admin || {};


	Modules.Admin.Games = function(){
		this.__moduleName = 'games';
		this.__defaultContainer = null;
		this.__dependencies = [Modules.Interface]; // List of classes for dependencies
	}

	Modules.Admin.Games.prototype.init = function(){
		this.setDefaultContainer('#games .page-content .mdl-grid .mdl-cell');

		// Linking the tab to the reload function
		document.querySelector('a[href="#games"]').addEventListener('click', (e) => {
			this.reload();
		});

		this.reload();

		console.log('Admin games module initialized.');
	}

	// Set the default container when modifying content
	Modules.Admin.Games.prototype.setDefaultContainer = function(container){
		if(typeof container != 'string' || !container)
			throw new Error('The container attribute must be a string (CSS selector of the container).');

		this.__defaultContainer = document.querySelector(container);
	}

	// Defining methods
	Modules.Admin.Games.prototype.get = function(id = '', onSuccess, onError){
		this.parent.ajax({
			method: 'GET',
			url: this.parent.apiPath('games' + (id == '' ? '' : '/') + id)
		}, function(obj){
			console.log(obj.response);
		}, function(err){
			console.error(err.error);
		});
	}

	Modules.Admin.Games.prototype.post = function(onSuccess, onError){

	}

	Modules.Admin.Games.prototype.put = function(id, onSuccess, onError){

	}

	Modules.Admin.Games.prototype.delete = function(id, onSuccess, onError){
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
	Modules.Admin.Games.prototype.clean = function(){
		this.__defaultContainer.innerHTML = '';
	}

	// Reload the interface with all games
	Modules.Admin.Games.prototype.reload = function(){
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
				currentModule.__defaultContainer.append(__table(games));

				// Modifiers
				document.querySelectorAll('table tbody tr td').forEach(function(td){
					td.addEventListener('click', function(e){
						// If not already displayed
						if(this.children.length == 0){
							// Saving previous value
							var previousContent = this.innerHTML,
								inputType = this.getAttribute('input-type');

							// Append a text input
							var input = null;

							switch(inputType){
								case 'textarea': {
									input = document.createElement('textarea');
									input.type = 'text';
									input.className = 'mdl-textfield__input';
									input.innerHTML = previousContent;
								} break;

								case 'number': {
									input = document.createElement('input');
									input.type = 'number';
									input.setAttribute('min', 0);
									input.className = 'mdl-textfield__input';
									input.setAttribute('value', previousContent);
								} break;

								case 'price': {
									input = document.createElement('input');
									input.type = 'number';
									input.setAttribute('min', 0);
									input.setAttribute('step', 0.01);
									input.className = 'mdl-textfield__input';

									// Modifying value
									previousContent = parseFloat(previousContent.slice(0, -1).replace(',', '.'));

									input.setAttribute('value', previousContent);
								} break;

								// case 'link': {
								// 	var dummy = document.createElement('div');
								// 	dummy.innerHTML = previousContent;

								// 	// If no children, then it's a string (there is no link)
								// 	if(dummy.children.length == 0)
								// 		previousContent = dummy.innerHTML;
								// 	else
								// 		previousContent = dummy.firstChild.innerHTML;

								// 	input = document.createElement('input');
								// 	input.type = 'text';
								// 	input.className = 'mdl-textfield__input';
								// 	input.setAttribute('value', previousContent);
								// }

								default: {
									input = document.createElement('input');
									input.type = 'text';
									input.className = 'mdl-textfield__input';
									input.setAttribute('value', previousContent);
								} break;
							}

							this.innerHTML = '';
							this.append(input);
	
							// Focusing and add a behavior when clicking elsewhere
							input.focus();
							input.addEventListener('blur', function(e){
								var content = this.value;

								switch(inputType){
									case 'price': {
										content = parseFloat(content).formatNumber(2, ',', ' ') + '€';
									} break;

									default: {

									} break;
								}

								// // If we have a link, rebuilding it
								// if(inputType == 'link')
								// 	content = '<a href="' + content + '">' + content + '</a>';

								this.replaceWith(content);

								if(content != previousContent){
									console.log('Content modified !');
								}
							});

							// If enter is pressed
							input.addEventListener('keypress', function(e){
								if(e.keyCode == 13){
									this.blur(); // Releasing
								}
							});
						}
					});
				});
			}
			else {
				// Otherwise, just displaying a card saying there is no order yet
				var info = {
					title: 'Aucun jeu disponible',
					text: 'Aucun jeu n\'est disponible à la vente pour le moment. Revenez plus tard.<br />',
					action: 'Ajouter un jeu',
					actionLink: '',
					icon: ''
				};

				currentModule.__defaultContainer.append(currentModule.parent.getModule(Modules.Interface).__infoCard(info));				
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

			currentModule.__defaultContainer.append(currentModule.parent.getModule(Modules.Interface).__errorCard(e));

			console.error(err.error);
		});
	}
})();