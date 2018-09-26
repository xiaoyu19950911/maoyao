package com.qjd.rry.service;

import com.qjd.rry.response.SearchResultResponse;
import com.qjd.rry.utils.Result;

public interface SearchService {
    Result<SearchResultResponse> querySearchResult(String searchInfo);
}
