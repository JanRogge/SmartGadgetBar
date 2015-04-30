package de.szut.SmartGadgetBar.Widgets.Clock;

import java.util.Properties;

import javax.swing.JPanel;

import de.szut.SmartGadgetBar.GUI.Clock_UI;
import de.szut.SmartGadgetBar.Model.WidgetInterface;

public class Clock  implements WidgetInterface{
	private Clock_UI ui;
	private TimeThread time;
	private boolean otherTimeZones;
	public Clock(){
		System.out.println("Clock");
		ui = new Clock_UI(this);
		time = new TimeThread(ui.getMainTime());
		time.start();
	}
	public void stopThread(){
		time.setRunning(false);
	}
//	public static void main(String[] args) throws IOException {
//		Calendar c = new GregorianCalendar();
//		Date date1 = new Date();
//		SimpleDateFormat df = new SimpleDateFormat();
//		df.setTimeZone(TimeZone.getTimeZone("GMT+2"));
//		//System.out.println(df.format(date1));
//    }
	public boolean otherTimeZonesSelectet(){
		if(otherTimeZones){
			time.otherTimeZones(ui.getTimeLabel(), ui.getZoneName(), "GMT+3");
			return true;
		}
		return false;
	}
	@Override
	public JPanel getPanel() {
		// TODO Auto-generated method stub
		return ui;
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWidgetName() {
		// TODO Auto-generated method stub
		return "Clock";
	}
	
}
