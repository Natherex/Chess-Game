package gamestate;

import pieces.*;

import board.ChessBoard;

import java.util.Arrays;

public class GameState {
    private int gameState;
    private boolean isWhiteTurn = true;
    private boolean isBlackTurn = false;
    private int[] fillersLocation;
    private int wScore = 0;
    private int bScore = 0;

    public GameState() {
        gameState = 0;
    }

    public GameState(GameState gs) {
        this.gameState = gs.gameState;
        this.isWhiteTurn = gs.isWhiteTurn;
        this.isBlackTurn = gs.isBlackTurn;
        this.wScore = gs.getwScore();
        this.bScore = gs.getbScore();
        if (fillersLocation != null)
            fillersLocation = Arrays.copyOf(gs.fillersLocation, gs.fillersLocation.length);
    }

    /**
     * Changes the current colors turn
     */
    public void changeTurn() {
        isWhiteTurn = !isWhiteTurn;
        isBlackTurn = !isBlackTurn;
    }

    /**
     * checks whos turn it is
     *
     * @return returns true if it is whites turn
     */
    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    /**
     * checks whos turn it is
     *
     * @return returns true if it is blacks turn
     */
    public boolean isBlackTurn() {
        return isBlackTurn;
    }

    /**
     * checks what state the game is currently in
     *
     * @return returns an integer that corresponds with a gamestate
     * - 1 is check , 2 is checkmate, 3 is stalemate, 0 is normal
     */
    public int getGameState() {
        return gameState;
    }

    /**
     * Updates the current game state
     * Sets the gamestate to an integer that corresponds with a gamestate
     * - 1 is check , 2 is checkmate, 3 is stalemate, 0 is normal
     */

    public int getwScore() {
        return wScore;
    }

    public void setwScore(int wScore) {
        this.wScore = wScore;
    }

    public void incrementWScore(int value) {
        wScore += value;
    }

    public int getbScore() {
        return bScore;
    }

    public void setbScore(int bScore) {
        this.bScore = bScore;
    }

    public void incrementBScore(int value) {
        bScore += value;
    }

    public void updateGameState(ChessBoard c, String color, String end) {
        if (isCheck(c, color)) {
            if (isCheckmate(c, fillersLocation, color)) {
                if (color == "w")
                    bScore += 999;
                else
                    wScore += 999;
                gameState = 2;
            } else {
                if (color == "w")
                    bScore += 1;
                else
                    wScore += 1;
                gameState = 1;

            }
        } else if (isStaleMate(c, color)) {
            gameState = 3;
        } else {
            if (gameState == 1) {
                if (c.currentPlayer() == "w") {
                    wScore -= 1;
                } else
                    bScore -= 1;
            }
            gameState = 0;
        }

        promote(c, end);

    }

    /**
     * Checks if the chessboard is currently in the state of checkmate for the current player
     * Assumes king is in check and decides if it is actually a checkmate.
     *
     * @param c                Chessboard to be analyzed
     * @param checkersLocation location of the piece that has caused check
     * @param color            color of the current player
     * @return true if chessboard is in checkmate
     */
    public boolean isCheckmate(ChessBoard c, int[] checkersLocation, String color) {
        return isCheck(c, color) && !canKingMove(c, color) && (doubleCheck(c) || !canKingBeBlocked(c, checkersLocation, color)) && (doubleCheck(c) || !canCheckerBeTaken(c, checkersLocation, color));
    }
    //returns true if in check or if in checkmate

    /**
     * Checks if the chessboard is currently in the state of check for the current player
     *
     * @param c     Chessboard to be analyzed
     * @param color color of the current player
     * @return if the player is currently in check
     */
    public boolean isCheck(ChessBoard c, String color) {
        String oColor;
        if (color == "w")
            oColor = "b";
        else
            oColor = "w";
        int[] kingsLocation = findKing(c, color);
        //checks if the king would be put in check if it makes this move by seeing if any pieces can fill the kings current location
        // THere is one special case where it is actually a pawn that can fill the tile but cannot actually attack
        // that tile and so this does not threaten the king.
        return canTileBeFilled(c, kingsLocation, oColor) && (!c.getGrid()[fillersLocation[0]][fillersLocation[1]].getName().equals("Pawn") || kingsLocation[1] != fillersLocation[1]);

    }

