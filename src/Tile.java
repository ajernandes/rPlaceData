import java.io.Serializable;

public class Tile implements Comparable<Tile>  {
    public int x;
    public int y;
    public int color; /* this is the color index from the data, not the RGB */
    public String user;
    public long time; /* time the tile was placed it is expected to be a UNIX time stamp in milliseconds */


    public Tile(int x, int y, int color, String user, long time) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.user = user;
        this.time = time;
    }

    public Tile(Tile tile) {
        this.x = tile.x;
        this.y = tile.y;
        this.color = tile.color;
        this.user = tile.user;
        this.time = tile.time;
    }

    @Override
    public int compareTo(Tile otherTile) {
        return Long.compare(time, otherTile.time);
    }
}