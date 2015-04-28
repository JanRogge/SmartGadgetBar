package de.szut.SmartGadgetBar.GUI;

import java.util.Properties;

public abstract class AbstractWidgetPanel {
	private WidgetInterface widget;
	private JPanel panel;
	
	
	
	public AbstractWidgetPanel(WidgetInterface parent){
		this.widget = parent;
	}
	public String getWidget() {
		return widget;
	}
	
	public void optionClicked(){
		new OptionPanelWidget(widget);
	}
	
	 abstract void initializePanel();
	
}
