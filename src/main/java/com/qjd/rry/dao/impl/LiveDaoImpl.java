package com.qjd.rry.dao.impl;

import com.qjd.rry.dao.LiveDao;
import com.qjd.rry.entity.Live;
import com.qjd.rry.entity.delete.LiveDelete;
import com.qjd.rry.repository.LiveRepository;
import com.qjd.rry.repository.delete.LiveDeleteRepository;
import com.qjd.rry.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-10 22:17
 **/
@Repository
public class LiveDaoImpl implements LiveDao {

    @Autowired
    LiveRepository liveRepository;

    @Autowired
    LiveDeleteRepository liveDeleteRepository;

    @Override
    public Live createLive(Live businessLive) {
        businessLive.setCreateTime(new Date());
        businessLive.setUpdateTime(new Date());
        return liveRepository.save(businessLive);
    }

    @Override
    public Live getLiveById(Integer id) {
        return liveRepository.getOne(id);
    }

    @Override
    public Live getLiveByCourseId(Integer courseId) {
        Live live=liveRepository.findFirstByCourseIdOrderByCreateTimeDesc(courseId);
        if (courseId==null||live==null)
            return new Live();
        else
            return live;
    }

    @Override
    public Page<Live> getLiveInBatch(Live live, Pageable pageable) {
        Assert.notNull(live,"live is required !");
        Page<Live> result=liveRepository.findAll((Specification<Live>) (root, query, cb) -> {
            List<Predicate> list=new ArrayList<>();
            if (!StringUtils.isEmpty(live.getCourseId()))
                list.add(cb.equal(root.get("courseId").as(Integer.class),live.getCourseId()));
            if (!StringUtils.isEmpty(live.getRecordUrl()))
                list.add(cb.equal(root.get("recordUrl").as(String.class),live.getRecordUrl()));
            if (!StringUtils.isEmpty(live.getRecordTimeLength()))
                list.add(cb.equal(root.get("recordTimeLength").as(Integer.class),live.getRecordTimeLength()));
            Predicate[] p=new Predicate[list.size()];
            return cb.and(list.toArray(p));
        },pageable);
        return result;
    }

    @Override
    public Live updateLive(Live businessLive) {
        Live live=liveRepository.getOne(businessLive.getId());
        if (businessLive.getRecordUrl()!=null)
            live.setRecordUrl(businessLive.getRecordUrl());
        if (businessLive.getRecordTimeLength()!=null)
            live.setRecordTimeLength(businessLive.getRecordTimeLength());
        if (businessLive.getCourseId()!=null)
            live.setCourseId(businessLive.getCourseId());
        if (businessLive.getWeihouLiveId()!=null)
            live.setWeihouLiveId(businessLive.getWeihouLiveId());
        live.setUpdateTime(new Date());
        return liveRepository.save(live);
    }

    @Override
    public void deleteInBatch(List<Live> liveList) {
        Date now=new Date();
        List<LiveDelete> liveDeleteList=liveList.stream().map(live -> {
            LiveDelete liveDelete=new LiveDelete();
            BeanUtil.copy(live,liveDelete);
            liveDelete.setUpdateTime(now);
            return liveDelete;
        }).collect(Collectors.toList());
        liveDeleteRepository.saveAll(liveDeleteList);
        liveRepository.deleteInBatch(liveList);
    }
}
