package com.chess.engine.pieces;

import com.chess.engine.PieceColor;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.*;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece {
    private final static int[] CANDIDATE_MOVE_DIRECTIONS = { -9, -8, -7, -1, 1, 7, 8, 9 };

    public King(int tileIndex, PieceColor color) {
        super(tileIndex, color);
    }

    @Override
    public Collection<Move> getLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int direction : CANDIDATE_MOVE_DIRECTIONS){
            int candidateDestinationIdx = this.tileIndex + direction;

            if(!BoardUtils.isValidIndex(candidateDestinationIdx)){
                continue;
            }

            if(isFirstColumnExclusion(this.tileIndex, direction) ||
               isEighthColumnExclusion(this.tileIndex, direction)){
                continue;
            }

            final Tile candidateDestinationTile = board.getTile(candidateDestinationIdx);

            if(!candidateDestinationTile.isOccupied()){
                legalMoves.add(new PassiveMove(board, this, candidateDestinationIdx));
            } else {
                final Piece pieceAtDestination = candidateDestinationTile.getPiece();

                if(pieceAtDestination.getColor() != this.color){
                    legalMoves.add(new CaptureMove(board, this, candidateDestinationIdx, pieceAtDestination));
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentIdx, final int direction){
        return BoardUtils.FIRST_COLUMN[currentIdx] &&
                (direction == -9 || direction == 7 || direction == -1);
    }

    private static boolean isEighthColumnExclusion(final int currentIdx, final int direction){
        return BoardUtils.EIGHTH_COLUMN[currentIdx] &&
                (direction == 9 || direction == -7 || direction == 1);
    }
}
