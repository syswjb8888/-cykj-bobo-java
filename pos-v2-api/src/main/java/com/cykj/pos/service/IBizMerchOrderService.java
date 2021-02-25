package com.cykj.pos.service;

import com.cykj.pos.domain.BizMerchOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.domain.dto.OrderDTO;

import java.util.List;

/**
 * 订单Service接口
 *
 * @author weijianbo
 * @date 2021-02-24
 */
public interface IBizMerchOrderService extends IService<BizMerchOrder> {

    /**
     * 查询列表
     */
    List<BizMerchOrder> queryList(BizMerchOrder bizMerchOrder);

    /**
     * 提交订单
     * @param orderDTO
     * @return
     */
    BizMerchOrder submitOrder(OrderDTO orderDTO);

    /**
     * 根据商户查询订单列表
     * @param orderDTO
     * @return
     */
    List<BizMerchOrder> getOrderListByMerchId(OrderDTO orderDTO);

    /**
     * 根据申请商户查询订单列表
     * @param orderDTO
     * @return
     */
    List<BizMerchOrder> getOrderListByParentId(OrderDTO orderDTO);

    /**
     * 根据订单id查询订单
     * @param orderDTO
     * @return
     */
    BizMerchOrder getOrdertById(OrderDTO orderDTO);
    /**
     * 根据伙伴id查询订单
     * @param orderDTO
     * @return
     */
    BizMerchOrder getOrdertByParentId(OrderDTO orderDTO);
}
