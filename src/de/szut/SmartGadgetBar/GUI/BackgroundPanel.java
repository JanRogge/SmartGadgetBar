package de.szut.SmartGadgetBar.GUI;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import de.szut.SmartGadgetBar.Model.PropertyLoader;
import de.szut.SmartGadgetBar.Model.WidgetLoader;
import de.szut.SmartGadgetBar.Widgets.PGP.PGP;

import java.awt.Color;
import java.util.Properties;

public class BackgroundPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2546051500075793695L;
    private int pX, pY;
    private JTextField textField;
    private JMenu addWidgets;
    private WidgetLoader widgetLoader;
    public static final int GAP = 5;

	/**
	 * Create the panel.
	 */
	public BackgroundPanel(MainFrame mainf) {
		widgetLoader = new WidgetLoader();
		JPopupMenu menuPopup = new JPopupMenu();
		JMenuItem closeMenuItem = new JMenuItem("Close");
		JMenuItem mainOptions= new JMenuItem("Options");
		addWidgets= new JMenu("Add Widgets");
		String [] availabelWidgets = new PropertyLoader().loadProperties("config/config.ini").getProperty("availableWidgets").split(",");
		addtoMenu(availabelWidgets);
		closeMenuItem.addActionListener(e -> {
		mainf.close();
		});
		mainOptions.addActionListener(e -> {
			new OptionPanelMain(mainf);
		});
		menuPopup.add(addWidgets);
		menuPopup.add(mainOptions);
        menuPopup.add(closeMenuItem);
       
        setComponentPopupMenu(menuPopup);
		setLayout(null);
		setBounds(5,5, 300, 200);
		
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
		JMenuItem subMenu = new JMenuItem("Delete");
		subMenu.addActionListener(e-> {
			remove(panel);
		});
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
		});
		menuPopup1.add(closeWidget);
        menuPopup1.add(closeMenuItem1);
        panel1.setComponentPopupMenu(menuPopup1);
	}
	public void addtoMenu(String[] Widgets){
		for(int i = 0; i < Widgets.length; i++){
			JMenuItem widget= new JMenuItem(Widgets[i]);
			addWidgets.add(widget);
			int x = i;
			widget.addActionListener(e -> {
				JPanel widgetPanel = widgetLoader.loadWidget("bin/de/szut/SmartGadgetBar/Widgets/" + Widgets[x] +"/" + Widgets[x] + ".class").getPanel();
				add(widgetPanel);
				rebuild();
			});	
		}
		
	}
	@Override
	public void remove(Component comp){
		super.remove(comp);
		this.rebuild();
	}
	public void rebuild(){
		
		int curpos = BackgroundPanel.GAP;
		for(Component c:getComponents()){
			c.setLocation(10, curpos);
			curpos+=c.getHeight()+BackgroundPanel.GAP;
		}
		repaint();
		revalidate();
	}
}
