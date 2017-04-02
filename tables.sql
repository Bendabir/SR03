-- Creating database
CREATE DATABASE IF NOT EXISTS sr03 
	CHARACTER SET 'utf8';

-- Creating tables
CREATE TABLE IF NOT EXISTS sr03.games (
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(255) NOT NULL,
	console VARCHAR(32) NOT NULL,
	price FLOAT UNSIGNED DEFAULT 0,
	release_date DATE NOT NULL,
	UNIQUE (title, console, release_date)
);

CREATE TABLE IF NOT EXISTS sr03.users (
	username VARCHAR(16) PRIMARY KEY,
	password VARCHAR(255) NOT NULL,
	firstname VARCHAR(64) NOT NULL,
	lastname VARCHAR(64) NOT NULL, 
	birth_date DATE NOT NULL,
	status VARCHAR(32) NOT NULL DEFAULT "user"
);

CREATE TABLE IF NOT EXISTS sr03.cart (
	game INT UNSIGNED,
	user VARCHAR(16),
	quantity INT UNSIGNED NOT NULL DEFAULT 1,
	PRIMARY KEY (game, user),
	FOREIGN KEY (game) REFERENCES sr03.games(id),
	FOREIGN KEY (user) REFERENCES sr03.users(username)
);