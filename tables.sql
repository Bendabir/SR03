-- Drop database
DROP DATABASE IF EXISTS sr03;

-- Creating database
CREATE DATABASE IF NOT EXISTS sr03 
	CHARACTER SET 'utf8';

-- Creating tables
CREATE TABLE IF NOT EXISTS sr03.consoles (
	name VARCHAR(32) PRIMARY KEY,
	launched_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS sr03.game_types (
	name VARCHAR(64) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS sr03.games (
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(255) NOT NULL,
	console VARCHAR(32) NOT NULL,
	price DOUBLE UNSIGNED DEFAULT 0,
	release_date DATE NOT NULL,
	stock INT UNSIGNED NOT NULL DEFAULT 0,
	UNIQUE (title, console, release_date),
	FOREIGN KEY (console) REFERENCES sr03.consoles(name)
);

CREATE TABLE IF NOT EXISTS sr03.assoc_game_types_games (
	type VARCHAR(64),
	game INT UNSIGNED,
	PRIMARY KEY (type, game),
	FOREIGN KEY (type) REFERENCES sr03.game_types(name),
	FOREIGN KEY (game) REFERENCES sr03.games(id)
);

CREATE TABLE IF NOT EXISTS sr03.users (
	username VARCHAR(16) PRIMARY KEY,
	password VARCHAR(255) NOT NULL,
	firstname VARCHAR(64) NOT NULL,
	lastname VARCHAR(64) NOT NULL, 
	birth_date DATE NOT NULL,
	status VARCHAR(32) NOT NULL DEFAULT "user"
);

CREATE TABLE IF NOT EXISTS sr03.sales (
	game INT UNSIGNED,
	user VARCHAR(16),
	quantity INT UNSIGNED NOT NULL DEFAULT 1,
	PRIMARY KEY (game, user),
	FOREIGN KEY (game) REFERENCES sr03.games(id),
	FOREIGN KEY (user) REFERENCES sr03.users(username)
);