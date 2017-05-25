; var Modules = Modules || {};

(function(){
	'use strict';
	Modules.Interface = function(){
		this.__moduleName = 'interface';
		this.__dependencies = [];
	}

	Modules.Interface.prototype.init = function(){
		console.log('Interface module loaded.');
	}

	// Create an error card
	Modules.Interface.prototype.__errorCard = function(error){
		// Checking the error
		if(typeof error != 'object' || !error)
			throw new Error('The error attribute must be an object.');

		// Building the card using a HTML5 template
		var template = document.querySelector('#error-card-template');

		// Filling the template
		template.content.querySelector('.error-message').textContent = error.message + ' (' + error.code +')';
		template.content.querySelector('.error-more-information').textContent = error.moreInformation;
		template.content.querySelector('.error-action').textContent = error.action;
		template.content.querySelector('.mdl-card__menu .material-icons').textContent = error.icon;

		return document.importNode(template.content, true);
	}

	// Create an info card
	Modules.Interface.prototype.__infoCard = function(info){
		// Checking the error
		if(typeof info != 'object' || !info)
			throw new Error('The info attribute must be an object.');

		// Building the card using a HTML5 template
		var template = document.querySelector('#info-card-template');

		template.content.querySelector('.info-title').textContent = info.title;
		template.content.querySelector('.info-text').innerHTML = info.text + '<br /><br />';
		template.content.querySelector('.info-action').textContent = info.action;
		// template.content.querySelector('.info-action').setAttribute('href', info.actionLink);

		template.content.querySelector('.info-icon .material-icons').textContent = info.icon;

		return document.importNode(template.content, true);
	}	

	// Give a loader element
	Modules.Interface.prototype.__loader = function(){
		var loader = document.createElement('div');
		loader.className = 'mdl-progress mdl-js-progress mdl-progress__indeterminate';

		return loader;
	}	
})();