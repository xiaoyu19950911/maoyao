package com.qjd.rry.service.impl;

import com.qjd.rry.entity.Category;
import com.qjd.rry.repository.CategoryRepository;
import com.qjd.rry.request.SeqUpdateRequest;
import com.qjd.rry.service.SystemService;
import com.qjd.rry.utils.Result;
import com.qjd.rry.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-09-03 10:21
 **/
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Result updateSeq(List<SeqUpdateRequest> request) {
        for (SeqUpdateRequest seqUpdateRequest : request) {
            Category category = categoryRepository.getOne(seqUpdateRequest.getId());
            category.setSequence(seqUpdateRequest.getSeq());
            category.setUpdateTime(new Date());
            categoryRepository.save(category);
        }
        return ResultUtils.success();
    }
}
