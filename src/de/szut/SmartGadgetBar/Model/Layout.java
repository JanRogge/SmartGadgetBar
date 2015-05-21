package de.szut.SmartGadgetBar.Model;

import java.io.Serializable;

public class Layout implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2936000611293834881L;

	private String[] widgets;

	public Layout(String[] widgets) {
		this.widgets = widgets;
	}

	public String[] getWidgets() {
		return widgets;
	}
}