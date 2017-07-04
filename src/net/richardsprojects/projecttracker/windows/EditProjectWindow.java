package net.richardsprojects.projecttracker.windows;

import net.richardsprojects.projecttracker.Main;
import net.richardsprojects.projecttracker.ThemeUtils;
import net.richardsprojects.projecttracker.actionlisteners.ProjectStatusButtons;
import net.richardsprojects.projecttracker.data.Project;
import net.richardsprojects.projecttracker.data.ProjectStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class EditProjectWindow extends JFrame {

	private final Project project;

	public EditProjectWindow(final Project project) {
		// TODO: Prevent people from continuing to open edit windows

		super("Edit - " + project.getName());
		ProjectStatus status = project.getProjectStatus();
		this.project = project;

		JPanel main = ThemeUtils.createBasicPanel(false);
		main.setLayout(new BorderLayout());
		main.add(ThemeUtils.createHeader(" Edit - " + project.getName(), 400), BorderLayout.PAGE_START);
	    
	    JPanel content = ThemeUtils.createBasicPanel(true);
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

		// setup buttons
	    JButton newTimeButton = ThemeUtils.createGradientButton("New Time Session");
	    JButton newIncomeButton = ThemeUtils.createGradientButton("New Income Transaction");
	    newTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.currentDialog = new NewTimeSessionWindow(project, EditProjectWindow.this);
				dispatchEvent(new WindowEvent(EditProjectWindow.this, WindowEvent.WINDOW_CLOSING));
			} 	
	    });
		newIncomeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.currentDialog = new NewIncomeTransactionWindow(project);
				dispatchEvent(new WindowEvent(EditProjectWindow.this, WindowEvent.WINDOW_CLOSING));
			} 	
	    });
	    newIncomeButton.setMinimumSize(new Dimension(Integer.MAX_VALUE, 20));
	    newTimeButton.setMinimumSize(new Dimension(Integer.MAX_VALUE, 20));

	    content.add(newTimeButton);
	    ThemeUtils.addSeperatorToJPanel(content);
	    content.add(newIncomeButton);
	    ThemeUtils.addSeperatorToJPanel(content);
	    
	    // create project Status radio buttons
	    JRadioButton inProgress = ThemeUtils.createRadioButton("In Progress", ThemeUtils.getSmallFont());
	    inProgress.setActionCommand("1");
	    JRadioButton priority = ThemeUtils.createRadioButton("Priority", ThemeUtils.getSmallFont());
	    priority.setActionCommand("2");
	    JRadioButton finished = ThemeUtils.createRadioButton("Finished", ThemeUtils.getSmallFont());
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
	    
	    content.add(inProgress);
	    content.add(priority);
	    content.add(finished);
	    
	    main.add(content, BorderLayout.CENTER);
	    add(main);

	    pack();
	    setSize(400, 220);
	    setVisible(true);
	    setLocationRelativeTo(null);
	    setResizable(false);
	}

	public Project getProject() {
		return project;
	}
}

