package com.cykj.pos.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cykj.common.core.domain.AjaxResult;
import com.cykj.common.core.domain.entity.SysDictData;
import com.cykj.common.core.domain.entity.SysUser;
import com.cykj.common.core.domain.model.LoginUser;
import com.cykj.common.utils.SecurityUtils;
import com.cykj.pos.domain.*;
import com.cykj.pos.domain.dto.BillQueryDTO;
import com.cykj.pos.enums.bizstatus.BizStatusContantEnum;
import com.cykj.pos.profit.dto.MicroMerchantDTO;
import com.cykj.pos.profit.dto.PosTerminalDTO;
import com.cykj.pos.profit.dto.WalletDTO;
import com.cykj.pos.profit.service.IMerchantProfitService;
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

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/pos/api/v2/wallet")
public class PosV2AppWalletController {

    private final IBizWalletService iBizWalletService;

    private final ISysUserService sysUserService;

    private final IBizVerifyCodeService verifyCodeService;

    private final IBizMicroInfoService microInfoService;

    private final IBizMerchantService merchantService;

    private final ISysDictDataService sysDictDataService;

    private final IBizMerchBillService merchBillService;

    @ApiOperation(value="我的钱包首页")
    @ApiImplicitParams({@ApiImplicitParam(name="userId",value = "用户主键Id",dataType = "long",required = true,paramType="body")})
    @ApiResponses({@ApiResponse(code=200,response = BizWallet.class,message = "我的钱包首页数据获取成功")})
    @PostMapping("/homepage")
    public AjaxResult walletHomePage(@RequestBody WalletDTO walletDTO){
        AjaxResult ajaxResult = AjaxResult.success("我的钱包首页数据获取成功");
        // 获取用户信息
        Long userId = LoginUserUtils.getLoginUserId();
        // 获取用户信息
        SysUser sysUser =  sysUserService.selectUserById(userId);
        walletDTO.setUserId(userId);
        BizWallet wallet = iBizWalletService.getMyWallet(walletDTO);
        // 设置支付密码
        wallet.setPayPassword(sysUser.getPaymentPassword());
        ajaxResult.put("data",wallet);
        return ajaxResult;
    }

    @ApiOperation(value="我的钱包提现")
    @ApiImplicitParams({@ApiImplicitParam(name="userId",value = "用户主键Id",dataType = "long",required = true,paramType="body"),
            @ApiImplicitParam(name="profitAmount",value = "结算帐户总额",dataType = "double",required = false,paramType="body"),
            @ApiImplicitParam(name="rewardAmount",value = "奖励帐户总额",dataType = "double",required = false,paramType="body"),
            @ApiImplicitParam(name="walletAmount",value = "钱包总额",dataType = "double",required = false,paramType="body")})
    @ApiResponses({@ApiResponse(code=200,response = BizWallet.class,message = "提现成功")})
    @PostMapping("/cashout")
    public AjaxResult walletCashout(@RequestBody WalletDTO walletDTO){
        AjaxResult ajaxResult = AjaxResult.success("提现成功");
        //TODO: 1.调用第三方支付接口，2.成功或失败之后发送通知消息,3.重新查询我的钱包账户信息,4.记录提现交易日志
        //ajaxResult.put("data",iBizWalletService.getMyWallet(walletDTO));
        return ajaxResult;
    }

    @ApiOperation(value="设置支付密码")
    @ApiResponses({@ApiResponse(code=200,response = BizWallet.class,message = "支付密码设置成功")})
    @PostMapping("/paypassword/setting")
    public AjaxResult setWalletPayPassword(@RequestBody WalletDTO walletDTO){
        AjaxResult ajaxResult = AjaxResult.success("支付密码设置成功");
        SysUser user = SecurityUtils.getLoginUser().getUser();
        String mobile = user.getPhonenumber();
        String verifyCode = walletDTO.getVerifyCode();
        BizStatusContantEnum bizStatus = verifyCodeService.verifyCodeValidate(mobile,verifyCode);
        if(bizStatus != BizStatusContantEnum.SMS_SUCCESS){
            return AjaxResult.error(bizStatus.getName());
        }
        LambdaQueryWrapper<BizMicroInfo> lqw = Wrappers.lambdaQuery();
        lqw.likeLeft(BizMicroInfo::getMerchIdcard,walletDTO.getIdCard());
        List<BizMicroInfo> microList = microInfoService.list(lqw);
        if(microList.size() < 0)return AjaxResult.error("身份证件号码后6位有误");

        iBizWalletService.setPayPassword(walletDTO);
        return ajaxResult;
    }

    /**
     * 账单明细接口
     * @param billQueryDTO
     * @return
     */
    @PostMapping("/bill/list")
    public AjaxResult queryTerminalList(@RequestBody BillQueryDTO billQueryDTO) {

        AjaxResult ajaxResult = AjaxResult.success();
        billQueryDTO.setUserId(LoginUserUtils.getLoginUserId());
        // 获得商户信息
        BizMerchant merchant = merchantService.getMerchantByUserId(billQueryDTO.getUserId());
        billQueryDTO.setMerchId(merchant.getMerchId());
        // 获得交易类型字典值
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("merchant_bill_type");
        // 获得字典值
        List<SysDictData> billTypeList =  sysDictDataService.selectDictDataList(sysDictData);
        Long merchantId = merchant.getMerchId();
        List<BillQueryDTO> terminalList =
                merchBillService.getPageBillListByMerchId(billQueryDTO);
        ajaxResult.put("data", terminalList);
        return ajaxResult;
    }

}
