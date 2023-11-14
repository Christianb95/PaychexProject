package com.example.backend_paychex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SQLtoJSONSecurity {
    public static boolean isSafeQuery(String sqlQuery){
        String pattern = ".*\\b(DELETE|INSERT|DROP|ADD|CREATE|ALTER|TRUNCATE|UPDATE)\\b.*";
        return !sqlQuery.matches(pattern);
    }
}
