package cz.cvut.fel.pjv.board;

public class BoardUtils {
    public static final boolean[] FIRST_COLUM = null;
    public static final boolean[] SECOND_COLUM = null;
    public static final boolean[] SEVEN_COLUM = null;
    public static final boolean[] EIGTH_COLUM = null;

    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate no!");
    }
    public static boolean isValidTileCoordinate(int coordinate) {
        return coordinate >=0 && coordinate < 64;
    }

}
