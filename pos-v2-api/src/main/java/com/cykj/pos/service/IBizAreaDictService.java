package com.cykj.pos.service;

import com.cykj.pos.domain.BizAreaDict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cykj.pos.profit.dto.SysDictTypeDTO;

import java.util.List;

/**
 * 地区管理Service接口
 *
 * @author weijianbo
 * @date 2021-02-23
 */
public interface IBizAreaDictService extends IService<BizAreaDict> {

    /**
     * 查询列表
     */
    List<BizAreaDict> queryList(BizAreaDict bizAreaDict);

    /**
     * 根据父id查询数据
     * @param dictTypeDTO
     * @return
     */
    List<BizAreaDict> getAreaDictDataListByPid(SysDictTypeDTO dictTypeDTO);
}
