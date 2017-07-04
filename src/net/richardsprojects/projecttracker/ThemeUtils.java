package net.richardsprojects.projecttracker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * A collection of static methods for creating pieces of the UI that are
 * commonly used throughout the code such as buttons, labels and panels.
 *
 * @author RichardB122
 * @version 7/1/17
 */
public class ThemeUtils {

    private static Font getHeaderFont() {
        return new Font(new JLabel().getFont().getName(), Font.BOLD, 25);
    }

    public static Font getMediumFont() {
        return new Font(new JLabel().getFont().getName(), Font.PLAIN, 20);
    }

    public static Font getSmallFont() {
        return new Font(new JLabel().getFont().getName(), Font.PLAIN, 14);
    }

    public static Font getSmallBoldFont() {
        return new Font(new JLabel().getFont().getName(), Font.BOLD, 14);
    }

    public static JLabel createHeader(String text, int width) {
        JLabel header = new JLabel(text);

        header.setFont(getHeaderFont());
        header.setOpaque(true);
        header.setBackground(Color.decode("#263238"));
        header.setForeground(Color.white);
        header.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(5,8,5,0)));

        Dimension dimension = header.getPreferredSize();
        dimension.height = 35;
        if (width > -1) dimension.width = width;
        header.setPreferredSize(dimension);
        header.setMinimumSize(dimension);

        return header;
    }

    public static JLabel createHeader(String text) {
        return createHeader(text, -1);
    }

    public static JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.decode("#d9d9d9"));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setAlignmentX( Component.LEFT_ALIGNMENT );

        return label;
    }

    public static JPanel createBasicPanel(boolean padding) {
        JPanel jpanel = new JPanel();
        jpanel.setBackground(Color.decode("#546E7A"));

        if (padding) {
            jpanel.add(Box.createRigidArea(new Dimension(0, 10)));
            jpanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        }

        return jpanel;
    }

    public static JButton createGradientButton(String text) {
        JButton button = new ThemedButton(text);
        button.setForeground(Color.decode("#d9d9d9"));
        button.setFont(new Font(new JLabel().getFont().getName(), Font.BOLD, 16));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setAlignmentX( Component.LEFT_ALIGNMENT );

        return button;
    }

    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.decode("#d9d9d9"));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font(new JLabel().getFont().getName(), Font.BOLD, 16));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setAlignmentX( Component.LEFT_ALIGNMENT );

        return button;
    }

    public static JRadioButton createRadioButton(String text, Font font) {
        JRadioButton jRadioButton = new JRadioButton(text);
        jRadioButton.setBackground(Color.decode("#546E7A"));
        jRadioButton.setForeground(Color.decode("#d9d9d9"));
        jRadioButton.setSelected(false);
        jRadioButton.setFont(font);

        return jRadioButton;
    }

    public static void addSeperatorToJPanel(JPanel panel) {
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    public static void addHorizontalSpacer(JPanel panel) {
        panel.add(Box.createRigidArea(new Dimension(8, 0)));
    }

    // TODO: Add a method for creating themed JTextFields
}
