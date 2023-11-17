package com.example.backend_paychex;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    protected static String jsonStr;
    protected static String topFiveJSON;
    private ArrayList<Object> results = new ArrayList<>();

    protected void getQueryResults(ResultSet rs, int columnCount, ResultSetMetaData metaData){
        /*  Input: ResultSet contains results from query, int num of columns for table, ResultsSetMetaData
            Output: None
            Constructs ArrayList <LinkedHashMap>  from result set values and column names from result set metadata*/
        String colName, tableName;
        try{
            while (rs.next()) { //iterates over result set
                LinkedHashMap<String, Object> builder = new LinkedHashMap<>();
                for (int columnInd = 1; columnInd <= columnCount; columnInd++) {
                    colName = metaData.getColumnName(columnInd);
                    Object object = rs.getObject(columnInd);
                    builder.put(colName, object);

                }
                results.add(builder);
            }
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
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); //uses GSON library to format JSON string
        jsonStr = gson.toJson(results); //converts array list to JSON string using GSON library. Used to create JSON file
        topFiveJSON = gson.toJson(results.subList(0, 5)); //converts first 5 elements into JSON string for display
                                                        // on app page
    }
}
