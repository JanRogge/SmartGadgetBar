package de.szut.SmartGadgetBar.Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Der
 * @author Simeon Kublenz, Fabian Brinkmann
 */
public class PropertyLoader {
	
	/**
	 * L�dt die Properties
	 * @return geladene Properties
	 */
	public Properties loadProperties(String fileName/*, Properties defaultProperties*/){

		Properties p = new Properties();
		try {
			p.load(new FileInputStream(new File(new File(".").getCanonicalPath() + "/" + fileName)));
		}
		catch (FileNotFoundException e) {
			//p = defaultProperties;
			try {
				p.store(new FileOutputStream(new File(new File(".").getCanonicalPath() + "/" + fileName)), "");
			}
			catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}
	
	
	
	/**
	 * Speichert die �bergebenen Werte in der Properties Datei
	 * @param rectangle position un gr��e des Frames
	 * @param treeWidth breite des treePanels
	 */
	public void saveProperties(Properties p, String fileName){
		try {
			p.store(new FileOutputStream(new File(new File(".").getCanonicalPath() + "/" + fileName)), "");
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
