import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Canvas implements Serializable {
    public short width;
    public short heigth;
    public HashMap<Integer,Pixel> pixels;

    public Canvas(short width, short height) {
        this.width = width;
        this.heigth = height;
        this.pixels = new HashMap<Integer,Pixel>(width * height);
        for (int i = 0; i < width * height; i++) this.pixels.put(i, new Pixel(i, 0));
    }

    public void insert(Tile tile, int linearIndex) {
        // if (tile.x > width || tile.y > heigth) return;
        this.pixels.get(linearIndex).addTile(tile);
    }

    public Pixel getPixel(int x, int y) {
        int index = (heigth * y) + x;
        return this.pixels.get(index);
    }
}
