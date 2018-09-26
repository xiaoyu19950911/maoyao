package com.qjd.rry.repository.delete;

import com.qjd.rry.entity.delete.CategoryDelete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-26 16:17
 **/
public interface CategoryDeleteRepository extends JpaRepository<CategoryDelete,Integer>{

    CategoryDelete findFirstByCode(String code);

    List<CategoryDelete> findAllByPCode(String code);

    List<CategoryDelete> findAllByPCodeOrderBySequence(String code);

    List<CategoryDelete> findAllByPCodeIn(List<String> pCodeList);
}
