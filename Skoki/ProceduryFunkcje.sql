DELIMITER $$
CREATE OR REPLACE PROCEDURE WypiszKonkursyMiejsce(IN Im VARCHAR(30),IN Naz VARCHAR(30),IN Miejsce INT)
BEGIN
	DECLARE spr INT;
	SELECT COUNT(*) INTO spr FROM Skoczkowie WHERE Imię=Im AND Nazwisko=Naz;
	IF spr=0 THEN
		SELECT 'Nie ma takiego skoczka';
	ELSE
		IF Miejsce<1 OR Miejsce>3 THEN
			SELECT 'Nie można zająć takiego miejsca';
		ELSE
			IF Miejsce=1 THEN
				SELECT Nazwa AS Skocznia,Kraj,Miasto,Termin,Zwycięzca,`2 miejsce`,`3 miejsce`,`Nagroda` FROM Konkursy K JOIN Skocznie S ON K.Skocznia=S.ID WHERE Zwycięzca=(SELECT ID FROM Skoczkowie WHERE Imię=Im AND Nazwisko=Naz);
			ELSEIF Miejsce=2 THEN
				SELECT Nazwa AS Skocznia,Kraj,Miasto,Termin,Zwycięzca,`2 miejsce`,`3 miejsce`,`Nagroda` FROM Konkursy K JOIN Skocznie S ON K.Skocznia=S.ID WHERE `2 miejsce`=(SELECT ID FROM Skoczkowie WHERE Imię=Im AND Nazwisko=Naz);
			ELSE
				SELECT Nazwa AS Skocznia,Kraj,Miasto,Termin,Zwycięzca,`2 miejsce`,`3 miejsce`,`Nagroda` FROM Konkursy K JOIN Skocznie S ON K.Skocznia=S.ID WHERE `3 miejsce`=(SELECT ID FROM Skoczkowie WHERE Imię=Im AND Nazwisko=Naz);
			END IF;
		END IF;
	END IF;		

END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE WypiszSkoczkowMiejsce (IN Miejsce INT)
BEGIN
	IF Miejsce<1 OR Miejsce>3 THEN
		SELECT 'Nie można zająć takiego miejsca';
	ELSE
		IF Miejsce=1 THEN
			CREATE OR REPLACE VIEW LiczbaZwyciestw AS
			SELECT Zwycięzca,COUNT(*)AS LiczbaMiejsc FROM Konkursy K GROUP BY Zwycięzca;
			SELECT ID,Imię,Nazwisko,LiczbaMiejsc FROM LiczbaZwyciestw LZ JOIN Skoczkowie S ON LZ.Zwycięzca=S.ID WHERE LiczbaMiejsc=(SELECT MAX(LiczbaMiejsc) FROM LiczbaZwyciestw);
		ELSEIF Miejsce=2 THEN
			CREATE OR REPLACE VIEW Liczba2Miejsc AS
			SELECT `2 miejsce`,COUNT(*)AS LiczbaMiejsc FROM Konkursy K GROUP BY `2 miejsce`;
			SELECT ID,Imię,Nazwisko,LiczbaMiejsc FROM Liczba2Miejsc L2 JOIN Skoczkowie S ON L2.`2 miejsce`=S.ID WHERE LiczbaMiejsc=(SELECT MAX(LiczbaMiejsc) FROM Liczba2Miejsc);
		ELSE
			CREATE OR REPLACE VIEW Liczba3Miejsc AS
			SELECT `3 miejsce`,COUNT(*)AS LiczbaMiejsc FROM Konkursy K GROUP BY `3 miejsce`;
			SELECT ID,Imię,Nazwisko,LiczbaMiejsc FROM Liczba3Miejsc L3 JOIN Skoczkowie S ON L3.`3 miejsce`=S.ID WHERE LiczbaMiejsc=(SELECT MAX(LIczbaMiejsc) FROM Liczba3Miejsc);
		END IF;
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE WypiszReprezentacjeMiejsce (IN Miejsce INT)
BEGIN
	IF Miejsce<1 OR Miejsce>3 THEN
		SELECT 'Nie można zająć takiego miejsca';
	ELSE
		IF Miejsce=1 THEN
			CREATE OR REPLACE VIEW LiczbaZwyciestwReprezentacja AS
			SELECT Reprezentacja,COUNT(*) AS LiczbaMiejsc FROM Konkursy K JOIN Skoczkowie S ON K.Zwycięzca=S.ID GROUP BY Reprezentacja;
			SELECT * FROM LiczbaZwyciestwReprezentacja WHERE LiczbaMiejsc=(SELECT MAX(LiczbaMiejsc) FROM LiczbaZwyciestwReprezentacja);
		ELSEIF Miejsce=2 THEN
			CREATE OR REPLACE VIEW Liczba2MiejscReprezentacja AS
			SELECT Reprezentacja,COUNT(*) AS LiczbaMiejsc FROM Konkursy K JOIN Skoczkowie S ON K.`2 miejsce`=S.ID GROUP BY Reprezentacja;
			SELECT * FROM Liczba2MiejscReprezentacja WHERE LiczbaMiejsc=(SELECT MAX(LiczbaMiejsc) FROM Liczba2MiejscReprezentacja);
		ELSE
			CREATE OR REPLACE VIEW Liczba3MiejscReprezentacja AS
			SELECT Reprezentacja,COUNT(*) AS LiczbaMiejsc FROM Konkursy K JOIN Skoczkowie S ON K.`3 miejsce`=S.ID GROUP BY Reprezentacja;
			SELECT * FROM Liczba3MiejscReprezentacja WHERE LiczbaMiejsc=(SELECT MAX(LiczbaMiejsc) FROM Liczba3MiejscReprezentacja);
		END IF;
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE WypiszSkocznieMiasto(IN Mia VARCHAR(30))
BEGIN
	SET @str='SELECT * FROM Skocznie WHERE Miasto=?';
	PREPARE stmt FROM @str;
	EXECUTE stmt USING Mia;
	DEALLOCATE PREPARE stmt;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE PodopieczniTrenera(IN Im VARCHAR(30),IN Naz VARCHAR(30))
