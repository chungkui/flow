package org.snaker.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("获取历史任务列表")
public class ListUserHistoryTaskRequest {

    @ApiModelProperty(notes = "操作人")
    String userName;
    @ApiModelProperty(notes = "分页大小")
    private Integer pageSize;
    @ApiModelProperty(notes = "当前页")
    private Integer page;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
