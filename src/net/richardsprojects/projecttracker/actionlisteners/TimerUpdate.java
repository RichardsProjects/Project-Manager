package net.richardsprojects.projecttracker.actionlisteners;

import net.richardsprojects.projecttracker.windows.NewTimeSessionWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerUpdate implements ActionListener {

	private final NewTimeSessionWindow window;
	
	public TimerUpdate(NewTimeSessionWindow window) {
		this.window = window;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		window.seconds++;
		if(window.seconds > 59) {
			window.minutes++;
			window.seconds = 0;
			if(window.minutes > 59) {
				window.hours++;
				window.minutes = 0;
			}
		}
		window.updateGUI();
	}

}
