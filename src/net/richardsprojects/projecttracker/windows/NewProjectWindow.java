package net.richardsprojects.projecttracker.windows;

import net.richardsprojects.projecttracker.Main;
import net.richardsprojects.projecttracker.ThemeUtils;
import net.richardsprojects.projecttracker.data.Project;
import net.richardsprojects.projecttracker.data.ProjectType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewProjectWindow extends JFrame {
	
	public NewProjectWindow() {
	    super("New Project");
	    
	    JPanel main = ThemeUtils.createBasicPanel(false);
	    main.setLayout(new BorderLayout());
	    
	    main.add(ThemeUtils.createHeader("New Project", 400), BorderLayout.PAGE_START);
	    
	    JPanel content = ThemeUtils.createBasicPanel(true);
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

		// project name line
	    JPanel namePanel = ThemeUtils.createBasicPanel(false);
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		final JTextField nameField = new JTextField();
	    nameField.setPreferredSize(new Dimension(300, 25));
	    nameField.setSize(new Dimension(300, 25));
		namePanel.add(ThemeUtils.createLabel("Name:", ThemeUtils.getSmallBoldFont()));
		ThemeUtils.addHorizontalSpacer(namePanel);
		namePanel.add(nameField);
	    content.add(namePanel);

		ThemeUtils.addSeperatorToJPanel(content);

		// project type line
		JPanel typePanel = ThemeUtils.createBasicPanel(false);
		typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.X_AXIS));
	    final JComboBox typeField = new JComboBox(new String[] {"Freelance Job", "Personal Project", "Premium Plugin"});
	    typeField.setEditable(false);
		typeField.setPreferredSize(new Dimension(300, 25));
		typeField.setSize(new Dimension(300, 25));
		typePanel.add(ThemeUtils.createLabel("Type:", ThemeUtils.getSmallBoldFont()));
		ThemeUtils.addHorizontalSpacer(typePanel);
	    typePanel.add(typeField);
		content.add(typePanel);

	    ThemeUtils.addSeperatorToJPanel(content);

	    // "Create" button
		JPanel buttonPanel = ThemeUtils.createBasicPanel(false);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
	    JButton button = ThemeUtils.createGradientButton("Create");
	    button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!nameField.getText().equals("")) {
					boolean nameUsed = false;
					for (Project project : Main.projects) {
						if (project.getName().equals(nameField.getText())) {
							nameUsed = true;
							break;
						}
					}

					if (!nameUsed) {
						ProjectType type = null;
						if (typeField.getSelectedIndex() == 0) {
							type = ProjectType.FREELANCE_JOB;
						} else if (typeField.getSelectedIndex() == 1) {
							type = ProjectType.PERSONAL_PROJECT;
						} else if (typeField.getSelectedIndex() == 2) {
							type = ProjectType.PREMIUM_PLUGIN;
						}

						// add project to list
						Project project = new Project(nameField.getText(), type);
						Main.projects.add(project);
						Main.mainWindow.updatePanel();

						NewProjectWindow.super.dispose(); // close JFrame
					} else {
						JOptionPane.showMessageDialog(null,"There is already a project with that name.");
					}
				}
			}	    	
	    });
	    buttonPanel.add(button);
		content.add(buttonPanel);

	    main.add(content, BorderLayout.WEST);
	    add(main);
	    
	    pack();
	    setVisible(true);
	    setResizable(false);
	    setLocationRelativeTo(null);
	}
}
