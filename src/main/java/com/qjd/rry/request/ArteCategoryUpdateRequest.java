package com.qjd.rry.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-17 14:37
 **/

@ApiModel
@Data
public class ArteCategoryUpdateRequest {

    @ApiModelProperty("艺术家id")
    @NotNull
    private Integer userId;

    @ApiModelProperty("艺术家分类id列表")
    @NotNull
    private List<Integer> categoryId;
}
