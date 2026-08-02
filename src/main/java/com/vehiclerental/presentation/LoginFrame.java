package com.vehiclerental.presentation;

import com.vehiclerental.repository.ManagerRepository;
import com.vehiclerental.service.LoginService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

/**
 * The LoginFrame class provides the user interface
 * for manager authentication and system login.
 */
public class LoginFrame extends JFrame {

    // Constants
    private static final String FONT_NAME = "Segoe UI";
    private static final String TITLE_APP = "SB CAR RENTAL";

    private static final Color NAVY = new Color(18, 54, 82);
    private static final Color PAGE_BG = new Color(226, 239, 248);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_DARK = new Color(33, 37, 41);
    private static final Color TEXT_GRAY = new Color(108, 117, 125);
    private static final Color BLUE = new Color(65, 130, 190);
    private static final Color RED = new Color(214, 80, 80);

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckBox;
    private JLabel messageLabel;
    private JButton loginButton;
    private JButton exitButton;

    private final transient LoginService loginService =
            new LoginService(new ManagerRepository());

    public LoginFrame() {
        initializeFrame();

        JPanel mainPanel = new GradientBackgroundPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.add(createLoginCard());

        add(mainPanel);

        initializeEvents();

        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("SB Car Rental - Login");
        setSize(1000, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JPanel createLoginCard() {
        RoundedPanel card = new RoundedPanel(32, CARD_BG);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(560, 620));
        card.setBorder(new EmptyBorder(24, 55, 32, 55));

        card.add(createLogoPanel());
        card.add(Box.createVerticalStrut(8));
        card.add(createTitlePanel());
        card.add(Box.createVerticalStrut(16));
        card.add(createFormPanel());
        card.add(Box.createVerticalStrut(18));
        card.add(createButtonsPanel());
        card.add(Box.createVerticalStrut(16));
        card.add(createFooterLabel());

        return card;
    }

    private JPanel createLogoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(430, 120));
        panel.setPreferredSize(new Dimension(430, 120));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel logoLabel = new JLabel(loadLogoIcon(115, 115));
        panel.add(logoLabel);

        return panel;
    }

    private JPanel createTitlePanel() {
        JPanel panel = createYBoxPanel(430, 115);

        JLabel title = createStandardLabel(TITLE_APP, Font.BOLD, 30, NAVY, Component.CENTER_ALIGNMENT);
        JLabel subtitle = createStandardLabel("Vehicle Rental Management System", Font.PLAIN, 15, TEXT_GRAY, Component.CENTER_ALIGNMENT);
        JLabel welcome = createStandardLabel("Welcome Back", Font.BOLD, 22, TEXT_DARK, Component.CENTER_ALIGNMENT);
        JLabel loginText = createStandardLabel("Login to continue to your dashboard", Font.PLAIN, 13, TEXT_GRAY, Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(12));
        panel.add(welcome);
        panel.add(Box.createVerticalStrut(4));
        panel.add(loginText);

        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = createYBoxPanel(430, 210);

        JLabel usernameLabel = createInputLabel("Username");
        usernameField = createTextField();

        JLabel passwordLabel = createInputLabel("Password");
        passwordField = createPasswordField();

        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setOpaque(false);
        showPasswordCheckBox.setFont(new Font(FONT_NAME, Font.PLAIN, 13));
        showPasswordCheckBox.setForeground(TEXT_DARK);
        showPasswordCheckBox.setFocusPainted(false);
        showPasswordCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        messageLabel = createStandardLabel(" ", Font.BOLD, 13, RED, Component.LEFT_ALIGNMENT);

        panel.add(usernameLabel);
        panel.add(Box.createVerticalStrut(6));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(12));
        panel.add(passwordLabel);
        panel.add(Box.createVerticalStrut(6));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(8));
        panel.add(showPasswordCheckBox);
        panel.add(Box.createVerticalStrut(6));
        panel.add(messageLabel);

        return panel;
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(430, 50));
        panel.setPreferredSize(new Dimension(430, 50));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton = createButton("Login", BLUE);
        exitButton = createButton("Exit", RED);

        panel.add(loginButton);
        panel.add(exitButton);

        return panel;
    }

    private JLabel createFooterLabel() {
        return createStandardLabel("© 2026 SB Car Rental", Font.PLAIN, 12, TEXT_GRAY, Component.CENTER_ALIGNMENT);
    }

    // --- Helper UI Methods ---

    private JPanel createYBoxPanel(int width, int height) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setMaximumSize(new Dimension(width, height));
        panel.setPreferredSize(new Dimension(width, height));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return panel;
    }

    private JLabel createStandardLabel(String text, int fontStyle, int fontSize, Color color, float alignmentX) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(FONT_NAME, fontStyle, fontSize));
        label.setForeground(color);
        label.setAlignmentX(alignmentX);
        return label;
    }

    private JLabel createInputLabel(String text) {
        return createStandardLabel(text, Font.BOLD, 14, TEXT_DARK, Component.LEFT_ALIGNMENT);
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        setupInputField(field);
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        setupInputField(field);
        field.setEchoChar('•');
        return field;
    }

    private void setupInputField(JTextField field) {
        field.setPreferredSize(new Dimension(360, 40));
        field.setMaximumSize(new Dimension(360, 40));
        field.setFont(new Font(FONT_NAME, Font.PLAIN, 15));
        field.setForeground(TEXT_DARK);
        field.setCaretColor(BLUE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 210, 225), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(170, 46));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font(FONT_NAME, Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private ImageIcon loadLogoIcon(int width, int height) {
        URL logoUrl = getClass().getResource("/images/logo.png");

        if (logoUrl == null) {
            return new ImageIcon();
        }

        ImageIcon icon = new ImageIcon(logoUrl);
        Image image = icon.getImage().getScaledInstance(
                width,
                height,
                Image.SCALE_SMOOTH
        );

        return new ImageIcon(image);
    }

    // --- Logic & Events ---

    private void initializeEvents() {
        loginButton.addActionListener(e -> login());
        exitButton.addActionListener(e -> System.exit(0));

        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        });
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter username and password.");
            return;
        }

        boolean success = loginService.login(username, password);

        if (success) {
            dispose();
            new DashboardFrame();
        } else {
            showError("Invalid username or password.");
        }
    }

    private void showError(String message) {
        messageLabel.setForeground(RED);
        messageLabel.setText(message);
    }

    // --- Custom UI Components ---

    private static class GradientBackgroundPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;

            GradientPaint gradient = new GradientPaint(
                    0, 0, PAGE_BG,
                    getWidth(), getHeight(), new Color(244, 248, 252)
            );

            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(18, 54, 82, 20));
            g2.fillOval(-150, -150, 410, 410);

            g2.setColor(new Color(65, 130, 190, 18));
            g2.fillOval(getWidth() - 240, getHeight() - 240, 390, 390);
        }
    }

    private static class RoundedPanel extends JPanel {

        private final int radius;
        private final Color backgroundColor;

        public RoundedPanel(int radius, Color backgroundColor) {
            this.radius = radius;
            this.backgroundColor = backgroundColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(new Color(0, 0, 0, 18));
            g2.fillRoundRect(8, 8, getWidth() - 16, getHeight() - 16, radius, radius);

            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth() - 16, getHeight() - 16, radius, radius);

            g2.dispose();

            super.paintComponent(g);
        }
    }
}