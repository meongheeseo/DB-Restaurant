import java.sql.*;
import java.util.Scanner;

public class Q3Interface {
    // Username + Password
    private static final String username = "cs421g07";
    private static final String password = "sunlightchicken9115";

    public static void main(String[] args) throws SQLException {
        // Table name as command line arg
        int option = 7;
        Scanner scan;
        String tableName = "";

        if (args.length > 0)
            tableName += args[0];

        try {
            DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
        } catch (SQLException e) {
            System.out.println("Class not found");
            e.printStackTrace();
            return;
        }

        System.out.println("Driver registered.");
        String url = "jdbc:db2://db2:50000/cs421";
        Connection con = DriverManager.getConnection(url, username, password);
        Statement statement = con.createStatement();
        System.out.println("Connection Established!\n");

        while (true) {
            System.out.println("Options:");
            System.out.println("\t1) Add a new customer.");
            System.out.println("\t2) Add a new staff.");
            System.out.println("\t3) Add a new menu item.");
            System.out
                    .println("\t4) For Order - check whether the order has been delivered or not.");
            System.out
                    .println("\t5) For Existing Customer - moves to a new place.");
            System.out
                    .println("\t6) For Customer - place a new order using online.");
            System.out.println("\t7) Quit\n");

            scan = new Scanner(System.in);

            System.out.print("Please choose an option number: ");
            option = scan.nextInt();

            if (option == 1) {
                addNewCustomer(con, statement);
            } else if (option == 2) {
                addNewStaff(con, statement);
            } else if (option == 3) {
                addNewMenu(con, statement);
            } else if (option == 4) {
                checkStatus(con, statement);
            } else if (option == 5) {
                moveCustomer(con, statement);
            } else if (option == 6) {
                placeOnlineOrder(con, statement);
            } else if (option == 7) {
                System.out.println("Quitting");
                break;
            } else {
                System.out.println("Invalid choice.");
            }

        }

        // Close connection
        statement.close();
        con.close();
    }

    public static void addNewCustomer(Connection con, Statement statement)
            throws SQLException {
        int cid = 0;
        Scanner scan = new Scanner(System.in);
        String firstName = "";
        String lastName = "";
        String address = "";
        String phone = "";

        System.out.println("\nEnter first name: ");
        firstName = scan.nextLine();
        System.out.println("Enter last name: ");
        lastName = scan.nextLine();
        System.out.println("Enter address: ");
        address = scan.nextLine();
        System.out
                .println("Enter phone number in following format 000-000-0000: ");
        phone = scan.nextLine();

        try {
            // Find the CID that we can use for the new customer.
            String findCid = "SELECT MAX(cid) FROM Customers";
            java.sql.ResultSet rs = statement.executeQuery(findCid);

            while (rs.next()) {
                cid = rs.getInt(1);
            }

            cid++;
            String newCustomer = "INSERT INTO Customers VALUES(" + cid + ", \'"
                    + firstName + "\', \'" + lastName + "\', \'" + address
                    + "\', \'" + phone + "\')";
            statement.executeUpdate(newCustomer);

            System.out.println("New Customer Added.");

        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlState = e.getSQLState();

            System.out.println("Code: " + sqlCode + " State: " + sqlState);
            System.out.println("ERROR: Failed to add new Customer.");
            return;
        }
    }

