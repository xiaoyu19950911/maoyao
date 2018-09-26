package com.qjd.rry.service;

import com.qjd.rry.request.*;
import com.qjd.rry.response.*;
import com.qjd.rry.response.V0.*;
import com.qjd.rry.utils.Result;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Result<UserInfoResponse> queryUserInfo();

    Result<UserLiveRoomResponse> queryUserLiveRoom(Integer userId);

    Result<ListResponse<TeacherInfo>> queryAttentionTeacherList(Integer userId, Pageable pageable);

    Result<ListResponse<TeacherInfo>> queryCategoryTeacherList(String searchInfo, Integer categoryId, Pageable pageable);

    Result<UserAccountInfoResponse> queryUserAccountInfo();

    Result<UserInfoResponse> modifyUserInfo(UserInfoRequest request) throws Exception;

    Result<ListResponse<TeacherInfo>> queryArtistList(ArtistListGetRequest request, Pageable pageable);

    Result<ListResponse<TeacherInfo>> queryPartArtistList(Pageable pageable);

    Result modifiableInfo(ArteRequest request);

    Result<ListResponse<OwnAccount>> queryOwnAccountList(String searchInfo, Pageable pageable);

    Result<UserLiveRoomResponse> modifyAttentionTeacherList(AttentionTeacherListUpdateRequest request);

    Result<ListResponse<Agents>> queryAgentsList(AgentsListGetRequest request, Pageable pageable);

    Result<ListResponse<InvitationArtistInfo>> queryInvitationArtistList(InvitationArtistListGetRequest request, Pageable pageable);

    Result<ListResponse<IncomeGetResponse>> getIncomeResponse(IncomeResponseGetRequest request, Pageable pageable);

    Result<ListResponse<ExpenditureGetResponse>> getExpenditureResponse(Pageable pageable);

    Result<ListResponse<WebTeacherInfo>> getTeacherListByCategory(Integer categoryId, Integer status, Boolean isBanner, String role, Pageable pageable);

    Result<List<CategoryListGetResponse>> getCategoryList(Integer userId);

    Result updateArteCategory(ArteCategoryUpdateRequest request);

    Result<UserInfoGetResponse> getUserInfo(Integer userId);

    Result updateUser(UserUpdateRequest request) throws Exception;

    Result<ListResponse<AllIncomeGetResponse>> getAllIncomeResponse(AllIncomeGetRequest request, Pageable pageable);

    Result<IncomeInfoGetResponse> getIncomeInfo(Integer orderItemId);

    Result<ListResponse<WebTeacherInfo>> getTeacherListByCategory(TeacherListByCategoryGetRequest request, Pageable pageable);

    Result deleteArtistInfoInBatch(List<ArteRequest> request);

    Result modifyAccountInfo(AccountInfoModifyRequest request);

    Result<IncomeSituationGetResponse> getIncomeSituation(IncomeSituationGetRequest request);

    Result<UserIncomeSituationGetResponse> getUserIncomeSituation(UserIncomeSituationGetRequest request);
}
