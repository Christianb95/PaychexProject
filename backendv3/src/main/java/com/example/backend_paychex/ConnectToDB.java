package com.example.backend_paychex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//creates getter, setter, and constructors
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Service
public class ConnectToDB {
    /* DTO temporarily stores username, password, and databaseURL to form and close database connection.
      Stores connection */
    private String username;
    private String password;
    private String databaseURL;
    private Connection con;


    protected void createConnection() throws ClassNotFoundException, SQLException{ //TODO change to logger?
        /*  Input: None, uses values above
            Output: None
            Tests database connection with username, password, and database url, then closes connection
            */
        Class.forName("oracle.jdbc.driver.OracleDriver"); //Driver information.
        con = DriverManager.getConnection(databaseURL, username, PasswordSec.decrypt(password));
        if (con.isValid(5)){
            SQLQuery.username = username;
            SQLQuery.password = password;
            SQLQuery.databaseURL = databaseURL;
        }
        con.close();

    }
}
