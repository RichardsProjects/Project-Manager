package net.richardsprojects.projecttracker;

import net.richardsprojects.projecttracker.data.*;
import net.richardsprojects.projecttracker.windows.MainWindow;
import net.richardsprojects.projecttracker.windows.SplashWindow;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Main {

	public static MainWindow mainWindow;
	private static SplashWindow splashWindow;
	public static JFrame currentDialog = null;

	public static List<Project> projects;
	
	private static File dataDirectory;
	public static boolean alternateDirectorySet = false;
	
	public static Screens currentScreen = Screens.IN_PROGRESS;
	public static Project historyProject = null;
	public static Screens previousScreen = null;

	public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public static boolean closeRequested = false;

	public static void main(String[] args) {
		// create splash window
		BufferedImage bg = null;
		try {
			//noinspection ConstantConditions
			bg = ImageIO.read(Main.class.getClassLoader().getResource("splash.png"));
		} catch (Exception e) {
			System.out.println("[WARNING] Unable to load splash background.");
		}
		splashWindow = new SplashWindow(bg);

		// setup
		projects = new ArrayList<Project>();
		
		dataDirectory = new File(defaultDirectory());
		
		// create data directory if it doesn't already exist
		if(!dataDirectory.exists()) dataDirectory.mkdirs(); 

		// TODO: Add a settings menu to specify/change the "alternate path"
		// check for alternate path
		File alternatePath = new File(dataDirectory, ".alternatePath");
		if (alternatePath.exists()) {
		    try {
                String path = (new String(Files.readAllBytes(Paths.get(alternatePath.getAbsolutePath())), StandardCharsets.UTF_8)).trim();
                dataDirectory = new File(path);
                alternateDirectorySet = true;
                if (!dataDirectory.exists()) dataDirectory.mkdirs();
            } catch (IOException e) {
                e.printStackTrace();
            }
		}

		// TODO: Look for a .lock file and stop the application if there is one

		// load all projects
		File projectFile = new File(dataDirectory, "projects.json");
		if (projectFile.exists()) {
			String json = null;
			try {
				json = new String(Files.readAllBytes(Paths.get(projectFile.getAbsolutePath())), StandardCharsets.UTF_8);
				JSONObject jsonObject = new JSONObject(json);

				for (Map.Entry<String, Object> entry : jsonObject.toMap().entrySet()) {
					String projectName = entry.getKey();
					JSONObject projectObject = jsonObject.getJSONObject(projectName);
					Date date = null;

					try {
						date = formatter.parse(projectObject.getString("date-added")); // date
					} catch (Exception e) {
						System.out.println("There was an exception parsing the date " + projectObject.getString("date-added"));
						continue;
					}

					ProjectStatus status = ProjectStatus.getEnumFromName(projectObject.getString("status")); // status
					ProjectType type = ProjectType.getEnumFromName(projectObject.getString("type")); // type

					// time sessions
					JSONArray timeSessions = projectObject.getJSONArray("time-sessions");
					List<TimeSession> timeSessionList = new ArrayList<TimeSession>();
					for (int i = 0; i < timeSessions.length(); i++) {
						JSONObject timeObject = timeSessions.getJSONObject(i);

						try {
							Date start = formatter.parse(timeObject.getString("startTime"));
							Date end = formatter.parse(timeObject.getString("endTime"));
							TimeSession timeSession = new TimeSession(start, end);
							timeSessionList.add(timeSession);
						} catch (Exception e) {
							e.printStackTrace();
							break;
						}
					}

					// income
					JSONArray incomeArray = projectObject.getJSONArray("income");
					List<IncomeTransaction> incomeList = new ArrayList<IncomeTransaction>();
					for (int i = 0; i < incomeArray.length(); i++) {
						JSONObject incomeObject = incomeArray.getJSONObject(i);

						try {
							Date transactionDate = formatter.parse(incomeObject.getString("date"));
							double amount = incomeObject.getDouble("amount");
							IncomeTransaction transaction = new IncomeTransaction(amount, transactionDate);
							incomeList.add(transaction);
						} catch (Exception e) {
							e.printStackTrace();
							break;
						}
					}

					Project project = new Project(projectName, timeSessionList, incomeList, type, date, status);
					projects.add(project);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("projects.json could not be found.");
		}
		
		// create Main Window
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	mainWindow = new MainWindow();
            	splashWindow.setVisible(false);
            	splashWindow.dispose();
            	splashWindow = null;
            }
        });

	}

	public static void save() {
		JSONObject json = new JSONObject();

		for (Project project : projects) {
			JSONObject projectObject = new JSONObject();

			projectObject.put("date-added", formatter.format(project.getDateAdded())); // date
			projectObject.put("status", ProjectStatus.getName(project.getProjectStatus())); // status
			projectObject.put("type", ProjectType.getName(project.getProjectType())); // type

			// time sessions
			JSONArray timeSessions = new JSONArray();
			for (TimeSession timeSession : project.getTimeSessions()) {
				JSONObject timeObject = new JSONObject();
				timeObject.put("startTime", formatter.format(timeSession.getStartTime()));
				timeObject.put("endTime", formatter.format(timeSession.getEndTime()));
				timeSessions.put(timeObject);
			}
			projectObject.put("time-sessions", timeSessions);

			// income
			JSONArray incomeArray = new JSONArray();
			for (IncomeTransaction income : project.getIncomeTransactions()) {
				JSONObject incomeObject = new JSONObject();
				incomeObject.put("date", formatter.format(income.getDate()));
				incomeObject.put("amount", income.getIncome());
				incomeArray.put(incomeObject);
			}
			projectObject.put("income", incomeArray);

			json.put(project.getName(), projectObject);
		}

		String result = json.toString(4);
		FileWriter file;
		try {
			file = new FileWriter(dataDirectory + File.separator + "projects.json");
			file.write(result);
			file.flush();
			file.close();
			System.out.println("Exiting...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String defaultDirectory()
	{
		String OS = System.getProperty("os.name").toUpperCase();

		if (OS.contains("WIN")) {
			return System.getenv("APPDATA") + File.separator + "ProjectManager";
		} else if (OS.contains("MAC")) {
			return System.getProperty("user.home") + File.separator + "Library" +
					File.separator + "Application Support" + File.separator + "ProjectManager";
		} else if (OS.contains("NUX")) {
			return System.getProperty("user.home") + File.separator + ".projectManager";
		}

		return System.getProperty("user.dir") + File.separator + "ProjectManager";
	}

}


