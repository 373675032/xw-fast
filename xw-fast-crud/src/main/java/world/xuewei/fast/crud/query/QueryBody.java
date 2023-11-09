package world.xuewei.fast.crud.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import world.xuewei.fast.core.exception.BusinessRunTimeException;
import world.xuewei.fast.core.util.Assert;
import world.xuewei.fast.core.util.VariableNameUtils;


import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义查询体
 *
 * @author XUEW
 * @since 2023/9/4 14:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryBody<T> {

    /**
     * 关键词查询内容
     */
    private String keyword;

    /**
     * 关键字查询字段
     */
    private List<String> keywordFields;

    /**
     * 结果返回指定字段
     */
    private List<String> includeFields;

    /**
     * 查询条件
     */
    private List<QueryCondition> conditions;

    /**
     * 排序信息
     */
    private List<QueryOrder> orderBy;

    /**
     * 分页信息
     */
    private QueryPage pageBy;

    /**
     * 参数合法性检查
     */
    public void check() {
        if (Assert.notEmpty(keyword) && Assert.isEmpty(keywordFields)) {
            throw new BusinessRunTimeException("关键词查询时必须指定 keywordFields，且必须指定为字符类型字段");
        }
        if (Assert.notEmpty(conditions)) {
            conditions.forEach(QueryCondition::check);
        }
        if (Assert.notEmpty(orderBy)) {
            orderBy.forEach(QueryOrder::check);
        }
    }

    /**
     * 构建 QueryWrapper
     *
     * @return QueryWrapper
     */
    public QueryWrapper<T> buildWrapper() {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        // 关键词
        if (Assert.notEmpty(keywordFields) && Assert.notEmpty(keyword)) {
            wrapper.and(orWrapper -> {
                for (String keywordField : keywordFields) {
                    orWrapper.like(VariableNameUtils.humpToLine(keywordField), keyword).or();
                }
            });
        }
        // 指定查询字段
        if (Assert.notEmpty(includeFields)) {
            String[] array = includeFields.stream().map(VariableNameUtils::humpToLine).collect(Collectors.toList()).toArray(new String[]{});
            wrapper.select(array);
        }
        // 查询条件
        if (Assert.notEmpty(conditions)) {
            for (QueryCondition condition : conditions) {
                String field = VariableNameUtils.humpToLine(condition.getField());
                String type = condition.getType();
                Object value = condition.getValue();
                if ("ALL_DATA".equals(value)) {
                    continue;
                }
                switch (type) {
                    case "eq": // 等于 =
                        wrapper.eq(field, value);
                        break;
                    case "ne": // 不等于 <>
                        wrapper.ne(field, value);
                        break;
                    case "gt": // 大于 >
                        wrapper.gt(field, value);
                        break;
                    case "ge": // 大于等于 >=
                        wrapper.ge(field, value);
                        break;
                    case "lt": // 小于 <
                        wrapper.lt(field, value);
                        break;
                    case "le": // 小于等于 <=
                        wrapper.le(field, value);
                        break;
                    case "contain": // LIKE '%值%'
                        wrapper.like(field, value);
                        break;
                    case "notContain": // NOT LIKE '%值%'
                        wrapper.notLike(field, value);
                        break;
                    case "startWith": // LIKE '%值'
                        wrapper.likeLeft(field, value);
                        break;
                    case "endWith": // LIKE '值%'
                        wrapper.likeRight(field, value);
                        break;
                    case "isNull": // 字段 IS NULL
                        wrapper.isNull(field);
                        break;
                    case "isNotNull": // 字段 IS NOT NULL
                        wrapper.isNotNull(field);
                        break;
                    case "in": // 字段 IN (value.get(0), value.get(1), ...)
                        try {
                            wrapper.in(field, parseValue2List(value).toArray());
                        } catch (JsonProcessingException e) {
                            throw new BusinessRunTimeException("in 操作的值必须为数组");
                        }
                        break;
                    case "notIn": // 字段 NOT IN (value.get(0), value.get(1), ...)
                        try {
                            wrapper.notIn(field, parseValue2List(value).toArray());
                        } catch (JsonProcessingException e) {
                            throw new BusinessRunTimeException("notIn 操作的值必须为数组");
                        }
                        break;
                    case "between": // BETWEEN 值1 AND 值2
                        try {
                            List<Object> betweenList = parseValue2List(value);
                            if (betweenList.size() != 2) {
                                throw new BusinessRunTimeException("between 操作的值必须为数组且长度为 2");
                            }
                            wrapper.between(field, betweenList.get(0), betweenList.get(1));
                        } catch (JsonProcessingException e) {
                            throw new BusinessRunTimeException("between 操作的值必须为数组且长度为 2");
                        }
                        break;
                    case "notBetween": // NOT BETWEEN 值1 AND 值2
                        try {
                            List<Object> betweenList = parseValue2List(value);
                            if (betweenList.size() != 2) {
                                throw new BusinessRunTimeException("notBetween 操作的值必须为数组且长度为 2");
                            }
                            wrapper.notBetween(field, betweenList.get(0), betweenList.get(1));
                        } catch (JsonProcessingException e) {
                            throw new BusinessRunTimeException("notBetween 操作的值必须为数组且长度为 2");
                        }
                        break;
                    default:
                        throw new BusinessRunTimeException("不支持的查询操作：" + type);
                }
            }
        }
        // 排序
        if (Assert.notEmpty(orderBy)) {
            for (QueryOrder queryOrder : orderBy) {
                String field = VariableNameUtils.humpToLine(queryOrder.getField());
                String type = queryOrder.getType().toLowerCase();
                switch (type) {
                    case "asc":
                        wrapper.orderByAsc(field);
                        break;
                    case "desc":
                        wrapper.orderByDesc(field);
                        break;
                    default:
                        throw new BusinessRunTimeException("不支持的排序操作：" + type);
                }
            }
        }
        return wrapper;
    }

    /**
     * 构建 IPage
     *
     * @return IPage
     */
    public IPage<T> buildPage() {
        if (Assert.isEmpty(pageBy) || (Assert.isEmpty(pageBy.getPageNum()) && Assert.isEmpty(pageBy.getPageSize()))) {
            return null;
        }
        if (Assert.isEmpty(pageBy.getPageNum()) || Assert.isEmpty(pageBy.getPageSize())) {
            throw new BusinessRunTimeException("pageNum 与 pageSize 必须同时指定");
        }
        return new Page<>(pageBy.getPageNum(), pageBy.getPageSize());
    }

    /**
     * 将 Object 类型的对象（实际上是 JsonArray） 转为 List
     *
     * @param value 对象
     * @return List
     * @throws JsonProcessingException JSON 解析异常
     */
    private List<Object> parseValue2List(Object value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Object.class);
        return objectMapper.readValue(String.valueOf(value), listType);
    }
}
