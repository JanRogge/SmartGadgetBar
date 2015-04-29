package de.szut.SmartGadgetBar.Widgets.Clock;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Clock {
	public static void main(String[] args) throws IOException {
		Calendar c = new GregorianCalendar();
		Date date1 = new Date();
		SimpleDateFormat df = new SimpleDateFormat();
		df.setTimeZone(TimeZone.getTimeZone("PST"));
		System.out.println(df.format(date1));
    }
}
