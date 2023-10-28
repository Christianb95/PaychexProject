package com.example.backend_paychex;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SQLtoJSONException {
    public static class NotSafeQuery extends Exception{
        public NotSafeQuery(String message){
            super(message);
        }
    }

    public static class NoQueryEntered extends Exception {
        public NoQueryEntered(String message) {
            super(message);
        }
    }
}
