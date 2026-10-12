package com.java1.fundamentals.hello;

import org.springframework.stereotype.Component;

@Component
public class EmployeeUtility {

    public String greeting() {
        return "Hello Spring Boot";
    }
}
