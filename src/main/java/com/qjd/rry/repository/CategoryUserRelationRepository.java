package com.qjd.rry.repository;

import com.qjd.rry.entity.CategoryUserRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-04 15:04
 **/
public interface CategoryUserRelationRepository extends JpaRepository<CategoryUserRelation,Integer> {

    Page<CategoryUserRelation> findAllByCategoryId(Integer categoryId, Pageable pageable);

    List<CategoryUserRelation> findAllByCategoryId(Integer categoryId);

    List<CategoryUserRelation> findAllByCategoryIdIn(List<Integer> categoryId);

    void deleteAllByCategoryId(Integer id);

    void deleteAllByUserId(Integer userId);

    List<CategoryUserRelation> findAllByUserId(Integer userId);
}
