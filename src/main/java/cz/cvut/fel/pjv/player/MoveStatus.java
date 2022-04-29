package cz.cvut.fel.pjv.player;

public enum MoveStatus {
    DIED{
        @Override
        boolean isDIED() {
            return true;
        }
    },
    ILLEGAL_MOVES {
        @Override
        boolean isDIED() {
            return false;
        }
    },
    PLAYER_STILL_IN_CHECK {
        @Override
        boolean isDIED() {
            return false;
        }
    };

    abstract boolean isDIED();
}
