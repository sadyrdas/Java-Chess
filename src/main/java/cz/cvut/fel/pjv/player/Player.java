package cz.cvut.fel.pjv.player;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.Move;
import cz.cvut.fel.pjv.piece.King;
import cz.cvut.fel.pjv.piece.Piece;

import java.util.Collection;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;

    Player(final Board board,final Collection<Move> legalMoves, final Collection<Move> opponentMoves){
        this.board =  board;
        this.playerKing = THEKING();
        this.legalMoves = legalMoves;

    }
    private King THEKING() {
        for (final Piece piece: getActivePieces()) {
            if(piece.getPieceType().isKing()){
                return (King) piece;
            }
        }
        throw new RuntimeException("Not here, NOT VALID BOARD!");
    }

    public abstract Collection<Piece> getActivePieces();
}
