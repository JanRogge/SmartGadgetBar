package de.szut.SmartGadgetBar.Widgets.Clock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JLabel;

public class TimeThread extends Thread {
	protected boolean isRunning = true;
	protected JLabel timeLabel;
	private Date currentTime;
	private Calendar currentCalendar;
	private String timeZone;
	private boolean maintime;

	protected SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	public TimeThread(JLabel timeLabel, String timeZone, boolean maintime) {
		this.timeLabel = timeLabel;
		this.timeZone = timeZone;
		this.maintime = maintime;
	}

	public void mainTimeZone() {
		currentCalendar = Calendar.getInstance();
		currentTime = currentCalendar.getTime();
		timeFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		if (maintime) {
			timeLabel.setText(timeFormat.format(currentTime));
		} else {
			timeLabel.setText("<html><body>" + timeZone + "<br>"
					+ timeFormat.format(currentTime) + "</body></html>");
		}

	}

	@Override
	public void run() {
		while (isRunning) {
			mainTimeZone();
			try {
				sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public JLabel getLabel() {
		return timeLabel;
	}
}
