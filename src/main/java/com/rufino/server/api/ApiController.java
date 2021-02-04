package com.rufino.server.api;

import java.util.HashMap;
import java.util.Map;

import com.rufino.server.model.FileCloud;
import com.rufino.server.services.ApiServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @Autowired
    ApiServices apiService;

    @PostMapping("upload")
    public FileCloud uploadFile(@RequestParam("file") MultipartFile file) {
        return apiService.uploadFile(file);
    }

    @DeleteMapping("delete/{id}")
    public Map<String, String> deleteFile(@PathVariable String id) {
        Map<String, String> message = new HashMap<>();
        apiService.deleteFile(id);
        message.put("message", "successfully operation");
        return message;
    }
}
