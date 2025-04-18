package org.example.pieceMovement;

import org.example.model.*;
import org.example.model.enums.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KingMovementTests {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        for (int i = 0; i < 8 ; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = board.getSquareArray()[i][j];
                square.setOccupyingPiece(null);
            }
        }
        board.getWhitePieces().clear();
        board.getBlackPieces().clear();
    }

    @Test
    void testKingCentralPosition() {
        Square from = board.getSquare("d4");
        King king = new King(PieceColor.WHITE, from);
        from.setOccupyingPiece(king);

        List<Square> moves = king.getLegalMoves(board);

        String[] expectedMoves = {
                "c3", "c4", "c5",
                "d3",       "d5",
                "e3", "e4", "e5"
        };

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

        assertEquals(8, moves.size());
    }

    @Test
    void testKingCornerPosition() {
        Square from = board.getSquare("a1");
        King king = new King(PieceColor.WHITE, from);
        from.setOccupyingPiece(king);

        List<Square> moves = king.getLegalMoves(board);

        String[] expectedMoves = {
                "a2", "b1", "b2"
        };

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

        assertEquals(3, moves.size());
    }

    @Test
    void testKingBlockedByFriendlyPieces() {
        Square from = board.getSquare("d4");
        King king = new King(PieceColor.WHITE, from);
        from.setOccupyingPiece(king);

        String[] friendlyPositions = {
                "c3", "c4", "c5",
                "d3",       "d5",
                "e3", "e4", "e5"
        };

        for (String pos : friendlyPositions) {
            Square square = board.getSquare(pos);
            square.setOccupyingPiece(new King(PieceColor.WHITE, square));
        }

        List<Square> moves = king.getLegalMoves(board);
        assertEquals(0, moves.size(), "King shouldn't be able to move onto friendly pieces");
    }

    @Test
    void testKingCanCaptureEnemies() {
        Square from = board.getSquare("d4");
        King king = new King(PieceColor.WHITE, from);
        from.setOccupyingPiece(king);

        String[] enemyPositions = {
                "c3", "c4", "c5",
                "d3",       "d5",
                "e3", "e4", "e5"
        };

        for (String pos : enemyPositions) {
            Square square = board.getSquare(pos);
            square.setOccupyingPiece(new King(PieceColor.BLACK, square));
        }

        List<Square> moves = king.getLegalMoves(board);

        for (String pos : enemyPositions) {
            assertTrue(moves.contains(board.getSquare(pos)), "King should be able to capture at " + pos);
        }

        assertEquals(8, moves.size());
    }

    @Test
    void testKingAtBoardEdge() {
        Square from = board.getSquare("h5");
        King king = new King(PieceColor.WHITE, from);
        from.setOccupyingPiece(king);

        List<Square> moves = king.getLegalMoves(board);

        String[] expectedMoves = {
                "g4", "g5", "g6",
                "h4",       "h6"
        };

        for (String pos : expectedMoves) {
            assertTrue(moves.contains(board.getSquare(pos)), "Expected move to " + pos);
        }

        assertEquals(5, moves.size());
    }

    @Test
    void testKingSurroundedByMixedPieces() {
        Square from = board.getSquare("d4");
        King king = new King(PieceColor.WHITE, from);
        from.setOccupyingPiece(king);

        String[] friendly = { "c3", "c4", "d3", "e3" };
        String[] enemies = { "c5", "d5", "e4", "e5" };

        for (String pos : friendly) {
            Square square = board.getSquare(pos);
            square.setOccupyingPiece(new King(PieceColor.WHITE, square));
        }

        for (String pos : enemies) {
            Square square = board.getSquare(pos);
            square.setOccupyingPiece(new King(PieceColor.BLACK, square));
        }

        List<Square> moves = king.getLegalMoves(board);

        for (String pos : enemies) {
            assertTrue(moves.contains(board.getSquare(pos)), "King should capture enemy at " + pos);
        }

        for (String pos : friendly) {
            assertFalse(moves.contains(board.getSquare(pos)), "King should not move to friendly at " + pos);
        }

        assertEquals(4, moves.size());
    }
}
