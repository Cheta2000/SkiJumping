package com.Mateusz.Lista4.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Server {

	public Server() {
		try (var listener = new ServerSocket(1234)) {
			System.out.println("Uruchomiono serwer...");
			var pool = Executors.newFixedThreadPool(50);
			while (true) {
				pool.execute(new ClientConnection(listener.accept()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
	}

}

class ClientConnection implements Runnable {
	private Socket socket;
	private Scanner input;
	private PrintWriter output;
	private Connection conn;
	String login = "";
	String password = "";

	public ClientConnection(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			setup();
			processQueries();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setup() throws IOException {
		input = new Scanner(socket.getInputStream());
		output = new PrintWriter(socket.getOutputStream(), true);
		while (true) {
			login = input.nextLine();
			password = input.nextLine();
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Skoki?noAccessToProcedureBodies=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
						login, password);
				output.println("Zalogowano");
				System.out.println("Nowy użytkownik: " + login);
				break;
			} catch (SQLException e) {
				output.println("Niepoprawne dane logowania");
				e.printStackTrace();

			}
		}
	}

	private void processQueries() {
		String receivedMessage = "";
		while (true) {
			if (input.hasNextLine()) {
				receivedMessage = input.nextLine();
				try {
					Statement stmt = conn.createStatement();
					CallableStatement cs;
					ResultSet rs;
					Process runtimeProcess;
					if (receivedMessage.startsWith("SELECT") || receivedMessage.startsWith("{CALL")) {
						if (receivedMessage.startsWith("SELECT")) {
							rs = stmt.executeQuery(receivedMessage);
						} else {
							cs = conn.prepareCall(receivedMessage);
							rs = cs.executeQuery();
						}
						ResultSetMetaData rsmd = rs.getMetaData();
						int columns = rsmd.getColumnCount();
						String[] types = new String[columns];
						String score = "";
						for (int i = 1; i <= columns; i++) {
							types[i - 1] = rsmd.getColumnClassName(i);
							score = score + rsmd.getColumnName(i) + " | ";
						}
						output.println(score);
						while (rs.next()) {
							score = "";
							for (int i = 1; i <= columns; i++) {
								if (types[i - 1].contains("Integer")) {
									score = score + rs.getInt(i) + " | ";
								} else if (types[i - 1].contains("String")) {
									score = score + rs.getString(i) + " | ";
								} else if (types[i - 1].contains("Timestamp")) {
									score = score + rs.getTimestamp(i) + " | ";
								} else if (types[i - 1].contains("Date")) {
									score = score + rs.getDate(i) + " | ";
								} else if (types[i - 1].contains("Long")) {
									score = score + rs.getLong(i) + " | ";
								} else if (types[i - 1].contains("Big")) {
									score = score + rs.getBigDecimal(i) + " |";
								}
							}
							output.println(score);
						}
					} else if (receivedMessage.equals("Backup")) {
						String backupCmd = "mysqldump -u" + login + " -p" + password
								+ " --databases Skoki --routines -r /home/mateusz/Lab4/Skokibackup.sql";
						runtimeProcess = Runtime.getRuntime().exec(backupCmd);
						int processComplete = runtimeProcess.waitFor();
						if (processComplete == 0) {
							output.println("Backup zakończony");
						} else {
							output.println("Backup nie powiódł się lub nie masz do niego uprawnień");
						}

					} else if (receivedMessage.equals("Restore")) {
						String[] restoreCmd = { "mysql", "--user=" + login, "--password=" + password, "-e",
								"source " + "/home/mateusz/Lab4/Skokibackup.sql" };
						runtimeProcess = Runtime.getRuntime().exec(restoreCmd);
						int processComplete = runtimeProcess.waitFor();
						if (processComplete == 0) {
							output.println("Restore zakończony");
						} else {
							output.println("Restore nie powiódł się lub nie masz do niego uprawnień");
						}

					} else if (receivedMessage.equals("LOGIN")) {
						PreparedStatement prepStmt = conn
								.prepareStatement("SELECT ID FROM Users WHERE Rola=? AND Login=? AND Hasło=MD5(?)");
						prepStmt.setString(1, input.nextLine());
						prepStmt.setString(2, input.nextLine());
						prepStmt.setString(3, input.nextLine());
						rs = prepStmt.executeQuery();
						if (rs.next()) {
							output.println("UserID: " + rs.getInt(1));
						} else {
							output.println("Nie ma takiego użytkownika");
						}
					}

					else {
						int counter = stmt.executeUpdate(receivedMessage);
						output.println("Zmieniono: " + counter);
					}
				} catch (SQLException e) {
					output.println("Błąd: " + e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

}