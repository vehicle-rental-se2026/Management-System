package com.vehiclerental.presentation;

import com.vehiclerental.billing.BillingService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BillingFrame extends JFrame {

    private JTextField rentalDaysField;
    private JTextField lateDaysField;

    private JLabel rentalCostLabel;
    private JLabel penaltyLabel;
    private JLabel totalLabel;

    private JButton calculateButton;
    private JButton backButton;

    private final BillingService billingService =
            new BillingService();

    public BillingFrame() {

        initializeFrame();

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(createHeader(), BorderLayout.NORTH);

        mainPanel.add(createCenter(), BorderLayout.CENTER);

        add(mainPanel);

        initializeEvents();

        setVisible(true);

    }

    private void initializeFrame() {

        setTitle("Billing");

        setSize(800,600);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    private JPanel createHeader() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(new Color(100,181,246));

        panel.setBorder(new EmptyBorder(15,20,15,20));

        JLabel title = new JLabel("Billing");

        title.setForeground(Color.WHITE);

        title.setFont(new Font("Segoe UI",Font.BOLD,28));

        panel.add(title,BorderLayout.WEST);

        backButton = new JButton("Back");

        backButton.setBackground(new Color(239,83,80));

        backButton.setForeground(Color.WHITE);

        backButton.setFont(new Font("Segoe UI",Font.BOLD,15));

        backButton.setFocusPainted(false);

        panel.add(backButton,BorderLayout.EAST);

        return panel;

    }

    private JPanel createCenter() {

        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        panel.setBorder(new EmptyBorder(40,120,40,120));

        JLabel rentalDaysLabel = new JLabel("Rental Days");

        rentalDaysLabel.setFont(new Font("Segoe UI",Font.BOLD,18));

        rentalDaysField = new JTextField();

        rentalDaysField.setMaximumSize(new Dimension(400,40));

        rentalDaysField.setFont(new Font("Segoe UI",Font.PLAIN,16));

        JLabel lateDaysLabel = new JLabel("Late Days");

        lateDaysLabel.setFont(new Font("Segoe UI",Font.BOLD,18));

        lateDaysField = new JTextField();

        lateDaysField.setMaximumSize(new Dimension(400,40));

        lateDaysField.setFont(new Font("Segoe UI",Font.PLAIN,16));

        calculateButton = new JButton("Calculate");

        calculateButton.setBackground(new Color(66,165,245));

        calculateButton.setForeground(Color.WHITE);

        calculateButton.setFont(new Font("Segoe UI",Font.BOLD,18));

        calculateButton.setFocusPainted(false);

        calculateButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        rentalCostLabel = new JLabel("Rental Cost : 0.0 JD");

        rentalCostLabel.setFont(new Font("Segoe UI",Font.BOLD,16));

        penaltyLabel = new JLabel("Late Penalty : 0.0 JD");

        penaltyLabel.setFont(new Font("Segoe UI",Font.BOLD,16));

        totalLabel = new JLabel("Total Cost : 0.0 JD");

        totalLabel.setFont(new Font("Segoe UI",Font.BOLD,18));

        panel.add(rentalDaysLabel);

        panel.add(Box.createVerticalStrut(10));

        panel.add(rentalDaysField);

        panel.add(Box.createVerticalStrut(20));

        panel.add(lateDaysLabel);

        panel.add(Box.createVerticalStrut(10));

        panel.add(lateDaysField);

        panel.add(Box.createVerticalStrut(30));

        panel.add(calculateButton);

        panel.add(Box.createVerticalStrut(30));

        panel.add(rentalCostLabel);

        panel.add(Box.createVerticalStrut(10));

        panel.add(penaltyLabel);

        panel.add(Box.createVerticalStrut(10));

        panel.add(totalLabel);

        return panel;

    }

    private void initializeEvents() {

        calculateButton.addActionListener(e -> calculateBill());

        backButton.addActionListener(e -> {

            dispose();

            new DashboardFrame();

        });

    }

    private void calculateBill() {

        try {

            int rentalDays = Integer.parseInt(rentalDaysField.getText());

            int lateDays = Integer.parseInt(lateDaysField.getText());

            double rentalCost =
                    billingService.calculateRentalCost(rentalDays);

            double penalty =
                    billingService.calculateLatePenalty(lateDays);

            double total =
                    billingService.calculateTotal(rentalDays, lateDays);

            rentalCostLabel.setText(
                    "Rental Cost : " + rentalCost + "ILS₪");

            penaltyLabel.setText(
                    "Late Penalty : " + penalty + "ILS₪");

            totalLabel.setText(
                    "Total Cost : " + total + "ILS₪");

        }

        catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numbers.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }
}

