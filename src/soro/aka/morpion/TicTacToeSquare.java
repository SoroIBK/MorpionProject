package soro.aka.morpion;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;

public class TicTacToeSquare extends Label {

    private static TicTacToeModel model = TicTacToeModel.getInstance();

    public static ObjectProperty<Owner> ownerProperty =
            new SimpleObjectProperty<>(Owner.NONE);


    private BooleanProperty winnerProperty =
            new SimpleBooleanProperty(false);

    public ObjectProperty<Owner> ownerProperty() {
        if (model.turn.getValue().equals(Owner.FIRST)){
            this.setText("X");
        }
        else if (model.turn.getValue().equals(Owner.SECOND)){
            this.setText("O");
        }
        else  {
            this.setText("");
        }
        return null;
    }

    public BooleanProperty colorProperty() {
        return null;
    }

    public TicTacToeSquare(int row, int column) {
        model.board[row][column] = this.ownerProperty;
    }


}
