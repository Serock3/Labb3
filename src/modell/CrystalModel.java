package modell;

import java.awt.*;
import java.util.Arrays;

/**
 * Created by sebastian on 2017-01-29.
 */
public class CrystalModel {

    private boolean[][] matrix; //matrix representing bath coordinates, true for occupied, false for empty

    private final int center; //exists purely for clarity, is constant at half the width. Used both for x and y coordinate.

    private int escapeRadiusPresentage = 80;
    private int escapeRadius;

    private Point oin;//coordinates of current


    private int pause; //time to pause between steps //todo: make graphics and model asynchronous so pauses work properly, might work right away with swing.

    public CrystalModel(int bathWidth) {
        matrix = new boolean[bathWidth][bathWidth]; //the bath is symmetrical by default
        center = bathWidth/2;
        escapeRadius = bathWidth*escapeRadiusPresentage/(100*2);
    }

    private boolean crystallizeOneIon(){return true;}

    private boolean getModelValue(int x, int y){return false;}

    private boolean outsideCircle(int r, int x, int y){return false;}

    private boolean anyNeighbours(int x, int y){return false;}

    private void dropNewIon(){}

    @Override
    //fixme: skriv ut en textbaserad representation av badet
    public String toString() {
        return "CrystalModel{" +
                "matrix=" + Arrays.toString(matrix) +
                ", center=" + center +
                ", escapeRadiusPresentage=" + escapeRadiusPresentage +
                ", escapeRadius=" + escapeRadius +
                ", oin=" + oin +
                ", pause=" + pause +
                '}';
    } private void reset(){}

    private int xBathToModelRep(int x){return 0;}

    private int yBathToModelRep(int y){return 0;}
}
