package com.example.backend_paychex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.sql.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SQLQuery{
    private String query;
    private ResultsToJSON resultsToJSON = new ResultsToJSON();
    protected static Connection con;
    @Setter
    private static String username;
    @Setter
    private static String password;
    @Setter
    private static String databaseURL;

    protected void queryBuilder(String safeQuery) throws Exception {
        /*  Input: Sanitized string query
            Output: None
            builds query as prepared statement, and passes query info to create JSON string */
        try {
            con = DriverManager.getConnection(databaseURL, username, SQLtoJSONSecurity.decrypt(password));
            PreparedStatement stmt = con.prepareStatement(safeQuery); //prepares query
            ResultSet rs = stmt.executeQuery(); //executes SQL query and returns result set
            ResultSetMetaData metaData = rs.getMetaData(); //gets metadata from result set to use as keys
            int columnCount = metaData.getColumnCount(); //gets column count from metadata for iteration
            resultsToJSON.getQueryResults(rs, columnCount, metaData); //processes query results and converts to JSON format
            stmt.clearParameters();
            rs.close();
            stmt.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        //closes out statements, sets, and connection

    }

}

