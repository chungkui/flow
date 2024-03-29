package org.snaker.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.sql.Blob;

@ApiModel("流程定义")
public class ProcessResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(notes = "id")
    private String id;

    /**
     * 流程名称
     */
    @ApiModelProperty(notes = "流程名称")
    private String name;

    /**
     * 流程显示名称
     */
    @ApiModelProperty(notes = "流程显示名称")
    private String displayName;

    /**
     * 流程类型
     */
    @ApiModelProperty(notes = "流程类型")
    private String type;

    /**
     * 实例url
     */
    @ApiModelProperty(notes = "实例url")
    private String instanceUrl;

    /**
     * 流程是否可用
     */
    @ApiModelProperty(notes = "流程是否可用")
    private Integer state;
    /**
     * 版本
     */
    @ApiModelProperty(notes = "版本")
    private Integer version;

    /**
     * 创建时间
     */
    @ApiModelProperty(notes = "创建时间")
    private String createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(notes = "创建人")
    private String creator;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInstanceUrl() {
        return instanceUrl;
    }

    public void setInstanceUrl(String instanceUrl) {
        this.instanceUrl = instanceUrl;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }


}
