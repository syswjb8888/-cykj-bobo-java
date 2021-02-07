package com.cykj.pos.mapper;

import com.cykj.pos.domain.BizMerchant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cykj.pos.profit.dto.MerchantDTO;
import com.cykj.pos.profit.dto.MerchantDict;

import java.util.List;

/**
 * 报件商户信息Mapper接口
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
public interface BizMerchantMapper extends BaseMapper<BizMerchant> {
    /**
     * 查询商户的所有伙伴信息 魏建波
     * @param merchantDTO
     * @return
     */
    List<MerchantDict> selectPagedPartnerList(MerchantDTO merchantDTO);
}
