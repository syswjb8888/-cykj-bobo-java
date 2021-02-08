package com.cykj.pos.controller.api;

import com.cykj.common.core.domain.AjaxResult;
import com.cykj.common.core.domain.entity.SysDictData;
import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.domain.BizPosMachine;
import com.cykj.pos.domain.dto.MachineTransferDTO;
import com.cykj.pos.profit.dto.OperationRecordsDTO;
import com.cykj.pos.profit.dto.PosTerminalDTO;
import com.cykj.pos.service.IBizAllocAdjRecordsService;
import com.cykj.pos.service.IBizMerchantService;
import com.cykj.pos.service.IBizPosMachineService;
import com.cykj.pos.util.LoginUserUtils;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/pos/api/v2/terminal")
public class PosV2AppTerminalController {

    private final IBizMerchantService merchantService;

    private final IBizPosMachineService posMachineService;

    private final IBizAllocAdjRecordsService allocAdjRecordsService;

    @ApiOperation(value = "获取用户终端统计数据")
    @ApiResponses({@ApiResponse(code = 200, response = PosTerminalDTO.class, message = "获取用户终端统计数据响应成功")})
    @PostMapping("/counts")
    public AjaxResult terminalManagePageData(@RequestBody PosTerminalDTO terminalDTO) {
        AjaxResult ajaxResult = AjaxResult.success();
        terminalDTO.setUserId(LoginUserUtils.getLoginUserId());
        BizMerchant merchant = merchantService.getMerchantByUserId(terminalDTO.getUserId());
        Long merchantId = merchant.getMerchId();
        Long terminalCounts = posMachineService.getPosMachineCountsByMerchId(merchantId);
        Long terminalActivatedCounts = posMachineService.getPosMachineActivatedCountsByMerchId(merchantId);
        Long noActivatedCounts = terminalCounts - terminalActivatedCounts;
        terminalDTO.setTerminalCounts(terminalCounts);
        terminalDTO.setTerminalActivatedCounts(terminalActivatedCounts);
        terminalDTO.setTerminalNoActivateCounts(noActivatedCounts);
        terminalDTO.setMerchId(merchantId);
        ajaxResult.put("data", terminalDTO);
        return ajaxResult;
    }

    @ApiOperation(value = "获取用户终端列表")
    @ApiImplicitParams({@ApiImplicitParam(name="posActivateStatus",value = "终端激活状态,0-未激活,1-已激活",dataType = "string",required = false,paramType="body"),
            @ApiImplicitParam(name="operType",value = "操作类型,传值为:任意整型值，仅划拔时需要该参数",dataType = "string",required = false,paramType="body"),
            @ApiImplicitParam(name="posType",value = "品牌类型,如拉卡拉等，筛选条件，多个用英文逗号分隔",dataType = "string",required = false,paramType="body"),
            @ApiImplicitParam(name="posMachineType",value = "机具类型,如传统POS等，筛选条件，多个用英文逗号分隔",dataType = "string",required = false,paramType="body"),
            @ApiImplicitParam(name="pageNo",value = "页号，默认传-1表示不分页",dataType = "int",required = true,paramType="body",defaultValue = "1"),
            @ApiImplicitParam(name="pageSize",value = "页大小，默认传-1表示不分页",dataType = "int",required = true,paramType="body",defaultValue = "10")})
    @ApiResponses({@ApiResponse(code = 200, response = BizPosMachine.class, message = "获取用户终端列表数据响应成功"),})
    @PostMapping("/list")
    public AjaxResult queryTerminalList(@RequestBody PosTerminalDTO terminalDTO) {

        AjaxResult ajaxResult = AjaxResult.success();
        terminalDTO.setUserId(LoginUserUtils.getLoginUserId());
        BizMerchant merchant = merchantService.getMerchantByUserId(terminalDTO.getUserId());
        Long merchantId = merchant.getMerchId();
        List<MachineTransferDTO> terminalList =
                posMachineService.getPagePosMachinesByMerchId(merchantId, terminalDTO,
                        terminalDTO.getPageNo(), terminalDTO.getPageSize());
        ajaxResult.put("data", terminalList);
        return ajaxResult;
    }

