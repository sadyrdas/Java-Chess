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

    Piece(final int piecePosition, final TEAM pieceTeam, final PieceType pieceType) {
        this.pieceTeam = pieceTeam;
        this.piecePosition = piecePosition;
        //finish this work
        this.pieceType = pieceType;
        this.isFirstMove = false;;
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

    public enum PieceType {

        PAWN("p"){
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("k") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("b") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK("r") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("q") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING("k") {
            @Override
            public boolean isKing() {
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
    }

}
