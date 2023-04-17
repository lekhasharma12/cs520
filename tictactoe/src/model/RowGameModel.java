package model;


import java.util.Stack;

public class RowGameModel
{
    public static final String GAME_END_NOWINNER = "Game ends in a draw";

    public RowBlockModel[][] blocksData = new RowBlockModel[3][3];

    /**
     * The current player taking their turn
     */
    private Player player = Player.PLAYER_1;
    public int movesLeft = 9;

    private String finalResult = null;

    private Stack<BlockIndex> moveHistory = new Stack<>();


    public RowGameModel() {
	super();

	for (int row = 0; row < 3; row++) {
	    for (int col = 0; col < 3; col++) {
		blocksData[row][col] = new RowBlockModel(this);
	    } // end for col
	} // end for row
    }

    public Player getPlayer() {
	return this.player;
    }

    public void setPlayer(Player player) {
	// Perform input validation
	if (player == null) {
	    throw new IllegalArgumentException("The player must be non-null.");
	}

	this.player = player;
    }
    
    public String getFinalResult() {
	return this.finalResult;
    }

    public void setFinalResult(String finalResult) {
	this.finalResult = finalResult;
    }

    public Stack<BlockIndex> getMoveHistory() {
        return moveHistory;
    }

    public void setMoveHistory(Stack<BlockIndex> moveHistory) {
        this.moveHistory = moveHistory;
    }

    public void pushToMoveHistory(BlockIndex move) {
        this.moveHistory.push(move);
    }

    public BlockIndex popFromMoveHistory() {
        return this.moveHistory.pop();
    }
}
