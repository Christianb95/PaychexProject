package com.example.backend_paychex;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;

/*TODO: store SQL results, convert to format to nest easily?, Refactor to make more object oriented.
 */
@Data
@AllArgsConstructor
@Service
public class ResultsToJSON {
    protected static List<Object> topFiveJSON;
    protected static ArrayList<Object> results = new ArrayList<>();

    protected void getQueryResults(ResultSet rs, int columnCount, ResultSetMetaData metaData) throws Exception{
        /*  Input: ResultSet contains results from query, int num of columns for table, ResultsSetMetaData
            Output: None
            Parses through ResultSet from SQL query and calls create tree for each element in result set, then calls
            addpath to add columns as new node path in created tree*/
        String colName;
        results.clear();
        try {
            while (rs.next()) {
                //Initializes tree to store data from result set in hierarchy
                ResultCreateTree createTree = new ResultCreateTree();

                //iterates over result set and passes column names and field value to construct tree path
                for (int columnInd = 1; columnInd <= columnCount; columnInd++) {
                    Object value = rs.getObject(columnInd); // gets field value
                    colName = metaData.getColumnName(columnInd); //gets column name
                    createTree.addPath(colName, value);

                }
                results.add(createTree.toJson());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        modArray();
    }
    protected void modArray(){
        /*Input: None
          Output: None
          Creates sublist of first 5 results array for displaying on front end page
         */
        int endIdx = Math.min(results.size(), 5);
        topFiveJSON = results.subList(0, endIdx);
    }
}
