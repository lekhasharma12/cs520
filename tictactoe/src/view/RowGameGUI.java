package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import model.BlockIndex;
import model.RowGameModel;
import controller.RowGameController;

/**
 * The RowGameGUI class is applying the Composite design pattern.
 * This class is the Composite. The class also is-a Component (i.e. View).
 */
public class RowGameGUI implements View {
    public JFrame gui = new JFrame("Tic Tac Toe");
    public RowGameModel gameModel = new RowGameModel();
    private GameBoardView gameBoardView;
    private UndoView undo;
    /** For the Composite design pattern, the RowGameGUI is the Composite */
    private List<View> viewList = new ArrayList<>();
    public JButton reset = new JButton("Reset");


    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameGUI(RowGameController controller) {
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(new Dimension(500, 350));
        gui.setResizable(true);

        JPanel gamePanel = new JPanel(new FlowLayout());
        JPanel game = new JPanel(new GridLayout(3,3));
        gamePanel.add(game, BorderLayout.CENTER);

        JPanel options = new JPanel(new FlowLayout());
        options.add(reset);
        JPanel messages = new JPanel(new FlowLayout());
        messages.setBackground(Color.white);

        gui.add(gamePanel, BorderLayout.NORTH);
        gui.add(options, BorderLayout.CENTER);
        gui.add(messages, BorderLayout.SOUTH);

	GameStatusView gameStatusView = new GameStatusView(messages);
	addView(gameStatusView);

    undo = new UndoView(options, controller);
    addView(undo);

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.resetGame();
            }
        });

	this.gameBoardView = new GameBoardView(game, controller);
	addView(this.gameBoardView);


    }

    public BlockIndex getBlockIndex(JButton block) {
	return this.gameBoardView.getBlockIndex(block);
    }

    public void addView(View view) {
	// For the Composite API
	
	// Perform input validation
	if (view == null) {
	    throw new IllegalArgumentException("The view must be non-null.");
	}

	this.viewList.add(view);
    }

    public void update(RowGameModel model) {
	// For the Composite API
	
	for (View currentView : this.viewList) {
	    currentView.update(model);
	} // end for currentView
    }

    // Fetches the status of undo button - for testing
    public boolean getUndoStatus() {
        return undo.getUndoButtonStatus();
    }

    // getter for gameBoardView - added for view testing
    public GameBoardView getBoardView() {
        return gameBoardView;
    }

    // Fetches the status of the given block - for testing
    public boolean getBlockStatus(BlockIndex blockIndex) {
        return gameBoardView.isBlockEnabled(blockIndex);
    }

}
