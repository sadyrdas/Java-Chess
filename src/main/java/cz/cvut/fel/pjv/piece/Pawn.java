package cz.cvut.fel.pjv.piece;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.TEAM;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.BoardUtils;
import cz.cvut.fel.pjv.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {

    private final static int[] POSSIBLE_MOVE_COORDINATES = {8, 16, 7, 9};

    public Pawn(final TEAM pieceTEAM, final int piecePosition) {
        super(piecePosition, pieceTEAM, PieceType.PAWN);
    }
    //First of all, I prescribed legal pawn moves. 1) Non-attacking moves, 2) Jumping over one tile.
    @Override
    public Collection<Move> writeLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : POSSIBLE_MOVE_COORDINATES) {

            final int possibleCoordinate = this.piecePosition + (this.pieceTeam.getDirection() * currentCandidateOffset);

            if(!BoardUtils.isValidTileCoordinate(possibleCoordinate)) {
                continue;
            }
            if(currentCandidateOffset == 8 && !board.getTile(possibleCoordinate).IsTileOccupied()) {
                //finish this work(deal with promotion!!!!
                legalMoves.add(new Move.MainMove(board, this, possibleCoordinate));
            } else if(currentCandidateOffset == 16 && this.isFirstMove() &&
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceTeam().isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceTeam().isWhite())) {
                final int behindPossibleCoordinate = this.piecePosition + (this.pieceTeam.getDirection() * 8);
                if(!board.getTile(behindPossibleCoordinate).IsTileOccupied() &&
                        !board.getTile(possibleCoordinate).IsTileOccupied()) {
                    legalMoves.add(new Move.MainMove(board, this, possibleCoordinate));
                }
            //Here I have recorded exceptions for black pawns, namely on the edges of the chessboard.
            }else if(currentCandidateOffset == 7 &&
                    !((BoardUtils.EIGHT_COLUM[this.piecePosition] && this.pieceTeam.isWhite() ||
                    (BoardUtils.FIRST_COLUM[this.piecePosition] && this.pieceTeam.isBlack())))) {
                if(board.getTile(possibleCoordinate).IsTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(possibleCoordinate).getPiece();
                    if(this.pieceTeam != pieceOnCandidate.getPieceTeam()){
                        //Finish this
                        legalMoves.add(new Move.MainMove(board, this, possibleCoordinate));
                    }
                }
            //Here I have recorded exceptions for white pawns, namely on the edges of the chessboard.
            }else if(currentCandidateOffset == 9 &&
                    !((BoardUtils.FIRST_COLUM[this.piecePosition] && this.pieceTeam.isWhite() ||
                            (BoardUtils.EIGHT_COLUM[this.piecePosition] && this.pieceTeam.isBlack())))) {
                if(board.getTile(possibleCoordinate).IsTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(possibleCoordinate).getPiece();
                    if(this.pieceTeam != pieceOnCandidate.getPieceTeam()){
                        //Finish this
                        legalMoves.add(new Move.MainMove(board, this, possibleCoordinate));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Pawn movePiece(Move move) {
        return new Pawn(move.getMovedPiece().getPieceTeam(), move.getDestination());
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}
