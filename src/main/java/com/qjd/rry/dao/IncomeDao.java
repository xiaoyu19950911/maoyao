package com.qjd.rry.dao;

import com.qjd.rry.entity.Income;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-16 10:18
 **/
public interface IncomeDao {

    /**
     *
     * @param income
     * @return Income
     */
    public Income createIncome(Income income);

    /**
     *
     * @param id
     * @param userId
     * @param resourceTypeList
     * @param incomeTypeList
     * @param resourceId
     * @param buyResourceUserId
     * @param ownResourceUserId
     * @param pageable
     * @return Page<Income>
     */
    public Page<Income> getIncome(Integer id,Integer orderItemId, Integer userId, List<Integer> resourceTypeList, List<Integer> incomeTypeList, Integer resourceId, Integer buyResourceUserId, Integer ownResourceUserId, Pageable pageable);

    /**
     *
     * @param orderItem
     * @return BigDecimal
     */
    public BigDecimal getProIncomeAmount(Integer orderItem);

    /**
     *
     * @param userId
     * @return Page<Income>
     */
    Page<Income> getAllIncomeByUserId(Integer userId,Pageable pageable);

    Page<Income> getAllIncomeByUserIdAndCreateTime(Integer userId, Date startTime, Date endTime, Pageable pageable);
}
