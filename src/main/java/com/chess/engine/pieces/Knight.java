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

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_OFFSETS =  { -17, -15, -10, -6, 6, 10, 15, 17 };

    public Knight(final int tileIndex, final PieceColor color) {
        super(tileIndex, color);
    }

    @Override
    public Collection<Move> getLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int offset : CANDIDATE_MOVE_OFFSETS){
            final int candidateDestinationIdx = this.tileIndex + offset;

            if(BoardUtils.isValidIndex(candidateDestinationIdx)){
                continue;
            }

            if(isFirstColumnExclusion(this.tileIndex, offset) ||
                    isSecondColumnExclusion(this.tileIndex, offset) ||
                    isSeventhColumnExclusion(this.tileIndex, offset) ||
                    isEighthColumnExclusion(this.tileIndex, offset)){
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

    private static boolean isFirstColumnExclusion(final int currentIdx, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentIdx] &&
                (candidateOffset == -17 || candidateOffset == -10 || candidateOffset == 6 || candidateOffset == 15);
    }

    private static boolean isSecondColumnExclusion(final int currentIdx, final int candidateOffset){
        return BoardUtils.SECOND_COLUMN[currentIdx] &&
                (candidateOffset == -10 || candidateOffset == 6);
    }

    private static boolean isSeventhColumnExclusion(final int currentIdx, final int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentIdx] &&
                (candidateOffset == -6 || candidateOffset == 10);
    }

    private static boolean isEighthColumnExclusion(final int currentIdx, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentIdx] &&
                (candidateOffset == 17 || candidateOffset == 10 || candidateOffset == -6 || candidateOffset == -15);
    }
}
