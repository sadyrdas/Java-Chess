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

    Piece(final int piecePosition, final TEAM pieceTeam, final PieceType pieceType, final boolean isFirstMove) {
        this.piecePosition = piecePosition;
        this.pieceTeam = pieceTeam;
        this.pieceType = pieceType;
        this.isFirstMove = isFirstMove;
        this.cachedHashCode = computeHashCode();
    }
    //This describes like piece = movedPiece
    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceTeam.hashCode();
        result = 31 * result + piecePosition;
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
        return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() &&
                pieceTeam == otherPiece.getPieceTeam() && isFirstMove == otherPiece.isFirstMove();
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

    public int getPieceValue() {
        return this.pieceType.getPieceValue();
    }

    public abstract Collection<Move> writeLegalMoves(final Board board);

    public abstract Piece movePiece(Move move);


    public enum PieceType {

        PAWN("P", 10){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("N",30) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B",30) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("R",50) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        },
        QUEEN("Q",90) {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KING("K",150) {
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        };

        private String pieceName;
        private int pieceValue;
        PieceType(final String pieceName, final int pieceValue) {
            this.pieceName = pieceName;
            this.pieceValue = pieceValue;
        }
        @Override
        public String toString() {
            return this.pieceName;
        }

        public int getPieceValue() {
            return this.pieceValue;
        }

        public abstract boolean isKing();

        public abstract boolean isRook();
    }

}
