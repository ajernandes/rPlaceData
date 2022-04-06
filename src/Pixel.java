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
        if (tiles.size() == 0) return new Tile(x, y, -1, "user", -1);
        return tiles.get(0);
    }

    public Tile getLastTile() {
        Collections.sort(tiles);
        if (tiles.size() == 0) return new Tile(x, y, -1, "user", -1);
        return tiles.get(tiles.size() - 1);
    }
}
