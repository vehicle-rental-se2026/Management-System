package com.vehiclerental.presentation;

import com.vehiclerental.domain.Rental;
import com.vehiclerental.notification.EmailNotificationService;
import com.vehiclerental.repository.RentalRepository;
import com.vehiclerental.service.RentalService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ReturnVehicleFrame extends JFrame {

    private JComboBox<Rental> rentalComboBox;

    private JButton returnButton;

    private JButton backButton;

    private JLabel messageLabel;

    private final RentalRepository rentalRepository =
            new RentalRepository();

    private final RentalService rentalService =
            new RentalService(new EmailNotificationService());

    public ReturnVehicleFrame() {

        initializeFrame();

        JPanel panel = new JPanel(new BorderLayout());

        panel.add(createHeader(), BorderLayout.NORTH);

        panel.add(createCenter(), BorderLayout.CENTER);

        add(panel);

        loadRentals();

        initializeEvents();

        setVisible(true);

    }

    private void initializeFrame() {

        setTitle("Return Vehicle");

        setSize(800,600);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    private JPanel createHeader() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(new Color(100,181,246));

        panel.setBorder(new EmptyBorder(15,20,15,20));

        JLabel title = new JLabel("Return Vehicle");

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

        panel.setBorder(new EmptyBorder(40,120,40,120));

        JLabel label = new JLabel("Select Rental");

        label.setFont(new Font("Segoe UI",Font.BOLD,18));

        rentalComboBox = new JComboBox<>();

        rentalComboBox.setMaximumSize(new Dimension(400,40));

        returnButton = new JButton("Return Vehicle");

        returnButton.setBackground(new Color(76,175,80));

        returnButton.setForeground(Color.WHITE);

        returnButton.setFocusPainted(false);

        returnButton.setFont(new Font("Segoe UI",Font.BOLD,18));

        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        messageLabel = new JLabel("");

        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        messageLabel.setFont(new Font("Segoe UI",Font.BOLD,16));

        panel.add(label);

        panel.add(Box.createVerticalStrut(15));

        panel.add(rentalComboBox);

        panel.add(Box.createVerticalStrut(35));

        panel.add(returnButton);

        panel.add(Box.createVerticalStrut(20));

        panel.add(messageLabel);

        return panel;

    }
    private void loadRentals() {

        rentalComboBox.removeAllItems();

        for (Rental rental : rentalRepository.getAllRentals()) {

            if (rental.isActive()) {

                rentalComboBox.addItem(rental);

            }

        }

    }
    private void initializeEvents() {

        returnButton.addActionListener(e -> returnVehicle());

        backButton.addActionListener(e -> {

            dispose();

            new DashboardFrame();

        });

    }
    private void returnVehicle() {

        Rental rental = (Rental) rentalComboBox.getSelectedItem();

        if (rental == null) {

            messageLabel.setForeground(Color.RED);

            messageLabel.setText("Please select a rental.");

            return;

        }

        rentalService.returnVehicle(rental);

        messageLabel.setForeground(new Color(0,128,0));

        messageLabel.setText("Vehicle returned successfully.");

        loadRentals();

    }}