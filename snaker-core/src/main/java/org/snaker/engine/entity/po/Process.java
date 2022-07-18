package org.snaker.engine.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import java.sql.Blob;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.BlobTypeHandler;
import org.snaker.engine.model.ProcessModel;

/**
 * <p>
 * 流程定义表
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wf_process")
public class Process implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
      private String id;

    /**
     * 流程名称
     */
    private String name;

    /**
     * 流程显示名称
     */
    @TableField("display_Name")
    private String displayName;

    /**
     * 流程类型
     */
    private String type;

    /**
     * 实例url
     */
    @TableField("instance_Url")
    private String instanceUrl;

    /**
     * 流程是否可用
     */
    private Integer state;

    /**
     * 流程模型定义
     */
    @TableField(typeHandler = BlobTypeHandler.class)
    private byte[] content;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 创建时间
     */
    @TableField("create_Time")
    private String createTime;

    /**
     * 创建人
     */
    private String creator;

    @TableField(exist = false)
    private ProcessModel model;

}
