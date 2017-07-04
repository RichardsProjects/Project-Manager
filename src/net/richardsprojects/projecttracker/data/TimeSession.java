package net.richardsprojects.projecttracker.data;

import java.util.Date;

public class TimeSession {

	private final Date startTime;
	private final Date endTime;
	
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
