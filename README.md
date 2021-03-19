# CPSC-501-Refactoring

As discussed, refactoring was only done on files contained in folder pieces. All tests are located in the pieces folder and all refactorings were done on the classes inside this folder. No other classes were modified for this project. If project is downloaded runGUI.bat will run the game.

The initial code was created by me and my CPSC 233 group. I was the major contributer to the set of classes that i am refactoring.

 A. The first refactoring involved classes Pawn.java, Rook.java, Knight.java, Pawn.java, Queen.java, Bishop.java, King.java .public boolean IsValidMove was a method in all these classes and was altered . The code that was modified in this method was the code that involved the unique movements of each piece.
B. There were multiple bad smells that were detected and addressed within isValidMove. These were duplicated Code, long Method.
C. The Refactoring that was applied was the extract method. All the code unique to that piece's movements were moved into a new method. The method was unique to each class. Then this code was invoked from whichever locations it was invoked from.  For example, for Bishop.java a new method was created called canBishopMoveLegally. canBishopMoveLegally was then called from multiple spots in the code. This was done for every class. Each class called this method multiple times.
D. The resulting code was public boolean canBishopMoveLegally in Bishop.java, public boolean canQueenMoveLegally in Queen.java, … 
E. The code was tested by running tests that tried to move each piece with a valid move, an invalid move, trying to take another piece, and try to make an illegal move that would put the players king in check.
F. The code is structured better because it is easier to read and understand what the code is doing. It would also be easier to debug and make changes to each pieces movements. It also removed duplicate code.
G. Yes. The method could be pulled up in the future if future refactors took place on this function.
I. There were multiple branches and merges done for this refactor. SHA numbers : a7195379923339b24ca8fc8d8b58fd1412420910,db4da1cb8e70b2e25e9ba6f05c32c0b0056b0054,0dd13ca3337a5e589732780fecf5a659ac4958fa,f90af3a0df10c9b1f45c23d7b1775144a188afff,b837f9df133eb5d0f6d2b07c9fc0e21d91d8636d,40920a50214f28e9250051484c1d0babd0d54410. Then Finally 66cf030e11a9d1a59bfb648e81627c3878596082 was used to merge all of these into Master. This refactoring was done utilizing branch/merge. Note: All refactorings were done this way.
            
A. The second refactoring involved classes Pawn.java, Rook.java, Knight.java, Pawn.java, Queen.java, Bishop.java, King.java .The code in the method isValidMove was modified. All the data Clumps at the beginning of this method were joined together.
B. The bad smell that was detected in this method was long parameter lists and data clumps as well as shotgun surgery. 
C. The refactoring that was applied was extract class. A new class called Movement.java was created and all these data fields were added to the class using move fields. An Object of type movement was then created for each method that required these fields.
D. The result of this refactor was the code found in Movement.java
E. The code was tested by running tests that tried to move each piece with a valid move, an invalid move, trying to take another piece, and try to make an illegal move that would put the players king in check. This ensured nothing was missed.
F. The code is better structured because the parameter lists had shrunk, as well as the parameter lists being more organized and certain parameters did not need to be recalculated in multiple spots.
G. Yes, some methods could be moved into this new class. These methods are currently scattered around multiple classes because they did not have a class that they necessarily fit in.
H. The SHA number for this refactoring was 3637c5ae1c4e1ac50bd191ab228cf07268dac6fd

A. The third refactoring involved classes Pawn.java, Rook.java, Knight.java, Pawn.java, Queen.java, Bishop.java, King.java. The code that was modified was in the method  isValidMove. Two local variables were added final boolean movingToEmptySpot and final boolean killingEnemyPiece. 
B. The code was altered because each class calculated these variables in different ways and it was very hard to follow.  Initially each class had a unique way of calculating these variables but having these variables allowed the unification of the code. The bad smell was long parameter lists and duplicate code.
C. The refactoring that was applied was to introduce explaining variables. The steps that were followed was that a temporary variable was created with a name that explains its purpose , making the expressions easier to understand and read.
D. The resulting code was the addition of two booleans, movingToEmptySpot and killingEnemyPiece. These variables then replaced the long expressions present in each conditional.
E. The code was tested by running tests that tried to move each piece with a valid move, an invalid move, trying to take another piece, and try to make an illegal move that would put the players king in check. This ensured nothing was missed.
F. The code is better structured because now each class calculates isValidMove the same except the King. This allows each piece to be easily modified because each change only needs to be made in one spot and is consistent in every class. Before these modifications each change required changes in multiple locations.
G. Yes with this change it is apparent how the different methods in each class could be modified and then renamed so they all have identical pieces and then can be pulled up to the parent class. This would make it easier to make new pieces in the future and modify pieces.
H. The SHA number used was 8e96b7aa68c93decc8e075452ce286f105c859fe

