package backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.lang.reflect.Array;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;
/*TODO: store SQL results, convert to format to nest easily?, Refactor to make more object oriented.
 */
public class toJSON {
    private ResultSet rs;
    private int columnCount;
    private ResultSetMetaData metaData;
    private Map<String, Object> resultMap;
    protected toJSON(ArrayList query_results) {
        this.rs = (ResultSet) query_results.get(0);
        this.columnCount = (int) query_results.get(1);
        this.metaData = (ResultSetMetaData) query_results.get(2);
        setMap();
    }
    public Map<String, Object> getMap(){
        return resultMap;
    }

    public int getColCount(){
        return columnCount;
    }

    public ResultSetMetaData getMetaData(){
        return metaData;
    }

    public ResultSet getResultSet(){
        return rs;
    }
    protected void setMap() {
        /*  Input: ResultSet contains results from query, int num of columns for table, ResultsSetMetaData
            Output: LinkedHash Map <String, Object>
            Constructs linked hashmap from result set values and column names from result set metadata*/
        Map<String, Object> map = new LinkedHashMap<>();
        String col_name;
        try{
            while (rs.next()) { //iterates over result set
                for (int column_ind = 1; column_ind <= this.columnCount; column_ind++) {
                    col_name = metaData.getColumnName(column_ind); //gets column name
                    Object object = rs.getObject(column_ind); //gets cell value
                    resultMap.put(col_name, object.toString()); //adds column name and value to linked hash map as key:value pair
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    //TODO: Make more flexible
//    public static void toJSONWithGSON(Map<String, Object> tax_map, Map<String, Object> tax_rate_map,
//                                      Map<String, Object> location_map)
        protected void toJSONWithGSON(Map<String, Object> tax_map){
        /*  Input: LinkedHash Map <String, Object> tax_map, tax_rate_map, location_map
            Output: None
            Nests hash maps and converts to JSON string. Passes JSON string to writeJSON
        */
        String tax_json;
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); //uses GSON library to format JSON string
        Boolean button = true; //TODO: replace with button press
//        if (!location_map.isEmpty())
//            tax_map.put("location", location_map); //nests location hashmap in tax hash map
//        if (!tax_rate_map.isEmpty())
//            tax_map.put("tr_rate_percent", tax_rate_map); //nests tax_rate hashmap in tax hash map
        tax_json = gson.toJson(tax_map); //converts hash tax_map to JSON string using GSON library
        System.out.println(tax_json); //TODO: replace with display
    }
    protected void writeJSON(String tax_json) {
        /*  Input: String json
            Output: None
            Writes JSON string to JSON file */
        try {
            File file = new File("output.json"); //creates file and saves in current directory
            FileWriter file_writer = new FileWriter(file);
            file_writer.write(tax_json); //writes json string.
            file_writer.flush();
            file_writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
