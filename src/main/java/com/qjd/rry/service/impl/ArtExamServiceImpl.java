package com.qjd.rry.service.impl;

import com.qjd.rry.convert.ArtConvert;
import com.qjd.rry.dao.ArtExamDao;
import com.qjd.rry.entity.ArtExam;
import com.qjd.rry.request.AdmissionCreateRequest;
import com.qjd.rry.request.AdmissionDeleteRequest;
import com.qjd.rry.request.AdmissionUpdateRequest;
import com.qjd.rry.response.AdmissionInfoByIdGetResponse;
import com.qjd.rry.response.ArtExamResponse;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.service.ArtExamService;
import com.qjd.rry.utils.Result;
import com.qjd.rry.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtExamServiceImpl implements ArtExamService {

    @Autowired
    ArtExamDao artExamDao;

    @Override
    public Result<ListResponse<ArtExamResponse>> queryAdmissionInfo(Integer type, Pageable pageable) {
        ListResponse result=artExamDao.getArtExamByType(type,pageable);
        List<ArtExam> artExamList=result.getValue();
        List<ArtExamResponse> artExamResponseList=artExamList.stream().map(ArtConvert::ArtExamToArtExamResponse).collect(Collectors.toList());
        result.setValue(artExamResponseList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ArtExamResponse> createAdmission(AdmissionCreateRequest request) {
        ArtExam businessArtExam=ArtExam.builder().turl(request.getUrl()).title(request.getTitle()).purl(request.getAvatar()).contentType(request.getContentType()).type(request.getType()).build();
        ArtExam artExam=artExamDao.createArtExam(businessArtExam);
        ArtExamResponse result=ArtConvert.ArtExamToArtExamResponse(artExam);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ArtExamResponse> updateAdmission(AdmissionUpdateRequest request) {
        ArtExam businessArtExam=ArtExam.builder().id(request.getId()).turl(request.getContext()).purl(request.getAvatar()).contentType(request.getContentType()).title(request.getTitle()).type(request.getType()).build();
        ArtExam artExam=artExamDao.updateExam(businessArtExam);
        ArtExamResponse result=ArtConvert.ArtExamToArtExamResponse(artExam);
        return ResultUtils.success(result);
    }

    @Override
    @Transactional
    public Result deleteAdmission(AdmissionDeleteRequest request) {
        artExamDao.deleteArtExamInBatchByIdList(request.getArtIdList());
        return ResultUtils.success();
    }

    @Override
    public Result<AdmissionInfoByIdGetResponse> getAdmissionInfoById(Integer id) {
        ArtExam artExam=artExamDao.getArtExam(id);
        AdmissionInfoByIdGetResponse result=AdmissionInfoByIdGetResponse.builder().id(id).title(artExam.getTitle()).cover(artExam.getPurl()).content(artExam.getTurl()).contentType(artExam.getContentType()).build();
        return ResultUtils.success(result);
    }
}
