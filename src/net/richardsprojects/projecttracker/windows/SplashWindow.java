package net.richardsprojects.projecttracker.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A splash window that displays while data is loading.
 *
 * @author RichardB122
 * @version 1.0.0
 */
public class SplashWindow extends JFrame {

    private final JPanel panel;

    public SplashWindow(BufferedImage bg) {
        super("Splash");
        setUndecorated(true);

        panel = new JPanel();
        panel.setLayout(new BorderLayout(2,2));
        panel.setBackground(Color.decode("#3238b8"));

        JLabel text = new JLabel("Developed by Richard Bimmer");
        text.setForeground(Color.WHITE);
        text.setBorder(BorderFactory.createEmptyBorder(5, 5,5 ,5));
        panel.add(text, BorderLayout.PAGE_START);

        JLabel text2 = new JLabel("(C) 2015 - 2018");
        text2.setForeground(Color.WHITE);
        text2.setBorder(BorderFactory.createEmptyBorder(5, 5,5 ,5));
        panel.add(text2, BorderLayout.PAGE_END);

        JLabel backgroundLabel = new JLabel();
        if (bg != null) backgroundLabel.setIcon(new ImageIcon(bg));
        backgroundLabel.setPreferredSize(new Dimension(300, 85));
        backgroundLabel.setSize(new Dimension(300, 85));
        panel.add(backgroundLabel, BorderLayout.LINE_START);
        setContentPane(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
