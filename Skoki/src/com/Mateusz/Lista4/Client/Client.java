package com.Mateusz.Lista4.Client;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.crypto.URIReferenceException;

import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.Scanner;

public class Client extends JFrame {

	private int userID;
	private Socket socket;
	private Scanner input;
	private PrintWriter output;
	private JFrame frame;
	private JButton button1, button2, button3, button4, button5;
	private JRadioButton radio1, radio2, radio3, radio4;
	private ButtonGroup group;
	private JTextField textField1, textField2, textField3, textField4, textField5, textField6, textField7, textField8,
			textField9, textField10, textField11;
	private JTextArea textArea1;
	private JPasswordField passField;
	private JLabel label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12,
			label13, label14, label15, label16, label17, label18, label19;
	private ComboStore[] store1, store2, store3, store4;
	private JComboBox box1, box2, box3, box4, box5, box6;
	private JCheckBox checkBox1;
	private JScrollPane scroll;
	private JSlider slider;
	private JMenuBar menu;
	private JMenu subMenu1, subMenu2, subMenu3;
	private JMenuItem i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, i19;
	private JDialog dialog1, dialog2, dialog3;
	private String login = "";
	private String password = "";
	private String query = "";
	private int queryType = 0;
	private String procedure = "";
	private String function = "";
	private String[] tables = { "Skoczkowie", "Reprezentacje", "Obsada", "Trenerzy", "Konkursy", "Skocznie" };
	private int[] joins = { 0, 0, 0, 0, 0, 0 };
	private String[] joinTypes = { "JOIN", "LEFT JOIN", "RIGHT JOIN", "CROSS JOIN" };
	private String joinType = "";
	private String[] logins = { "root", "Dyrektor", "Trener", "Szef zawodow", "Widz" };
	private String[] atributes = { "*", "Skoczkowie.ID", "Skoczkowie.Imię", "Skoczkowie.Nazwisko",
			"Skoczkowie.Narodowość", "`Data urodzenia`", "Skoczkowie.Reprezentacja", "Punkty", "`Liczba zwycięstw`",
			"Reprezentacje.Nazwa", "`Skocznia domowa`", "`Liczba zawodników`", "`Suma punktów`", "Obsada.Reprezentacja",
			"Trener", "Trenerzy.ID", "Trenerzy.Imię", "Trenerzy.Nazwisko", "Trenerzy.Narodowość", "Wiek", "Skocznia",
			"Termin", "Zwycięzca", "`2 miejsce`", "`3 miejsce`", "Nagroda", "Skocznie.ID", "Skocznie.Nazwa", "Kraj",
			"Miasto", "Liczba konkursów", "Rozmiar", "Rekord" };
	private String[] proceduresNames = { "WypiszKonkursyMiejsce", "WypiszSkoczkowMiejsce", "WypiszReprezentacjeMiejsce",
			"WypiszSkocznieMiasto", "PodopieczniTrenera", "LiczbaTrenerowReprezentacji", "MlodyStary",
			"DaneOSkoczniachKraj", "KonkursyWKrajuSkoczka", "UstawRekord", "UstawMiejsca", "Klasyfikacja",
			"WyplacNagrody", "NajwiecejPodiumSkocznia" };
	private int[] procedureNoArgs = { 3, 1, 1, 1, 2, 1, 2, 2, 2, 2, 5, 1, 1, 2 };
	private int procedureNumber = 0;
	private String[] functionsNames = { "SumowanieNagrod", "SredniaWiekuReprezentacji", "LiczbaKonkursowKraj" };
	private int[] functionNoArgs = { 2, 1, 1 };
	private int functionNumber = 0;
	private Boolean value = false;

	public Client() throws Exception {
		socket = new Socket("localhost", 1234);
		input = new Scanner(socket.getInputStream());
		output = new PrintWriter(socket.getOutputStream(), true);
		userID = -1;
		login();
		loginOnAccount();
		createDialogs();
		run();
		connect();
	}

