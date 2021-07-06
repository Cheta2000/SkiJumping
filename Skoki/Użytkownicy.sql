CREATE USER 'Dyrektor'@'localhost' IDENTIFIED BY 'Dyrektor123';
GRANT SELECT,INSERT,UPDATE,DELETE ON Skoki.* TO 'Dyrektor'@'localhost';

CREATE USER 'Trener'@'localhost' IDENTIFIED BY 'Trener123';
GRANT SELECT ON Skoki.* TO 'Trener'@'localhost';
GRANT INSERT,UPDATE ON Skoki.Skoczkowie TO 'Trener'@'localhost';
GRANT INSERT,UPDATE ON Skoki.Trenerzy TO 'Trener'@'localhost';

CREATE USER 'Szef zawodow'@'localhost' IDENTIFIED BY 'Szef123';
GRANT SELECT ON Skoki.* TO 'Szef zawodow'@'localhost';
GRANT INSERT,UPDATE,DELETE ON Skoki.Skocznie TO 'Szef zawodow'@'localhost';
GRANT INSERT,UPDATE,DELETE ON Skoki.Konkursy TO 'Szef zawodow'@'localhost';

CREATE USER 'Widz'@'localhost';
GRANT SELECT ON Skoki.* TO 'Widz'@'localhost';

GRANT EXECUTE ON Skoki.* TO 'Dyrektor'@'localhost';


GRANT EXECUTE ON PROCEDURE DaneOSkoczniachKraj TO 'Trener'@'localhost';
GRANT EXECUTE ON PROCEDURE Klasyfikacja TO 'Trener'@'localhost';
GRANT EXECUTE ON PROCEDURE LiczbaTrenerowReprezentacji TO 'Trener'@'localhost';
GRANT EXECUTE ON PROCEDURE MlodyStary TO 'Trener'@'localhost';
GRANT EXECUTE ON PROCEDURE NajwiecejPodiumSkocznia TO 'Trener'@'localhost';
GRANT EXECUTE ON PROCEDURE PodopieczniTrenera TO 'Trener'@'localhost';
GRANT EXECUTE ON PROCEDURE WypiszKonkursyMiejsce TO 'Trener'@'localhost';
GRANT EXECUTE ON PROCEDURE WypiszReprezentacjeMiejsce TO 'Trener'@'localhost';
GRANT EXECUTE ON PROCEDURE WypiszSkoczkowMiejsce TO 'Trener'@'localhost';
GRANT EXECUTE ON PROCEDURE WypiszSkocznieMiasto TO 'Trener'@'localhost';
GRANT EXECUTE ON FUNCTION LiczbaKonkursowKraj TO 'Trener'@'localhost';
GRANT EXECUTE ON FUNCTION SredniaWiekuReprezentacji TO 'Trener'@'localhost';
GRANT EXECUTE ON FUNCTION SumowanieNagrod TO 'Trener'@'localhost';

GRANT EXECUTE ON PROCEDURE DaneOSkoczniachKraj TO 'Szef zawodow'@'localhost';
GRANT EXECUTE ON PROCEDURE LiczKonkursy TO 'Szef zawodow'@'localhost';
GRANT EXECUTE ON PROCEDURE UstawMiejsca TO 'Szef zawodow'@'localhost';
GRANT EXECUTE ON PROCEDURE UstawRekord TO 'Szef zawodow'@'localhost';
GRANT EXECUTE ON PROCEDURE WypiszSkocznieMiasto TO 'Szef zawodow'@'localhost';
GRANT EXECUTE ON FUNCTION LiczbaKonkursowKraj TO 'Szef zawodow'@'localhost';
GRANT EXECUTE ON PROCEDURE WypiszKonkursyMiejsce TO 'Szef zawodow'@'localhost';

GRANT EXECUTE ON PROCEDURE DaneOSkoczniachKraj TO 'Widz'@'localhost';
GRANT EXECUTE ON PROCEDURE Klasyfikacja TO 'Widz'@'localhost';
GRANT EXECUTE ON PROCEDURE KonkursyWKrajuSkoczka TO 'Widz'@'localhost';
GRANT EXECUTE ON PROCEDURE LiczbaTrenerowReprezentacji TO 'Widz'@'localhost';
GRANT EXECUTE ON PROCEDURE MlodyStary TO 'Widz'@'localhost';
GRANT EXECUTE ON PROCEDURE NajwiecejPodiumSkocznia TO 'Widz'@'localhost';
GRANT EXECUTE ON PROCEDURE PodopieczniTrenera TO 'Widz'@'localhost';
GRANT EXECUTE ON PROCEDURE WypiszKonkursyMiejsce TO 'Widz'@'localhost';
GRANT EXECUTE ON PROCEDURE WypiszReprezentacjeMiejsce TO 'Widz'@'localhost';
GRANT EXECUTE ON PROCEDURE WypiszSkoczkowMiejsce TO 'Widz'@'localhost';
GRANT EXECUTE ON PROCEDURE WypiszSkocznieMiasto TO 'Widz'@'localhost';
GRANT EXECUTE ON FUNCTION LiczbaKonkursowKraj TO 'Widz'@'localhost';
GRANT EXECUTE ON FUNCTION SredniaWiekuReprezentacji TO 'Widz'@'localhost';
GRANT EXECUTE ON FUNCTION SumowanieNagrod TO 'Widz'@'localhost';

FLUSH PRIVILEGES;

INSERT INTO Users(`Rola`,`Login`,`Hasło`) VALUES ('Trener','Jan Kowalski',MD5('Kowalski123')),('Trener','Kamil Nowak',MD5('Nowak123')),('Dyrektor','Michał Jankowski',MD5('Jankowski123')),('Szef zawodów','Rafał Górniak',MD5('Górniak123'));
