package de.szut.SmartGadgetBar.GUI;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import de.szut.SmartGadgetBar.Model.WidgetInterface;

public abstract class AbstractWidgetPanel extends JPanel {
	protected WidgetInterface widget;
	
	
	
	public AbstractWidgetPanel(WidgetInterface parent){
		this.widget = parent;
		JPopupMenu popup = new JPopupMenu();
		add(popup);
		JMenuItem subMenu = new JMenuItem("Options");
		subMenu.addActionListener(e -> {
			new OptionPanelWidget(widget);
			System.out.println("Insert Options here");
		});
		JMenuItem closeWidget = new JMenuItem("Delete");
		closeWidget.addActionListener(e -> {
			this.getParent().remove(this);
		});
		popup.add(closeWidget);
		popup.add(subMenu);
		setComponentPopupMenu(popup);
		setVisible(true);
	}
	public WidgetInterface getWidget() {
		return widget;
	}
	
	public void optionClicked(){
		new OptionPanelWidget(widget);
	}
	
	abstract void initializePanel();
	
}
