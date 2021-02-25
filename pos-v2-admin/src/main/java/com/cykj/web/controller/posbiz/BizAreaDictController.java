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
import com.cykj.pos.domain.BizAreaDict;
import com.cykj.pos.service.IBizAreaDictService;
import com.cykj.common.utils.poi.ExcelUtil;
import com.cykj.common.core.page.TableDataInfo;

/**
 * 地区管理Controller
 *
 * @author weijianbo
 * @date 2021-02-23
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/area/dict" )
public class BizAreaDictController extends BaseController {

    private final IBizAreaDictService iBizAreaDictService;

    /**
     * 查询地区管理列表
     */
    @PreAuthorize("@ss.hasPermi('area:dict:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizAreaDict bizAreaDict) {
        startPage();
        List<BizAreaDict> list = iBizAreaDictService.queryList(bizAreaDict);
        return getDataTable(list);
    }

    /**
     * 导出地区管理列表
     */
    @PreAuthorize("@ss.hasPermi('area:dict:export')" )
    @Log(title = "地区管理" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(BizAreaDict bizAreaDict) {
        List<BizAreaDict> list = iBizAreaDictService.queryList(bizAreaDict);
        ExcelUtil<BizAreaDict> util = new ExcelUtil<BizAreaDict>(BizAreaDict.class);
        return util.exportExcel(list, "dict" );
    }

    /**
     * 获取地区管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('area:dict:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult getInfo(@PathVariable("id" ) Integer id) {
        return AjaxResult.success(iBizAreaDictService.getById(id));
    }

    /**
     * 新增地区管理
     */
    @PreAuthorize("@ss.hasPermi('area:dict:add')" )
    @Log(title = "地区管理" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizAreaDict bizAreaDict) {
        return toAjax(iBizAreaDictService.save(bizAreaDict) ? 1 : 0);
    }

    /**
     * 修改地区管理
     */
    @PreAuthorize("@ss.hasPermi('area:dict:edit')" )
    @Log(title = "地区管理" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizAreaDict bizAreaDict) {
        return toAjax(iBizAreaDictService.updateById(bizAreaDict) ? 1 : 0);
    }

    /**
     * 删除地区管理
     */
    @PreAuthorize("@ss.hasPermi('area:dict:remove')" )
    @Log(title = "地区管理" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(iBizAreaDictService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }
}
