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

public class Bishop extends Piece {
    private final static int[] CANDIDATE_MOVE_DIRECTIONS = { -9, -7, 7, 9};

    public Bishop(int tileIndex, PieceColor color) {
        super(tileIndex, color);
    }

    @Override
    public Collection<Move> getLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int direction : CANDIDATE_MOVE_DIRECTIONS){
            int candidateDestinationIdx = this.tileIndex + direction;

            while(BoardUtils.isValidIndex(candidateDestinationIdx)){
                if(isFirstColumnExclusion(candidateDestinationIdx, direction) ||
                   isEighthColumnExclusion(candidateDestinationIdx, direction)){
                    break;
                }

                final Tile candidateDestinationTile = board.getTile(candidateDestinationIdx);

                if(!candidateDestinationTile.isOccupied()){
                    legalMoves.add(new PassiveMove(board, this, candidateDestinationIdx));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();

                    if(pieceAtDestination.getColor() != this.color){
                        legalMoves.add(new CaptureMove(board, this, candidateDestinationIdx, pieceAtDestination));
                    }

                    break; // If we found a piece no more moves are legal in this direction
                }

                candidateDestinationIdx += direction;
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentIdx, final int direction){
        return BoardUtils.FIRST_COLUMN[currentIdx] &&
                (direction == -9 || direction == 7);
    }

    private static boolean isEighthColumnExclusion(final int currentIdx, final int direction){
        return BoardUtils.EIGHTH_COLUMN[currentIdx] &&
                (direction == 9 || direction == -7);
    }

    @Override
    public String toString() {
        return "B";
    }
}
