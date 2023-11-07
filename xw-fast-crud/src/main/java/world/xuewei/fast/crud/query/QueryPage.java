package world.xuewei.fast.crud.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询分页信息
 *
 * @author XUEW
 * @since 2023/9/4 14:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryPage {

    /**
     * 查询指定页
     */
    protected Long pageNum;

    /**
     * 页面大小
     */
    protected Long pageSize;
}
