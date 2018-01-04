package net.richardsprojects.projecttracker;

import net.richardsprojects.projecttracker.data.TimeSession;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Utils {

	public static Map<TimeUnit,Long> computeDiff(Date date1, Date date2) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    List<TimeUnit> units = new ArrayList<TimeUnit>();
	    units.add(TimeUnit.SECONDS);
	    units.add(TimeUnit.MINUTES);
	    units.add(TimeUnit.HOURS);
	    Collections.reverse(units);
	    Map<TimeUnit,Long> result = new LinkedHashMap<TimeUnit,Long>();
	    long milliesRest = diffInMillies;
	    for ( TimeUnit unit : units ) {
	        long diff = unit.convert(milliesRest,TimeUnit.MILLISECONDS);
	        long diffInMilliesForUnit = unit.toMillis(diff);
	        milliesRest = milliesRest - diffInMilliesForUnit;
	        result.put(unit,diff);
	    }
	    return result;
	}

	public static String totalTimeSessionList(ArrayList<TimeSession> sessions) {
		String time = "";
		long hours = 0;
		long minutes = 0;
		long seconds = 0;
		Date date = new Date();

		for(TimeSession session : sessions) {
			Date startTime = session.getStartTime();
			Date endTime = session.getEndTime();

			Map<TimeUnit,Long> times = Utils.computeDiff(startTime, endTime);
			seconds = seconds + times.get(TimeUnit.SECONDS);
			minutes = minutes + times.get(TimeUnit.MINUTES);
			hours = hours + times.get(TimeUnit.HOURS);

			if (seconds > 59) {
				long moreSeconds = seconds - 60;
				long moreMinutes = moreSeconds/60;
				long newSeconds = moreSeconds % 60;
				minutes = minutes + moreMinutes + 1;
				seconds = newSeconds;
			}

			if (minutes > 59) {
				long moreMinutes = minutes - 60;
				long moreHours = moreMinutes / 60;
				long newMinutes = moreMinutes % 60;
				hours = hours + moreHours + 1;
				minutes = newMinutes;
			}
		}

		time = hours + " hours " + minutes +  " minutes " + seconds + " seconds";
		return time;
	}
	
}
