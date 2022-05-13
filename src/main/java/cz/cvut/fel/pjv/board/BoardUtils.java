package cz.cvut.fel.pjv.board;


import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
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
    public static final String[] ALGEBRAIC_NOTATION = initializeAlgebraicNotation();
    public static final Map<String, Integer> POSITION_TO_COORDINATE = initializePositCoordinateToMap();



    public static final int TILES = 64;
    public static final int TILES_PER_ROW = 8;

    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate me!");}

    private static String[] initializeAlgebraicNotation() {
        return new String[] {
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"
        };
    }

    private static Map<String, Integer> initializePositCoordinateToMap() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for (int i = 0; i< TILES; i++) {
            positionToCoordinate.put(ALGEBRAIC_NOTATION[i], i );
        }
        return ImmutableMap.copyOf(positionToCoordinate);
    }


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
        } while (rowNumber % TILES_PER_ROW != 0);
        return row;
    }

    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >=0 && coordinate < TILES;
    }
//finish later
    public static int getCoordinateAtPosition(final String position) {
        return POSITION_TO_COORDINATE.get(position);
    }
    public static String getPositionAtCoordinate(final int coordinate) {
        return ALGEBRAIC_NOTATION[coordinate];

    }
}
