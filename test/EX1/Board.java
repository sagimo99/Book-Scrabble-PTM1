package EX1.test;

import java.util.ArrayList;

public class Board {
    private static final int BOARD_SIZE = 15; // Assuming board size 15x15
    private static Board instance = null;
    private boolean isEmpty;
    private Tile[][] tiles;
    final byte dl = 2;
    final byte tl = 3;
    final byte dw = 20;
    final byte tw = 30;

    private final byte[][] BonusMatrix = {
            { tw, 0, 0, dl, 0, 0, 0, tw, 0, 0, 0, dl, 0, 0, tw },
            { 0, dw, 0, 0, 0, tl, 0, 0, 0, tl, 0, 0, 0, dw, 0 },
            { 0, 0, dw, 0, 0, 0, dl, 0, dl, 0, 0, 0, dw, 0, 0 },
            { dl, 0, 0, dw, 0, 0, 0, dl, 0, 0, 0, dw, 0, 0, dl },
            { 0, 0, 0, 0, dw, 0, 0, 0, 0, 0, dw, 0, 0, 0, 0 },
            { 0, tl, 0, 0, 0, tl, 0, 0, 0, tl, 0, 0, 0, tl, 0 },
            { 0, 0, dl, 0, 0, 0, dl, 0, dl, 0, 0, 0, dl, 0, 0 },
            { tw, 0, 0, dl, 0, 0, 0, dw, 0, 0, 0, dl, 0, 0, tw },
            { 0, 0, dl, 0, 0, 0, dl, 0, dl, 0, 0, 0, dl, 0, 0 },
            { 0, tl, 0, 0, 0, tl, 0, 0, 0, tl, 0, 0, 0, tl, 0 },
            { 0, 0, 0, 0, dw, 0, 0, 0, 0, 0, dw, 0, 0, 0, 0 },
            { dl, 0, 0, dw, 0, 0, 0, dl, 0, 0, 0, dw, 0, 0, dl },
            { 0, 0, dw, 0, 0, 0, dl, 0, dl, 0, 0, 0, dw, 0, 0 },
            { 0, dw, 0, 0, 0, tl, 0, 0, 0, tl, 0, 0, 0, dw, 0 },
            { tw, 0, 0, dl, 0, 0, 0, tw, 0, 0, 0, dl, 0, 0, tw } };

    private Board() {
        tiles = new Tile[BOARD_SIZE][BOARD_SIZE];
        isEmpty = true;
    }

