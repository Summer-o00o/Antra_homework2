package project2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project2.Model.Pojo.Data;
import project2.exceptions.FetchException;
import project2.services.Project2Service;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Project2Controller {
    private final Project2Service service;

    @Autowired
    public Project2Controller(Project2Service project2Service){
        this.service = project2Service;
    }

    @GetMapping(params = "!name")
    public ResponseEntity<List<Data>> getAllData(){
        List<Data> fetchResult = service.getAllData();
        return ResponseEntity.status(200).body(fetchResult);
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<Data>> getDataByName(@RequestParam String name){
        List<Data> fetchResult = service.getDataByName(name);
        return ResponseEntity.status(200).body(fetchResult);
    }

    @ExceptionHandler(FetchException.class)
    public ResponseEntity<String> handleFetchException(FetchException exception){
        return ResponseEntity.status(500).body(exception.getMessage());
    }

}
