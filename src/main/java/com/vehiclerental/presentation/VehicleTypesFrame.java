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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * VehicleTypesFrame displays category-based statistics and inventory list.
 */
public class VehicleTypesFrame extends JFrame {

    private static final String DEFAULT_FONT = "Segoe UI";
    private static final String APP_BRAND = "SB Car Rental Management System";

    // Theme Palette
    private static final Color DARK_NAVY = new Color(18, 54, 82);
    private static final Color LIGHT_NAVY = new Color(35, 92, 132);
    private static final Color BG_MAIN = new Color(244, 248, 252);
    private static final Color TEXT_MAIN = new Color(33, 37, 41);
    private static final Color TEXT_SUB = new Color(108, 117, 125);
    private static final Color COLOR_BLUE = new Color(65, 130, 190);
    private static final Color COLOR_GREEN = new Color(78, 150, 120);
    private static final Color COLOR_AMBER = new Color(210, 145, 80);
    private static final Color COLOR_RED = new Color(214, 80, 80);

    // Data Components
    private DefaultTableModel tableGridModel;
    private JButton navBackButton;
    private JButton navRefreshButton;

    // Metrics Labels
    private JLabel countTotalLabel;
    private JLabel countCategoriesLabel;
    private JLabel dominantCategoryLabel;

    private final VehicleRepository vehicleDataRepo = new VehicleRepository();

    public VehicleTypesFrame() {
        configureFrameSettings();
        assembleLayout();
        attachEventListeners();
        fetchAndDisplayData();
        setVisible(true);
    }

    private void configureFrameSettings() {
        setTitle("Vehicle Types");
        setSize(1050, 680);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void assembleLayout() {
        JPanel basePanel = new JPanel(new BorderLayout());
        basePanel.setBackground(BG_MAIN);

        basePanel.add(buildTopBar(), BorderLayout.NORTH);
        basePanel.add(buildCentralContent(), BorderLayout.CENTER);
        basePanel.add(buildBottomBar(), BorderLayout.SOUTH);

        setContentPane(basePanel);
    }

    private JPanel buildTopBar() {
        JPanel headerContainer = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, DARK_NAVY, getWidth(), getHeight(), LIGHT_NAVY));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerContainer.setBorder(new EmptyBorder(24, 34, 24, 34));

        JPanel titleWrapper = new JPanel();
        titleWrapper.setOpaque(false);
        titleWrapper.setLayout(new BoxLayout(titleWrapper, BoxLayout.Y_AXIS));

        JLabel headTitle = buildLabel("Vehicle Types", Font.BOLD, 32, Color.WHITE);
        JLabel headSub = buildLabel("View all vehicles grouped by their rental category", Font.PLAIN, 15, new Color(220, 235, 245));

        titleWrapper.add(headTitle);
        titleWrapper.add(Box.createVerticalStrut(5));
        titleWrapper.add(headSub);

