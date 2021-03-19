import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.TextAlignment;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import board.ChessBoard;
import pieces.Piece;
import AI.AI;

public final class GUIMain extends Application {
    private static final String SELECTION_URL = "/assets/selection.png";
    private boolean running;

    private String startLocation;
    private String endLocation;
    private ChessBoard board;
    private AI computerOne;
    private AI computerTwo;

    private StackPane[][] stackPaneGrid;
    private Piece[][] pieceGrid;
    private Label playerTurnLabel;
    private VBox numberVBox;
    private HBox letterHBox;
    private Label whiteScore;
    private Label blackScore;

    /**
     * Enumerable for the different gamemodes.
     */
    private enum gamemode {
        WHITE_VS_BLACK, WHITE_VS_COMPUTER, COMPUTER_VS_BLACK, COMPUTER_VS_COMPUTER
    }

    private gamemode currentMode;

    /**
     * Creates the main menu screen and sets the current mode.
     */
    private void mainMenu() {
        Stage mainMenu = new Stage();

        // Creates the title
        ImageView whiteKing = new ImageView(new Image("/assets/Chess_klt60.png"));
        ImageView blackKing = new ImageView(new Image("/assets/Chess_kdt60.png"));
        Label title = new Label("Chess");
        title.setFont(new Font("Ink Free", 60));
        title.setTextAlignment(TextAlignment.CENTER);
        title.setAlignment(Pos.TOP_CENTER);
        title.setTextFill(Color.web("#ffffff"));

        HBox titleBox = new HBox();
        titleBox.getChildren().addAll(whiteKing, title, blackKing);
        titleBox.setAlignment(Pos.CENTER);

        // Creates the buttons
        Font buttonFont = new Font("Times New Roman", 20);

        Button optionOne = new Button("White vs. Black");
        optionOne.setMinSize(235, 85);
        optionOne.setFont(buttonFont);
        optionOne.setOnAction(event -> {
            currentMode = gamemode.WHITE_VS_BLACK;
            mainMenu.close();
        });

        Button optionTwo = new Button("White vs. Computer");
        optionTwo.setMinSize(235, 85);
        optionTwo.setFont(buttonFont);
        optionTwo.setOnAction(event -> {
            currentMode = gamemode.WHITE_VS_COMPUTER;
            computerOne = new AI();
            mainMenu.close();
        });

        Button optionThree = new Button("Computer vs. Black");
        optionThree.setMinSize(235, 85);
        optionThree.setFont(buttonFont);
        optionThree.setOnAction(event -> {
            currentMode = gamemode.COMPUTER_VS_BLACK;
            computerOne = new AI();
            mainMenu.close();
        });

        Button optionFour = new Button("Computer vs. Computer");
        optionFour.setMinSize(235, 85);
        optionFour.setFont(buttonFont);
        optionFour.setOnAction(event -> {
            currentMode = gamemode.COMPUTER_VS_COMPUTER;
            computerOne = new AI();
            computerTwo = new AI();
            mainMenu.close();
        });

        VBox buttonBox = new VBox();
        buttonBox.getChildren().addAll(optionOne, optionTwo, optionThree);
        buttonBox.setSpacing(20);
        buttonBox.setPadding(new Insets(15, 0, 20, 0));
        buttonBox.setAlignment(Pos.CENTER);

        // Creates the main window group.
        BorderPane mainWindow = new BorderPane();
        mainWindow.setTop(titleBox);
        mainWindow.setBottom(buttonBox);
        String backgroundImageLocation = "/assets/background-brown-carpentry-326311.jpg";
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(backgroundImageLocation),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        mainWindow.setBackground(new Background(backgroundImage));

        // Adds all components to the actual window.
        mainMenu.setOnCloseRequest(event -> System.exit(0));
        mainMenu.getIcons().add(new Image("/assets/Chess_klt60.png"));
        mainMenu.setTitle("Chess");
        mainMenu.setScene(new Scene(mainWindow, 300, 445)); // +105px for the height if adding the Computer vs. Computer option back.
        mainMenu.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) {
        mainMenu();

        board = new ChessBoard(false);
        board.initialize();
        pieceGrid = board.getGrid();

        // Initializes 8x8 StackPanes and adds event handler to each one.
        stackPaneGrid = new StackPane[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                stackPaneGrid[row][column] = new StackPane();
                stackPaneGrid[row][column].setOnMouseClicked(new MouseEventHandler(row, column));
            }
        }

