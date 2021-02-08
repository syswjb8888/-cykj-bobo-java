package com.cykj.pos.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cykj.common.config.RuoYiConfig;
import com.cykj.common.core.domain.AjaxResult;
import com.cykj.common.core.domain.entity.SysUser;
import com.cykj.common.core.domain.model.LoginUser;
import com.cykj.common.utils.SecurityUtils;
import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.domain.BizMicroInfo;
import com.cykj.pos.domain.BizPosMachine;
import com.cykj.pos.domain.dto.PaymentPassUpdateDTO;
import com.cykj.pos.domain.dto.UpdatePortraitDTO;
import com.cykj.pos.enums.bizstatus.BizStatusContantEnum;
import com.cykj.pos.profit.dto.*;
import com.cykj.pos.service.IBizMerchantService;
import com.cykj.pos.service.IBizMicroInfoService;
import com.cykj.pos.service.IBizVerifyCodeService;
import com.cykj.pos.util.LoginUserUtils;
import com.cykj.system.service.ISysUserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pos/api/v2/merchant")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PosV2AppMerchantController {

    private final IBizMerchantService merchantService;

    private final IBizVerifyCodeService verifyCodeService;

    private final ISysUserService sysUserService;

    private final IBizMicroInfoService microInfoService;

    private final RuoYiConfig ruoYiConfig;

    @ApiOperation(value="获取商户及子商户列表")
    @ApiResponses({@ApiResponse(code=200,response = BizMerchant.class,message = "业务数据响应成功")})
    @PostMapping("/list")
    public AjaxResult queryTerminalList(@RequestBody MerchantDTO merchantDTO){
        AjaxResult ajaxResult = AjaxResult.success();
        Long userId = LoginUserUtils.getLoginUserId();
        BizMerchant merchant = merchantService.getMerchantByUserId(userId);
        List<BizMerchant> merchantList = merchantService.getAllSubNodeAndIncludeSelf(merchant.getMerchId());
        ajaxResult.put("data",merchantList);
        return  ajaxResult;
    }
    @ApiOperation(value="获取当前商户信息")
    @ApiResponses({@ApiResponse(code=200,response = BizMicroInfo.class,message = "业务数据响应成功")})
    @PostMapping("/current")
    public AjaxResult getCurrentMerchat(@RequestBody BizMerchant merchantBrief){
        AjaxResult ajaxResult = AjaxResult.success();
        Long userId = LoginUserUtils.getLoginUserId();
        BizMerchant merchant = merchantService.getMerchantByUserId(userId);

        LambdaQueryWrapper<BizMicroInfo> lqw = Wrappers.lambdaQuery();
        lqw.eq(BizMicroInfo::getMerchId ,merchant.getMerchId());
        BizMicroInfo microInfo =  microInfoService.getOne(lqw);
        // 获取头像信息
        SysUser sysUser = sysUserService.selectUserById(userId);
        String portrait = sysUser.getPortrait();
        Map<String,Object> merchanInfo = new HashMap<>();
        merchanInfo.put("merchantBrief",merchant);
        merchanInfo.put("merchantDetail",microInfo);
        merchanInfo.put("portrait",portrait);
        ajaxResult.put("data",merchanInfo);
        return  ajaxResult;
    }
    @ApiOperation(value="获取直接子商户字典")
    @ApiResponses({@ApiResponse(code=200,response = MerchantDict.class,message = "业务数据响应成功")})
    @PostMapping("/direct/child/dict")
    public AjaxResult getChildMerchantDictList(@RequestBody MerchantDTO merchantDTO){
        AjaxResult ajaxResult = AjaxResult.success();
        Long userId = LoginUserUtils.getLoginUserId();
        // List<MerchantDict> dicts = merchantService.getChildMerchartDictList(userId);
        BizMerchant merchant = merchantService.getMerchantByUserId(userId);
        Long merchId = merchant.getMerchId();
        // 设置商户编号
        merchantDTO.setMerchId(merchId);
        List<MerchantDict> dicts = merchantService.selectPagedPartnerList(merchantDTO);
        ajaxResult.put("data",dicts);
        return ajaxResult;
    }

    @ApiOperation(value="获取我的伙伴列表")
    @ApiImplicitParams({@ApiImplicitParam(name="nickName",value = "用户姓名",dataType = "string",required = false,paramType="body"),
            @ApiImplicitParam(name="verifyStatus",value = "商户报件状态，0-报件审核中，1-报件成功，2-异常",dataType = "string",required = false,paramType="body"),
            @ApiImplicitParam(name="monthType",value = "数据月份，'this'-本月，'last'-上月",dataType = "string",required = false,paramType="body"),
            @ApiImplicitParam(name="pageNo",value = "当前页号，默认-1",dataType = "int",required = true,paramType="body"),
            @ApiImplicitParam(name="pageSize",value = "当前页大小，默认-1",dataType = "int",required = true,paramType="body")})
    @ApiResponses({@ApiResponse(code=200,response = PartnerDTO.class,message = "业务数据响应成功")})
    @PostMapping("/mypartner/list")
    public AjaxResult getPartnerList(@RequestBody MerchantDTO merchantDTO){
        AjaxResult ajaxResult = AjaxResult.success();
        merchantDTO.setUserId(LoginUserUtils.getLoginUserId());
        ajaxResult.put("data",merchantService.getPagedPartnerList(merchantDTO));
        ajaxResult.put("partnerCounts",merchantService.getChildMerchantCounts(merchantDTO.getUserId(),merchantDTO.getVerifyStatus(),"",merchantDTO.getNickName()));
        return ajaxResult;
    }

    @ApiOperation(value="获取我的伙伴详情")
    @ApiResponses({@ApiResponse(code=200,response = PartnerDTO.class,message = "业务数据响应成功")})
    @PostMapping("/mypartner/detail")
    public AjaxResult getPartnerDetail(@RequestBody MerchantDTO merchantDTO){
        AjaxResult ajaxResult = AjaxResult.success();
        // ajaxResult.put("data",merchantService.getPartnerDetail(LoginUserUtils.getLoginUserId()));
        ajaxResult.put("data",merchantService.getPartnerDetail(merchantDTO.getUserId()));
        return ajaxResult;
    }

    @ApiOperation(value="商户入驻")
    @ApiResponses({@ApiResponse(code=200,response = MicroMerchantDTO.class,message = "商户入驻成功")})
    @PostMapping("/register")
    public AjaxResult merchantRegister(@RequestBody MicroMerchantDTO microMerchantDTO){
        AjaxResult ajaxResult = AjaxResult.success("商户入驻成功");
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        String mobile = sysUser.getPhonenumber();
        String verifyCode = microMerchantDTO.getVerifyCode();
        BizStatusContantEnum bizStatus = verifyCodeService.verifyCodeValidate(mobile,verifyCode);
        if(bizStatus != BizStatusContantEnum.SMS_SUCCESS){
            return AjaxResult.error(bizStatus.getName());
        }
        microMerchantDTO.setUserId(sysUser.getUserId());
        merchantService.microMerchantRegister(microMerchantDTO);
        return ajaxResult;
    }

    @ApiOperation(value="登录密码设置")
    @ApiImplicitParams({@ApiImplicitParam(name="password",value = "登录密码密文",dataType = "string",required = true,paramType="body")})
    @ApiResponses({@ApiResponse(code=200,response = PartnerInviteDTO.class,message = "登录密码重置成功")})
    @PostMapping("/password/reset")
    public AjaxResult resetLoginPassword(@RequestBody PartnerInviteDTO partnerInviteDTO){
        AjaxResult ajaxResult = AjaxResult.success("登录密码重置成功");
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        String mobile = sysUser.getPhonenumber();
        String verifyCode = partnerInviteDTO.getVerifyCode();
        BizStatusContantEnum bizStatus = verifyCodeService.verifyCodeValidate(mobile,verifyCode);
        if(bizStatus != BizStatusContantEnum.SMS_SUCCESS){
            return AjaxResult.error(bizStatus.getName());
        }
        //密码密文处理，前端算法解密-新算法加密-保存
        String password = SecurityUtils.encryptPassword(partnerInviteDTO.getPassword());
        sysUserService.resetUserPwd(sysUser.getUserName(),password);
        return ajaxResult;
    }
    @ApiOperation(value="获得伙伴邀请码")
    @PostMapping("/mypartner/invite")
    public AjaxResult getPartnerInviteCode(@RequestBody MerchantDTO merchantDTO){
        AjaxResult ajaxResult = AjaxResult.success();
        // 获取用户
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        // 获取用户id
        Long userId = LoginUserUtils.getLoginUserId();
        BizMerchant merchant =  merchantService.getMerchantByUserId(userId);
        Map<String,Object> merchanInfo = new HashMap<>();
        merchanInfo.put("merchCode",merchant.getMerchCode());
        String invitationUrl = ruoYiConfig.getInvitationUrl();
        merchanInfo.put("url",invitationUrl);
        ajaxResult.put("data",merchanInfo);
        return ajaxResult;
    }

    @ApiOperation(value="验证设置支付密码")
    @PostMapping("/payment/validate")
    public AjaxResult paymentValidate(@RequestBody PaymentPassUpdateDTO merchantDTO){
        // 验证码验证
        String verifyCode = merchantDTO.getVerifyCode();
        String mobile = merchantDTO.getMobile();
        BizStatusContantEnum bizStatus = verifyCodeService.verifyCodeValidate(mobile,verifyCode);
        if(bizStatus != BizStatusContantEnum.SMS_SUCCESS){
            return AjaxResult.error(bizStatus.getName());
        }
        AjaxResult ajaxResult = AjaxResult.success();
        // 获取用户
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        // 获取用户id
        Long userId = LoginUserUtils.getLoginUserId();
        BizMerchant merchant =  merchantService.getMerchantByUserId(userId);
        Long merId =  merchant.getMerchId();
        BizMicroInfo microInfo =  microInfoService.getBizMicroInfoByMerchId(merId);
        String merchIdCard =  microInfo.getMerchIdcard(); // 数据库中的身份证号
        String cardNo = merchantDTO.getCardNo();
        if(merchIdCard==null || merchIdCard.length()<=0){
            return AjaxResult.error(BizStatusContantEnum.CARD_NO_IS_NULL.getName());
        }
        String dbCardNo = merchIdCard.substring(merchIdCard.length()-6);// 截取后6位
        if(!cardNo.equals(dbCardNo)){
            return AjaxResult.error(BizStatusContantEnum.PAYMENT_CARDNO_ERROR.getName());
        }
        return AjaxResult.success(BizStatusContantEnum.PAYMENT_VALIDATE_SUCCESS.getName());
    }

    @ApiOperation(value="验证重置修改支付密码")
    @PostMapping("/payment/validatePassUpdate")
    // 验证码   原支付密码
    public AjaxResult paymentValidatePassUpdate(@RequestBody PaymentPassUpdateDTO merchantDTO){
        AjaxResult ajaxResult = AjaxResult.success();
        // 获取用户
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        // 获得用户id
        Long userId = sysUser.getUserId();
        // 获取用户信息
        SysUser loginUser =  sysUserService.selectUserById(userId);
        // 获取支付
        String dbPaymentPassword = loginUser.getPaymentPassword();
        // 获得接口传递过来的输入支付密码
        String password = merchantDTO.getPassword();
        // 验证
        boolean flag = SecurityUtils.matchesPassword(password,dbPaymentPassword);
        if(!flag){
            // 原支付密码输入有误 PAYMENT_ORIGINAL_PASSWORD_ERROR
            return AjaxResult.error(BizStatusContantEnum.PAYMENT_ORIGINAL_PASSWORD_ERROR.getName());
        }
        return ajaxResult;
    }


    @ApiOperation(value="修改支付密码")
    @PostMapping("/payment/updatePassword")
    public AjaxResult updatePaymentPass(@RequestBody PaymentPassUpdateDTO merchantDTO){
        AjaxResult ajaxResult = AjaxResult.success();
        // 获取用户
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        String paymentPassword = SecurityUtils.encryptPassword(merchantDTO.getPassword());
        sysUser.setPaymentPassword(paymentPassword);
        sysUserService.resetPaymentPass(sysUser);
        return ajaxResult;
    }

    // 修改头像

    @ApiOperation(value="修改头像")
    @PostMapping("/updatePortrait")
    public AjaxResult updatPortrait(@RequestBody UpdatePortraitDTO merchantDTO){
        AjaxResult ajaxResult = AjaxResult.success();
        // 获取用户
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        String portraitUrl = merchantDTO.getPortraitUrl();
        sysUser.setPortrait(portraitUrl);
        sysUserService.resetPortraitUrl(sysUser);
        return ajaxResult;
    }

}