BEGIN
	DECLARE spr INT;
	SELECT COUNT(*) INTO spr FROM Trenerzy WHERE Imię=Im AND Nazwisko=Naz;
	IF spr>0 THEN
		SET @str='SELECT S.ID,S.Imię,S.Nazwisko,S.Narodowość FROM Trenerzy T JOIN Obsada O ON T.ID=O.Trener JOIN Skoczkowie S ON O.Reprezentacja=S.Reprezentacja WHERE T.Imię=? AND T.Nazwisko=?';
		PREPARE stmt FROM @str;
		EXECUTE stmt USING Im,Naz;
		DEALLOCATE PREPARE stmt;
	ELSE
		SELECT 'Nie ma takiego trenera';
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE LiczbaTrenerowReprezentacji(IN Rep VARCHAR(30))
BEGIN
	DECLARE spr INT;
	SELECT COUNT(*) INTO spr FROM Reprezentacje WHERE Nazwa=Rep;
	IF spr>0 THEN
		SET @str='SELECT Nazwa,COUNT(*) AS LiczbaTrenerów FROM Reprezentacje R JOIN Obsada O ON R.Nazwa=O.Reprezentacja WHERE Nazwa=? GROUP BY Nazwa';
		PREPARE stmt FROM @str;
		EXECUTE stmt USING Rep;
		DEALLOCATE PREPARE stmt;
	ELSE 
		SELECT 'Nie ma takiej reprezentacji';
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE FUNCTION SumowanieNagrod(Im VARCHAR(30), Naz VARCHAR(30)) RETURNS INT DETERMINISTIC
BEGIN
	DECLARE suma INT;
	DECLARE spr INT;
	SELECT COUNT(*) INTO spr FROM Skoczkowie WHERE Imię=Im AND Nazwisko=Naz;
	IF spr>0 THEN
		SELECT SUM(Nagroda) INTO suma FROM Skoczkowie S JOIN Konkursy K ON S.ID=K.Zwycięzca WHERE Imię=Im AND Nazwisko= Naz GROUP BY S.ID;
		RETURN suma;
	ELSE
		RETURN -1;
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE FUNCTION SredniaWiekuReprezentacji(Rep VARCHAR(30)) RETURNS INT DETERMINISTIC
BEGIN
	DECLARE spr INT;
	DECLARE srednia INT;
	SELECT COUNT(*) INTO spr FROM Reprezentacje WHERE Nazwa=Rep;
	IF spr>0 THEN
		SELECT AVG(YEAR(NOW())-YEAR(`Data urodzenia`)) INTO srednia FROM Skoczkowie WHERE Reprezentacja=Rep GROUP BY Reprezentacja;
		RETURN srednia;
	ELSE
		RETURN -1;
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE MlodyStary(IN Wybór VARCHAR(4), IN Rep VARCHAR(30))
BEGIN
	DECLARE agg VARCHAR(4);
	DECLARE spr INT;
	SELECT COUNT(*) INTO spr FROM Reprezentacje WHERE Nazwa=Rep;
	IF spr>0 THEN
		IF Wybór='MAX'THEN
			SET agg='MIN';
		ELSEIF Wybór='MIN' THEN
			SET agg='MAX';
		END IF;
		IF agg='MAX' OR agg='MIN' THEN
			SET @str=CONCAT('SELECT Imię,Nazwisko,`Data urodzenia` FROM Skoczkowie WHERE `Data urodzenia`=(SELECT ',agg,'(`Data urodzenia`) FROM Skoczkowie WHERE Reprezentacja=?)');
			PREPARE stmt FROM @str;
			EXECUTE stmt USING Rep;
			DEALLOCATE PREPARE stmt;
		ELSE
			SELECT 'Nie ma takiej opcji';
		END IF;
	ELSE
		SELECT 'Nie ma takiej reprezentacji';
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE DaneOSkoczniachKraj(IN Agg VARCHAR(6),IN Naz VARCHAR(30))
BEGIN
	IF Agg='MIN' OR Agg='MAX' OR Agg='AVG' OR Agg='SUM' THEN
		SET @str=CONCAT('SELECT ',Agg,'(Rozmiar) AS ', Agg,' FROM Skocznie WHERE Kraj=? GROUP BY Kraj');
		PREPARE stmt FROM @str;
		EXECUTE stmt USING Naz;
		DEALLOCATE PREPARE stmt;
	ELSEIF Agg='COUNT' THEN
		SET @str=CONCAT('SELECT ',Agg,'(*) AS Liczba FROM Skocznie WHERE Kraj=? GROUP BY Kraj');
		PREPARE stmt FROM @str;
		EXECUTE stmt USING Naz;
		DEALLOCATE PREPARE stmt;
	ELSE 
		SELECT 'Nie ma takiego wyboru';
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE FUNCTION LiczbaKonkursowKraj(Naz VARCHAR(30)) RETURNS INT
BEGIN
	DECLARE wynik INT;
	SELECT COUNT(*) INTO wynik FROM Konkursy K JOIN Skocznie S ON K.Skocznia=S.ID WHERE Kraj=Naz GROUP BY Kraj;
	RETURN wynik;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE KonkursyWKrajuSkoczka(IN Im VARCHAR(30),IN Naz VARCHAR(30))
