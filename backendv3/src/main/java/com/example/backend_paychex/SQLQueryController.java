package com.example.backend_paychex;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import static com.example.backend_paychex.SQLtoJSONSecurity.isSafeQuery;

//Receives query from frontend, checks query for potential injections, sends query to query builder

@RestController
@CrossOrigin
@RequestMapping("/api/v3")
public class SQLQueryController {
    @PostMapping ("/query")
    public ResponseEntity<String> query(@RequestBody SQLQuery sqlQuery) throws SQLtoJSONException.NotSafeQuery {
        /*  Input: RequestBody mapped to DTO SQLQuery
            Output: ResponseEntity for status
            Calls static methods isSafeQuery from SQLtoJSONSecurity and queryBuilder from SQLQuery.
        */
        String query = sqlQuery.getQuery();
        boolean isSafe = isSafeQuery(query);
        if(isSafe){
            try {
                sqlQuery.queryBuilder(query);
            } catch(SQLException e){
                e.printStackTrace();
            }
        } else{
            String message = "Query not accepted";
            throw new SQLtoJSONException.NotSafeQuery(message);
        }
        return ResponseEntity.ok("Query Successful");
    }

}
