package com.cykj.pos.service.impl;

import com.alibaba.fastjson.JSON;
import com.cykj.common.core.domain.entity.SysUser;
import com.cykj.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import com.cykj.pos.mapper.BizMessageRecordsMapper;
import com.cykj.pos.domain.BizMessageRecords;
import com.cykj.pos.service.IBizMessageRecordsService;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.Session;
import java.util.List;

/**
 * 离用户消息Service业务层处理
 *
 * @author weijianbo
 * @date 2021-02-21
 */
@Service
public class BizMessageRecordsServiceImpl extends ServiceImpl<BizMessageRecordsMapper, BizMessageRecords> implements IBizMessageRecordsService {

    @Override
    public List<BizMessageRecords> queryList(BizMessageRecords bizMessageRecords) {
        LambdaQueryWrapper<BizMessageRecords> lqw = Wrappers.lambdaQuery();
        if (bizMessageRecords.getMsgUserId() != null){
            lqw.eq(BizMessageRecords::getMsgUserId ,bizMessageRecords.getMsgUserId());
        }
        if (StringUtils.isNotBlank(bizMessageRecords.getMsgContent())){
            lqw.eq(BizMessageRecords::getMsgContent ,bizMessageRecords.getMsgContent());
        }
        if (bizMessageRecords.getMsgStatus() != null){
            lqw.eq(BizMessageRecords::getMsgStatus ,bizMessageRecords.getMsgStatus());
        }
        return this.list(lqw);
    }

    @Override
    @Transactional
    public void sendInfo(Session session, BizMessageRecords msg, SysUser user) {
        // 保存起来
        save(msg);
        if (session != null) {
            try {
                // 把对象转换成json进行传递
                String m = JSON.toJSONString(msg);
                session.getBasicRemote().sendText(m);
                msg.setMsgStatus(1); // 已发送
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (user != null) {
            //用户存在，但用户不在线，则将消息保存
            msg.setMsgStatus(0); // 未发送
        }
        saveOrUpdate(msg);
    }

}