        // Sets the background for each square on the grid in an alternating pattern.
        boolean isCurrentlyWhiteSquare = false;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (isCurrentlyWhiteSquare) {
                    stackPaneGrid[row][column].setBackground(new Background(new BackgroundFill(ChessBoard.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    stackPaneGrid[row][column].setBackground(new Background(new BackgroundFill(ChessBoard.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));
                }

                // Adds the current piece to the StackPane as well.
                if (pieceGrid[row][column] != null) {
                    String imgLocation = pieceGrid[row][column].getIconLocation();
                    ImageView img = new ImageView(new Image(imgLocation));
                    stackPaneGrid[row][column].getChildren().addAll(new ImageView(), img);
                } else {
                    ImageView img = new ImageView();
                    stackPaneGrid[row][column].getChildren().addAll(new ImageView(), img);
                }

                isCurrentlyWhiteSquare = !isCurrentlyWhiteSquare;
            }
            isCurrentlyWhiteSquare = !isCurrentlyWhiteSquare;
        }

        // Sets row and column constraints for each square on the grid to be 60x60.
        GridPane mainBoard = new GridPane();
        for (int i = 0; i < 8; i++) {
            mainBoard.getColumnConstraints().add(new ColumnConstraints(60));
            mainBoard.getRowConstraints().add(new RowConstraints(60));
        }
        mainBoard.setPrefSize(480, 480);

        // Adds all the StackPanes to the GridPane.
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                mainBoard.add(stackPaneGrid[7 - row][column], column, row);
            }
        }

        // Create the row numbers
        numberVBox = new VBox();
        for (int i = 0; i < 8; i++) {
            Label numbers = new Label(String.valueOf(ChessBoard.FLIPPED_ROWS[i]));
            numbers.setFont(new Font("Arial", 20));
            numbers.setTextAlignment(TextAlignment.CENTER);
            numbers.setAlignment(Pos.CENTER);
            numbers.setMinWidth(20);
            numbers.setMaxHeight(60);
            numbers.setMinHeight(60);
            numberVBox.getChildren().add(numbers);
        }

        // Create the column letters
        letterHBox = new HBox();
        Label[] letters = new Label[8];
        for (int i = 0; i < 8; i++) {
            letters[i] = new Label(Character.toString(ChessBoard.VALID_COLUMNS[i]));
            letters[i].setFont(new Font("Arial", 20));
            letters[i].setTextAlignment(TextAlignment.CENTER);
            letters[i].setAlignment(Pos.CENTER);
            letters[i].setMinWidth(60);
            letters[i].setMinHeight(25);
            letterHBox.getChildren().add(letters[i]);
        }

        // Creates the label for whose turn is it.
        playerTurnLabel = new Label(board.currentTurnString());
        playerTurnLabel.setTextAlignment(TextAlignment.CENTER);
        playerTurnLabel.setAlignment(Pos.CENTER);
        playerTurnLabel.setFont(new Font("Arial", 20));
        playerTurnLabel.setMinHeight(25);
        playerTurnLabel.setMinWidth(480);

        // Add the rows and columns to the mainBoardGUI GridPane
        GridPane mainBoardGUI = new GridPane();
        mainBoardGUI.add(playerTurnLabel, 1, 0);
        mainBoardGUI.add(numberVBox, 0, 1);
        mainBoardGUI.add(mainBoard, 1, 1);
        mainBoardGUI.add(letterHBox, 1, 2);

