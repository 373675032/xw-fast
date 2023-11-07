package world.xuewei.fast.crud.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import world.xuewei.fast.core.exception.BusinessRunTimeException;
import world.xuewei.fast.core.util.Assert;


/**
 * 查询条件
 *
 * @author XUEW
 * @since 2023/9/4 14:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryCondition {

    /**
     * 字段
     */
    private String field;

    /**
     * 操作
     */
    private String type;

    /**
     * 值
     */
    private Object value;

    /**
     * 参数合法性检查
     */
    public void check() {
        if (Assert.isEmpty(field) || Assert.isEmpty(type)) {
            throw new BusinessRunTimeException("QueryCondition 中 field、type 不可为空");
        }
    }
}
