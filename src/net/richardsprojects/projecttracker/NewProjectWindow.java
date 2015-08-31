package net.richardsprojects.projecttracker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class NewProjectWindow extends JFrame {
	
	public NewProjectWindow() {
	    super("New Project");
	    
	    JPanel jpanel = new JPanel();
	    jpanel.setLayout(new BorderLayout());
	    
	    JLabel header = new JLabel(" New Project");
	    header.setFont(new Font(header.getFont().getName(), Font.PLAIN, 25));
	    header.setOpaque(true);
	    header.setBackground(Color.decode("#0066CC"));
	    header.setForeground(Color.white);
	    header.setPreferredSize(new Dimension(400, 25));
	    jpanel.add(header, BorderLayout.PAGE_START);
	    
	    JPanel jpanel2 = new JPanel();
	    jpanel2.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();

	    c.gridx = 0;
	    c.gridy = 0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    jpanel2.add(Box.createRigidArea(new Dimension(0, 10)), c);
	    
	    c.gridx = 0;
	    c.gridy = 1;
	    c.gridwidth = 3;
	    c.gridheight = 1;
	    JLabel nameLabel = new JLabel("Project Name: ");
	    nameLabel.setPreferredSize(new Dimension(350, 25));
	    jpanel2.add(nameLabel, c);
	    
	    c.gridy = 2;
	    c.gridwidth = 2;
	    final JTextField nameField = new JTextField();
	    nameField.setHorizontalAlignment(SwingConstants.LEFT);
	    nameField.setPreferredSize(new Dimension(350, 25));
	    nameField.setSize(new Dimension(350, 25));
	    jpanel2.add(nameField, c);
	    
	    c.gridy = 3;
	    c.gridwidth = 3;
	    JLabel typeLabel = new JLabel("Project Type: ", null, JLabel.LEFT);
	    typeLabel.setPreferredSize(new Dimension(350, 25));
	    jpanel2.add(typeLabel, c);
	    
	    c.gridy = 4;
	    c.gridwidth = 1;
	    String[] options = {"Freelance Job", "Personal Project", "Premium Plugin"};
	    final JComboBox typeField = new JComboBox(options);
	    typeField.setEditable(false);
	    jpanel2.add(typeField, c);
	    
	    c.gridy = 5;
	    c.gridwidth = 1;
	    jpanel2.add(Box.createRigidArea(new Dimension(0, 10)), c);
	    
	    c.gridy = 6;
	    c.gridwidth = 1;
	    c.anchor = GridBagConstraints.LINE_START;
	    JButton button = new JButton("Create");
	    button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO: Check if a project with that name already exists
				if(!nameField.getText().equals("")) {
					//Create Project
					ProjectType type = null;
					if(typeField.getSelectedIndex() == 0) {
						type = ProjectType.FREELANCE_JOB;
					} else if(typeField.getSelectedIndex() == 1) {
						type = ProjectType.PERSONAL_PROJECT;
					} else if(typeField.getSelectedIndex() == 2) {
						type = ProjectType.PREMIUM_PLUGIN;
					}
					Project project = new Project(nameField.getText(), type);
					
					//Add Project to list
					Main.projects.add(project);
					Main.mainWindow.updatePanel();
					
					//Close JFrame
					NewProjectWindow.super.dispose();
				}
			}	    	
	    });
	    jpanel2.add(button, c);
	    
	    c.gridy = 7;
	    c.gridwidth = 1;
	    jpanel2.add(Box.createRigidArea(new Dimension(0, 10)), c);
	    
	    jpanel.add(jpanel2, BorderLayout.CENTER);
	    
	    add(jpanel);
	    
	    pack();
	    
	    setVisible(true);
	    setResizable(false);
	    setLocationRelativeTo(null);
	}
}
