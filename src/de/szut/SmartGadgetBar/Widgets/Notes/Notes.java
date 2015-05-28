package de.szut.SmartGadgetBar.Widgets.Notes;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import de.szut.SmartGadgetBar.Model.WidgetInterface;
import de.szut.SmartGadgetBar.GUI.AbstractWidgetPanel;
import de.szut.SmartGadgetBar.GUI.Notes_UI;

/**
 * Die Notizen
 * 
 * @author Simeon Kublenz
 *
 */
public class Notes implements WidgetInterface {

	public static final String DBPath = "databasepath";
	private Notes_UI ui;
	private Properties properties;
	private Connection connection;
	private Statement statement;
	String[][] datesAndNames;
	String[] options;

	/**
	 * Erzeugt ein neues Objekt des Notes Widget
	 */
	public Notes() {
		ui = new Notes_UI(this);
		loadProperties();
		loadDatabase();
		ui.setText(getLastNote());
	}

	/**
	 * Stellt die Verbindung zu der Datenbank her
	 */
	public void loadDatabase() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:"
					+ properties.getProperty(DBPath));
			statement = connection.createStatement();
			statement
					.executeUpdate("CREATE TABLE IF NOT EXISTS notes (Date DATE NOT NULL, Name TINYTEXT, Text TEXT NOT NULL)");
		} catch (SQLException e) {
			try {
				connection = DriverManager.getConnection("jdbc:sqlite:"
						+ new File("defaultnotes.db3").getAbsolutePath());
				statement = connection.createStatement();
				statement
						.executeUpdate("CREATE TABLE IF NOT EXISTS notes (Date DATE NOT NULL, Name TINYTEXT, Text TEXT NOT NULL)");
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
	}

	/**
	 * Gibt die neuste Notiz zurück
	 * 
	 * @return
	 */
	private String getLastNote() {
		try {
			ResultSet resultSet = statement
					.executeQuery("SELECT Text FROM notes;");
			String text = "";
			while (resultSet.next()) {
				text = resultSet.getString(1);
			}
			return text;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Speichert eine Notiz in der Datenbank
	 * 
	 * @param note
	 *            die zu speichernde Notiz
	 */
	public void saveNote(String note) {
		try {
			statement
					.executeUpdate("INSERT INTO notes (Date,Text) VALUES (CURRENT_DATE,'"
							+ note + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Speichert eine Notiz mit Titel in der Datenbank
	 * 
	 * @param note
	 *            die zu speichernde Notiz
	 * @param name
	 *            Der Titel der Notiz
	 */
	public void saveNote(String note, String name) {
		try {
			statement.executeUpdate("INSERT INTO notes VALUES (CURRENT_DATE,'"
					+ name + "','" + note + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gibt die Initialien aller Notizen zurück
	 * 
	 * @return
	 */
	public String[] getDatesAndNames() {
		ArrayList<String[]> dates = new ArrayList<String[]>();
		try {
			ResultSet resultSet = statement
					.executeQuery("SELECT Date, Name FROM notes;");
			while (resultSet.next()) {
				dates.add(new String[2]);
				dates.get(dates.size() - 1)[0] = resultSet.getString(1);
				dates.get(dates.size() - 1)[1] = resultSet.getString(2);
			}
			datesAndNames = dates.toArray(new String[dates.size()][]);
			options = new String[datesAndNames.length];
			for (int i = 0; i < datesAndNames.length; i++) {
				options[i] = datesAndNames[i][0] + "   ";
				if (datesAndNames[i][1] != null) {
					options[i] += datesAndNames[i][1];
				}
			}
			return options;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gibt die Notiz einer bestimmten Initialie zurück
	 * 
	 * @param option
	 *            Die Initieale der gewünschten Notiz
	 * @return die gewünschte Notiz
	 */
	public String getNoteFromOption(String option) {
		for (int i = 0; i < options.length; i++) {
			if (option == options[i]) {
				if (datesAndNames[i][1] == null) {
					return getNote(datesAndNames[i][0]);
				} else {
					return getNote(datesAndNames[i][0], datesAndNames[i][1]);
				}
			}
		}
		return null;
	}

	/**
	 * Gibt die erste Notiz mit dem übergebenen Datum zurück
	 * 
	 * @param date
	 *            Das gewünschte Datum
	 * @return Die geladene Notiz
	 */
	public String getNote(String date) {
		try {
			ResultSet resultSet = statement
					.executeQuery("SELECT Text FROM notes WHERE Date = '"
							+ date + "';");
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gibt die erste Notiz mit dem übergebenen Datum und dem Titel zurück
	 * 
	 * @param date
	 *            Das gewünschte Datum
	 * @param name
	 *            Der gewünschte Titel
	 * @return Die geladene Notiz
	 */
	public String getNote(String date, String name) {
		try {
			ResultSet resultSet = statement
					.executeQuery("SELECT Text FROM notes WHERE Date = '"
							+ date + "' AND Name = '" + name + "';");
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Beendet die Verbindung zur Datenbank
	 */
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Speichert eine Notiz als Txt
	 * @param file die Datei in der gespeichert werden soll
	 * @param text die Notiz ide gespeichert werden soll
	 */
	public void saveTxt(File file, String text) {
		try {
			FileWriter writer = new FileWriter(file, false);
			writer.write(text);
			writer.flush();
			writer.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public AbstractWidgetPanel getPanel() {
		return ui;
	}

	@Override
	public String getWidgetName() {
		return "Notes";
	}

	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
		saveProperties();
	}

	@Override
	public Properties getProperties() {
		return properties;
	}

	@Override
	public Properties getDefaultProperties() {
		Properties defaultProps = new Properties();
		defaultProps.setProperty("databasepath",
				new File("defaultnotes.db3").getAbsolutePath());
		return defaultProps;
	}

	@Override
	public void close() {
		saveNote(ui.getText());
		closeConnection();
	}
}
