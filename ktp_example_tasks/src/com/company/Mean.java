package com.company;

public class Mean {
    public static double mean(int[] numbers) {
        int summa = 0;
        int i = 0;
        for (int number : numbers) {
            summa += number;
            i++;
        }
        return (double) summa / i;
    }

    public static void main(String[] args) {
        double result = mean(new int[]{2, 3, 2, 3});
        System.out.println(result);
    }
}
