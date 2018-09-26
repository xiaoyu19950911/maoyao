package com.qjd.rry.dao;

import com.qjd.rry.entity.Category;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-18 11:43
 **/
public interface CategoryDao {

    public Category updateCategory(Category category);

    void createCategory(Category businessCategory);

    Category getCategoryById(Integer categoryId);

    Category getCategoryByCode(String code);

    void deleteById(Integer id);
}
