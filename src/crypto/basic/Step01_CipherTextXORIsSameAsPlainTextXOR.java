package crypto.basic;

import crypto.util.CipherUtil;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class Step01_CipherTextXORIsSameAsPlainTextXOR {
    public static void main(String[] args) {
        byte[] p1 = "hello world!!".getBytes(StandardCharsets.UTF_8);
        byte[] p2 = "hello world@@".getBytes(StandardCharsets.UTF_8);
        byte[] k  = "ZZ".getBytes(StandardCharsets.UTF_8);

        byte[] c1 = CipherUtil.xorByteArrs(p1, k);
        byte[] c2 = CipherUtil.xorByteArrs(p2, k);

        byte[] chs = CipherUtil.xorByteArrs(c1, c2);     // c1 ⊕ c2
        byte[] phs = CipherUtil.xorByteArrs(p1, p2);     // p1 ⊕ p2

        System.out.println("c1: " + Arrays.toString(c1));
        System.out.println("c2: " + Arrays.toString(c2));
        System.out.println("chs: " + Arrays.toString(chs));
        System.out.println("phs: " + Arrays.toString(phs));
        System.out.println("chs=phs" + Arrays.toString(chs).equals(Arrays.toString(phs)));
    }

    static void 공통된_부분_확인() {
        byte[] p1 = "hello world!!".getBytes(StandardCharsets.UTF_8);
        byte[] p2 = "hello world@@".getBytes(StandardCharsets.UTF_8);

        byte[] p3 = "hello world11".getBytes(StandardCharsets.UTF_8);
        byte[] p4 = "hello world22".getBytes(StandardCharsets.UTF_8);

        byte[] k  = "ZZ".getBytes(StandardCharsets.UTF_8);

        byte[] c1 = CipherUtil.xorByteArrs(p1, k);
        byte[] c2 = CipherUtil.xorByteArrs(p2, k);

        byte[] c3 = CipherUtil.xorByteArrs(p3, k);
        byte[] c4 = CipherUtil.xorByteArrs(p4, k);

        byte[] r1hs = CipherUtil.xorByteArrs(c1, c2);     // c1 ⊕ c2
        byte[] r2hs = CipherUtil.xorByteArrs(c3, c4);     // c3 ⊕ c4

        System.out.println("r1hs(b64): " + Base64.getEncoder().encodeToString(r1hs));
        System.out.println("r2hs(b64): " + Base64.getEncoder().encodeToString(r2hs));
    }
}
