package cz.cvut.fel.pjv.piece;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.TEAM;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.BoardUtils;
import cz.cvut.fel.pjv.board.Move;
import cz.cvut.fel.pjv.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cz.cvut.fel.pjv.board.Move.*;

public class King extends Piece{

    private final static int[] POSSIBLE_MOVE_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};


    public King(final TEAM pieceTEAM, final int piecePosition) {
        super(piecePosition, pieceTEAM, PieceType.KING, true);

    }

    public King(final TEAM pieceTeam, final int piecePosition, final boolean isFirstMove, final boolean isCastled, final boolean kingSideCastleCapable, final boolean queenSideCastleCapable) {
        super(piecePosition, pieceTeam, PieceType.KING, isFirstMove);

    }





    //In this part of the code, I have spelled out all the legal moves of the king.
    @Override
    public Collection<Move> writeLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset: POSSIBLE_MOVE_COORDINATES) {
            final int possibleCoordinate = this.piecePosition + currentCandidateOffset;

            if(IsFirstColumExclusion(this.piecePosition, currentCandidateOffset)
                    || IsEightColumExclusion(this.piecePosition, currentCandidateOffset)) {
                continue;
            }

            if(BoardUtils.isValidTileCoordinate(possibleCoordinate)) {
                final Tile possibleCoordinateTile = board.getTile(possibleCoordinate);
                if (!possibleCoordinateTile.IsTileOccupied()) {
                    legalMoves.add(new MainMove(board, this, possibleCoordinate));
                } else {
                    final Piece pieceAtDestination = possibleCoordinateTile.getPiece();
                    final TEAM pieceTEAM = pieceAtDestination.getPieceTeam();
                    if (this.pieceTeam != pieceTEAM) {
                        legalMoves.add(new MainAttackMove(board, this, possibleCoordinate, pieceAtDestination));
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public King movePiece(final Move move) {
        return new King(move.getMovedPiece().getPieceTeam(), move.getDestination(), false, move.isCastlingMove(), false, false);
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }



    //Here I have prescribed exceptions for the king's moves.
    private static boolean IsFirstColumExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUM[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 ||
                candidateOffset == 7);
    }

    private static boolean IsEightColumExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHT_COLUM[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
    }

}