BEGIN
	DECLARE spr INT;
	SELECT COUNT(*) INTO spr FROM Skoczkowie WHERE Imię=Im AND Nazwisko=Naz;
	IF spr>0 THEN 
		SET @str='SELECT Nazwa,Termin,Kraj,Miasto,Zwycięzca,`2 miejsce`,`3 miejsce`,Nagroda FROM Konkursy K JOIN Skocznie S ON K.Skocznia=S.ID JOIN Skoczkowie Sk ON S.Kraj=Sk.`Narodowość` WHERE Imię=? AND Nazwisko=?';
		PREPARE stmt FROM @str;
		EXECUTE stmt USING Im,Naz;
		DEALLOCATE PREPARE stmt;
	ELSE
		SELECT 'Nie ma takiego skoczka';
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE UstawRekord(IN Naz VARCHAR(30), IN Nowy INT)
BEGIN
	DECLARE spr INT;
	SELECT COUNT(*) INTO spr FROM Skocznie WHERE Nazwa=Naz;
	IF spr>0 THEN
		UPDATE Skocznie
		SET Rekord=Nowy
		WHERE Nazwa=Naz;
		SELECT 'Ustawiono nowy rekord';
	ELSE
		SELECT 'Nie ma takiej skoczni';
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE UstawMiejsca(IN P INT,IN D INT,IN T INT,IN Sko INT,IN Ter DATETIME)
BEGIN
	DECLARE spr INT;
	DECLARE spr2 INT;
	SELECT COUNT(*) INTO spr FROM Konkursy WHERE Skocznia=Sko AND Termin=Ter;
	IF spr>0 THEN
		SELECT COUNT(*) INTO spr2 FROM Skoczkowie WHERE ID=P OR ID=D OR ID=T;
		IF spr2=3 THEN
			UPDATE Konkursy
			SET Zwycięzca=P,`2 miejsce`=D,`3 miejsce`=T
			WHERE Skocznia=Sko AND Termin=Ter;
		ELSE 
			SELECT 'Błędne dane zawodników';
		END IF;
	ELSE
		SELECT 'Nie ma takiego konkursu';
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE Klasyfikacja(IN Wybór VARCHAR(30))
BEGIN
	IF Wybór='Skoczkowie' THEN
		SELECT * FROM Skoczkowie ORDER BY Punkty DESC,`Liczba zwycięstw` DESC;
	ELSEIF Wybór="Reprezentacje" THEN
		SELECT * FROM Reprezentacje ORDER BY `Suma punktów` DESC,`Liczba zawodników` ASC;
	ELSE
		SELECT 'Nie ma takiego wyboru';
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE LiczPunkty()
BEGIN
	DECLARE koniec INT DEFAULT FALSE;
	DECLARE p,d,t INT;
	DECLARE punkty CURSOR FOR(
	SELECT Zwycięzca,`2 miejsce`,`3 miejsce` FROM Konkursy);
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET koniec=1;
	OPEN punkty;
	read_loop:LOOP
		FETCH punkty INTO p,d,t;
		IF koniec THEN
			LEAVE read_loop;
		ELSE 
			UPDATE Skoczkowie
			SET Punkty=Punkty+100,`Liczba zwycięstw`=`Liczba zwycięstw`+1
			WHERE ID=p;
			UPDATE Skoczkowie
			SET Punkty=Punkty+70
			WHERE ID=d;
			UPDATE Skoczkowie
			SET Punkty=Punkty+40
			WHERE ID=t;
			UPDATE Skoczkowie
			SET Punkty=Punkty+10
			WHERE ID<>p AND ID<>d AND ID<>t;
		END IF;
	END LOOP;
	CLOSE punkty;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE LiczZawodnikow()
