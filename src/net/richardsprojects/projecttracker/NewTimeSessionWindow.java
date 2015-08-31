package net.richardsprojects.projecttracker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;

import net.richardsprojects.projecttracker.actionlisteners.TimerUpdate;

public class NewTimeSessionWindow extends JFrame {

	private Project project;
	private EditProjectWindow editProjectWindow;
	private JButton startOrStopButton;
	
	private List<TimeSession> timeSessions = new ArrayList<TimeSession>();
	
	public int seconds = 0;
	public int minutes = 0;
	public int hours = 0;
	
	public Date startTime = null;
	public Date endTime = null;
	
	private static Timer timer;
	
	private boolean isRunning = false;
	
	private JPanel mainJPanel = new JPanel();
	
	public NewTimeSessionWindow(Project project, EditProjectWindow editProjectWindow) {
		super("New Time Session");
		
		this.project = project;
		this.editProjectWindow = editProjectWindow;
		
		createGUI();
		
		pack();
	    setSize(400, 250);
	    setVisible(true);
	    setLocationRelativeTo(null);
	    setResizable(false);
	}
	
	private void createGUI() {
		mainJPanel = new JPanel();
		mainJPanel.setLayout(new BorderLayout());
		
		JLabel header = new JLabel(" New Time Session");
	    header.setFont(new Font(header.getFont().getName(), Font.PLAIN, 25));
	    header.setOpaque(true);
	    header.setBackground(Color.decode("#0066CC"));
	    header.setForeground(Color.white);
	    header.setPreferredSize(new Dimension(400, 25));
	    header.setMinimumSize(new Dimension(400, 25));
	    
	    mainJPanel.add(header, BorderLayout.PAGE_START);
	    
	    JPanel jpanel2 = new JPanel();
	    
	    jpanel2.setLayout(new BoxLayout(jpanel2, BoxLayout.PAGE_AXIS));
	    jpanel2.setBorder(new EmptyBorder(8, 8, 8, 8));
	    jpanel2.add(Box.createRigidArea(new Dimension(0, 10)));
	    
	    String hoursStr = hours + "";
	    String minutesStr = minutes + "";
	    String secondsStr = seconds + "";
	    if(hoursStr.equals("0")) hoursStr = "00";
	    if(minutesStr.equals("0")) minutesStr = "00";
	    if(secondsStr.equals("0")) secondsStr = "00";
	    if(hoursStr.length() == 1) hoursStr = "0" + hoursStr;
	    if(minutesStr.length() == 1) minutesStr = "0" + minutesStr;
	    if(secondsStr.length() == 1) secondsStr = "0" + secondsStr;
	    
	    JLabel time = new JLabel("Time Working: " + hoursStr + ":" + minutesStr + ":" + secondsStr);
	    time.setMinimumSize(new Dimension(Integer.MAX_VALUE, 400));
	    time.setFont(new Font(time.getFont().getName(), Font.PLAIN, 20));
	    jpanel2.add(time);
	    
	    if(!this.isRunning) {
		    startOrStopButton = new JButton("Start");
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
		    startOrStopButton = new JButton("Stop");
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

	    jpanel2.add(startOrStopButton);
	    jpanel2.add(Box.createRigidArea(new Dimension(0, 10)));
	    
	    //Save Button
	    JButton saveButton = new JButton("Save");
	    saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isRunning) {
					JOptionPane.showMessageDialog(null, "You cannot save a time session while the timer is running.");
				} else {
					if(timeSessions.size() != 0) {
						for(TimeSession session : timeSessions) {
							Date sTime = session.getStartTime();
							Date eTime = session.getEndTime();
							project.addTimeSessionData(sTime, eTime);
						}						
						setVisible(false);
						editProjectWindow.setVisible(false);
						Main.mainWindow.updatePanel();
					} else {
						JOptionPane.showMessageDialog(null, "You have to start and stop the timer before you can save.");
					}
				}
			}	    		    	
	    });
	    jpanel2.add(saveButton);
	    
	    jpanel2.setMinimumSize(new Dimension(Integer.MAX_VALUE, 400));
	    
	    mainJPanel.add(jpanel2, BorderLayout.CENTER);
	    
	    //TODO: Make size based-off minimums
	    //TODO: Center elements
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
	
}
