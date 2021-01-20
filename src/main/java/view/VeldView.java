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
import javafx.scene.paint.Color;
import model.Paneel;
import model.Peddel;
import model.PowerUp;

/**
 *
 * @author lowie
 */
public class VeldView {

    private final Peddel peddelModel;
    private PowerUp powerUpModel;
    private final Paneel paneelModel;

    private final StenenView stenenView;
    private final PeddelView peddelView;
    private PowerUpView powerUpView;
    private final BallenView ballenView;

    private final Pane paneel;

    private int n;
    private final int intervalPowerUp;
    private final int intervalPowerUpDuration;

    private final TimerPeddel timerPeddel;
    public boolean statusPink;
    public boolean statusBlack;

    private final double peddelMultiplier;

    public VeldView(StenenView stenenView, Peddel peddelModel, BallenView ballenview, PeddelView peddelView, PowerUpView powerUpView,
            PowerUp powerUpModel, TimerPeddel timerPeddel, Paneel paneelModel, Pane paneel) {
        this.peddelModel = peddelModel;
        this.powerUpModel = powerUpModel;
        this.paneelModel = paneelModel;

        this.ballenView = ballenview;
        this.stenenView = stenenView;
        this.peddelView = peddelView;
        this.powerUpView = powerUpView;

        this.paneel = paneel;
        this.timerPeddel = timerPeddel;

        intervalPowerUp = 1;
        intervalPowerUpDuration = 5;

        peddelMultiplier = 1.5;
    }

    public void update() {
        botsingBal();
        peddelView.update();
        ballenView.update();
        stenenView.update();
        powerUpView.update();
        toonPowerUp();
        tijdPeddel();
    }

    private void botsingBal() {
        ObservableList<Node> stenen = stenenView.getChildrenUnmodifiable();
        ObservableList<Node> ballen = ballenView.getChildrenUnmodifiable();
        double straal;

        for (Node b : ballen) {
            Bounds boundsBal = b.localToParent(b.getBoundsInLocal());
            straal = boundsBal.getWidth() / 2;
            Point2D middelPunt = b.localToParent(Point2D.ZERO);
            if (middelPunt.getY() + straal >= peddelModel.getY() - 3
                    && middelPunt.getY() + straal <= peddelModel.getY() + 3
                    && middelPunt.getX() > peddelModel.getX()
                    && middelPunt.getX() < peddelModel.getX() + peddelModel.getBreedte()) {
                b.setId("10");
            }
            if (Math.sqrt(Math.pow(powerUpView.getRandX() - middelPunt.getX(), 2) + Math.pow(powerUpView.getRandY() - middelPunt.getY(), 2)) < straal + powerUpModel.getStraal() && !powerUpView.getChildrenUnmodifiable().isEmpty()) {
                if (powerUpView.getKleurC().equals(Color.PINK)) {
                    peddelModel.setBreedte(peddelMultiplier * peddelModel.getBreedte());
                    peddelView.createPeddel();
                    timerPeddel.setTijdPeddel();
                    statusPink = true;
                } else if (powerUpView.getKleurC().equals(Color.PURPLE)) {
                    ballenView.statusPurple = true;
                    timerPeddel.setTijdPeddel();
                } else if (powerUpView.getKleurC().equals(Color.BLACK)) {
                    peddelModel.setBreedte((peddelModel.getBreedte() / peddelMultiplier));
                    peddelView.createPeddel();
                    timerPeddel.setTijdPeddel();
                    statusBlack = true;
                }
                powerUpView.getChildrenUnmodifiable().get(0).setId("1");
                timerPeddel.setTijdPowerUp();
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
        powerUpView.reset();
        toonPowerUp();
    }

    private void toonPowerUp() {
        if (powerUpView.getChildrenUnmodifiable().isEmpty() && timerPeddel.getTijdPowerUp() > intervalPowerUp) {
            this.powerUpModel = new PowerUp(powerUpModel.getStraal());
            timerPeddel.setTijdPowerUp();
            powerUpView = new PowerUpView(powerUpModel, paneelModel);
            paneel.getChildren().add(powerUpView);
        }
    }

    private void tijdPeddel() {
        if (timerPeddel.getTijdPeddel() > intervalPowerUpDuration && statusPink) {
            peddelModel.setBreedte(peddelModel.getBreedte() / peddelMultiplier);
            peddelView.createPeddel();
            statusPink = false;
            timerPeddel.setTijdPowerUp();
        } else if (timerPeddel.getTijdPeddel() > intervalPowerUpDuration && ballenView.statusPurple) {
            ballenView.statusPurple = false;
            timerPeddel.setTijdPowerUp();
        } else if (timerPeddel.getTijdPeddel() > intervalPowerUpDuration && statusBlack) {
            peddelModel.setBreedte(peddelModel.getBreedte() * peddelMultiplier);
            peddelView.createPeddel();
            statusBlack = false;
            timerPeddel.setTijdPowerUp();
        }
    }

    /**
     * @return the peddelView
     */
    public PeddelView getPeddelView() {
        return peddelView;
    }

    public String timerPeddel() {
        if (statusPink || ballenView.statusPurple || statusBlack) {
            return (intervalPowerUpDuration - timerPeddel.getTijdPeddel() + "");
        }
        return intervalPowerUpDuration + "";
    }
}
