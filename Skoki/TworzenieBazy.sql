CREATE DATABASE Jumping;

USE Jumping;

CREATE TABLE IF NOT EXISTS Trenerzy(
ID INT NOT NULL AUTO_INCREMENT,
Imię VARCHAR(30) CHECK(Imię REGEXP '^[a-zA-ZĄąĆćĘęŁłÓoŚśŻżŹźŃń]+$'),
Nazwisko VARCHAR(30) CHECK(Nazwisko REGEXP '^[a-zA-ZĄąĆćĘęŁłÓoŚśŻżŹźŃń-]+$'),
Narodowość VARCHAR(30) CHECK(Narodowość REGEXP '^[a-zA-ZĄąĆćĘęŁłÓoŚśŻżŹźŃń ]+$'),
Wiek INT CHECK (Wiek>0),
PRIMARY KEY(ID));

CREATE TABLE IF NOT EXISTS Skocznie(
ID INT NOT NULL AUTO_INCREMENT,
Nazwa VARCHAR(40),
Kraj VARCHAR(30) CHECK(Kraj REGEXP '^[a-zA-ZĄąĆćĘęŁłÓoŚśŻżŹźŃń ]+$'),
Miasto VARCHAR(30) CHECK(Miasto REGEXP '^[a-zA-ZĄąĆćĘęŁłÓoŚśŻżŹźŃń ]+$'),
`Liczba konkursów` INT DEFAULT 0,
Rozmiar INT CHECK(Rozmiar>0),
Rekord INT CHECK(Rekord>0),
PRIMARY KEY(ID));

CREATE TABLE IF NOT EXISTS Reprezentacje(
Nazwa VARCHAR(40) NOT NULL CHECK(Nazwa REGEXP '^[a-zA-ZĄąĆćĘęŁłÓoŚśŻżŹźŃń ]+$'),
`Skocznia domowa` INT,
`Liczba zawodników` INT DEFAULT 0,
`Suma punktów` INT DEFAULT 0,
PRIMARY KEY(Nazwa),
CONSTRAINT fk_ReprezentacjaSkocznie FOREIGN KEY(`Skocznia domowa`) REFERENCES Skocznie(ID) ON DELETE SET NULL);

CREATE TABLE IF NOT EXISTS Skoczkowie(
ID INT NOT NULL AUTO_INCREMENT,
Imię VARCHAR(30) CHECK(Imię REGEXP '^[a-zA-ZĄąĆćĘęŁłÓoŚśŻżŹźŃń]+$'),
Nazwisko VARCHAR(30) CHECK(Nazwisko REGEXP '^[a-zA-ZĄąĆćĘęŁłÓoŚśŻżŹźŃń-]+$'),
Narodowość VARCHAR(30) CHECK(Narodowość REGEXP '^[a-zA-ZĄąĆćĘęŁłÓoŚśŻżŹźŃń ]+$'),
`Data urodzenia` DATE,
Reprezentacja VARCHAR(40),
Punkty INT DEFAULT 0,
`Liczba zwycięstw` INT DEFAULT 0,
PRIMARY KEY(ID),
CONSTRAINT fk_SkoczkowieReprezentacje FOREIGN KEY(Reprezentacja) REFERENCES Reprezentacje(Nazwa) ON DELETE SET NULL);

CREATE TABLE IF NOT EXISTS Obsada(
Reprezentacja VARCHAR(40) NOT NULL,
Trener INT NOT NULL,
PRIMARY KEY(Reprezentacja,Trener),
CONSTRAINT fk_ObsadaReprezentacje FOREIGN KEY(Reprezentacja) REFERENCES Reprezentacje(Nazwa) ON DELETE CASCADE,
CONSTRAINT fk_ObsadaTrenerzy FOREIGN KEY(Trener) REFERENCES Trenerzy(ID) ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS Konkursy(
Skocznia INT NOT NULL,
Termin DATETIME NOT NULL,
Zwycięzca INT,
`2 miejsce` INT,
`3 miejsce` INT,
Nagroda INT CHECK (Nagroda>=0),
PRIMARY KEY(Skocznia,Termin),
CONSTRAINT fk_KonkursySkocznie FOREIGN KEY(Skocznia) REFERENCES Skocznie(ID),
CONSTRAINT fk_KonkursySkoczkowie1 FOREIGN KEY(Zwycięzca) REFERENCES Skoczkowie(ID) ON DELETE SET NULL,
CONSTRAINT fk_KonkursySkoczkowie2 FOREIGN KEY(`2 miejsce`) REFERENCES Skoczkowie(ID) ON DELETE SET NULL,
CONSTRAINT fk_KonkursySkoczkowie3 FOREIGN KEY(`3 miejsce`) REFERENCES Skoczkowie(ID) ON DELETE SET NULL);

CREATE TABLE Users(
ID INT AUTO_INCREMENT NOT NULL,
Rola VARCHAR(20),
Login VARCHAR(30),
Hasło VARCHAR(60),
PRIMARY KEY(ID));
