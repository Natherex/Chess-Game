package AI;
import board.ChessBoard;
import java.math.*;
import java.util.stream.StreamSupport;

public class AI {
    private int depth;
    private String startLocation;
    private String endLocation;
    private int[] bestStart ={0,0};
    private int[] bestEnd ={0,0};

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }
    /**
     * Finds the best move for the AI to take and stores them in the instance variables startLocation
     * and endLocation in the AI class
     * @param board Chessboard to be analyzed
     * @param color color of the current player
     * @return  int[0] yCoordinate of piece to move
     *          int[1] xCoordinate of piece to move
     *          int[2] yCoordinate of endLocation
     *          int[3] xCoordinate of endLocation
     */
    public int AIsMove(ChessBoard board,String color, int depth)
    {

        int AIMove = 0;
        AIMove = bestMove(board,AIMove,color,depth);

        startLocation = board.unparseLocation(bestStart);
        endLocation = board.unparseLocation(bestEnd);
        return AIMove;

    }
    private int bestMove(ChessBoard board, int totalScore, String color, int depth)
    {
        int[] startCoordinate;
        int[] endCoordinate;
        int bestMove = 0;
        int previousBest = 0;
        int scoreChange = 0;
        boolean isPreviousBest = false;
        boolean checkMate = false;
        if(depth <= 0 )
            return totalScore;
        for(int startX=0;startX<8;startX++)
        {
            for(int startY=0; startY<8; startY++)
            {
                for(int endX =0; endX<8; endX++)
                {
                    for( int endY = 0; endY<8;endY++)
                    {
                        startCoordinate = new int []{startY,startX};
                        endCoordinate = new int[]{endY,endX};
                        if(board.getGrid()[startY][startX] != null)
                        {
                            ChessBoard temp = new ChessBoard(board);
                            if(temp.unparseLocation(startCoordinate) != null && temp.unparseLocation(endCoordinate) != null && temp.getGamestate().kingIsSafe(temp,temp.unparseLocation(startCoordinate),temp.unparseLocation(endCoordinate),temp.currentPlayer()) && temp.movePiece(temp.unparseLocation(startCoordinate),temp.unparseLocation(endCoordinate))) {
                                temp.changeTurn();
                                temp.getGamestate().updateGameState(temp, temp.currentPlayer(), temp.unparseLocation(endCoordinate));

                                if(board.currentPlayer() =="b") {
                                    scoreChange = temp.getGamestate().getbScore() - board.getGamestate().getbScore();
                                    if (color == "b")
                                        bestMove = scoreChange + bestMove(temp, 0, color, depth - 1);
                                    else
                                        bestMove = 0 - scoreChange +bestMove(temp, 0, color, depth - 1);
                                }
                                else {
                                    scoreChange = temp.getGamestate().getwScore() - board.getGamestate().getwScore();
                                    if (color == "w")
                                        bestMove = scoreChange+ bestMove(temp, 0 , color, depth - 1);
                                    else
                                        bestMove = 0- scoreChange +bestMove(temp, 0, color, depth - 1);
                                }

                                if (!isPreviousBest) {
                                    if(board.currentPlayer() == color)
                                    {
                                        bestStart = startCoordinate;
                                        bestEnd = endCoordinate;
                                    }
                                    previousBest =bestMove;
                                    isPreviousBest = true;
                                }

                                if(bestMove == previousBest && Math.random() < 0.2) {
                                    if (board.currentPlayer() == color) {
                                        if (bestMove >= previousBest) {
                                            bestStart = startCoordinate;
                                            bestEnd = endCoordinate;
                                            previousBest = bestMove;
                                        }
                                    } else {
                                        if (bestMove <= previousBest) {
                                            previousBest = bestMove;
                                        }
                                    }
                                }else
                                {
                                    if (board.currentPlayer() == color) {
                                        if (bestMove > previousBest) {
                                            bestStart = startCoordinate;
                                            bestEnd = endCoordinate;
                                            previousBest = bestMove;
                                        }
                                    } else {
                                        if (bestMove < previousBest) {
                                            previousBest = bestMove;
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }

        return previousBest;
    }
}

