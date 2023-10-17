package com.example.backend_paychex;

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
        /*  Input: queryResults from SQLQuery class
            Output: None
            Unpacks query ArrayList and assigns to variables to pass into setMap */
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
        String colName;
        try{
            while (rs.next()) { //iterates over result set
                for (int columnInd = 1; columnInd <= columnCount; columnInd++) {
                    colName = metaData.getColumnName(columnInd); //gets column name
                    Object object = rs.getObject(columnInd); //gets cell value
                    resultMap.put(colName, object.toString()); //adds column name and value to linked hash map as key:value pair
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
