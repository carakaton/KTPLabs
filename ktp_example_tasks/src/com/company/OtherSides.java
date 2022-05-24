package com.company;

import java.util.Arrays;

public class OtherSides {
    public static double[] otherSides(int shortest_side) {
        double middle_side = shortest_side * 2;
        double largest_side = Math.round(shortest_side * Math.sqrt(3) / 0.01) * 0.01;
        return new double[]{middle_side, largest_side};
    }

    public static void main(String[] args) {
        double[] result = otherSides(3);
        System.out.println(Arrays.toString(result));
    }
}
