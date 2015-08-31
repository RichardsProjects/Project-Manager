package net.richardsprojects.projecttracker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import net.richardsprojects.projecttracker.actionlisteners.ProjectStatusButtons;

public class EditProjectWindow extends JFrame {

	private ProjectStatus status;
	public Project project;
	
	public EditProjectWindow(final Project project) {
		super("Edit - " + project.getName());
		status = project.getProjectStatus();
		this.project = project;
		
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new BorderLayout());
		
		JLabel header = new JLabel(" Edit - " + project.getName());
	    header.setFont(new Font(header.getFont().getName(), Font.PLAIN, 25));
	    header.setOpaque(true);
	    header.setBackground(Color.decode("#0066CC"));
	    header.setForeground(Color.white);
	    header.setPreferredSize(new Dimension(400, 25));
	    header.setMinimumSize(new Dimension(400, 25));
	    
	    jpanel.add(header, BorderLayout.PAGE_START);
	    
	    JPanel jpanel2 = new JPanel();
	    jpanel2.setLayout(new BoxLayout(jpanel2, BoxLayout.PAGE_AXIS));
	    
	    jpanel2.setBorder(new EmptyBorder(8, 8, 8, 8));
	    
	   	jpanel2.add(Box.createRigidArea(new Dimension(0, 10)));
	    
	    JButton button = new JButton("New Time Session");
	    JButton button2 = new JButton("New Income Transaction");
	    button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewTimeSessionWindow timeSession = new NewTimeSessionWindow(project, EditProjectWindow.this);
				dispatchEvent(new WindowEvent(EditProjectWindow.this, WindowEvent.WINDOW_CLOSING));
			} 	
	    });
	    button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewIncomeTransactionWindow incomeTransaction = new NewIncomeTransactionWindow(project);
				dispatchEvent(new WindowEvent(EditProjectWindow.this, WindowEvent.WINDOW_CLOSING));
			} 	
	    });
	    button.setMinimumSize(new Dimension(Integer.MAX_VALUE, 20));
	    button2.setMinimumSize(new Dimension(Integer.MAX_VALUE, 20));
	    jpanel2.add(button);
	    jpanel2.add(button2);
	    
	    jpanel2.add(Box.createRigidArea(new Dimension(0, 10)));
	    
	    // Create Project Status Radio Buttons
	    JRadioButton inProgress = new JRadioButton("In Progress");
	    inProgress.setSelected(false);
	    inProgress.setActionCommand("1");
	    
	    JRadioButton priority = new JRadioButton("Priority");
	    priority.setSelected(false);
	    priority.setActionCommand("2");

	    JRadioButton finished = new JRadioButton("Finished");
	    finished.setSelected(false);
	    finished.setActionCommand("3");
	    	    
	    if(status == ProjectStatus.IN_PROGRESS) inProgress.setSelected(true);
	    if(status == ProjectStatus.PRIORITY) priority.setSelected(true);
	    if(status == ProjectStatus.FINISHED) finished.setSelected(true);
	    
	    ButtonGroup group = new ButtonGroup();
	    group.add(inProgress);
	    group.add(priority);
	    group.add(finished);
	    
	    ProjectStatusButtons listener = new ProjectStatusButtons(this);
	    
	    inProgress.addActionListener(listener);
	    priority.addActionListener(listener);
	    finished.addActionListener(listener);
	    
	    jpanel2.add(inProgress);
	    jpanel2.add(priority);
	    jpanel2.add(finished);
	    
	    jpanel.add(jpanel2, BorderLayout.CENTER);
	    add(jpanel);
	    pack();
	    
	    //TODO: Make the width calculated properly
	    //TODO: Make the button centered
	    setSize(400, 220);
	    setVisible(true);
	    setLocationRelativeTo(null);
	    setResizable(false);
	}	
}

