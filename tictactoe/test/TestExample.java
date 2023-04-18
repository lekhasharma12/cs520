import model.BlockIndex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.Player;
import model.RowBlockModel;
import controller.RowGameController;

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

}
