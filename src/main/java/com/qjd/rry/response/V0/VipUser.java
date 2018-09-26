package com.qjd.rry.response.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-23 20:43
 **/
@Builder
@ApiModel
@Data
public class VipUser {

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("vip开始时间")
    private Long startTime;

    @ApiModelProperty("vip到期时间")
    private Long endTime;

    @ApiModelProperty("vip类型(vip类型(0030000、非vip；003001、普通vip；003002、超级vip；003003、试用vip))")
    private String type;
}
