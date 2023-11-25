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

    public static boolean validateURL(String dbURL) {
        /*
        Input: String database URL
        Output: Bool
        Tests database URL to see if allowed database
        * */
        String pattern = "(?i).*\\b(jdbc:oracle|jdbc:mysql|jdbc:sqlserver|jdbc:postgresql)\\b.*";
        return dbURL.matches(pattern);
    }
    public static String decrypt(String encryptedData) {
        /*
        Input: encrypted String
        Output: decrypted String
         */

        int shift = 3;
        StringBuilder decryptedData = new StringBuilder();

        for (int i = 0; i < encryptedData.length(); i++) {
            char c = encryptedData.charAt(i);

            if (Character.isLetter(c)) {
                if (Character.isUpperCase(c)) {
                    decryptedData.append((char) ('A' + (c - 'A' - shift + 26) % 26));
                } else {
                    decryptedData.append((char) ('a' + (c - 'a' - shift + 26) % 26));
                }
            } else {
                decryptedData.append(c);
            }
        }
        return decryptedData.toString();
    }
}
