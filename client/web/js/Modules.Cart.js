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
		
		template.content.querySelector('.cart-line-title').textContent = line.game.title + ' (' + line.game.console + ')';
		template.content.querySelector('.cart-line-game-description').textContent = line.game.description;
		template.content.querySelector('.cart-line-game-price').textContent = line.game.price.formatNumber(2, ',', ' ');
		template.content.querySelector('.cart-line-game-quantity').textContent = line.quantity;

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
			url: this.parent.apiPath('cart'),
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
		
		if(typeof this.parent.interface == 'undefined')
			throw new Error('This module needs the Interface module in order to run properly.');

		this.__defaultContainer.append(this.parent.interface.__loader());

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
			}
			else {
				// Otherwise, just displaying a card saying there is no order yet
				var info = {
					title: 'Aucun article dans le panier',
					text: 'Vous n\'avez pas ajouté de jeux au panier pour le moment. <br />Une fois des jeux ajouté au panier, ils apparaîtront dans cet onglet.',
					action: 'Accéder à la liste des jeux',
					actionLink: '#games',
					icon: ''
				};

				currentModule.__defaultContainer.append(currentModule.parent.interface.__infoCard(info));
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

			currentModule.__defaultContainer.append(currentModule.parent.interface.__errorCard(e));

			console.error(err.error);
		});		
	}
})();