        // Creates the score panel
        whiteScore = new Label("White's Score: " + board.getGamestate().getwScore());
        whiteScore.setTextAlignment(TextAlignment.LEFT);
        whiteScore.setFont(new Font("Times New Roman", 20));
        whiteScore.setAlignment(Pos.CENTER_LEFT);
        whiteScore.setMinSize(250, 25);
        whiteScore.setPadding(new Insets(0, 0, 0, 5));

        blackScore = new Label("Black's Score: " + board.getGamestate().getbScore());
        blackScore.setTextAlignment(TextAlignment.RIGHT);
        blackScore.setFont(new Font("Times new Roman", 20));
        blackScore.setAlignment(Pos.CENTER_RIGHT);
        blackScore.setMinSize(250, 25);
        blackScore.setPadding(new Insets(0, 5, 0, 0));

        HBox scorePanel = new HBox();
        scorePanel.getChildren().addAll(whiteScore, blackScore);

        // Adds all the components to a main window frame
        BorderPane mainWindow = new BorderPane();
        mainWindow.setCenter(mainBoardGUI);
        mainWindow.setBottom(scorePanel);

        // Creates a new scene and adds it to the stage
        Scene mainScene = new Scene(mainWindow, 500, 555);
        primaryStage.getIcons().add(new Image("/assets/Chess_klt60.png"));
        primaryStage.setTitle("Chess");
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Main game loop
        AnimationTimer mainLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Checks if the two locations are the same location to avoid NullPointerExceptions
                if (startLocation != null && endLocation != null) {
                    if (startLocation.equals(endLocation)) {
                        startLocation = null;
                        endLocation = null;
                    }
                }

                // Main game logic
                switch (currentMode) {
                    // Player vs. Player logic
                    case WHITE_VS_BLACK:
                        if (startLocation != null && endLocation != null) {
                            if (board.getGamestate().kingIsSafe(board, startLocation, endLocation, board.currentPlayer()) && board.movePiece(startLocation, endLocation)) {
                                board.changeTurn();
                                board.getGamestate().updateGameState(board, board.currentPlayer(), endLocation);
                                updateWindow();
                            }

                            startLocation = null;
                            endLocation = null;
                        }
                        break;

                    // Player vs. Computer logic
                    case WHITE_VS_COMPUTER:
                        if (board.isWhiteTurn() && startLocation != null && endLocation != null) {
                            if (board.getGamestate().kingIsSafe(board, startLocation, endLocation, board.currentPlayer()) && board.movePiece(startLocation, endLocation)) {
                                board.changeTurn();
                                board.getGamestate().updateGameState(board, board.currentPlayer(), endLocation);
                                updateWindow();
                            }

                            startLocation = null;
                            endLocation = null;
                        } else if (board.isBlackTurn()) {
                            computerOne.AIsMove(board, board.currentPlayer(), 2);
                            startLocation = computerOne.getStartLocation();
                            endLocation = computerOne.getEndLocation();

                            if (board.getGamestate().kingIsSafe(board, startLocation, endLocation, board.currentPlayer()) && board.movePiece(startLocation, endLocation)) {
                                board.changeTurn();
                                board.getGamestate().updateGameState(board, board.currentPlayer(), endLocation);
                                updateWindow();
                            }

                            startLocation = null;
                            endLocation = null;
                        }
                        break;

                    // Computer vs. Player logic
                    case COMPUTER_VS_BLACK:
                        if (board.isWhiteTurn()) {
                            computerOne.AIsMove(board, board.currentPlayer(), 2);
                            startLocation = computerOne.getStartLocation();
                            endLocation = computerOne.getEndLocation();

                            if (board.getGamestate().kingIsSafe(board, startLocation, endLocation, board.currentPlayer()) && board.movePiece(startLocation, endLocation)) {
                                board.changeTurn();
                                board.getGamestate().updateGameState(board, board.currentPlayer(), endLocation);
                                updateWindow();
                            }

                            startLocation = null;
                            endLocation = null;
                        } else if (board.isBlackTurn() && startLocation != null && endLocation != null) {
                            if (board.getGamestate().kingIsSafe(board, startLocation, endLocation, board.currentPlayer()) && board.movePiece(startLocation, endLocation)) {
                                board.changeTurn();
                                board.getGamestate().updateGameState(board, board.currentPlayer(), endLocation);
                                updateWindow();
                            }

                            startLocation = null;
                            endLocation = null;
                        }
                        break;

                    // Computer vs. Computer
                    case COMPUTER_VS_COMPUTER:
                        if (board.isWhiteTurn()) {
                            computerOne.AIsMove(board, board.currentPlayer(), 2);
                            startLocation = computerOne.getStartLocation();
                            endLocation = computerOne.getEndLocation();

                            if (board.getGamestate().kingIsSafe(board, startLocation, endLocation, board.currentPlayer()) && board.movePiece(startLocation, endLocation)) {
                                board.changeTurn();
                                board.getGamestate().updateGameState(board, board.currentPlayer(), endLocation);
                                updateWindow();
                            }

                            startLocation = null;
                            endLocation = null;
                        } else if (board.isBlackTurn()) {
                            computerTwo.AIsMove(board, board.currentPlayer(), 2);
                            startLocation = computerTwo.getStartLocation();
                            endLocation = computerTwo.getEndLocation();

                            if (board.getGamestate().kingIsSafe(board, startLocation, endLocation, board.currentPlayer()) && board.movePiece(startLocation, endLocation)) {
                                board.changeTurn();
                                board.getGamestate().updateGameState(board, board.currentPlayer(), endLocation);
                                updateWindow();
                            }

                            startLocation = null;
                            endLocation = null;
                        }
                }


