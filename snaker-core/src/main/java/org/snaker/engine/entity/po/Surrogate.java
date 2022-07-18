package org.snaker.engine.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 委托代理表
 * </p>
 *
 * @author jason
 * @since 2022-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wf_surrogate")
public class Surrogate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
      private String id;

    /**
     * 流程名称
     */
    @TableField("process_Name")
    private String processName;

    /**
     * 授权人
     */
    private String operator;

    /**
     * 代理人
     */
    private String surrogate;

    /**
     * 操作时间
     */
    private String odate;

    /**
     * 开始时间
     */
    private String sdate;

    /**
     * 结束时间
     */
    private String edate;

    /**
     * 状态
     */
    private Integer state;


}
