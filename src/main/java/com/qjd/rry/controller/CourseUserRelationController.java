package com.qjd.rry.controller;

import com.qjd.rry.entity.CourseUserRelation;
import com.qjd.rry.repository.CourseUserRelationRepository;
import com.qjd.rry.request.CourseUserRelationGetRequest;
import com.qjd.rry.utils.Result;
import com.qjd.rry.utils.ResultUtils;
import io.shardingjdbc.core.keygen.DefaultKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @program: rry
 * @description: 分库分表相关测试controller
 * @author: XiaoYu
 * @create: 2018-06-15 18:37
 **/
@RestController
@RequestMapping("/CourseUserRelation")
@ApiIgnore
public class CourseUserRelationController {

    @Autowired
    CourseUserRelationRepository courseUserRelationRepository;

    //@Autowired
    DefaultKeyGenerator defaultKeyGenerator;//主键生成

    @PostMapping("/createCourseUserRelation")
    public Result createCourseUserRelation(@RequestBody CourseUserRelation courseUserRelation, BindingResult bindingResult) {
        courseUserRelation.setId(defaultKeyGenerator.generateKey().longValue());
        courseUserRelationRepository.save(courseUserRelation);
        return ResultUtils.success();
    }

    @GetMapping("/getCourseUserRelation")
    public Result getCourseUserRelation(CourseUserRelationGetRequest request, BindingResult bindingResult, @RequestParam Long id) {
        return ResultUtils.success(courseUserRelationRepository.getOne(request.getId()));
    }
}
