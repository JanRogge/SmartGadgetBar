package de.szut.SmartGadgetBar.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.TimeZone;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.szut.SmartGadgetBar.Widgets.Clock.Clock;

public class Clock_UI extends AbstractWidgetPanel {
	private static final long serialVersionUID = 3815141531387992662L;
	private Clock widget;
	private JLabel mainTime;
	private JLabel altTime1;
	private JLabel altTime2;
	private JLabel altTime3;

	/**
	 * Create the panel.
	 */
	public Clock_UI(Clock parent) {
		super(parent);
		widget = parent;
		initializePanel();
		
	}

	public JLabel getMainTime() {
		return mainTime;
	}
	@Override
	void initializePanel() {
		// TODO Auto-generated method stub
		setSize(280, 100);
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(200, 200));
		panel.setOpaque(true);
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		mainTime = new JLabel();
		panel.add(mainTime, BorderLayout.CENTER);
		mainTime.setFont(new Font("Tahoma", Font.PLAIN, 47));
		mainTime.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(0, 3, 10, 0));


		altTime1 = new JLabel("");
		altTime1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(altTime1);

		altTime2 = new JLabel("");
		altTime2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(altTime2);

		altTime3 = new JLabel("");
		altTime3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel_1.add(altTime3);

		repaint();
		revalidate();
	}

	@Override
	public void pushFiles(File[] files) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void optionClicked() {
		// TODO Auto-generated method stub
		OptionPanelWidget opw = new OptionPanelWidget(widget);
		opw.addButton("Set TimeZone to Label1", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				widget.setOtherTimeLabels(altTime1, opw.getComboBox().getSelectedItem().toString());
			}

		});
		opw.addButton("Set TimeZone to Label2", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				widget.setOtherTimeLabels(altTime2, opw.getComboBox().getSelectedItem().toString());
			}

		});
		opw.addButton("Set TimeZone to Label3", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				widget.setOtherTimeLabels(altTime3, opw.getComboBox().getSelectedItem().toString());
			}

		});
		opw.addcomboBox(new DefaultComboBoxModel<Object>(TimeZone.getAvailableIDs()));
		opw.addButton("Delete Label 1", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				altTime1.setText("");
				widget.stoponeLabel(altTime1);
			}

		});
		opw.addButton("Delete Label 2", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				altTime2.setText("");
				widget.stoponeLabel(altTime2);
			}

		});
		opw.addButton("Delete Label 3", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				altTime3.setText("");
				widget.stoponeLabel(altTime3);
			}

		});
		opw.finish();
	}
}
