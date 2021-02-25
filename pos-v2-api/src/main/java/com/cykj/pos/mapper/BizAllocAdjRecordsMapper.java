package com.cykj.pos.mapper;

import com.cykj.pos.domain.BizAllocAdjRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 划拔回调记录Mapper接口
 *
 * @author ningbingwu
 * @date 2021-01-15
 */
public interface BizAllocAdjRecordsMapper extends BaseMapper<BizAllocAdjRecords> {

    List<String> selectAdjRecordsListByOrderId(Long orderId);
}
