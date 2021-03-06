package de.szut.SmartGadgetBar.Widgets.Notes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JPanel;

import de.szut.SmartGadgetBar.Model.WidgetInterface;
import de.szut.SmartGadgetBar.GUI.Notes_UI;

public class Notes implements WidgetInterface {
	
	private Notes_UI ui;
	private Properties properties;
	private File database;
	private Connection connection;
	private Statement statement;
	String[][] datesAndNames;
	String[] options;
	
	public void loadDatabase() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + database.getPath());
			statement = connection.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS notes (Date DATE NOT NULL, Name TINYTEXT, Text TEXT NOT NULL)");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Notes() {
		ui = new Notes_UI(this);
		try {
			database = new File(new File(".").getCanonicalPath() + "/notes.db3");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		loadDatabase();
	}
	
	public void saveNote(String note) {
		try {
			statement.executeUpdate("INSERT INTO notes (Date,Text) VALUES (CURRENT_DATE,'" + note + "');");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void saveNote(String note, String name) {
		try {
			statement.executeUpdate("INSERT INTO notes VALUES (CURRENT_DATE,'" + name + "','" + note + "');");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String[] getDatesAndNames() {
		ArrayList<String[]> dates = new ArrayList<String[]>();
		try {
			ResultSet resultSet = statement.executeQuery("SELECT Date, Name FROM notes;");
			while(resultSet.next()) {
				dates.add(new String[2]);
				dates.get(dates.size()-1)[0] = resultSet.getString(1);
				dates.get(dates.size()-1)[1] = resultSet.getString(2);
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
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getNoteFromOption(String option) {
		for (int i = 0; i < options.length; i++) {
			if (option == options[i]) {
				if (datesAndNames[i][1] == null) {
					return getNote(datesAndNames[i][0]);
				}
				else {
					return getNote(datesAndNames[i][0], datesAndNames[i][1]);
				}
			}
		}
		return null;
	}
	
	public String getNote(String date) {
		try {
			ResultSet resultSet = statement.executeQuery("SELECT Text FROM notes WHERE Date = '" + date + "';");
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getNote(String date, String name) {
		try {
			System.out.println("SELECT Text FROM notes WHERE Date = '" + date + "' AND Name = '" +  name + "';");
			ResultSet resultSet = statement.executeQuery("SELECT Text FROM notes WHERE Date = '" + date + "' AND Name = '" +  name + "';");
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void closeConnection() {
		try {
			connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
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
	public JPanel getPanel() {
		return ui;
	}
	
	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	@Override
	public Properties getProperties() {
		return properties;
	}
	
	@Override
	public String getWidgetName() {
		return "Notes";
	}
}
