package com.qjd.rry.service.impl;

import com.qjd.rry.convert.bean.UserConvertBean;
import com.qjd.rry.entity.Category;
import com.qjd.rry.entity.User;
import com.qjd.rry.enums.CategoryEnums;
import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.repository.CategoryRepository;
import com.qjd.rry.repository.UserRepository;
import com.qjd.rry.request.BannerCountUpdateRequest;
import com.qjd.rry.request.BannerListUpdateRequest;
import com.qjd.rry.response.BannerCountGetResponse;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.response.V0.WebTeacherInfo;
import com.qjd.rry.service.BannerService;
import com.qjd.rry.utils.PageUtil;
import com.qjd.rry.utils.Result;
import com.qjd.rry.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BannerServiceImpl implements BannerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserConvertBean userConvertBean;


    @Override
    @Transactional
    public Result modifyBanner(BannerListUpdateRequest request) {
        List<User> userList = request.getUserIdList().stream().map(id -> {
            User user = userRepository.getOne(id);
            log.debug(user.getNickname());
            if (request.getType() == 1) {
                Category category = categoryRepository.findFirstByCode(CategoryEnums.BANNER_COUNT.getCode());
                List<User> bannerUserList = userRepository.findAllByIsBanner(Boolean.TRUE);
                if (bannerUserList.size() + request.getUserIdList().size() > Integer.parseInt(category.getContext1()))
                    throw new CommunalException(-1, "banner数量超过最大限制！");
                user.setIsBanner(Boolean.TRUE);
            } else if (request.getType() == 2) {
                user.setIsBanner(Boolean.FALSE);
            }
            return user;
        }).collect(Collectors.toList());
        userRepository.saveAll(userList);
        return ResultUtils.success();
    }

    @Override
    public Result<ListResponse<WebTeacherInfo>> getBannerList(Pageable pageable) {
        Page<User> userPage = userRepository.findAllByIsBanner(Boolean.TRUE, pageable);
        ListResponse result = PageUtil.PageListToListResponse(userPage);
        List<WebTeacherInfo> webTeacherInfoList = userConvertBean.userListToWebTeacherInfoList(userPage.getContent());
        result.setValue(webTeacherInfoList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<BannerCountGetResponse> getBannerCount() {
        Category category = categoryRepository.findFirstByCode(CategoryEnums.BANNER_COUNT.getCode());
        List<User> userList = userRepository.findAllByIsBanner(Boolean.TRUE);
        BannerCountGetResponse result = BannerCountGetResponse.builder().bannerCount(Integer.parseInt(category.getContext1())).existBannerCount(userList.size()).build();
        return ResultUtils.success(result);
    }

    @Override
    @Transactional
    public Result updateBannerCount(BannerCountUpdateRequest request) {
        Category category = categoryRepository.findFirstByCode(CategoryEnums.BANNER_COUNT.getCode());
        category.setContext1(request.getBannerCount().toString());
        category.setUpdateTime(new Date());
        categoryRepository.save(category);
        return ResultUtils.success();
    }


}
