package net.richardsprojects.projecttracker.data;

import net.richardsprojects.projecttracker.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Project {

	private final String projectName;
	private List<TimeSession> timeSessions = new ArrayList<TimeSession>();
	private List<IncomeTransaction> incomeTransactions = new ArrayList<IncomeTransaction>();
	private ProjectType projectType;
	private Date dateAdded;
	private ProjectStatus projectStatus;

	/**
	 * This constructor is used when loading projects in which you have all the data.
	 *
	 * @param projectName name of the project
	 * @param timeSessions list of time sessions
	 * @param incomeTransactions list of income transactions
	 * @param projectType type of the project
	 * @param dateAdded date added
	 * @param projectStatus status of the project
	 */
	public Project(String projectName, List<TimeSession> timeSessions, List<IncomeTransaction> incomeTransactions, ProjectType projectType, Date dateAdded, ProjectStatus projectStatus) {
		this.projectName = projectName;
		this.timeSessions = timeSessions;
		this.incomeTransactions = incomeTransactions;
		this.projectType = projectType;
		this.dateAdded = dateAdded;
		this.projectStatus = projectStatus;
	}
	
	public void setProjectType(ProjectType type) {
		projectType = type;
	}
	
	public ProjectType getProjectType() {
		return projectType;
	}
	
	public void setDateAdded(Date date) {
		dateAdded = date;
	}
	
	public Date getDateAdded() {
		return dateAdded;
	}
	
	public void addTimeSessionData(Date startTime, Date endTime) {
		timeSessions.add(new TimeSession(startTime, endTime));
	}
	
	public void addIncomeTransactionData(double money, Date date) {
		incomeTransactions.add(new IncomeTransaction(money, date));
	}

	/**
	 * This constructor is used when creating new projects.
	 *
	 * @param name name of the new project
	 * @param type type of the new project
	 */
	public Project(String name, ProjectType type) {
		this.projectName = name;
		this.projectType = type;
		this.dateAdded = new Date();
		this.projectStatus = ProjectStatus.IN_PROGRESS;
	}
	
	public String getName() {
		return this.projectName;
	}
	
	public String getTotalTime() {
		String time = "";
		long hours = 0;
		long minutes = 0;
		long seconds = 0;
		
		for(TimeSession session : timeSessions) {
			Date startTime = session.getStartTime();
			Date endTime = session.getEndTime();
			
			Map<TimeUnit,Long> times = Utils.computeDiff(startTime, endTime);
			seconds = seconds + times.get(TimeUnit.SECONDS);
			minutes = minutes + times.get(TimeUnit.MINUTES);
			hours = hours + times.get(TimeUnit.HOURS);
			
			if(seconds > 59) {
				long moreSeconds = seconds - 60;
				long moreMinutes = moreSeconds/60;
				long newSeconds = moreSeconds % 60;
				minutes = minutes + moreMinutes + 1;
				seconds = newSeconds;
			}
			
			if(minutes > 59) {
				long moreMinutes = minutes - 60;
				long moreHours = moreMinutes/60;
				long newMinutes = moreMinutes % 60;
				hours = hours + moreHours + 1;
				minutes = newMinutes;
			}
		}
		
		time = hours + " hours " + minutes +  " minutes " + seconds + " seconds";
		return time;
	}
	
	public double getTotalIncome() {
		double totalIncome = 0;
		
		for(IncomeTransaction transaction : incomeTransactions) {
			totalIncome = totalIncome + transaction.getIncome();
		}
		
		return totalIncome;
	}
	
	public ProjectStatus getProjectStatus() {
		return this.projectStatus;
	}
	
	public void setProjectStatus(ProjectStatus status) {
		this.projectStatus = status; // Update in Project class
	}
	
	public String getMonthlyTime() {
		String time = "";
		long hours = 0;
		long minutes = 0;
		long seconds = 0;
		Date date = new Date();
		
		for(TimeSession session : timeSessions) {
			Date startTime = session.getStartTime();
			Date endTime = session.getEndTime();			
			
			if(startTime.getYear() == date.getYear() && startTime.getMonth() == date.getMonth()) {
				Map<TimeUnit,Long> times = Utils.computeDiff(startTime, endTime);
				seconds = seconds + times.get(TimeUnit.SECONDS);
				minutes = minutes + times.get(TimeUnit.MINUTES);
				hours = hours + times.get(TimeUnit.HOURS);
				
				if(seconds > 59) {
					long moreSeconds = seconds - 60;
					long moreMinutes = moreSeconds/60;
					long newSeconds = moreSeconds % 60;
					minutes = minutes + moreMinutes + 1;
					seconds = newSeconds;
				}
				
				if(minutes > 59) {
					long moreMinutes = minutes - 60;
					long moreHours = moreMinutes/60;
					long newMinutes = moreMinutes % 60;
					hours = hours + moreHours + 1;
					minutes = newMinutes;
				}
			}
		}
		
		time = hours + " hours " + minutes +  " minutes " + seconds + " seconds";
		return time;
	}
	
	public double getMonthlyIncome() {
		double totalIncome = 0;
		Date date = new Date();
		
		for(IncomeTransaction transaction : incomeTransactions) {
			if(transaction.getDate().getMonth() == date.getMonth() && transaction.getDate().getYear() == date.getYear()) {
				totalIncome = totalIncome + transaction.getIncome();
			}
		}
		
		return totalIncome;
	}

	public List<TimeSession> getTimeSessions() {
		return timeSessions;
	}

	public List<IncomeTransaction> getIncomeTransactions() {
		return incomeTransactions;
	}
}
