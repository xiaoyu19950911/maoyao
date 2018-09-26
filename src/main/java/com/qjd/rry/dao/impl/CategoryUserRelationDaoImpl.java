package com.qjd.rry.dao.impl;

import com.qjd.rry.dao.CategoryUserRelationDao;
import com.qjd.rry.entity.CategoryUserRelation;
import com.qjd.rry.entity.delete.CategoryUserRelationDelete;
import com.qjd.rry.repository.CategoryUserRelationRepository;
import com.qjd.rry.repository.delete.CategoryUserRelationDeleteRepository;
import com.qjd.rry.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-24 10:56
 **/
@Repository
public class CategoryUserRelationDaoImpl implements CategoryUserRelationDao {

    @Autowired
    CategoryUserRelationRepository categoryUserRelationRepository;

    @Autowired
    CategoryUserRelationDeleteRepository categoryUserRelationDeleteRepository;

    @Override
    public Page<CategoryUserRelation> getAllByCategoryId(Integer categoryId, Pageable pageable) {
        Page<CategoryUserRelation> page;
        if (categoryId == null)
            page = categoryUserRelationRepository.findAll(pageable);
        else
            page = categoryUserRelationRepository.findAllByCategoryId(categoryId, pageable);
        return page;
    }

    @Override
    public CategoryUserRelation createCategoryUserRelation(CategoryUserRelation businessCategoryUserRelation) {
        businessCategoryUserRelation.setCreateTime(new Date());
        businessCategoryUserRelation.setUpdateTime(new Date());
        return categoryUserRelationRepository.save(businessCategoryUserRelation);
    }

    @Override
    public List<CategoryUserRelation> getAllByUserId(Integer id) {
        return categoryUserRelationRepository.findAllByUserId(id);
    }

    @Override
    public void deleteAllByUserId(Integer userId) {
        if (userId != null) {
            List<CategoryUserRelation> categoryUserRelationList = categoryUserRelationRepository.findAllByUserId(userId);
            deleteAllByCategoryUserRelationList(categoryUserRelationList);
        }
    }

    @Override
    public void deleteAllByCategoryUserRelationList(List<CategoryUserRelation> categoryUserRelationList) {
        Date now=new Date();
        List<CategoryUserRelationDelete> categoryUserRelationDeleteList = categoryUserRelationList.stream().map(categoryUserRelation -> {
            CategoryUserRelationDelete categoryUserRelationDelete = new CategoryUserRelationDelete();
            BeanUtil.copy(categoryUserRelation, categoryUserRelationDelete);
            categoryUserRelationDelete.setUpdateTime(now);
            return categoryUserRelationDelete;
        }).collect(Collectors.toList());
        categoryUserRelationDeleteRepository.saveAll(categoryUserRelationDeleteList);
        categoryUserRelationRepository.deleteAll(categoryUserRelationList);
    }
}
