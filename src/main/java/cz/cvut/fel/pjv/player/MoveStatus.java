package cz.cvut.fel.pjv.player;

public enum MoveStatus {
    DIED{
        @Override
        boolean isDIED() {
            return true;
        }
    };

    abstract boolean isDIED();
}
