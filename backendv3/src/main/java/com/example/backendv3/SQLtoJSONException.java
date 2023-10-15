package com.example.backendv3;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SQLtoJSONException {
    public static class NotSafeQuery extends Exception{
        public NotSafeQuery(String message){
            super(message);
        }
    }

}
