package net.richardsprojects.projecttracker.actionlisteners;

import net.richardsprojects.projecttracker.Main;
import net.richardsprojects.projecttracker.data.ProjectStatus;
import net.richardsprojects.projecttracker.windows.EditProjectWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectStatusButtons implements ActionListener {

	private final EditProjectWindow window;
	
	public ProjectStatusButtons(EditProjectWindow window) {
		this.window = window;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("1")) {
			window.getProject().setProjectStatus(ProjectStatus.IN_PROGRESS);
			Main.mainWindow.updatePanel();
		} else if (e.getActionCommand().equals("2")) {
			window.getProject().setProjectStatus(ProjectStatus.PRIORITY);
			Main.mainWindow.updatePanel();
		} else if (e.getActionCommand().equals("3")) {
			window.getProject().setProjectStatus(ProjectStatus.FINISHED);
			Main.mainWindow.updatePanel();
		}
	}

}
