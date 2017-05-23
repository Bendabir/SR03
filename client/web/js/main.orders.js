; main.addModule({}, 'orders');

(function(publics){
	'use strict';

	// Private members
	var privates = {};

	privates.__defaultContainer = null;

	// Build a <li> element based on the order line
	privates.__line = function(line){
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
		
		// template.content.querySelector('.order-line-game-title').textContent = line.game.title;
		// template.content.querySelector('.order-line-game-console').textContent = line.game.console;
		template.content.querySelector('.order-line-title').textContent = line.game.title + ' (' + line.game.console + ')';
		template.content.querySelector('.order-line-game-description').textContent = line.game.description;
		template.content.querySelector('.order-line-game-price').textContent = line.unitPrice.formatNumber(2, ',', ' ');
		template.content.querySelector('.order-line-game-quantity').textContent = line.quantity;

		return document.importNode(template.content, true);
	}

	// Build a card based on the order 
	privates.__card = function(order){
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
			ul.insertBefore(privates.__line(l), ul.firstChild);
		});	
		
		return document.importNode(template.content, true);
	}

	// Public members
	publics.init = function(){
		publics.setDefaultContainer('#orders .page-content .mdl-grid .mdl-cell .mdl-grid');

		// Linking the tab to the reload function
		document.querySelector('a[href="#orders"]').addEventListener('click', function(e){
			publics.reload();
		});

		publics.reload();

		console.log('Orders module initialized.');
	}

	// Set the default container when modifying content
	publics.setDefaultContainer = function(container){
		if(typeof container != 'string' || !container)
			throw new Error('The container attribute must be a string (CSS selector of the container).');

		privates.__defaultContainer = document.querySelector(container);
	}



	// Defining methods
	publics.get = function(num = '', onSuccess, onError){
		main.ajax({
			method: 'GET',
			url: main.__apiPath('orders' + (num == '' ? '' : '/') + num)
		}, function(obj){
			console.log(obj.response);
		}, function(err){
			console.error(err.error);
		});
	}

	// Clean the interface
	publics.clean = function(){
		privates.__defaultContainer.innerHTML = '';
	}

	// Reload the interface with all games
	publics.reload = function(){
		publics.clean();
		
		privates.__defaultContainer.append(main.__loader());

		main.ajax({
			method: 'GET',
			url: main.__apiPath('orders'),
			async: main.__async		
		}, function(obj){
			var orders = obj.response;
			
			publics.clean();

			// Building the interface
			orders.forEach(function(o){
				privates.__defaultContainer.append(privates.__card(o));
			});
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

			publics.clean();

			privates.__defaultContainer.append(main.__errorCard(e));

			console.error(err.error);
		});		
	}
})(main.orders);