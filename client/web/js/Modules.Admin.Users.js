; var Modules = Modules || {};

(function(){
	'use stict';

	function __line(user){
		// Checking the user
		if(typeof user != 'object' || !user)
			throw new Error('The user attribute must be an object.');

		// Building the card using a HTML5 template
		var template = document.querySelector('#admin-users-line');

		template.content.querySelector('.admin-users-line-user-login').textContent = user.username;
		template.content.querySelector('.admin-users-line-user-firstname').textContent = user.firstName;
		template.content.querySelector('.admin-users-line-user-lastname').textContent = user.lastName;
		template.content.querySelector('.admin-users-line-user-status').textContent = user.status;

		return document.importNode(template.content, true);
	}

	function __table(users){
		// Checking the line
		if(!Array.isArray(users) || !users)
			throw new Error('The users attribute must be an array.');

		// Building the card using a HTML5 template
		var template = document.querySelector('#admin-users-table');

		var table = template.content.querySelector('tbody');

		// Cleaning previous lines
		table.innerHTML = '';

		users.forEach(function(u){
			table.append(__line(u));
		});

		return document.importNode(template.content, true);	
	}


	Modules.Admin = Modules.Admin || {};


	Modules.Admin.Users = function(){
		this.__moduleName = 'users';
		this.__defaultContainer = null;
		this.__dependencies = [Modules.Interface]; // List of classes for dependencies
	}

	Modules.Admin.Users.prototype.init = function(){
		this.setDefaultContainer('#users .page-content .mdl-grid .mdl-cell');

		this.reload();

		// Linking the tab to the reload function
		document.querySelector('a[href="#users"]').addEventListener('click', (e) => {
			this.reload();
		});

		console.log('Admin users module initialized.');
	}

	// Set the default container when modifying content
	Modules.Admin.Users.prototype.setDefaultContainer = function(container){
		if(typeof container != 'string' || !container)
			throw new Error('The container attribute must be a string (CSS selector of the container).');

		this.__defaultContainer = document.querySelector(container);
	}

	// Defining methods
	Modules.Admin.Users.prototype.get = function(id = '', onSuccess, onError){
		this.parent.ajax({
			method: 'GET',
			url: this.parent.apiPath('users' + (id == '' ? '' : '/') + id)
		}, function(obj){
			console.log(obj.response);
		}, function(err){
			console.error(err.error);
		});
	}

	// Clean the interface
	Modules.Admin.Users.prototype.clean = function(){
		this.__defaultContainer.innerHTML = '';
	}

	// Reload the interface with all consoles
	Modules.Admin.Users.prototype.reload = function(){
		this.clean();

		this.__defaultContainer.append(this.parent.getModule(Modules.Interface).__loader());

		var currentModule = this;

		this.parent.ajax({
			method: 'GET',
			url: this.parent.apiPath('users')
		}, function(obj){
			var users = obj.response;
			
			currentModule.clean();

			// If we have orders, then buiding the interface
			if(users.length > 0){
				currentModule.__defaultContainer.append(__table(users));
			}
			else {
				// Otherwise, just displaying a card saying there is no order yet
				var info = {
					title: 'Aucune utilisateur incrit',
					text: 'Aucun utilisateur ne s\'est inscrit sur le site pour le moment.<br />',
					action: 'Accueil',
					actionLink: '',
					icon: ''
				};

				currentModule.__defaultContainer.append(currentModule.parent.getModule(Modules.Interface).__infoCard(info));				
			}			

			// Updating the game number in the tabs
			document.querySelector('#users-number').textContent = users.length;
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