A. For the fourth refacoring all piece files were altered. classes Pawn.java, Rook.java, Knight.java, Pawn.java, Queen.java, Bishop.java, King.java. The variable c and all methods with piece specific names.
B. The bad smell was divergent code, and comments. It was divergent code because with all methods having different names it made it harder to make changes without having to go looking in multiple places and comments because multiple people worked on the code and so each class referred to the same variable with multiple names. This made it hard to make changes to the code because you werent sure what name was used.
C. Two refactorings were applied. The two were rename variables and rename methods. The steps were very straight forward; each variable in every class that had the same function was renamed to be identical. This was done with methods as well.
D. The resulting code was in all unique game piece files. The variables chessboard c was used in some fields while chessboard board was used in others. They were all modified to chessboard board. The methods for legal moves all had unique names; they were all renamed to isLegalMove.
E. The code was tested by running tests that tried to move each piece with a valid move, an invalid move, trying to take another piece, and try to make an illegal move that would put the players king in check. This ensured nothing was missed and it still compiled.
F. The code is better structurally because now every class's movement methods are identical except for the parts that make that piece unique and those unique parts are entirely separate from the common parts. Reading and finding bugs is now easier and it is easier to identify what each function does and how each class works.
G. Yes now isLegalMove is identical in all functions and can be pulled up except for in the king class.
H. This refactoring can be cross referenced with SHA number :156c2b10e191e4c314f595314ec6a8b6f7c4618e

A. For the final refactoring the code in Pieces.java as well as classes Pawn.java, Rook.java, Knight.java, Pawn.java, Queen.java, Bishop.java, King.java.
B. The bad smell was duplicated code and alternative classes with different interfaces.
C. Pull up method and move method refactoring was applied to resolve these bad smells. The steps that were followed were that first all code that made King.javas code unique from the other pieces was moved. This was code for castling and checking if the king was near another king. These checks made sense to be moved to isLegalMove. Then since all classes now had identical isValidMove methods this method was moved to the parent class and then isLegalMove was overridden by each child which allowed each class to have their own flavor of isValidMove. This also caused isValidMove and isLegalMove to be made as public methods.
D. The code in Piece.java was the resulting code.
E. The code was tested by running tests that tried to move each piece with a valid move, an invalid move, trying to take another piece, and try to make an illegal move that would put the players king in check. This ensured nothing was missed and it still compiled.
F. The code is now better structured because you only need to make changes in one spot. If a unique piece needs to be changed or given a new move set it can be easily added in an else if in the method isLegalMove. If anything needs to be changed about how pieces in general work such as killing other pieces or updated scores this can now be done in one spot in the parent class instead of needing to be modified in each child.
G. All unique pieces now have only what is unique to them so i don't see any refactorings that could be done to them. Permissions though could be refactored since changes from private to public were made to incorporate this refactoring.
H.  The final commit was df2cebea09cf4e196213f8384810c984bb55ca89. This was removing the isValidMove from king.java after the move method refactoring was used. 86202f6acb92a4ddc9e5d93d38761347d4b3a6fd was the commit used to remove the isValidMove from the other classes and add it to pieces.java

Please note not all commits were listed only the final commits. Multiple commits were done per refactoring.


See below for instruction on how to run the game itself.





# CPSC-233-Chess-Game

Ever wanted to play a game of Chess on the go? Not enough space to have a proper Chess setup? This project is the solution for you! 
A Simple Chess game made using Java.

## How to Download and Run Files

1. To download the game, click the dropdown menu on the top left and select "**master** as the branch.
2. Then, click the green button at the top right of the screen labeled "**Clone or Download**".
3. Afterwards, select the option labeled "**Download ZIP**"
4. Open the newly download ZIP file and extract the folder to anywhere on the PC
5. Run the correct file depending on your OS (Operating System) using the steps below:

## **For Windows:**
  #### If Playing the Text-based Version:
     Run "runText.bat"
  #### If Playing the GUI-based Version:
     Run "runGUI.bat"
    
## **For Linux:**

   #### If Playing the Text-based Version:
    1. Right click the file named "runText.sh" and Select "Properties"
    2. Select the "Permissions" tab
    3. Check the box labeled "Allow executing file as a program" and close the window
    4. Double click "runText.sh" and select either "Run in Terminal" or "Run"
    
   #### If Playing the GUI-based Version:
    1. Right click the file named "runGUI.sh" and Select "Properties"
    2. Select the "Permissions" tab
    3. Check the box labeled "Allow executing file as a program" and close the window
    4. Double click "runGUI.sh" and select either "Run in Terminal" or "Run"

### How to Play (GUI-Based Version)

1. To play, the user is promted with a standard Chessboard. Player One is white side and begins first. The current player's turn is denoted by the text at the top of the window.
2. To move a piece, select a piece of the appropriate color (white pieces if white side, black pieces if black)
3. After selecting a piece, click another space that the corrosponding piece can move to (either empty or with a black piece).    If move is illegal, the piece will not move and you will have to reselect a piece again.
4. After the move completes, it is now Player Two's turn (black side) and repeat steps 2-3 for their turn.

### How to Play (Text-Based Version)

1. To play, the user is prompted with a command console with a picture of the board state. Player One plays first.
2. To select a piece to move, type in the coordinate of the piece that corresponds to your color. (ie. Player One only selects white pieces, Player Two only selects black pieces)
3. After selecting a piece, type in the coordinate of the space you want to move your piece to.
4. If the piece selected matches the player and the move is valid, the play proceeds and the turn will swap to the opponent.
5. Repeat steps 2-4 for Player Two.
