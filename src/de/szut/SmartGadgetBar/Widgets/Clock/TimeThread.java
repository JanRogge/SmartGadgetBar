package de.szut.SmartGadgetBar.Widgets.Clock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import de.szut.SmartGadgetBar.GUI.Clock_UI;

public class TimeThread extends Thread{
	 protected boolean isRunning;

     protected JLabel dateLabel;
     protected JLabel timeLabel;
     private Clock_UI ui;
     private Date currentTime;
     private Calendar currentCalendar;
     private static final String MAINTIMEZONE = "GMT+2";

     protected SimpleDateFormat timeFormat =
             new SimpleDateFormat("h:mm:ss");

     public TimeThread(JLabel timeLabel, Clock_UI ui) {
         //this.dateLabel = dateLabel;
         this.timeLabel = timeLabel;
         this.isRunning = true;
         this.ui = ui;
     }
     public void mainTimeZone(){
    	 currentCalendar = Calendar.getInstance();
    	 currentTime = currentCalendar.getTime();
    	 timeFormat.setTimeZone(TimeZone.getTimeZone(MAINTIMEZONE));
    	 timeLabel.setText(timeFormat.format(currentTime));
     }
     public void otherTimeZones(JLabel time, JLabel zone, String timezone){
    	 currentCalendar = Calendar.getInstance();
    	 currentTime = currentCalendar.getTime();
    	 timeFormat.setTimeZone(TimeZone.getTimeZone(timezone));
    	 time.setText(timeFormat.format(currentTime));
    	 zone.setText(timezone);
     }
     public void setTimeUI(){
    	 mainTimeZone();
    	 otherTimeZones(ui.getTimeLabel(), ui.getZoneName(), "GMT+3");
     }

     @Override
     public void run() {
         while (isRunning) {
             SwingUtilities.invokeLater(new Runnable() {
                 @Override
                 public void run() {
                     setTimeUI();
                 }
             });

             try {
                 Thread.sleep(1000);
             } catch (InterruptedException e) {
             }
         }
     }

     public void setRunning(boolean isRunning) {
         this.isRunning = isRunning;
     }
}
