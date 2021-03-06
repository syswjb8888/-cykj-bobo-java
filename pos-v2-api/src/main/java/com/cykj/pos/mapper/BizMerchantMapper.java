package com.cykj.pos.mapper;

import com.cykj.pos.domain.BizMerchant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cykj.pos.domain.dto.HomePageDTO;
import com.cykj.pos.profit.dto.HomePageDataDTO;
import com.cykj.pos.profit.dto.MerchantDTO;
import com.cykj.pos.profit.dto.MerchantDict;
import com.cykj.pos.profit.dto.TransAmountDataDTO;

import java.util.List;

/**
 * 报件商户信息Mapper接口
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
public interface BizMerchantMapper extends BaseMapper<BizMerchant> {
    /**
     * 查询商户的直接伙伴信息 魏建波
     * @param merchantDTO
     * @return
     */
    List<MerchantDict> selectPagedPartnerList(MerchantDTO merchantDTO);

    /**
     * 本月新增伙伴 魏建波
     * @param homePageDTO
     * @return
     */
    Integer getMonthlyNewPartnerCount(HomePageDTO homePageDTO);
    /**
     * 本月新增商户 魏建波
     * @param homePageDTO
     * @return
     */
    Integer getMonthlyNewMerchCounts(HomePageDTO homePageDTO);

    /**
     * 获得直接父合作伙伴和总部服务中心 魏建波
     * @param parentMerchId
     * @return
     */
    List<BizMerchant> selectParentMerchByUserId(Long parentMerchId);

    /**
     * 查看首页数据  一个sql完事
     * @param homePageDTO
     * @return
     */
    HomePageDataDTO selectHomePageDataDTO(HomePageDTO homePageDTO);

    /**
     * 本月数据信息
     * @param homePageDTO
     * @return
     */
    TransAmountDataDTO selectTransAmountDataDTO(HomePageDTO homePageDTO);
}
