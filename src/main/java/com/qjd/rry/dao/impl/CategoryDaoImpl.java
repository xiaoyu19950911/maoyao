package com.qjd.rry.dao.impl;

import com.qjd.rry.dao.CategoryDao;
import com.qjd.rry.dao.CategoryUserRelationDao;
import com.qjd.rry.entity.Category;
import com.qjd.rry.entity.CategoryUserRelation;
import com.qjd.rry.entity.delete.CategoryDelete;
import com.qjd.rry.enums.CategoryEnums;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.repository.CategoryRepository;
import com.qjd.rry.repository.CategoryUserRelationRepository;
import com.qjd.rry.repository.delete.CategoryDeleteRepository;
import com.qjd.rry.repository.delete.CategoryUserRelationDeleteRepository;
import com.qjd.rry.utils.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-18 11:47
 **/
@Repository
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryDeleteRepository categoryDeleteRepository;

    @Autowired
    CategoryUserRelationRepository categoryUserRelationRepository;

    @Autowired
    CategoryUserRelationDeleteRepository categoryUserRelationDeleteRepository;

    @Autowired
    CategoryUserRelationDao categoryUserRelationDao;

    @Override
    public Category updateCategory(Category businessCategory) {
        Category category = categoryRepository.getOne(businessCategory.getId());
        if (category.getId() == null)
            throw new CommunalException(-1, "categoryId 不能为空！");
        if (StringUtils.isNotEmpty(businessCategory.getContext1()))
            category.setContext1(businessCategory.getContext1());
        if (StringUtils.isNotEmpty(businessCategory.getName()))
            category.setName(businessCategory.getName());
        if (StringUtils.isNotEmpty(businessCategory.getPurl()))
            category.setPurl(businessCategory.getPurl());
        if (StringUtils.isNotEmpty(businessCategory.getRemark()))
            category.setRemark(businessCategory.getRemark());
        if (businessCategory.getSequence() != null)
            category.setSequence(businessCategory.getSequence());
        category.setUpdateTime(new Date());
        return categoryRepository.save(category);
    }

    @Override
    public void createCategory(Category businessCategory) {
        Integer seq=categoryRepository.findAllByPCode(CategoryEnums.USER.getCode()).stream().map(Category::getSequence).max(Comparator.naturalOrder()).get();
        businessCategory.setSequence(seq+1);
        businessCategory.setCreateTime(new Date());
        businessCategory.setUpdateTime(new Date());
        categoryRepository.save(businessCategory);
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.getOne(categoryId);
    }

    @Override
    public Category getCategoryByCode(String code) {
        Category category;
        if (StringUtils.isEmpty(code))
            category = new Category();
        else {
            category = categoryRepository.findFirstByCode(code);
        }
        return category == null ? new Category() : category;
    }

    @Override
    public void deleteById(Integer id) {
        if (ProgramEnums.USER_BASIC_ART.getCode().equals(id)) {
            throw new CommunalException(-1, "无法删除该分类！");
        }
        if (id != null) {
            Date now=new Date();
            /**
             * 插入CategoryDelete
             */
            CategoryDelete categoryDelete = new CategoryDelete();
            Category category = categoryRepository.getOne(id);
            BeanUtil.copy(category, categoryDelete);
            categoryDelete.setUpdateTime(now);
            categoryDeleteRepository.save(categoryDelete);

            /**
             * 删除CategoryUserRelation
             */
            List<CategoryUserRelation> categoryUserRelationList = categoryUserRelationRepository.findAllByCategoryId(id);
            categoryUserRelationDao.deleteAllByCategoryUserRelationList(categoryUserRelationList);


            /**
             * 删除Category
             */
            categoryRepository.deleteById(id);
        }
    }
}
