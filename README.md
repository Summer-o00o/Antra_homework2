1. create new module and copy project2 and project 3 into 2 new modules
2. rewrite the code to compatible with java 8 and springboot 2
3. add eureka client and config client dependency to pom.xml file in project2 and project3
4. enable annotation @EnableEurekaClient in application class
5. config eureka client in project2 and project 3 .properties files.
6. move properties to config server by creating bootstrap.properties (specify name and port of config server)
7. config properties file of gateway of the newly added app so that endpoint can be visited through gateway
8. use terminal command to encrypt port with keystore in config
9. add ribbon dependency in search pom.xml and define load balance strategy in search .properties file
10. create RestTemplateConfig class and add annotation @LoadBalanced for rest template bean
11. use completable future to call endpoint of project2 and details service, use eureka to fetch ip address 
12. scan common package to send general response (@RestControllerAdvice)
13. add hystrix to search service by adding dependency to pom.xml, add annotation @EnableHystrix in
    application class, and add annotation @HystrixCommand in service class, define fallback method.
14. if method has annotation @HystrixCommand, then the method needs to be called from another bean to trigger
    the fallback method.
15. Config hystrix in .properties files for the condition of trigger circuit breaker
16. use terminal to create keystore file and encrypt username and password used for the database connection
17. download Prometheus and Grafana, install Pronetheus using pre-compiled binaries. 
18. add Prometheus dependency in pom.xml for project2 and project 3 service and config prometheus in .properties files
19. modify prometheus.yml file under application.
20. Prometheus port: 9090 , Grafana port: 3000 . Has to start local Promtheus and Grafana from terminal first
21. import Spring Boot Statistics Dashboard.json to create dashboard of the application
22. provide Swagger / Openai api documentation for project2 and project3.
23. add Swagger dependency in pom.xml file and config swagger url in .properties file.
24. create SwaggerConfig class and add @EnableSwagger2
25. use http://localhost:applicationPort/swagger-ui.html to check endpoints

**26. download splunk adn splunk universal forwarder, follow the step to set up localhost port :8000**

**27. start the splunk, open splunk, in setting - forwarding and receiving, add new receiving port: 9997**
28. create logback-spring.xml file for each microservice for Lockback to log a file
29. create and config inputs (index, source type, log file path...) and outputs.conf (server port) file (splunk/etc/system/local)
30. restart splunk universal forwarder, in splunk search and reports, search index = "homework2"


