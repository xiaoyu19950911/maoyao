package com.qjd.rry.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.ClassUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;


@Slf4j
public class WebhooksVerifyUtil {

    /**
     * Ping++ 公钥
     */
    /*@Value("${pay.pubKeyPath}")
    String pubKeyPath;*/

    private static String pubKeyPath = "pingpp_public_key.pem";

   /* private static final String pubKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0ja4BrZpK7eAT+ZWLGcw\n" +
            "J3pd8yk3R9lM2uc7fw4BBW19mDoBOrxPkFdr63sSwQdGBHeldctyvY2W5uwLe7ks\n" +
            "4OrNY3wqzeqkeYrrBoVt17uHStpY1ssRfmSuL6XRD4fAXmw5FBKiAllyncXLPM6q\n" +
            "0bt8HHxwcCBSRVk0LAyI9w1RhAh/oK29hDmflKrQMMj+0A+sHCREpceOV+laFxmk\n" +
            "yMyh3w927n89rnP4sAcqMo+62zevci8Wla6KfMzMN1FpCnyR+cmHuEvrgSq+Hp/R\n" +
            "pRX6TkyCM640ERiei3Z/Ct3BwHKAyiz30JbDHFjP+KEB4fb9E9wiXaJTv4xGWxlo\n" +
            "MQIDAQAB";*/

    /**
     * 读取文件, 部署 web 程序的时候, 签名和验签内容需要从 request 中获得
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String getStringFromFile(String filePath) throws Exception {
        log.info("公钥路径为：{}", filePath);
        FileInputStream in = new FileInputStream(filePath);
        InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
        BufferedReader bf = new BufferedReader(inReader);
        StringBuilder sb = new StringBuilder();
        String line;
        do {
            line = bf.readLine();
            if (line != null) {
                if (sb.length() != 0) {
                    sb.append("\n");
                }
                sb.append(line);
            }
        } while (line != null);

        return sb.toString();
    }

    /**
     * 获得公钥
     *
     * @return
     * @throws Exception
     */
    public static PublicKey getPubKey() throws Exception {
        String pubKeyString = getStringFromFile( ClassUtils.getDefaultClassLoader().getResource(pubKeyPath).getPath());
        //String pubKeyString =pubKey;
                pubKeyString = pubKeyString.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
        byte[] keyBytes = Base64.decodeBase64(pubKeyString);

        // generate public key
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(spec);
        return publicKey;
    }

    /**
     * 验证签名
     *
     * @param dataString
     * @param signatureString
     * @param publicKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verifyData(String dataString, String signatureString, PublicKey publicKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
        byte[] signatureBytes = Base64.decodeBase64(signatureString);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(dataString.getBytes("UTF-8"));
        return signature.verify(signatureBytes);
    }
}
