package org.snaker.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("发起流程请求")
public class StartRequest {
    @ApiModelProperty(notes = "流程名称")
    private String processName;
    @ApiModelProperty(notes = "操作人")
    private String operator;

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
