package de.szut.SmartGadgetBar.Model;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {
	
	private Properties properties;
	private File file;
	
	/**
	 * Konstruktor des PropertyReaders
	 */
	public PropertyLoader() {
		properties = new Properties();
		try {
			file = new File (new File(".").getCanonicalPath() + "/Properties.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Lädt die Properties
	 * @return geladene Properties
	 */
	public String[][] loadProperties(String[][] propertiesNames) {
		String[][] propertiesValues = new String[propertiesNames.length][];
			try {
				properties.load(new FileInputStream(file));
				for (int x = 0; x < propertiesValues.length; x++) {
					propertiesValues[x] = new String[propertiesValues.length];
					for (int y = 0; y < propertiesValues.length; y++) {
						propertiesValues[x][y] = properties.getProperty(propertiesNames[x][y]);
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		return propertiesValues;
	}
	
	
	/**
	 * Speichert die übergebenen Werte in der Properties Datei
	 * @param rectangle position un größe des Frames
	 * @param treeWidth breite des treePanels
	 */
	public void savePreperties(String[][] propertiesNames, String[][] propertiesValues) {
		for (int x = 0; x < propertiesNames.length; x++) {
			for (int y = 0; y < propertiesNames[x].length; y++) {
				properties.setProperty(propertiesNames[x][y], propertiesValues[x][y]);
			}
		}
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.store(fileOut, "SQLite Browser Properties");
			fileOut.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
