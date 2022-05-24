package com.company;

public class InatorInator {
    public static String inatorInator(String name) {
        if (name.endsWith("a") || name.endsWith("e") || name.endsWith("o") ||
                name.endsWith("u") || name.endsWith("i") || name.endsWith("y"))
            return new String(name + "-inator " + name.length() + "000");
        else
            return new String(name + "inator " + name.length() + "000");
    }

    public static void main(String[] args) {
        String result = inatorInator("Doom");
        System.out.println(result);
    }
}