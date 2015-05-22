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
	 * Lädt die Properties
	 * @return geladene Properties
	 */
	public Properties loadProperties(String fileName, Properties defaultProperties){
		File k = new File("config/");
		if(!k.exists()){
			k.mkdir();
		}
		Properties p = new Properties();
		try (FileInputStream f = new FileInputStream(new File(new File(".").getCanonicalPath() + "/" + fileName))){
			p.load(f);
		}
		catch (FileNotFoundException e) {
			p = defaultProperties;
			try (FileOutputStream f = new FileOutputStream(new File(new File(".").getCanonicalPath() + "/" + fileName))){
				p.store(f, "");
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
	 * Speichert die übergebenen Werte in der Properties Datei
	 * @param rectangle position un größe des Frames
	 * @param treeWidth breite des treePanels
	 */
	public void saveProperties(Properties p, String fileName){
		try (FileOutputStream f = new FileOutputStream(new File(new File(".").getCanonicalPath() + "/" + fileName))){
			p.store(f, "");
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
