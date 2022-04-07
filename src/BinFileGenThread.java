import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/* reads in the 78 input files using multi-threading */

public class BinFileGenThread extends Thread {
    public void run(int index) throws IOException {
        
        /* read the data from the CSV into a binary file */

        BufferedReader reader = new BufferedReader(new FileReader("../data/data_" + index + ".csv"));
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream("../data/comp/data_" + index + ".bin"));
        String line = reader.readLine();
        line = reader.readLine();
        while (line != null) {
            try {
                String[] lineSplit = line.split(",");
                int color = Integer.valueOf(lineSplit[2].substring(1), 16);
                long offset = toMS("2022-01-01 00:00:00.000 UTC");
                long time = toMS(lineSplit[0]) - offset;
                int linearIndex = 2000 * Integer.parseInt(lineSplit[3].substring(1)) + Integer.parseInt(lineSplit[4].substring(0, lineSplit[4].length() - 1));
                writer.write(intToByteArray(color), 0, 4);
                writer.write(intToByteArray((int)time), 0, 4);
                writer.write(intToByteArray(linearIndex), 0, 4);
                // read next line
                line = reader.readLine();
            }
            catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }
        reader.close();
        writer.close();
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

    /* https://stackoverflow.com/questions/5399798/byte-array-and-int-conversion-in-java */

    public static byte[] intToByteArray(int a) {
        return new byte[] {
            (byte) ((a >> 24) & 0xFF),
            (byte) ((a >> 16) & 0xFF),
            (byte) ((a >> 8) & 0xFF), 
            (byte) (a & 0xFF)
        };
    }
}
