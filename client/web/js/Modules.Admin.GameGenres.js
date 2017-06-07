; var Modules = Modules || {};

(function(){
	'use stict';

	function __line(genre){
		// Checking the genre
		if(typeof genre != 'object' || !genre)
			throw new Error('The genre attribute must be an object.');

		if(typeof genre.description == 'undefined')
			genre.description = 'Pas de description.';

		// Building the card using a HTML5 template
		var template = document.querySelector('#admin-game-genres-line');

		template.content.querySelector('.admin-game-genres-line-genre-name').textContent = genre.name;
		template.content.querySelector('.admin-game-genres-line-genre-description').textContent = genre.description;

		return document.importNode(template.content, true);
	}

	function __table(genres){
		// Checking the line
		if(!Array.isArray(genres) || !genres)
			throw new Error('The genres attribute must be an array.');

		// Building the card using a HTML5 template
		var template = document.querySelector('#admin-game-genres-table');

		var table = template.content.querySelector('tbody');

		// Cleaning previous lines
		table.innerHTML = '';

		genres.forEach(function(g){
			table.append(__line(g));
		});

		return document.importNode(template.content, true);	
	}


	Modules.Admin = Modules.Admin || {};


	Modules.Admin.GameGenres = function(){
		this.__moduleName = 'gameGenres';
		this.__defaultContainer = null;
		this.__dependencies = [Modules.Interface]; // List of classes for dependencies
	}

	Modules.Admin.GameGenres.prototype.init = function(){
		this.setDefaultContainer('#genres .page-content .mdl-grid .mdl-cell');

		this.reload();

		// Linking the tab to the reload function
		document.querySelector('a[href="#genres"]').addEventListener('click', (e) => {
			this.reload();
		});

		// Binding click event to show the dialog window
		var dialog = document.querySelector('#add-game-genre-dialog'),
			showDialogButton = document.querySelector('#add-game-genre');

		// Need to implement a polyfill
		if(!dialog.showModal) {
			dialogPolyfill.registerDialog(dialog);
		}

		// On click on the button, show the dialog
		showDialogButton.addEventListener('click', function(e){
			dialog.showModal();
		});

		// When clicking on close button
		dialog.querySelector('.close').addEventListener('click', function(e){
			dialog.close();
		});

		dialog.querySelector('.add').addEventListener('click', (e) => {
			// Building the console
			var genre = {
				name: dialog.querySelector('#dialog-game-genre-name').value.substr(0, 255) || null,
				description: dialog.querySelector('#dialog-game-genre-description').value || null,
			};

			// Checking required fields 
			if(genre.name != null){
				// Sending data
				this.parent.ajax({
					method: 'POST',
					url: this.parent.apiPath('gameGenres'),
					headers: {
						'Content-Type': 'application/json'
					},
					data: genre
				}, (obj) => {
					console.log(obj.response);

					var response = obj.response;

					if(typeof response.name != 'undefined'){
						this.reload();
						dialog.close();
					}
				}, (err) => {
					console.log(err.error);
				});
			}
		});

		console.log('Admin game genres module initialized.');
	}

	// Set the default container when modifying content
	Modules.Admin.GameGenres.prototype.setDefaultContainer = function(container){
		if(typeof container != 'string' || !container)
			throw new Error('The container attribute must be a string (CSS selector of the container).');

		this.__defaultContainer = document.querySelector(container);
	}

	// Defining methods
	Modules.Admin.GameGenres.prototype.get = function(id = '', onSuccess, onError){
		this.parent.ajax({
			method: 'GET',
			url: this.parent.apiPath('gameGenres' + (id == '' ? '' : '/') + id)
		}, function(obj){
			console.log(obj.response);
		}, function(err){
			console.error(err.error);
		});
	}

	// Clean the interface
	Modules.Admin.GameGenres.prototype.clean = function(){
		this.__defaultContainer.innerHTML = '';
	}

	// Reload the interface with all consoles
	Modules.Admin.GameGenres.prototype.reload = function(){
		this.clean();

		this.__defaultContainer.append(this.parent.getModule(Modules.Interface).__loader());

		var currentModule = this;

		this.parent.ajax({
			method: 'GET',
			url: this.parent.apiPath('gameGenres')
		}, function(obj){
			var genres = obj.response;
			
			currentModule.clean();

			// If we have orders, then buiding the interface
			if(genres.length > 0){
				currentModule.__defaultContainer.append(__table(genres));

				// Say MDL to upgrade the element
				// componentHandler.upgradeElement(document.querySelector('table'));

				// Modifiers
				document.querySelectorAll('table tbody tr td').forEach(function(td){
					td.addEventListener('click', function(e){
						// If not already displayed
						if(this.children.length == 0){
							// Saving previous value
							var previousContent = this.textContent,
								inputType = this.getAttribute('input-type');

							// Append a text input
							var input = null;

							switch(inputType){
								case 'textarea': {
									input = document.createElement('textarea');
									input.type = 'text';
									input.className = 'mdl-textfield__input';
									input.innerHTML = previousContent;
								} break;

								default: {
									input = document.createElement('input');
									input.type = 'text';
									input.className = 'mdl-textfield__input';
									input.setAttribute('value', previousContent);
								} break;
							}

							this.innerHTML = '';
							this.append(input);
	
							// Focusing and add a behavior when clicking elsewhere
							input.focus();
							input.addEventListener('blur', function(e){
								var content = this.value;

								this.replaceWith(content);

								if(content != previousContent){
									// Get line to update on database
									var parent = td.parentElement;

									// Building data
									var genre = {
										name: parent.querySelector('.admin-game-genres-line-genre-name').textContent.substr(0, 64),
										description: parent.querySelector('.admin-game-genres-line-genre-description').textContent,
									};

									// Updating the genre
									currentModule.parent.ajax({
										method: 'PUT',
										url: currentModule.parent.apiPath('gameGenres/' + genre.name),
										headers: {
											'Content-Type': 'application/json'
										},
										data: genre										
									}, function(obj){
										console.log(obj.response);

										// If update failed, reload
										if(obj.response == null)
											currentModule.reload();
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
							});

							// If enter is pressed
							input.addEventListener('keypress', function(e){
								if(e.keyCode == 13){
									this.blur(); // Releasing
								}
							});
						}
					});
				});
			}
			else {
				// Otherwise, just displaying a card saying there is no order yet
				var info = {
					title: 'Aucune genre disponible',
					text: 'Aucune genre de jeu n\'a été ajouté pour le moment. Ajoutez en !<br />',
					action: 'Ajouter un genre',
					actionLink: '',
					icon: ''
				};

				currentModule.__defaultContainer.append(currentModule.parent.getModule(Modules.Interface).__infoCard(info));				
			}			

			// Updating the game number in the tabs
			document.querySelector('#genres-number').textContent = genres.length;
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