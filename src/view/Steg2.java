package view;

import model.CrystalModel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sebastian on 2017-01-30.
 */
public class Steg2 {
    public static void main(String[] args) {
        int windowSize = Integer.parseInt(args[0]);
        System.out.println("Window size:"+windowSize);
        JFrame f = new JFrame("Steg 2");
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(windowSize,windowSize);
        f.add(new CrystalView(true,new CrystalModel(windowSize)));
    }
}
