import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/* reads in the 78 input files using multi-threading */

public class FileParser extends Thread {

    private static final Object gatekeeper = new Object();
    public static Canvas canvas = new Canvas(2000, 2000);

    public void run(int index) throws IOException {
        
        /* read the data from the CSV into a Canvas object */
        BufferedReader reader;

        reader = new BufferedReader(new FileReader("data/data_" + index + ".csv"));
        String line = reader.readLine();
        line = reader.readLine();
        while (line != null) {
            try {
                String[] lineSplit = line.split(",");
                synchronized (gatekeeper) {
                    canvas.insert(new Tile(Integer.parseInt(lineSplit[3].substring(1)), Integer.parseInt(lineSplit[4].substring(0, lineSplit[4].length() - 1)), Integer.valueOf(lineSplit[2].substring(1), 16), lineSplit[1].hashCode(), toMS(lineSplit[0])));
                }
                // read next line
                line = reader.readLine();
            }
            catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }
        reader.close();
    }


    /* modified from https://phpfog.com/how-to-convert-timestamp-to-unix-time-epoch-in-java/ */

    public static Long toMS (String timestamp){
        if(timestamp == null) return null;
        try {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS 'UTC'");
          Date dt = sdf.parse(timestamp);
          long epoch = dt.getTime();
          return (epoch);
        } catch(ParseException e) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss 'UTC'");
                Date dt = sdf.parse(timestamp);
                long epoch = dt.getTime();
                return (epoch);
            } catch (ParseException f) {
                return null;
            }
        }
    }
}
