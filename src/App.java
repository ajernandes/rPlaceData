import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.image.BufferedImage;

public class App {
    public static void main(String[] args) throws Exception {

        Canvas canvas = new Canvas(1000, 1000);

		BufferedReader reader;

        /* read the data from the CSV into a Canvas object */

        reader = new BufferedReader(new FileReader(
					"data.csv"));
			String line = reader.readLine();
			line = reader.readLine();
			line = reader.readLine();
			line = reader.readLine();
			while (line != null) {
			// for (int i = 0; i < 2000000; i++) {
                try {
                    String[] lineSplit = line.split(",");
                    String time = lineSplit[0];
                    int timeInt = toMS(time);
                    canvas.insert(new Tile(Integer.parseInt(lineSplit[2]), Integer.parseInt(lineSplit[3]), Integer.parseInt(lineSplit[4]), lineSplit[1], timeInt));
				    // read next line
				    line = reader.readLine();
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
			}
			reader.close();

            /* Create an image containing only the first tile placed on each pixel */


            /* from https://stackoverflow.com/questions/10767471/convert-2d-array-in-java-to-image */

            // String path = "first.png";
            // BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
            // for (int x = 0; x < canvas.width; x++) {
            //     for (int y = 0; y < canvas.heigth; y++) {
            //         int c = canvas.getPixel(x, y).getFirstTile().color;
            //         image.setRGB(x, y, getColorFromIndex(c));
            //     }
            // }
        
            // File ImageFile = new File(path);
            // try {
            //     ImageIO.write(image, "png", ImageFile);
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }

            /* find out what was the most number of changes */
            int max_changes = 0;
            for (int x = 0; x < canvas.width; x++) {
                for (int y = 0; y < canvas.heigth; y++) {
                    if (max_changes < canvas.getPixel(x, y).getNumberOfTiles()) max_changes = canvas.getPixel(x, y).getNumberOfTiles();
                }
            }
            /* open the image writer */
            String path = "heatmap.png";
            BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
            /* for each pixel */
            for (int x = 0; x < canvas.width; x++) {
                for (int y = 0; y < canvas.heigth; y++) {
                    /* get the number of times it changes and cooreleate that to a RGB code */
                    int changes = canvas.getPixel(x, y).getNumberOfTiles();
                    image.setRGB(x, y, getHeatColor(changes / max_changes));
                }
            }
        
            File ImageFile = new File(path);
            try {
                ImageIO.write(image, "png", ImageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    /* modified from https://phpfog.com/how-to-convert-timestamp-to-unix-time-epoch-in-java/ */

    public static Integer toMS (String timestamp){
        if(timestamp == null) return null;
        try {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS 'UTC'");
          Date dt = sdf.parse(timestamp);
          long epoch = dt.getTime();
          return (int)(epoch);
        } catch(ParseException e) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss 'UTC'");
                Date dt = sdf.parse(timestamp);
                long epoch = dt.getTime();
                return (int)(epoch);
            } catch (ParseException f) {
                return null;
            }
        }
    }

    static int getColorFromIndex(int index) {
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
            default: return 0;
        }
    }

    static int getHeatColor(int percentile) {
        if (percentile < 0.1) return 0x000000;
        if (percentile < 0.2) return 0x000326;
        if (percentile < 0.3) return 0x1e0047;
        if (percentile < 0.4) return 0x2b0047;
        if (percentile < 0.5) return 0x350047;
        if (percentile < 0.6) return 0x400047;
        if (percentile < 0.7) return 0x70002d;
        if (percentile < 0.8) return 0xd40023;
        if (percentile < 0.9) return 0xe60707;
        return 0xFFFFFF;
    }
}

