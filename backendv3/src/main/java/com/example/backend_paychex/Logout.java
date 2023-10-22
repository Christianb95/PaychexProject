package com.example.backend_paychex;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class Logout {
    @Autowired
    private ConnectToDB connectToDB;
    public void closeConnection() throws SQLException { //change to shutdown hook?
        /*  Input: None
            Output: None
            Closes connection when program is signed out
          */
        connectToDB.con.close();
        System.out.println(connectToDB.con);
        boolean test = connectToDB.con.isClosed();
        boolean test2 = connectToDB.con.isValid(5); //
        System.out.println(test);
        System.out.println(test2);
    }
}
