package com.example.backend_paychex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SQLtoJSONSecurity {
    public static boolean isSafeQuery(String sqlQuery){
        /*
        Input: String query
        Output: Bool
        Tests query to see if prohibited commands are in query, and returns true or false
        * */
        String pattern = "(?i).*\\b(DELETE|INSERT|DROP|ADD|CREATE|ALTER|TRUNCATE|UPDATE)\\b.*";
        return !sqlQuery.matches(pattern);
    }
}
