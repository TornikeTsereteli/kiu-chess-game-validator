package GameSimulationTests;

import org.example.common.GameSimulator.GameSimulator;
import org.example.common.GameSimulator.GameSimulatorBase;
import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GameSimulatorTests {

    private GameSimulator gameSimulator;

    @BeforeEach
    void setUp(){
        Board board = new Board();
        gameSimulator = new GameSimulator(board);
    }


    @Test
    public void testSimpleDevelopmentMoves_NoCapturesOrSpecialMoves() throws Exception {
        List<String> moves = List.of("e4", "e5", "Nf3", "Nc6", "Bc4", "Nf6", "Nc3");
        List<ChessMove> chessmoves = ChessMove.fromTextList(moves);
        gameSimulator.SimulateGame(chessmoves);

        Board board = gameSimulator.getBoard();

        // Rooks should still be at their original positions
        assertEquals(Rook.class, board.getSquare("a1").getOccupyingPiece().getClass());
        assertEquals(Rook.class, board.getSquare("h1").getOccupyingPiece().getClass());
        assertEquals(Rook.class, board.getSquare("a8").getOccupyingPiece().getClass());
        assertEquals(Rook.class, board.getSquare("h8").getOccupyingPiece().getClass());

        // Knights after Nf3 and Nc6 and Nc3
        assertEquals(Knight.class, board.getSquare("f3").getOccupyingPiece().getClass());
        assertEquals(Knight.class, board.getSquare("c6").getOccupyingPiece().getClass());
        assertEquals(Knight.class, board.getSquare("c3").getOccupyingPiece().getClass());

        // Bishops after Bc4
        assertEquals(Bishop.class, board.getSquare("c4").getOccupyingPiece().getClass());

        // Pawns on e4 and e5
        assertEquals(Pawn.class, board.getSquare("e4").getOccupyingPiece().getClass());
        assertEquals(Pawn.class, board.getSquare("e5").getOccupyingPiece().getClass());

        // Empty original knight positions
        assertNull(board.getSquare("g1").getOccupyingPiece());
        assertNull(board.getSquare("b8").getOccupyingPiece());

        // Empty original bishop square for Bc1
        assertNull(board.getSquare("f1").getOccupyingPiece());
    }


    @Test
    public void testSimpleDevelopmentMoves_WithCapturesAndNoSpecialMoves() throws Exception {
        List<String> moves = List.of("e4", "e5", "Nf3", "Nc6", "Bc4", "Nf6", "Nc3", "Ne4", "Ne5");
        List<ChessMove> chessmoves = ChessMove.fromTextList(moves);
        gameSimulator.SimulateGame(chessmoves);

        Board board = gameSimulator.getBoard();

        // Rooks still in original positions
        assertEquals(Rook.class, board.getSquare("a1").getOccupyingPiece().getClass());
        assertEquals(Rook.class, board.getSquare("h1").getOccupyingPiece().getClass());
        assertEquals(Rook.class, board.getSquare("a8").getOccupyingPiece().getClass());
        assertEquals(Rook.class, board.getSquare("h8").getOccupyingPiece().getClass());

        // Knights
        assertEquals(Knight.class, board.getSquare("c3").getOccupyingPiece().getClass()); // White
        assertEquals(Knight.class, board.getSquare("c6").getOccupyingPiece().getClass()); // Black
        assertEquals(Knight.class, board.getSquare("e5").getOccupyingPiece().getClass()); // White captured on e5
        assertEquals(Knight.class, board.getSquare("e5").getOccupyingPiece().getClass()); // White captured on e5

        // Bishop
        assertEquals(Bishop.class, board.getSquare("c4").getOccupyingPiece().getClass());

        // Captured squares should be empty
        assertNull(board.getSquare("f3").getOccupyingPiece()); // White knight moved → captured
        assertNull(board.getSquare("f6").getOccupyingPiece()); // Black knight moved → captured

        // Empty starting squares
        assertNull(board.getSquare("g1").getOccupyingPiece()); // Nf3
        assertNull(board.getSquare("b8").getOccupyingPiece()); // Nc6
        assertNull(board.getSquare("f1").getOccupyingPiece()); // Bc4
    }


}
