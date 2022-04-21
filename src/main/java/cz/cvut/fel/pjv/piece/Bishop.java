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

public class Bishop extends Piece {

    private final static int[] POSSIBLE_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9};

    Bishop(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    //Nejprve jsem identifikoval všechny možné pohyby bispkupa jako seznam
//S pomocí smyčky jsem je prošel a pokud dlaždice existuje a je zdarma nebo ma nejaky jiny kamen,
// pak udelam break, abychom mohl vyjit z smycky
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

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffSEt) {
        return BoardUtils.FIRST_COLUM[currentPosition] && (currentPosition == -9) || (candidateOffSEt == 7);
    }

    private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffSEt) {
        return BoardUtils.EIGTH_COLUM[currentPosition] && (currentPosition == -7) || (candidateOffSEt == 9);

    }
}
