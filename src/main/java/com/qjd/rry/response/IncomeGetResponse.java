package com.qjd.rry.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-15 18:33
 **/
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeGetResponse {

    @ApiModelProperty("1、分享课程/专栏收益；2、代理商提成收益；3、出售课程/专栏收益；4、购买课程/专栏开支；5、购买付费推广开支；6、购买vip开支")
    private Integer incomeType;

    @ApiModelProperty("资源id")
    private Integer referenceId;

    @ApiModelProperty("资源类型(1、课程；2、专栏；3、付费推广；4、vip)")
    private Integer referenceType;

    @ApiModelProperty("资源名称")
    private String referenceName;

    @ApiModelProperty("时间")
    private Long happenTime;

    @ApiModelProperty("金额")
    private BigDecimal amount;

    @ApiModelProperty("购买者昵称")
    private String nickName;

    @ApiModelProperty("购买者头像")
    private String avatar;

}
