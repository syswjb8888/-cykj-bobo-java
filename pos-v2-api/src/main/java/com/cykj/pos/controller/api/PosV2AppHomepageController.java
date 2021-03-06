package com.cykj.pos.controller.api;

import com.cykj.common.core.domain.AjaxResult;
import com.cykj.common.core.domain.entity.SysUser;
import com.cykj.framework.web.service.TokenService;
import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.domain.dto.HomePageDTO;
import com.cykj.pos.enums.bizstatus.CommEnum;
import com.cykj.pos.profit.dto.HomePageDataDTO;
import com.cykj.pos.profit.dto.SysUserDTO;
import com.cykj.pos.profit.dto.TransAmountDataDTO;
import com.cykj.pos.service.IBizMerchTransactionsService;
import com.cykj.pos.service.IBizMerchantService;
import com.cykj.pos.util.DateUtils;
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

    @ApiOperation(value = "数据本月统计")
    @PostMapping("/transAmountStatistics")
    public AjaxResult homePageTransAmountData(@RequestBody SysUserDTO userVo) {
        AjaxResult ajaxResult = AjaxResult.success();
        Long userId = LoginUserUtils.getLoginUserId();
        BizMerchant merchant = merchantService.getMerchantByUserId(userId);
        if (merchant == null) {
            ajaxResult = AjaxResult.success("当前用户未入驻商户");
            ajaxResult.put("code", "500001");
            return ajaxResult;
        }
        Long merchId = merchant.getMerchId();
        // 商户当前月的销售总金额
        // BigDecimal monthlyTransAmount = transRecordsService.getMonthlyTransAmountByMerchId(merchId);
        // 商户交易额个人业绩
        // BigDecimal merchantTransAmount = transRecordsService.getMonthlyMerchantTransAmountByMerchId(merchId);
        // 伙伴交易额
        // BigDecimal partnerTransAmount = monthlyTransAmount.subtract(merchantTransAmount);
        // 本月新增商户
        // Integer monthlyNewMerchantCounts = merchantService.getMonthlyNewMerchCounts(merchId);
        // 累计商户
        // Integer totalMerchantCounts = merchantService.getTotalMerchantByMerId(merchId);
        // String formatedDate = DateUtils.getCaculateYearAndMonth("this", "yyyy-MM");
        // BizMerchant bizMerchant = merchantService.getMerchantByMerchId(merchId);
        // 本月新增伙伴   累计伙伴
        // Integer monthlyNewPartnerCounts = merchantService.getChildMerchantCounts(bizMerchant.getUserId(), null, formatedDate, null);
        // 累计伙伴
        // Integer totalPartnerCounts = merchantService.getChildMerchantCounts(bizMerchant.getUserId(), null, null, null);

        // TransAmountDataDTO dataVo = new TransAmountDataDTO();
        TransAmountDataDTO dataVo = merchantService.getTransAmountDataDTO(merchId,userId);
        /*dataVo.setMerchantTransAmount(merchantTransAmount); // 商户交易额
        dataVo.setMonthlyNewMerchantCounts(monthlyNewMerchantCounts); // 月新增商户数
        dataVo.setTotalMerchantCounts(totalMerchantCounts); // 累计商户
        dataVo.setPartnerTransAmount(partnerTransAmount); //伙伴交易额
        dataVo.setMonthlyNewPartnerCounts(monthlyNewPartnerCounts); //新增合作伙伴
        dataVo.setTotalPartnerCounts(totalPartnerCounts);// 累计伙伴*/
        ajaxResult.put("data", dataVo);
        return ajaxResult;
    }

    @ApiOperation(value = "获取APP首页数据")
    @ApiResponses({@ApiResponse(code = 200, response = HomePageDataDTO.class, message = "业务数据响应成功")})
    @PostMapping("/data")
    public AjaxResult homePageDataList(@RequestBody SysUserDTO userVo) {
        AjaxResult ajaxResult = AjaxResult.success();
        Long userId = LoginUserUtils.getLoginUserId();
        BizMerchant merchant = merchantService.getMerchantByUserId(userId);
        if (merchant == null) {
            ajaxResult = AjaxResult.success("当前用户未入驻商户");
            ajaxResult.put("code", "500001");
            return ajaxResult;
        }
        Long merchId = merchant.getMerchId();
        /* BigDecimal monthlyTransAmount = transRecordsService.getMonthlyTransAmountByMerchId(merchId); //商户月交易额
        Integer partnerCounts = merchantService.getMonthlyNewPartnerCount(merchId);// 本月新增伙伴
        Integer leafCounts = merchantService.getMonthlyNewMerchCounts(merchId);// 本月新增商户

        String expressNews = noticeService.getExpressNews();
        // 获取用户信息
        SysUser sysUser = sysUserService.selectUserById(userId);
        HomePageDataDTO dataVo = new HomePageDataDTO();
        */
        HomePageDataDTO dataVo = merchantService.getHomePageDataDTO(merchId,userId);
        /*dataVo.setMonthlyNewMerchantCounts(leafCounts);
        dataVo.setMonthlyNewPartnerCounts(partnerCounts);
        dataVo.setMonthlyTransAmount(monthlyTransAmount);
        dataVo.setExpressNews(expressNews);
        dataVo.setNickName(sysUser.getNickName());*/
        dataVo.setMerchCode(merchant.getMerchCode());
        // 腾讯接口对接用的常量
        dataVo.setSecretId(CommEnum.SECRETID.getValue());
        dataVo.setSecretKey(CommEnum.SECRETKEY.getValue());
        dataVo.setBucketName(CommEnum.BUCKETNAME.getValue());
        // 客服电话
        dataVo.setServicePhone(CommEnum.SERVICE_PHONE.getValue());
        // 设置头像
        // dataVo.setPortrait(sysUser.getPortrait());
        ajaxResult.put("data", dataVo);
        return ajaxResult;
    }
}
