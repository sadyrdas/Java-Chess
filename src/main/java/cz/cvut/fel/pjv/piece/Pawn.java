package cz.cvut.fel.pjv.piece;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.Alliance;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.BoardUtils;
import cz.cvut.fel.pjv.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {

    private final static int[] POSSIBLE_MOVE_COORDINATES = {8, 16, 7, 9};

    Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }
    //First of all, I prescribed legal pawn moves. 1) Non-attacking moves, 2) Jumping over one tile.
    @Override
    public Collection<Move> writeLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : POSSIBLE_MOVE_COORDINATES) {

            final int possibleCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);

            if(!BoardUtils.isValidTileCoordinate(possibleCoordinate)) {
                continue;
            }
            if(currentCandidateOffset == 8 && board.getTile(possibleCoordinate).IsTileOccupied()) {
                //finish this work(deal with promotion!!!!
                legalMoves.add(new Move.MajorMove(board, this, possibleCoordinate));
            } else if(currentCandidateOffset == 16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite())) {
                final int behindPossibleCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                if(!board.getTile(behindPossibleCoordinate).IsTileOccupied() &&
                        !board.getTile(possibleCoordinate).IsTileOccupied()) ;
                legalMoves.add(new Move.MajorMove(board, this, possibleCoordinate));
            //Here I have recorded exceptions for black pawns, namely on the edges of the chessboard.
            }else if(currentCandidateOffset == 7 &&
                    !((BoardUtils.EIGTH_COLUM[this.piecePosition] && this.pieceAlliance.isWhite() ||
                    (BoardUtils.FIRST_COLUM[this.piecePosition] && this.pieceAlliance.isBlack())))) {
                if(board.getTile(possibleCoordinate).IsTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(possibleCoordinate).getPiece();
                    if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                        //Finish this
                        legalMoves.add(new Move.MajorMove(board, this, possibleCoordinate));
                    }
                }
            //Here I have recorded exceptions for white pawns, namely on the edges of the chessboard.
            }else if(currentCandidateOffset == 9 &&
                    !((BoardUtils.FIRST_COLUM[this.piecePosition] && this.pieceAlliance.isWhite() ||
                            (BoardUtils.EIGTH_COLUM[this.piecePosition] && this.pieceAlliance.isBlack())))) {
                if(board.getTile(possibleCoordinate).IsTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(possibleCoordinate).getPiece();
                    if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                        //Finish this
                        legalMoves.add(new Move.MajorMove(board, this, possibleCoordinate));
                    }
                }

            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
}
