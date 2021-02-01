package com.cykj.pos.service;

import com.cykj.pos.domain.BizProfitSettle;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 分润结算信息Service接口
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
public interface IBizProfitSettleService extends IService<BizProfitSettle> {

    /**
     * 查询列表
     */
    List<BizProfitSettle> queryList(BizProfitSettle bizProfitSettle);
}
