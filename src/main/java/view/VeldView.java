/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import be.inf1.mavenproject2.TimerPeddel;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import model.Paneel;
import model.Peddel;
import model.PowerUp;

/**
 *
 * @author lowie
 */
public class VeldView {

    private final Peddel peddelModel;
    private  PowerUp powerUpModel;
    private final Paneel paneelModel;

    private final StenenView stenenView;
    private final PeddelView peddelView;
    private PowerUpView powerUpView;
    private final BallenView ballenView;
    
    private Pane paneel;

    private final double cos;
    private int n;
    private final int interval;
    
    private TimerPeddel timerPeddel;

    public VeldView(StenenView stenenView, Peddel peddelModel, BallenView ballenview, PeddelView peddelView, PowerUpView powerUpView, PowerUp powerUpModel, TimerPeddel timerPeddel, Paneel paneelModel, Pane paneel) {
        this.peddelModel = peddelModel;
        this.powerUpModel = powerUpModel;
        this.paneelModel = paneelModel;

        this.ballenView = ballenview;
        this.stenenView = stenenView;
        this.peddelView = peddelView;
        this.powerUpView = powerUpView;
        
        
        this.paneel = paneel;
        this.timerPeddel = timerPeddel;

        interval = 10;
        cos = Math.cos(Math.toRadians(45));
    }

    public void update() {
        botsingBal();
        ballenView.update();
        stenenView.update();
        powerUpView.update();
        toonPowerUp();
    }

    private void botsingBal() {
        ObservableList<Node> stenen = stenenView.getChildrenUnmodifiable();
        ObservableList<Node> ballen = ballenView.getChildrenUnmodifiable();
        double straal;

        for (Node b : ballen) {
            Bounds boundsBal = b.localToParent(b.getBoundsInLocal());
            straal = boundsBal.getWidth() / 2;
            Point2D middelPunt = b.localToParent(Point2D.ZERO);
            if (middelPunt.getY() + straal >= peddelModel.getY() - 1
                    && middelPunt.getY() + straal <= peddelModel.getY() + 1
                    && middelPunt.getX() > peddelModel.getX()
                    && middelPunt.getX() < peddelModel.getX() + peddelModel.getBreedte()) {
                b.setId("10");
            }
            if (Math.sqrt(Math.pow(powerUpView.getRandX() - middelPunt.getX(), 2) + Math.pow(powerUpView.getRandY() - middelPunt.getY(), 2)) < straal + powerUpModel.getStraal() && !powerUpView.getChildrenUnmodifiable().isEmpty()) {
                powerUpView.getChildrenUnmodifiable().get(0).setId("1");
                double breedte = peddelModel.getBreedte();
                peddelModel.setBreedte(1.5 * breedte);
                peddelView.createPeddel();
            }
        }

        for (Node s : stenen) {
            Bounds steenBounds = getBoundSteen(s);
            for (Node b : ballen) {
                Point2D middelPunt = b.localToParent(Point2D.ZERO);
                Bounds boundsBal = b.localToParent(b.getBoundsInLocal());
                straal = boundsBal.getWidth() / 2;
                if (middelPunt.getY() + straal >= steenBounds.getMinY() - 3 //onderkant bal met bovenkant steen
                        && middelPunt.getY() + straal <= steenBounds.getMinY() + 3
                        && middelPunt.getX() >= steenBounds.getMinX()
                        && middelPunt.getX() <= steenBounds.getMinX() + steenBounds.getWidth()) {
                    b.setId("1");
                    s.setId("geraakt");
                } else if (middelPunt.getY() - straal >= steenBounds.getMaxY() - 3 //bovenkant bal met onderkant steen
                        && middelPunt.getY() - straal <= steenBounds.getMaxY() + 3
                        && middelPunt.getX() >= steenBounds.getMinX()
                        && middelPunt.getX() <= steenBounds.getMinX() + steenBounds.getWidth()) {
                    b.setId("2");
                    s.setId("geraakt");
                } else if (middelPunt.getX() + straal >= steenBounds.getMinX() - 3 //rechterkant bal met linkerkant steen
                        && middelPunt.getX() + straal <= steenBounds.getMinX() + 3
                        && middelPunt.getY() >= steenBounds.getMinY()
                        && middelPunt.getY() <= steenBounds.getMinY() + steenBounds.getHeight()) {
                    b.setId("3");
                    s.setId("geraakt");
                } else if (middelPunt.getX() - straal >= steenBounds.getMaxX() - 3 //linkerkant bal met rechterkant steen
                        && middelPunt.getX() - straal <= steenBounds.getMaxX() + 3
                        && middelPunt.getY() >= steenBounds.getMinY()
                        && middelPunt.getY() <= steenBounds.getMinY() + steenBounds.getHeight()) {
                    b.setId("4");
                    s.setId("geraakt");
                } else if (Math.sqrt(Math.pow(middelPunt.getX() - steenBounds.getMinX(), 2) + Math.pow(middelPunt.getY() - steenBounds.getMinY(), 2)) < straal) { //linksboven bal
                    b.setId("5");
                    s.setId("geraakt");
                } else if (Math.sqrt(Math.pow(middelPunt.getX() - steenBounds.getMaxX(), 2) + Math.pow(middelPunt.getY() - steenBounds.getMaxY(), 2)) < straal) { //rechtsonder bal
                    b.setId("6");
                    s.setId("geraakt");
                } else if (Math.sqrt(Math.pow(middelPunt.getX() - steenBounds.getMinX(), 2) + Math.pow(middelPunt.getY() + steenBounds.getHeight() - steenBounds.getMinY(), 2)) < straal) { //rechtsboven bal
                    b.setId("7");
                    s.setId("geraakt");
                } else if (Math.sqrt(Math.pow(middelPunt.getX() - steenBounds.getMinX(), 2) + Math.pow(middelPunt.getY() - steenBounds.getHeight() - steenBounds.getMinY(), 2)) < straal) { //linksonder bal
                    b.setId("8");
                    s.setId("geraakt");
                }
            }
        }
    }

    private Bounds getBoundSteen(Node s) {
        return s.localToParent(s.getBoundsInLocal());
    }

    public void reset() {
        ballenView.reset();
        peddelModel.reset();
        peddelView.update();
        stenenView.maakStenen();
    }

    private void toonPowerUp() {
        System.out.println(timerPeddel.getT());
        System.out.println(powerUpView.getChildrenUnmodifiable().isEmpty());
        if (powerUpView.getChildrenUnmodifiable().isEmpty() && timerPeddel.getT() > interval) {
            this.powerUpModel = new PowerUp(powerUpModel.getStraal());
            timerPeddel.setT();
            powerUpView = new PowerUpView(powerUpModel, paneelModel);
            paneel.getChildren().add(powerUpView);
        }
    }

    private void tijdPeddel() {

    }
}
