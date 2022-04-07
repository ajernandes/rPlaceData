import java.io.Serializable;

public class Tile implements Comparable<Tile>  {
    public int color; /* this is the color index from the data, not the RGB */
    public long time; /* time the tile was placed it is expected to be a UNIX time stamp in milliseconds */


    public Tile(int color, long time) {
        this.color = color;
        this.time = time;
    }

    public Tile(Tile tile) {
        this.color = tile.color;
        this.time = tile.time;
    }

    @Override
    public int compareTo(Tile otherTile) {
        return Long.compare(time, otherTile.time);
    }
}