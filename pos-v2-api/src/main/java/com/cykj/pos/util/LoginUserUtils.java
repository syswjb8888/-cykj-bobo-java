package com.cykj.pos.util;

import com.cykj.common.core.domain.entity.SysUser;
import com.cykj.common.core.domain.model.LoginUser;
import com.cykj.common.utils.SecurityUtils;

public class LoginUserUtils {
    public static Long getLoginUserId(){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser sysUser = loginUser.getUser();
        return sysUser.getUserId();
    }
}
