package com.cykj.pos.service;

import com.cykj.common.core.domain.entity.SysUser;
import com.cykj.pos.domain.BizMessageRecords;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cykj.pos.profit.dto.MessageDTO;

import javax.websocket.Session;
import java.util.List;

/**
 * 用户消息Service接口
 *
 * @author weijianbo
 * @date 2021-02-21
 */
public interface IBizMessageRecordsService extends IService<BizMessageRecords> {

    /**
     * 查询列表
     */
    List<BizMessageRecords> queryList(BizMessageRecords bizMessageRecords);
    /**
     * 发送系统消息
     */
    public void sendInfo(Session session, BizMessageRecords messageRecords, SysUser user);

    /**
     * 通过用户id和消息类型查找消息列表
     * @param messageDTO
     * @return
     */
    List<MessageDTO> getMessageListByUserIdAndType(MessageDTO messageDTO);

    /**
     * 根据消息id获取消息
     * @param messageDTO
     * @return
     */
    MessageDTO getMessageByMgsId(MessageDTO messageDTO);
}
