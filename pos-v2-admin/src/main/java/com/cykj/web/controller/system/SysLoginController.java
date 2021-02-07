package com.cykj.web.controller.system;

import java.util.List;
import java.util.Set;

import com.cykj.pos.enums.bizstatus.CommEnum;
import com.cykj.pos.service.IDataSecurityService;
import com.cykj.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cykj.common.config.RuoYiConfig;
import com.cykj.common.constant.Constants;
import com.cykj.common.core.domain.AjaxResult;
import com.cykj.common.core.domain.entity.SysMenu;
import com.cykj.common.core.domain.entity.SysUser;
import com.cykj.common.core.domain.model.LoginBody;
import com.cykj.common.core.domain.model.LoginUser;
import com.cykj.common.core.redis.RedisCache;
import com.cykj.common.utils.ServletUtils;
import com.cykj.framework.web.service.SysLoginService;
import com.cykj.framework.web.service.SysPermissionService;
import com.cykj.framework.web.service.TokenService;
import com.cykj.system.service.ISysMenuService;

/**
 * 登录验证
 *
 * @author cykj
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private  ISysUserService sysUserService;

    @Autowired
    private RuoYiConfig config;

    @Autowired
    private IDataSecurityService ticketService;
    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody){
        AjaxResult ajax = AjaxResult.success();
        String username = loginBody.getUsername();
        // 生成令牌
        String token = loginService.login(username, loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());

        String redisKey = loginBody.getUsername()+"-login-error-counts";
        Integer errorCounts = redisCache.getCacheObject(redisKey);
        if(errorCounts != null){
            redisCache.deleteObject(redisKey);
        }
        LoginUser loginUser = tokenService.getLoginUser(token);
        ajax.put(Constants.TOKEN, token);
        ajax.put("loginUser",loginUser);
        // 获得腾讯秘钥信息
        ajax.put(CommEnum.SECRETID.getKey(),CommEnum.SECRETID.getValue());
        ajax.put(CommEnum.SECRETKEY.getKey(),CommEnum.SECRETKEY.getValue());
        ajax.put(CommEnum.BUCKETNAME.getKey(),CommEnum.BUCKETNAME.getValue());
        ajax.put(Constants.TICKET, ticketService.createTicket(loginBody.getUsername(), config.getTicketTimeOut()));
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
