/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Paneel;

/**
 *
 * @author lowie
 */
public class PaneelView extends Region {
    private Paneel paneel;
    private Rectangle r;

    public PaneelView(Paneel paneel) {
        this.paneel = paneel;
        createBorder();
        getChildren().add(r);
    }
    
    public void createBorder(){
        r = new Rectangle(paneel.getBreedte(),paneel.getHoogte(),Color.BLACK);
        
    }
    
    

}