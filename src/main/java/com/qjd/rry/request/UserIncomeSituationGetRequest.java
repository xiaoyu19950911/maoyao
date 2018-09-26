package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-09-03 15:57
 **/
@Data
@ApiModel
public class UserIncomeSituationGetRequest {

    @ApiModelProperty(value = "开始时间",dataType = "date",required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date startTime;

    @ApiModelProperty(value = "结束时间",dataType = "date",required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date endTime;
}
