package com.sjd.service1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RequiredArgsConstructor
@RestController
public class HelloController {

  private final RestTemplate restTemplate;

  @Value("${spring.application.name:unknown}")
  private String appName;

  @Value("${services.endpoint.service1:http://localhost:10000}")
  private String service1endpoint;

  @Value("${services.endpoint.service2:http://localhost:11000}")
  private String service2endpoint;

  @RequestMapping("/hello")
  public ResponseEntity<String> hello() {
    return ok("Hello from " + appName);
  }

  @RequestMapping("/other")
  public ResponseEntity<String> other() {

    log.debug("AppName : {}, s1e : {}, s2e : {}", appName, service1endpoint, service2endpoint);

    if (appName.equals("service1")) {

      ResponseEntity<String> hello =
          restTemplate.getForEntity(service2endpoint + "/service2/hello", String.class);

      return ok(appName + " called other service, response was : " + hello.getBody());

    } else if (appName.equals("service2")) {

      ResponseEntity<String> hello =
          restTemplate.getForEntity(service1endpoint + "/service1/hello", String.class);

      return ok(appName + " called other service, response was : " + hello.getBody());
    }

    return ok("Other didn't call anything :-(");
  }
}
