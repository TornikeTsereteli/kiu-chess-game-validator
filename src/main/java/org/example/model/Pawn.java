package org.example.model;


import org.example.common.moveExecutor.BasicMoveExecutor;
import org.example.common.moveExecutor.MoveExecutorStrategy;
import org.example.common.movement.MovementStrategy;
import org.example.common.movement.StandardPawnMovement;
import org.example.model.enums.PieceColor;

public class Pawn extends Piece {


    public Pawn(PieceColor color, Square initSq) {
        super(color, initSq);
    }


    @Override
    protected MovementStrategy getMovementStrategy() {
        return new StandardPawnMovement(this,false);
    }

    @Override
    protected MoveExecutorStrategy getMoveExecutorStrategy() {
        return new BasicMoveExecutor(this);
    }


    @Override
    public boolean move(Square fin,Board board) {
        setMovementStrategy(new StandardPawnMovement(this,true));
        return super.move(fin,board);
    }


}
