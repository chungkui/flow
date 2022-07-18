package org.snaker.engine.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.snaker.engine.entity.po.Task;
import org.snaker.engine.model.TaskModel;

/**
 * <p>
 * 历史任务表
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wf_hist_task")
public class HistTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
      private String id;

    /**
     * 流程实例ID
     */
    @TableField("order_Id")
    private String orderId;

    /**
     * 任务名称
     */
    @TableField("task_Name")
    private String taskName;

    /**
     * 任务显示名称
     */
    @TableField("display_Name")
    private String displayName;

    /**
     * 任务类型
     */
    @TableField("task_Type")
    private Integer taskType;

    /**
     * 参与类型
     */
    @TableField("perform_Type")
    private Integer performType;

    /**
     * 任务状态
     */
    @TableField("task_State")
    private Integer taskState;

    /**
     * 任务处理人
     */
    private String operator;

    /**
     * 任务创建时间
     */
    @TableField("create_Time")
    private String createTime;

    /**
     * 任务完成时间
     */
    @TableField("finish_Time")
    private String finishTime;

    /**
     * 任务期望完成时间
     */
    @TableField("expire_Time")
    private String expireTime;

    /**
     * 任务处理url
     */
    @TableField("action_Url")
    private String actionUrl;

    /**
     * 父任务ID
     */
    @TableField("parent_Task_Id")
    private String parentTaskId;

    /**
     * 附属变量json存储
     */
    private String variable;

    private String[] actorIds;
    public Task undoTask() {
        Task task = new Task();
        task.setOrderId(this.getOrderId());;
        task.setTaskName(this.getTaskName());
        task.setDisplayName(this.getDisplayName());
        task.setTaskType(this.getTaskType());
        task.setExpireTime(this.getExpireTime());
        task.setActionUrl(this.getActionUrl());
        task.setParentTaskId(this.getParentTaskId());
        task.setVariable(this.getVariable());
        task.setPerformType(this.getPerformType());
        task.setOperator(this.getOperator());
        return task;
    }
    public boolean isPerformAny() {
        return this.performType.intValue() == TaskModel.PerformType.ANY.ordinal();
    }
}
