package backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;

/* TODO: Add public, protected, and private for variables and methods. Refactor to correctly process multiple tables and more advanced SQL queries.
*/
public class laura_main {
    public static void main(String[] args) {
        /*  Input: None
            Output: None
            Creates connection with database and calls methods
            Gets linked hashmap for tax table, extracts values for keys location ID and tax rate ID
            Passes those values to methods to create location and tax rate maps
            Passes created maps to JSON converter
        */
        String url = "jdbc:oracle:thin:@localhost:1521:xe"; //Database information
        //<editor-fold desc="Username and Password">
        String user = "system";
        String password = "";
        //</editor-fold>
        Number tax_id, location_id;
        Map<String, Object> tax_map, location_map, tax_rate_map;

        try {
            Class.forName(
                    "oracle.jdbc.driver.OracleDriver"); //Driver information.
                                                                 // Here for testing purposes. Move to SQL query class
            Connection con = DriverManager.getConnection(url, user, password);//connects to database

//            tax_map = sqlQuery.tax(con);
//            tax_id = Integer.valueOf((String) tax_map.get("TAX_ID")); //gets tax_ID from tax map
//            location_id = Integer.valueOf((String) tax_map.get("TAX_LOCATION_ID")); //gets location_id from location map
//            tax_rate_map = sqlQuery.tax_rate(con, tax_id);
//            location_map = sqlQuery.location(con, location_id);
//            toJSON.toJSONWithGSON(tax_map, tax_rate_map, location_map);
            Map<String, Object>tax_info;
            ArrayList<Object> query_results;
            Scanner myObj = new Scanner(System.in);
            String query = myObj.nextLine();
            query_results= sqlQuery.query_builder(con, query);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
