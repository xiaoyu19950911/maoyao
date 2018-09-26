package com.qjd.rry.controller;

import com.qjd.rry.request.*;
import com.qjd.rry.response.PromotionResponse;
import com.qjd.rry.response.UserCategoryResponse;
import com.qjd.rry.response.VipCardPriceGetResponse;
import com.qjd.rry.response.VipResponse;
import com.qjd.rry.service.CategoryService;
import com.qjd.rry.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: rry
 * @description: 查询列表信息
 * @author: XiaoYu
 * @create: 2018-03-21 20:36
 **/
@RestController
@RequestMapping("/category")
@Api(value = "category", description = "分类相关接口")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "/querypromotion")
    @ApiOperation("获取推广列表")
    public Result<PromotionResponse> getPromotionInfo() {
        return categoryService.queryCategoryList();
    }

    @GetMapping(value = "/queryvipcategorylist")
    @ApiOperation("获取可开通vip列表")
    public Result<VipResponse> getVipCategoryList(@Valid @ModelAttribute VipCategoryListGetRequest request, BindingResult bindingResult) {
        return categoryService.queryVipCategoryList(request);
    }

    @GetMapping(value = "/getVipCardPrice")
    @ApiOperation("查询vip年卡价格")
    public Result<VipCardPriceGetResponse> getVipCardPrice(@Valid @ModelAttribute VipCardPriceGetRequest request,BindingResult bindingResult) {
        return categoryService.getVipCardPrice(request);
    }

    @GetMapping(value = "/queryusercategorylist")
    @ApiOperation("获取艺术家分类/首页底部模块分类(web_admin、app)")
    public Result<List<UserCategoryResponse>> getUserCategoryResponse(@Valid @ModelAttribute UserCategoryGetRequest request, BindingResult bindingResult) {
        return categoryService.queryUserCategory(request.getType());
    }

    @PostMapping(value = "/updatecategoryname")
    @ApiOperation("web_分类管理-编辑分类名/底部静态资源(web_admin)")
    @PreAuthorize("hasAnyRole('ROLE_ROOT')")
    public Result updateCategory(@Valid @RequestBody CategoryUpdateRequest request, BindingResult bindingResult) {
        return categoryService.updateCategory(request);
    }

    @PostMapping(value = "/createCategory")
    @ApiOperation("分类管理-增加分类/底部静态资源(web_admin)")
    @PreAuthorize("hasAnyRole('ROLE_ROOT')")
    public Result createCategory(@Valid @RequestBody CategoryCreateRequest request, BindingResult bindingResult) {
        return categoryService.createCategory(request);
    }

    @GetMapping(value = "/getCategoryInfo")
    @ApiOperation("底部静态资源-详情(web_admin)")
    @PreAuthorize("hasAnyRole('ROLE_ROOT')")
    public Result<UserCategoryResponse> getCategoryInfo(@Valid @ModelAttribute CategoryInfoGetRequest request, BindingResult bindingResult) {
        return categoryService.getCategoryInfo(request.getCategoryId());
    }

    @PostMapping(value = "/deleteCategory")
    @ApiOperation("分类管理-删除分类(web_admin)")
    @PreAuthorize("hasAnyRole('ROLE_ROOT')")
    public Result deleteCategory(@Valid @RequestBody CategoryDeleteRequest request, BindingResult bindingResult) {
        return categoryService.deleteCategory(request);
    }
}
