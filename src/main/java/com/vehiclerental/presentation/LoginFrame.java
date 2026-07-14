package com.vehiclerental.presentation;

import com.vehiclerental.repository.ManagerRepository;
import com.vehiclerental.service.LoginService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JButton loginButton;
    private JButton exitButton;

    private JCheckBox showPassword;

    private final LoginService loginService =
            new LoginService(new ManagerRepository());

    public LoginFrame() {

        initializeFrame();

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        JPanel leftPanel = createLeftPanel();

        JPanel rightPanel = createRightPanel();

        mainPanel.add(leftPanel);

        mainPanel.add(rightPanel);

        add(mainPanel);

        initializeEvents();

        setVisible(true);

    }

    private void initializeFrame() {

        setTitle("SB Car Rental");

        setSize(900, 700);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private JPanel createLeftPanel() {

        JPanel panel = new JPanel();

        panel.setBackground(new Color(15, 76, 129));

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setBorder(new EmptyBorder(35, 30, 35, 30));

        panel.add(Box.createVerticalGlue());

        ImageIcon icon = new ImageIcon(
                getClass().getResource("/images/logo.png"));

        Image image = icon.getImage().getScaledInstance(
                280,
                280,
                Image.SCALE_SMOOTH);

        JLabel logo = new JLabel(new ImageIcon(image));

        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(logo);

        panel.add(Box.createVerticalStrut(25));

        JLabel title = new JLabel("SB CAR RENTAL");

        title.setFont(new Font("Segoe UI", Font.BOLD, 34));

        title.setForeground(Color.WHITE);

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);

        panel.add(Box.createVerticalStrut(15));

        JLabel subTitle = new JLabel("Vehicle Rental Management System");

        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        subTitle.setForeground(Color.WHITE);

        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(subTitle);

        panel.add(Box.createVerticalStrut(30));

        JLabel line1 = new JLabel("✔ Cars");

        line1.setForeground(Color.WHITE);

        line1.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        line1.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(line1);

        panel.add(Box.createVerticalStrut(10));

        JLabel line2 = new JLabel("✔ Trucks");

        line2.setForeground(Color.WHITE);

        line2.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        line2.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(line2);

        panel.add(Box.createVerticalStrut(10));

        JLabel line3 = new JLabel("✔ Motorcycles");

        line3.setForeground(Color.WHITE);

        line3.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        line3.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(line3);

        panel.add(Box.createVerticalStrut(10));

        JLabel line4 = new JLabel("✔ Electric Vehicles");

        line4.setForeground(Color.WHITE);

        line4.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        line4.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(line4);

        panel.add(Box.createVerticalGlue());

        return panel;

    }

    private JPanel createRightPanel() {

        JPanel panel = new JPanel();

        panel.setBackground(Color.WHITE);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setBorder(new EmptyBorder(50, 60, 50, 60));

        JLabel welcome = new JLabel("Welcome Back");

        welcome.setFont(new Font("Segoe UI", Font.BOLD, 34));

        welcome.setForeground(new Color(15, 76, 129));

        welcome.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(welcome);

        panel.add(Box.createVerticalStrut(8));

        JLabel subTitle = new JLabel("Please login to continue");

        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 17));

        subTitle.setForeground(Color.GRAY);

        subTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(subTitle);

        panel.add(Box.createVerticalStrut(40));

        JLabel usernameLabel = new JLabel("Username");

        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(usernameLabel);

        panel.add(Box.createVerticalStrut(8));

        usernameField = new JTextField();

        usernameField.setMaximumSize(new Dimension(340, 45));

        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        panel.add(usernameField);

        panel.add(Box.createVerticalStrut(20));

        JLabel passwordLabel = new JLabel("Password");

        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(passwordLabel);

        panel.add(Box.createVerticalStrut(8));

        passwordField = new JPasswordField();

        passwordField.setMaximumSize(new Dimension(340, 45));

        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        panel.add(passwordField);

        panel.add(Box.createVerticalStrut(10));

        showPassword = new JCheckBox("Show Password");

        showPassword.setBackground(Color.WHITE);

        showPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        showPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(showPassword);

        panel.add(Box.createVerticalStrut(30));

        loginButton = new JButton("LOGIN");

        loginButton.setMaximumSize(new Dimension(340, 48));

        loginButton.setBackground(new Color(25, 118, 210));

        loginButton.setForeground(Color.WHITE);

        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 18));

        loginButton.setFocusPainted(false);

        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panel.add(loginButton);

        panel.add(Box.createVerticalStrut(15));

        exitButton = new JButton("EXIT");

        exitButton.setMaximumSize(new Dimension(340, 45));

        exitButton.setBackground(new Color(220, 53, 69));

        exitButton.setForeground(Color.WHITE);

        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 17));

        exitButton.setFocusPainted(false);

        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panel.add(exitButton);

        panel.add(Box.createVerticalGlue());

        JLabel footer = new JLabel("© 2026 SB Car Rental");

        footer.setForeground(Color.GRAY);

        footer.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        footer.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(footer);

        return panel;

    }

    private void initializeEvents() {

        showPassword.addActionListener(e -> {

            if (showPassword.isSelected()) {

                passwordField.setEchoChar((char) 0);

            } else {

                passwordField.setEchoChar('•');

            }

        });

        loginButton.addActionListener(e -> login());

        exitButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(

                    this,

                    "Are you sure you want to exit?",

                    "Exit",

                    JOptionPane.YES_NO_OPTION,

                    JOptionPane.QUESTION_MESSAGE

            );

            if (choice == JOptionPane.YES_OPTION) {

                System.exit(0);

            }

        });

    }

    private void login() {

        String username = usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);

            return;

        }

        boolean success = loginService.login(username, password);

        if (success) {

            dispose();

            new DashboardFrame();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);

            passwordField.setText("");

            usernameField.requestFocus();

        }

    }
}