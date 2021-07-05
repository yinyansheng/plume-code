package com.plume.code.common.model;

import lombok.Data;

@Data
public abstract class BasePageQuery {

    protected Integer pageIndex = 1;

    protected Integer pageSize = 10;

    public Integer getStart() {
        return (pageIndex - 1) * pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
