import static org.junit.Assert.*;
import org.junit.Test;

// MAKE SURE TO GIVE EACH PLAYER A KING!
public class PawnTest {

    @Test
    public void movePawnTest_1() {
        ChessBoard board = new ChessBoard();
        board.placePiece("King", "w", "A1");
        board.placePiece("Pawn", "w", "A2");
        board.placePiece("King", "b", "A8");
        board.placePiece("Pawn", "b", "A7");

        assertTrue("Moved white pawn from A2 to A4", board.movePiece("A2", "A4"));
        assertFalse("Moved nothing from A2 to A4", board.movePiece("A2", "A4"));

        board.changeTurn();

        assertTrue("Moved black pawn from A7 to A5", board.movePiece("A7", "A5"));
        assertFalse("Moved nothing from A7 to A5", board.movePiece("A7", "A5"));
    }

    @Test
    public void movePawnTest_2() {
        ChessBoard board = new ChessBoard();
        board.placePiece("King", "w", "A1");
        board.placePiece("Pawn", "w", "A2");
        board.placePiece("King", "b", "A8");
        board.placePiece("Pawn", "b", "A3");

        assertFalse("White pawn moving from A2 to A4 shouldn't be possible with a black pawn at A3", board.movePiece("A2", "A4"));
    }

    @Test
    public void killPawnTest() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "w", "A1");
        board.placePiece("King", "b", "A8");
        board.placePiece("Pawn", "w", "B1");
        board.placePiece("Pawn", "b", "C2");
        board.placePiece("Pawn", "w", "C7");
        board.placePiece("Pawn", "b", "B8");

        assertTrue("White pawn should be able to kill black pawn on C3", board.movePiece("B1", "C2"));

        board.changeTurn();

        assertTrue("Black pawn should be able to kill white pawn on C7", board.movePiece("B8", "C7"));

    }

    @Test
    public void cannotMovePawnTest() {

        ChessBoard board = new ChessBoard();
        board.placePiece("King", "b", "A8");
        board.placePiece("King", "w", "A1");
        board.placePiece("Pawn", "w", "B1");
        board.placePiece("Rook", "b", "H1");
        board.placePiece("Pawn", "b", "B8");
        board.placePiece("Rook", "w", "H8");

        assertFalse("White pawn cannot move from B1 as King will be in check", board.movePiece("B1", "B2"));

        board.changeTurn();

        assertFalse("Black pawn cannont move from B8 as King will be in check", board.movePiece("B8", "B7"));

    }


}
