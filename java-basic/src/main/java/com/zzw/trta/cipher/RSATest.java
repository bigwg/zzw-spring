package com.zzw.trta.cipher;

import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSATest {
    private static String ALGORITHM = "RSA";
    public static String SIGN_ALGORITHMS = "SHA1WithRSA";
    private static String CHAR_SET = "UTF-8";
    private static String pb_str = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbCKjMo5L125bsfzt2sZZvPwyfZnjII5YLUmqWXTjQfJd9jmSi3k+k/MapQATZG68nqqCwdWFlFreDLTJG9MZ6B85LCxL+/5hixFzjsqL49o/Y2cO0TtFVDKYT7n/NWMeVwzgrTC8+8BVqO/LXwbQaKPh0awOSDRhGj/qqB8dCFQIDAQAB";
    private static String pr_str = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJsIqMyjkvXblux/O3axlm8/DJ9meMgjlgtSapZdONB8l32OZKLeT6T8xqlABNkbryeqoLB1YWUWt4MtMkb0xnoHzksLEv7/mGLEXOOyovj2j9jZw7RO0VUMphPuf81Yx5XDOCtMLz7wFWo78tfBtBoo+HRrA5INGEaP+qoHx0IVAgMBAAECgYEAgil4By/CX/a4NODxnMqmwcnOoDnZaDcwb1sHsCyeWbRxP/IAHhnGBoBPjqP4LgunOAdymBRE7Clxc4nLr3Us+NL0icGSmkFU9pwHma4dJetvZynnG2yu0XEKVRkjFmLyb2fCbR7F7tZX7GS8BoXEd/+s8ooCBjK1B4Q3TnSUvIECQQDHoESrUdA7Niyb0X5j8D/xrYew39CGgrUWUW9wIjNxzdLApIQ1CIHxqNJVzoxxcxudBtqhZn3b+MlgamJTIvZhAkEAxtCmCDpuI/czuG868TIVNSc85D6WBkdUOSSc9XiohBJNdhLdWR7LHiuEx0UTQVoFkRb7vlxpU498P938FTJANQJAXohj3GlK3a9i4osLelIcRdtyRW1Ht1sQXDhGOGjJydu5MOb3gJG9z22t5VqBoXvsGKlVQVdixJeE7XaTh2EoIQJATzt7z6+pkT/FJu19Kf5xoTs/8x5WH65xjExhGRq63deFc1bVS90PybhCND/9CoBb05S7QwQFHb4DIFjRS4k5OQJAY8ZxyjkICgyLt/ocRIZza6V0671zVrzGJ6N7i6urgRTS+ffchW38E3ArAE78KKM0KwCuCRn/UhbnMRTpQQR2dQ==";

    public static void main(String[] args) throws NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException {
        String content = "{\"version\":\"1.0.0\",\"page_no\":1,\"page_size\":200,\"start_time\":\"2016-08-24 12:01:01\"}";
        String estr = encrypt(content, pr_str);
        System.out.println("私钥加密：" + estr);
        System.out.println("公钥解密：" + decrypt(estr, pb_str));

    }

    public static String decrypt(String content, String publicKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (null != publicKey && !"".equals(publicKey)) {
            PublicKey pk = getPublicKey(publicKey);
            byte[] data = decryptByPublicKey(content, pk);
            String res = new String(data, CHAR_SET);
            return res;
        } else {
            return null;
        }
    }

    public static String encrypt(String content, String key) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        PrivateKey pk = getPrivateKey(key);
        byte[] data = encryptByPrivateKey(content, pk);
        String res = null;
        res = Base64.getEncoder().encodeToString(data);
        return res;
    }

    private static PrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey privatekey = keyFactory.generatePrivate(keySpec);
        return privatekey;
    }

    private static PublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey publickey = keyFactory.generatePublic(keySpec);
        return publickey;
    }

    private static byte[] decryptByPublicKey(String content, PublicKey pk) {
        InputStream ins = null;
        ByteArrayOutputStream writer = null;

        try {
            Cipher ch = Cipher.getInstance(ALGORITHM);
            ch.init(2, pk);
            ins = new ByteArrayInputStream(Base64.getDecoder().decode(content));
            writer = new ByteArrayOutputStream();

            int bufl;
            byte[] block;
            for (byte[] buf = new byte[128]; (bufl = ins.read(buf)) != -1; writer.write(ch.doFinal(block))) {
                if (buf.length == bufl) {
                    block = buf;
                } else {
                    block = new byte[bufl];

                    for (int i = 0; i < bufl; ++i) {
                        block[i] = buf[i];
                    }
                }
            }

            block = writer.toByteArray();
            return block;
        } catch (Exception var21) {
            var21.printStackTrace();
        } finally {
            try {
                ins.close();
            } catch (IOException var20) {
                var20.printStackTrace();
            }

            try {
                writer.close();
            } catch (IOException var19) {
                var19.printStackTrace();
            }

        }

        return null;
    }

    private static byte[] encryptByPrivateKey(String content, PrivateKey pk) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        Cipher ch = Cipher.getInstance(ALGORITHM);
        ch.init(1, pk);
        byte[] contentBytes = content.getBytes(CHAR_SET);
        byte[] enBytes = null;
        for (int i = 0; i < contentBytes.length; i += 64) {
            byte[] doFinal = ch.doFinal(ArrayUtils.subarray(contentBytes, i, i + 64));
            enBytes = ArrayUtils.addAll(enBytes, doFinal);
        }
        return enBytes;
    }

    private static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String s = Base64.getEncoder().encodeToString(keyBytes);
        return s;
    }

    public static String createKey() throws Exception {
        String res = null;
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        pb_str = getKeyString(publicKey);
        System.out.println("public:\n" + pb_str);
        pr_str = getKeyString(privateKey);
        System.out.println("private:\n" + pr_str);
        return (String) res;
    }

    public static String sign(String data, String privateKey, String sign_algorithms) throws Exception {
        PrivateKey privateKey2 = getPrivateKey(privateKey);
        Signature signature = Signature.getInstance(sign_algorithms);
        signature.initSign(privateKey2);
        signature.update(data.getBytes());
        String s = Base64.getEncoder().encodeToString(signature.sign());
        return s;
    }

    public static boolean verify(String data, String publicKey, String sign_algorithms, String signatureResult) throws Exception {
        PublicKey publicKey2 = getPublicKey(publicKey);
        Signature signature = Signature.getInstance(sign_algorithms);
        signature.initVerify(publicKey2);
        signature.update(data.getBytes());
        byte[] sign = Base64.getDecoder().decode(signatureResult);
        return signature.verify(sign);
    }
}
