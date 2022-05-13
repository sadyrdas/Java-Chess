package cz.cvut.fel.pjv.board;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import cz.cvut.fel.pjv.TEAM;
import cz.cvut.fel.pjv.piece.*;
import cz.cvut.fel.pjv.player.BlackPlayer;
import cz.cvut.fel.pjv.player.Player;
import cz.cvut.fel.pjv.player.WhitePlayer;

import java.util.*;

//This class will describe the board.
public class Board {

    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;
    private final Pawn enPassantPawn;

    private Board(final Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = writeActivePieces(this.gameBoard, TEAM.WHITE);
        this.blackPieces = writeActivePieces(this.gameBoard, TEAM.BLACK);
        this.enPassantPawn = builder.enPassantPawn;
        final Collection<Move> whiteStandardLegalMoves = writeLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardLegalMoves = writeLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);

        //Here i need finish method for choosing Team for currentPlayer;
        this.currentPlayer = builder.nextMoveMaker.pickplayer(this.whitePlayer, this.blackPlayer);
    }
// this method will print board in text show ASCII
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i < BoardUtils.TILES; i++) {
            final String tileText = this.gameBoard.get(i).toString();
            builder.append(String.format("%3s", tileText));
            if((i + 1) % BoardUtils.TILES_PER_ROW == 0) {
                builder.append("\n");
            }
        } return builder.toString();
    }
    public WhitePlayer whitePlayer() {
        return this.whitePlayer;
    }

    public BlackPlayer blackPlayer() {
        return this.blackPlayer;
    }
    public Collection<Piece> getWhitePieces() {
        return this.whitePieces;
    }
    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    public Player getWhitePlayer() {
        return this.whitePlayer;
    }

    public Player getBlackPlayer() {
        return this.blackPlayer;
    }

    public Player currentPlayer() {
        return this.currentPlayer;
    }
    public Pawn getEnPassantPawn() {
        return this.enPassantPawn;
    }

    //This for loop describes all legal moves for starting board
    private Collection<Move> writeLegalMoves(Collection<Piece> pieces) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final Piece piece : pieces) {
            legalMoves.addAll(piece.writeLegalMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }
    // This part of code describes all and return all pieces, which stay on the piece and their alliance
    private static Collection<Piece> writeActivePieces(final List<Tile> gameBoard, final TEAM TEAM) {

        final List<Piece> activePieces = new ArrayList<>();
        for (final Tile tile: gameBoard) {
            if (tile.IsTileOccupied()) {
                final Piece piece = tile.getPiece();
                if (piece.getPieceTeam() == TEAM) {
                    activePieces.add(piece);
                }
            }
        }

        return ImmutableList.copyOf(activePieces);
    }

    public Tile getTile(final int tileCoordinate) {
        return gameBoard.get(tileCoordinate);
    }
    //In this for loop, i am going to connect tile with identification(for every tile unique identification)
    private static List<Tile> createGameBoard(final Builder builder) {
        final Tile[] tiles = new Tile[BoardUtils.TILES];
        for (int i = 0; i < BoardUtils.TILES; i ++) {
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    public static Board createStandardBoard() {
        final Builder builder = new Builder();
        // Black Alliance
        builder.setPiece(new Rook(TEAM.BLACK, 0));
        builder.setPiece(new Knight(TEAM.BLACK, 1));
        builder.setPiece(new Bishop(TEAM.BLACK, 2));
        builder.setPiece(new Queen(TEAM.BLACK, 3));
        builder.setPiece(new King(TEAM.BLACK, 4, true, true));
        builder.setPiece(new Bishop(TEAM.BLACK, 5));
        builder.setPiece(new Knight(TEAM.BLACK, 6));
        builder.setPiece(new Rook(TEAM.BLACK, 7));
        builder.setPiece(new Pawn(TEAM.BLACK, 8));
        builder.setPiece(new Pawn(TEAM.BLACK, 9));
        builder.setPiece(new Pawn(TEAM.BLACK, 10));
        builder.setPiece(new Pawn(TEAM.BLACK, 11));
        builder.setPiece(new Pawn(TEAM.BLACK, 12));
        builder.setPiece(new Pawn(TEAM.BLACK, 13));
        builder.setPiece(new Pawn(TEAM.BLACK, 14));
        builder.setPiece(new Pawn(TEAM.BLACK, 15));
        // White Alliance
        builder.setPiece(new Pawn(TEAM.WHITE, 48));
        builder.setPiece(new Pawn(TEAM.WHITE, 49));
        builder.setPiece(new Pawn(TEAM.WHITE, 50));
        builder.setPiece(new Pawn(TEAM.WHITE, 51));
        builder.setPiece(new Pawn(TEAM.WHITE, 52));
        builder.setPiece(new Pawn(TEAM.WHITE, 53));
        builder.setPiece(new Pawn(TEAM.WHITE, 54));
        builder.setPiece(new Pawn(TEAM.WHITE, 55));
        builder.setPiece(new Rook(TEAM.WHITE, 56));
        builder.setPiece(new Knight(TEAM.WHITE, 57));
        builder.setPiece(new Bishop(TEAM.WHITE, 58));
        builder.setPiece(new Queen(TEAM.WHITE, 59));
        builder.setPiece(new King(TEAM.WHITE, 60, true, true));
        builder.setPiece(new Bishop(TEAM.WHITE, 61));
        builder.setPiece(new Knight(TEAM.WHITE, 62));
        builder.setPiece(new Rook(TEAM.WHITE, 63));
        //white to move(because first move in the chess is white_move)
        builder.setMoveMaker(TEAM.WHITE);
        //build the board
        return builder.build();
    }

    public Iterable<Move> getAllLegalMoves() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(), this.blackPlayer.getLegalMoves()));
    }

    //It will be a builder of gameboard;
    public static class Builder {
        Map<Integer, Piece> boardConfig;
        TEAM nextMoveMaker;
        Pawn enPassantPawn;

        public Builder() {
            this.boardConfig = new HashMap<>();
        }

        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final TEAM nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }
        public void setEnPassantPiece(final Pawn movedPawn) {
            this.enPassantPawn = enPassantPawn;
        }
        public Board build() {
            return new Board(this);
        }

    }
}
