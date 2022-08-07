package org.snaker.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

@ApiModel("执行任务")
public class ExecTaskRequest {
    @ApiModelProperty(notes = "任务id")
    String taskId;
    @ApiModelProperty(notes = "操作人")
    String userName;
    @ApiModelProperty(notes = "执行变量")
    private Map<String,Object> params;
    @ApiModelProperty(notes = "跳转节点名称")
    private String nodeName;
    @ApiModelProperty(notes = "办理人列表")
    List<String> nextOperator;

    public List<String> getNextOperator() {
        return nextOperator;
    }

    public void setNextOperator(List<String> nextOperator) {
        this.nextOperator = nextOperator;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
