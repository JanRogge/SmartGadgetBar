package de.szut.SmartGadgetBar.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTextField;

import de.szut.SmartGadgetBar.Widgets.Calculator.Calculator;

public class Calculator_UI extends AbstractWidgetPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4216742631090750686L;
	private Calculator widget;
	private JTextField field;
	private JLabel erge;

	public Calculator_UI(Calculator parent) {
		super(parent);
		this.widget = parent;

		initializePanel();

	}

	@Override
	void initializePanel() {

		setSize(280, 80);
		setLayout(null);
		setColor(Color.LIGHT_GRAY);
		setImage("graphics/calculator.png");

		field = new JTextField();
		field.setBounds(10, 10, 250, 27);
		field.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (widget.calculate(field.getText()) == null) {
						erge.setText("= ERROR");
					} else {
						erge.setText("= " + widget.calculate(field.getText()));
					}

				}
			}

		});

		erge = new JLabel("= ");
		erge.setBounds(30, 40, 5000, 50);
		erge.setForeground(Color.white);
		erge.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(field);
		add(erge);

		setVisible(true);
	}

	@Override
	public void pushFiles(File[] files) {
		// TODO Auto-generated method stub

	}

	@Override
	public void optionClicked() {
		// TODO Auto-generated method stub
		OptionPanelWidget opw = new OptionPanelWidget(widget);
		opw.addProperty(Calculator.MSG,
				"What are you searching for? Calculator need no options!");
		opw.finish();
	}

}
