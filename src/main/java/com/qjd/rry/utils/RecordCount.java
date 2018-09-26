package com.qjd.rry.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-27 20:30
 **/
@Data
@ApiModel
public class RecordCount implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = -435328320159692760L;

    @ApiModelProperty("当前页码")
    private int startCount;

    @ApiModelProperty("每页大小")
    private int pageSize;

    @ApiModelProperty("总页码")
    private int totalPageCount;

    @ApiModelProperty("总条数")
    private long totalCount;

    @ApiModelProperty("当前页数据量")
    private int numberOfElements;


    public RecordCount() {}

    /**
     * Constructor.
     * @param start the starting count.
     * @param pageSize the maximum record count.
     * @param total the total record count.
     */
    public RecordCount(int start, int pageSize, long total,int totalPageCount,int numberOfElements)
    {
        this.startCount=start+1;
        this.pageSize = pageSize;
        this.totalCount = total;
        this.totalPageCount=totalPageCount;
        this.numberOfElements=numberOfElements;
    }

    /**
     * Constructor.
     * @param rc the record count.
     */
    public RecordCount(RecordCount rc)
    {
        this(rc.getStartCount(), rc.getPageSize(), rc.getTotalCount(),rc.getTotalPageCount(),rc.getNumberOfElements());
    }
}
