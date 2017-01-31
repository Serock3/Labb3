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
        CrystalModel crystalModel = new CrystalModel(windowSize);
        SwingUtilities.invokeLater(()->{
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            CrystalView crystalView = new CrystalView(false,crystalModel);
            crystalView.setPreferredSize(new Dimension(windowSize,windowSize));
            f.add(crystalView);
            f.pack();
            crystalView.setup();
            crystalModel.addObserver(crystalView);
        });

        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                while (crystalModel.crystallizeOneIon());
                return null;
            }
        };
        worker.execute();

    }
}
