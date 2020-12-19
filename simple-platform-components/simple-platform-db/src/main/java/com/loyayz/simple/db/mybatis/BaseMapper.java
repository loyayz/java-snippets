package com.loyayz.simple.db.mybatis;

import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.loyayz.simple.db.Sorter;
import com.loyayz.simple.db.mybatis.extension.MybatisConstants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
public interface BaseMapper<T> extends Mapper<T> {

    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     */
    int insert(T entity);

    /**
     * 批量插入
     */
    int batchInsert(List<T> entities);

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    int deleteById(Serializable id);

    /**
     * 删除（根据ID 批量删除）
     *
     * @param ids 主键ID列表(不能为 null 以及 empty)
     */
    int deleteByIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> ids);

    /**
     * 根据 ID 修改
     *
     * @param entity 实体对象
     */
    int updateById(@Param(Constants.ENTITY) T entity);

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    T findById(Serializable id);

    /**
     * 查询（根据ID 批量查询）
     *
     * @param ids     主键ID列表(不能为 null 以及 empty)
     * @param sorters 排序
     */
    List<T> listByIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> ids,
                      @Param(MybatisConstants.CONDITION_SORTER) Sorter... sorters);

    /**
     * 根据 entity 条件查询
     *
     * @param entity  实体对象（可以为 null）
     * @param sorters 排序
     */
    List<T> listByCondition(@Param(Constants.ENTITY) T entity,
                            @Param(MybatisConstants.CONDITION_SORTER) Sorter... sorters);

    /**
     * 根据 entity 条件，查询总记录数
     *
     * @param entity 实体对象（可以为 null）
     */
    Integer countByCondition(@Param(Constants.ENTITY) T entity);

    /**
     * 根据 entity 条件，查询是否存在记录
     *
     * @param entity 实体对象（可以为 null）
     */
    default Boolean existByCondition(T entity) {
        Integer num = this.countByCondition(entity);
        return num != null && num > 0;
    }

}
