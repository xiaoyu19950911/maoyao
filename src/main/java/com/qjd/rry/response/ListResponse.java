package com.qjd.rry.response;

import com.qjd.rry.utils.RecordCount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-02 11:01
 **/
@Data
@ApiModel
public class ListResponse<T> extends RecordCount {

    @ApiModelProperty("列表内容")
    private List<T> value;

    public ListResponse(int start, int pageSize, long total, int totalPageCount,int numberOfElements) {
        super(start, pageSize, total, totalPageCount,numberOfElements);
    }
}