    public static void addNewStaff(Connection con, Statement statement)
            throws SQLException {
        Scanner scan = new Scanner(System.in);
        String firstName = "";
        String lastName = "";
        String phone = "";
        String position = "";
        int sid = 0;
        int salary = 0;

        System.out.println("\nEnter first name: ");
        firstName = scan.nextLine();
        System.out.println("Enter last name: ");
        lastName = scan.nextLine();
        System.out
                .println("Enter phone number in following format 000-000-0000: ");
        phone = scan.nextLine();
        System.out
                .println("Enter position (waiter/waitress, cook, delivery etc.): ");
        position = scan.nextLine();
        System.out.println("Enter monthly salary: ");
        salary = scan.nextInt();

        try {
            String findSid = "SELECT MAX(sid) FROM Staff";
            java.sql.ResultSet rs = statement.executeQuery(findSid);

            while (rs.next()) {
                sid = rs.getInt(1);
            }

            sid++;
            String newStaff = "INSERT INTO Staff VALUES(" + sid + ", \'"
                    + phone + "\', " + salary + ", \'" + firstName + "\', \'"
                    + lastName + "\')";
            statement.executeUpdate(newStaff);

            if (position.equals("delivery")) {
                newStaff = "INSERT INTO DeliveryStaff VALUES(" + sid + ")";
                statement.executeUpdate(newStaff);
                System.out
                        .println("New Staff Added into Staff and DeliveryStaff.");
            } else {
                newStaff = "INSERT INTO GeneralStaff VALUES (" + sid + ", \'"
                        + position + "\')";
                statement.executeUpdate(newStaff);
                System.out
                        .println("New Staff Added into Staff and GeneralStaff.");
            }
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlState = e.getSQLState();

            System.out.println("Code: " + sqlCode + " State: " + sqlState);
            System.out.println("ERROR: Failed to add new Customer.");
            return;
        }
    }

    public static void addNewMenu(Connection con, Statement statement)
            throws SQLException {
        Scanner scan = new Scanner(System.in);
        String name = "";
        String description = "";
        double price = 0.00;
        String type = "";
        String category = "";
        int mid = 0;

        System.out.println("\nEnter name: ");
        name = scan.nextLine();
        System.out.println("Write description: ");
        description = scan.nextLine();
        System.out.println("Enter type (Food or Beverage): ");
        type = scan.nextLine();
        System.out.println("Enter category: ");
        category = scan.nextLine();
        System.out.println("Enter price: ");
        price = scan.nextDouble();

        try {
            String findSid = "SELECT MAX(mid) FROM Menu";
            java.sql.ResultSet rs = statement.executeQuery(findSid);

            while (rs.next()) {
                mid = rs.getInt(1);
            }

            mid++;
            String newMenu = "INSERT INTO Menu VALUES(" + mid + ", \'" + name
                    + "\', \'" + description + "\', " + price + ")";
            statement.executeUpdate(newMenu);

            if (type.equals("Food") || type.equals("food")) {
                newMenu = "INSERT INTO Food VALUES(" + mid + ", \'" + category
                        + "\')";
                statement.executeUpdate(newMenu);
                System.out.println("New Menu Added into Menu and Food.");
            } else {
                newMenu = "INSERT INTO Beverage VALUES(" + mid + ", \'"
                        + category + "\')";
                statement.executeUpdate(newMenu);
                System.out.println("New Menu Added into Menu and Beverage.");
            }
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlState = e.getSQLState();

            System.out.println("Code: " + sqlCode + " State: " + sqlState);
            System.out.println("ERROR: Failed to add new Customer.");
            return;
        }
    }

    public static void checkStatus(Connection con, Statement statement)
            throws SQLException {
        Scanner scan = new Scanner(System.in);
        int cid = 0;
        int oid = 0;
        String checkDelivered = "";
        String firstName = "";
        String lastName = "";
        String phone = "";
        String delivered = "";

        System.out.println("\nEnter customer's first name: ");
        firstName = scan.nextLine();
        System.out.println("Enter customer's last name: ");
        lastName = scan.nextLine();
        System.out
                .println("Enter customer's phone number in the following format 000-000-0000: ");
        phone = scan.nextLine();

        try {
            String findCid = "SELECT cid FROM Customers WHERE firstName = \'"
                    + firstName + "\' AND lastName = \'" + lastName
                    + "\' AND phone = \'" + phone + "\'";

            java.sql.ResultSet rs = statement.executeQuery(findCid);

            while (rs.next()) {
                cid = rs.getInt(1);
            }

            String findSid = "SELECT MAX(oid) FROM Done_by WHERE cid = " + cid;
            rs = statement.executeQuery(findSid);

            while (rs.next())
                oid = rs.getInt(1);

            checkDelivered = "SELECT delivered FROM Fulfills WHERE oid = "
                    + oid;
            rs = statement.executeQuery(checkDelivered);

            while (rs.next()) {
                delivered = rs.getString(1);
            }

            if (delivered.equals("N") || delivered.equals("N")) {
                System.out.println("The order ID: " + oid + ", ordered by "
                        + firstName + " " + lastName
                        + " has not been delivered yet.");
            } else if (delivered.equals("Y") || delivered.equals("y")) {
                System.out.println("The order ID: " + oid + ", ordered by "
                        + firstName + " " + lastName + " has been delivered.");
            } else {
                System.out
                        .println("The status of the order is unknown at this time.");
            }
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlState = e.getSQLState();

            System.out.println("Code: " + sqlCode + " State: " + sqlState);
            System.out.println("ERROR: Failed to check order status.");
            return;
        }
    }

