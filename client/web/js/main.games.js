; main.addModule({}, 'games');

(function(publics){
	'use strict';

	// Private members
	var privates = {};

	privates.__defaultContainer = null;

	// Transform an array into a string, 
	privates.__stringifyGenres = function(genres){
		if(!Array.isArray(genres))
			throw new Error('The genre attribute must be an array.');

		return (genres.length == 0 ? 'Inconnu' : genres.join(' | '));
	}

	// Build a card and return the DOM object
	privates.__card = function(game){
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
		template.content.querySelector('.game-card-release-date').textContent = ' ' + game.releaseDate;
		template.content.querySelector('.game-card-genre').textContent = ' ' + privates.__stringifyGenres(game.genres);
		template.content.querySelector('.game-card-price').textContent = ' ' + game.price;
		template.content.querySelector('.game-card-add-to-cart-button').textContent = (game.stock > 0 ? 'Ajouter au panier' : 'En rupture de stock');
		template.content.querySelector('.game-card-add-to-cart-button').setAttribute('game-id', game.id);

		if(game.stock == 0)
			template.content.querySelector('.game-card-add-to-cart-button').setAttribute('disabled', true);

		return document.importNode(template.content, true);
	}

	// Public members
	publics.init = function(){
		console.log('Games module initialized.');
	}

	// Set the default container when modifying content
	publics.setDefaultContainer = function(container){
		if(typeof container != 'string' || !container)
			throw new Error('The container attribute must be a string (id of the container).');

		privates.__defaultContainer = document.querySelector('#' + container + ' .page-content .mdl-grid .mdl-cell .mdl-grid');
	}



	// Defining methods
	publics.get = function(id = '', onSuccess, onError){
		main.ajax({
			method: 'GET',
			url: main.apiPath('games' + (id == '' ? '' : '/') + id)
		}, function(obj){
			console.log(obj.response);
		}, function(err){
			console.error(err.error);
		});
	}

	publics.post = function(onSuccess, onError){

	}

	publics.put = function(id, onSuccess, onError){

	}

	publics.delete = function(id, onSuccess, onError){
		main.ajax({
			method: 'DELETE',
			url: main.apiPath('games/' + id)
		}, function(obj){
			console.log(obj.response);
		}, function(err){
			console.error(err.error);
		});
	}
})(main.games);