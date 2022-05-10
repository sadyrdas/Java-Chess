package cz.cvut.fel.pjv.board;

import java.util.Map;

//This class describes exceptions for all pieces on the board.
public class BoardUtils {
    public static final boolean[] FIRST_COLUM = initColumn(0);
    public static final boolean[] SECOND_COLUM = initColumn(1);
    public static final boolean[] SEVEN_COLUM = initColumn(6);
    public static final boolean[] EIGHT_COLUM = initColumn(7);


    public static final boolean[] FIRST_ROW = initRow(0);
    public static final boolean[] SECOND_ROW = initRow(8);
    public static final boolean[] THIRD_ROW = initRow(16);
    public static final boolean[] FOURTH_ROW = initRow(24);
    public static final boolean[] FIFTH_ROW = initRow(32);
    public static final boolean[] SIXTH_ROW = initRow(40);
    public static final boolean[] SEVENTH_ROW = initRow(48);
    public static final boolean[] EIGHT_ROW = initRow(56);

    //finish later
//    public static final String[] ALGEBRAIC_NOTATION = initalizeAlgebraicNotation();
//    public static final Map<String, Integer> POSITION_TO_COORDINATE = initalizePositCoordinToMap();

    public static final int TILES = 64;
    public static final int TILES_PER_ROW = 8;

    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate me!");}

    //Here I want to initialize and control specific tiles in a column on the chessboard.
    private static boolean[] initColumn(int columnNumber) {
        final boolean[] column = new boolean[64];
        do {
            column[columnNumber] = true;
            columnNumber += TILES_PER_ROW;
        } while (columnNumber < TILES);
        return column;
    }

    private static boolean[] initRow(int rowNumber) {
        final boolean[] row = new boolean[TILES];
        do {
            row[rowNumber] = true;
            rowNumber++;
        }while (rowNumber % TILES_PER_ROW != 0 );
        return row;
    }

    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >=0 && coordinate < TILES;
    }
//finish later
//    public static int getCoordinateAtPosition(final String position) {
//        return POSITION_TO_COORDINATE.get(position);

//    }
//    public static int getPositionAtCoordinate(final int coordinate) {
//        return ALGEBRAIC_NOTATION[coordinate];

//    }
}
