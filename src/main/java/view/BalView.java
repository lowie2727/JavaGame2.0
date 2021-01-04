/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Bal;

/**
 *
 * @author lowie
 */
public class BalView extends Region {

    private Bal bal;
    private Circle c;

    public BalView(Bal bal) {
        this.bal = bal;
        createBal();
    }

    public void createBal() {
        c = new Circle(bal.getStraal(), Color.BLUE);
        getChildren().add(c);
    }
}
