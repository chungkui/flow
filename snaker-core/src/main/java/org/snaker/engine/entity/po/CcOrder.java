package org.snaker.engine.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 抄送实例表
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wf_cc_order")
public class CcOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流程实例ID
     */
    @TableField("order_Id")
    private String orderId;

    /**
     * 参与者ID
     */
    @TableField("actor_Id")
    private String actorId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 发起人
     */
    private String creator;

    /**
     * 创建时间
     */
    @TableField("create_Time")
    private String createTime;

    /**
     * 完成时间
     */
    @TableField("finish_Time")
    private String finishTime;


}