                // Checks if the game is over: 2 - Checkmate, 3 - Stalemate
                if (board.getGamestate().getGameState() == 2 || board.getGamestate().getGameState() == 3) {
                    running = false;

                    // Opens the play again windows if not already opened.
                    if (playAgainState == 0) {
                        playAgainState = 1;
                        createPlayAgainWindow();
                    }

                    // Resets the board if the user wants to play again.
                    else if (playAgainState == 2) {
                        playAgainState = 0;
                        board.resetBoard();
                        updateWindow();
                        running = true;
                    }

                    // If user does not want to play again, the game closes.
                    else if (playAgainState == 3) {
                        primaryStage.close();
                    }
                }
            }
        };

        running = true;
        mainLoop.start();
    }

    /**
     * Inner class to handle the mouse click events.
     */
    private class MouseEventHandler implements EventHandler<MouseEvent> {
        private int row;
        private int column;

        public MouseEventHandler(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public void handle(MouseEvent event) {
            if (running) {
                if (startLocation == null) {
                    startLocation = board.unparseLocation(new int[]{row, column});
                    addSelection();
                } else if (endLocation == null) {
                    endLocation = board.unparseLocation(new int[]{row, column});
                    clearSelection();
                }
            }
        }
    }

    /**
     * Updates all the squares to reflect the state of the current board.
     */
    private void updateWindow() {
        pieceGrid = board.getGrid();

        // Updates all the chess board locations
        ObservableList<Node> nodes;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                nodes = stackPaneGrid[row][column].getChildren();

                // Sets the image at the current location to the piece at that location.
                // Sets the image to null if there is no piece there.
                if (pieceGrid[row][column] != null) {
                    String imgLocation = pieceGrid[row][column].getIconLocation();
                    ImageView img = (ImageView) (nodes.get(1));
                    img.setImage(new Image(imgLocation));
                } else {
                    ImageView img = (ImageView) (nodes.get(1));
                    img.setImage(null);
                }
            }
        }

        // Updates the current turn string as well as the score
        playerTurnLabel.setText(board.currentTurnString());
        whiteScore.setText("White's Score: " + board.getGamestate().getwScore());
        blackScore.setText("Black's Score: " + board.getGamestate().getbScore());

        // Updates the rows numbers and column letters if the board is being flipped
        if (board.isBeingFlipped() && board.isBlackTurn()) {
            // Update rows
            nodes = numberVBox.getChildren();
            for (int i = 0; i < 8; i++) {
                Label lbl = (Label) (nodes.get(i));
                lbl.setText(String.valueOf(ChessBoard.VALID_ROWS[i]));
            }

            // Update columns
            nodes = letterHBox.getChildren();
            for (int i = 0; i < 8; i++) {
                Label lbl = (Label) (nodes.get(i));
                lbl.setText(Character.toString(ChessBoard.FLIPPED_COLUMNS[i]));
            }
        } else {
            // Update rows
            nodes = numberVBox.getChildren();
            for (int i = 0; i < 8; i++) {
                Label lbl = (Label) (nodes.get(i));
                lbl.setText(String.valueOf(ChessBoard.FLIPPED_ROWS[i]));
            }

            // Update columns
            nodes = letterHBox.getChildren();
            for (int i = 0; i < 8; i++) {
                Label lbl = (Label) (nodes.get(i));
                lbl.setText(Character.toString(ChessBoard.VALID_COLUMNS[i]));
            }
        }
    }

    /**
     * Adds the selection marker onto the board.
     */
    private void addSelection() {
        int[] location = board.parseLocation(startLocation);
        int row = location[0];
        int column = location[1];

        ObservableList<Node> nodes = stackPaneGrid[row][column].getChildren();
        Image selectionImg = new Image(SELECTION_URL);
        ImageView currentSpot = (ImageView) (nodes.get(0));
        currentSpot.setImage(selectionImg);
    }

    /**
     * Clears the selection marker from the board.
     */
    private void clearSelection() {
        int[] location = board.parseLocation(startLocation);
        int row = location[0];
        int column = location[1];

        ObservableList<Node> nodes = stackPaneGrid[row][column].getChildren();
        ImageView currentSpot = (ImageView) (nodes.get(0));
        currentSpot.setImage(null);
    }

    /**
     * Play again state variable for the play again window.
     * 0 - Not yet selected
     * 1 - In process of selecting
     * 2 - Play again
     * 3 - Do not play again
     *
     * @see #createPlayAgainWindow()
     */
    private int playAgainState = 0;

    /**
     * Creates a window which prompts the user if they want to play again.
     * Stores intermediate values in {@link #playAgainState}.
     */
    private void createPlayAgainWindow() {
        Stage playAgainStage = new Stage();
        Font font = new Font("Arial", 20);
        int currentGamestate = board.getGamestate().getGameState();

        // Creates the win message.
        Label winMessage = new Label();
        if (currentGamestate == 2)
            winMessage.setText(board.isBlackTurn() ? "White side wins!" : "Black side wins!");
        else if (currentGamestate == 3)
            winMessage.setText("Stalemate!");
        winMessage.setFont(font);
        winMessage.setAlignment(Pos.CENTER);
        winMessage.setTextAlignment(TextAlignment.CENTER);

        // Creates the play again message.
        Label playAgainMessage = new Label("Play Again?");
        playAgainMessage.setFont(font);
        playAgainMessage.setAlignment(Pos.CENTER);
        playAgainMessage.setTextAlignment(TextAlignment.CENTER);

        // Creates the buttons.
        Button yesButton = new Button("Yes");
        yesButton.setOnAction(event -> {
            playAgainState = 2;
            playAgainStage.close();
        });
        yesButton.setMinSize(100, 10);

        Button noButton = new Button("No");
        noButton.setOnAction(event -> {
            playAgainState = 3;
            playAgainStage.close();
        });
        noButton.setMinSize(100, 10);

        // Adds the buttons to a group.
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(yesButton, noButton);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);

        // Adds all components to a main group.
        VBox mainWindow = new VBox();
        mainWindow.getChildren().addAll(winMessage, playAgainMessage, buttonBox);
        mainWindow.setAlignment(Pos.CENTER);

        playAgainStage.setOnCloseRequest(event -> playAgainState = 3);
        playAgainStage.getIcons().add(new Image("/assets/Chess_qlt60.png"));
        // playAgainStage.setTitle("Play Again?");
        playAgainStage.setScene(new Scene(mainWindow, 200, 100));
        playAgainStage.setResizable(false);
        playAgainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
