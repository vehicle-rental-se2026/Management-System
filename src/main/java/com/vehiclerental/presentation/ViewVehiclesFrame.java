package com.vehiclerental.presentation;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.repository.VehicleRepository;
import com.vehiclerental.service.VehicleService;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * ViewVehiclesFrame displays the complete active catalog of vehicles available for rent.
 */
public class ViewVehiclesFrame extends JFrame {

    private static final String APP_FONT = "Segoe UI";

    // Palette Setup
    private static final Color DARK_PRIMARY = new Color(18, 54, 82);
    private static final Color DARK_SECONDARY = new Color(35, 92, 132);
    private static final Color CANVAS_BG = new Color(244, 248, 252);
    private static final Color CONTENT_TEXT = new Color(33, 37, 41);
    private static final Color MUTED_TEXT = new Color(108, 117, 125);
    private static final Color ACCENT_BLUE = new Color(65, 130, 190);
    private static final Color DANGER_RED = new Color(214, 80, 80);
    private static final Color EMERALD_GREEN = new Color(78, 150, 120);

    private DefaultTableModel fleetTableModel;
    private JButton reloadDataBtn;
    private JButton navigateBackBtn;
    private JLabel totalFleetCounter;

    private final VehicleService vehicleDataService = new VehicleService(new VehicleRepository());

    public ViewVehiclesFrame() {
        configureFrameWindow();

        JPanel masterContainer = new JPanel(new BorderLayout());
        masterContainer.setBackground(CANVAS_BG);

        masterContainer.add(buildTopHeaderSection(), BorderLayout.NORTH);
        masterContainer.add(buildMainGridSection(), BorderLayout.CENTER);
        masterContainer.add(buildBottomBarSection(), BorderLayout.SOUTH);

        add(masterContainer);

        populateFleetData();
        attachEventListeners();

        setVisible(true);
    }

    private void configureFrameWindow() {
        setTitle("View Vehicles");
        setSize(1050, 680);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JPanel buildTopHeaderSection() {
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, DARK_PRIMARY, getWidth(), getHeight(), DARK_SECONDARY));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setBorder(new EmptyBorder(24, 34, 24, 34));

        JPanel titleGroup = new JPanel();
        titleGroup.setOpaque(false);
        titleGroup.setLayout(new BoxLayout(titleGroup, BoxLayout.Y_AXIS));

        JLabel titleText = new JLabel("Available Vehicles");
        titleText.setFont(new Font(APP_FONT, Font.BOLD, 32));
        titleText.setForeground(Color.WHITE);

        JLabel subtext = new JLabel("Browse all available vehicles in the rental system");
        subtext.setFont(new Font(APP_FONT, Font.PLAIN, 15));
        subtext.setForeground(new Color(220, 235, 245));

        titleGroup.add(titleText);
        titleGroup.add(Box.createVerticalStrut(5));
        titleGroup.add(subtext);

        headerPanel.add(titleGroup, BorderLayout.WEST);

