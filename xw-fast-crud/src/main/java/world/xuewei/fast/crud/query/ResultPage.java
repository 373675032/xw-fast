package world.xuewei.fast.crud.query;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import world.xuewei.fast.core.util.Assert;

import java.util.List;

/**
 * 分页查询结果
 *
 * @author XUEW
 * @since 2023/11/2 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResultPage<T> extends QueryPage {

    /**
     * 总记录数
     */
    private Long totalRecord;

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 记录结果
     */
    private List<T> records;

    /**
     * 转换分页信息
     *
     * @param wrapperPage Mybatis Pulse分页信息
     */
    public ResultPage(IPage<T> wrapperPage) {
        if (Assert.notEmpty(wrapperPage)) {
            this.pageNum = wrapperPage.getCurrent();
            this.pageSize = wrapperPage.getSize();
            this.totalPage = wrapperPage.getPages();
            this.totalRecord = wrapperPage.getTotal();
            this.records = wrapperPage.getRecords();
        }
    }
}
