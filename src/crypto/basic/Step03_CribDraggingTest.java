package crypto.basic;

import crypto.util.CipherUtil;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Step03_CribDraggingTest {
    static void betterOtpTest() {
        byte[] p1 = "the quick brown fox jumps".getBytes(StandardCharsets.UTF_8);
        byte[] p2 = "the slow black cat walks".getBytes(StandardCharsets.UTF_8);
        byte[] k = "generateRandomKey(p1.length)".getBytes(StandardCharsets.UTF_8);

        byte[] c1 = CipherUtil.xorByteArrs(p1, k);
        byte[] c2 = CipherUtil.xorByteArrs(p2, k);
        byte[] chs = CipherUtil.xorByteArrs(c1, c2);

        System.out.println("XOR result: " + Arrays.toString(chs));

        // 크립 드래깅
        String[] cribs = {"the", "a"};
        for(String crib : cribs) {
//            cribDrag(chs, crib);
        }
    }
}
