import static org.junit.Assert.*;
import org.junit.Test;

public class RookTest {

    @Test
    public void moveRookTest_1() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Rook", "w", "A4");
        board.placePiece("Rook", "b", "F7");

        assertTrue("Moved white rook from A4 to H4", board.movePiece("A4", "H4"));
        assertFalse("Moved nothing from A4 to H4", board.movePiece("A4", "H4"));

        board.changeTurn();

        assertTrue("Moved black rook from F7 to F8", board.movePiece("F7", "F8"));
        assertFalse("Moved nothing from F7 to F8", board.movePiece("F7", "F8"));

    }

    @Test
    public void moveRookTest_2() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Rook", "w", "A4");
        board.placePiece("Rook", "b", "G7");

        assertFalse("White rook should not be able to move from A4 to D8", board.movePiece("A4", "D8"));

        board.changeTurn();

        assertFalse("Black rook should not be able to move from G7 to E5", board.movePiece("G7", "E5"));

    }

    @Test
    public void killRookTest() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Rook", "w", "B6");
        board.placePiece("Rook", "b", "B2");
        board.placePiece("Rook", "w", "E6");
        board.placePiece("Rook", "b", "H6");

        assertTrue("White rook should be able to kill black rook on B2 from B6", board.movePiece("B6", "B2"));

        board.changeTurn();

        assertTrue("Black rook should be able to kill white rook on E6 from H6", board.movePiece("H6", "E6"));
    }

    @Test
    public void cannotMoveRookTest() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Rook", "w", "A5");
        board.placePiece("Rook", "b", "A7");
        board.placePiece("Rook", "b", "E8");
        board.placePiece("Rook", "w", "H8");

        assertFalse("White rook cannot move from A5 as King will be in check", board.movePiece("A5", "H5"));

        board.changeTurn();

        assertFalse("Black rook cannont move from E8 as King will be in check", board.movePiece("E8", "E2"));

    }

}