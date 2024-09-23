import javax.swing.*;
import javax.swing.table.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class PFM {
    private JFrame frame;
    private JTextField incomeField;
    private JTextField foodField;
    private JTextField rentField;
    private JTextField transportationField;
    private JTextField entertainmentField;
    private DefaultTableModel model;
    private JTable table;

    public PFM() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Personal Finance Manager");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        createInputPanel(panel);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{ double income = Double.parseDouble(incomeField.getText());
                    double food = Double.parseDouble(foodField.getText());
                    double rent = Double.parseDouble(rentField.getText());
                    double transportation = Double.parseDouble(transportationField.getText());
                    double entertainment = Double.parseDouble(entertainmentField.getText());

                    calculateAndDisplayCharts(income, food, rent, transportation, entertainment);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input!");
                }
            }
        });

        panel.add(submitButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    private void createInputPanel(JPanel panel) {
        JLabel incomeLabel = new JLabel("Income:");
        incomeField = new JTextField(10);
        panel.add(incomeLabel);
        panel.add(incomeField);

        JLabel foodLabel = new JLabel("Food:");
        foodField = new JTextField(10);
        panel.add(foodLabel);
        panel.add(foodField);

        JLabel rentLabel = new JLabel("Rent:");
        rentField = new JTextField(10);
        panel.add(rentLabel);
        panel.add(rentField);

        JLabel transportationLabel = new JLabel("Transportation:");
        transportationField = new JTextField(10);
        panel.add(transportationLabel);
        panel.add(transportationField);

        JLabel entertainmentLabel = new JLabel("Entertainment:");
        entertainmentField = new JTextField(10);
        panel.add(entertainmentLabel);
        panel.add(entertainmentField);
    }

    private void calculateAndDisplayCharts(double income, double food, double rent, double transportation, double entertainment) {
        double totalIncome = income - food - rent - transportation - entertainment;

        DefaultPieDataset expensesDataset = new DefaultPieDataset();
        expensesDataset.setValue("Food", food);
        expensesDataset.setValue("Rent", rent);
        expensesDataset.setValue("Transportation", transportation);
        expensesDataset.setValue("Entertainment", entertainment);

        JFreeChart chart = ChartFactory.createPieChart("Personal Finance Chart", expensesDataset, true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setCircular(true);
        plot.setSectionOutlinesVisible(true);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));

        JFrame chartFrame = new JFrame("Chart");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.add(chartPanel);
        chartFrame.pack();
        chartFrame.setVisible(true);

        double investment = income - totalIncome;
        double savings = totalIncome;

        DefaultPieDataset investmentDataset = new DefaultPieDataset();
        investmentDataset.setValue("Stocks", investment * 0.04);
        investmentDataset.setValue("Mutual Funds", investment * 0.05);
        investmentDataset.setValue("SIP's", investment * 0.06);
        investmentDataset.setValue("Remaining Savings", savings);

        JFreeChart investmentChart = ChartFactory.createPieChart("Investment Chart", investmentDataset, true, true, false);
        PiePlot investmentPlot = (PiePlot) investmentChart.getPlot();
        investmentPlot.setCircular(true);
        investmentPlot.setSectionOutlinesVisible(true);

        ChartPanel investmentChartPanel = new ChartPanel(investmentChart);
        investmentChartPanel.setPreferredSize(new Dimension(800, 600));

        JFrame investmentChartFrame = new JFrame("Investment Chart");
        investmentChartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        investmentChartFrame.add(investmentChartPanel);
        investmentChartFrame.pack();
        investmentChartFrame.setVisible(true);

        displaySavingsTable(savings);
    }

    private void displaySavingsTable(double savings) {
        model = new DefaultTableModel();
        model.addColumn("Category");
        model.addColumn("Amount");

        Object[][] data = {
                {"Savings", String.valueOf(savings)},
                {"Investment (Stocks)", String.valueOf(savings * 0.04)},
                {"Investment (Mutual Funds)", String.valueOf(savings * 0.05)},
                {"Investment (SIP's)", String.valueOf(savings * 0.06)},
                {"Remaining Savings", String.valueOf(savings)}
        };

        for (Object[] row : data) {
            model.addRow(row);
        }

        table = new JTable(model);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 12));

        JFrame tableFrame = new JFrame("Savings Table");
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.add(new JScrollPane(table));
        tableFrame.pack();
        tableFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PFM();
            }
        });
    }
}