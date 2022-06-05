package soro.aka.morpion;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.NumberExpression;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.*;



public  class TicTacToeModel {

    /**
     * Taille du plateau de jeu (pour être extensible).
     */
    public final static int BOARD_WIDTH = 3;
    public final static int BOARD_HEIGHT = 3;
    /**
     8 * Nombre de pièces alignés pour gagner (idem).
     9 */
    public final static int WINNING_COUNT = 3;

    /**
     12 * Joueur courant.
     13 */
    public ObjectProperty<Owner> turn =
            new SimpleObjectProperty<>(Owner.FIRST);
    /**
     17 * Vainqueur du jeu, NONE si pas de vainqueur.
     18 */
    public final ObjectProperty<Owner> winner =
            new SimpleObjectProperty<>(Owner.NONE);
    /**
     22 * Plateau de jeu.
     23 */
    public static ObjectProperty<Owner>[][] board;
    /**
     26 * Positions gagnantes.
     27 */
    public BooleanProperty[][] winningBoard;

    /**
     Constructeur privé
     */
    private TicTacToeModel() {
        board = new ObjectProperty[BOARD_WIDTH][BOARD_HEIGHT];
        for(int i = 0; i < BOARD_WIDTH; i++){
            for (int j = 0; j < BOARD_HEIGHT; j++){
               new TicTacToeSquare(i, j);
            }
        }
    }

    /**
     * @return la seule instance possible du jeu.
     */
    public static TicTacToeModel getInstance() {
        return TicTacToeModelHolder.INSTANCE;
    }
    /**
     Classe interne selon le pattern singleton.
     */
    private static class TicTacToeModelHolder {
        public static TicTacToeModel INSTANCE =
                new TicTacToeModel();
    }
    public void restart() {
        for(int i = 0; i < BOARD_WIDTH; i++){
            for (int j = 0; j < BOARD_HEIGHT; j++){
                new TicTacToeSquare(i, j);
            }
        }
        turn.setValue(Owner.FIRST);
    }

    public final ObjectProperty<Owner> turnProperty() {
        //turn.bind(TicTacToeSquare.ownerProperty());
        return null;
    }

    public final ObjectProperty<Owner> getSquare(int row, int column) {
        return board[row][column];
    }

    public final BooleanProperty getWinningSquare(int row, int column) {
        if(row%2!=0){
            for (int i = row-(WINNING_COUNT/2); i < row; i++){
                for (int j = column; j < column+(WINNING_COUNT/2); j++){
                    if (board[i][j].equals(turn)) {
                        return new SimpleBooleanProperty(true);
                    }
                }
            }
        }
        return new SimpleBooleanProperty(false);
    }

    /***
     * Cette fonction ne doit donner le bon résultat que si le jeu
     * est terminé. L’affichage peut être caché avant la fin du jeu.
     * @return résultat du jeu sous forme de texte
     */
    public final StringExpression getEndOfGameMessage() {

        return null;
    }

    public void setWinner(Owner winner) {
        this.winner.set(winner);
    }

    public boolean validSquare(int row, int column) {
        if(legalMove(row, column).getValue().equals(true)){
            return true;
        }
        else {
            return false;
        }
    }

    public void nextPlayer() {
        if (turn.getValue().equals(Owner.FIRST)){
            turn.set(Owner.SECOND);
        }
        else if (turn.getValue().equals(Owner.SECOND)){
            turn.set(Owner.FIRST);
        }
    }

    /**
     75 * Jouer dans la case (row, column) quand c’est possible.
     76 */
    public void play(int row, int column) {
        if(legalMove(row, column).get() == true){
            board[row][column] = turn;
            nextPlayer();
        }
    }

    /**
     80 * @return true s’il est possible de jouer dans la case
     81 * c’est-à-dire la case est libre et le jeu n’est pas terminé
     82 */
    public BooleanBinding legalMove(int row, int column) {
        if (board[row][column].get().equals(Owner.NONE)){
            return new BooleanBinding() {
                @Override
                protected boolean computeValue() {
                    return true;
                }
            };
        }
        else {
            return new BooleanBinding() {
                @Override
                protected boolean computeValue() {
                    return false;
                }
            };
        }
    }

    public NumberExpression getScore(Owner owner) {

        return null;
    }

    /**
     88 * @return true si le jeu est terminé
     89 * (soit un joueur a gagné, soit il n’y a plus de cases à jouer)
     90 */
    public BooleanBinding gameOver() {
        /*
        if (!winner.get().equals(Owner.NONE)){
            return new BooleanBinding() {
                @Override
                protected boolean computeValue() {
                    return true;
                }
            };
        }


        for (int i = 0; i <= BOARD_WIDTH; i++){
            for (int j = 0; j <= BOARD_WIDTH; j++){
                if (!board[j][j].equals(Owner.NONE)) {
                    return new BooleanBinding() {
                        @Override
                        protected boolean computeValue() {
                            return true;
                        }
                    };
                }
            }
        }

         */

        return null;
    }

}
