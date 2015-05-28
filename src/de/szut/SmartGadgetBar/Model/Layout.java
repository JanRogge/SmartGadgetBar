package de.szut.SmartGadgetBar.Model;

import java.io.Serializable;

/**
 * Eine Speicherklasse zum speichern des Layouts
 * 
 * @author Simeon Kublenz
 *
 */
public class Layout implements Serializable {

	private static final long serialVersionUID = -2936000611293834881L;

	private String[] widgets;

	/**
	 * Erzeugt ein neues Objekt des Layouts mit den Widgets
	 * 
	 * @param widgets
	 *            die Widgetname
	 */
	public Layout(String[] widgets) {
		this.widgets = widgets;
	}

	/**
	 * Gibt die Widgetnamen des Layouts zurück
	 * 
	 * @return Die Widgetnamen
	 */
	public String[] getWidgets() {
		return widgets;
	}
}