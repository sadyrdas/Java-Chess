package cz.cvut.fel.pjv.piece;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.Alliance;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.BoardUtils;
import cz.cvut.fel.pjv.board.Move;
import cz.cvut.fel.pjv.board.Tile;

import java.util.ArrayList;
import java.util.List;
// Vytvořil jsem logiku koně pohybů podle obrázků na internetu,
// které zobrazovaly pravidla, podle kterých kún chodí.
public class Knight extends Piece {

    private final static int[] POSSIBLE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    //Nejprve jsem identifikoval všechny možné pohyby koně jako seznam
//S pomocí smyčky jsem je prošel a pokud dlaždice existuje a je zdarma,
// pak jsem jen dal svého koně tam, pokud ne, pak dávám informace o tom, co je Aliance (bílá nebo černá).
    @Override
    public List<Move> writeLegalMoves(Board board) {
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
                if (possibleCoordinateTile.IsTileOccupied()) {
                    legalMoves.add(new Move());
                } else {
                    final Piece pieceAtDestination = possibleCoordinateTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new Move());
                    }
                }
            }

        }
        return ImmutableList.copyOf(legalMoves);
    }

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
        return BoardUtils.EIGTH_COLUM[currentPosition] && (candidateOffset == -15 || candidateOffset == -6 ||
                candidateOffset == 10 || candidateOffset == 17);
    }

}
