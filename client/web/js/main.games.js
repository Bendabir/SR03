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

	privates.__card = function(game){
		// Checking the game
		if(typeof game != 'object' || !game)
			throw new Error('The game attribute must be an object.');

		if(typeof game.publisher == 'undefined')
			game.publisher = 'Inconnu';

		if(typeof game.description == 'undefined')
			game.description = 'Pas de description.';

		// Building the card
		var cardRoot = document.createElement('div');



		return cardRoot; // Give the card
	}

	// Public members
	publics.init = function(){
		console.log('Games module initialized.');
	}

	// Set the default container when modifying content
	publics.setDefaultContainer = function(container){
		if(typeof container != 'string' || !container)
			throw new Error('The container attribute must be a string (id of the container).');

		privates.__defaultContainer = document.querySelector('#' + container + ' .page-content');
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