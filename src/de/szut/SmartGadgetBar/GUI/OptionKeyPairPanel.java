package de.szut.SmartGadgetBar.GUI;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Klasse f�r die Darstellung eines key-value pairs. Statt dem Key wird ein
 * bezeichnender String angezeigt
 * 
 * @author Fabian Brinkmann
 * 
 */
public class OptionKeyPairPanel extends JPanel {

	private static final long serialVersionUID = 863181219795666823L;
	private String key;
	private String value;

	private JLabel keyLab;
	private JTextField valueFld;

	/**
	 * Erzeugt ein neues Panel zum anzeigen der Schl�ssel
	 * 
	 * @param k
	 *            der Schl�ssel mit dem auf die Property zugegriffen werden kann
	 * @param object
	 *            die Value des dazugeh�rigen Schl�ssels. wird als Textfeld
	 *            angezeigt und kann bearbeitet werden
	 * @param title
	 *            der bezeichnende String, der neben dem Textfeld mit der Value
	 *            gezeigt wird
	 */
	public OptionKeyPairPanel(Object k, Object object, String title) {
		this.key = k.toString();
		if (object == null) {
			value = "";
		} else {
			this.value = object.toString();
		}

		keyLab = new JLabel(title);
		valueFld = new JTextField(value);
		setLayout(null);
		keyLab.setBounds(5, 0, 150, 20);
		add(keyLab);
		valueFld.setBounds(175, 0, 150, 20);
		add(valueFld);
		setVisible(true);
	}

	/**
	 * Gibt den Schl�ssel zur�ck
	 * 
	 * @return Der Schl�ssel
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Gibt den Value zur�ck
	 * 
	 * @return Der Value
	 */
	public String getValue() {
		return valueFld.getText();
	}
}