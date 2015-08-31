package net.richardsprojects.projecttracker.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.richardsprojects.projecttracker.EditProjectWindow;
import net.richardsprojects.projecttracker.Main;
import net.richardsprojects.projecttracker.ProjectStatus;

public class ProjectStatusButtons implements ActionListener {

	private EditProjectWindow window;
	
	public ProjectStatusButtons(EditProjectWindow window) {
		this.window = window;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("1")) {
			window.project.setProjectStatus(ProjectStatus.IN_PROGRESS);
			Main.mainWindow.updatePanel();
		} else if(e.getActionCommand().equals("2")) {
			window.project.setProjectStatus(ProjectStatus.PRIORITY);
			Main.mainWindow.updatePanel();
		} else if(e.getActionCommand().equals("3")) {
			window.project.setProjectStatus(ProjectStatus.FINISHED);
			Main.mainWindow.updatePanel();
		}
	}

}
