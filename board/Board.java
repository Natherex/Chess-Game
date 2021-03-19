package board;

import pieces.*;

/**
 * Generalized board object for creating other boards.
 */
public class Board {
    private int length;
    private int height;
    Piece[][] grid;

    public Board(int length, int height) {
        this.length = length;
        this.height = height;
        this.grid = new Piece[height][length];
    }

    public Board (Board b) {
        this.length = b.length;
        this.height = b.height;
        this.grid = b.getCopyGrid();
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    public Piece[][] getGrid() {
        return grid;
    }

    public Piece[][] getCopyGrid() {
        Piece[][] tempGrid = new Piece[height][length];

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < length; column++) {
                if (grid[row][column] != null) {
                    String name = grid[row][column].getName();
                    Piece currentPiece = grid[row][column];
                    switch (name) {
                        case "Rook":
                            tempGrid[row][column] = new Rook(currentPiece);
                            break;
                        case "Knight":
                            tempGrid[row][column] = new Knight(currentPiece);
                            break;
                        case "Bishop":
                            tempGrid[row][column] = new Bishop(currentPiece);
                            break;
                        case "Queen":
                            tempGrid[row][column] = new Queen(currentPiece);
                            break;
                        case "King":
                            tempGrid[row][column] = new King(currentPiece);
                            break;
                        case "Pawn":
                            tempGrid[row][column] = new Pawn(currentPiece);
                            break;
                    }
                }
            }
        }

        return tempGrid;
    }

}