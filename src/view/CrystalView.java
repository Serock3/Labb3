package view;

import model.CrystalModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by sebastian on 2017-01-30.
 */
public class CrystalView extends JPanel implements Observer{

    CrystalModel crystalModel;
    public CrystalView(boolean isDoubleBuffered, CrystalModel crystalModel) {
        super(isDoubleBuffered);
        this.crystalModel = crystalModel;

        //setPreferredSize(new Dimension(crystalModel.getBathWidth(),crystalModel.getBathWidth()));
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g); // först
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
