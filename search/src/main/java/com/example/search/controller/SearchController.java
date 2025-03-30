package com.example.search.controller;

import com.example.search.model.pojo.ResponsePojo;
import com.example.search.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService){
        this.searchService = searchService;
    }

    @GetMapping("/weather/search")
    public ResponseEntity<ResponsePojo> getDetails(@RequestHeader("Authorization") String token) {
        //combine data in controller
        Object students = searchService.getStudents(token);
        Object details = searchService.getDetails();
        ResponsePojo responsePojo = new ResponsePojo();
        responsePojo.setStudentPojos(students);
        responsePojo.setDetails(details);
        return ResponseEntity.status(200).body(responsePojo);
    }
}
