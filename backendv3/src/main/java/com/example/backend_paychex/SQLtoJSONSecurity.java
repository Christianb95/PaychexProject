package com.example.backend_paychex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class SQLtoJSONSecurity {
    public static boolean isSafeQuery(String sqlQuery){
        String pattern = ".*\\b(DELETE|INSERT|DROP|ADD|CREATE|ALTER|TRUNCATE|UPDATE)\\b.*";
        boolean safe = !sqlQuery.matches(pattern);
        if(!safe){
            Path dirPath = Path.of("./logs");
            String dirStrPath = "./logs";
            if(!Files.exists(dirPath)){
                try {
                    Files.createDirectories(dirPath);
                } catch(IOException e){
                    System.err.println("Failed to create directory");//TODO: Change to write logfile
                }
            }

        }
        return safe;
    }
}
