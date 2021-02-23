package com.cykj.pos.mapper;

import com.cykj.pos.domain.BizMessageRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cykj.pos.profit.dto.MessageDTO;

import java.util.List;

/**
 * 离用户消息Mapper接口
 *
 * @author ningbingwu
 * @date 2021-01-18
 */
public interface BizMessageRecordsMapper extends BaseMapper<BizMessageRecords> {
    /**
     * 查询信息列表
     * @param messageDTO
     * @return
     */
    List<MessageDTO> selectMessageListByUserIdAndType(MessageDTO messageDTO);

    /**
     * 获得单个消息
     * @param messageDTO
     * @return
     */
    MessageDTO selectMessageByMgsId(MessageDTO messageDTO);
}
