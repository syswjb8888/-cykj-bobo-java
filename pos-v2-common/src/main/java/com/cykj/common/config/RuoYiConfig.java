package com.cykj.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 读取项目相关配置
 *
 * @author cykj
 */
@Component
@ConfigurationProperties(prefix = "cykj")
public class RuoYiConfig{
    /** 项目名称 */
    private String name;

    /** 版本 */
    private String version;

    /** 版权年份 */
    private String copyrightYear;

    /** 实例演示开关 */
    private boolean demoEnabled;

    /** 上传路径 */
    private static String profile;

    /** 获取地址开关 */
    private static boolean addressEnabled;

    private Integer ticketTimeOut;

    private String signSecretKey;

    private Integer errorLoginCounts;

    private Integer smsEffectiveDuration;

    private Integer minEffectiveDuration;

    private String invitationUrl;

    private List<String> posQueryFilterConditions;

    private String signenv;

    public String getInvitationUrl() {
        return invitationUrl;
    }

    public void setInvitationUrl(String invitationUrl) {
        this.invitationUrl = invitationUrl;
    }

    public String getSignenv() {
        return signenv;
    }

    public void setSignenv(String signenv) {
        this.signenv = signenv;
    }

    public List<String> getPosQueryFilterConditions() {
        return posQueryFilterConditions;
    }

    public void setPosQueryFilterConditions(List<String> posQueryFilterConditions) {
        this.posQueryFilterConditions = posQueryFilterConditions;
    }

    public Integer getSmsEffectiveDuration() {
        return smsEffectiveDuration;
    }

    public void setSmsEffectiveDuration(Integer smsEffectiveDuration) {
        this.smsEffectiveDuration = smsEffectiveDuration;
    }

    public Integer getTicketTimeOut() {
		return ticketTimeOut;
	}

	public void setTicketTimeOut(Integer ticketTimeOut) {
		this.ticketTimeOut = ticketTimeOut;
	}

	public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        this.copyrightYear = copyrightYear;
    }

    public boolean isDemoEnabled()
    {
        return demoEnabled;
    }

    public void setDemoEnabled(boolean demoEnabled)
    {
        this.demoEnabled = demoEnabled;
    }

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        RuoYiConfig.profile = profile;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        RuoYiConfig.addressEnabled = addressEnabled;
    }

    public Integer getMinEffectiveDuration() {
        return minEffectiveDuration;
    }

    public void setMinEffectiveDuration(Integer minEffectiveDuration) {
        this.minEffectiveDuration = minEffectiveDuration;
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }

	public String getSignSecretKey() {
		return signSecretKey;
	}

	public void setSignSecretKey(String signSecretKey) {
		this.signSecretKey = signSecretKey;
	}

	public Integer getErrorLoginCounts() {
		return errorLoginCounts;
	}

	public void setErrorLoginCounts(Integer errorLoginCounts) {
		this.errorLoginCounts = errorLoginCounts;
	}
}
