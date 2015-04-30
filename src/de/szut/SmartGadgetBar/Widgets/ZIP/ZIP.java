package de.szut.SmartGadgetBar.Widgets.ZIP;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.zip.*;

import javax.swing.JPanel;

import de.szut.SmartGadgetBar.GUI.ZIP_UI;
import de.szut.SmartGadgetBar.Model.WidgetInterface;

public class ZIP  implements WidgetInterface {

	private Deflater deflet;
	private ZIP_UI ui;
	private Properties props;
	public final String widgetName = "ZIP";
	
	public ZIP() {

		ui = new ZIP_UI(this);
	}
	public void comprimate(File[] files, String outName){
		
		String[] temp = new String[files.length];
		for(int i =0; i< temp.length; i++){
			temp[i] = files[i].getAbsolutePath();
		}
		new Comprimator().comprimate(temp, outName);
		
	}
	
	public void comprimate(String[] files, String outName) {
		new Comprimator().comprimate(files, outName);

	}

	public void decomprimate(String zipFile) {
		Decomprimator.decomprimate(zipFile);
	}

	@Override
	public JPanel getPanel() {
		// TODO Auto-generated method stub
		return ui;
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		props = properties;
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
