package net.richardsprojects.projecttracker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.richardsprojects.projecttracker.actionlisteners.NewProjectHandler;

public class MainWindow extends JFrame {
	
	JPanel mainJPanel = new JPanel();
	
	public MainWindow() {
		//Create JFrame
		super("Project Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//Create Menu
		MenuBar menubar = new MenuBar();
		
		//Create Projects Menu
		Menu projectsMenu = new Menu("Projects");
		MenuItem newProject = new MenuItem("New Project");
		newProject.addActionListener(new NewProjectHandler());
		projectsMenu.add(newProject);
		
		Menu viewMenu = new Menu("View");
		MenuItem completedProjects = new MenuItem("Completed Projects");
		completedProjects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.currentScreen = Screens.COMPLETED;
				updatePanel();
			}			
		});
		MenuItem inProgressProjects = new MenuItem("In Progress Projects");
		inProgressProjects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.currentScreen = Screens.IN_PROGRESS;
				updatePanel();
			}			
		});
		viewMenu.add(completedProjects);
		viewMenu.add(inProgressProjects);
		
		//Add Projects Menu to Menubar
		menubar.add(projectsMenu);
		menubar.add(viewMenu);
		
		//Add Menubar to JFrame
		setMenuBar(menubar);
		
		createPanel();
    
		//Make JFrame visible
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
	    setVisible(true);    // FIRST visible = true
	}
	
	public void createPanel() {
		mainJPanel = new JPanel();
		String headerText = "";
		if(Main.currentScreen == Screens.IN_PROGRESS) headerText = "Projects - In Progress";
		if(Main.currentScreen == Screens.COMPLETED) headerText = "Projects - Completed";
		JLabel header = new JLabel(" " + headerText);
	    header.setFont(new Font(header.getFont().getName(), Font.PLAIN, 35));
	    header.setOpaque(true);
	    header.setBackground(Color.decode("#0066CC"));
	    header.setForeground(Color.white);
	    mainJPanel.setLayout(new BorderLayout());
	    mainJPanel.add(header, BorderLayout.PAGE_START);
		
		if(Main.projects.size() > 0) {
			JPanel projectsList = new JPanel();
			projectsList.setBorder(new EmptyBorder(8, 8, 8, 8));
			projectsList.setLayout(new BoxLayout(projectsList, BoxLayout.PAGE_AXIS));
			int totalProjects = 0;
			for(final Project p : Main.projects) {
				boolean shouldDisplay = false;
				if(Main.currentScreen == Screens.COMPLETED && p.getProjectStatus() == ProjectStatus.FINISHED) shouldDisplay = true;
				if(Main.currentScreen == Screens.IN_PROGRESS && (p.getProjectStatus() == ProjectStatus.IN_PROGRESS || p.getProjectStatus() == ProjectStatus.PRIORITY)) shouldDisplay = true;
				
				if(shouldDisplay) {
					totalProjects++;
					
					//Main Project Panel
					JPanel projectPanel = new JPanel();
					projectPanel.setLayout(new BorderLayout());
					
					//Title Part on Project Panel
					JPanel titlePanel = new JPanel();
					titlePanel.setLayout(new BorderLayout());				
									
					String title = p.getName() + " - " + ProjectType.getName(p.getProjectType());
					JLabel projectTitle = new JLabel(title);
					projectTitle.setFont(new Font(projectTitle.getFont().getName(), Font.PLAIN, 24));
					titlePanel.add(projectTitle, BorderLayout.LINE_START);
					
					JButton button = new JButton("Edit");
					
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
								EditProjectWindow editProject = new EditProjectWindow(p);
						}
					});
					
					button.setMaximumSize(new Dimension(60, 20));
					titlePanel.add(button, BorderLayout.LINE_END);
					
					//Extra Information Panel
					JPanel informationPanel = new JPanel();
					informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.PAGE_AXIS));
					
					JLabel blankLabel = new JLabel(" ");
					blankLabel.setFont(new Font(projectTitle.getFont().getName(), Font.PLAIN, 14));
					Date dateAdded = p.getDateAdded();
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
					String dateInfo = "Date Added: " + dateFormat.format(dateAdded);
					JLabel dateLabel = new JLabel(dateInfo);
					dateLabel.setFont(new Font(projectTitle.getFont().getName(), Font.PLAIN, 14));
					JLabel totalTime = new JLabel("Total Time: " + p.getTotalTime());
					totalTime.setFont(new Font(projectTitle.getFont().getName(), Font.BOLD, 14));
					JLabel totalIncome = new JLabel("Total Income: $" + p.getTotalIncome());
					totalIncome.setFont(new Font(projectTitle.getFont().getName(), Font.BOLD, 14));
					JLabel status = new JLabel("Status: " + ProjectStatus.getName(p.getProjectStatus()));
					status.setFont(new Font(projectTitle.getFont().getName(), Font.PLAIN, 14));
					JLabel monthlyTime = new JLabel("Monthly Time: " + p.getMonthlyTime());
					monthlyTime.setFont(new Font(projectTitle.getFont().getName(), Font.PLAIN, 14));
					JLabel monthlyIncome = new JLabel("Monthly Income: $" + p.getMonthlyIncome());
					monthlyIncome.setFont(new Font(projectTitle.getFont().getName(), Font.PLAIN, 14));
					
					informationPanel.add(dateLabel);
					informationPanel.add(status);
					informationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
					informationPanel.add(monthlyTime);
					informationPanel.add(monthlyIncome);
					informationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
					informationPanel.add(totalTime);
					informationPanel.add(totalIncome);
					
					//Setup panel
					projectPanel.add(titlePanel, BorderLayout.PAGE_START);
					projectPanel.add(informationPanel, BorderLayout.LINE_START);
					projectPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, projectPanel.getMinimumSize().height));
	
					projectsList.add(projectPanel);
					JSeparator separator = new JSeparator();
					separator.setForeground(Color.GRAY);
					separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 8));
					projectsList.add(separator);				
				}
			}
			JScrollPane projectsListPane = new JScrollPane(projectsList);
			projectsListPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			projectsListPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			mainJPanel.add(projectsListPane, BorderLayout.CENTER);
		}
		
		add(mainJPanel);
	}
	
	public void updatePanel() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
        		//Reset and re-create panel
        		remove(mainJPanel);
        		createPanel();
        		
        		// Update Panel
        		revalidate();
        		repaint();
            }
        });
	}

}
