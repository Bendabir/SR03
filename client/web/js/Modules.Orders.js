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
		var template = document.querySelector('#order-line-template');

		template.content.querySelector('.order-line-title').textContent = line.game.title + ' (' + line.game.console + ')';
		template.content.querySelector('.order-line-game-description').textContent = line.game.description;
		template.content.querySelector('.order-line-game-price').textContent = line.unitPrice.formatNumber(2, ',', ' ');
		template.content.querySelector('.order-line-game-quantity').textContent = line.quantity;
		template.content.querySelector('.order-line-game-cover').setAttribute('src', typeof line.game.cover == 'undefined' ? './img/no_cover.png' : line.game.cover);

		return document.importNode(template.content, true);
	}

	// Build a card based on the order 
	function __card(order){
		// Checking the line
		if(typeof order != 'object' || !order)
			throw new Error('The order attribute must be an object.');

		if(!Array.isArray(order.lines) || !order.lines)
			throw new Error('The order attribute must have an array of order lines.');

		// Computing total amount
		var orderAmount = order.lines.map(function(l){
			return l.unitPrice * l.quantity;
		}).reduce(function(a, b){
			return a + b;
		}, 0);

		// Building the card using a HTML5 template
		var template = document.querySelector('#order-card-template');

		// template.content.querySelector('.order-card-order-num').textContent = order.num;
		// template.content.querySelector('.order-card-order-date').textContent = (new Date(order.date)).toLocaleDateString();
		template.content.querySelector('.order-card-title').innerHTML = 'Commande n°' + order.num + '<br />Effectuée le ' + (new Date(order.date)).toLocaleDateString();
		template.content.querySelectorAll('.order-card-order-amount').forEach(function(e){
			e.textContent = orderAmount.formatNumber(2, ',', ' ');
		});

		var ul = template.content.querySelector('.order-card-order-lines');

		// Cleaning previous lines		
		while(ul.children.length > 2)
			ul.removeChild(ul.firstChild);

		// Appending lines (reversed)
		order.lines.reverse().forEach(function(l){
			ul.insertBefore(__line(l), ul.firstChild);
		});	
		
		return document.importNode(template.content, true);
	}

	Modules.Orders = function(){
		this.__moduleName = 'orders';
		this.__defaultContainer = null;
		this.__dependencies = [Modules.Interface];	
	}

	// Public members
	Modules.Orders.prototype.init = function(){
		this.setDefaultContainer('#orders .page-content .mdl-grid .mdl-cell .mdl-grid');

		// Linking the tab to the reload function
		document.querySelector('a[href="#orders"]').addEventListener('click', e => this.reload());

		this.reload();

		console.log('Orders module initialized.');
	}

	// Set the default container when modifying content
	Modules.Orders.prototype.setDefaultContainer = function(container){
		if(typeof container != 'string' || !container)
			throw new Error('The container attribute must be a string (CSS selector of the container).');

		this.__defaultContainer = document.querySelector(container);
	}

	// Defining methods
	Modules.Orders.prototype.get = function(num = '', onSuccess, onError){
		this.parent.ajax({
			method: 'GET',
			url: this.parent.apiPath('orders' + (num == '' ? '' : '/') + num)
		}, function(obj){
			console.log(obj.response);
		}, function(err){
			console.error(err.error);
		});
	}

	// Clean the interface
	Modules.Orders.prototype.clean = function(){
		this.__defaultContainer.innerHTML = '';
	}

	// Reload the interface with all games
	Modules.Orders.prototype.reload = function(){
		this.clean();

		this.__defaultContainer.append(this.parent.getModule(Modules.Interface).__loader());

		var currentModule = this;

		this.parent.ajax({
			method: 'GET',
			url: this.parent.apiPath('orders'),
			async: this.parent.__async		
		}, function(obj){
			var orders = obj.response;
			
			currentModule.clean();

			// If we have orders, then buiding the interface
			if(orders.length){
				// Building the interface
				orders.forEach(function(o){
					currentModule.__defaultContainer.append(__card(o));
				});
			}
			else {
				// Otherwise, just displaying a card saying there is no order yet
				var info = {
					title: 'Aucune commande',
					text: 'Vous n\'avez passé aucune commande pour le moment. <br />Une fois des commandes effectuées, elle apparaîtront dans cet onglet.',
					action: 'Accéder à mon panier',
					actionLink: '#cart',
					actionSimulateTabClick: true,
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