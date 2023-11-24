package com.example.backend_paychex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    protected static String username;
    protected static String password;
    protected static String databaseURL;

    protected void queryBuilder(String safeQuery) throws Exception {
        /*  Input: Sanitized string query
            Output: None
            builds query as prepared statement, and passes query info to create JSON string */
        try {
            con = DriverManager.getConnection(databaseURL, username, PasswordSec.decrypt(password));
            PreparedStatement stmt = con.prepareStatement(safeQuery); //prepares query
            ResultSet rs = stmt.executeQuery(); //executes SQL query and returns result set
            ResultSetMetaData metaData = rs.getMetaData(); //gets metadata from result set to use as keys
            int columnCount = metaData.getColumnCount(); //gets column count from metadata for iteration
            resultsToJSON.getQueryResults(rs, columnCount, metaData); //calls conversion to JSON
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