    @ApiOperation(value = "区间查询用户终端列表")
    @ApiImplicitParams({@ApiImplicitParam(name="posCodeStart",value = "区间起始SN系列号",dataType = "string",required = true,paramType="body"),
            @ApiImplicitParam(name="posCodeEnd",value = "区间结束SN系列号",dataType = "string",required = true,paramType="body"),
            @ApiImplicitParam(name="posActivateStatus",value = "终端激活状态，0-未激活，1-已激活",dataType = "string",required = true,paramType="body")})
    @ApiResponses({@ApiResponse(code = 200, response = BizPosMachine.class, message = "区间查询用户终端列表数据响应成功")})
    @PostMapping("/interval/list")
    public AjaxResult queryTerminalIntervalList(@RequestBody PosTerminalDTO terminalDTO) {
        AjaxResult ajaxResult = AjaxResult.success();
        terminalDTO.setUserId(LoginUserUtils.getLoginUserId());
        List<BizPosMachine> terminalList = posMachineService.getPosMachinesByInteval(terminalDTO);
        ajaxResult.put("data", terminalList);
        return ajaxResult;
    }

    @ApiOperation(value = "终端划拔回调操作")
    @ApiImplicitParams({@ApiImplicitParam(name="merchId",value = "目标商户Id",dataType = "int",required = true,paramType="body"),
            @ApiImplicitParam(name="operType",value = "操作类型，1-划拔，2-回调",dataType = "int",required = true,paramType="body"),
            @ApiImplicitParam(name="posIds",value = "划拔或回调终端Id(posId)列表",dataType = "long",required = true,paramType="body")})
    @ApiResponses({@ApiResponse(code = 200, response = PosTerminalDTO.class, message = "终端划拔回调操作成功")})
    @PostMapping("/operations")
    public AjaxResult allocateTerminalList(@RequestBody PosTerminalDTO terminalDTO) {
        AjaxResult ajaxResult = AjaxResult.success();
        List<Long> posIdList = terminalDTO.getPosIds();
        Long merchId = terminalDTO.getMerchId();
        Long userId = LoginUserUtils.getLoginUserId();
        Integer operateType = terminalDTO.getOperType();
        //回调操作时，目标商户即为操作商户本身
        if(2 == operateType){
            merchId = merchantService.getMerchantByUserId(userId).getMerchId();
        }
        Integer counts = posMachineService.doTheOperations(posIdList,merchId ,userId,operateType);
        Map<String, Integer> responseData = new HashMap<>();
        responseData.put("counts", counts);
        ajaxResult.put("data", responseData);
        return ajaxResult;
    }

    @ApiOperation(value = "终端划拔回调简要记录")
    @ApiImplicitParams({@ApiImplicitParam(name="operType",value = "操作类型，1-划拔，2-回调",dataType = "int",required = true,paramType="body")})
    @ApiResponses({@ApiResponse(code = 200, response= OperationRecordsDTO.class, message = "划拔回调记录数据响应成功")})
    @PostMapping("/operations/brief/records")
    public AjaxResult briefOperationsRecordsList(@RequestBody PosTerminalDTO terminalDTO) {
        AjaxResult ajaxResult = AjaxResult.success();
        terminalDTO.setUserId(LoginUserUtils.getLoginUserId());
        ajaxResult.put("data", allocAdjRecordsService.getCurrentYearBriefOperationRecordsList(terminalDTO));
        return ajaxResult;
    }

    @ApiOperation(value = "终端划拔回调记录")
    @ApiImplicitParams({@ApiImplicitParam(name="userId",value = "操作划拔回调用户Id",dataType = "long",required = true,paramType="body"),
            @ApiImplicitParam(name="operType",value = "操作类型，1-划拔，2-回调",dataType = "int",required = true,paramType="body"),
            @ApiImplicitParam(name="pageNo",value = "页号",dataType = "int",required = false,paramType="body",defaultValue = "1"),
            @ApiImplicitParam(name="pageSize",value = "每页大小",dataType = "int",required = false,paramType="body",defaultValue = "10")})
    @ApiResponses({@ApiResponse(code = 200, response = PosTerminalDTO.class, message = "划拔回调记录数据响应成功")})
    @PostMapping("/operations/records")
    public AjaxResult operationsRecordsList(@RequestBody PosTerminalDTO terminalDTO) {
        AjaxResult ajaxResult = AjaxResult.success();
        terminalDTO.setUserId(LoginUserUtils.getLoginUserId());
        ajaxResult.put("data", allocAdjRecordsService.getPageOperationRecordsList(terminalDTO));
        ajaxResult.put("counts", allocAdjRecordsService.geOperationRecordsCounts(terminalDTO));
        return ajaxResult;
    }

    @ApiOperation(value = "终端查询筛选条件")
    @ApiResponses({@ApiResponse(code = 200, response = SysDictData.class, message = "终端查询筛选条件数据响应成功")})
    @PostMapping("/filter/conditions")
    public AjaxResult filterConditions() {
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("data",posMachineService.getPosFilterConditions());
        return ajaxResult;
    }
}
