;var main = {};

(function(publics){
	'use strict';

	// Private members
	var privates = {};

	privates.__hostName = document.location.origin + ':8080';
	privates.__pathName = '/server';

	privates.__modules = {}; // Different loaded modules


	// Public members
	// Add a module to the admin object
	publics.addModule = function(module, moduleName){
		if(module === this)
			throw new Error('Cannot register the admin object in the admin object.');

		if(typeof moduleName != 'string' || typeof module != 'object' || !module)
			throw new Error('The module name must be a string. The module must be an object.');

		// If module not added yet
		if(!(moduleName in privates.__modules)){
			module.parent = publics; // Save a reference on the parent
			privates.__modules[moduleName] = module;
			publics[moduleName] = module; // Shortcut for module access
		}
		
		return privates.__modules[moduleName];
	}

	// Remove a module from the handler.
	publics.removeModule = function(name){
		if(name in privates.__modules){
			delete privates.__modules[name];
			delete publics[name];
			return true;
		}

		return false;
	}	

	publics.ajax = function(params, onSuccess, onError){
		if(typeof params == 'undefined'){
			throw new Error('Parameters are required.');
		}

		if(typeof params.url == 'undefined'){
			throw new Error('URL is required.');
		}

		if(typeof params.method == 'undefined'){
			throw new Error('Method is required.');
		}

		if(typeof params.async == 'undefined'){
			params.async = true;
		}

		// Update url with session id
		if(this.sessionid){
			params.url += ';jsessionid=' + this.sessionid;
		}

		var xhr = new XMLHttpRequest();

		xhr.addEventListener('readystatechange', function(e){
			if(xhr.readyState == 4){
				if(xhr.status >= 200 && xhr.status < 300){
					if(typeof onSuccess != 'undefined')
						onSuccess({
							error: null,
							response: JSON.parse(xhr.responseText),
							status: xhr.status,
							xhr: xhr
						});
				}
				else {
					if(typeof onError != 'undefined')
						onError({
							error: {
								message: xhr.statusText,
								state: xhr.readyState,
								status: xhr.status
							},
							response: JSON.parse(xhr.responseText),
							status: xhr.status,
							xhr: xhr
						});
				}
			}
		});

		if(['GET', 'DELETE'].indexOf(params.method) != -1){
			if(typeof params.data != 'undefined'){
				var url = (params.url.substring(params.url.length - 1, params.url.length) == '/')? '?' : '/?';

				for(var key in params.data){
					url += key + '=' + params.data[key] + '&';
				}

				url = url.substring(0, url.length - 1);
				params.url += url;
			}

			xhr.open(params.method, params.url, params.async);
			xhr.setRequestHeaders(params.headers || {});
			xhr.send();
		}
		else if(['POST', 'PUT'].indexOf(params.method) != -1){
			xhr.open(params.method, params.url, params.async);
			xhr.setRequestHeaders(params.headers || {});

			if(typeof params.data != 'undefined'){
				if(params.headers && params.headers['Content-Type'] && params.headers['Content-Type'] == 'application/json'){
					xhr.send(JSON.stringify(params.data));
				} else {
					xhr.send(params.data);
				}
			}
			else {
				xhr.send();
			}
		}
		else {
			throw new Error('This method is not supported.');
		}
	}

	publics.init = function(){
		// Loading session ID
		this.ajax({
			method: 'GET',
			url: './jsessionid.php'
		}, function(obj){
			publics.sessionid = obj.response.id;
		}, function(obj){
			publics.sessionid = null;
		});

		// Init all modules (if init function exists)
		for(var m in privates.__modules){
			var module = privates.__modules[m];

			if(module.init)
				module.init();
		}

		console.log('Main mandler initialized.');
	}

	publics.apiPath = function(resource = ''){return privates.__hostName + privates.__pathName + '/api/' + resource;}
	publics.loginPath = privates.__hostName + privates.__pathName + '/login';
	publics.logoutPath = privates.__hostName + privates.__pathName + '/logout';	
})(main);