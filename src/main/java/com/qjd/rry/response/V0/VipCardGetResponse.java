package com.qjd.rry.response.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-09-11 17:57
 **/
@Data
@ApiModel
@Builder
public class VipCardGetResponse {

    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("卡号")
    private String cardId;

    @ApiModelProperty("激活者昵称")
    private String awakeUserNickName;

    @ApiModelProperty("激活时间")
    private Date awakeTime;

}
