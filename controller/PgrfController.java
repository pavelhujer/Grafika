package c_05_streda_13_15.controller;

import c_05_streda_13_15.fill.SeedFiller;
import c_05_streda_13_15.model.Point;
import c_05_streda_13_15.renderer.Renderer;
import c_05_streda_13_15.view.Raster;
import transforms.Mat3;
import transforms.Mat3Identity;
import transforms.Mat3Transl2D;
import transforms.Point2D;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PgrfController {

    private Raster raster;
    private Renderer renderer;
    private SeedFiller seedFiller;
    private List<Point> polygonPoints = new ArrayList<>();
    private List<Point> clipPoints = new ArrayList<>(); // TODO
    private List<Point2D> linePoints = new ArrayList<>();

    private Mat3 transl = new Mat3Identity();
    private int mx, my;

    public PgrfController(Raster raster) {
        this.raster = raster;
        initObjects();
        initListeners();
    }

    private void initObjects() {
        renderer = new Renderer(raster);

        seedFiller = new SeedFiller();
        seedFiller.setRaster(raster);
    }

    private void initListeners() {
        raster.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    polygonPoints.add(new Point(e.getX(), e.getY()));
                    if (polygonPoints.size() == 1) { // při prvním kliknutí přidat rovnou i druhý bod
                        polygonPoints.add(new Point(e.getX(), e.getY()));
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    linePoints.add(new Point2D(e.getX(), e.getY()));
                    linePoints.add(new Point2D(e.getX(), e.getY()));

                } else if (SwingUtilities.isMiddleMouseButton(e)) {
                    mx = e.getX();
                    my = e.getY();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isControlDown()) {
                    seedFiller.init(e.getX(), e.getY(), 0xff00ff);
                    seedFiller.fill();
                }
            }
        });
        raster.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    polygonPoints.get(polygonPoints.size() - 1).x = e.getX();
                    polygonPoints.get(polygonPoints.size() - 1).y = e.getY();
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    linePoints.set(linePoints.size() - 1,
                            linePoints.get(linePoints.size() - 1).withX(e.getX())
                    );
                    linePoints.set(linePoints.size() - 1,
                            linePoints.get(linePoints.size() - 1).withY(e.getY())
                    );
                } else if (SwingUtilities.isMiddleMouseButton(e)) {
                    transl = transl.mul(new Mat3Transl2D(e.getX() - mx, e.getY() - my));
                    mx = e.getX();
                    my = e.getY();
                }
                update();
            }
        });
        raster.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //System.out.println(e.getKeyCode());
                // při zmáčknutí klávesy C vymazat plátno
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    raster.clear();
                }
            }
        });
    }

    private void update() {
        raster.clear();
        renderer.drawPolygon(polygonPoints, 0xff0000);

        List<Point2D> transformedLines = new ArrayList<>();
        for (Point2D point : linePoints) {
            transformedLines.add(point.mul(transl));
        }
        renderer.drawLines(transformedLines, 0x00ff00);

        //List<Point> out = renderer.clip(...)
        //renderer.drawPolygon(out, 0xfff000);
    }

}
