package com.company;

public class Mean {
    public static double totalDistance(double liters, double litersPerKilo, int passengers, boolean cooler) {
        litersPerKilo *= 1.00 + 0.05 * passengers;
        if (cooler) litersPerKilo *= 1.10;

        return liters * 100 / litersPerKilo;
    }

    public static void main(String[] args) {
        double result = totalDistance(70.0, 7.0, 0, false);
        System.out.println(result);
    }

}
