import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.image.BufferedImage;


public class App {

    
    public static void main(String[] args) throws Exception {

        int canvas_width = 2000;
        int canvas_height = 2000;
        int numFile = 78;
        
        /* run the multi-threaded import */
        FileParser[] threadArray = new FileParser[numFile];
        for (int i = 0; i < threadArray.length; i++) {
            threadArray[i] = new FileParser();
            threadArray[i].run(i);
        }

        for (FileParser thread : threadArray) {
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        Canvas canvas = FileParser.canvas;

            /* Create an image containing only the first tile placed on each pixel */


            /* from https://stackoverflow.com/questions/10767471/convert-2d-array-in-java-to-image */

            String path = "first.png";
            BufferedImage image = new BufferedImage(canvas_width, canvas_height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < canvas.width; x++) {
                for (int y = 0; y < canvas.heigth; y++) {
                    int c = canvas.getPixel(x, y).getFirstTile().color;
                    image.setRGB(x, y, c);
                }
            }
        
            File ImageFile = new File(path);
            try {
                ImageIO.write(image, "png", ImageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            path = "final.png";
            image = new BufferedImage(canvas_width, canvas_height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < canvas.width; x++) {
                for (int y = 0; y < canvas.heigth; y++) {
                    int c = canvas.getPixel(x, y).getLastTile().color;
                    image.setRGB(x, y, c);
                }
            }
        
            ImageFile = new File(path);
            try {
                ImageIO.write(image, "png", ImageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /* average color */

            path = "average.png";
            image = new BufferedImage(canvas_width + 1, canvas_height + 1, BufferedImage.TYPE_INT_RGB);
            for (ArrayList<Pixel> row : canvas.pixels) {
                for (Pixel pixel : row) {
                    image.setRGB(pixel.x, pixel.y, pixel.getAverageRGB());
                }
            }
        
            ImageFile = new File(path);
            try {
                ImageIO.write(image, "png", ImageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /* median color */

            path = "median.png";
            image = new BufferedImage(canvas_width + 1, canvas_height + 1, BufferedImage.TYPE_INT_RGB);
            for (ArrayList<Pixel> row : canvas.pixels) {
                for (Pixel pixel : row) {
                    image.setRGB(pixel.x, pixel.y, pixel.getMedianRGB());
                }
            }
        
            ImageFile = new File(path);
            try {
                ImageIO.write(image, "png", ImageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /* zero and one placement */

            path = "lowactivity.png";
            image = new BufferedImage(canvas_width + 1, canvas_height + 1, BufferedImage.TYPE_INT_RGB);
            int untouched = 0;
            int oncetouched = 0;
            int useless = 0;
            for (ArrayList<Pixel> row : canvas.pixels) {
                for (Pixel pixel : row) {
                    if (pixel.getNumberOfTiles() == 0) {
                        image.setRGB(pixel.x, pixel.y, 0xFFFFFF);
                        untouched++;
                    }
                    if (pixel.getNumberOfTiles() == 1) {
                        image.setRGB(pixel.x, pixel.y, pixel.getFirstTile().color);
                        if (pixel.getFirstTile().color == 0) useless++;
                        oncetouched++;
                    }
                }
            }
            System.out.println(untouched + " pixels with no placement made");
            System.out.println(oncetouched + " pixels only touched once");
            System.out.println(useless + " pixels touched once by placing white on them");
        
            ImageFile = new File(path);
            try {
                ImageIO.write(image, "png", ImageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }


            path = "highactivity.png";
            image = new BufferedImage(canvas_width + 1, canvas_height + 1, BufferedImage.TYPE_INT_RGB);
            for (ArrayList<Pixel> row : canvas.pixels) {
                for (Pixel pixel : row) {
                    if (pixel.getNumberOfTiles() >= 1000) {
                        image.setRGB(pixel.x, pixel.y, pixel.getLastTile().color);
                    }
                }
            }
        
            ImageFile = new File(path);
            try {
                ImageIO.write(image, "png", ImageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /* user heatmap */
            int max_users = 0;
            for (int x = 0; x < canvas.width; x++) {
                for (int y = 0; y < canvas.heigth; y++) {
                    if (max_users < canvas.getPixel(x, y).getNumUsers()) max_users = canvas.getPixel(x, y).getNumUsers();
                }
            }
            /* open the image writer */
            path = "userheatmap.png";
            image = new BufferedImage(canvas_width, canvas_height, BufferedImage.TYPE_INT_RGB);
            /* for each pixel */
            for (int x = 0; x < canvas.width; x++) {
                for (int y = 0; y < canvas.heigth; y++) {
                    /* get the number of times it changes and cooreleate that to a RGB code */
                    int changes = canvas.getPixel(x, y).getNumUsers();
                    image.setRGB(x, y, getHeatColor(changes /  (double) max_users));
                }
            }
            ImageFile = new File(path);
            try {
                ImageIO.write(image, "png", ImageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /* changes heatmap */
            int max_changes = 0;
            for (int x = 0; x < canvas.width; x++) {
                for (int y = 0; y < canvas.heigth; y++) {
                    if (max_changes < canvas.getPixel(x, y).getNumberOfTiles()) max_changes = canvas.getPixel(x, y).getNumberOfTiles();
                }
            }
            /* open the image writer */
            path = "heatmap.png";
            image = new BufferedImage(canvas_width, canvas_height, BufferedImage.TYPE_INT_RGB);
            /* for each pixel */
            for (int x = 0; x < canvas.width; x++) {
                for (int y = 0; y < canvas.heigth; y++) {
                    /* get the number of times it changes and cooreleate that to a RGB code */
                    int changes = canvas.getPixel(x, y).getNumberOfTiles();
                    image.setRGB(x, y, getHeatColor(changes /  (double) max_changes));
                }
            }
            ImageFile = new File(path);
            try {
                ImageIO.write(image, "png", ImageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    static int getHeatColor(double percentile) {
        if (percentile < 0.00001) return 0x000000;
        if (percentile < 0.0005) return 0x000326;
        if (percentile < 0.0010) return 0x1e0047;
        if (percentile < 0.0020) return 0x2b0047;
        if (percentile < 0.0050) return 0x350047;
        if (percentile < 0.0100) return 0x400047;
        if (percentile < 0.0250) return 0x70002d;
        if (percentile < 0.0400) return 0xd40023;
        if (percentile < 0.0700) return 0xe60707;
        return 0xFFFFFF;
    }
}

