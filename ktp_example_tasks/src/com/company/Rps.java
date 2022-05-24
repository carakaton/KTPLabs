package com.company;

public class Rps {
    public static void rps(String first_player, String second_player) {
        int result = 0;
        int[] map = new int[3];
        if (first_player == "rock") map = new int[]{0, -1, 1};
        if (first_player == "paper") map = new int[]{1, 0, -1};
        if (first_player == "scissors") map = new int[]{-1, 1, 0};

        if (second_player == "rock") result += map[0];
        if (second_player == "paper") result += map[1];
        if (second_player == "scissors") result += map[2];

        if (result == 1) System.out.println("Player 1 wins");
        if (result == -1) System.out.println("Player 2 win");
        if (result == 0) System.out.println("TIE");
    }

    public static void main(String[] args) {
        rps("paper", "rock");
    }
}