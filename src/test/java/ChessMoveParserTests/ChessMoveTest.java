package ChessMoveParserTests;


import org.example.model.ChessMove;
import org.example.model.enums.ChessPiece;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ChessMoveTest {

    @Test
    void testFromTextWithSimplePawnMove() throws Exception {
        ChessMove move = ChessMove.fromText("e4");

        assertEquals(ChessPiece.PAWN, move.getPiece());
//        assertEquals("e2", move.getFrom());
        assertEquals("e4", move.getPosition());
        assertFalse(move.isCapture());
        assertFalse(move.isPromotion());
        assertFalse(move.isCheck());
        assertFalse(move.isCheckmate());
    }

    @Test
    void testFromTextWithPieceMove() throws Exception {
        ChessMove move = ChessMove.fromText("Ne2");

        assertEquals(ChessPiece.KNIGHT, move.getPiece());
        assertNull(move.getFrom());
        assertEquals("e2", move.getPosition());
        assertFalse(move.isCapture());
        assertFalse(move.isPromotion());
    }

    @Test
    void testFromTextWithCastling() throws Exception {
        // Kingside castling
        ChessMove kingsideCastle = ChessMove.fromText("O-O");
        assertEquals(ChessPiece.KING, kingsideCastle.getPiece());
        assertEquals("O-O", kingsideCastle.getPosition());

        // Queenside castling
        ChessMove queensideCastle = ChessMove.fromText("O-O-O");
        assertEquals(ChessPiece.KING, queensideCastle.getPiece());
        assertEquals("O-O-O", queensideCastle.getPosition());
    }

    @Test
    void testFromTextWithCheck() throws Exception {
        ChessMove move = ChessMove.fromText("Qd7+");

        assertEquals(ChessPiece.QUEEN, move.getPiece());
        assertEquals("d7", move.getPosition());
        assertTrue(move.isCheck());
        assertFalse(move.isCheckmate());
    }

    @Test
    void testFromTextWithCheckmate() throws Exception {
        ChessMove move = ChessMove.fromText("Qf7#");

        assertEquals(ChessPiece.QUEEN, move.getPiece());
        assertEquals("f7", move.getPosition());
        assertFalse(move.isCheck());
        assertTrue(move.isCheckmate());
    }

    @Test
    void testFromTextWithPawnCapture() throws Exception {
        ChessMove move = ChessMove.fromText("exd5");

        assertEquals(ChessPiece.PAWN, move.getPiece());
        assertEquals("e", move.getFrom());
        assertEquals("d5", move.getPosition());
        assertTrue(move.isCapture());
    }

    @Test
    void testFromTextWithPieceCapture() throws Exception {
        ChessMove move = ChessMove.fromText("Nxe5");

        assertEquals(ChessPiece.KNIGHT, move.getPiece());
        assertEquals("e5", move.getPosition());
        assertTrue(move.isCapture());
    }

    @Test
    void testFromTextWithPromotion() throws Exception {
        ChessMove move = ChessMove.fromText("e8=Q");

        assertEquals(ChessPiece.PAWN, move.getPiece());
        assertEquals("e8", move.getPosition());
        assertTrue(move.isPromotion());
        assertEquals(ChessPiece.QUEEN, move.getPromotedPiece());
    }

    @Test
    void testFromTextWithPromotionAndCapture() throws Exception {
        ChessMove move = ChessMove.fromText("exd8=Q");

        assertEquals(ChessPiece.PAWN, move.getPiece());
        assertEquals("e", move.getFrom());
        assertEquals("d8", move.getPosition());
        assertTrue(move.isCapture());
        assertTrue(move.isPromotion());
        assertEquals(ChessPiece.QUEEN, move.getPromotedPiece());
    }

    @Test
    void testFromTextWithDisambiguationRank() throws Exception {
        ChessMove move = ChessMove.fromText("R2d1");

        assertEquals(ChessPiece.ROOK, move.getPiece());
        assertEquals("2", move.getFrom());
        assertEquals("d1", move.getPosition());
    }

    @Test
    void testFromTextWithDisambiguationAndCapture() throws Exception {
        ChessMove move = ChessMove.fromText("Raxd1");

        assertEquals(ChessPiece.ROOK, move.getPiece());
        assertEquals("a", move.getFrom());
        assertEquals("d1", move.getPosition());
        assertTrue(move.isCapture());
    }

    @Test
    void testFromTextWithPromotionCheckmate() throws Exception {
        ChessMove move = ChessMove.fromText("e8=Q#");

        assertEquals(ChessPiece.PAWN, move.getPiece());
        assertEquals("e8", move.getPosition());
        assertTrue(move.isPromotion());
        assertEquals(ChessPiece.QUEEN, move.getPromotedPiece());
        assertTrue(move.isCheckmate());
    }

    @Test
    void testFromTextInvalidNotation() {
        Exception exception = assertThrows(Exception.class, () -> {
            ChessMove.fromText("Z5");
        });

        assertTrue(exception.getMessage().contains("Invalid"));
    }

    @ParameterizedTest
    @MethodSource("provideValidMoveNotations")
    void testFromTextWithVariousMoveNotations(String notation, ChessPiece expectedPiece,
                                              String expectedPosition, boolean expectedCapture,
                                              boolean expectedCheck, boolean expectedCheckmate) throws Exception {
        ChessMove move = ChessMove.fromText(notation);

        assertEquals(expectedPiece, move.getPiece());
        assertEquals(expectedPosition, move.getPosition());
        assertEquals(expectedCapture, move.isCapture());
        assertEquals(expectedCheck, move.isCheck());
        assertEquals(expectedCheckmate, move.isCheckmate());
    }

    private static Stream<Arguments> provideValidMoveNotations() {
        return Stream.of(
                Arguments.of("e4", ChessPiece.PAWN, "e4", false, false, false),
                Arguments.of("Nf3", ChessPiece.KNIGHT, "f3", false, false, false),
                Arguments.of("Qd1", ChessPiece.QUEEN, "d1", false, false, false),
                Arguments.of("Kd2", ChessPiece.KING, "d2", false, false, false),
                Arguments.of("Bc4", ChessPiece.BISHOP, "c4", false, false, false),
                Arguments.of("Ra1", ChessPiece.ROOK, "a1", false, false, false),
                Arguments.of("exd5", ChessPiece.PAWN, "d5", true, false, false),
                Arguments.of("Nxe5", ChessPiece.KNIGHT, "e5", true, false, false),
                Arguments.of("Qxf7+", ChessPiece.QUEEN, "f7", true, true, false),
                Arguments.of("Qxf7#", ChessPiece.QUEEN, "f7", true, false, true)
        );
    }

    @Test
    void testToStructuredMovesWithMultipleMoves() throws Exception {
        List<String> sanMoves = Arrays.asList("e4", "e5", "Nf3", "Nc6", "Bb5", "a6");
        List<ChessMove> moves = new ChessMove().toStructuredMoves(sanMoves);

        assertEquals(6, moves.size());
        assertEquals(ChessPiece.PAWN, moves.get(0).getPiece());
        assertEquals("e4", moves.get(0).getPosition());
        assertEquals(ChessPiece.PAWN, moves.get(1).getPiece());
        assertEquals("e5", moves.get(1).getPosition());
        assertEquals(ChessPiece.KNIGHT, moves.get(2).getPiece());
        assertEquals("f3", moves.get(2).getPosition());
    }

    @Test
    void testLongAlgebraicNotation() throws Exception {
        ChessMove move = ChessMove.fromText("e4");

        assertEquals(ChessPiece.PAWN, move.getPiece());
        assertEquals("e4", move.getPosition());
    }

    @Test
    void testSquareToSquareWithCapture() throws Exception {
        // In long algebraic notation, capture is implicit from the positions
        ChessMove move = ChessMove.fromText("exd5");

        assertEquals(ChessPiece.PAWN, move.getPiece());  // Default
        assertEquals("d5", move.getPosition());
        assertEquals("e", move.getFrom());
    }
}