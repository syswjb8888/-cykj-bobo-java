package com.cykj.pos.util;

import io.lettuce.core.ScriptOutputType;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.UUID;

public class DESHelperUtil {
    /**
     * 偏移变量，固定占8位字节
     */
    private final static String IV_PARAMETER = "12345678";
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "DES";
    /**
     * 加密/解密算法-工作模式-填充模式
     */
    private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";
    /**
     * 默认编码
     */
    private static final String CHARSET = "UTF-8";

    /**
     * 生成key
     *
     * @param password
     * @return
     * @throws Exception
     */
    private static Key generateKey(String password) throws Exception {
        DESKeySpec dks = new DESKeySpec(password.getBytes(CHARSET));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        return keyFactory.generateSecret(dks);
    }


    /**
     * DES加密字符串
     *
     * @param password 加密密码，长度不能够小于8位
     * @param data 待加密字符串
     * @return 加密后内容
     */
    public static String encrypt(String password, String data) {
        if (data == null)
            return null;
        try {
            Key secretKey = generateKey(password);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(CHARSET));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] bytes = cipher.doFinal(data.getBytes(CHARSET));

            //JDK1.8及以上可直接使用Base64，JDK1.7及以下可以使用BASE64Encoder
            //Android平台可以使用android.util.Base64
            return new String(java.util.Base64.getEncoder().encode(bytes));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * DES解密字符串
     *
     * @param password 解密密码，长度不能够小于8位
     * @param data 待解密字符串
     * @return 解密后内容
     */
    public static String decrypt(String password, String data) {
        if (data == null)
            return null;
        try {
            Key secretKey = generateKey(password);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(CHARSET));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            return new String(cipher.doFinal(Base64.getDecoder().decode(data.getBytes(CHARSET))), CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获得加密数据的秘钥
     * @return
     */
    public static String getSecretKey(){
        return UUID.randomUUID().toString().replace("-","");
    }
    public static void main(String[] args) {
        String money = "36688.00";
        String money1 = "26688.00"; // 结算款账户
        String money2 = "10000.00"; // 奖励
        /*System.out.println(encrypt("cf7442e3910d4a80a3839f940b86fd95",money1));
        System.out.println(encrypt("cf7442e3910d4a80a3839f940b86fd95",money2));*/
        //7278ec2e85484ce49aa23fdb1637f3ef
        //99.00  nxyzfvZX0eA=
        //0.00   iLpHF4qYK6Q=
        //DecimalFormat df1 = new DecimalFormat("#.00");

       /* BigDecimal decimal = new BigDecimal(0.00);
        BigDecimal d = decimal.setScale(2,BigDecimal.ROUND_DOWN); //ROUND_HALF_UP 四射无人*/
        String secretBalance = encrypt("7278ec2e85484ce49aa23fdb1637f3ef",0+"");
        System.out.println(secretBalance);  //nxyzfvZX0eA=
        // 加密
       /* for(int i=0;i<10;i++){
            // 获得秘钥
            String secretKey = getSecretKey();
            // 获得加密的信息
            String secretBalance = encrypt(secreKey,money);
            System.out.println("secreKey="+secreKey+",secretBalance="+secretBalance);
        }*/
        /**
         secreKey=cf7442e3910d4a80a3839f940b86fd95,secretBalance=supyB3F8L0U0mwjGAza0Cw==
         secreKey=475ed9c809ae46d38dde2204ade0a314,secretBalance=0HxNUktThCg7o39pIrzu3A==
         secreKey=50984681b4564acda8f66d7d8dfa1b31,secretBalance=SzN3Bm+LTDQchAbsPWB3Bw==
         secreKey=6b472248336d40408c81f9cae20a0e46,secretBalance=mKwQL9UIJQRLurKanfrESA==
         secreKey=fa2a1f0b717f45c686ce40eb104d12a0,secretBalance=ZTZEJeqFHw5/BLDGp9X0Kw==
         secreKey=ba0370294aaf4656a548709327900b50,secretBalance=aQUBkIlI38hpdbb0fAZEdg==
         secreKey=0be954cfefb24b62a401bb62ab108bcc,secretBalance=b/u90U5Hqq4aHfWqgnf5/Q==
         secreKey=76c7a84bcb984072a9558984b53733b1,secretBalance=Pu9itLw6nMFxfV38rIXOKQ==
         secreKey=e173686b7bc140ecbe2b83ec55c8eb72,secretBalance=y38qvUARK/Zf+/9zHUIxmw==
         secreKey=0325f17f4f094c04a2e157a343651b63,secretBalance=2WPJW/8uaD6Fk+GQO2DpjQ==

         6R5Zkcjt5eQUZXth5rSSAg==
         u+DISe2EF5JrOqSS3s/VTw==
         */
        // 解密
        /*String str1 = decrypt("7278ec2e85484ce49aa23fdb1637f3ef","1QAxtA5O7cw=");
        System.out.println("str1="+str1);*/
        //System.out.println(UUID.randomUUID().toString().replace("-","").length());

        System.out.println("====加密：======");
        String encrption = DESHelperUtil.encrypt("7278ec2e85484ce49aa23fdb1637f3ef", "10000");
        System.out.println(encrption);
        //0         1QAxtA5O7cw=
        // 0.00     iLpHF4qYK6Q=
        // 10000    +riFIVwCACM=
        System.out.println("====解密：======");
        System.out.println("==========");
        System.out.println(DESHelperUtil.decrypt("7278ec2e85484ce49aa23fdb1637f3ef", "+riFIVwCACM="));
        System.out.println("==========");
    }
}
