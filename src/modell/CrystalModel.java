package modell;

import java.awt.*;
import java.nio.charset.CoderMalfunctionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Created by sebastian on 2017-01-29.
 */
public class CrystalModel {

    private boolean[][] matrix; //matrix representing bath coordinates, true for occupied, false for empty

    private final int bathWidth;
    private final int center; //exists purely for clarity, is constant at half the width. Used both for x and y coordinate.

    private int startRadiusPresentage = 80;
    private int startRadius;

    private Point ion;//coordinates of current


    private int pause; //time to pause between steps //todo: make graphics and model asynchronous so pauses work properly, might work right away with swing.

    public CrystalModel(int bathWidth) {
        if(bathWidth%2==0)bathWidth++;//makes the width odd to ensure existance of center point
        this.bathWidth=bathWidth;
        matrix = new boolean[bathWidth][bathWidth]; //the bath is symmetrical by default
        center = (bathWidth-1)/2; // represents the center point, but also the escape radius
        startRadius = bathWidth* startRadiusPresentage /(100*2);
    }

    public boolean crystallizeOneIon(){
        reset();
        dropNewIon();
        if(getModelValue(ion.x,ion.y))return false; //if new ion is released on top of existing one, then crystal is done
        while (true){
            int ran = (int)(Math.random()*4);
            switch(ran){
                case 0: ion.move(1,0);
                case 1: ion.move(-1,0);
                case 2: ion.move(0,1);
                case 3: ion.move(0,-1);
            }
            if(anyNeighbours(ion.x,ion.y)) return !outsideCircle(center,ion.x,ion.y);
            if(outsideCircle(center,ion.x,ion.y))dropNewIon();
        }
    }

    public boolean getModelValue(int x, int y){
        x=xBathToModelRep(x);
        y=yBathToModelRep(y);
        return matrix[x][y];
    }

    private boolean outsideCircle(int r, int x, int y){
        return ((x^2+y^2) > (r^2));
    }

    private boolean anyNeighbours(int x, int y){
        x=xBathToModelRep(x);
        y=yBathToModelRep(y);
        return (matrix[x+1][y] || matrix[x-1][y] || matrix[x][y+1] || matrix[x][y-1]);
    }

    private void dropNewIon(){
        ion = new Point((int)(startRadius *Math.cos(Math.random()*2*3.14)), (int)(startRadius *Math.sin(Math.random()*2*3.14)));
    }

    @Override
    //fixme: skriv ut en textbaserad representation av badet
    public String toString() {
        return arrayToString(matrix);
    }

    private void reset(){
        matrix = new boolean[bathWidth][bathWidth];
        matrix[center][center] = true;
    }

    /**
     * Transformerar från badkordinat till modellens kordinat.
     * tex  badkordinat 0,0 transformeras till modell kordinat size/2, size/2
     * om size är badets diameter
     * @param x x-koordinaten i badets kordinatsystem tex -200..200
     * @return motsvarande kordinat i modellens kordinatsystem tex 0..400
     */
    private int xBathToModelRep(int x) {
        return x+center;
    }
    private int yBathToModelRep(int y) {
        return center-y;
    }

    //todo: deleteme
    public static String arrayToString(boolean[][] a) {

        String aString;
        aString = "";
        int column;
        int row;

        for (row = 0; row < a.length; row++) {
            for (column = 0; column < a[0].length; column++ ) {
                aString = aString + " " + (a[row][column]?"o":"x");
            }
            aString = aString + "\n";
        }

        return aString;
    }
}
