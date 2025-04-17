package org.example.common.movement;

import org.example.model.*;

import java.util.LinkedList;
import java.util.List;

public class StandardKingMovement implements MovementStrategy{

    private Piece king;

    public StandardKingMovement(Piece king) {
        this.king = king;
    }


    @Override
    public List<Square> getLegalMoves(Board chessBoard) {
        LinkedList<Square> legalMoves = new LinkedList<Square>();

        Square[][] board = chessBoard.getSquareArray();

        Square position = king.getPosition();
        int x = position.getPosition().getX();
        int y = position.getPosition().getY();


        for (int i = 1; i > -2; i--) {
            for (int k = 1; k > -2; k--) {
                if (!(i == 0 && k == 0)) {
                    try {
                        if (!board[y + k][x + i].isOccupied() ||
                                board[y + k][x + i].getOccupyingPiece().getColor()
                                        != king.getColor()) {
                            legalMoves.add(board[y + k][x + i]);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                }
            }
        }

        return legalMoves;
    }


}
