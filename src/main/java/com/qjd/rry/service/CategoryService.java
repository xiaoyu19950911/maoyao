package com.qjd.rry.service;

import com.qjd.rry.request.*;
import com.qjd.rry.response.PromotionResponse;
import com.qjd.rry.response.UserCategoryResponse;
import com.qjd.rry.response.VipCardPriceGetResponse;
import com.qjd.rry.response.VipResponse;
import com.qjd.rry.utils.Result;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-21 20:46
 **/
public interface CategoryService {
    Result<PromotionResponse> queryCategoryList();

    Result<VipResponse> queryVipCategoryList(VipCategoryListGetRequest request);

    Result<List<UserCategoryResponse>> queryUserCategory(Integer type);

    Result updateCategory(CategoryUpdateRequest request);

    Result deleteCategory(CategoryDeleteRequest request);

    Result createCategory(CategoryCreateRequest request);

    Result<UserCategoryResponse> getCategoryInfo(Integer categoryId);

    Result<VipCardPriceGetResponse> getVipCardPrice(VipCardPriceGetRequest request);
}
