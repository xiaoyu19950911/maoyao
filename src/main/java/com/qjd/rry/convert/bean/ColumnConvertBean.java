package com.qjd.rry.convert.bean;

import com.qjd.rry.dao.UserDao;
import com.qjd.rry.dao.VipDao;
import com.qjd.rry.entity.*;
import com.qjd.rry.enums.CategoryEnums;
import com.qjd.rry.repository.*;
import com.qjd.rry.request.ColumnInfoRequest;
import com.qjd.rry.request.UpdateColumnInfoRequest;
import com.qjd.rry.request.V0.RichText;
import com.qjd.rry.response.V0.ColumnDetailedInfo;
import com.qjd.rry.response.V0.ColumnInfo;
import com.qjd.rry.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: rry
 * @description:
 * @author: XiaoYu
 * @create: 2018-04-02 15:35
 **/
@Component
public class ColumnConvertBean {

    @Autowired
    CourseUserRelationRepository courseUserRelationRepository;

    @Autowired
    CourseColumnRelationRepository courseColumnRelationRepository;

    @Autowired
    ColumnRepository columnRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    VipDao vipDao;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDao userDao;

    public ColumnInfo ColumnToColumnInfo(Columns columns) {
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.setId(columns.getId());
        columnInfo.setApplyCount(courseUserRelationRepository.findCountByColumnId(columns.getId()));
        columnInfo.setCover(columns.getCover());
        columnInfo.setName(columns.getName());
        columnInfo.setPrice(columns.getPrice());
        columnInfo.setVersion(courseColumnRelationRepository.findCountByColumnId(columns.getId()));
        return columnInfo;
    }

    public ColumnDetailedInfo ColumnToColumnDetailedInfo(Columns columns, List<CourseColumnIntro> courseColumnIntroList) {
        ColumnDetailedInfo result = new ColumnDetailedInfo();
        Integer userId=tokenUtil.getUserId();
        Vip vip=vipDao.getVipByUserId(userId);
        Boolean isVip=vip!=null&&vip.getEndTime()!=null&&vip.getEndTime().after(new Date());
        List<RichText> richTextList=courseColumnIntroList.stream().map(courseColumnIntro -> RichText.builder().id(courseColumnIntro.getId()).type(courseColumnIntro.getType()).context(courseColumnIntro.getContext()).build()).collect(Collectors.toList());
        result.setId(columns.getId());
        if (columns.getPrice()==null||columns.getPrice().compareTo(BigDecimal.ZERO)==0)
            result.setIsFreeSignUp(true);
        else
            result.setIsFreeSignUp(isVip&&(columns.getUserVip()==null||columns.getUserVip()));
        result.setName(columns.getName());
        result.setPrice(columns.getPrice()==null?"0":columns.getPrice().toString());
        //result.setPrice(columns.getPrice()==null||columns.getPrice().compareTo(new BigDecimal(0)) == 0 ? "免费" : columns.getPrice().toString());
        result.setCover(columns.getCover());
        result.setProportion(columns.getProportion());
        result.setRichTextList(richTextList);
        result.setUseVip(columns.getUserVip());
        result.setUserId(columns.getUserId());
        User user=userRepository.findUserById(columns.getUserId());
        //result.setApplyCount(courseUserRelationRepository.findCountByColumnId(columns.getId()));
        result.setApplyCount(columns.getApplyCount());
        result.setUserName(user.getNickname());
        result.setVersion(courseColumnRelationRepository.findCountByColumnId(columns.getId()));
        result.setStatus(courseUserRelationRepository.findFirstByUserIdAndColumnId(tokenUtil.getUserId(),columns.getId())==null?0:1);
        return result;
    }

    public Columns ColumnInfoRequestToColumn(ColumnInfoRequest request){
        Columns columns=new Columns();
        String purl;
        Integer userId=request.getUserId()==null?tokenUtil.getUserId():request.getUserId();
        if (request.getPurl() == null||request.getPurl().isEmpty()) {
            List<Category> categoryList = categoryRepository.findAllByPCode(CategoryEnums.COLUMN_COVER.getCode());
            Collections.shuffle(categoryList);
            purl = categoryList.get(0).getPurl();
        } else {
            purl = request.getPurl();
        }
        columns.setCover(purl);
        columns.setName(request.getTitle());
        columns.setPrice(request.getPrice());
        columns.setProportion(request.getProportion());
        columns.setUserId(userId);
        columns.setUserVip(request.getUseVip());
        columns.setCreateTime(new Date());
        columns.setUpdateTime(new Date());
        return columns;
    }

    public Columns UpdateColumnInfoRequestToColumn(UpdateColumnInfoRequest request){
        Columns columns=columnRepository.getOne(request.getColumnId());
        columns.setCover(request.getPurl());
        columns.setName(request.getTitle());
        columns.setPrice(request.getPrice());
        columns.setProportion(request.getProportion());
        columns.setUserVip(request.getUseVip());
        columns.setUpdateTime(new Date());
        return columns;
    }
}
