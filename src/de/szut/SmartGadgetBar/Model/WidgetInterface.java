package de.szut.SmartGadgetBar.Model;

import java.util.Properties;

import de.szut.SmartGadgetBar.GUI.AbstractWidgetPanel;

/**
 * Das Interface welches die Widgets implementieren m�ssen
 * 
 * @author Simeon Kublenz, Fabian Brinkmann
 *
 */
public interface WidgetInterface {

	/**
	 * Gibt das Panel des Widgets zur�ck
	 * 
	 * @return Das Panel des Widgets
	 */
	public AbstractWidgetPanel getPanel();

	/**
	 * Setzt die Properties des Widgets
	 * 
	 * @param properties
	 *            die neuen Einstellungen
	 */
	public void setProperties(Properties properties);

	/**
	 * Gbt die Properties des Widgets zur�ck
	 * 
	 * @return die aktuellen Einstellungen
	 */
	public Properties getProperties();

	/**
	 * Gibt den Namen des Widgets zur�ck
	 * 
	 * @return Der Widgetname
	 */
	public String getWidgetName();

	/**
	 * Gibt die Standardeinstellungen dieses Widgets zur�ck
	 * 
	 * @return die Standarteinstellungen
	 */
	public Properties getDefaultProperties();

	/**
	 * Beendet dieses Widget
	 */
	public void close();

	/**
	 * L�dt die Properties
	 */
	default void loadProperties() {
		setProperties(new PropertyLoader().loadProperties("config/"
				+ getWidgetName() + ".ini", getDefaultProperties()));
	}

	/**
	 * Speichert die Properties
	 */
	default void saveProperties() {
		new PropertyLoader().saveProperties(getProperties(), "config/"
				+ getWidgetName() + ".ini");
	}

}
