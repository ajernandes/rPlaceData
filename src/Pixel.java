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


    public int getMedianRGB() {
        Collections.sort(tiles);
        if (tiles.size() == 0) return 0x333333;
        return decodeColor(tiles.get(tiles.size()/2).color);
    }

    public int getAverageRGB() {
        if (tiles.size() == 0) return 0x000000;
        int sumRed = 0;
        int sumGreen = 0;
        int sumBlue = 0;
        for (Tile tile : tiles) {
            int color = decodeColor(tile.color);
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

    private int decodeColor (int index) {
        switch (index) {
            case 0: return 0xFFFFFF;
            case 1: return 0xE4E4E4;
            case 2: return 0x888888;
            case 3: return 0x222222;
            case 4: return 0xFFA7D1;
            case 5: return 0xE50000;
            case 6: return 0xE59500;
            case 7: return 0xA06A42;
            case 8: return 0xE5D900;
            case 9: return 0x94E044;
            case 10: return 0x02BE01;
            case 11: return 0x00E5F0;
            case 12: return 0x0083C7;
            case 13: return 0x0000EA;
            case 14: return 0xE04AFF;
            case 15: return 0x820080;
            default: return 0xFFFFFF;
        }
    }
}
