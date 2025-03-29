package project2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import project2.Model.Pojo.Data;
import project2.Model.Pojo.FetchResult;
import project2.exceptions.FetchException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Project2Service {
    private final RestTemplate restTemplate;

    @Autowired
    public Project2Service(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    //GetMapping get all data
    public List<Data> getAllData(){
        String url = "https://jsonmock.hackerrank.com/api/countries";
        FetchResult fetchResult;
        try {
            fetchResult = restTemplate.getForObject(url, FetchResult.class);
        }catch(Exception exception){
            throw new FetchException("Fetch did not succeed");
        }
        return fetchResult.getData();
    }

    public List<Data> getDataByName(String name){
        String url = "https://jsonmock.hackerrank.com/api/countries";
        FetchResult fetchResult;
        try{
            fetchResult = restTemplate.getForObject(url, FetchResult.class);
        }catch (Exception exception){
            throw new FetchException("Fetch did not succeed");
        }
        List<Data> data = fetchResult.getData().stream()
                                        .filter(e-> e.getName().equals(name))
                                        .collect(Collectors.toList());
        return data;
    }
}
