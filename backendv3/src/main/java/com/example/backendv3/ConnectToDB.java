package com.example.backendv3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//creates getter, setter, and constructors
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectToDB {
      /* DTO temporarily stores username, password, and databaseURL to form and close database connection.
      Stores connection */
    private String username;
    private String password;
    private String databaseURL;
    protected static Connection con;

    protected void createConnection() throws ClassNotFoundException, SQLException{ //TODO change to logger?
        /*  Input: None, uses values above
            Output: None
            Creates database connection with username, password, and database url
            */
            Class.forName("oracle.jdbc.driver.OracleDriver"); //Driver information.
            con = DriverManager.getConnection(databaseURL, username, password); //manually creates database connection
    }
    public static void closeConnection() throws SQLException{
        /*  Input: None
            Output: None
            Closes connection when program is signed out
          */
        con.close();
    }
}
