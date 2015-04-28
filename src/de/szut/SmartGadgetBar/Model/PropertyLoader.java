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
	public Properties loadProperties(String fileName){

		Properties p = new Properties();
		try {
			p.load(new FileInputStream(new File(fileName)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}
	
	
	
	/**
	 * Speichert die übergebenen Werte in der Properties Datei
	 * @param rectangle position un größe des Frames
	 * @param treeWidth breite des treePanels
	 */
	public void saveProperties(Properties p, String fileName){
		try {
			p.store(new FileOutputStream(new File(fileName)), "");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
