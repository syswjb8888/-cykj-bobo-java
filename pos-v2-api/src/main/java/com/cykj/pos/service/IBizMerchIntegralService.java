package com.cykj.pos.service;

import com.cykj.pos.domain.BizMerchIntegral;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 积分管理Service接口
 *
 * @author weijianbo
 * @date 2021-02-19
 */
public interface IBizMerchIntegralService extends IService<BizMerchIntegral> {

    /**
     * 查询列表
     */
    List<BizMerchIntegral> queryList(BizMerchIntegral bizMerchIntegral);
}
