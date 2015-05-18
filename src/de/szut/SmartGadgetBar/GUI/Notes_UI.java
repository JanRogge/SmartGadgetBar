package de.szut.SmartGadgetBar.GUI;

import java.awt.Rectangle;
import java.io.File;

import javax.swing.JTextArea;

import de.szut.SmartGadgetBar.Model.WidgetInterface;

public class Notes_UI extends AbstractWidgetPanel {
	
	public Notes_UI(WidgetInterface parent) {
		super(parent);
		setBounds(new Rectangle(0, 0, 280, 80));
		initializePanel();
	}
	
	@Override
	void initializePanel() {
		setLayout(null);
		JTextArea textfield = new JTextArea();
		textfield.setBounds(0, 10, 280, 60);
		add(textfield);
		setVisible(true);
	}
	
	@Override
	public void pushFiles(File[] files) {
	}
}
