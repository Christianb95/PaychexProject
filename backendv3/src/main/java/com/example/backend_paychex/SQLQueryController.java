package com.example.backend_paychex;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.example.backend_paychex.SQLtoJSONSecurity.isSafeQuery;

//Receives query from frontend, checks query for potential injections, sends query to query builder

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v3")
public class SQLQueryController {
    @PostMapping ("/query")
    public ResponseEntity<String> query(@RequestBody SQLQuery sqlQuery) {
        /*  Input: RequestBody mapped to DTO SQLQuery
            Output: ResponseEntity for status
            Calls static methods isSafeQuery from SQLtoJSONSecurity and queryBuilder from SQLQuery.
        */
        String query = sqlQuery.getQuery();
        try {
            boolean isSafe = isSafeQuery(query);
            if (isSafe) {
                sqlQuery.queryBuilder(query);
                return ResponseEntity.ok("Query Successful");
            } else {
                String message = "Query contains prohibited actions";
                throw new Exception(message);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
