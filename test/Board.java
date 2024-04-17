package test;

public class Board {
    private static final int BOARD_SIZE = 15; // Assuming board size 15x15
    private static Board instance = null;
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

    public boolean boardLegal(Word word) {
        Tile[] wordTiles = word.getTiles();
        int col = word.getCol();
        int row = word.getRow();
        boolean vertical = word.isVertical();

        if (col < 0 || row < 0 || (vertical && row + wordTiles.length > BOARD_SIZE)
                || (!vertical && col + wordTiles.length > BOARD_SIZE)) {
            return false;
        }

        boolean intersectsExistingTiles = false;
        for (Tile tile : wordTiles) {
            if (vertical) {
                if (isValidPosition(col, row) && tiles[col][row] != null) {
                    intersectsExistingTiles = true;
                    break;
                }
                row++;
            } else {
                if (isValidPosition(col, row) && tiles[col][row] != null) {
                    intersectsExistingTiles = true;
                    break;
                }
                col++;
            }
        }

        if (!intersectsExistingTiles) {
            return false;
        }

        for (Tile tile : wordTiles) {
            if (isValidPosition(col, row) && tiles[col][row] != null && !tiles[col][row].equals(tile)) {
                return false;
            }
            if (vertical) {
                row++;
            } else {
                col++;
            }
        }

        return true;
    }

    public boolean dictionaryLegal(Word word) {
        return true;
    }

    public int tryPlaceWord(Word word) {
        int sum = 0;
        int size = word.getTiles().length;
        for (int i = 0; i < size; i++) {
            sum += word.getTiles()[i].score;
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
