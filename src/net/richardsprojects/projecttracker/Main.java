package net.richardsprojects.projecttracker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

	public static MainWindow mainWindow;
	public static List<Project> projects;
	
	public static File dataDirectory;
	
	public static Screens currentScreen = Screens.IN_PROGRESS;
	
	public static void main(String[] args) {
		//Setup
		projects = new ArrayList<Project>();
		
		dataDirectory = new File(System.getenv("APPDATA") + File.separator + "ProjectTracker");
		
		//Create Data Directory if it doesn't already exist
		if(!dataDirectory.exists()) dataDirectory.mkdirs(); 
		
		//Load all projects
		String[] projectNames = dataDirectory.list();
		for(String projectName : projectNames)
		{
		    if (new File(dataDirectory.getAbsolutePath() + File.separator + projectName).isDirectory())
		    {		    	
		    	Project project = new Project(projectName);
		    	projects.add(project);
		    }
		}
		
		//Create Main Window
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                mainWindow = new MainWindow();
            }
        });

	}
	

}


