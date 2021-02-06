package com.cykj.pos.mapper;

import com.cykj.pos.domain.BizMerchBill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cykj.pos.domain.dto.BillQueryDTO;

import java.util.List;

/**
 * 账单信息Mapper接口
 *
 * @author weijianbo
 * @date 2021-02-06
 */
public interface BizMerchBillMapper extends BaseMapper<BizMerchBill> {
    /**
     * 根据条件获得账单信息列表
     * @param billQueryDTO
     * @return
     */
    List<BillQueryDTO> getPageBillListByMerchId(BillQueryDTO billQueryDTO);
}
