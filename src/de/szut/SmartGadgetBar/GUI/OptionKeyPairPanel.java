package de.szut.SmartGadgetBar.GUI;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OptionKeyPairPanel extends JPanel{

	private String key;
	private String value;
	
	private JLabel keyLab;
	private JTextField valueFld;
	
	public OptionKeyPairPanel(Object k, Object object){
		this.key = k.toString();
		this.value = object.toString();
		
		keyLab = new JLabel(key);
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
		return value;
	}
}
