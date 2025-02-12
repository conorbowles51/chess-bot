package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

public abstract class Tile {
    protected final int index;

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createEmptyTiles();

    private static Map<Integer, EmptyTile> createEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int index, final Piece piece){
        return piece != null ? new OccupiedTile(index, piece) : EMPTY_TILES_CACHE.get(index);
    }

    private Tile(final int index){
        this.index = index;
    }

    public abstract boolean isOccupied();
    public abstract Piece getPiece();

    public static final class EmptyTile extends Tile {
        private EmptyTile(final int index){
            super(index);
        }

        @Override
        public boolean isOccupied(){
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }

        @Override
        public String toString() { return "-"; }
    }

    public static final class OccupiedTile extends Tile {
        private final Piece piece;

        private OccupiedTile(int index, Piece piece){
            super(index);
            this.piece = piece;
        }

        @Override
        public boolean isOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.piece;
        }

        @Override
        public String toString() {
            return this.piece.getColor().isBlack() ? this.piece.toString().toLowerCase() :
                                                     this.piece.toString().toUpperCase();
        }
    }
}
