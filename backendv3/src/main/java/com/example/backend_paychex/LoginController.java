package com.example.backend_paychex;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

//RestAPI receives login info from frontend in RequestBody
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v3")
public class LoginController {
    @PostMapping("/login")
    public ResponseEntity<String>login(@RequestBody Map<String, String> info) {
        /*  Input: RequestBody mapped to info Map
            Output: ResponseEntity for status
            Verifies log in information, and if login is accurate, info is passed to SQLQuery class vars
        */

        try {
            Connection connection = DriverManager.getConnection(info.get("databaseURL"), info.get("username"),
                    PasswordSec.decrypt(info.get("password")));

            if (connection.isValid(3) && SQLtoJSONSecurity.validateURL(info.get("databaseURL"))){
                SQLQuery.databaseURL = info.get("databaseURL");
                SQLQuery.username = info.get("username");
                SQLQuery.password = info.get("password");
            }
            connection.close();
            return ResponseEntity.ok("Login Successful");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + e.getMessage());
        }
    }
}
