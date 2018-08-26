package com.ray.monitor.web.vo;

import com.ray.monitor.model.TempInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rui on 2018/8/26.
 */
public class PageTempVO implements Serializable {
    private static final long serialVersionUID = 188974455120367680L;

    private long totalCount;

    private int totalPage;

    private int page;

    private TempVO tempVO;

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public TempVO getTempVO() {
        return tempVO;
    }

    public void setTempVO(TempVO tempVO) {
        this.tempVO = tempVO;
    }
}
