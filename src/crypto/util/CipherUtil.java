package crypto.util;

import java.awt.image.BufferedImage;

public class CipherUtil {
    public static byte[] xorByteArrs(byte[] a, byte[] b) {

        int n = Math.max(a.length, b.length);

        byte[] out = new byte[n];

        for (int i = 0; i < n; i++) {
            byte atmp = 0;
            byte btmp = 0;

            if (i < a.length) {
                atmp = a[i];
            }

            if (i < b.length) {
                btmp = b[i];
            }

            out[i] = (byte) (atmp ^ btmp);
        }
        return out;
    }

    public static int[] getRandomIntArr(int len) {
        int[] result = new int[len];
        for (int i = 0; i < len; i++) {
            result[i] = (int) Math.floor(Math.random() * Integer.MAX_VALUE);
        }

        return result;
    }

    public static int[] xorIntArrs(int[] intArr, int[] padArr) {
        int[] resultBytes = new int[intArr.length];

        for (int i = 0; i < intArr.length; i++) {
            resultBytes[i] = intArr[i] ^ padArr[i];
        }

        return resultBytes;
    }

    public static BufferedImage intArrToImage(int[] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int[] opaquePixels = new int[pixels.length];

        for (int i = 0; i < pixels.length; i++) {
            opaquePixels[i] = pixels[i] | 0xFF000000;
        }
        image.setRGB(0, 0, width, height, opaquePixels, 0, width);
        return image;
    }
}