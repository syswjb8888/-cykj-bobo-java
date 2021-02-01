package com.cykj.pos.service;

import com.cykj.pos.domain.BizEnterpriseInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 企业商户报件信息Service接口
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
public interface IBizEnterpriseInfoService extends IService<BizEnterpriseInfo> {

    /**
     * 查询列表
     */
    List<BizEnterpriseInfo> queryList(BizEnterpriseInfo bizEnterpriseInfo);
}
