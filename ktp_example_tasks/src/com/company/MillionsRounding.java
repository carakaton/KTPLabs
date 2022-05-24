package com.company;

import java.util.HashMap;

public class MillionsRounding {
    public static void millionsRounding(HashMap<String, Integer> cities) {
        cities.replaceAll((name, value) -> (int) Math.round((double) cities.get(name) / 1_000_000) * 1_000_000);
    }

    public static void main(String[] args) {
        HashMap<String, Integer> cities = new HashMap<>();
        cities.put("Nice", 942208);
        cities.put("Abu Dhabi", 1482816);
        cities.put("Naples", 2186853);
        cities.put("Vatican City", 572);

        millionsRounding(cities);
        System.out.println(cities);
    }
}
