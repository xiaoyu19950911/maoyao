package com.qjd.rry.dao;

import com.qjd.rry.entity.Columns;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-16 14:40
 **/
public interface ColumnDao {

    /**
     *
     * @param id
     * @return
     */
    public Columns getColumnById(Integer id);

    /**
     *
     * @param columns
     */
    Columns updateColumn(Columns columns);

    /**
     *
     * @param columnIdList
     * @return
     */
    List<Columns> getColumnByIdIn(List<Integer> columnIdList);

    /**
     *
     * @param columnsList
     */
    void deleteAllInBatch(List<Columns> columnsList);

    /**
     *
     * @param userId
     * @param searchInfo
     * @return
     */
    Page<Columns> getColumns(Integer userId, String searchInfo, Pageable pageable);
}
