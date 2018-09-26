package com.qjd.rry.dao.impl;


import com.qjd.rry.dao.IncomeDao;
import com.qjd.rry.entity.Income;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-16 10:19
 **/
@Repository
public class IncomeDaoImpl implements IncomeDao {

    @Autowired
    IncomeRepository incomeRepository;

    @Override
    public Income createIncome(Income businessIncome) {
        businessIncome.setCreateTime(new Date());
        businessIncome.setUpdateTime(new Date());
        return incomeRepository.save(businessIncome);
    }

    @Override
    public BigDecimal getProIncomeAmount(Integer orderItemId) {
        return incomeRepository.findProUserAmount(orderItemId, ProgramEnums.INCOME_TYPE_COMMISSION.getCode());
    }

    @Override
    public Page<Income> getAllIncomeByUserId(Integer userId, Pageable pageable) {
        return incomeRepository.findAllByUserIdOrBuyResourceUserId(userId, userId, pageable);
    }

    @Override
    public Page<Income> getAllIncomeByUserIdAndCreateTime(Integer userId, Date startTime, Date endTime, Pageable pageable) {
        return incomeRepository.findAllByUserIdOrBuyResourceUserIdAndCreateTimeBetween(userId, userId, startTime, endTime, pageable);
    }

    @Override
    public Page<Income> getIncome(Integer id, Integer orderItemId, Integer userId, List<Integer> resourceTypeList, List<Integer> incomeTypeList, Integer resourceId, Integer buyResourceUserId, Integer ownResourceUserId, Pageable pageable) {
        if (pageable == null) {
            Sort sort = new Sort(Sort.Direction.DESC, "id");
            pageable = new PageRequest(0, 10000, sort);
        }
        Page<Income> page = incomeRepository.findAll((Specification<Income>) (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (id != null)
                list.add(cb.equal(root.get("id").as(Integer.class), id));
            if (orderItemId != null)
                list.add(cb.equal(root.get("orderItemId").as(Integer.class), orderItemId));
            if (userId != null)
                list.add(cb.equal(root.get("userId").as(Integer.class), userId));
            if (resourceTypeList != null)
                list.add(cb.and(root.get("resourceType").in(resourceTypeList)));
            if (incomeTypeList != null)
                list.add(cb.and(root.get("incomeType").in(incomeTypeList)));
            if (resourceId != null)
                list.add(cb.equal(root.get("resourceId").as(Integer.class), resourceId));
            if (buyResourceUserId != null)
                list.add(cb.equal(root.get("buyResourceUserId").as(Integer.class), buyResourceUserId));
            if (ownResourceUserId != null)
                list.add(cb.equal(root.get("ownResourceUserId").as(Integer.class), ownResourceUserId));
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }, pageable);
        return page;
    }
}
