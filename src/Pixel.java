import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Pixel implements Serializable {
    public int x;
    public int y;
    public ArrayList<Tile> tiles;
    public int inceptionTime; /* this is the time that the pixel was created, since the canvas was expanded twice */
                              /* it is expected to be a UNIX time stamp in milliseconds */

    public Pixel(int x, int y, int inceptionTime) {
        this.x = x;
        this.y = y;
        this.tiles = new ArrayList<>();
        this.inceptionTime = inceptionTime;
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    public int getNumberOfTiles() {
        return this.tiles.size();
    }

    public Tile getFirstTile() {
        Collections.sort(tiles);
        if (tiles.size() == 0) return new Tile(x, y, -1, -1);
        return tiles.get(0);
    }

    public Tile getLastTile() {
        Collections.sort(tiles);
        if (tiles.size() == 0) return new Tile(x, y, -1, -1);
        return tiles.get(tiles.size() - 1);
    }


    public int getMedianRGB() {
        Collections.sort(tiles);
        if (tiles.size() == 0) return 0x333333;
        return tiles.get(tiles.size()/2).color;
    }

    public int getAverageRGB() {
        if (tiles.size() == 0) return 0x000000;
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        for (Tile tile : tiles) {
            int color = tile.color;
            sumRed += color / 0x10000;
            sumGreen += (color / 0x100) % 0x100;
            sumBlue += color % 0x100;
        }
        int red = (sumRed) / this.tiles.size();
        int green = (sumGreen) / this.tiles.size();
        int blue = (sumBlue) / this.tiles.size();

        return (red * 0x10000) + (green * 0x100) + blue;
    }

    public int getPixelAtTime(String time) {


        return 0;
    }
}
