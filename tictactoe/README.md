# TicTacToe

### How to build and test (from Terminal):

1. Make sure that you have Apache Ant installed. Run each ant command in the tictactoe folder, which contains the `build.xml` build file.

2. Run `ant document` to generate the javadoc (a hypertext description) for all of the java classes. Generated hypertext description will be in the `jdoc` folder. Open the `index.html` file. 

3. Run `ant compile` to compile all of the java classes. Compiled classes will be in the `bin` folder.

4. Run `ant test` to run all unit tests.

### How to run (from Terminal):

1. After building the project (i.e., running `ant`), run the following command in the tictactoe folder:
   `java -cp bin RowGameApp`

### How to clean up (from Terminal):

1. Run `ant clean` to clean the project (i.e., delete all generated files).


### Added the undo functionality: 

1. Added the class UndoView inside the view.

2. Implemented the interface view. The update function in UndoView enables or disables the undo button.

3. The undo button is set to be disabled, when no moves are made or there is a winning condition.

4. Undo button is enabled only when the move history is not empty; i.e atleast one move is made.

5. Defined the undoMove function in the controller which will pop the latest row and column from the stack. It manipulates the gameModel to clear the last played block and makes it legal again. This also reverts the player to the previous one. 

6. Controller then updates the view.



