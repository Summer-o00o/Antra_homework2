package com.example.search.service;

import com.example.search.model.pojo.StudentPojo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SearchService {
    private final RestTemplate restTemplate;

    @Autowired
    public SearchService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    //has to call methods directly in controller, when put combine data method in service, Hystrix didn't work
    //has to call method from another Bean!!
    @HystrixCommand(fallbackMethod = "getDefaultStudents")
    public Object getStudents(String token) {
        CompletableFuture<List<StudentPojo>> studentsFuture = CompletableFuture.supplyAsync(() -> {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", token);
            HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

            ResponseEntity<List<StudentPojo>> response = restTemplate.exchange(
                    "http://PROJECT3/student",
                    HttpMethod.GET,
                    httpEntity,
                    new ParameterizedTypeReference<List<StudentPojo>>() {}
            );

            List<StudentPojo> students = response.getBody();
            return students;
        });
        return studentsFuture.join();
    }

    public Object getDefaultStudents(String token){
        return "project3 service is down";
    }


    @HystrixCommand(fallbackMethod = "getDefaultDetails")
    public Object getDetails(){
        CompletableFuture<Object> detailsFuture =  CompletableFuture.supplyAsync(() -> {
            Object details = restTemplate.getForObject(
                    "http://details/details/port",
                    Object.class
            );
            return details;
        });
        return detailsFuture.join();
    }

    public Object getDefaultDetails(){
        return "Details service is down";
    }
}
