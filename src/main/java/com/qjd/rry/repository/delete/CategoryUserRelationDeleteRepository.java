package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.delete.CategoryUserRelationDelete;
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
public interface CategoryUserRelationDeleteRepository extends JpaRepository<CategoryUserRelationDelete,Integer> {

    Page<CategoryUserRelationDelete> findAllByCategoryId(Integer categoryId, Pageable pageable);

    List<CategoryUserRelationDelete> findAllByCategoryId(Integer categoryId);

    void deleteAllByCategoryId(Integer id);

    void deleteAllByUserId(Integer userId);

    List<CategoryUserRelationDelete> findAllByUserId(Integer userId);
}
