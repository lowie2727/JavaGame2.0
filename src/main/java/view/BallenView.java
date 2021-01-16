/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import model.Bal;
import model.Ballen;
import model.Peddel;

/**
 *
 * @author lowie
 */
public class BallenView extends Region {

    private final Ballen ballen;
    private final Bal balModel;
    private final Peddel peddel;

    public BallenView(Ballen ballen, Bal bal, Peddel peddel) {
        this.balModel = bal;
        this.ballen = ballen;
        this.peddel = peddel;
        maakBallen();
        update();
    }

    public final void maakBallen() {
        getChildren().clear();
        ArrayList<Bal> b = ballen.getBallen();
        for (int i = 0; i < ballen.getaantalBallen(); i++) {
            b.get(i).setX(b.get(i).getX()+i*20);
            BalView bv = new BalView(b.get(i));
            bv.setTranslateX(b.get(i).getX());
            bv.setTranslateY(b.get(i).getY());
            getChildren().add(bv);
        }
    }

    public void update() {
        ArrayList<Bal> b = ballen.getBallen();
        for (int i = 0; i <= b.size() - 1; i++) {
            Bal bal = b.get(i);
            Node balNode = getChildren().get(i);
            balNode.setTranslateX(bal.getX());
            balNode.setTranslateY(bal.getY());
            if (null != balNode.getId()) {
                switch (balNode.getId()) {
                    case "1":
                        if (bal.getVy() > 0) {
                            bal.setVy();
                        }
                        break;
                    case "2":
                        if (bal.getVy() < 0) {
                            bal.setVy();
                        }
                        break;
                    case "3":
                        if (bal.getVx() > 0) {
                            bal.setVx();
                        }
                        break;
                    case "4":
                        if (bal.getVx() < 0) {
                            bal.setVx();
                        }
                        break;
                    case "5":
                        if (bal.getVx() < 0 && bal.getVy() < 0) {
                            bal.setVy();
                            bal.setVx();
                        }
                        break;
                    case "6":
                        if (bal.getVx() > 0 && bal.getVy() > 0) {
                            bal.setVx();
                            bal.setVy();
                        }
                        break;
                    case "7":
                        if (bal.getVx() > 0 && bal.getVy() < 0) {
                            bal.setVx();
                            bal.setVy();
                        }
                        break;
                    case "8":
                        if (bal.getVx() < 0 && bal.getVy() > 0) {
                            bal.setVx();
                            bal.setVy();
                        }
                        break;
                    case "9":
                        bal.setVx(bal.getSnelheidX());
                        bal.setVy(bal.getSnelheidY());
                        break;
                    case "10":
                        if (bal.getVy() > 0) {
                            double midden = peddel.getX() + peddel.getBreedte() / 2;
                            if ((bal.getX() - midden) > 0) {
                                if (bal.getVx() < 0) {
                                    bal.setVx();
                                }
                                double temp1 = 1 - (bal.getX() - midden) / (peddel.getBreedte() / 2);
                                bal.setHy((temp1 * 0.7) + 0.3);
                            } else {
                                if (bal.getVx() > 0) {
                                    bal.setVx();
                                }
                                double temp2 = 1 - (midden - bal.getX()) / (peddel.getBreedte() / 2);
                                bal.setHy((temp2 * 0.7) + 0.3);
                            }
                            bal.setVy(bal.getSnelheidY() * bal.getHy());
                            bal.setHy(1);
                        }
                    default:
                        break;
                }
                balNode.setId(null);
            }
        }
        if (b.get(0).getY() > 490 && b.get(0).getVy() != 0) {
            gameOver();
        }
    }

    public void reset() {
        ArrayList<Bal> ballenLijst = this.ballen.getBallen();
        for (int i = 50; i > 1; i--) {
            if (getChildren().size() + 1 > i && !getChildren().isEmpty()) {
                getChildren().remove(i - 1);
            }
            if (!ballenLijst.isEmpty() && ballenLijst.size() + 1 > i) {
                ballenLijst.remove(i - 1);
            }
        }
        update();
        ArrayList<Bal> b = ballen.getBallen();
        b.get(0).setVx(0);
        b.get(0).setVy(0);
        b.get(0).setX(500);
        b.get(0).setY(472);
        getChildren().get(0).setTranslateX(b.get(0).getX());
        getChildren().get(0).setTranslateY(b.get(0).getY());

    }

    private void gameOver() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("game over");
        alert.setContentText("je bent dood...\ndruk op reset om opnieuw te beginnen");
        alert.showAndWait();
    }

    public int getAantalBallen() {
        return getChildren().size();
    }
}
