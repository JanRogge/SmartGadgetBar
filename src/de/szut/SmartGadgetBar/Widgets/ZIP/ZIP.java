package de.szut.SmartGadgetBar.Widgets.ZIP;

import java.io.File;
import java.util.Properties;
import java.util.zip.*;

import de.szut.SmartGadgetBar.GUI.AbstractWidgetPanel;
import de.szut.SmartGadgetBar.GUI.ZIP_UI;
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
		return ui;
	}

	@Override
	public void setProperties(Properties properties) {
		props = properties;
		saveProperties();
	}

	@Override
	public Properties getProperties() {
		return props;
	}

	@Override
	public String getWidgetName() {
		return widgetName;
	}

	@Override
	public Properties getDefaultProperties() {
		Properties defaultProps = new Properties();
		defaultProps.setProperty("comprimatelocation", new File(".").getAbsolutePath());
		return defaultProps;
	}
}
