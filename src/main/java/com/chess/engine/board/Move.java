package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.chess.engine.board.Board.*;

public abstract class Move {
    final Board board;
    final Piece piece;
    final int destinationIdx;

    private Move(Board board, Piece piece, int destinationIdx) {
        this.board = board;
        this.piece = piece;
        this.destinationIdx = destinationIdx;
    }

    public int getDestinationIndex() {
        return this.destinationIdx;
    }

    public abstract Board execute();

    public static final class PassiveMove extends Move {

        public PassiveMove(final Board board, final Piece piece, final int destinationIdx) {
            super(board, piece, destinationIdx);
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();

            for(final Piece piece : this.board.getCurrentPlayer().getActivePieces()){
                if(!piece.equals(this.piece)){ // TODO override equals
                    builder.setPiece(piece);
                }
            }

            for(final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }

            builder.setPiece(null);
            builder.setNextToMove(this.board.getCurrentPlayer().getOpponent().getPieceColor());

            return builder.build();
        }
    }

    public static final class CaptureMove extends Move {

        final Piece capturedPiece;

        public CaptureMove(final Board board, final Piece piece, final int destinationIdx, final Piece capturedPiece) {
            super(board, piece, destinationIdx);
            this.capturedPiece = capturedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }
}
