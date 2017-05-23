; main.addModule({}, 'orders');

(function(publics){
	'use strict';

	// Private members
	var privates = {};

	privates.__defaultContainer = null;


	// Public members
	publics.init = function(){
		publics.setDefaultContainer('#orders .page-content .mdl-grid .mdl-cell .mdl-grid');

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
	}
})(main.orders);