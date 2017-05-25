// ; var main = {};

; var Handler = (function(){
	// Helpers 
	Number.prototype.formatNumber = function(c, d, t){
		var n = this, 
			c = isNaN(c = Math.abs(c)) ? 2 : c, 
			d = d == undefined ? "." : d, 
			t = t == undefined ? "," : t, 
			s = n < 0 ? "-" : "", 
			i = String(parseInt(n = Math.abs(Number(n) || 0).toFixed(c))), 
			j = (j = i.length) > 3 ? j % 3 : 0;

		return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
	};

	XMLHttpRequest.prototype.setRequestHeaders = function _setRequestHeaders(rh, defaultCT){
		if(!defaultCT) 
			defaultCT = 'application/json';

		for(var header in rh){
			this.setRequestHeader(header, rh[header]);
		}
	}

	// Application
	var Handler = {};

	// Constructor
	Handler.app = function(async = true){
		this.__port = 8080;
		this.__hostName = document.location.origin + ':' + this.__port;
		this.__pathName = '/server';
		this.__loginPath = this.__hostName + this.__pathName + '/login';
		this.__logoutPath = this.__hostName + this.__pathName + '/logout';
		this.__async = async; // For testing since the server is crap
		this.__modules = {};
		this.__sessionid = null;
	}

	// Add a module to the admin object
	Handler.app.prototype.addModule = function(module, moduleName){
		if(module === this)
			throw new Error('Cannot register the handler object in the handler object.');

		if(typeof moduleName != 'string' || typeof module != 'object' || !module)
			throw new Error('The module name must be a string. The module must be an object.');

		// If module not added yet
		if(!(moduleName in this.__modules)){
			module.parent = this; // Save a reference on the parent
			this.__modules[moduleName] = module;
			this[moduleName] = module; // Shortcut for module access
		}
		
		// return this.__modules[moduleName];
		return this;
	}

	// Remove a module from the handler.
	Handler.app.prototype.removeModule = function(name){
		if(!(name in privates.__modules))
			throw new Error('This module is not registered and cannot be deleted.');

		delete privates.__modules[name];
		delete publics[name];
		return this;
	}	

	Handler.app.prototype.ajax = function(params, onSuccess, onError){
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
		if(this.__sessionid){
			params.url += ';jsessionid=' + this.__sessionid;
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

	Handler.app.prototype.updateSessionId = function(){
		var handler = this;

		// Loading session ID
		this.ajax({
			method: 'GET',
			url: './jsessionid.php',
			async: false // Dirty but otherwise, some other functions run without having the token...
		}, function(obj){
			handler.__sessionid = obj.response.id;
		}, function(obj){
			handler.__sessionid = null;
		});

		return this;
	}

	Handler.app.prototype.init = function(){
		this.updateSessionId();

		// Init all modules (if init function exists)
		for(var m in this.__modules){
			var module = this.__modules[m];

			// Checking dependencies for each module
			for(var i = 0; i < module.__dependencies.length; i++){
				// Checking if a module correspond to this dependencies
				if(this.getModule(module.__dependencies[i]) == null)
					throw new Error('Module "' + module.__moduleName + '" is missing another module in order to run properly.');
			}

			if(module.init)
				module.init();
		}

		console.log('Handler initialized.');
	}


	Handler.app.prototype.apiPath = function(resource = ''){
		return this.__hostName + this.__pathName + '/api/' + resource;
	}

	// Return a module corresponding to a class
	Handler.app.prototype.getModule = function(moduleClass){
		if(typeof moduleClass != 'function')
			return null;
			// throw new Error('The module class should be a function');

		for(var m in this.__modules){
			if(this.__modules[m] instanceof moduleClass)
				return this.__modules[m];
		}

		return null;
	}

	return Handler;
})();