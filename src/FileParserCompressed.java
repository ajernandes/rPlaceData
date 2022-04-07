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

        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream("data/comp/data_" + index + ".bin"));
        while (inputStream.available() != 0) {
            try {
                int color = inputStream.read();
                int linearIndex = inputStream.read();
                int time = inputStream.read();
                synchronized (gatekeeper) {
                    canvas.insert(new Tile(color, time), linearIndex);
                }
                // read next line
            }
            catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }
        inputStream.close();
    }
}
