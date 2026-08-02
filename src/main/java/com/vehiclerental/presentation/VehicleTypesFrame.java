package com.vehiclerental.presentation;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.repository.VehicleRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The VehicleTypesFrame class provides the user
 * interface for viewing vehicle types.
 */
public class VehicleTypesFrame extends JFrame {

    private static final Color NAVY = new Color(18, 54, 82);
    private static final Color NAVY_LIGHT = new Color(35, 92, 132);
    private static final Color PAGE_BG = new Color(244, 248, 252);
    private static final Color TEXT_DARK = new Color(33, 37, 41);
    private static final Color TEXT_GRAY = new Color(108, 117, 125);
    private static final Color BLUE = new Color(65, 130, 190);
    private static final Color GREEN = new Color(78, 150, 120);
    private static final Color ORANGE = new Color(210, 145, 80);
    private static final Color RED = new Color(214, 80, 80);

    private DefaultTableModel model;

    private JButton backButton;
    private JButton refreshButton;

    private JLabel totalVehiclesLabel;
    private JLabel typesCountLabel;
    private JLabel mainTypeLabel;

    private final VehicleRepository repository =
            new VehicleRepository();

    public VehicleTypesFrame() {
        initializeFrame();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PAGE_BG);

        mainPanel.add(createHeader(), BorderLayout.NORTH);
        mainPanel.add(createCenter(), BorderLayout.CENTER);
        mainPanel.add(createFooter(), BorderLayout.SOUTH);

        add(mainPanel);

        loadVehicleTypes();
        initializeEvents();

        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Vehicle Types");
        setSize(1050, 680);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JPanel createHeader() {
        GradientPanel header = new GradientPanel(NAVY, NAVY_LIGHT);
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(24, 34, 24, 34));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Vehicle Types");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("View all vehicles grouped by their rental category");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(new Color(220, 235, 245));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.WEST);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 6));
        buttonsPanel.setOpaque(false);

        refreshButton = createHeaderButton("Refresh", BLUE);
        backButton = createHeaderButton("Back", RED);

        buttonsPanel.add(refreshButton);
        buttonsPanel.add(backButton);

        header.add(buttonsPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel createCenter() {
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(PAGE_BG);
        center.setBorder(new EmptyBorder(28, 38, 28, 38));

        center.add(createSummaryPanel(), BorderLayout.NORTH);

        RoundedPanel tableCard = new RoundedPanel(24, Color.WHITE);
        tableCard.setLayout(new BorderLayout());
        tableCard.setBorder(new EmptyBorder(22, 22, 22, 22));

        tableCard.add(createTable(), BorderLayout.CENTER);

        center.add(tableCard, BorderLayout.CENTER);

        return center;
    }

    private JPanel createSummaryPanel() {
        RoundedPanel panel = new RoundedPanel(22, Color.WHITE);
        panel.setLayout(new GridLayout(1, 3, 20, 0));
        panel.setBorder(new EmptyBorder(18, 24, 18, 24));
        panel.setPreferredSize(new Dimension(900, 105));

        totalVehiclesLabel = new JLabel("0", SwingConstants.CENTER);
        typesCountLabel = new JLabel("0", SwingConstants.CENTER);
        mainTypeLabel = new JLabel("--", SwingConstants.CENTER);

        panel.add(createInfoCard("Total Vehicles", totalVehiclesLabel, BLUE));
        panel.add(createInfoCard("Vehicle Types", typesCountLabel, GREEN));
        panel.add(createInfoCard("Most Common Type", mainTypeLabel, ORANGE));

        return panel;
    }

    private JPanel createInfoCard(String title, JLabel valueLabel, Color color) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        titleLabel.setForeground(TEXT_GRAY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(valueLabel);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JScrollPane createTable() {
        String[] columns = {
                "ID",
                "Brand",
                "Model",
                "Type"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(38);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setForeground(TEXT_DARK);
        table.setGridColor(new Color(225, 232, 238));
        table.setSelectionBackground(new Color(225, 241, 255));
        table.setSelectionForeground(TEXT_DARK);
        table.setShowVerticalLines(false);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(235, 242, 248));
        header.setForeground(TEXT_DARK);
        header.setPreferredSize(new Dimension(header.getWidth(), 42));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(220);
        table.getColumnModel().getColumn(2).setPreferredWidth(260);
        table.getColumnModel().getColumn(3).setPreferredWidth(180);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 230, 238), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        return scrollPane;
    }

    private void loadVehicleTypes() {
        model.setRowCount(0);

        Map<String, Integer> typeCounts = new LinkedHashMap<>();

        int totalVehicles = 0;

        for (Vehicle vehicle : repository.getAllVehicles()) {
            String type = String.valueOf(vehicle.getVehicleType());

            model.addRow(new Object[]{
                    vehicle.getId(),
                    vehicle.getBrand(),
                    vehicle.getModel(),
                    type
            });

            typeCounts.put(type, typeCounts.getOrDefault(type, 0) + 1);
            totalVehicles++;
        }

        totalVehiclesLabel.setText(String.valueOf(totalVehicles));
        typesCountLabel.setText(String.valueOf(typeCounts.size()));
        mainTypeLabel.setText(getMostCommonType(typeCounts));
    }

    private String getMostCommonType(Map<String, Integer> typeCounts) {
        String mostCommonType = "--";
        int highestCount = 0;

        for (Map.Entry<String, Integer> entry : typeCounts.entrySet()) {
            if (entry.getValue() > highestCount) {
                highestCount = entry.getValue();
                mostCommonType = entry.getKey();
            }
        }

        return mostCommonType;
    }

    private JButton createHeaderButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(110, 42));
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
        refreshButton.addActionListener(e -> loadVehicleTypes());

        backButton.addActionListener(e -> {
            dispose();
            new DashboardFrame();
        });
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