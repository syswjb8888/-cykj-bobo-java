package com.cykj.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cykj.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.cykj.common.core.domain.entity.SysDictData;
import com.cykj.common.utils.DictUtils;
import com.cykj.system.mapper.SysDictDataMapper;
import com.cykj.system.service.ISysDictDataService;

/**
 * 字典 业务层处理
 *
 * @author cykj
 */
@Service
public class SysDictDataServiceImpl implements ISysDictDataService{

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData)
    {
        return dictDataMapper.selectDictDataList(dictData);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue)
    {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public SysDictData selectDictDataById(Long dictCode)
    {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    /**
     * 批量删除字典数据信息
     *
     * @param dictCodes 需要删除的字典数据ID
     * @return 结果
     */
    @Override
    public int deleteDictDataByIds(Long[] dictCodes)
    {
        int row = dictDataMapper.deleteDictDataByIds(dictCodes);
        if (row > 0)
        {
            DictUtils.clearDictCache();
        }
        return row;
    }

    /**
     * 新增保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    public int insertDictData(SysDictData dictData)
    {
        int row = dictDataMapper.insertDictData(dictData);
        if (row > 0)
        {
            DictUtils.clearDictCache();
        }
        return row;
    }

    /**
     * 修改保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    public int updateDictData(SysDictData dictData)
    {
        int row = dictDataMapper.updateDictData(dictData);
        if (row > 0)
        {
            DictUtils.clearDictCache();
        }
        return row;
    }
    public List<SysDictData> selectSysDictData(String dictType,String dictLevelCode){
        LambdaQueryWrapper<SysDictData> merchantQuery = Wrappers.lambdaQuery();
        merchantQuery.eq(SysDictData::getDictType ,dictType);
        Object[] params = new Object[]{dictType};
        String sql = "select * from sys_dict_data where dict_type=? order by dict_sort";
        if(StringUtils.isNotBlank(dictLevelCode)){
            params = new Object[]{dictType,dictLevelCode};
            sql = "select * from sys_dict_data where dict_type=? and dict_value like concat(?,'%') order by dict_sort";
        }
        return jdbcTemplate.query(sql,params, new BeanPropertyRowMapper<>(SysDictData.class));
    }
}
