package com.company;

public class WarOfNumbers {
    public static int warOfNumbers(int[] numbers) {
        int even = 0;
        int odd = 0;
        for (int num : numbers) {
            if (num % 2 == 0) even += num;
            else odd += num;
        }
        return Math.abs(even - odd);
    }

    public static void main(String[] args) {
        int result = warOfNumbers(new int[]{5, 9, 45, 6, 2, 7, 34, 8, 6, 90, 5, 243});
        System.out.println(result);
    }
}