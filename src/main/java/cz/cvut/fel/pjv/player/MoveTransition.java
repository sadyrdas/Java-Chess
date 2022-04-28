package cz.cvut.fel.pjv.player;

import cz.cvut.fel.pjv.board.Board;
import cz.cvut.fel.pjv.board.Move;

public class MoveTransition {
    private final Board transitionBoard;
    private final MoveStatus moveStatus;
    private final Move move;

    public MoveTransition(final Board transitionBoard, MoveStatus moveStatus, Move move ){
        this.transitionBoard = transitionBoard;
        this.moveStatus = moveStatus;
        this.move = move;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
}
