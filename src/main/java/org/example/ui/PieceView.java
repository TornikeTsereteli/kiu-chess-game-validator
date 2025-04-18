package org.example.ui;

import org.example.model.Piece;
import org.example.util.PieceImageLoader;

import java.awt.*;

public class PieceView {
    private Piece piece;
    private Image image;

    public PieceView(Piece piece, String path) {
        this.piece = piece;
        this.image = PieceImageLoader.loadImage(path);
    }

    public void render(Graphics g, int width, int height) {
        g.drawImage(this.image, 0, 0, width, height, null);
    }


}