    public boolean kingIsSafe(ChessBoard c, String start, String end, String playersColor) {
        ChessBoard temp = new ChessBoard(c);
        int[] startCoordinate = c.parseLocation(start);
        int startY = startCoordinate[0];
        int startX = startCoordinate[1];

        int[] endCoordinate = c.parseLocation(end);
        int endY = endCoordinate[0];
        int endX = endCoordinate[1];


        if (temp.getGrid()[endY][endX] != null && !temp.getGrid()[endY][endX].getColor().equals(playersColor))
            temp.removePiece(end);
        if (temp.getGrid()[endY][endX] == null)
            temp.forcedMove(start, end);

        return !isCheck(temp, playersColor);
    }

    /**
     * Checks if the Chessboard is in stalemate for the current player
     *
     * @param c     Chessboard to be analyzed
     * @param color color of the player
     * @return if the player is currently in stalemate
     */
    public boolean isStaleMate(ChessBoard c, String color) {

        int[] coordinate = new int[2];
        int[] otherCoordinate = new int[2];
        int isAble = 0;
        ChessBoard copyBoard = new ChessBoard(c);
        String oColor;
        String someStart;
        String someEnd;

        if (color.equals("w"))
            oColor = "b";
        else
            oColor = "w";

        //First loop to set the theoretical start
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                //coordinate 0 is Y direction/row
                coordinate[0] = j;
                coordinate[1] = i;

                someStart = c.unparseLocation(coordinate);

                //Second loop to set the theoretical end
                for (int a = 0; a < 8; a++) {
                    for (int b = 0; b < 8; b++) {

                        otherCoordinate[0] = b;
                        otherCoordinate[1] = a;

                        someEnd = c.unparseLocation(otherCoordinate);

                        if (copyBoard.getGrid()[coordinate[0]][coordinate[1]] != null) {
                            if (copyBoard.getGrid()[coordinate[0]][coordinate[1]].getColor().equals(color)) {
                                if (copyBoard.getGrid()[coordinate[0]][coordinate[1]].isValidMove(copyBoard, someStart, someEnd)) {
                                    isAble++;
                                    copyBoard = new ChessBoard(c);

                                }
                            }
                        }
                    }
                }
            }
        }

        return isAble == 0;

    }

    /**
     * Checks if the king has any valid moves
     *
     * @param c Chessboard to be analyzed
     * @return if the current players king can move
     */
    public boolean canKingMove(ChessBoard c, String color) {
        int[] kingsLocation = findKing(c, color);
        int[] temp;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (kingsLocation[0] + j < 8 && kingsLocation[0] + j >= 0) {
                    if (kingsLocation[1] + i < 8 && kingsLocation[1] + i >= 0) {
                        temp = new int[]{kingsLocation[0] + j, kingsLocation[1] + i};
                        //if((c.getGrid()[temp[0]][temp[1]] == null || c.getGrid()[temp[0]][temp[1]].getColor().equals(c.oppositePlayer()))
                        //		&& !canTileBeFilled(c, temp,c.oppositePlayer()))
                        if (kingIsSafe(c, c.unparseLocation(kingsLocation), c.unparseLocation(temp), color))
                            return true;
                    }
                }
            }
        }
        return false;

    }

    /**
     * Finds the king on the chessboard
     *
     * @param c     Chessboard to be analyzed
     * @param color color of the current player
     * @return location of the king(y,x)
     */
    public int[] findKing(ChessBoard c, String color) {
        int[] coordinates = new int[2];
        for (int row = 0; row < c.getHeight(); row++) {
            for (int column = 0; column < c.getLength(); column++) {
                if (c.getGrid()[row][column] != null && c.getGrid()[row][column].getName().equals("King") && c.getGrid()[row][column].getColor().equals(color)) {
                    coordinates[0] = row;
                    coordinates[1] = column;
                    return coordinates;
                }
            }
        }
        return null;

    }

    /**
     * Checks to see if another piece can save the king from check/checkmate
     *
     * @param c                Chessboard to be analyzed
     * @param color            color of the current player
     * @param checkersLocation location of the piece that has caused check
     * @return if the king can be saved with another piece
     */
    public boolean canKingBeBlocked(ChessBoard c, int[] checkersLocation, String color) {
        int[] kingsLocation = new int[2];
        kingsLocation = findKing(c, color);
        if (c.getGrid()[checkersLocation[0]][checkersLocation[1]].getName().equals("Knight")) {
            return false;
        }
        if (c.getGrid()[checkersLocation[0]][checkersLocation[1]].getName().equals("Pawn")) {
            return false;
        }
        while (!Arrays.equals(kingsLocation, checkersLocation)) {
            if (canTileBeFilled(c, checkersLocation, color)) {
                return true;
            }
            if (checkersLocation[0] < kingsLocation[0]) {
                checkersLocation[0]++;
            } else if (checkersLocation[0] > kingsLocation[0]) {
                checkersLocation[0]--;
            }
            if (checkersLocation[1] < kingsLocation[1]) {
                checkersLocation[1]++;
            } else if (checkersLocation[1] > kingsLocation[1]) {
                checkersLocation[1]--;
            }
        }

        return false;
    }

    /**
     * Checks if the specified tile can be filled by another piece
     *
     * @param c          Chessboard to be analyzed
     * @param color      color of the current player
     * @param coordinate location of the piece that wants to be checked
     * @return if the king can be saved with another piece
     */
    public boolean canTileBeFilled(ChessBoard c, int[] coordinate, String color) {
        if (coordinate == null || color == null) {
            return false;
        }
        //check if knight can fill tile
        if (coordinate[0] >= 1 && coordinate[1] >= 2 && c.getGrid()[coordinate[0] - 1][coordinate[1] - 2] != null) {
            if (c.getGrid()[coordinate[0] - 1][coordinate[1] - 2].getName().equals("Knight")) {
                if (c.getGrid()[coordinate[0] - 1][coordinate[1] - 2].getColor().equals(color)) {
                    fillersLocation = new int[]{coordinate[0] - 1, coordinate[1] - 2};
                    return true;
                }
            }
        }
        if (coordinate[0] >= 2 && coordinate[1] >= 1 && c.getGrid()[coordinate[0] - 2][coordinate[1] - 1] != null) {
            if (c.getGrid()[coordinate[0] - 2][coordinate[1] - 1].getName().equals("Knight")) {
                if (c.getGrid()[coordinate[0] - 2][coordinate[1] - 1].getColor().equals(color)) {
                    fillersLocation = new int[]{coordinate[0] - 2, coordinate[1] - 1};
                    return true;
                }
            }
        }
        if (coordinate[0] < 7 && coordinate[1] >= 2 && c.getGrid()[coordinate[0] + 1][coordinate[1] - 2] != null) {
            if (c.getGrid()[coordinate[0] + 1][coordinate[1] - 2].getName().equals("Knight")) {
                if (c.getGrid()[coordinate[0] + 1][coordinate[1] - 2].getColor().equals(color)) {
                    fillersLocation = new int[]{coordinate[0] + 1, coordinate[1] - 2};
                    return true;
                }
            }
        }
        if (coordinate[0] < 6 && coordinate[1] >= 1 && c.getGrid()[coordinate[0] + 2][coordinate[1] - 1] != null) {
            if (c.getGrid()[coordinate[0] + 2][coordinate[1] - 1].getName().equals("Knight")) {
                if (c.getGrid()[coordinate[0] + 2][coordinate[1] - 1].getColor().equals(color)) {
                    fillersLocation = new int[]{coordinate[0] + 2, coordinate[1] - 1};
                    return true;
                }
            }
        }
        if (coordinate[0] >= 1 && coordinate[1] < 6 && c.getGrid()[coordinate[0] - 1][coordinate[1] + 2] != null) {
            if (c.getGrid()[coordinate[0] - 1][coordinate[1] + 2].getName().equals("Knight")) {
                if (c.getGrid()[coordinate[0] - 1][coordinate[1] + 2].getColor().equals(color)) {
                    fillersLocation = new int[]{coordinate[0] - 1, coordinate[1] + 2};
                    return true;
                }
            }
        }
        if (coordinate[0] >= 2 && coordinate[1] < 7 && c.getGrid()[coordinate[0] - 2][coordinate[1] + 1] != null) {
            if (c.getGrid()[coordinate[0] - 2][coordinate[1] + 1].getName().equals("Knight")) {
                if (c.getGrid()[coordinate[0] - 2][coordinate[1] + 1].getColor().equals(color)) {
                    fillersLocation = new int[]{coordinate[0] - 2, coordinate[1] + 1};
                    return true;
                }
            }
        }
        if (coordinate[0] < 7 && coordinate[1] < 6 && c.getGrid()[coordinate[0] + 1][coordinate[1] + 2] != null) {
            if (c.getGrid()[coordinate[0] + 1][coordinate[1] + 2].getName().equals("Knight")) {
                if (c.getGrid()[coordinate[0] + 1][coordinate[1] + 2].getColor().equals(color)) {
                    fillersLocation = new int[]{coordinate[0] + 1, coordinate[1] + 2};
                    return true;
                }
            }
        }
        if (coordinate[0] < 6 && coordinate[1] < 7 && c.getGrid()[coordinate[0] + 2][coordinate[1] + 1] != null) {
            if (c.getGrid()[coordinate[0] + 2][coordinate[1] + 1].getName().equals("Knight")) {
                if (c.getGrid()[coordinate[0] + 2][coordinate[1] + 1].getColor().equals(color)) {
                    fillersLocation = new int[]{coordinate[0] + 2, coordinate[1] + 1};
                    return true;
                }
            }
        }

        //check right
        int i = 1;
        boolean open = true;
        while (coordinate[1] + i < 8 && open) {

            if (c.getGrid()[coordinate[0]][coordinate[1] + i] != null) {
                if (c.getGrid()[coordinate[0]][coordinate[1] + i].getName().equals("Rook")
                        || c.getGrid()[coordinate[0]][coordinate[1] + i].getName().equals("Queen")) {
                    if (c.getGrid()[coordinate[0]][coordinate[1] + i].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0], coordinate[1] + i};
                        return true;
                    } else
                        break;
                } else
                    break;

            }
            i++;
        }
        //check up
        i = 1;

        while (coordinate[0] + i < 8 && open) {

            if (c.getGrid()[coordinate[0] + i][coordinate[1]] != null) {
                if (c.getGrid()[coordinate[0] + i][coordinate[1]].getName().equals("Rook")
                        || c.getGrid()[coordinate[0] + i][coordinate[1]].getName().equals("Queen")) {
                    if (c.getGrid()[coordinate[0] + i][coordinate[1]].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] + i, coordinate[1]};
                        return true;
                    } else
                        break;
                } else
                    break;

            }
            i++;
        }
        //check left
        i = 1;

        while (coordinate[1] - i >= 0 && open) {

            if (c.getGrid()[coordinate[0]][coordinate[1] - i] != null) {
                if (c.getGrid()[coordinate[0]][coordinate[1] - i].getName().equals("Rook")
                        || c.getGrid()[coordinate[0]][coordinate[1] - i].getName().equals("Queen")) {
                    if (c.getGrid()[coordinate[0]][coordinate[1] - i].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0], coordinate[1] - i};
                        return true;
                    } else
                        break;
                } else
                    break;

            }
            i++;
        }
        //check to down
        i = 1;

        while (coordinate[0] - i >= 0 && open) {

            if (c.getGrid()[coordinate[0] - i][coordinate[1]] != null) {
                if (c.getGrid()[coordinate[0] - i][coordinate[1]].getName().equals("Rook")
                        || c.getGrid()[coordinate[0] - i][coordinate[1]].getName().equals("Queen")) {
                    if (c.getGrid()[coordinate[0] - i][coordinate[1]].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] - i, coordinate[1]};
                        return true;
                    } else
                        break;
                } else
                    break;

            }
            i++;
        }
        //check top right
        i = 1;

        while (coordinate[0] + i < 8 && coordinate[1] + i < 8 && open) {

            if (c.getGrid()[coordinate[0] + i][coordinate[1] + i] != null) {
                if (c.getGrid()[coordinate[0] + i][coordinate[1] + i].getName().equals("Bishop")
                        || c.getGrid()[coordinate[0] + i][coordinate[1] + i].getName().equals("Queen")) {
                    if (c.getGrid()[coordinate[0] + i][coordinate[1] + i].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] + i, coordinate[1] + i};
                        return true;
                    } else
                        break;
                } else
                    break;

            }
            i++;
        }
        //check top left
        i = 1;

        while (coordinate[0] - i >= 0 && coordinate[1] + i < 8 && open) {

            if (c.getGrid()[coordinate[0] - i][coordinate[1] + i] != null) {
                if (c.getGrid()[coordinate[0] - i][coordinate[1] + i].getName().equals("Bishop")
                        || c.getGrid()[coordinate[0] - i][coordinate[1] + i].getName().equals("Queen")) {
                    if (c.getGrid()[coordinate[0] - i][coordinate[1] + i].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] - i, coordinate[1] + i};
                        return true;
                    } else
                        break;
                } else
                    break;

            }
            i++;
        }
        //check bottom left
        i = 1;

        while (coordinate[0] - i >= 0 && coordinate[1] - i >= 0 && open) {

            if (c.getGrid()[coordinate[0] - i][coordinate[1] - i] != null) {
                if (c.getGrid()[coordinate[0] - i][coordinate[1] - i].getName().equals("Bishop")
                        || c.getGrid()[coordinate[0] - i][coordinate[1] - i].getName().equals("Queen")) {
                    if (c.getGrid()[coordinate[0] - i][coordinate[1] - i].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] - i, coordinate[1] - i};
                        return true;
                    } else
                        break;
                } else
                    break;

            }
            i++;
        }
        //check bottom right
        i = 1;

        while (coordinate[0] + i < 8 && coordinate[1] - i >= 0 && open) {

            if (c.getGrid()[coordinate[0] + i][coordinate[1] - i] != null) {
                if (c.getGrid()[coordinate[0] + i][coordinate[1] - i].getName().equals("Bishop") || c.getGrid()[coordinate[0] + i][coordinate[1] - i].getName().equals("Queen")) {
                    if (c.getGrid()[coordinate[0] + i][coordinate[1] - i].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] + i, coordinate[1] - i};
                        return true;
                    } else
                        break;
                } else
                    break;


            }
            i++;
        }

        // check king
        int[] temp;
        for (i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (coordinate[0] + j < 8 && coordinate[0] + j >= 0) {
                    if (coordinate[1] + i < 8 && coordinate[1] + i >= 0) {
                        if (c.getGrid()[coordinate[0] + j][coordinate[1] + i] != null && c.getGrid()[coordinate[0] + j][coordinate[1] + i].getName().equals("King") && (i != 0 || j != 0)) {
                            temp = new int[]{coordinate[0] + j, coordinate[1] + i};
                            if (kingIsSafe(c, c.unparseLocation(temp), c.unparseLocation(coordinate), color)) {
                                fillersLocation = temp;
                                return true;
                            }
                        }
                    }
                }
            }
        }

        //check if white pawn can fill tile
        if (color.equals("w")) {
            //check if pawn can fill tile
            if (coordinate[0] > 0 && c.getGrid()[coordinate[0] - 1][coordinate[1]] != null) {
                if (c.getGrid()[coordinate[0] - 1][coordinate[1]].getName().equals("Pawn")) {
                    if (c.getGrid()[coordinate[0] - 1][coordinate[1]].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] - 1, coordinate[1]};
                        return true;
                    }
                }
            }
            if (coordinate[0] > 0 && coordinate[1] > 0 && c.getGrid()[coordinate[0] - 1][coordinate[1] - 1] != null) {
                if (c.getGrid()[coordinate[0] - 1][coordinate[1] - 1].getName().equals("Pawn")) {
                    if (c.getGrid()[coordinate[0] - 1][coordinate[1] - 1].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] - 1, coordinate[1] - 1};
                        return true;
                    }
                }
            }
            if (coordinate[0] > 0 && coordinate[1] < 7 && c.getGrid()[coordinate[0] - 1][coordinate[1] + 1] != null) {
                if (c.getGrid()[coordinate[0] - 1][coordinate[1] + 1].getName().equals("Pawn")) {
                    if (c.getGrid()[coordinate[0] - 1][coordinate[1] + 1].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] - 1, coordinate[1] + 1};
                        return true;
                    }
                }
            }
        }
        //check if black pawn can fill tile
        if (color.equals("b")) {

            if (coordinate[0] < 7 && c.getGrid()[coordinate[0] + 1][coordinate[1]] != null) {
                if (c.getGrid()[coordinate[0] + 1][coordinate[1]].getName().equals("Pawn")) {
                    if (c.getGrid()[coordinate[0] + 1][coordinate[1]].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] + 1, coordinate[1]};
                        return true;
                    }
                }
            }
            if (coordinate[0] < 7 && coordinate[1] > 0 && c.getGrid()[coordinate[0] + 1][coordinate[1] - 1] != null) {
                if (c.getGrid()[coordinate[0] + 1][coordinate[1] - 1].getName().equals("Pawn")) {
                    if (c.getGrid()[coordinate[0] + 1][coordinate[1] - 1].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] + 1, coordinate[1] - 1};
                        return true;
                    }
                }
            }
            if (coordinate[0] < 7 && coordinate[1] < 7 && c.getGrid()[coordinate[0] + 1][coordinate[1] + 1] != null) {
                if (c.getGrid()[coordinate[0] + 1][coordinate[1] + 1].getName().equals("Pawn")) {
                    if (c.getGrid()[coordinate[0] + 1][coordinate[1] + 1].getColor().equals(color)) {
                        fillersLocation = new int[]{coordinate[0] + 1, coordinate[1] + 1};
                        return true;
                    }
                }
            }
        }
        return false;


    }

    public boolean canCheckerBeTaken(ChessBoard c, int[] checkersLocation, String color) {
        return false;
    }

    public boolean doubleCheck(ChessBoard c) {
        return false;
    }

    /**
     * Checks if a pawn is able to promote
     *
     * @param board Current board
     * @param end   Ending location of the piece
     */
    public void promote(ChessBoard board, String end) {


        int[] endLocation = board.parseLocation(end);
        int xSpot = endLocation[1];
        int ySpot = endLocation[0];

        //Checks if a white pawn is on the 8th rank
        if (ySpot == 7 && board.getGrid()[ySpot][xSpot].getName().equals("Pawn")) {

            board.getGrid()[ySpot][xSpot] = new Queen("w");
        }

        //Checks if a black pawn is on the 1st rank
        if (ySpot == 0 && board.getGrid()[ySpot][xSpot].getName().equals("Pawn")) {

            board.getGrid()[ySpot][xSpot] = new Queen("b");
        }
    }
}

