package org.example.model;

import lombok.Getter;
import lombok.Setter;
import org.example.model.enums.PieceColor;

public class Square {
    @Getter
    private final Position position;

    @Getter
    private final PieceColor color;

    @Getter @Setter
    private Piece occupyingPiece;

    @Getter @Setter
    private boolean display = true; // should be refactored moved out to

    public Square(PieceColor color, int x, int y) {
        this.color = color;
        this.position = new Position(x, y);
    }

    public boolean isOccupied() {
        return occupyingPiece != null;
    }

    public void put(Piece piece) {
        this.occupyingPiece = piece;
        if (piece != null) {
            piece.setPosition(this);
        }
    }

    public Piece removePiece() {
        Piece piece = this.occupyingPiece;
        this.occupyingPiece = null;
        return piece;
    }

    public boolean getDisplay(){
        return display;
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Square other = (Square) obj;
        return position.equals(other.position);
    }
}