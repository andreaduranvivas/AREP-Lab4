package edu.arep.myspring.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import static java.lang.System.out;

/**
 * Hello world!
 *
 */
public class MySpring {
    public static void main( String[] args ) throws ClassNotFoundException {
        Class c = Class.forName("edu.arep.myspring.example.Alumno");
        //Class c = String.class;
        Field[] campos = c.getFields();
        printMembers(campos, "Campos: ");

        Constructor[] constructores = c.getConstructors();
        printMembers(constructores, "Constructores: ");

        Method[] metodos = c.getMethods();
        printMembers(metodos, "Métodos: ");
    }

    public static void suma(int a, int b){
        System.out.println("a + b = " + (a+b));
    }

    private static void printMembers(Member[] mbrs, String s) {
        out.format("%s:%n", s);
        for (Member mbr : mbrs) {
            if (mbr instanceof Field)
                out.format(" %s%n", ((Field) mbr).toGenericString());
            else if (mbr instanceof Constructor)
                out.format(" %s%n", ((Constructor) mbr).toGenericString());
            else if (mbr instanceof Method)
                out.format(" %s%n", ((Method) mbr).toGenericString());
        }
        if (mbrs.length == 0)
            out.format(" -- No %s --%n", s);
        out.format("%n");
    }
}
