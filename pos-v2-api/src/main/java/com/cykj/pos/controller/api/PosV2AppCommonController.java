package com.cykj.pos.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cykj.common.core.domain.AjaxResult;
import com.cykj.common.core.domain.entity.SysDictData;
import com.cykj.common.core.domain.entity.SysDictType;
import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.enums.bizstatus.BizStatusContantEnum;
import com.cykj.pos.profit.dto.PosTerminalDTO;
import com.cykj.pos.profit.dto.SysDictTypeDTO;
import com.cykj.pos.service.IBizMerchantService;
import com.cykj.pos.service.IBizVerifyCodeService;
import com.cykj.pos.util.LoginUserUtils;
import com.cykj.system.service.ISysDictDataService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/pos/api/v2/common")
public class PosV2AppCommonController {

    private final ISysDictDataService iSysDictDataService;

    private  final IBizVerifyCodeService iBizVerifyCodeService;

    @ApiOperation(value = "数据字典查询")
    @ApiImplicitParams({@ApiImplicitParam(name="dictType",value = "具体查看系统数据字典",dataType = "string",required = true,paramType="body"),
    @ApiImplicitParam(name="dictLevelCode",value = "字典值层级码，用于地区联动查询，查询市级数据字典，传省级前两位；查询县市级数据字典，传市级前三位",dataType = "string",required = false,paramType="body")})
    @ApiResponses({@ApiResponse(code = 200, response = SysDictData.class, message = "获取数据字典响应成功")})
    @PostMapping("/dictdata/list")
    public AjaxResult getDictData(@RequestBody SysDictTypeDTO dictTypeDTO){
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("data",iSysDictDataService.selectSysDictData(dictTypeDTO.getDictType(),dictTypeDTO.getDictLevelCode()));
        return  ajaxResult;
    }

    @ApiOperation(value="APP用户发送短信验证码")
    @ApiImplicitParams({@ApiImplicitParam(name="mobile",value = "路径参数1：合法手机号码",dataType = "long",required = true,paramType="body")})
    @ApiResponses({@ApiResponse(code=200,response = AjaxResult.class,message = "业务数据响应成功")})
    @GetMapping("/verifyCode/sender/{mobile}")
    public AjaxResult verifyCodeSender(@PathVariable String mobile){
        Long userId = LoginUserUtils.getLoginUserId();
        if(userId == null){
            return AjaxResult.error("用户非法");
        }
        BizStatusContantEnum bizStatus = iBizVerifyCodeService.sendVerifyCode(mobile, userId);
        if(bizStatus == BizStatusContantEnum.BIZ_SUCCESS)return AjaxResult.success("短信验证码发送成功");
        return AjaxResult.error(bizStatus.getName());
    }
}
