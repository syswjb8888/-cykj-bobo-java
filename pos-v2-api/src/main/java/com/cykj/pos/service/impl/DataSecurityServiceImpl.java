package com.cykj.pos.service.impl;

import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import com.cykj.common.config.RuoYiConfig;
import com.cykj.common.core.redis.RedisCache;
import com.cykj.common.utils.uuid.UUID;
import com.cykj.pos.service.IDataSecurityService;
import com.cykj.pos.util.PkiUtil;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSecurityServiceImpl implements IDataSecurityService {
    @Autowired
	private RedisCache redisCache;

    @Autowired
	private RuoYiConfig config;

    @Override
	public  String createTicket(String loginName,Integer timeout){
		String uuid = UUID.randomUUID().toString();
		String ticket = uuid.replace("-", "");
		redisCache.setCacheObject(ticket, loginName, timeout, TimeUnit.MINUTES);
		return ticket;
	}

	@Override
	public boolean dataVerfiy(String sign,String data){
		PkiUtil.init(config.getSignenv());
		try {
			return PkiUtil.verifyDetachedSigned(Base64.decode(sign.replaceAll("\\s", "")), data.getBytes(Charset.forName("UTF-8")));
		} catch (CMSException e) {
			e.printStackTrace();
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		return false;
	}
}
