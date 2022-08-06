package org.snaker.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查询任务请求踢")
public class TaskRequest {
    @ApiModelProperty(notes = "处理人")
    private String assignees;
    @ApiModelProperty(notes = "任务类型")
    private Integer type;
    @ApiModelProperty(notes = "分页大小")
    private Integer pageSize;
    @ApiModelProperty(notes = "当前页")
    private Integer page;
    public String getAssignees() {
        return assignees;
    }

    public void setAssignees(String assignees) {
        this.assignees = assignees;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
