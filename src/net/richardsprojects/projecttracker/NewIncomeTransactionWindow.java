package net.richardsprojects.projecttracker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class NewIncomeTransactionWindow extends JFrame {

	private Project project;
	private JPanel mainJPanel = new JPanel();
	
	public NewIncomeTransactionWindow(Project project) {
		super("New Income Transaction");
		
		this.project = project;
		
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
		
		JLabel header = new JLabel(" New Income Transaction");
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
	    
	    JLabel incomeLabel = new JLabel("Income:");
	    final JTextField textField = new JTextField();
	    textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
	    JButton saveButton = new JButton("Save");
	    saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					double money = Double.parseDouble(textField.getText());
					project.addIncomeTransactionData(money, new Date());
					Main.mainWindow.updatePanel();
					dispatchEvent(new WindowEvent(NewIncomeTransactionWindow.this, WindowEvent.WINDOW_CLOSING));
				} catch(NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "The value entered into the field must be a number.");
				}
			}  	
	    });
	    
	    jpanel2.add(incomeLabel);
	    jpanel2.add(textField);
	    jpanel2.add(saveButton);
	    
	    mainJPanel.add(jpanel2);
	    add(mainJPanel);	    
	}	
}