    public static Board getBoard() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    public Tile[][] getTiles() {
        Tile[][] copyTiles = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                copyTiles[i][j] = tiles[i][j];
            }
        }
        return copyTiles;
    }

    public void placeTile(int x, int y, Tile tile) {
        if (isValidPosition(x, y)) {
            tiles[x][y] = tile;
        } else {
            System.out.println("Invalid position!");
        }
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
    }

    private boolean onStar(Word w) {
        int i = w.getRow();
        int j = w.getCol();
        // for(int k = 0; k <w.getTiles().length; k++) {
        for (@SuppressWarnings("unused")
        Tile t : w.getTiles()) {
            if (i == 7 && j == 7)
                return true;
            if (w.isVertical())
                i++;
            else
                j++;
        }
        return false;
    }

    private boolean crossTile(Word w) {
        int i = w.getRow(), j = w.getCol();
        for (@SuppressWarnings("unused")
        Tile t : w.getTiles()) {

            if (tiles[i][j] != null ||
                    (isValidPosition(i + 1, j) && tiles[i + 1][j] != null) ||
                    (isValidPosition(i + 1, j + 1) && tiles[i + 1][j + 1] != null) ||
                    (isValidPosition(i, j + 1) && tiles[i][j + 1] != null) ||
                    (isValidPosition(i - 1, j + 1) && tiles[i - 1][j + 1] != null) ||
                    (isValidPosition(i - 1, j) && tiles[i - 1][j] != null) ||
                    (isValidPosition(i - 1, j - 1) && tiles[i - 1][j - 1] != null) ||
                    (isValidPosition(i, j - 1) && tiles[i][j - 1] != null) ||
                    (isValidPosition(i + 1, j - 1) && tiles[i + 1][j - 1] != null))
                return true;

            if (w.isVertical())
                i++;
            else
                j++;
        }
        return false;
    }

    private boolean isChangable(Word w) {
        int i = w.getRow(), j = w.getCol();
        for (Tile t : w.getTiles()) {
            if (tiles[i][j] != null && tiles[i][j] != t)
                return true;
            if (w.isVertical())
                i++;
            else
                j++;
        }
        return false;
    }

    public boolean boardLegal(Word w) {

        int row = w.getRow();
        int col = w.getCol();

        int vCol, vRow;
        if (!isValidPosition(row, col))
            return false;
        if (w.isVertical()) {
            vCol = col;
            vRow = row + w.getTiles().length - 1;
        } else {
            vRow = row;
            vCol = col + w.getTiles().length - 1;
        }
        if (!isValidPosition(vRow, vCol))
            return false;
        if (isEmpty && !onStar(w))
            return false;

        if (!isEmpty && !crossTile(w))
            return false;

        if (isChangable(w))
            return false;

        return true;

    }

    public boolean dictionaryLegal(Word word) {
        return true;
    }

    private ArrayList<Word> getAllWords(Tile[][] tiles) {
        ArrayList<Word> words = new ArrayList<>();

        // Horizontal Scan
        for (int i = 0; i < tiles.length; i++) {
            int j = 0;
            while (j < tiles[i].length) {
                ArrayList<Tile> tl = new ArrayList<>();
                int row = i, col = j;
                while (j < tiles[i].length && tiles[i][j] != null) {
                    tl.add(tiles[i][j]);
                    j++;
                }
                if (tl.size() > 1) {
                    Tile[] nTiles = new Tile[tl.size()];
                    words.add(new Word(tl.toArray(nTiles), row, col, false));
                }
                j++;
            }
        }

        // Vertical Scan
        for (int j = 0; j < tiles[0].length; j++) {
            int i = 0;
            while (i < tiles.length) {
                ArrayList<Tile> tl = new ArrayList<>();
                int row = i, col = j;
                while (i < tiles.length && tiles[i][j] != null) {
                    tl.add(tiles[i][j]);
                    i++;
                }
                if (tl.size() > 1) {
                    Tile[] nTiles = new Tile[tl.size()];
                    words.add(new Word(tl.toArray(nTiles), row, col, true));
                }
                i++;
            }
        }

        return words;
    }

    public ArrayList<Word> getWords(Word w) {
        Tile[][] tiles = getTiles(); // clone tiles
        ArrayList<Word> before = getAllWords(tiles);
        // check the placement of the new word w
        int row = w.getRow();
        int col = w.getCol();
        for (Tile t : w.getTiles()) {
            tiles[row][col] = t;
            if (w.isVertical())
                row++;
            else
                col++;
        }
        ArrayList<Word> after = getAllWords(tiles);
        after.removeAll(before); // substruct of old words list
        return after;
    }

    public int tryPlaceWord(Word w) {

        Tile[] ts = w.getTiles();
        int row = w.getRow();
        int col = w.getCol();
        for (int i = 0; i < ts.length; i++) {
            if (ts[i] == null)
                ts[i] = tiles[row][col];
            if (w.isVertical())
                row++;
            else
                col++;
        }

        Word test = new Word(ts, w.getRow(), w.getCol(), w.isVertical());

        int sum = 0;
        if (boardLegal(test)) {
            ArrayList<Word> newWords = getWords(test);
            for (Word nw : newWords) {
                if (dictionaryLegal(nw))
                    sum += getScore(nw);
                else
                    return 0;
            }
        }

        // the placement
        row = w.getRow();
        col = w.getCol();
        for (Tile t : w.getTiles()) {
            tiles[row][col] = t;
            if (w.isVertical())
                row++;
            else
                col++;
        }

        if (isEmpty) {
            isEmpty = false;
            BonusMatrix[7][7] = 0;
        }
        return sum;
    }

    public int getScore(Word word) {
        int col = word.getCol();
        int row = word.getRow();
        int sum = 0;
        int countDW = 0;
        int countTW = 0;

        for (Tile t : word.getTiles()) {
            sum += t.score;
            if (BonusMatrix[row][col] == dl)
                sum += t.score;
            if (BonusMatrix[row][col] == tl)
                sum += t.score * 2;
            if (BonusMatrix[row][col] == dw)
                countDW++;
            if (BonusMatrix[row][col] == tw)
                countTW++;
            if (word.isVertical())
                row++;
            else
                col++;
        }

        if (countDW > 0)
            sum *= (2 * countDW);
        if (countTW > 0)
            sum *= (3 * countTW);

        return sum;

    }

}
