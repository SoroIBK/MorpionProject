package soro.aka.morpion;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Controller extends Application {
    private VBox vBox;
    protected static GridPane gridPane;
    protected static Button buttonRestart;
    protected static HBox hBox;
    protected static Label labelFirstPlayer;
    protected static Label labelSecondPlayer;
    protected static Label labelNonePlayer;
    protected static Label result;


    private static  TicTacToeModel modele = TicTacToeModel.getInstance();
    public static int lbf = 0;
    public static int lbs = 0;
    public static int lbn = modele.BOARD_WIDTH*modele.BOARD_HEIGHT;
    public static Label lbff = new Label("0");
    public static Label lbss = new Label("0");
    public static Label lbnn = new Label(""+lbn+"");



    @Override
    public void start(Stage stage) throws Exception {
        vBox= new VBox();
        vBox.setPrefWidth(600.0);
        vBox.setPrefHeight(354.0);

        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20, 10, 10, 40));
        for(int i =0; i<modele.BOARD_WIDTH; i++){
            for(int j =0; j<modele.BOARD_HEIGHT; j++){
                Label label = new Label("");
                label.setAlignment(Pos.CENTER);
                label.setMaxWidth(90);
                label.setMaxHeight(50);
                label.setFont(Font.font("Verdana", 25.0));
                gridPane.add(label, i,j);
            }
        }
        for (int i = 0; i < modele.BOARD_WIDTH; i++) {
            RowConstraints con = new RowConstraints();
            con.prefHeightProperty().bind(vBox.prefHeightProperty().divide(6));
            ColumnConstraints ron = new ColumnConstraints();
            ron.prefWidthProperty().bind(vBox.prefWidthProperty().divide(6));
            gridPane.getRowConstraints().add(con);
            gridPane.getColumnConstraints().add(ron);
        }

        buttonRestart = new Button();
        buttonRestart.setText("Restart");
        buttonRestart.setPrefWidth(100.0);
        buttonRestart.setPadding(new Insets(3));
        buttonRestart.setFont(Font.font("Verdana", 17.0));
        buttonRestart.setOnAction(e -> restart());

        result = new Label("");
        result.setStyle("-fx-background-color: #ccffff;");
        result.setFont(Font.font("Verdana", 17.0));

        hBox = new HBox();
        hBox.setPadding(new Insets(10));
        hBox.prefWidthProperty().bind(vBox.prefWidthProperty().subtract(20));
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(30.0);

        labelFirstPlayer = new Label();
        labelFirstPlayer.textProperty().bind(lbff.textProperty().concat(" cases pour X"));
        labelFirstPlayer.setStyle("-fx-background-color: #ccffff;");
        labelFirstPlayer.setFont(Font.font("Verdana", 17.0));

        labelSecondPlayer = new Label();
        labelSecondPlayer.textProperty().bind(lbss.textProperty().concat(" cases pour O"));
        labelSecondPlayer.setStyle("-fx-background-color: red;");
        labelSecondPlayer.setTextFill(Paint.valueOf("white"));
        labelSecondPlayer.setFont(Font.font("Verdana", 17.0));

        labelNonePlayer = new Label();
        labelNonePlayer.textProperty().bind(lbnn.textProperty().concat(" cases libres"));
        labelNonePlayer.setFont(Font.font("Verdana", 17.0));

        hBox.getChildren().addAll(labelFirstPlayer, labelSecondPlayer, labelNonePlayer);

        vBox.getChildren().addAll(gridPane, buttonRestart, result, hBox);


        for (Node child : gridPane.getChildren()) {
            child.setOnMouseClicked(e -> action(e));
            child.setOnMouseEntered(e -> hover(e));
            child.setOnMouseExited(e -> mouseExited(e));
        }

        Scene scene = new Scene(vBox, 600, 500);
        stage.setTitle("Morpion");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private void hover(MouseEvent mouseEvent) {
        if(((Node) mouseEvent.getSource()).getTypeSelector().equals("Label")){
            Node chd = ((Node) mouseEvent.getSource());
            int row = gridPane.getRowIndex(chd).intValue();
            int column = gridPane.getColumnIndex(chd).intValue();
            Boolean b = modele.legalMove(row, column).getValue();
            //Boolean gameOver = modele.gameOver().getValue();
            if(b == true){
                ((Label)mouseEvent.getSource()).setStyle("-fx-background-color: #99cc00;");
            }
            else{
                ((Label)mouseEvent.getSource()).setStyle("-fx-background-color: red;");
            }
        }
    }
    private void mouseExited(MouseEvent mouseEvent) {
        if(((Node) mouseEvent.getSource()).getTypeSelector().equals("Label")){
            Node chd = ((Node) mouseEvent.getSource());
            int row = gridPane.getRowIndex(chd).intValue();
            int column = gridPane.getColumnIndex(chd).intValue();
            Boolean b = modele.legalMove(row, column).getValue();
            //Boolean gameOver = modele.gameOver().getValue();
            if(b == true){
                ((Label)mouseEvent.getSource()).setStyle("-fx-background-color: #f4f4f4;");
            }
            else if (lbn == 0){
                ((Label)mouseEvent.getSource()).setStyle("-fx-background-color: red;");
            }
           else{
                ((Label)mouseEvent.getSource()).setStyle("-fx-background-color: #f4f4f4;");
            }
        }
    }

    private void action(MouseEvent mouseEvent) {
        if(((Node) mouseEvent.getSource()).getTypeSelector().equals("Label")){
            Node chd = ((Node) mouseEvent.getSource());
            int row = gridPane.getRowIndex(chd).intValue();
            int column = gridPane.getColumnIndex(chd).intValue();
            Boolean b = modele.legalMove(row, column).getValue();
            //Boolean gameOver = modele.gameOver().getValue();
            if(b == true){
                if(modele.turn.getValue().equals(Owner.FIRST)){
                    ((Label)mouseEvent.getSource()).setText("X");
                    modele.play(row, column);
                    lbf++;
                    lbn--;
                    lbff.setText(String.valueOf(lbf));
                    lbnn.setText(String.valueOf(lbn));
                }
                else if(modele.turn.getValue().equals(Owner.SECOND)){
                    ((Label)mouseEvent.getSource()).setText("O");
                    modele.play(row, column);
                    lbs++;
                    lbn--;
                    lbss.setText(String.valueOf(lbs));
                    lbnn.setText(String.valueOf(lbn));
                }

                if (lbn == 0){
                    for (Node child : gridPane.getChildren()) {
                        child.setStyle("-fx-background-color: red;");
                    }
                    labelNonePlayer.setVisible(false);
                }else{
                    labelNonePlayer.setVisible(true);
                }

            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void restart() {
        for (Node node : gridPane.getChildren()) {
            if(node.getTypeSelector().equals("Label")){
                ((Label) node).setText("");
            }
            node.setStyle("-fx-background-color: #f4f4f4;");
        }
        modele.restart();
        lbf = 0;
        lbff.setText("0");
        lbs = 0;
        lbss.setText("0");
        lbn = modele.BOARD_WIDTH*modele.BOARD_HEIGHT;
        lbnn.setText(""+lbn+"");
        labelNonePlayer.setVisible(true);

    }


}
