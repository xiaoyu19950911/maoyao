package com.qjd.rry.dao;

import com.qjd.rry.entity.CategoryUserRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-24 10:56
 **/
public interface CategoryUserRelationDao {

    public Page<CategoryUserRelation> getAllByCategoryId(Integer categoryId, Pageable pageable) ;

    /**
     *
     * @param categoryUserRelation
     */
    CategoryUserRelation createCategoryUserRelation(CategoryUserRelation categoryUserRelation);

    /**
     *
     * @param id
     * @return
     */
    List<CategoryUserRelation> getAllByUserId(Integer id);

    void deleteAllByUserId(Integer userId);

    void deleteAllByCategoryUserRelationList(List<CategoryUserRelation> categoryUserRelationList);
}
