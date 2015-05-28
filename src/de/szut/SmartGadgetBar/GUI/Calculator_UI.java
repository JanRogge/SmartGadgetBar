package de.szut.SmartGadgetBar.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTextField;

import de.szut.SmartGadgetBar.Widgets.Calculator.Calculator;

/**
 * Die grafische Oberfläche des Calculator Widgets
 * 
 * @author Fabian Brinkmann
 *
 */
public class Calculator_UI extends AbstractWidgetPanel {

	private static final long serialVersionUID = 4216742631090750686L;
	private Calculator widget;
	private JTextField field;
	private JLabel erge;

	/**
	 * Erzeugt ein neues Objekt der Calculator_UI mit einem zugehörigen Widget
	 * 
	 * @param parent
	 *            Das Widget welches zu diesem Panel gehören soll
	 */
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
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (field.getText() != null && !field.getText().equals("")) {
						if (widget.calculate(field.getText()) == null) {
							erge.setText("= ERROR");
						} else {
							erge.setText("= "
									+ widget.calculate(field.getText()));
						}
					} else {
						erge.setText("= ERROR");
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

	/**
	 * Nicht in Gebrauch. Tut nichts
	 */
	@Override
	public void pushFiles(File[] files) {
	}

	/**
	 * Öffnet ein leeres Optionsfeld
	 */
	@Override
	public void optionClicked() {
		OptionPanelWidget opw = new OptionPanelWidget(widget);
		opw.addProperty(Calculator.MSG,
				"What are you searching for? Calculator need no options!");
		opw.finish();
	}
}
