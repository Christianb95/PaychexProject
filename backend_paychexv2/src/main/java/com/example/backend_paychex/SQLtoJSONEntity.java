package com.example.backend_paychex;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data //creates getter/setter/required args methods
public class SQLtoJSONEntity {
    private String sqlQuery;
}
