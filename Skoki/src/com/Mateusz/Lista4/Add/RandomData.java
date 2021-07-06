package com.Mateusz.Lista4.Add;
import java.util.Random;

public class RandomData {
	Random rand = new Random();
	int counter1 = -1;
	int counter2 = -1;

	public String randomName() {
		String[] names = { "Kamil", "Adam", "Piotr", "Jakub", "Mateusz", "Maciej", "Dawid", "Wojciech", "Tomasz",
				"Andrzej", "Stefan", "Halvor", "Joseph", "Severin", "Antii", "George", "Kevin", "Marcus", "Remy",
				"Donald", "Mix", "Kelvin", "William", "Anze", "Peter", "Piotr", "Dulan", "Michael", "John", "George",
				"Harry", "Ronald", "Robert", "Karl", "Andre", "Yuki", "Nasau", "Constantin", "Junshiro", "Antii",
				"Thomas", "Clemens", "Viktor", "Yarek", "Daniel", "Marius", "Mackenzie", "Bjoern", "Savic", "Vladimir",
				"Max", "Giovani", "Lars", "Richard", "Casey", "Cestmir", "Rupert", "Michaił", "Walter", "Roman",
				"Wilgen", "Bonn", "Alexander", "Burito", "Claude", "Constatin", "Sven", "Sloop", "Bartlomeu", "Nicolas",
				"Gyeorgy", "Lucas", "Roll", "Bigit", "Desmond", "Aufgang", "Despacito", "Nurm", "Derek", "Jacek", "Apy",
				"Wenso", "Wess", "Stan", "Klutcher", "Warnish" };
		int random = rand.nextInt(names.length);
		return names[random];
	}

	public String randomSurname() {
		String[] surnames = { "Kowalski", "Nowak", "Stoch", "Małysz", "Żyła", "Kubacki", "Wellinger", "Kraft", "Geiger",
				"Woll", "Freund", "Prevc", "Clou", "Haman", "Sato", "Kenobi", "Vor", "Semenic", "Lanisek", "Koudelka",
				"Terminator", "Bickner", "Schlirenzauer", "Morgenstern", "Funaki", "Crabb", "Potter", "Lock",
				"Granerud", "Lindvik", "Johnson", "Fettner", "Mort", "Champagne", "Ito", "Klimov", "Tande", "Bartol",
				"Aigro", "Takeuchi", "Chęciński", "Visotto", "Hernandez", "Xanax", "Eriksen", "Jelar", "Pavlov", "Koba",
				"Eisen", "Hofer", "Mors", "Mertens", "Left", "Duda", "Stoeckl", "Doleżau", "Horngachar", "Pavlovcic",
				"Zografski", "Kasparov", "Botvinov", "Hoerl", "Knight", "Wallace", "Beep", "Damm", "Hohenzauer",
				"Kluczov", "Lotar", "Kimchi", "Seeyoun", "Tkachenko", "Sesenov", "Hoh", "Dish", "Merakulo", "Robertson",
				"Gladyr", "Eisenbichler", "Schmid", "Hanavald", "Nurmsalu", "Weng", "Sausage", "Nordish", "Styles",
				"Prick", "Boyd-Clowes", "Bickner", "Brother", "Sucker", "Klopp", "Guardiola" };
		int random = rand.nextInt(surnames.length);
		return surnames[random];
	}

	public String randomCountry(int range) {
		String[] countries = { "Polska", "Czechy", "Estonia", "Austria", "USA", "Niemcy", "Norwegia", "Rosja",
				"Finlandia", "Japonia", "Szwajcaria", "Francja", "Łotwa", "Kazachstan", "Słowenia", "Kanada",
				"Hiszpania", "Portugalia", "Belgia", "Rumunia", "Litwa", "Australia", "Birma", "Kamerun", "Chiny",
				"Ukraina", "Serbia", "Czarnogóra", "Wietnam", "Meksyk", "Tunezja", "Egipt", "RPA", "Zimbabwe", "Dania",
				"Szwecja", "Kosowo", "Albania", "Andora", "Argentyna", "Holandia", "Irlandia", "Islandia", "Kolumbia",
				"Mołdawia", "Monako", "Lichtenstein", "Słowacja", "Anglia", "Szkocja", "Nibylandia", "Urugwaj",
				"Turcja" };
		int random;
		if (range == 0) {
			random = rand.nextInt(countries.length);
		} else {
			random = rand.nextInt(range);
		}
		return countries[random];
	}

	public String country() {
		String[] countries = { "Polska", "Czechy", "Estonia", "Austria", "USA", "Niemcy", "Norwegia", "Rosja",
				"Finlandia", "Japonia", "Szwajcaria", "Francja", "Łotwa", "Kazachstan", "Słowenia", "Kanada",
				"Hiszpania", "Portugalia", "Belgia", "Rumunia", "Litwa", "Australia", "Birma", "Kamerun", "Chiny",
				"Ukraina", "Serbia", "Czarnogóra", "Wietnam", "Meksyk", "Tunezja", "Egipt", "RPA", "Zimbabwe", "Dania",
				"Szwecja", "Kosowo", "Albania", "Andora", "Argentyna", "Holandia", "Irlandia", "Islandia", "Kolumbia",
				"Mołdawia", "Monako", "Lichtenstein", "Słowacja", "Anglia", "Szkocja", "Nibylandia", "Urugwaj",
				"Turcja" };
		counter2++;
		return countries[counter2];
	}

