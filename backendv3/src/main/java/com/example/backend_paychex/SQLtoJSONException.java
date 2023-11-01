package com.example.backend_paychex;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SQLtoJSONException extends Throwable {
    public static class NotSafeQuery extends Exception{
        public NotSafeQuery(){
            super("Query invalid. Please rewrite the query and resubmit");
        }
    }

    public static class NoResultsReturned extends Exception {
        public NoResultsReturned() {
            super("This query did not return any results");
        }
    }
}
