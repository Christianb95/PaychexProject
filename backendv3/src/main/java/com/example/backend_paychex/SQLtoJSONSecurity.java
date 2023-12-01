package com.example.backend_paychex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLtoJSONSecurity {
    public static boolean isSafeQuery(String sqlQuery) throws Exception {
        /*
        Input: String query
        Output: Bool
        Tests query to see if prohibited commands are in query, and returns true or false
        * */
        sqlQuery = sqlQuery.trim();
        String pattern = "(?ism).*\\b(DELETE|INSERT|DROP|ADD|CREATE|ALTER|TRUNCATE|UPDATE)\\b.*";
        if (!sqlQuery.matches(pattern)){
            return true;
        }else {
            String message = "Query contains prohibited actions.";
            throw new Exception(message);
        }
    }

    public static boolean singleQueryOnly(String sqlQuery) throws Exception {
        /*
        Input: String query
        Output: Bool
        Tests query to see if ;followed by a word is present, and lets user know that multiple queries are prohibited
        */
        sqlQuery = sqlQuery.trim();
        String pattern = ".*;\\s+[a-zA-Z].*";
        if (!sqlQuery.matches(pattern)){
            return true;
        }else{
            String message = "Cannot accept more than one query at a time";
            throw new Exception(message);
        }
    }

    public static boolean validateURL(String dbURL) {
        /*
        Input: String database URL
        Output: Bool
        Tests database URL to see if allowed database
        * */
        String pattern = "^[a-zA-Z0-9:@]+$";
        if (dbURL.matches(pattern)) {
            String wordPattern = "(?i).*\\b(jdbc:oracle|jdbc:mysql|jdbc:sqlserver|jdbc:postgresql)\\b.*";
            return dbURL.matches(wordPattern);
        }
        return false;
    }
    public static String decrypt(String encryptedData) {
        /*
        Input: encrypted String
        Output: decrypted String
         */
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
