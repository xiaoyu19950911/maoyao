package com.qjd.rry.service.impl;

import com.qjd.rry.convert.OrderConvert;
import com.qjd.rry.convert.VipConvert;
import com.qjd.rry.dao.CategoryDao;
import com.qjd.rry.dao.UserDao;
import com.qjd.rry.dao.VipCardDao;
import com.qjd.rry.dao.VipDao;
import com.qjd.rry.entity.Category;
import com.qjd.rry.entity.User;
import com.qjd.rry.entity.Vip;
import com.qjd.rry.entity.VipCard;
import com.qjd.rry.enums.CategoryEnums;
import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.repository.VipCardRepository;
import com.qjd.rry.repository.VipRepository;
import com.qjd.rry.request.VipCardAwakeRequest;
import com.qjd.rry.request.VipCardListGetRequest;
import com.qjd.rry.request.VipPumpUpdateRequest;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.response.V0.VipCardGetResponse;
import com.qjd.rry.response.V0.VipUser;
import com.qjd.rry.response.VipInfoResponse;
import com.qjd.rry.response.VipPumpGetResponse;
import com.qjd.rry.service.VipService;
import com.qjd.rry.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VipServiceImpl implements VipService {

    @Autowired
    VipRepository vipRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    VipDao vipDao;

    @Autowired
    UserDao userDao;

    @Autowired
    VipCardRepository vipCardRepository;

    @Autowired
    VipCardDao vipCardDao;

    @Override
    public Result<VipInfoResponse> queryVipInfo() {
        Vip vip = vipRepository.findFirstByUserId(tokenUtil.getUserId());
        if (vip == null) {
            vip = Vip.builder().type(CategoryEnums.NULL_VIP.getCode()).build();
        }
        VipInfoResponse vipInfoResponse = VipConvert.VipToVipInfoResponse(vip);
        return ResultUtils.success(vipInfoResponse);
    }

    @Override
    @Transactional
    public Result updateVipPump(VipPumpUpdateRequest request) {
        Category category = categoryDao.getCategoryByCode(CategoryEnums.VIP.getCode());
        if (request.getPump() != null)
            category.setContext1(request.getPump().toString());//设置vip折扣
        if (request.getIsBuyOneGetOneFree() != null)
            category.setContext2(request.getIsBuyOneGetOneFree().toString());
        categoryDao.updateCategory(category);
        return ResultUtils.success();
    }

    @Override
    public Result<ListResponse<VipUser>> getVipUserList(Pageable pageable) {
        Page<Vip> page = vipDao.getAllVip(pageable);
        ListResponse response = PageUtil.PageListToListResponse(page);
        List<VipUser> vipUserList = page.getContent().stream().map(vip -> {
            User user = userDao.getUserById(vip.getUserId());
            return VipUser.builder().endTime(TimeTransUtil.DateToUnix(vip.getEndTime())).nickName(user.getNickname()).startTime(TimeTransUtil.DateToUnix(vip.getStartTime())).type(vip.getType()).userId(vip.getUserId()).build();
        }).collect(Collectors.toList());
        response.setValue(vipUserList);
        return ResultUtils.success(response);
    }

    @Override
    public Result<VipPumpGetResponse> getVipPump() {
        Category category = categoryDao.getCategoryByCode(CategoryEnums.VIP.getCode());
        VipPumpGetResponse result = VipPumpGetResponse.builder().pump(category.getContext1()).isBuyOneGetOneFree(Boolean.valueOf(category.getContext2())).build();
        return ResultUtils.success(result);
    }

    @Override
    @Transactional
    public Result awakeVipCard(VipCardAwakeRequest request) {
        Date now = new Date();
        Integer userId = request.getUserId() == null ? tokenUtil.getUserId() : request.getUserId();
        String cardId = request.getCardId();
        VipCard vipCard = vipCardRepository.findFirstByCardId(cardId);
        if (vipCard == null)
            throw new CommunalException(-1, "卡号不存在！");
        if (vipCard.getIsAwake())
            throw new CommunalException(-1, "该卡已失效！");
        vipCard.setAwakeTime(now);
        vipCard.setAwakeUserId(userId);
        vipCard.setIsAwake(Boolean.TRUE);
        vipCard.setUpdateTime(now);
        vipCardRepository.save(vipCard);
        Category category = categoryDao.getCategoryByCode(CategoryEnums.YEAR_VIP.getCode());

        Vip vip = vipDao.getVipByUserId(userId);
        Vip businessVip;
        Calendar calendar = Calendar.getInstance();
        if (vip == null) {
            businessVip = OrderConvert.categoryToVip(category, userId);
            vipRepository.save(businessVip);
        } else {
            if (vip.getEndTime().after(new Date()))
                calendar.setTime(vip.getEndTime());
            switch (category.getTimeType()) {
                case 1:
                    calendar.add(Calendar.YEAR, Integer.parseInt(category.getContext1()));
                    break;
                case 2:
                    calendar.add(Calendar.MONTH, Integer.parseInt(category.getContext1()));
                    break;
                case 3:
                    calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(category.getContext1()));
                    break;
                case 4:
                    calendar.add(Calendar.HOUR, Integer.parseInt(category.getContext1()));
                    break;
            }
            if (vip.getEndTime().before(now))
                vip.setStartTime(now);
            vip.setEndTime(calendar.getTime());
            vip.setUpdateTime(now);
            vip.setType(category.getCode());
            vipRepository.save(vip);
        }
        return ResultUtils.success();
    }


    @Override
    public Result<ListResponse<VipCardGetResponse>> getVipCardList(VipCardListGetRequest request, Pageable pageable) {
        Integer userId = request.getUserId() == null ? tokenUtil.getUserId() : request.getUserId();
        Page<VipCard> vipCardPage = vipCardDao.getVipCards(userId, request.getIsAwake(), pageable);
        ListResponse result = PageUtil.PageListToListResponse(vipCardPage);
        List<VipCardGetResponse> vipUserList = vipCardPage.getContent().stream().map(vipCard ->
        {
            User user = userDao.getUserById(vipCard.getAwakeUserId());
            return VipCardGetResponse.builder().id(vipCard.getId()).cardId(vipCard.getCardId()).awakeUserNickName(user.getNickname()).awakeTime(vipCard.getAwakeTime()).build();
        }).collect(Collectors.toList());
        result.setValue(vipUserList);
        return ResultUtils.success(result);
    }
}
