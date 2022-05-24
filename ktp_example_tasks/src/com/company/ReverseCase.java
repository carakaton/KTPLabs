package com.company;

public class ReverseCase {
    public static String reverseCase(String string) {
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++)
        {
            char c = chars[i];
            if (Character.isUpperCase(c)) chars[i] = Character.toLowerCase(c);
            else if (Character.isLowerCase(c)) chars[i] = Character.toUpperCase(c);
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        String result = reverseCase("Happy Birthday");
        System.out.println(result);
    }
}
