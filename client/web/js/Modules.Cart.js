; var Modules = Modules || {};

(function(){
	'use stict';

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
		}, function(obj){
			console.log(obj.response);
		}, function(err){
			console.error(err.error);
		});		
	}

	Modules.Cart.prototype.reload = function(){

	}
})();