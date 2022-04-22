package cz.cvut.fel.pjv.piece;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.Alliance;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.BoardUtils;
import cz.cvut.fel.pjv.board.Move;
import cz.cvut.fel.pjv.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends Piece{

    private final static int[] POSSIBLE_MOVE_VECTOR_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};

    Queen(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }
//In this part of the code, I have spelled out all the legal moves of the queen.
    @Override
    public Collection<Move> writeLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateCoordinateOffset : POSSIBLE_MOVE_VECTOR_COORDINATES) {
            int possibleCoordinate = this.piecePosition;
            while (BoardUtils.isValidTileCoordinate(possibleCoordinate)) {
                if (isFirstColumnExclusion(possibleCoordinate, candidateCoordinateOffset) ||
                        isEigthColumnExclusion(possibleCoordinate, candidateCoordinateOffset)) {
                    break;
                }
                possibleCoordinate += candidateCoordinateOffset;
                if (BoardUtils.isValidTileCoordinate(possibleCoordinate)) {
                    final Tile possibleCoordinateTile = board.getTile(possibleCoordinate);
                    if (possibleCoordinateTile.IsTileOccupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, possibleCoordinate));
                    } else {
                        final Piece pieceAtDestination = possibleCoordinateTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(new Move.AttackMove(board, this, possibleCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
    //Here I have prescribed exceptions for the queen's moves.
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffSEt) {
        return BoardUtils.FIRST_COLUM[currentPosition] && (candidateOffSEt == -1 || currentPosition == -9 || candidateOffSEt == 7);
    }

    private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffSEt) {
        return BoardUtils.EIGTH_COLUM[currentPosition] && (currentPosition == -7 || candidateOffSEt == 1 || candidateOffSEt == 9);

    }
}
