package com.cykj.pos.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cykj.common.core.domain.AjaxResult;
import com.cykj.common.core.domain.entity.SysDictData;
import com.cykj.common.core.domain.entity.SysUser;
import com.cykj.common.utils.SecurityUtils;
import com.cykj.pos.domain.BizMerchOrder;
import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.domain.BizMicroInfo;
import com.cykj.pos.domain.BizWallet;
import com.cykj.pos.domain.dto.BillQueryDTO;
import com.cykj.pos.domain.dto.IntegralDTO;
import com.cykj.pos.domain.dto.IntegralDetailDTO;
import com.cykj.pos.domain.dto.OrderDTO;
import com.cykj.pos.enums.bizstatus.BizStatusContantEnum;
import com.cykj.pos.profit.dto.WalletDTO;
import com.cykj.pos.service.*;
import com.cykj.pos.util.LoginUserUtils;
import com.cykj.system.service.ISysDictDataService;
import com.cykj.system.service.ISysUserService;
import io.swagger.annotations.*;
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
@RequestMapping("/pos/api/v2/integral")
public class PosV2AppIntegralController {
    private final IBizWalletService walletService;
    private final IBizMerchIntegralService integralService;
    private final IBizMerchantService merchantService;
    private final IBizMerchOrderService merchOrderService;

    /**
     * 我的积分首页  获得积分  传递userId  用户id
     * @param integralDTO
     * @return
     */
    @PostMapping("/homepage")
    public AjaxResult integralHomePage(@RequestBody IntegralDTO integralDTO){
        AjaxResult ajaxResult = AjaxResult.success("我的积分首页数据获取成功");
        BizWallet wallet = walletService.getMyWalletByUserId(integralDTO.getUserId());
        IntegralDTO integral = new IntegralDTO();
        integral.setIntegral(wallet.getIntegral()); // 设置通用积分
        integral.setActivityIntegral(wallet.getActivityIntegral());// 设置活动积分
        integral.setSecretKey(wallet.getSecretKey()); // 秘钥
        ajaxResult.put("data",integral);
        return ajaxResult;
    }

    /**
     * 获得积分列表  传入 用户id  积分类型（transType 不填入就是全部 收入1 支出 2）
     * @param integralDTO
     * @return
     */
    @PostMapping("/list")
    public AjaxResult list(@RequestBody IntegralDTO integralDTO){
        AjaxResult ajaxResult = AjaxResult.success("我的积分明细数据列表获取成功");
        List<Map<String, IntegralDetailDTO>> integralList = integralService.getIntegralList(integralDTO);
        ajaxResult.put("data",integralList);
        return ajaxResult;
    }

    /**
     * 积分兑换 选择兑换对象
     * @param integralDTO
     * @return
     */
    @PostMapping("/convert")
    public AjaxResult getParentMerchByMerchId(@RequestBody IntegralDTO integralDTO){
        AjaxResult ajaxResult = AjaxResult.success("我的选择兑换数据获取成功");
        List<BizMerchant> parentMerchList = merchantService.getParentMerchByUserId(integralDTO.getUserId());
        ajaxResult.put("data",parentMerchList);
        return ajaxResult;
    }
    @PostMapping("/order")
    public AjaxResult submitOrder(@RequestBody OrderDTO orderDTO){
        AjaxResult ajaxResult = AjaxResult.success("我的订单提交成功");
        Long userId =  orderDTO.getUserId();
        BizMerchant merchant =  merchantService.getMerchantByUserId(userId);
        orderDTO.setMerchId(merchant.getMerchId());
        BizMerchOrder order = merchOrderService.submitOrder(orderDTO);
        ajaxResult.put("data",order);
        return ajaxResult;
    }

}
