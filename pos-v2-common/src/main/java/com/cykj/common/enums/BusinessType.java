package com.cykj.common.enums;

/**
 * 业务操作类型
 *
 * @author cykj
 */
public enum BusinessType
{
    /**
     * 其它
     */
    OTHER,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 生成代码
     */
    GENCODE,

    /**
     * 清空数据
     */
    CLEAN,
    /**
     * 分润计算
     */
    PROFIT_CACULATE,
    /**
     * 分润支付
     */
    PROFIT_PAYMENT,
    /**
     * 设备划拔
     */
    TERMINAL_ALLOCATE,
    /**
     * 设备回调
     */
    TERMINAL_ADJUST
}
