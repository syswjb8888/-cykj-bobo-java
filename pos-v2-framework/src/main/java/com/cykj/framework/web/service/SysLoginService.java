package com.cykj.framework.web.service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.cykj.common.config.RuoYiConfig;
import com.cykj.common.constant.Constants;
import com.cykj.common.core.domain.model.LoginUser;
import com.cykj.common.core.redis.RedisCache;
import com.cykj.common.exception.CustomException;
import com.cykj.common.exception.user.CaptchaException;
import com.cykj.common.exception.user.CaptchaExpireException;
import com.cykj.common.exception.user.UserPasswordNotMatchException;
import com.cykj.common.utils.MessageUtils;
import com.cykj.framework.manager.AsyncManager;
import com.cykj.framework.manager.factory.AsyncFactory;

/**
 * 登录校验方法
 *
 * @author cykj
 */
@Component
public class SysLoginService{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid){

    	boolean isEndPlatformLogin = uuid.contains("-endplatform");

    	if(isEndPlatformLogin){
    		//验证码校验逻辑
            String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid.substring(0, uuid.lastIndexOf("-endplatform"));
            String captcha = redisCache.getCacheObject(verifyKey);
            redisCache.deleteObject(verifyKey);
            if (captcha == null){
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
                throw new CaptchaExpireException();
            }
            if (!code.equalsIgnoreCase(captcha)){
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
                throw new CaptchaException();
            }
    	}
        // 用户验证
        Authentication authentication = null;
        String redisKey = username+"-login-error-counts";
        Integer errorLoginCounts = redisCache.getCacheObject(redisKey);
        if(errorLoginCounts == null){
            errorLoginCounts = 0;
        }
        try{
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (Exception e){
            if (e instanceof BadCredentialsException){
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));

                throw new UserPasswordNotMatchException(username);
            }else{
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        /*
            单点登录
            魏建波添加的功能
         */
        /*-----------------------------------------------------------------------------*/
        // 删除key开头是 login_tokens:  让之前登录的用户token失效
        Collection<String> loginKeyCollection = redisCache.keys("login_tokens:*");
        for(String key:loginKeyCollection){
            // 遍历找到登录账户一致的登录信息
            Object obj = redisCache.getCacheObject(key);
            if(obj instanceof LoginUser){
                LoginUser u = (LoginUser) obj;
                if(u.getUsername().equals(username)){
                    // 需要删除
                    redisCache.deleteObject(key);
                }
            }
        }
        /*-----------------------------------------------------------------------------*/
        // 生成token
        return tokenService.createToken(loginUser);
    }
}
