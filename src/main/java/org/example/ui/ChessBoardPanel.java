package org.example.ui;
import org.example.model.*;
import org.example.model.enums.PieceColor;

import javax.swing.*;
import java.awt.*;

public class ChessBoardPanel extends JPanel {
    private Board board;

    public void setBoard(Board board) {
        this.board = board;
        repaint(); // trigger repaint when board changes
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int tileSize = 64;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int x = col * tileSize;
                int y = row * tileSize;
                boolean isWhite = (row + col) % 2 == 0;

                // Draw tile
                g.setColor(isWhite ? Color.WHITE : Color.GRAY);
                g.fillRect(x, y, tileSize, tileSize);

                // Get piece at square
                char file = (char) ('a' + col);
                int rank = 8 - row;
                String squareId = "" + file + rank;

                if (board != null) {
                    Piece piece = board.getSquare(squareId).getOccupyingPiece();
                    if (piece != null) {
                        g.setColor(piece.getColor() == PieceColor.WHITE ? Color.BLACK : Color.RED);
                        g.setFont(new Font("SansSerif", Font.BOLD, 36));
                        g.drawString(getPieceUnicode(piece), x + 18, y + 45);
                    }
                }
            }
        }
    }

    private String getPieceUnicode(Piece piece) {
        boolean isWhite = piece.getColor() == PieceColor.WHITE;
        if (piece instanceof King) return isWhite ? "♔" : "♚";
        if (piece instanceof Queen) return isWhite ? "♕" : "♛";
        if (piece instanceof Rook) return isWhite ? "♖" : "♜";
        if (piece instanceof Bishop) return isWhite ? "♗" : "♝";
        if (piece instanceof Knight) return isWhite ? "♘" : "♞";
        if (piece instanceof Pawn) return isWhite ? "♙" : "♟";
        return "?";
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(8 * 64, 8 * 64);
    }
}
