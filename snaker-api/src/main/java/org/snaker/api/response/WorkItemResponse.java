package org.snaker.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("工作事项")
public class WorkItemResponse {
    /**
     * 流程定义ID
     */
    @ApiModelProperty(notes = "流程定义ID")
    private String processId;
    /**
     * 流程实例ID
     */
    @ApiModelProperty(notes = "流程实例ID")
    private String orderId;
    /**
     * 任务ID
     */
    @ApiModelProperty(notes = "任务ID")
    private String taskId;
    /**
     * 流程名称
     */
    @ApiModelProperty(notes = "流程名称")
    private String processName;
    /**
     * 流程实例url
     */
    @ApiModelProperty(notes = "流程实例url")
    private String instanceUrl;
    /**
     * 流程实例为子流程时，该字段标识父流程实例ID
     */
    @ApiModelProperty(notes = "父流程实例ID")
    private String parentId;
    /**
     * 流程实例创建者ID
     */
    @ApiModelProperty(notes = "流程实例创建者ID")
    private String creator;
    /**
     * 流程实例创建时间
     */
    @ApiModelProperty(notes = "流程实例创建时间")
    private String orderCreateTime;
    /**
     * 流程实例结束时间
     */
    @ApiModelProperty(notes = "流程实例结束时间")
    private String orderEndTime;
    /**
     * 流程实例期望完成时间
     */
    @ApiModelProperty(notes = "流程实例期望完成时间")
    private String orderExpireTime;
    /**
     * 流程实例编号
     */
    @ApiModelProperty(notes = "流程实例编号")
    private String orderNo;
    /**
     * 流程实例附属变量
     */
    @ApiModelProperty(notes = "流程实例附属变量")
    private String orderVariable;
    /**
     * 任务名称
     */
    @ApiModelProperty(notes = "任务名称")
    private String taskName;
    /**
     * 任务标识名称
     */
    @ApiModelProperty(notes = "任务标识名称")
    private String taskKey;
    /**
     * 参与类型（0：普通任务；1：参与者fork任务[即：如果10个参与者，需要每个人都要完成，才继续流转]）
     */
    @ApiModelProperty(notes = "参与类型")
    private Integer performType;
    /**
     * 任务类型
     */
    @ApiModelProperty(notes = "任务类型")
    private Integer taskType;
    /**
     * 任务状态（0：结束；1：活动）
     */
    @ApiModelProperty(notes = "任务状态（0：结束；1：活动）")
    private Integer taskState;
    /**
     * 任务创建时间
     */
    @ApiModelProperty(notes = "任务创建时间")
    private String taskCreateTime;
    /**
     * 任务完成时间
     */
    @ApiModelProperty(notes = "任务完成时间")
    private String taskEndTime;
    /**
     * 期望任务完成时间
     */
    @ApiModelProperty(notes = "期望任务完成时间")
    private String taskExpireTime;
    /**
     * 任务附属变量
     */
    @ApiModelProperty(notes = "任务附属变量")
    private String taskVariable;
    /**
     * 任务处理者ID
     */
    @ApiModelProperty(notes = "任务处理者ID")
    private String operator;
    /**
     * 任务关联的表单url
     */
    @ApiModelProperty(notes = "任务关联的表单url")
    private String actionUrl;
    /**
     * 任务参与者列表
     */
    @ApiModelProperty(notes = "任务参与者列表")
    private String[] actorIds;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getInstanceUrl() {
        return instanceUrl;
    }

    public void setInstanceUrl(String instanceUrl) {
        this.instanceUrl = instanceUrl;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(String orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public String getOrderExpireTime() {
        return orderExpireTime;
    }

    public void setOrderExpireTime(String orderExpireTime) {
        this.orderExpireTime = orderExpireTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderVariable() {
        return orderVariable;
    }

    public void setOrderVariable(String orderVariable) {
        this.orderVariable = orderVariable;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public Integer getPerformType() {
        return performType;
    }

    public void setPerformType(Integer performType) {
        this.performType = performType;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Integer getTaskState() {
        return taskState;
    }

    public void setTaskState(Integer taskState) {
        this.taskState = taskState;
    }

    public String getTaskCreateTime() {
        return taskCreateTime;
    }

    public void setTaskCreateTime(String taskCreateTime) {
        this.taskCreateTime = taskCreateTime;
    }

    public String getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public String getTaskExpireTime() {
        return taskExpireTime;
    }

    public void setTaskExpireTime(String taskExpireTime) {
        this.taskExpireTime = taskExpireTime;
    }

    public String getTaskVariable() {
        return taskVariable;
    }

    public void setTaskVariable(String taskVariable) {
        this.taskVariable = taskVariable;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String[] getActorIds() {
        return actorIds;
    }

    public void setActorIds(String[] actorIds) {
        this.actorIds = actorIds;
    }
}
