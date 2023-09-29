import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;

/* TODO: Condense tax, tax_rate, and location into 1 function? Work with Gabe.
*/
public class main {
    public static void main(String[] args) {
        /*
        Creates connection with database and calls methods
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

            /*
            Gets linked hashmap for tax table, extracts values for keys location ID and tax rate ID
            Passes those values to methods to create location and tax rate maps
            Passes created maps to JSON converter
            */
            tax_map = tax(con);
            tax_id = Integer.valueOf((String) tax_map.get("TAX_ID"));
            location_id = Integer.valueOf((String) tax_map.get("TAX_LOCATION_ID"));
            tax_rate_map = tax_rate(con, tax_id);
            location_map = location(con, location_id);
            toJSONWithGSON(tax_map, tax_rate_map, location_map);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> tax(Connection con) {
        /*
        Test SQL query
        Passes query to builder, and gets results set, column number, and results set metaData back
        Passes results set, column number, and results set metaData to map builder, and returns map
         */
        String name = "California Income Tax";
        String tax_query = "select * from TAX WHERE TAX_NAME= ?";
        Map<String, Object>tax_info;
        ArrayList<Object> query_results;
        query_results= query_builder(con, tax_query, name);
        tax_info = map_builder((ResultSet) query_results.get(0), (int) query_results.get(1),
                (ResultSetMetaData) query_results.get(2));
        return tax_info;
    }
    public static Map<String, Object> tax_rate(Connection con, Number tax_id){
        /*
        Test tax_rate SQL query
        Passes query to builder, and gets results set, column number, and results set metaData back
        Passes results set, column number, and results set metaData to map builder, and returns map
         */
        String tax_rate_query = "select * from TAX_RATE WHERE TR_TAX_ID= ?";
        Map <String, Object> tax_rate_info;
        ArrayList<Object> query_results;
        query_results= query_builder(con, tax_rate_query, tax_id.toString());
        tax_rate_info = map_builder((ResultSet) query_results.get(0), (int) query_results.get(1),
                (ResultSetMetaData) query_results.get(2));
        return tax_rate_info;
    }
    public static Map<String, Object> location(Connection con, Number location_id) {
        /*
        Test location SQL query
        Passes query to builder, and gets results set, column number, and results set metaData back
        Passes results set, column number, and results set metaData to map builder, and returns map
         */
        String location_query = "select * from LOCATION WHERE LOCATION_ID= ?";
        Map <String, Object> location_info;
        ArrayList<Object> query_results;
        query_results= query_builder(con, location_query, location_id.toString());
        location_info = map_builder((ResultSet) query_results.get(0), (int) query_results.get(1),
                (ResultSetMetaData) query_results.get(2));
        return location_info;
    }

    public static ArrayList<Object>query_builder(Connection con, String query, String name){
        /*builds query as prepared statement, and returns query info */
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

    public static Map<String, Object> map_builder(ResultSet rs, int column_count, ResultSetMetaData metaData) {
        /*Constructs linked hashmap from result set and result set metadata*/
        Map<String, Object> map = new LinkedHashMap<>();
        String col_name;
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
    public static void toJSONWithGSON(Map<String, Object> tax_map, Map<String, Object> tax_rate_map,
                                      Map<String, Object> location_map){
        String tax_json;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Boolean button = true; //replace with button press
        tax_map.put("location", location_map);
        tax_map.put("tr_rate_percent", tax_rate_map);
        tax_json = gson.toJson(tax_map);
        System.out.println(tax_json);
        if(button)
            writeJSON(tax_json);
    }
    public static void writeJSON(String tax_json){
        try {
            File file = new File("output.json");
            FileWriter file_writer = new FileWriter(file);
            file_writer.write(tax_json);
            file_writer.flush();
            file_writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
