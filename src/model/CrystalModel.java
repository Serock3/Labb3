package model;


import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by sebastian on 2017-01-29.
 */
public class CrystalModel extends Observable{

    private boolean[][] matrix; //matrix representing bath coordinates, true for occupied, false for empty

    private final int bathWidth;
    private final int center; //exists purely for clarity, is constant at half the width. Used both for x and y coordinate.

    private int startRadiusPercentage = 80;
    private int startRadius;

    private Point ion;//coordinates of current

    private boolean debug = false; //if true, dubug info will be printed in console

    private int pause; //time to pause between steps //todo: make graphics and model asynchronous so pauses work properly, might work right away with swing.



    public CrystalModel(int bathWidth) {
        if (bathWidth % 2 == 0) bathWidth++;//makes the width odd to ensure existance of center point
        this.bathWidth = bathWidth;
        center = (bathWidth - 1) / 2; // represents the center point, but also the escape radius
        startRadius = bathWidth * startRadiusPercentage / (100 * 2);
        reset();
    }

    public boolean crystallizeOneIon() {
        dropNewIon();
        if (getModelValue(ion.x, ion.y)) {
            print("Ion dropped atop existing one");
            return false; //if new ion is released on top of existing one, then crystal is done
        }
        while (true) {
            int ran = (int) (Math.random() * 4);
            //print("Random number: " + ran); //spams too much even for debugging.
            switch (ran) {
                case 0:
                    ion.x++;
                    break;
                case 1:
                    ion.x--;
                    break;
                case 2:
                    ion.y++;
                    break;
                case 3:
                    ion.y--;
                    break;
            }
            print("Ion moved, new pos: " + ion.x + "," + ion.y);
            if (anyNeighbours(ion.x, ion.y)) {
                addIonToMatrix(ion.x, ion.y);
                print("Ion added to crystal");
                setChanged();
                notifyObservers();
                return !outsideCircle(center, ion.x, ion.y);
            }
            if (outsideCircle(center, ion.x, ion.y)) {
                print("Ion outside circle, dropping new");
                dropNewIon();
            }
        }
    }

    public boolean getModelValue(int x, int y) {
        x = xBathToModelRep(x);
        y = yBathToModelRep(y);
        if (x < 0 || x > (bathWidth - 1) || y < 0 || y > (bathWidth - 1)) return false; //prevents out of bounds error by returning false, doesn't interfere with logic.
        return matrix[x][y];
    }

    private boolean outsideCircle(int r, int x, int y) {
        return ((Math.pow(x, 2) + Math.pow(y, 2)) > (Math.pow(r, 2)));
    }

    private boolean anyNeighbours(int x, int y) {
        return (getModelValue(x + 1, y) || getModelValue(x - 1, y) || getModelValue(x, y + 1) || getModelValue(x, y - 1));
    }

    private void dropNewIon() {
        double ranAngle = Math.random() * 2 * 3.14;
        print("Random angle is: " + ranAngle);
        ion = new Point((int) (startRadius * Math.cos(ranAngle)), (int) (startRadius * Math.sin(ranAngle)));
        print("Ion dropped at: " + ion.x + "," + ion.y);
    }

    private void addIonToMatrix(int x, int y) {
        matrix[xBathToModelRep(x)][yBathToModelRep(y)] = true;
    }

    @Override
    public String toString() {
        return arrayToString(matrix);
    }

    private void reset() {
        matrix = new boolean[bathWidth][bathWidth];
        matrix[center][center] = true;
    }

    /**
     * Transformerar från badkordinat till modellens kordinat.
     * tex  badkordinat 0,0 transformeras till model kordinat size/2, size/2
     * om size är badets diameter
     *
     * @param x x-koordinaten i badets kordinatsystem tex -200..200
     * @return motsvarande kordinat i modellens kordinatsystem tex 0..400
     */
    private int xBathToModelRep(int x) {
        return x + center;
    }

    private int yBathToModelRep(int y) {
        return y + center;
    }

    private int BathModelRepToX(int xIndex){
        return xIndex-center;
    }

    private int BathModelRepToY(int yIndex){
        return yIndex-center;
    }

    private void print(String message) {
        if (debug) System.out.println(message);
    }

    public int getBathWidth() {
        return bathWidth;
    }

    public Point getCurrentIon(){
        return ion;
    }

    /**
     *
     * @return
     */
    public ArrayList<Point> getCrystalIons(){
        ArrayList<Point> ions = new ArrayList<Point>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j])ions.add(new Point(BathModelRepToX(i),BathModelRepToY(j)));
            }
        }
        return ions;
    }

    //todo: deleteme
    private static String arrayToString(boolean[][] a) {

        String aString;
        aString = "";
        int column;
        int row;

        for (row = 0; row < a.length; row++) {
            for (column = 0; column < a[0].length; column++) {
                aString = aString + " " + (a[row][column] ? "o" : " ");
            }
            aString = aString + "\n";
        }

        return aString;
    }
}
