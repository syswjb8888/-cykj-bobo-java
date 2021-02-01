package com.cykj.pos.util;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class PkiUtil {

    private static final Logger logger = LoggerFactory.getLogger(PkiUtil.class);

    /**
     * 保存证书的目录
     */
    private static String CERT_DIR = "classpath:kuaiqiansdk-crypto/";
    /**
     * 快钱的证书
     */
    private static List<X509Certificate> KQ_CERTS = new LinkedList<X509Certificate>();

    static {
        try {
            if (Security.getProvider("BC") == null) {
                Security.addProvider(new BouncyCastleProvider());
            }
        } catch (Exception e) {
            throw new RuntimeException("Fail to load crypto provider", e);
        }
    }

    /**
     *
     * 加载证书
     * @param profile 环境
     */
    public static void init(String profile) {
        init(CERT_DIR, profile);
    }

    /**
     *
     * 加载证书
     * @param certDir 证书目录
     * @param profile 环境
     */
    public static void init(String certDir, String profile) {
        Date now = new Date();
        String dir = certDir;
        if (certDir == null) {
            dir = CERT_DIR;
        }
        if (!dir.endsWith("/")) {
            dir = dir + "/";
        }
        String kqDirName = certDir + profile;
        String[] kcerFileNames = FileUtil.list(kqDirName, new String[] { "cer", "crt" });
        if ((kcerFileNames == null) || (kcerFileNames.length == 0)) {
            throw new RuntimeException("证书不存在, 目录:" + kqDirName + ", 后缀:cer,crt");
        }

        for (String kfn : kcerFileNames) {
            String path = kqDirName + "/" + kfn;
            logger.info("加载快钱证书{}", path);
            X509Certificate kc = getCertificate(path);
            if (kc.getNotAfter().before(now)) {
                logger.warn("证书{}已于{}过期，建议从目录移除", path, kc.getNotAfter());
            } else {
                KQ_CERTS.add(kc);
            }
        }
        if (KQ_CERTS.isEmpty()) {
            throw new RuntimeException("不存在有效证书");
        }
    }

    /**
     *
     * 验签
     * @param p7Signed 签名
     * @param dataBeSigned 数据
     * @return true：成功 false：失败
     */
    public static boolean verifyDetachedSigned(byte[] p7Signed, byte[] dataBeSigned)
            throws CMSException, OperatorCreationException, CertificateException {
        return verifyDetachedSigned(p7Signed, dataBeSigned, KQ_CERTS);
    }

    /**
    *
    * 验签
    * @param p7Signed 签名
    * @param dataBeSigned 数据
    * @param certs 证书列表
    * @return true：成功 false：失败
    */
    public static boolean verifyDetachedSigned(byte[] p7Signed, byte[] dataBeSigned, List<X509Certificate> certs)
            throws CMSException, OperatorCreationException, CertificateException {
        CMSTypedData typedData = new CMSProcessableByteArray(dataBeSigned);
        CMSSignedData signedData = new CMSSignedData(typedData, p7Signed);
        return doVerifySign(signedData, certs);
    }

    @SuppressWarnings("unchecked")
    private static boolean doVerifySign(CMSSignedData signedData, List<X509Certificate> certs)
            throws OperatorCreationException, CertificateException, CMSException {
        Store<X509CertificateHolder> store = signedData.getCertificates();
        for (SignerInformation signerInfo : signedData.getSignerInfos().getSigners()) {
            Collection<X509CertificateHolder> certCollection = store.getMatches(signerInfo.getSID());
            X509CertificateHolder certHolder = (X509CertificateHolder) certCollection.iterator().next();
            SignerInformationVerifier verifier = new JcaSimpleSignerInfoVerifierBuilder().setProvider("BC")
                    .build(certHolder);
            boolean verified = signerInfo.verify(verifier);
            if (verified) {
                X509Certificate cert = new JcaX509CertificateConverter().setProvider("BC").getCertificate(certHolder);
                for (X509Certificate c : certs) {
                    if (isSame(cert, c)) {
                        return true;
                    }
                }
                logger.error("签名证书不在证书列表中");
            }
        }
        return false;
    }

    private static boolean isSame(X509Certificate a, X509Certificate b) {
        String fa = getFingerprint(a);
        String fb = getFingerprint(b);
        return fa.equals(fb);
    }

    private static String getFingerprint(X509Certificate cert) {
        return Hex.toHexString(calcFingerprint(cert));
    }

    private static byte[] calcFingerprint(X509Certificate cert) {
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA1", "BC");
            mdInst.update(cert.getEncoded());
            return mdInst.digest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static X509Certificate getCertificate(String cerPath) {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
            return (X509Certificate) cf
                    .generateCertificate(new FileInputStream(new File(FileUtil.getUrl(cerPath).toURI())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
