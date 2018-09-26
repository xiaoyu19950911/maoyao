package com.qjd.rry.service.impl;

import com.google.common.collect.Lists;
import com.qjd.rry.convert.bean.ColumnConvertBean;
import com.qjd.rry.dao.ColumnDao;
import com.qjd.rry.dao.CourseColumnIntroDao;
import com.qjd.rry.dao.CourseColumnRelationDao;
import com.qjd.rry.dao.CourseUserRelationDao;
import com.qjd.rry.entity.*;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.exception.CommunalException;
import com.qjd.rry.repository.*;
import com.qjd.rry.request.*;
import com.qjd.rry.response.ListResponse;
import com.qjd.rry.response.V0.ColumnDetailedInfo;
import com.qjd.rry.response.V0.ColumnInfo;
import com.qjd.rry.service.ColumnService;
import com.qjd.rry.utils.PageUtil;
import com.qjd.rry.utils.Result;
import com.qjd.rry.utils.ResultUtils;
import com.qjd.rry.utils.TokenUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColumnServiceImpl implements ColumnService {

    @Autowired
    CourseColumnRelationDao courseColumnRelationDao;

    @Autowired
    ColumnRepository columnRepository;

    @Autowired
    ColumnConvertBean columnConvert;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    CourseUserRelationRepository courseUserRelationRepository;

    @Autowired
    CourseColumnRelationRepository courseColumnRelationRepository;

    @Autowired
    CourseColumnIntroRepository courseColumnIntroRepository;

    @Autowired
    ColumnDao columnDao;

    @Autowired
    CourseColumnIntroDao courseColumnIntroDao;

    @Autowired
    CourseUserRelationDao courseUserRelationDao;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserAuthsRepository userAuthsRepository;

    @Override
    public Result<ListResponse<ColumnInfo>> queryUserColumnInfoList(UserColumnInfoListGetRequest request, Pageable pageable) {

        Integer userId=request.getUserId();
        String searchInfo=request.getSearchInfo();
        Page<Columns> pageList=columnDao.getColumns(userId,searchInfo,pageable);



        /*if (userId != null) {
            pageList = columnRepository.findAllByUserId(userId, pageable);
        } else {
            pageList = columnRepository.findAll(pageable);
        }*/
        ListResponse<ColumnInfo> result = PageUtil.PageListToListResponse(pageList);
        List<ColumnInfo> columnInfoList = pageList.getContent().stream().map(columnConvert::ColumnToColumnInfo).collect(Collectors.toList());
        result.setValue(columnInfoList);
        return ResultUtils.success(result);
    }

    @Override
    public Result<ListResponse<ColumnInfo>> queryPurchasedColumnInfoList(Pageable pageable) {
        Integer userId = tokenUtil.getUserId();
        Page<Integer> page = courseUserRelationDao.getAllColumnIdByUserId(userId, pageable);
        //Page<CourseUserRelation> page=courseUserRelationRepository.findAllByUserId(userId,pageable);
        ListResponse result = PageUtil.PageListToListResponse(page);
        List<Integer> columnIdList = page.getContent();
        if (CollectionUtils.isNotEmpty(columnIdList)) {
            List<Columns> columnsList = columnDao.getColumnByIdIn(columnIdList);
            List<ColumnInfo> columnInfoList = columnsList.stream().map(columnConvert::ColumnToColumnInfo).collect(Collectors.toList());
            result.setValue(columnInfoList);
        }
        return ResultUtils.success(result);
    }

    @Override
    public Result<ColumnDetailedInfo> queryPurchasedColumnInfo(Integer columnId) {
        List<CourseColumnIntro> courseColumnIntroList = courseColumnIntroRepository.findAllByReferenceIdAndReferenceType(columnId, ProgramEnums.REFERENCE_TYPE_COLUMN.getCode());
        Columns columns = columnRepository.findFirstById(columnId);
        ColumnDetailedInfo result = columnConvert.ColumnToColumnDetailedInfo(columns, courseColumnIntroList);
        return ResultUtils.success(result);
    }

    @Override
    @Transactional
    public Result createColumn(ColumnInfoRequest request) {
        Columns columns = columnConvert.ColumnInfoRequestToColumn(request);
        Date now = new Date();
        columnRepository.save(columns);
        if (request.getRichTextList() != null && request.getRichTextList().size() != 0) {
            List<CourseColumnIntro> courseColumnIntroList = request.getRichTextList().stream().map(richText -> CourseColumnIntro.builder().type(richText.getType()).context(richText.getContext()).referenceId(columns.getId()).referenceType(ProgramEnums.REFERENCE_TYPE_COLUMN.getCode()).createTime(now).updateTime(now).build()).collect(Collectors.toList());
            courseColumnIntroRepository.saveAll(courseColumnIntroList);
        }
        if (request.getCourseIdList() != null && request.getCourseIdList().size() != 0) {
            List<CourseColumnRelation> courseColumnRelationList = request.getCourseIdList().stream().map(id -> {
                CourseColumnRelation courseColumnRelation = new CourseColumnRelation();
                courseColumnRelation.setCourseId(id);
                courseColumnRelation.setCreateTime(now);
                courseColumnRelation.setUpdateTime(now);
                courseColumnRelation.setColumnId(columns.getId());
                return courseColumnRelation;
            }).collect(Collectors.toList());
            courseColumnRelationRepository.saveAll(courseColumnRelationList);
        }
        return ResultUtils.success();
    }

    @Override
    @Transactional
    public Result modifyColumn(UpdateColumnInfoRequest request) {
        Date now = new Date();
        Columns businessColumn = Columns.builder().id(request.getColumnId()).cover(request.getPurl()).name(request.getTitle()).price(request.getPrice()).proportion(request.getProportion()).userVip(request.getUseVip()).build();
        columnDao.updateColumn(businessColumn);
        List<CourseColumnIntro> courseColumnIntroList=courseColumnIntroRepository.findAllByReferenceIdAndReferenceType(request.getColumnId(),ProgramEnums.REFERENCE_TYPE_COLUMN.getCode());
        courseColumnIntroDao.deleteInBatch(courseColumnIntroList);
        if (request.getRichTextList() != null && request.getRichTextList().size() != 0) {
            courseColumnIntroList = request.getRichTextList().stream().map(richText -> CourseColumnIntro.builder().type(richText.getType()).context(richText.getContext()).referenceId(request.getColumnId()).referenceType(ProgramEnums.REFERENCE_TYPE_COLUMN.getCode()).createTime(now).updateTime(now).build()).collect(Collectors.toList());
            courseColumnIntroDao.createCourseColumnIntroInBatch(courseColumnIntroList);
        }
        return ResultUtils.success();
    }

    @Override
    @Transactional
    public Result createCourseToColumn(CourseToColumnRequest request) {
        Date now = new Date();
        List<CourseColumnRelation> courseColumnRelationList = request.getCourseIdList().stream().map(id -> {
            CourseColumnRelation courseColumnRelation = new CourseColumnRelation();
            courseColumnRelation.setCourseId(id);
            courseColumnRelation.setUpdateTime(now);
            courseColumnRelation.setCreateTime(now);
            courseColumnRelation.setColumnId(request.getColumnId());
            return courseColumnRelation;
        }).collect(Collectors.toList());
        courseColumnRelationRepository.saveAll(courseColumnRelationList);
        return ResultUtils.success();
    }

    @Override
    @Transactional
    public Result deleteColumn(ColumnDeleteRequest request) {
        List<Integer> columnIdList = request.getColumnIdList();
        CourseUserRelation courseUserRelation;
        for (Integer columnId : columnIdList) {
            courseUserRelation = courseUserRelationRepository.findFirstByColumnId(columnId);
            if (courseUserRelation != null && courseUserRelation.getPrice().compareTo(BigDecimal.ZERO) != 0) {
                throw new CommunalException(-1, "该专栏已被购买，无法删除！");
            }
        }
        List<Columns> columnsList = columnRepository.findAllByIdIn(columnIdList);
        columnDao.deleteAllInBatch(columnsList);
        List<CourseUserRelation> courseUserRelationList = courseUserRelationRepository.findAllByColumnIdIn(columnIdList);
        courseUserRelationDao.deleteInBatch(courseUserRelationList);
        List<CourseColumnIntro> courseColumnIntroList=courseColumnIntroRepository.findAllByReferenceIdInAndReferenceType(columnIdList,ProgramEnums.REFERENCE_TYPE_COLUMN.getCode());
        courseColumnIntroDao.deleteInBatch(courseColumnIntroList);
        List<CourseColumnRelation> courseColumnRelationList=courseColumnRelationRepository.findAllByColumnIdIn(columnIdList);
        courseColumnRelationDao.deleteInBatch(courseColumnRelationList);
        return ResultUtils.success();
    }

    @Override
    public Result<ListResponse<ColumnInfo>> getVipFreeColumnList(Pageable pageable) {
        List<Role> roleList = roleRepository.findAllByIdIn(Lists.newArrayList(ProgramEnums.ROLE_AGENTS.getCode(), ProgramEnums.ROLE_ADMIN.getCode(),ProgramEnums.ROLE_AGENTS_2.getCode()));//平台自由账号和代理商
        List<Integer> userIdList = userAuthsRepository.findAllByRolesIn(roleList).stream().map(UserAuths::getId).collect(Collectors.toList());//平台自由账号和代理商
        Page<Columns> columnsPage = columnRepository.findAllByPriceAfterAndUserVipAndUserIdInOrderByCreateTimeDesc(BigDecimal.ZERO, Boolean.TRUE, userIdList, pageable);
        ListResponse result = PageUtil.PageListToListResponse(columnsPage);
        List<ColumnInfo> columnInfoList = columnsPage.getContent().stream().map(columnConvert::ColumnToColumnInfo).collect(Collectors.toList());
        result.setValue(columnInfoList);
        return ResultUtils.success(result);
    }

    @Override
    @Transactional
    public Result deleteCourseFromColumn(CourseToColumnRequest request) {
        courseColumnRelationRepository.deleteAllByColumnIdAndCourseIdIn(request.getColumnId(), request.getCourseIdList());
        return ResultUtils.success();
    }

}