        JPanel btnFlow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 6));
        btnFlow.setOpaque(false);

        navRefreshButton = buildStyledButton("Refresh", COLOR_BLUE);
        navBackButton = buildStyledButton("Back", COLOR_RED);

        btnFlow.add(navRefreshButton);
        btnFlow.add(navBackButton);

        headerContainer.add(titleWrapper, BorderLayout.WEST);
        headerContainer.add(btnFlow, BorderLayout.EAST);

        return headerContainer;
    }

    private JPanel buildCentralContent() {
        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.setBackground(BG_MAIN);
        bodyPanel.setBorder(new EmptyBorder(28, 38, 28, 38));

        bodyPanel.add(buildMetricsDeck(), BorderLayout.NORTH);

        JPanel gridCard = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 14));
                g2.fillRoundRect(6, 6, getWidth() - 12, getHeight() - 12, 24, 24);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 12, getHeight() - 12, 24, 24);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        gridCard.setOpaque(false);
        gridCard.setBorder(new EmptyBorder(22, 22, 22, 22));
        gridCard.add(buildTableComponent(), BorderLayout.CENTER);

        bodyPanel.add(gridCard, BorderLayout.CENTER);

        return bodyPanel;
    }

    private JPanel buildMetricsDeck() {
        JPanel metricsCard = new JPanel(new GridLayout(1, 3, 20, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth() - 12, getHeight() - 12, 22, 22);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        metricsCard.setOpaque(false);
        metricsCard.setBorder(new EmptyBorder(18, 24, 18, 24));
        metricsCard.setPreferredSize(new Dimension(900, 105));

        countTotalLabel = buildLabel("0", Font.BOLD, 24, COLOR_BLUE);
        countCategoriesLabel = buildLabel("0", Font.BOLD, 24, COLOR_GREEN);
        dominantCategoryLabel = buildLabel("--", Font.BOLD, 24, COLOR_AMBER);

        countTotalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        countCategoriesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dominantCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);

        metricsCard.add(createMetricItem("Total Vehicles", countTotalLabel));
        metricsCard.add(createMetricItem("Vehicle Types", countCategoriesLabel));
        metricsCard.add(createMetricItem("Most Common Type", dominantCategoryLabel));

        return metricsCard;
    }

    private JPanel createMetricItem(String headerText, JLabel valueDisplay) {
        JPanel itemBox = new JPanel();
        itemBox.setOpaque(false);
        itemBox.setLayout(new BoxLayout(itemBox, BoxLayout.Y_AXIS));

        JLabel titleLbl = buildLabel(headerText, Font.PLAIN, 15, TEXT_SUB);

        itemBox.add(Box.createVerticalGlue());
        itemBox.add(titleLbl);
        itemBox.add(Box.createVerticalStrut(8));
        itemBox.add(valueDisplay);
        itemBox.add(Box.createVerticalGlue());

        return itemBox;
    }

    private JScrollPane buildTableComponent() {
        String[] headers = {"ID", "Brand", "Model", "Type"};

        tableGridModel = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable gridTable = new JTable(tableGridModel);
        gridTable.setRowHeight(38);
        gridTable.setFont(new Font(DEFAULT_FONT, Font.PLAIN, 15));
        gridTable.setForeground(TEXT_MAIN);
        gridTable.setGridColor(new Color(225, 232, 238));
        gridTable.setSelectionBackground(new Color(225, 241, 255));
        gridTable.setSelectionForeground(TEXT_MAIN);
        gridTable.setShowVerticalLines(false);

        JTableHeader th = gridTable.getTableHeader();
        th.setFont(new Font(DEFAULT_FONT, Font.BOLD, 15));
        th.setBackground(new Color(235, 242, 248));
        th.setForeground(TEXT_MAIN);
        th.setPreferredSize(new Dimension(th.getWidth(), 42));

        DefaultTableCellRenderer centerAlignment = new DefaultTableCellRenderer();
        centerAlignment.setHorizontalAlignment(SwingConstants.CENTER);

        gridTable.getColumnModel().getColumn(0).setCellRenderer(centerAlignment);
        gridTable.getColumnModel().getColumn(3).setCellRenderer(centerAlignment);

        gridTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        gridTable.getColumnModel().getColumn(1).setPreferredWidth(220);
        gridTable.getColumnModel().getColumn(2).setPreferredWidth(260);
        gridTable.getColumnModel().getColumn(3).setPreferredWidth(180);

        JScrollPane scrollContainer = new JScrollPane(gridTable);
        scrollContainer.setBorder(BorderFactory.createLineBorder(new Color(220, 230, 238), 1));
        scrollContainer.getViewport().setBackground(Color.WHITE);

        return scrollContainer;
    }

    private JPanel buildBottomBar() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(248, 249, 250));
        footerPanel.setBorder(new EmptyBorder(12, 0, 12, 0));

        JLabel brandNotice = buildLabel(APP_BRAND, Font.PLAIN, 13, TEXT_SUB);
        brandNotice.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerPanel.add(brandNotice);

        return footerPanel;
    }

    // --- Control Factories ---

    private JLabel buildLabel(String caption, int style, int size, Color color) {
        JLabel lbl = new JLabel(caption);
        lbl.setFont(new Font(DEFAULT_FONT, style, size));
        lbl.setForeground(color);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    private JButton buildStyledButton(String labelText, Color bgTheme) {
        JButton button = new JButton(labelText);
        button.setPreferredSize(new Dimension(110, 42));
        button.setBackground(bgTheme);
        button.setForeground(Color.WHITE);
        button.setFont(new Font(DEFAULT_FONT, Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgTheme.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgTheme);
            }
        });

        return button;
    }

    // --- Action Mechanics ---

    private void fetchAndDisplayData() {
        tableGridModel.setRowCount(0);

        var allVehicles = vehicleDataRepo.getAllVehicles();

        for (Vehicle v : allVehicles) {
            tableGridModel.addRow(new Object[]{
                    v.getId(),
                    v.getBrand(),
                    v.getModel(),
                    String.valueOf(v.getVehicleType())
            });
        }

        countTotalLabel.setText(String.valueOf(allVehicles.size()));

        // Group & Count using Stream API
        Map<String, Long> categoryFrequencyMap = allVehicles.stream()
                .map(v -> String.valueOf(v.getVehicleType()))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        countCategoriesLabel.setText(String.valueOf(categoryFrequencyMap.size()));

        String topCategory = categoryFrequencyMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("--");

        dominantCategoryLabel.setText(topCategory);
    }

    private void attachEventListeners() {
        navRefreshButton.addActionListener(e -> fetchAndDisplayData());
        navBackButton.addActionListener(e -> {
            dispose();
            new DashboardFrame();
        });
    }
}