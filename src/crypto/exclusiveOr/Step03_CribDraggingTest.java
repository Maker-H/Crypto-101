package crypto.exclusiveOr;

import crypto.util.CipherUtil;

import java.nio.charset.StandardCharsets;

public class Step03_CribDraggingTest {

    public static void main(String[] args) {

        byte[] p1 = "MEET AT BRIDGE TOMORROW".getBytes(StandardCharsets.UTF_8);
        byte[] p2 = "OK MEET ME AT SEVEN".getBytes(StandardCharsets.UTF_8);
        byte[] k = "generateRandomKey(p1.length)".getBytes(StandardCharsets.UTF_8);

        byte[] c1 = CipherUtil.xorByteArrs(p1, k);
        byte[] c2 = CipherUtil.xorByteArrs(p2, k);

        betterOtpTest(c1, c2);
        getPad(c1, c2);
    }

    static void betterOtpTest(byte[] c1, byte[] c2) {

        byte[] chs = CipherUtil.xorByteArrs(c1, c2);
        System.out.println("chs:" + new String(chs, StandardCharsets.UTF_8));

        String[] cribs = {"the", "OK"};
        for (String crib : cribs) {
            System.out.println("crib: " + crib);
            int cribLen = crib.length();
            String chsStr = new String(chs);

            for (int i = 0; i < chsStr.length() - cribLen; i+=cribLen) {
                String substring = chsStr.substring(i, i + cribLen);

                byte[] cribBytes = crib.getBytes(StandardCharsets.UTF_8);
                byte[] substringBytes = substring.getBytes(StandardCharsets.UTF_8);
                byte[] xorBytes = CipherUtil.xorByteArrs(cribBytes, substringBytes);

                System.out.printf("%s-%s: %s\n", i, i+cribLen, new String(xorBytes, StandardCharsets.UTF_8));
            }
        }
    }

    static void getPad(byte[] c1, byte[] c2) {
        String c1Sub = new String(c1, StandardCharsets.UTF_8).substring(0, 2);
        String c2Sub = new String(c2, StandardCharsets.UTF_8).substring(0, 2);

        byte[] c1Pad = CipherUtil.xorByteArrs(c1Sub.getBytes(StandardCharsets.UTF_8), "OK".getBytes(StandardCharsets.UTF_8));
        byte[] c2Pad = CipherUtil.xorByteArrs(c2Sub.getBytes(StandardCharsets.UTF_8), "OK".getBytes(StandardCharsets.UTF_8));

        System.out.println("c1Pad: " + new String(c1Pad, StandardCharsets.UTF_8));
        System.out.println("c2Pad: " + new String(c2Pad, StandardCharsets.UTF_8));
    }
}
