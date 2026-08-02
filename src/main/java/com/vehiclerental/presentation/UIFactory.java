package com.vehiclerental.presentation;

import javax.swing.*;
import java.awt.*;

public class UIFactory {

    // --- الثوابت العامة للتنسيق والألوان ---
    public static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    public static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    public static final Color BACKGROUND_COLOR = new Color(245, 247, 250);
    public static final Color TEXT_COLOR = new Color(44, 62, 80);
    public static final Color WHITE = Color.WHITE;

    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);

    // --- إنشاء الأزرار التكرارية ---
    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(FONT_BUTTON);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(FONT_BUTTON);
        button.setBackground(SECONDARY_COLOR);
        button.setForeground(WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // --- إنشاء النصوص والتسميات ---
    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_REGULAR);
        label.setForeground(TEXT_COLOR);
        return label;
    }

    public static JLabel createHeaderTitle(String text) {
        JLabel title = new JLabel(text, SwingConstants.CENTER);
        title.setFont(FONT_TITLE);
        title.setForeground(WHITE);
        return title;
    }

    // --- إنشاء حقول المدخلات ---
    public static JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(FONT_REGULAR);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return textField;
    }

    // --- حاوية نموذجية (Form Panel) ---
    public static JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 230)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        return panel;
    }
}