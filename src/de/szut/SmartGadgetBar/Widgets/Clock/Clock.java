package de.szut.SmartGadgetBar.Widgets.Clock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;

import javax.swing.JLabel;

import de.szut.SmartGadgetBar.GUI.AbstractWidgetPanel;
import de.szut.SmartGadgetBar.GUI.Clock_UI;
import de.szut.SmartGadgetBar.Model.WidgetInterface;

public class Clock  implements WidgetInterface{
	public static final String CITIES = "cities";
	private Clock_UI ui;
	private TimeThread time;
	private Properties props;
	ArrayList<TimeThread> threads = new ArrayList<TimeThread>();
	
	public Clock(){
		TimeZone timeZone = Calendar.getInstance().getTimeZone(); 
		ui = new Clock_UI(this);
		time = new TimeThread(ui.getMainTime(), timeZone.getID(), true);
		threads.add(time);
		time.start();
		loadProperties();
	}
	public void stopThread(){
		for (TimeThread thread : threads) {
			thread.setRunning(false);
		}
	}
	public void setOtherTimeLabels(JLabel label, String timezone){
		if(label.getText() != ""){
			for (TimeThread thread : threads) {
				if(label == thread.getLabel()){
					thread.setRunning(false);
					time = new TimeThread(label, timezone, false);
				}		
			}
			threads.add(time);
		} else{
			time = new TimeThread(label, timezone, false);
			threads.add(time);
		}
		time.start();
	}
	@Override
	public AbstractWidgetPanel getPanel() {
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
	
	@Override
	public Properties getDefaultProperties() {
		Properties defaultProperties = new Properties();
		return defaultProperties;
	}
	@Override
	public void close() {
		// TODO Auto-generated method stub
		stopThread();
	}
}
