package cz.cvut.fel.pjv.board;

import cz.cvut.fel.pjv.piece.Pawn;
import cz.cvut.fel.pjv.piece.Piece;
import cz.cvut.fel.pjv.piece.Rook;

import static cz.cvut.fel.pjv.board.Board.*;

//This class describes all moves off pieces.
public abstract class Move {

    protected final Board board;
    protected final Piece movedPiece;
    protected final int destinationCoordinate;
    protected final boolean isFirstMove;


    public static final Move NO_MOVE = new NoMoves();

    private Move(final Board board,
                 final Piece movedPiece,
                 final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
        this.isFirstMove = movedPiece.isFirstMove();
    }
    //finish this
    private Move(final Board board, final int destinationCoordinate) {
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPiece = null;
        this.isFirstMove = false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.destinationCoordinate;
        result = prime * result + this.movedPiece.hashCode();
        result = prime * result + this.movedPiece.getPiecePosition();
        return result;
    }

    @Override
    public boolean equals(final Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Piece)) {
            return false;
        }
        final Move otherMove = (Move) other;
        return getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
                getDestination() == otherMove.getDestination() &&
                getMovedPiece().equals(otherMove.getMovedPiece());


    }

    public int getCurrentCoordinate() {
        return this.movedPiece.getPiecePosition();
    }
    public int getDestination() {
        return this.destinationCoordinate;
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public boolean isAttack() {
        return false;
    }

    public boolean isCastlingMove() {
        return false;
    }

    public Piece getAttackPiece() {
        return null;
    }

    //this describes build of new board in the old board for all pieces, which are NOT MOVED!!!
    public Board execution() {
        final Builder builder = new Builder();
        for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
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

        public MainMove(final Board board, final Piece movedPiece,
                        final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MainMove && super.equals(other);
        }
//finish later
//        @Override
//        public String toString() {
//            return movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
//        }
     }

    public static class AttackMove extends Move {

        final Piece attackedPiece;

        public AttackMove(final Board board, final Piece movedPiece,
                          final int destinationCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }

            if(!(other instanceof AttackMove)) {
                return false;
            }
            final AttackMove  otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) && getAttackPiece().equals(otherAttackMove.getAttackPiece());
        }

        @Override
        public Board execution() {
            return null;
        }

        @Override
        public boolean isAttack() {
            return true;
        }
        @Override
        public Piece getAttackPiece() {
            return this.attackedPiece;
        }
    }

    public static final class PawnMove extends Move {

        public PawnMove(final Board board, final Piece movedPiece,
                        final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static class PawnAttackMove extends AttackMove {

        public PawnAttackMove(final Board board, final Piece movedPiece,
                              final int destinationCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    }

    public static final class PawnEnPassantAttackMove extends PawnAttackMove {
        public PawnEnPassantAttackMove(final Board board, final Piece movedPiece,
                                       final int destinationCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    }

    public static final class PawnJump extends Move {

        public PawnJump(final Board board, final Piece movedPiece,
                        final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
        @Override
        public Board execution() {
            final Builder builder = new Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPiece(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getTeam());
            return builder.build();
        }
    }

    static abstract class CastleMoves extends Move {

        protected Rook castleRook;
        protected final int castleRookStart;
        protected final int castleRookDestination;

        public CastleMoves(final Board board, final Piece movedPiece,
                           final int destinationCoordinate,
                           final Rook castleRook,
                           final int castleRookStart,
                           final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination =castleRookDestination;
        }
        public Rook getCastleRook() {
            return this.castleRook;
        }
        @Override
        public boolean isCastlingMove() {
            return true;
        }

        @Override
        public Board execution() {
            final Builder builder = new Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            //look in the first move on normal pieces
            builder.setPiece(new Rook(this.castleRook.getPieceTeam(), this.castleRookDestination));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getTeam());
            return builder.build();
        }
    }

    public static final class KingSideCastleMoves extends CastleMoves {

        public KingSideCastleMoves(final Board board, final Piece movedPiece,
                                   final int destinationCoordinate,
                                   final Rook castleRook,
                                   final int castleRookStart,
                                   final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }
        @Override
        public String toString() {
            return "0-0";
        }
    }

    public static final class QueenSideCastleMoves extends CastleMoves {

        public QueenSideCastleMoves(final Board board, final Piece movedPiece,
                                    final int destinationCoordinate,
                                    final Rook castleRook,
                                    final int castleRookStart,
                                    final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }
        @Override
        public String toString() {
            return "0-0-0";
        }
    }

    public static final class NoMoves extends Move {

        public NoMoves() {
            super(null, null,-1);
        }
        @Override
        public Board execution() {
            throw new RuntimeException("cannot execute no moves");
        }
    }

    public static class MoveFactory {

        private MoveFactory() {
            throw new RuntimeException("Not instantiatable!");
        }

        public static Move createMove(final Board board,
                                      final int currentCoordinate,
                                      final int destinationCoordinate) {
            for (final Move move : board.getAllLegalMoves()) {
                if (move.getCurrentCoordinate() == currentCoordinate &&
                        move.getDestination() == destinationCoordinate) {
                    return move;
                }
            }
            return NO_MOVE;
        }
    }
}
