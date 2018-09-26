package com.qjd.rry.dao.impl;

import com.google.common.collect.Lists;
import com.qjd.rry.dao.ColumnDao;
import com.qjd.rry.entity.Columns;
import com.qjd.rry.entity.delete.ColumnsDelete;
import com.qjd.rry.repository.ColumnRepository;
import com.qjd.rry.repository.delete.ColumnDeleteRepository;
import com.qjd.rry.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-16 14:40
 **/
@Repository
public class ColumnDaoImpl implements ColumnDao {

    @Autowired
    ColumnRepository columnRepository;

    @Autowired
    ColumnDeleteRepository columnDeleteRepository;

    @Override
    public Columns getColumnById(Integer id) {
        Columns columns = columnRepository.findFirstById(id);
        if (columns == null) {
            ColumnsDelete columnsDelete = columnDeleteRepository.findFirstById(id);
            if (columnsDelete != null) {
                columns = new Columns();
                BeanUtil.copy(columnsDelete, columns);
            }
        }
        return columns;
    }

    @Override
    public Columns updateColumn(Columns businessColumns) {
        Columns columns = columnRepository.findFirstById(businessColumns.getId());
        if (businessColumns.getPrice() != null)
            columns.setPrice(businessColumns.getPrice());
        if (businessColumns.getUserVip() != null)
            columns.setUserVip(businessColumns.getUserVip());
        if (businessColumns.getName() != null)
            columns.setName(businessColumns.getName());
        if (businessColumns.getProportion() != null)
            columns.setProportion(businessColumns.getProportion());
        if (businessColumns.getUserId() != null)
            columns.setUserId(businessColumns.getUserId());
        if (businessColumns.getCover() != null)
            columns.setCover(businessColumns.getCover());
        if (businessColumns.getIntro() != null)
            columns.setIntro(businessColumns.getIntro());
        columns.setUpdateTime(new Date());
        return columnRepository.save(columns);
    }

    @Override
    public List<Columns> getColumnByIdIn(List<Integer> columnIdList) {
        return columnRepository.findAllByIdIn(columnIdList);
    }

    @Override
    public void deleteAllInBatch(List<Columns> columnsList) {
        Date now = new Date();
        List<ColumnsDelete> columnsDeleteList = columnsList.stream().map(columns -> {
            ColumnsDelete columnsDelete = new ColumnsDelete();
            BeanUtil.copy(columns, columnsDelete);
            columnsDelete.setUpdateTime(now);
            return columnsDelete;
        }).collect(Collectors.toList());
        columnDeleteRepository.saveAll(columnsDeleteList);
        columnRepository.deleteAll(columnsList);


    }

    @Override
    public Page<Columns> getColumns(Integer userId, String searchInfo, Pageable pageable) {
        Page<Columns> page = columnRepository.findAll((Specification<Columns>) (root, query, cb) -> {
            List<Predicate> list = Lists.newArrayList();
            if (userId != null)
                list.add(cb.equal(root.get("userId").as(Integer.class), userId));
            if (searchInfo != null && !searchInfo.isEmpty())
                list.add(cb.like(root.get("name").as(String.class), "%" + searchInfo + "%"));
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }, pageable);
        return page;
    }
}
