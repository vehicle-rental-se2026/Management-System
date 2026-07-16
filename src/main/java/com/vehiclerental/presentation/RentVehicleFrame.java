package com.vehiclerental.presentation;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.notification.EmailNotificationService;
import com.vehiclerental.repository.VehicleRepository;
import com.vehiclerental.service.RentalService;
import com.vehiclerental.service.VehicleService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RentVehicleFrame extends JFrame {

    private JComboBox<Vehicle> vehicleComboBox;

    private JTextField daysField;

    private JButton rentButton;

    private JButton backButton;

    private JLabel messageLabel;

    private final VehicleService vehicleService =
            new VehicleService(new VehicleRepository());

    private final RentalService rentalService =
            new RentalService(new EmailNotificationService());

    public RentVehicleFrame() {

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

        setTitle("Rent Vehicle");

        setSize(800,600);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    private JPanel createHeader() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(new Color(100,181,246));

        panel.setBorder(new EmptyBorder(15,20,15,20));

        JLabel title = new JLabel("Rent Vehicle");

        title.setFont(new Font("Segoe UI",Font.BOLD,28));

        title.setForeground(Color.WHITE);

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

        JLabel vehicleLabel = new JLabel("Select Vehicle");

        vehicleLabel.setFont(new Font("Segoe UI",Font.BOLD,18));

        vehicleComboBox = new JComboBox<>();

        vehicleComboBox.setMaximumSize(new Dimension(400,40));

        vehicleComboBox.setFont(new Font("Segoe UI",Font.PLAIN,16));

        JLabel daysLabel = new JLabel("Rental Days");

        daysLabel.setFont(new Font("Segoe UI",Font.BOLD,18));

        daysField = new JTextField();

        daysField.setMaximumSize(new Dimension(400,40));

        daysField.setFont(new Font("Segoe UI",Font.PLAIN,16));

        rentButton = new JButton("Rent Vehicle");

        rentButton.setBackground(new Color(66,165,245));

        rentButton.setForeground(Color.WHITE);

        rentButton.setFont(new Font("Segoe UI",Font.BOLD,18));

        rentButton.setFocusPainted(false);

        rentButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        messageLabel = new JLabel("");

        messageLabel.setFont(new Font("Segoe UI",Font.BOLD,16));

        messageLabel.setForeground(Color.RED);

        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(vehicleLabel);

        panel.add(Box.createVerticalStrut(10));

        panel.add(vehicleComboBox);

        panel.add(Box.createVerticalStrut(25));

        panel.add(daysLabel);

        panel.add(Box.createVerticalStrut(10));

        panel.add(daysField);

        panel.add(Box.createVerticalStrut(35));

        panel.add(rentButton);

        panel.add(Box.createVerticalStrut(20));

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

        rentButton.addActionListener(e -> rentVehicle());

        backButton.addActionListener(e -> {

            dispose();

            new DashboardFrame();

        });

    }

    private void rentVehicle() {

        Vehicle vehicle = (Vehicle) vehicleComboBox.getSelectedItem();

        if (vehicle == null) {

            messageLabel.setText("Please select a vehicle.");

            return;

        }

        if (daysField.getText().trim().isEmpty()) {

            messageLabel.setText("Please enter rental days.");

            return;

        }

        int days;

        try {

            days = Integer.parseInt(daysField.getText());

        }

        catch (NumberFormatException ex) {

            messageLabel.setText("Invalid number.");

            return;

        }

        boolean success = rentalService.rentVehicle(vehicle, days);

        if (success) {

            messageLabel.setForeground(new Color(0,128,0));

            messageLabel.setText("Vehicle rented successfully.");

            loadVehicles();

            daysField.setText("");

        }

        else {

            messageLabel.setForeground(Color.RED);

            messageLabel.setText("Vehicle cannot be rented.");

        }

    }
}
