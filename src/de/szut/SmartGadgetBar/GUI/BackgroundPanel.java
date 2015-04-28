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
    private JMenu addWidgets;
    public static final int GAP = 5;

	/**
	 * Create the panel.
	 */
	public BackgroundPanel(MainFrame mainf, PropLoader props) {
		JPopupMenu menuPopup = new JPopupMenu();
		JMenuItem closeMenuItem = new JMenuItem("Close");
		JMenuItem mainOptions= new JMenuItem("Options");
		addWidgets= new JMenu("Add Widgets");
		addtoMenu(props.getWidgets());
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
		//add(new TestPanel(this, 0));
		//add(new TestPanel(this, 1));
		//add(new TestPanel(this, 2));
		testWidgets();
		//testOutputs();
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
		panel1.setBounds(10, 80, 280, 90);
		add(panel1);
		panel1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPopupMenu menuPopup1 = new JPopupMenu();
		JMenuItem closeMenuItem1 = new JMenuItem("Test");
		closeMenuItem1.addActionListener(e-> {
			//mainf.close();
		});
		JMenuItem closeWidget = new JMenuItem("Delete");
		closeWidget.addActionListener(e-> {
			remove(panel1);
			rebuild();
		});
		menuPopup1.add(closeWidget);
        menuPopup1.add(closeMenuItem1);
        panel1.setComponentPopupMenu(menuPopup1);
	}
	public void addtoMenu(String[] Widgets){
		for(int i = 0; i < Widgets.length; i++){
			JMenuItem widget= new JMenuItem(Widgets[i]);
			addWidgets.add(widget);
			widget.addActionListener(e -> {
				add(new TestPanel(this, 2));
				rebuild();
			});	
		}
		
	}
	public void rebuild(){
		repaint();
		revalidate();
	}
	public void testOutputs(){
//		System.out.println(this.getComponent(1).getSize().getHeight());
//		System.out.println(this.getComponent(0).getSize().getHeight());
	}
}
