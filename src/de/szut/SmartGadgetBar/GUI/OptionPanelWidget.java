package de.szut.SmartGadgetBar.GUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.szut.SmartGadgetBar.Model.WidgetInterface;

/**
 * Klasse fuer das anzeigen der Optionen von Widgets
 * 
 * @author Fabian Brinkmann
 * 
 */
public class OptionPanelWidget {

	private JFrame frame;
	private Properties props;
	private WidgetInterface widget;
	private JButton svbtn;
	private ArrayList<OptionKeyPairPanel> options;
	private JPanel topPanel;
	private JButton canc;
	private JComboBox<Object> ocb;

	/**
	 * Dem Konstruktor wird das Widget uebergeben, fuer welches Optionen
	 * angezeigt werden sollen
	 * 
	 * @param widin
	 */
	public OptionPanelWidget(WidgetInterface widin) {
		frame = new JFrame();
		frame.setSize(new Dimension(500, 500));
		svbtn = new JButton("Save");
		canc = new JButton("Cancel");
		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

		widget = widin;
		props = widget.getProperties();
		svbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveProperties();
			}
		});
		canc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

		frame.setTitle(widget.getWidgetName());

		options = new ArrayList<OptionKeyPairPanel>();

	}

	/**
	 * Speichert die neuen Optionen im Widget
	 */
	private void saveProperties() {
		for (OptionKeyPairPanel okpp : options) {
			props.setProperty(okpp.getKey(), okpp.getValue());
		}
		widget.setProperties(props);
	}

	/**
	 * Fügt Optionen hinzu zum Fenster hinzu
	 * 
	 * @param key
	 *            der Key, mit dem auf die Property zugegriffen werden kann
	 * @param title
	 *            ein Bezeichnender String, der dem User gezeigt wird
	 */
	public void addProperty(Object key, String title) {
		OptionKeyPairPanel op = new OptionKeyPairPanel(key, props.get(key),
				title);
		options.add(op);
		topPanel.add(op);
	}

	/**
	 * Fügt einen neuen Knopf zu den Optionen hinzu
	 * 
	 * @param text
	 *            Der Text der auf dem Knopf stehen soll
	 * @param l
	 *            Der ActionListener der auf den Knopf horchen soll
	 */
	public void addButton(String text, ActionListener l) {
		JButton ob = new JButton(text);
		ob.setBounds(175, 0, 150, 20);
		ob.addActionListener(l);
		topPanel.add(ob);
	}

	/**
	 * Fügt einen neue KomboBox zu den Optionen hinzu
	 * 
	 * @param model
	 *            Die Liste der Optionen die zur Auswahl stehen sollen
	 */
	public void addcomboBox(DefaultComboBoxModel<Object> model) {
		ocb = new JComboBox<Object>();
		ocb.setEditable(true);
		ocb.setModel(model);
		topPanel.add(ocb);
	}

	/**
	 * Gibt die Kombobox zurück
	 * 
	 * @return die Kombobox
	 */
	public JComboBox<Object> getComboBox() {
		return ocb;
	}

	/**
	 * Die Methode finish wird aufgerufen, sobald alle Optionen geaddet wurden.
	 * Fuegt den Speicher und Abbrechen Button hinzu und macht den Frame sichbar
	 */
	public void finish() {
		topPanel.add(svbtn);
		topPanel.add(canc);
		frame.setContentPane(topPanel);
		frame.setVisible(true);
	}

}
