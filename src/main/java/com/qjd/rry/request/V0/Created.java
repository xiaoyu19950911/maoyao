package com.qjd.rry.request.V0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class Created {

    @ApiModelProperty("大于  charge 对象的创建时间，用 Unix 时间戳表示")
    private Integer gt;

    @ApiModelProperty("大于或等于  charge 对象的创建时间，用 Unix 时间戳表示")
    private Integer gte;

    @ApiModelProperty("小于  charge 对象的创建时间，用 Unix 时间戳表示")
    private Integer It;

    @ApiModelProperty("小于或等于  charge 对象的创建时间，用 Unix 时间戳表示")
    private Integer Ite;
}
