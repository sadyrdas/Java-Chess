package cz.cvut.fel.pjv.piece;

import cz.cvut.fel.pjv.Alliance;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.Move;

import java.util.Collection;


public abstract class Piece {
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;

    Piece(final int piecePosition, final Alliance pieceAlliance ) {
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        //finish this work
        this.isFirstMove = false;
    }
    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    public abstract Collection<Move> writeLegalMoves(final Board board);


}
