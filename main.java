import com.google.gson.Gson;

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
        Map<String, String> tax_map;
        Map<String, String> location_map;
        Map<String, String> tax_rate_map;

        //

        try {
            Class.forName(
                    "oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(url, user, password);
            tax_map = tax(con);
//            System.out.println(tax_map.get("TAX_ID"));
//            String test = tax_map.get("TAX_ID");
//            System.out.println(Integer.valueOf(tax_map.get("TAX_ID")));
            tax_id = Integer.valueOf(tax_map.get("TAX_ID"));
//            System.out.println(tax_id);
            location_id = Integer.valueOf(tax_map.get("TAX_LOCATION_ID"));
//            System.out.println(location_id);
            tax_rate_map = tax_rate(con, tax_id);
            location_map = location(con, location_id);
//            System.out.println(tax_map);
//            System.out.println(tax_rate_map);
//            System.out.println(location_map);
//
            toJSONWithGSON(tax_map, tax_rate_map, location_map);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> tax(Connection con) {
        String name = "New York Unemployment Insurance";
        String tax_query = "select * from TAX WHERE TAX_NAME= ?";
        Map<String, String>tax_info = new LinkedHashMap<>();
        ArrayList<Object> query_results;
        query_results= query_builder(con, tax_query, name);
        tax_info = map_builder((ResultSet) query_results.get(0), (int) query_results.get(1),
                (ResultSetMetaData) query_results.get(2));
        return tax_info;
    }
    public static Map<String, String> tax_rate(Connection con, Number tax_id){
        String tax_rate_query = "select * from TAX_RATE WHERE TR_TAX_ID= ?";
        Map <String, String> tax_rate_info;
        ArrayList<Object> query_results;
        query_results= query_builder(con, tax_rate_query, tax_id.toString());
        tax_rate_info = map_builder((ResultSet) query_results.get(0), (int) query_results.get(1),
                (ResultSetMetaData) query_results.get(2));
        return tax_rate_info;
    }
    public static Map<String, String> location(Connection con, Number location_id) {
        String location_query = "select * from LOCATION WHERE LOCATION_ID= ?";
        Map <String, String> location_info;
        ArrayList<Object> query_results;
        query_results= query_builder(con, location_query, location_id.toString());
        location_info = map_builder((ResultSet) query_results.get(0), (int) query_results.get(1),
                (ResultSetMetaData) query_results.get(2));
//            System.out.printf("%s", location_info[0]);

        return location_info;
    }
    public static Map<String, String> map_builder(ResultSet rs, int column_count, ResultSetMetaData metaData) {
        //Change from ArrayList to LinkedHashMap?
        Map<String, String> map = new LinkedHashMap<>();
        String col_name = null;
        try{
            while (rs.next()) {
                for (int column_ind = 1; column_ind <= column_count; column_ind++) {
                    col_name = metaData.getColumnName(column_ind);
                    Object object = rs.getObject(column_ind);
                    map.put(col_name, object.toString());
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return map;
    }
    public static ArrayList<Object>query_builder(Connection con, String query, String name){
        ArrayList<Object> query_info = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int column_count = metaData.getColumnCount();
            query_info.add(rs);
            query_info.add(column_count);
            query_info.add(metaData);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return query_info;
    }
    public static void toJSONWithGSON(Map<String, String> tax_map, Map<String, String> tax_rate_map,
                                      Map<String, String> location_map){
//        String tax_json = new Gson().toJson(tax_map);
//        String tax_rate_json = new Gson().toJson(tax_rate_map);
//        String location_json = new Gson().toJson(location_map);
        String tax_json, tax_rate_json, location_json;
        tax_map.put("location", location_map.toString());
        tax_json = new Gson().toJson(tax_map);
        System.out.println(tax_json);
//        System.out.println(tax_rate_json);
//        System.out.println(location_json);
    }
}
