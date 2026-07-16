package com.vehiclerental.presentation;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.repository.VehicleRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VehicleTypesFrame extends JFrame {

    private JTable table;

    private DefaultTableModel model;

    private JButton backButton;

    private final VehicleRepository repository =
            new VehicleRepository();

    public VehicleTypesFrame() {

        initializeFrame();

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(createHeader(), BorderLayout.NORTH);

        mainPanel.add(createTable(), BorderLayout.CENTER);

        add(mainPanel);

        loadVehicleTypes();

        initializeEvents();

        setVisible(true);

    }

    private void initializeFrame() {

        setTitle("Vehicle Types");

        setSize(850,600);

        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    private JPanel createHeader() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(new Color(100,181,246));

        panel.setBorder(new EmptyBorder(15,20,15,20));

        JLabel title = new JLabel("Vehicle Types");

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
    private JScrollPane createTable() {

        model = new DefaultTableModel(

                new String[]{

                        "ID",

                        "Brand",

                        "Model",

                        "Type"

                },0){

            @Override
            public boolean isCellEditable(int row,int column){

                return false;

            }

        };

        table = new JTable(model);

        table.setRowHeight(28);

        table.setFont(new Font("Segoe UI",Font.PLAIN,15));

        table.getTableHeader().setFont(
                new Font("Segoe UI",Font.BOLD,15));

        return new JScrollPane(table);

    }
    private void loadVehicleTypes() {

        model.setRowCount(0);

        for (Vehicle vehicle : repository.getAllVehicles()) {

            model.addRow(new Object[]{

                    vehicle.getId(),

                    vehicle.getBrand(),

                    vehicle.getModel(),

                    vehicle.getVehicleType()

            });

        }

    }
    private void initializeEvents() {

        backButton.addActionListener(e -> {

            dispose();

            new DashboardFrame();

        });

    }}