	public String randomCity() {
		String[] cities = { "Wrocław", "Warszawa", "Zakopane", "Wisła", "Titisee neustadt", "Berlin", "Oberstdorf",
				"Garmisch partenkirchen", "Innsbruck", "Bischofshofen", "Nizny tagil", "Moskwa", "Wiedeń", "Engelberg",
				"Ruka", "Vikersund", "Planica", "Rasnov", "Iron mountain", "Waszyngton", "Belgrad", "Lahti", "Willinen",
				"Ząb", "Klingenthal", "Oslo", "Trondheim", "Lillehammer", "Sapporo", "Tokio", "Lizbona", "La Valetta",
				"Harrahov", "Sydney", "Kuopio", "Rasnau", "Liberec", "Park city", "Hakuba", "Falun", "Villach",
				"Predazzo", "Monachium", "Madryt", "Barcelona", "Bad mitterndorf", "Karpacz", "Kair", "Buenos aires",
				"Hongkong", "Nowy york", "Paryż", "Bilbao", "Porto", "Seul", "Ankara", "Kraków", "Tirana", "Baku",
				"Czaykowski", "Brotterode", "Helsinki", "Ateny", "Haga", "Bratysława" };
		int random = rand.nextInt(cities.length);
		return cities[random];
	}

	public String randomBirthDate() {
		int year = rand.nextInt(30) + 1970;
		int month = rand.nextInt(12) + 1;
		int day;
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			day = rand.nextInt(31) + 1;
		} else if (month == 2) {
			if (year % 4 == 0) {
				day = rand.nextInt(29) + 1;
			} else {
				day = rand.nextInt(28) + 1;
			}
		} else {
			day = rand.nextInt(30) + 1;
		}
		return Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);
	}

	public int randomNumber(int min, int max) {
		return rand.nextInt(max - min + 1) + min;
	}

	public String randomHillName() {
		String[] hillNames = { "Velikanka", "Ruka tunturi", "Grose schanze", "Titlis1", "Titlis2", "Titlis3", "Russ",
				"Malinka", "Skocznia Adama Małysza", "Aist", "Schattenberg schanze", "Trampoline dal ben",
				"Hochfirstschanze", "Olympiaschanze", "Vogtland arena", "Paul Ausserlainer schanze", "Wielka krokiew",
				"Okurayama", "Muhlenkopfschanze", "Kulm", "Trambulina", "Valea carbunarii", "Salpausselka",
				"Holmenkollenbakken", "Lysgardsbakken", "Granasen", "Bergisel", "Pine mountain", "Inselbergschanze",
				"Renabakkena", "Snow ruyi", "Bogtland", "Vogtland arena", "Śnieżynka", "Kiremirtebleke", "Vosen1",
				"Vosen2", "KalibriUno", "KalibriDos", "KalibriQuatro", "Grande Schanzone", "Aesanbakke", "Sorma0",
				"Olympico national" };
		int random = rand.nextInt(hillNames.length);
		return hillNames[random];
	}

	public String hillName() {
		String[] hillNames = { "Velikanka", "Ruka tunturi", "Grose schanze", "Titlis1", "Titlis2", "Titlis3", "Russ",
				"Malinka", "Skocznia Adama Małysza", "Aist", "Schattenberg schanze", "Trampoline dal ben",
				"Hochfirstschanze", "Olympiaschanze", "Vogtland arena", "Paul Ausserlainer schanze", "Wielka krokiew",
				"Okurayama", "Muhlenkopfschanze", "Kulm", "Trambulina", "Valea carbunarii", "Salpausselka",
				"Holmenkollenbakken", "Lysgardsbakken", "Granasen", "Bergisel", "Pine mountain", "Inselbergschanze",
				"Renabakkena", "Snow ruyi", "Bogtland", "Vogtland arena", "Śnieżynka", "Kiremirtebleke", "Vosen1",
				"Vosen2", "KalibriUno", "KalibriDos", "KalibriQuatro", "Grande Schanzone", "Aesanbakke", "Sorma0",
				"Olympico national" };
		if (counter1 == hillNames.length - 1) {
			counter1 = rand.nextInt(hillNames.length);
		} else {
			counter1++;
		}
		return hillNames[counter1];
	}

	public String dateTime() {
		int year = rand.nextInt(2) + 2020;
		int month = rand.nextInt(12) + 1;
		int day;
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			day = rand.nextInt(31) + 1;
		} else if (month == 2) {
			if (year % 4 == 0) {
				day = rand.nextInt(29) + 1;
			} else {
				day = rand.nextInt(28) + 1;
			}
		} else {
			day = rand.nextInt(30) + 1;
		}
		int hour = rand.nextInt(5) + 13;
		int minute;
		int choose2 = rand.nextInt(4);
		if (choose2 == 0) {
			minute = 30;
		} else if (choose2 == 1) {
			minute = 0;
		} else if (choose2 == 2) {
			minute = 15;
		} else {
			minute = 45;
		}
		if (minute == 0) {
			return Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day) + " "
					+ Integer.toString(hour) + ":00:00";
		}
		return Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day) + " "
				+ Integer.toString(hour) + ":" + Integer.toString(minute) + ":00";

	}

	public int sign() {
		int sign = rand.nextInt(4);
		if (sign == 0) {
			return -1;
		}
		return 1;
	}
}
