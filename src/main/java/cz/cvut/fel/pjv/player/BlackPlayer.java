package cz.cvut.fel.pjv.player;

//this class describes player, who plays on the Black side

import cz.cvut.fel.pjv.TEAM;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.Move;
import cz.cvut.fel.pjv.piece.Piece;
import java.util.Collection;


public class BlackPlayer extends Player{
    public BlackPlayer(final Board board, final Collection<Move> whiteStandardLegalMoves, final Collection<Move> blackStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public TEAM getTeam() {
        return TEAM.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.getWhitePlayer();
    }

    @Override
    protected Collection<Move> writeKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals) {
        return null;
    }
}
