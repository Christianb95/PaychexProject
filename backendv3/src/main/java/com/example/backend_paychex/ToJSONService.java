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
//    private Map <String, Object> resultMap = new LinkedHashMap<>();
    private ArrayList<Object> results = new ArrayList<>();

    protected void getQueryResults(ResultSet rs, int columnCount, ResultSetMetaData metaData){
        /*  Input: ResultSet contains results from query, int num of columns for table, ResultsSetMetaData
            Output: LinkedHash Map <String, Object>
            Constructs linked hashmap from result set values and column names from result set metadata*/
        String colName;
        try{
            while (rs.next()) { //iterates over result set
                LinkedHashMap<String, Object> builder = new LinkedHashMap<>();
                for (int columnInd = 1; columnInd <= columnCount; columnInd++) {
                    colName = metaData.getColumnName(columnInd); //gets column name
                    Object object = rs.getObject(columnInd); //gets cell value
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
        jsonStr = gson.toJson(results); //converts hash tax_map to JSON string using GSON library
    }
}
