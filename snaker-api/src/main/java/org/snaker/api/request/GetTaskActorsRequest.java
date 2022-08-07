package org.snaker.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("获取任务办理人")
public class GetTaskActorsRequest {
    @ApiModelProperty(notes = "流程实例id")
    String orderId;
    @ApiModelProperty(notes = "任务名称")
    String taskName;

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
}
