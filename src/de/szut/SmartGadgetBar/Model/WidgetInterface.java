package de.szut.SmartGadgetBar.Model;

import java.util.Properties;

import de.szut.SmartGadgetBar.GUI.AbstractWidgetPanel;

public interface WidgetInterface {
	public AbstractWidgetPanel getPanel();
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
