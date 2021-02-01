package com.cykj.pos.service;

public interface IDataSecurityService {
    /**
     * 根据登录用户创建接口需要的请求凭证ticket
     * @param loginName
     * @param timeout
     * @return
     */
    public  String createTicket(String loginName,Integer timeout);

    /**
     * 验签
     * @param sign 签名串
     * @param data 数据json串
     * @return
     */
    public boolean dataVerfiy(String sign,String data);
}
