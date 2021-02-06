package com.cykj.pos.test;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

@Component
public class DES{
    public byte[] bytes;

    //生成一个DES密钥
    public static String getKey(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56);
            // 生成一个Key
            SecretKey generateKey = keyGenerator.generateKey();
            // 转变为字节数组
            byte[] encoded = generateKey.getEncoded();
            // 生成密钥字符串
            String encodeHexString = Hex.encodeHexString(encoded);
            return encodeHexString;
        } catch (Exception e) {
            e.printStackTrace();
            return "密钥生成错误.";
        }
    }

    //加密
    public static String encryptor(String str,String Key){
        String s=null;
        try {
            DESKeySpec desKey = new DESKeySpec(Key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE,securekey);    //初始化密码器，用密钥 secretKey 进入加密模式
            byte[] bytes=cipher.doFinal(str.getBytes());   //加密
            s= Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "加密错误.";
        }
        return s;
    }

    //解密
    public static String decryptor(String buff,String Key){
        String s=null;
        try {
            DESKeySpec desKey = new DESKeySpec(Key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE,securekey);
            byte[] responseByte=cipher.doFinal(Base64.decodeBase64(buff));
            s=new String(responseByte);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return "解密错误.";
        }
    }

    public static void main(String[] args) {
        /*String key = getKey();
        System.out.println(key); // b58691ad52c21c34   f45df4d5f7f22a7a
        // 加密
        String str1 = encryptor("123",key);
        System.out.println("str1="+str1);*/
        // 解密
        String str2 = decryptor("0CRMeN66h8Q=","f45df4d5f7f22a88");
        System.out.println("str2="+str2);
    }
}