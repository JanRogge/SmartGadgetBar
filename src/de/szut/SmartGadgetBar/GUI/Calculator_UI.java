package de.szut.SmartGadgetBar.GUI;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTextField;

import de.szut.SmartGadgetBar.Widgets.Calculator.Calculator;
import de.szut.SmartGadgetBar.Widgets.Notes.Notes;

public class Calculator_UI extends AbstractWidgetPanel{

	
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
		
		setSize(280,80);
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		
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
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					System.out.println("enter");
					erge.setText(widget.calculate(field.getText()));

					
				}
			}
			
			
		});
		
		erge = new JLabel("=");
		erge.setBounds(30,40,5000,50);
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
		OptionPanelWidget opw= new OptionPanelWidget(widget);
		opw.addProperty(Calculator.MSG, "What are you searching for? Calculator need no options!");
		opw.finish();
	}
	
	

}
