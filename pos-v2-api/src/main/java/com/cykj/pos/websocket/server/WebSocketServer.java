package com.cykj.pos.websocket.server;

import com.cykj.common.core.domain.entity.SysUser;
import com.cykj.pos.domain.BizMessageRecords;
import com.cykj.pos.service.IBizMessageRecordsService;
import com.cykj.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

    public WebSocketServer(){}
    public void sendMessage(Session session, String message) throws IOException {
        if(session != null){
            session.getBasicRemote().sendText(message);
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") Long userId){
        Set<Long> connectedUser = sessionPools.keySet();

            //TODO:用户连接后则查询离线消息中是否存在未发送的消息，存在则发送
            if(!connectedUser.contains(userId)){

                BizMessageRecords offline = new BizMessageRecords();
                offline.setMsgUserId(userId);
                offline.setMsgStatus(0);
                List<BizMessageRecords> offlineList = messageRecordsService.queryList(offline);
                for(BizMessageRecords msg: offlineList){
                    executor.execute(() -> {
                        try {
                            sendMessage(session, msg.getMsgContent());
                            msg.setMsgStatus(1);
                            //发送完成即删除
                            messageRecordsService.removeById(msg.getMsgId());
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
    public void onClose(@PathParam(value = "userId") String userId){
        sessionPools.remove(userId);
        subOnlineCount();
        System.out.println(userId + "断开webSocket连接！当前人数为" + online);
    }

    @OnMessage
    public void onMessage(String message){
        for (Session session: sessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch(Exception e){
                e.printStackTrace();
                continue;
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public void sendInfo(Long userId, String message){
        Session session = sessionPools.get(userId);
        SysUser user = sysUserService.selectUserById(userId);
        if(session != null) {
            try {
                sendMessage(session, message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(user != null){
           //用户存在，但用户不在线，则将消息保存
            BizMessageRecords msg = new BizMessageRecords();
            msg.setMsgUserId(userId);
            msg.setMsgContent(message);
            msg.setMsgStatus(0);
            messageRecordsService.save(msg);
        }
    }

    public static void addOnlineCount(){
        online.incrementAndGet();
    }

    public static void subOnlineCount() {
        online.decrementAndGet();
    }
}
