package net.richardsprojects.projecttracker.windows;

import net.richardsprojects.projecttracker.Main;
import net.richardsprojects.projecttracker.ThemeUtils;
import net.richardsprojects.projecttracker.actionlisteners.TimerUpdate;
import net.richardsprojects.projecttracker.data.Project;
import net.richardsprojects.projecttracker.data.TimeSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewTimeSessionWindow extends JFrame {

	private final Project project;

	private final List<TimeSession> timeSessions = new ArrayList<TimeSession>();
	
	public int seconds = 0;
	public int minutes = 0;
	public int hours = 0;
	
	private Date startTime = null;
	private Date endTime = null;
	
	private static Timer timer;
	
	private boolean isRunning = false;
	
	private JPanel mainJPanel = new JPanel();
	
	public NewTimeSessionWindow(Project project, EditProjectWindow editProjectWindow) {
		super("New Time Session");
		
		this.project = project;
		EditProjectWindow editProjectWindow1 = editProjectWindow;
		
		createGUI();

		Main.mainWindow.setFocusable(false);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				handleClosing();
			}
		});

		pack();
	    setSize(400, 250);
	    setVisible(true);
	    setLocationRelativeTo(null);
	    setResizable(false);
	}

	private void handleClosing() {
		if (timeSessions.size() > 0) {
			int result = showWarningMessage();

			if (result == JOptionPane.YES_OPTION) {
				if (save()) {
					Main.currentDialog = null;
					dispose();
					if (Main.closeRequested) {
						Main.mainWindow.dispatchEvent(new WindowEvent(Main.mainWindow, WindowEvent.WINDOW_CLOSING));
					}
				}
			} else if (result == JOptionPane.NO_OPTION) {
				Main.currentDialog = null;
				dispose();
				if (Main.closeRequested) {
					Main.mainWindow.dispatchEvent(new WindowEvent(Main.mainWindow, WindowEvent.WINDOW_CLOSING));
				}
			} else if (result == JOptionPane.CANCEL_OPTION) {
				if (Main.closeRequested) Main.closeRequested = false;
			}
		} else {
			if (isRunning) {
				JOptionPane.showMessageDialog(null, "Please stop your current time session first.");
			} else {
				Main.currentDialog = null;
				dispose();
				if (Main.closeRequested) {
					Main.mainWindow.dispatchEvent(new WindowEvent(Main.mainWindow, WindowEvent.WINDOW_CLOSING));
				}
			}
		}
	}

	private int showWarningMessage() {
		String[] options = new String[] {"Yes", "No", "Cancel"};
		String defaultOption = options[0];

		return JOptionPane.showOptionDialog(this, "Do you want to save the current time session?",
				"Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, defaultOption);
	}

	private void createGUI() {
		mainJPanel = ThemeUtils.createBasicPanel(false);
		mainJPanel.setLayout(new BorderLayout());

	    mainJPanel.add(ThemeUtils.createHeader("New Time Session", 400), BorderLayout.PAGE_START);
	    
	    JPanel jpanel = ThemeUtils.createBasicPanel(true);
	    jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.PAGE_AXIS));

	    jpanel.add(ThemeUtils.createLabel(getTimeWorking(), ThemeUtils.getMediumFont()));
	    ThemeUtils.addSeperatorToJPanel(jpanel);

		JButton startOrStopButton;
		if(!this.isRunning) {
		    startOrStopButton = ThemeUtils.createButton("Start");
			startOrStopButton.setForeground(Color.BLACK);
		    startOrStopButton.setBackground(Color.GREEN);
		    startOrStopButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					NewTimeSessionWindow.timer = new Timer(1000, new TimerUpdate(NewTimeSessionWindow.this));
					NewTimeSessionWindow.timer.start();
					if(startTime == null) startTime = new Date();					
					isRunning = true;
					updateGUI();
				}		    	
		    });
	    } else {
		    startOrStopButton = ThemeUtils.createButton("Stop");
		    startOrStopButton.setForeground(Color.BLACK);
		    startOrStopButton.setBackground(Color.RED);
		    startOrStopButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					NewTimeSessionWindow.timer.stop();
					endTime = new Date();
					TimeSession session = new TimeSession(startTime, endTime);
					startTime = null;
					endTime = null;
					timeSessions.add(session);
					isRunning = false;
					updateGUI();
				}		    	
		    });
	    }

	    jpanel.add(startOrStopButton);
		ThemeUtils.addSeperatorToJPanel(jpanel);
	    
	    // save Button
	    JButton saveButton = ThemeUtils.createGradientButton("Save");
	    saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save();
			}	    		    	
	    });
	    jpanel.add(saveButton);
	    
	    jpanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 400));
	    
	    mainJPanel.add(jpanel, BorderLayout.CENTER);
	    
	    //TODO: Add changelog section
	    
	    add(mainJPanel);
	}
	
	public void updateGUI() {
		remove(mainJPanel);
		createGUI();
		
		//Repaint
		revalidate();
		repaint();
	}

	private String getTimeWorking() {
		String hoursStr = hours + "";
		String minutesStr = minutes + "";
		String secondsStr = seconds + "";
		if (hoursStr.equals("0")) hoursStr = "00";
		if (minutesStr.equals("0")) minutesStr = "00";
		if (secondsStr.equals("0")) secondsStr = "00";
		if (hoursStr.length() == 1) hoursStr = "0" + hoursStr;
		if (minutesStr.length() == 1) minutesStr = "0" + minutesStr;
		if (secondsStr.length() == 1) secondsStr = "0" + secondsStr;
		return "Time Working: " + hoursStr + ":" + minutesStr + ":" + secondsStr;
	}

	private boolean save() {
		if (isRunning) {
			JOptionPane.showMessageDialog(null, "You cannot save a time session while the timer is running.");
			return false;
		} else {
			if(timeSessions.size() != 0) {
				for(TimeSession session : timeSessions) {
					Date sTime = session.getStartTime();
					Date eTime = session.getEndTime();
					project.addTimeSessionData(sTime, eTime);
				}
				Main.currentDialog = null;
				Main.mainWindow.updatePanel();
				dispose();
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "You have to start and stop the timer before you can save.");
				return true;
			}
		}
	}
}
