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

        try {
            Class.forName(
                    "oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, user, password);
           ArrayList<String> tax_arr = tax(con);
//           System.out.print(tax_arr);
           tax_id = Integer.valueOf(tax_arr.get(1));
           location_id = Integer.valueOf(tax_arr.get(tax_arr.size()-1));
//           tax_rate(con, tax_id);
           location(con, location_id);
//                System.out.printf("%s\n%s\n", tax_id.toString(), location_id.toString());
            System.out.println();

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

            while (rs.next()) {
                for (int column_ind = 1; column_ind <= column_count; column_ind++) {
                    col_name = metaData.getColumnName(column_ind);
                    Object object = rs.getObject(column_ind);
                    tax_info.add(col_name);
                    tax_info.add(object.toString());
//                    if (column_ind == 1) {
//                        tax_id = (Number) object;
//                        System.out.printf("%s:%s \n", col_name, tax_id.toString()); //change to stringbuilder
//                    } else if (column_ind == column_count) {
//                        location_id = (Number) object;
//                        System.out.printf("%s:%s \n", col_name, location_id.toString()); //change to stringbuilder
//                    } else {
//                        System.out.printf("%s:%s \n", col_name, object.toString()); //change to stringbuilder
//                    }
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return tax_info;
    }
    public static void tax_rate(Connection con, Number tax_id){
        String tax_query = "select * from TAX_RATE WHERE TR_TAX_ID= ?";
        String col_name = null;
        try {
            PreparedStatement stmt = con.prepareStatement(tax_query);
            stmt.setString(1, tax_id.toString());
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int column_count = metaData.getColumnCount();
            while(rs.next()){
                for(int column_ind = 1; column_ind <= column_count; column_ind++){
                    col_name = metaData.getColumnName(column_ind);
                    Object object = rs.getObject(column_ind);
                    System.out.printf("%s:%s \n", col_name, object.toString());
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void location(Connection con, Number location_id) {
        String tax_query = "select * from LOCATION WHERE LOCATION_ID= ?";
        String col_name = null;
        try {
            PreparedStatement stmt = con.prepareStatement(tax_query);
            stmt.setString(1, location_id.toString());
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int column_count = metaData.getColumnCount();
            String[] location_info = new String[column_count*2];

            while (rs.next()) {
                for (int column_ind = 1; column_ind <= column_count; column_ind++) {
                    col_name = metaData.getColumnName(column_ind);
                    Object object = rs.getObject(column_ind);
                    location_info[column_ind] = col_name + object;
                    System.out.printf("%s:%s \n", col_name, object.toString());
                }
            }
//            System.out.printf("%s", location_info[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
