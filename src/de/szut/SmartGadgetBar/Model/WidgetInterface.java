package de.szut.SmartGadgetBar.Model;

import java.util.Properties;

import de.szut.SmartGadgetBar.GUI.AbstractWidgetPanel;

/**
 * Das Interface welches die Widgets implementieren müssen
 * 
 * @author Simeon Kublenz, Fabian Brinkmann
 *
 */
public interface WidgetInterface {

	/**
	 * Gibt das Panel des Widgets zurück
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
	 * Gbt die Properties des Widgets zurück
	 * 
	 * @return die aktuellen Einstellungen
	 */
	public Properties getProperties();

	/**
	 * Gibt den Namen des Widgets zurück
	 * 
	 * @return Der Widgetname
	 */
	public String getWidgetName();

	/**
	 * Gibt die Standardeinstellungen dieses Widgets zurück
	 * 
	 * @return die Standarteinstellungen
	 */
	public Properties getDefaultProperties();

	/**
	 * Beendet dieses Widget
	 */
	public void close();

	/**
	 * Lädt die Properties
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
