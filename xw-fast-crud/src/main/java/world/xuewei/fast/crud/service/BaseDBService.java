package world.xuewei.fast.crud.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import world.xuewei.fast.core.util.VariableNameUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 基础数据库服务类
 *
 * @author XUEW
 * @since 下午9:02 2023/2/12
 */
public class BaseDBService<T> extends ServiceImpl<BaseMapper<T>, T> implements BaseDBInterface<T> {

    private final BaseMapper<T> mapper;

    public BaseDBService(BaseMapper<T> mapper) {
        this.mapper = mapper;
    }

    @Override
    public T saveData(T obj) {
        super.saveOrUpdate(obj);
        return obj;
    }

    @Override
    public List<T> saveBatchData(T[] array) {
        List<T> objList = Arrays.stream(array).collect(Collectors.toList());
        return this.saveBatchData(objList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<T> saveBatchData(Collection<T> list) {
        // 循环新增，若数据量大可自行扩展
        list.forEach(this::saveData);
        return new ArrayList<>(list);
    }

    @Override
    public T insert(T obj) {
        mapper.insert(obj);
        return obj;
    }

    @Override
    public List<T> insertBatch(T[] array) {
        List<T> objList = Arrays.stream(array).collect(Collectors.toList());
        return this.insertBatch(objList);
    }

    @Override
    public List<T> insertBatch(Collection<T> list) {
        super.saveBatch(list);
        return new ArrayList<>(list);
    }

    @Override
    public T update(T obj) {
        mapper.updateById(obj);
        return obj;
    }

    @Override
    public List<T> updateBatch(T[] array) {
        List<T> objList = Arrays.stream(array).collect(Collectors.toList());
        return this.updateBatch(objList);
    }

    @Override
    public List<T> updateBatch(Collection<T> list) {
        super.updateBatchById(list);
        return new ArrayList<>(list);
    }

    @Override
    public int delete(Serializable id) {
        return mapper.deleteById(id);
    }

    @Override
    public int deleteBatch(Serializable[] ids) {
        List<Serializable> idList = Arrays.stream(ids).collect(Collectors.toList());
        return this.deleteBatch(idList);
    }

    @Override
    public int deleteBatch(Collection<? extends Serializable> ids) {
        return mapper.deleteBatchIds(ids);
    }

    @Override
    public int deleteByField(String field, Object value) {
        return mapper.delete(new QueryWrapper<T>().eq(field, value));
    }

    @Override
    public int deleteBatchByField(String field, Object[] values) {
        return mapper.delete(new QueryWrapper<T>().in(field, values));
    }

    @Override
    public int deleteBatchByField(String field, List<Object> values) {
        return this.deleteBatchByField(field, values.toArray());
    }

    @Override
    public T getById(Serializable id) {
        return mapper.selectById(id);
    }

    @Override
    public List<T> getByIds(Serializable[] ids) {
        List<Serializable> idList = Arrays.stream(ids).collect(Collectors.toList());
        return this.getByIds(idList);
    }

    @Override
    public List<T> getByIds(Collection<? extends Serializable> ids) {
        return mapper.selectBatchIds(ids);
    }

    @Override
    public List<T> getAll() {
        return super.list();
    }

    @Override
    public List<T> getByWrapper(Wrapper<T> wrapper) {
        return mapper.selectList(wrapper);
    }

    @Override
    public List<T> getByField(String field, Object value) {
        return mapper.selectList(new QueryWrapper<T>().eq(field, value));
    }

    @Override
    public List<T> getBatchByField(String field, Object[] values) {
        return mapper.selectList(new QueryWrapper<T>().in(field, values));
    }

    @Override
    public List<T> getBatchByField(String field, List<Object> values) {
        return this.getBatchByField(field, values.toArray());
    }

    @Override
    public List<T> getByObj(T obj) {
        if (ObjectUtil.isEmpty(obj)) {
            return getAll();
        }
        Map<String, Object> bean2Map = BeanUtil.beanToMap(obj);
        return getByMap(bean2Map);
    }

    @Override
    public List<T> getByMap(Map<String, Object> map) {
        Map<String, Object> newMap = new HashMap<>();
        for (String key : map.keySet()) {
            if (ObjectUtil.isEmpty(map.get(key))) {
                continue;
            }
            newMap.put(VariableNameUtils.humpToLine(key), map.get(key));
        }
        return mapper.selectByMap(newMap);
    }

    @Override
    public int countAll() {
        return super.count();
    }

    @Override
    public int countByField(String field, Object value) {
        return mapper.selectCount(new QueryWrapper<T>().eq(field, value));
    }

    @Override
    public int countByWrapper(Wrapper<T> wrapper) {
        return mapper.selectCount(wrapper);
    }

    @Override
    public int countByObj(T obj) {
        return this.countByMap(BeanUtil.beanToMap(obj));
    }

    @Override
    public int countByMap(Map<String, Object> map) {
        return mapper.selectCount(createWrapperFromMap(map));
    }

    @Override
    public IPage<T> getByWrapperPage(Wrapper<T> wrapper, IPage<T> page) {
        return mapper.selectPage(page, wrapper);
    }

    @Override
    public IPage<T> getByObjPage(T obj, IPage<T> page) {
        return this.getByMapPage(BeanUtil.beanToMap(obj), page);
    }

    @Override
    public IPage<T> getByMapPage(Map<String, Object> map, IPage<T> page) {
        return this.getByWrapperPage(createWrapperFromMap(map), page);
    }

    /**
     * 通过 Map 创建 QueryWrapper 查询对象
     *
     * @param map 映射
     * @return QueryWrapper
     */
    private QueryWrapper<T> createWrapperFromMap(Map<String, Object> map) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        for (Map.Entry<String, Object> temp : map.entrySet()) {
            String key = temp.getKey();
            if (ObjectUtil.isEmpty(temp.getValue())) {
                continue;
            }
            wrapper.eq(VariableNameUtils.humpToLine(key), map.get(key));
        }
        return wrapper;
    }
}
