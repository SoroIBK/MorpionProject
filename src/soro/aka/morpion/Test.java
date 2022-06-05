package soro.aka.morpion;

import javafx.beans.property.ObjectProperty;
import javafx.scene.text.Text;

public class Test {
    public static void main(String[] args) {
        ObjectProperty[][] board = new ObjectProperty[3][3];

        for (ObjectProperty[] properties : board) {
            System.out.println(properties);
        }
        System.out.println(board);
    }
}
