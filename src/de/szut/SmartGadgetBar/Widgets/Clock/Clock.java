package de.szut.SmartGadgetBar.Widgets.Clock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;

import javax.swing.JLabel;

import de.szut.SmartGadgetBar.GUI.AbstractWidgetPanel;
import de.szut.SmartGadgetBar.GUI.Clock_UI;
import de.szut.SmartGadgetBar.Model.WidgetInterface;

/**
 * Die Uhr
 * 
 * @author Jan-Philipp Rogge
 *
 */
public class Clock implements WidgetInterface {
	private Clock_UI ui;
	private TimeThread time;
	private Properties props;
	ArrayList<TimeThread> threads = new ArrayList<TimeThread>();

	/**
	 * Erzeugt ein neues Objekt der Clock
	 */
	public Clock() {
		TimeZone timeZone = Calendar.getInstance().getTimeZone();
		ui = new Clock_UI(this);
		time = new TimeThread(ui.getMainTime(), timeZone.getID(), true);
		threads.add(time);
		time.start();
		loadProperties();
	}

	/**
	 * Stopt die Uhr
	 */
	public void stopThread() {
		for (TimeThread thread : threads) {
			thread.setRunning(false);
		}
	}

	/**
	 * Stopt den Thread eies Labels
	 * 
	 * @param label
	 *            das zu pausierende Label
	 */
	public void stoponeLabel(JLabel label) {
		if (!threads.isEmpty()) {
			for (int i = 0; i < threads.size(); i++) {
				if (label == threads.get(i).getLabel()) {
					threads.get(i).setRunning(false);
				}
			}
		}

	}

	/**
	 * ----------------------------------------------------------------------
	 * @param label
	 * @param timezone
	 */
	public void setOtherTimeLabels(JLabel label, String timezone) {
		if (label.getText() != "") {
			for (TimeThread thread : threads) {
				if (label == thread.getLabel()) {
					thread.setRunning(false);
					time = new TimeThread(label, timezone, false);
					time.start();
				}
			}
			threads.add(time);
		} else {
			time = new TimeThread(label, timezone, false);
			time.start();
			threads.add(time);
		}
	}

	@Override
	public AbstractWidgetPanel getPanel() {
		return ui;
	}

	@Override
	public void setProperties(Properties properties) {
		props = properties;
		saveProperties();
	}

	@Override
	public Properties getProperties() {
		return props;
	}

	@Override
	public String getWidgetName() {
		return "Clock";
	}

	@Override
	public Properties getDefaultProperties() {
		Properties defaultProperties = new Properties();
		return defaultProperties;
	}

	@Override
	public void close() {
		stopThread();
	}
}
