package com.cykj.pos.service;

import com.cykj.pos.domain.BizMerchIntegral;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cykj.pos.domain.dto.IntegralDTO;
import com.cykj.pos.domain.dto.IntegralDetailDTO;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据条件查询积分明细
     * @param integralDTO
     * @return
     */
    List getIntegralList(IntegralDTO integralDTO);
}
