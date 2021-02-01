package com.cykj.web.controller.posbiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cykj.common.core.domain.model.LoginUser;
import com.cykj.common.utils.ServletUtils;
import com.cykj.framework.web.service.TokenService;
import com.cykj.pos.profit.dto.MerchantDict;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cykj.common.annotation.Log;
import com.cykj.common.core.controller.BaseController;
import com.cykj.common.core.domain.AjaxResult;
import com.cykj.common.enums.BusinessType;
import com.cykj.pos.domain.BizMerchant;
import com.cykj.pos.service.IBizMerchantService;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.common.core.page.TableDataInfo;

/**
 * 报件商户信息Controller
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/merchant/merchant" )
public class BizMerchartController extends BaseController {

    private final IBizMerchantService iBizMerchantService;

    private final TokenService tokenService;

    /**
     * 查询报件商户信息列表
     */
    @PreAuthorize("@ss.hasPermi('merchant:merchant:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizMerchant bizMerchart) {
        startPage();
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        BizMerchant parentMerchant = iBizMerchantService.getMerchantByUserId(loginUser.getUser().getUserId());
        Long parentId = 1L;
        if(parentMerchant != null) {
            parentId = parentMerchant.getMerchId();
        }
        List<BizMerchant> merchants = iBizMerchantService.getAllSubNodeAndIncludeSelf(parentId,bizMerchart);

        return getDataTable(merchants);
    }

    /**
     * 导出报件商户信息列表
     */
    @PreAuthorize("@ss.hasPermi('merchant:merchant:export')" )
    @Log(title = "报件商户信息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizMerchant bizMerchart) {
        List<BizMerchant> list = iBizMerchantService.queryList(bizMerchart);
        ExcelUtil<BizMerchant> util = new ExcelUtil<BizMerchant>(BizMerchant.class);
        return util.exportExcel(list, "merchant" );
    }

    /**
     * 获取报件商户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:merchant:query')" )
    @GetMapping(value = "/{merchId}" )
    public AjaxResult getInfo(@PathVariable("merchId" ) Long merchId) {
        return AjaxResult.success(iBizMerchantService.getById(merchId));
    }

    /**
     * 新增报件商户信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:merchant:add')" )
    @Log(title = "报件商户信息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizMerchant bizMerchart) {
        return toAjax(iBizMerchantService.save(bizMerchart) ? 1 : 0);
    }

    /**
     * 修改报件商户信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:merchant:edit')" )
    @Log(title = "报件商户信息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizMerchant bizMerchart) {
        return toAjax(iBizMerchantService.updateById(bizMerchart) ? 1 : 0);
    }

    /**
     * 删除报件商户信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:merchant:remove')" )
    @Log(title = "报件商户信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{merchIds}" )
    public AjaxResult remove(@PathVariable Long[] merchIds) {
        return toAjax(iBizMerchantService.removeByIds(Arrays.asList(merchIds)) ? 1 : 0);
    }

    @PreAuthorize("@ss.hasPermi('merchant:merchant:tree')")
    @GetMapping("/tree")
    public AjaxResult treeSelect(BizMerchant merchant){
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        BizMerchant parentMerchant = iBizMerchantService.getMerchantByUserId(loginUser.getUser().getUserId());
        Long parentId = 1L;
        if(parentMerchant != null) {
            parentId = parentMerchant.getMerchId();
        }
        List<BizMerchant> merchants = iBizMerchantService.getAllSubNodeAndIncludeSelf(parentId);
        return AjaxResult.success(iBizMerchantService.buildMerchantTreeSelect(merchants));
    }

    @PreAuthorize("@ss.hasPermi('merchant:merchant:dict')")
    @GetMapping("/dict")
    public AjaxResult getMerchantDict(){
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        BizMerchant parentMerchant = iBizMerchantService.getMerchantByUserId(loginUser.getUser().getUserId());
        Long parentId = 1L;
        if(parentMerchant != null) parentId = parentMerchant.getMerchId();

        List<MerchantDict> dicts = iBizMerchantService.getChildMerchartDictList(parentId);

        return AjaxResult.success(dicts);
    }
}
