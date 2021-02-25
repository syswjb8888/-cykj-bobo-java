package com.cykj.pos.mapper;

import com.cykj.pos.domain.BizMerchOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cykj.pos.domain.dto.OrderDTO;

import java.util.List;

/**
 * 订单Mapper接口
 *
 * @author weijianbo
 * @date 2021-02-24
 */
public interface BizMerchOrderMapper extends BaseMapper<BizMerchOrder> {
    /**
     * 根据商户id查询订单列表
     * @param orderDTO
     * @return
     */
    List<BizMerchOrder> selectOrderListByMerchId(OrderDTO orderDTO);

    /**
     * 根据申请商户id查询订单列表
     * @param orderDTO
     * @return
     */
    List<BizMerchOrder> selectOrderListByParentId(OrderDTO orderDTO);

    /**
     * 根据订单id查询订单
     * @param orderDTO
     * @return
     */
    BizMerchOrder selectOrdertById(OrderDTO orderDTO);
    /**
     * 根据直接伙伴id查询订单
     * @param orderDTO
     * @return
     */
    BizMerchOrder selectOrdertByParentId(OrderDTO orderDTO);
}
