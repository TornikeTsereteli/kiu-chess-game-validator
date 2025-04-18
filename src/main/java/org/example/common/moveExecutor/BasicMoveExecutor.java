package org.example.common.moveExecutor;

import org.example.model.Board;
import org.example.model.Piece;
import org.example.model.Square;

public class BasicMoveExecutor implements MoveExecutorStrategy{

    private final Piece piece;

    public BasicMoveExecutor(Piece piece) {
        this.piece = piece;
    }

    @Override
    public boolean executeMove(Board board, Square destination) {
        Piece occup = destination.getOccupyingPiece();

        if (occup != null) {
            if (occup.getColor() == piece.getColor()) return false;
            else board.capturePiece(destination,piece);
        }

        piece.getPosition().removePiece();
        piece.setPosition(destination);
        piece.getPosition().put(piece);
        return true;
    }
}
