import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;

/* TODO: Condense tax, tax_rate, and location into 1 method? Work with Gabe.
*/
public class main {
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
        String password = "Welllightupthedark144";
        //</editor-fold>
        Number tax_id, location_id;
        Map<String, Object> tax_map, location_map, tax_rate_map;

        try {
            Class.forName(
                    "oracle.jdbc.driver.OracleDriver"); //Driver information.
                                                                 // Here for testing purposes. Move to SQL query class
            Connection con = DriverManager.getConnection(url, user, password);//connects to database

            tax_map = tax(con);
            tax_id = Integer.valueOf((String) tax_map.get("TAX_ID")); //gets tax_ID from tax map
            location_id = Integer.valueOf((String) tax_map.get("TAX_LOCATION_ID")); //gets location_id from location map
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
        /*  Input: Connection
            Output: Linkedhash map with tax information for tax name given
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
        /*  Input: Connection and Num tax id
            Output: Linkedhash map of tax_rate_info table for tax id
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
        /*  Input: Connection, and Num location id
            Output: LinkedHash map with location information for location id
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
        /*  Input: Connection to database, String query, and String name (tax name, tax_id, or location_id)
            Output: ArrayList of query information
            builds query as prepared statement, and returns query info */
        ArrayList<Object> query_info = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(query); //prepares query and then name is inserted to replace ?
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery(); //executes SQL query and returns result set
            ResultSetMetaData metaData = rs.getMetaData(); //gets metadata from result set to use as keys
            int column_count = metaData.getColumnCount(); //gets column count from metadata for iteration
            //adds to array to return info above
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
        /*  Input: ResultSet contains results from query, int num of columns for table, ResultsSetMetaData
            Output: LinkedHash Map <String, Object>
            Constructs linked hashmap from result set values and column names from result set metadata*/
        Map<String, Object> map = new LinkedHashMap<>();
        String col_name;
        try{
            while (rs.next()) { //iterates over result set
                for (int column_ind = 1; column_ind <= column_count; column_ind++) {
                    col_name = metaData.getColumnName(column_ind); //gets column name
                    Object object = rs.getObject(column_ind); //gets cell value
                    map.put(col_name, object.toString()); //adds column name and value to linked hash map as key:value pair
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
        /*  Input: LinkedHash Map <String, Object> tax_map, tax_rate_map, location_map
            Output: None
            Nests hash maps and converts to JSON string. Passes JSON string to writeJSON
        */
        String tax_json;
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); //uses GSON library to format JSON string
        Boolean button = true; //replace with button press
        tax_map.put("location", location_map); //nests location hashmap in tax hash map
        tax_map.put("tr_rate_percent", tax_rate_map); //nests tax_rate hashmap in tax hash map
        tax_json = gson.toJson(tax_map); //converts hash tax_map to JSON string using GSON library
        System.out.println(tax_json);
        if(button)
            writeJSON(tax_json);
    }
    public static void writeJSON(String tax_json){
        /*  Input: String json
            Output: None
            Writes JSON string to JSON file */
        try {
            File file = new File("output.json"); //creates file and saves in current directory
            FileWriter file_writer = new FileWriter(file);
            file_writer.write(tax_json); //writes json string.
            file_writer.flush();
            file_writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
