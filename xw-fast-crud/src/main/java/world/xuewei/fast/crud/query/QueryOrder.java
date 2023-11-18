package world.xuewei.fast.crud.query;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import world.xuewei.fast.core.exception.BusinessRunTimeException;

/**
 * 排序信息
 *
 * @author XUEW
 * @since 2023/9/4 14:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryOrder {

    /**
     * 排序字段
     */
    private String field;

    /**
     * 排序策略：desc、ase
     */
    private String type;

    /**
     * 参数合法性检查
     */
    public void check() {
        if (ObjectUtil.isEmpty(field) || ObjectUtil.isEmpty(type)) {
            throw new BusinessRunTimeException("QueryOrder 中 field、type 均不可为空");
        }
    }
}
