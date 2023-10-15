package com.example.backendv3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.lang.*;
import static com.example.backendv3.SQLQuery.queryInfo;

/*TODO: store SQL results, convert to format to nest easily?, Refactor to make more object oriented.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class ToJSONService {
    protected static String jsonStr;
    private Map <String, Object> resultMap;

    protected void getQueryResults(ArrayList<Object>queryResults){
        ResultSet rs = (ResultSet) queryResults.get(0);
        int columnCount = (int) queryResults.get(1);
        ResultSetMetaData metaData = (ResultSetMetaData) queryResults.get(2);
        setMap(rs, columnCount, metaData);
    }
    protected void setMap(ResultSet rs, int columnCount, ResultSetMetaData metaData) {
        /*  Input: ResultSet contains results from query, int num of columns for table, ResultsSetMetaData
            Output: LinkedHash Map <String, Object>
            Constructs linked hashmap from result set values and column names from result set metadata*/
        Map<String, Object> map = new LinkedHashMap<>();
        String col_name;
        try{
            while (rs.next()) { //iterates over result set
                for (int column_ind = 1; column_ind <= columnCount; column_ind++) {
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
//TODO: Make more flexible. Handle complex queries.
    protected void toJSONWithGSON(){
        /*  Input: Map<String, Object>
            Output: None
            Nests hash maps and converts to JSON string. Passes JSON string to writeJSON
        */
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); //uses GSON library to format JSON string
        Boolean button = true; //TODO: replace with button press
        //TODO Test to see if private resultMap causes issue with conversion?
        jsonStr = gson.toJson(resultMap); //converts hash tax_map to JSON string using GSON library
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
