package com.vehiclerental.presentation;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.notification.EmailNotificationService;
import com.vehiclerental.repository.VehicleRepository;
import com.vehiclerental.service.RentalService;
import com.vehiclerental.service.VehicleService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RentalReminderFrame extends JFrame {

    private JComboBox<Vehicle> vehicleComboBox;

    private JButton reminderButton;

    private JButton backButton;

    private JLabel messageLabel;

    private final VehicleService vehicleService =
            new VehicleService(new VehicleRepository());

    private final RentalService rentalService =
            new RentalService(new EmailNotificationService());

    public RentalReminderFrame() {

        initializeFrame();

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(createHeader(), BorderLayout.NORTH);

        mainPanel.add(createCenter(), BorderLayout.CENTER);

        add(mainPanel);

        loadVehicles();

        initializeEvents();

        setVisible(true);

    }

    private void initializeFrame() {

        setTitle("Rental Reminder");

        setSize(800,600);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    private JPanel createHeader() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(new Color(100,181,246));

        panel.setBorder(new EmptyBorder(15,20,15,20));

        JLabel title = new JLabel("Rental Reminder");

        title.setForeground(Color.WHITE);

        title.setFont(new Font("Segoe UI",Font.BOLD,28));

        panel.add(title,BorderLayout.WEST);

        backButton = new JButton("Back");

        backButton.setBackground(new Color(239,83,80));

        backButton.setForeground(Color.WHITE);

        backButton.setFocusPainted(false);

        panel.add(backButton,BorderLayout.EAST);

        return panel;

    }
    private JPanel createCenter() {

        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        panel.setBorder(new EmptyBorder(50,120,50,120));

        JLabel vehicleLabel = new JLabel("Select Vehicle");

        vehicleLabel.setFont(new Font("Segoe UI",Font.BOLD,18));

        vehicleComboBox = new JComboBox<>();

        vehicleComboBox.setMaximumSize(new Dimension(400,40));

        vehicleComboBox.setFont(new Font("Segoe UI",Font.PLAIN,16));

        reminderButton = new JButton("Send Reminder");

        reminderButton.setBackground(new Color(66,165,245));

        reminderButton.setForeground(Color.WHITE);

        reminderButton.setFont(new Font("Segoe UI",Font.BOLD,18));

        reminderButton.setFocusPainted(false);

        reminderButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        messageLabel = new JLabel("");

        messageLabel.setFont(new Font("Segoe UI",Font.BOLD,16));

        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(vehicleLabel);

        panel.add(Box.createVerticalStrut(15));

        panel.add(vehicleComboBox);

        panel.add(Box.createVerticalStrut(35));

        panel.add(reminderButton);

        panel.add(Box.createVerticalStrut(25));

        panel.add(messageLabel);

        return panel;

    }
    private void loadVehicles() {

        vehicleComboBox.removeAllItems();

        for (Vehicle vehicle : vehicleService.getAvailableVehicles()) {

            vehicleComboBox.addItem(vehicle);

        }

    }
    private void initializeEvents() {

        reminderButton.addActionListener(e -> sendReminder());

        backButton.addActionListener(e -> {

            dispose();

            new DashboardFrame();

        });

    }
    private void sendReminder() {

        Vehicle vehicle = (Vehicle) vehicleComboBox.getSelectedItem();

        if (vehicle == null) {

            messageLabel.setForeground(Color.RED);

            messageLabel.setText("Please select a vehicle.");

            return;

        }

        rentalService.sendRentalReminder(vehicle);

        messageLabel.setForeground(new Color(0,128,0));

        messageLabel.setText("Reminder sent successfully.");

    }}