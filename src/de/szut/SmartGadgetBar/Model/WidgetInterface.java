package de.szut.SmartGadgetBar.Model;

import java.util.Properties;

import javax.swing.JPanel;

public interface WidgetInterface {
	public JPanel getPanel();
	public void setProperties(Properties properties);
	public Properties getProperties();
	public String getWidgetName();

	default void loadProperties() {
		setProperties(new PropertyLoader().loadProperties("config/"+getWidgetName()+".ini"));
	}
	default void saveProperties() {
		new PropertyLoader().saveProperties(getProperties(), "config/"+getWidgetName()+".ini");
	}
	
}
