import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/* reads in the 78 input files using multi-threading */
/* reads in a compressed, binary, version of the file */

public class FileParserCompressed extends Thread {

    private static final Object gatekeeper = new Object();
    public static Canvas canvas = new Canvas((short) 2000, (short) 2000);

    public void run(int index) throws IOException {
        
        /* read the data from the binary file into a Canvas object */

        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream("../data/comp/data_" + index + ".bin"));
        while (inputStream.available() != 0) {
            try {
                byte[] colorArr = {0, 0, 0, 0};
                byte[] indexArr = {0, 0, 0, 0};
                byte[] timeArr = {0, 0, 0, 0};
                inputStream.read(colorArr, 0, 4);
                inputStream.read(timeArr, 0, 4);
                inputStream.read(indexArr, 0, 4);
                int color = byteArrayToInt(colorArr);
                int time = byteArrayToInt(timeArr);
                int linearIndex = byteArrayToInt(indexArr);
                synchronized (gatekeeper) {
                    canvas.insert(new Tile(color, time), linearIndex);
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }
        inputStream.close();
    }

    /* from https://stackoverflow.com/questions/5399798/byte-array-and-int-conversion-in-java */

    public static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }
}
