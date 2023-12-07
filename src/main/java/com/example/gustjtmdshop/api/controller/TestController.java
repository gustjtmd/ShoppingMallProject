package com.example.gustjtmdshop.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {


    @RequestMapping(value = "/permitAll", method = RequestMethod.GET)
    public ResponseEntity<String> permitAll() {
        return ResponseEntity.ok("누구나 접근이 가능합니다.");
    }

    @RequestMapping(value = "/authenticated", method = RequestMethod.GET)
    public ResponseEntity<String> authenticated(@RequestHeader String Authorization){
        return ResponseEntity.ok("로그인한 사람 누구나 가능합니다.");
    }

    @RequestMapping(value = "/member", method =RequestMethod.GET)
    public ResponseEntity<String> member(@RequestHeader String Authorization){
        return ResponseEntity.ok("member 가능합니다.");
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ResponseEntity<String> admin(@RequestHeader String Authorization){
        log.info("Authorization313131313131{}", Authorization);
        return ResponseEntity.ok("admin 가능합니다.");
    }

    @GetMapping("/hello")
    public String helloworld() {
        return "hello world";
    }

}
