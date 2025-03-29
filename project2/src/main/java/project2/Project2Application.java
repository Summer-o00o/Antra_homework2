package project2;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class Project2Application {
    public static void main(String[] args){
        SpringApplication.run(Project2Application.class, args);
    }
}
