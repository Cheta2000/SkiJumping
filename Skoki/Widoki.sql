Są wykorzystane w funkcjach, nie ma do nich dostępu

CREATE OR REPLACE VIEW LiczbaZwycięstw AS
SELECT Zwycięzca,COUNT(*)AS LiczbaMiejsc FROM Konkursy K GROUP BY Zwycięzca;

CREATE OR REPLACE VIEW Liczba2Miejsc AS
SELECT `2 miejsce`,COUNT(*)AS LiczbaMiejsc FROM Konkursy K GROUP BY `2 miejsce`;

CREATE OR REPLACE VIEW Liczba3Miejsc AS
SELECT `3 miejsce`,COUNT(*)AS LiczbaMiejsc FROM Konkursy K GROUP BY `3 miejsce`;

CREATE OR REPLACE VIEW LiczbaZwycięstwReprezentacja AS
SELECT Reprezentacja,COUNT(*) AS LiczbaMiejsc FROM Konkursy K JOIN Skoczkowie S ON K.Zwycięzca=S.ID GROUP BY Reprezentacja;

CREATE OR REPLACE VIEW Liczba2MiejscReprezentacja AS
SELECT Reprezentacja,COUNT(*) AS LiczbaMiejsc FROM Konkursy K JOIN Skoczkowie S ON K.`2 miejsce`=S.ID GROUP BY Reprezentacja;

CREATE OR REPLACE VIEW Liczba3MiejscReprezentacja AS
SELECT Reprezentacja,COUNT(*) AS LiczbaMiejsc FROM Konkursy K JOIN Skoczkowie S ON K.`3 miejsce`=S.ID GROUP BY Reprezentacja;
