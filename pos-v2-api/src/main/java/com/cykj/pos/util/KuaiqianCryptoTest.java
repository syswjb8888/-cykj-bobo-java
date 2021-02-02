package com.cykj.pos.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.util.encoders.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;

public class KuaiqianCryptoTest {
    /**
     * 生产环境
     */
    public static final String ENV_PRODUCTION = "production";

    /**
     * 测试环境
     */
    public static final String ENV_TEST = "test";

    /*public static void main(String[] args) throws Exception {
        // 环境配置
        String envName = ENV_PRODUCTION;
        // 加载数据
        File dataFile = new File(KuaiqianCryptoTest.class.getResource("/").toURI().getPath() + envName + "_data.txt");
        JSONObject jsonData = JSONObject.parseObject(IOUtils.toString(new FileInputStream(dataFile)));
        String sign = jsonData.getString("sign");
        String data = jsonData.getString("data");

        // 初始化
        PkiUtil.init(envName);
        // 验签
        try {
            boolean verify = PkiUtil.verifyDetachedSigned(Base64.decode(sign.replaceAll("\\s", "")), data.getBytes(Charset.forName("UTF-8")));
            if (verify) {
                System.out.println("验签成功");
            } else {
                System.out.println("验签失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("验签失败");
        }
    }*/
}
