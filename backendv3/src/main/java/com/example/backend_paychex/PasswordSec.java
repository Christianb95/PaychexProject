package com.example.backend_paychex;

public class PasswordSec {
    public static String decrypt(String encryptedData) {

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
