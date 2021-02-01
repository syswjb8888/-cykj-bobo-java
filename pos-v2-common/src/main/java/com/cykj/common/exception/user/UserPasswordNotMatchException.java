package com.cykj.common.exception.user;

/**
 * 用户密码不正确或不符合规范异常类
 *
 * @author cykj
 */
public class UserPasswordNotMatchException extends UserException
{
    private static final long serialVersionUID = 1L;
    private String userName;
    public UserPasswordNotMatchException(){
        super("user.password.not.match", null);
    }
    public UserPasswordNotMatchException(String userName){
        super("user.password.not.match", null);
        this.userName = userName;
    }
    public String getUserName(){
        return this.userName;
    }
}
