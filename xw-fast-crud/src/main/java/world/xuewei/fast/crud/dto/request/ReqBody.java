package world.xuewei.fast.crud.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import world.xuewei.fast.crud.query.QueryBody;

import java.io.Serializable;
import java.util.List;

/**
 * 通用请求体
 *
 * @author XUEW
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqBody<T> {

    /**
     * 实体对象
     */
    private T obj;

    /**
     * 实体对象列表
     */
    private List<T> objs;

    /**
     * ID
     */
    private Serializable id;

    /**
     * ID 数组
     */
    private List<Serializable> ids;

    /**
     * 字段名
     */
    private String field;

    /**
     * 值
     */
    private Object value;

    /**
     * 值数组
     */
    private List<Object> values;

    /**
     * 自定义查询体
     */
    private QueryBody<T> queryBody;
}
