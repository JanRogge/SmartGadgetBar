package de.szut.SmartGadgetBar.Widgets.ZIP;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.zip.*;

import javax.swing.JPanel;

import de.szut.SmartGadgetBar.GUI.AbstractWidgetPanel;
import de.szut.SmartGadgetBar.GUI.ZIP_UI;
import de.szut.SmartGadgetBar.Model.PropertyLoader;
import de.szut.SmartGadgetBar.Model.WidgetInterface;

/**
 * Klasse zum De/Komprimieren von Dateien und Ordnern
 * 
 */
public class ZIP  implements WidgetInterface {

	public static final String COMLOCATION = "comprimatelocation";
	public static final String STANDLOCATION = "zips/output.zip";
	private Deflater deflet;
	private ZIP_UI ui;
	private Properties props;
	public final String widgetName = "ZIP";
	
	public ZIP() {

		ui = new ZIP_UI(this);
		loadProperties();
	}
	
	/**
	 * Komprimiert die gegebenen Verzeichnisse
	 * @param files
	 */
	public void comprimate(File[] files){

		String[] temp = new String[files.length];
		for(int i =0; i< temp.length; i++){
			temp[i] = files[i].getAbsolutePath();
		}

		if(new File(props.getProperty(ZIP.COMLOCATION)).isFile()){
			new Comprimator().comprimate(temp, props.getProperty(ZIP.COMLOCATION));
			
		}else{
			new Comprimator().comprimate(temp, ZIP.STANDLOCATION);
		}
		
	}
	
	public void comprimate(String[] files) {
		if(new File(props.getProperty(ZIP.COMLOCATION)).isFile()){
			new Comprimator().comprimate(files, props.getProperty(ZIP.COMLOCATION));
			
		}else{
			new Comprimator().comprimate(files, ZIP.STANDLOCATION);
		}

	}
	
	/**
	 * Dekomprimiert die gegebene +*.zip Datei
	 * @param zipFile
	 */
	public void decomprimate(String zipFile) {
		Decomprimator.decomprimate(zipFile);
	}

	@Override
	public AbstractWidgetPanel getPanel() {
		// TODO Auto-generated method stub
		return ui;
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		props = properties;
		saveProperties();
	}

	@Override
	public Properties getProperties() {
		// TODO Auto-generated method stub
		return props;
	}

	@Override
	public String getWidgetName() {
		// TODO Auto-generated method stub
		return widgetName;
	}
	

}
