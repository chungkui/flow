package org.snaker.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("流程定义请求")
public class ProcessRequest{
    @ApiModelProperty(notes = "显示名称")
    private String displayName;
    @ApiModelProperty(notes = "分页大小")
    private Integer pageSize;
    @ApiModelProperty(notes = "当前页")
    private Integer page;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
