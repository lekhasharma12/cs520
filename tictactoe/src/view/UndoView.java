package view;

import controller.RowGameController;
import model.RowGameModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UndoView implements View {

    public JButton undo;

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

    @Override
    public void update(RowGameModel model) {
        if(model.movesLeft==9 || model.getFinalResult()!=null){
            this.undo.setEnabled(false);
        } else if(!model.getMoveHistory().isEmpty()){
            this.undo.setEnabled(true);
        }
    }

}
