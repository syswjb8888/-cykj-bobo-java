package com.cykj.pos.service.impl;

import com.cykj.pos.domain.*;
import com.cykj.pos.domain.dto.OrderDTO;
import com.cykj.pos.service.IBizMerchIntegralService;
import com.cykj.pos.service.IBizMerchantService;
import com.cykj.pos.service.IBizWalletService;
import com.cykj.pos.util.BigDecimalUtil;
import com.cykj.pos.util.DESHelperUtil;
import com.cykj.pos.util.DateUtils;
import com.cykj.pos.util.OrderUtil;
import com.cykj.pos.websocket.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.cykj.pos.mapper.BizMerchOrderMapper;
import com.cykj.pos.service.IBizMerchOrderService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单Service业务层处理
 *
 * @author weijianbo
 * @date 2021-02-24
 */
@Service
public class BizMerchOrderServiceImpl extends ServiceImpl<BizMerchOrderMapper, BizMerchOrder> implements IBizMerchOrderService {

    @Autowired
    IBizMerchantService merchantService;
    @Autowired
    private WebSocketServer webSocketServer;
    @Autowired
    IBizMerchIntegralService merchIntegralService;
    @Autowired
    IBizWalletService walletService;
    @Autowired
    BizMerchOrderMapper merchOrderMapper;

