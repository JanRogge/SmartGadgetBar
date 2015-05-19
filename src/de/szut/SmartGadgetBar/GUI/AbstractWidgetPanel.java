package de.szut.SmartGadgetBar.GUI;

import java.io.File;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import de.szut.SmartGadgetBar.Model.WidgetInterface;

/**
 * Abstracte Klasse, die alle UI's  der widgets erben muessen
 *
 */
public abstract class AbstractWidgetPanel extends JPanel {
	
	protected WidgetInterface widget;
	
	
	/**
	 * Konstruktor
	 * Die UI kennt das Widget um mit ihm zu interagieren
	 * Hat bereits die Menuepunkte Options und Delete
	 * @param parent
	 */
	public AbstractWidgetPanel(WidgetInterface parent){
		this.widget = parent;
		JPopupMenu popup = new JPopupMenu();
		add(popup);
		JMenuItem subMenu = new JMenuItem("Options");
		subMenu.addActionListener(e -> {
			widget.getPanel().optionClicked();
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
	
	public abstract void optionClicked();
	
	abstract void initializePanel();
	
	public abstract void pushFiles(File[] files);
}
