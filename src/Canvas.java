import java.io.Serializable;
import java.util.ArrayList;

public class Canvas implements Serializable {
    public int width;
    public int heigth;
    public ArrayList<ArrayList<Pixel>> pixels;

    public Canvas(int width, int height) {
        this.width = width;
        this.heigth = height;
        this.pixels = new ArrayList<ArrayList<Pixel>>();
        for (int i = 0; i < width + 1; i++) {
            this.pixels.add(new ArrayList<Pixel>());
            for (int j = 0; j < height + 1; j++) {
                this.pixels.get(i).add(new Pixel(i, j, 0));
            }
        }
    }

    public void insert(Tile tile) {
        if (tile.x > width || tile.y > heigth) return;
        this.pixels.get(tile.x).get(tile.y).addTile(new Tile(tile));
    }

    public Pixel getPixel(int x, int y) {
        return this.pixels.get(x).get(y);
    }
}
