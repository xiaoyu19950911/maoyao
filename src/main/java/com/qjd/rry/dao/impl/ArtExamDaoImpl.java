package com.qjd.rry.dao.impl;

import com.google.common.collect.Lists;
import com.qjd.rry.dao.ArtExamDao;
import com.qjd.rry.entity.ArtExam;
import com.qjd.rry.entity.delete.ArtExamDelete;
import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.repository.ArtExamRepository;
import com.qjd.rry.repository.delete.ArtExamDeleteRepository;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.utils.BeanUtil;
import com.qjd.rry.utils.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-18 10:26
 **/
@Repository
public class ArtExamDaoImpl implements ArtExamDao {

    @Autowired
    ArtExamRepository artExamRepository;

    @Autowired
    ArtExamDeleteRepository artExamDeleteRepository;

    @Override
    public ArtExam createArtExam(ArtExam businessArtExam) {
        businessArtExam.setUpdateTime(new Date());
        businessArtExam.setCreateTime(new Date());
        return artExamRepository.save(businessArtExam);
    }

    @Override
    public ListResponse getArtExamByType(Integer type, Pageable pageable) {
        Page<ArtExam> page;
        if (type == null) {
            page = artExamRepository.findAll(pageable);
        } else {
            page = artExamRepository.findAllByType(type, pageable);
        }
        return PageUtil.PageListToListResponse(page);
    }

    @Override
    public ArtExam updateExam(ArtExam businessArtExam) {
        if (businessArtExam.getId() == null)
            throw new CommunalException(-1, "artExam id:" + businessArtExam.getId() + " not found!");
        ArtExam artExam = artExamRepository.getOne(businessArtExam.getId());
        if (StringUtils.isNotEmpty(businessArtExam.getTurl()))
            artExam.setTurl(businessArtExam.getTurl());
        if (StringUtils.isNotEmpty(businessArtExam.getPurl()))
            artExam.setPurl(businessArtExam.getPurl());
        if (StringUtils.isNotEmpty(businessArtExam.getTitle()))
            artExam.setTitle(businessArtExam.getTitle());
        if (businessArtExam.getType() != null)
            artExam.setType(businessArtExam.getType());
        if (businessArtExam.getContentType()!=null)
            artExam.setContentType(businessArtExam.getContentType());
        artExam.setUpdateTime(new Date());
        return artExamRepository.save(artExam);
    }

    @Override
    public void deleteArtExamInBatchByIdList(List<Integer> artIdList) {
        Date now = new Date();
        List<ArtExamDelete> artExamDeleteList = Lists.newArrayList();
        for (Integer id : artIdList) {
            ArtExam artExam = artExamRepository.getOne(id);
            ArtExamDelete artExamDelete = new ArtExamDelete();
            BeanUtil.copy(artExam, artExamDelete);
            artExamDelete.setUpdateTime(now);
            artExamDeleteList.add(artExamDelete);
        }
        artExamDeleteRepository.saveAll(artExamDeleteList);
        artExamRepository.deleteAllByIdIn(artIdList);
    }

    @Override
    public ArtExam getArtExam(Integer id) {
        return artExamRepository.getOne(id);
    }
}
