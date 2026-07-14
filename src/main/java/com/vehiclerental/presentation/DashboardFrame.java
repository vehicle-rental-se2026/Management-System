package com.vehiclerental.presentation;

import com.vehiclerental.notification.EmailNotificationService;
import com.vehiclerental.repository.VehicleRepository;
import com.vehiclerental.service.RentalService;
import com.vehiclerental.service.VehicleService;
import com.vehiclerental.repository.VehicleRepository;
import com.vehiclerental.service.VehicleService;
import com.vehiclerental.service.RentalService;
import com.vehiclerental.notification.EmailNotificationService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private final VehicleService vehicleService =
            new VehicleService(new VehicleRepository());

    private final RentalService rentalService =
            new RentalService(new EmailNotificationService());

    private JButton vehiclesButton;
    private JButton rentButton;
    private JButton returnButton;
    private JButton billingButton;
    private JButton reminderButton;
    private JButton vehicleTypesButton;
    private JButton logoutButton;

    private JLabel availableVehiclesLabel;
    private JLabel rentedVehiclesLabel;
    private JLabel statusLabel;

    public DashboardFrame() {

        initializeFrame();

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createCenter(), BorderLayout.CENTER);
        mainPanel.add(createFooter(), BorderLayout.SOUTH);

        add(mainPanel);

        initializeEvents();

        refreshDashboard();

        setVisible(true);

    }

    private void initializeFrame() {

        setTitle("SB Car Rental Dashboard");
        setSize(1000,700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private JPanel createHeader() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(new Color(15,76,129));

        panel.setBorder(new EmptyBorder(15,20,15,20));

        JLabel title = new JLabel("SB CAR RENTAL");

        title.setForeground(Color.WHITE);

        title.setFont(new Font("Segoe UI",Font.BOLD,30));

        panel.add(title,BorderLayout.WEST);

        JLabel admin = new JLabel("Administrator");

        admin.setForeground(Color.WHITE);

        admin.setFont(new Font("Segoe UI",Font.PLAIN,17));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        rightPanel.setOpaque(false);

        rightPanel.add(admin);

        logoutButton = new JButton("Logout");

        logoutButton.setBackground(new Color(220,53,69));

        logoutButton.setForeground(Color.WHITE);

        logoutButton.setFocusPainted(false);

        logoutButton.setFont(new Font("Segoe UI",Font.BOLD,15));

        rightPanel.add(logoutButton);

        panel.add(rightPanel,BorderLayout.EAST);

        return panel;

    }

    private JPanel createCenter() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBorder(new EmptyBorder(20,20,20,20));

        JLabel welcome = new JLabel(
                "Welcome to SB Car Rental Management System",
                SwingConstants.CENTER);

        welcome.setFont(new Font("Segoe UI",Font.BOLD,28));

        panel.add(welcome,BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(2,3,20,20));

        vehiclesButton = createCard("🚗\nView Vehicles");
        rentButton = createCard("📋\nRent Vehicle");
        returnButton = createCard("↩\nReturn Vehicle");
        billingButton = createCard("💰\nBilling");
        vehicleTypesButton = createCard("🚙\nVehicle Types");
        reminderButton = createCard("🔔\nRental Reminder");

        cards.add(vehiclesButton);
        cards.add(rentButton);
        cards.add(returnButton);
        cards.add(billingButton);
        cards.add(vehicleTypesButton);
        cards.add(reminderButton);

        panel.add(cards,BorderLayout.CENTER);

        JPanel informationPanel = new JPanel(new GridLayout(3,1,5,5));

        informationPanel.setBorder(
                BorderFactory.createTitledBorder("System Information"));

        availableVehiclesLabel = new JLabel();

        rentedVehiclesLabel = new JLabel();

        statusLabel = new JLabel("System Status : Online");

        informationPanel.add(availableVehiclesLabel);
        informationPanel.add(rentedVehiclesLabel);
        informationPanel.add(statusLabel);

        panel.add(informationPanel,BorderLayout.SOUTH);

        return panel;

    }

    private JPanel createFooter() {

        JPanel panel = new JPanel();

        panel.setBackground(new Color(240,240,240));

        JLabel footer = new JLabel(
                "© 2026 SB Car Rental - Vehicle Rental Management System");

        footer.setFont(new Font("Segoe UI",Font.PLAIN,13));

        footer.setForeground(Color.GRAY);

        panel.add(footer);

        return panel;

    }

    private JButton createCard(String text) {

        JButton button = new JButton("<html><center>" +
                text.replace("\n","<br>") +
                "</center></html>");

        button.setBackground(new Color(25,118,210));

        button.setForeground(Color.WHITE);

        button.setFont(new Font("Segoe UI",Font.BOLD,18));

        button.setFocusPainted(false);

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        return button;

    }

    private void refreshDashboard() {

        int availableVehicles =
                vehicleService.getAvailableVehicles().size();

        availableVehiclesLabel.setText(
                "Available Vehicles : " + availableVehicles);

        rentedVehiclesLabel.setText(
                "Rented Vehicles : --");

    }

    private void initializeEvents() {

        logoutButton.addActionListener(e -> {

            int option = JOptionPane.showConfirmDialog(

                    this,

                    "Are you sure you want to logout?",

                    "Logout",

                    JOptionPane.YES_NO_OPTION

            );

            if(option == JOptionPane.YES_OPTION){

                dispose();

                new LoginFrame();

            }

        });

        vehiclesButton.addActionListener(e -> {

            dispose();

            new ViewVehiclesFrame();

        });

        rentButton.addActionListener(e -> {

            dispose();

            new RentVehicleFrame();

        });

        returnButton.addActionListener(e -> {

            dispose();

            new ReturnVehicleFrame();

        });

        billingButton.addActionListener(e -> {

            dispose();

            new BillingFrame();

        });

        vehicleTypesButton.addActionListener(e -> {

            dispose();

            new VehicleTypesFrame();

        });

        reminderButton.addActionListener(e -> {

            dispose();

            new RentalReminderFrame();

        });

    }

}