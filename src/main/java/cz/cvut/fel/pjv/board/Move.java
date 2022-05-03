package cz.cvut.fel.pjv.board;

import cz.cvut.fel.pjv.piece.Piece;
//This class describes all moves off pieces.
public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    Move(final Board board,
         final Piece piece,
         final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = piece;
        this.destinationCoordinate = destinationCoordinate;
    }

    //this describes build of new board in the old board for all pieces, which are NOT MOVED!!!
    public Board execution() {
        final Board.Builder builder = new Board.Builder();
        for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
            //finished equals methods for pieces!
            if (!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getTeam());
        return builder.build();
    }

    public static final class MainMove extends Move {

        public MainMove(final Board board, final Piece piece,
                        final int destinationCoordinate) {
            super(board, piece, destinationCoordinate);
        }
    }
    public static final class AttackMove extends Move {

        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece piece,
                   final int destinationCoordinate, final Piece attackedPiece) {
            super(board, piece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execution() {
            return null;
        }
    }

    public int getDestination() {
        return this.destinationCoordinate;
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

}
