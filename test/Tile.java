package test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Tile {
    public final char letter;
    public final int score;

    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    public static class Bag {
        private static Bag instance = null;
        private final int[] quantities;
        private final int[] maxQuantities;
        private final Tile[] tiles;
        private int size;

        private Bag() {
            this.quantities = new int[] {
                    9, // A
                    2, // B
                    2, // C
                    4, // D
                    12, // E
                    2, // F
                    3, // G
                    2, // H
                    9, // I
                    1, // J
                    1, // K
                    4, // L
                    2, // M
                    6, // N
                    8, // O
                    2, // P
                    1, // Q
                    6, // R
                    4, // S
                    6, // T
                    4, // U
                    2, // V
                    2, // W
                    1, // X
                    2, // Y
                    1, // Z
            };
            this.maxQuantities = quantities.clone();
            new HashMap<Character, Integer>();
            tiles = new Tile[] {
                    new Tile('A', 1),
                    new Tile('B', 3),
                    new Tile('C', 3),
                    new Tile('D', 2),
                    new Tile('E', 1),
                    new Tile('F', 4),
                    new Tile('G', 2),
                    new Tile('H', 4),
                    new Tile('I', 1),
                    new Tile('J', 8),
                    new Tile('K', 5),
                    new Tile('L', 1),
                    new Tile('M', 3),
                    new Tile('N', 1),
                    new Tile('O', 1),
                    new Tile('P', 3),
                    new Tile('Q', 10),
                    new Tile('R', 1),
                    new Tile('S', 1),
                    new Tile('T', 1),
                    new Tile('U', 1),
                    new Tile('V', 4),
                    new Tile('W', 4),
                    new Tile('X', 8),
                    new Tile('Y', 4),
                    new Tile('Z', 10),
            };
            size = 0;
            for (int q : quantities) {
                size += q;
            }
        }

        public static Bag getBag() {
            if (instance == null) {
                instance = new Bag();
            }
            return instance;
        }

        public Tile getRand() {
            if (size <= 0) {
                return null;
            }

            Random rand = new Random();
            int randomIndex = rand.nextInt(quantities.length);
            while (quantities[randomIndex] == 0)
                randomIndex = rand.nextInt(quantities.length);
            size--;
            quantities[randomIndex]--;
            return tiles[randomIndex];
        }

        public Tile getTile(char letter) {
            if (letter >= 'A' && letter <= 'Z' && quantities[letter - 'A'] > 0) {
                quantities[letter - 'A'] -= 1;
                size -= 1;
                return tiles[letter - 'A'];
            }

            return null;
        }

        public void put(Tile tile) {
            int index = tile.letter - 'A';
            if (quantities[index] < maxQuantities[index]) {
                quantities[index]++;
                size++;
            }
        }

        public int size() {
            return size;
        }

        public int[] getQuantities() {
            return Arrays.copyOf(quantities, quantities.length);
        }

    }
}
