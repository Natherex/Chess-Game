import static org.junit.Assert.*;
import org.junit.Test;

public class BishopTest {

    @Test
    public void moveBishopTest_1() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Bishop", "w", "B4");
        board.placePiece("Bishop", "b", "G4");

        assertTrue("Moved white bishop from B4 to F8", board.movePiece("B4", "F8"));
        assertFalse("Moved nothing from B4 to F8", board.movePiece("B4", "F8"));

        board.changeTurn();

        assertTrue("Moved black bishop from G4 to D1", board.movePiece("G4", "D1"));
        assertFalse("Moved nothing from G4 to D1", board.movePiece("G4", "D1"));

    }

    @Test
    public void moveBishopTest_2() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Bishop", "w", "B4");
        board.placePiece("Bishop", "b", "H3");

        assertFalse("White bishop should not be able to move from B4 to B8", board.movePiece("B4", "B8"));

        board.changeTurn();

        assertFalse("Black bishop should not be able to move from H3 to H1", board.movePiece("H3", "H1"));

    }

    @Test
    public void killBishopTest() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Bishop", "w", "C7");
        board.placePiece("Bishop", "b", "G3");
        board.placePiece("Bishop", "w", "H6");
        board.placePiece("Bishop", "b", "E3");

        assertTrue("White bishop should be able to kill black bishop on G3", board.movePiece("C7", "G3"));

        board.changeTurn();

        assertTrue("Black bishop should be able to kill white bishop on H6", board.movePiece("E3", "H6"));

    }

    @Test
    public void cannotMoveBishopTest() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Bishop", "w", "A5");
        board.placePiece("Rook", "b", "A7");
        board.placePiece("Bishop", "b", "E8");
        board.placePiece("Rook", "w", "H8");

        assertFalse("White bishop cannot move from A5 as King will be in check", board.movePiece("A5", "B4"));

        board.changeTurn();

        assertFalse("Black bishop cannont move from E5 as King will be in check", board.movePiece("E5", "F6"));

    }

}