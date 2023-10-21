package com.example.backend_paychex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    protected Connection con;

    protected Connection createConnection() throws ClassNotFoundException, SQLException{ //TODO change to logger?
        /*  Input: None, uses values above
            Output: None
            Creates database connection with username, password, and database url
            */
            Class.forName("oracle.jdbc.driver.OracleDriver"); //Driver information.
            System.out.println(username);
            System.out.println(databaseURL);
            this.con = DriverManager.getConnection(databaseURL, username, password);
            return con; //manually creates database connection
    }
    public static void closeConnection() throws SQLException{ //change to shutdown hook?
        /*  Input: None
            Output: None
            Closes connection when program is signed out
          */
    }
}
