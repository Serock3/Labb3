package view;

import model.CrystalModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by sebastian on 2017-01-30.
 */
public class CrystalView extends JPanel implements Observer {

    Color backGroundColor = Color.black;
    Color crystalColor = Color.orange;

    AffineTransform af = new AffineTransform();
    CrystalModel crystalModel;

    int mousePosX = -1;
    int mousePosY = -1;
    double mouseScrollSpeed = 1.1;

    public CrystalView(boolean isDoubleBuffered, CrystalModel crystalModel) {
        super(isDoubleBuffered);
        this.crystalModel = crystalModel;

        MouseAdapter mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePosX = e.getX();
                mousePosY = e.getY();

            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (mousePosX == -1 || mousePosY == -1) {
                    mousePosX = e.getX();
                    mousePosY = e.getY();
                    return;
                }

                //Takes the current zoom level into consideration when translating, such that the mouse stays on the same part of the image when dragged
                try {
                    AffineTransform afInverse = af.createInverse();
                    Point2D.Float distanceChange = new Point2D.Float(-(mousePosX - e.getX()), -(mousePosY - e.getY()));
                    afInverse.deltaTransform(distanceChange, distanceChange);
                    af.translate(distanceChange.x, distanceChange.y);
                } catch (NoninvertibleTransformException e1) {
                    e1.printStackTrace();
                }

                mousePosX = e.getX();
                mousePosY = e.getY();

                validate();
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mousePosX = e.getX();
                mousePosY = e.getY();
            }


            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double scaleChange = Math.pow(mouseScrollSpeed, -e.getWheelRotation());
                directionalZoom(scaleChange,e);
                mousePosX = e.getX();
                mousePosY = e.getY();
                validate();
                repaint();
            }


        };

        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addMouseWheelListener(mouse);
    }


    private void zoom(double scaleChange) {
        af.scale(scaleChange, scaleChange);
    }

    /**
     * Zooms in and translates such that the point were the mouse is at always stay in the same place
     * @param scaleChange the change in scale to zoom
     * @param e MouseWheelEvent used to retrieve mouse position
     */
    private void directionalZoom(double scaleChange, MouseWheelEvent e) {
        try {
            AffineTransform afInverse = af.createInverse();
            Point2D.Float mouseCoordinate = new Point2D.Float(e.getX(), e.getY());
            afInverse.transform(mouseCoordinate, mouseCoordinate);
            zoom(scaleChange);
            af.transform(mouseCoordinate,mouseCoordinate);
            double xOffset = mouseCoordinate.x-e.getX();
            double yOffset = mouseCoordinate.y-e.getY();
            Point2D.Double offset = new Point2D.Double(xOffset,yOffset);
            afInverse.deltaTransform(offset,offset);
            af.translate(-offset.x,-offset.y);
        } catch (NoninvertibleTransformException e1) {
            e1.printStackTrace();
        }
    }


    /**
     * Setup method to be called after components are packed, carries setup reliant on e.g. component size
     */
    public void setup() {
        af.translate(getWidth() / 2, getHeight() / 2);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        initialPaint(g2d);

        drawCrystal(g2d, crystalModel);
    }

    /**
     * Paints background and transforms coordinates for future painting
     */
    private void initialPaint(Graphics2D g2d) {
        g2d.setColor(backGroundColor);
        g2d.fill(g2d.getClip());
        g2d.setColor(crystalColor);
        g2d.transform(af);
        /*
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        */
    }

    private void drawCrystal(Graphics2D g2d, CrystalModel crystalModel) {
        ArrayList<Point> ions = crystalModel.getCrystalIons();
        ions.forEach((ion) -> g2d.fillRect(ion.x, ion.y, 1, 1));
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
