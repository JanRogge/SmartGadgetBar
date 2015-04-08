package de.szut.SmartGadgetBar.GUI;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ClockView extends WidgetPanel {

	public ClockView(JPanel parent) {
		super(parent);
		setLayout(null);
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setBackground(Color.MAGENTA);
		parent.add(this);
		
		
	}


}
