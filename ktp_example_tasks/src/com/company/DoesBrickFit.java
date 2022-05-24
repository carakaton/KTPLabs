package com.company;

public class DoesBrickFit {
    public static boolean doesBrickFit(int a, int b, int c, int w, int h) {
        return (a + b <= w + h) || (b + c <= w + h) || (a + c <= w + h);
    }

    public static void main(String[] args) {
        boolean result = doesBrickFit(1, 2, 2, 1, 2);
        System.out.println(result);
    }
}
