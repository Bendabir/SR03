; var Modules = Modules || {};

(function(){
	'use stict';

	function __orderLine(line){
		// Checking the line
		if(typeof line != 'object' || !line)
			throw new Error('The line attribute must be an object.');

		if(typeof line.game != 'object' || !line.game)
			throw new Error('The line attribute must have a game element.');

		if(typeof line.game.publisher == 'undefined')
			line.game.publisher = 'Inconnu';

		if(typeof line.game.description == 'undefined')
			line.game.description = 'Pas de description.';

		// Building the card using a HTML5 template
		var template = document.querySelector('#order-line-template');

		template.content.querySelector('.order-line-title').textContent = line.game.title + ' (' + line.game.console + ')';
		template.content.querySelector('.order-line-game-description').textContent = line.game.description;
		template.content.querySelector('.order-line-game-price').textContent = line.unitPrice.formatNumber(2, ',', ' ');
		template.content.querySelector('.order-line-game-quantity').textContent = line.quantity;
		template.content.querySelector('.order-line-game-cover').setAttribute('src', typeof line.game.cover == 'undefined' ? '../img/no_cover.png' : line.game.cover);

		return document.importNode(template.content, true);
	}

	function __line(order){
		// Checking the order
		if(typeof order != 'object' || !order)
			throw new Error('The order attribute must be an object.');

		// Building the card using a HTML5 template
		var template = document.querySelector('#admin-orders-line');

		template.content.querySelector('.admin-orders-line-order-login').textContent = order.user;
		template.content.querySelector('.admin-orders-line-order-num').textContent = order.num;
		template.content.querySelector('.admin-orders-line-order-date').textContent = order.date.split('-').reverse().join('/');
		
		var ul = template.content.querySelector('.table-order-lines');
		ul.innerHTML = ''; // Cleaning previous elements

		// Loading games
		order.lines.forEach(function(l){
			ul.append(__orderLine(l));
		});

		var totalAmount = order.lines.map(function(l){
			return l.quantity * l.unitPrice;
		}).reduce(function(a, b){
			return a + b;
		});

		template.content.querySelector('.admin-orders-line-order-amount').textContent = totalAmount.formatNumber(2, ',', ' ') + '€';

		return document.importNode(template.content, true);
	}

	function __table(orders){
		// Checking the line
		if(!Array.isArray(orders) || !orders)
			throw new Error('The orders attribute must be an array.');

		// Building the card using a HTML5 template
		var template = document.querySelector('#admin-orders-table');

		var table = template.content.querySelector('tbody');

		// Cleaning previous lines
		table.innerHTML = '';

		orders.forEach(function(o){
			table.append(__line(o));
		});

		return document.importNode(template.content, true);	
	}


	Modules.Admin = Modules.Admin || {};


	Modules.Admin.Orders = function(){
		this.__moduleName = 'orders';
		this.__defaultContainer = null;
		this.__dependencies = [Modules.Interface]; // List of classes for dependencies
	}

	Modules.Admin.Orders.prototype.init = function(){
		this.setDefaultContainer('#orders-container');

		this.reload();

		// Linking the tab to the reload function
		document.querySelector('a[href="#orders"]').addEventListener('click', (e) => {
			this.reload();
		});

		var currentModule = this;

		// Linking the search bar
		document.querySelector('#search-user-orders').addEventListener('keypress', function(e){
			if(e.keyCode == 13){
				var login = this.value;

				if(login != ''){
					this.blur(); // Releasing

					// Loading interface for this user
					currentModule.clean();

					currentModule.__defaultContainer.append(currentModule.parent.getModule(Modules.Interface).__loader());

					currentModule.parent.ajax({
						method: 'GET',
						url: currentModule.parent.apiPath('orders/' + login)
					}, function(obj){
						var orders = obj.response;
						
						currentModule.clean();

						// Adding login to orders
						orders.forEach(function(o){
							o.user = login;
						});

						// If we have orders, then buiding the interface
						if(orders.length > 0){
							currentModule.__defaultContainer.append(__table(orders));
						}
						else {
							// Otherwise, just displaying a card saying there is no order yet
							var info = {
								title: 'Aucune commande',
								text: 'Aucune commande n\'a été effectuée par cet utilisateur pour le moment ou cet utilisateur n\'existe pas.<br />',
								action: 'Effacer',
								actionLink: '',
								icon: ''
							};

							var infoCard = currentModule.parent.getModule(Modules.Interface).__infoCard(info);
							infoCard.querySelector('.info-action').addEventListener('click', function(e){
								document.querySelector('#clean-search-button').click(); // Simulating click on clean button 
							});
							currentModule.__defaultContainer.append(infoCard);
						}			
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
			}
		});

		// Cleaning button
		document.querySelector('#clean-search-button').addEventListener('click', (e) => {
			var input = document.querySelector('#search-user-orders');
				
			input.value = null;
			input.blur();
			componentHandler.upgradeElement(input);

			// Reload interface
			this.reload();
		});

		console.log('Admin orders module initialized.');
	}

	// Set the default container when modifying content
	Modules.Admin.Orders.prototype.setDefaultContainer = function(container){
		if(typeof container != 'string' || !container)
			throw new Error('The container attribute must be a string (CSS selector of the container).');

		this.__defaultContainer = document.querySelector(container);
	}

	// Defining methods
	Modules.Admin.Orders.prototype.get = function(id = '', onSuccess, onError){
		this.parent.ajax({
			method: 'GET',
			url: this.parent.apiPath('orders' + (id == '' ? '' : '/') + id)
		}, function(obj){
			console.log(obj.response);
		}, function(err){
			console.error(err.error);
		});
	}

	// Clean the interface
	Modules.Admin.Orders.prototype.clean = function(){
		this.__defaultContainer.innerHTML = '';
	}

	// Reload the interface with all consoles
	Modules.Admin.Orders.prototype.reload = function(){
		this.clean();

		this.__defaultContainer.append(this.parent.getModule(Modules.Interface).__loader());

		var currentModule = this;

		this.parent.ajax({
			method: 'GET',
			url: this.parent.apiPath('orders/all')
		}, function(obj){
			var orders = obj.response;
			
			currentModule.clean();

			// If we have orders, then buiding the interface
			if(orders.length > 0){
				currentModule.__defaultContainer.append(__table(orders));
			}
			else {
				// Otherwise, just displaying a card saying there is no order yet
				var info = {
					title: 'Aucune commande',
					text: 'Aucune commande n\'a été effectuée pour le moment.<br />',
					action: 'Accueil',
					actionLink: '',
					icon: ''
				};

				currentModule.__defaultContainer.append(currentModule.parent.getModule(Modules.Interface).__infoCard(info));				
			}			

			// Updating the game number in the tabs
			document.querySelector('#orders-number').textContent = orders.length;
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