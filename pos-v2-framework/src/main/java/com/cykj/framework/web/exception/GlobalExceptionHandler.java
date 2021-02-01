package com.cykj.framework.web.exception;

import com.cykj.common.config.RuoYiConfig;
import com.cykj.common.constant.Constants;
import com.cykj.common.core.redis.RedisCache;
import com.cykj.common.exception.user.UserPasswordNotMatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.cykj.common.constant.HttpStatus;
import com.cykj.common.core.domain.AjaxResult;
import com.cykj.common.exception.BaseException;
import com.cykj.common.exception.CustomException;
import com.cykj.common.exception.DemoModeException;
import com.cykj.common.utils.StringUtils;

import javax.validation.ConstraintViolationException;
import java.util.concurrent.TimeUnit;

/**
 * 全局异常处理器
 *
 * @author cykj
 */
@RestControllerAdvice
public class GlobalExceptionHandler{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private RuoYiConfig config;

    @Autowired
    private  RedisCache redisCache;
    /**
     * 基础异常
     */
    @ExceptionHandler(BaseException.class)
    public AjaxResult baseException(BaseException e)
    {
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(CustomException.class)
    public AjaxResult businessException(CustomException e)
    {
        if (StringUtils.isNull(e.getCode()))
        {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public AjaxResult handlerNoFoundException(Exception e)
    {
        log.error(e.getMessage(), e);
        return AjaxResult.error(HttpStatus.NOT_FOUND, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAuthorizationException(AccessDeniedException e)
    {
        log.error(e.getMessage());
        return AjaxResult.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    @ExceptionHandler(AccountExpiredException.class)
    public AjaxResult handleAccountExpiredException(AccountExpiredException e)
    {
        log.error(e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }
    @ExceptionHandler(UserPasswordNotMatchException.class)
    public AjaxResult handleUserPasswordNotMatchException(UserPasswordNotMatchException e){
        log.error(e.getMessage(), e);
        String redisKey = e.getUserName()+"-login-error-counts";
        Integer errorLoginCounts = redisCache.getCacheObject(redisKey);
        if(errorLoginCounts == null){
            errorLoginCounts = 1;
        }
        int maxCounts = config.getErrorLoginCounts();
        AjaxResult ajax = AjaxResult.error();
        if(errorLoginCounts >= maxCounts){
            ajax.put("msg","用户名或密码输入错误超过["+maxCounts+"]次，请于"+ Constants.ERROR_LOGIN_EXPIRATION+"分钟后重试！");
        }else{
            ajax.put("msg","用户或密码错误，错误次数["+errorLoginCounts+"],还有["+(maxCounts-errorLoginCounts)+"]次机会");
        }

        errorLoginCounts++;
        redisCache.setCacheObject(redisKey, errorLoginCounts, Constants.ERROR_LOGIN_EXPIRATION, TimeUnit.MINUTES);
        ajax.put("code","100000");
        return ajax;
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public AjaxResult handleUsernameNotFoundException(UsernameNotFoundException e)
    {
        log.error(e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e)
    {
        log.error(e.getMessage(), e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult validatedBindException(BindException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public AjaxResult constraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        String message = e.getConstraintViolations().iterator().next().getMessage();
        return AjaxResult.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validExceptionHandler(MethodArgumentNotValidException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.error(message);
    }

    /**
     * 演示模式异常
     */
    @ExceptionHandler(DemoModeException.class)
    public AjaxResult demoModeException(DemoModeException e)
    {
        return AjaxResult.error("演示模式，不允许操作");
    }
}
