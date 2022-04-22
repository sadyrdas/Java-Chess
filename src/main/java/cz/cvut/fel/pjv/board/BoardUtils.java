package cz.cvut.fel.pjv.board;

public class BoardUtils {
    public static final boolean[] FIRST_COLUM = initColumn(0);
    public static final boolean[] SECOND_COLUM = initColumn(1);
    public static final boolean[] SEVEN_COLUM = initColumn(6);
    public static final boolean[] EIGTH_COLUM = initColumn(7);

    public static final boolean[] SECOND_ROW = null;
    public static final boolean[] SEVENTH_ROW = null;

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_NOW = 8;
    //Here I want to initialize and control specific tiles in a column on the chessboard.
    private static boolean[] initColumn(int columnNumber) {
        final boolean[] column = new boolean[64];
        do {
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_NOW;
        } while (columnNumber < NUM_TILES);
        return column;
    }

    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate me!");
    }
    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >=0 && coordinate < NUM_TILES;
    }

}
