package com.qjd.rry.repository;

import com.qjd.rry.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-26 16:17
 **/
public interface CategoryRepository extends JpaRepository<Category,Integer>{

    Category findFirstByCode(String code);

    List<Category> findAllByPCode(String code);

    List<Category> findAllByPCodeOrderBySequence(String code);

    List<Category> findAllByPCodeIn(List<String> pCodeList);

    List<Category> findAllByPCodeInOrderBySequence(List<String> pCodeList);

}
