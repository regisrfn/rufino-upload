package com.rufino.server.api;

import javax.validation.Valid;

import com.rufino.server.model.FileBase64;
import com.rufino.server.model.FileCloud;
import com.rufino.server.services.ApiServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("upload/base64")
    public FileCloud uploadBase64File(@Valid @RequestBody FileBase64 file) {
        return apiService.uploadFile(file);
    }

}
