package de.szut.SmartGadgetBar.GUI;

import java.awt.Rectangle;
import java.util.Properties;

import javax.swing.JFrame;

import de.szut.SmartGadgetBar.Model.PropertyLoader;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8252911925423161561L;
	private Properties props;
	private Rectangle windowDimension;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		props = new PropertyLoader().loadProperties("config/config.ini");
		setAlwaysOnTop(Boolean.getBoolean(props.getProperty("alwaysontop")));
		setUndecorated(true);
		setType(javax.swing.JFrame.Type.UTILITY);
		setContentPane(new BackgroundPanel(this));
		windowDimension = new Rectangle();
		windowDimension.setBounds(Double.valueOf(props.getProperty("position.x")).intValue(),Double.valueOf(props.getProperty("position.y")).intValue(), Integer.parseInt(props.getProperty("size.x")), Integer.parseInt(props.getProperty("size.y")));
		setBounds(windowDimension);
	}

	public void close(){
		setVisible(false);
		dispose();
		windowDimension = getBounds();
		props.setProperty("position.x",String.valueOf(windowDimension.getX()));
		props.setProperty("position.y",String.valueOf(windowDimension.getY()));
		props.setProperty("size.x", String.valueOf(windowDimension.width));
		props.setProperty("size.y", String.valueOf(windowDimension.height));
		props.setProperty("alwaysontop",String.valueOf(isAlwaysOnTop()));
		new PropertyLoader().saveProperties(props, "config/config.ini");;
	}
	
	private Properties getDefaultProperties() {
		Properties defaultProps = new Properties();
		defaultProps.setProperty("size.y", "600");
		defaultProps.setProperty("position.y", "75.0");
		defaultProps.setProperty("size.x", "300");
		defaultProps.setProperty("position.x", "383.0");
		defaultProps.setProperty("availableWidgets", "Clock,Calc,PGP,ZIP,Calculator,Notes");
		defaultProps.setProperty("alwaysontop", "false");
		return defaultProps;
	}
}
