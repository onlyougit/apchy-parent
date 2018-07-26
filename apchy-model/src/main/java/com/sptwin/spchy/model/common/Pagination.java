package com.sptwin.spchy.model.common;

/**
 * Created by wangweibin on 2017/4/3.
 */
public class Pagination {

    private Integer pageIndex;
    private Integer pageSize;
    private String sortField;
    private String sortOrder;
    private Integer startIndex;

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getStartIndex() {

        return startIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getPageIndex() {

        return pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }
}
