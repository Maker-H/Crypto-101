

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;


public class HeesomTest {


    void otpProperty() {
        byte[] p1 = "hello world!!".getBytes(StandardCharsets.UTF_8);
        byte[] p2 = "hello world@@".getBytes(StandardCharsets.UTF_8);
        byte[] k  = "ZZ".getBytes(StandardCharsets.UTF_8);

        byte[] c1 = xorBytes(p1, k);
        byte[] c2 = xorBytes(p2, k);

        byte[] chs = xorBytes(c1, c2);     // c1 ⊕ c2
        byte[] phs = xorBytes(p1, p2);     // p1 ⊕ p2


        System.out.println("c1: " + Arrays.toString(c1));
        System.out.println("c2: " + Arrays.toString(c2));
        System.out.println("chs: " + Arrays.toString(chs));
    }

    void betterOtpTest() {
        byte[] p1 = "the quick brown fox jumps".getBytes(StandardCharsets.UTF_8);
        byte[] p2 = "the slow black cat walks".getBytes(StandardCharsets.UTF_8);
        byte[] k = "generateRandomKey(p1.length)".getBytes(StandardCharsets.UTF_8);

        byte[] c1 = xorBytes(p1, k);
        byte[] c2 = xorBytes(p2, k);
        byte[] chs = xorBytes(c1, c2);

        System.out.println("XOR result: " + Arrays.toString(chs));

        // 크립 드래깅
        String[] cribs = {"the", "a"};
        for(String crib : cribs) {
//            cribDrag(chs, crib);
        }
    }

    void 공통된_부분_확인() {
        byte[] p1 = "hello world!!".getBytes(StandardCharsets.UTF_8);
        byte[] p2 = "hello world@@".getBytes(StandardCharsets.UTF_8);

        byte[] p3 = "hello world11".getBytes(StandardCharsets.UTF_8);
        byte[] p4 = "hello world22".getBytes(StandardCharsets.UTF_8);

        byte[] k  = "ZZ".getBytes(StandardCharsets.UTF_8);

        byte[] c1 = xorBytes(p1, k);
        byte[] c2 = xorBytes(p2, k);

        byte[] c3 = xorBytes(p3, k);
        byte[] c4 = xorBytes(p4, k);

        byte[] r1hs = xorBytes(c1, c2);     // c1 ⊕ c2
        byte[] r2hs = xorBytes(c3, c4);     // c3 ⊕ c4

        System.out.println("r1hs(b64): " + Base64.getEncoder().encodeToString(r1hs));
        System.out.println("r2hs(b64): " + Base64.getEncoder().encodeToString(r2hs));
    }

    void 이미지_c1_c2_확인() {
        File file1 = new File("D:\\[PJT] scrap_pjt\\hometax_isu\\src\\test\\java\\cipher\\unnamed1.png");
        File file2 = new File("D:\\[PJT] scrap_pjt\\hometax_isu\\src\\test\\java\\cipher\\unnamed2.png");

        try {
            BufferedImage image1 = ImageIO.read(file1);
            BufferedImage image2 = ImageIO.read(file2);

            int width1 = image1.getWidth();
            int height1 = image1.getHeight();
            int[] pixels1 = new int[width1 * height1];
            int width2 = image2.getWidth();
            int height2 = image2.getHeight();
            int[] pixels2 = new int[width2 * height2];

            int[] rgb1 = image1.getRGB(0, 0, width1, height1, pixels1, 0, width1);
            int[] rgb2 = image2.getRGB(0, 0, width2, height2, pixels2, 0, width2);

            int[] padArr = getRandomIntArr(rgb1.length);
//            System.out.println("padArr:" + Arrays.toString(padArr));

            int[] cipheredRgb1 = xorIntArr(rgb1, padArr);
            int[] cipheredRgb2 = xorIntArr(rgb2, padArr);

            BufferedImage cipheredImage1 = intArrToImage(cipheredRgb1, width1, height1);
            BufferedImage cipheredImage2 = intArrToImage(cipheredRgb2, width2, height2);

            File cipher1File = new File("ciphered_image1.png");
            File cipher2File = new File("ciphered_image2.png");
            System.out.println(cipher1File.getAbsolutePath());

            ImageIO.write(cipheredImage1, "png", cipher1File);
            ImageIO.write(cipheredImage2, "png", cipher2File);

            int[] c1c2Rgb = xorIntArr(cipheredRgb1, cipheredRgb2);
            System.out.println("c1c2:" + Arrays.toString(c1c2Rgb));
            BufferedImage c1c2image = intArrToImage(c1c2Rgb, width1, height1);
            File c1c2IFile = new File("c1c2_image.png");
            ImageIO.write(c1c2image, "png", c1c2IFile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    BufferedImage intArrToImage(int[] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int[] opaquePixels = new int[pixels.length];

        for (int i = 0; i < pixels.length; i++) {
            opaquePixels[i] = pixels[i] | 0xFF000000;
        }
        image.setRGB(0, 0, width, height, opaquePixels, 0, width);
        return image;
    }

    int[] getRandomIntArr(int len) {
        int[] result = new int[len];
        for (int i = 0; i < len; i++) {
            result[i] = (int) Math.floor(Math.random() * Integer.MAX_VALUE);
        }

        return result;
    }

    int[] xorIntArr(int[] intArr, int[] padArr) {
        int[] resultBytes = new int[intArr.length];

        for (int i = 0; i < intArr.length; i++) {
            resultBytes[i] = intArr[i] ^ padArr[i];
        }

        return resultBytes;
    }

    byte[] xorBytes(byte[] a, byte[] b) {

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

            out[i] = (byte)(atmp ^ btmp);
        }
        return out;
    }
}
