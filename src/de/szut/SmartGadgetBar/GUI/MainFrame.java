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
		props = new PropertyLoader().loadProperties("config/config.ini", getDefaultProperties());
		boolean onTop = false;
		if(props.getProperty("alwaysontop").equals("true")){
			onTop = true;
		} else{
			onTop = false;
		}
		setAlwaysOnTop(onTop);
		setUndecorated(true);
		setType(javax.swing.JFrame.Type.UTILITY);
		setContentPane(new BackgroundPanel(this, new LayoutManager().read(new File(props.getProperty("defaultLayoutPath")))));
		windowDimension = new Rectangle();
		windowDimension.setBounds(Double.valueOf(props.getProperty("position.x")).intValue(),Double.valueOf(props.getProperty("position.y")).intValue(), Integer.parseInt(props.getProperty("size.x")), Integer.parseInt(props.getProperty("size.y")));	
		setBounds(windowDimension);
		setShape(new RoundRectangle2D.Double(0, 0, Integer.parseInt(props.getProperty("size.x")), Integer.parseInt(props.getProperty("size.y")), 30, 30));
	}

	public void close(Layout layout){
		setVisible(false);
		dispose();
		new LayoutManager().write(layout, props.getProperty("defaultLayoutPath"));
		windowDimension = getBounds();
		props.setProperty("position.x",String.valueOf(windowDimension.getX()));
		props.setProperty("position.y",String.valueOf(windowDimension.getY()));
		props.setProperty("size.x", String.valueOf(windowDimension.width));
		props.setProperty("size.y", String.valueOf(windowDimension.height));
		props.setProperty("alwaysontop", String.valueOf(this.isAlwaysOnTop()));
		new PropertyLoader().saveProperties(props, "config/config.ini");
		System.exit(0);
	}
	
	private Properties getDefaultProperties() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) (screenSize.getHeight() -50);
		System.out.println(String.valueOf(height));
		Properties defaultProps = new Properties();
		defaultProps.setProperty("defaultLayoutPath", "defaultLayout");
		defaultProps.setProperty("size.y", String.valueOf(height));
		defaultProps.setProperty("position.y", "75.0");
		defaultProps.setProperty("size.x", "300");
		defaultProps.setProperty("position.x", "383.0");
		defaultProps.setProperty("availableWidgets", "Clock,Search,PGP,ZIP,Calculator,Notes");
		defaultProps.setProperty("alwaysontop", "false");
		return defaultProps;
	}
	
}
