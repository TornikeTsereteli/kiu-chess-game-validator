package org.example.model;

import org.example.common.moveExecutor.BasicMoveExecutor;
import org.example.common.moveExecutor.MoveExecutorStrategy;
import org.example.common.movement.MovementStrategy;
import org.example.common.movement.StandardBishopMovement;
import org.example.model.enums.PieceColor;

public class Bishop extends Piece {

    public Bishop(PieceColor color, Square initSq) {
        super(color, initSq);
    }

    @Override
    protected MovementStrategy getMovementStrategy() {
        return new StandardBishopMovement(this);
    }

    @Override
    protected MoveExecutorStrategy getMoveExecutorStrategy() {
        return new BasicMoveExecutor(this);
    }

//    @Override
//    public boolean attacksSquare(Square target, Board board) {
//        int dx = Math.abs(this.getPosition().getPosition().getX() - target.getPosition().getX());
//        int dy = Math.abs(this.getPosition().getPosition().getY() - target.getPosition().getY());
//
//        if (dx != dy) return false;
//        return board.isPathClear(this.getPosition(), target);
//    }

}