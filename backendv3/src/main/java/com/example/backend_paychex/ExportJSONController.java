package com.example.backend_paychex;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;


@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/v3")
public class ExportJSONController {
    @GetMapping("/exportJSON")
    public void exportJSON(HttpServletResponse response) throws IOException {

        response.setHeader("Content-Disposition", "attachment; filename=data.json");
        response.setContentType("application/json");

        response.getWriter().write(String.valueOf(ToJSONService.data));

    }

}
