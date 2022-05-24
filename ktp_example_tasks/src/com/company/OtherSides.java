package com.company;

import java.util.Arrays;

public class OtherSides {
    public static double[] otherSides(int short_side) {
        double middle_side = short_side * 2;
        double large_side = Math.round(short_side * Math.sqrt(3) / 0.01) * 0.01;
        return new double[]{middle_side, large_side};
    }

    public static void main(String[] args) {
        double[] result = otherSides(3);
        System.out.println(Arrays.toString(result));
    }
}