	private void connect() {
		String receivedMessage = "";
		try {
			while (true) {
				receivedMessage = input.nextLine();
				if (receivedMessage.contains("Błąd")) {
					textArea1.setForeground(Color.RED);
					if (receivedMessage.contains("denied")) {
						textArea1.append("Nie masz uprawnień do tej operacji");
					} else {
						textArea1.append(receivedMessage);
					}
				} else if (receivedMessage.contains("UserID")) {
					userID = Integer.parseInt(receivedMessage.substring(8));
					textArea1.setForeground(Color.BLACK);
					textArea1.setText("Zalogowano");
					dialog3.setVisible(false);
				} else if (receivedMessage.contains("Nie ma")) {
					label16.setText(receivedMessage);
				} else {
					textArea1.setForeground(Color.BLACK);
					textArea1.append(receivedMessage + "\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void login() {
		label1 = new JLabel("Login:");
		label2 = new JLabel("Hasło:");
		label16 = new JLabel("WITAJ!");
		label1.setBounds(20, 170, 100, 30);
		label2.setBounds(20, 220, 100, 30);
		label16.setBounds(20, 20, 500, 100);
		label16.setFont(new Font("Serif", Font.BOLD, 20));
		box4 = new JComboBox(logins);
		box4.setBounds(120, 170, 300, 30);
		passField = new JPasswordField();
		passField.setBounds(120, 220, 300, 30);
		button1 = new JButton("Zaloguj");
		button1.setBounds(100, 300, 300, 100);
		button1.setBackground(Color.CYAN);
		button1.addActionListener(new Listener());
		dialog1 = new JDialog(this, "Logowanie", true);
		dialog1.setResizable(false);
		dialog1.setLayout(null);
		dialog1.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		dialog1.getContentPane().setBackground(Color.LIGHT_GRAY);
		dialog1.add(box4);
		dialog1.add(passField);
		dialog1.add(label1);
		dialog1.add(label2);
		dialog1.add(label16);
		dialog1.add(button1);
		dialog1.setBounds(700, 350, 500, 500);
		dialog1.setVisible(true);
	}

	private void loginOnAccount() {
		textField1 = new JTextField();
		textField1.setBounds(120, 170, 300, 30);
		dialog3 = new JDialog(this, "Logowanie na konto", true);
		dialog3.setResizable(false);
		dialog3.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		dialog3.setLayout(null);
		dialog3.setBounds(700, 350, 500, 500);
		dialog3.getContentPane().setBackground(Color.DARK_GRAY);
		label1.setForeground(Color.WHITE);
		label2.setForeground(Color.WHITE);
		label16.setForeground(Color.WHITE);
		dialog3.add(passField);
		dialog3.add(textField1);
		dialog3.add(label1);
		dialog3.add(label2);
		dialog3.add(label16);
		dialog3.add(button1);
	}

	private void createDialogs() {
		dialog2 = new JDialog(this, "Parametry", true);
		dialog2.setDefaultCloseOperation(HIDE_ON_CLOSE);
		dialog2.setResizable(false);
		dialog2.setLayout(null);
		dialog2.getContentPane().setBackground(Color.ORANGE);
		dialog2.setBounds(700, 350, 500, 500);
		button5 = new JButton("Uruchom");
		button5.setBackground(Color.RED);
		button5.setBounds(0, 365, 500, 100);
		button5.addActionListener(new Listener());
		textField6 = new JTextField();
		textField6.setBounds(20, 120, 100, 30);
		textField7 = new JTextField();
		textField7.setBounds(200, 120, 100, 30);
		textField8 = new JTextField();
		textField8.setBounds(380, 120, 100, 30);
		textField9 = new JTextField();
		textField9.setBounds(110, 240, 100, 30);
		textField10 = new JTextField();
		textField10.setBounds(290, 240, 100, 30);
		label10 = new JLabel("Parametr 1");
		label11 = new JLabel("Parametr 2");
		label12 = new JLabel("Parametr 3");
		label13 = new JLabel("Parametr 4");
		label14 = new JLabel("Parametr 5");
		label10.setFont(new Font("Serif", Font.BOLD, 16));
		label11.setFont(new Font("Serif", Font.BOLD, 16));
		label12.setFont(new Font("Serif", Font.BOLD, 16));
		label13.setFont(new Font("Serif", Font.BOLD, 16));
		label14.setFont(new Font("Serif", Font.BOLD, 16));
		label10.setBounds(20, 80, 100, 20);
		label11.setBounds(200, 80, 100, 20);
		label12.setBounds(380, 80, 100, 20);
		label13.setBounds(110, 200, 100, 20);
		label14.setBounds(290, 200, 100, 20);
		dialog2.add(textField6);
		dialog2.add(textField7);
		dialog2.add(textField8);
		dialog2.add(textField9);
		dialog2.add(textField10);
		dialog2.add(label10);
		dialog2.add(label11);
		dialog2.add(label12);
		dialog2.add(label13);
		dialog2.add(label14);
		dialog2.add(button5);
	}

	private void run() {
		frame = new JFrame("Skoki: " + login);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setBounds(500, 0, 1000, 1000);
		frame.setLayout(null);
		frame.getContentPane().setBackground(Color.PINK);
		frame.setResizable(false);
		label3 = new JLabel("Pola");
		label4 = new JLabel("Tabele");
		label5 = new JLabel("Operacja");
		label6 = new JLabel("<html>Funkcje agregujące, set</html>");
		label7 = new JLabel("Warunki i wartości");
		label8 = new JLabel("Grupowanie");
		label9 = new JLabel("Sortowanie");
		label15 = new JLabel();
		label17 = new JLabel("Typ złączenia");
		label18 = new JLabel("Warunki na grupie");
		label19 = new JLabel("Typ sortowania i inne pola");
		label3.setFont(new Font("Serif", Font.BOLD, 16));
		label4.setFont(new Font("Serif", Font.BOLD, 16));
		label5.setFont(new Font("Serif", Font.BOLD, 16));
		label6.setFont(new Font("Serif", Font.BOLD, 16));
		label7.setFont(new Font("Serif", Font.BOLD, 16));
		label8.setFont(new Font("Serif", Font.BOLD, 16));
		label9.setFont(new Font("Serif", Font.BOLD, 16));
		label15.setFont(new Font("Serif", Font.BOLD, 16));
		label17.setFont(new Font("Serif", Font.BOLD, 16));
		label18.setFont(new Font("Serif", Font.BOLD, 16));
		label19.setFont(new Font("Serif", Font.BOLD, 16));
		label3.setBounds(325, 20, 100, 20);
		label4.setBounds(595, 20, 100, 20);
		label5.setBounds(60, 10, 100, 20);
		label6.setBounds(245, 115, 300, 40);
		label7.setBounds(790, 20, 200, 20);
		label8.setBounds(150, 210, 200, 20);
		label9.setBounds(550, 210, 200, 20);
		label15.setBounds(10, 10, 500, 20);
		label17.setBounds(565, 125, 200, 20);
		label18.setBounds(120, 300, 200, 20);
		label19.setBounds(485, 300, 250, 20);
		label15.setForeground(Color.BLUE);
		textArea1 = new JTextArea();
		textArea1.setFont(new Font("Verdana", Font.BOLD, 17));
		textField2 = new JTextField();
		textField2.setBounds(200, 160, 300, 30);
		textField3 = new JTextField();
		textField3.setBounds(750, 50, 240, 30);
		textField4 = new JTextField();
		textField4.setBounds(450, 340, 300, 30);
		textField5 = new JTextField();
		textField5.setBounds(800, 180, 70, 70);
		textField5.addKeyListener(new TextListener());
		textField11 = new JTextField();
		textField11.setBounds(50, 340, 300, 30);
		checkBox1 = new JCheckBox("Limit");
		checkBox1.setBounds(800, 130, 70, 30);
		button2 = new JButton("Wykonaj kopię zapasową");
		button2.setBounds(0, 400, 500, 100);
		button2.setBackground(Color.GREEN);
		button2.addActionListener(new Listener());
		button3 = new JButton("Wczytaj kopię zapasową");
		button3.setBounds(500, 400, 500, 100);
		button3.setBackground(Color.CYAN);
		button3.addActionListener(new Listener());
		button4 = new JButton("Zatwierdź i wykonaj");
		button4.setBounds(800, 300, 200, 100);
		button4.setBackground(Color.RED);
		button4.addActionListener(new Listener());
		scroll = new JScrollPane(textArea1);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(0, 500, 1000, 442);
		slider = new JSlider(0, 200, 0);
		slider.setMinorTickSpacing(10);
		slider.setMajorTickSpacing(200);
		slider.setLabelTable(slider.createStandardLabels(200));
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setPaintTrack(true);
		slider.setOrientation(SwingConstants.VERTICAL);
		slider.setBounds(890, 130, 60, 160);
		slider.addChangeListener(new SliderListener());
		radio1 = new JRadioButton("SELECT");
		radio1.setBounds(50, 50, 100, 35);
		radio2 = new JRadioButton("INSERT");
		radio2.setBounds(50, 85, 100, 35);
		radio3 = new JRadioButton("UPDATE");
		radio3.setBounds(50, 120, 100, 35);
		radio4 = new JRadioButton("DELETE");
		radio4.setBounds(50, 155, 100, 35);
		group = new ButtonGroup();
		group.add(radio1);
		group.add(radio2);
		group.add(radio3);
		group.add(radio4);
		store1 = new ComboStore[33];
		for (int i = 0; i < 33; i++) {
			store1[i] = new ComboStore(atributes[i], value);
		}
		box1 = new JComboBox(store1);
		box1.setRenderer(new CheckComboRenderer());
		box1.addActionListener(new ComboListener());
		box1.setBounds(200, 50, 300, 30);
		store2 = new ComboStore[6];
		for (int i = 0; i < 6; i++) {
			store2[i] = new ComboStore(tables[i], value);
		}
		box2 = new JComboBox(store2);
		box2.setRenderer(new CheckComboRenderer());
		box2.addActionListener(new ComboListener());
		box2.setBounds(550, 50, 150, 30);
		store3 = new ComboStore[32];
		for (int i = 1; i < 33; i++) {
			store3[i - 1] = new ComboStore(atributes[i], value);
		}
		box3 = new JComboBox(store3);
		box3.setRenderer(new CheckComboRenderer());
		box3.addActionListener(new ComboListener());
		box3.setBounds(50, 250, 300, 30);
		box5 = new JComboBox(joinTypes);
		box5.setBounds(550, 160, 150, 30);
		store4 = new ComboStore[32];
		for (int i = 1; i < 33; i++) {
			store4[i - 1] = new ComboStore(atributes[i], value);
		}
		box6 = new JComboBox(store4);
		box6.setRenderer(new CheckComboRenderer());
		box6.addActionListener(new ComboListener());
		box6.setBounds(450, 250, 300, 30);
		menu = new JMenuBar();
		subMenu1 = new JMenu("Procedury");
		subMenu2 = new JMenu("Funkcje");
		subMenu3 = new JMenu("Logowanie");
		i1 = new JMenuItem("Wypisz konkurs po skoczku i zajętym miejscu (VVI)");
		i1.addActionListener(new Listener());
		i2 = new JMenuItem("Wypisz skoczków po maksymalnej ilości miejsc (I)");
		i2.addActionListener(new Listener());
		i3 = new JMenuItem("Wypisz reprezentacje po maksymalnej ilości miejsc (I)");
		i3.addActionListener(new Listener());
		i4 = new JMenuItem("Wypisz skocznie w mieście (V)");
		i4.addActionListener(new Listener());
		i5 = new JMenuItem("Wypisz podopiecznych trenera (VV)");
		i5.addActionListener(new Listener());
		i6 = new JMenuItem("Wypisz liczbe trenerów po reprezentacji (V)");
		i6.addActionListener(new Listener());
		i7 = new JMenuItem("Sumuj nagrody zawodnika (VV)");
		i7.addActionListener(new Listener());
		i8 = new JMenuItem("Wypisz średnia wieku reprezentacji (V)");
		i8.addActionListener(new Listener());
		i9 = new JMenuItem("Wypisz najmłodszego/najstarszego z reprezentacji (AV)");
		i9.addActionListener(new Listener());
		i10 = new JMenuItem("Wypisz dane o skoczniach w kraju (AV)");
		i10.addActionListener(new Listener());
		i11 = new JMenuItem("Wypisz liczbę konkursów w kraju (V)");
		i11.addActionListener(new Listener());
		i12 = new JMenuItem("Wypisz konkursy w kraju skoczka (VV)");
		i12.addActionListener(new Listener());
		i13 = new JMenuItem("Ustaw rekord skoczni (VI)");
		i13.addActionListener(new Listener());
		i14 = new JMenuItem("Ustaw miejsca w konkursie (IIIIT)");
		i14.addActionListener(new Listener());
		i15 = new JMenuItem("Wypisz klasyfikację generalną (V)");
		i15.addActionListener(new Listener());
		i16 = new JMenuItem("Wypłać nagrody (I)");
		i16.addActionListener(new Listener());
		i17 = new JMenuItem("Wypisz ulubioną skocznie po skoczku (VV)");
		i17.addActionListener(new Listener());
		i18 = new JMenuItem("Loguj na konto");
		i18.addActionListener(new Listener());
		i19 = new JMenuItem("Wyloguj");
		i19.addActionListener(new Listener());
		subMenu1.add(i1);
		subMenu1.add(i2);
		subMenu1.add(i3);
		subMenu1.add(i4);
		subMenu1.add(i5);
		subMenu1.add(i6);
		subMenu2.add(i7);
		subMenu2.add(i8);
		subMenu1.add(i9);
		subMenu1.add(i10);
		subMenu2.add(i11);
		subMenu1.add(i12);
		subMenu1.add(i13);
		subMenu1.add(i14);
		subMenu1.add(i15);
		subMenu1.add(i16);
		subMenu1.add(i17);
		subMenu3.add(i18);
		subMenu3.add(i19);
		menu.add(subMenu1);
		menu.add(subMenu2);
		menu.add(subMenu3);
		frame.setJMenuBar(menu);
		frame.add(label3);
		frame.add(label4);
		frame.add(label5);
		frame.add(label6);
		frame.add(label7);
		frame.add(label8);
		frame.add(label9);
		frame.add(label17);
		frame.add(label18);
		frame.add(label19);
		frame.add(textField2);
		frame.add(textField3);
		frame.add(textField4);
		frame.add(textField5);
		frame.add(textField11);
		frame.add(radio1);
		frame.add(radio2);
		frame.add(radio3);
		frame.add(radio4);
		frame.add(button2);
		frame.add(button3);
		frame.add(button4);
		frame.add(scroll);
		frame.add(slider, "Wartość");
		frame.add(checkBox1);
		frame.add(box1);
		frame.add(box2);
		frame.add(box3);
		frame.add(box5);
		frame.add(box6);
		frame.setVisible(true);
	}

	private void command() {
		dialog2.add(label15);
		dialog2.setVisible(true);

	}

	public void reset() {
		query = "";
		procedure = "";
		procedureNumber = 0;
		functionNumber = 0;
		textField2.setText("");
		textField6.setText("");
		textField7.setText("");
		textField8.setText("");
		textField9.setText("");
		textField10.setText("");
		textField11.setText("");
		dialog2.setVisible(false);
		queryType = 0;
		joinType = "";
		slider.setValue(0);
		group.clearSelection();
		textField2.setText("");
		textField3.setText("");
		textField4.setText("");
		textField5.setText("");
		box1.setSelectedIndex(1);
		box1.setSelectedIndex(0);
		box2.setSelectedIndex(1);
		box2.setSelectedIndex(0);
		box3.setSelectedIndex(1);
		box3.setSelectedIndex(0);
		box5.setSelectedIndex(0);
		box6.setSelectedIndex(1);
		box6.setSelectedIndex(0);
		for (int i = 0; i < 33; i++) {
			store1[i].state = false;
		}
		for (int i = 0; i < 6; i++) {
			store2[i].state = false;
			joins[i] = 0;
		}
		for (int i = 0; i < 32; i++) {
			store3[i].state = false;
			store4[i].state = false;
		}
		checkBox1.setSelected(false);
	}

	public static void main(String[] args) {
		try {
			Client client = new Client();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int counter = 0;
			if (e.getActionCommand().equals("Zaloguj")) {
				if (dialog1.isVisible()) {
					login = (String) box4.getSelectedItem();
					output.println(login);
					output.println(passField.getText());
					passField.setText("");
					if (input.nextLine().equals("Zalogowano")) {
						if (login.equals("root")) {
							userID = 0;
						}
						dialog1.setVisible(false);
					} else {
						label16.setForeground(Color.RED);
						label16.setText("Niepoprawne dane logowania");
						passField.setText("");
					}
				} else {
					output.println("LOGIN");
					output.println(login);
					output.println(textField1.getText());
					output.println(passField.getText());
					textField1.setText("");
					passField.setText("");
				}
			} else if (e.getActionCommand().equals("Zatwierdź i wykonaj")) {
				query = "";
				counter = 0;
				if (radio1.isSelected()) {
					query = "SELECT ";
					queryType = 1;
				} else if (radio2.isSelected()) {
					query = "INSERT";
					queryType = 2;
				} else if (radio3.isSelected()) {
					query = "UPDATE";
					queryType = 3;
				} else if (radio4.isSelected()) {
					query = "DELETE";
					queryType = 4;
				}
				if (queryType == 1) {
					for (int i = 0; i < 33; i++) {
						if (store1[i].state == true) {
							if (counter > 0) {
								query = query + ",";
							}
							query = query + " " + store1[i].id;
							counter++;
						}
					}

					if (!(textField2.getText().equals(""))) {
						if (counter > 0) {
							query = query + ", ";
						}
						query = query + textField2.getText();
						counter = 0;
					}
				}
				if (queryType == 1 || queryType == 4) {
					query = query + " FROM";
				} else if (queryType == 3) {
					query = query + "";
				} else if (queryType == 2) {
					query = query + " INTO";
				}
				counter = 0;
				joinType = (String) box5.getSelectedItem();
				if (joinType.equals("CROSS JOIN")) {
					for (int i = 0; i < 6; i++) {
						if (store2[i].state == true) {
							if (counter == 0) {
								query = query + " " + store2[i].id;
							} else {
								query = query + " CROSS JOIN " + store2[i].id;
							}
							counter++;
						}
					}
				} else {
					for (int i = 0; i < 6; i++) {
						if (store2[i].state == true) {
							counter++;
							if (counter == 1) {
								query = query + " " + store2[i].id;
							}
							joins[i] = 1;
							switch (i) {
							case 1:
								if (joins[0] == 1) {

									query = query + " " + joinType + " " + store2[1].id
											+ " ON Skoczkowie.Reprezentacja=Reprezentacje.Nazwa";
								}

								break;
							case 2:
								if (joins[1] == 1) {
									query = query + " " + joinType + " " + store2[2].id
											+ " ON Reprezentacje.Nazwa=Obsada.Reprezentacja";
								}
								break;
							case 3:
								if (joins[2] == 1) {
									query = query + " " + joinType + " " + store2[3].id
											+ " ON Obsada.Trener=Trenerzy.ID";
								}
								break;
							case 4:
								if (joins[0] == 1) {
									query = query + " " + joinType + " " + store2[4].id
											+ " ON Skoczkowie.ID=Konkursy.Zwycięzca OR Skoczkowie.ID=Konkursy.`2 miejsce` OR Skoczkowie.ID=Konkursy.`3 miejsce`";
								}
								break;
							case 5:
								if (joins[4] == 1) {
									query = query + " " + joinType + " " + store2[5].id
											+ " ON Konkursy.Skocznia=Skocznie.ID";
								}
								if (joins[1] == 1) {
									query = query + " AND Reprezentacje.`Skocznia domowa`=Skocznie.ID";
									break;
								}
							}
						}
					}
				}
				counter = 0;

				if (queryType == 3) {
					query = query + " SET";
				}
				if (queryType == 2) {
					query = query + "(";
					for (int i = 0; i < 33; i++) {
						if (store1[i].state == true) {
							if (counter > 0) {
								query = query + ",";
							}
							counter++;
							query = query + " " + store1[i].id;
						}
					}
					query = query + ")";
					// query = query + " " + textField2.getText();
				}

				if (queryType == 3) {
					String[] values = textField2.getText().split(",");
					for (int i = 0; i < 33; i++) {
						if (store1[i].state == true) {
							if (counter > 0) {
								query = query + ",";
							}
							query = query + " " + store1[i].id + "=";
							query = query + values[counter];
							counter++;
						}
					}
				}
				counter = 0;
				if (!textField3.getText().equals("")) {
					if (queryType == 2) {
						query = query + " VALUES " + textField3.getText();
					} else {
						query = query + " WHERE " + textField3.getText();
					}
				}
				if (queryType == 1) {
					for (int i = 0; i < 32; i++) {
						if (store3[i].state == true) {
							if (counter == 0) {
								query = query + " GROUP BY";
							}
							if (counter > 0) {
								query = query + ",";
							}
							counter++;
							query = query + " " + store3[i].id;
						}
					}
					if (!(textField11.getText().equals(""))) {
						query = query + " HAVING " + textField11.getText();
					}
					counter = 0;
					String[] values = textField4.getText().split(",");
					for (int i = 0; i < 32; i++) {
						if (store4[i].state == true) {
							if (counter == 0) {
								query = query + " ORDER BY";
							}
							if (counter > 0) {
								query = query + ",";
							}
							while (values[counter].length() > 4) {
								query = query + " " + values[counter] + ",";
								counter++;
								if (counter == values.length) {
									break;
								}
							}
							query = query + " " + store4[i].id + " " + values[counter];
							counter++;
						}
					}
					for (int i = counter; i < values.length; i++) {
						if (!(values[counter].equals(""))) {
							if (counter > 0) {
								query = query + ",";
							}
							query = query + " " + values[counter];
						}
					}
					if (checkBox1.isSelected() && !(textField5.getText().equals(""))) {
						query = query + " LIMIT " + textField5.getText();
					}
				}
				if (queryType != 1 && userID < 0 && !(login.equals("Widz"))) {
					textArea1.setForeground(Color.BLACK);
					textArea1.setText("Musisz się zalogować do tej operacji");
				} else {
					output.println(query);
					textArea1.setText("");
					textArea1.append("Zapytanie: " + query + "\n");
				}
				reset();
			} else if (e.getActionCommand().equals("Wykonaj kopię zapasową")) {
				if (userID == 0) {
					textArea1.setText("");
					output.println("Backup");
				} else {
					textArea1.setText("Nie masz uprawnień do backup'u");
				}
			} else if (e.getActionCommand().equals("Wczytaj kopię zapasową")) {
				if (userID == 0) {
					textArea1.setText("");
					output.println("Restore");
				} else {
					textArea1.setText("Nie masz uprawnień do restore'a");
				}
			} else if (e.getActionCommand().equals("Uruchom")) {
				counter = 0;
				if (userID >= 0 || login.equals("Widz")) {
					if (procedureNumber > 0) {
						procedure = "{CALL " + proceduresNames[procedureNumber - 1] + "(";
						if (counter < procedureNoArgs[procedureNumber - 1]) {
							counter++;
							procedure = procedure + textField6.getText();
						}
						if (counter < procedureNoArgs[procedureNumber - 1]) {
							counter++;
							procedure = procedure + "," + textField7.getText();
						}
						if (counter < procedureNoArgs[procedureNumber - 1]) {
							counter++;
							procedure = procedure + "," + textField8.getText();
						}
						if (counter < procedureNoArgs[procedureNumber - 1]) {
							counter++;
							procedure = procedure + "," + textField9.getText();
						}
						if (counter < procedureNoArgs[procedureNumber - 1]) {
							counter++;
							procedure = procedure + "," + textField10.getText();
						}
						procedure = procedure + ")}";
						output.println(procedure);
						textArea1.setText("Zapytanie: " + procedure + "\n");
					} else if (functionNumber > 0) {
						function = "SELECT " + functionsNames[functionNumber - 1] + "(";
						if (counter < functionNoArgs[functionNumber - 1]) {
							counter++;
							function = function + textField6.getText();
						}
						if (counter < functionNoArgs[functionNumber - 1]) {
							counter++;
							function = function + "," + textField7.getText();
						}
						function = function + ")";
						output.println(function);
						textArea1.setText("");
					}
				} else {
					textArea1.setForeground(Color.BLACK);
					textArea1.setText("Musisz się zalogować do tej operacji");
				}
				reset();

			} else if (e.getSource() == i18) {
				dialog3.setVisible(true);
			} else if (e.getSource() == i19) {
				if (userID > 0) {
					textArea1.setForeground(Color.BLACK);
					textArea1.setText("Wylogowano");
					userID = -1;
				}
			} else {
				label15.setText(e.getActionCommand());
				if (e.getSource() == i1) {
					procedureNumber = 1;
				}
				if (e.getSource() == i2) {
					procedureNumber = 2;
				}
				if (e.getSource() == i3) {
					procedureNumber = 3;
				}
				if (e.getSource() == i4) {
					procedureNumber = 4;
				}
				if (e.getSource() == i5) {
					procedureNumber = 5;
				}
				if (e.getSource() == i6) {
					procedureNumber = 6;
				}
				if (e.getSource() == i7) {
					functionNumber = 1;
				}
				if (e.getSource() == i8) {
					functionNumber = 2;
				}
				if (e.getSource() == i9) {
					procedureNumber = 7;
				}
				if (e.getSource() == i10) {
					procedureNumber = 8;
				}
				if (e.getSource() == i11) {
					functionNumber = 3;
				}
				if (e.getSource() == i12) {
					procedureNumber = 9;
				}
				if (e.getSource() == i13) {
					procedureNumber = 10;
				}
				if (e.getSource() == i14) {
					procedureNumber = 11;
				}
				if (e.getSource() == i15) {
					procedureNumber = 12;
				}
				if (e.getSource() == i16) {
					procedureNumber = 13;
				}
				if (e.getSource() == i17) {
					procedureNumber = 14;
				}
				command();
			}

		}
	}

	class CheckComboRenderer implements ListCellRenderer {
		JCheckBox checkBox;

		public CheckComboRenderer() {
			checkBox = new JCheckBox();
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			ComboStore store = (ComboStore) value;
			checkBox.setText(store.id);
			checkBox.setSelected(((Boolean) store.state).booleanValue());
			checkBox.setBackground(isSelected ? Color.red : Color.white);
			checkBox.setForeground(isSelected ? Color.white : Color.black);
			return checkBox;
		}
	}

	class ComboStore {
		String id;
		Boolean state;

		public ComboStore(String id, Boolean state) {
			this.id = id;
			this.state = state;
		}
	}

	class ComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			ComboStore store = (ComboStore) cb.getSelectedItem();
			CheckComboRenderer ccr = (CheckComboRenderer) cb.getRenderer();
			ccr.checkBox.setSelected((store.state = !store.state));
		}
	}

	class SliderListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			textField5.setText(String.valueOf(slider.getValue()));

		}
	}

	class TextListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			String typed = textField5.getText();
			if (!typed.matches("\\d+") || typed.length() > 3) {
				return;
			}
			int value = Integer.parseInt(typed);
			if (value <= 200) {
				slider.setValue(value);
			}
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}
	}

}
