package net.richardsprojects.projecttracker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

public class Project {

	private String projectName;
	private List<TimeSession> timeSessions = new ArrayList<TimeSession>();
	private List<IncomeTransaction> incomeTransactions = new ArrayList<IncomeTransaction>();
	private List<Expense> expenses;
	private ProjectType projectType;
	private Date dateAdded;
	private ProjectStatus projectStatus;
	
	public Project(String name) {
		this.projectName = name;
		
		File projectDirectory = new File(Main.dataDirectory.getAbsolutePath() + File.separator + projectName);
    	File main = new File(projectDirectory.getAbsoluteFile() + File.separator + "main.txt");
    	        
        int type = 0;
        int status = 0;
        ProjectType projectType;
        Date parsedDate = null;
    	if(main.exists()) {
        	try {
				for (String line : Files.readAllLines(Paths.get(main.getAbsolutePath()))) {
				    if(line.contains("type: ")) {
				    	String typeStr = line;
				    	typeStr = typeStr.replace("type: ", "");
				    	try {
				    		type = Integer.parseInt(typeStr);
				    	} catch(NumberFormatException e) {
				    		break;
				    	}
				    	projectType = ProjectType.getType(type);
				    } else if(line.contains("date-added: ")) {
				    	String dateStr = line;
				    	dateStr = dateStr.replace("date-added: ", "");
				    	
				    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				    	try {
					    	parsedDate = formatter.parse(dateStr);
				    	} catch(ParseException e) {
				    		break;
				    	}
				    } else if(line.contains("status: ")) {
				    	String statusStr = line;
				    	statusStr = statusStr.replace("status: ", "");
				    	try {
				    		status = Integer.parseInt(statusStr);
				    	} catch(NumberFormatException e) {
				    		break;
				    	}
				    	projectStatus = ProjectStatus.getType(status);
				    }
				}
			} catch (IOException e) {}
        }
    	
    	projectType = ProjectType.getType(type);
    	setProjectType(projectType);
    	setDateAdded(parsedDate);
		
		// Load time sessions
    	File timeSessionsDirectory = new File(projectDirectory.getAbsolutePath() + File.separator + "timeSessions");
    	
    	String[] timeSessionNames = timeSessionsDirectory.list();
    	if(timeSessionNames.length > 0) {
    		for(String timeSession : timeSessionNames)
    		{
    			Date startTime = null;
    			Date endTime = null;
    			try {
    				for (String line : Files.readAllLines(Paths.get(timeSessionsDirectory.getAbsolutePath() + File.separator + timeSession))) {
    					if(line.contains("startTime: ")) {
    						String dateStr = line;
    						dateStr = dateStr.replace("startTime: ", "");
    						
    						DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    						startTime = sdf.parse(dateStr);    						
    					} else if(line.contains("endTime: ")) {
    						String dateStr = line;
    						dateStr = dateStr.replace("endTime: ", "");
    						
    						DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    						endTime = sdf.parse(dateStr);  
    					}
    				}					
    			} catch(Exception e) {
    				e.printStackTrace();
    			}
    			
    			DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    			
    			if(startTime != null && endTime != null) {
    				TimeSession session = new TimeSession(startTime, endTime);
    				timeSessions.add(session);
    			}
    		}
    	}
    	
		// Load income data
    	File incomeTransactionsDirectory = new File(projectDirectory.getAbsolutePath() + File.separator + "income");    	
    	String[] incomeTransactionsList = incomeTransactionsDirectory.list();
    	if(incomeTransactionsList.length > 0) {
    		for(String incomeTransaction : incomeTransactionsList)
    		{
    			Date date = null;
    			double income = 0;
    			try {
    				for (String line : Files.readAllLines(Paths.get(incomeTransactionsDirectory.getAbsolutePath() + File.separator + incomeTransaction))) {
    					if(line.contains("date: ")) {
    						String dateStr = line;
    						dateStr = dateStr.replace("date: ", "");
    						
    						DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    						date = sdf.parse(dateStr);    						
    					} else if(line.contains("income: ")) {
    						String incomeStr = line;
    						incomeStr = incomeStr.replace("income: ", "");
    						
    						income = Double.parseDouble(incomeStr);  
    					}
    				}					
    			} catch(Exception e) {
    				e.printStackTrace();
    			}
    			
    			if(income > 0 && date != null) {
    				System.out.println("Test");
    				IncomeTransaction incomeTrans = new IncomeTransaction(income, date);
    				incomeTransactions.add(incomeTrans);
    			}
    		}
    	}
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
	
	public boolean addTimeSessionData(Date startTime, Date endTime) {
		File timeSessionFolder = new File(Main.dataDirectory.getAbsolutePath() + File.separator + projectName + File.separator + "timeSessions");
		
		//Calculate File name
		Date day = new Date();
		SimpleDateFormat dayFormat = new SimpleDateFormat("MM_dd_yyyy");
		String fileName = dayFormat.format(day);
		String tempName = fileName;
		int number = 1;
		while(new File(timeSessionFolder.getAbsolutePath() + File.separator + tempName + ".txt").exists()) {
			tempName = fileName + "_" + number;
			number++;
		}
		
		//Print out data to file
		PrintWriter writer;
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			writer = new PrintWriter(timeSessionFolder.getAbsolutePath() + File.separator + tempName + ".txt", "UTF-8");
			writer.println("startTime: " + sdf.format(startTime));
			writer.println("endTime: " + sdf.format(endTime));
			writer.close();
			timeSessions.add(new TimeSession(startTime, endTime));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An error occured while attempting to save the data");
			return false;
		}
		return true;
	}
	
