package com.cykj.pos.controller.api;

import com.cykj.common.core.domain.AjaxResult;
import com.cykj.common.core.domain.entity.SysUser;
import com.cykj.framework.web.service.TokenService;
import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.enums.bizstatus.CommEnum;
import com.cykj.pos.profit.dto.HomePageDataDTO;
import com.cykj.pos.profit.dto.SysUserDTO;
import com.cykj.pos.service.IBizMerchTransactionsService;
import com.cykj.pos.service.IBizMerchantService;
import com.cykj.pos.util.LoginUserUtils;
import com.cykj.system.service.ISysNoticeService;
import com.cykj.system.service.ISysUserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/pos/api/v2/homepage")
public class PosV2AppHomepageController {

    private final IBizMerchantService merchantService;

    private final IBizMerchTransactionsService transRecordsService;

    private final ISysNoticeService noticeService;

    private final TokenService tokenService;

    private final ISysUserService sysUserService;



    @ApiOperation(value="获取APP首页数据")
    @ApiResponses({@ApiResponse(code=200,response = HomePageDataDTO.class,message = "业务数据响应成功")})
    @PostMapping("/data")
    public AjaxResult homePageDataList(@RequestBody SysUserDTO userVo) {
        AjaxResult ajaxResult = AjaxResult.success();
        Long userId = LoginUserUtils.getLoginUserId();
        BizMerchant merchant = merchantService.getMerchantByUserId(userId);
        if(merchant == null){
            ajaxResult = AjaxResult.success("当前用户未入驻商户");
            ajaxResult.put("code","500001");
            return ajaxResult;
        }
        Long merchId = merchant.getMerchId();
        BigDecimal monthlyTransAmount = transRecordsService.getMonthlyTransAmountByMerchId(merchId);

        Integer counts = merchantService.getMonthlyNewMerchantCounts(merchId);
        Integer leafCounts = merchantService.getMonthLyNewMerchantExcludeChild(merchId);
        Integer partnerCounts = counts - leafCounts;
        String expressNews = noticeService.getExpressNews();
        // 获取用户信息
        SysUser sysUser = sysUserService.selectUserById(userId);

        HomePageDataDTO dataVo = new HomePageDataDTO();
        dataVo.setMonthlyNewMerchantCounts(leafCounts);
        dataVo.setMonthlyNewPartnerCounts(partnerCounts);
        dataVo.setMonthlyTransAmount(monthlyTransAmount);
        dataVo.setExpressNews(expressNews);
        dataVo.setNickName(sysUser.getNickName());
        dataVo.setMerchCode(merchant.getMerchCode());
        // 腾讯接口对接用的常量
        dataVo.setSecretId(CommEnum.SECRETID.getValue());
        dataVo.setSecretKey(CommEnum.SECRETKEY.getValue());
        dataVo.setBucketName(CommEnum.BUCKETNAME.getValue());
        ajaxResult.put("data",dataVo);
        return ajaxResult;
    }
}
