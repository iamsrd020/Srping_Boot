package com.java1.fundamentals.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final EmployeeUtility employeeUtility;

    public HelloController(EmployeeUtility employeeUtility) {
        this.employeeUtility = employeeUtility;
    }

    @GetMapping("/hello")
    public String hello() {
        return employeeUtility.greeting();
    }
}
