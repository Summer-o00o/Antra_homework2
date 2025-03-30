package com.example.search.service;

import com.example.search.model.pojo.ResponsePojo;
import com.example.search.model.pojo.StudentPojo;
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

    public ResponsePojo getCombinedData(String token){
        CompletableFuture<Object> getDetails = CompletableFuture.supplyAsync(() -> {
            return getDetails();
        });

        CompletableFuture<List<StudentPojo>> getStudents = CompletableFuture.supplyAsync(() -> {
            return getStudents(token);
        });

        ResponsePojo responsePojo = new ResponsePojo();
        responsePojo.setDetails(getDetails.join());
        responsePojo.setStudentPojos(getStudents.join());

        return responsePojo;
    }

    private List<StudentPojo> getStudents(String token){
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
    }

    private Object getDetails(){
        Object details = restTemplate.getForObject(
                "http://details/details/port",
                Object.class
        );
        return details;
    }
}
