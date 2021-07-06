package com.Mateusz.Lista4.Add;

import java.sql.*;

public class AddingValues {

	public static void main(String[] args) {
		RandomData randomData = new RandomData();
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Skoki", "mateusz",
					"Casillas123");
			int counter = 0;
			String query;
			PreparedStatement preparedStmt;
			for (int i = 0; i < 30; i++) {
				query = "INSERT INTO Trenerzy(Imię,Nazwisko,Narodowość,Wiek) VALUES(?,?,?,?)";
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, randomData.randomName());
				preparedStmt.setString(2, randomData.randomSurname());
				preparedStmt.setString(3, randomData.randomCountry(0));
				preparedStmt.setInt(4, randomData.randomNumber(20, 60));
				preparedStmt.execute();
				counter++;
			}
			System.out.println("Pomyslnie dodano do trenerow: " + counter);
			counter = 0;
			for (int i = 0; i < 50; i++) {
				query = "INSERT INTO Skocznie(Nazwa,Kraj,Miasto,Rozmiar,Rekord) VALUES(?,?,?,?,?)";
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, randomData.hillName());
				preparedStmt.setString(2, randomData.randomCountry(0));
				preparedStmt.setString(3, randomData.randomCity());
				int size = randomData.randomNumber(90, 250);
				preparedStmt.setInt(4, size);
				preparedStmt.setInt(5, size + randomData.sign() * randomData.randomNumber(0, 20));
				preparedStmt.execute();
				counter++;
			}
			System.out.println("Pomyslnie dodano do skoczni: " + counter);
			counter = 0;
			for (int i = 0; i < 20; i++) {
				query = "INSERT INTO Reprezentacje(Nazwa,`Skocznia domowa`) VALUES(?,?)";
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, randomData.country());
				preparedStmt.setInt(2, randomData.randomNumber(1, 50));
				preparedStmt.execute();
				counter++;
			}
			System.out.println("Pomyslnie dodano do skoczni: " + counter);
			counter = 0;
			for (int i = 0; i < 120; i++) {
				query = "INSERT INTO Skoczkowie(Imię,Nazwisko,Narodowość,`Data urodzenia`,Reprezentacja) VALUES(?,?,?,?,?)";
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, randomData.randomName());
				preparedStmt.setString(2, randomData.randomSurname());
				preparedStmt.setString(3, randomData.randomCountry(0));
				preparedStmt.setString(4, randomData.randomBirthDate());
				preparedStmt.setString(5, randomData.randomCountry(20));
				preparedStmt.execute();
				counter++;
			}
			System.out.println("Pomyslnie dodano do skoczkow: " + counter);
			counter = 0;
			for (int i = 0; i < 30; i++) {
				query = "INSERT INTO Obsada(Reprezentacja,Trener) VALUES(?,?)";
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setString(1, randomData.randomCountry(20));
				preparedStmt.setInt(2, randomData.randomNumber(1, 30));
				preparedStmt.execute();
				counter++;
			}
			System.out.println("Pomyslnie dodano do obsady: " + counter);
			counter = 0;
			for (int i = 0; i < 300; i++) {
				query = "INSERT INTO Konkursy(Skocznia,Termin,Zwycięzca,`2 miejsce`,`3 miejsce`,Nagroda) VALUES(?,?,?,?,?,?)";
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setInt(1, randomData.randomNumber(1, 50));
				preparedStmt.setString(2, randomData.dateTime());
				int place1 = randomData.randomNumber(1, 120);
				preparedStmt.setInt(3, place1);
				int place2 = randomData.randomNumber(1, 120);
				while (place2 == place1) {
					place2 = randomData.randomNumber(1, 120);
				}
				preparedStmt.setInt(4, place2);
				int place3 = randomData.randomNumber(1, 120);
				while (place3 == place2 || place3 == place1) {
					place3 = randomData.randomNumber(1, 120);
				}
				preparedStmt.setInt(5, place3);
				preparedStmt.setInt(6, randomData.randomNumber(1000, 15000));
				preparedStmt.execute();
				counter++;
			}
			System.out.println("Pomyslnie dodano do skoczni: " + counter);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
