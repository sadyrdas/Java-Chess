package cz.cvut.fel.pjv.pgn;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.BoardUtils;
import cz.cvut.fel.pjv.piece.Pawn;

import java.util.Locale;

public class FenUtils {
    private FenUtils() {
        throw new RuntimeException("Mistake!");

    }
    public static Board createGameFromPNG(final String FenString) {
        return null;

    }

    public static String createFenFromGame(final Board board ) {
        return writeBoardText(board) + "" + writeCurrentPlayerText(board) + "" + writeCastleText(board) + "" + writeEnPassant(board) + "" + "0 1";
    }

    private static String writeBoardText(final Board board) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < BoardUtils.TILES ; i++) {
            final String tileText = board.getTile(i).toString();
            stringBuilder.append(tileText);
        }
        stringBuilder.insert(8, "/");
        stringBuilder.insert(17, "/");
        stringBuilder.insert(26, "/");
        stringBuilder.insert(35, "/");
        stringBuilder.insert(44, "/");
        stringBuilder.insert(53, "/");
        stringBuilder.insert(62, "/");
        return stringBuilder.toString().replaceAll("--------", "8")
                                       .replaceAll("-------", "7")
                                       .replaceAll("------", "6")
                                       .replaceAll("-----", "5")
                                       .replaceAll("----", "4")
                                       .replaceAll("---", "3")
                                       .replaceAll("--", "2")
                                       .replaceAll("-", "1");

    }

    private static String writeEnPassant(final Board board) {
        final Pawn enPassantPawn = board.getEnPassantPawn();
        if(enPassantPawn != null) {
            return BoardUtils.getPositionAtCoordinate(enPassantPawn.getPiecePosition() + (8) * enPassantPawn.getPieceTeam().getOppositeDirection());


        }
        return "-";
    }

    private static String writeCastleText(final Board board) {
        final StringBuilder stringBuilder = new StringBuilder();
        if(board.whitePlayer().isKingSideCastleCapable()) {
            stringBuilder.append("K");
        }
        if(board.whitePlayer().isQueenSideCastleCapable()) {
            stringBuilder.append("Q");
        }
        if(board.blackPlayer().isKingSideCastleCapable()) {
            stringBuilder.append("K");
        }
        if(board.blackPlayer().isKingSideCastleCapable()) {
            stringBuilder.append("Q");
        }

        final String result = stringBuilder.toString();
        return result.isEmpty() ? "-" : result;
    }

    private static String writeCurrentPlayerText(final Board board) {
        return board.currentPlayer().toString().substring(0, 1).toLowerCase();
    }

}
