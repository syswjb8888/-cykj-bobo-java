package com.cykj.pos.websocket.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cykj.common.core.domain.entity.SysUser;
import com.cykj.common.utils.DateUtils;
import com.cykj.pos.domain.BizMessageRecords;
import com.cykj.pos.service.IBizMessageRecordsService;
import com.cykj.pos.util.SpringUtil;
import com.cykj.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * WebSocket服务类
 *
 * @author weijianbo
 * @date 2021-02-19
 */
@Component
@ServerEndpoint(value = "/socket/{userId}")
public class WebSocketServer {
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IBizMessageRecordsService messageRecordsService;

    private static AtomicInteger online = new AtomicInteger();
    private static Map<Long, Session> sessionPools = new ConcurrentHashMap<>();

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10,
            200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));

    public WebSocketServer() {
    }

    public void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            session.getBasicRemote().sendText(message);
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") Long userId) {
        // 获得后台接口
        messageRecordsService = SpringUtil.getBean(IBizMessageRecordsService.class);
        Set<Long> connectedUser = sessionPools.keySet();

        //TODO:用户连接后则查询离线消息中是否存在未发送的消息，存在则发送
            if (!connectedUser.contains(userId)) {

                BizMessageRecords offline = new BizMessageRecords();
                offline.setMsgUserId(userId);
                offline.setMsgStatus(0);
                List<BizMessageRecords> offlineList = messageRecordsService.queryList(offline);
                for (BizMessageRecords msg : offlineList) {
                    executor.execute(() -> {
                        try {
                            // 把对象转换成json进行传递
                            String message = JSON.toJSONString(msg);
                            sendMessage(session, message);
                            msg.setMsgStatus(1);
                            //发送完成即删除
                            // messageRecordsService.removeById(msg.getMsgId());
                            messageRecordsService.saveOrUpdate(msg); // 设置为已发送
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }

        sessionPools.put(userId, session);
        addOnlineCount();
    }

    @OnClose
    public void onClose(@PathParam(value = "userId") String userId) {
        sessionPools.remove(userId);
        subOnlineCount();
        System.out.println(userId + "断开webSocket连接！当前人数为" + online);
    }

    @OnMessage
    public void onMessage(String message) {
        for (Session session : sessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    /**
     * 向某个用户发送消息   这个是专门给第三方发送消息提供的功能接口
     * @param userId
     * @param message 消息字符串
     */
    public void sendInfo(Long userId, String message) {
        // 获得后端接口
        sysUserService = SpringUtil.getBean(ISysUserService.class);
        Session session = sessionPools.get(userId);
        SysUser user = sysUserService.selectUserById(userId);
        BizMessageRecords msg = new BizMessageRecords();
        msg.setMsgUserId(userId);
        msg.setMsgContent(message);
        msg.setReadStatus(0);
        msg.setMsgType(2);
        msg.setCreateTime(DateUtils.getNowDate());
        // 保存起来
        messageRecordsService.save(msg);
        System.out.println(msg);
        if (session != null) {
            try {
                // 把对象转换成json进行传递
                String m = JSON.toJSONString(msg);
                sendMessage(session, m);
                msg.setMsgStatus(1); // 已发送
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (user != null) {
            //用户存在，但用户不在线，则将消息保存
            msg.setMsgStatus(0); // 为发送
        }
        messageRecordsService.saveOrUpdate(msg);
    }
    /**
     * 向某个用户发送消息对象   这个是专门给第三方发送消息提供的功能接口
     * 调用前需要自己封装消息对象  包括消息信息和消息类型等信息
     * @param userId
     * @param msg 消息对象
     */
    public void sendInfo(Long userId, BizMessageRecords msg) {
        // 获得后端接口
        sysUserService = SpringUtil.getBean(ISysUserService.class);
        Session session = sessionPools.get(userId);
        SysUser user = sysUserService.selectUserById(userId);
        msg.setMsgUserId(userId);
        // 服务器当前时间
        /*LocalDate localDate = LocalDate.now();
        String formatedDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));*/
        msg.setCreateTime(DateUtils.getNowDate());
        // 保存起来
        messageRecordsService.save(msg);
        if (session != null) {
            try {
                // 把对象转换成json进行传递
                String m = JSON.toJSONString(msg);
                sendMessage(session, m);
                msg.setMsgStatus(1); // 已发送
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (user != null) {
            //用户存在，但用户不在线，则将消息保存
            msg.setMsgStatus(0); // 未发送
        }
        messageRecordsService.saveOrUpdate(msg);
    }

    public static void addOnlineCount() {
        online.incrementAndGet();
    }

    public static void subOnlineCount() {
        online.decrementAndGet();
    }
}
