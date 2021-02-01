package com.cykj.pos.service;

import com.cykj.pos.domain.BizProfitRule;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 分润规则制订Service接口
 *
 * @author ningbingwu
 * @date 2021-01-08
 */
public interface IBizProfitRuleService extends IService<BizProfitRule> {

    /**
     * 查询列表
     */
    List<BizProfitRule> queryList(BizProfitRule bizProfitRule);
}
