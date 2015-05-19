package de.szut.SmartGadgetBar.GUI;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * Klasse fuer die Darstellung eines key-value pairs.
 * Statt dem Key wird ein bezeichnender String angezeigt
 *
 */
public class OptionKeyPairPanel extends JPanel{

	private String key;
	private String value;
	
	private JLabel keyLab;
	private JTextField valueFld;
	
	/**
	 * @param k der Key, mit dem auf die property zugegriffen werden kann
	 * @param object die value des dazugehoerigen keys. wird als textfield angezeigt und kann 
	 * bearbeitet werden
	 * @param title der bezeichnende String, der neben dem textfeld mit der value gezeigt wird
	 */
	public OptionKeyPairPanel(Object k, Object object, String title){
		this.key = k.toString();
		if(object == null){
			value ="";
		}else{
			
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
	public String getKey(){
		return key;
	}
	public String getValue(){
		return valueFld.getText();
	}
}
