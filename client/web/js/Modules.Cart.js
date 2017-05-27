; var Modules = Modules || {};

(function(){
	'use stict';

	// Build a <li> element based on the order line
	function __line(line){
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
		var template = document.querySelector('#cart-line-template');

		template.content.querySelector('li').setAttribute("game-id", line.game.id);
		template.content.querySelector('.cart-line-title').textContent = line.game.title + ' (' + line.game.console + ')';
		// template.content.querySelector('.cart-line-game-description').textContent = line.game.description;
		template.content.querySelector('.cart-line-game-price').textContent = line.game.price.formatNumber(2, ',', ' ') + '€';
		template.content.querySelector('.cart-line-game-quantity').textContent = line.quantity;
		template.content.querySelector('.cart-line-game-cover').setAttribute('src', typeof line.game.cover == 'undefined' ? './img/no_cover.png' : line.game.cover);

		return document.importNode(template.content, true);
	}

	// Build a card based on the cart 
	function __card(cart){
		// Checking the line
		if(!Array.isArray(cart) || !cart)
			throw new Error('The cart attribute must be an array.');

		// Computing total amount
		var cartAmount = cart.map(function(g){
			return g.game.price * g.quantity;
		}).reduce(function(a, b){
			return a + b;
		}, 0);

		// Building the card using a HTML5 template
		var template = document.querySelector('#cart-card-template');

		template.content.querySelector('.cart-card-order-amount').textContent = cartAmount.formatNumber(2, ',', ' ');

		var ul = template.content.querySelector('.cart-card-cart-lines');

		// Cleaning previous lines		
		while(ul.children.length > 2)
			ul.removeChild(ul.firstChild);

		// Appending lines (reversed)
		cart.reverse().forEach(function(l){
			ul.insertBefore(__line(l), ul.firstChild);
		});	
		
		return document.importNode(template.content, true);
	}

	Modules.Cart = function(){
		this.__moduleName = 'cart';
		this.__defaultContainer = null;
		this.__dependencies = [Modules.Interface];	
	}

	// Public members
	Modules.Cart.prototype.init = function(){
		this.setDefaultContainer('#cart .page-content .mdl-grid .mdl-cell .mdl-grid');

		// Linking the tab to the reload function
		document.querySelector('a[href="#cart"]').addEventListener('click', e => this.reload());

		this.reload();

		console.log('Cart module initialized.');
	}

	// Set the default container when modifying content
	Modules.Cart.prototype.setDefaultContainer = function(container){
		if(typeof container != 'string' || !container)
			throw new Error('The container attribute must be a string (CSS selector of the container).');

		this.__defaultContainer = document.querySelector(container);
	}

	// Defining methods
	Modules.Cart.prototype.get = function(num = '', onSuccess, onError){
		this.parent.ajax({
			method: 'GET',
			url: this.parent.apiPath('cart' + (num == '' ? '' : '/') + num)
		}, function(obj){
			console.log(obj.response);
		}, function(err){
			console.error(err.error);
		});
	}

	// Validate an order
	Modules.Cart.prototype.validate = function(){
		this.parent.ajax({
			method: 'GET',
			url: this.parent.apiPath('cart/validate')
		}, (obj) => {
			console.log(obj.response);
			
			this.clean();

			// Displaying an info card
			// Otherwise, just displaying a card saying there is no order yet
			var info = {
				title: 'Commande effectuée !',
				text: 'Votre commande a bien été effectuée. <br />Pour consulter l\'historique de vos commandes, rendez-vous dans l\'onglet <b>Commandes</b> ou cliquez sur le bouton ci-dessous.',
				action: 'Mes commandes',
				actionLink: '#orders',
				actionSimulateTabClick: true,	
				icon: ''
			};

			this.__defaultContainer.append(this.parent.getModule(Modules.Interface).__infoCard(info));			
		}, (err) => {
			console.error(err.error);
			this.reload();
		});
	}

	Modules.Cart.prototype.post = function(gameID, quantity){
		this.parent.ajax({
			method: 'POST',
			url: this.parent.apiPath('cart'),
			headers: {
				'Content-Type': 'application/json'
			},
			data: {
				game: gameID,
				quantity: quantity
			}
		}, function(obj){
			console.log(obj.response);
		}, function(err){
			console.error(err.error);
		});
	}

	Modules.Cart.prototype.clear = function(){
		this.parent.ajax({
			method: 'DELETE',
			url: this.parent.apiPath('cart')
		}, (obj) => {
			console.log(obj.response);
			this.reload();		
		}, (err) => {
			console.error(err.error);
			this.reload();		
		});		
	}

	Modules.Cart.prototype.delete = function(index){
		this.parent.ajax({
			method: 'DELETE',
			url: this.parent.apiPath('cart/' + index)
		}, (obj) => {
			console.log(obj.response);
			this.reload();		
		}, (err) => {
			console.error(err.error);
			this.reload();		
		});	
	}

	Modules.Cart.prototype.clean = function(){
		this.__defaultContainer.innerHTML = '';
	}

	Modules.Cart.prototype.reload = function(){
		this.clean();

		this.__defaultContainer.append(this.parent.getModule(Modules.Interface).__loader());

		var currentModule = this;

		this.parent.ajax({
			method: 'GET',
			url: this.parent.apiPath('cart'),
			async: this.parent.__async		
		}, function(obj){
			var cart = obj.response;
			
			currentModule.clean();

			// If we have cart, then buiding the interface
			if(cart.length){
				// Building the interface
				currentModule.__defaultContainer.append(__card(cart));

				// Linking the interface
				document.querySelector('.cart-card-clear-cart').addEventListener('click', function(e){
					currentModule.clear();
				});

				document.querySelector('.cart-card-validate-cart').addEventListener('click', function(e){
					currentModule.validate();
				});

				// Linking increase and decrease buttons
				currentModule.__defaultContainer.querySelectorAll('li.game-line').forEach(function(gameLine, index){
					// var gameID = parseInt(gameLine.getAttribute('game-id'));

					// For each button
					gameLine.querySelectorAll('.cart-line-game-modify-quantity').forEach(function(button){
						var modifierType = button.getAttribute("modifier-type");
	
						// Preparing data for update
						var data = {
							quantity: parseInt(gameLine.querySelector('.cart-line-game-quantity').innerHTML)
						};

						switch(modifierType){
							case 'increase': {
								data.quantity++;
							} break;

							case 'decrease': {
								data.quantity--;
							} break;

							default: {} break;
						}

						button.addEventListener('click', function(event){
							if(data.quantity == 0){
								// Delete from cart
								currentModule.delete(index);
							}
							else {
								currentModule.parent.ajax({
									method: 'PUT',
									url: currentModule.parent.apiPath('cart/' + index),
									headers: {
										'Content-Type': 'application/json'
									},
									data: data
								}, function(obj){
									currentModule.reload();
									console.log(obj.response);
								}, function(err){
									currentModule.reload();
									console.log(err.error);
								});
							}
						});
					});
				});
			}
			else {
				// Otherwise, just displaying a card saying there is no order yet
				var info = {
					title: 'Aucun article dans le panier',
					text: 'Vous n\'avez pas ajouté de jeux au panier pour le moment. <br />Une fois des jeux ajouté au panier, ils apparaîtront dans cet onglet.',
					action: 'Accéder à la liste des jeux',
					actionLink: '#games',
					actionSimulateTabClick: true,
					icon: ''
				};

				currentModule.__defaultContainer.append(currentModule.parent.getModule(Modules.Interface).__infoCard(info));
			}

			// Updating the game number in the tabs
			document.querySelector('#products-in-cart-number').textContent = cart.length;			
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
				case 401: {
					e.moreInformation = 'Tentez de vous déconnecter et de vous reconnecter pour résoudre le problème.';
					e.action = 'Se reconnecter';
				} break;
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