	public boolean addIncomeTransactionData(double money, Date date) {
		File incomeFolder = new File(Main.dataDirectory.getAbsolutePath() + File.separator + projectName + File.separator + "income");
		
		//Calculate File name
		Date day = new Date();
		SimpleDateFormat dayFormat = new SimpleDateFormat("MM_dd_yyyy");
		String fileName = dayFormat.format(day);
		String tempName = fileName;
		int number = 1;
		while(new File(incomeFolder.getAbsolutePath() + File.separator + tempName + ".txt").exists()) {
			tempName = fileName + "_" + number;
			number++;
		}
		
		//Print out data to file
		PrintWriter writer;
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			writer = new PrintWriter(incomeFolder.getAbsolutePath() + File.separator + tempName + ".txt", "UTF-8");
			writer.println("income: " + money);
			writer.println("date: " + sdf.format(date));
			writer.close();
			incomeTransactions.add(new IncomeTransaction(money, date));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "An error occured while attempting to save the data");
			return false;
		}
		return true;
	}
	
	//This is used when creating a new project
	//Thus nothing needs to be loaded from disk
	public Project(String name, ProjectType type) {
		this.projectName = name;
		this.projectType = type;
		this.dateAdded = new Date();
		this.projectStatus = ProjectStatus.IN_PROGRESS;
		
		//Create Folder & Data
		File folder = new File(Main.dataDirectory.getAbsolutePath() + File.separator + name);
		folder.mkdirs();
		
		PrintWriter writer;
		try {
			writer = new PrintWriter(folder.getAbsolutePath() + File.separator + "main.txt", "UTF-8");
			writer.println("type: " + ProjectType.getNumber(type));
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			writer.println("date-added: " + dateFormat.format(dateAdded));
			writer.println("status: 1");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		File income = new File(Main.dataDirectory.getAbsolutePath() + File.separator + name + File.separator + "income");
		income.mkdirs();
		
		File timeSessions = new File(Main.dataDirectory.getAbsolutePath() + File.separator + name + File.separator + "timeSessions");
		timeSessions.mkdirs();
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
		
		// Update the save file
		File folder = new File(Main.dataDirectory.getAbsolutePath() + File.separator + this.projectName);
		PrintWriter writer;
		try {
			writer = new PrintWriter(folder.getAbsolutePath() + File.separator + "main.txt", "UTF-8");
			writer.write("");
			writer.println("type: " + ProjectType.getNumber(this.projectType));
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			writer.println("date-added: " + dateFormat.format(dateAdded));
			writer.println("status: " + ProjectStatus.getNumber(status));
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
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
}
