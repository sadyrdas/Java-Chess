package cz.cvut.fel.pjv.player;
//this class describes Player, his king and legal moves, and his opponent's legal moves;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import cz.cvut.fel.pjv.TEAM;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.Move;
import cz.cvut.fel.pjv.piece.King;
import cz.cvut.fel.pjv.piece.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    protected final boolean isInCheck;
    Player(final Board board,final Collection<Move> legalMoves, final Collection<Move> opponentMoves){
        this.board =  board;
        this.playerKing = THEKING();
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, writeKingCastles(legalMoves, opponentMoves)));
        this.isInCheck = !Player.writeAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();

    }

    protected static Collection<Move> writeAttacksOnTile(int piecePosition, Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();
        for(final Move move : moves) {
            if (piecePosition == move.getDestination()) {
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    public King getPlayerKing() {
        return this.playerKing;
    }

    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
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
        return isInCheck;
    }

    public boolean isInCheckMate() {
        return isInCheck && !EscapeMoves();
    }



    public boolean isInMate() {
        return !isInCheck && !EscapeMoves();
    }

    private boolean EscapeMoves() {
        for(final Move move : this.legalMoves) {
            final MoveTransition transition = makeMove(move);
            if(transition.getMoveStatus().isDIED()){
                return true;

            }
        }
        return false;
    }

    public boolean isSuperPiece() {
        return false;
    }
    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public MoveTransition makeMove(final Move move) {
        if(!isMoveLegal(move)) {
            return new MoveTransition(this.board, MoveStatus.ILLEGAL_MOVES , move);
        }
        final Board transitionBoard = move.execution();

        final Collection<Move> kingAttacks = Player.writeAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(), transitionBoard.currentPlayer().getLegalMoves());

        if(!kingAttacks.isEmpty()) {
            return new MoveTransition(this.board, MoveStatus.PLAYER_STILL_IN_CHECK, move);
        }

        return new MoveTransition(transitionBoard, MoveStatus.DIED, move);
    }
    public abstract Collection<Piece> getActivePieces();
    public abstract TEAM getTeam();
    public abstract Player getOpponent();
    protected abstract Collection<Move> writeKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals);
}
