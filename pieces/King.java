package pieces;

import board.ChessBoard;

public class King extends Piece {

    public King() {
        super("w", "King");
        setIconLocation();
        super.setValue(99);
    }

    public King(String color) {
        super(color, "King");
        setIconLocation();
        super.setValue(99);
    }

    public King(Piece p) {
        super(p);
    }

    /**
     * Sets the icon's picture location to the appropriate picture.
     */
    private void setIconLocation() {
        if (getColor().equals("w"))
            setIconLocation("/assets/Chess_klt60.png");
        else
            setIconLocation("/assets/Chess_kdt60.png");
    }

    public String toString() {
        return "Ki(" + getColor() + ")";
    }
    @Override
    public boolean isLegalMove(ChessBoard board, Movement playersMove)
    {

        if (playersMove.totalDistance == null)
            return false;
        // Cannot move beside the other king
        if (inRangeOfOtherKing(board, getColor(), playersMove.end)) {
            return false;
        }

        // Cannot move in a position diagonal from a pawn since the pawn can kill the king.
        if (willDieFromPawn(board, getColor(), playersMove.end)) {
            return false;
        }

        if (!board.getGamestate().kingIsSafe(board, playersMove.start, playersMove.end, playersMove.color)) {
            return false;
        }
        if( Math.abs(playersMove.xDirection) < 2 && Math.abs(playersMove.yDirection) < 2
                && board.isWayClear(playersMove.start, playersMove.end)
                && canPieceMoveLegally(board, playersMove.start,playersMove.end, playersMove.color))
            return true;
        else if(((playersMove.xDirection == 1 && playersMove.yDirection == 0)
                || (playersMove.yDirection == 1 && playersMove.xDirection == 0))
                && board.isWayClear(playersMove.start, playersMove.end)
                && canPieceMoveLegally(board, playersMove.start, playersMove.end, playersMove.color) )
            return true;
        //castling logic
        else if (board.getGrid()[playersMove.startY][playersMove.startX] != null) {
            String start = playersMove.start;
            String end = playersMove.end;
            // White can castle left if spaces are clear king and rook have not moved yet
            if (board.getGrid()[0][0] != null && start.equals("E1") && end.equals("C1") && canPieceMoveLegally(board, start, end, playersMove.color) && getTimesMoved() == 0 && board.getGrid()[0][0].getTimesMoved() == 0 && board.isWayClear("E1", "A1")) {
                int[] rookLocation = board.parseLocation("A1");
                if (board.getGrid()[rookLocation[0]][rookLocation[1]].getName().equals("Rook")) {
                    incrementTimesMoved();
                    board.forcedMove("A1", "D1");
                    return true;
                }
            }

            // Black can castle left if spaces are clear and king and rook have not moved yet
            else if (board.getGrid()[7][7] != null && start.equals("E8") && end.equals("G8") && canPieceMoveLegally(board, start, end, playersMove.color) && getTimesMoved() == 0 && board.getGrid()[7][7].getTimesMoved() == 0 && board.isWayClear("H8", "E8")) {
                int[] rookLocation = board.parseLocation("H8");
                if (board.getGrid()[rookLocation[0]][rookLocation[1]].getName().equals("Rook")) {
                    incrementTimesMoved();
                    board.forcedMove("H8", "F8");
                    return true;
                }
            }

            // White can castle right if spaces are clear and king and rook have not moved yet
            else if (board.getGrid()[0][7] != null && start.equals("E1") && end.equals("G1") && canPieceMoveLegally(board, start, end, playersMove.color) && getTimesMoved() == 0 && board.getGrid()[0][7].getTimesMoved() == 0 && board.isWayClear("E1", "H1")) {
                int[] rookLocation = board.parseLocation("H1");
                if (board.getGrid()[rookLocation[0]][rookLocation[1]].getName().equals("Rook")) {
                    incrementTimesMoved();
                    board.forcedMove("H1", "F1");
                    return true;
                }
            }

            // Black can castle right if spaces are clear and king and rook have not moved yet
            else if (board.getGrid()[7][0] != null && start.equals("E8") && end.equals("C8") && canPieceMoveLegally(board, start, end, playersMove.color) && getTimesMoved() == 0 && board.getGrid()[7][0].getTimesMoved() == 0 && board.isWayClear("A8", "E8")) {
                int[] rookLocation = board.parseLocation("A8");
                if (board.getGrid()[rookLocation[0]][rookLocation[1]].getName().equals("Rook")) {
                    incrementTimesMoved();
                    board.forcedMove("A8", "D8");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Intermediate function for use in {@link #isValidMove(ChessBoard, String, String)}.
     *
     * @param board        Current chess board being used.
     * @param currentColor Current player's turn's color.
     * @param endLocation  The end location of the move being made.
     * @return Returns whether or not making that move will put the king beside the other king - which would
     * be an invalid move.
     */
    private Boolean inRangeOfOtherKing(ChessBoard board, String currentColor, String endLocation) {
        if (currentColor.equals("w")) {
            int[] blackKingGridLocation = board.getGamestate().findKing(board, "b");
            String blackKingLocation = board.unparseLocation(blackKingGridLocation);
            if (blackKingLocation == null)
                return false;
            int[] distance = board.distance(endLocation, blackKingLocation);
            int rowDistance = Math.abs(distance[0]);
            int columnDistance = Math.abs(distance[1]);
            return rowDistance < 2 && columnDistance < 2;
        } else {
            int[] whiteKingGridLocation = board.getGamestate().findKing(board, "w");
            String whiteKingLocation = board.unparseLocation(whiteKingGridLocation);

            int[] distance = board.distance(endLocation, whiteKingLocation);
            int rowDistance = Math.abs(distance[0]);
            int columnDistance = Math.abs(distance[1]);

            return rowDistance < 2 && columnDistance < 2;
        }
    }

    /**
     * Intermediate function for use in {@link #isValidMove(ChessBoard, String, String)}.
     *
     * @param board        Current chess board being used.
     * @param currentColor Current player's turn's color.
     * @param endLocation  The end location of the move being made.
     * @return Returns whether or not the king will die from a pawn with the given move.
     */
    private Boolean willDieFromPawn(ChessBoard board, String currentColor, String endLocation) {
        Piece[][] boardGrid = board.getGrid();
        int[] start = board.parseLocation(endLocation);
        int row = start[0];
        int column = start[1];

        if (board.isWhiteTurn() || (board.isBlackTurn() && board.isBeingFlipped())) {
            try {
                Piece adjacentPiece = boardGrid[row + 1][column - 1];
                if (adjacentPiece != null) {
                    if (!adjacentPiece.getColor().equals(currentColor) && adjacentPiece.getName().equals("Pawn"))
                        return true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }

            try {
                Piece adjacentPiece = boardGrid[row + 1][column + 1];
                if (adjacentPiece != null) {
                    if (!adjacentPiece.getColor().equals(currentColor) && adjacentPiece.getName().equals("Pawn"))
                        return true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        } else {
            try {
                Piece adjacentPiece = boardGrid[row - 1][column - 1];
                if (adjacentPiece != null) {
                    if (!adjacentPiece.getColor().equals(currentColor) && adjacentPiece.getName().equals("Pawn"))
                        return true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }

            try {
                Piece adjacentPiece = boardGrid[row - 1][column + 1];
                if (adjacentPiece != null) {
                    if (!adjacentPiece.getColor().equals(currentColor) && adjacentPiece.getName().equals("Pawn"))
                        return true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }

        return false;
    }
}
