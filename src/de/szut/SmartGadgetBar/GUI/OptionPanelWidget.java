package de.szut.SmartGadgetBar.GUI;

import java.util.Properties;

public class OptionPanelWidget {

	public OptionPanelWidget(WidgetInterface widin){
		Properties prop = widin.getProperties();
		
		/*
		 * Code der die Properties anzeigt.
		 * Mit "ok" werden die Properties geändert 
		 * und im Objekt prop gespeichert.
		 * 
		 */
		
		
		widin.setProperties(prop);
	}
	
}