    @Override
    public List<BizMerchOrder> queryList(BizMerchOrder bizMerchOrder) {
        LambdaQueryWrapper<BizMerchOrder> lqw = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(bizMerchOrder.getOrderNo())) {
            lqw.eq(BizMerchOrder::getOrderNo, bizMerchOrder.getOrderNo());
        }
        if (bizMerchOrder.getMerchId() != null) {
            lqw.eq(BizMerchOrder::getMerchId, bizMerchOrder.getMerchId());
        }
        if (bizMerchOrder.getProductId() != null) {
            lqw.eq(BizMerchOrder::getProductId, bizMerchOrder.getProductId());
        }
        if (bizMerchOrder.getNum() != null) {
            lqw.eq(BizMerchOrder::getNum, bizMerchOrder.getNum());
        }
        if (bizMerchOrder.getMoney() != null) {
            lqw.eq(BizMerchOrder::getMoney, bizMerchOrder.getMoney());
        }
        if (bizMerchOrder.getParentId() != null) {
            lqw.eq(BizMerchOrder::getParentId, bizMerchOrder.getParentId());
        }
        if (StringUtils.isNotBlank(bizMerchOrder.getParentName())) {
            lqw.like(BizMerchOrder::getParentName, bizMerchOrder.getParentName());
        }
        if (bizMerchOrder.getStatus() != null) {
            lqw.eq(BizMerchOrder::getStatus, bizMerchOrder.getStatus());
        }
        if (bizMerchOrder.getType() != null) {
            lqw.eq(BizMerchOrder::getType, bizMerchOrder.getType());
        }
        if (bizMerchOrder.getLogisticsId() != null) {
            lqw.eq(BizMerchOrder::getLogisticsId, bizMerchOrder.getLogisticsId());
        }
        return this.list(lqw);
    }

    @Override
    @Transactional
    public BizMerchOrder submitOrder(OrderDTO orderDTO) {
        BizMerchant merchant = merchantService.getMerchantByMerchId(orderDTO.getParentId()); // 划拨商户的信息
        String parentName = merchant.getMerchName();
        // 获得订单编号
        String orderNo = OrderUtil.randomOrderNo();
        BizMerchOrder merchOrder = new BizMerchOrder();
        merchOrder.setOrderNo(orderNo);// 订单号
        merchOrder.setMerchId(orderDTO.getMerchId());// 商户id
        merchOrder.setProductId(1L);// 商品id
        merchOrder.setNum(orderDTO.getNum()); // 数量
        merchOrder.setMoney(orderDTO.getMoney()); // 积分金额
        merchOrder.setParentId(orderDTO.getParentId());// 划拨商户id
        merchOrder.setParentName(parentName); // 商户名称
        merchOrder.setStatus(0); //订单状态
        merchOrder.setType(1);// 消费类型  1 积分对象   2现金支付
        merchOrder.setDispatchType(orderDTO.getDispatchType());
        // 系统当前时间
        String formatedDate = DateUtils.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss");
        ;
        merchOrder.setCreateTime(formatedDate);
        saveOrUpdate(merchOrder); //保存或者更新
        // 更新钱包中的积分   积分明细表
        //--------------------- 插入积分明细   激活机具--------------------------
        BizMerchIntegral merchIntegral = new BizMerchIntegral();
        merchIntegral.setMerchId(orderDTO.getMerchId());
        merchIntegral.setIntegralType("机具兑换");
        merchIntegral.setValue(-orderDTO.getMoney());
        merchIntegral.setTransType("2");//收入  支出2
        merchIntegral.setOrderId(orderNo); //订单编号
        merchIntegralService.saveOrUpdate(merchIntegral);
        //---------------------  更新钱包   ----------------------------
        // 1-通过user_id获取钱包
        // 获得用户id
        BizMerchant bizMerchant = merchantService.getMerchantByMerchId(orderDTO.getMerchId());
        Long userId = bizMerchant.getUserId();
        BizWallet wallet = walletService.getMyWalletByUserId(userId);
        // 更新数据
        String secIntegral = wallet.getIntegral(); // 通用积分
        String key = wallet.getSecretKey();// 获得key
        // 解密数据
        String integralStr = DESHelperUtil.decrypt(key, secIntegral);
        Long integral = Long.parseLong(integralStr);
        integral = integral - orderDTO.getMoney();// 通用积分
        // 加密
        String integralMoneyStr = DESHelperUtil.encrypt(key, String.valueOf(integral));
        wallet.setIntegral(integralMoneyStr);
        wallet.setSecretKey(key);
        // 保存钱包信息
        walletService.saveOrUpdate(wallet);

        //--------------------- 插入积分明细   兑换申请处理--------------------------
        BizMerchIntegral merchIntegral2 = new BizMerchIntegral();
        merchIntegral2.setMerchId(orderDTO.getParentId()); // 父商户
        merchIntegral2.setIntegralType("兑换申请");
        merchIntegral2.setValue(orderDTO.getMoney());
        merchIntegral2.setTransType("1");//收入  支出2
        merchIntegral2.setOrderId(orderNo); //订单编号
        merchIntegralService.saveOrUpdate(merchIntegral2);
        //---------------------  更新钱包   ----------------------------
        // 1-通过user_id获取钱包
        // 获得用户id
        BizMerchant bizMerchant2 = merchantService.getMerchantByMerchId(orderDTO.getParentId());
        Long userId2 = bizMerchant2.getUserId();
        BizWallet wallet2 = walletService.getMyWalletByUserId(userId2);
        // 更新数据
        String secIntegral2 = wallet2.getIntegral(); // 通用积分
        String key2 = wallet2.getSecretKey();// 获得key
        // 解密数据
        String integralStr2 = DESHelperUtil.decrypt(key2, secIntegral2);
        Long integral2 = Long.parseLong(integralStr2);
        integral2 = integral2 + orderDTO.getMoney();// 通用积分
        // 加密
        String integralMoneyStr2 = DESHelperUtil.encrypt(key2, String.valueOf(integral2));
        wallet2.setIntegral(integralMoneyStr2);
        wallet2.setSecretKey(key2);
        // 保存钱包信息
        walletService.saveOrUpdate(wallet2);

        // 发消息  入库
        // String rukuMess = "您的机具已经下发，共100台机具，请注意查收";
        BizMessageRecords duiHuanMessageRecords = new BizMessageRecords();
        // 发消息   6- 兑换申请
        String duiHuanMess = "您收到了来自" + bizMerchant.getMerchName() + "的机具兑换申请，共" + orderDTO.getNum() + "台机具";
        duiHuanMessageRecords.setMsgContent(duiHuanMess);// 消息内容
        duiHuanMessageRecords.setMsgType(6); // 消息类型  兑换申请
        duiHuanMessageRecords.setReadStatus(0);// 消息未读
        duiHuanMessageRecords.setAdviceType(1); // 业务消息
        webSocketServer.sendInfo(merchant.getUserId(), duiHuanMessageRecords);// 发送消息

        return merchOrder;
    }

    @Override
    public List<BizMerchOrder> getOrderListByMerchId(OrderDTO orderDTO) {

        if(orderDTO.getPageNo()!=null && orderDTO.getPageSize()!=null){
            int pageNo = orderDTO.getPageNo();
            int pageSize = orderDTO.getPageSize();
            int start =  (pageNo-1)*pageSize;
            orderDTO.setStart(start);
        }
        return merchOrderMapper.selectOrderListByMerchId(orderDTO);
    }

    @Override
    public List<BizMerchOrder> getOrderListByParentId(OrderDTO orderDTO) {

        if(orderDTO.getPageNo()!=null && orderDTO.getPageSize()!=null){
            int pageNo = orderDTO.getPageNo();
            int pageSize = orderDTO.getPageSize();
            int start =  (pageNo-1)*pageSize;
            orderDTO.setStart(start);
        }
        return merchOrderMapper.selectOrderListByParentId(orderDTO);
    }

    @Override
    public BizMerchOrder getOrdertById(OrderDTO orderDTO) {
        return merchOrderMapper.selectOrdertById(orderDTO);
    }

    @Override
    public BizMerchOrder getOrdertByParentId(OrderDTO orderDTO) {
        return merchOrderMapper.selectOrdertByParentId(orderDTO);
    }
}
