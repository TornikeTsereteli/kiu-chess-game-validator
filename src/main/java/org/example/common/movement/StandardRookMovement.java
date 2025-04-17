package org.example.common.movement;

import org.example.model.Board;
import org.example.model.Piece;
import org.example.model.Square;

import java.util.List;

public class StandardRookMovement implements MovementStrategy{

    private final Piece rook;

    public StandardRookMovement(Piece rook) {
        this.rook = rook;
    }


    @Override
    public List<Square> getLegalMoves(Board chessBoard) {
        return null;
    }

}
