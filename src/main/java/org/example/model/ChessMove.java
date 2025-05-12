package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.model.enums.ChessPiece;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChessMove {
    private ChessPiece piece;
    @Setter
    private String from;
    private String position;
    private boolean isCapture;
    private boolean isPromotion;
    private ChessPiece promotedPiece;
    private boolean isCheck;
    private boolean isCheckmate;

    public ChessMove(){}

    public static ChessMove fromText(String moveText) throws Exception {
        ChessMove move = new ChessMove();
        return move.parse(moveText);
    }


    public static List<ChessMove> fromTextList(List<String> moves) throws Exception {
        ChessMove move = new ChessMove();
        return move.toStructuredMoves(moves);
    }

    // Constructor for normal moves
    public ChessMove(ChessPiece piece, String position) {
        this.piece = piece;
        this.position = position;
        this.isCapture = false;
        this.isPromotion = false;
        this.isCheck = false;
        this.isCheckmate = false;
    }

    // Constructor for captures
    public ChessMove(ChessPiece piece, String position, boolean isCapture) {
        this(piece, position);
        this.isCapture = isCapture;
    }

    // Constructor for Pawn moves
    public ChessMove(String position, boolean isCapture, boolean isPromotion, ChessPiece promotedPiece) {
        this.piece = ChessPiece.PAWN;
        this.position = position;
        this.isCapture = isCapture;
        this.isPromotion = isPromotion;
        this.promotedPiece = promotedPiece;
        this.isCheck = false;
        this.isCheckmate = false;
    }

    // Comprehensive constructor
    public ChessMove(ChessPiece piece, String from, String position, boolean isCapture,
                     boolean isPromotion, ChessPiece promotedPiece, boolean isCheck, boolean isCheckmate) {
        this.piece = piece;
        this.from = from;
        this.position = position;
        this.isCapture = isCapture;
        this.isPromotion = isPromotion;
        this.promotedPiece = promotedPiece;
        this.isCheck = isCheck;
        this.isCheckmate = isCheckmate;
    }

    public List<ChessMove> toStructuredMoves(List<String> sanMoves) throws Exception {
        List<ChessMove> moves = new ArrayList<>();
        for (String originalMoveText : sanMoves) {
            String moveText = originalMoveText;
            moves.add(parse(moveText));
        }
        return moves;
    }



    private ChessMove parse(String moveText) throws Exception {

        // Variables to track move properties
        ChessPiece piece = ChessPiece.PAWN; // Default piece is pawn
        String position = null;
        boolean isCapture = false;
        boolean isPromotion = false;
        ChessPiece promotedPiece = null;
        String from = null;
        boolean isCheck = false;
        boolean isCheckmate = false;

        // Check for check and checkmate indicators
        if (moveText.endsWith("+")) {
            isCheck = true;
            moveText = moveText.substring(0, moveText.length() - 1);
        } else if (moveText.endsWith("#")) {
            isCheckmate = true;
            moveText = moveText.substring(0, moveText.length() - 1);
        }

        // Handling castling: O-O (kingside) or O-O-O (queenside)
        if (moveText.equals("O-O") || moveText.equals("O-O-O")) {
            piece = ChessPiece.KING;
            position = moveText; // Just the castling notation
        }
        // Handle pawn promotion with capture (e.g., exd8=Q)
        else if (moveText.matches("^[a-h]x[a-h][18]=[QRBN]$")) {
            piece = ChessPiece.PAWN;
            from = moveText.substring(0, 1); // Source file (e.g., 'e')
            position = moveText.substring(2, 4); // Destination square (e.g., 'd8')
            isCapture = true;
            isPromotion = true;
            promotedPiece = getPieceFromChar(moveText.charAt(moveText.length() - 1));
        }
        // Handle pawn promotion without capture (e.g., e8=Q)
        else if (moveText.matches("^[a-h][18]=[QRBN]$")) {
            piece = ChessPiece.PAWN;
            position = moveText.substring(0, 2); // Destination square (e.g., 'e8')
            isPromotion = true;
            promotedPiece = getPieceFromChar(moveText.charAt(moveText.length() - 1));
        }
        // Handle disambiguated piece moves with captures (e.g., Raxc7)
        else if (moveText.matches("^[KQRBN][a-h]x[a-h][1-8]$")) {
            piece = getPieceFromChar(moveText.charAt(0));
            from = moveText.substring(1, 2); // Disambiguation (file)
            isCapture = true;
            position = moveText.substring(3); // Destination
        }
        // Handle disambiguated piece moves with rank (e.g., R5c7)
        else if (moveText.matches("^[KQRBN][1-8][a-h][1-8]$")) {
            piece = getPieceFromChar(moveText.charAt(0));
            from = moveText.substring(1, 2); // Disambiguation (rank)
            position = moveText.substring(2); // Destination
        }
        // Handle disambiguated piece moves with file (e.g., Rac7)
        else if (moveText.matches("^[KQRBN][a-h][a-h][1-8]$")) {
            piece = getPieceFromChar(moveText.charAt(0));
            from = moveText.substring(1, 2); // Disambiguation (file)
            position = moveText.substring(2); // Destination
        }
        // Handle piece captures (e.g., Nxe5)
        else if (moveText.matches("^[KQRBN]x[a-h][1-8]$")) {
            piece = getPieceFromChar(moveText.charAt(0));
            isCapture = true;
            position = moveText.substring(2); // Destination
        }
        // Handle standard piece moves (e.g., Nf3)
        else if (moveText.matches("^[KQRBN][a-h][1-8]$")) {
            piece = getPieceFromChar(moveText.charAt(0));
            position = moveText.substring(1); // Destination
        }
        // Handle pawn captures (e.g., exd5)
        else if (moveText.matches("^[a-h]x[a-h][1-8]$")) {
            piece = ChessPiece.PAWN;
            from = moveText.substring(0, 1); // Source file
            isCapture = true;
            position = moveText.substring(2); // Destination
        }
        // Handle standard pawn moves (e.g., e4)
        else if (moveText.matches("^[a-h][1-8]$")) {
            piece = ChessPiece.PAWN;
            position = moveText;
        }
        // Handle cases not covered by the above patterns
        else {
            throw new Exception("Invalid move notation:");
        }

        ChessMove move = new ChessMove(piece, from, position, isCapture,
                isPromotion, promotedPiece, isCheck, isCheckmate);

        return move;
    }


    private ChessPiece getPieceFromChar(char pieceChar) {
        switch (pieceChar) {
            case 'K':
                return ChessPiece.KING;
            case 'Q':
                return ChessPiece.QUEEN;
            case 'R':
                return ChessPiece.ROOK;
            case 'B':
                return ChessPiece.BISHOP;
            case 'N':
                return ChessPiece.KNIGHT;
            default:
                throw new IllegalArgumentException("Invalid piece: " + pieceChar);
        }
    }

    @Override
    public String toString() {
        return "ChessMove{" +
                "piece=" + piece +
                ", from='" + from + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}