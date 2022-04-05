public class Tile implements Comparable<Tile>  {
    public Cord location;
    public int color; /* this is the color index from the data, not the RGB */
    public String user;
    public int time; /* time the tile was placed it is expected to be a UNIX time stamp in milliseconds */


    public Tile(int xCord, int yCord, int color, String user, int time) {
        this.location = new Cord(xCord, yCord);
        this.color = color;
        this.user = user;
        this.time = time;
    }

    public Tile(Tile tile) {
        this.location = new Cord(tile.location);
        this.color = tile.color;
        this.user = tile.user;
        this.time = tile.time;
    }

    @Override
    public int compareTo(Tile otherTile) {
        return Integer.compare(time, otherTile.time);
    }
}