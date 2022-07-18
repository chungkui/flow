package org.snaker.engine.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.snaker.engine.entity.po.Order;

/**
 * <p>
 * 历史流程实例表
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wf_hist_order")
public class HistOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 流程定义ID
     */
    @TableField("process_Id")
    private String processId;

    /**
     * 状态
     */
    @TableField("order_State")
    private Integer orderState;

    /**
     * 发起人
     */
    private String creator;

    /**
     * 发起时间
     */
    @TableField("create_Time")
    private String createTime;

    /**
     * 完成时间
     */
    @TableField("end_Time")
    private String endTime;

    /**
     * 期望完成时间
     */
    @TableField("expire_Time")
    private String expireTime;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 父流程ID
     */
    @TableField("parent_Id")
    private String parentId;

    /**
     * 流程实例编号
     */
    @TableField("order_No")
    private String orderNo;

    /**
     * 附属变量json存储
     */
    private String variable;

    public Order undo() {
        Order order = new Order();
        order.setId(this.id);
        order.setProcessId(this.processId);
        order.setParentId(this.parentId);
        order.setCreator(this.creator);
        order.setCreateTime(this.createTime);
        order.setLastUpdator(this.creator);
        order.setLastUpdateTime(this.endTime);
        order.setExpireTime(this.expireTime);
        order.setOrderNo(this.orderNo);
        order.setPriority(this.priority);
        order.setVariable(this.variable);
        order.setVersion(0);
        return order;
    }

}
