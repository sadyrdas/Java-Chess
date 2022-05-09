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

public class Rook extends Piece{

    private final static int[] POSSIBLE_MOVE_VECTOR_COORDINATES = {-8, -1, 1, 8};


    public Rook(final TEAM pieceTEAM, final int piecePosition) {
        super(piecePosition, pieceTEAM, PieceType.ROOK);
    }
    ////In this part of the code, I have spelled out all the legal moves of the rook.
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
                        legalMoves.add(new Move.MainMove(board, this, possibleCoordinate));
                    } else {
                        final Piece pieceAtDestination = possibleCoordinateTile.getPiece();
                        final TEAM pieceTEAM = pieceAtDestination.getPieceTeam();
                        if (this.pieceTeam != pieceTEAM) {
                            legalMoves.add(new Move.AttackMove(board, this, possibleCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Rook movePiece(Move move) {
        return new Rook(move.getMovedPiece().getPieceTeam(), move.getDestination());
    }

    @Override
    public String toString() {
        return PieceType.ROOK.toString();
    }

    //Here I have prescribed exceptions for the rook's moves.
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffSEt) {
        return BoardUtils.FIRST_COLUM[currentPosition] && (candidateOffSEt == -1);
    }

    private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffSEt) {
        return BoardUtils.EIGHT_COLUM[currentPosition] && (candidateOffSEt == -1);

    }
}
