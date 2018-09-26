package com.qjd.rry.service.impl;

import com.qjd.rry.convert.CategoryConvert;
import com.qjd.rry.dao.CategoryDao;
import com.qjd.rry.entity.Category;
import com.qjd.rry.entity.Vip;
import com.qjd.rry.enums.CategoryEnums;
import com.qjd.rry.repository.CategoryRepository;
import com.qjd.rry.repository.CategoryUserRelationRepository;
import com.qjd.rry.repository.VipRepository;
import com.qjd.rry.request.*;
import com.qjd.rry.response.*;
import com.qjd.rry.service.CategoryService;
import com.qjd.rry.utils.Result;
import com.qjd.rry.utils.ResultUtils;
import com.qjd.rry.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-21 20:46
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VipRepository vipRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    CategoryUserRelationRepository categoryUserRelationRepository;

    @Autowired
    CategoryDao categoryDao;

    @Override
    public Result<PromotionResponse> queryCategoryList() {
        List<Category> categoryList = categoryRepository.findAllByPCode(CategoryEnums.PROMOTION.getCode());
        Category category = categoryRepository.findFirstByCode(CategoryEnums.PROMOTION.getCode());
        category = category == null ? new Category() : category;
        PromotionResponse promotionResponse = new PromotionResponse();
        List<CategoryInfoResponse> categoryInfoResponseList = categoryList.stream().map(CategoryConvert::categoryToCategoryInfoResponse).collect(Collectors.toList());
        promotionResponse.setIntro(category.getRemark());
        promotionResponse.setCategoryInfoResponseList(categoryInfoResponseList);
        return ResultUtils.success(promotionResponse);
    }

    @Override
    public Result<VipResponse> queryVipCategoryList(VipCategoryListGetRequest request) {
        Boolean isApplePrice = request.getIsApplePrice() == null ? Boolean.FALSE : request.getIsApplePrice();
        List<String> pCodeList = categoryRepository.findAllByPCode(isApplePrice ? CategoryEnums.APPLE_VIP.getCode() : CategoryEnums.VIP.getCode()).stream().map(Category::getCode).collect(Collectors.toList());
        List<Category> categoryList = categoryRepository.findAllByPCodeInOrderBySequence(pCodeList);
        Category category1 = categoryRepository.findFirstByCode(CategoryEnums.VIP.getCode());
        /**
         * 过滤掉试用vip
         */
        Vip vip = vipRepository.findFirstByUserId(tokenUtil.getUserId());
        if (vip != null) {
            categoryList = categoryList.stream().filter(category -> !category.getPCode().equals(CategoryEnums.TRIAL_VIP.getCode()) && !category.getPCode().equals(CategoryEnums.APPLE_TRIAL_VIP.getCode())).collect(Collectors.toList());
        }
        VipResponse vipResponse = new VipResponse();
        vipResponse.setIntro(category1.getRemark());
        List<VipCategoryResponse> vipCategoryResponseList = categoryList.stream().filter(category -> category.getIsDisplay() == null || category.getIsDisplay()).map(CategoryConvert::categoryToVipCategoryResponse).collect(Collectors.toList());
        vipResponse.setVipCategoryResponseList(vipCategoryResponseList);
        return ResultUtils.success(vipResponse);
    }

    @Override
    public Result<List<UserCategoryResponse>> queryUserCategory(Integer type) {
        List<Category> categoryList = null;
        if (type == 1) {
            categoryList = categoryRepository.findAllByPCodeOrderBySequence(CategoryEnums.USER.getCode());
        } else if (type == 2) {
            categoryList = categoryRepository.findAllByPCode(CategoryEnums.MODULE.getCode());
        }
        List<UserCategoryResponse> userCategoryResponseList = categoryList != null ? categoryList.stream().map(CategoryConvert::categoryToUserCategoryResponse).collect(Collectors.toList()) : null;
        return ResultUtils.success(userCategoryResponseList);
    }

    @Override
    @Transactional
    public Result updateCategory(CategoryUpdateRequest request) {
        Category businessCategory = Category.builder().name(request.getName()).sequence(request.getSequence()).purl(request.getUrl()).context1(request.getContext()).id(request.getId()).build();
        categoryDao.updateCategory(businessCategory);
        return ResultUtils.success();
    }

    @Override
    @Transactional
    public Result deleteCategory(CategoryDeleteRequest request) {
        categoryDao.deleteById(request.getId());
        return ResultUtils.success();
    }

    @Override
    @Transactional
    public Result createCategory(CategoryCreateRequest request) {
        Category businessCategory = Category.builder().name(request.getName()).sequence(request.getSequence()).context1(request.getContext()).purl(request.getCover()).pCode(CategoryEnums.USER.getCode()).build();
        categoryDao.createCategory(businessCategory);
        return ResultUtils.success();
    }

    @Override
    public Result<UserCategoryResponse> getCategoryInfo(Integer categoryId) {
        Category category = categoryDao.getCategoryById(categoryId);
        UserCategoryResponse response = CategoryConvert.categoryToUserCategoryResponse(category);
        return ResultUtils.success(response);
    }

    @Override
    public Result<VipCardPriceGetResponse> getVipCardPrice(VipCardPriceGetRequest request) {
        Integer count = request.getCount();
        List<Category> categoryList = categoryRepository.findAllByPCode(CategoryEnums.CARD_YEAR.getCode());
        String minValue = categoryList.stream().filter(category -> count >= Integer.parseInt(category.getContext3())).max(Comparator.comparingInt(s -> Integer.parseInt(s.getContext3()))).get().getContext2();
        BigDecimal amount = new BigDecimal(minValue);
        BigDecimal totalAmount = amount.multiply(new BigDecimal(count));
        VipCardPriceGetResponse result = VipCardPriceGetResponse.builder().amount(amount).totalAmount(totalAmount).build();
        //List<VipCardPriceGetResponse> resultList=categoryList.stream().map(category->VipCardPriceGetResponse.builder().categoryId(category.getId()).cardCount(Integer.parseInt(category.getContext3())).amount(new BigDecimal(category.getContext2())).build()).collect(Collectors.toList());//此时context1为时长，context2为价格，context3为年卡数量
        return ResultUtils.success(result);
    }
}
