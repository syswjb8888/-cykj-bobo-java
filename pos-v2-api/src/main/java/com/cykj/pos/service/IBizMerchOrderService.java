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
}
