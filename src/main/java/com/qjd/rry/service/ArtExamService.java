package com.qjd.rry.service;

import com.qjd.rry.request.AdmissionCreateRequest;
import com.qjd.rry.request.AdmissionDeleteRequest;
import com.qjd.rry.request.AdmissionUpdateRequest;
import com.qjd.rry.response.AdmissionInfoByIdGetResponse;
import com.qjd.rry.response.ArtExamResponse;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.utils.Result;
import org.springframework.data.domain.Pageable;

public interface ArtExamService {
    Result<ListResponse<ArtExamResponse>> queryAdmissionInfo(Integer type, Pageable pageable);

    Result<ArtExamResponse> createAdmission(AdmissionCreateRequest request);

    Result<ArtExamResponse> updateAdmission(AdmissionUpdateRequest request);

    Result deleteAdmission(AdmissionDeleteRequest request);

    Result<AdmissionInfoByIdGetResponse> getAdmissionInfoById(Integer id);
}
