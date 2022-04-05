public class Cord {
    public int x;
    public int y;

    public Cord(int xCord, int yCord) {
        this.x = xCord;
        this.y = yCord;
    }
    public Cord(Cord cord) {
        this.x = cord.x;
        this.y = cord.y;
    }
}