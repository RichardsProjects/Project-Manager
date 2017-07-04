package net.richardsprojects.projecttracker.windows;

import net.richardsprojects.projecttracker.Main;
import net.richardsprojects.projecttracker.ThemeUtils;
import net.richardsprojects.projecttracker.data.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

class NewIncomeTransactionWindow extends JFrame {

	private final Project project;
	private JTextField textField;

	public NewIncomeTransactionWindow(Project project) {
		super("New Income Transaction");
		
		this.project = project;
		
		createGUI();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (textField.getText().length() > 0) {
					int result = showWarningMessage();
					if (result == JOptionPane.YES_OPTION) {
						if (save()) {
							dispose();
							Main.currentDialog = null;

							if (Main.closeRequested) {
								Main.mainWindow.dispatchEvent(new WindowEvent(Main.mainWindow, WindowEvent.WINDOW_CLOSING));
							}
						} else {
							Main.currentDialog = null;
							if (Main.closeRequested) Main.closeRequested = false;
						}
					} else if (result == JOptionPane.NO_OPTION) {
						dispose();
						Main.currentDialog = null;
						if (Main.closeRequested) {
							Main.mainWindow.dispatchEvent(new WindowEvent(Main.mainWindow, WindowEvent.WINDOW_CLOSING));
						}
					}
			} else {
				Main.currentDialog = null;
				dispose();
				if (Main.closeRequested) {
					Main.mainWindow.dispatchEvent(new WindowEvent(Main.mainWindow, WindowEvent.WINDOW_CLOSING));
				}
			}
		}
		});

		pack();
	    setSize(400, 250);
	    setVisible(true);
	    setLocationRelativeTo(null);
	    setResizable(false);
	}

	private int showWarningMessage() {
		String[] options = new String[] {"Yes", "No"};
		String defaultOption = options[0];

		return JOptionPane.showOptionDialog(this, "Do you want to save this income transaction?",
				"Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, defaultOption);
	}

	private void createGUI() {
		JPanel main = ThemeUtils.createBasicPanel(false);
		main.setLayout(new BorderLayout());
		main.add(ThemeUtils.createHeader("New Income Transaction", 400), BorderLayout.PAGE_START);
	    
	    JPanel content = ThemeUtils.createBasicPanel(true);
	    content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

	    JPanel incomePanel = ThemeUtils.createBasicPanel(false);
	    incomePanel.setLayout(new BoxLayout(incomePanel, BoxLayout.X_AXIS));
	    JLabel incomeLabel = ThemeUtils.createLabel("Income: $", ThemeUtils.getSmallBoldFont());
	    textField = new JTextField();
	    textField.setMaximumSize(new Dimension(200, 20));
	    incomePanel.add(incomeLabel);
	    incomePanel.add(textField);
	    content.add(incomePanel);

	    JButton saveButton = ThemeUtils.createGradientButton("Save");
	    saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save();
			}  	
	    });

	    content.add(saveButton);

	    main.add(content);
	    add(main);
	}

	private boolean save() {
		try {
			double money = Double.parseDouble(textField.getText());
			project.addIncomeTransactionData(money, new Date());
			Main.mainWindow.updatePanel();
			return true;
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "The value entered into the field must be a number.");
			return false;
		}
	}

}
