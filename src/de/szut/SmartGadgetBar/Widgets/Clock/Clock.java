package de.szut.SmartGadgetBar.Widgets.Clock;

import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.szut.SmartGadgetBar.GUI.Clock_UI;
import de.szut.SmartGadgetBar.Model.WidgetInterface;

public class Clock  implements WidgetInterface{
	private Clock_UI ui;
	private TimeThread time;
	private Properties props;
	
	public Clock(){
		TimeZone timeZone = Calendar.getInstance().getTimeZone(); 
		ui = new Clock_UI(this);
		time = new TimeThread(ui.getMainTime(), timeZone.getID(), true);
		threads.add(time);
		time.start();
		loadProperties();
	}
	public void stopThread(){
		time.setRunning(false);
	}
	public void setOtherTimeLabels(JLabel label, String timezone){
		time = new TimeThread(label, timezone, false);
		threads.add(time);
		time.start();
	}
	@Override
	public JPanel getPanel() {
		// TODO Auto-generated method stub
		return ui;
	}

	@Override
	public void setProperties(Properties properties) {
		props = properties;
		saveProperties();
	}

	@Override
	public Properties getProperties() {
		// TODO Auto-generated method stub
		return props;
	}

	@Override
	public String getWidgetName() {
		// TODO Auto-generated method stub
		return "Clock";
	}
}
