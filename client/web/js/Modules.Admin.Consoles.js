; var Modules = Modules || {};

(function(){
	'use stict';

	function __line(console){
		// Checking the console
		if(typeof console != 'object' || !console)
			throw new Error('The console attribute must be an object.');

		var maxLength = 25;

		// Building the card using a HTML5 template
		var template = document.querySelector('#admin-consoles-line');

		template.content.querySelector('.admin-consoles-line-console-name').textContent = console.name;
		template.content.querySelector('.admin-consoles-line-console-launch-date').textContent = console.launchedDate.split('-').reverse().join('/');

		return document.importNode(template.content, true);
	}

	function __table(consoles){
		// Checking the line
		if(!Array.isArray(consoles) || !consoles)
			throw new Error('The consoles attribute must be an array.');

		// Building the card using a HTML5 template
		var template = document.querySelector('#admin-consoles-table');

		var table = template.content.querySelector('tbody');

		// Cleaning previous lines
		table.innerHTML = '';

		consoles.forEach(function(c){
			table.append(__line(c));
		});

		return document.importNode(template.content, true);	
	}


	Modules.Admin = Modules.Admin || {};


	Modules.Admin.Consoles = function(){
		this.__moduleName = 'consoles';
		this.__defaultContainer = null;
		this.__dependencies = [Modules.Interface]; // List of classes for dependencies
	}

	Modules.Admin.Consoles.prototype.init = function(){
		this.setDefaultContainer('#consoles .page-content .mdl-grid .mdl-cell');

		this.reload();

		// Linking the tab to the reload function
		document.querySelector('a[href="#consoles"]').addEventListener('click', (e) => {
			this.reload();
		});

		// Binding click event to show the dialog window
		var dialog = document.querySelector('#add-console-dialog'),
			showDialogButton = document.querySelector('#add-console');

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
			var consoleData = {
				name: dialog.querySelector('#dialog-console-name').value.substr(0, 255) || null,
				launchedDate: dialog.querySelector('#dialog-console-launch-date').value.split('/').reverse().join('-') || null,
			};

			// Checking required fields 
			if(consoleData.name != null && consoleData.launchedDate != null){
				// Sending data
				this.parent.ajax({
					method: 'POST',
					url: this.parent.apiPath('consoles'),
					headers: {
						'Content-Type': 'application/json'
					},
					data: consoleData
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

		console.log('Admin consoles module initialized.');
	}

	// Set the default container when modifying content
	Modules.Admin.Consoles.prototype.setDefaultContainer = function(container){
		if(typeof container != 'string' || !container)
			throw new Error('The container attribute must be a string (CSS selector of the container).');

		this.__defaultContainer = document.querySelector(container);
	}

	// Defining methods
	Modules.Admin.Consoles.prototype.get = function(id = '', onSuccess, onError){
		this.parent.ajax({
			method: 'GET',
			url: this.parent.apiPath('consoles' + (id == '' ? '' : '/') + id)
		}, function(obj){
			console.log(obj.response);
		}, function(err){
			console.error(err.error);
		});
	}

	// Clean the interface
	Modules.Admin.Consoles.prototype.clean = function(){
		this.__defaultContainer.innerHTML = '';
	}

	// Reload the interface with all consoles
	Modules.Admin.Consoles.prototype.reload = function(){
		this.clean();

		this.__defaultContainer.append(this.parent.getModule(Modules.Interface).__loader());

		var currentModule = this;

		this.parent.ajax({
			method: 'GET',
			url: this.parent.apiPath('consoles')
		}, function(obj){
			var consoles = obj.response;
			
			currentModule.clean();

			// If we have orders, then buiding the interface
			if(consoles.length > 0){
				currentModule.__defaultContainer.append(__table(consoles));

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
								case 'date': {
									input = document.createElement('input');
									input.type = 'date';
									input.className = 'mdl-textfield__input';
									input.setAttribute('min', '1950-01-01');
									input.setAttribute('value', previousContent.split('/').reverse().join('-'));
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

								switch(inputType){
									case 'date' : {
										content = content.split('-').reverse().join('/');
									}

									default: {

									} break;
								}

								this.replaceWith(content);

								if(content != previousContent){
									// Get line to update on database
									var parent = td.parentElement;

									// Building data
									var consoleData = {
										name: parent.querySelector('.admin-consoles-line-console-name').textContent.substr(0, 32),
										launchedDate: parent.querySelector('.admin-consoles-line-console-launch-date').textContent.split('/').reverse().join('-'),
									};

									// Updating the console
									currentModule.parent.ajax({
										method: 'PUT',
										url: currentModule.parent.apiPath('consoles/' + consoleData.name),
										headers: {
											'Content-Type': 'application/json'
										},
										data: consoleData										
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
					title: 'Aucune console disponible',
					text: 'Aucune console n\'a été ajouté pour le moment. Ajoutez en !<br />',
					action: 'Ajouter une console',
					actionLink: '',
					icon: ''
				};

				currentModule.__defaultContainer.append(currentModule.parent.getModule(Modules.Interface).__infoCard(info));				
			}			

			// Updating the game number in the tabs
			document.querySelector('#consoles-number').textContent = consoles.length;
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