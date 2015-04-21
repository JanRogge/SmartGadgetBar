package de.szut.SmartGadgetBar.GUI;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;





import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import java.awt.Color;

public class BackgroundPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2546051500075793695L;
    private int pX, pY;
    private JTextField textField;

	/**
	 * Create the panel.
	 */
	public BackgroundPanel(MainFrame mainf) {
		JPopupMenu menuPopup = new JPopupMenu();
		JMenuItem closeMenuItem = new JMenuItem("Close");
		JMenuItem mainOptions= new JMenuItem("Options");
		JMenu addWidgets= new JMenu("Add Widgets");
		JMenuItem widget= new JMenuItem("Widget XY");
		addWidgets.add(widget);
		closeMenuItem.addActionListener(e-> {
			mainf.close();
		});
		mainOptions.addActionListener(e-> {
			OptionPanel frame = new OptionPanel(mainf);
		});
		menuPopup.add(addWidgets);
		menuPopup.add(mainOptions);
        menuPopup.add(closeMenuItem);
       
        setComponentPopupMenu(menuPopup);
		setLayout(null);
        
        setBounds(10, 10, 300, 155);
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				pX = me.getX();
				pY = me.getY();
			}
		});
		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent me) {
				mainf.setLocation(getParent().getParent().getParent().getLocation().x + me.getX() - pX, mainf.getLocation().y
						+ me.getY() - pY);
			}
		});
		testWidgets();
	}
	public void testWidgets(){
		JPanel panel = new JPanel();
		panel.setBounds(10, 5, 280, 70);
		add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		JPopupMenu popup = new JPopupMenu();
		textField.add(popup);
		JMenuItem subMenu = new JMenuItem("Options");
		popup.add(subMenu);
		textField.setComponentPopupMenu(popup);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.RED);
		panel1.setBounds(10, 80, 280, 70);
		add(panel1);
		panel1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPopupMenu menuPopup1 = new JPopupMenu();
		JMenuItem closeMenuItem1 = new JMenuItem("Test");
		closeMenuItem1.addActionListener(e-> {
			//mainf.close();
		});
        menuPopup1.add(closeMenuItem1);
        panel1.setComponentPopupMenu(menuPopup1);
	}
}