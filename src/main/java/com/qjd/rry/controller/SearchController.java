package com.qjd.rry.controller;

import com.qjd.rry.response.SearchResultResponse;
import com.qjd.rry.service.SearchService;
import com.qjd.rry.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@Api(value = "search", description = "搜索相关接口")
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("/querysearchresult")
    @ApiOperation("搜索相关信息")
    public Result<SearchResultResponse> getSearchResult(@RequestParam String searchInfo) {
        return searchService.querySearchResult(searchInfo);
    }
}
