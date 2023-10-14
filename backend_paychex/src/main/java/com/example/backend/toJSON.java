package com.example.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;


import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;
/*TODO: store SQL results, convert to format to nest easily?, Refactor to Service and Class? Don't like how its written now
 */
public class toJSON {
    @Getter
    private ResultSet rs;
    @Getter
    private int columnCount;
    @Getter
    private ResultSetMetaData metaData;
    private String jsonStr;
    private Map <String, Object> resultMap;
    protected toJSON(ArrayList query_results) {
        this.rs = (ResultSet) query_results.get(0);
        this.columnCount = (int) query_results.get(1);
        this.metaData = (ResultSetMetaData) query_results.get(2);
        //Below run automatically. May need to change config based on testing
        setMap();
        toJSONWithGSON();
    }
    public Map<String, Object> getMap(){
        return resultMap;
    }

//    public int getColCount(){
//        return columnCount;
//    }

//    public ResultSetMetaData getMetaData(){
//        return metaData;
//    }
//
//    public ResultSet getResultSet(){
//        return rs;
//    }

    public String getJsonStr(){
        return jsonStr;
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
//    public static void toJSONWithGSON(Map<String, Object> tax_map, Map<String, Object> tax_rate_map,
//                                      Map<String, Object> location_map)
//TODO: Make more flexible. Handle complex queries.
    protected void toJSONWithGSON(){
        /*  Input: Map<String, Object>
            Output: None
            Nests hash maps and converts to JSON string. Passes JSON string to writeJSON
        */
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); //uses GSON library to format JSON string
        Boolean button = true; //TODO: replace with button press
//        if (!location_map.isEmpty())
//            tax_map.put("location", location_map); //nests location hashmap in tax hash map
//        if (!tax_rate_map.isEmpty())
//            tax_map.put("tr_rate_percent", tax_rate_map); //nests tax_rate hashmap in tax hash map
        //TODO Test to see if private resultMap causes issue with conversion?
        jsonStr = gson.toJson(resultMap); //converts hash tax_map to JSON string using GSON library
        System.out.println(jsonStr); //TODO: replace with display and getter
    }
    protected void writeJSON() {
        /*  Input: String json
            Output: None
            Writes JSON string to JSON file */
        try {
            File file = new File("output.json"); //creates file and saves in current directory
            FileWriter file_writer = new FileWriter(file);
            file_writer.write(jsonStr); //writes json string.
            file_writer.flush();
            file_writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
