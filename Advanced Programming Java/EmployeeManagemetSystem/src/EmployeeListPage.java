import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EmployeeListPage extends JPanel {
    private JFrame parentFrame;
    private JTable table;
    private DefaultTableModel model;

    public EmployeeListPage(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        // Create a table model with column names
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] { "ID", "Name", "Position", "Salary", "Actions" });

        // Fetch data from the database and populate the table
        fetchData();

        // Create the table using the model
        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Make only the "Actions" column editable
            }

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 4) {
                    return new ButtonRenderer();
                }
                return super.getCellRenderer(row, column);
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if (column == 4) {
                    return new ButtonEditor(new JCheckBox());
                }
                return super.getCellEditor(row, column);
            }
        };
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Add Employee");
        JButton editButton = new JButton("Edit Employee");
        JButton deleteButton = new JButton("Delete Employee");
        JButton dashboardButton = new JButton("Back to Dashboard");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(dashboardButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.getContentPane().removeAll();
                parentFrame.add(new AddEditEmployeePage(parentFrame, null));
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) model.getValueAt(selectedRow, 0);
                    parentFrame.getContentPane().removeAll();
                    parentFrame.add(new AddEditEmployeePage(parentFrame, id));
                    parentFrame.revalidate();
                    parentFrame.repaint();
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Please select an employee to edit.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) model.getValueAt(selectedRow, 0);
                    deleteEmployee(id);
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Please select an employee to delete.");
                }
            }
        });

        dashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.getContentPane().removeAll();
                parentFrame.add(new DashboardPage(parentFrame));
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });

        // Add search functionality
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText().trim();
                if (!searchTerm.isEmpty()) {
                    searchEmployees(searchTerm);
                } else {
                    fetchData(); // If search term is empty, fetch all employees
                }
            }
        });
        add(searchPanel, BorderLayout.NORTH);

        // Enable sorting on table headers
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = table.columnAtPoint(e.getPoint());
                sortTable(col);
            }
        });

        // Adjust column widths to fit content
        adjustColumnWidths();
    }

    private void fetchData() {
        model.setRowCount(0); // Clear existing rows
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");

            // Populate the table model with data
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String position = resultSet.getString("position");
                double salary = resultSet.getDouble("salary");
                JButton detailButton = new JButton("Details");
                detailButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        parentFrame.getContentPane().removeAll();
                        parentFrame.add(new EmployeeDetailsPage(parentFrame, id));
                        parentFrame.revalidate();
                        parentFrame.repaint();
                    }
                });
                model.addRow(new Object[] { id, name, position, salary, detailButton });
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteEmployee(int id) {
        try {
            Connection connection = getConnection();
            String query = "DELETE FROM employees WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(parentFrame, "Employee deleted successfully.");
                fetchData(); // Refresh the table after deletion
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Failed to delete employee.");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchEmployees(String searchTerm) {
        model.setRowCount(0); // Clear existing rows
        try {
            Connection connection = getConnection();
            String query = "SELECT * FROM employees WHERE name LIKE ? OR position LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            String searchPattern = "%" + searchTerm + "%";
            statement.setString(1, searchPattern);
            statement.setString(2, searchPattern);
            ResultSet resultSet = statement.executeQuery();

            // Populate the table model with data
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String position = resultSet.getString("position");
                double salary = resultSet.getDouble("salary");
                JButton detailButton = new JButton("Details");
                detailButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        parentFrame.getContentPane().removeAll();
                        parentFrame.add(new EmployeeDetailsPage(parentFrame, id));
                        parentFrame.revalidate();
                        parentFrame.repaint();
                    }
                });
                model.addRow(new Object[] { id, name, position, salary, detailButton });
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sortTable(int columnIndex) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.toggleSortOrder(columnIndex);
    }

    private void adjustColumnWidths() {
        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }

            tableColumn.setPreferredWidth(preferredWidth);
        }
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/company";
        String user = "root";
        String password = "@Anti7452"; // replace with your actual database password
        return DriverManager.getConnection(url, user, password);
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        if (value instanceof JButton) {
            JButton button = (JButton) value;
            return button;
        }
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof JButton) {
            button = (JButton) value;
        } else {
            button.setText(value.toString());
        }
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            // Perform action when button is clicked
            System.out.println(button.getText() + ": Clicked!");
        }
        isPushed = false;
        return button;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
