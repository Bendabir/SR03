;var gamesStore = {};

(function(publics){
	// Private members
	var privates = {};

	privates.hostName = document.location.origin + ':8080';
	privates.pathName = '/server';

	privates.apiPath = function(resource = ''){return this.hostName + this.pathName + '/api/' + resource};
	privates.loginPath = function(){return this.hostName + this.pathName + '/login'};
	privates.logoutPath = function(){return this.hostName + this.pathName + '/logout'};

	privates.jsessionid = '';

	privates.login = function(){
		// Looking for cookies
		var cookies = document.cookie.split(';');

		for(var i = 0; i < cookies.length; i++){
			if(cookies[i].indexOf('JSESSIONID') != -1){
				privates.jsessionid = cookies[i].split('=')[1];
				break;
			}
		}
	}


	// Public members
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
							response: xhr.responseText,
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
							response: xhr.responseText,
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
			publics.sessionid = JSON.parse(obj.response).id;
		}, function(obj){
			publics.sessionid = null;
		});
	}

})(gamesStore);

gamesStore.init();
