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
