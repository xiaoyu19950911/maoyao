package com.qjd.rry.dao;

import com.qjd.rry.entity.ArtExam;
import com.qjd.rry.response.ListResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-05-18 10:25
 **/
public interface ArtExamDao {

    public ArtExam createArtExam(ArtExam artExam);

    public ListResponse getArtExamByType(Integer type, Pageable pageable);

    ArtExam updateExam(ArtExam businessArtExam);

    void deleteArtExamInBatchByIdList(List<Integer> artIdList);

    ArtExam getArtExam(Integer id);
}
