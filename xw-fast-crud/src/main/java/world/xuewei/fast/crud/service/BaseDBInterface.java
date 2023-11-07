package world.xuewei.fast.crud.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 基础服务抽象接口
 *
 * @author XUEW
 * @since 2023/9/1 16:14
 */
public interface BaseDBInterface<T> extends IService<T> {

    /**
     * 保存实体，若传入对象 id 字段不为空则更新对象，若为空则新增对象。
     *
     * @param obj 实体对象
     * @return 实体对象
     */
    T saveData(T obj);

    /**
     * 批量保存实体，若传入对象 id 字段不为空则更新对象，若为空则新增对象。
     *
     * @param array 实体对象数组
     * @return 实体对象列表
     */
    List<T> saveBatchData(T[] array);

    /**
     * 批量保存实体，若传入对象 id 字段不为空则更新对象，若为空则新增对象。
     *
     * @param list 实体对象集合
     * @return 实体对象列表
     */
    List<T> saveBatchData(Collection<T> list);

    /**
     * 新增实体
     *
     * @param obj 实体对象
     * @return 实体对象
     */
    T insert(T obj);

    /**
     * 批量新增实体
     *
     * @param array 实体对象数组
     * @return 实体对象列表
     */
    List<T> insertBatch(T[] array);

    /**
     * 批量新增实体
     *
     * @param list 实体对象集合
     * @return 实体对象列表
     */
    List<T> insertBatch(Collection<T> list);

    /**
     * 更新实体，更新操作要求 id 不为空
     *
     * @param obj 实体对象
     * @return 实体对象
     */
    T update(T obj);

    /**
     * 批量更新实体
     *
     * @param array 实体对象数组
     * @return 实体对象列表
     */
    List<T> updateBatch(T[] array);

    /**
     * 批量更新实体
     *
     * @param list 实体对象集合
     * @return 实体对象列表
     */
    List<T> updateBatch(Collection<T> list);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int delete(Serializable id);

    /**
     * 批量通过主键删除
     *
     * @param ids 主键数组
     * @return 影响行数
     */
    int deleteBatch(Serializable[] ids);

    /**
     * 批量通过主键数据
     *
     * @param ids 主键集合
     * @return 影响行数
     */
    int deleteBatch(Collection<? extends Serializable> ids);

    /**
     * 通过指定字段及对应值删除数据
     *
     * @param field 字段名称
     * @param value 值
     * @return 影响行数
     */
    int deleteByField(String field, Object value);

    /**
     * 批量通过指定字段及对应值删除数据
     *
     * @param field  字段名称
     * @param values 值
     * @return 影响行数
     */
    int deleteBatchByField(String field, Object[] values);

    /**
     * 批量通过指定字段及对应值删除数据
     *
     * @param field  字段名称
     * @param values 值
     * @return 影响行数
     */
    int deleteBatchByField(String field, List<Object> values);

    /**
     * 通过主键查询
     *
     * @param id 主键
     * @return 实体对象
     */
    T getById(Serializable id);

    /**
     * 根据主键查询
     *
     * @param ids 主键数组
     * @return 结果列表
     */
    List<T> getByIds(Serializable[] ids);

    /**
     * 根据主键查询
     *
     * @param ids 主键集合
     * @return 结果列表
     */
    List<T> getByIds(Collection<? extends Serializable> ids);

    /**
     * 查询全部
     *
     * @return 结果列表
     */
    List<T> getAll();

    /**
     * 根据查询条件查询
     *
     * @param wrapper 查询条件
     * @return 结果列表
     */
    List<T> getByWrapper(Wrapper<T> wrapper);

    /**
     * 根据指定字段和值查询
     *
     * @param field 字段名称
     * @param value 值
     * @return 结果列表
     */
    List<T> getByField(String field, Object value);

    /**
     * 根据指定字段和值查询
     *
     * @param field  字段名称
     * @param values 值
     * @return 结果列表
     */
    List<T> getBatchByField(String field, Object[] values);

    /**
     * 根据指定字段和值查询
     *
     * @param field  字段名称
     * @param values 值
     * @return 结果列表
     */
    List<T> getBatchByField(String field, List<Object> values);

    /**
     * 根据实体查询数据集合
     *
     * @param obj 实体对象
     * @return 结果列表
     */
    List<T> getByObj(T obj);

    /**
     * 根据 Map 查询数据集合
     *
     * @param map 字段映射
     * @return 结果列表
     */
    List<T> getByMap(Map<String, Object> map);

    /**
     * 计数全部
     *
     * @return 结果列表
     */
    int countAll();

    /**
     * 根据指定字段和值计数
     *
     * @param field 字段名称
     * @param value 值
     * @return 结果列表
     */
    int countByField(String field, Object value);

    /**
     * 根据查询条件计数
     *
     * @param wrapper 查询条件
     * @return 结果列表
     */
    int countByWrapper(Wrapper<T> wrapper);

    /**
     * 根据实体计数数据集合
     *
     * @param obj 实体对象
     * @return 结果列表
     */
    int countByObj(T obj);

    /**
     * 根据 Map 计数数据集合
     *
     * @param map 字段映射
     * @return 结果列表
     */
    int countByMap(Map<String, Object> map);

    /**
     * 根据实体分页查询数据集合
     *
     * @param wrapper 查询条件
     * @param page    分页对象
     * @return 结果
     */
    IPage<T> getByWrapperPage(Wrapper<T> wrapper, IPage<T> page);

    /**
     * 根据实体分页查询数据集合
     *
     * @param obj  实体对象
     * @param page 分页对象
     * @return 结果
     */
    IPage<T> getByObjPage(T obj, IPage<T> page);

    /**
     * 根据实体分页查询数据集合
     *
     * @param map  字段映射
     * @param page 分页对象
     * @return 结果
     */
    IPage<T> getByMapPage(Map<String, Object> map, IPage<T> page);
}