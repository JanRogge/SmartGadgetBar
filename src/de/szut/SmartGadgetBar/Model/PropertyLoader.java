package de.szut.SmartGadgetBar.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Der
 * @author Simeon Kublenz
 */
public class PropertyLoader {
	
	/**
	 * Lädt die Properties
	 * @return geladene Properties
	 */
	public Properties loadProperties(String fileName){

		Properties p = new Properties();
		try {
			p.load(new FileInputStream(new File(new File(".").getCanonicalPath() + "/" + fileName)));
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
			p.store(new FileOutputStream(new File(new File(".").getCanonicalPath() + "/" + fileName)), "");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
