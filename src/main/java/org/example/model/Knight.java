package org.example.model;


import org.example.common.moveExecutor.MoveExecutorStrategy;
import org.example.common.movement.MovementStrategy;
import org.example.common.movement.StandardKnightMovement;
import org.example.model.enums.PieceColor;
import org.example.common.moveExecutor.BasicMoveExecutor;

public class Knight extends Piece {

    public Knight(PieceColor color, Square initSq) {
        super(color, initSq);
    }

    @Override
    protected MovementStrategy getMovementStrategy() {
        return new StandardKnightMovement(this);
    }

    @Override
    protected MoveExecutorStrategy getMoveExecutorStrategy()
    {
        return new BasicMoveExecutor(this);
    }



}
