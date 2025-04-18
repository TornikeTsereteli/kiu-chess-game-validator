package org.example.common.checkmateDetector;

import org.example.model.enums.PieceColor;

public interface CheckmateDetector {
    boolean isInCheck(PieceColor color);
    boolean isCheckMate(PieceColor color);

    // potentially new feature, do not have enough time for it :(((
    boolean isStalemate(PieceColor color);
}
