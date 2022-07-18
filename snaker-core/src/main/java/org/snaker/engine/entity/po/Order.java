package org.snaker.engine.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 流程实例表
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wf_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
      private String id;

    /**
     * 父流程ID
     */
    @TableField("parent_Id")
    private String parentId;

    /**
     * 流程定义ID
     */
    @TableField("process_Id")
    private String processId;

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
     * 期望完成时间
     */
    @TableField("expire_Time")
    private String expireTime;

    /**
     * 上次更新时间
     */
    @TableField("last_Update_Time")
    private String lastUpdateTime;

    /**
     * 上次更新人
     */
    @TableField("last_Updator")
    private String lastUpdator;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 父流程依赖的节点名称
     */
    @TableField("parent_Node_Name")
    private String parentNodeName;

    /**
     * 流程实例编号
     */
    @TableField("order_No")
    private String orderNo;

    /**
     * 附属变量json存储
     */
    private String variable;

    /**
     * 版本
     */
    private Integer version;


}
