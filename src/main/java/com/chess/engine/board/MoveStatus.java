package com.chess.engine.board;

public enum MoveStatus {
    COMPLETED {
        @Override
        public boolean isMoveSuccess() {
            return true;
        }
    },

    ILLEGAL_MOVE {
        @Override
        public boolean isMoveSuccess() {
            return false;
        }
    };

    public abstract boolean isMoveSuccess();
}
