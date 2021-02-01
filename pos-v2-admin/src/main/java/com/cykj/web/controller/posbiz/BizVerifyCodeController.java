package com.cykj.web.controller.posbiz;

import com.cykj.common.annotation.Log;
import com.cykj.common.core.controller.BaseController;
import com.cykj.common.core.domain.AjaxResult;
import com.cykj.common.core.page.TableDataInfo;
import com.cykj.common.enums.BusinessType;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.pos.domain.BizVerifyCode;
import com.cykj.pos.service.IBizVerifyCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 短信验证码Controller
 *
 * @author ningbingwu
 * @date 2021-01-18
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/verifycode/verifycode" )
public class BizVerifyCodeController extends BaseController {

    private final IBizVerifyCodeService iBizVerifyCodeService;

    /**
     * 查询短信验证码列表
     */
    @PreAuthorize("@ss.hasPermi('verifycode:verifycode:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizVerifyCode bizVerifyCode) {
        startPage();
        List<BizVerifyCode> list = iBizVerifyCodeService.queryList(bizVerifyCode);
        return getDataTable(list);
    }

    /**
     * 导出短信验证码列表
     */
    @PreAuthorize("@ss.hasPermi('verifycode:verifycode:export')" )
    @Log(title = "短信验证码" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizVerifyCode bizVerifyCode) {
        List<BizVerifyCode> list = iBizVerifyCodeService.queryList(bizVerifyCode);
        ExcelUtil<BizVerifyCode> util = new ExcelUtil<BizVerifyCode>(BizVerifyCode.class);
        return util.exportExcel(list, "verifycode" );
    }

    /**
     * 获取短信验证码详细信息
     */
    @PreAuthorize("@ss.hasPermi('verifycode:verifycode:query')" )
    @GetMapping(value = "/{verId}" )
    public AjaxResult getInfo(@PathVariable("verId" ) Long verId) {
        return AjaxResult.success(iBizVerifyCodeService.getById(verId));
    }

    /**
     * 新增短信验证码
     */
    @PreAuthorize("@ss.hasPermi('verifycode:verifycode:add')" )
    @Log(title = "短信验证码" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizVerifyCode bizVerifyCode) {
        return toAjax(iBizVerifyCodeService.save(bizVerifyCode) ? 1 : 0);
    }

    /**
     * 修改短信验证码
     */
    @PreAuthorize("@ss.hasPermi('verifycode:verifycode:edit')" )
    @Log(title = "短信验证码" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizVerifyCode bizVerifyCode) {
        return toAjax(iBizVerifyCodeService.updateById(bizVerifyCode) ? 1 : 0);
    }

    /**
     * 删除短信验证码
     */
    @PreAuthorize("@ss.hasPermi('verifycode:verifycode:remove')" )
    @Log(title = "短信验证码" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{verIds}" )
    public AjaxResult remove(@PathVariable Long[] verIds) {
        return toAjax(iBizVerifyCodeService.removeByIds(Arrays.asList(verIds)) ? 1 : 0);
    }
}
