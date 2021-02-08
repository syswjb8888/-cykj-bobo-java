package com.cykj.pos.mapper;

import com.cykj.pos.domain.BizPosMachine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 终端设备信息Mapper接口
 *
 * @author ruoyi
 * @date 2021-01-11
 */
public interface BizPosMachineMapper extends BaseMapper<BizPosMachine> {
    /**
     * 根据伙伴信息查询机器总量
     * @param merchantId
     * @return
     */
    Integer getPosMachineAllCountsByMerchId(Long merchantId);
}
