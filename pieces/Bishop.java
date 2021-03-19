package pieces;

import board.ChessBoard;

public class Bishop extends Piece {

    public Bishop() {
        super("b", "Bishop");
        setIconLocation();
        super.setValue(3);
    }

    public Bishop(String color) {
        super(color, "Bishop");
        setIconLocation();
        super.setValue(3);
    }

    public Bishop(Piece p) {
        super(p);
    }

    /**
     * Sets the icon's picture location to the appropriate picture.
     */
    private void setIconLocation() {
        if (getColor().equals("w"))
            setIconLocation("/assets/Chess_blt60.png");
        else
            setIconLocation("/assets/Chess_bdt60.png");

    }

    public String toString() {
        return "Bi(" + getColor() + ")";
    }

    @Override
    public boolean isLegalMove(ChessBoard board, Movement playersMove)
    {
        if( Math.abs(playersMove.xDirection) == Math.abs(playersMove.yDirection) && board.isWayClear(playersMove.start, playersMove.end) && canPieceMoveLegally(board, playersMove.start, playersMove.end, playersMove.color) )
            return true;
        else
            return false;
    }

}
