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
public class SQLQuery{
    private String query;
    protected static ArrayList<Object> queryInfo = new ArrayList<Object>();
    @Autowired
    private ToJSONService toJSONService = new ToJSONService();
    protected static Connection con;
    protected static String username;
    protected static String password;
    protected static String databaseURL;

    protected void createConnection() throws ClassNotFoundException, SQLException { //TODO change to logger?
        /*  Input: None, uses values above
            Output: None
            Creates database connection with username, password, and database url
            */
        if(con!=null) {
            if (con.isClosed()) {
                System.out.println("con is closed");
            }
        }
        Class.forName("oracle.jdbc.driver.OracleDriver"); //Driver information.
        con = DriverManager.getConnection(databaseURL, username, password);
    }
    protected void queryBuilder(String safeQuery) throws SQLException, ClassNotFoundException{
        /*  Input: Sanitized string query
            Output: ArrayList of query information
            builds query as prepared statement, and passes query info to create JSON string */

        queryInfo.clear(); //clears linked hashmap for every new query

        //re-establishes connection for every new query

        //prepares SQl query, executes query, and gets result set, metadata etc.
        PreparedStatement stmt = con.prepareStatement(safeQuery); //prepares query
        ResultSet rs = stmt.executeQuery(); //executes SQL query and returns result set
        ResultSetMetaData metaData = rs.getMetaData(); //gets metadata from result set to use as keys
        int column_count = metaData.getColumnCount(); //gets column count from metadata for iteration

        //adds resultset, metadata, and column number to array to process
        queryInfo.add(rs);
        queryInfo.add(column_count);
        queryInfo.add(metaData);
        toJSONService.getQueryResults(queryInfo); //calls conversion to JSON

        //closes out statements, sets, and connection
        stmt.clearParameters();
        rs.close();
        stmt.close();
        con.close();
        System.out.println(con.isValid(5));
    }

}

