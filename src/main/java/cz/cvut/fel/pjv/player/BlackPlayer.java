package cz.cvut.fel.pjv.player;

//this class describes player, who plays on the Black side

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


public class BlackPlayer extends Player{
    public BlackPlayer(final Board board, final Collection<Move> whiteStandardLegalMoves, final Collection<Move> blackStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public TEAM getTeam() {
        return TEAM.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.getWhitePlayer();
    }

    @Override
    protected Collection<Move> writeKingCastles(final Collection<Move> playerLegals,
                                                final Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();
        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            //white king side castle
            if (!this.board.getTile(5).IsTileOccupied() && !this.board.getTile(6).IsTileOccupied()) {
                final Tile rookTile = this.board.getTile(7);

                if(rookTile.IsTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.writeAttacksOnTile(5, opponentLegals).isEmpty() &&
                            Player.writeAttacksOnTile(6, opponentLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        //finish
                        kingCastles.add(new KingSideCastleMoves(this.board,
                                this.playerKing, 6,
                                (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 5 ));
                    }
                }
            }
            //white queen side castle
            if (!this.board.getTile(1).IsTileOccupied() &&
                    !this.board.getTile(2).IsTileOccupied() &&
                    !this.board.getTile(3).IsTileOccupied()){
                final Tile rookTile = this.board.getTile(0);
                if (rookTile.IsTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    kingCastles.add(new QueenSideCastleMoves(this.board,
                            this.playerKing, 2,
                            (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 3 ));
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    }
}

