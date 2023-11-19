package com.example.backend_paychex;
import com.google.gson.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
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
    protected static JSONArray jsonFullArray;
    protected static JSONArray topFiveJSON;
    private ArrayList<Object> results = new ArrayList<>();

    protected void getQueryResults(ResultSet rs, int columnCount, ResultSetMetaData metaData){
        /*  Input: ResultSet contains results from query, int num of columns for table, ResultsSetMetaData
            Output: None
            Constructs ArrayList <LinkedHashMap>  from result set values and column names from result set metadata*/
        String colName, tableName;
//        JsonObject test = new JsonObject();
//        JsonArray data = new JsonArray();
        try {
            while (rs.next()) {
                //iterates over result set
//                JSONObject builder = new JSONObject();
                LinkedHashMap<String, List<Object>> builder = new LinkedHashMap<>();
//                JsonArray row = new JsonArray();
//                for (int columnInd = 1; columnInd <= columnCount; columnInd++) {
//                    colName = metaData.getColumnName(columnInd);
//                   row.add(new JsonPrimitive(rs.getString(colName)));
//                }
//                data.add(row);

                for (int columnInd = 1; columnInd <= columnCount; columnInd++) {
                    Object object = rs.getObject(columnInd); // gets field value
                    JSONObject test = new JSONObject(object);
                    System.out.println(test);
                    colName = metaData.getColumnName(columnInd);

                    if (colName.contains(".")) {
                        List<String> temp = Arrays.asList(colName.split("\\."));
                        Collections.reverse(temp);

                        JSONObject nested = new JSONObject();

                        for (int i = 0; i < temp.size() - 1; i++) {
                            String ele = temp.get(i);
                            if (nested.isEmpty())
                                nested = new JSONObject().put(ele, object);
                            else
                                nested = new JSONObject().put(ele, nested);
                        }
/*                        System.out.println(nested);*/
                        String key = temp.get(temp.size() - 1);
                        if (builder.containsKey(key)){
                            List<Object> values = builder.get(key);
                            values.add(nested);
                        }
                        else if (!colName.equals(key)){
                            List<Object>values = new ArrayList<>();
                            values.add(nested);
                            builder.put(key, values);
                        }
                    } else {
                        List<Object>values = new ArrayList<>();
                        values.add(object);
                        builder.put(colName, values);
                    }
                }

                results.add(builder);
            }

//                LinkedHashMap<String, Object> builder = new LinkedHashMap<>();
//
//                for (int columnInd = 1; columnInd <= columnCount; columnInd++) {
//                    LinkedHashMap<String, Object> nested = new LinkedHashMap<>();
//                    Object object = rs.getObject(columnInd); // gets field value
//                    colName = metaData.getColumnName(columnInd);
//
//                    if (colName.contains(".")) {
//                        nested.clear();
//                        List<String> temp = Arrays.asList(colName.split("\\."));
//                        Collections.reverse(temp);
//                        LinkedHashMap<String, Object> current = nested;
//                        for (int i = 0; i < temp.size()-1; i++){
//                            String ele = temp.get(i);
//                            if (nested.isEmpty())
//                                current.put(ele, object);
//                            else
//                                current.put(ele, nested);
//                            nested = current;
//                            current = new LinkedHashMap<>();
//                            System.out.println(nested);
//                        }
//
////                        for (String ele : temp) {
////                            //add in first section, which is ele/field val
////                            current.put(ele, object);
////                            object = current;
////                            current = new LinkedHashMap<>();
////                        }
//                        builder.put(temp.get(temp.size()-1), nested);
//                        System.out.println(builder);
//                    } else {
//                        builder.put(colName, object);
//                    }
//
//                }
//                results.add(builder);
//                }
//            test.add("Example", data);
//            System.out.println(test);
        }
        catch (SQLException e) {
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
        jsonFullArray = new JSONArray(results);
        List<Object> topFive;
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create(); //uses GSON library to format JSON string
//        jsonStr = gson.toJson(jsonFullArray); //converts array list to JSON string using GSON library. Used to create JSON file
        if (results.size()>5) {
            topFive = results.subList(0, 5);//converts first 5 elements into JSON string for display
        }else{
            topFive = results;// on app page
        }
        topFiveJSON = new JSONArray(topFive);
    }
}
