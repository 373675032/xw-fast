package world.xuewei.fast.crud.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 基础数据库实体
 *
 * @author XUEW
 * @since 2023/9/5 14:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    /**
     * 主键 ID
     */
    @TableId
    protected Long id;

    /**
     * 删除状态，逻辑删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    protected Integer deleted;

    /**
     * 创建人
     */
    protected String createBy;

    /**
     * 修改人
     */
    protected String updateBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    protected Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Date updateTime;
}
