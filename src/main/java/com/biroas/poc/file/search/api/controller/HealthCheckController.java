package com.biroas.poc.file.search.api.controller;

import com.biroas.poc.file.search.api.service.FileSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @Autowired
    FileSearchService fileSearchService;

    @GetMapping("/health-check")
    public ResponseEntity<String> myCustomCheck() {
        PageRequest pageRequest=new PageRequest(0,1);
        fileSearchService.findAll(pageRequest);
        return new ResponseEntity<>("Elasticsearch OK", HttpStatus.OK);
    }

}
