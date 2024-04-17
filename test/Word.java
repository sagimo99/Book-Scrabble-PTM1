package test;

import java.util.Arrays;
import java.util.Objects;

public class Word {
    private final Tile[] tiles;
    private final int col;
    private final int row;
    private final boolean vertical;

    public Word(Tile[] tiles, int col, int row, boolean vertical) {
        this.tiles = Arrays.copyOf(tiles, tiles.length);
        this.col = col;
        this.row = row;
        this.vertical = vertical;
    }

    public Tile[] getTiles() {
        return Arrays.copyOf(tiles, tiles.length);
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public boolean isVertical() {
        return vertical;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Word word = (Word) o;
        return col == word.col && row == word.row && vertical == word.vertical && Arrays.equals(tiles, word.tiles);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(col, row, vertical);
        result = 31 * result + Arrays.hashCode(tiles);
        return result;
    }
}
