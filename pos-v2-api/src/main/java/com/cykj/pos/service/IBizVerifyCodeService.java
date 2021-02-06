package com.cykj.pos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cykj.pos.domain.BizVerifyCode;
import com.cykj.pos.domain.dto.VerifyCodeDTO;
import com.cykj.pos.enums.bizstatus.BizStatusContantEnum;

import java.util.List;

/**
 * 短信验证码Service接口
 *
 * @author ningbingwu
 * @date 2021-01-18
 */
public interface IBizVerifyCodeService extends IService<BizVerifyCode> {

    /**
     * 查询列表
     */
    List<BizVerifyCode> queryList(BizVerifyCode bizVerifyCode);

    /**
     *发送短信验证码
     * @param mobile
     */
    public BizStatusContantEnum sendVerifyCode(String mobile,Long userId);

    /**
     * 短信验证码校验
     * @param mobile
     * @param verifyCode
     * @return
     */
    public BizStatusContantEnum verifyCodeValidate(String mobile, String verifyCode);

    /**
     * 获取与上一次发送验证码时间间隔 秒
     * @param mobile
     * @return
     */
    VerifyCodeDTO getMinEffectiveDurationMinute(String mobile);
}
