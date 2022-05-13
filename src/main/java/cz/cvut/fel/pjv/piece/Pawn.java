package cz.cvut.fel.pjv.piece;

import com.google.common.collect.ImmutableList;
import cz.cvut.fel.pjv.TEAM;
import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.BoardUtils;
import cz.cvut.fel.pjv.board.Move;
import cz.cvut.fel.pjv.board.Move.MainMove;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cz.cvut.fel.pjv.board.Move.*;

public class Pawn extends Piece {

    private final static int[] POSSIBLE_MOVE_COORDINATES = {8, 16, 7, 9};

    public Pawn(final TEAM pieceTEAM, final int piecePosition) {
        super(piecePosition, pieceTEAM, PieceType.PAWN, true);
    }
    //this second constructor in all of pieces describes not first move pieces;
    public Pawn(final TEAM pieceTeam, final int piecePosition, final boolean isFirstMove) {
        super(piecePosition, pieceTeam, PieceType.PAWN, isFirstMove);
    }
    //First of all, I prescribed legal pawn moves. 1) Non-attacking moves, 2) Jumping over one tile.
    @Override
    public Collection<Move> writeLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : POSSIBLE_MOVE_COORDINATES) {

            int possibleCoordinate = this.piecePosition + (this.pieceTeam.getDirection() * currentCandidateOffset);

            if (!BoardUtils.isValidTileCoordinate(possibleCoordinate)) {
                continue;
            }
            //normal move and pawnPromotion
            if (currentCandidateOffset == 8 && !board.getTile(possibleCoordinate).IsTileOccupied()) {
                if (this.pieceTeam.isPawnPromotion(possibleCoordinate)) {
                    legalMoves.add(new PawnPromotion (new PawnMove(board, this, possibleCoordinate)));

                } else {
                    legalMoves.add(new PawnMove(board, this, possibleCoordinate));
                }
                //pawn jump
                }
            else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    ((BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceTeam().isWhite()) ||
                            (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceTeam().isBlack()))) {
                final int behindPossibleCoordinate = this.piecePosition + (this.pieceTeam.getDirection() * 8);
                if (!board.getTile(behindPossibleCoordinate).IsTileOccupied() &&
                        !board.getTile(possibleCoordinate).IsTileOccupied()) {
                    legalMoves.add(new PawnJump(board, this, possibleCoordinate));
                }
                // Here one of way for Pawn attack and pawnPromotion and EnPassantPawn
            } else if (currentCandidateOffset == 7 &&
                    !((BoardUtils.EIGHT_COLUM[this.piecePosition] && this.pieceTeam.isWhite() ||
                            (BoardUtils.FIRST_COLUM[this.piecePosition] && this.pieceTeam.isBlack())))) {
                if (board.getTile(possibleCoordinate).IsTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(possibleCoordinate).getPiece();
                    if (this.pieceTeam != pieceOnCandidate.getPieceTeam()) {
                        if (this.pieceTeam.isPawnPromotion(possibleCoordinate)) {
                            legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, possibleCoordinate, pieceOnCandidate)));

                        }else {
                            legalMoves.add(new PawnAttackMove(board, this, possibleCoordinate, pieceOnCandidate));
                        }
                    }
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                        (this.piecePosition + (this.pieceTeam.getOppositeDirection()))) {

                    final Piece pieceOnCandidate = board.getEnPassantPawn();
                    if (this.pieceTeam != pieceOnCandidate.getPieceTeam()) {
                        legalMoves.add(new PawnEnPassantAttackMove(board, this, possibleCoordinate, pieceOnCandidate));
                        }

                }
                // Here one of way for Pawn attack and pawnPromotion and EnPassantPawn
            } else if (currentCandidateOffset == 9 &&
                    !((BoardUtils.FIRST_COLUM[this.piecePosition] && this.pieceTeam.isWhite() ||
                            (BoardUtils.EIGHT_COLUM[this.piecePosition] && this.pieceTeam.isBlack())))) {
                if (board.getTile(possibleCoordinate).IsTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(possibleCoordinate).getPiece();
                    if (this.pieceTeam != pieceOnCandidate.getPieceTeam()) {
                        if (this.pieceTeam.isPawnPromotion(possibleCoordinate)) {
                            legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, possibleCoordinate, pieceOnCandidate)));


                        } else {
                            legalMoves.add(new PawnAttackMove(board, this, possibleCoordinate, pieceOnCandidate));
                        }
                    }
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                        (this.piecePosition - (this.pieceTeam.getOppositeDirection()))) {
                    final Piece pieceOnCandidate = board.getEnPassantPawn();
                    if (this.pieceTeam != pieceOnCandidate.getPieceTeam()) {
                        legalMoves.add(new PawnEnPassantAttackMove(board, this, possibleCoordinate, pieceOnCandidate));
                        }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }
    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getMovedPiece().getPieceTeam(), move.getDestination());
    }


    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }

    public Piece getPromotionPiece() {
        return new Queen(this.pieceTeam, this.piecePosition, false);
    }
}
