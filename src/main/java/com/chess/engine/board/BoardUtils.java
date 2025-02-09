package com.chess.engine.board;

public class BoardUtils {
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] SECOND_ROW = null;
    public static final boolean[] SEVENTH_ROW = null;

    public static final int NUM_TILES = 64;
    public static final int ROW_SIZE = 8;

    public static boolean isValidIndex(final int candidateDestinationIdx) {
        return candidateDestinationIdx >= 0 && candidateDestinationIdx < 64;
    }

    private static boolean[] initColumn(final int columnNumber){
        final boolean[] column = new boolean[NUM_TILES];

        for(int i = 0; i < NUM_TILES; i++){
            column[i] = i % ROW_SIZE == columnNumber;
        }

        return column;
    }
}
