package org.example.common.movement;


import org.example.model.Board;
import org.example.model.Piece;
import org.example.model.Square;

import java.util.List;

public class StandardPawnMovement implements MovementStrategy{

    private final Piece pawn;
    private boolean wasMoved;
    public StandardPawnMovement(Piece pawn, boolean wasMoved) {
        this.pawn = pawn;
        this.wasMoved = wasMoved;
    }

    @Override
    public List<Square> getLegalMoves(Board board) {
        return null;
    }
}
