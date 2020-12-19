package com.loyayz.simple.db.mybatis;

import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.loyayz.simple.db.Sorter;
import com.loyayz.simple.db.mybatis.extension.MybatisConstants;
import com.loyayz.simple.db.mybatis.extension.MybatisUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
public abstract class BaseTable<T extends BaseTable> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 新增或修改
     */
    public void save() {
        boolean updated = false;
        if (pkVal() != null) {
            updated = this.updateById();
        }
        if (!updated) {
            this.insert();
        }
    }

    /**
     * 插入（字段选择插入）
     */
    public boolean insert() {
        return MybatisUtils.executeInsert(getClass(), MybatisConstants.METHOD_INSERT, this);
    }

    /**
     * 批量插入
     */
    public boolean insert(List<T> entities) {
        return MybatisUtils.executeInsert(getClass(), MybatisConstants.METHOD_BATCH_INSERT, entities);
    }

    /**
     * 根据主键删除
     */
    public boolean deleteById() {
        return deleteById(pkVal());
    }

    public boolean deleteById(Serializable id) {
        Assert.isFalse(StringUtils.checkValNull(id), "deleteById primaryKey is null.");
        return MybatisUtils.executeDelete(getClass(), MybatisConstants.METHOD_DELETE_BY_ID, id);
    }

    /**
     * 根据主键删除
     */
    public boolean deleteByIds(Collection<Serializable> ids) {
        Assert.isFalse(CollectionUtils.isEmpty(ids), "deleteByIds primaryKeys is empty.");
        Map<String, Object> map = new HashMap<>(2);
        map.put(Constants.COLLECTION, ids);
        return MybatisUtils.executeDelete(getClass(), MybatisConstants.METHOD_DELETE_BY_IDS, map);
    }

    /**
     * 根据主键更新（字段选择更新）
     */
    public boolean updateById() {
        return updateById(pkVal());
    }

    public boolean updateById(Serializable id) {
        Assert.isFalse(StringUtils.checkValNull(id), "updateById primaryKey is null.");
        Map<String, Object> map = new HashMap<>(2);
        map.put(Constants.ENTITY, this);
        return MybatisUtils.executeUpdate(getClass(), MybatisConstants.METHOD_UPDATE_BY_ID, map);
    }

    /**
     * 根据主键查询
     */
    public T findById() {
        return findById(pkVal());
    }

    public T findById(Serializable id) {
        Assert.isFalse(StringUtils.checkValNull(id), "findById primaryKey is null.");
        return MybatisUtils.executeSelectOne(getClass(), MybatisConstants.METHOD_FIND_BY_ID, id);
    }

    /**
     * 列表：根据 ids 查询
     */
    public List<T> listByIds(Collection<Serializable> ids, Sorter... sorters) {
        Map<String, Object> map = new HashMap<>(3);
        map.put(Constants.COLLECTION, ids);
        map.put(MybatisConstants.CONDITION_SORTER, sorters);
        return MybatisUtils.executeSelectList(getClass(), MybatisConstants.METHOD_LIST_BY_IDS, map);
    }

    /**
     * 列表：根据字段值查询
     */
    public List<T> listByCondition(Sorter... sorters) {
        Map<String, Object> map = new HashMap<>(3);
        map.put(Constants.ENTITY, this);
        map.put(MybatisConstants.CONDITION_SORTER, sorters);
        return MybatisUtils.executeSelectList(getClass(), MybatisConstants.METHOD_LIST_BY_CONDITION, map);
    }

    /**
     * 查询总数
     */
    public Integer countByCondition() {
        Map<String, Object> map = new HashMap<>(2);
        map.put(Constants.ENTITY, this);
        Integer total = MybatisUtils.executeSelectOne(getClass(), MybatisConstants.METHOD_COUNT_BY_CONDITION, map);
        return SqlHelper.retCount(total);
    }

    /**
     * 是否存在
     */
    public Boolean existByCondition() {
        Integer num = this.countByCondition();
        return num != null && num > 0;
    }

    /**
     * 主键值
     */
    public Serializable pkVal() {
        return (Serializable) ReflectionKit.getFieldValue(this, TableInfoHelper.getTableInfo(getClass()).getKeyProperty());
    }

}
