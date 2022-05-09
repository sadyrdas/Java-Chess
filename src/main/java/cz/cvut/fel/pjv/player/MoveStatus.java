package cz.cvut.fel.pjv.player;

public enum MoveStatus {
    DID {
        @Override
        public boolean isDID() {
            return true;
        }
    },
    ILLEGAL_MOVES {
        @Override
        public boolean isDID() {
            return false;
        }
    },
    PLAYER_STILL_IN_CHECK {
        @Override
        public boolean isDID() {
            return false;
        }
    };

    public abstract boolean isDID();
}
