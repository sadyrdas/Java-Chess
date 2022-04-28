package cz.cvut.fel.pjv.player;
//this class describes Player, his king and legal moves, and his opponent's legal moves;
import cz.cvut.fel.pjv.TEAM;
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
    //There will be implemented all moves, which connected with Players;
    public boolean isInCheck() {
        return false;
    }

    public boolean isInCheckMate() {
        return false;
    }

    public boolean isInMate() {
        return false;
    }

    public boolean isSuperPiece() {
        return false;
    }
    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public MoveTransition makeMove(final Move move) {
        return null;
    }
    public abstract Collection<Piece> getActivePieces();
    public abstract TEAM getTeam();
    public abstract Player getOpponent();
}
