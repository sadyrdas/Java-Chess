package cz.cvut.fel.pjv.board;
//This class describes a chessboard tile.
import com.google.common.collect.ImmutableMap;
import cz.cvut.fel.pjv.piece.Piece;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {
    private static Piece piece;
    protected final int tileCoordinate;
//This function creates all empty tiles.
    private static final Map<Integer, EmptyTile> EMPTY_TILE_CACHE = createAllPossibleEmptyTiles();
    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < BoardUtils.TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece ) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILE_CACHE.get(tileCoordinate);
    }
    private Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }
    public abstract  boolean IsTileOccupied();

    public abstract Piece getPiece();

    public int getTileCoordinate() {
        return this.tileCoordinate;
    }

    //This class describes all empty tiles.
    public static final class EmptyTile extends Tile {
        EmptyTile(final int coordinate) {
            super(coordinate);
        }

        @Override
        public String toString() {
            return ":)";
        }

        @Override
        public boolean IsTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }
    //This class describes all occupied tiles.
    public static final class OccupiedTile extends Tile {
        private final Piece pieceOnTile;
        OccupiedTile(int tileCoordinate, final Piece placeOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = placeOnTile;
        }
        @Override
        public String toString() {
            return getPiece().getPieceTeam().isBlack() ? getPiece().toString().toLowerCase() :
                    getPiece().toString();
        }
        @Override
        public boolean IsTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}
