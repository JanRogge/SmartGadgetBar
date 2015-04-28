package de.szut.SmartGadgetBar.GUI;

import javax.swing.JPanel;

import de.szut.SmartGadgetBar.Model.WidgetInterface;

public abstract class AbstractWidgetPanel extends JPanel {
	protected WidgetInterface widget;
	
	
	
	public AbstractWidgetPanel(WidgetInterface parent){
		this.widget = parent;
	}
	public WidgetInterface getWidget() {
		return widget;
	}
	
	public void optionClicked(){
		new OptionPanelWidget(widget);
	}
	
	abstract void initializePanel();
	
}
