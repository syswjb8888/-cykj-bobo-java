package com.cykj.pos.mapper;

import com.cykj.pos.domain.BizVerifyCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cykj.pos.domain.dto.VerifyCodeDTO;
import org.apache.ibatis.annotations.Select;

/**
 * 短信验证码Mapper接口
 *
 * @author ningbingwu
 * @date 2021-01-18
 */
public interface BizVerifyCodeMapper extends BaseMapper<BizVerifyCode> {

    @Select("select * from biz_verify_code where ver_code=#{verifyCode} and ver_mobile=#{mobile} and ver_status=0 ")
    public BizVerifyCode getVerifyCode(String verifyCode,String mobile);

    /**
     * 获取与上一次发送验证码时间间隔 秒
     * @param mobile
     * @return
     */
    VerifyCodeDTO getMinEffectiveDurationMinute(String mobile);
}
