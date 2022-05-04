package cz.cvut.fel.pjv.piece;

import cz.cvut.fel.pjv.TEAM;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.Move;

import java.util.Collection;

//This is the general class of all figures, I indicate here the position of the figure and the alliance of the figure (black and white).
public abstract class Piece {
    protected final int piecePosition;
    protected final TEAM pieceTeam;
    protected final PieceType pieceType;
    protected final boolean isFirstMove;
    private final int cachedHashCode;

    Piece(final int piecePosition, final TEAM pieceTeam, final PieceType pieceType) {
        this.pieceTeam = pieceTeam;
        this.piecePosition = piecePosition;
        //finish this work
        this.pieceType = pieceType;
        this.isFirstMove = false;
        this.cachedHashCode = computeHashCode();
    }
    //This describes like piece = movedPiece
    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceTeam.hashCode();
        result = 31* result + piecePosition;
        result = 31 * result + (isFirstMove ? 1:0);
        return result;
    }
    @Override
    public boolean equals(final Object other) {
        if(this == other){
            return true;
        }
        if(!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() && pieceTeam == otherPiece.getPieceTeam() && isFirstMove == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode() {
        return this.cachedHashCode;

    }

    public int getPiecePosition() {return this.piecePosition;}

    public TEAM getPieceTeam() {
        return this.pieceTeam;
    }

    public PieceType getPieceType() {return this.pieceType;}

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    public abstract Collection<Move> writeLegalMoves(final Board board);

    public abstract Piece movePiece(Move move);


    public enum PieceType {

        PAWN("p"){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("k") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("b") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("r") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        QUEEN("q") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        KING("k") {
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        };

        private String pieceName;
        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }
        @Override
        public String toString() {
            return this.pieceName;
        }
        //finish this work
        public abstract boolean isKing();

        public abstract boolean isRook();
    }

}
