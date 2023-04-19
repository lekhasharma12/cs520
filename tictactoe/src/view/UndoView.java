package view;

import controller.RowGameController;
import model.RowGameModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The UndoView class visualizes the undo button of the game
 *
 * NOTE) For the Composite design pattern, this class is-a Component (i.e. View).
 */
public class UndoView implements View {

    private JButton undo;

    public UndoView(JPanel options, RowGameController controller) {
        this.undo = new JButton("Undo");
        this.undo.setEnabled(false);
        options.add(undo);
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.undoMove();
            }
        });
    }

    public boolean getUndoButtonStatus() {
        return undo.isEnabled();
    }

    @Override
    public void update(RowGameModel model) {
        if(model.movesLeft==9 || model.getFinalResult()!=null){
            this.undo.setEnabled(false);
        } else if(!model.checkIfMoveHistoryIsEmpty()){
            this.undo.setEnabled(true);
        }
    }

}
