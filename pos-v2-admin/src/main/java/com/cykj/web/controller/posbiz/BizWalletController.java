package com.cykj.web.controller.posbiz;

import java.util.List;
import java.util.Arrays;

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
import com.cykj.pos.domain.BizWallet;
import com.cykj.pos.service.IBizWalletService;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.common.core.page.TableDataInfo;

/**
 * 商户钱包Controller
 *
 * @author ningbingwu
 * @date 2021-01-11
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/merchant/wallet" )
public class BizWalletController extends BaseController {

    private final IBizWalletService iBizWalletService;

    /**
     * 查询商户钱包列表
     */
    @PreAuthorize("@ss.hasPermi('merchant:wallet:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizWallet bizWallet) {
        startPage();
        List<BizWallet> list = iBizWalletService.queryList(bizWallet);
        return getDataTable(list);
    }

    /**
     * 导出商户钱包列表
     */
    @PreAuthorize("@ss.hasPermi('merchant:wallet:export')" )
    @Log(title = "商户钱包" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizWallet bizWallet) {
        List<BizWallet> list = iBizWalletService.queryList(bizWallet);
        ExcelUtil<BizWallet> util = new ExcelUtil<BizWallet>(BizWallet.class);
        return util.exportExcel(list, "wallet" );
    }

    /**
     * 获取商户钱包详细信息
     */
    @PreAuthorize("@ss.hasPermi('merchant:wallet:query')" )
    @GetMapping(value = "/{walletId}" )
    public AjaxResult getInfo(@PathVariable("walletId" ) Long walletId) {
        return AjaxResult.success(iBizWalletService.getById(walletId));
    }

    /**
     * 新增商户钱包
     */
    @PreAuthorize("@ss.hasPermi('merchant:wallet:add')" )
    @Log(title = "商户钱包" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizWallet bizWallet) {
        return toAjax(iBizWalletService.save(bizWallet) ? 1 : 0);
    }

    /**
     * 修改商户钱包
     */
    @PreAuthorize("@ss.hasPermi('merchant:wallet:edit')" )
    @Log(title = "商户钱包" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizWallet bizWallet) {
        return toAjax(iBizWalletService.updateById(bizWallet) ? 1 : 0);
    }

    /**
     * 删除商户钱包
     */
    @PreAuthorize("@ss.hasPermi('merchant:wallet:remove')" )
    @Log(title = "商户钱包" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{walletIds}" )
    public AjaxResult remove(@PathVariable Long[] walletIds) {
        return toAjax(iBizWalletService.removeByIds(Arrays.asList(walletIds)) ? 1 : 0);
    }
}
