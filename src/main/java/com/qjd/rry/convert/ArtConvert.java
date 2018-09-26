package com.qjd.rry.convert;

import com.qjd.rry.entity.ArtExam;
import com.qjd.rry.response.ArtExamResponse;
import com.qjd.rry.utils.TimeTransUtil;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-03-30 16:34
 **/
public class ArtConvert {

    public static ArtExamResponse ArtExamToArtExamResponse(ArtExam artExam){
        ArtExamResponse artExamResponse=new ArtExamResponse();
        artExamResponse.setAdmissionId(artExam.getId());
        artExamResponse.setTurl(artExam.getTurl());
        artExamResponse.setPurl(artExam.getPurl());
        artExamResponse.setTime(TimeTransUtil.DateToUnix(artExam.getCreateTime()));
        artExamResponse.setTitle(artExam.getTitle());
        artExamResponse.setContentType(artExam.getContentType());
        return artExamResponse;
    }
}
