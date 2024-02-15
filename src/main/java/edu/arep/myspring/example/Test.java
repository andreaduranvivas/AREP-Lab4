package edu.arep.myspring.example;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) //Anotacion hasta tiempo de ejecucion
@Target(ElementType.METHOD)
public @interface Test {
}
