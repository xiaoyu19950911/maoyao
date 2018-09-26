package com.qjd.rry.convert;

import com.qjd.rry.entity.Category;
import com.qjd.rry.response.CategoryInfoResponse;
import com.qjd.rry.response.UserCategoryResponse;
import com.qjd.rry.response.VipCategoryResponse;
import com.qjd.rry.utils.TimeTransUtil;

import java.math.BigDecimal;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-02 11:07
 **/
public class CategoryConvert {

    public static VipCategoryResponse categoryToVipCategoryResponse(Category category) {
        VipCategoryResponse result = new VipCategoryResponse();
        result.setCategoryId(category.getId());
        result.setTimeLength(Integer.parseInt(category.getContext1()));
        result.setTimeType(category.getTimeType());
        result.setPrice(new BigDecimal(category.getContext2()));
        result.setVipType(category.getPCode());
        result.setAppleId(category.getTurl());
        return result;
    }

    public static CategoryInfoResponse categoryToCategoryInfoResponse(Category category) {
        CategoryInfoResponse categoryInfoResponse = new CategoryInfoResponse();
        categoryInfoResponse.setAmount(new BigDecimal(category.getContext2()));
        categoryInfoResponse.setCount(Integer.parseInt(category.getContext1()));
        categoryInfoResponse.setId(category.getId());
        return categoryInfoResponse;
    }

    public static UserCategoryResponse categoryToUserCategoryResponse(Category category) {
        UserCategoryResponse result = new UserCategoryResponse();
        result.setId(category.getId());
        result.setName(category.getName());
        result.setPurl(category.getPurl());
        result.setTurl(category.getTurl());
        result.setTime(TimeTransUtil.DateToUnix(category.getUpdateTime()));
        result.setSequence(category.getSequence());
        return result;
    }

}
