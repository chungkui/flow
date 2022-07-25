package org.snaker.api.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collections;
import java.util.List;

@ApiModel(description = "分页")
public class ResPage<T> {
     /**
     * 查询数据列表
     */
    @ApiModelProperty(notes = "数据列表")
    protected List<T> data = Collections.emptyList();
    /**
     * 总数
     */
    @ApiModelProperty(notes = "总数量")
    protected long count = 0;
    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty(notes = "分页大小")
    protected long pageSize = 10;

    @ApiModelProperty(notes = "当前页")
    protected long nowPage = 1;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getNowPage() {
        return nowPage;
    }

    public void setNowPage(long nowPage) {
        this.nowPage = nowPage;
    }
}
