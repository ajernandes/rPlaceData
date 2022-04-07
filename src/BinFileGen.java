import java.io.IOException;

public class BinFileGen {
    public static void main(String args[]) throws IOException {

        short numFile = 78;

        /* run the multi-threaded compression */
        BinFileGenThread[] compArray = new BinFileGenThread[numFile];
        for (int i = 0; i < compArray.length; i++) {
            compArray[i] = new BinFileGenThread();
            compArray[i].run(i);
        }

        for (BinFileGenThread thread : compArray) {
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}
