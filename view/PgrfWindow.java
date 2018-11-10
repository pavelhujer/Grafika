package c_05_streda_13_15.view;

import javax.swing.*;
import java.awt.image.Raster;

public class PgrfWindow extends JFrame {

   private final Raster raster;

    public PgrfWindow() {
        // bez tohoto nastavení se okno zavře, ale aplikace stále běží na pozadí
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(Raster.WIDTH, Raster.HEIGHT); // velikost okna
        setLocationRelativeTo(null);// vycentrovat okno
        setTitle("PGRF1 cvičení"); // titulek okna

        // inicializace plátna, do kterého budeme kreslit img
     //   raster = new Raster();
       // raster.setFocusable(true);
       // raster.grabFocus();

        //add(raster); // vložit plátno do okna
    }

//    public Raster getRaster() {
      //  return raster;
    }
//}
