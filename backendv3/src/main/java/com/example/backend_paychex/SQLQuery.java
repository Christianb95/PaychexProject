package com.example.backend_paychex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SQLQuery {
    private String query;
    protected static ArrayList<Object> queryInfo;
    @Autowired

    private ToJSONService toJSONService;
    private Connection dbConnection;
    protected void queryBuilder(String safeQuery) throws SQLException{
        /*  Input: Sanitized string query
            Output: ArrayList of query information
            builds query as prepared statement, and returns query info */
        PreparedStatement stmt = dbConnection.prepareStatement(safeQuery); //prepares query
        ResultSet rs = stmt.executeQuery(); //executes SQL query and returns result set
        ResultSetMetaData metaData = rs.getMetaData(); //gets metadata from result set to use as keys
        int column_count = metaData.getColumnCount(); //gets column count from metadata for iteration
        //adds to array to return info above
        queryInfo.add(rs);
        queryInfo.add(column_count);
        queryInfo.add(metaData);
        toJSONService.getQueryResults(queryInfo);
    }

}

