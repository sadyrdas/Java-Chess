package cz.cvut.fel.pjv.piece;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.TEAM;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.BoardUtils;
import cz.cvut.fel.pjv.board.Move;
import cz.cvut.fel.pjv.board.Tile;

import java.util.ArrayList;
import java.util.List;

import static cz.cvut.fel.pjv.board.Move.*;

//I created the logic of the horse's movements according to pictures on the internet,
// which depicted the rules by which the horse walks.
public class Knight extends Piece {

    private final static int[] POSSIBLE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final TEAM pieceTEAM, final int piecePosition) {
        super(piecePosition, pieceTEAM, PieceType.KNIGHT);
    }

    //First, I identified all the possible movements of the horse as a list
    //With the help of a loop I passed them and if the tile exists and is free,
    //then I just put my horse there, if not, then I give information about what the alliance is (white or black).
    @Override
    public List<Move> writeLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffSet : POSSIBLE_MOVE_COORDINATES) {

            final int possibleCoordinate = this.piecePosition + currentCandidateOffSet;

            if (BoardUtils.isValidTileCoordinate(possibleCoordinate)) {
                if (IsFirstColumExclusion(this.piecePosition, currentCandidateOffSet) ||
                        IsSecondColumExclusion(this.piecePosition, currentCandidateOffSet) ||
                        IsSevenColumExclusion(this.piecePosition, currentCandidateOffSet) ||
                        IsEightColumExclusion(this.piecePosition, currentCandidateOffSet)) {
                    continue;
                }

                final Tile possibleCoordinateTile = board.getTile(possibleCoordinate);
                if (!possibleCoordinateTile.IsTileOccupied()) {
                    legalMoves.add(new MainMove(board, this, possibleCoordinate));
                } else {
                    final Piece pieceAtDestination = possibleCoordinateTile.getPiece();
                    final TEAM pieceTEAM = pieceAtDestination.getPieceTeam();
                    if (this.pieceTeam != pieceTEAM) {
                        legalMoves.add(new AttackMove(board, this, possibleCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Knight movePiece(Move move) {
        return new Knight(move.getMovedPiece().getPieceTeam(), move.getDestination());
    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }

// Here I have the exceptions that are made when moving the stove to a certain tile.
    private static boolean IsFirstColumExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUM[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
                candidateOffset == 6 || candidateOffset == 15);
    }

    private static boolean IsSecondColumExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SECOND_COLUM[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
    }

    private static boolean IsSevenColumExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SEVEN_COLUM[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);

    }

    private static boolean IsEightColumExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHT_COLUM[currentPosition] && (candidateOffset == -15 || candidateOffset == -6 ||
                candidateOffset == 10 || candidateOffset == 17);
    }

}
