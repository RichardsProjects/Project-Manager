package net.richardsprojects.projecttracker;

import java.util.Date;

public class TimeSession {

	Date startTime;
	Date endTime;
	
	public TimeSession(Date startTime, Date endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	
}
