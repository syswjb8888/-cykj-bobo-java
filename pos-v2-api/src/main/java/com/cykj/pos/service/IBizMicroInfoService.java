package com.cykj.pos.service;

import com.cykj.pos.domain.BizMicroInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 报件小微商户信息Service接口
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
public interface IBizMicroInfoService extends IService<BizMicroInfo> {

    /**
     * 查询列表
     */
    List<BizMicroInfo> queryList(BizMicroInfo bizMicroInfo);

    /**
     * 通过商户id获取小微商户附加信息
     * @param merchId
     * @return
     */
    public BizMicroInfo getBizMicroInfoByMerchId(Long merchId);

    /**
     * 身份证号是否存在
     * @param card
     * @return
     */
    boolean idCordIsExist(String card);
}