    public static void moveCustomer(Connection con, Statement statement)
            throws SQLException {
        Scanner scan = new Scanner(System.in);
        int cid = 0;
        String firstName = "";
        String lastName = "";
        String phone = "";
        String newAddress = "";
        String newPostalCode = "";
        String check = "NO";

        // System.out.println("\nEnter customer ID: ");
        // cid = scan.nextInt();
        // scan.next();
        System.out.println("\nEnter customer's first name: ");
        firstName = scan.nextLine();
        System.out.println("Enter customer's last name: ");
        lastName = scan.nextLine();
        System.out
                .println("Enter customer's phone number in the following format 000-000-0000: ");
        phone = scan.nextLine();
        System.out.println("Enter new address: ");
        newAddress = scan.nextLine();

        while (true) {
            System.out.println("Enter new postal code: ");
            newPostalCode = scan.nextLine();
            if (newPostalCode.length() != 6) {
                System.out.println("Please enter a valid postal code.");
                continue;
            }
            break;
        }

        try {
            // String checkArea =
            // "SELECT COUNT(*) FROM AreaCode WHERE postalCode = \'"
            // + newPostalCode + "\'";
            // java.sql.ResultSet rs = statement.executeQuery(checkArea);
            // int areaCount = rs.getInt(1);
            // // if postal code does not exist
            // if (areaCount == 0) {
            // String addArea = "INSERT INTO AreaCode VALUES(\'"
            // + newPostalCode + "\')";
            // statement.executeUpdate(addArea);
            // }
            // String updateArea = "UPDATE Customers SET address = \'"
            // + newAddress + "\' WHERE cid = " + cid;
            // statement.executeUpdate(updateArea);

            // String updateLivesIn = "UPDATE Lives_in SET postalCode = \'"
            // + newPostalCode + "\' WHERE cid = " + cid;
            // statement.executeUpdate(updateLivesIn);

            // System.out
            // .println("The customer address has successfully been updated.");
            String findCid = "SELECT cid FROM Customers WHERE firstName = \'"
                    + firstName + "\' AND lastName = \'" + lastName
                    + "\' AND phone = \'" + phone + "\'";

            java.sql.ResultSet rs = statement.executeQuery(findCid);

            while (rs.next()) {
                cid = rs.getInt(1);
            }

            String updateCustomers = "UPDATE Customers SET address=\'"
                    + newAddress + "\' WHERE cid=" + cid;
            statement.executeUpdate(updateCustomers);

            String checkAreaCode = "SELECT * FROM AreaCode WHERE postalcode=\'"
                    + newPostalCode + "\'";
            String updateLivesin = "UPDATE Lives_in SET postalcode=\'"
                    + newPostalCode + "\' WHERE cid=" + cid;

            try {
                rs = statement.executeQuery(check);

                statement.executeUpdate(updateLivesin);

                System.out
                        .println("The new areacode already exists, so address for customer "
                                + firstName
                                + " "
                                + lastName
                                + ", with ID = "
                                + cid + ", has been successfully updated.");
            } catch (SQLException e) {
                String insertAreaCode = "INSERT INTO AreaCode VALUES(\'"
                        + newPostalCode + "\')";

                statement.executeUpdate(insertAreaCode);
                statement.executeUpdate(updateLivesin);

                System.out
                        .println("The new areacode does not exists, so address for customer "
                                + firstName
                                + " "
                                + lastName
                                + ", with ID = "
                                + cid
                                + ", has been successfully updated and new areacode has been successfully created.");
            }

        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlState = e.getSQLState();

            System.out.println("Code: " + sqlCode + " State: " + sqlState);
            System.out.println("ERROR: Failed to update customer address.");
            return;
        }
    }

