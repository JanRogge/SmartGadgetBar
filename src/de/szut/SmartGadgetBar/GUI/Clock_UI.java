package de.szut.SmartGadgetBar.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.szut.SmartGadgetBar.Widgets.Clock.Clock;
import de.szut.SmartGadgetBar.Widgets.Clock.TimeThread;

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
		setSize(280,221);
		setBackground(Color.YELLOW);
		setLayout(new BorderLayout(0, 0));
		
		if(widget.otherTimeZonesSelectet()){
			lblName = new JLabel("Name");
			lblName.setSize(46, 14);
			lblTime = new JLabel("Time");
			lblTime.setSize(46, 14);
			if(this.getComponentCount() == 1){
				lblName.setLocation(5, 102);
				lblTime.setLocation(70, 102);
			}
			System.out.println(this.getComponentCount());
			//add(lblName);
			//add(lblTime);
		}

		
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		mainTime= new JLabel();
		mainTime.setText("Test");
		panel.add(mainTime);
		mainTime.setFont(new Font("Tahoma", Font.PLAIN, 47));
		mainTime.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
//		textField = new JTextField();
//		textField.setBounds(38, 124, 195, 20);
//		add(textField);
//		textField.setColumns(10);
//		setVisible(true);
	}
}
