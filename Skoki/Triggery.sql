DELIMITER $$
CREATE OR REPLACE TRIGGER DodajSkoczka BEFORE INSERT ON Skoczkowie
FOR EACH ROW
BEGIN
	UPDATE Reprezentacje 
	SET `Liczba zawodników`=`Liczba zawodników`+1,`Suma punktów`=`Suma punktów`+NEW.Punkty
	WHERE Nazwa=NEW.Reprezentacja;
END $$
DELIMITER ;



DELIMITER $$
CREATE OR REPLACE TRIGGER EdytujSkoczka BEFORE UPDATE ON Skoczkowie
FOR EACH ROW
BEGIN
	UPDATE Reprezentacje 
	SET `Liczba zawodników`=`Liczba zawodników`+1,`Suma punktów`=`Suma punktów`+NEW.Punkty
	WHERE Nazwa=NEW.Reprezentacja;
	UPDATE Reprezentacje 
	SET `Liczba zawodników`=`Liczba zawodników`-1,`Suma punktów`=`Suma punktów`-OLD.Punkty
	WHERE Nazwa=OLD.Reprezentacja;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE TRIGGER UsuńSkoczka BEFORE DELETE ON Skoczkowie
FOR EACH ROW
BEGIN
	UPDATE Reprezentacje 
	SET `Liczba zawodników`=`Liczba zawodników`-1,`Suma punktów`=`Suma punktów`-OLD.Punkty
	WHERE Nazwa=OLD.Reprezentacja;
	UPDATE Konkursy
	SET Zwycięzca=`2 miejsce`,`2 miejsce`=`3 miejsce`,`3 miejsce`=NULL
	WHERE Zwycięzca=OLD.ID;
	UPDATE Konkursy
	SET `2 miejsce`=`3 miejsce`,`3 miejsce`=NULL
	WHERE `2 miejsce`=OLD.ID;
	UPDATE Konkursy
	SET `3 miejsce`=NULL
	WHERE `3 miejsce`=OLD.ID;	
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE TRIGGER DodajKonkurs BEFORE INSERT ON Konkursy
FOR EACH ROW
BEGIN
	UPDATE Skocznie 
	SET `Liczba konkursów`=`Liczba konkursów`+1
	WHERE ID=NEW.Skocznia;
	UPDATE Skoczkowie
	SET Punkty=Punkty+100,`Liczba zwycięstw`=`Liczba zwycięstw`+1
	WHERE ID=NEW.Zwycięzca;
	UPDATE Skoczkowie
	SET Punkty=Punkty+70
	WHERE ID=NEW.`2 miejsce`;
	UPDATE Skoczkowie
	SET Punkty=Punkty+40
	WHERE ID=NEW.`3 miejsce`;
	UPDATE Skoczkowie
	SET Punkty=Punkty+10
	WHERE ID<>NEW.Zwycięzca AND ID<>NEW.`2 miejsce` AND ID<>NEW.`3 miejsce`;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE TRIGGER EdytujKonkurs BEFORE UPDATE ON Konkursy
FOR EACH ROW
BEGIN
	UPDATE Skocznie
	SET `Liczba konkursów`=`Liczba konkursów`+1
	WHERE ID=NEW.Skocznia;
	UPDATE Skocznie
	SET `Liczba konkursów`=`Liczba konkursów`-1
	WHERE ID=OLD.Skocznia;
	UPDATE Skoczkowie
	SET Punkty=Punkty+100
	WHERE ID=NEW.Zwycięzca;
	UPDATE Skoczkowie
	SET Punkty=Punkty-100
	WHERE ID=OLD.Zwycięzca;
	UPDATE Skoczkowie
	SET Punkty=Punkty+70
	WHERE ID=NEW.`2 miejsce`;
	UPDATE Skoczkowie
	SET Punkty=Punkty-70
	WHERE ID=OLD.`2 miejsce`;
	UPDATE Skoczkowie
	SET Punkty=Punkty+40
	WHERE ID=NEW.`3 miejsce`;
	UPDATE Skoczkowie
	SET Punkty=Punkty-40
	WHERE ID=OLD.`3 miejsce`;
	UPDATE Skoczkowie
	SET Punkty=Punkty+10
	WHERE ID<>NEW.Zwycięzca AND ID<>NEW.`2 miejsce` AND ID<>NEW.`3 miejsce`;
	UPDATE Skoczkowie
	SET Punkty=Punkty-10
	WHERE ID<>OLD.Zwycięzca AND ID<>OLD.`2 miejsce` AND ID<>OLD.`3 miejsce`;
END $$
DELIMITER ;

DELIMITER $$
CREATE OR REPLACE TRIGGER UsuńKonkurs BEFORE DELETE ON Konkursy
FOR EACH ROW
BEGIN
	UPDATE Skocznie
	SET `Liczba konkursów`=`Liczba konkursów`-1
	WHERE ID=OLD.Skocznia;
	UPDATE Skoczkowie
	SET Punkty=Punkty-100,`Liczba zwycięstw`=`Liczba zwycięstw`-1
	WHERE ID=OLD.Zwycięzca;
	UPDATE Skoczkowie
	SET Punkty=Punkty-70
	WHERE ID=OLD.`2 miejsce`;
	UPDATE Skoczkowie
	SET Punkty=Punkty-40
	WHERE ID=OLD.`3 miejsce`;
	UPDATE Skoczkowie
	SET Punkty=Punkty-10
	WHERE ID<>OLD.Zwycięzca AND ID<>OLD.`2 miejsce` AND ID<>OLD.`3 miejsce`;
END $$
DELIMITER ;
