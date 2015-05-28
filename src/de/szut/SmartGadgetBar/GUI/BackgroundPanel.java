package de.szut.SmartGadgetBar.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.szut.SmartGadgetBar.Model.Layout;
import de.szut.SmartGadgetBar.Model.LayoutManager;
import de.szut.SmartGadgetBar.Model.PropertyLoader;
import de.szut.SmartGadgetBar.Model.WidgetInterface;
import de.szut.SmartGadgetBar.Model.WidgetLoader;

/**
 * Das Hintergrundpanel für die GUI auf dem die Widgets liegen
 * 
 * @author Jan-Philipp Rogge, Simeon Kublenz
 *
 */
public class BackgroundPanel extends JPanel {

	private static final long serialVersionUID = -2546051500075793695L;
	private int pX, pY;
	private JMenu addWidgets;
	private WidgetLoader widgetLoader;
	public static final int GAP = 5;
	private int actualHeight = 0;
	private String path;
	private ArrayList<WidgetInterface> actualWidgets = new ArrayList<WidgetInterface>();

	/**
	 * Creates the panel.
	 * 
	 * @param mainf
	 *            Das Mainframe auf dem dieses Panel liegt
	 * @param layout
	 *            Das Layout dass als Startlayout geladen wird
	 */
	public BackgroundPanel(MainFrame mainf, Layout layout) {
		widgetLoader = new WidgetLoader();
		JPopupMenu menuPopup = new JPopupMenu();
		JMenuItem closeMenuItem = new JMenuItem("Close");
		JMenuItem mainOptions = new JMenuItem("Options");
		JMenuItem saveLayout = new JMenuItem("Save Layout");
		JMenuItem loadLayout = new JMenuItem("Load Layout");
		addWidgets = new JMenu("Add Widgets");
		String[] availabelWidgets = new PropertyLoader()
				.loadProperties("config/config.ini", null)
				.getProperty("availableWidgets").split(",");
		addToMenu(availabelWidgets);
		closeMenuItem.addActionListener(e -> {
			mainf.close(new Layout(getWidgets()));
		});
		mainOptions.addActionListener(e -> {
			new OptionPanelMain(mainf);
		});
		saveLayout.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.removeChoosableFileFilter(fileChooser
					.getChoosableFileFilters()[0]);
			fileChooser.setFileFilter(new FileNameExtensionFilter(
					"Binär Datei", "bin"));
			fileChooser.showOpenDialog(null);
			if (fileChooser.getSelectedFile() != null) {
				new LayoutManager().write(new Layout(getWidgets()), fileChooser
						.getSelectedFile().getAbsolutePath());
			}
		});
		loadLayout.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.removeChoosableFileFilter(fileChooser
					.getChoosableFileFilters()[0]);
			fileChooser.setFileFilter(new FileNameExtensionFilter(
					"Binär Datei", "bin"));
			fileChooser.showOpenDialog(null);
			if (fileChooser.getSelectedFile() != null) {
				Layout loadedLayout = new LayoutManager().read(fileChooser
						.getSelectedFile());
				setWidgetLayout(loadedLayout);
			}
		});
		menuPopup.add(addWidgets);
		menuPopup.add(mainOptions);
		menuPopup.add(closeMenuItem);
		menuPopup.add(saveLayout);
		menuPopup.add(loadLayout);
		setComponentPopupMenu(menuPopup);
		setLayout(null);
		setImage("graphics/05.jpg");

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				pX = me.getX();
				pY = me.getY();
			}
		});
		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent me) {
				mainf.setLocation(mainf.getLocation().x + me.getX() - pX,
						mainf.getLocation().y + me.getY() - pY);
			}
		});
		setWidgetLayout(layout);
	}

	/**
	 * Setzt das aktuelle Layout
	 * 
	 * @param layout
	 *            Das Layout welches geladen werden soll
	 */
	private void setWidgetLayout(Layout layout) {
		if (layout != null) {
			String[] widgets = layout.getWidgets();
			while (actualWidgets.size() > 0) {
				actualHeight -= actualWidgets.get(0).getPanel().getHeight() + 5;
				actualWidgets.get(0).close();
			}
			for (String widget : widgets) {
				AbstractWidgetPanel widgetPanel = widgetLoader.loadWidget(
						"bin/de/szut/SmartGadgetBar/Widgets/" + widget + "/"
								+ widget + ".class").getPanel();
				actualWidgets.add(widgetPanel.getWidget());
				actualHeight += widgetPanel.getHeight() + 5;
				add(widgetPanel);
				rebuild();
			}
		}
	}

	/**
	 * Gibt die Namen der aktuell enthaltenen Widgets zurück
	 * 
	 * @return Die Liste der aktuellen Widgets
	 */
	private String[] getWidgets() {
		String[] array = new String[actualWidgets.size()];
		for (int i = 0; i < actualWidgets.size(); i++) {
			array[i] = actualWidgets.get(i).getWidgetName();
		}
		return array;
	}

	/**
	 * Fügt die Liste von Widgetnamen dem "Add Widgets" Menü im PopupMenu hinzu
	 * 
	 * @param widgets
	 *            Die Liste der hinzuzufügenden Widgets
	 */
	private void addToMenu(String[] widgets) {
		for (int i = 0; i < widgets.length; i++) {
			JMenuItem widget = new JMenuItem(widgets[i]);
			addWidgets.add(widget);
			int x = i;
			widget.addActionListener(e -> {
				AbstractWidgetPanel widgetPanel = widgetLoader.loadWidget(
						"bin/de/szut/SmartGadgetBar/Widgets/" + widgets[x]
								+ "/" + widgets[x] + ".class").getPanel();
				if (actualHeight + widgetPanel.getHeight() <= getHeight()) {
					actualWidgets.add(widgetPanel.getWidget());
					actualHeight += widgetPanel.getHeight() + 5;
					add(widgetPanel);
					rebuild();
				} else {
					JOptionPane
							.showMessageDialog(
									null,
									"Die WidgetBar kann dieses Widget nicht mehr aufnehmen",
									"Die WidgetBar ist voll",
									JOptionPane.WARNING_MESSAGE);
				}
			});
		}
	}

	@Override
	public void remove(Component comp) {
		if (comp instanceof AbstractWidgetPanel) {
			actualWidgets.remove(((AbstractWidgetPanel) comp).getWidget());
		}
		actualHeight -= comp.getHeight() + 5;
		super.remove(comp);
		this.rebuild();
	}

	@Override
	public void paintComponent(Graphics g) {
		BufferedImage image = null;
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (image == null) {
			graphics.setColor(Color.cyan);
		} else {
			graphics.drawImage(image, 0, 0, null);
		}
	};

	/**
	 * Richtet die Components in diesem Panel neu an
	 */
	public void rebuild() {
		int curpos = BackgroundPanel.GAP;
		for (Component c : getComponents()) {
			c.setLocation(10, curpos);
			curpos += c.getHeight() + BackgroundPanel.GAP;
		}
		repaint();
		revalidate();
	}

	/**
	 * Setzt das Hintergrundbild dieses Components
	 * 
	 * @param path
	 *            Der Pfad des neuen Hintergrundbildes
	 */
	public void setImage(String path) {
		this.path = path;
	}

	/**
	 * Entfernt alle Widgets von diesem Panel
	 */
	public void closeAll() {
		for (WidgetInterface widget : actualWidgets) {
			widget.close();
		}
	}
}
