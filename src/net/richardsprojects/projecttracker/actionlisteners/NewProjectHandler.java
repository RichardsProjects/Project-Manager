package net.richardsprojects.projecttracker.actionlisteners;

import net.richardsprojects.projecttracker.windows.NewProjectWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewProjectHandler implements ActionListener {

	public void actionPerformed(ActionEvent arg0) {
			NewProjectWindow newProjectFrame = new NewProjectWindow();
	}
	
}