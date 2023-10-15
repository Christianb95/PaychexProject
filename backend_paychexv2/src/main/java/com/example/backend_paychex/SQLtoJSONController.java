package com.example.backend_paychex;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/custom-query")
@CrossOrigin

public class SQLtoJSONController {
    private final JdbcTemplate jdbcTemplate;

    public SQLtoJSONController(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping
    public List<Map<String, Object>>executeCustomQuery(@RequestBody SQLtoJSONEntity sqLtoJSONEntity) throws SQLtoJSONException.NotSafeQuery { //attaches Request body to element
        String sql = sqLtoJSONEntity.getSqlQuery();
        boolean sanitized = SQLtoJSONSecurity.isSafeQuery(sql);
        if (!sanitized) {
            throw new SQLtoJSONException.NotSafeQuery("Unsafe query detected: " + sql); //TODO: figure out how to end?
        }
        List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(sql); //submits query. TODO: double check for security issues
        return resultSet;
    }
}
