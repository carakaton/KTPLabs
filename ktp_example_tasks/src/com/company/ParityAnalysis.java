package com.company;

public class ParityAnalysis {
    public static boolean parityAnalysis(int number) {
        int original = number;
        int summa = 0;
        while (number > 0) {
            summa += number % 10;
            number = number / 10;
        }
        return (original % 2 == 0 && summa % 2 == 0) || (original % 2 != 0 && summa % 2 != 0);
    }

    public static void main(String[] args) {
        boolean result = parityAnalysis(243);
        System.out.println(result);
    }
}
