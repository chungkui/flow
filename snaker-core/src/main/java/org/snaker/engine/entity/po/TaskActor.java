package org.snaker.engine.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 任务参与者表
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wf_task_actor")
public class TaskActor implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId
    private Long id;
    /**
     * 任务ID
     */
    @TableField("task_Id")
    private String taskId;

    /**
     * 参与者ID
     */
    @TableField("actor_Id")
    private String actorId;


}
