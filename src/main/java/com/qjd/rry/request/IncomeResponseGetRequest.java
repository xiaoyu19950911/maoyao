package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-08-20 10:58
 **/
@Data
@ApiModel
public class IncomeResponseGetRequest {

    @ApiModelProperty(value = "类型（1、收入；2、支出）",required = true)
    @NotNull(message = "类型不能为空！")
    private List<Integer> typeList;

    @ApiModelProperty(value = "开始时间",dataType = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty(value = "结束时间",dataType = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
}
