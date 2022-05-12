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

public class Bishop extends Piece {

    private final static int[] POSSIBLE_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9};

    public Bishop(final TEAM pieceTEAM, final int piecePosition) {
        super(piecePosition, pieceTEAM, PieceType.BISHOP, true);

    }
    public Bishop(final TEAM pieceTeam, final int piecePosition, final boolean isFirstMove) {
        super(piecePosition, pieceTeam, PieceType.BISHOP, isFirstMove);
    }

    //First, I identified all possible bispkup moves as a list
    //With the help of the loop, I passed them and if the tile exists and is free or has some other stove,
    // then I will make a break so that we can come out of the loop
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
                    if (!possibleCoordinateTile.IsTileOccupied()) {
                        legalMoves.add(new MainMove(board, this, possibleCoordinate));
                    } else {
                        final Piece pieceAtDestination = possibleCoordinateTile.getPiece();
                        final TEAM pieceTEAM = pieceAtDestination.getPieceTeam();
                        if (this.pieceTeam != pieceTEAM) {
                            legalMoves.add(new MainAttackMove(board, this, possibleCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Bishop movePiece(final Move move) {
        return new Bishop(move.getMovedPiece().getPieceTeam(), move.getDestination());
    }

    @Override
    public String toString() {
        return PieceType.BISHOP.toString();
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffSEt) {
        return BoardUtils.FIRST_COLUM[currentPosition] && (candidateOffSEt == -9 || candidateOffSEt == 7);
    }

    private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffSEt) {
        return BoardUtils.EIGHT_COLUM[currentPosition] && (candidateOffSEt == -7 || candidateOffSEt == 9);

    }
}
