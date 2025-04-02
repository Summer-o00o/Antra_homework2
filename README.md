1. create new module and copy project2 and project 3 into 2 new modules
2. rewrite the code to compatible with java 8 and springboot 2
3. add eureka client and config client dependency to pom.xml file in project2 and project3





10. download local Prometheus and Grafana to create dashboard of the application -- use pre-complied binaries
11. modify prometheus.yml file in the download folder as following
    scrape_configs:
        - job_name: "project2-service"
          metrics_path: "/actuator/prometheus"
          static_configs:
        - targets: ["localhost:8300"]
        - job_name: "project3-service"
          metrics_path: "/actuator/prometheus"
          static_configs:
        - targets: ["localhost:8400"]
12. Prometheus port: 9090
13. Grafana port: 3000
14. Has to start local Promtheus and Grafana from terminal first