package cz.cvut.fel.pjv.player;
//this class describes player, who plays on the White side;
import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.TEAM;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.Move;
import cz.cvut.fel.pjv.board.Tile;
import cz.cvut.fel.pjv.piece.Piece;
import cz.cvut.fel.pjv.piece.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cz.cvut.fel.pjv.board.Move.*;

public class WhitePlayer extends Player{
    public WhitePlayer(final Board board, final  Collection<Move> whiteStandardLegalMoves, final Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public TEAM getTeam() {
        return TEAM.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.getBlackPlayer();
    }

    @Override
    protected Collection<Move> writeKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentLegals) {

        final List<Move> kingCastles = new ArrayList<>();
        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            //white king side castle
            if (!this.board.getTile(61).IsTileOccupied() && !this.board.getTile(62).IsTileOccupied()) {
                final Tile rookTile = this.board.getTile(63);

                if(rookTile.IsTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.writeAttacksOnTile(61, opponentLegals).isEmpty() &&
                            Player.writeAttacksOnTile(62, opponentLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                            //finish
                            kingCastles.add(new KingSideCastleMoves(this.board,
                                    this.playerKing, 62,
                                    (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 61 ));
                    }
                }
            }
            //white queen side castle
            if (!this.board.getTile(59).IsTileOccupied() && !this.board.getTile(58).IsTileOccupied() &&
                    !this.board.getTile(57).IsTileOccupied()){
                final Tile rookTile = this.board.getTile(56);
                if (rookTile.IsTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    kingCastles.add(new QueenSideCastleMoves(this.board,
                            this.playerKing, 58,
                            (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 59 ));
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    }
}
