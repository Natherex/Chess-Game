package pieces;

import board.ChessBoard;

public class Knight extends Piece {

    public Knight() {
        super("w", "Knight");
        setIconLocation();
        super.setValue(3);
    }

    public Knight(String color) {
        super(color, "Knight");
        setIconLocation();
        super.setValue(3);
    }

    public Knight(Piece p) {
        super(p);
    }

    /**
     * Sets the icon's picture location to the appropriate picture.
     */
    private void setIconLocation() {
        if (getColor().equals("w"))
            setIconLocation("/assets/Chess_nlt60.png");
        else
            setIconLocation("/assets/Chess_ndt60.png");
    }

    public String toString() {
        return "Kn(" + getColor() + ")";
    }


    @Override
    public boolean isLegalMove(ChessBoard board, Movement playersMove)
    {
        // Can move two spaces forwards or backwards and one left or right
        if( Math.abs(playersMove.xDirection) == 2 && Math.abs(playersMove.yDirection) == 1 && canPieceMoveLegally(board, playersMove.start, playersMove.end, playersMove.color) )
            return true;
        // Can move one space forward or backward and two left or right
        else if(Math.abs(playersMove.xDirection) == 1 && Math.abs(playersMove.yDirection) == 2 && canPieceMoveLegally(board, playersMove.start, playersMove.end, playersMove.color) )
            return true;
        else
            return false;
    }
}
