package com.cykj.pos.controller.api;


import com.cykj.common.core.domain.AjaxResult;
import com.cykj.common.core.domain.entity.SysUser;
import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.enums.bizstatus.BizStatusContantEnum;
import com.cykj.pos.profit.dto.BankCardDTO;
import com.cykj.pos.service.IBizMerchantService;
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

@RestController
@RequestMapping("/pos/api/v2/bankcard")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PosV2AppBankCardController {

    private final IBizMerchantService iBizMerchantService;

    @ApiOperation(value="获取银行卡信息")
    @ApiResponses({@ApiResponse(code=200,response = BankCardDTO.class,message = "业务数据响应成功")})
    @PostMapping("/homepage")
    public AjaxResult getBankCardHomepage(@RequestBody BankCardDTO bankCardDTO){
        AjaxResult ajaxResult = AjaxResult.success();
        Long userId = LoginUserUtils.getLoginUserId();
        bankCardDTO.setUserId(userId);
        // 银行卡照片url
        ajaxResult.put("data",iBizMerchantService.getBankCardInfo(bankCardDTO));
        return ajaxResult;
    }
    @ApiOperation(value="银行卡变更")
    @ApiImplicitParams({@ApiImplicitParam(name="bankName",value = "开户行名称",dataType = "string",required = true,paramType="body"),
            @ApiImplicitParam(name="bankCardNo",value = "银行卡号",dataType = "string",required = true,paramType="body"),
            @ApiImplicitParam(name="bankCity",value = "开户行所在城市,区县编码数据字典",dataType = "string",required = true,paramType="body"),
            @ApiImplicitParam(name="bankCardImg",value = "银行卡照片",dataType = "string",required = true,paramType="body")})
    @ApiResponses({@ApiResponse(code=200,response = BankCardDTO.class,message = "业务数据响应成功")})
    @PostMapping("/alter")
    public AjaxResult alterBankCardInfo(@RequestBody BankCardDTO bankCardDTO){
        AjaxResult ajaxResult = AjaxResult.success();
        Long userId = LoginUserUtils.getLoginUserId();
        bankCardDTO.setUserId(userId);
        ajaxResult.put("data",iBizMerchantService.alterBankCardInfo(bankCardDTO));
        return ajaxResult;
    }
}
