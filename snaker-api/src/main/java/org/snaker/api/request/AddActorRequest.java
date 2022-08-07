package org.snaker.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("添加参与者")
public class AddActorRequest {
    @ApiModelProperty(notes = "流程实例id")
    String orderId;
    @ApiModelProperty(notes = "任务名称")
    String taskName;
    @ApiModelProperty(notes = "操作人")
    String operator;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
