package de.szut.SmartGadgetBar.GUI;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.util.Properties;

import javax.swing.JFrame;

import de.szut.SmartGadgetBar.Model.Layout;
import de.szut.SmartGadgetBar.Model.LayoutManager;
import de.szut.SmartGadgetBar.Model.PropertyLoader;

/**
 * Das Hauptfenster auf dem die Widgets angezeigt werden
 * 
 * @author Jan-Philipp Rogge
 *
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = -8252911925423161561L;
	private Properties props;
	private Rectangle windowDimension;

	/**
	 * Erzeugt ein neues Objekt des Hauptfensters
	 */
	public MainFrame() {
		props = new PropertyLoader().loadProperties("config/config.ini",
				getDefaultProperties());
		boolean onTop = false;
		if (props.getProperty("alwaysontop").equals("true")) {
			onTop = true;
		} else {
			onTop = false;
		}
		setAlwaysOnTop(onTop);
		setUndecorated(true);
		setType(javax.swing.JFrame.Type.UTILITY);
		setContentPane(new BackgroundPanel(this,
				new LayoutManager().read(new File(props
						.getProperty("defaultLayoutPath")))));
		windowDimension = new Rectangle();
		try {
			windowDimension.x = Double.valueOf(props.getProperty("position.x"))
					.intValue();
		} catch (NumberFormatException e) {
			windowDimension.x = 383;
			props.setProperty("position.x", "383.0");
		}
		try {
			windowDimension.y = Double.valueOf(props.getProperty("position.y"))
					.intValue();
		} catch (NumberFormatException e) {
			windowDimension.y = 75;
			props.setProperty("position.y", "75.0");
		}
		try {
			windowDimension.width = Double.valueOf(props.getProperty("size.x"))
					.intValue();
		} catch (NumberFormatException e) {
			windowDimension.width = 300;
			props.setProperty("size.x", "300.0");
		}
		try {
			windowDimension.height = Double
					.valueOf(props.getProperty("size.y")).intValue();
		} catch (NumberFormatException e) {
			windowDimension.height = 300;
			props.setProperty(
					"size.y",
					String.valueOf(Toolkit.getDefaultToolkit().getScreenSize()
							.getHeight() - 50));
		}
		setBounds(windowDimension);
		setShape(new RoundRectangle2D.Double(0, 0, Double.valueOf(props
				.getProperty("size.x")), Double.valueOf(props
				.getProperty("size.y")), 30, 30));
	}

	/**
	 * Schließt das Hauptfenster uns speichert alles
	 * 
	 * @param layout
	 *            Das Layout welches als neues Startlayout gespeichert wird
	 */
	public void close(Layout layout) {
		setVisible(false);
		dispose();
		((BackgroundPanel) this.getContentPane()).closeAll();
		new LayoutManager().write(layout,
				props.getProperty("defaultLayoutPath"));
		windowDimension = getBounds();
		props.setProperty("position.x", String.valueOf(windowDimension.getX()));
		props.setProperty("position.y", String.valueOf(windowDimension.getY()));
		props.setProperty("size.x", String.valueOf(windowDimension.width));
		props.setProperty("size.y", String.valueOf(windowDimension.height));
		props.setProperty("alwaysontop", String.valueOf(this.isAlwaysOnTop()));
		new PropertyLoader().saveProperties(props, "config/config.ini");
		System.exit(0);
	}

	/**
	 * Gibt die Standartoptionen des Fensters zurück
	 * 
	 * @return Die Standartoptionen des Fensters
	 */
	private Properties getDefaultProperties() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double height = screenSize.getHeight() - 50;
		Properties defaultProps = new Properties();
		defaultProps.setProperty("defaultLayoutPath", "layouts/defaultLayout");
		defaultProps.setProperty("size.y", String.valueOf(height));
		defaultProps.setProperty("position.y", "75.0");
		defaultProps.setProperty("size.x", "300");
		defaultProps.setProperty("position.x", "383.0");
		defaultProps.setProperty("availableWidgets",
				"Clock,Search,PGP,ZIP,Calculator,Notes");
		defaultProps.setProperty("alwaysontop", "false");
		return defaultProps;
	}
}