import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MedicalStoreManagement extends JFrame {

    // Database
    Connection con;

    // Login
    String adminUser = "admin";
    String adminPass = "123";

    // tables
    JTable customerTable, medicineTable, salesTable, userTable;

    DefaultTableModel customerModel;
    DefaultTableModel medicineModel;
    DefaultTableModel salesModel;
    DefaultTableModel userModel;

    JTabbedPane tabs;

    // Database Connection
    void connectDB() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/medical",
                    "root",
                    "majid@786"
            );

            JOptionPane.showMessageDialog(this,
                    "Database Connected Successfully");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    "Database Connection Failed\n" + e);
        }
    }

    // Login Method
    boolean login() {

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(
                null,
                message,
                "Admin Login",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals(adminUser)
                    && password.equals(adminPass)) {

                JOptionPane.showMessageDialog(null,
                        "Login Successful");

                return true;

            } else {

                JOptionPane.showMessageDialog(null,
                        "Invalid Username or Password");

                return false;
            }
        }

        return false;
    }

    // Constructor
    MedicalStoreManagement() {

        connectDB();

        setTitle("Medical Store Management System");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tabs = new JTabbedPane();

        customerPanel();
        medicinePanel();
        salesPanel();
        usersPanel();

        add(tabs);

        setVisible(true);
    }

    // Customers Panels
    void customerPanel() {

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("CUSTOMERS", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        panel.add(title, BorderLayout.NORTH);

        customerModel = new DefaultTableModel();

        customerModel.addColumn("ID");
        customerModel.addColumn("Name");
        customerModel.addColumn("Phone");
        customerModel.addColumn("Address");

        customerTable = new JTable(customerModel);

        loadCustomers();

        panel.add(new JScrollPane(customerTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();

        JButton add = new JButton("Add");
        JButton delete = new JButton("Delete");
        JButton refresh = new JButton("Refresh");

        btnPanel.add(add);
        btnPanel.add(delete);
        btnPanel.add(refresh);

        panel.add(btnPanel, BorderLayout.SOUTH);

        // Add Customer
        add.addActionListener(e -> {

            try {

                String name = JOptionPane.showInputDialog("Customer Name");
                String phone = JOptionPane.showInputDialog("Phone");
                String address = JOptionPane.showInputDialog("Address");

                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO customers(customer_name,phone,address) VALUES(?,?,?)"
                );

                ps.setString(1, name);
                ps.setString(2, phone);
                ps.setString(3, address);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Customer Added Successfully");

                loadCustomers();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this, ex);
            }
        });

        // Delete Customer
        delete.addActionListener(e -> {

            try {

                int row = customerTable.getSelectedRow();

                if (row == -1) {

                    JOptionPane.showMessageDialog(this,
                            "Select Customer First");
                    return;
                }

                String id = customerModel.getValueAt(row, 0).toString();

                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM customers WHERE customer_id=?"
                );

                ps.setInt(1, Integer.parseInt(id));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Customer Deleted");

                loadCustomers();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this, ex);
            }
        });

        refresh.addActionListener(e -> loadCustomers());

        tabs.add("Customers", panel);
    }

    // Load Customers
    void loadCustomers() {

        try {

            customerModel.setRowCount(0);

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                    "SELECT * FROM customers"
            );

            while (rs.next()) {

                customerModel.addRow(new Object[]{
                        rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("phone"),
                        rs.getString("address")
                });
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, e);
        }
    }

    // Medicines Panels
    void medicinePanel() {

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("MEDICINES", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        panel.add(title, BorderLayout.NORTH);

        medicineModel = new DefaultTableModel();

        medicineModel.addColumn("ID");
        medicineModel.addColumn("Name");
        medicineModel.addColumn("Company");
        medicineModel.addColumn("Category");
        medicineModel.addColumn("Price");
        medicineModel.addColumn("Quantity");
        medicineModel.addColumn("MFG Date");
        medicineModel.addColumn("EXP Date");

        medicineTable = new JTable(medicineModel);

        loadMedicines();

        panel.add(new JScrollPane(medicineTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();

        JButton add = new JButton("Add");
        JButton delete = new JButton("Delete");
        JButton refresh = new JButton("Refresh");

        btnPanel.add(add);
        btnPanel.add(delete);
        btnPanel.add(refresh);

        panel.add(btnPanel, BorderLayout.SOUTH);

        // ADD MEDICINE
        add.addActionListener(e -> {

            try {

                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO medicines(medicine_name,company,category,price,quantity,manufacture_date,expiry_date) VALUES(?,?,?,?,?,?,?)"
                );

                ps.setString(1,
                        JOptionPane.showInputDialog("Medicine Name"));

                ps.setString(2,
                        JOptionPane.showInputDialog("Company"));

                ps.setString(3,
                        JOptionPane.showInputDialog("Category"));

                ps.setDouble(4,
                        Double.parseDouble(
                                JOptionPane.showInputDialog("Price")));

                ps.setInt(5,
                        Integer.parseInt(
                                JOptionPane.showInputDialog("Quantity")));

                ps.setString(6,
                        JOptionPane.showInputDialog("Manufacture Date YYYY-MM-DD"));

                ps.setString(7,
                        JOptionPane.showInputDialog("Expiry Date YYYY-MM-DD"));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Medicine Added Successfully");

                loadMedicines();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this, ex);
            }
        });

        // Delete Medicine
        delete.addActionListener(e -> {

            try {

                int row = medicineTable.getSelectedRow();

                if (row == -1) return;

                String id = medicineModel.getValueAt(row, 0).toString();

                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM medicines WHERE medicine_id=?"
                );

                ps.setInt(1, Integer.parseInt(id));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Medicine Deleted");

                loadMedicines();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this, ex);
            }
        });

        refresh.addActionListener(e -> loadMedicines());

        tabs.add("Medicines", panel);
    }

    // Load Medicine
    void loadMedicines() {

        try {

            medicineModel.setRowCount(0);

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                    "SELECT * FROM medicines"
            );

            while (rs.next()) {

                medicineModel.addRow(new Object[]{
                        rs.getInt("medicine_id"),
                        rs.getString("medicine_name"),
                        rs.getString("company"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("manufacture_date"),
                        rs.getString("expiry_date")
                });
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, e);
        }
    }

    // Sales panel
    void salesPanel() {

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("SALES", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        panel.add(title, BorderLayout.NORTH);

        salesModel = new DefaultTableModel();

        salesModel.addColumn("Sale ID");
        salesModel.addColumn("Customer ID");
        salesModel.addColumn("Medicine ID");
        salesModel.addColumn("Quantity");
        salesModel.addColumn("Total Price");
        salesModel.addColumn("Sale Date");

        salesTable = new JTable(salesModel);

        loadSales();

        panel.add(new JScrollPane(salesTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();

        JButton add = new JButton("Add");
        JButton delete = new JButton("Delete");
        JButton refresh = new JButton("Refresh");

        btnPanel.add(add);
        btnPanel.add(delete);
        btnPanel.add(refresh);

        panel.add(btnPanel, BorderLayout.SOUTH);

        // Add Sale
        add.addActionListener(e -> {

            try {

                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO sales(customer_id,medicine_id,quantity,total_price,sale_date) VALUES(?,?,?,?,?)"
                );

                ps.setInt(1,
                        Integer.parseInt(
                                JOptionPane.showInputDialog("Customer ID")));

                ps.setInt(2,
                        Integer.parseInt(
                                JOptionPane.showInputDialog("Medicine ID")));

                ps.setInt(3,
                        Integer.parseInt(
                                JOptionPane.showInputDialog("Quantity")));

                ps.setDouble(4,
                        Double.parseDouble(
                                JOptionPane.showInputDialog("Total Price")));

                ps.setString(5,
                        JOptionPane.showInputDialog("Sale Date YYYY-MM-DD"));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Sale Added Successfully");

                loadSales();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this, ex);
            }
        });

        // Delete Sales
        delete.addActionListener(e -> {

            try {

                int row = salesTable.getSelectedRow();

                if (row == -1) return;

                String id = salesModel.getValueAt(row, 0).toString();

                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM sales WHERE sale_id=?"
                );

                ps.setInt(1, Integer.parseInt(id));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Sale Deleted");

                loadSales();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this, ex);
            }
        });

        refresh.addActionListener(e -> loadSales());

        tabs.add("Sales", panel);
    }

    //Load Sales
    void loadSales() {

        try {

            salesModel.setRowCount(0);

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                    "SELECT * FROM sales"
            );

            while (rs.next()) {

                salesModel.addRow(new Object[]{
                        rs.getInt("sale_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("medicine_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getString("sale_date")
                });
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, e);
        }
    }

    // Users Panel
    void usersPanel() {

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("USERS", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        panel.add(title, BorderLayout.NORTH);

        userModel = new DefaultTableModel();

        userModel.addColumn("User ID");
        userModel.addColumn("Username");
        userModel.addColumn("Password");
        userModel.addColumn("Role");

        userTable = new JTable(userModel);

        loadUsers();

        panel.add(new JScrollPane(userTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();

        JButton add = new JButton("Add");
        JButton delete = new JButton("Delete");
        JButton refresh = new JButton("Refresh");

        btnPanel.add(add);
        btnPanel.add(delete);
        btnPanel.add(refresh);

        panel.add(btnPanel, BorderLayout.SOUTH);

        // ADD USER
        add.addActionListener(e -> {

            try {

                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO users(username,password,role) VALUES(?,?,?)"
                );

                ps.setString(1,
                        JOptionPane.showInputDialog("Username"));

                ps.setString(2,
                        JOptionPane.showInputDialog("Password"));

                ps.setString(3,
                        JOptionPane.showInputDialog("Role"));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "User Added Successfully");

                loadUsers();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this, ex);
            }
        });

        // DELETE USER
        delete.addActionListener(e -> {

            try {

                int row = userTable.getSelectedRow();

                if (row == -1) return;

                String id = userModel.getValueAt(row, 0).toString();

                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM users WHERE user_id=?"
                );

                ps.setInt(1, Integer.parseInt(id));

                ps.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "User Deleted");

                loadUsers();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this, ex);
            }
        });

        refresh.addActionListener(e -> loadUsers());

        tabs.add("Users", panel);
    }

    // Load Users
    void loadUsers() {

        try {

            userModel.setRowCount(0);

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                    "SELECT * FROM users"
            );

            while (rs.next()) {

                userModel.addRow(new Object[]{
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                });
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, e);
        }
    }

    //  Main
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            MedicalStoreManagement app =
                    new MedicalStoreManagement();

            boolean ok = app.login();

            if (!ok) {

                System.exit(0);
            }
        });
    }
}