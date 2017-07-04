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
        panel = new JPanel();

        setPreferredSize(new Dimension(300, 120));
        setSize(new Dimension(300, 120));
        setUndecorated(true);

        JLabel backgroundLabel = new JLabel();
        if (bg != null) backgroundLabel.setIcon(new ImageIcon(bg));
        panel.add(backgroundLabel);
        setContentPane(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
