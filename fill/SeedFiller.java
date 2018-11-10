package c_05_streda_13_15.fill;

import c_05_streda_13_15.view.Raster;

public class SeedFiller implements Filler {

    private Raster raster;
    private int background, fillColor;
    private int x, y;

    @Override
    public void setRaster(Raster raster) {
        this.raster = raster;
    }

    @Override
    public void fill() {
        seed(x, y);
    }

    public void init(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.fillColor = color;
        background = raster.getPixel(x, y);
    }

    // pozor na rekurzivní volání
    // nutné upravit parametr pro VM "-Xss100m"
    // https://stackoverflow.com/questions/4967885/jvm-option-xss-what-does-it-do-exactly
    private void seed(int ax, int ay) {
        if (ax >= 0 && ay >= 0 && ax < Raster.WIDTH && ay < Raster.HEIGHT) {
            if (background == raster.getPixel(ax, ay)) {
                raster.drawPixel(ax, ay, fillColor);
                seed(ax + 1, ay);
                seed(ax - 1, ay);
                seed(ax, ay + 1);
                seed(ax, ay - 1);
            }
        }
    }
}
