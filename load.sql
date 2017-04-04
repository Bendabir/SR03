-- Example data set
INSERT INTO sr03.consoles (name) VALUES ('PS1');
INSERT INTO sr03.consoles (name) VALUES ('PS2');
INSERT INTO sr03.consoles (name) VALUES ('PS3');
INSERT INTO sr03.consoles (name) VALUES ('PS4');
INSERT INTO sr03.consoles (name) VALUES ('PC');
INSERT INTO sr03.consoles (name) VALUES ('GameBoy');

INSERT INTO sr03.game_types (name) VALUES ('RPG');
INSERT INTO sr03.game_types (name) VALUES ('MMORPG');
INSERT INTO sr03.game_types (name) VALUES ('FPS');
INSERT INTO sr03.game_types (name) VALUES ('Card');
INSERT INTO sr03.game_types (name) VALUES ('Aventure');

INSERT INTO sr03.games (title, console, price, release_date, stock) VALUES ('Rocket League', 'PS4', 20, '2015-07-07', 100);
INSERT INTO sr03.games (title, console, price, release_date, stock) VALUES ('Shadow of Mordor', 'PC', 4.61, '2014-09-30', 5);
INSERT INTO sr03.games (title, console, price, release_date, stock) VALUES ('Middle-earth: Shadow of War', 'PC', 60, '2017-08-22', 1000);
