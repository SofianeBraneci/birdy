package tools;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class DateConverter {

	public static long getDateDiff(Timestamp old, Timestamp current, TimeUnit unit) {
		long diff = current.getTime() - old.getTime();
		System.out.println(diff);
		return unit.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	public static String getCurrentDate() {
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMM Y, HH:mm");
		return dateTime.format(formatter);
	}
	
}
