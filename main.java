import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.lang.reflect.Array;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;

public class main {
    public static void main(String[] args) {
        /* Take in Tax Name - get information, and location_id, and tr_id.
        Pass location_id to location table
        Pass tr_id to tax rate table
         */
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        //<editor-fold desc="Username and Password">
        String user = "system";
        String password = "Welllightupthedark144";
        //</editor-fold>
        Number tax_id = null;
        Number location_id = null;
        ArrayList<String> tax_arr;
        ArrayList<String> location_arr;
        ArrayList<String> tax_rate_arr;

        try {
            Class.forName(
                    "oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, user, password);
            tax_arr = tax(con);
            System.out.println(tax_arr);
            tax_id = Integer.valueOf(tax_arr.get(1));
            location_id = Integer.valueOf(tax_arr.get(tax_arr.size()-1));
            tax_rate_arr = tax_rate(con, tax_id);
            location_arr = location(con, location_id);
            System.out.println(tax_rate_arr);
            System.out.println(location_arr);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<String> tax(Connection con) {
        String name = "New York Unemployment Insurance";
        String query = "select * from TAX WHERE TAX_NAME= ?";
        String col_name;
        ArrayList <String> tax_info = new ArrayList<String>();
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int column_count = metaData.getColumnCount();
            tax_info = arr_builder(rs, column_count, metaData);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return tax_info;
    }
    public static ArrayList<String> tax_rate(Connection con, Number tax_id){
        String tax_query = "select * from TAX_RATE WHERE TR_TAX_ID= ?";
        String col_name = null;
        ArrayList <String> tax_rate_info = new ArrayList<String>();
        try {
            PreparedStatement stmt = con.prepareStatement(tax_query);
            stmt.setString(1, tax_id.toString());
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int column_count = metaData.getColumnCount();
            tax_rate_info = arr_builder(rs, column_count, metaData);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return tax_rate_info;
    }
    public static ArrayList<String> location(Connection con, Number location_id) {
        String tax_query = "select * from LOCATION WHERE LOCATION_ID= ?";
        ArrayList <String> location_info = new ArrayList<String>();
        try {
            PreparedStatement stmt = con.prepareStatement(tax_query);
            stmt.setString(1, location_id.toString());
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int column_count = metaData.getColumnCount();
            location_info = arr_builder(rs, column_count, metaData);
            }
//            System.out.printf("%s", location_info[0]);
        catch (SQLException e) {
            e.printStackTrace();
        }
        return location_info;
    }
    public static ArrayList<String> arr_builder(ResultSet rs, int column_count, ResultSetMetaData metaData) {
        ArrayList<String> arr = new ArrayList<String>();
        String col_name = null;
        try{
            while (rs.next()) {
                for (int column_ind = 1; column_ind <= column_count; column_ind++) {
                    col_name = metaData.getColumnName(column_ind);
                    Object object = rs.getObject(column_ind);
                    arr.add(col_name);
                    arr.add(object.toString());
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return arr;
    }
}
