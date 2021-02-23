package com.cykj.pos.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cykj.common.core.domain.AjaxResult;
import com.cykj.common.core.domain.entity.SysDictData;
import com.cykj.common.core.domain.entity.SysUser;
import com.cykj.common.utils.SecurityUtils;
import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.domain.BizMessageRecords;
import com.cykj.pos.domain.BizMicroInfo;
import com.cykj.pos.domain.BizWallet;
import com.cykj.pos.domain.dto.BillQueryDTO;
import com.cykj.pos.enums.bizstatus.BizStatusContantEnum;
import com.cykj.pos.profit.dto.MessageDTO;
import com.cykj.pos.profit.dto.WalletDTO;
import com.cykj.pos.service.*;
import com.cykj.pos.util.LoginUserUtils;
import com.cykj.system.service.ISysDictDataService;
import com.cykj.system.service.ISysUserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *消息管理Controller
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/pos/api/v2/message")
public class PosV2AppMessageController {


    private final IBizMessageRecordsService messageRecordsService;

    /**
     * 需要传递userId和msgType
     * 通过用户id和消息类型查找消息列表接口
     * @param messageDTO
     * @return
     */
    @PostMapping("/list")
    public AjaxResult list(@RequestBody MessageDTO messageDTO){
        AjaxResult ajaxResult = AjaxResult.success("我的消息列表数据获取成功");
        // 获取消息列表
        List<MessageDTO> messageList = messageRecordsService.getMessageListByUserIdAndType(messageDTO);

        ajaxResult.put("data",messageList);
        return ajaxResult;
    }

    /**
     * 获得单个消息  传入一个msgId
     * @param messageDTO
     * @return
     */
    @PostMapping("/detail")
    public AjaxResult detail(@RequestBody MessageDTO messageDTO){
        AjaxResult ajaxResult = AjaxResult.success("我的消息数据获取成功");
        // 获取单个消息列表
        MessageDTO message = messageRecordsService.getMessageByMgsId(messageDTO);
        ajaxResult.put("data",message);
        return ajaxResult;
    }


}
