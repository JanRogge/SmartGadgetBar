package de.szut.SmartGadgetBar.GUI;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javafx.stage.FileChooser;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.szut.SmartGadgetBar.Model.Layout;
import de.szut.SmartGadgetBar.Model.LayoutManager;
import de.szut.SmartGadgetBar.Model.PropertyLoader;
import de.szut.SmartGadgetBar.Model.WidgetInterface;
import de.szut.SmartGadgetBar.Model.WidgetLoader;

public class BackgroundPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2546051500075793695L;
	private int pX, pY;
    private JMenu addWidgets;
    private WidgetLoader widgetLoader;
    public static final int GAP = 5;
	private ArrayList<WidgetInterface> actualWidgets = new ArrayList<WidgetInterface>();

	/**
	 * Create the panel.
	 */
	public BackgroundPanel(MainFrame mainf, Layout layout) {
		widgetLoader = new WidgetLoader();
		JPopupMenu menuPopup = new JPopupMenu();
		JMenuItem closeMenuItem = new JMenuItem("Close");
		JMenuItem mainOptions= new JMenuItem("Options");
		JMenuItem saveLayout= new JMenuItem("Save Layout");
		JMenuItem loadLayout= new JMenuItem("Load Layout");
		addWidgets= new JMenu("Add Widgets");
		String [] availabelWidgets = new PropertyLoader().loadProperties("config/config.ini", null).getProperty("availableWidgets").split(",");
		addtoMenu(availabelWidgets);
		closeMenuItem.addActionListener(e -> {
			mainf.close(new Layout(getWidgets()));
		});
		mainOptions.addActionListener(e -> {
			new OptionPanelMain(mainf);
		});
		saveLayout.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.removeChoosableFileFilter(fileChooser.getChoosableFileFilters()[0]);
			fileChooser.setFileFilter(new FileNameExtensionFilter("Binär Datei","bin"));
			fileChooser.showOpenDialog(null);
			if (fileChooser.getSelectedFile() != null) {
				new LayoutManager().write(new Layout(getWidgets()), fileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		loadLayout.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.removeChoosableFileFilter(fileChooser.getChoosableFileFilters()[0]);
			fileChooser.setFileFilter(new FileNameExtensionFilter("Binär Datei","bin"));
			fileChooser.showOpenDialog(null);
			if (fileChooser.getSelectedFile() != null) {
				Layout loadedLayout = new LayoutManager().read(fileChooser.getSelectedFile());
				setWidgetLayout(loadedLayout);
			}
		});
		menuPopup.add(addWidgets);
		menuPopup.add(mainOptions);
		menuPopup.add(saveLayout);
		menuPopup.add(loadLayout);
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
		setWidgetLayout(layout);
	}
	
	private void setWidgetLayout(Layout layout) {
		String[] widgets = layout.getWidgets();
		while (actualWidgets.size() > 0) {
			actualWidgets.get(0).close();
		}
		for (String widget : widgets) {
			AbstractWidgetPanel widgetPanel = widgetLoader.loadWidget("bin/de/szut/SmartGadgetBar/Widgets/" + widget +"/" + widget + ".class").getPanel();
			actualWidgets.add(widgetPanel.getWidget());
			add(widgetPanel);
			rebuild();
		}
	}

	private String[] getWidgets() {
		String[] array = new String[actualWidgets.size()];
		for (int i = 0; i < actualWidgets.size(); i++) {
			array[i] = actualWidgets.get(i).getWidgetName();
		}
		return array;
	}

	public void addtoMenu(String[] widgets){
		for(int i = 0; i < widgets.length; i++){
			JMenuItem widget= new JMenuItem(widgets[i]);
			addWidgets.add(widget);
			int x = i;
			widget.addActionListener(e -> {
				AbstractWidgetPanel widgetPanel = widgetLoader.loadWidget("bin/de/szut/SmartGadgetBar/Widgets/" + widgets[x] +"/" + widgets[x] + ".class").getPanel();
				actualWidgets.add(widgetPanel.getWidget());
				add(widgetPanel);
				rebuild();
			});	
		}
		
	}
	@Override
	public void remove(Component comp) {
		if (comp instanceof AbstractWidgetPanel) {
			actualWidgets.remove(((AbstractWidgetPanel) comp).getWidget());
		}
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
