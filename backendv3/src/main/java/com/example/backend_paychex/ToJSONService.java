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
    protected static JsonArray jsonFullArray;
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
                    if (colName.contains(".")){
                        List<String> colSplit = List.of(colName.split("\\."));
                        for(Object ele: colSplit){
                            jsonObjectBuilder.add(String.valueOf(colSplit.get(0)), data.createArrayBuilder()
                                    .add(data.createObjectBuilder()
                                    .add(String.valueOf(ele), String.valueOf(object)
                                    )));
                        }
                    }else {
                        jsonObjectBuilder.add(colName, String.valueOf(object));
                    }
                }
                jsonArray.add(jsonObjectBuilder);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        toJSON();
    }
//TODO: Make more flexible. Handle complex queries.
    protected void toJSON(){
        /*  Input: Map<String, Object>
            Output: None
            Nests hash maps and converts to JSON string. Passes JSON string to writeJSON
        */
        JsonArrayBuilder firstFiveElementsBuilder = Json.createArrayBuilder();
        jsonFullArray = jsonArray.build().asJsonArray();

        for (int i = 0; i < Math.min(5, jsonFullArray.size()); i++) {
            firstFiveElementsBuilder.add(jsonFullArray.get(i));
        }
        topFiveJSON = firstFiveElementsBuilder.build();
        System.out.println(topFiveJSON);
    }
}