    public static void placeOnlineOrder(Connection con, Statement statement)
            throws SQLException {
        Scanner scan = new Scanner(System.in);
        int cid = 0;
        int oid = 0;
        int mid = 0;
        String firstName = "";
        String lastName = "";
        String phone = "";
        String pCode = "";
        String menuItem = "";
        boolean ordered = false;

        System.out.println("\nEnter customer's first name: ");
        firstName = scan.nextLine();
        System.out.println("Enter customer's last name: ");
        lastName = scan.nextLine();
        System.out
                .println("Enter customer's phone number in the following format 000-000-0000: ");
        phone = scan.nextLine();

        try {
            String findCid = "SELECT cid FROM Customers WHERE firstName = \'"
                    + firstName + "\' AND lastName = \'" + lastName
                    + "\' AND phone = \'" + phone + "\'";

            java.sql.ResultSet rs = statement.executeQuery(findCid);

            while (rs.next()) {
                cid = rs.getInt(1);
            }

            String findOid = "SELECT MAX(oid) FROM Order";
            rs = statement.executeQuery(findOid);

            while (rs.next()) {
                oid = rs.getInt(1);
            }
            oid++;

            String newOrder = "INSERT INTO Order VALUES(" + oid
                    + ", \'online\')";
            statement.executeUpdate(newOrder);

            String doneBy = "INSERT INTO Done_by VALUES(" + oid + ", " + cid
                    + ")";
            statement.executeUpdate(doneBy);

            String findPcode = "SELECT postalCode FROM Lives_in WHERE cid = "
                    + cid;
            rs = statement.executeQuery(findPcode);

            while (rs.next()) {
                pCode = rs.getString(1);
            }

            String comesFrom = "INSERT INTO Comes_from VALUES(" + oid + ", \'"
                    + pCode + "\')";
            statement.executeUpdate(comesFrom);

            while (true) {
                System.out
                        .println("Enter mid of the menu you would like to order (if done type 0): ");
                mid = scan.nextInt();

                if (mid == 0) {
                    break;
                }

                String newMenu = "INSERT INTO Used_to VALUES(" + oid + ", "
                        + mid + ")";
                statement.executeUpdate(newMenu);
            }

            System.out.println("A new order has been started.");
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlState = e.getSQLState();

            System.out.println("Code: " + sqlCode + " State: " + sqlState);
            System.out.println("ERROR: Failed to add new order.");
            return;
        }

        // while (true) {
        // System.out.println("Enter menu item, or \"exit\" to exit: ");
        // menuItem = scan.nextLine();
        // if (menuItem.equals("exit")) {
        // if (ordered == false) {
        // System.out
        // .println("Please add at least 1 item to the order.");
        // continue;
        // } else
        // break;
        // }
        // try {
        // String findMid = "SELECT mid FROM Menu WHERE name = "
        // + menuItem;
        // java.sql.ResultSet rs = statement.executeQuery(findMid);
        // mid = rs.getInt(1);
        // String newItem = "INSERT INTO Used_to VALUES(" + oid + ", "
        // + mid + ")";
        // statement.executeUpdate(newItem);
        // System.out
        // .println("The menu item has been added to the order.");
        // ordered = true;
        // } catch (SQLException e) {
        // int sqlCode = e.getErrorCode();
        // String sqlState = e.getSQLState();

        // System.out.println("Code: " + sqlCode + " State: " + sqlState);
        // System.out.println("ERROR: Failed to add menu item to order.");
        // }
        // }
    }
}
