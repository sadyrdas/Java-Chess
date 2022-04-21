package cz.cvut.fel.pjv.piece;

import cz.cvut.fel.pjv.Alliance;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.BoardUtils;
import cz.cvut.fel.pjv.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {

    private final static int[] POSSIBLE_MOVE_COORDINATES = {8};

    Pawn(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> writeLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : POSSIBLE_MOVE_COORDINATES) {
            int possibleCoordinate = this.piecePosition + (this.getPieceAlliance().getDirection() * currentCandidateOffset);
            if(!BoardUtils.isValidTileCoordinate(possibleCoordinate)) {
                continue;
            }
            if(currentCandidateOffset == 8 && board.getTile(possibleCoordinate).IsTileOccupied()) {
                //finish this work!!!!

                legalMoves.add(new Move.MajorMove(board, this, possibleCoordinate));
            }

        }

        return legalMoves;
    }
}
