package de.szut.SmartGadgetBar.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import de.szut.SmartGadgetBar.Model.WidgetInterface;

public class OptionPanelWidget {

	private JFrame frame;
	private Properties props;
	private WidgetInterface widget;
	private JButton svbtn;
	private ArrayList<OptionKeyPairPanel> options;
	private JPanel topPanel;
	
	public OptionPanelWidget(WidgetInterface widin){
		frame = new JFrame();
		svbtn = new JButton("Save");
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
		
		frame.setTitle(widget.getWidgetName());
		
		options = new ArrayList<OptionKeyPairPanel>();
		OptionKeyPairPanel op;
		
		for(Object k: props.keySet()){
			System.out.println(k);
			op = new OptionKeyPairPanel(k, props.get(k));
			options.add(op);
			topPanel.add(op);
		}
		topPanel.add(svbtn);
		frame.setContentPane(topPanel);
		
		frame.setVisible(true);
	}
	 
	private void saveProperties(){
		for(OptionKeyPairPanel okpp: options){
			props.setProperty(okpp.getKey(), okpp.getValue());
		}
		widget.setProperties(props);
	}
	
	
	

	
}
