

//public class Main {
//    public static void main(String[] args) {
//        String url = "jdbc:mysql://localhost:3306/your_database";
//        String username = "your_username";
//        String password = "your_password";
//
//        DatabaseManager dbManager = new DatabaseManager(url, username, password);
//
//        String query = "SELECT * FROM your_table";
//        ResultSet resultSet = dbManager.executeQuery(query);
//
//        try {
//            while (resultSet.next()) {
//                // Access data using resultSet.getXXX() methods
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//
//                // Process the data as needed
//                System.out.println("ID: " + id + ", Name: " + name);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        dbManager.closeConnection();
//    }
//}
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.lang.*;

public class main {
    public static void main(String[] args) {
        /*  Input: None
            Output: None
            Creates connection with database and calls methods
            Gets linked hashmap for tax table, extracts values for keys location ID and tax rate ID
            Passes those values to methods to create location and tax rate maps
            Passes created maps to JSON converter
        */
//        DatabaseManager dbManager = new DatabaseManager(url, username, password);
        String url = "jdbc:oracle:thin:@localhost:1521:xe"; //Database information
        //<editor-fold desc="Username and Password">
        String user = "system";
        String password = "";
        //</editor-fold>
        try {
            Class.forName(
                    "oracle.jdbc.driver.OracleDriver"); //Driver information.
            // Here for testing purposes. Move to SQL query class
            Connection con = DriverManager.getConnection(url, user, password);//connects to database
            Map<String, Object>tax_info;
            ArrayList<Object> query_results;
            System.out.println("Here");
            Scanner myObj = new Scanner(System.in);
            String query = myObj.nextLine();
            query_results= sqlQuery.query_builder(con, query);
            System.out.println("Here 2");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}