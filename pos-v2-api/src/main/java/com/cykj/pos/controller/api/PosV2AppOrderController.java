package com.cykj.pos.controller.api;

import com.cykj.common.core.domain.AjaxResult;
import com.cykj.pos.domain.BizMerchOrder;
import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.domain.BizWallet;
import com.cykj.pos.domain.dto.IntegralDTO;
import com.cykj.pos.domain.dto.IntegralDetailDTO;
import com.cykj.pos.domain.dto.OrderDTO;
import com.cykj.pos.service.IBizMerchIntegralService;
import com.cykj.pos.service.IBizMerchOrderService;
import com.cykj.pos.service.IBizMerchantService;
import com.cykj.pos.service.IBizWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 *积分管理Controller
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/pos/api/v2/order")
public class PosV2AppOrderController {
    private final IBizMerchOrderService merchOrderService;
    private final IBizMerchantService merchantService;


    /**
     * 我的积分首页  获得积分  传递userId  用户id
     * @param orderDTO
     * @return
     */
    @PostMapping("/list")
    public AjaxResult list(@RequestBody OrderDTO orderDTO){
        AjaxResult ajaxResult = AjaxResult.success("我的订单兑换列表获取成功");
        List<BizMerchOrder> merchOrderList = null;
        if(orderDTO.getOrderType()==1){ //查询我的兑换
            merchOrderList = merchOrderService.getOrderListByMerchId(orderDTO); // 根据商户查询订单
        }else if(orderDTO.getOrderType()==2){ // 兑换申请
            merchOrderList = merchOrderService.getOrderListByParentId(orderDTO);// 根据被申请伙伴查询订单
        }
        ajaxResult.put("data",merchOrderList);
        return ajaxResult;
    }
    @PostMapping("/detail")
    public AjaxResult detail(@RequestBody OrderDTO orderDTO){
        AjaxResult ajaxResult = AjaxResult.success("我的订单详情获取成功");
        BizMerchOrder merchOrder = null;
        if(orderDTO.getOrderType()==1){ //查询我的兑换
            merchOrder = merchOrderService.getOrdertById(orderDTO); // 根据商户id查询订单
        }else if(orderDTO.getOrderType()==2){ // 兑换申请
            merchOrder = merchOrderService.getOrdertByParentId(orderDTO);// 根据被申请伙伴id查询订单
        }
        ajaxResult.put("data",merchOrder);
        return ajaxResult;
    }
}
