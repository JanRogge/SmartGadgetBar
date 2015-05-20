package de.szut.SmartGadgetBar.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.TimeZone;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.szut.SmartGadgetBar.Widgets.Clock.Clock;

public class Clock_UI extends AbstractWidgetPanel {
	private static final long serialVersionUID = 3815141531387992662L;
	private Clock widget;
	private JLabel mainTime;

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

	@Override
	void initializePanel() {
		// TODO Auto-generated method stub
		setSize(280,200);
		setBackground(Color.YELLOW);
		setLayout(new BorderLayout(0, 0));

		
		
		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(200, 200));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		mainTime = new JLabel();
		panel.add(mainTime, BorderLayout.CENTER);
		mainTime.setFont(new Font("Tahoma", Font.PLAIN, 47));
		mainTime.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(0, 3, 10, 0));
		
		JComboBox<Object> values = new JComboBox<>();
		values.setEditable(true);
		values.setModel(new DefaultComboBoxModel<Object>(TimeZone.getAvailableIDs()));
		
		JLabel altTime1 = new JLabel("");
		altTime1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(altTime1);
		
		JLabel altTime2 = new JLabel("");
				altTime2.setFont(new Font("Tahoma", Font.PLAIN, 11));
				panel_1.add(altTime2);
		
				JLabel altTime3 = new JLabel("");
				altTime3.setFont(new Font("Tahoma", Font.PLAIN, 11));
				panel_1.add(altTime3);
		
				JButton btnNewButton = new JButton("New button");
				btnNewButton.addActionListener(e -> {
					widget.setOtherTimeLabels(altTime1, values.getSelectedItem().toString());
				});
				panel_1.add(btnNewButton);
		
				JButton btnNewButton_1 = new JButton("New button");
				btnNewButton_1.addActionListener(e -> {
					widget.setOtherTimeLabels(altTime2, values.getSelectedItem().toString());
				});
				panel_1.add(btnNewButton_1);
		
				JButton btnNewButton_2 = new JButton("New button");
				btnNewButton_2.addActionListener(e -> {
					widget.setOtherTimeLabels(altTime3, values.getSelectedItem().toString());
				});
				panel_1.add(btnNewButton_2);
		
				panel_1.add(values);
	}
	@Override
	public void pushFiles(File[] files) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void optionClicked() {
		// TODO Auto-generated method stub
		OptionPanelWidget opw= new OptionPanelWidget(widget);
		opw.addProperty(Clock.CITIES, "Cities:");
		opw.finish();
	}
}
