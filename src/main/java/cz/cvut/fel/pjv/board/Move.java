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
//Finish later
    public abstract Board execution();

    public static final class MainMove extends Move {

        public MainMove(final Board board, final Piece piece,
                        final int destinationCoordinate) {
            super(board, piece, destinationCoordinate);
        }

        @Override
        public Board execution() {
            return null;
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

}
