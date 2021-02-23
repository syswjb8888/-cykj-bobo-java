package com.cykj.pos.mapper;

import com.cykj.pos.domain.BizMerchIntegral;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cykj.pos.domain.dto.IntegralDTO;
import com.cykj.pos.domain.dto.IntegralDetailDTO;

import java.util.List;
import java.util.Map;

/**
 * 积分管理Mapper接口
 *
 * @author weijianbo
 * @date 2021-02-19
 */
public interface BizMerchIntegralMapper extends BaseMapper<BizMerchIntegral> {
    /**
     * 查看积分详情
     *
     * @param integralDTO
     * @return
     */
    List<IntegralDetailDTO> selectIntegralList(IntegralDTO integralDTO);

    /**
     * 获取年+月份
     * @param integralDTO
     * @return
     */
    List<String> selectMonthListByUserIdAndTranType(IntegralDTO integralDTO);
}
