package org.example.model;


import org.example.common.moveExecutor.MoveExecutorStrategy;
import org.example.common.movement.MovementStrategy;
import org.example.model.enums.PieceColor;

public class Knight extends Piece {

    public Knight(PieceColor color, Square initSq) {
        super(color, initSq);
    }

    @Override
    protected MovementStrategy getMovementStrategy() {
//        return new StandardKnightMovement(this);
        return null;
    }

    @Override
    protected MoveExecutorStrategy getMoveExecutorStrategy()
    {
//        return new BasicMoveExecutor(this);
        return null;
    }

//    @Override
//    public boolean attacksSquare(Square target, Board board) {
//        int dx = Math.abs(this.getPosition().getPosition().getX() - target.getPosition().getX());
//        int dy = Math.abs(this.getPosition().getPosition().getY() - target.getPosition().getY());
//        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
//    }


}
