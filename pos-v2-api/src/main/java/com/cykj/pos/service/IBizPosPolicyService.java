package com.cykj.pos.service;

import com.cykj.pos.domain.BizPosPolicy;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * 政策管理Service接口
 *
 * @author weijianbo
 * @date 2021-02-18
 */
public interface IBizPosPolicyService extends IService<BizPosPolicy> {

    /**
     * 查询列表
     */
    List<BizPosPolicy> queryList(BizPosPolicy bizPosPolicy);
}
