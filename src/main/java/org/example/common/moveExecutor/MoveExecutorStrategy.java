package org.example.common.moveExecutor;

import org.example.model.Board;
import org.example.model.Square;

public interface MoveExecutorStrategy {
    boolean executeMove(Board board, Square destination);}
