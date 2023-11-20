package com.example.backend_paychex;
import com.google.gson.*;
import jakarta.json.JsonArray;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import jakarta.json.*;
import java.sql.*;
import java.util.*;
import java.lang.*;

/*TODO: store SQL results, convert to format to nest easily?, Refactor to make more object oriented.
 */
@Data
@AllArgsConstructor
@Service
public class ToJSONService {
    protected static JsonArray topFiveJSON;
    protected static JsonBuilderFactory data = Json.createBuilderFactory(null);
    protected static JsonArrayBuilder jsonArray = data.createArrayBuilder();
//    private ArrayList<Object> results = new ArrayList<>();

    protected void getQueryResults(ResultSet rs, int columnCount, ResultSetMetaData metaData){
        /*  Input: ResultSet contains results from query, int num of columns for table, ResultsSetMetaData
            Output: None
            Constructs ArrayList <LinkedHashMap>  from result set values and column names from result set metadata*/
        String colName;
        try {
            while (rs.next()) {
                //iterates over result set
                JsonObjectBuilder jsonObjectBuilder = data.createObjectBuilder();
                for (int columnInd = 1; columnInd <= columnCount; columnInd++) {
                    Object object = rs.getObject(columnInd); // gets field value
                    colName = metaData.getColumnName(columnInd);
                    if
                    jsonObjectBuilder.add(colName, String.valueOf(object));
                }
                jsonArray.add(jsonObjectBuilder);
            }
            System.out.println(jsonArray.build());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        toJSONWithGSON();
    }
//TODO: Make more flexible. Handle complex queries.
    protected void toJSONWithGSON(){
        /*  Input: Map<String, Object>
            Output: None
            Nests hash maps and converts to JSON string. Passes JSON string to writeJSON
        */
//        jsonFullArray = new JSONArray(results);
        List<Object> topFive;
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create(); //uses GSON library to format JSON string
//        jsonStr = gson.toJson(jsonFullArray); //converts array list to JSON string using GSON library. Used to create JSON file
//        if (data.length()>5) {
//            topFive = data.toList().subList(0, 5);//converts first 5 elements into JSON string for display
//        }else{
//            topFive = data.toList();// on app page
//        }
//        topFiveJSON = new JSONArray(topFive);
    }
}
