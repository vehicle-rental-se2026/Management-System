package com.vehiclerental.presentation;

import com.vehiclerental.billing.BillingService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * The BillingFrame class provides the user interface
 * for calculating rental costs, late penalties,
 * and total payment.
 */
public class BillingFrame extends JFrame {

    private static final Color NAVY = new Color(18, 54, 82);
    private static final Color NAVY_LIGHT = new Color(35, 92, 132);
    private static final Color PAGE_BG = new Color(244, 248, 252);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_DARK = new Color(33, 37, 41);
    private static final Color TEXT_GRAY = new Color(108, 117, 125);
    private static final Color BLUE = new Color(65, 130, 190);
    private static final Color GREEN = new Color(78, 150, 120);
    private static final Color ORANGE = new Color(210, 145, 80);
    private static final Color RED = new Color(214, 80, 80);

    private JTextField rentalDaysField;
    private JTextField lateDaysField;

    private JLabel rentalCostValueLabel;
    private JLabel penaltyValueLabel;
    private JLabel totalValueLabel;
    private JLabel messageLabel;

    private JButton calculateButton;
    private JButton clearButton;
    private JButton backButton;

    private final BillingService billingService =
            new BillingService();

    public BillingFrame() {
        initializeFrame();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PAGE_BG);

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createCenter(), BorderLayout.CENTER);
        mainPanel.add(createFooter(), BorderLayout.SOUTH);

        add(mainPanel);

        initializeEvents();

        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Billing");
        setSize(980, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JPanel createHeader() {
        GradientPanel header = new GradientPanel(NAVY, NAVY_LIGHT);
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(24, 36, 24, 36));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Billing");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Calculate rental cost, late penalty, and total amount");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(new Color(220, 235, 245));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.WEST);

        backButton = createButton("Back", RED, 110, 42);
        header.add(backButton, BorderLayout.EAST);

        return header;
    }

    private JPanel createCenter() {
        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(PAGE_BG);
        background.setBorder(new EmptyBorder(32, 42, 32, 42));

        RoundedPanel mainCard = new RoundedPanel(28, CARD_BG);
        mainCard.setLayout(new BorderLayout());
        mainCard.setBorder(new EmptyBorder(30, 30, 30, 30));
        mainCard.setPreferredSize(new Dimension(820, 410));

        JPanel content = new JPanel(new GridLayout(1, 2, 28, 0));
        content.setOpaque(false);

        content.add(createInputPanel());
        content.add(createResultPanel());

        mainCard.add(content, BorderLayout.CENTER);
        background.add(mainCard);

        return background;
    }

    private JPanel createInputPanel() {
        RoundedPanel panel = new RoundedPanel(24, new Color(236, 245, 252));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 28, 30, 28));

        JLabel title = new JLabel("Billing Details");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(NAVY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Enter rental days and late days.");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_GRAY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(8));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(28));

        JLabel rentalDaysLabel = createInputLabel("Rental Days");
        panel.add(rentalDaysLabel);
        panel.add(Box.createVerticalStrut(8));

        rentalDaysField = createTextField();
        panel.add(rentalDaysField);

        panel.add(Box.createVerticalStrut(20));

        JLabel lateDaysLabel = createInputLabel("Late Days");
        panel.add(lateDaysLabel);
        panel.add(Box.createVerticalStrut(8));

        lateDaysField = createTextField();
        panel.add(lateDaysField);

        panel.add(Box.createVerticalStrut(18));

        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        messageLabel.setForeground(RED);
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(messageLabel);

        panel.add(Box.createVerticalStrut(16));

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 14, 0));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setMaximumSize(new Dimension(360, 44));
        buttonsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        calculateButton = createButton("Calculate", GREEN, 160, 44);
        clearButton = createButton("Clear", BLUE, 160, 44);

        buttonsPanel.add(calculateButton);
        buttonsPanel.add(clearButton);

        panel.add(buttonsPanel);

        panel.add(Box.createVerticalGlue());

        JLabel hint = new JLabel("<html>Example: Rental Days = 5, Late Days = 2</html>");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        hint.setForeground(TEXT_GRAY);
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(hint);

        return panel;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Payment Summary");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(TEXT_DARK);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Calculated values will appear below.");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(TEXT_GRAY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(8));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(26));

        rentalCostValueLabel = createValueLabel("0.00 ₪", BLUE);
        penaltyValueLabel = createValueLabel("0.00 ₪", ORANGE);
        totalValueLabel = createValueLabel("0.00 ₪", GREEN);

        panel.add(createResultCard("Rental Cost", rentalCostValueLabel));
        panel.add(Box.createVerticalStrut(14));
        panel.add(createResultCard("Late Penalty", penaltyValueLabel));
        panel.add(Box.createVerticalStrut(14));
        panel.add(createResultCard("Total Cost", totalValueLabel));

        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createResultCard(String title, JLabel valueLabel) {
        RoundedPanel card = new RoundedPanel(20, Color.WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(18, 20, 18, 20));
        card.setMaximumSize(new Dimension(360, 80));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(TEXT_GRAY);

        card.add(titleLabel, BorderLayout.WEST);
        card.add(valueLabel, BorderLayout.EAST);

        return card;
    }

    private JLabel createValueLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(color);
        return label;
    }

    private JLabel createInputLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setForeground(TEXT_DARK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(360, 44));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setForeground(TEXT_DARK);
        field.setCaretColor(BLUE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 210, 225), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JButton createButton(String text, Color color, int width, int height) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
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

    private JPanel createFooter() {
        JPanel footer = new JPanel();
        footer.setBackground(new Color(248, 249, 250));
        footer.setBorder(new EmptyBorder(12, 0, 12, 0));

        JLabel label = new JLabel("SB Car Rental Management System");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(TEXT_GRAY);

        footer.add(label);
        return footer;
    }

    private void initializeEvents() {
        calculateButton.addActionListener(e -> calculateBill());

        clearButton.addActionListener(e -> clearFields());

        backButton.addActionListener(e -> {
            dispose();
            new DashboardFrame();
        });
    }

    private void calculateBill() {
        String rentalDaysText = rentalDaysField.getText().trim();
        String lateDaysText = lateDaysField.getText().trim();

        if (rentalDaysText.isEmpty() || lateDaysText.isEmpty()) {
            showMessage("Please enter rental days and late days.");
            return;
        }

        int rentalDays;
        int lateDays;

        try {
            rentalDays = Integer.parseInt(rentalDaysText);
            lateDays = Integer.parseInt(lateDaysText);
        } catch (NumberFormatException ex) {
            showMessage("Please enter valid numbers.");
            return;
        }

        if (rentalDays < 0 || lateDays < 0) {
            showMessage("Days cannot be negative.");
            return;
        }

        double rentalCost = billingService.calculateRentalCost(rentalDays);
        double penalty = billingService.calculateLatePenalty(lateDays);
        double total = billingService.calculateTotal(rentalDays, lateDays);

        rentalCostValueLabel.setText(formatMoney(rentalCost));
        penaltyValueLabel.setText(formatMoney(penalty));
        totalValueLabel.setText(formatMoney(total));

        messageLabel.setForeground(GREEN);
        messageLabel.setText("Bill calculated successfully.");
    }

    private String formatMoney(double value) {
        return String.format("%.2f ₪", value);
    }

    private void clearFields() {
        rentalDaysField.setText("");
        lateDaysField.setText("");

        rentalCostValueLabel.setText("0.00 ₪");
        penaltyValueLabel.setText("0.00 ₪");
        totalValueLabel.setText("0.00 ₪");

        messageLabel.setText(" ");
        messageLabel.setForeground(RED);
    }

    private void showMessage(String message) {
        messageLabel.setForeground(RED);
        messageLabel.setText(message);
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

            g2.setColor(new Color(0, 0, 0, 14));
            g2.fillRoundRect(6, 6, getWidth() - 12, getHeight() - 12, radius, radius);

            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth() - 12, getHeight() - 12, radius, radius);

            g2.dispose();

            super.paintComponent(g);
        }
    }

    private static class GradientPanel extends JPanel {

        private final Color startColor;
        private final Color endColor;

        public GradientPanel(Color startColor, Color endColor) {
            this.startColor = startColor;
            this.endColor = endColor;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;

            GradientPaint gradient = new GradientPaint(
                    0, 0, startColor,
                    getWidth(), getHeight(), endColor
            );

            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}