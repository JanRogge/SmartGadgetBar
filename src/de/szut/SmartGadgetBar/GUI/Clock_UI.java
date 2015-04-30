package de.szut.SmartGadgetBar.GUI;

import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;


import de.szut.SmartGadgetBar.Widgets.Clock.Clock;

import de.szut.SmartGadgetBar.Widgets.Clock.TimeThread;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;

public class Clock_UI extends AbstractWidgetPanel {
	private Clock widget;
	private TimeThread time;
	private JLabel mainTime;
	private JLabel lblName;
	private JLabel lblTime;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public Clock_UI(Clock parent) {
		super(parent);
		widget = parent;
		initializePanel();
	}
	public JLabel getMainTime(){
		return mainTime;
	}
	public JLabel getZoneName(){
		return lblName;
	}
	public JLabel getTimeLabel(){
		return lblTime;
	}

	@Override
	void initializePanel() {
		// TODO Auto-generated method stub
		setSize(280,155);
		setLayout(null);
		setBackground(Color.YELLOW);
		
		mainTime= new JLabel();
		mainTime.setFont(new Font("Tahoma", Font.PLAIN, 47));
		mainTime.setHorizontalAlignment(SwingConstants.CENTER);
		mainTime.setBounds(10, 11, 260, 80);
		add(mainTime);
		
		if(widget.otherTimeZonesSelectet()){
			lblName = new JLabel("Name");
			lblName.setBounds(10, 102, 46, 14);
			lblTime = new JLabel("Time");
			lblTime.setBounds(160, 102, 46, 14);
			add(lblName);
			add(lblTime);
		}

		JPopupMenu popup = new JPopupMenu();
		add(popup);
		JMenuItem subMenu = new JMenuItem("Options");
		subMenu.addActionListener(e -> {
			System.out.println("Insert Options here");
		});
		JMenuItem closeWidget = new JMenuItem("Delete");
		closeWidget.addActionListener(e -> {
			this.getParent().remove(this);
			widget.stopThread();
		});
		popup.add(closeWidget);
		popup.add(subMenu);
		setComponentPopupMenu(popup);
		
		textField = new JTextField();
		textField.setBounds(38, 124, 195, 20);
		add(textField);
		textField.setColumns(10);
		setVisible(true);
	}
}
