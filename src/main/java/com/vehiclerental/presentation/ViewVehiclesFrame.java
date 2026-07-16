package com.vehiclerental.presentation;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.repository.VehicleRepository;
import com.vehiclerental.service.VehicleService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewVehiclesFrame extends JFrame {

    private JTable table;

    private DefaultTableModel model;

    private JButton refreshButton;

    private JButton backButton;

    private final VehicleService vehicleService =
            new VehicleService(new VehicleRepository());

    public ViewVehiclesFrame() {

        initializeFrame();

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(createHeader(), BorderLayout.NORTH);

        mainPanel.add(createTable(), BorderLayout.CENTER);

        mainPanel.add(createFooter(), BorderLayout.SOUTH);

        add(mainPanel);

        loadVehicles();

        initializeEvents();

        setVisible(true);

    }

    private void initializeFrame() {

        setTitle("View Vehicles");

        setSize(1000,650);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    private JPanel createHeader() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(new Color(100,181,246));

        panel.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));

        JLabel title = new JLabel("Available Vehicles");

        title.setForeground(Color.WHITE);

        title.setFont(new Font("Segoe UI",Font.BOLD,28));

        panel.add(title,BorderLayout.WEST);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        buttons.setOpaque(false);

        refreshButton = new JButton("Refresh");

        refreshButton.setBackground(new Color(66,165,245));

        refreshButton.setForeground(Color.WHITE);

        refreshButton.setFocusPainted(false);

        backButton = new JButton("Back");

        backButton.setBackground(new Color(239,83,80));

        backButton.setForeground(Color.WHITE);

        backButton.setFocusPainted(false);

        buttons.add(refreshButton);

        buttons.add(backButton);

        panel.add(buttons,BorderLayout.EAST);

        return panel;

    }

    private JScrollPane createTable() {

        String[] columns = {

                "ID",

                "Brand",

                "Model",

                "Available"

        };

        model = new DefaultTableModel(columns,0){

            @Override
            public boolean isCellEditable(int row,int column){

                return false;

            }

        };

        table = new JTable(model);

        table.setRowHeight(30);

        table.setFont(new Font("Segoe UI",Font.PLAIN,15));

        table.getTableHeader().setFont(
                new Font("Segoe UI",Font.BOLD,15));

        return new JScrollPane(table);

    }

    private JPanel createFooter(){

        JPanel panel = new JPanel();

        panel.setBackground(new Color(245,245,245));

        JLabel label = new JLabel(
                "SB Car Rental Management System");

        label.setForeground(Color.GRAY);

        panel.add(label);

        return panel;

    }

    private void loadVehicles(){

        model.setRowCount(0);

        List<Vehicle> vehicles =
                vehicleService.getAvailableVehicles();

        for(Vehicle vehicle : vehicles){

            model.addRow(new Object[]{

                    vehicle.getId(),

                    vehicle.getBrand(),

                    vehicle.getModel(),

                    vehicle.isAvailable() ? "Available" : "Rented"

            });

        }

    }

    private void initializeEvents(){

        refreshButton.addActionListener(e->{

            loadVehicles();

        });

        backButton.addActionListener(e->{

            dispose();

            new DashboardFrame();

        });

    }
}