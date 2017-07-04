package net.richardsprojects.projecttracker;

import javax.swing.*;
import java.awt.*;

/**
 * A custom button with a Gradient theme used in the Project Manager.
 *
 * @author RichardB122
 * @version 7/4/17
 */
class ThemedButton extends JButton {

    public ThemedButton(String text) {
        super(text);

        setContentAreaFilled(false);
        setBackground(Color.decode("#37474F"));
        setBorderPainted(false);
        setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g.create();

        g2.setPaint(new GradientPaint(
                new Point(0, 0),
                getBackground(),
                new Point(0, getHeight()/3),
                Color.decode("#455A64")));
        g2.fillRect(0, 0, getWidth(), getHeight()/3);
        g2.setPaint(new GradientPaint(
                new Point(0, getHeight()/3),
                Color.decode("#455A64"),
                new Point(0, getHeight()),
                getBackground()));
        g2.fillRect(0, getHeight()/3, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }
}