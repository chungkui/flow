package org.snaker.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("流程部署")
public class DeployRequest {
    /**
     * 流程定义xml
     */
    @ApiModelProperty(notes = "流程定义xml")
    String xml;
    /**
     * 流程定义id
     */
    @ApiModelProperty(notes = "流程定义id")
    String id;
    /**
     * 创建人
     */
    @ApiModelProperty(notes = "创建人")
    String createUser;
    public String getXml() {
        return xml;
    }


    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
