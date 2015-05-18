package de.szut.SmartGadgetBar.Widgets.Notes;

import java.util.Properties;

import javax.swing.JPanel;

import de.szut.SmartGadgetBar.Model.WidgetInterface;
import de.szut.SmartGadgetBar.GUI.Notes_UI;

public class Notes implements WidgetInterface {
	
	private Notes_UI ui;
	private Properties properties;
	
	public Notes() {
		ui = new Notes_UI(this);
	}
	
	@Override
	public JPanel getPanel() {
		// TODO Auto-generated method stub
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
