package cz.cvut.fel.pjv.board;

import com.google.common.collect.ImmutableMap;
import cz.cvut.fel.pjv.piece.Piece;

import java.util.HashMap;
import java.util.Map;

public abstract class Tile {
    private static Piece piece;
    protected final int tileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILE_CACHE = createAllPossibleEmptyTiles();
    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < 64; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece ) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILE_CACHE.get(tileCoordinate);
    }
    private Tile(int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }
    public abstract  boolean IsTileOccupied();

    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile {
        EmptyTile(final int coordinate) {
            super(coordinate);
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
    public static final class OccupiedTile extends Tile {
        private final Piece pieceOnTile;
        OccupiedTile(int tileCoordinate, Piece placeOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = placeOnTile;
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