        JPanel controlsGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 6));
        controlsGroup.setOpaque(false);

        reloadDataBtn = constructActionButton("Refresh", ACCENT_BLUE);
        navigateBackBtn = constructActionButton("Back", DANGER_RED);

        controlsGroup.add(reloadDataBtn);
        controlsGroup.add(navigateBackBtn);

        headerPanel.add(controlsGroup, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel buildMainGridSection() {
        JPanel workspace = new JPanel(new BorderLayout());
        workspace.setBackground(CANVAS_BG);
        workspace.setBorder(new EmptyBorder(28, 38, 28, 38));

        workspace.add(buildStatusOverviewBar(), BorderLayout.NORTH);

        JPanel cardContainer = new JPanel(new BorderLayout());
        cardContainer.setBackground(Color.WHITE);
        cardContainer.setBorder(new CompoundBorder(
                new LineBorder(new Color(218, 228, 236), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        cardContainer.add(buildDataTableComponent(), BorderLayout.CENTER);
        workspace.add(cardContainer, BorderLayout.CENTER);

        return workspace;
    }

    private JPanel buildStatusOverviewBar() {
        JPanel summaryBox = new JPanel(new BorderLayout());
        summaryBox.setBackground(Color.WHITE);
        summaryBox.setBorder(new CompoundBorder(
                new LineBorder(new Color(218, 228, 236), 1, true),
                new EmptyBorder(16, 24, 16, 24)
        ));
        summaryBox.setPreferredSize(new Dimension(900, 95));

        JPanel textStack = new JPanel();
        textStack.setOpaque(false);
        textStack.setLayout(new BoxLayout(textStack, BoxLayout.Y_AXIS));

        JLabel headerLbl = new JLabel("Vehicle List");
        headerLbl.setFont(new Font(APP_FONT, Font.BOLD, 22));
        headerLbl.setForeground(CONTENT_TEXT);

        JLabel infoLbl = new JLabel("Only available vehicles are displayed in this table.");
        infoLbl.setFont(new Font(APP_FONT, Font.PLAIN, 14));
        infoLbl.setForeground(MUTED_TEXT);

        textStack.add(Box.createVerticalGlue());
        textStack.add(headerLbl);
        textStack.add(Box.createVerticalStrut(6));
        textStack.add(infoLbl);
        textStack.add(Box.createVerticalGlue());

        totalFleetCounter = new JLabel("0 Vehicles");
        totalFleetCounter.setFont(new Font(APP_FONT, Font.BOLD, 20));
        totalFleetCounter.setForeground(EMERALD_GREEN);
        totalFleetCounter.setHorizontalAlignment(SwingConstants.RIGHT);

        summaryBox.add(textStack, BorderLayout.WEST);
        summaryBox.add(totalFleetCounter, BorderLayout.EAST);

        return summaryBox;
    }

    private JScrollPane buildDataTableComponent() {
        String[] tableHeaders = {"ID", "Brand", "Model", "Status"};

        fleetTableModel = new DefaultTableModel(tableHeaders, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        JTable vehicleGrid = new JTable(fleetTableModel);
        vehicleGrid.setRowHeight(40);
        vehicleGrid.setFont(new Font(APP_FONT, Font.PLAIN, 15));
        vehicleGrid.setForeground(CONTENT_TEXT);
        vehicleGrid.setGridColor(new Color(230, 236, 242));
        vehicleGrid.setSelectionBackground(new Color(228, 242, 255));
        vehicleGrid.setSelectionForeground(CONTENT_TEXT);
        vehicleGrid.setShowVerticalLines(false);

        vehicleGrid.getTableHeader().setFont(new Font(APP_FONT, Font.BOLD, 15));
        vehicleGrid.getTableHeader().setBackground(new Color(236, 243, 249));
        vehicleGrid.getTableHeader().setForeground(CONTENT_TEXT);
        vehicleGrid.getTableHeader().setPreferredSize(new Dimension(0, 42));

        DefaultTableCellRenderer centeredCellRenderer = new DefaultTableCellRenderer();
        centeredCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        vehicleGrid.getColumnModel().getColumn(0).setCellRenderer(centeredCellRenderer);
        vehicleGrid.getColumnModel().getColumn(3).setCellRenderer(centeredCellRenderer);

        vehicleGrid.getColumnModel().getColumn(0).setPreferredWidth(90);
        vehicleGrid.getColumnModel().getColumn(1).setPreferredWidth(210);
        vehicleGrid.getColumnModel().getColumn(2).setPreferredWidth(250);
        vehicleGrid.getColumnModel().getColumn(3).setPreferredWidth(150);

        JScrollPane scrollContainer = new JScrollPane(vehicleGrid);
        scrollContainer.setBorder(BorderFactory.createLineBorder(new Color(220, 230, 238), 1));
        scrollContainer.getViewport().setBackground(Color.WHITE);

        return scrollContainer;
    }

    private JPanel buildBottomBarSection() {
        JPanel footerBar = new JPanel();
        footerBar.setBackground(new Color(248, 249, 250));
        footerBar.setBorder(new EmptyBorder(12, 0, 12, 0));

        JLabel footerCaption = new JLabel("SB Car Rental Management System");
        footerCaption.setFont(new Font(APP_FONT, Font.PLAIN, 13));
        footerCaption.setForeground(MUTED_TEXT);

        footerBar.add(footerCaption);

        return footerBar;
    }

    private JButton constructActionButton(String caption, Color themeColor) {
        JButton button = new JButton(caption);
        button.setPreferredSize(new Dimension(110, 42));
        button.setBackground(themeColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font(APP_FONT, Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(themeColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(themeColor);
            }
        });

        return button;
    }

    private void populateFleetData() {
        fleetTableModel.setRowCount(0);

        List<Vehicle> activeFleet = vehicleDataService.getAvailableVehicles();

        for (Vehicle entry : activeFleet) {
            fleetTableModel.addRow(new Object[]{
                    entry.getId(),
                    entry.getBrand(),
                    entry.getModel(),
                    entry.isAvailable() ? "Available" : "Rented"
            });
        }

        totalFleetCounter.setText(activeFleet.size() + " Vehicles");
    }

    private void attachEventListeners() {
        reloadDataBtn.addActionListener(e -> populateFleetData());

        navigateBackBtn.addActionListener(e -> {
            dispose();
            new DashboardFrame();
        });
    }
}