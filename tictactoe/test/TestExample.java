import model.BlockIndex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.Player;
import model.RowBlockModel;
import controller.RowGameController;
import model.RowGameModel;


/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestExample {
    private RowGameController game;


    @Before
    public void setUp() {
        game = new RowGameController();
    }

    @After
        public void tearDown() {
        game = null;
    }

    @Test
    public void testNewGame() {
        assertEquals (Player.PLAYER_1, game.gameModel.getPlayer());
        assertEquals (9, game.gameModel.movesLeft);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewBlockViolatesPrecondition() {
	RowBlockModel block = new RowBlockModel(null);
    }

    @Test
    public void testUndoDisabled() {
        // checking pre-conditions i.e. no moves are made by the user
        assertEquals (9, game.gameModel.movesLeft);

        // no method under test execution as user is not making a move

        // checking post-conditions i.e. undo should be disabled
        assertEquals(game.gameView.getUndoStatus(), false);
    }

    @Test
    public void testUndo() {
        // checking pre-conditions i.e. making a legal move, testing if move is made and if undo is enabled
        game.move(new BlockIndex(0,0));
        assertEquals (8, game.gameModel.movesLeft);
        assertEquals(false, game.gameModel.blocksData[0][0].getIsLegalMove());
        assertEquals(game.gameView.getUndoStatus(), true);

        // executing method under test i.e. undo function
        game.undoMove();

        // checking post-conditions i.e. game should be reverted to old conditions
        assertEquals (9, game.gameModel.movesLeft);
        assertEquals(true, game.gameModel.blocksData[0][0].getIsLegalMove());
        assertEquals(game.gameView.getUndoStatus(), false);
    }

    @Test
    public void testIllegalMove(){

        // checking pre-conditions i.e. making a legal move, testing if move is made and if undo is enabled
        assertEquals (9, game.gameModel.movesLeft);
        assertEquals(true, game.gameModel.blocksData[1][1].getIsLegalMove());
        assertEquals(false,game.gameView.getUndoStatus());
        //View- testing
        assertTrue("Button for 1,1 should be enabled", game.gameView.getBlockStatus(new BlockIndex(1, 1)));


        
        // executing - making a legal move at 1,1
        game.move(new BlockIndex(1, 1));
        assertEquals(8, game.gameModel.movesLeft);
        assertEquals(Player.PLAYER_2, game.gameModel.getPlayer());
        assertEquals(false, game.gameModel.blocksData[1][1].getIsLegalMove());
        assertEquals("X", game.gameModel.blocksData[1][1].getContents());

        //executing - make a illegal move 1,1
        game.move(new BlockIndex(1, 1));

        //checking post-conditions - no update to the game model.
        assertEquals(8, game.gameModel.movesLeft);
        assertEquals(Player.PLAYER_2, game.gameModel.getPlayer());
        assertEquals(false, game.gameModel.blocksData[1][1].getIsLegalMove());
        assertEquals("X", game.gameModel.blocksData[1][1].getContents());

        // testing View
        assertFalse("Button for 1,1 should be disabled", game.gameView.getBlockStatus(new BlockIndex(1, 1)));
    }

    @Test
    public void testLegalMove(){

       // checking pre-conditions i.e. making a legal move, testing if move is made and if undo is enabled
       assertEquals (9, game.gameModel.movesLeft);
       assertEquals(true, game.gameModel.blocksData[1][1].getIsLegalMove());
       assertEquals(false,game.gameView.getUndoStatus());

       
       // executing - making a legal move at 1,1
       game.move(new BlockIndex(1, 1));

        //checking post-conditions - update to the game model.
       assertEquals(8, game.gameModel.movesLeft);
       assertEquals(Player.PLAYER_2, game.gameModel.getPlayer());
       assertEquals(false, game.gameModel.blocksData[1][1].getIsLegalMove());
       assertEquals("X", game.gameModel.blocksData[1][1].getContents());

    }

    @Test
    public void testPlayerWins(){

        // checking pre-conditions i.e. making a legal move, testing if move is made and if undo is enabled
        assertEquals (9, game.gameModel.movesLeft);
        assertEquals(Player.PLAYER_1, game.gameModel.getPlayer());
        assertEquals(false,game.gameView.getUndoStatus());
        assertNull("final result should be null at the beginning", game.gameModel.getFinalResult() );

        
        // executing - Player 1 move at 0,0
        game.move(new BlockIndex(0, 0));

        // Player 2 move at 0,1 
        game.move(new BlockIndex(0, 1));

        // Player 1 move at 1,1
        game.move(new BlockIndex(1, 1));

        // Player 2 move at 0,2
        game.move(new BlockIndex(0, 2));

        // Player 1 move at 2,2 and wins!
        game.move(new BlockIndex(2, 2));

        //checking post-conditions - a final result is set
        assertEquals(4, game.gameModel.movesLeft);
        assertEquals("Player 1 wins!",game.gameModel.getFinalResult());
    }

    @Test
    public void testMatchTie(){

        // checking pre-conditions i.e. making a legal move, testing if move is made and if undo is enabled
        assertEquals (9, game.gameModel.movesLeft);
        assertEquals(Player.PLAYER_1, game.gameModel.getPlayer());
        assertEquals(false,game.gameView.getUndoStatus());
        assertNull("final result should be null at the beginning", game.gameModel.getFinalResult());

        
        // executing - Player 1 move at 0,0
        game.move(new BlockIndex(0, 0));
        // Player 2 move at 1,1
        game.move(new BlockIndex(1, 1));
        // Player 1 move at 1,0
        game.move(new BlockIndex(1, 0));
        // Player 2 move at 2,0
        game.move(new BlockIndex(2, 0));
        // Player 1 move at 2,2
        game.move(new BlockIndex(2, 2));
        // Player 2 move at 0,1
        game.move(new BlockIndex(0, 1));
        // Player 1 move at 2,1
        game.move(new BlockIndex(2,1));
        //Player 2 move at 1,2
        game.move(new BlockIndex(1, 2));
        //player 1 move at 0, 2
        game.move(new BlockIndex(0, 2));



        //checking post-conditions - a final result is set
        assertEquals(0, game.gameModel.movesLeft);
        assertEquals(RowGameModel.GAME_END_NOWINNER,game.gameModel.getFinalResult());
    }

    private void checkInitCondition(){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                assertEquals("", game.gameModel.blocksData[row][col].getContents());
                assertEquals(true, game.gameModel.blocksData[row][col].getIsLegalMove());
                assertTrue("All block buttons should be enabled", game.gameView.getBlockStatus(new BlockIndex(row, col)));
            } 
        }
    }

    @Test
    public void testResetGame(){
        // checking pre-conditions i.e. making a legal move, testing if move is made and if undo is enabled
        assertEquals (9, game.gameModel.movesLeft);
        assertEquals(Player.PLAYER_1, game.gameModel.getPlayer());
        assertEquals(false,game.gameView.getUndoStatus());
        checkInitCondition();
        assertNull("final result should be null at the beginning", game.gameModel.getFinalResult() );

        
        // executing - few moves
        game.move(new BlockIndex(0, 0));
        game.move(new BlockIndex(1, 1));
        game.move(new BlockIndex(1, 0));
        game.move(new BlockIndex(2, 0));
        game.move(new BlockIndex(2, 2));
        game.move(new BlockIndex(0, 1));

        // reset game
        game.resetGame();

        //checking post-conditions - model should be reset to intial configuration
        assertEquals (9, game.gameModel.movesLeft);
        assertEquals(Player.PLAYER_1, game.gameModel.getPlayer());
        assertEquals(false,game.gameView.getUndoStatus());
        checkInitCondition();
        assertNull(game.gameModel.getFinalResult() );

    }
}
