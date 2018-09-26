package com.qjd.rry.service.impl;

import com.qjd.rry.convert.UserConvert;
import com.qjd.rry.convert.bean.ColumnConvertBean;
import com.qjd.rry.convert.bean.CourseConvertBean;
import com.qjd.rry.repository.ColumnRepository;
import com.qjd.rry.repository.CourseColumnRelationRepository;
import com.qjd.rry.repository.CourseRepository;
import com.qjd.rry.repository.UserRepository;
import com.qjd.rry.response.SearchResultResponse;
import com.qjd.rry.response.V0.ColumnInfo;
import com.qjd.rry.response.V0.CourseInfo;
import com.qjd.rry.response.V0.TeacherInfo;
import com.qjd.rry.service.SearchService;
import com.qjd.rry.utils.DateTypeUtil;
import com.qjd.rry.utils.Result;
import com.qjd.rry.utils.ResultUtils;
import com.qjd.rry.utils.TimeTransUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ColumnRepository columnRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseColumnRelationRepository courseColumnRelationRepository;

    @Autowired
    CourseConvertBean courseConvert;

    @Autowired
    ColumnConvertBean columnConvert;

    @Override
    public Result<SearchResultResponse> querySearchResult(String searchInfo) {
        Boolean isInteger=DateTypeUtil.isNumericZidai(searchInfo);
        Date date =TimeTransUtil.getSixBeforeTime();
        List<TeacherInfo> teacherInfoList;
        if (isInteger){
            teacherInfoList = userRepository.findAllByIdLikeOrNicknameLike(Integer.parseInt(searchInfo), "%"+searchInfo+"%").stream().map(UserConvert::userToTeacherInfo).collect(Collectors.toList());
        }else {
            teacherInfoList=userRepository.findAllByNicknameLike("%"+searchInfo+"%").stream().map(UserConvert::userToTeacherInfo).collect(Collectors.toList());;
        }
        List<CourseInfo> courseInfoList=courseRepository.findAllByTitleLike("%"+searchInfo+"%").stream().map(course -> courseConvert.CourseToCourseInfo(course, date)).collect(Collectors.toList());
        List<ColumnInfo> columnInfoList=columnRepository.findAllByNameLike("%"+searchInfo+"%").stream().map(columnConvert::ColumnToColumnInfo).collect(Collectors.toList());
        SearchResultResponse result=new SearchResultResponse();
        result.setColumnInfoList(columnInfoList);
        result.setCourseInfoList(courseInfoList);
        result.setTeacherInfoList(teacherInfoList);
        return ResultUtils.success(result);
    }
}
