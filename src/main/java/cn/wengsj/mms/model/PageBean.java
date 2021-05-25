package cn.wengsj.mms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * PageBean
 * @param <T>
 */
public class PageBean<T> {
    private int currentPage = 1;    // 需要显示的页码
    private int totalPage = 1;   // 总页数 = 总记录数 / 每页显示记录数  (+ 1)
    private int pageCount = 10;    // 每页记录数
    private int totalCount = 0; // 总记录数

    private List<T> pageData = new ArrayList<T>();

    //总页数
    public int getTotalPage() {
        if( totalCount % pageCount == 0){
            totalCount = totalCount / pageCount;
        } else {
            totalCount = totalCount / pageCount + 1;
        }
        return totalCount;
    }

    public void setTotalPage(int totalPage) {

    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }



    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }
}
