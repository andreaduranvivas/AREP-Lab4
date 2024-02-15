package edu.arep.myspring.components;

import edu.arep.myspring.runtime.Component;
import edu.arep.myspring.runtime.GetMapping;

@Component
public class ComponenteWeb {

    @GetMapping("/hello")
    public static String hello() {

        return "Greetings from Spring Boot!";
    }



}