BEGIN 
	DECLARE koniec INT DEFAULT FALSE;
	DECLARE naz VARCHAR(30);
	DECLARE pkt INT;
	DECLARE zawodnicy CURSOR FOR(
	SELECT Reprezentacja,Punkty FROM Skoczkowie);
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET koniec=1;
	OPEN zawodnicy;
	read_loop:LOOP
		FETCH zawodnicy INTO naz,pkt;
		IF koniec THEN
			LEAVE read_loop;
		ELSE 
			UPDATE Reprezentacje
			SET `Liczba zawodników`=`Liczba zawodników`+1,`Suma punktów`=`Suma punktów`+pkt
			WHERE Nazwa=naz;
		END IF;
	END LOOP;
	CLOSE zawodnicy;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE LiczKonkursy()
BEGIN
	DECLARE koniec INT DEFAULT FALSE;
	DECLARE sko INT;
	DECLARE miejsce CURSOR FOR(
	SELECT Skocznia FROM Konkursy);
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET koniec=1;
	OPEN miejsce;
	read_loop:LOOP
		FETCH miejsce INTO sko;
		IF koniec THEN
			LEAVE read_loop;
		ELSE 
			UPDATE Skocznie
			SET `Liczba konkursów`=`Liczba konkursów`+1
			WHERE ID=sko;
		END IF;
	END LOOP;
	CLOSE miejsce;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE PROCEDURE WyplacNagrody(IN kwota INT)
BEGIN
	DECLARE koniec INT DEFAULT FALSE;
	DECLARE ile INT;
	DECLARE licznik1 INT DEFAULT 0;
	DECLARE licznik2 INT DEFAULT 0;
	DECLARE wypłacanie CURSOR FOR(
	SELECT SUM(Nagroda) FROM Konkursy GROUP BY Zwycięzca);
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET koniec=TRUE;
	OPEN wypłacanie;
	SET autocommit=0;
	START TRANSACTION;
	read_loop: LOOP
		FETCH wypłacanie INTO ile;
		IF koniec THEN
			LEAVE read_loop;
		ELSE
			SET licznik1=licznik1+1;
			SET kwota=kwota-ile;
			SET licznik2=licznik2+ile;
		END IF;
		IF kwota<0 THEN
			ROLLBACK;
			SELECT 'Zbyt mała kwota' AS Wynik;
			LEAVE read_loop;
		END IF;
	END LOOP;
	IF kwota>=0 THEN
		SELECT CONCAT('Wypłacono łącznie ',licznik2,' dla ',licznik1,' zawodników. Zostało ',kwota) AS Wynik;
	END IF;
	COMMIT;	
END $$
DELIMITER ;


DELIMITER $$
CREATE OR REPLACE PROCEDURE NajwiecejPodiumSkocznia(IN Im VARCHAR(30),IN Naz VARCHAR(30))
BEGIN
	DECLARE pom INT;
	SELECT ID INTO pom FROM Skoczkowie WHERE Imię=Im AND Nazwisko=Naz;
	IF pom>0 THEN
		SELECT * FROM (SELECT Nazwa,Kraj,Miasto,Rozmiar,Rekord,COUNT(*) AS LiczbaPodium FROM Konkursy K JOIN Skocznie S ON K.Skocznia=S.ID WHERE Zwycięzca=pom OR `2 miejsce`=pom OR `3 miejsce`=pom GROUP BY Skocznia) A WHERE A.LiczbaPodium=(SELECT MAX(LiczbaPodium) AS Max FROM (SELECT Nazwa,Kraj,Miasto,Rozmiar,Rekord,COUNT(*) AS LiczbaPodium FROM Konkursy K JOIN Skocznie S ON K.Skocznia=S.ID WHERE Zwycięzca=pom OR `2 miejsce`=pom OR `3 miejsce`=pom GROUP BY Skocznia) T);
	ELSE
		SELECT 'Nie ma takiego zawodnika';
	END IF;
END $$
DELIMITER ;
