
--SQL TEST VALUES

--TABLE Gabarit
INSERT INTO Gabarit (id, libelle) VALUES (1, 'urbaine');
INSERT INTO Gabarit (id, libelle) VALUES (2, 'compacte');
INSERT INTO Gabarit (id, libelle) VALUES (3, 'SUV');
INSERT INTO Gabarit (id, libelle) VALUES (4, 'break');
INSERT INTO Gabarit (id, libelle) VALUES (5, 'routière');
INSERT INTO Gabarit (id, libelle) VALUES (6, 'fourgonnette');

--TABLE Voiture

INSERT INTO Voiture (id, modele, nbrPlaces) VALUES (1, 'Clio', 1);
INSERT INTO Voiture (id, modele, nbrPlaces) VALUES (2, 'Nissan', 1);
INSERT INTO Voiture (id, modele, nbrPlaces) VALUES (3, '206', 1);
INSERT INTO Voiture (id, modele, nbrPlaces) VALUES (4, 'A3', 1);

--TABLE Personne
INSERT INTO Personne (login, password, nom, prenom, datedenaissance, adresse, voiture_id, admin) VALUES ('zzz@zzz.fr', 'zzz', 'zzz', 'zzz',  PARSEDATETIME('1994-07-24','yyyy-MM-dd'), 'zzz', NULL, false);
INSERT INTO Personne (login, password, nom, prenom, dateDeNaissance, adresse, voiture_id, admin) VALUES ('user@free.fr', 'user', 'Skywalker', 'Anakin',  PARSEDATETIME('1994-07-24','yyyy-MM-dd'), 'c', 2, false);
INSERT INTO Personne (login, password, nom, prenom, dateDeNaissance, adresse, voiture_id, admin) VALUES ('admin@free.fr', 'admin', 'Windu', 'Mace',  PARSEDATETIME('1994-07-24','yyyy-MM-dd'), 'Corrusant', 2, true);

-- INSERT INTO RESERVATION VALUES(0, 1,'test',1,'a@a.fr',2)


--TABLE Ville
INSERT INTO Ville (id, nom) VALUES (1, 'Paris');
INSERT INTO Ville (id, nom) VALUES (2, 'Orleans');
INSERT INTO Ville (id, nom) VALUES (3, 'Marseille');
INSERT INTO Ville (id, nom) VALUES (4, 'Lyon');
INSERT INTO Ville (id, nom) VALUES (5, 'Toulouse');
INSERT INTO Ville (id, nom) VALUES (6, 'Nice');
INSERT INTO Ville (id, nom) VALUES (7, 'Nantes');
INSERT INTO Ville (id, nom) VALUES (8, 'Strasbourg');
INSERT INTO Ville (id, nom) VALUES (9, 'Montpellier');
INSERT INTO Ville (id, nom) VALUES (10, 'Bordeaux');
INSERT INTO Ville (id, nom) VALUES (11, 'Rennes');
INSERT INTO Ville (id, nom) VALUES (12, 'Le Havre');
INSERT INTO Ville (id, nom) VALUES (13, 'Reims');
INSERT INTO Ville (id, nom) VALUES (14, 'Lille');
INSERT INTO Ville (id, nom) VALUES (15, 'Saint Etienne');
INSERT INTO Ville (id, nom) VALUES (16, 'Toulon');
INSERT INTO Ville (id, nom) VALUES (17, 'Grenoble');
INSERT INTO Ville (id, nom) VALUES (18, 'Angers');
INSERT INTO Ville (id, nom) VALUES (19, 'Dijon');
INSERT INTO Ville (id, nom) VALUES (20, 'Brest');

--TRAJET PARIS-->MARSEILLE AVEC ORLEANS EN ETAPE INTERMEDAIRE
--TABLE Trajet
INSERT INTO Trajet (id, conducteur_login, heuredepart, nbrplacesdisponibles, villedepart_id) VALUES (1, 'user@free.fr', PARSEDATETIME('2017-12-07:14:00','yyyy-MM-dd:HH:mm'), 4, 1);


-- TABLE ETAPE
INSERT INTO Etape (id, prix, heurearrivee, trajet_id, villearrivee_id) VALUES (1, 9.5, PARSEDATETIME('2017-12-07:15:00','yyyy-MM-dd:HH:mm'), 1, 2);
INSERT INTO Etape (id, prix, heurearrivee, trajet_id, villearrivee_id) VALUES (2, 9.5, PARSEDATETIME('2017-12-07:18:00','yyyy-MM-dd:HH:mm'), 1, 3);
