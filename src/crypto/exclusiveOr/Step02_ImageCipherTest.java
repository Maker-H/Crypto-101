package crypto.exclusiveOr;

import crypto.util.CipherUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Step02_ImageCipherTest {


    static void 이미지_c1_c2_확인() {
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

            int[] padArr = CipherUtil.getRandomIntArr(rgb1.length);

            int[] cipheredRgb1 = CipherUtil.xorIntArrs(rgb1, padArr);
            int[] cipheredRgb2 = CipherUtil.xorIntArrs(rgb2, padArr);

            BufferedImage cipheredImage1 = CipherUtil.intArrToImage(cipheredRgb1, width1, height1);
            BufferedImage cipheredImage2 = CipherUtil.intArrToImage(cipheredRgb2, width2, height2);

            File cipher1File = new File("ciphered_image1.png");
            File cipher2File = new File("ciphered_image2.png");
            System.out.println(cipher1File.getAbsolutePath());

            ImageIO.write(cipheredImage1, "png", cipher1File);
            ImageIO.write(cipheredImage2, "png", cipher2File);

            int[] c1c2Rgb = CipherUtil.xorIntArrs(cipheredRgb1, cipheredRgb2);
            System.out.println("c1c2:" + Arrays.toString(c1c2Rgb));
            BufferedImage c1c2image = CipherUtil.intArrToImage(c1c2Rgb, width1, height1);
            File c1c2IFile = new File("c1c2_image.png");
            ImageIO.write(c1c2image, "png", c1c2IFile);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}